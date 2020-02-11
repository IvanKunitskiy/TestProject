package com.nymbus.frontoffice.clientsearch;

import com.nymbus.actions.Actions;
import com.nymbus.base.BaseTest;
import com.nymbus.models.TempClient;
import com.nymbus.pages.Pages;
import com.nymbus.util.Constants;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

@Epic("Frontoffice")
@Feature("User Management")
@Owner("Dmytro")
public class C22526_SearchByName extends BaseTest {
    // TODO: Need to change this objects to Petro's 'Client'
    private TempClient client;

    @BeforeMethod
    public void preCondition() {
        client = new TempClient("Anna", "Adams");

        navigateToUrl(Constants.URL);

        LOG.info("Step 1: Log in to the system as the User from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        Pages.navigationPage().waitForUserMenuVisible();
    }

    @Test(description = "C22526, Search client by name")
    public void searchByName() {
        LOG.info("Step 2: Click within search field and try to search for an existing client (by first name)");
        Pages.clientsPage().typeToClientsSearchInputField(client.getFirstName().substring(0, 3));
        int lookupResultsCount = Pages.clientsPage().getLookupResultsCount();
        Assert.assertEquals(lookupResultsCount, 8);
        Assert.assertTrue(Pages.clientsPage().isLoadMoreResultsButtonVisible());

        List<TempClient> clients = Actions.clientPageActions().getAllClientsFromLookupResults(lookupResultsCount);
        Assert.assertTrue(Actions.clientPageActions().verifySearchResultsByFirstName(clients, client.getFirstName().substring(0, 3)));

        LOG.info("Step 3: Click [Search] button");
        Pages.clientsPage().clickOnSearchButton();
        int searchResults = Pages.clientsSearchResultsPage().getSearchResultsCount();
        Assert.assertEquals(searchResults, 10);
        Assert.assertTrue(Pages.clientsSearchResultsPage().isLoadMoreResultsButtonVisible());

        clients = Actions.clientsSearchResultsPageActions().getAllClientsFromResult(searchResults);
        Assert.assertTrue(Actions.clientPageActions().verifySearchResultsByFirstName(clients, client.getFirstName().substring(0, 3)));

        LOG.info("Step 4: Clear the data from the field and try to search for an existing client (by last name)");
        Pages.clientsPage().clickOnSearchInputFieldClearButton();

        Pages.clientsPage().typeToClientsSearchInputField(client.getLastName().substring(0, 3));
        lookupResultsCount = Pages.clientsPage().getLookupResultsCount();
        Assert.assertEquals(lookupResultsCount, 8);
        Assert.assertTrue(Pages.clientsPage().isLoadMoreResultsButtonVisible());

        clients = Actions.clientPageActions().getAllClientsFromLookupResults(lookupResultsCount);
        Assert.assertTrue(Actions.clientPageActions().verifySearchResultsByLastName(clients, client.getLastName().substring(0, 3)));
    }
}
