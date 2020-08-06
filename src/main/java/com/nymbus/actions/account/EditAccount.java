package com.nymbus.actions.account;

import com.nymbus.newmodels.account.Account;
import com.nymbus.pages.Pages;
import org.testng.Assert;

import java.util.List;
import java.util.Random;

public class EditAccount {

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

    public void setCurrentOfficerWithJs(Account account) {
        Pages.editAccountPage().clickCurrentOfficerSelectorButtonWithJs();
        List<String> listOfCurrentOfficers = Pages.editAccountPage().getCurrentOfficerList();

        Assert.assertTrue(listOfCurrentOfficers.size() > 0, "There are no options available");
        if (account.getCurrentOfficer() == null) {
            account.setCurrentOfficer(listOfCurrentOfficers.get(new Random().nextInt(listOfCurrentOfficers.size())).trim());
        }
        Pages.editAccountPage().clickItemInDropDownWithJs(account.getCurrentOfficer());
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

    public void setBankBranchWithJs(Account account) {
        Pages.editAccountPage().clickBankBranchSelectorButtonWithJs();
        List<String> listOfBankBranchOptions = Pages.editAccountPage().getBankBranchList();

        Assert.assertTrue(listOfBankBranchOptions.size() > 0, "There are no options available");
        if (account.getBankBranch() == null) {
            account.setBankBranch(listOfBankBranchOptions.get(new Random().nextInt(listOfBankBranchOptions.size())).trim());
        }
        Pages.editAccountPage().clickItemInDropDownWithJs(account.getBankBranch());
    }

    public void setCallClassCode(Account account) {
        Pages.editAccountPage().clickCallClassCodeSelectorButton();
        List<String> listOfCallClassCode = Pages.editAccountPage().getCallClassCodeList();

        if (listOfCallClassCode.size() > 0) {
            if (account.getCallClassCode() == null) {
                account.setCallClassCode(listOfCallClassCode.get(new Random().nextInt(listOfCallClassCode.size())).trim());
            }
            Pages.editAccountPage().clickCallClassCodeSelectorOption(account.getCallClassCode());
        }
    }

    public void setCorrespondingAccount(Account account) {
        Pages.editAccountPage().clickCorrespondingAccountSelectorButton();
        List<String> listOfCorrespondingAccount = Pages.editAccountPage().getCorrespondingAccountList();

        if (listOfCorrespondingAccount.size() > 0) {
            if (account.getCorrespondingAccount() == null) {
                account.setCorrespondingAccount(listOfCorrespondingAccount.get(new Random().nextInt(listOfCorrespondingAccount.size())).trim());
            }
            Pages.editAccountPage().clickCorrespondingAccountSelectorOption(account.getCorrespondingAccount().replaceAll("[^0-9]", ""));
        }
    }

    public void setCorrespondingAccountWithJs(Account account) {
        Pages.editAccountPage().clickCorrespondingAccountSelectorButtonWithJs();
        List<String> listOfCorrespondingAccount = Pages.editAccountPage().getCorrespondingAccountList();

        Assert.assertTrue(listOfCorrespondingAccount.size() > 0, "There are no product types available");
        if (account.getCorrespondingAccount() == null) {
            account.setCorrespondingAccount(listOfCorrespondingAccount.get(new Random().nextInt(listOfCorrespondingAccount.size())).trim());
        }
        Pages.editAccountPage().clickItemInDropDownWithJs(account.getCorrespondingAccount().replaceAll("[^0-9]", ""));
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

    public void setMailCodeWithJs(Account account) {
        Pages.editAccountPage().clickMailCodeSelectorButtonWithJs();
        List<String> listOfMailCode = Pages.editAccountPage().getMailCodeList();

        Assert.assertTrue(listOfMailCode.size() > 0, "There are no options available");
        if (account.getMailCode() == null) {
            account.setMailCode(listOfMailCode.get(new Random().nextInt(listOfMailCode.size())).trim());
        }
        Pages.editAccountPage().clickItemInDropDownWithJs(account.getMailCode());
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

    public void setChargeOrAnalyze(Account account) {
        Pages.editAccountPage().clickChargeOrAnalyzeSelectorButton();
        List<String> listOfChargeOrAnalyze = Pages.editAccountPage().getChargeOrAnalyzeList();

        Assert.assertTrue(listOfChargeOrAnalyze.size() > 0, "There are no product types available");
        if (account.getChargeOrAnalyze() == null) {
            account.setChargeOrAnalyze(listOfChargeOrAnalyze.get(new Random().nextInt(listOfChargeOrAnalyze.size())).trim());
        }
        Pages.editAccountPage().clickChargeOrAnalyzeSelectorOption(account.getChargeOrAnalyze());
    }

    public void setBankruptcyJudgement(Account account) {
        Pages.editAccountPage().clickBankruptcyJudgementSelectorButton();
        List<String> listOfBankruptcyJudgement = Pages.editAccountPage().getBankruptcyJudgementList();

        Assert.assertTrue(listOfBankruptcyJudgement.size() > 0, "There are no product types available");
        if (account.getBankruptcyJudgement() == null) {
            account.setBankruptcyJudgement(listOfBankruptcyJudgement.get(new Random().nextInt(listOfBankruptcyJudgement.size())).trim());
        }
        Pages.editAccountPage().clickBankruptcyJudgementSelectorOption(account.getBankruptcyJudgement());
    }

    public void setStatementCycle(Account account) {
        Pages.editAccountPage().clickStatementCycleSelectorButton();
        List<String> listOfStatementCycle = Pages.editAccountPage().getStatementCycleList();

        Assert.assertTrue(listOfStatementCycle.size() > 0, "There are no product types available");
        if (account.getStatementCycle() == null) {
            account.setStatementCycle(listOfStatementCycle.get(new Random().nextInt(listOfStatementCycle.size())).trim());
        }
        Pages.editAccountPage().clickStatementCycleOption(account.getStatementCycle());
    }

    public void selectValuesInFieldsThatWereNotAvailableDuringCheckingAccountCreation(Account account) {
        setCurrentOfficer(account);
        setBankBranch(account);
        Pages.editAccountPage().setInterestRate(account.getInterestRate());
        setChargeOrAnalyze(account);
        setAutomaticOverdraftStatus(account);
        setFederalWHReason(account);
        Pages.editAccountPage().setFederalWHPercent(account.getFederalWHPercent());
        Pages.editAccountPage().setUserDefinedField_1(account.getUserDefinedField_1());
        Pages.editAccountPage().setUserDefinedField_2(account.getUserDefinedField_2());
        Pages.editAccountPage().setUserDefinedField_3(account.getUserDefinedField_3());
        Pages.editAccountPage().setUserDefinedField_4(account.getUserDefinedField_4());
        setCallClassCode(account);
        Pages.editAccountPage().setNumberOfDebitCardsIssued(account.getNumberOfDebitCardsIssued());
        setReasonDebitCardChargeWaived(account);
        setBankruptcyJudgement(account);
        if (Pages.editAccountPage().getApplySeasonalAddressSwitchValue().equals("YES")) {
            Pages.editAccountPage().clickApplySeasonalAddressSwitch();
        }
    }

    public void selectValuesInFieldsThatWereNotAvailableDuringSavingsAccountCreation(Account account) {
        setFederalWHReason(account);
        setReasonDebitCardChargeWaived(account);
        Pages.editAccountPage().setPrintStatementNextUpdate(account.getPrintStatementNextUpdate());
        Pages.editAccountPage().setFederalWHPercent(account.getFederalWHPercent());
        Pages.editAccountPage().setUserDefinedField_1(account.getUserDefinedField_1());
        Pages.editAccountPage().setUserDefinedField_2(account.getUserDefinedField_2());
        Pages.editAccountPage().setUserDefinedField_3(account.getUserDefinedField_3());
        Pages.editAccountPage().setUserDefinedField_4(account.getUserDefinedField_4());
        Pages.editAccountPage().setNumberOfDebitCardsIssued(account.getNumberOfDebitCardsIssued());
        setCurrentOfficer(account);
        if (!Pages.editAccountPage().isInterestRateDisabledInEditMode()) {
            Pages.editAccountPage().setInterestRate(account.getInterestRate());
        }
        setBankBranch(account);
        setCallClassCode(account);
        setStatementCycle(account);
        if (Pages.editAccountPage().getExemptFromRegCCSwitchValue().equals("no")) {
            Pages.editAccountPage().clickExemptFromRegCCSwitch();
        }
        if (Pages.editAccountPage().getNewAccountSwitchValue().equals("no")) {
            Pages.editAccountPage().clickNewAccountSwitch();
        }
        if (Pages.editAccountPage().getTransactionalAccountSwitchValue().equals("no")) {
            Pages.editAccountPage().clickTransactionalAccountSwitch();
        }
        if (Pages.editAccountPage().getApplySeasonalAddressSwitchValue().equals("yes")) {
            Pages.editAccountPage().clickApplySeasonalAddressSwitch();
        }
    }

    public void selectValuesInDropdownFieldsRequiredForSafeDepositBoxAccount(Account account) {
        AccountActions.editAccount().setCurrentOfficer(account);
        AccountActions.editAccount().setBankBranch(account);
        AccountActions.editAccount().setMailCode(account);
        AccountActions.editAccount().setCorrespondingAccount(account);
      /*  AccountActions.editAccount().setDiscountReason(account);*/
    }

    public void selectValuesInDropdownFieldsRequiredForSafeDepositBoxAccountWithJs(Account account) {
        AccountActions.editAccount().setCurrentOfficerWithJs(account);
        AccountActions.editAccount().setBankBranchWithJs(account);
        AccountActions.editAccount().setMailCodeWithJs(account);
        AccountActions.editAccount().setCorrespondingAccountWithJs(account);
        /*  AccountActions.editAccount().setDiscountReason(account);*/
    }

    public void fillInInputFieldsThatWereNotAvailableDuringCDAccountCreation(Account account) {
        setFederalWHReason(account);
        setCurrentOfficer(account);
        Pages.editAccountPage().setInterestRate(account.getInterestRate());
        setBankBranch(account);
        setCallClassCode(account);
        if (!Pages.editAccountPage().getTransactionalAccountInEditMode().equals("NO")) {
            Pages.editAccountPage().clickTransactionalAccountSwitch();
        }
        Pages.editAccountPage().setFederalWHPercent(account.getFederalWHPercent());
        Pages.editAccountPage().setUserDefinedField_1(account.getUserDefinedField_1());
        Pages.editAccountPage().setUserDefinedField_2(account.getUserDefinedField_2());
        Pages.editAccountPage().setUserDefinedField_3(account.getUserDefinedField_3());
        Pages.editAccountPage().setUserDefinedField_4(account.getUserDefinedField_4());
    }

    public void fillInInputFieldsThatWereNotAvailableDuringCDIRAAccountCreation(Account account) {
        AccountActions.editAccount().setFederalWHReason(account);
        Pages.editAccountPage().setDateOfFirstDeposit(account.getDateOfFirstDeposit());
        Pages.editAccountPage().setBirthDate(account.getBirthDate());
        Pages.editAccountPage().setDateDeceased(account.getDateDeceased());
        Pages.editAccountPage().setBankRoutingNumberInterestOnCDValue(account.getBankRoutingNumberInterestOnCD());
        Pages.editAccountPage().setUserDefinedField_1(account.getUserDefinedField_1());
        Pages.editAccountPage().setUserDefinedField_2(account.getUserDefinedField_2());
        Pages.editAccountPage().setUserDefinedField_3(account.getUserDefinedField_3());
        Pages.editAccountPage().setUserDefinedField_4(account.getUserDefinedField_4());
        setCurrentOfficer(account);
        if (!Pages.editAccountPage().isInterestRateDisabledInEditMode()) {
            Pages.editAccountPage().setInterestRate(account.getInterestRate());
        }
        setBankBranch(account);
        setCallClassCode(account);
        account.setApplyInterestTo("CHK Acct");
        setApplyInterestTo(account);
        setCorrespondingAccount(account);
        if (!Pages.editAccountPage().getTransactionalAccountInEditMode().equalsIgnoreCase("yes")) {
            Pages.editAccountPage().clickTransactionalAccountSwitch();
        }
        if (Pages.editAccountPage().getApplySeasonalAddressSwitchValue().equals("yes")) {
            Pages.editAccountPage().clickApplySeasonalAddressSwitch();
        }
    }

    public void editSavingsAccount(Account account) {
        Pages.accountDetailsPage().clickEditButton();
        AccountActions.editAccount().selectValuesInFieldsThatWereNotAvailableDuringSavingsAccountCreation(account);
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForEditButton();
    }

    public void editCDAccount(Account account) {
        Pages.accountDetailsPage().clickEditButton();
        AccountActions.editAccount().fillInInputFieldsThatWereNotAvailableDuringCDAccountCreation(account);
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

    public void activateAccount() {
        Pages.accountDetailsPage().clickActivateButton();
        Pages.accountDetailsPage().waitForStatusChangedToActive();
    }
}
