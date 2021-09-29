package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.DateTime;
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
import com.nymbus.newmodels.transaction.enums.TransactionCode;
import com.nymbus.pages.Pages;
import com.nymbus.pages.webadmin.WebAdminPages;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Transactions")
@Owner("Petro")
public class C22669_JournalPerformEcForCdRedemptionBalanceIenpTest extends BaseTest {

    private Account cdAccount;
    private Transaction transaction_311;
    private double accruedInterest;
    private double transactionAmountWithInterest;

    @BeforeMethod
    public void preCondition() {

        // Set up Client
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        // Set up CD account
        // Accounts "Date opened" and transactions "Effective date" should be in the past in order to increase IENP value
        String systemDateMinusTenDays = DateTime.getDateMinusDays(WebAdminActions.loginActions().getSystemDate(), 10);
        cdAccount = new Account().setCdAccountData();
        cdAccount.setDateOpened(systemDateMinusTenDays);

        // Set up transaction for increasing the CD account balance
        transaction_311 = new TransactionConstructor(new GLDebitMiscCreditCHKAccBuilder()).constructTransaction();
        transaction_311.getTransactionDestination().setAccountNumber(cdAccount.getAccountNumber());
        transaction_311.getTransactionDestination().setTransactionCode(TransactionCode.NEW_CD_311.getTransCode());

        Transaction transaction_315 = new TransactionConstructor(new MiscDebitGLCreditTransactionBuilder()).constructTransaction();
        transaction_315.getTransactionSource().setAccountNumber(cdAccount.getAccountNumber());
        transaction_315.getTransactionSource().setTransactionCode(TransactionCode.REDEEMED_CD_315.getTransCode());
        transaction_315.getTransactionDestination().setTransactionCode(TransactionCode.GL_CREDIT_865.getTransCode());

        // Login to the system
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set the required CHK account data
        cdAccount.setBankBranch(Actions.usersActions().getBankBranch());
        cdAccount.setProduct(Actions.productsActions().getProduct(Products.CD_PRODUCTS, AccountType.CD, RateType.FIXED));

        // Create a client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        client.getIndividualType().setClientID(Pages.clientDetailsPage().getClientID());

        // Create CHK account
        AccountActions.createAccount().createCDAccount(cdAccount);

        // Perform transaction to assign the money to CHK account
        performGLDebitMiscCreditTransaction(transaction_311, systemDateMinusTenDays);

        // Get the IENP value from account overview
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(cdAccount.getAccountNumber());
        accruedInterest = Double.parseDouble(Pages.accountDetailsPage().getAccruedInterestWithPeriod());
        transactionAmountWithInterest = transaction_311.getTransactionDestination().getAmount() + accruedInterest;
        transaction_315.getTransactionSource().setAmount(transactionAmountWithInterest);
        transaction_315.getTransactionDestination().setAmount(transactionAmountWithInterest);
        Actions.loginActions().doLogOutProgrammatically();

        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        performMiscDebitGLCreditTransaction(transaction_315, DateTime.getLocalDateWithPattern("MM/dd/yyyy"));
        Actions.loginActions().doLogOutProgrammatically();
    }

    private final String TEST_RUN_NAME = "Transactions";

