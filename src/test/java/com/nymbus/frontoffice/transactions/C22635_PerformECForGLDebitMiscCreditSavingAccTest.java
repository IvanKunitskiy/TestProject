package com.nymbus.frontoffice.transactions;

import com.codeborne.selenide.Selenide;
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
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitMiscCreditBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.verifyingModels.AccountDates;
import com.nymbus.newmodels.transaction.verifyingModels.BalanceData;
import com.nymbus.newmodels.transaction.verifyingModels.TransactionData;
import com.nymbus.pages.Pages;
import com.nymbus.pages.webadmin.WebAdminPages;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class C22635_PerformECForGLDebitMiscCreditSavingAccTest extends BaseTest {
    private Transaction transaction;
    private BalanceData balanceData;
    private TransactionData transactionData;
    private AccountDates accountDates;

    @BeforeMethod
    public void prepareTransactionData() {
        // Set up client, account and transaction
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();
        Account savingsAccount = new Account().setSavingsAccountData();
        transaction = new TransactionConstructor(new GLDebitMiscCreditBuilder()).constructTransaction();
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create account
        AccountActions.createAccount().createSavingsAccount(savingsAccount);

        // Set up transaction with account number
        transaction.getTransactionDestination().setAccountNumber(savingsAccount.getAccountNumber());

        // perform transaction
        Actions.transactionActions().openProofDateLoginModalWindow();
        String postingDate = Pages.tellerModalPage().getProofDateValue();
        Actions.transactionActions().doLoginProofDate();
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().createGlDebitMiscCreditTransaction(transaction);
        String effectiveDate = Pages.tellerPage().getEffectiveDate();
        Actions.transactionActions().clickCommitButton();
        Pages.tellerPage().closeModal();

        // remove REG CC rule instruction
        Actions.clientPageActions().searchAndOpenClientByName(transaction.getTransactionDestination().getAccountNumber());
        AccountActions.editAccount().goToInstructionsTab();
        String INSTRUCTION_REASON = "Reg CC";
        AccountActions.createInstruction().deleteInstructionByReasonText(INSTRUCTION_REASON);

        // Get balance info after transaction
        AccountActions.editAccount().navigateToAccountDetails(transaction.getTransactionDestination().getAccountNumber(), false);
        balanceData = AccountActions.retrievingAccountData().getBalanceData();
        accountDates = AccountActions.retrievingAccountData().getAccountDates();

        logInfo("Balance data after transaction: " + balanceData.toString());

        transactionData = new TransactionData(postingDate,
                effectiveDate,
                "-",
                balanceData.getCurrentBalance(),
                transaction.getTransactionDestination().getAmount());

        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22635, Perform EC for GL Debit -> Misc Credit transaction (Savings Account)")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyErrorCorrectForGLDebitMiscCreditTransactionOnSavingAcc() {
        logInfo("Step 1: Log in to the system as the user from the preconditions");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        Actions.transactionActions().loginTeller();

        logInfo("Step 2: Go to Journal page and log in to the proof date");
        Actions.journalActions().goToJournalPage();
        Actions.journalActions().applyFilterByAccountNumber(transaction.getTransactionDestination().getAccountNumber());
        Actions.journalActions().clickLastTransaction();

        logInfo("Step 3: Search for the transaction from the preconditions and click on it to open on Details");
        Actions.journalActions().clickLastTransaction();

        logInfo("Step 4: Click [Error Correct] button");
        Pages.journalDetailsPage().clickErrorCorrectButton();
        Pages.journalDetailsPage().waitForErrorCorrectButtonInvisibility();
        Assert.assertEquals(Actions.journalActions().getTransactionState(), "Void",
                "Transaction state doesn't changed");
        balanceData.subtractAmount(transaction.getTransactionDestination().getAmount());
        accountDates.reduceLastDepositAmount(transaction.getTransactionDestination().getAmount());
        accountDates.reduceNumberOfDeposits(1);
        logInfo("Balance data after error correct: " + balanceData.toString());
        logInfo("Account dates and deposit amount after error correct: " + accountDates.toString());

        logInfo("Step 5: Search for the account that was used in Misc Credit item and open it on Details");
        AccountActions.editAccount().navigateToAccountDetails(transaction.getTransactionDestination().getAccountNumber(), false);

        logInfo("Step 6: Verify such fields: current balance, available balance, Aggregate Balance Year to date, Total Contributions for Life of Account");
        BalanceData actualBalanceData = AccountActions.retrievingAccountData().getBalanceData();
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
        Actions.loginActions().doLogOut();

        logInfo("Step 11: Log in to the WebAdmin, go to RulesUI and search for the error corrected transaction items");
        Selenide.open(Constants.WEB_ADMIN_URL);
        WebAdminActions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        WebAdminActions.webAdminTransactionActions().goToTransactionUrl(transaction.getTransactionDestination().getAccountNumber());
        Assert.assertTrue(WebAdminPages.rulesUIQueryAnalyzerPage().getNumberOfSearchResult() > 0,
                "Transaction items doesn't find !");

        logInfo("Step 12: Check gltransactionitempostingstatus value for Deposit (Misc Credit) item");
        Assert.assertEquals(WebAdminPages.rulesUIQueryAnalyzerPage().getGLTransactionItemPostingStatusValue(1), "Void",
                "Posted status doesn't match!");
        String transactionHeader = WebAdminPages.rulesUIQueryAnalyzerPage().getTransactionHeaderIdValue(1);

        logInfo("Step 13: Go to bank.data.gl.interface and search for the record using deletedIncluded: true");
        WebAdminActions.webAdminTransactionActions().goToGLInterfaceWithDeletedItems(transactionHeader);
        Assert.assertTrue(WebAdminPages.rulesUIQueryAnalyzerPage().getNumberOfSearchResultInterfaceTable() > 0,
                "Transaction items doesn't find!");
        Assert.assertNotEquals(WebAdminPages.rulesUIQueryAnalyzerPage().getDeletedWhenValue(1), "",
                "Deleted When field is blank!");
        Assert.assertNotEquals(WebAdminPages.rulesUIQueryAnalyzerPage().getDeletedBy(1), "",
                "Deleted By field is blank!");
    }
}