package com.nymbus.actions.account;

import com.nymbus.newmodels.account.Account;
import com.nymbus.pages.Pages;
import org.testng.Assert;

public class AccountDetailsActions {

    public void clickMoreButton() {
        if (Pages.accountDetailsPage().isMoreButtonVisible()) {
            Pages.accountDetailsPage().clickMoreButton();
        }
    }

    public void clickLessButtonAndVerifyMoreIsVisible() {
        if (Pages.accountDetailsPage().isLessButtonVisible()) {
            Pages.accountDetailsPage().clickLessButton();
            Assert.assertTrue(Pages.accountDetailsPage().isMoreButtonVisible(), "More button is not visible");
        }
    }

    /**
     * Common account records verification
     */

    public void verifyCommonAccountRecords(Account account) {
        Assert.assertEquals(Pages.accountDetailsPage().getAccountTitleValue(), account.getAccountTitle(), "'Title' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getCurrentOfficerValue(), account.getCurrentOfficer(), "'Current Officer' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getProductValue(), account.getProduct(), "'Product' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getBankBranchValue(), account.getBankBranch(), "'Bank Branch' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getDateOpenedValue(), account.getDateOpened(), "'Date Opened' value does not match");
        if (account.getCallClassCode() != null) {
            Assert.assertEquals(Pages.accountDetailsPage().getCallClassCode(), account.getCallClassCode(), "'Call Class' value does not match");
        }
        if (!account.getProductType().equals("CHK Account") && account.getCorrespondingAccount() != null) {
            Assert.assertEquals(Pages.accountDetailsPage().getCorrespondingAccount(), account.getCorrespondingAccount(), "'Call Class' value does not match");
        }
    }

    /**
     * IRA account records verification
     */

    public void verifyCommonIraAccountRecords(Account account) {
        Assert.assertEquals(Pages.accountDetailsPage().getIraDistributionFrequency(), account.getIraDistributionFrequency(), "' IRA Distribution Frequency' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getIraDistributionCode(), account.getIraDistributionCode(), "' IRA Distribution Code' value does not match");
        if (!Pages.accountDetailsPage().getIraDistributionAmount().isEmpty()) {
            Assert.assertEquals(Pages.accountDetailsPage().getIraDistributionAmount(), account.getIraDistributionAmount(), "'IRA distribution amount' value does not match");
        }
        if (!Pages.accountDetailsPage().getDateNextIRADistribution().isEmpty()) {
            Assert.assertEquals(Pages.accountDetailsPage().getDateNextIRADistribution(), account.getDateNextIRADistribution(), "'Date next IRA distribution' value does not match");
        }
        if (!Pages.accountDetailsPage().getDateOfFirstDeposit().isEmpty()) {
            Assert.assertEquals(Pages.accountDetailsPage().getDateOfFirstDeposit(), account.getDateOfFirstDeposit(), "'Date Of First Deposit' value does not match");
        }
    }

    /**
     * CD account records verification
     */

    public void verifyCommonCdAccountRecords(Account account) {
        Assert.assertEquals(Pages.accountDetailsPage().getInterestFrequencyCode(), account.getInterestFrequency(), "'Interest Frequency' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getApplyInterestTo(), account.getApplyInterestTo(), "'Apply Interest To' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getInterestType(), account.getInterestType(), "'Interest Type' value does not match");
    }

    public void verifyEditedCdAccountRecords(Account account) {
        verifyCommonAccountRecords(account);
        Assert.assertEquals(Pages.accountDetailsPage().getAccountNumberValue(), account.getAccountNumber(), "'Account Number' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getInterestFrequencyCode(), account.getInterestFrequency(), "'Interest Frequency' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getApplyInterestTo(), account.getApplyInterestTo(), "'Apply Interest To' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getInterestType(), account.getInterestType(), "'Interest Type' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getInterestRateValue(), account.getInterestRate(), "'Interest Rate' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getFederalWHReason(), account.getFederalWHReason(), "'Federal WH Reason' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getFederalWHPercent(), account.getFederalWHPercent(), "'Federal WH Percent' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getUserDefinedField_1(), account.getUserDefinedField_1(), "'User Defined Field 1' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getUserDefinedField_2(), account.getUserDefinedField_2(), "'User Defined Field 2' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getUserDefinedField_3(), account.getUserDefinedField_3(), "'User Defined Field 3' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getUserDefinedField_4(), account.getUserDefinedField_4(), "'User Defined Field 4' value does not match");
    }

    /**
     * Checking account verification
     */

    public void verifyChkAccountRecords(Account account) {
        verifyCommonAccountRecords(account);
        Assert.assertEquals(Pages.accountDetailsPage().getStatementCycle(), account.getStatementCycle(), "'Statement Cycle' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getInterestRateValue(), account.getInterestRate(), "'Interest Rate' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getAccountAnalysisValue(), account.getAccountAnalysis(), "'Account Analysis' value does not match");
        if (account.getChargeOrAnalyze() != null) {
            Assert.assertEquals(Pages.accountDetailsPage().getChargeOrAnalyze(), account.getChargeOrAnalyze(), "'Charge or Analyze' value does not match");
        }
    }

    /**
     * Regular savings account verification
     */

    public void verifySavingsAccountRecords(Account account) {
        verifyCommonAccountRecords(account);
        Assert.assertEquals(Pages.accountDetailsPage().getStatementCycle(), account.getStatementCycle(), "'Statement Cycle' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getInterestRateValue(), account.getInterestRate(), "'Interest Rate' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getMailCodeValue(), account.getMailCode(), "'Mail Code' value does not match");
    }

    /**
     * Regular CD account verification
     */

    public void verifyCDAccountRecords(Account account) {
        verifyCommonAccountRecords(account);
        verifyCommonCdAccountRecords(account);
    }

    /**
     * Savings IRA account verification
     */

    public void verifySavingsIRAAccountRecords(Account account) {
        verifyCommonAccountRecords(account);
        verifyCommonIraAccountRecords(account);
        Assert.assertEquals(Pages.accountDetailsPage().getInterestFrequency(), account.getInterestFrequency(), "'Interest Frequency' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getStatementCycle(), account.getStatementCycle(), "'Statement Cycle' value does not match");
    }

    /**
     * CD IRA account verification
     */

    public void verifyCdIraAccountRecords(Account account) {
        verifyCommonAccountRecords(account);
        verifyCommonIraAccountRecords(account);
        verifyCommonCdAccountRecords(account);
    }
}
