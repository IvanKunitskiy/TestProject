package com.nymbus.frontoffice.clientsearch;

import com.nymbus.actions.Actions;
import com.nymbus.base.BaseTest;
import com.nymbus.models.TempClient;
import com.nymbus.models.client.Client;
import com.nymbus.pages.Pages;
import com.nymbus.util.Constants;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertTrue;

@Epic("Frontoffice")
@Feature("User Management")
@Owner("Dmytro")
public class C22526_SearchByName extends BaseTest {
    private Client client;

    @BeforeMethod
    public void preCondition() {
        client = new Client();
        client.setFirstName("Anna");
        client.setLastName("Adams");

        navigateToUrl(Constants.URL);

        LOG.info("Step 1: Log in to the system as the User from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        Pages.navigationPage().waitForUserMenuVisible();
    }

    @Test(description = "C22526, Search client by name")
    public void searchByName() {
        LOG.info("Step 2: Click within search field and try to search for an existing client (by first name)");
        final String firstNameLetters = client.getFirstName().substring(0, 3);
        Pages.clientsPage().typeToClientsSearchInputField(firstNameLetters);

        int lookupResultsCount = Pages.clientsPage().getLookupResultsCount();
        Assert.assertEquals(lookupResultsCount, 8);
        assertTrue(Pages.clientsPage().isLoadMoreResultsButtonVisible());

        List<TempClient> clients = Actions.clientPageActions().getAllClientsFromLookupResults(lookupResultsCount);
        clients.stream().forEach(c -> assertTrue(c.getFirstName().contains(firstNameLetters)));

        LOG.info("Step 3: Click [Search] button");
        Pages.clientsPage().clickOnSearchButton();
        int searchResults = Pages.clientsSearchResultsPage().getSearchResultsCount();
        Assert.assertEquals(searchResults, 10);
        assertTrue(Pages.clientsSearchResultsPage().isLoadMoreResultsButtonVisible());

        clients = Actions.clientsSearchResultsPageActions().getAllClientsFromResult(searchResults);
        clients.stream().forEach(c -> assertTrue(c.getFirstName().contains(firstNameLetters)));

        LOG.info("Step 4: Clear the data from the field and try to search for an existing client (by last name)");
        Pages.clientsPage().clickOnSearchInputFieldClearButton();

        final String lastNameLetters = client.getLastName().substring(0, 3);
        Pages.clientsPage().typeToClientsSearchInputField(lastNameLetters);

        lookupResultsCount = Pages.clientsPage().getLookupResultsCount();
        Assert.assertEquals(lookupResultsCount, 8);
        assertTrue(Pages.clientsPage().isLoadMoreResultsButtonVisible());

        clients = Actions.clientPageActions().getAllClientsFromLookupResults(lookupResultsCount);
        clients.stream().forEach(c -> assertTrue(c.getLastName().contains(lastNameLetters)));
    }
}
