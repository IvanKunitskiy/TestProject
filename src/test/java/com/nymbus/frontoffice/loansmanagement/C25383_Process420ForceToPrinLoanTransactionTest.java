package com.nymbus.frontoffice.loansmanagement;

import com.nymbus.core.base.BaseTest;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Loans Management")
@Owner("Petro")
public class C25383_Process420ForceToPrinLoanTransactionTest extends BaseTest {

    @BeforeMethod
    public void preCondition() {

    }

    private final String TEST_RUN_NAME = "Loans Management";

    @TestRailIssue(issueID = 25383, testRunName = TEST_RUN_NAME)
    @Test(description = "C25383, Process 420 - Force To Prin loan transaction")
    @Severity(SeverityLevel.CRITICAL)
    public void process420ForceToPrinLoanTransaction() {

    }
}
