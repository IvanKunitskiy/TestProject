package com.nymbus.frontoffice.loansmanagement;

import com.nymbus.core.base.BaseTest;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Loans Management")
@Owner("Petro")
public class C33829_PaymentDueRecordTransactionHistory416PaymentTransactionForAmount extends BaseTest {
    private final String TEST_RUN_NAME = "Loans Management";
    public void preConditions(){}

    @TestRailIssue(issueID = 33829, testRunName = TEST_RUN_NAME)
    @Test(description = "C33829, Payment Due Record:Transaction History: \"416 - Payment\" transaction for amount < amount due of Active PD")
    @Severity(SeverityLevel.CRITICAL)
    public void paymentDueRecordTransactionHistory416PaymentTransactionForAmount() {

    }
}
