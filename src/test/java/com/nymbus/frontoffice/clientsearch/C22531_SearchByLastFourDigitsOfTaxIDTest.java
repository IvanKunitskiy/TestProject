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

@Epic("Frontoffice")
@Feature("Clients search")
@Owner("Dmytro")
public class C22531_SearchByLastFourDigitsOfTaxIDTest extends BaseTest {

    private Client client;

    @BeforeMethod
    public void preConditions() {

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

    @Test(description = "C22531, Search by last four digits of TaxID")
    @Severity(SeverityLevel.CRITICAL)
    public void searchByTaxID() {

        logInfo("Step 1: Log in to the system as the User from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Click within search field and try to search for an existing client (by 4 last digits of tax id number)");
        final String taxIDQuery = client.getTaxID().substring(5);
        Pages.clientsPage().typeToClientsSearchInputField(taxIDQuery);
        Assert.assertTrue(Pages.clientsPage().getAllLookupResults().size() > 0, "There are no relevant lookup results");
        if (Pages.clientsPage().getAllLookupResults().size() == 8) {
            Assert.assertTrue(Pages.clientsPage().isLoadMoreResultsButtonVisible(), "'Load more results' button is not visible in search lookup list");
        }
        Assert.assertTrue(Pages.clientsPage().isSearchResultsRelative(Pages.clientsPage().getAllLookupResults(), taxIDQuery), "Search results are not relevant");

        logInfo("Step 3: Click the 'Search' button");
        Pages.clientsPage().clickOnSearchButton();
        Assert.assertTrue(Pages.clientsSearchResultsPage().getClientIDsFromSearchResults().contains(client.getClientID()), "Client not found in search results by 'Client ID' criteria");
    }
}