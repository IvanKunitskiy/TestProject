package com.nymbus.frontoffice.loansmanagement;

import com.nymbus.core.base.BaseTest;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Loans Management")
@Owner("Petro")
public class C25454_InterestRateChangeManuallyChangeInterestRateOnConvertedLoanAccountTest extends BaseTest {

    @BeforeMethod
    public void preCondition() {

    }

    private final String TEST_RUN_NAME = "Loans Management";

    @TestRailIssue(issueID = 25454, testRunName = TEST_RUN_NAME)
    @Test(description = "C25454, Interest Rate Change: Manually change interest rate on converted loan account")
    @Severity(SeverityLevel.CRITICAL)
    public void interestRateChangeManuallyChangeInterestRateOnConvertedLoanAccount() {

    }
}
