package com.nymbus.frontoffice.usermanagement;

import com.nymbus.actions.Actions;
import com.nymbus.pages.Pages;
import com.nymbus.pages.settings.SettingsPage;
import com.nymbus.pages.webadmin.WebAdminPages;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.base.BaseTest;
import com.nymbus.models.User;
import com.nymbus.util.Constants;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("User Management")
public class C22519_CreateUserTest extends BaseTest {

    private User user;

    @BeforeMethod
    public void prepareUserData(){
        user = new User().setDefaultUserData();
    }

    @Test(description = "C22519, Create User + Forgot Password + Log In")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyUserCreation() {
        navigateToUrl(Constants.URL);

        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        Pages.aSideMenuPage().clickSettingsMenuItem();
        Actions.usersActions().createUser(user);

        SettingsPage.viewUserPage().waitViewUserDataVisible();

        Assert.assertEquals(Actions.usersActions().getUserFromUserViewPage(), user,
                "User's data isn't the same");

        Actions.loginActions().doLogOut();

        navigateToUrl(Constants.WEB_ADMIN_URL);
        WebAdminActions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        WebAdminActions.webAdminUsersActions().setUserPassword(user);

        navigateToUrl(Constants.URL);

        Actions.loginActions().doLogin(user.getLoginID(), user.getPassword());

        Assert.assertFalse(Pages.loginPage().isErrorsVisibleOnLoginForm(),
                "Error messages is visible");

        Pages.clientsPage().waitForAddNewClientButton();

        Pages.aSideMenuPage().waitForASideMenu();
        Assert.assertTrue(Pages.aSideMenuPage().isClientPageOpened(),
                "Client page is not opened");

        Pages.navigationPage().waitForUserMenuVisible();
        Pages.navigationPage().clickAccountButton();

        Assert.assertTrue(Pages.navigationPage().getUserFullName().contains(user.getFirstName()),
                "User's first name is not visible. Found: " + Pages.navigationPage().getUserFullName());

        Assert.assertTrue(Pages.navigationPage().getUserFullName().contains(user.getLastName()),
                "User's last name is not visible. Found: " + Pages.navigationPage().getUserFullName());

        //TODO Title issue in case setting password throw web admin

//        Pages.navigationPage().clickViewMyProfileLink();
//
//        Assert.assertEquals(Actions.usersActions().getUserFromUserViewPage(), user,
//                "User's data isn't the same");
    }
}
