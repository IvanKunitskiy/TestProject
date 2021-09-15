package com.nymbus.frontoffice.loansmanagement;

import com.nymbus.core.base.BaseTest;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Loans Management")
@Owner("Petro")
public class C32545_PaymentProcessing421ForceToIntNoPdRecords extends BaseTest {

    private final String loanProductName = "Test Loan Product";
    private final String loanProductInitials = "TLP";
    private final String TEST_RUN_NAME = "Loans Management";

    @BeforeMethod
    public void preConditions() {

    }

    @TestRailIssue(issueID = 32545, testRunName = TEST_RUN_NAME)
    @Test(description = "C32545, Payment Processing: 421 - Force To Int, no PD records")
    @Severity(SeverityLevel.CRITICAL)
    public void paymentProcessing421ForceToIntNoPdRecords() {

    }
}
