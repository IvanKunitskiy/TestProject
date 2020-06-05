package com.nymbus.actions.account;

import com.nymbus.newmodels.account.Account;
import com.nymbus.pages.Pages;
import org.testng.Assert;

import java.util.List;
import java.util.Random;

public class EditAccount {

    public void setWhenSurchargesRefunded(Account account) {
        Pages.editAccountPage().clickWhenSurchargesRefundedSelectorButton();
        List<String> listOfWhenSurchargesRefunded = Pages.editAccountPage().getWhenSurchargesRefundedList();

        Assert.assertTrue(listOfWhenSurchargesRefunded.size() > 0, "There are no options available.");
        if (account.getWhenSurchargesRefunded() == null) {
            account.setWhenSurchargesRefunded(listOfWhenSurchargesRefunded.get(new Random().nextInt(listOfWhenSurchargesRefunded.size())).trim());
        }
        Pages.editAccountPage().clickWhenSurchargesRefundedSelectorOption(account.getWhenSurchargesRefunded());
    }

    public void setReasonAutoOdChgWaived(Account account) {
        Pages.editAccountPage().clickReasonAutoOdChgWaivedSelectorButton();
        List<String> listOfReasonAutoOdChgWaived = Pages.editAccountPage().getReasonAutoOdChgWaivedList();

        Assert.assertTrue(listOfReasonAutoOdChgWaived.size() > 0, "There are no options available");
        if (account.getReasonAutoOdChgWaived() == null) {
            account.setReasonAutoOdChgWaived(listOfReasonAutoOdChgWaived.get(new Random().nextInt(listOfReasonAutoOdChgWaived.size())).trim());
        }
        Pages.editAccountPage().clickReasonAutoOdChgWaivedSelectorOption(account.getReasonAutoOdChgWaived());
    }

    public void setAutomaticOverdraftStatus(Account account) {
        Pages.editAccountPage().clickAutomaticOverdraftStatusSelectorButton();
        List<String> listOfClickAutomaticOverdraftStatus = Pages.editAccountPage().getAutomaticOverdraftStatusList();

        Assert.assertTrue(listOfClickAutomaticOverdraftStatus.size() > 0, "There are no options available");
        if (account.getAutomaticOverdraftStatus() == null) {
            account.setAutomaticOverdraftStatus(listOfClickAutomaticOverdraftStatus.get(new Random().nextInt(listOfClickAutomaticOverdraftStatus.size())).trim());
        }
        Pages.editAccountPage().clickAutomaticOverdraftStatusSelectorOption(account.getAutomaticOverdraftStatus());
    }

    public void setReasonDebitCardChargeWaived(Account account) {
        Pages.editAccountPage().clickReasonDebitCardChargeWaivedOptionSelectorButton();
        List<String> listOfReasonReasonDebitCardChargeWaived = Pages.editAccountPage().getReasonDebitCardChargeWaivedList();

        Assert.assertTrue(listOfReasonReasonDebitCardChargeWaived.size() > 0, "There are no options available");
        if (account.getReasonDebitCardChargeWaived() == null) {
            account.setReasonDebitCardChargeWaived(listOfReasonReasonDebitCardChargeWaived.get(new Random().nextInt(listOfReasonReasonDebitCardChargeWaived.size())).trim());
        }
        Pages.editAccountPage().clickReasonDebitCardChargeWaivedSelectorOption(account.getReasonDebitCardChargeWaived());
    }

    public void setReasonAutoNSFChgWaived(Account account) {
        Pages.editAccountPage().clickReasonAutoNSFChgWaivedSelectorButton();
        List<String> listOfReasonAutoNSFChgWaived = Pages.editAccountPage().getReasonAutoNSFChgWaivedList();

        Assert.assertTrue(listOfReasonAutoNSFChgWaived.size() > 0, "There are no options available");
        if (account.getReasonAutoNSFChgWaived() == null) {
            account.setReasonAutoNSFChgWaived(listOfReasonAutoNSFChgWaived.get(new Random().nextInt(listOfReasonAutoNSFChgWaived.size())).trim());
        }
        Pages.editAccountPage().clickReasonAutoNSFChgWaivedSelectorOption(account.getReasonAutoNSFChgWaived());
    }

    public void setOdProtectionAcct(Account account) {
        Pages.editAccountPage().clickOdProtectionAcctSelectorButton();
        List<String> listOfOdProtectionAcct = Pages.editAccountPage().getOdProtectionAcctList();

        Assert.assertTrue(listOfOdProtectionAcct.size() > 0, "There are no product types available");
        if (account.getOdProtectionAcct() == null) {
            account.setOdProtectionAcct(listOfOdProtectionAcct.get(new Random().nextInt(listOfOdProtectionAcct.size())).trim());
        }
        Pages.editAccountPage().clickOdProtectionAcctSelectorOption(account.getOdProtectionAcct().replaceAll("[^0-9]", ""));
    }

