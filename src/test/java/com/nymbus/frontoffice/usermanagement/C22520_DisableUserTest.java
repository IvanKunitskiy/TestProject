package com.nymbus.frontoffice.usermanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.base.BaseTest;
import com.nymbus.models.User;
import com.nymbus.pages.Pages;
import com.nymbus.pages.settings.SettingsPage;
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
public class C22520_DisableUserTest extends BaseTest {

    private User user;

    @BeforeMethod
    public void prepareUserData(){
        user = new User().setDefaultUserData();
    }

    @Test(description = "C22520, Disable User")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyUserDisabling() {
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

        Actions.loginActions().doLogOut();
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        Actions.usersActions().searchUser(user);

        SettingsPage.usersSearchPage().clickCellByUserData(user.getEmail());

        user.setIsLoginDisabledFlag(true);

        Actions.usersActions().editUser(user);

        Assert.assertEquals(Actions.usersActions().getUserFromUserViewPage(), user,
                "User's data isn't the same");

        Actions.loginActions().doLogOut();

        Pages.loginPage().waitForLoginForm();
        Pages.loginPage().typeUserName(user.getLoginID());
        Pages.loginPage().typePassword(user.getPassword());
        Pages.loginPage().clickEnterButton();
        Pages.loginPage().wait(2);
        Assert.assertTrue(Pages.loginPage().isUserNameFieldHasError(),
                "Error messages for user name field is not visible");
        Assert.assertTrue(Pages.loginPage().isPasswordFieldHasError(),
                "Error messages for password field is not visible");

        Assert.assertEquals(Pages.loginPage().getErrorMessage(),
                "The user account has been disabled, please contact your system administrator",
                "Expected message is not visible");
    }
}
