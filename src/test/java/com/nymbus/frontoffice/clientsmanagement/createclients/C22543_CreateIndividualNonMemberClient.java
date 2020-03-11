package com.nymbus.frontoffice.clientsmanagement.createclients;

import com.nymbus.actions.Actions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.base.BaseTest;
import com.nymbus.models.client.Client;
import com.nymbus.util.Constants;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Create clients")
@Owner("Petro")
public class C22543_CreateIndividualNonMemberClient extends BaseTest {

    private Client client;

    @BeforeMethod
    public void prepareClientData(){
        client = new Client().setDefaultClientData();
        client.setClientType("IndividualType");
        client.setClientStatus("Non-member");
    }

    @Test(description = "C22543, Create Client - IndividualType - Non Member")
    @Severity(SeverityLevel.CRITICAL)
    public void firstTest() {
        navigateToUrl(Constants.URL);
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        ClientsActions.createClient().createClient(client);

        Assert.assertEquals(ClientsActions.verifyClientDataActions().getIndividualClientData(), client,
                "Client's data is not equals");
    }
}
