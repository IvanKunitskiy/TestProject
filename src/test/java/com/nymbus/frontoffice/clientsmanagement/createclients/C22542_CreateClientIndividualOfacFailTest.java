package com.nymbus.frontoffice.clientsmanagement.createclients;

import com.codeborne.selenide.Selenide;
import com.nymbus.actions.Actions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.client.verifyingmodels.FirstNameAndLastNameModel;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.pages.Pages;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Create clients")
@Owner("Petro")
public class C22542_CreateClientIndividualOfacFailTest extends BaseTest {

    private IndividualClient individualClient;

    @BeforeMethod
    public void prepareClientData(){
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());

        individualClient = individualClientBuilder.buildClient();

        Selenide.open(Constants.WEB_ADMIN_URL);

        WebAdminActions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        FirstNameAndLastNameModel existingClient = WebAdminActions.webAdminUsersActions().getExistingIndividualClient();

        individualClient.getIndividualType().setFirstName(existingClient.getFirstName());
        individualClient.getIndividualType().setMiddleName("");
        individualClient.getIndividualType().setLastName(existingClient.getLastName());
    }

    private final String TEST_RUN_NAME = "Create clients";

    @TestRailIssue(issueID = 22542, testRunName = TEST_RUN_NAME)
    @Test(description = "C22542, Create Client - IndividualType - Ofac check fail")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyOfacValidation() {
        logInfo("Step 1: Log in to the system as the User from the preconditions");
        Selenide.open(Constants.URL);
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Go to Clients page and click [Add new Client] button");
        logInfo("Step 3: Select Client type == Individual");
        logInfo("Step 4: Fill in the following fields using data from some specific OFAC entry from preconditions:\n" +
                "- First Name (security.ofac.entry->firstName)\n" +
                "- Last Name (security.ofac.entry->lastName)\n" +
                "- Country in Address section (security.ofac.entry.address-> country)\n" +
                "- Address in Address section (security.ofac.entry.address-> address1, address2, address3)\n" +
                "- City in Address section (security.ofac.entry.address->city)");
        logInfo(" Step 5: Fill in all other required fields with correct data and click [Save and Continue] button");
        ClientsActions.createClient().openClientPage();

        Pages.clientsSearchPage().clickAddNewClient();

        ClientsActions.individualClientActions().setBasicInformation(individualClient);

        Assert.assertFalse(Pages.addClientPage().isVerificationSuccess(),
                "Client data verification is success");

        logInfo("Step 6: Click [Confirm Match] button");
        Pages.ofacCheckFailModalPage().clickConfirm();

        logInfo("Step 7: Click [Print] button");
        SelenideTools.switchToLastTab();
        Pages.ofacCheckFailModalPage().clickPrint();
    }
}