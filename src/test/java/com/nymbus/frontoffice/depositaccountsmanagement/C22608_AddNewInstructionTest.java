package com.nymbus.frontoffice.depositaccountsmanagement;

import com.codeborne.selenide.Selenide;
import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.data.entity.User;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.pages.Pages;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class C22608_AddNewInstructionTest extends BaseTest {

    private IndividualClient client;
    private User user;
    private Account chkAccount;

    @BeforeMethod
    public void preCondition() {

        // Set up a user
        user = new User().setDefaultUserData();

        // Set up a client
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        // Set up account
        chkAccount = new Account().setCHKAccountData();

        // Create user and set him a password
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        Actions.usersActions().createUser(user);
        Actions.loginActions().doLogOut();
        Selenide.open(Constants.WEB_ADMIN_URL);
        WebAdminActions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        WebAdminActions.webAdminUsersActions().setUserPassword(user);
        WebAdminActions.loginActions().doLogout();

        // Create a client
        Selenide.open(Constants.URL);
        Actions.loginActions().doLogin(user.getLoginID(), user.getPassword());
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create CHK account
        AccountActions.createAccount().createCHKAccount(chkAccount);
        Actions.loginActions().doLogOut();

    }

    @Test(description = "C22608, Add new instruction")
    @Severity(SeverityLevel.CRITICAL)
    public void addNewInstruction() {

        logInfo("Step 1: Log in to the system as User from the preconditions");
        Selenide.open(Constants.URL);
        Actions.loginActions().doLogin(user.getLoginID(), user.getPassword());

        logInfo("Step 2: Search for account from the precondition and open it on Notes tab");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(chkAccount);
        Pages.accountNavigationPage().clickInstructionsTab();

        logInfo("Step 3: Click [New Instruction] button");
        Pages.accountInstructionsPage().clickNewInstructionButton();

        logInfo("Step 4: Select any Instruction Type (e.g. Activity Hold) and fill in all fields:\n" +
                "- Notes text field - any alphanumeric value\n" +
                "- Expiration Date > Current date");
        // TODO: Implement step

        logInfo("Step 5: Refresh the page and pay attention to the colored bar in the header");
        // TODO: Implement step

        logInfo("Step 6: Go to Account Maintenance-> Maintenance History page");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

        logInfo("Step 7: Look through the Maintenance History records and check that records about the newly created Instruction are present in the list");
        // TODO: Implement verification at Maintenance History page
    }
}
