package com.nymbus.frontoffice.clientsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.clients.documents.DocumentActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.Functions;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.client.other.document.CompanyID;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.client.other.DocumentFactory;
import com.nymbus.pages.Pages;
import com.nymbus.util.Random;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Box Accounts Management")
@Owner("Petro")
public class C22557_ViewEditDeleteRestoreClientLevelDocumentationTest extends BaseTest {

    private IndividualClient client;
    private CompanyID companyIDDocument;
    private String clientID;

    @BeforeMethod
    public void preCondition() {
        // Set up Client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        // Set up the document
        companyIDDocument = new DocumentFactory().getCompanyIDDocument();

        // Create the client
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        clientID = Pages.clientDetailsPage().getClientID();

        // Create company id document and logout
        DocumentActions.createDocumentActions().createNewCompanyIDDocument(companyIDDocument);
        Actions.loginActions().doLogOut();
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "C22557, View / edit / delete/ restore Client level document")
    public void addNewDocumentOnClientLevel() {
        logInfo("Step 1: Log in to the system as User from the preconditions");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        Pages.navigationPage().waitForUserMenuVisible();

        logInfo("Step 2: Go to Clients and search for the client from the precondition");
        Actions.clientPageActions().searchAndOpenIndividualClientByID(clientID);

        logInfo("Step 3: Open it on Documents tab and search for document from preconditions");
        Pages.accountNavigationPage().clickDocumentsTab();

        logInfo("Step 4: Click 3 dots menu and click [Edit] button");
        Pages.documentsPage().clickDocumentRowByDocumentIDSelector(companyIDDocument.getIdNumber());
        String imgSrcBeforeEdit = Pages.documentOverviewPage().getImageSrc();
        Pages.documentOverviewPage().clickCloseButton();
        Pages.documentsPage().clickEditButtonByDocumentID(companyIDDocument.getIdNumber());

        logInfo("Step 5: Make any changes in the populated fields, change the attached document and click [Save Changes] button");
        companyIDDocument.setIdNumber(Random.genString(10));
        Pages.addNewDocumentPage().replaceDocument(Functions.getFilePathByName("clientDocument3.png"));
        Pages.addNewDocumentPage().typeValueToIDNumberField(companyIDDocument.getIdNumber());
        Pages.addNewDocumentPage().clickSaveChangesButton();

        logInfo("Step 6: Click the document in the documents list");
        Pages.documentsPage().clickDocumentRowByDocumentIDSelector(companyIDDocument.getIdNumber());
        String imgSrcAfterEdit = Pages.documentOverviewPage().getImageSrc();
        // TODO: Clarify how to validate that doc image is changed
        Assert.assertNotEquals(imgSrcBeforeEdit, imgSrcAfterEdit, "Document image doesn't changed!");

        logInfo("Step 7: Close the preview and verify info displayed in documents list (to see if other changes are applied )");
        Pages.documentOverviewPage().clickCloseButton();
        Assert.assertTrue(Pages.documentsPage().isDocumentIdPresentInTheList(companyIDDocument.getIdNumber()));

        logInfo("Step 8: Check the document and click the appeared [Delete] button");
        Pages.documentsPage().clickCheckboxByDocumentIDSelector(companyIDDocument.getIdNumber());
        Pages.documentsPage().clickDeleteButton();

        logInfo("Step 9: Click the green tooltip");
        Pages.documentsPage().clickRestoreTooltip();
        Assert.assertTrue(Pages.documentsPage().isDocumentIdPresentInTheList(companyIDDocument.getIdNumber()));

        logInfo("Step 10: Open Clients profile on Maintenance -> Maintenance History page");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

        logInfo("Step 11: Look through the records on the Maintenance History page and verify that records about editing, deleting/ restoring the Document are present on the Maintenance History page");
        AccountActions.accountMaintenanceActions().expandAllRows();
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Drag and Drop Documents here") >= 3,
                "'Drag and Drop Documents here' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("ID Type") >= 2,
                "'ID Type' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("ID Number") >= 3,
                "'ID Number' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Issued by") >= 2,
                "'Issued by' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Country") >= 2,
                "'Country' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Issue Date") >= 2,
                "'Issued Date' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Expiration Date") >= 2,
                "'Expiration Date' row count is incorrect!");
    }
}