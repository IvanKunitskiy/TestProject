package com.nymbus.frontoffice.usermanagement;

import com.nymbus.actions.Actions;
import com.nymbus.base.BaseTest;
import com.nymbus.models.User;
import com.nymbus.pages.Pages;
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

    User user;

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

    }

}
