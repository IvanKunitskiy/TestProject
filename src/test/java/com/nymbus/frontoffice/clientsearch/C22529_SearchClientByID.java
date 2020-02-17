package com.nymbus.frontoffice.clientsearch;

import com.nymbus.actions.Actions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.base.BaseTest;
import com.nymbus.models.client.Client;
import com.nymbus.pages.Pages;
import com.nymbus.util.Constants;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("User Managenment")
@Owner("Dmytro")
public class C22529_SearchClientByID extends BaseTest {
    private Client client;

    @BeforeMethod
    public void preCondition() {
        client = new Client().setDefaultClientData();
    }

    @Test(description = "C22529, Search client by ID")
    public void SearchByID() {
        System.out.println(client.toString());
        String clientID =  client.getClientID();
        String clientFullName =  client.getLastName() + ", " + client.getFirstName();
        System.out.println("----------> " + clientID);

        navigateToUrl(Constants.URL);

        LOG.info("Step 1: Log in to the system as the User from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.createClient().createClient(client);

        LOG.info("Step 2: Click within search field and try to search for an existing client (by client id)");


    }


}
