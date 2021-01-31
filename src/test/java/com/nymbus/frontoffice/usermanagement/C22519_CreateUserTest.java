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
public class C22519_CreateUserTest extends BaseTest {

    private User user;

    @BeforeMethod
    public void prepareUserData() {
        user = new User().setDefaultUserData();
    }

    private final String TEST_RUN_NAME = "User Management";

    @TestRailIssue(issueID = 22519, testRunName = TEST_RUN_NAME)
    @Test(description = "C22519, Create User + Forgot Password + Log In")
    public void verifyUserCreation() {

        logInfo("Step 1: Log in to the system as the User from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: In the left-hand menu select Settings and click [Add new] button in 'Users' widget");
        logInfo("Step 3: Fill in all the required fields:");
        logInfo("Step 4: Fill in all other fields with any data and click [Save Changes] button\n" +
                "NOTE: Login Disabled should be set to NO, Is Active = YES");
        Actions.usersActions().createUser(user);

        logInfo("Step 5: Look through the fields and verify that all previously entered data is kept in the profile");
        Assert.assertEquals(Actions.usersActions().getUserFromUserViewPage(), user,
                "User's data isn't the same");

        logInfo("Step 6: Perform log out from the users sub-menu in the header at the top-right corner of the screen");
        Actions.loginActions().doLogOut();

        logInfo("Step 7: Click [Trouble Logging In?] button and fill in 'Email' field with an Email that was assigned to the newly created user,\n" +
                "fill in CAPTCHA field and click [Send me a new password] button");
        logInfo("Step 8: Open the inbox of the appropriate Email and click the link inside the received email with Account password reset request");
        logInfo("Step 9: Insert valid password (based on the security settings from the WebAdmin)\n" +
                "e.g. length 8char., password contains Digit, Special Char., Upper case and Lower case char.\n" +
                "confirm it by inserting the same password again and click [Enter] button");
        Selenide.open(Constants.WEB_ADMIN_URL);
        WebAdminActions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        WebAdminActions.webAdminUsersActions().setUserPassword(user);


        logInfo("Step 10: Try to login using the new password and the Login ID from Step3");
        Selenide.open(Constants.URL);
        Actions.loginActions().doLogin(user.getLoginID(), user.getPassword());
        Pages.navigationPage().waitForUserMenuVisible();
        Pages.navigationPage().clickAccountButton();
        Assert.assertTrue(Pages.navigationPage().getUserFullName().contains(user.getFirstName()),
                "User's first name is not visible. Found: " + Pages.navigationPage().getUserFullName());
        Assert.assertTrue(Pages.navigationPage().getUserFullName().contains(user.getLastName()),
                "User's last name is not visible. Found: " + Pages.navigationPage().getUserFullName());
        Pages.navigationPage().clickViewMyProfileLink();
        Assert.assertEquals(Actions.usersActions().getUserFromUserViewPage(), user,
                "User's data isn't the same");
    }
}