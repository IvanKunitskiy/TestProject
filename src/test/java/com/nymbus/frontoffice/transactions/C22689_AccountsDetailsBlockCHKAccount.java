package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.transfers.TransfersActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.client.other.transfer.InsufficientFundsTransfer;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitDepositCHKAccBuilder;
import com.nymbus.newmodels.generation.tansactions.builder.WithdrawalGLDebitCHKAccBuilder;
import com.nymbus.newmodels.generation.transfers.TransferBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.verifyingModels.BalanceDataForCHKAcc;
import com.nymbus.newmodels.transaction.verifyingModels.TransactionData;
import com.nymbus.pages.Pages;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Transactions")
@Owner("Dmytro")
public class C22689_AccountsDetailsBlockCHKAccount extends BaseTest {
    private Transaction transaction;
    private BalanceDataForCHKAcc expectedBalanceData;
    private BalanceDataForCHKAcc expectedSavingsBalanceData;
    private TransactionData chkAccTransactionData;
    private TransactionData savingsAccTransactionData;
    private Account checkAccount;
    private Account savingsAccount;
    private double transactionAmount = 150.00;
    private String overdraftLimit;
    private double overdraftLimitSum;
    private InsufficientFundsTransfer insufficientFundsTransfer;
    private double savingsTransactionAmount = 2000.00;
    private IndividualClient client;
    private String accruedInterest;

