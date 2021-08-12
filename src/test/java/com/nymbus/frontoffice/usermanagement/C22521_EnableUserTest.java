package com.nymbus.frontoffice.usermanagement;

import com.codeborne.selenide.Selenide;
import com.nymbus.actions.Actions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.data.entity.User;
import com.nymbus.pages.Pages;
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
public class C22521_EnableUserTest extends BaseTest {

    private User user;

    @BeforeMethod
    public void prepareUserData() {
        user = new User().setDefaultUserData();


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
    }

    private final String TEST_RUN_NAME = "User Management";

    @TestRailIssue(issueID = 22521, testRunName = TEST_RUN_NAME)
    @Test(description = "C22521, Enable User")
    public void verifyUserEnabling() {

        logInfo("Step 1: Log in to the system as the User from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Go to Settings and click [View All] button in Users widget");
        logInfo("Step 3: Search for any User with 'Login Disabled'=YES' and 'Is Active'=Yes, open its profile in Edit mode");
        Actions.usersActions().searUserOnCustomerSearchPage(user);

        logInfo("Step 4: Switch the 'Login Disabled' switcher to NO and click [Save Changes] button");
        user.setIsLoginDisabledFlag(false);
        Actions.usersActions().editUser(user);

        logInfo("Step 5: Perform log out from the users sub-menu in the header at the top-right corner of the screen");
        Actions.loginActions().doLogOut();

        logInfo("Step 6: Try to log in to the system as the User from the Step3");
        Actions.loginActions().doLogin(user.getLoginID(), user.getPassword());
    }
}