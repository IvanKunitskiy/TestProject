package com.nymbus.actions.webadmin;

import com.nymbus.pages.webadmin.WebAdminPages;
import org.testng.Assert;

public class LoginActions {

    public void doLogin(String userName, String password) {
        WebAdminPages.loginPage().waitForLoginForm();
        WebAdminPages.loginPage().typeUserName(userName);
        WebAdminPages.loginPage().typePassword(password);
        WebAdminPages.loginPage().clickGoButton();
        Assert.assertFalse(WebAdminPages.loginPage().isErrorMessageVisibleOnLoginForm(),
                "Error messages is visible");

        WebAdminPages.navigationPage().waitForPageLoaded();
    }

    public void doLogout() {
        WebAdminPages.navigationPage().clickLogoutMenu();
        WebAdminPages.navigationPage().clickSignOut();
        WebAdminPages.loginPage().waitForLoginForm();
    }
}
