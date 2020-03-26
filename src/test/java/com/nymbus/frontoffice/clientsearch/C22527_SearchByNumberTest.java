package com.nymbus.frontoffice.clientsearch;

import com.codeborne.selenide.Selenide;
import com.nymbus.actions.Actions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.models.client.Client;
import com.nymbus.pages.Pages;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.testng.Assert.assertTrue;

@Epic("Frontoffice")
@Feature("Clients search")
@Owner("Dmytro")
public class C22527_SearchByNumberTest extends BaseTest {
    private Client client;

    @BeforeMethod
    public void preCondition() {
        client = new Client();
        client.setAccountNumber("28461564083");

        Selenide.open(Constants.URL);

        logInfo("Step 1: Log in to the system as the User from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        Pages.navigationPage().waitForUserMenuVisible();
    }

    @Severity(CRITICAL)
    @Test(description = "C22527, Search individualClient by number")
    public void searchByNumber() {
        logInfo("Step 2: Click within search field and try to search for an existing client (by first name)");
        String accountNumber = client.getAccountNumber();
        String lastFourNumbers = accountNumber.substring(accountNumber.length() - 4);
        Pages.clientsSearchPage().typeToClientsSearchInputField(lastFourNumbers);
        int lookupResultsCount = Pages.clientsSearchPage().getLookupResultsCount();
        Assert.assertEquals(lookupResultsCount, 8);
        assertTrue(Pages.clientsSearchPage().isLoadMoreResultsButtonVisible());

        List<String> clients = Pages.clientsSearchPage().getAllLookupResults();
        clients.stream().forEach(s -> Assert.assertEquals(s.substring(s.length() - 4), lastFourNumbers));

        logInfo("Step 3: Click [Search] button");
        Pages.clientsSearchPage().clickOnSearchButton();
        int searchResults = Pages.clientsSearchResultsPage().getAccountNumbersFromSearchResults().size();
        assertTrue(searchResults <= 10);
        if (searchResults == 10)
            assertTrue(Pages.clientsSearchResultsPage().isLoadMoreResultsButtonVisible());

        clients = Pages.clientsSearchResultsPage().getAccountNumbersFromSearchResults();
        clients.stream().forEach(s -> assertTrue(s.substring(s.length() - 4).contains(lastFourNumbers)));

        logInfo("Step 4: Clear the data from the field and try to search for an existing client (by last name)");
        Pages.clientsSearchPage().clickOnSearchInputFieldClearButton();

        Pages.clientsSearchPage().typeToClientsSearchInputField(client.getAccountNumber());
        lookupResultsCount = Pages.clientsSearchPage().getLookupResultsCount();
        Assert.assertEquals(lookupResultsCount, 1);
        Assert.assertFalse(Pages.clientsSearchPage().isLoadMoreResultsButtonVisible());

        clients = Pages.clientsSearchPage().getAllLookupResults();
        Assert.assertEquals(clients.get(0), client.getAccountNumber());

        logInfo("Step 5: Click [Search] button and pay attention to the search results list");
        // TODO: Need to implement assertion for exist Client object
    }
}
