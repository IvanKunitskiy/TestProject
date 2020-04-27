package com.nymbus.frontoffice.login;

import com.codeborne.selenide.WebDriverRunner;
import com.nymbus.actions.Actions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Log in")
@Owner("Petro")
public class C22514_LogOutTest extends BaseTest {

    @Test(description = "C22514, Log Out")
    public void verifyLogOut() {

        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        Assert.assertTrue(WebDriverRunner.url().toLowerCase().contains("frontoffice"),
                "Front office section is not opened");

        Assert.assertTrue(WebDriverRunner.url().toLowerCase().contains("customer"),
                "Client page is not opened");

        Actions.loginActions().doLogOut();

        Assert.assertTrue(Pages.loginPage().isUserNameFieldVisible(),
                "User name field is not visible");

        Assert.assertTrue(Pages.loginPage().isUserNameFieldVisible(),
                "Password field is not visible");

        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        Pages.aSideMenuPage().clickLoansMenuItem();
        Pages.loansPage().waitForLoanPageLoaded();

        Assert.assertTrue(Pages.aSideMenuPage().isLoansPageOpened(),
                "Loans page is not opened");

        Assert.assertTrue(WebDriverRunner.url().toLowerCase().contains("loan"),
                "Loan page is not opened");

        Actions.loginActions().doLogOut();

        Assert.assertTrue(Pages.loginPage().isUserNameFieldVisible(),
                "User name field is not visible");

        Assert.assertTrue(Pages.loginPage().isUserNameFieldVisible(),
                "Password field is not visible");

        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        Pages.aSideMenuPage().clickReportGeneratorMenuItem();
        Pages.reportGeneratorPage().waitForReportGeneratorPageLoaded();

        Assert.assertTrue(Pages.aSideMenuPage().isReportGeneratorPageOpened(),
                "Report generator page is not opened");

        Assert.assertTrue(WebDriverRunner.url().toLowerCase().contains("ahr"),
                "Report generator is not opened");

        Actions.loginActions().doLogOut();

        Assert.assertTrue(Pages.loginPage().isUserNameFieldVisible(),
                "User name field is not visible");

        Assert.assertTrue(Pages.loginPage().isUserNameFieldVisible(),
                "Password field is not visible");

        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        Pages.aSideMenuPage().clickBackOfficeMenuItem();


        Assert.assertTrue(Pages.aSideMenuPage().isBackOfficePageOpened(),
                "Back office page is not opened");

        Assert.assertTrue(WebDriverRunner.url().toLowerCase().contains("backoffice"),
                "Back office page is not opened");

        Actions.loginActions().doLogOut();

        Assert.assertTrue(Pages.loginPage().isUserNameFieldVisible(),
                "User name field is not visible");

        Assert.assertTrue(Pages.loginPage().isUserNameFieldVisible(),
                "Password field is not visible");

        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        Pages.aSideMenuPage().clickSettingsMenuItem();
        Pages.settings().waitForSettingsPageLoaded();

        Assert.assertTrue(Pages.aSideMenuPage().isSettingsPageOpened(),
                "Settings page is not opened");

        Assert.assertTrue(WebDriverRunner.url().toLowerCase().contains("settings"),
                "Settings page is not opened");

        Actions.loginActions().doLogOut();

        Assert.assertTrue(Pages.loginPage().isUserNameFieldVisible(),
                "User name field is not visible");

        Assert.assertTrue(Pages.loginPage().isUserNameFieldVisible(),
                "Password field is not visible");

    }
}