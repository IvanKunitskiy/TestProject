package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.models.client.Client;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitMiscCreditCHKAccBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.verifyingModels.AccountDates;
import com.nymbus.newmodels.transaction.verifyingModels.BalanceDataForCHKAcc;
import com.nymbus.newmodels.transaction.verifyingModels.TransactionData;
import com.nymbus.pages.Pages;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class C23999_PerformECForGLDebitMiscCreditCHKAccTest extends BaseTest {
    private Transaction transaction;
    private BalanceDataForCHKAcc balanceData;
    private TransactionData transactionData;
    private AccountDates accountDates;

    @BeforeMethod
    public void prepareTransactionData() {
        // Set up client, account and transaction
        Client client = new Client().setDefaultClientData();
        client.setClientStatus("Member");
        client.setClientType("Individual");
        Account chkAccount = new Account().setCHKAccountData();
        transaction = new TransactionConstructor(new GLDebitMiscCreditCHKAccBuilder()).constructTransaction();
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Create client
        ClientsActions.createClient().createClient(client);

        // Create account
        AccountActions.createAccount().createCHKAccountForTransactionPurpose(chkAccount);

        // Set up transaction with account number
        transaction.getTransactionDestination().setAccountNumber(chkAccount.getAccountNumber());

        AccountActions.editAccount().navigateToAccountDetails(transaction.getTransactionDestination().getAccountNumber(), false);

        // perform transaction
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().createGlDebitMiscCreditTransaction(transaction);
        Actions.transactionActions().clickCommitButton();
        Pages.tellerPage().closeModal();

        // Get balance info after transaction
        AccountActions.editAccount().navigateToAccountDetails(transaction.getTransactionDestination().getAccountNumber(), false);
        balanceData = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();
        accountDates = AccountActions.retrievingAccountData().getAccountDates();

        logInfo("Balance data after transaction: " + balanceData.toString());

        transactionData = new TransactionData(transaction.getTransactionDate(),
                transaction.getTransactionDate(),
                "-",
                balanceData.getCurrentBalance(),
                transaction.getTransactionDestination().getAmount());
    }

    @Test(description = "C23999,  Perform EC for GL Debit -> Misc Credit transaction (CHK Account)")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyErrorCorrectForGLDebitMiscCreditTransactionOnCHKAcc() {
        logInfo("Step 2: Go to Journal page and log in to the proof date");
        Actions.journalActions().goToJournalPage();

        logInfo("Step 3: Search for the transaction from the preconditions and click on it to open on Details");
        Actions.journalActions().clickLastTransaction();

        logInfo("Step 4: Click [Error Correct] button");
        Pages.journalDetailsPage().clickErrorCorrectButton();
        Pages.journalDetailsPage().waitForErrorCorrectButtonInvisibility();
        Assert.assertEquals(Actions.journalActions().getTransactionState(), "Void",
                "Transaction state doesn't changed");
        balanceData.reduceAmount(transaction.getTransactionDestination().getAmount());
        accountDates.reduceLastDepositAmount(transaction.getTransactionDestination().getAmount());
        accountDates.reduceNumberOfDeposits(1);
        logInfo("Balance data after error correct: " + balanceData.toString());
        logInfo("Account dates and deposit amount after error correct: " + accountDates.toString());

        logInfo("Step 5: Search for the account that was used in Misc Credit item and open it on Details");
        AccountActions.editAccount().navigateToAccountDetails(transaction.getTransactionDestination().getAccountNumber(), false);

        logInfo("Step 6: Verify such fields: current balance, available balance, Aggregate Balance Year to date, Total Contributions for Life of Account");
        BalanceDataForCHKAcc actualBalanceData = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();
        AccountDates actualAccountDates = AccountActions.retrievingAccountData().getAccountDates();
        Assert.assertEquals(actualBalanceData, balanceData, "Balance data doesn't match!");

        logInfo("Step 7: Verify such fields: Date Last Deposit, Date Last Activity/Contact");
        Assert.assertEquals(actualAccountDates.getLastDepositDate(), accountDates.getLastDepositDateBeforeTransaction(),
                "Last Deposit Date doesn't match!");
        Assert.assertEquals(actualAccountDates.getLastActivityDate(), accountDates.getLastActivityDateBeforeTransaction(),
                "Last Activity Date doesn't match!");

        logInfo("Step 8: Verify Number Of Deposits This Statement Cycle");
        Assert.assertEquals(actualAccountDates.getNumberOfDeposits(), accountDates.getNumberOfDeposits(),
                "Number Of Deposits This Statement Cycle doesn't match!");

        logInfo("Step 9: Verify Last Deposit Amount field");
        Assert.assertEquals(actualAccountDates.getLastDepositAmount(), accountDates.getLastDepositAmount(),
                "Last Deposit Amount doesn't match!");

        logInfo("Step 10: Open account on Transactions history and verify that transaction is written to transactions history page with EC status (last column)");
        transactionData.setBalance(balanceData.getCurrentBalance());
        AccountActions.retrievingAccountData().goToTransactionsTab();
        TransactionData actualTransactionData = AccountActions.retrievingAccountData().getTransactionData();
        Assert.assertEquals(actualTransactionData, transactionData, "Transaction data doesn't match!");

        // TODO add verification for webadmin
    }
}