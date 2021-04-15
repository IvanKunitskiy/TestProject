package com.nymbus.frontoffice.loansmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Loans Management")
@Owner("Petro")
public class C25426_ChargeOffProcessingPartialChargeOffProcessingTest extends BaseTest {

    @BeforeMethod
    public void preCondition() {

    }

    private final String TEST_RUN_NAME = "Loans Management";

    @TestRailIssue(issueID = 25426, testRunName = TEST_RUN_NAME)
    @Test(description="C25426, Charge Off Processing: Partial Charge Off processing")
    @Severity(SeverityLevel.CRITICAL)
    public void commercialParticipationLoanSellRepurchase() {

        logInfo("Step 1: Log in to the system");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to the 'Cashier Defined Transactions' page" );
    }
}
