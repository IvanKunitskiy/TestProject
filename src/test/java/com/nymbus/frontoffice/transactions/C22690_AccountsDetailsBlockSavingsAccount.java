package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.accountinstructions.ActivityHoldInstruction;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.accountinstructions.InstructionConstructor;
import com.nymbus.newmodels.generation.accountinstructions.builder.ActivityHoldInstructionBuilder;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitDepositCHKAccBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.verifyingModels.BalanceDataForCHKAcc;
import com.nymbus.newmodels.transaction.verifyingModels.TransactionData;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Transactions")
@Owner("Dmytro")
public class C22690_AccountsDetailsBlockSavingsAccount extends BaseTest {
    private BalanceDataForCHKAcc expectedSavingsBalanceData;
    private TransactionData savingsAccTransactionData;
    private Account savingsAccount;
    private double savingsTransactionAmount = 200.00;
    private IndividualClient client;
    private String accruedInterest;
    private ActivityHoldInstruction activityHoldInstruction;

    @BeforeMethod
    public void prepareTransactionData() {
        // Set up Client and Account
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();
        savingsAccount = new Account().setSavingsAccountData();
        Transaction depositSavingsTransaction = new TransactionConstructor(new GLDebitDepositCHKAccBuilder()).constructTransaction();

        // Log in
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set products
        savingsAccount.setProduct(Actions.productsActions().getProduct(Products.SAVINGS_PRODUCTS, AccountType.REGULAR_SAVINGS, RateType.FIXED));

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        client.getIndividualType().setClientID(Pages.clientDetailsPage().getClientID());

        // Create account
        AccountActions.createAccount().createSavingsAccount(savingsAccount);

        // Set up transaction with account number
        depositSavingsTransaction.getTransactionDestination().setAccountNumber(savingsAccount.getAccountNumber());
        depositSavingsTransaction.getTransactionDestination().setTransactionCode("209 - Deposit");
        depositSavingsTransaction.getTransactionDestination().setAmount(savingsTransactionAmount);
        depositSavingsTransaction.getTransactionSource().setAmount(savingsTransactionAmount);
        depositSavingsTransaction.setTransactionDate(DateTime.getDateMinusDays(WebAdminActions.loginActions().getSystemDate(), 3));

        // Perform deposit transaction
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().createTransaction(depositSavingsTransaction);
        Pages.tellerPage().setEffectiveDate(depositSavingsTransaction.getTransactionDate());
        Actions.transactionActions().clickCommitButton();
        Pages.tellerPage().closeModal();

        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set transaction with amount value
        Actions.clientPageActions().searchAndOpenClientByName(savingsAccount.getAccountNumber());
        expectedSavingsBalanceData = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();
        savingsAccTransactionData = new TransactionData(DateTime.getLocalDateOfPattern("MM/dd/yyyy"), DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                "-", expectedSavingsBalanceData.getCurrentBalance(), savingsTransactionAmount);
        accruedInterest = Pages.accountDetailsPage().getAccruedInterest();
        Actions.loginActions().doLogOut();

        // Set up instruction
        InstructionConstructor instructionConstructor = new InstructionConstructor(new ActivityHoldInstructionBuilder());
        activityHoldInstruction = instructionConstructor.constructInstruction(ActivityHoldInstruction.class);

        //Set instruction
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(savingsAccount);
        Pages.accountNavigationPage().clickInstructionsTab();
        AccountActions.createInstruction().createActivityHoldInstruction(activityHoldInstruction);
        Actions.loginActions().doLogOutWithSubmit();
    }

    @Test(description = "C22690, Accounts Details block - Savings Account")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyNSFTransaction() {
        logInfo("Step 1: Log in to the system as the user from the preconditions");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to Teller page and log in to the proof date");
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();

        logInfo("Step 3: Select any fund type related to regular account (e.g. Misc Debit)");
        Pages.tellerPage().clickMiscDebitButton();

        logInfo("Step 4: Search for account by its full account number");
        Actions.transactionActions().inputAccountNumber(savingsAccount.getAccountNumber());

        logInfo("Step 5: Look at the information displayed for CHK account in \"Account Quick View\" section");
        String pifNumber = Actions.transactionActions().getPIFNumber();
        Assert.assertEquals(pifNumber, client.getIndividualType().getClientID(), "Client ID doesn't match");
        String accountType = Actions.transactionActions().getAccountType();
        Assert.assertEquals(accountType, savingsAccount.getProductType(), "Account Type doesn't match");
        double currentBalance = Actions.transactionActions().getCurrentBalance();
        Assert.assertEquals(currentBalance, expectedSavingsBalanceData.getCurrentBalance(),
                "Current balance not correct");
        double accruedInterest = Actions.transactionActions().getAccruedInterest();
        Assert.assertEquals(accruedInterest, Double.parseDouble(this.accruedInterest),
                "Accrued interest doesn't match");
        double availableBalance = Actions.transactionActions().getAvailableBalance();
        Assert.assertEquals(availableBalance, expectedSavingsBalanceData.getAvailableBalance(),
                "Available balance not correct.");
        String automaticOverdraftLimit = Actions.transactionActions().getFirstAutomaticOverdraftLimit();
        Assert.assertEquals(automaticOverdraftLimit, "", "Overdraft limit doesn't match");
        String dateOpened = Actions.transactionActions().getDateOpened();
        String date = DateTime.getDateWithFormat(dateOpened, "MMMMMMM dd,yyyy", "MM/dd/yyyy");
        Assert.assertEquals(date, savingsAccount.getDateOpened(), "Date opened doesn't match");

        logInfo("Step 5: Click [Details] button");
        Pages.tellerPage().clickDetailsButton();
        SelenideTools.switchToLastTab();
        Pages.accountDetailsPage().waitForFullProfileButton();
    }
}