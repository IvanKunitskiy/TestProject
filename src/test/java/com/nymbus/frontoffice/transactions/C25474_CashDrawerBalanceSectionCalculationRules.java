package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.pages.Pages;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Transactions")
@Owner("Dmytro")
public class C25474_CashDrawerBalanceSectionCalculationRules extends BaseTest {


    private final String TEST_RUN_NAME = "Transactions";

    @TestRailIssue(issueID = 25474, testRunName = TEST_RUN_NAME)
    @Test(description = "C25474, Cash Drawer - Balance section - Calculation rules")
    @Severity(SeverityLevel.CRITICAL)
    public void cashDrawerBalanceSectionCalculationRules() {
        logInfo("Step 1: Log in to the system as User from the preconditions");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to Cash Drawer screen and log in to proof date");
        Pages.aSideMenuPage().clickCashDrawerMenuItem();
        Actions.transactionActions().doLoginTeller();

        logInfo("Step 3: Verify the 'Beginning Cash' field's value");
        String beginningCash = Pages.cashDrawerBalancePage().getBeginningCash();
        //assert

        logInfo("Step 4: Verify the 'Cash In' field's value");
        String cashIn = Pages.cashDrawerBalancePage().getCashIn();
        //assert

        logInfo("Step 5: Verify the 'Cash Out' field's value");
        String cashOut = Pages.cashDrawerBalancePage().getCashOut();
        //assert

        logInfo("Step 6: Verify the 'Calculated Cash' field's value");
        String calculatedCash = Pages.cashDrawerBalancePage().getCalculatedCash();
        //assert

        logInfo("Step 7: Verify the 'Counted Cash' field's value");
        String countedCash = Pages.cashDrawerBalancePage().getCountedCash();
        //assert

        logInfo("Step 8: Verify the 'Cash Over' field's value");
        String cashOver = Pages.cashDrawerBalancePage().getCashOver();
        //assert

        logInfo("Step 9: Verify the 'Cash Short' field's value");
        String cashShort = Pages.cashDrawerBalancePage().getCashShort();
        //assert
    }
}
