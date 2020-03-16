package com.nymbus.frontoffice.clientsmanagement.createclients;

import com.codeborne.selenide.Selenide;
import com.nymbus.actions.Actions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.models.client.Client;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Create clients")
@Owner("Petro")
public class C22541_CreateIndividualMemberClient extends BaseTest {

    private Client client;

    @BeforeMethod
    public void prepareClientData(){
        client = new Client().setDefaultClientData();
        client.setClientType("Individual");
        client.setClientStatus("Member");
    }

    @Test(description = "C22541, Create Client - Individual - Member")
    @Severity(SeverityLevel.CRITICAL)
    public void firstTest() {
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        ClientsActions.createClient().createClient(client);

        Assert.assertEquals(ClientsActions.verifyClientDataActions().getIndividualClientData(), client,
                "Client's data is not equals");

    }
}
