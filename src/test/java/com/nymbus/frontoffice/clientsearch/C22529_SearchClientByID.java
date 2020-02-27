package com.nymbus.frontoffice.clientsearch;

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

import java.util.List;

@Epic("Frontoffice")
@Feature("Clients search")
@Severity(SeverityLevel.CRITICAL)
@Owner("Dmytro")
public class C22529_SearchClientByID extends BaseTest {

    private Client client;

    @BeforeMethod
    public void preCondition() {
        client = new Client().setDefaultClientData();
    }

    @Test(description = "C22529, Search individualClient by ID")
    public void searchClientByID() {

        LOG.info("Step 1: Log in to the system as the User from the precondition");
        navigateToUrl(Constants.URL);
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.createClient().createClient(client);

        final String clientID = Pages.clientDetailsPage().getClientID();
        Pages.aSideMenuPage().clickClientMenuItem();

        LOG.info("Step 2: Click within search field and try to search for an existing individualClient (by individualClient id)");
        Pages.clientsPage().typeToClientsSearchInputField(clientID);
        List<String> lookupResults = Pages.clientsPage().getAllLookupResults();
        Assert.assertTrue(Pages.clientsPage().isSearchResultsRelative(lookupResults, clientID));

        LOG.info("Step 3: Click the 'Search' button");
        Pages.clientsPage().clickOnSearchButton();
        Pages.clientsSearchResultsPage().waitForSearchResults();

        Assert.assertEquals(Pages.clientsSearchResultsPage().getClientFirstNameFromResultByIndex(1), client.getFirstName(), "First name is not relevant to the individualClient");
        Assert.assertEquals(Pages.clientsSearchResultsPage().getClientLastNameFromResultByIndex(1), client.getLastName(), "Last name is not relevant to the individualClient");
        Assert.assertEquals(Pages.clientsSearchResultsPage().getClientIDFromResultByIndex(1), clientID, "Client id is not relevant to the individualClient");
        Assert.assertEquals(Pages.clientsSearchResultsPage().getClientTypeFromResultByIndex(1), client.getClientType(), "Client basicinformation is not relevant to the individualClient");
        Assert.assertEquals(Pages.clientsSearchResultsPage().getClientAddressFromResultByIndex(1), client.getAddress().getAddress(), "Client address is not relevant to the individualClient");
        Assert.assertEquals(Pages.clientsSearchResultsPage().getClientAKAFromResultByIndex(1), client.getAKA_1(), "Client AKA is not relevant to the individualClient");
    }
}
