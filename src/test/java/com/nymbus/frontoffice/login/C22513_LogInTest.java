package com.nymbus.frontoffice.login;

import com.nymbus.core.base.BaseTest;
import com.nymbus.testrail.TestRailIssue;
import com.nymbus.core.utils.Constants;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Log In")
@Owner("Petro")
public class C22513_LogInTest extends BaseTest {

    private final String TEST_RUN_NAME = "Log in";

    @TestRailIssue(issueID = 22513, testRunName = TEST_RUN_NAME)
    @Test(description = "C22513, Log in")
    public void verifyLogin() {

        Pages.loginPage().waitForLoginForm();
        Pages.loginPage().typeUserName("");
        Pages.loginPage().typePassword("");
        Pages.loginPage().clickEnterButton();
        Assert.assertTrue(Pages.loginPage().isUserNameFieldHasError(),
                "Error messages for user name field is not visible");
        Assert.assertTrue(Pages.loginPage().isPasswordFieldHasError(),
                "Error messages for password field is not visible");

        Pages.loginPage().waitForLoginForm();
        Pages.loginPage().typeUserName(Constants.USERNAME);
        Pages.loginPage().typePassword(Constants.PASSWORD);
        Pages.loginPage().clickEnterButton();
        Assert.assertFalse(Pages.loginPage().isErrorsVisibleOnLoginForm(),
                "Error messages is visible");

        Pages.clientsSearchPage().waitForAddNewClientButton();

        Pages.aSideMenuPage().waitForASideMenu();
        Assert.assertTrue(Pages.aSideMenuPage().isClientPageOpened(),
                "Client page is not opened");

        Pages.navigationPage().waitForUserMenuVisible();
        Pages.navigationPage().clickAccountButton();

        Assert.assertTrue(Pages.navigationPage().getUserFullName().contains(Constants.FIRST_NAME),
                "User's first name is not visible. Found: " + Pages.navigationPage().getUserFullName());

        Assert.assertTrue(Pages.navigationPage().getUserFullName().contains(Constants.LAST_NAME),
                "User's last name is not visible. Found: " + Pages.navigationPage().getUserFullName());
    }
}