package com.nymbus.frontoffice.clientsearch;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
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
    private Account savingsAccount;

    @BeforeMethod
    public void preCondition() {
        // Set up Client and Account
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();
        savingsAccount = new Account().setSavingsAccountData();

        // Login to the system and create a client
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        Pages.clientDetailsPage().waitForPageLoaded();

        // Create Saving account
        AccountActions.createAccount().createSavingsAccount(savingsAccount);
        Actions.loginActions().doLogOut();
    }

    @Severity(CRITICAL)
    @Test(description = "C22527, Search individualClient by number")
    public void searchByNumber() {
        logInfo("Step 1: Log in to the system as the User from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        Pages.navigationPage().waitForUserMenuVisible();

        logInfo("Step 2: Click within search field and try to search for an existing client (by first name)");
        String accountNumber = savingsAccount.getAccountNumber();
        String lastFourNumbers = accountNumber.substring(accountNumber.length() - 4);
        Pages.clientsSearchPage().typeToClientsSearchInputField(lastFourNumbers);
        int lookupResultsCount = Pages.clientsSearchPage().getLookupResultsCount();
        Assert.assertEquals(lookupResultsCount, 8, "Lookup results is incorrect!");
        assertTrue(Pages.clientsSearchPage().isLoadMoreResultsButtonVisible(), "Load more results button is not visible!");

        List<String> clients = Pages.clientsSearchPage().getAllLookupResults();
        verifyResultsList(clients, lastFourNumbers);

        logInfo("Step 3: Click [Search] button");
        Pages.clientsSearchPage().clickOnSearchButton();
        int searchResults = Pages.clientsSearchResultsPage().getAccountNumbersFromSearchResults().size();
        assertTrue(searchResults <= 10, "Search result count is more then 10!");
        if (searchResults == 10)
            assertTrue(Pages.clientsSearchResultsPage().isLoadMoreResultsButtonVisible(), "Load more button is not visible!");

        clients = Pages.clientsSearchResultsPage().getAccountNumbersFromSearchResults();
        verifyResultsList(clients, lastFourNumbers);

        logInfo("Step 4: Clear the data from the field and try to search for an existing client (by last name)");
        Pages.clientsSearchPage().clickOnSearchInputFieldClearButton();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Pages.clientsSearchPage().typeToClientsSearchInputField(accountNumber);
        lookupResultsCount = Pages.clientsSearchPage().getLookupResultsCount();
        Assert.assertEquals(lookupResultsCount, 1);
        Assert.assertFalse(Pages.clientsSearchPage().isLoadMoreResultsButtonVisible());

        clients = Pages.clientsSearchPage().getAllLookupResults();
        Assert.assertEquals(clients.get(0), savingsAccount.getAccountNumber());

        logInfo("Step 5: Click [Search] button and pay attention to the search results list");
        // TODO: Need to implement assertion for exist Client object
    }

    private void verifyResultsList(List<String> clients, String number) {
        for (int i = 0; i < clients.size(); i++) {
            String client = clients.get(i);
            assertTrue(client.substring(client.length() - 4).contains(number),
                    "Search result " +i + "account number isn't relevant!");
        }
    }
}