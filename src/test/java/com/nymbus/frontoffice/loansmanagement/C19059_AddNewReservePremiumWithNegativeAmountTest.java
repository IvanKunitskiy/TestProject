package com.nymbus.frontoffice.loansmanagement;

import com.nymbus.core.base.BaseTest;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Loans Management")
@Owner("Petro")
public class C19059_AddNewReservePremiumWithNegativeAmountTest extends BaseTest {

    @BeforeMethod
    public void preCondition() {

    }

    private final String TEST_RUN_NAME = "Loans Management";

    @TestRailIssue(issueID = 19059, testRunName = TEST_RUN_NAME)
    @Test(description="C19059, Add new 'Reserve/Premium' with negative amount")
    @Severity(SeverityLevel.CRITICAL)
    public void addNewReservePremiumWithNegativeAmount() {

    }
}
