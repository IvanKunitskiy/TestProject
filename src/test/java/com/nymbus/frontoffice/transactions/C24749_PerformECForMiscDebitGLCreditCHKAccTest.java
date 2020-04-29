package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitMiscCreditCHKAccBuilder;
import com.nymbus.newmodels.generation.tansactions.builder.MiscDebitGLCreditTransactionBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.verifyingModels.ExtendedBalanceDataForCHKAcc;
import com.nymbus.newmodels.transaction.verifyingModels.TransactionData;
import com.nymbus.pages.Pages;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class C24749_PerformECForMiscDebitGLCreditCHKAccTest extends BaseTest {
    private Transaction miscDebitGLCreditTransaction;
    private ExtendedBalanceDataForCHKAcc balanceDataForCHKAcc;
    private TransactionData transactionData;

    @BeforeMethod
    public void prepareTransactionData() {
        // Set up Client and Account
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();
        Account checkAccount = new Account().setCHKAccountData();
        miscDebitGLCreditTransaction = new TransactionConstructor(new MiscDebitGLCreditTransactionBuilder()).constructTransaction();
        Transaction glDebitMiscCreditTransaction = new TransactionConstructor(new GLDebitMiscCreditCHKAccBuilder()).constructTransaction();
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create account
        AccountActions.createAccount().createCHKAccountForTransactionPurpose(checkAccount);

        // Set up transaction with account number
        glDebitMiscCreditTransaction.getTransactionDestination().setAccountNumber(checkAccount.getAccountNumber());
        miscDebitGLCreditTransaction.getTransactionSource().setAccountNumber(checkAccount.getAccountNumber());

        // Perform transaction
        Actions.transactionActions().performGLDebitMiscCreditTransaction(glDebitMiscCreditTransaction);

        Actions.transactionActions().createMiscDebitGLCreditTransaction(miscDebitGLCreditTransaction);

        Actions.transactionActions().clickCommitButton();

        Pages.tellerPage().closeModal();

        Actions.clientPageActions().searchAndOpenClientByName(miscDebitGLCreditTransaction.getTransactionSource().getAccountNumber());

        balanceDataForCHKAcc = AccountActions.retrievingAccountData().getExtendedBalanceDataForCHKAcc();

        transactionData = new TransactionData(miscDebitGLCreditTransaction.getTransactionDate(),
                miscDebitGLCreditTransaction.getTransactionDate(),
                "+",
                balanceDataForCHKAcc.getCurrentBalance(),
                miscDebitGLCreditTransaction.getTransactionDestination().getAmount());

        Actions.loginActions().doLogOut();
    }

    @Test(description = "C24749, Perform EC for Misc Debit -> GL Credit transaction (CHK account)")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyErrorCorrectForMiscDebitGLCreditTransactionOnCHKAcc() {
        logInfo("Step 1: Log in to the system as the user from the preconditions");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        Actions.clientPageActions().searchAndOpenClientByName(miscDebitGLCreditTransaction.getTransactionSource().getAccountNumber());

        logInfo("Step 2: Go to Journal page and log in to the proof date");
        Actions.journalActions().goToJournalPage();
        Actions.transactionActions().doLoginTeller();

        logInfo("Step 3: Search for the transaction from the preconditions and click on it to open on Details");
        Actions.journalActions().clickLastTransaction();

        logInfo("Step 4: Click [Error Correct] button");
        Pages.journalDetailsPage().clickErrorCorrectButton();
        Pages.journalDetailsPage().waitForErrorCorrectButtonInvisibility();
        Assert.assertEquals(Actions.journalActions().getTransactionState(), "Void",
                "Transaction state doesn't changed");

        balanceDataForCHKAcc.addAmount(miscDebitGLCreditTransaction.getTransactionDestination().getAmount());
        logInfo("Balance data after error correct: " + balanceDataForCHKAcc.toString());

        logInfo("Step 5: Search for the account that was used in Misc Credit item and open it on Details");
        Actions.clientPageActions().searchAndOpenClientByName(miscDebitGLCreditTransaction.getTransactionSource().getAccountNumber());

        ExtendedBalanceDataForCHKAcc actualBalanceDate = AccountActions.retrievingAccountData().getExtendedBalanceDataForCHKAcc();

        logInfo("Step 6: Close Transaction Receipt popup and \n" +
                "Go to the account used in Misc Debit item. Verify such fields: \n" +
                "- current balance \n" +
                "- available balance \n" +
                "- Collected Balance \n" +
                "- Average Balance");
        Assert.assertEquals(actualBalanceDate, balanceDataForCHKAcc, "Balance data doesn't match!");

        logInfo("Step 10: Open account on Transactions history and verify that transaction is written to transactions history page with EC status (last column)");
        transactionData.setBalance(balanceDataForCHKAcc.getCurrentBalance());
        AccountActions.retrievingAccountData().goToTransactionsTab();
        TransactionData actualTransactionData = AccountActions.retrievingAccountData().getTransactionData();
        Assert.assertEquals(actualTransactionData, transactionData, "Transaction data doesn't match!");

        // TODO add verification for webadmin
    }
}