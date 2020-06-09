package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.tansactions.MultipleTransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.CashInMiscCreditCHKAccBuilder;
import com.nymbus.newmodels.generation.tansactions.builder.MiscDebitCashDepositMultipleTransactionBuilder;
import com.nymbus.newmodels.transaction.MultipleTransaction;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.verifyingModels.BalanceData;
import com.nymbus.newmodels.transaction.verifyingModels.BalanceDataForCDAcc;
import com.nymbus.newmodels.transaction.verifyingModels.BalanceDataForCHKAcc;
import com.nymbus.newmodels.transaction.verifyingModels.TransactionData;
import com.nymbus.pages.Pages;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class C22639_PerformECForTransactionWithMultipleSourceAndDestinations extends BaseTest {
    private MultipleTransaction multipleTransaction;
    private Account chkAccount;
    private Account saveAccount;
    private Account cdAccount;
    private BalanceData savingAccBalanceData;
    private BalanceDataForCDAcc cdAccBalanceData;
    private BalanceDataForCHKAcc chkAccBalanceData;
    private TransactionData chkAccTransactionData;
    private TransactionData cdAccTransactionData;
    private TransactionData savingAccTransactionData;

    @BeforeMethod
    public void prepareTransactionData() {
        // Set up Client and Accounts
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        chkAccount = new Account().setCHKAccountData();
        saveAccount = new Account().setSavingsAccountData();
        cdAccount = new Account().setCDAccountData();

        // Set up  transactions
        Transaction transaction = new TransactionConstructor(new CashInMiscCreditCHKAccBuilder()).constructTransaction();
        multipleTransaction = new MultipleTransactionConstructor(new MiscDebitCashDepositMultipleTransactionBuilder()).constructTransaction();

        // Log in
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create CHK account
        AccountActions.createAccount().createCHKAccountForTransactionPurpose(chkAccount);
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());

        // Create CD account
        AccountActions.createAccount().createCDAccountForTransactionPurpose(cdAccount);
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());

        // Create Savings account
        AccountActions.createAccount().createSavingAccountForTransactionPurpose(saveAccount);
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());

        // Set up transaction with account number
        transaction.getTransactionDestination().setAccountNumber(chkAccount.getAccountNumber());

        // Set up multiple transaction with account numbers
        multipleTransaction.getSources().get(1).setAccountNumber(chkAccount.getAccountNumber());

        multipleTransaction.getDestinations().get(1).setAccountNumber(saveAccount.getAccountNumber());

        multipleTransaction.getDestinations().get(2).setAccountNumber(cdAccount.getAccountNumber());

        // Perform cash in transaction for CHK acc
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().createCashInMiscCreditTransaction(transaction);
        Actions.transactionActions().clickCommitButton();
        Pages.verifyConductorModalPage().clickVerifyButton();
        Pages.tellerPage().closeModal();

        // Perform multiple transaction
        Actions.transactionActions().createTransactionWithMultipleSources(multipleTransaction);
        Actions.transactionActions().clickCommitButton();
        Pages.verifyConductorModalPage().clickVerifyButton();
        Actions.transactionActions().waitForLoadSpinnerInvisibility();
        Assert.assertFalse(Pages.tellerPage().isNotificationsPresent(), "Error message is visible!");
        Pages.tellerPage().closeModal();

        // Set up expected balance data
        Actions.clientPageActions().searchAndOpenClientByName(chkAccount.getAccountNumber());
        chkAccBalanceData = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();
        Actions.clientPageActions().searchAndOpenClientByName(cdAccount.getAccountNumber());
        cdAccBalanceData = AccountActions.retrievingAccountData().getBalanceDataForCDAcc();
        Actions.clientPageActions().searchAndOpenClientByName(saveAccount.getAccountNumber());
        AccountActions.editAccount().goToInstructionsTab();
        String INSTRUCTION_REASON = "Reg CC";
        AccountActions.createInstruction().deleteInstructionByReasonText(INSTRUCTION_REASON);
        AccountActions.editAccount().goToDetailsTab();
        savingAccBalanceData = AccountActions.retrievingAccountData().getBalanceData();

        // Set up transaction data
        chkAccTransactionData = getExpectedTransactionData(multipleTransaction.getTransactionDate(), "+",
                chkAccBalanceData.getCurrentBalance(), multipleTransaction.getSources().get(1).getAmount());
        cdAccTransactionData = getExpectedTransactionData(multipleTransaction.getTransactionDate(), "+",
                cdAccBalanceData.getCurrentBalance(),multipleTransaction.getDestinations().get(2).getAmount());
        savingAccTransactionData = getExpectedTransactionData(multipleTransaction.getTransactionDate(), "-",
                savingAccBalanceData.getCurrentBalance(), multipleTransaction.getDestinations().get(1).getAmount());

        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22639, Perform EC for transaction with multiple sources and destinations")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyErrorCorrectForMultipleTransaction() {
        logInfo("Step 1: Log in to the system as the user from the preconditions");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Go to Journal page and log in to the proof date");
        Actions.journalActions().goToJournalPage();
        Actions.transactionActions().doLoginTeller();

        logInfo("Step 3: Search for the transaction from the preconditions and click on it to open on Details");
        Actions.journalActions().applyFilterByAccountNumber(multipleTransaction.getDestinations().get(1).getAccountNumber());
        Actions.journalActions().clickLastTransaction();

        logInfo("Step 4: Click [Error Correct] button");
        Pages.journalDetailsPage().clickErrorCorrectButton();
        Pages.journalDetailsPage().waitForErrorCorrectButtonInvisibility();
        Assert.assertEquals(Actions.journalActions().getTransactionState(), "Void",
                "Transaction state doesn't changed");
        chkAccBalanceData.addAmount(multipleTransaction.getSources().get(1).getAmount());
        chkAccTransactionData.setBalance(chkAccBalanceData.getCurrentBalance());
        cdAccBalanceData.reduceAmount(multipleTransaction.getDestinations().get(2).getAmount());
        cdAccTransactionData.setBalance(cdAccBalanceData.getCurrentBalance());
        savingAccBalanceData.subtractAmount(multipleTransaction.getDestinations().get(1).getAmount());
        savingAccTransactionData.setBalance(savingAccBalanceData.getCurrentBalance());

        logInfo("Step 5: Search for CHK account that was used in Source and verify its: \n" +
                "- current balance \n" +
                "- available balance");
        Actions.clientPageActions().searchAndOpenClientByName(chkAccount.getAccountNumber());
        BalanceDataForCHKAcc actualBalanceDataForCHKAcc = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();
        Assert.assertEquals(actualBalanceDataForCHKAcc, chkAccBalanceData, "CHKAccount balances is not correct!");

        logInfo("Step 6: Open account on Transactions history and verify that transaction is written on transactions history page");
        AccountActions.retrievingAccountData().goToTransactionsTab();
        TransactionData actualTransactionData = AccountActions.retrievingAccountData().getTransactionData();
        Assert.assertEquals(actualTransactionData, chkAccTransactionData, "Transaction data doesn't match!");
    }

    private TransactionData getExpectedTransactionData(String date, String symbol,  double currentBalance, double amount) {
       return new TransactionData(date, date, symbol, currentBalance, amount);
    }
}