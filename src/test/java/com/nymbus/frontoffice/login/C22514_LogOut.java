package com.nymbus.frontoffice.login;

import com.nymbus.actions.Actions;
import com.nymbus.base.BaseTest;
import com.nymbus.pages.Pages;
import com.nymbus.util.Constants;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Log out")
public class C22514_LogOut extends BaseTest {

    public void loginWithVerification(){

        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        Pages.clientsPage().waitForAddNewClientButton();

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

    @Test(description = "C22514, Log Out")
    @Severity(SeverityLevel.CRITICAL)
    public void firstTest() {

        navigateToUrl(Constants.URL);

        loginWithVerification();

        Assert.assertTrue(BaseTest.getDriver().getCurrentUrl().toLowerCase().contains("frontoffice"),
                "Front office section is not opened");

        Assert.assertTrue(BaseTest.getDriver().getCurrentUrl().toLowerCase().contains("customer"),
                "Client page is not opened");

        Actions.loginActions().doLogOut();

        Assert.assertTrue(Pages.loginPage().isUserNameFieldVisible(),
                "User name field is not visible");

        Assert.assertTrue(Pages.loginPage().isUserNameFieldVisible(),
                "Password field is not visible");

        loginWithVerification();

        Pages.aSideMenuPage().clickLoansMenuItem();
        Pages.loansPage().waitForLoanPageLoaded();

        Assert.assertTrue(Pages.aSideMenuPage().isLoansPageOpened(),
                "Loans page is not opened");

        Assert.assertTrue(BaseTest.getDriver().getCurrentUrl().toLowerCase().contains("loan"),
                "Loan page is not opened");

        Actions.loginActions().doLogOut();

        Assert.assertTrue(Pages.loginPage().isUserNameFieldVisible(),
                "User name field is not visible");

        Assert.assertTrue(Pages.loginPage().isUserNameFieldVisible(),
                "Password field is not visible");

        loginWithVerification();

        Pages.aSideMenuPage().clickReportGeneratorMenuItem();
        Pages.reportGeneratorPage().waitForReportGeneratorPageLoaded();

        Assert.assertTrue(Pages.aSideMenuPage().isReportGeneratorPageOpened(),
                "Report generator page is not opened");

        Assert.assertTrue(BaseTest.getDriver().getCurrentUrl().toLowerCase().contains("ahr"),
                "Report generator is not opened");

        Actions.loginActions().doLogOut();

        Assert.assertTrue(Pages.loginPage().isUserNameFieldVisible(),
                "User name field is not visible");

        Assert.assertTrue(Pages.loginPage().isUserNameFieldVisible(),
                "Password field is not visible");

        loginWithVerification();

        Pages.aSideMenuPage().clickBackOfficeMenuItem();


        Assert.assertTrue(Pages.aSideMenuPage().isBackOfficePageOpened(),
                "Back office page is not opened");

        Assert.assertTrue(BaseTest.getDriver().getCurrentUrl().toLowerCase().contains("backoffice"),
                "Back office page is not opened");

        Actions.loginActions().doLogOut();

        Assert.assertTrue(Pages.loginPage().isUserNameFieldVisible(),
                "User name field is not visible");

        Assert.assertTrue(Pages.loginPage().isUserNameFieldVisible(),
                "Password field is not visible");

        loginWithVerification();

        Pages.aSideMenuPage().clickSettingsMenuItem();
        Pages.settings().waitForSettingsPageLoaded();

        Assert.assertTrue(Pages.aSideMenuPage().isSettingsPageOpened(),
                "Settings page is not opened");

        Assert.assertTrue(BaseTest.getDriver().getCurrentUrl().toLowerCase().contains("settings"),
                "Settings page is not opened");

        Actions.loginActions().doLogOut();

        Assert.assertTrue(Pages.loginPage().isUserNameFieldVisible(),
                "User name field is not visible");

        Assert.assertTrue(Pages.loginPage().isUserNameFieldVisible(),
                "Password field is not visible");

    }
}
