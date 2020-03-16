package com.nymbus.frontoffice.usermanagement;

import com.codeborne.selenide.Selenide;
import com.nymbus.actions.Actions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.data.entity.User;
import com.nymbus.pages.Pages;
import com.nymbus.pages.settings.SettingsPage;
import io.qameta.allure.*;
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

    @Test(description = "C22519, Create User + Forgot Password + Log In")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyUserCreation() {

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

        Pages.navigationPage().waitForUserMenuVisible();
        Pages.navigationPage().clickAccountButton();

        Assert.assertTrue(Pages.navigationPage().getUserFullName().contains(user.getFirstName()),
                "User's first name is not visible. Found: " + Pages.navigationPage().getUserFullName());

        Assert.assertTrue(Pages.navigationPage().getUserFullName().contains(user.getLastName()),
                "User's last name is not visible. Found: " + Pages.navigationPage().getUserFullName());


        Pages.navigationPage().clickViewMyProfileLink();

        //TODO Title issue in case setting password throw web admin
//        Assert.assertEquals(Actions.usersActions().getUserFromUserViewPage(), user,
//                "User's data isn't the same");
    }
}
