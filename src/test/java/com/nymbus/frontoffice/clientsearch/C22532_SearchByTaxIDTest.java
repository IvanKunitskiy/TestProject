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
public class C22532_SearchByTaxIDTest extends BaseTest {
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

    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void searchByTaxID() {
        logInfo("Step 1: Log in to the system as the User from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Click within search field and try to search for an existing client (by 4 last digits of tax id number)");
        String taxIDQuery = client.getTaxID().substring(5);
        Pages.clientsSearchPage().typeToClientsSearchInputField(taxIDQuery);
        Assert.assertTrue(Pages.clientsSearchPage().getAllLookupResults().size() > 0, "There are no relevant lookup results");
        if (Pages.clientsSearchPage().getAllLookupResults().size() == 8) {
            Assert.assertTrue(Pages.clientsSearchPage().isLoadMoreResultsButtonVisible(), "'Load more results' button is not visible in search lookup list");
        }
        Assert.assertTrue(Pages.clientsSearchPage().isSearchResultsRelative(Pages.clientsSearchPage().getAllLookupResults(), taxIDQuery), "Search results are not relevant");

        logInfo("Step 3: Clear the data from the field and try to search for an existing client (by full tax id number)");
        Pages.clientsSearchPage().clickOnSearchInputFieldClearButton();
        Pages.clientsSearchPage().typeToClientsSearchInputField(client.getTaxID());
        Assert.assertTrue(Pages.clientsSearchPage().isSearchResultsRelative(Pages.clientsSearchPage().getAllLookupResults(), client.getTaxID()), "Search results are not relevant");

        logInfo("Step 4: Click the 'Search' button");
        Pages.clientsSearchPage().clickOnSearchButton();
        Assert.assertEquals(Pages.clientsSearchResultsPage().getClientFirstNameFromResultByIndex(1), client.getFirstName(), "First name is not relevant to the client");
        Assert.assertEquals(Pages.clientsSearchResultsPage().getClientLastNameFromResultByIndex(1), client.getLastName(), "Last name is not relevant to the client");
        Assert.assertEquals(Pages.clientsSearchResultsPage().getClientIDFromResultByIndex(1), client.getClientID(), "Client id is not relevant to the client");
        Assert.assertEquals(Pages.clientsSearchResultsPage().getClientTypeFromResultByIndex(1), client.getClientType(), "Client type is not relevant to the client");
        Assert.assertEquals(Pages.clientsSearchResultsPage().getClientAddressFromResultByIndex(1), client.getAddress().getAddress(), "Client address is not relevant to the client");
        Assert.assertEquals(Pages.clientsSearchResultsPage().getClientAKAFromResultByIndex(1), client.getAKA_1(), "Client AKA is not relevant to the client");
    }

}