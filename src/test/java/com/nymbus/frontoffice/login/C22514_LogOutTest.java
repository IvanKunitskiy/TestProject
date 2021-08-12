package com.nymbus.frontoffice.login;

import com.codeborne.selenide.WebDriverRunner;
import com.nymbus.actions.Actions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.pages.Pages;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Log in")
@Owner("Petro")
public class C22514_LogOutTest extends BaseTest {

    private final String TEST_RUN_NAME = "Log in";

    @TestRailIssue(issueID = 22514, testRunName = TEST_RUN_NAME)
    @Test(description = "C22514, Log Out")
    public void verifyLogOut() {

        logInfo("Step 1: Log in to the system as the user from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        Assert.assertTrue(WebDriverRunner.url().toLowerCase().contains("frontoffice"),
                "Front office section is not opened");

        Assert.assertTrue(WebDriverRunner.url().toLowerCase().contains("customer"),
                "Client page is not opened");

        logInfo("Step 2: Perform log out from the system using the [Sign Out] button from the users sub-menu " +
                "in the header at the top-right corner of the screen:\n" +
                "- Frontoffice app (any of the following: - Clients, - Teller, - Cash Drawer, - Journal, " +
                "- Teller to Teller Transfer, - Cashier Defined Transactions)");
        Actions.loginActions().doLogOut();

        Assert.assertTrue(Pages.loginPage().isUserNameFieldVisible(),
                "User name field is not visible");

        Assert.assertTrue(Pages.loginPage().isUserNameFieldVisible(),
                "Password field is not visible");

        logInfo("Step 3: Log in to the system as the user from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 4: Perform log out from the system using the [Sign Out] button from the Users sub-menu in " +
                "the header at the top-right corner of the screen:\n" +
                "- Loans app");
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

        logInfo("Step 5: Log in to the system as the user from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 6: Perform log out from the system using the [Sign Out] button from the Users sub-menu " +
                "in the header at the top-right corner of the screen:\n" +
                "- Report Generator app");
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

        logInfo("Step 7: Log in to the system as the user from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 8: Perform log out from the system using the [Sign Out] button from the Users sub-menu " +
                "in the header at the top-right corner of the screen:\n" +
                "- Backoffice app");
        Pages.aSideMenuPage().clickBackOfficeMenuItem();

        Assert.assertTrue(Pages.aSideMenuPage().isBackOfficePageOpened(),
                "Back office page is not opened");

        Assert.assertTrue(WebDriverRunner.url().toLowerCase().contains("backoffice"),
                "Back office page is not opened");

        logInfo("Step 9: Log in to the system as the user from the precondition");
        Actions.loginActions().doLogOut();

        Assert.assertTrue(Pages.loginPage().isUserNameFieldVisible(),
                "User name field is not visible");

        Assert.assertTrue(Pages.loginPage().isUserNameFieldVisible(),
                "Password field is not visible");

        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 10: Perform log out from the system using the [Sign Out] button from the Users " +
                "sub-menu in the header at the top-right corner of the screen:\n" +
                "- Settings app");
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