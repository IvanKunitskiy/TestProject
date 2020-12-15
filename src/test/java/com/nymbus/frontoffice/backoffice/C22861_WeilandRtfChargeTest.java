package com.nymbus.frontoffice.backoffice;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitMiscCreditBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Backoffice")
@Feature("Account Analysis")
@Owner("Petro")
public class C22861_WeilandRtfChargeTest extends BaseTest {

    private String chkAccountNumber;
    private String cdtTemplateName;

    @BeforeMethod
    public void preCondition() {

        SelenideTools.openUrl(Constants.WEB_ADMIN_URL);
        WebAdminActions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Assert.assertTrue(WebAdminActions.webAdminUsersActions().getAccAnalyzeWithRdcCodeAndAmountCount() > 0,
                "There are no records found with RDC charge code");
        Assert.assertEquals(WebAdminActions.webAdminUsersActions().getRemoteDepositReturnEFTDescription(),
                "Returned Deposit Item - MSB", "Description is not equal to 'Returned Deposit Item - MSB'");
        cdtTemplateName = WebAdminActions.webAdminUsersActions().getCdtTemplateNameWithMiscDebitCommittedFromChkOnGlAccount();
        WebAdminActions.loginActions().doLogout();

        // Set up Client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        // Set up CHK  account
        Account chkAccount = new Account().setCHKAccountData();
        chkAccountNumber = chkAccount.getAccountNumber();

        // Set up transactions
        Transaction transaction = new TransactionConstructor(new GLDebitMiscCreditBuilder()).constructTransaction();
        transaction.getTransactionDestination().setAccountNumber(chkAccountNumber);

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

        // Increase account balance
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Actions.transactionActions().performGLDebitMiscCreditTransaction(transaction);
        Actions.loginActions().doLogOutProgrammatically();

    }

    @Test(description = "C22815, Weiland: RDC charge")
    @Severity(SeverityLevel.CRITICAL)
    public void weilandRdcCharge() {

        logInfo("Step 1: Log in to the system as the user from the precondition");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to Cashier Defined Transactions page");
        Pages.aSideMenuPage().clickCashierDefinedTransactionsMenuItem();
        Actions.transactionActions().doLoginTeller();

        logInfo("Step 3: Select CDT Template from the preconditions");
        Actions.cashierDefinedActions().setTellerOperation(cdtTemplateName);

        logInfo("Step 4: Select CHK account from the preconditions in Source\n" +
                "and select any GL account in the Destination");
        logInfo("Step 5: Expand Source and Destination items and specify such fields:\n" +
                "- Amount = any\n" +
                "- Notes = \"Returned Deposit Item - MSB\" --- two spaces after '-' sign\n" +
                "and click [Commit Transaction]");
        performCashierTransactionFromTemplate(chkAccountNumber, "90.00");
        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 6: Search for CHK account that was used in transaction and open it on Account Analysis tab");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(chkAccountNumber);
        Pages.accountDetailsPage().clickCommercialAnalysisTab();
        Pages.accountCommercialAnalysisPage().waitForTableResults();

        logInfo("Step 7: Look through the records and verify that RTF record is written to Rate Analysis History");
        Assert.assertEquals(Pages.accountCommercialAnalysisPage().getDateFromTableByRowIndex(1),
                DateTime.getLocalDateOfPattern("MM/dd/yyyy"), "'Date' is not valid");
        Assert.assertEquals(Pages.accountCommercialAnalysisPage().getUserByRowIndex(1),
                Constants.FIRST_NAME + " " + Constants.LAST_NAME, "'User' is not valid");
        Assert.assertEquals(Pages.accountCommercialAnalysisPage().getChargeCodeByRowIndex(1), "RTF",
                "'Charge code' is not valid");
        Assert.assertEquals(Pages.accountCommercialAnalysisPage().getDescriptionByRowIndex(1),
                "Returned Transit Check Fee","'Description' is not valid");
        Assert.assertEquals(Pages.accountCommercialAnalysisPage().getRateTypeByRowIndex(1),
                "Per Item","'Rate Type' is not valid");
//        Assert.assertEquals(Pages.accountCommercialAnalysisPage().getVolumeFromTableByRowIndex(1),
//                Functions.getStringValueWithOnlyDigits(glDebitSource.getAmount()), "'Volume' is not valid");
        Assert.assertTrue(Pages.accountCommercialAnalysisPage().getExportedByRowIndex(1).equalsIgnoreCase("no"),
                "'Exported' is not valid");
    }

    private void performCashierTransactionFromTemplate(String accountNumber, String amount) {
        int index = 0;
        Actions.cashierDefinedActions().fillSourceAccountNumber(accountNumber, index);
        Actions.cashierDefinedActions().fillSourceAmount(amount, index);
        Actions.cashierDefinedActions().fillSourceDetails("Returned Deposit Item -  MSB", index);
        Actions.cashierDefinedActions().fillDestinationAmount(amount, index);
        Pages.cashierPage().clickCommitButton();
    }

}
