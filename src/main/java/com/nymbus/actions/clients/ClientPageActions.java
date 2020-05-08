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

    public List<String> getAllClientFirstName(int lookupResultsCount){
        List<String> listOfFirstName = new ArrayList<>();

        for (int i = 1; i <= lookupResultsCount; i++) {
            listOfFirstName.add(Pages.clientsSearchPage().getClientFirstNameFromLookupResultByIndex(i));
        }

        return listOfFirstName;
    }

    public List<String> getAllClientLastName(int lookupResultsCount){
        List<String> listOfLastName = new ArrayList<>();

        for (int i = 1; i <= lookupResultsCount; i++) {
            listOfLastName.add(Pages.clientsSearchPage().getClientLastNameFromLookupResultByIndex(i));
        }

        return listOfLastName;
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

    public void closeAllNotifications() {
        int notificationsCount = Pages.clientDetailsPage().getNotificationCount();

        closeNotifications(notificationsCount);
    }

    private void closeNotifications(int count) {
        for (int i = 1; i <= count; i++) {
            Pages.clientDetailsPage().clickCloseNotificationByIndex(i);

            Pages.clientDetailsPage().clickCloseNotificationByIndex(i);

            Pages.clientDetailsPage().waitForNotificationInvisibility(i);
        }
    }
}