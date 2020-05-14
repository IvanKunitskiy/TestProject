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
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Epic("Frontoffice")
@Feature("Clients search")
@Owner("Dmytro")
public class C22528_SearchByDebitCardTest extends BaseTest {
    private Client client;

    @BeforeMethod
    public void preCondition() {
        client = new Client();
        client.setCardNumber("1425504407949279");

        Selenide.open(Constants.URL);

        logInfo("Step 1: Log in to the system as the User from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        Pages.navigationPage().waitForUserMenuVisible();
    }

    @Severity(CRITICAL)
    @Test(description = "C22528, Search individualClient by debitcard number")
    public void searchByCardNumber() {
        String cardNumber = client.getCardNumber();
        String lastFourNumbers = cardNumber.substring(cardNumber.length() - 4);
        String hiddenNumber = "XXXX-XXXX-XXXX-" + lastFourNumbers;

        logInfo("Step 2: Click within search field and try to search for an existing Debit Card");
        Pages.clientsSearchPage().typeToClientsSearchInputField(lastFourNumbers);

        int lookupResultsCount = Pages.clientsSearchPage().getLookupResultsCount();
        Assert.assertTrue(lookupResultsCount <= 8);
        if (lookupResultsCount == 8)
            assertTrue(Pages.clientsSearchPage().isLoadMoreResultsButtonVisible());

        logInfo("Step 3: Pay attention to the display of the Debit Card in the quick search field");
        List<String> clients = Pages.clientsSearchPage().getAllLookupResults();
        clients.stream()
                .filter(s -> s.contains("XXXX-XXXX-XXXX-"))
                .forEach(s -> Assert.assertEquals(s, hiddenNumber));

        logInfo("Step 4: Clear the data from the field and try to search for an existing client");
        Pages.clientsSearchPage().clickOnSearchInputFieldClearButton();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Pages.clientsSearchPage().typeToClientsSearchInputField(cardNumber);

        clients = Pages.clientsSearchPage().getAllLookupResults();
        assertEquals(clients.get(0), hiddenNumber);
    }
}
