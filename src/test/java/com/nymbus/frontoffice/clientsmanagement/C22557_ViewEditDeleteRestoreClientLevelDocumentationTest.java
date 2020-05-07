package com.nymbus.frontoffice.clientsmanagement;

import com.codeborne.selenide.Selenide;
import com.nymbus.actions.Actions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.clients.documents.DocumentActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.Functions;
import com.nymbus.models.client.Client;
import com.nymbus.newmodels.client.other.document.CompanyID;
import com.nymbus.newmodels.generation.client.other.DocumentFactory;
import com.nymbus.pages.Pages;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class C22557_ViewEditDeleteRestoreClientLevelDocumentationTest extends BaseTest {

    Client client;
    CompanyID companyIDDocument;

    @BeforeMethod
    public void preCondition() {
        // Set up the client
        client = new Client().setDefaultClientData();
        client.setClientStatus("Member");
        client.setClientType("Individual");

        // Set up the document
        companyIDDocument = new DocumentFactory().getCompanyIDDocument();

        // Create the client
        Selenide.open(Constants.URL);
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.createClient().createClient(client);
        client.setClientID(Pages.clientDetailsPage().getClientID());
        DocumentActions.createDocumentActions().createNewCompanyIDDocument(companyIDDocument);
        Actions.loginActions().doLogOut();
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "C22557, View / edit / delete/ restore Client level document")
    public void addNewDocumentOnClientLevel() {

        logInfo("Step 1: Log in to the system as User from the preconditions");
        Selenide.open(Constants.URL);
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        Pages.navigationPage().waitForUserMenuVisible();

        logInfo("Step 2: Go to Clients and search for the client from the precondition");
        Actions.clientPageActions().searchAndOpenClientByID(client);

        logInfo("Step 3: Open it on Documents tab and search for document from preconditions");
        Pages.accountNavigationPage().clickDocumentsTab();

        logInfo("Step 4: Click 3 dots menu and click [Edit] button");
        Pages.documentsPage().clickEditButtonByDocumentID(companyIDDocument.getIdNumber());

        logInfo("Step 5: Make any changes in the populated fields, change the attached document and click [Save Changes] button");
        companyIDDocument.setIdNumber("C00000000");
        Pages.addNewDocumentPage().replaceDocument(Functions.getFilePathByName("clientDocument2.png"));
        Pages.addNewDocumentPage().typeValueToIDNumberField(companyIDDocument.getIdNumber());
        Pages.addNewDocumentPage().clickSaveChangesButton();

        logInfo("Step 6: Click the document in the documents list");
        Pages.documentsPage().clickDocumentRowByDocumentIDSelector(companyIDDocument.getIdNumber());
        // TODO: Clarify how to validate that doc image is changed

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
        // TODO: Implement verification at Maintenance History page
    }
}