    public void setFederalWHReason(Account account) {
        Pages.editAccountPage().clickFederalWHReasonSelectorButton();
        List<String> listOfFederalWHReason = Pages.editAccountPage().getFederalWHReasonList();

        Assert.assertTrue(listOfFederalWHReason.size() > 0, "There are no options available");
        if (account.getFederalWHReason() == null) {
            account.setFederalWHReason(listOfFederalWHReason.get(new Random().nextInt(listOfFederalWHReason.size())).trim());
        }
        Pages.editAccountPage().clickFederalWHReasonSelectorOption(account.getFederalWHReason());
    }

    public void setReasonATMChargeWaived(Account account) {
        Pages.editAccountPage().clickReasonATMChargeWaivedSelectorButton();
        List<String> listOfReasonATMChargeWaived = Pages.editAccountPage().getReasonATMChargeWaivedList();

        Assert.assertTrue(listOfReasonATMChargeWaived.size() > 0, "There are no options available");
        if (account.getReasonATMChargeWaived() == null) {
            account.setReasonATMChargeWaived(listOfReasonATMChargeWaived.get(new Random().nextInt(listOfReasonATMChargeWaived.size())).trim());
        }
        Pages.editAccountPage().clickReasonATMChargeWaivedSelectorOption(account.getReasonATMChargeWaived());
    }

    public void setCurrentOfficer(Account account) {
        Pages.editAccountPage().clickCurrentOfficerSelectorButton();
        List<String> listOfCurrentOfficers = Pages.editAccountPage().getCurrentOfficerList();

        Assert.assertTrue(listOfCurrentOfficers.size() > 0, "There are no options available");
        if (account.getCurrentOfficer() == null) {
            account.setCurrentOfficer(listOfCurrentOfficers.get(new Random().nextInt(listOfCurrentOfficers.size())).trim());
        }
        Pages.editAccountPage().clickCurrentOfficerSelectorOption(account.getCurrentOfficer());
    }

    public void setBankBranch(Account account) {
        Pages.editAccountPage().clickBankBranchSelectorButton();
        List<String> listOfBankBranchOptions = Pages.editAccountPage().getBankBranchList();

        Assert.assertTrue(listOfBankBranchOptions.size() > 0, "There are no options available");
        if (account.getBankBranch() == null) {
            account.setBankBranch(listOfBankBranchOptions.get(new Random().nextInt(listOfBankBranchOptions.size())).trim());
        }
        Pages.editAccountPage().clickBankBranchOption(account.getBankBranch());
    }

    public void setCallClassCode(Account account) {
        Pages.editAccountPage().clickCallClassCodeSelectorButton();
        List<String> listOfCallClassCode = Pages.editAccountPage().getCallClassCodeList();

        Assert.assertTrue(listOfCallClassCode.size() > 0, "There are no options available");
        if (account.getCallClassCode() == null) {
            account.setCallClassCode(listOfCallClassCode.get(new Random().nextInt(listOfCallClassCode.size())).trim());
        }
        Pages.editAccountPage().clickCallClassCodeSelectorOption(account.getCallClassCode());
    }

    public void setCorrespondingAccount(Account account) {
        Pages.editAccountPage().clickCorrespondingAccountSelectorButton();
        List<String> listOfCorrespondingAccount = Pages.editAccountPage().getCorrespondingAccountList();

        Assert.assertTrue(listOfCorrespondingAccount.size() > 0, "There are no product types available");
        if (account.getCorrespondingAccount() == null) {
            account.setCorrespondingAccount(listOfCorrespondingAccount.get(new Random().nextInt(listOfCorrespondingAccount.size())).trim());
        }
        Pages.editAccountPage().clickCorrespondingAccountSelectorOption(account.getCorrespondingAccount().replaceAll("[^0-9]", ""));
    }

    public void setDiscountReason(Account account) {
        Pages.editAccountPage().clickDiscountReasonSelectorButton();
        List<String> listOfDiscountReason = Pages.editAccountPage().getDiscountReasonList();

        Assert.assertTrue(listOfDiscountReason.size() > 0, "There are no options available");
        if (account.getDiscountReason() == null) {
            account.setDiscountReason(listOfDiscountReason.get(new Random().nextInt(listOfDiscountReason.size())).trim());
        }
        Pages.editAccountPage().clickDiscountReasonSelectorOption(account.getDiscountReason());
    }

