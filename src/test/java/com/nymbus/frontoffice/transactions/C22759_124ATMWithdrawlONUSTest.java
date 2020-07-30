package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.client.other.debitcard.DebitCard;
import com.nymbus.newmodels.generation.bincontrol.BinControlConstructor;
import com.nymbus.newmodels.generation.bincontrol.builder.BinControlBuilder;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.debitcard.DebitCardConstructor;
import com.nymbus.newmodels.generation.debitcard.builder.DebitCardBuilder;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitMiscCreditCHKAccBuilder;
import com.nymbus.newmodels.settings.bincontrol.BinControl;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.enums.TransactionCode;
import com.nymbus.newmodels.transaction.verifyingModels.BalanceDataForCHKAcc;
import com.nymbus.newmodels.transaction.verifyingModels.NonTellerTransactionData;
import com.nymbus.newmodels.transaction.verifyingModels.TransactionData;
import com.nymbus.pages.Pages;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class C22759_124ATMWithdrawlONUSTest extends BaseTest {
    private BalanceDataForCHKAcc expectedBalanceData;
    private NonTellerTransactionData nonTellerTransactionData;
    private TransactionData chkAccTransactionData;
    private String checkingAccountNumber;
    private double transactionAmount;
    private IndividualClient client;

    @BeforeMethod
    public void prepareTransactionData() {
        // Set up Client, Account and Transaction
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();
        Account checkAccount = new Account().setCHKAccountData();
        Transaction glDebitMiscCreditTransaction = new TransactionConstructor(new GLDebitMiscCreditCHKAccBuilder()).constructTransaction();

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
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        String terminalId = Actions.nonTellerTransactionActions().getTerminalID(1);

        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create CHK account
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        AccountActions.createAccount().createCHKAccountForTransactionPurpose(checkAccount);
        checkingAccountNumber = checkAccount.getAccountNumber();

        // Set up transaction with account number
        glDebitMiscCreditTransaction.getTransactionDestination().setAccountNumber(checkAccount.getAccountNumber());

        //Create debit card
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        Pages.clientDetailsPage().clickOnMaintenanceTab();
        Pages.clientDetailsPage().clickOnNewDebitCardButton();
        Actions.debitCardModalWindowActions().fillDebitCard(debitCard);
        String expirationDate = Actions.debitCardModalWindowActions().getExpirationDate();
        Pages.debitCardModalWindow().clickOnSaveAndFinishButton();
        Pages.debitCardModalWindow().waitForAddNewDebitCardModalWindowInvisibility();
        String debitCardNumber = Actions.debitCardModalWindowActions().getCardNumber(1);

        // Re-login in system for updating teller session
        Actions.loginActions().doLogOut();
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Perform transaction
        Actions.transactionActions().loginTeller();
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().createGlDebitMiscCreditTransaction(glDebitMiscCreditTransaction);
        Actions.transactionActions().clickCommitButton();
        Pages.tellerPage().closeModal();
        transactionAmount = glDebitMiscCreditTransaction.getTransactionDestination().getAmount();

        Actions.clientPageActions().searchAndOpenClientByName(checkAccount.getAccountNumber());
        expectedBalanceData = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();

        // Set up nonTeller transaction data
        nonTellerTransactionData = new NonTellerTransactionData();
        nonTellerTransactionData.setCardNumber(debitCardNumber);
        nonTellerTransactionData.setAmount(glDebitMiscCreditTransaction.getTransactionDestination().getAmount());
        nonTellerTransactionData.setExpirationDate(expirationDate);
        nonTellerTransactionData.setTerminalId(terminalId);
        chkAccTransactionData = new TransactionData(DateTime.getLocalDateOfPattern("MM/dd/yyyy"), DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                "-", expectedBalanceData.getCurrentBalance(),
                glDebitMiscCreditTransaction.getTransactionDestination().getAmount());

        Actions.loginActions().doLogOut();
    }

    @Test(description = " C22759, 124 ATM Withdrawal ONUS")
    @Severity(SeverityLevel.CRITICAL)
    public void verify124ATMWithdrawalONUSTransaction() {
        logInfo("Step 1: Go to the Swagger and log in as the User from the preconditions");
        logInfo("Step 2:Expand widgets-controller/widget._GenericProcess and run the following request with ONUS terminal ID");
        Actions.nonTellerTransactionActions().performATMWithdrawalONUSTransaction(nonTellerTransactionData);

        logInfo("Step 3: Log in to the system as the User from the preconditions");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 4: Search for CHK account from the precondition and Verify Account's: \n" +
                "- current balance \n" +
                "- available balance");
        Actions.clientPageActions().searchAndOpenClientByName(checkingAccountNumber);
        expectedBalanceData.reduceAmount(transactionAmount);
        BalanceDataForCHKAcc actualBalanceData = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();
        Assert.assertEquals(actualBalanceData, expectedBalanceData, "CHKAccount balances is not correct!");

        chkAccTransactionData.setBalance(expectedBalanceData.getCurrentBalance());
        AccountActions.retrievingAccountData().goToTransactionsTab();
        TransactionData actualTransactionData = AccountActions.retrievingAccountData().getTransactionData();
        Assert.assertEquals(actualTransactionData, chkAccTransactionData, "Transaction data doesn't match!");

        logInfo("Step 5: Verify that there is NO 129-ATM Usage Fee for 124 ATM Withdrawal ONUS transaction");
        Assert.assertEquals(Pages.accountTransactionPage().getTransactionItemsCount(), 2,
                            "Transaction count is incorrect!");
        Assert.assertFalse(Actions.transactionActions().isTransactionCodePresent(TransactionCode.ATM_USAGE_129.getTransCode()),
                            "129-ATM Usage Fee presents in transaction list");

        logInfo("Step 6: Go to Client Maintenance and click [View all Cards] button in 'Cards Management' widget");
        logInfo("Step 7: Click [View History] link on the Debit Card from the precondition");
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        Actions.debitCardModalWindowActions().goToCardHistory(1);
        double actualAmount = Actions.debitCardModalWindowActions().getTransactionAmount(1);
        Assert.assertEquals(actualAmount, chkAccTransactionData.getAmount(), "Transaction amount is incorrect!");
    }
}