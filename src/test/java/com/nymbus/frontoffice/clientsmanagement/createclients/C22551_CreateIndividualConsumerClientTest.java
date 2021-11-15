package com.nymbus.frontoffice.clientsmanagement.createclients;

import com.nymbus.actions.Actions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.models.client.Client;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.client.clientdetails.type.ClientStatus;
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
public class C22551_CreateIndividualConsumerClientTest extends BaseTest {

    private IndividualClient individualClient;

    @BeforeMethod
    public void prepareClientData(){
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());

        individualClient = individualClientBuilder.buildClient();
        individualClient.getIndividualType().setClientStatus(ClientStatus.CONSUMER);
    }

    private final String TEST_RUN_NAME = "Create clients";

    @TestRailIssue(issueID = 22551, testRunName = TEST_RUN_NAME)
    @Test(description = "C22551, Create Client - Consumer")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyIndividualConsumerClientCreation() {

        logInfo("Step 1: Log in to the system as the User from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Go to Clients page and click [Add new Client] button");
        ClientsActions.createClient().openClientPage();
        Pages.clientsSearchPage().clickAddNewClient();

        logInfo("Step 3: Select Client type == Individual");
        logInfo("Step 4: Select Client Status == Consumer");
        logInfo("Step 5: Fill in such text fields with valid data:\n" +
                "- First Name - any alphabetical value\n" +
                "- Middle Name - any alphabetical value\n" +
                "- Last Name - any alphabetical value\n" +
                "- Tax ID - UNIQUE numeric value with length = 9\n" +
                "- Address Line 1 - any alphanumeric value\n" +
                "- Address Line 2- any alphanumeric value\n" +
                "- City- any alphanumeric value\n" +
                "- Zip Code - 5 digits, + additional field - 4 digits\n" +
                "- Years in this address - any numeric value\n" +
                "- ID Number - any numeric value");
        logInfo("Step 6: Select data for such drop-down fields:\n" +
                "- Issued By\n" +
                "- ID Type - Passport\n" +
                "- Country - any (e.g. United States)\n" +
                "- State - any\n" +
                "- Address Type - Physical");
        logInfo("Step 7: Fill in such calendar fields with valid data:\n" +
                "- Birth Date - any date ≤ Current Date\n" +
                "- Expiration Date - any date ≥ Current Date");
        logInfo("Step 8: Click [Save and Continue] button and Submit OFAC check popup");
        ClientsActions.individualClientActions().setBasicInformation(individualClient);
        Assert.assertTrue(Pages.addClientPage().isVerificationSuccess(),
                "Client data verification is not success");
        Pages.addClientPage().clickOkModalButton();

        logInfo("Step 9: Click [View Consumer Profile] button");
        Pages.addClientPage().clickViewMemberProfileButton();
        ClientsActions.individualClientActions().verifyClientData(individualClient);

    }
}
