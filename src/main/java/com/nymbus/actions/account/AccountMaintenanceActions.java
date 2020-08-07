package com.nymbus.actions.account;

import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.account.Account;
import com.nymbus.pages.Pages;
import org.testng.Assert;

public class AccountMaintenanceActions {
    public void expandAllRows() {
        do {
            Pages.accountMaintenancePage().clickViewMoreButton();
            SelenideTools.sleep(Constants.MICRO_TIMEOUT);
        }
        while (Pages.accountMaintenancePage().isMoreButtonVisible());
    }

    /**
     * Verification of records which are common for all accounts
     */

    public void verifyGeneralRecordsAfterEditing(Account account) {
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Product") >= 1,
                "'Product' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("accounttype") >= 1,
                "'accounttype' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Account Title") >= 1,
                "'Account Title' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Bank Branch") >= 1,
                "'Bank Branch' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("User Defined Field 1") >= 1,
                "'User Defined Field 1' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("User Defined Field 2") >= 1,
                "'User Defined Field 2' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("User Defined Field 3") >= 1,
                "'User Defined Field 3' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("User Defined Field 4") >= 1,
                "'User Defined Field 4' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Federal W/H reason") >= 1,
                "'Federal W/H reason' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Date Opened") >= 1,
                "'Date Opened' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Current Officer") >= 1,
                "'Current Officer' row count is incorrect!");
        if (account.getCallClassCode() != null) {
            Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Call Class Code") >= 1,
                    "'Call Class Code' row count is incorrect!");
        }
    }

    /**
     * CHK account verification
     */

    public void verifyChkAccountRecordsAfterEditing(Account account) {
        expandAllRows();
        verifyGeneralRecordsAfterEditing(account);
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Charge or analyze") >= 1,
                "'Charge or analyze' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Automatic Overdraft Status") >= 1,
                "'Automatic Overdraft Status' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Interest Rate") >= 1,
                "'Interest Rate' row count is incorrect!");

        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Number of Debit Cards issued") >= 1,
                "'Number of Debit Cards issued' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Reason Debit Card Charge Waived") >= 1,
                "'Reason Debit Card Charge Waived' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Bankruptcy/Judgement") >= 1,
                "'Bankruptcy/Judgement' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Federal W/H percent") >= 1,
                "'Federal W/H percent' row count is incorrect!");
    }

    /**
     * Regular savings account verification
     */

    public void verifySavingsAccountRecordsAfterEditing(Account account) {
        expandAllRows();
        verifyGeneralRecordsAfterEditing(account);
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Interest Rate") >= 1,
                "'Interest Rate' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Reason Debit Card Charge Waived") >= 1,
                "'Reason Debit Card Charge Waived' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Print Statement Next Update") >= 1,
                "'Print Statement Next Update' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Number of Debit Cards issued") >= 1,
                "'Number of Debit Cards issued' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Statement Cycle") >= 1,
                "'Statement Cycle' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Interest Frequency") >= 1,
                "'Interest Frequency' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Federal W/H percent") >= 1,
                "'Federal W/H percent' row count is incorrect!");
    }

    /**
     * Savings IRA account verification
     */

    public void verifySavingsIraAccountRecordsAfterEditing(Account account) {
        expandAllRows();
        verifyGeneralRecordsAfterEditing(account);
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Reason Debit Card Charge Waived") >= 1,
                "'Reason Debit Card Charge Waived' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Print Statement Next Update") >= 1,
                "'Print Statement Next Update' row count is incorrect!");

        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Number of Debit Cards issued") >= 1,
                "'Number of Debit Cards issued' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Statement Cycle") >= 1,
                "'Statement Cycle' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Interest Frequency") >= 1,
                "'Interest Frequency' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Federal W/H percent") >= 1,
                "'Federal W/H percent' row count is incorrect!");
    }

    /**
     * Regular CD account verification
     */

    public void verifyRegularCdAccountRecordsAfterEditing(Account account) {
        expandAllRows();
        verifyGeneralRecordsAfterEditing(account);
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Interest Type") >= 1,
                "'Interest Type' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Interest Rate") >= 1,
                "'Interest Rate' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Apply Interest To") >= 1,
                "'Apply Interest To' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Federal W/H percent") >= 1,
                "'Federal W/H percent' row count is incorrect!");
    }

    /**
     * CD IRA account verification
     */

    public void verifyCdIraAccountRecordsAfterEditing(Account account) {
        expandAllRows();
        verifyGeneralRecordsAfterEditing(account);
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Date Of First Deposit") >= 1,
                "'Date Of First Deposit' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Date of Birth") >= 1,
                "'Birth Date' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Date Deceased") >= 1,
                "'Date Deceased' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Transactional Account") >= 1,
                "'Transactional Account' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Apply Seasonal Address") >= 1,
                "'Apply Seasonal Address' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Interest Rate") >= 1,
                "'Interest Rate' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Apply Interest To") >= 1,
                "'Apply Interest To' row count is incorrect!");
    }
}