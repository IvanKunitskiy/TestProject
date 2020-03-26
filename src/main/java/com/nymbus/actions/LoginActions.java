package com.nymbus.actions;

import com.nymbus.pages.Pages;
import org.testng.Assert;

public class LoginActions {

    public void doLogin(String userName, String password) {
        Pages.loginPage().waitForLoginForm();
        Pages.loginPage().typeUserName(userName);
        Pages.loginPage().typePassword(password);
        Pages.loginPage().clickEnterButton();
        Assert.assertFalse(Pages.loginPage().isErrorsVisibleOnLoginForm(),
                "Error messages is visible");
        Pages.clientsSearchPage().waitForAddNewClientButton();
        Pages.aSideMenuPage().waitForASideMenu();
        Assert.assertTrue(Pages.aSideMenuPage().isClientPageOpened(),
                "Client page is not opened");
    }

    public void doLogOut() {
        Pages.navigationPage().waitForUserMenuVisible();
        if (!Pages.navigationPage().isSingOutButtonVisible())
            Pages.navigationPage().clickAccountButton();
        Pages.navigationPage().clickSignOut();
        Pages.loginPage().waitForLoginForm();
    }
}
