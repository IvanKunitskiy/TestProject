package com.nymbus.frontoffice.clientsearch;

import com.nymbus.actions.Actions;
import com.nymbus.base.BaseTest;
import com.nymbus.models.client.Client;
import com.nymbus.pages.Pages;
import com.nymbus.util.Constants;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

@Epic("Frontoffice")
@Feature("Clients search")
@Owner("Dmytro")
public class C22533_SearchByLastFourOfDebitCard extends BaseTest {

    private Client client;

    @BeforeMethod
    public void preCondition() {
        client = new Client();
        client.setCardNumber("4133441334495808");
    }

    @Test(description = "C22533, Search by last four of debit card number")
    @Severity(SeverityLevel.CRITICAL)
    public void searchByCardNumber() {

        LOG.info("Step 1: Log in to the system as the User from the precondition");
        navigateToUrl(Constants.URL);
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        Pages.navigationPage().waitForUserMenuVisible();

        String cardNumber = client.getCardNumber();
        String lastFourNumbers = cardNumber.substring(cardNumber.length() - 4);
        String hiddenNumber = "XXXX-XXXX-XXXX-" + lastFourNumbers;

        LOG.info("Step 2: Click within search field and try to search for an existing Debit Card (by last 4 digits)");
        Pages.clientsPage().typeToClientsSearchInputField(lastFourNumbers);
        Assert.assertTrue(Pages.clientsPage().getAllLookupResults().size() > 0, "There are no relevant lookup results");

        if (Pages.clientsPage().getAllLookupResults().size() == 8) {
            Assert.assertTrue(Pages.clientsPage().isLoadMoreResultsButtonVisible(), "'Load more results' button is not visible in search lookup list");
        }

        LOG.info("Step 3: Pay attention to the display of the Debit Card in the quick search field");
        List<String> clients = Pages.clientsPage().getAllLookupResults();
        clients.stream()
                .filter(s -> s.contains("XXXX-XXXX-XXXX-"))
                .forEach(s -> Assert.assertEquals(s, hiddenNumber));
    }
}
