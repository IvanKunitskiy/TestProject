package com.nymbus.frontoffice.clientsmanagement.createclients;

import com.nymbus.actions.Actions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.base.BaseTest;
import com.nymbus.model.client.Client;
import com.nymbus.pages.Pages;
import com.nymbus.util.Constants;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Create clients")
@Owner("Petro")
public class C22551_CreateIndividualConsumerClient extends BaseTest {

    private Client client;

    @BeforeMethod
    public void prepareClientData(){
        client = new Client().setConsumerClientData();
        client.setClientType("IndividualType");
    }

    @Test(description = "C22551, Create Client - Consumer")
    @Severity(SeverityLevel.CRITICAL)
    public void firstTest() {
        navigateToUrl(Constants.URL);
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        ClientsActions.createClient().openClientPage();
        Pages.clientsPage().clickAddNewClient();
        ClientsActions.createClient().setBasicInformation(client);
        Assert.assertTrue(Pages.addClientPage().isVerificationSuccess(),
                "Client data verification is not success");
        Pages.addClientPage().clickOkModalButton();

        Pages.addClientPage().clickViewMemberProfileButton();

        Assert.assertEquals(ClientsActions.verifyClientDataActions().getIndividualConsumerClientData(), client,
                "Client's data is not equals");

    }
}
