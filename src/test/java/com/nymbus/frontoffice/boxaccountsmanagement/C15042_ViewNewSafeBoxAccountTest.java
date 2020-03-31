package com.nymbus.frontoffice.boxaccountsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.models.client.Client;
import com.nymbus.newmodels.account.Account;
import com.nymbus.pages.Pages;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Feature("Box Accounts Management")
@Owner("Dmytro")
public class C15042_ViewNewSafeBoxAccountTest extends BaseTest {
    private Client client;
    private Account safeDepositBoxAccount;

    @BeforeMethod
    public void preConditions() {
        client = new Client().setDefaultClientData();
        client.setClientStatus("Member");
        safeDepositBoxAccount = new Account().setSafeDepositBoxData();
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "C15042, View new safe box account", enabled = false)
    public void viewNewSafeBoxAccount() {
        logInfo("Step 1: Log in to Nymbus");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        ClientsActions.createClient().createClient(client);
        final String clientID = Pages.clientDetailsPage().getClientID();
        AccountActions.createAccount().createSafeDepositBoxAccount(safeDepositBoxAccount);

        logInfo("Step 2: Go to Clients screen and search for client from preconditions");
        Pages.aSideMenuPage().clickClientMenuItem();
        Pages.clientsSearchPage().typeToClientsSearchInputField(clientID);

        Assert.assertTrue(Pages.clientsSearchPage().getAllLookupResults().size() == 1, "There is more than one client found");
        Assert.assertTrue(Pages.clientsSearchPage().isSearchResultsRelative(Pages.clientsSearchPage().getAllLookupResults(), clientID));

        Pages.clientsSearchPage().clickOnSearchButton();

        logInfo("Step 3: Open the account");
        Pages.clientsSearchResultsPage().clickTheExactlyMatchedClientInSearchResults();
        Pages.clientDetailsPage().waitForPageLoaded();
        Pages.clientDetailsPage().clickAccountsTab();
        Pages.clientDetailsPage().openAccountByNumber(safeDepositBoxAccount.getAccountNumber());

        logInfo("Step 4: Verify the displayed fields in view mode");
        Assert.assertEquals(safeDepositBoxAccount.getProductType(), Pages.accountDetailsPage().getProductTypeValue(), "Product type is not relevant");
        Assert.assertEquals(safeDepositBoxAccount.getBoxSize(), Pages.accountDetailsPage().getBoxSizeValue(), "Box size is not relevant");
        Assert.assertEquals(safeDepositBoxAccount.getAccountNumber(), Pages.accountDetailsPage().getAccountNumberValue(), "Account number is not relevant");
        Assert.assertEquals(safeDepositBoxAccount.getAccountTitle(), Pages.accountDetailsPage().getAccountTitleValue(), "Account title is not relevant");
        Assert.assertEquals(safeDepositBoxAccount.getBankBranch(), Pages.accountDetailsPage().getBankBranchValue(), "Bank branch is not relevant");

        // TODO: Wait for initial test case update and change respectively
    }
}