package com.nymbus.frontoffice.backoffice;

import com.nymbus.actions.Actions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.backoffice.itemsToWork.Item;
import com.nymbus.pages.Pages;
import com.nymbus.testrail.CustomStepResult;
import com.nymbus.testrail.TestRailAssert;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Backoffice")
@Feature("Notices")
@Owner("Dmytro")
public class C22846_ItemsToWorkFiltersResults extends BaseTest {
    private Item payACHItemToWork;
    private Item rejectedItemToWork;

    @BeforeMethod
    public void preCondition() {
        //Get Items To Work
        payACHItemToWork = WebAdminActions.webAdminUsersActions().getItemToWork(userCredentials);
        rejectedItemToWork = WebAdminActions.webAdminUsersActions().getRejectedItemToWork(userCredentials);
    }

    private final String TEST_RUN_NAME = "Account Analysis";

    @TestRailIssue(issueID = 22846, testRunName = TEST_RUN_NAME)
    @Test(description = "C22846, Items to work: filters results")
    @Severity(SeverityLevel.CRITICAL)
    public void itemsToWorkFiltersResults() {
        logInfo("Step 1: Log in to the system as the user from the precondition");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to Backoffice > Items to Work");
        Pages.aSideMenuPage().clickBackOfficeMenuItem();
        Pages.backOfficePage().clickItemToWork();

        logInfo("Step 3: Set status == Pay, Source == ACH and specify date which is related to reject1 in filters section;\n" +
                "Run the search");
        Pages.backOfficePage().clickStatusDropDown();
        String status = "Pay";
        Pages.backOfficePage().clickStatusOrSource(status);
        Pages.backOfficePage().clickSourceDropDown();
        String source = "ACH";
        Pages.backOfficePage().clickStatusOrSource(source);
        String dateTimePosted = payACHItemToWork.getDateTimePosted();
        String replace = dateTimePosted.replace('-', '/');
        String firstYear = replace.substring(5) + "/" + replace.substring(0,4);
        Pages.backOfficePage().inputProofDate(firstYear);
        Pages.backOfficePage().clickFilterButton();
        String branch = Pages.backOfficePage().getBranchByAccountNumber(payACHItemToWork.getAccountNumber());
        String proofDateByAccountNumber = Pages.backOfficePage().getProofDateByAccountNumber(payACHItemToWork.getAccountNumber());
        replace = DateTime.getDateWithFormat(replace,"yyyy/mm/dd","mm/dd/yyyy");
        TestRailAssert.assertTrue(proofDateByAccountNumber.equals(replace),
                new CustomStepResult("Date is valid", String.format("Date is not valid. Expected %s, actual %s", replace, proofDateByAccountNumber)));
        TestRailAssert.assertTrue(Pages.backOfficePage().getStatusByAccountNumber(payACHItemToWork.getAccountNumber()).equals(status),
                new CustomStepResult("Status is valid", "Status is not valid"));
        TestRailAssert.assertTrue(Pages.backOfficePage().getSourceByAccountNumber(payACHItemToWork.getAccountNumber()).equals(source),
                new CustomStepResult("Source is valid", "Source is not valid"));

        logInfo("Step 4: Set status == Rejected, reason == Bad Account and specify date which is related to reject2 in filters section;\n" +
                "Run the search");
        Pages.backOfficePage().clickXSource();
        Pages.backOfficePage().clickXSource();
        Pages.backOfficePage().clickStatusDropDown();
        status = "Rejected";
        Pages.backOfficePage().clickStatusOrSource(status);
        Pages.backOfficePage().inputReasonBadCode();
        dateTimePosted = rejectedItemToWork.getDateTimePosted();
        replace = dateTimePosted.replace('-', '/');
        firstYear = replace.substring(5) + "/" + replace.substring(0,4);
        Pages.backOfficePage().inputProofDate(firstYear);
        Pages.backOfficePage().clickFilterButton();
        proofDateByAccountNumber = Pages.backOfficePage().getProofDateByAccountNumber(rejectedItemToWork.getAccountNumber());
        replace = DateTime.getDateWithFormat(replace,"yyyy/mm/dd","mm/dd/yyyy");
        TestRailAssert.assertTrue(proofDateByAccountNumber.equals(replace),
                new CustomStepResult("Date is valid", String.format("Date is not valid. Expected %s, actual %s", replace, proofDateByAccountNumber)));
        TestRailAssert.assertTrue(Pages.backOfficePage().getStatusByAccountNumber(rejectedItemToWork.getAccountNumber()).equals(status),
                new CustomStepResult("Status is valid", "Status is not valid"));
        TestRailAssert.assertTrue(Pages.backOfficePage().getSourceByAccountNumber(rejectedItemToWork.getAccountNumber()).equals(source),
                new CustomStepResult("Source is valid", "Source is not valid"));

        logInfo("Step 5: Reset all filters and set Branch == BranchX;\n" +
                "Run the search");
        Pages.backOfficePage().clickXSource();
        Pages.backOfficePage().clickXSource();
        Pages.backOfficePage().inputProofDate("");
        Pages.backOfficePage().clickBranchDropDown();
        Pages.backOfficePage().clickBranchOption(branch);
        Pages.backOfficePage().clickFilterButton();
        for (int i = 0; i<5; i++){
            Pages.backOfficePage().clickShowMore();
        }
        dateTimePosted = payACHItemToWork.getDateTimePosted();
        replace = dateTimePosted.replace('-', '/');
        status = "Pay";
        source = "ACH";
        proofDateByAccountNumber = Pages.backOfficePage().getProofDateByAccountNumber(payACHItemToWork.getAccountNumber());
        replace = DateTime.getDateWithFormat(replace,"yyyy/mm/dd","mm/dd/yyyy");
        TestRailAssert.assertTrue(proofDateByAccountNumber.equals(replace),
                new CustomStepResult("Date is valid", String.format("Date is not valid. Expected %s, actual %s", replace, proofDateByAccountNumber)));
        TestRailAssert.assertTrue(Pages.backOfficePage().getStatusByAccountNumber(payACHItemToWork.getAccountNumber()).equals(status),
                new CustomStepResult("Status is valid", "Status is not valid"));
        TestRailAssert.assertTrue(Pages.backOfficePage().getSourceByAccountNumber(payACHItemToWork.getAccountNumber()).equals(source),
                new CustomStepResult("Source is valid", "Source is not valid"));

    }
}
