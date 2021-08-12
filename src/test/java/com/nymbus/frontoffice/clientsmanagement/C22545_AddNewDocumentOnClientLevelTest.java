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
import com.nymbus.testrail.CustomStepResult;
import com.nymbus.testrail.TestRailAssert;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Box Accounts Management")
@Feature("Petro")
public class C22545_AddNewDocumentOnClientLevelTest extends BaseTest {

    private CompanyID companyIDDocument;
    private String clientID;

    @BeforeMethod
    public void preCondition() {
        // Set up the client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

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

    private final String TEST_RUN_NAME = "Clients Management";

    @TestRailIssue(issueID = 22545, testRunName = TEST_RUN_NAME)
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

        logInfo("Step 5: Drag & drop some file (e.g. picture or PDF), fill in all the text fields with some valid data, but skip calendar date picker fields");
        logInfo("Step 6: Fill in 'issue date' and 'expiration date' fields by expanding calendar date picker, use mouse to select date.\n" +
                "Note: Skip this step if date field is not supposed to exist (e.g. for logo)");
        logInfo("Step 7: Click [Save Changes] button");
        Pages.addNewDocumentPage().uploadNewDocument(Functions.getFilePathByName("clientDocument.png"));
        DocumentActions.createDocumentActions().setIDType(companyIDDocument);
        Pages.addNewDocumentPage().typeValueToIDNumberField(companyIDDocument.getIdNumber());
        DocumentActions.createDocumentActions().setIssuedBY(companyIDDocument);
        DocumentActions.createDocumentActions().setCountry(companyIDDocument);
        Pages.addNewDocumentPage().setIssueDateValue(companyIDDocument.getIssueDate());
        Pages.addNewDocumentPage().setExpirationDateValue(companyIDDocument.getExpirationDate());
        Pages.addNewDocumentPage().clickSaveChangesButton();
        Pages.addNewDocumentPage().waitForModalInvisibility();

        logInfo("Step 8: Make sure that Document identification icon is present for any document");
        TestRailAssert.assertTrue(Pages.documentsPage().getDocumentIdentificationIconCount() == 4,
                new CustomStepResult("Icons are not available for all documents", "Icons are available for all documents"));

        logInfo("Step 9: Open Clients profile on Maintenance -> Maintenance History page");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

        logInfo("Step 10: Look through the records on the Maintenance History page and verify that records about newly created Document are present on the Maintenance History page");
        AccountActions.accountMaintenanceActions().verifyClientLevelDocumentRecords();
    }
}
