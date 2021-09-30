package com.nymbus.frontoffice.loansmanagement;

import com.nymbus.core.base.BaseTest;
import com.nymbus.newmodels.account.Account;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Loans Management")
@Owner("Petro")
public class C46344_PaymentDueRecordsPostPartialPaymentOnAmortizedLoanWithGeneratedPaymentDueRecordsCycleNo extends BaseTest {

    private Account loanAccount;
    private Account chkAccount;
    private String localDate;
    private final String loanProductName = "Test Loan Product";
    private final String loanProductInitials = "TLP";
    private final String TEST_RUN_NAME = "Loans Management";

    @BeforeMethod
    public void preConditions() {

    }

    @TestRailIssue(issueID = 46344, testRunName = TEST_RUN_NAME)
    @Test(description = "C46344, Payment Due Records: Post partial payment on Amortized loan with generated Payment Due Records | Cycle == 'No'")
    @Severity(SeverityLevel.CRITICAL)
    public void paymentDueRecordsPostPartialPaymentOnAmortizedLoanWithGeneratedPaymentDueRecordsCycleNo() {

    }
}
