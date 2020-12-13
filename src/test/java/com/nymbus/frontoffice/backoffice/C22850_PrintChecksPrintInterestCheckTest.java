package com.nymbus.frontoffice.backoffice;

import com.codeborne.selenide.Selenide;
import com.nymbus.actions.Actions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.Functions;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Backoffice")
@Feature("Notices")
@Owner("Petro")
public class C22850_PrintChecksPrintInterestCheckTest extends BaseTest {

    private String accountNumberWithInterestCheck;
    private String officialCheckControl;

    @BeforeMethod
    public void preCondition() {
        Selenide.open(Constants.WEB_ADMIN_URL);
        WebAdminActions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        accountNumberWithInterestCheck = WebAdminActions.webAdminUsersActions().getAccountNumberWithInterestCheck(1);
        officialCheckControl = WebAdminActions.webAdminUsersActions().getOfficialCheckControlNumber();
        WebAdminActions.loginActions().doLogout();
    }

    @Test(description = "C22850, Print Checks: Print Interest Check")
    @Severity(SeverityLevel.CRITICAL)
    public void printChecksPrintInterestCheck() {

        logInfo("Step 1: Log in to the system as the user from the precondition");
        Selenide.open(Constants.URL);
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to Backoffice > Print Checks page");
        Pages.aSideMenuPage().clickBackOfficeMenuItem();
        Pages.backOfficePage().clickPrintChecksSeeMoreLink();

        logInfo("Step 3: Specify Check Type: Interest Check and run the search");
        Pages.printChecksPage().clickCheckTypeSelector();
        Pages.printChecksPage().clickCheckTypeOption("Interest Check");
        Pages.printChecksPage().clickSearchButton();

        logInfo("Step 4: Search for interest check generated for account from preconditions;\n" +
                "Check it and click [Print Checks] button");
        Actions.printChecksActions().expandAllRows();
        Pages.printChecksPage().selectLineWithAccountNumber(accountNumberWithInterestCheck);
        String checkNumber = Pages.printChecksPage().getCheckNumberFromLineWithAccountNumber(accountNumberWithInterestCheck);
        Pages.printChecksPage().clickPrintChecksButton();

        logInfo("Step 5: Verify check information");
        Actions.mainActions().switchToTab(1);
        Pages.checkPrintPage().waitForLoadingSpinnerInvisibility();
        Actions.printChecksActions().verifyMicrLineInPdf(checkNumber, officialCheckControl);
    }

    @AfterMethod(description = "Delete the downloaded PDF.")
    public void postCondition() {
        logInfo("Deleting the downloaded PDF...");
        Functions.cleanDirectory(System.getProperty("user.dir") + "/screenshots/");
    }
}
