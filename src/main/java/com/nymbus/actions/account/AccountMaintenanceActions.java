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
        expandAllRows();
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

    public void verifyGeneralRecords(Account account) {
        expandAllRows();
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Product") >= 1,
                "'Product' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("accounttype") >= 1,
                "'accounttype' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Account Title") >= 1,
                "'Account Title' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Current Officer") >= 1,
                "'Current Officer' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Bank Branch") >= 1,
                "'Bank Branch' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Date Opened") >= 1,
                "'Date Opened' row count is incorrect!");
        if (account.getCallClassCode() != null) {
            Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Call Class Code") >= 1,
                    "'Call Class Code' row count is incorrect!");
        }
        if (account.getCorrespondingAccount() != null) {
            Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Corresponding Account") >= 1,
                    "'Corresponding Account' row count is incorrect!");
        }
    }

    /**
     * CHK account verification
     */

    public void verifyChkAccountRecords(Account account) {
        verifyGeneralRecords(account);
        Assert.assertEquals(Pages.accountMaintenancePage().getChangeTypeElementsCount("Interest Rate"), 1,
                "'Interest Rate' row count is incorrect!");
        Assert.assertEquals(Pages.accountMaintenancePage().getChangeTypeElementsCount("Statement Cycle"), 1,
                "'Statement Cycle' row count is incorrect!");
        Assert.assertEquals(Pages.accountMaintenancePage().getChangeTypeElementsCount("Charge or analyze"), 1,
                "'Charge or analyze' row count is incorrect!");
        Assert.assertEquals(Pages.accountMaintenancePage().getChangeTypeElementsCount("Account analysis"), 1,
                "'Account analysis' row count is incorrect!");
    }

    public void verifyChkAccountRecordsAfterEditing(Account account) {
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

    public void verifySavingsAccountRecords(Account account) {
        verifyGeneralRecords(account);
        Assert.assertEquals(Pages.accountMaintenancePage().getChangeTypeElementsCount("Interest Rate"), 1,
                "'Interest Rate' row count is incorrect!");
        Assert.assertEquals(Pages.accountMaintenancePage().getChangeTypeElementsCount("Statement Cycle"), 1,
                "'Statement Cycle' row count is incorrect!");
        Assert.assertEquals(Pages.accountMaintenancePage().getChangeTypeElementsCount("Mail Code"), 1,
                "'Mail Code' row count is incorrect!");
        Assert.assertEquals(Pages.accountMaintenancePage().getChangeTypeElementsCount("Interest Frequency"), 1,
                "'Interest Frequency' row count is incorrect!");
    }

    public void verifySavingsAccountRecordsAfterEditing(Account account) {
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

    public void verifySavingsIraAccountRecords(Account account) {
        verifyGeneralRecords(account);
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Interest Frequency") >= 1,
                "'Interest Frequency' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Statement Cycle") >= 1,
                "'Statement Cycle' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("IRA distribution amount") >= 1,
                "'IRA distribution amount' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Date next IRA distribution") >= 1,
                "'Date next IRA distribution' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("IRA Distribution Frequency") >= 1,
                "'IRA Distribution Frequency' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("IRA Distribution Code") >= 1,
                "'IRA Distribution Code' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("IRA Distribution Code") >= 1,
                "'Date Of First Deposit' row count is incorrect!");
    }

    public void verifySavingsIraAccountRecordsAfterEditing(Account account) {
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

    public void verifyRegularCdAccountRecords(Account account) {
        verifyGeneralRecords(account);
        Assert.assertEquals(Pages.accountMaintenancePage().getChangeTypeElementsCount("Interest Type"), 1,
                "'Interest Type' row count is incorrect!");
        Assert.assertEquals(Pages.accountMaintenancePage().getChangeTypeElementsCount("Apply Interest To"), 1,
                "'Apply Interest To' row count is incorrect!");
        Assert.assertEquals(Pages.accountMaintenancePage().getChangeTypeElementsCount("Interest Rate"), 1,
                "'Interest Rate' row count is incorrect!");
    }

    /**
     * CD IRA account verification
     */

    public void verifyCdIraAccountRecordsAfterEditing(Account account) {
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

    public void verifyCdIraAccountRecords(Account account) {
        verifyGeneralRecords(account);
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Interest Type") >= 1,
                "'Interest Type' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Interest Frequency") >= 1,
                "'Interest Frequency' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("IRA Distribution Frequency") >= 1,
                "'IRA Distribution Frequency' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("IRA Distribution Code") >= 1,
                "'IRA Distribution Code' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Apply Interest To") >= 1,
                "'Apply Interest To' row count is incorrect!");
    }

    /**
     * Dormant Account With No Monetary Transaction verification
     */
    public void verifyDormantAccountWithNoMonetaryTransactionRecords() {
        expandAllRows();
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Date Last Activity/Contact") >= 1,
                "'Date Last Activity/Contact' row count is incorrect!");
    }

    /**
     * Closed Account With No Monetary Transaction verification
     */

    public void verifyClosedAccountWithNoMonetaryTransactionRecords() {
        expandAllRows();
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Account Status") >= 1,
                "'Account Status' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Date Closed") >= 1,
                "'Date Closed' row count is incorrect!");
    }

    /**
     * Added Account Level Document verification
     */

    public void verifyAddedAccountLevelDocumentRecords() {
        expandAllRows();
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Drag and Drop Documents here") >= 1,
                "'Drag and Drop Documents here' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Category") >= 1,
                "'Category' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Parent Category") >= 1,
                "'Parent Category' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Notes") >= 1,
                "'Notes' row count is incorrect!");
    }

    /**
     * Edited Deleted Restored Account Level Document verification
     */

    public void verifyEditedAccountLevelDocumentRecords() {
        expandAllRows();
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Drag and Drop Documents here") >= 3,
                "'Drag and Drop Documents here' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Category") >= 4,
                "'Category' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Parent Category") >= 4,
                "'Parent Category' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Notes") >= 4,
                "'Notes' row count is incorrect!");
    }

    /**
     * Add Account Level Notes verification
     */

    public void verifyAddedAccountLevelNotesRecords() {
        expandAllRows();
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Due Date") >= 1,
                "'Due Date' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Expiration Date") >= 1,
                "'Expiration Date' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Subject") >= 1,
                "'Subject' row count is incorrect!");
    }

    /**
     * Edit Delete Account Level Notes verification
     */

    public void verifyEditDeleteAccountLevelNotesRecords() {
        expandAllRows();
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Responsible Officer") >= 2,
                "'Responsible Officer' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Due Date") >= 2,
                "'Due Date' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Expiration Date") >= 2,
                "'Expiration Date' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Subject") >= 2,
                "'Subject' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Severity") >= 2,
                "'Severity' row count is incorrect!");
    }

    /**
     * Added New Instruction verification
     */

    public void verifyAddedNewInstructionRecords() {
        expandAllRows();
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Instruction Type") >= 1,
                "'Instruction Type' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Notes") >= 1,
                "'Notes' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Expiration Date") >= 1,
                "'Expiration Date' row count is incorrect!");
    }

    /**
     * Edit / Delete special instruction verification
     */

    public void verifyEditDeleteSpecialInstructionRecords() {
        expandAllRows();
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Instruction Type") >= 2,
                "'Instruction Type' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Notes") >= 2,
                "'Notes' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Expiration Date") >= 2,
                "'Expiration Date' row count is incorrect!");
    }
}