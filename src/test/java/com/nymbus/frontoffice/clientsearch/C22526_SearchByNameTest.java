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

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.testng.Assert.assertTrue;

@Epic("Frontoffice")
@Feature("Clients search")
@Owner("Dmytro")
public class C22526_SearchByNameTest extends BaseTest {
    private Client client;

    @BeforeMethod
    public void preCondition() {
        client = new Client();
        client.setFirstName("Anna");
        client.setLastName("Adams");

        Selenide.open(Constants.URL);

        logInfo("Step 1: Log in to the system as the User from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        Pages.navigationPage().waitForUserMenuVisible();
    }

    @Severity(CRITICAL)
    @Test(description = "C22526, Search individualClient by name")
    public void searchByName() {
        logInfo("Step 2: Click within search field and try to search for an existing client (by first name)");
        String firstNameLetters = client.getFirstName().substring(0, 3);
        Pages.clientsSearchPage().typeToClientsSearchInputField(firstNameLetters);

        int lookupResultsCount = Pages.clientsSearchPage().getLookupResultsCount();
        Assert.assertEquals(lookupResultsCount, 8);
        assertTrue(Pages.clientsSearchPage().isLoadMoreResultsButtonVisible());

        Pages.clientsSearchPage().getAllLookupResults()
                .stream().forEach(c -> assertTrue(c.contains(firstNameLetters)));

        logInfo("Step 3: Click [Search] button");
        Pages.clientsSearchPage().clickOnSearchButton();

        int searchResults = Pages.clientsSearchResultsPage().getSearchResultsCount();

        Assert.assertEquals(searchResults, 10);

        Pages.clientsSearchResultsPage().getListOfClientsName()
                .stream().forEach(c -> assertTrue(c.contains(firstNameLetters)));

        if (searchResults > 8)
            assertTrue(Pages.clientsSearchResultsPage().isLoadMoreResultsButtonVisible());

        logInfo("Step 4: Clear the data from the field and try to search for an existing client (by last name)");
        Pages.clientsSearchPage().clickOnSearchInputFieldClearButton();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String lastNameLetters = client.getLastName().substring(0, 3);
        Pages.clientsSearchPage().typeToClientsSearchInputField(lastNameLetters);

        lookupResultsCount = Pages.clientsSearchPage().getLookupResultsCount();
        Assert.assertEquals(lookupResultsCount, 8);
        assertTrue(Pages.clientsSearchPage().isLoadMoreResultsButtonVisible());

        Pages.clientsSearchPage().getAllLookupResults()
                .stream().forEach(c -> assertTrue(c.contains(lastNameLetters)));

        Pages.clientsSearchPage().clickOnSearchInputFieldClearButton();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Pages.clientsSearchPage().typeToClientsSearchInputField(client.getFirstName() + " " + client.getLastName());

        Pages.clientsSearchPage().getAllLookupResults()
                .stream().forEach(c -> assertTrue(c.contains(client.getFirstName()) &&
                c.contains(client.getLastName())));

    }
}
