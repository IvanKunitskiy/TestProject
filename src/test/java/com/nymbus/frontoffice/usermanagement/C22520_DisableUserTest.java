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
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("User Management")
@Owner("Petro")
public class C22520_DisableUserTest extends BaseTest {

    private User user;

    @BeforeMethod
    public void precondition(){
        user = new User().setDefaultUserData();

        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        Actions.usersActions().createUser(user);

        Assert.assertEquals(Actions.usersActions().getUserFromUserViewPage(), user,
                "User's data isn't the same");

        Actions.loginActions().doLogOut();

        Selenide.open(Constants.WEB_ADMIN_URL);
        WebAdminActions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        WebAdminActions.webAdminUsersActions().setUserPassword(user);

        Selenide.open(Constants.URL);

        Actions.loginActions().doLogin(user.getLoginID(), user.getPassword());
        Actions.loginActions().doLogOut();
    }

    private final String TEST_RUN_NAME = "User Management";

    @TestRailIssue(issueID = 22520, testRunName = TEST_RUN_NAME)
    @Test(description = "C22520, Disable User")
    public void verifyUserDisabling() {

        logInfo("Step 1: Log in to the system as the User from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Go to Settings and search for any User in 'Users' widget, open its profile in Edit mode");
        Actions.usersActions().searchUser(user);

        SettingsPage.usersSearchPage().clickCellByUserData(user.getEmail());

        logInfo("Step 3: Switch the 'Login Disabled' switcher to YES and click [Save Changes] button");
        user.setIsLoginDisabledFlag(true);

        Actions.usersActions().editUser(user);

        Assert.assertEquals(Actions.usersActions().getUserFromUserViewPage(), user,
                "User's data isn't the same");

        logInfo("Step 4: Perform log out from the users sub-menu in the header at the top-right corner of the screen");
        Actions.loginActions().doLogOut();

        logInfo("Step 5: Try to log in to the system as the User from the Step2");
        Pages.loginPage().waitForLoginForm();
        Pages.loginPage().typeUserName(user.getLoginID());
        Pages.loginPage().typePassword(user.getPassword());
        Pages.loginPage().clickEnterButton();

        Assert.assertEquals(Pages.loginPage().getErrorMessage(),
                "The user account has been disabled. Please contact your System Administrator to restore access.",
                "Expected message is not visible");

        Assert.assertTrue(Pages.loginPage().isUserNameFieldHasError(),
                "Error messages for user name field is not visible");
        Assert.assertTrue(Pages.loginPage().isPasswordFieldHasError(),
                "Error messages for password field is not visible");
    }
}