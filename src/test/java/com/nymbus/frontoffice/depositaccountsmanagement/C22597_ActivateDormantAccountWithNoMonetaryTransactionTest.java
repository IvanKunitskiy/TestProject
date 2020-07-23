package com.nymbus.frontoffice.depositaccountsmanagement;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Deposit Accounts Management")
@Owner("Petro")
public class C22597_ActivateDormantAccountWithNoMonetaryTransactionTest extends BaseTest {

    private IndividualClient client;
    private Account chkAccount;
    private String systemDate;

    @BeforeMethod
    public void preCondition() {

        // Set up client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        // Set up account
        chkAccount = new Account().setCHKAccountData();

        // Get system date
        systemDate = WebAdminActions.loginActions().getSystemDate();

        // Create a client
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create CHK account
        AccountActions.createAccount().createCHKAccountForDormantPurpose(chkAccount);
        String[] url = WebDriverRunner.url().split("/");
        String rootID = url[url.length - 2];
        Actions.loginActions().doLogOut();

        // Set account as 'Dormant'
        Selenide.open(Constants.WEB_ADMIN_URL);
        WebAdminActions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        WebAdminActions.webAdminUsersActions().setDormantAccount(rootID, chkAccount);
        WebAdminActions.loginActions().doLogoutProgrammatically();
    }

    @Test(description = "C22597, Activate Dormant Account with no monetary transaction")
    @Severity(SeverityLevel.CRITICAL)
    public void activateDormantAccountWithNoMonetaryTransaction() {

        logInfo("Step 1: Log in to the system as User from the preconditions");
        Selenide.open(Constants.URL);
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for the CHK account from the precondition and open it on Details");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(chkAccount);
        Assert.assertTrue(Pages.accountDetailsPage().isActivateButtonVisible(), "'Activate' button not visible");

        logInfo("Step 3: Click [Activate] button and pay attention to the Account Status");
        AccountActions.editAccount().activateAccount();
        Assert.assertEquals(Pages.accountDetailsPage().getAccountStatus(), "Active", "Account status is not 'Active'");

        logInfo("Step 4: Pay attention to the available buttons on the page");
        Assert.assertTrue(Pages.accountDetailsPage().isEditButtonVisible(), "'Edit' button is not visible");

        logInfo("Step 5: Pay attention to the 'Date Last Activity/Contact' field");
        Assert.assertEquals(Pages.accountDetailsPage().getDateLastActivityValue(), systemDate);

        logInfo("Step 6: Go to Account Maintenance-> Maintenance History page and check that there are records about changing Account status to 'Active' and updating the 'Date Last Activity/Contact' field");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();
        AccountActions.accountMaintenanceActions().expandAllRows();
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Date Last Activity/Contact") >= 1,
                "'Date Last Activity/Contact' row count is incorrect!");
    }
}