    public void setMailCode(Account account) {
        Pages.editAccountPage().clickMailCodeSelectorButton();
        List<String> listOfMailCode = Pages.editAccountPage().getMailCodeList();

        Assert.assertTrue(listOfMailCode.size() > 0, "There are no options available");
        if (account.getMailCode() == null) {
            account.setMailCode(listOfMailCode.get(new Random().nextInt(listOfMailCode.size())).trim());
        }
        Pages.editAccountPage().clickMailCodeSelectorOption(account.getMailCode());
    }

    public void setApplyInterestTo(Account account) {
        Pages.editAccountPage().clickApplyInterestToSelectorButton();
        List<String> listOfApplyInterestTo = Pages.editAccountPage().getApplyInterestToList();

        Assert.assertTrue(listOfApplyInterestTo.size() > 0, "There are no product types available");
        if (account.getApplyInterestTo() == null) {
            account.setApplyInterestTo(listOfApplyInterestTo.get(new Random().nextInt(listOfApplyInterestTo.size())).trim());
        }
        Pages.editAccountPage().clickApplyInterestToSelectorOption(account.getApplyInterestTo());
    }

    // TODO: Accounts page is under reconstruction. Check elements presence for commented out lines and delete or uncomment respectively.
    public void selectValuesInDropdownFieldsThatWereNotAvailableDuringCheckingAccountCreation(Account account) {
        setFederalWHReason(account);
//        setReasonATMChargeWaived(account);
//        setOdProtectionAcct(account);
//        setReasonAutoNSFChgWaived(account);
        setReasonDebitCardChargeWaived(account);
        setAutomaticOverdraftStatus(account);
//        setReasonAutoOdChgWaived(account);
//        setWhenSurchargesRefunded(account);
    }

    public void fillInInputFieldsThatWereNotAvailableDuringCheckingAccountCreation(Account account) {
        Pages.editAccountPage().setFederalWHPercent(account.getFederalWHPercent());
        Pages.editAccountPage().setNumberOfATMCardsIssued(account.getNumberOfATMCardsIssued());
        Pages.editAccountPage().setEarningCreditRate(account.getEarningCreditRate());
        Pages.editAccountPage().setUserDefinedField_1(account.getUserDefinedField_1());
        Pages.editAccountPage().setUserDefinedField_2(account.getUserDefinedField_2());
        Pages.editAccountPage().setUserDefinedField_3(account.getUserDefinedField_3());
        Pages.editAccountPage().setUserDefinedField_4(account.getUserDefinedField_4());
        Pages.editAccountPage().setNumberOfDebitCardsIssued(account.getNumberOfDebitCardsIssued());
        Pages.editAccountPage().setAutomaticOverdraftLimit(account.getAutomaticOverdraftLimit());
        Pages.editAccountPage().setCashCollDaysBeforeChg(account.getCashCollDaysBeforeChg());
        Pages.editAccountPage().setCashCollInterestRate(account.getCashCollInterestRate());
        Pages.editAccountPage().setCashCollInterestChg(account.getCashCollInterestChg());
        Pages.editAccountPage().setCashCollFloat(account.getCashCollFloat());
        Pages.editAccountPage().setPositivePay(account.getPositivePay());
    }

    public void selectValuesInDropdownFieldsThatWereNotAvailableDuringSavingsAccountCreation(Account account) {
        setFederalWHReason(account);
        setReasonATMChargeWaived(account);
        setReasonDebitCardChargeWaived(account);
    }

    public void selectValuesInDropdownFieldsRequiredForSafeDepositBoxAccount(Account account) {
        AccountActions.editAccount().setCurrentOfficer(account);
        AccountActions.editAccount().setBankBranch(account);
        AccountActions.editAccount().setMailCode(account);
        AccountActions.editAccount().setCorrespondingAccount(account);
      /*  AccountActions.editAccount().setDiscountReason(account);*/
    }

    public void fillInInputFieldsThatWereNotAvailableDuringSavingsAccountCreation(Account account) {
        Pages.editAccountPage().setPrintStatementNextUpdate(account.getPrintStatementNextUpdate());
        Pages.editAccountPage().setInterestPaidYTD(account.getInterestPaidYTD());
        Pages.editAccountPage().setFederalWHPercent(account.getFederalWHPercent());
        Pages.editAccountPage().setNumberOfATMCardsIssued(account.getNumberOfATMCardsIssued());
        Pages.editAccountPage().setUserDefinedField_1(account.getUserDefinedField_1());
        Pages.editAccountPage().setUserDefinedField_2(account.getUserDefinedField_2());
        Pages.editAccountPage().setUserDefinedField_3(account.getUserDefinedField_3());
        Pages.editAccountPage().setUserDefinedField_4(account.getUserDefinedField_4());
        Pages.editAccountPage().setNumberOfDebitCardsIssued(account.getNumberOfDebitCardsIssued());
    }

