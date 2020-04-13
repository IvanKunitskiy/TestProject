package com.nymbus.frontoffice.depositaccountsmanagement;

import com.codeborne.selenide.Selenide;
import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.models.client.Client;
import com.nymbus.newmodels.account.Account;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Deposit Accounts Management")
@Owner("Dmytro")
public class C22598_CloseAccountWithNoMonetaryTransactionTest extends BaseTest {

    private Client client;
    private Account checkingAccount;

    @BeforeMethod
    public void preCondition() {

        // Set up Client
        client = new Client().setDefaultClientData();
        client.setClientStatus("Member");
        client.setClientType("Individual");

        checkingAccount = new Account().setCHKAccountData();
        checkingAccount.setBankBranch("Inspire - Langhorne"); // Branch of the 'autotest autotest' user

        // Login to the system and create a client
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.createClient().createClient(client);
        AccountActions.createAccount().createCHKAccount(checkingAccount);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22598, Close Account with no monetary transaction")
    @Severity(SeverityLevel.CRITICAL)
    public void closeAccountWithNoMonetaryTransactionTest() {

        logInfo("Step 1: Log in to the system as the User from the precondition");
        Selenide.open(Constants.URL);
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for the account from the precondition (e.g. CHK account) and open it on Details");
        Pages.clientsSearchPage().typeToClientsSearchInputField(checkingAccount.getAccountNumber());
        Assert.assertTrue(Pages.clientsSearchPage().getAllLookupResults().size() == 1, "There is more than one client found");
        Assert.assertTrue(Pages.clientsSearchPage().isSearchResultsRelative(Pages.clientsSearchPage().getAllLookupResults(), checkingAccount.getAccountNumber()));
        Pages.clientsSearchPage().clickOnSearchButton();
        Pages.clientsSearchResultsPage().clickTheExactlyMatchedClientInSearchResults();
        Pages.clientDetailsPage().waitForPageLoaded();
        Pages.clientDetailsPage().clickAccountsTab();
        Pages.clientDetailsPage().openAccountByNumber(checkingAccount.getAccountNumber());

        logInfo("Step 3: Pay attention to the [Close Account] button");
        Assert.assertTrue(Pages.accountDetailsPage().isCloseAccountButtonVisible(), "'Close Account' button is not visible");

        logInfo("Step 4: Click [Close Account] button");
        Pages.accountDetailsPage().clickCloseAccountButton();
        Assert.assertTrue(Pages.accountDetailsPage().isAccountClosedNotificationVisible(), "'Account closed' notification is not visible");
        Assert.assertTrue(Pages.accountDetailsPage().isReOpenButtonVisible(), "'Re-Open' button is not visible");

        logInfo("Step 5: Go to Transactions tab and pay attention to the transaction list");
        Pages.accountNavigationPage().clickTransactionsTab();
        Assert.assertTrue(Pages.transactionsPage().isWithdrawAndCloseTransactionsVisible(), "Withdraw & Close transaction is not displayed on history page");

        logInfo("Step 6: Go to Account Maintenance-> Maintenance History page and check that there are records about the changing status to 'Closed' and filling in Date Closed field");
        Pages.accountNavigationPage().clickAccountsInBreadCrumbs();
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

        // TODO: Implement verification at Maintenance History page
    }
}
