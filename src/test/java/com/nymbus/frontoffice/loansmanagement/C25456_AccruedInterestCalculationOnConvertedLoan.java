package com.nymbus.frontoffice.loansmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Loans Management")
@Owner("Dmytro")
public class C25456_AccruedInterestCalculationOnConvertedLoan extends BaseTest {


    private final String TEST_RUN_NAME = "Loans Management";

    @TestRailIssue(issueID = 25454, testRunName = TEST_RUN_NAME)
    @Test(description = "C25456, Accrued interest calculation on converted loan")
    @Severity(SeverityLevel.CRITICAL)
    public void accruedInterestCalculationOnConvertedLoan() {
        logInfo("Step 1: Log in to SmartCore");
        SelenideTools.openUrl(Constants.URL);
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Open account from preconditions");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(activeLoanAccountId);

    }

}