    @BeforeMethod
    public void prepareTransactionData() {
        // Set up Client and Account
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();
        checkAccount = new Account().setCHKAccountData();
        savingsAccount = new Account().setSavingsAccountData();
        Transaction depositTransaction = new TransactionConstructor(new GLDebitDepositCHKAccBuilder()).constructTransaction();
        Transaction depositSavingsTransaction = new TransactionConstructor(new GLDebitDepositCHKAccBuilder()).constructTransaction();
        transaction = new TransactionConstructor(new WithdrawalGLDebitCHKAccBuilder()).constructTransaction();

        // Log in
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set products
        checkAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));
        savingsAccount.setProduct(Actions.productsActions().getProduct(Products.SAVINGS_PRODUCTS, AccountType.REGULAR_SAVINGS, RateType.FIXED));

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        client.getIndividualType().setClientID(Pages.clientDetailsPage().getClientID());

        overdraftLimit = "$ 500.00";

        // Create account
        AccountActions.createAccount().createCHKAccountForTransactionPurpose(checkAccount);
        AccountActions.editAccount().editOverdraftStatusAndLimit(overdraftLimit);
        Pages.accountDetailsPage().clickAccountsLink();
        AccountActions.createAccount().createSavingsAccount(savingsAccount);

        // Set up transaction with account number
        depositTransaction.getTransactionDestination().setAccountNumber(checkAccount.getAccountNumber());
        transaction.getTransactionSource().setAccountNumber(checkAccount.getAccountNumber());
        transaction.getTransactionSource().setAmount(transactionAmount);
        transaction.getTransactionDestination().setAmount(transactionAmount);
        overdraftLimitSum = Double.parseDouble(overdraftLimit.substring(overdraftLimit.indexOf("$") + 1)) +
                depositTransaction.getTransactionSource().getAmount();
        depositSavingsTransaction.getTransactionDestination().setAccountNumber(savingsAccount.getAccountNumber());
        depositSavingsTransaction.getTransactionDestination().setTransactionCode("209 - Deposit");
        depositSavingsTransaction.getTransactionDestination().setAmount(savingsTransactionAmount);
        depositSavingsTransaction.getTransactionSource().setAmount(savingsTransactionAmount);

        // Perform deposit transaction
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().createTransaction(depositTransaction);
        Actions.transactionActions().clickCommitButton();
        Pages.tellerPage().closeModal();
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().createTransaction(depositSavingsTransaction);
        Actions.transactionActions().clickCommitButton();
        Pages.tellerPage().closeModal();

        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set transaction with amount value
        Actions.clientPageActions().searchAndOpenClientByName(checkAccount.getAccountNumber());
        expectedBalanceData = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();
        chkAccTransactionData = new TransactionData(DateTime.getLocalDateOfPattern("MM/dd/yyyy"), DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                "-", expectedBalanceData.getCurrentBalance(), transactionAmount);
        accruedInterest = Pages.accountDetailsPage().getAccruedInterestThisStatementCycle();

        Actions.clientPageActions().searchAndOpenClientByName(savingsAccount.getAccountNumber());
        expectedSavingsBalanceData = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();
        savingsAccTransactionData = new TransactionData(DateTime.getLocalDateOfPattern("MM/dd/yyyy"), DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                "-", expectedSavingsBalanceData.getCurrentBalance(), savingsTransactionAmount);
        Actions.loginActions().doLogOut();

        // Set up transfer
        TransferBuilder transferBuilder = new TransferBuilder();
        insufficientFundsTransfer = transferBuilder.getInsufficientFundsTransfer();
        insufficientFundsTransfer.setFromAccount(savingsAccount);
        insufficientFundsTransfer.setToAccount(checkAccount);

        //Create transfer
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        Actions.clientPageActions().searchAndOpenIndividualClientByID(checkAccount.getAccountNumber());
        Pages.accountNavigationPage().clickTransfersTab();
        Pages.transfersPage().clickNewTransferButton();
        TransfersActions.addNewTransferActions().setInsufficientFundsTransferType(insufficientFundsTransfer);
        TransfersActions.addNewTransferActions().setInsufficientFundsFromAccount(insufficientFundsTransfer);
        TransfersActions.addNewTransferActions().setInsufficientFundsToAccount(insufficientFundsTransfer);
        Pages.newTransferPage().setNearestAmount(insufficientFundsTransfer.getNearestAmount());
        Pages.newTransferPage().setMaxAmount(insufficientFundsTransfer.getAmountToTransfer());
        //Pages.newTransferPage().setTransferCharge(insufficientFundsTransfer.getTransferCharge());
        Pages.newTransferPage().clickSaveButton();
        Pages.transfersPage().clickTransferInTheListByType(insufficientFundsTransfer.getTransferType().getTransferType());
        Pages.accountNavigationPage().clickAccountsTab();
        Pages.clientDetailsPage().openAccountByNumber(checkAccount.getAccountNumber());
        Actions.loginActions().doLogOut();
    }

    private final String TEST_RUN_NAME = "Transactions";

    @TestRailIssue(issueID = 22689, testRunName = TEST_RUN_NAME)
    @Test(description = "C22689, Accounts Details block - CHK Account")
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
        Actions.transactionActions().inputAccountNumber(checkAccount.getAccountNumber());

        logInfo("Step 5: Look at the information displayed for CHK account in \"Account Quick View\" section");
        String pifNumber = Actions.transactionActions().getPIFNumber();
        Assert.assertEquals(pifNumber, client.getIndividualType().getClientID(), "Client ID doesn't match");
        String accountType = Actions.transactionActions().getAccountType();
        Assert.assertEquals(accountType, checkAccount.getProductType(), "Account Type doesn't match");
        double currentBalance = Actions.transactionActions().getCurrentBalance();
        Assert.assertEquals(currentBalance, expectedBalanceData.getCurrentBalance(),
                "Current balance not correct");
        double accruedInterest = Actions.transactionActions().getAccruedInterest();
        Assert.assertEquals(accruedInterest, Double.parseDouble(this.accruedInterest),
                "Accrued interest doesn't match");
        double availableBalance = Actions.transactionActions().getAvailableBalance();
        Assert.assertEquals(availableBalance, expectedBalanceData.getAvailableBalance(),
                "Available balance not correct.");
        String automaticOverdraftLimit = Actions.transactionActions().getFirstAutomaticOverdraftLimit();
        Assert.assertEquals(automaticOverdraftLimit, overdraftLimit, "Overdraft limit doesn't match");
        String dateOpened = Actions.transactionActions().getDateOpened();
        String date = DateTime.getDateWithFormat(dateOpened, "MMMMMMM dd,yyyy", "MM/dd/yyyy");
        Assert.assertEquals(date, checkAccount.getDateOpened(), "Date opened doesn't match");

        logInfo("Step 5: Click [Details] button");
        Pages.tellerPage().clickDetailsButton();
        SelenideTools.switchToLastTab();
        Pages.accountDetailsPage().waitForFullProfileButton();
    }
}
