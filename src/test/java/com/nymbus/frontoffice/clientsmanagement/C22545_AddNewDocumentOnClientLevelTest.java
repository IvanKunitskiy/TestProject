package com.nymbus.frontoffice.clientsmanagement;

import com.nymbus.actions.Actions;
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
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Box Accounts Management")
public class C22545_AddNewDocumentOnClientLevelTest extends BaseTest {

    private IndividualClient client;
    private CompanyID companyIDDocument;
    private String clientID;

    @BeforeMethod
    public void preCondition() {
        // Set up the client
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
        Actions.loginActions().doLogOut();
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "C22545, Add New Document On Client Level")
    public void addNewDocumentOnClientLevel() {
        logInfo("Step 1: Log in to the system as User from the preconditions");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Go to Clients and search for the client from the precondition");
        Actions.clientPageActions().searchAndOpenIndividualClientByID(clientID);

        logInfo("Step 3: Open client profile on the Documents tab");
        Pages.accountNavigationPage().clickDocumentsTab();

        logInfo("Step 4: Click [Add New Document] button");
        Pages.documentsPage().clickAddNewDocumentButton();

        logInfo("Step 5: Drag & drop some file (e.g. picture or PDF), fill in all the displayed fields with some valid data and click [Save Changes] button");
        Pages.addNewDocumentPage().uploadNewDocument(Functions.getFilePathByName("clientDocument.png"));
        DocumentActions.createDocumentActions().setIDType(companyIDDocument);
        Pages.addNewDocumentPage().typeValueToIDNumberField(companyIDDocument.getIdNumber());
        DocumentActions.createDocumentActions().setIssuedBY(companyIDDocument);
        DocumentActions.createDocumentActions().setCountry(companyIDDocument);
        Pages.addNewDocumentPage().setIssueDateValue(companyIDDocument.getIssueDate());
        Pages.addNewDocumentPage().setExpirationDateValue(companyIDDocument.getExpirationDate());
        Pages.addNewDocumentPage().clickSaveChangesButton();

        logInfo("Step 6: Open Clients profile on Maintenance -> Maintenance History page");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

        logInfo("Step 7: Look through the records on Maintenance History page and check that all fields that were filled in during account creation are reported in account Maintenance History");
        // TODO: Implement verification at Maintenance History page
    }
}
