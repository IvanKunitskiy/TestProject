package com.nymbus.frontoffice.clientsearch;

import com.nymbus.actions.Actions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.base.BaseTest;
import com.nymbus.models.client.Client;
import com.nymbus.pages.Pages;
import com.nymbus.util.Constants;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Clients search")
@Owner("Dmytro")
public class C22531_SearchByLastFourDigitsOfTaxID extends BaseTest {

    private Client client;

    @BeforeMethod
    public void preConditions() {
        client = new Client().setDefaultClientData();
    }

    @Test(description = "C22531, Search by last four digits of TaxID")
    @Severity(SeverityLevel.CRITICAL)
    public void searchByTaxID() {
        LOG.info("Step 1: Log in to the system as the User from the precondition");
        navigateToUrl(Constants.URL);
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        ClientsActions.createClient().createClient(client);
        final String clientID = Pages.clientDetailsPage().getClientID();
        final String clientTaxID = client.getTaxID();
        Pages.aSideMenuPage().clickClientMenuItem();

        LOG.info("Step 2: Click within search field and try to search for an existing individualClient (by 4 last digits of tax id number)");
        final String taxIDQuery = clientTaxID.substring(5);
        Pages.clientsPage().typeToClientsSearchInputField(taxIDQuery);

        Assert.assertTrue(Pages.clientsPage().getAllLookupResults().size() > 0, "There are no relevant lookup results");
        if (Pages.clientsPage().getAllLookupResults().size() == 8) {
            Assert.assertTrue(Pages.clientsPage().isLoadMoreResultsButtonVisible(), "'Load more results' button is not visible in search lookup list");
        }
        Assert.assertTrue(Pages.clientsPage().isSearchResultsRelative(Pages.clientsPage().getAllLookupResults(), taxIDQuery), "Search results are not relevant");

        LOG.info("Step 3: Click the 'Search' button");
        Pages.clientsPage().clickOnSearchButton();
        Assert.assertTrue(Pages.clientsSearchResultsPage().getClientIDsFromSearchResults().contains(clientID), "Client not found in search results by 'Client ID' criteria");
    }
}