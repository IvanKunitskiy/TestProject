package com.nymbus.frontoffice.usermanagement;

import com.codeborne.selenide.Selenide;
import com.nymbus.actions.Actions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.data.entity.User;
import com.nymbus.pages.Pages;
import com.nymbus.pages.settings.SettingsPage;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("User Management")
@Owner("Petro")
public class C22521_EnableUserTest extends BaseTest {

    private User user;

    @BeforeMethod
    public void prepareUserData() {
        user = new User().setDefaultUserData();
    }

    private final String TEST_RUN_NAME = "User Management";

    @TestRailIssue(issueID = 22521, testRunName = TEST_RUN_NAME)
    @Test(description = "C22521, Enable User")
    public void verifyUserEnabling() {

        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        Pages.aSideMenuPage().clickSettingsMenuItem();
        Actions.usersActions().createUser(user);
        user.setIsLoginDisabledFlag(true);
        Actions.usersActions().editUser(user);

        Assert.assertEquals(Actions.usersActions().getUserFromUserViewPage(), user,
                "User's data isn't the same");

        Actions.loginActions().doLogOut();

        Selenide.open(Constants.WEB_ADMIN_URL);
        WebAdminActions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        WebAdminActions.webAdminUsersActions().setUserPassword(user);

        Selenide.open(Constants.URL);

        Pages.loginPage().waitForLoginForm();
        Pages.loginPage().typeUserName(user.getLoginID());
        Pages.loginPage().typePassword(user.getPassword());
        Pages.loginPage().clickEnterButton();

        Assert.assertEquals(Pages.loginPage().getErrorMessage(),
                "The user account has been disabled. Please contact your System Administrator to restore access.",
                "Expected message is not visible");
        Pages.loginPage().waitForErrorMessageDisappear();

        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        Actions.usersActions().searUserOnCustomerSearchPage(user);

        user.setIsLoginDisabledFlag(false);
        Actions.usersActions().editUser(user);
        Actions.loginActions().doLogOut();

        Actions.loginActions().doLogin(user.getLoginID(), user.getPassword());
    }
}