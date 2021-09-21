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
public class C32447_PaymentDueRecordsInactiveButtonActiveDues extends BaseTest {

    private Account loanAccount;
    private Account chkAccount;
    private final String loanProductName = "Test Loan Product";
    private final String loanProductInitials = "TLP";
    private final String TEST_RUN_NAME = "Loans Management";

    @BeforeMethod
    public void preConditions() {}

    @TestRailIssue(issueID = 32447, testRunName = TEST_RUN_NAME)
    @Test(description = "C32447, Payment Due Records: 'Inactive' button - Active Dues")
    @Severity(SeverityLevel.CRITICAL)
    public void paymentDueRecordsInactiveButtonActiveDues() {

    }

}
