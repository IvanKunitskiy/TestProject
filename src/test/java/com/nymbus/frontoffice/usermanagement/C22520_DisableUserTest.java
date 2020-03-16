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
public class C22520_DisableUserTest extends BaseTest {

    private User user;

    @BeforeMethod
    public void prepareUserData() {
        user = new User().setDefaultUserData();
    }

    @Test(description = "C22520, Disable User")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyUserDisabling() {

        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        Actions.usersActions().createUser(user);

        SettingsPage.viewUserPage().waitViewUserDataVisible();

        Assert.assertEquals(Actions.usersActions().getUserFromUserViewPage(), user,
                "User's data isn't the same");

        Actions.loginActions().doLogOut();

        Selenide.open(Constants.WEB_ADMIN_URL);
        WebAdminActions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        WebAdminActions.webAdminUsersActions().setUserPassword(user);

        Selenide.open(Constants.URL);

        Actions.loginActions().doLogin(user.getLoginID(), user.getPassword());
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

        Assert.assertEquals(Pages.loginPage().getErrorMessage(),
                "The user account has been disabled, please contact your system administrator",
                "Expected message is not visible");

        Assert.assertTrue(Pages.loginPage().isUserNameFieldHasError(),
                "Error messages for user name field is not visible");
        Assert.assertTrue(Pages.loginPage().isPasswordFieldHasError(),
                "Error messages for password field is not visible");
    }
}
