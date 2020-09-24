package com.nymbus.actions.account;

import com.nymbus.core.utils.Generator;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.verifyingmodels.SafeDepositKeyValues;
import com.nymbus.pages.Pages;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class VerifyingAccountDataActions {

    public void verifyPredefinedFields() {
        SoftAssert softAssert = new SoftAssert();
        String empty = "";
        softAssert.assertNotEquals(Pages.addAccountPage().getAccountType(),
                 empty,
                "'Account type' is empty!");
        softAssert.assertNotEquals(Pages.addAccountPage().getAccountHolderName(),
                 empty,
                "'Account Holder name' is empty!");
        softAssert.assertNotEquals(Pages.addAccountPage().getAccountHolderRelationship(),
                 empty,
                "'Relationship' is empty!");
        softAssert.assertNotEquals(Pages.addAccountPage().getDateOpened(),
                 empty,
                "'Date' is empty!");
        softAssert.assertNotEquals(Pages.addAccountPage().getOriginatingOfficer(),
                 empty,
                "'Originating officer' is empty!");
        softAssert.assertNotEquals(Pages.addAccountPage().getDateOpened(),
                 empty,
                "'Date' is empty!");
        softAssert.assertNotEquals(Pages.addAccountPage().getCurrentOfficer(),
                 empty,
                "'Current officer' is empty!");
        softAssert.assertNotEquals(Pages.addAccountPage().getBankBranch(),
                 empty,
                "'Bank branch' is empty!");
        softAssert.assertAll();
    }

    public void verifyFieldsInViewMode(Account safeDepositBoxAccount) {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(Pages.accountDetailsPage().getBoxSizeValue(),
                 safeDepositBoxAccount.getBoxSize(),
                "'Box Size' value does not match");
        softAssert.assertEquals(Pages.accountDetailsPage().getRentalAmount(),
                 safeDepositBoxAccount.getRentalAmount(),
                "'Rental Amount' value does not match");
        softAssert.assertEquals(Pages.accountDetailsPage().getCurrentOfficerValue(),
                 safeDepositBoxAccount.getCurrentOfficer(),
                "'Current Officer' value does not match");
        softAssert.assertEquals(Pages.accountDetailsPage().getBankBranchValue(),
                 safeDepositBoxAccount.getBankBranch(),
                "'Bank Branch' value does not match");
        if (safeDepositBoxAccount.getCorrespondingAccount() != null) {
            softAssert.assertTrue(Pages.accountDetailsPage().getCorrespondingAccount().contains(safeDepositBoxAccount.getCorrespondingAccount()),
                     "'Corresponding Account' value does not match");
        }
        if (safeDepositBoxAccount.getDiscountReason() != null) {
            softAssert.assertEquals(Pages.accountDetailsPage().getDiscountReason(),
                     safeDepositBoxAccount.getDiscountReason(),
                    "'Discount Reason' value does not match");
        }
        softAssert.assertEquals(Pages.accountDetailsPage().getAccountTitleValue(),
                 safeDepositBoxAccount.getAccountTitle(),
                "'Title' value does not match");
        softAssert.assertEquals(Pages.accountDetailsPage().getUserDefinedField_1(),
                 safeDepositBoxAccount.getUserDefinedField_1(),
                "'User defined field 1' value does not match");
        softAssert.assertEquals(Pages.accountDetailsPage().getUserDefinedField_2(),
                 safeDepositBoxAccount.getUserDefinedField_2(),
                "'User defined field 2' value does not match");
        softAssert.assertEquals(Pages.accountDetailsPage().getUserDefinedField_3(),
                 safeDepositBoxAccount.getUserDefinedField_3(),
                "'User defined field 3' value does not match");
        softAssert.assertEquals(Pages.accountDetailsPage().getUserDefinedField_4(),
                 safeDepositBoxAccount.getUserDefinedField_4(),
                "'User defined field 4' value does not match");
        softAssert.assertEquals(Pages.accountDetailsPage().getDiscountPeriods(),
                 safeDepositBoxAccount.getDiscountPeriods(),
                "'Discount Periods' value does not match");
        softAssert.assertEquals(Pages.accountDetailsPage().getDateOpenedValue(),
                 safeDepositBoxAccount.getDateOpened(),
                "'Date Opened' value does not match");
        softAssert.assertAll();
    }

    public void verifyFieldsInEditMode(Account safeDepositBoxAccount) {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(Pages.editAccountPage().getBoxSize(),
                 safeDepositBoxAccount.getBoxSize(),
                "'Box Size' value does not match");
        softAssert.assertEquals(Pages.editAccountPage().getRentalAmount(),
                 safeDepositBoxAccount.getRentalAmount(),
                "'Rental Amount' value does not match");
        softAssert.assertEquals(Pages.editAccountPage().getCurrentOfficerValueInEditMode(),
                 safeDepositBoxAccount.getCurrentOfficer(),
                "'Current Officer' value does not match");
        softAssert.assertEquals(Pages.editAccountPage().getBankBranchValueInEditMode(),
                 safeDepositBoxAccount.getBankBranch(),
                "'Bank Branch' value does not match");
        if (safeDepositBoxAccount.getCorrespondingAccount() != null) {
            softAssert.assertTrue(Pages.editAccountPage().getCorrespondingAccount().contains(safeDepositBoxAccount.getCorrespondingAccount()),
                    "'Corresponding Account' value does not match");
        }
        if (safeDepositBoxAccount.getDiscountReason() != null) {
            softAssert.assertEquals(Pages.editAccountPage().getDiscountReason(),
                    safeDepositBoxAccount.getDiscountReason(),
                    "'Discount Reason' value does not match");
        }
        softAssert.assertEquals(Pages.editAccountPage().getAccountTitleValueInEditMode(),
                 safeDepositBoxAccount.getAccountTitle(),
                "'Title' value does not match");
        softAssert.assertEquals(Pages.editAccountPage().getUserDefinedField1(),
                 safeDepositBoxAccount.getUserDefinedField_1(),
                "'User defined field 1' value does not match");
        softAssert.assertEquals(Pages.editAccountPage().getUserDefinedField2(),
                 safeDepositBoxAccount.getUserDefinedField_2(),
                "'User defined field 2' value does not match");
        softAssert.assertEquals(Pages.editAccountPage().getUserDefinedField3(),
                 safeDepositBoxAccount.getUserDefinedField_3(),
                "'User defined field 3' value does not match");
        softAssert.assertEquals(Pages.editAccountPage().getUserDefinedField4(),
                 safeDepositBoxAccount.getUserDefinedField_4(),
                "'User defined field 4' value does not match");
        softAssert.assertEquals(Pages.editAccountPage().getDiscountPeriods(),
                 safeDepositBoxAccount.getDiscountPeriods(),
                "'Discount Periods' value does not match");
        softAssert.assertEquals(Pages.editAccountPage().getDateOpenedValueInEditMode(),
                 safeDepositBoxAccount.getDateOpened(),
                "'Date Opened' value does not match");
        softAssert.assertAll();
    }

    public void verifySafeDepositBoxAccountInformationOnMaintenanceTab(Account safeDepositBoxAcc, int rowsCount) {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(Pages.accountMaintenancePage().getRowNewValueByRowName("User Defined Field 4", 1),
                                safeDepositBoxAcc.getUserDefinedField_4(),
                                "User Defined Field 4 is incorrect !");
        softAssert.assertEquals(Pages.accountMaintenancePage().getRowNewValueByRowName("User Defined Field 3", 1),
                                safeDepositBoxAcc.getUserDefinedField_3(),
                                "User Defined Field 3 is incorrect !");
        softAssert.assertEquals(Pages.accountMaintenancePage().getRowNewValueByRowName("User Defined Field 2", 1),
                                safeDepositBoxAcc.getUserDefinedField_2(),
                                "User Defined Field 2 is incorrect !");
        softAssert.assertEquals(Pages.accountMaintenancePage().getRowNewValueByRowName("User Defined Field 1", 1),
                                safeDepositBoxAcc.getUserDefinedField_1(),
                                "User Defined Field 1 is incorrect !");
        softAssert.assertEquals(Pages.accountMaintenancePage().getRowNewValueByRowName("Rental Amount", 1),
                                safeDepositBoxAcc.getRentalAmount(),
                                "Rental Amount is incorrect !");
        softAssert.assertEquals(Pages.accountMaintenancePage().getRowNewValueByRowName("Box Size", 1),
                                safeDepositBoxAcc.getBoxSize(),
                                "Box Size is incorrect !");
        softAssert.assertTrue(Pages.accountMaintenancePage().getRowsCount() > rowsCount,
                                "Rows count is incorrect!");
        softAssert.assertAll();
    }

    public void setSafeDepositBoxSizeAndRentalAmount(Account safeDepositBoxAccount, List<SafeDepositKeyValues> safeDepositKeyValues) {
        int randomIndex = Generator.genInt(0, safeDepositKeyValues.size() - 1);
        safeDepositBoxAccount.setBoxSize(safeDepositKeyValues.get(randomIndex).getSafeBoxSize());
        safeDepositBoxAccount.setRentalAmount(String.format("%.2f", safeDepositKeyValues.get(randomIndex).getSafeBoxRentalAmount()));
    }

    /**
     * Common account verification
     */

    public void verifyAccountFieldsWithUpdatedDataInViewMode(Account account) {
        Assert.assertEquals(Pages.accountDetailsPage().getCurrentOfficerValue(), account.getCurrentOfficer(), "'Current Officer' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getBankBranchValue(), account.getBankBranch(), "'Bank Branch' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getFederalWHReason(), account.getFederalWHReason(), "'Federal WH Reason' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getUserDefinedField_1(), account.getUserDefinedField_1(), "'User Defined Field 1' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getUserDefinedField_2(), account.getUserDefinedField_2(), "'User Defined Field 2' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getUserDefinedField_3(), account.getUserDefinedField_3(), "'User Defined Field 3' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getUserDefinedField_4(), account.getUserDefinedField_4(), "'User Defined Field 4' value does not match");
        if (account.getCallClassCode() != null) {
            Assert.assertEquals(Pages.accountDetailsPage().getCallClassCode(), account.getCallClassCode(), "'Call Class' value does not match");
        }
    }

    /**
     * Checking account verification
     */

    public void verifyChkAccountFieldsWithUpdatedDataInViewMode(Account account) {
        verifyAccountFieldsWithUpdatedDataInViewMode(account);
        Assert.assertEquals(Pages.accountDetailsPage().getInterestRateValue(), account.getInterestRate(), "'Interest Rate' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getChargeOrAnalyze(), account.getChargeOrAnalyze(), "'Charge or Analyze' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getAutomaticOverdraftStatus(), account.getAutomaticOverdraftStatus(), "'Automatic Overdraft Status' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getNumberOfDebitCardsIssued(), account.getNumberOfDebitCardsIssued(), "'Number Of Debit Cards Issued' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getReasonDebitCardChargeWaived(), account.getReasonDebitCardChargeWaived(), "'Reason Debit Card Charge Waived' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getBankruptcyJudgement(), account.getBankruptcyJudgement(), "'Bankruptcy Judgement' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getReasonDebitCardChargeWaived(), account.getReasonDebitCardChargeWaived(), "'Reason Debit Card Charge Waived' value does not match");
    }

    /**
     * Savings account verification
     */

    public void verifySavingsAccountFieldsWithUpdatedDataInViewMode(Account account) {
        verifyAccountFieldsWithUpdatedDataInViewMode(account);
        Assert.assertEquals(Pages.accountDetailsPage().getReasonDebitCardChargeWaived(), account.getReasonDebitCardChargeWaived(), "'Reason Debit Card Charge Waived' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getPrintStatementNextUpdate(), account.getPrintStatementNextUpdate(), "'Print Statement Next Update' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getFederalWHPercent(), account.getFederalWHPercent(), "'Federal WH Percent' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getNumberOfDebitCardsIssued(), account.getNumberOfDebitCardsIssued(), "'Number Of Debit Cards Issued' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getInterestRateValue(), account.getInterestRate(), "'Interest Rate' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getStatementCycle(), account.getStatementCycle(), "'Statement Cycle' value does not match");
    }

    /**
     * Savings IRA account verification
     */

    public void verifySavingsIraAccountFieldsWithUpdatedDataInEditMode(Account account) {
        verifyAccountFieldsWithUpdatedDataInViewMode(account);
        Assert.assertEquals(Pages.accountDetailsPage().getReasonDebitCardChargeWaived(), account.getReasonDebitCardChargeWaived(), "'Reason Debit Card Charge Waived' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getPrintStatementNextUpdate(), account.getPrintStatementNextUpdate(), "'Print Statement Next Update' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getFederalWHPercent(), account.getFederalWHPercent(), "'Federal WH Percent' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getNumberOfDebitCardsIssued(), account.getNumberOfDebitCardsIssued(), "'Number Of Debit Cards Issued' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getStatementCycle(), account.getStatementCycle(), "'Statement Cycle' value does not match");
    }

    /**
     * Regular CD account verification
     */

    public void verifyRegularCdAccountFieldsWithUpdatedDataInEditMode(Account account) {
        verifyAccountFieldsWithUpdatedDataInViewMode(account);
        Assert.assertEquals(Pages.accountDetailsPage().getFederalWHPercent(), account.getFederalWHPercent(), "'Federal WH Percent' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getInterestRateValue(), account.getInterestRate(), "'Interest Rate' value does not match");
    }

    /**
     * CD IRA account verification
     */

    public void verifyCdIraAccountFieldsWithUpdatedDataInEditMode(Account account) {
        verifyAccountFieldsWithUpdatedDataInViewMode(account);
        Assert.assertEquals(Pages.accountDetailsPage().getDateOfFirstDeposit(), account.getDateOfFirstDeposit(), "'Date Of First Deposit' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getBirthDate(), account.getBirthDate(), "'Birth Date' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getDateDeceased(), account.getDateDeceased(), "'Date Deceased' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getInterestRateValue(), account.getInterestRate(), "'Interest Rate' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getApplyInterestTo(), account.getApplyInterestTo(), "'Apply Interest To' value does not match");
        if (account.getCorrespondingAccount() != null) {
            Assert.assertEquals(Pages.accountDetailsPage().getCorrespondingAccount(), account.getCorrespondingAccount(), "'Corresponding Account' value does not match");
        }
    }
}