package com.nymbus.actions.clients;

import com.nymbus.models.TempClient;
import com.nymbus.models.client.Client;
import com.nymbus.newmodels.account.Account;
import com.nymbus.pages.Pages;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class ClientPageActions {

    public List<TempClient> getAllClientsFromLookupResults(int lookupResultsCount) {
        List<TempClient> clients = new ArrayList<>();
        for (int i = 1; i <= lookupResultsCount; i++) {
            TempClient client = new TempClient();
            client.setFirstName(Pages.clientsSearchPage().getClientFirstNameFromLookupResultByIndex(i));
            client.setLastName(Pages.clientsSearchPage().getClientLastNameFromLookupResultByIndex(i));
            clients.add(client);
        }

        return clients;
    }

    public void searchAndOpenClientByName(String name) {
        Pages.aSideMenuPage().clickClientMenuItem();
        Pages.clientsSearchPage().typeToClientsSearchInputField(name);
        Pages.clientsSearchResultsPage().clickSearchResultsWithText(name);
    }

    public void searchAndOpenClientByID(Client client) {
        Pages.clientsSearchPage().typeToClientsSearchInputField(client.getClientID());
        Assert.assertEquals(Pages.clientsSearchPage().getAllLookupResults().size(), 1, "There is more than one client found");
        Assert.assertTrue(Pages.clientsSearchPage().isSearchResultsRelative(Pages.clientsSearchPage().getAllLookupResults(), client.getClientID()));
        Pages.clientsSearchPage().clickOnSearchButton();
        Pages.clientsSearchResultsPage().clickTheExactlyMatchedClientInSearchResults();
        Pages.clientDetailsPage().waitForPageLoaded();
    }

    public void searchAndOpenClientByAccountNumber(Account account) {
        Pages.clientsSearchPage().typeToClientsSearchInputField(account.getAccountNumber());
        Assert.assertEquals(Pages.clientsSearchPage().getAllLookupResults().size(), 1, "There is more than one client found");
        Assert.assertTrue(Pages.clientsSearchPage().isSearchResultsRelative(Pages.clientsSearchPage().getAllLookupResults(), account.getAccountNumber()));
        Pages.clientsSearchPage().clickOnSearchButton();
        Pages.clientsSearchResultsPage().clickTheExactlyMatchedClientInSearchResults();
        Pages.clientDetailsPage().waitForPageLoaded();
    }

    public void searchAndOpenAccountByAccountNumber(Account account) {
        Pages.clientsSearchPage().typeToClientsSearchInputField(account.getAccountNumber());
        Assert.assertEquals(Pages.clientsSearchPage().getAllLookupResults().size(), 1, "There is more than one client found");
        Assert.assertTrue(Pages.clientsSearchPage().isSearchResultsRelative(Pages.clientsSearchPage().getAllLookupResults(), account.getAccountNumber()));
        Pages.clientsSearchPage().clickOnSearchButton();
        Pages.clientsSearchResultsPage().clickTheExactlyMatchedAccountInSearchResults(account.getAccountNumber());
    }
}