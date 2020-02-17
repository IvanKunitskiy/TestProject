package com.nymbus.frontoffice.clientsearch;

import com.nymbus.actions.Actions;
import com.nymbus.actions.client.ClientsActions;
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
@Feature("User Managenment")
@Severity(SeverityLevel.CRITICAL)
@Owner("Dmytro")
public class C22529_SearchClientByID extends BaseTest {

    private Client client;

    @BeforeMethod
    public void preCondition() {
        client = new Client().setDefaultClientData();
    }

    @Test(description = "C22529, Search client by ID")
    public void searchClientByID() {

        LOG.info("Step 1: Log in to the system as the User from the precondition");
        navigateToUrl(Constants.URL);
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.createClient().createClient(client);

        Pages.clientsPage().clickViewMemberProfileButton();
        final String clientID = Pages.clientsPage().getClientIdAfterCreation();
        Pages.aSideMenuPage().clickClientMenuItem();

        LOG.info("Step 2: Click within search field and try to search for an existing client (by client id)");
        Pages.clientsPage().typeToClientsSearchInputField(clientID);
        List<String> lookupResults = Pages.clientsPage().getAllLookupResults();
        Assert.assertTrue(Pages.clientsPage().isSearchResultsRelative(lookupResults, clientID));

        LOG.info("Step 3: Click the 'Search' button");
        Pages.clientsPage().clickOnSearchButton();
        Pages.clientsPage().waitForSearchResultsTableIsVisible();

        String searchResultsFirstRow = Pages.clientsPage().getClientsFromTable().get(0);
        System.out.println(searchResultsFirstRow);
        System.out.println("---> " + client.getLastName() + " " + client.getFirstName());
        Assert.assertTrue(searchResultsFirstRow.contains(client.getLastName() + " " + client.getFirstName()), "bad name");
        Assert.assertTrue(searchResultsFirstRow.contains(client.getClientID()), "bad id");
        Assert.assertTrue(searchResultsFirstRow.contains(client.getClientType()), "bad type");
//        Assert.assertTrue(searchResultsFirstRow.contains(client.getAddress()));

    }


}