    public void fillInInputFieldsThatWereNotAvailableDuringCDAccountCreation(Account account) {
        Pages.editAccountPage().setFederalWHPercent(account.getFederalWHPercent());
        Pages.editAccountPage().setUserDefinedField_1(account.getUserDefinedField_1());
        Pages.editAccountPage().setUserDefinedField_2(account.getUserDefinedField_2());
        Pages.editAccountPage().setUserDefinedField_3(account.getUserDefinedField_3());
        Pages.editAccountPage().setUserDefinedField_4(account.getUserDefinedField_4());
    }

    public void fillInInputFieldsThatWereNotAvailableDuringCDIRAAccountCreation(Account account) {
        Pages.editAccountPage().setBankAccountNumberInterestOnCDValue(account.getBankAccountNumberInterestOnCD());
        Pages.editAccountPage().setBankRoutingNumberInterestOnCDValue(account.getBankRoutingNumberInterestOnCD());
        Pages.editAccountPage().setUserDefinedField_1(account.getUserDefinedField_1());
        Pages.editAccountPage().setUserDefinedField_2(account.getUserDefinedField_2());
        Pages.editAccountPage().setUserDefinedField_3(account.getUserDefinedField_3());
        Pages.editAccountPage().setUserDefinedField_4(account.getUserDefinedField_4());
    }

    public void selectValuesInDropdownFieldsRequiredForCDIRAAccount(Account account) {
        AccountActions.editAccount().setCurrentOfficer(account);
        Pages.editAccountPage().setInterestRate(account.getInterestRate());
        AccountActions.editAccount().setBankBranch(account);
        AccountActions.editAccount().setCallClassCode(account);
        AccountActions.editAccount().setApplyInterestTo(account);
        AccountActions.editAccount().setCorrespondingAccount(account);
    }

    public void editSavingsAccount(Account account) {
        Pages.accountDetailsPage().clickEditButton();
        AccountActions.editAccount().selectValuesInDropdownFieldsThatWereNotAvailableDuringSavingsAccountCreation(account);
        AccountActions.editAccount().fillInInputFieldsThatWereNotAvailableDuringSavingsAccountCreation(account);
        AccountActions.createAccount().setCurrentOfficer(account);
        AccountActions.createAccount().setBankBranch(account);
        AccountActions.createAccount().setCallClassCode(account);
        AccountActions.createAccount().setStatementCycle(account);
        Pages.editAccountPage().clickNewAccountSwitch();
        Pages.editAccountPage().clickTransactionalAccountSwitch();
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForEditButton();
    }

    public void editCDAccount(Account account) {
        Pages.accountDetailsPage().clickEditButton();
        AccountActions.editAccount().setFederalWHReason(account);
        AccountActions.editAccount().fillInInputFieldsThatWereNotAvailableDuringCDAccountCreation(account);
        AccountActions.createAccount().setCurrentOfficer(account);
        Pages.editAccountPage().setInterestRate(account.getInterestRate());
        AccountActions.createAccount().setBankBranch(account);
        AccountActions.createAccount().setCallClassCode(account);
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForEditButton();
    }

    public void editSafeDepositBoxAccount(Account account) {
        Pages.accountDetailsPage().clickEditButton();
        selectValuesInDropdownFieldsRequiredForSafeDepositBoxAccount(account);
        Pages.editAccountPage().setUserDefinedField_1(account.getUserDefinedField_1());
        Pages.editAccountPage().setUserDefinedField_2(account.getUserDefinedField_2());
        Pages.editAccountPage().setUserDefinedField_3(account.getUserDefinedField_3());
        Pages.editAccountPage().setUserDefinedField_4(account.getUserDefinedField_4());
        Pages.editAccountPage().setDateLastAccess(account.getDateLastAccess());
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForEditButton();
    }

    public void navigateToAccountDetails(String accountNumber, boolean isMoreButtonClick) {
        Pages.aSideMenuPage().clickClientMenuItem();

        Pages.clientsSearchPage().typeToClientsSearchInputField(accountNumber);

        Pages.clientsSearchPage().clickOnViewAccountButtonByValue(accountNumber);

        if (isMoreButtonClick) {
            Pages.accountDetailsPage().clickMoreButton();
        }
    }

    public void goToInstructionsTab() {
        Pages.accountDetailsPage().clickInstructionsTab();
    }

    public void goToDetailsTab() {
        Pages.accountDetailsPage().clickDetailsTab();
    }

    public void goToMaintenanceTab() {
        Pages.accountDetailsPage().clickMaintenanceTab();
    }

    public void goToMaintenanceHistory() {
        Pages.accountDetailsPage().clickMaintenanceTab();

        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();
    }

    public void closeAccount() {
        Pages.accountDetailsPage().clickCloseAccountButton();

        Pages.accountDetailsPage().waitForReopenButton();
    }
}
