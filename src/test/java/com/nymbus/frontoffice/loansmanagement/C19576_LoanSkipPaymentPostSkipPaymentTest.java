package com.nymbus.frontoffice.loansmanagement;

import com.nymbus.core.base.BaseTest;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Loans Management")
@Owner("Petro")
public class C19576_LoanSkipPaymentPostSkipPaymentTest extends BaseTest {

    @BeforeMethod
    public void preCondition() {

    }

    private final String TEST_RUN_NAME = "Loans Management";

    @TestRailIssue(issueID = 19576, testRunName = TEST_RUN_NAME)
    @Test(description="C19576, Loan Skip Payment: Post Skip Payment")
    @Severity(SeverityLevel.CRITICAL)
    public void loanSkipPaymentPostSkipPayment() {

    }
}
