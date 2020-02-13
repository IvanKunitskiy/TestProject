package com.nymbus.frontoffice.clientsmanagement.createclients;

import com.nymbus.actions.Actions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.base.BaseTest;
import com.nymbus.models.client.Client;
import com.nymbus.pages.Pages;
import com.nymbus.util.Constants;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Create clients")
public class C22542_CreateClientIndividualOfacFail extends BaseTest {

    private Client client;

    @BeforeMethod
    public void prepareClientData(){
        client = new Client().setDefaultClientData();
        client.setClientType("Individual");
        client.setClientStatus("Member");
        client.setFirstName("Subhi");
        client.setMiddleName("");
        client.setLastName("TUFAYLI");
    }

    @Test(description = "C22542, Create Client - Individual - Ofac check fail")
    @Severity(SeverityLevel.CRITICAL)
    public void firstTest() {
        navigateToUrl(Constants.URL);
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        ClientsActions.createClient().openClientPage();

        Pages.clientsPage().clickAddNewClient();

        ClientsActions.createClient().setBasicInformation(client);

        Assert.assertFalse(Pages.addClientPage().isVerificationSuccess(),
                "Client data verification is success");

    }
}
