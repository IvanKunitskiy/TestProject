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
import com.nymbus.newmodels.accountinstructions.StopPaymentInstruction;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.accountinstructions.InstructionConstructor;
import com.nymbus.newmodels.generation.accountinstructions.builder.StopPaymentInstructionBuilder;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Backoffice")
@Feature("Account Analysis")
@Owner("Petro")
public class C22816_WeilandSpfChargeTest extends BaseTest {

    private String chkAccountNumber;
    private StopPaymentInstruction instruction;
    private String systemDate;

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
        systemDate = WebAdminActions.loginActions().getSystemDate();

        // Set up instruction
        instruction = new InstructionConstructor(new StopPaymentInstructionBuilder())
                .constructInstruction(StopPaymentInstruction.class);

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

    @Test(description = "C22816, Weiland SPF Charge")
    @Severity(SeverityLevel.CRITICAL)
    public void weilandSpfCharge() {

        logInfo("Step 1: Log in to the system as the user from the precondition");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Search for CHK account from the preconditions and open it on Instructions tab");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(chkAccountNumber);
        Pages.accountDetailsPage().clickInstructionsTab();

        logInfo("Step 3: Click [New Instruction] and select Instruction Type = Stop Payment");
        logInfo("Step 4: Fill in all required fields and click [Save] button");
        int instructionsCount = AccountActions.createInstruction().getInstructionCount();
        AccountActions.createInstruction().createStopPaymentInstruction(instruction);
        Pages.accountInstructionsPage().waitForCreatedInstruction(instructionsCount + 1);

        logInfo("Step 5: Open account on Account Analysis tab");
        Pages.accountDetailsPage().clickCommercialAnalysisTab();

        logInfo("Step 6: Look through the records and verify that SPF record is written to Rate Analysis History");
        Pages.accountCommercialAnalysisPage().setDateTo(systemDate);
        Pages.accountCommercialAnalysisPage().clickFilterButton();
        Pages.accountCommercialAnalysisPage().waitForTableResults();
        Assert.assertEquals(Pages.accountCommercialAnalysisPage().getRecordsCount(), 1,
                "Invalid records count at 'Rate Analysis History'");
        Assert.assertEquals(Pages.accountCommercialAnalysisPage().getDateFromTableByRowIndex(1),
                DateTime.getLocalDateOfPattern("MM/dd/yyyy"), "'Date' is not valid");
        Assert.assertEquals(Pages.accountCommercialAnalysisPage().getUserByRowIndex(1),
                Constants.FIRST_NAME + " " + Constants.LAST_NAME, "'User' is not valid");
        Assert.assertEquals(Pages.accountCommercialAnalysisPage().getChargeCodeByRowIndex(1), "SPF",
                "'Charge code' is not valid");
        Assert.assertEquals(Pages.accountCommercialAnalysisPage().getDescriptionByRowIndex(1),
                "Stop Payment Fee","'Description' is not valid");
        Assert.assertEquals(Pages.accountCommercialAnalysisPage().getRateTypeByRowIndex(1),
                "Per Item","'Rate Type' is not valid");
        Assert.assertEquals(Integer.parseInt(Pages.accountCommercialAnalysisPage().getVolumeFromTableByRowIndex(1)), 1,
                "'Volume' is not valid");
        Assert.assertTrue(Pages.accountCommercialAnalysisPage().getExportedByRowIndex(1).equalsIgnoreCase("no"),
                "'Exported' is not valid");

        logInfo("Step 7: Go to Instructions tab again and open Stop Payment instruction in Edit mode");
        Pages.accountDetailsPage().clickInstructionsTab();
        Pages.accountInstructionsPage().clickInstructionInListByIndex(1);
        Pages.accountInstructionsPage().clickEditButton();

        logInfo("Step 8: Make any changes (e.g. change Expiration Date) and click [Save] button");
        AccountActions.editInstructionActions().addTextToNotesField();
        Pages.accountInstructionsPage().clickSaveButton();
        Pages.accountInstructionsPage().waitForSaveButtonInvisibility();

        logInfo("Step 9: Go to Account Analysis tab again");
        Pages.accountDetailsPage().clickCommercialAnalysisTab();

        logInfo("Step 10 Look through the records and verify that new SPF record was NOT written to Rate Analysis History");
        Assert.assertEquals(Pages.accountCommercialAnalysisPage().getRecordsCount(), 1,
                "Invalid records count at 'Rate Analysis History'");
    }
}
