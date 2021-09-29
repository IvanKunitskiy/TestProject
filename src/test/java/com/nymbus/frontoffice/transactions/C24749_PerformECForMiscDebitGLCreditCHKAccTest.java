package com.nymbus.frontoffice.transactions;

import com.codeborne.selenide.Selenide;
import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.transactions.TransactionConstructor;
import com.nymbus.newmodels.generation.transactions.builder.GLDebitMiscCreditCHKAccBuilder;
import com.nymbus.newmodels.generation.transactions.builder.MiscDebitGLCreditTransactionBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.verifyingModels.ExtendedBalanceDataForCHKAcc;
import com.nymbus.newmodels.transaction.verifyingModels.TransactionData;
import com.nymbus.pages.Pages;
import com.nymbus.pages.webadmin.WebAdminPages;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Transactions")
@Owner("Dmytro")
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
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set product
        checkAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));

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
        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // remove REG CC rule instruction
        Actions.clientPageActions().searchAndOpenClientByName(glDebitMiscCreditTransaction.getTransactionDestination().getAccountNumber());
        double averageBalance = AccountActions.retrievingAccountData().getAverageBalance();
        AccountActions.editAccount().goToInstructionsTab();
        String INSTRUCTION_REASON = "Reg CC";
        AccountActions.createInstruction().deleteInstructionByReasonText(INSTRUCTION_REASON);

        // Logout and login for update teller session
        Actions.loginActions().doLogOut();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // perform misc debit GL credit transaction
        // perform transaction
        Actions.transactionActions().goToTellerPage();
        String postingDate = Pages.tellerModalPage().getProofDateValue();
        Actions.transactionActions().doLoginProofDate();
        Actions.transactionActions().createMiscDebitGLCreditTransaction(miscDebitGLCreditTransaction);
        String effectiveDate = Pages.tellerPage().getEffectiveDate();
        Actions.transactionActions().clickCommitButton();
        Pages.tellerPage().closeModal();

        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        Actions.clientPageActions().searchAndOpenClientByName(miscDebitGLCreditTransaction.getTransactionSource().getAccountNumber());

        balanceDataForCHKAcc = AccountActions.retrievingAccountData().getExtendedBalanceDataForCHKAcc();
        balanceDataForCHKAcc.setAverageBalance(averageBalance);

        transactionData = new TransactionData(postingDate,
                effectiveDate,
                "+",
                balanceDataForCHKAcc.getCurrentBalance(),
                miscDebitGLCreditTransaction.getTransactionDestination().getAmount());

        Actions.loginActions().doLogOut();
    }

    private final String TEST_RUN_NAME = "Transactions";

    @TestRailIssue(issueID = 24749, testRunName = TEST_RUN_NAME)
    @Test(description = "C24749, Perform EC for Misc Debit -> GL Credit transaction (CHK account)")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyErrorCorrectForMiscDebitGLCreditTransactionOnCHKAcc() {
        logInfo("Step 1: Log in to the system as the user from the preconditions");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to Journal page and log in to the proof date");
        Actions.journalActions().goToJournalPage();
        Actions.transactionActions().doLoginProofDate();

        logInfo("Step 3: Search for the transaction from the preconditions and click on it to open on Details");
        Actions.journalActions().applyFilterByAccountNumber(miscDebitGLCreditTransaction.getTransactionSource().getAccountNumber());
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

        logInfo("Step 11: Log in to the WebAdmin, go to RulesUI and search for the error corrected transaction items");
        Selenide.open(Constants.WEB_ADMIN_URL);
        WebAdminActions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        WebAdminActions.webAdminTransactionActions().goToTransactionUrl(miscDebitGLCreditTransaction.getTransactionSource().getAccountNumber());
        Assert.assertTrue(WebAdminPages.rulesUIQueryAnalyzerPage().getNumberOfSearchResult() > 0,
                "Transaction items doesn't find !");

        logInfo("Step 12: Check gltransactionitempostingstatus value for Deposit (Misc Credit) item");
        Assert.assertEquals(WebAdminPages.rulesUIQueryAnalyzerPage().getGLTransactionItemPostingStatusValue(2), "Void",
                "Posted status doesn't match!");
        String transactionHeader = WebAdminPages.rulesUIQueryAnalyzerPage().getTransactionHeaderIdValue(2);

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