    @TestRailIssue(issueID = 22669, testRunName = TEST_RUN_NAME)
    @Test(description = "C22669, Journal: Perform EC for CD redemption (balance + IENP)")
    @Severity(SeverityLevel.CRITICAL)
    public void journalPerformEcForCdRedemptionBalanceIenp() {

        logInfo("Step 1: Log in to the system as the user from the preconditions");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to Journal page and log in to the proof date");
        Actions.transactionActions().loginTeller();
        Actions.journalActions().goToJournalPage();

        logInfo("Step 3: Search for the transaction from preconditions and open its details");
        Actions.journalActions().applyFilterByAccountNumber(cdAccount.getAccountNumber());
        Actions.journalActions().clickLastTransaction();

        logInfo("Step 4: Click [Error Correct] button");
        Pages.journalDetailsPage().clickErrorCorrectButton();
        Pages.journalDetailsPage().waitForErrorCorrectButtonInvisibility();
        Assert.assertEquals(Actions.journalActions().getTransactionState(), "Void",
                "Transaction state hasn't changed");

        logInfo("Step 5: Open CD account that was used in the Transaction on Details and verify its:\n" +
                "- current balance\n" +
                "- available balance\n" +
                "- accrued interest\n" +
                "- account status\n" +
                "- account date closed");
        Actions.clientPageActions().searchAndOpenClientByName(cdAccount.getAccountNumber());
        Assert.assertEquals(transaction_311.getTransactionDestination().getAmount(),
                Double.parseDouble(Pages.accountDetailsPage().getCurrentBalance()),
                "Current balance not rolled back from $0.00 to previous value before closure");
        Assert.assertEquals(transaction_311.getTransactionDestination().getAmount(),
                Double.parseDouble(Pages.accountDetailsPage().getAvailableBalanceFromHeaderMenu()),
                "Available balance not rolled back from $0.00 to previous value before closure");
        Assert.assertEquals(accruedInterest, Double.parseDouble(Pages.accountDetailsPage().getAccruedInterest()),
                "'Accrued interest' is not valid");
        Assert.assertEquals("Active", Pages.accountDetailsPage().getAccountStatus(),
                "Account 'Status' is not 'Active'");
        Assert.assertTrue(Pages.accountDetailsPage().getDateClosed().isEmpty(),
                "'Date closed' is not empty");

        logInfo("Step 6: Open account on the Transactions tab and verify the Error corrected transaction");
        Pages.accountDetailsPage().clickTransactionsTab();
        String fullAmountValue_315 = Pages.accountTransactionPage().getFullAmountValue(1, "315");
        Assert.assertEquals(fullAmountValue_315.substring(0, 1), "+",
                "'315 - Redeemed CD' amount symbol is incorrect!");
        Assert.assertEquals(Double.parseDouble(fullAmountValue_315.substring(1)), transactionAmountWithInterest,
                "'315 - Redeemed CD' amount value is incorrect!");
        String fullAmountValue_307 = Pages.accountTransactionPage().getFullAmountValue(1, "307");
        Assert.assertEquals(fullAmountValue_307.substring(0, 1), "-",
                "'307 - Int Paid Comp' amount symbol is incorrect!");
        Assert.assertEquals(Double.parseDouble(fullAmountValue_307.substring(1)), accruedInterest,
                "'307 - Int Paid Comp' amount value is incorrect!");

        logInfo("Step 7: Log in to the WebAdmin, go to RulesUI and search for the error corrected transaction items using its bank.data.transaction.header rootid value");
        WebAdminActions.loginActions().openWebAdminPageInNewWindow();
        WebAdminActions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        WebAdminActions.webAdminTransactionActions().goToTransactionUrl(cdAccount.getAccountNumber());
        String transactionHeader = WebAdminPages.rulesUIQueryAnalyzerPage().getTransactionHeaderIdValue(2);

        logInfo("Step 8: Check gltransactionitempostingstatus value Debit Item with 315 - Redeemed CD trancode");
        String tranCodeDescriptionValue = TransactionCode.REDEEMED_CD_315.getTransCode().replaceAll("315\\s+-\\s", "");
        String glTransactionItemPostingStatusValue =
                WebAdminPages.rulesUIQueryAnalyzerPage().getGLTransactionItemPostingStatusByTranCode(tranCodeDescriptionValue);
        Assert.assertEquals("Void", glTransactionItemPostingStatusValue, "'gltransactionitempostingstatus' is not 'Void'");

        logInfo("Step 9: Go to bank.data.gl.interface and search for the record using deletedIncluded: true\n" +
                "Make sure that transaction item record was deleted on bank.data.gl.interface for the error-corrected transaction");
        WebAdminActions.webAdminTransactionActions().goToGLInterfaceWithDeletedItems(transactionHeader);
        Assert.assertFalse(WebAdminPages.rulesUIQueryAnalyzerPage().getDeletedBy(1).isEmpty(),
                "'Deleted by' record is empty");
        Assert.assertFalse(WebAdminPages.rulesUIQueryAnalyzerPage().getDeletedWhenValue(1).isEmpty(),
                "'Deleted when' record is empty");
        Assert.assertEquals(WebAdminPages.rulesUIQueryAnalyzerPage().getDeletedStatusByIndex(1), "deleted",
                "Transaction status is not 'deleted'");
        Assert.assertFalse(WebAdminPages.rulesUIQueryAnalyzerPage().getDeletedBy(2).isEmpty(),
                "'Deleted by' record is empty");
        Assert.assertFalse(WebAdminPages.rulesUIQueryAnalyzerPage().getDeletedWhenValue(2).isEmpty(),
                "'Deleted when' record is empty");
        Assert.assertEquals(WebAdminPages.rulesUIQueryAnalyzerPage().getDeletedStatusByIndex(2), "deleted",
                "Transaction status is not 'deleted'");
    }

    private void performGLDebitMiscCreditTransaction(Transaction transaction, String date) {
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().createGlDebitMiscCreditTransaction(transaction);
        Pages.tellerPage().setEffectiveDate(date);
        Actions.transactionActions().clickCommitButton();
        Assert.assertFalse(Pages.tellerPage().isNotificationsPresent(), "Error message is visible!");
        Pages.tellerPage().closeModal();
    }

    private void performMiscDebitGLCreditTransaction(Transaction transaction, String date) {
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().createMiscDebitGLCreditTransaction(transaction);
        Pages.tellerPage().setEffectiveDate(date);
        Actions.transactionActions().clickCommitButton();
        Assert.assertFalse(Pages.tellerPage().isNotificationsPresent(), "Error message is visible!");
        Pages.tellerPage().closeModal();
    }
}
