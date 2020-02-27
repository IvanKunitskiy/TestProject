package com.nymbus.frontoffice.usermanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.base.BaseTest;
import com.nymbus.model.User;
import com.nymbus.pages.Pages;
import com.nymbus.pages.settings.SettingsPage;
import com.nymbus.util.Constants;
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
    public void prepareUserData(){
        user = new User().setDefaultUserData();
    }

    @Test(description = "C22521, Enable User")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyUserEnabling() {
        navigateToUrl(Constants.URL);

        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        Pages.aSideMenuPage().clickSettingsMenuItem();
        Actions.usersActions().createUser(user);
        user.setIsLoginDisabledFlag(true);
        Actions.usersActions().editUser(user);

        SettingsPage.viewUserPage().waitViewUserDataVisible();

        Assert.assertEquals(Actions.usersActions().getUserFromUserViewPage(), user,
                "User's data isn't the same");

        Actions.loginActions().doLogOut();

        navigateToUrl(Constants.WEB_ADMIN_URL);
        WebAdminActions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        WebAdminActions.webAdminUsersActions().setUserPassword(user);

        navigateToUrl(Constants.URL);

        Pages.loginPage().waitForLoginForm();
        Pages.loginPage().typeUserName(user.getLoginID());
        Pages.loginPage().typePassword(user.getPassword());
        Pages.loginPage().clickEnterButton();
        Pages.loginPage().wait(2);

        Assert.assertEquals(Pages.loginPage().getErrorMessage(),
                "The user account has been disabled, please contact your system administrator",
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
