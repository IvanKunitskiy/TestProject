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
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        ClientsActions.createClient().openClientPage();
        Pages.clientsSearchPage().clickAddNewClient();
        ClientsActions.individualClientActions().setBasicInformation(individualClient);
        Assert.assertTrue(Pages.addClientPage().isVerificationSuccess(),
                "Client data verification is not success");
        Pages.addClientPage().clickOkModalButton();

        Pages.addClientPage().clickViewMemberProfileButton();

        ClientsActions.individualClientActions().verifyClientData(individualClient);
    }
}
