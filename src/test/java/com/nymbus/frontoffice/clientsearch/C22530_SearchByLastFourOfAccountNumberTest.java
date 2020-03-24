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
public class C22530_SearchByLastFourOfAccountNumberTest extends BaseTest {

    private Client client;

    @BeforeMethod
    public void preCondition() {

        // Set up Client
        client = new Client().setDefaultClientData();
        client.setClientStatus("Member");
        client.setClientType("Individual");
        client.setAccountNumber("28461564083");

        // Login to the system and create a client
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.createClient().createClient(client);
        Actions.loginActions().doLogOut();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    public void searchByLastFourOfAccountNumber() {

        logInfo("Step 1: Log in to the system as the User from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Click within search field and try to search for an existing account (by 4 last digits of account number)");
        final String accountNumberQuery = client.getAccountNumber().substring(client.getAccountNumber().length() - 4);
        Pages.clientsPage().typeToClientsSearchInputField(accountNumberQuery);
        Assert.assertTrue(Pages.clientsPage().getAllLookupResults().size() > 0, "There are no relevant lookup results");
        if (Pages.clientsPage().getAllLookupResults().size() == 8) {
            Assert.assertTrue(Pages.clientsPage().isLoadMoreResultsButtonVisible(), "'Load more results' button is not visible in search lookup list");
        }
        Assert.assertTrue(Pages.clientsPage().isSearchResultsRelative(Pages.clientsPage().getAllLookupResults(), accountNumberQuery), "Search results are not relevant");

        logInfo("Step 3: Click the 'Search' button");
        Pages.clientsPage().clickOnSearchButton();
        Assert.assertTrue(Pages.clientsPage().isSearchResultsRelative(Pages.clientsSearchResultsPage().getAccountNumbersFromSearchResults(), accountNumberQuery));
    }
}