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

import java.util.List;

@Epic("Frontoffice")
@Feature("Clients search")
@Owner("Dmytro")
public class C22529_SearchClientByIDTest extends BaseTest {
    private IndividualClient client;
    private String clientId;

     @BeforeMethod
    public void preConditions() {
        // Set up Client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        // Login to the system and create a client
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        clientId = Pages.clientDetailsPage().getClientID();
        Actions.loginActions().doLogOut();
    }

    private final String TEST_RUN_NAME = "Clients search";

    @TestRailIssue(issueID = 22529, testRunName = TEST_RUN_NAME)
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "C22529, Search client by ID")
    public void searchClientByID() {
        logInfo("Step 1: Log in to the system as the User from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Click within search field and try to search for an existing client (by client id)");
        Pages.clientsSearchPage().typeToClientsSearchInputField(clientId);
        List<String> lookupResults = Pages.clientsSearchPage().getAllLookupResults();
        Assert.assertTrue(Pages.clientsSearchPage().isSearchResultsRelative(lookupResults, clientId));

        logInfo("Step 3: Click the 'Search' button");
        Pages.clientsSearchPage().clickOnSearchButton();
        Pages.clientsSearchResultsPage().waitForSearchResults();

        Assert.assertEquals(Pages.clientsSearchResultsPage().getClientFirstNameFromResultByIndex(1), client.getIndividualType().getFirstName(), "First name is not relevant to the client");
        Assert.assertEquals(Pages.clientsSearchResultsPage().getClientLastNameFromResultByIndex(1), client.getIndividualType().getLastName(), "Last name is not relevant to the client");
        Assert.assertEquals(Pages.clientsSearchResultsPage().getClientIDFromResultByIndex(1), clientId, "Client id is not relevant to the client");
        Assert.assertEquals(Pages.clientsSearchResultsPage().getClientTypeFromResultByIndex(1), client.getIndividualType().getClientType().getClientType(), "Client type is not relevant to the client");
        Assert.assertEquals(Pages.clientsSearchResultsPage().getClientAddressFromResultByIndex(1), client.getIndividualType().getAddresses().get(0).getAddress(), "Client address is not relevant to the client");
        Assert.assertEquals(Pages.clientsSearchResultsPage().getClientAKAFromResultByIndex(1), client.getIndividualClientDetails().getAKAs().get(0), "Client AKA is not relevant to the client");
    }
}