package com.nymbus.frontoffice.clientsearch;

import com.nymbus.actions.Actions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.pages.Pages;
import com.nymbus.testrail.TestRailIssue;
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
    private IndividualClient client;

    @BeforeMethod
        public void preCondition() {

        // Set up Client and Account
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        // Login to the system and create a client
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        Pages.clientDetailsPage().waitForPageLoaded();

        Actions.loginActions().doLogOut();
    }

    private final String TEST_RUN_NAME = "Clients search";

    @TestRailIssue(issueID = 22526, testRunName = TEST_RUN_NAME)
    @Severity(CRITICAL)
    @Test(description = "C22526, Search individualClient by name")
    public void searchByName() {

        logInfo("Step 1: Log in to the system as the User from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        Pages.navigationPage().waitForUserMenuVisible();

        logInfo("Step 2: Click within search field and try to search for an existing client by:\n" +
                "- Client First Name (first 3 letters)\n" +
                "Do not click [Search] button");
        String firstNameLetters = client.getIndividualType().getFirstName().substring(0, 3);
        Pages.clientsSearchPage().typeToClientsSearchInputField(firstNameLetters);

        int lookupResultsCount = Pages.clientsSearchPage().getLookupResultOptionsCount();
        Assert.assertTrue(lookupResultsCount > 0);
        if (lookupResultsCount == 9) {
            assertTrue(Pages.clientsSearchPage().isLoadMoreResultsButtonVisible());
        }

        Pages.clientsSearchPage().getAllLookupResults().forEach(c -> assertTrue(c.contains(firstNameLetters)));

        logInfo("Step 3: Click [Search] button");
        Pages.clientsSearchPage().clickOnSearchButton();

        int searchResults = Pages.clientsSearchResultsPage().getSearchResultsCount();

        Assert.assertTrue(searchResults > 0);

        Pages.clientsSearchResultsPage().getListOfClientsName().forEach(c -> assertTrue(c.contains(firstNameLetters)));

        if (searchResults > 8) {
            assertTrue(Pages.clientsSearchResultsPage().isLoadMoreResultsButtonVisible());
        }

        logInfo("Step 4: Clear the data from the field and try to search for an existing client by:\n" +
                "- Client Last Name (first 3 letters)");
        Pages.clientsSearchPage().clickOnSearchInputFieldClearButton();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String lastNameLetters = client.getIndividualType().getLastName().substring(0, 3);
        Pages.clientsSearchPage().typeToClientsSearchInputField(lastNameLetters);

        lookupResultsCount = Pages.clientsSearchPage().getLookupResultOptionsCount();
        Assert.assertTrue(lookupResultsCount > 0);
        if (lookupResultsCount == 9) {
            assertTrue(Pages.clientsSearchPage().isLoadMoreResultsButtonVisible());
        }

        Pages.clientsSearchPage().getAllLookupResults().forEach(c -> assertTrue(c.contains(lastNameLetters)));

        Pages.clientsSearchPage().clickOnSearchInputFieldClearButton();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logInfo("Step 5: Clear the data from the field and try to search for an existing client by:\n" +
                "- Client full name (First Name and Last Name)");
        Pages.clientsSearchPage().typeToClientsSearchInputField(client.getNameForDebitCard());

        Pages.clientsSearchPage().getAllLookupResults().forEach(c -> assertTrue(c.contains(client.getIndividualType().getFirstName()) &&
                c.contains(client.getIndividualType().getLastName())));
    }
}