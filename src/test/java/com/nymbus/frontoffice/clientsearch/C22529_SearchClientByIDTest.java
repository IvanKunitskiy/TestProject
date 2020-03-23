package com.nymbus.frontoffice.clientsearch;

import com.nymbus.actions.Actions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.models.client.Client;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

@Epic("Frontoffice")
@Feature("Clients search")
@Severity(SeverityLevel.CRITICAL)
@Owner("Dmytro")
public class C22529_SearchClientByIDTest extends BaseTest {

    private Client client;

    @BeforeMethod
    public void preCondition() {
        // Set up Client
        client = new Client().setDefaultClientData();
        client.setClientStatus("Member");
        client.setClientType("Individual");

        // Login to the system and create a client
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.createClient().createClient(client);
        client.setClientID(Pages.clientDetailsPage().getClientID());
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22529, Search client by ID")
    public void searchClientByID() {
        logInfo("Step 1: Log in to the system as the User from the precondition");
        Selenide.open(Constants.URL);
        logInfo("Step 1: Log in to the system as the User from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Click within search field and try to search for an existing client (by client id)");
        Pages.clientsPage().typeToClientsSearchInputField(client.getClientID());
        List<String> lookupResults = Pages.clientsPage().getAllLookupResults();
        Assert.assertTrue(Pages.clientsPage().isSearchResultsRelative(lookupResults, client.getClientID()));

        logInfo("Step 3: Click the 'Search' button");
        Pages.clientsPage().clickOnSearchButton();
        Pages.clientsSearchResultsPage().waitForSearchResults();

        Assert.assertEquals(Pages.clientsSearchResultsPage().getClientFirstNameFromResultByIndex(1), client.getFirstName(), "First name is not relevant to the client");
        Assert.assertEquals(Pages.clientsSearchResultsPage().getClientLastNameFromResultByIndex(1), client.getLastName(), "Last name is not relevant to the client");
        Assert.assertEquals(Pages.clientsSearchResultsPage().getClientIDFromResultByIndex(1), client.getClientID(), "Client id is not relevant to the client");
        Assert.assertEquals(Pages.clientsSearchResultsPage().getClientTypeFromResultByIndex(1), client.getClientType(), "Client type is not relevant to the client");
        Assert.assertEquals(Pages.clientsSearchResultsPage().getClientAddressFromResultByIndex(1), client.getAddress().getAddress(), "Client address is not relevant to the client");
        Assert.assertEquals(Pages.clientsSearchResultsPage().getClientAKAFromResultByIndex(1), client.getAKA_1(), "Client AKA is not relevant to the client");
    }
}
