package com.nymbus.frontoffice.clientsearch;

import com.nymbus.actions.Actions;
import com.nymbus.base.BaseTest;
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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Epic("Frontoffice")
@Feature("Clients search")
@Owner("Dmytro")
public class C22528_SearchByDebitCard extends BaseTest {
    private Client client;

    @BeforeMethod
    public void preCondition() {
        client = new Client();
        client.setCardNumber("4133441334495808");

        navigateToUrl(Constants.URL);

        LOG.info("Step 1: Log in to the system as the User from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        Pages.navigationPage().waitForUserMenuVisible();
    }

    @Test(description = "C22528, Search client by card number")
    public void searchByCardNumber() {
        String cardNumber = client.getCardNumber();
        String lastFourNumbers = cardNumber.substring(cardNumber.length()-4);
        String hiddenNumber = "XXXX-XXXX-XXXX-" + lastFourNumbers;

        LOG.info("Step 2: Click within search field and try to search for an existing Debit Card");
        Pages.clientsPage().typeToClientsSearchInputField(lastFourNumbers);

        int lookupResultsCount = Pages.clientsPage().getLookupResultsCount();
        Assert.assertTrue(lookupResultsCount <= 8);
        if (lookupResultsCount == 8)
            assertTrue(Pages.clientsPage().isLoadMoreResultsButtonVisible());

        LOG.info("Step 3: Pay attention to the display of the Debit Card in the quick search field");
        List<String> clients = Pages.clientsPage().getAllLookupResults();
        clients.stream()
                .filter(s -> s.contains("XXXX-XXXX-XXXX-"))
                .forEach(s -> Assert.assertEquals(s, hiddenNumber));

        LOG.info("Step 4: Clear the data from the field and try to search for an existing client");
        Pages.clientsPage().clickOnSearchInputFieldClearButton();
        Pages.clientsPage().typeToClientsSearchInputField(cardNumber);

        clients = Pages.clientsPage().getAllLookupResults();
        assertEquals(clients.get(0), hiddenNumber);
    }
}
