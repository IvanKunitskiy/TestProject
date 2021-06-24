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
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Clients search")
@Owner("Dmytro")
public class C22531_SearchByLastFourDigitsOfTaxIDTest extends BaseTest {
    private IndividualClient client;
    private String clientId;

    @BeforeMethod
    public void preConditions() {
        // Set up Client and Account
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        // Login to the system and create a client
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        Pages.clientDetailsPage().waitForPageLoaded();
        clientId = Pages.clientDetailsPage().getClientID();

        Actions.loginActions().doLogOut();
    }

    private final String TEST_RUN_NAME = "Clients search";

    @TestRailIssue(issueID = 22531, testRunName = TEST_RUN_NAME)
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "C22531, Search by last four digits of TaxID")
    public void searchByTaxID() {
        logInfo("Step 1: Log in to the system as the User from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Click within search field and try to search for an existing client (by 4 last digits of tax id number)");
        final String taxIDQuery = client.getIndividualType().getTaxID().substring(5);
        Pages.clientsSearchPage().typeToClientsSearchInputField(taxIDQuery);
        Assert.assertTrue(Pages.clientsSearchPage().getAllLookupResults().size() > 0, "There are no relevant lookup results");
        if (Pages.clientsSearchPage().getLookupResultOptionsCount() == 9) {
            Assert.assertTrue(Pages.clientsSearchPage().isLoadMoreResultsButtonVisible(), "'Load more results' button is not visible in search lookup list");
        }
        Assert.assertTrue(Pages.clientsSearchPage().isSearchResultsRelative(Pages.clientsSearchPage().getAllLookupResults(), taxIDQuery), "Search results are not relevant");

        logInfo("Step 3: Click the 'Search' button");
        Pages.clientsSearchPage().clickOnSearchButton();
        Assert.assertTrue(Pages.clientsSearchResultsPage().getClientIDsFromSearchResults().contains(clientId), "Client not found in search results by 'Client ID' criteria");
    }
}