package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.Generator;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.client.other.debitcard.DebitCard;
import com.nymbus.newmodels.generation.bincontrol.BinControlConstructor;
import com.nymbus.newmodels.generation.bincontrol.builder.BinControlBuilder;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.debitcard.DebitCardConstructor;
import com.nymbus.newmodels.generation.debitcard.builder.DebitCardBuilder;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.CashInMiscCreditCHKAccBuilder;
import com.nymbus.newmodels.settings.bincontrol.BinControl;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.verifyingModels.BalanceDataForCHKAcc;
import com.nymbus.newmodels.transaction.verifyingModels.NonTellerTransactionData;
import com.nymbus.newmodels.transaction.verifyingModels.TransactionData;
import com.nymbus.pages.Pages;
import com.nymbus.testrail.CustomStepResult;
import com.nymbus.testrail.TestRailAssert;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class C37370_VerifyAccountBalancesOnCommitingAnyTransactionAfterATMTransaction extends BaseTest {
    private BalanceDataForCHKAcc expectedBalanceData;
    private NonTellerTransactionData nonTellerTransactionData;
    private TransactionData chkAccTransactionData;
    private String checkingAccountNumber;
    private double transactionAmount;
    private IndividualClient client;
    private Transaction transaction;
    private double amount = 100.00;

    @BeforeMethod
    public void preCondition() {
        // Set up Client, Account
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();
        Account checkAccount = new Account().setCHKAccountData();
        transactionAmount = 100.00;

        // Set up debit card and bin control
        DebitCardConstructor debitCardConstructor = new DebitCardConstructor();
        DebitCardBuilder debitCardBuilder = new DebitCardBuilder();
        debitCardConstructor.constructDebitCard(debitCardBuilder);
        DebitCard debitCard = debitCardBuilder.getCard();

        BinControlConstructor binControlConstructor = new BinControlConstructor();
        BinControlBuilder binControlBuilder = new BinControlBuilder();
        binControlConstructor.constructBinControl(binControlBuilder);
        BinControl binControl = binControlBuilder.getBinControl();
        binControl.setBinNumber("510986");
        binControl.setCardDescription("Consumer Debit");

        Actions.debitCardModalWindowActions().setDebitCardWithBinControl(debitCard, binControl);
        debitCard.getAccounts().add(checkAccount.getAccountNumber());
        debitCard.setNameOnCard(client.getNameForDebitCard());

        // Log in and create client
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        String terminalId = Actions.nonTellerTransactionActions().getTerminalID(2);

        // Set product
        checkAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));

        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        logInFile("Create client - " + client.getFullName());

        // Create CHK account
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        AccountActions.createAccount().createCHKAccountForTransactionPurpose(checkAccount);
        checkingAccountNumber = checkAccount.getAccountNumber();
        logInFile("Create CHK account - " + checkingAccountNumber);

        //Create debit card
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        Pages.clientDetailsPage().clickOnMaintenanceTab();
        Pages.clientDetailsPage().clickOnNewDebitCardButton();
        Actions.debitCardModalWindowActions().fillDebitCard(debitCard);
        String expirationDate = Actions.debitCardModalWindowActions().getExpirationDate();
        Pages.debitCardModalWindow().clickOnSaveAndFinishButton();
        Pages.debitCardModalWindow().waitForAddNewDebitCardModalWindowInvisibility();
        String debitCardNumber = Actions.debitCardModalWindowActions().getCardNumber(1);
        Pages.cardsManagementPage().clickEditButton(1);
        expirationDate = Actions.debitCardModalWindowActions().getExpirationDate();
        Pages.debitCardModalWindow().clickOnCancelButton();
        logInFile("Create debit card - " + debitCardNumber);

        nonTellerTransactionData = new NonTellerTransactionData();
        nonTellerTransactionData.setCardNumber(debitCardNumber);
        nonTellerTransactionData.setAmount(transactionAmount);
        nonTellerTransactionData.setExpirationDate(expirationDate);
        nonTellerTransactionData.setTerminalId(terminalId);

        Actions.loginActions().doLogOut();

        //Perform ATM transactions
        Actions.nonTellerTransactionActions().performATMTransaction(Generator.getFieldsMap(nonTellerTransactionData));

        //Create transactions
        transaction = new TransactionConstructor(new CashInMiscCreditCHKAccBuilder()).constructTransaction();
        transaction.getTransactionDestination().setAmount(amount);
        transaction.getTransactionDestination().setAccountNumber(checkingAccountNumber);
        transaction.getTransactionDestination().setTransactionCode("09 -");
        transaction.getTransactionSource().setAmount(amount);
    }

    private final String TEST_RUN_NAME = "Transactions";

    @TestRailIssue(issueID = 37370, testRunName = TEST_RUN_NAME)
    @Test(description = "C37370, Verify Account Balances on commiting any transaction after ATM transaction")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyAccountBalancesOnCommittingAnyTransactionAfterIntPayment() {
        logInfo("Step 1: Log in to the system as the user from the precondition");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to Teller page and log in to the Proof date");
        Actions.transactionActions().loginTeller();
        Actions.transactionActions().goToTellerPage();

        logInfo("Step 3: Commit any transaction with CHK/Savings/CD account from the precondition");
        Actions.transactionActions().createTransaction(transaction);
        Pages.tellerPage().clickCommitButton();
        Pages.verifyConductorModalPage().clickVerifyButton();
        SelenideTools.sleep(Constants.MINI_TIMEOUT);
        if (Pages.supervisorModalPage().isModalWindowVisible()) {
            Pages.supervisorModalPage().inputLogin(userCredentials.getUserName());
            Pages.supervisorModalPage().inputPassword(userCredentials.getPassword());
            Pages.supervisorModalPage().clickEnter();
        }
        Pages.tellerModalPage().clickCloseButton();

        logInfo("Step 4: Open account that was used in the transaction on Transactions tab and verify the committed transaction\n" +
                "Make sure that all balances are correct");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenClientByName(checkingAccountNumber);
        AccountActions.retrievingAccountData().goToTransactionsTab();
        double transactionAmount = AccountActions.retrievingAccountData().getAmountValue(1);
        TestRailAssert.assertTrue(transactionAmount == amount,
                new CustomStepResult("'Amount' is valid", String.format("'Amount' is not valid. Expected %s, found - %s",
                        amount, transactionAmount)));
        double balanceValue = AccountActions.retrievingAccountData().getBalanceValue(1);
        double actualCurrentBalance = transactionAmount + amount;
        TestRailAssert.assertTrue(actualCurrentBalance == balanceValue,
                new CustomStepResult("'Balance' is valid", String.format("'Balance' is not valid. Expected %s, found - %s",
                        actualCurrentBalance, balanceValue)));


    }


}
