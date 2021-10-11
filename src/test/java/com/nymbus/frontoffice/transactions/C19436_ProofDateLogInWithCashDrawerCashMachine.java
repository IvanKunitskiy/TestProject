package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import com.nymbus.pages.Pages;
import com.nymbus.testrail.CustomStepResult;
import com.nymbus.testrail.TestRailAssert;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Transactions")
@Owner("Dmytro")
public class C19436_ProofDateLogInWithCashDrawerCashMachine extends BaseTest {
    private String date;
    private String cashRecycler;

    @BeforeMethod
    public void prepareTransactionData() {
        //Check CFMIntegrationEnabled
        WebAdminActions.loginActions().openWebAdminPageInNewWindow();
        WebAdminActions.loginActions().doLogin(userCredentials.getUserName(),userCredentials.getPassword());
        if (WebAdminActions.webAdminUsersActions().isCFMIntegrationEnabled()) {
            throw new SkipException("CFMIntegrationEnabled = 1");
        }
        date = WebAdminActions.webAdminUsersActions().getDateFilesUpdatedThrough();
        date = DateTime.getDatePlusDays(date, 1);

        cashRecycler = WebAdminActions.webAdminUsersActions().getCashRecyclerName();

        WebAdminActions.loginActions().doLogout();
        WebAdminActions.loginActions().closeWebAdminPageAndSwitchToPreviousTab();
    }

    private final String TEST_RUN_NAME = "Transactions";

    @TestRailIssue(issueID = 19436, testRunName = TEST_RUN_NAME)
    @Test(description = "C19436, Proof Date Log In with Cash Drawer + Cash Machine")
    @Severity(SeverityLevel.CRITICAL)
    public void proofDateLogInWithCashDrawer() {
        logInfo("Step 1: Log in to the system as the user from preconditions");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Click on the User Profile logo icon in the top right corner of the screen\n" +
                "Press [Proof Date Login] button");
        Actions.transactionActions().goToTellerPage();
        Pages.tellerPage().waitModalWindow();
        TestRailAssert.assertEquals(Pages.tellerModalPage().getTeller(), "Teller", new CustomStepResult("Teller is ok",
                "Teller is not valid"));
        TestRailAssert.assertEquals(Pages.tellerModalPage().getProofDateValue(), date, new CustomStepResult("Date is ok",
                "Date is not valid"));
        TestRailAssert.assertEquals(Pages.tellerModalPage().getLocation(), "Clarence Office",
                new CustomStepResult("Location is ok", "Location is not valid"));
        TestRailAssert.assertEquals(Pages.tellerModalPage().getCashDrawerName(), userCredentials.getUserName(),
                new CustomStepResult("CashDrawer name is ok", "CashDrawer name is not valid"));
        TestRailAssert.assertEquals(Pages.tellerModalPage().getCashRecycler(), "Select Cash Recycler",
                new CustomStepResult("CashRecycler is ok", "CashRecycler is not valid"));

        logInfo("Step 3: Select Cash Machine from preconditions in the 'Select Cash Recycler' drop down");
        Pages.tellerModalPage().clickCashRecycler();
        Pages.tellerModalPage().clickCashRecyclerItem(cashRecycler);

        logInfo("Step 4: Select side from the 'Side' dropdown (e.g. 'Right' or 'Left')");
        Pages.tellerModalPage().clickSide();
        Pages.tellerModalPage().clickLeftSide();

        logInfo("Step 5: Press [Enter] button");
        Actions.transactionActions().loginTeller();
        TestRailAssert.assertEquals(Pages.tellerPage().getLocation(), "Clarence Office",
                new CustomStepResult("Location is ok", "Location is not valid"));
        TestRailAssert.assertEquals(Pages.tellerPage().getDrawerName(), userCredentials.getUserName(),
                new CustomStepResult("CashDrawer name is ok", "CashDrawer name is not valid"));
        TestRailAssert.assertEquals(Pages.tellerPage().getDrawerBalance(), "$2,016,900.00",
                new CustomStepResult("Drawer Balance is ok", "Drawer Balance is not valid"));
        TestRailAssert.assertEquals(Pages.tellerPage().getProofDate(), date, new CustomStepResult("Date is ok",
                "Date is not valid"));
    }
}