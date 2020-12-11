package com.nymbus.frontoffice.backoffice;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import com.nymbus.core.utils.Functions;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitMiscCreditBuilder;
import com.nymbus.newmodels.generation.tansactions.factory.DestinationFactory;
import com.nymbus.newmodels.generation.tansactions.factory.SourceFactory;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.TransactionDestination;
import com.nymbus.newmodels.transaction.TransactionSource;
import com.nymbus.newmodels.transaction.enums.TransactionCode;
import com.nymbus.pages.Pages;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Backoffice")
@Feature("Account Analysis")
@Owner("Petro")
public class C22815_WeilandRdcChargeTest extends BaseTest {

    private String chkAccountNumber;
    private TransactionDestination miscCreditDestination;
    private TransactionSource glDebitSource;
    private Transaction transaction_1;
    private Transaction transaction_2;

    @BeforeMethod
    public void preCondition() {
        SelenideTools.openUrl(Constants.WEB_ADMIN_URL);
        WebAdminActions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        int accAnalyzeWithRdcCodeAndAmountCount = WebAdminActions.webAdminUsersActions().getAccAnalyzeWithRdcCodeAndAmountCount();
        Assert.assertTrue(accAnalyzeWithRdcCodeAndAmountCount > 0, "There are no records found with RDC charge code");
        WebAdminActions.loginActions().doLogout();

        // Set up Client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        // Set up CHK  account
        Account chkAccount = new Account().setCHKAccountData();
        chkAccountNumber = chkAccount.getAccountNumber();

        glDebitSource = SourceFactory.getGlDebitSource();
        miscCreditDestination = DestinationFactory.getMiscCreditDestination();
        miscCreditDestination.setAccountNumber(chkAccountNumber);
        miscCreditDestination.setTransactionCode(TransactionCode.ATM_CREDIT_MEMO_103.getTransCode());
        miscCreditDestination.setAmount(100);

        // Set up transactions
        transaction_1 = new TransactionConstructor(new GLDebitMiscCreditBuilder()).constructTransaction();
        transaction_1.getTransactionSource().setAmount(90);
        transaction_1.getTransactionDestination().setAmount(90);
        transaction_1.getTransactionDestination().setAccountNumber(chkAccountNumber);
        transaction_1.getTransactionDestination().setTransactionCode(TransactionCode.ATM_DEPOSIT_109.getTransCode());

        transaction_2 = new TransactionConstructor(new GLDebitMiscCreditBuilder()).constructTransaction();
        transaction_2.getTransactionSource().setAmount(80);
        transaction_2.getTransactionDestination().setAmount(80);
        transaction_2.getTransactionDestination().setAccountNumber(chkAccountNumber);
        transaction_2.getTransactionDestination().setTransactionCode(TransactionCode.CREDIT_TRANSFER_101.getTransCode());

        // Log in
        SelenideTools.openUrl(Constants.URL);
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set product
        chkAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, "MSBC"));

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create CHK account
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        AccountActions.createAccount().createCHKAccount(chkAccount);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22815, Weiland: RDC charge")
    public void weilandRdcCharge() {
        logInfo("Step 1: Log in to the system as the user from the precondition");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to Teller page and log in to the Proof Date");
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();

        logInfo("Step 3: Select Gl/Debit in Source and Misc Credit in Destination");
        logInfo("Step 4: Search for CHK account from preconditions in Destination and select one of the following trancodes:\n" +
                "103,109, 110, 111");
        logInfo("Step 5: Fill in such fields:\n" +
                "- Amount Field in Source and in Destination\n" +
                "- Notes field in the Source\n" +
                "and click [Commit Transaction] button");
        Actions.transactionActions().setGlDebitSource(glDebitSource, 0);
        Actions.transactionActions().setMiscCreditDestination(miscCreditDestination, 0);
        Actions.transactionActions().clickCommitButton();
        Pages.tellerPage().closeModal();
        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 6: Search for CHK account that was used in transaction and open it on Account Analysis tab");
        logInfo("Step 7: Look through the records and verify that RDC record is written to Rate Analysis History");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(chkAccountNumber);
        Pages.accountDetailsPage().clickCommercialAnalysisTab();
        Pages.accountCommercialAnalysisPage().waitForTableResults();
        int rowIndex = 1;
        Assert.assertEquals(Pages.accountCommercialAnalysisPage().getDateFromTableByRowIndex(rowIndex),
                DateTime.getLocalDateOfPattern("MM/dd/yyyy"), "'Date' is not valid");
        Assert.assertEquals(Pages.accountCommercialAnalysisPage().getUserByRowIndex(rowIndex),
                Constants.FIRST_NAME + " " + Constants.LAST_NAME, "'User' is not valid");
        Assert.assertEquals(Pages.accountCommercialAnalysisPage().getChargeCodeByRowIndex(rowIndex), "RDC",
                "'Charge code' is not valid");
        Assert.assertEquals(Pages.accountCommercialAnalysisPage().getDescriptionByRowIndex(rowIndex),
                "Total Deposits ($)","'Description' is not valid");
        Assert.assertEquals(Pages.accountCommercialAnalysisPage().getRateTypeByRowIndex(rowIndex),
                "Per Item","'Rate Type' is not valid");
        Assert.assertEquals(Pages.accountCommercialAnalysisPage().getVolumeFromTableByRowIndex(rowIndex),
                Functions.getStringValueWithOnlyDigits(glDebitSource.getAmount()), "'Volume' is not valid");
        Assert.assertTrue(Pages.accountCommercialAnalysisPage().getExportedByRowIndex(rowIndex).equalsIgnoreCase("no"),
                "'Exported' is not valid");

        logInfo("Step 8: Go to Teller page again and commit one more transaction with CHK account from the precondition (e.g. select 109 trancode)");
        Actions.transactionActions().performGLDebitMiscCreditTransaction(transaction_1);
        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 9: Open CHK account on Account Analysis tab again and verify that 2-nd RDC record appeared on the page");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(chkAccountNumber);
        Pages.accountDetailsPage().clickCommercialAnalysisTab();
        Pages.accountCommercialAnalysisPage().waitForTableResults();
        Assert.assertEquals(Pages.accountCommercialAnalysisPage().getRecordsCount(), 2,
                "2nd record is not added for account");

        logInfo("Step 10: In another tab go to Journal and search for Transaction from Step8, open it on Details");
        SelenideTools.openUrlInNewWindow(Constants.URL);
        SelenideTools.switchTo().window(1);
        Actions.journalActions().goToJournalPage();
        Actions.transactionActions().doLoginProofDate();
        Actions.journalActions().applyFilterByAccountNumber(chkAccountNumber);
        Actions.journalActions().clickLastTransaction();

        logInfo("Step 11: Click [Error Correct] button");
        Pages.journalDetailsPage().clickErrorCorrectButton();
        Pages.journalDetailsPage().waitForErrorCorrectButtonInvisibility();

        logInfo("Step 12: Refresh Account Analysis page for CHK account and" +
                "verify that 2-nd RDC record was deleted for Error Corrected transaction");
        SelenideTools.closeCurrentTab();
        SelenideTools.switchTo().window(0);
        SelenideTools.refresh();
        Assert.assertEquals(Pages.accountCommercialAnalysisPage().getRecordsCount(), 1,
                "2nd record is not removed from account");

        logInfo("Step 13: Go to Teller page and commit another Transaction with CHK account from the preconditions\n" +
                "BUT select transaction code: 101 - Credit Transfer in Destination");
        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Actions.transactionActions().performGLDebitMiscCreditTransaction(transaction_2);

        logInfo("Step 14: Refresh Account Analysis page for CHK account and " +
                "verify that RDC record was NOT created after performing 101 - Credit Transfer transaction");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(chkAccountNumber);
        Pages.accountDetailsPage().clickCommercialAnalysisTab();
        Pages.accountCommercialAnalysisPage().waitForTableResults();
        Assert.assertEquals(Pages.accountCommercialAnalysisPage().getRecordsCount(), 1,
                "2nd record is not added for account");
    }
}
