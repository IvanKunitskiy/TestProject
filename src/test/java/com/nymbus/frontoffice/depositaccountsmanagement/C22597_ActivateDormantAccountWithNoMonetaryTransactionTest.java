package com.nymbus.frontoffice.depositaccountsmanagement;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import com.nymbus.models.client.Client;
import com.nymbus.newmodels.account.Account;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Deposit Accounts Management")
@Owner("Petro")
public class C22597_ActivateDormantAccountWithNoMonetaryTransactionTest extends BaseTest {

    private Client client;
    private Account chkAccount;
    private Account savingsAccount;

    @BeforeMethod
    public void preCondition() {
        // Set up client
        client = new Client().setDefaultClientData();
        client.setClientStatus("Member");
        client.setClientType("Individual");

        // Set up account
        chkAccount = new Account().setCHKAccountData();
        savingsAccount = new Account().setSavingsAccountData();

        // Create a client
        Selenide.open(Constants.URL);
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.createClient().createClient(client);

        // Create CHK / Savings account
        AccountActions.createAccount().createCHKAccount(chkAccount);
        String[] url = WebDriverRunner.url().split("/");
        String rootID = url[url.length - 2];
        Actions.loginActions().doLogOut();

        // Set account as 'Dormant'
        Selenide.open(Constants.WEB_ADMIN_URL);
        WebAdminActions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        WebAdminActions.webAdminUsersActions().setDormantAccount(rootID, chkAccount);
        WebAdminActions.loginActions().doLogout();
    }

    @Test(description = "C22597, Activate Dormant Account with no monetary transaction")
    @Severity(SeverityLevel.CRITICAL)
    public void activateDormantAccountWithNoMonetaryTransaction() {

        logInfo("Step 1: Log in to the system as User from the preconditions");
        Selenide.open(Constants.URL);
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for the CHK account from the precondition and open it on Details");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(chkAccount);
        Assert.assertFalse(Pages.accountDetailsPage().isEditButtonVisible(), "'Edit' button is visible");
        Assert.assertTrue(Pages.accountDetailsPage().isActivateButtonVisible(), "'Activate' button not visible");

        logInfo("Step 3: Click [Activate] button and pay attention to the Account Status");
        Pages.accountDetailsPage().clickActivateButton();
        Assert.assertEquals(Pages.accountDetailsPage().getAccountStatus(), "Active", "Account status is not 'Active'");

        logInfo("Step 4: Pay attention to the available buttons on the page");
        Assert.assertTrue(Pages.accountDetailsPage().isEditButtonVisible(), "'Edit' button is not visible");
        Assert.assertFalse(Pages.accountDetailsPage().isActivateButtonVisible(), "'Activate' button is visible");

        logInfo("Step 5: Pay attention to the 'Date Last Activity/Contact' field");
        Assert.assertEquals(Pages.accountDetailsPage().getDateLastActivityValue(), DateTime.getLocalDateTimeByPattern("MMM d, yyyy"));

        logInfo("Step 6: Go to Account Maintenance-> Maintenance History page and check that there are records about changing Account status to 'Active' and updating the 'Date Last Activity/Contact' field");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();
        // TODO: Implement verification at Maintenance History page
    }
}
