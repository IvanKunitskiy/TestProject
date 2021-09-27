package com.nymbus.frontoffice.loansmanagement;

import com.nymbus.core.base.BaseTest;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Loans Management")
@Owner("Petro")
public class C32555_AccountsListingCurrentDueDate2xActivePD extends BaseTest {

    private Account loanAccount;
    private Account chkAccount;
    private Transaction transaction_416;
    private String clientRootId;
    private final String loanProductName = "Test Loan Product";
    private final String loanProductInitials = "TLP";
    private final String TEST_RUN_NAME = "Loans Management";

    @BeforeMethod
    public void preConditions(){

    }

    @TestRailIssue(issueID = 32555, testRunName = TEST_RUN_NAME)
    @Test(description = "C32555, Accounts Listing - Current Due Date, 2x Active PDs")
    @Severity(SeverityLevel.CRITICAL)
    public void accountsListingCurrentDueDate2xActivePD() {

    }

}
