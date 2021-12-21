package com.nymbus.frontoffice.loansmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.pages.Pages;
import com.nymbus.testrail.CustomStepResult;
import com.nymbus.testrail.TestRailAssert;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Loans Management")
@Owner("Petro")
public class C40465_NonAccrualProcessingBypassNonAccrualFlagVerifyThatThereIsAnOpportunityToSwitchBypassNonAccrualOnDelinquentLoan extends BaseTest {

    private final String TEST_RUN_NAME = "Loans Management";
    private String loanAccountNumber;
    @BeforeMethod
    public void preConditions() {
        WebAdminActions.loginActions().openWebAdminPageInNewWindow();
        WebAdminActions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        loanAccountNumber = WebAdminActions.webAdminUsersActions().getLoanAccountWithDaysLateAboveOrEqualTwo();
        SelenideTools.sleep(Constants.MICRO_TIMEOUT);
        WebAdminActions.loginActions().doLogout();
        WebAdminActions.loginActions().closeWebAdminPageAndSwitchToPreviousTab();
    }

    @TestRailIssue(issueID = 40465, testRunName = TEST_RUN_NAME)
    @Test(description = "C40465, Non-Accrual Processing - Bypass Non-Accrual Flag: Verify that there is an opportunity to switch 'Bypass Non-Accrual on Delinquent Loan'>'YES' on account level if Days Late >= 2")
    @Severity(SeverityLevel.CRITICAL)
    public void nonAccrualProcessingBypassNonAccrualFlagVerifyThatThereIsAnOpportunityToSwitchBypassNonAccrualOnDelinquentLoan() {

        logInfo("Step 1: Log in to the system");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Open loan account from preconditions on 'Detail' page");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccountNumber);

        logInfo("Step 3: Click [Edit] button");
        Pages.accountDetailsPage().clickEditButton();
        SelenideTools.sleep(Constants.MINI_TIMEOUT);

        logInfo("Step 4: Turn on 'Bypass Non-Accrual on Delinquent Loan' -> 'Yes'");
        Pages.editAccountPage().clickBypassNonAccrualOnDelinquentLoanSwitch();

        logInfo("Step 5: Click [Save] button");
        Pages.editAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForEditButton();

        logInfo("Step 6: Check the 'Bypass Non-Accrual on Delinquent Loan' field");
        TestRailAssert.assertTrue(Pages.accountDetailsPage().isBypassNonAccrualOnDelinquentLoanSwitchYesValuePresent(),
                new CustomStepResult("'Bypass Non-Accrual on Delinquent Loan' switch value is not valid",
                        "'Bypass Non-Accrual on Delinquent Loan' switch value is valid"));
    }
}
