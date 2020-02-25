package com.nymbus.frontoffice.boxaccountsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.base.BaseTest;
import com.nymbus.models.account.Account;
import com.nymbus.models.client.Client;
import com.nymbus.pages.Pages;
import com.nymbus.util.Constants;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Feature("Box Accounts Management")
@Owner("Dmytro")
public class C15042_ViewNewSafeBoxAccount extends BaseTest {

    private Client client;
    private Account safeDepositBoxAccount;

    @BeforeMethod
    public void preConditions() {
        client = new Client().setDefaultClientData();
        safeDepositBoxAccount = new Account().setSafeDepositBoxData();
    }

    @Test(description = "C15042, View new safe box account")
    @Severity(SeverityLevel.CRITICAL)
    public void viewNewSafeBoxAccount() {

        LOG.info("Step 1: Log in to Nymbus");
        navigateToUrl(Constants.URL);
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        ClientsActions.createClient().createClient(client);
        AccountActions.createAccount().createSafeDepositBoxAccount(safeDepositBoxAccount);

        final String clientID = Pages.clientDetailsPage().getClientID();

        LOG.info("Step 2: Go to Clients screen and search for client from preconditions");
        Pages.aSideMenuPage().clickClientMenuItem();
        Pages.clientsPage().typeToClientsSearchInputField(clientID);
        Assert.assertTrue(Pages.clientsPage().getAllLookupResults().size() == 1, "There is more than one client found");
        Assert.assertTrue(Pages.clientsPage().isSearchResultsRelative(Pages.clientsPage().getAllLookupResults(), clientID));

        Pages.clientsPage().clickOnSearchButton();

        LOG.info("Step 3: Open the account");
        Pages.clientsSearchResultsPage().clickTheExactlyMatchedClientInSearchResults();
        Pages.clientDetailsPage().waitForIndividualInformationLoaded();
        Pages.clientDetailsPage().clickAccountsTab();

        // open the account

        LOG.info("Step 4: Verify the displayed fields in view mode");
        // All the account fields are displayed showing correct values


    }

}
