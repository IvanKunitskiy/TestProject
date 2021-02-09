package com.nymbus.frontoffice.loansmanagement;

import com.nymbus.core.base.BaseTest;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Loans Management")
@Owner("Dmytro")
public class C25378_ProcessLoanPaymentTransaction extends BaseTest {


    private final String TEST_RUN_NAME = "Loans Management";

    @TestRailIssue(issueID = 25378, testRunName = TEST_RUN_NAME)
    @Test(description = "C25378, Process 416 loan payment transaction")
    @Severity(SeverityLevel.CRITICAL)
    public void process416LoanPaymentTransaction() {



    }
}
