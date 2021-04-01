package com.nymbus.actions.account;

import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.account.Account;
import com.nymbus.pages.Pages;
import org.testng.Assert;

import java.util.List;
import java.util.Random;

public class EditAccount {

    /**
     * Set data in dropdown fields on edit account page
     */

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

    public void setDaysBaseYearBase(Account account) {
        Pages.editAccountPage().clickDaysBaseYearBaseSelectorButton();
        List<String> listOfDaysBaseYearBase = Pages.editAccountPage().getDaysBaseYearBaseList();

        Assert.assertTrue(listOfDaysBaseYearBase.size() > 0, "There are no 'Days Base/Year Base' options available");
        if (account.getDaysBaseYearBase() == null) {
            account.setDaysBaseYearBase(listOfDaysBaseYearBase.get(new Random().nextInt(listOfDaysBaseYearBase.size())).trim());
        }
        Pages.editAccountPage().clickDaysBaseYearBaseSelectorOption(account.getDaysBaseYearBase());
    }

    public void setRateChangeFrequency(Account account) {
        Pages.editAccountPage().clickRateChangeFrequencySelectorButton();
        List<String> listOfRateChangeFrequency = Pages.editAccountPage().getRateChangeFrequencyList();

        Assert.assertTrue(listOfRateChangeFrequency.size() > 0, "There are no product types available");
        if (account.getRateChangeFrequency() == null) {
            account.setRateChangeFrequency(listOfRateChangeFrequency.get(new Random().nextInt(listOfRateChangeFrequency.size())).trim());
        }
        Pages.editAccountPage().clickRateChangeFrequencySelectorOption(account.getRateChangeFrequency());
    }

    public void setPaymentChangeFrequency(Account account) {
        Pages.editAccountPage().clickPaymentChangeFrequencySelectorButton();
        List<String> listOfPaymentChangeFrequency = Pages.editAccountPage().getPaymentChangeFrequencyList();

        Assert.assertTrue(listOfPaymentChangeFrequency.size() > 0, "There are no product types available");
        if (account.getPaymentChangeFrequency() == null) {
            account.setPaymentChangeFrequency(listOfPaymentChangeFrequency.get(new Random().nextInt(listOfPaymentChangeFrequency.size())).trim());
        }
        Pages.editAccountPage().clickPaymentChangeFrequencySelectorOption(account.getPaymentChangeFrequency());
    }

    public void setRateIndex(Account account) {
        Pages.editAccountPage().clickRateIndexSelectorButton();
        List<String> listOfRateIndex = Pages.editAccountPage().getRateIndexList();

        Assert.assertTrue(listOfRateIndex.size() > 0, "There are no product types available");
        if (account.getRateIndex() == null) {
            account.setRateIndex(listOfRateIndex.get(new Random().nextInt(listOfRateIndex.size())).trim());
        }
        Pages.editAccountPage().clickRateIndexSelectorOption(account.getRateIndex());
    }

    public void setRateRoundingMethod(Account account) {
        Pages.editAccountPage().clickRateRoundingMethodSelectorButton();
        List<String> listOfRateRoundingMethod = Pages.editAccountPage().getRateRoundingMethodList();

        Assert.assertTrue(listOfRateRoundingMethod.size() > 0, "There are no product types available");
        if (account.getRateRoundingMethod() == null) {
            account.setRateRoundingMethod(listOfRateRoundingMethod.get(new Random().nextInt(listOfRateRoundingMethod.size())).trim());
        }
        Pages.editAccountPage().clickRateRoundingMethodSelectorOption(account.getRateRoundingMethod());
    }

    /**
     * Edit account after it was created and set values that were not available during creation
     */

    private void setUserDefinedFields(Account account) {
        Pages.editAccountPage().setUserDefinedField_1(account.getUserDefinedField_1());
        Pages.editAccountPage().setUserDefinedField_2(account.getUserDefinedField_2());
        Pages.editAccountPage().setUserDefinedField_3(account.getUserDefinedField_3());
        Pages.editAccountPage().setUserDefinedField_4(account.getUserDefinedField_4());
    }

    private void setFieldsThatWereNotAvailableDuringAccountCreation(Account account) {
        setCurrentOfficer(account);
        setBankBranch(account);
        setFederalWHReason(account);
        setCallClassCode(account);
        if (!Pages.editAccountPage().isInterestRateDisabledInEditMode()) {
            Pages.editAccountPage().setInterestRate(account.getInterestRate());
        }
    }

    public void selectValuesInFieldsThatWereNotAvailableDuringCheckingAccountCreation(Account account) {
        setFieldsThatWereNotAvailableDuringAccountCreation(account);
        setUserDefinedFields(account);
        setChargeOrAnalyze(account);
        setAutomaticOverdraftStatus(account);
        Pages.editAccountPage().setFederalWHPercent(account.getFederalWHPercent());
        Pages.editAccountPage().setNumberOfDebitCardsIssued(account.getNumberOfDebitCardsIssued());
        setReasonDebitCardChargeWaived(account);
        setBankruptcyJudgement(account);
        if (Pages.editAccountPage().getApplySeasonalAddressSwitchValue().equals("YES")) {
            Pages.editAccountPage().clickApplySeasonalAddressSwitch();
        }
    }

    public void selectValuesInFieldsThatWereNotAvailableDuringSavingsAccountCreation(Account account) {
        setFieldsThatWereNotAvailableDuringAccountCreation(account);
        setUserDefinedFields(account);
        setReasonDebitCardChargeWaived(account);
        Pages.editAccountPage().setPrintStatementNextUpdate(account.getPrintStatementNextUpdate());
        Pages.editAccountPage().setFederalWHPercent(account.getFederalWHPercent());
        Pages.editAccountPage().setNumberOfDebitCardsIssued(account.getNumberOfDebitCardsIssued());
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

    public void fillInInputFieldsThatWereNotAvailableDuringCDAccountCreation(Account account) {
        setFieldsThatWereNotAvailableDuringAccountCreation(account);
        setUserDefinedFields(account);
        if (!Pages.editAccountPage().getTransactionalAccountInEditMode().equals("NO")) {
            Pages.editAccountPage().clickTransactionalAccountSwitch();
        }
        Pages.editAccountPage().setFederalWHPercent(account.getFederalWHPercent());
    }

    public void fillInInputFieldsThatWereNotAvailableDuringCDIRAAccountCreation(Account account) {
        setFieldsThatWereNotAvailableDuringAccountCreation(account);
        setUserDefinedFields(account);
        Pages.editAccountPage().setDateOfFirstDeposit(account.getDateOfFirstDeposit());
        Pages.editAccountPage().setBirthDate(account.getBirthDate());
        Pages.editAccountPage().setDateDeceased(account.getDateDeceased());
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

    public void fillInPaymentChangeInputFieldsInLoanAccountEditMode(Account account) {
        AccountActions.editAccount().setPaymentChangeFrequency(account);
        AccountActions.editAccount().setRateChangeFrequency(account);
        Pages.editAccountPage().setNextRateChangeDate(account.getNextRateChangeDate());
        Pages.editAccountPage().setRateChangeLeadDays(account.getRateChangeLeadDays());
        Pages.editAccountPage().setNextPaymentChangeDate(account.getNextPaymentChangeDate());
        Pages.editAccountPage().setPaymentChangeLeadDays(account.getPaymentChangeLeadDays());
        AccountActions.editAccount().setRateIndex(account);
        Pages.editAccountPage().setRateMargin(account.getRateMargin());
        Pages.editAccountPage().setMinRate(account.getMinRate());
        Pages.editAccountPage().setMaxRate(account.getMaxRate());
        Pages.editAccountPage().setMaxRateChangeUpDown(account.getMaxRateChangeUpDown());
        Pages.editAccountPage().setMaxRateLifetimeCap(account.getMaxRateLifetimeCap());
        Pages.editAccountPage().setRateRoundingFactor(account.getRateRoundingFactor());
        AccountActions.editAccount().setRateRoundingMethod(account);
        Pages.editAccountPage().setOriginalInterestRate(account.getOriginalInterestRate());
    }

    public void selectValuesInDropdownFieldsRequiredForSafeDepositBoxAccount(Account account) {
        setCurrentOfficer(account);
        setBankBranch(account);
        setMailCode(account);
        setCorrespondingAccount(account);
      /*  setDiscountReason(account);*/
    }

    public void selectValuesInDropdownFieldsRequiredForSafeDepositBoxAccountWithJs(Account account) {
        setCurrentOfficerWithJs(account);
        setBankBranchWithJs(account);
        setMailCodeWithJs(account);
        setCorrespondingAccountWithJs(account);
        /*  setDiscountReason(account);*/
    }

    public void editSavingsAccount(Account account) {
        Pages.accountDetailsPage().clickEditButton();
        selectValuesInFieldsThatWereNotAvailableDuringSavingsAccountCreation(account);
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForEditButton();
    }

    public void editCDAccount(Account account) {
        Pages.accountDetailsPage().clickEditButton();
        fillInInputFieldsThatWereNotAvailableDuringCDAccountCreation(account);
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForEditButton();
    }

    public void editSafeDepositBoxAccount(Account account) {
        Pages.accountDetailsPage().clickEditButton();
        selectValuesInDropdownFieldsRequiredForSafeDepositBoxAccount(account);
        setUserDefinedFields(account);
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

    public void verifyFieldGroupsAreVisible() {
        Assert.assertTrue(Pages.editAccountPage().isBalanceAndInterestGroupVisible(), "'Balance and Interest' group is not visible");
        Assert.assertTrue(Pages.editAccountPage().isTransactionsGroupVisible(), "'Transactions' group is not visible");
        Assert.assertTrue(Pages.editAccountPage().isOverdraftGroupVisible(), "'Overdraft' group is not visible");
        Assert.assertTrue(Pages.editAccountPage().isMiscGroupVisible(), "'Misc' group is not visible");
    }

    /**
     * Common account fields verification
     */

    private void verifyGeneralFieldsAreDisabledForEditing() {
        Assert.assertTrue(Pages.editAccountPage().isProductTypeFieldDisabledInEditMode(), "'Product Type' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAccountNumberFieldDisabledInEditMode(), "'Account Number' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAccountTypeFieldDisabledInEditMode(), "'Account Type' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isOriginatingOfficerFieldDisabledInEditMode(), "'Originating Officer' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAccountStatusFieldDisabledInEditMode(), "'Account Status' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isDateOpenedFieldDisabledInEditMode(), "'Date Opened' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isDateClosedFieldDisabledInEditMode(), "'Date Closed field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAnnualPercentageYieldFieldDisabledInEditMode(), "'Annual Percentage Yield' field is not disabled");
    }

    private void verifyGeneralFieldsWithUpdatedDataInEditMode(Account account) {
        Assert.assertEquals(Pages.editAccountPage().getCurrentOfficerValueInEditMode(), account.getCurrentOfficer(), "'Current Officer' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getBankBranchValueInEditMode(), account.getBankBranch(), "'Bank Branch' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getFederalWHReasonInEditMode(), account.getFederalWHReason(), "'Federal WH Reason' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getUserDefinedField1(), account.getUserDefinedField_1(), "'User Defined Field 1' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getUserDefinedField2(), account.getUserDefinedField_2(), "'User Defined Field 2' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getUserDefinedField3(), account.getUserDefinedField_3(), "'User Defined Field 3' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getUserDefinedField4(), account.getUserDefinedField_4(), "'User Defined Field 4' value does not match");
        if (account.getCallClassCode() != null) {
            Assert.assertEquals(Pages.editAccountPage().getCallClassCodeValueInEditMode(), account.getCallClassCode(), "'Call Class' value does not match");
        }
    }

    private void verifyGeneralAccountFieldsAfterCreationInEditMode(Account account) {
        Assert.assertEquals(Pages.editAccountPage().getAccountTitleValueInEditMode(), account.getAccountTitle(), "'Title' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getBankBranchValueInEditMode(), account.getBankBranch(), "'Bank Branch' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getProductValueInEditMode(), account.getProduct(), "'Product' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getCurrentOfficerValueInEditMode(), account.getCurrentOfficer(), "'Current Officer' value does not match");
        if (account.getCallClassCode() != null) {
            Assert.assertEquals(Pages.editAccountPage().getCallClassCodeValueInEditMode(), account.getCallClassCode(), "'Call Class' value does not match");
        }
    }

    /**
     * Common fields for specific account types verification
     */

    public void verifyGeneralCdAccountFieldsAfterCreationInEditMode(Account account) {
        Assert.assertEquals(Pages.editAccountPage().getDateOpenedValueInEditMode(), account.getDateOpened(), "'Date Opened' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getInterestFrequencyCode(), account.getInterestFrequency(), "'Interest Frequency' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getApplyInterestTo(), account.getApplyInterestTo(), "'Apply Interest To' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getInterestType(), account.getInterestType(), "'Interest Type' value does not match");
        if (account.getCorrespondingAccount() != null) {
            Assert.assertEquals(Pages.editAccountPage().getCorrespondingAccount(), account.getCorrespondingAccount(), "'Corresponding Account' value does not match");
        }
    }

    public void verifyGeneralSavingsIraFieldsAfterCreationInEditMode(Account account) {
        Assert.assertEquals(Pages.editAccountPage().getStatementCycleValueInEditMode(), account.getStatementCycle(), "'Statement Cycle' value does not match");
        if (account.getCorrespondingAccount() != null) {
            Assert.assertEquals(Pages.editAccountPage().getCorrespondingAccount(), account.getCorrespondingAccount(), "'Corresponding Account' value does not match");
        }
        Assert.assertEquals(Pages.editAccountPage().getDateOpenedValueInEditMode(), account.getDateOpened(), "'Date Opened' value does not match");
    }

    public void verifyGeneralSavingsAccountFieldsWithUpdatedDataInEditMode(Account account) {
        verifyGeneralFieldsWithUpdatedDataInEditMode(account);
        Assert.assertEquals(Pages.editAccountPage().getFederalWHPercent(), account.getFederalWHPercent(), "'Federal WH Percent' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getNumberOfDebitCardsIssued(), account.getNumberOfDebitCardsIssued(), "'Number Of Debit Cards Issued' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getReasonDebitCardChargeWaived(), account.getReasonDebitCardChargeWaived(), "'Reason Debit Card Charge Waived' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getPrintStatementNextUpdate(), account.getPrintStatementNextUpdate(), "'Print Statement Next Update' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getStatementCycleValueInEditMode(), account.getStatementCycle(), "'Statement Cycle' value does not match");
    }

    public void verifyGeneralSavingsAccountFieldsAreDisabledForEditing() {
        Assert.assertTrue(Pages.editAccountPage().isSpecialMailingInstructionsDisabledInEditMode(), "'Special Mailing Instructions field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAverageBalanceDisabledInEditMode(), "'Average Balance' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isLowBalanceThisStatementCycleDisabledInEditMode(), "'Low Balance This Statement Cycle' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isBalanceLastStatementDisabledInEditMode(), "'Balance Last Statement' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isYtdAverageBalanceDisabledInEditMode(), "'Average Balance' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isDateLastStatementDisabledInEditMode(), "'Date Last Statement' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isPreviousStatementDateDisabledInEditMode(), "'Previous Statement Date' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isPreviousStatementBalanceDisabledInEditMode(), "'Previous Statement Balance' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isInterestPaidLastYearDisabledInEditMode(), "'Interest Paid Last Year' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isMonthlyLowBalanceDisabledInEditMode(), "'Monthly low balance' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isMonthlyNumberOfWithdrawalsDisabledInEditMode(), "'Monthly number of withdrawals' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAggregateBalanceYTDDisabledInEditMode(), "'Aggregate Balance Year to date' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAggregateColBalDisabledInEditMode(), "'Aggregate col bal' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAggrColLstStmtDisabledInEditMode(), "'Aggr col lst stmt' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isYtdAggrColBalDisabledInEditMode(), "'YTD aggr col bal' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isDateLastActivityContactDisabledInEditMode(), "'Date Last Activity/Contact' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isNumberOfDepositsThisStatementCycleDisabledInEditMode(), "'Number Of Deposits This Statement Cycle' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isNumberOfDebitsThisStatementCycleDisabledInEditMode(), "'Number of Debits This Statement Cycle' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isLastDebitAmountFieldDisabledInEditMode(), "'Last Debit Amount' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isLastDepositAmountDisabledInEditMode(), "'Last Deposit Amount' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isNumberRegDItemsDisabledInEditMode(), "'Number Reg D items (6)' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isYtdChargesWaivedDisabledInEditMode(), "'YTD charges waived' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isServiceChargesYTDDisabledInEditMode(), "'Service charges YTD' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAggrOdBalanceDisabledInEditMode(), "'Aggr OD balance' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAggrColOdLstStmtDisabledInEditMode(), "'Aggr col OD lst stmt' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAggrOdLstStmtDisabledInEditMode(), "'Aggr OD lst stmt' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAggrColOdBalDisabledInEditMode(), "'Aggr col OD bal' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isTaxesWithheldYTDDisabledInEditMode(), "'Taxes Withheld YTD' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isOneDayFloatDisabledInEditMode(), "'1 day float' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isTwoDayFloatDisabledInEditMode(), "'2 day float' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isThreeDayFloatDisabledInEditMode(), "'3 day float' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isFourDayFloatDisabledInEditMode(), "'4 day float' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isFiveDayFloatDisabledInEditMode(), "'5 day float' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isTotalEarningsForLifeOfAccountDisabledInEditMode(), "'Total Earnings for Life of Account' field is not disabled");
    }

    public void verifyGeneralCdAccountFieldsAreDisabledForEditing() {
        Assert.assertTrue(Pages.editAccountPage().isProductFieldDisabledInEditMode(), "'Product' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAccruedInterestDisabledInEditMode(), "'Accrued Interest' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isDailyInterestAccrualDisabledInEditMode(), "'Daily Interest Accrual' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAmountLastInterestPaidDisabledInEditMode(), "'Amount Last Interest Paid' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isDateLastInterestPaidDisabledInEditMode(), "'Date Last Interest Paid' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isDateNextInterestDisabledInEditMode(), "'Date next interest' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isNextInterestPaymentAmountDisabledInEditMode(), "'Next Interest Payment Amount' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isInterestPaidYTDDisabledInEditMode(), "'Interest paid year to date' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isOriginalBalanceDisabledInEditMode(), "'Original Balance' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isTermTypeMonthsDisabledInEditMode(), "'Term Type Months' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isMaturityDateDisabledInEditMode(), "'Maturity Date' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isPenaltyAmountYTDDisabledInEditMode(), "'Penalty Amount Year-to-date' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isBalanceAtRenewalDisabledInEditMode(), "'Balance At Renewal' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isDateOfRenewalDisabledInEditMode(), "'Date of renewal' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isInterestRateAtRenewalDisabledInEditMode(), "'Interest rate at renewal' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isRenewalAmountDisabledInEditMode(), "'Renewal amount' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isTaxesWithheldYTDDisabledInEditMode(), "'Taxes Withheld YTD' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isBalanceAtEndOfYearDisabledInEditMode(), "'Balance at end of year' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAccruedInterestAtEndOfYearDisabledInEditMode(), "'Accrued interest at end of year' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isInterestPaidLastYearDisabledInEditMode(), "'Interest Paid Last Year' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isPrintInterestNoticeOverrideDisabledInEditMode(), "'Print interest notice override' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isTotalEarningsDisabledInEditMode(), "'Total Earnings for Life of Account' field is not disabled");
    }

    public void verifyGeneralIraAccountFieldsAreDisabledForEditing(Account account) {
        Assert.assertEquals(Pages.editAccountPage().getIraDistributionFrequencyInEditMode(), account.getIraDistributionFrequency(), "' IRA Distribution Frequency' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getIraDistributionCodeInEditMode(), account.getIraDistributionCode(), "' IRA Distribution Code' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getIraDistributionAmountInEditMode(), account.getIraDistributionAmount(), "'IRA distribution amount' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getDateOfFirstDeposit(), account.getDateOfFirstDeposit(), "'Date Of First Deposit' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getDateNextIRADistributionInEditMode(), account.getDateNextIRADistribution(), "'Date next IRA distribution' value does not match");
    }

    /**
     * Checking account verification
     */

    public void verifyChkAccountFieldsAreDisabledForEditing() {
        verifyGeneralFieldsAreDisabledForEditing();
        Assert.assertTrue(Pages.editAccountPage().isDateLastDebitDisabledInEditMode(), "'Date Last Debit' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isNumberOfDebitsThisStatementCycleDisabledInEditMode(), "'Date Last Debit' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isDaysOverdraftFieldDisabledInEditMode(), "'Times Overdrawn-6 Months' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isDaysOverdraftAboveLimitFieldDisabledInEditMode(), "'Times $5000 Overdrawn-6 Months' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isLastDebitAmountFieldDisabledInEditMode(), "'Last Debit Amount' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAutomaticOverdraftLimitFieldDisabledInEditMode(), "'Automatic Overdraft Limit Field' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isTotalEarningsFieldDisabledInEditMode(), "'Total Earnings' field is not disabled");
    }

    public void verifyChkAccountFieldsWithUpdatedDataInEditMode(Account account) {
        verifyGeneralFieldsWithUpdatedDataInEditMode(account);
        Assert.assertEquals(Pages.editAccountPage().getInterestRateValueInEditMode(), account.getInterestRate(), "'Interest Rate' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getChargeOrAnalyzeInEditMode(), account.getChargeOrAnalyze(), "'Charge or Analyze' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getAutomaticOverdraftStatus(), account.getAutomaticOverdraftStatus(), "'Automatic Overdraft Status' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getFederalWHPercent(), account.getFederalWHPercent(), "'Federal WH Percent' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getNumberOfDebitCardsIssued(), account.getNumberOfDebitCardsIssued(), "'Number Of Debit Cards Issued' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getReasonDebitCardChargeWaived(), account.getReasonDebitCardChargeWaived(), "'Reason Debit Card Charge Waived' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getBankruptcyJudgement(), account.getBankruptcyJudgement(), "'Bankruptcy Judgement' value does not match");
    }

    public void verifyChkAccountFieldsAfterCreationInEditMode(Account account) {
        verifyGeneralAccountFieldsAfterCreationInEditMode(account);
        Assert.assertEquals(Pages.editAccountPage().getStatementCycleValueInEditMode(), account.getStatementCycle(), "'Statement Cycle' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getAccountAnalysisValueInEditMode(), account.getAccountAnalysis(), "'Account Analysis' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getInterestRateValueInEditMode(), account.getInterestRate(), "'Interest Rate' value does not match");
        if (account.getChargeOrAnalyze() != null) {
            Assert.assertEquals(Pages.editAccountPage().getChargeOrAnalyzeInEditMode(), account.getChargeOrAnalyze(), "'Charge or Analyze' value does not match");
        }
    }

    /**
     * Savings account verification
     */

    public void verifySavingsAccountFieldsAreDisabledForEditing() {
        verifyGeneralFieldsAreDisabledForEditing();
        verifyGeneralSavingsAccountFieldsAreDisabledForEditing();
        Assert.assertTrue(Pages.editAccountPage().isDateLastDebitDisabledInEditMode(), "'Date Last Debit' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isDateLastDepositDisabledInEditMode(), "'Date Last Deposit' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isDateOfFirstDepositDisabledInEditMode(), "'Date Of First Deposit' field is not disabled");
    }

    public void verifySavingsAccountFieldsWithUpdatedDataInEditMode(Account account) {
        verifyGeneralFieldsWithUpdatedDataInEditMode(account);
        verifyGeneralSavingsAccountFieldsWithUpdatedDataInEditMode(account);
        Assert.assertEquals(Pages.editAccountPage().getInterestRateValueInEditMode(), account.getInterestRate(), "'Interest Rate' value does not match");
    }

    public void verifySavingsAccountFieldsAfterCreationInEditMode(Account account) {
        verifyGeneralAccountFieldsAfterCreationInEditMode(account);
        verifyGeneralSavingsIraFieldsAfterCreationInEditMode(account);
        Assert.assertEquals(Pages.editAccountPage().getMailCode(), account.getMailCode(), "'Mail Code' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getInterestRateValueInEditMode(), account.getInterestRate(), "'Interest Rate' value does not match");
    }

    /**
     * Savings IRA account verification
     */

    public void verifySavingsIraAccountFieldsAreDisabledForEditing() {
        verifyGeneralFieldsAreDisabledForEditing();
        verifyGeneralSavingsAccountFieldsAreDisabledForEditing();
        Assert.assertTrue(Pages.editAccountPage().isInterestRateDisabledInEditMode(), "'Interest Rate' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isInterestLastPaidDisabledInEditMode(), "'Interest Last Paid' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAccruedInterestThisStatementCycleDisabledInEditMode(),"'Accrued Interest This Statement Cycle' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isLastDebitAmountFieldDisabledInEditMode(), "'Last Debit Amount' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isLastDepositAmountDisabledInEditMode(), "'Last Deposit Amount' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAmountLastIRADistributionDisabledInEditMode(), "'Amount last IRA distribution' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isDateLastIRADistributionDisabledInEditMode(), "'Date last IRA distribution' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isIRADistributionsYTDDisabledInEditMode(), "'IRA distributions YTD' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isTotalEarningsForLifeOfAccountDisabledInEditMode(), "'Total Earnings For Life Of Account' field is not disabled");
    }

    public void verifySavingsIraAccountFieldsWithUpdatedDataInEditMode(Account account) {
        verifyGeneralFieldsWithUpdatedDataInEditMode(account);
        verifyGeneralSavingsAccountFieldsWithUpdatedDataInEditMode(account);
    }

    public void verifySavingsIraFieldsAfterCreationInEditMode(Account account) {
        verifyGeneralAccountFieldsAfterCreationInEditMode(account);
        verifyGeneralIraAccountFieldsAreDisabledForEditing(account);
        verifyGeneralSavingsIraFieldsAfterCreationInEditMode(account);
        Assert.assertEquals(Pages.editAccountPage().getInterestFrequency(), account.getInterestFrequency(), "'Interest Frequency' value does not match");
    }

    /**
     * Regular CD account verification
     */

    public void verifyRegularCdAccountFieldsAreDisabledForEditing() {
        verifyGeneralFieldsAreDisabledForEditing();
        verifyGeneralCdAccountFieldsAreDisabledForEditing();
    }

    public void verifyRegularCdAccountFieldsWithUpdatedDataInEditMode(Account account) {
        verifyGeneralFieldsWithUpdatedDataInEditMode(account);
        Assert.assertEquals(Pages.editAccountPage().getInterestRateValueInEditMode(), account.getInterestRate(), "'Interest Rate' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getFederalWHPercent(), account.getFederalWHPercent(), "'Federal WH Percent' value does not match");
    }

    public void verifyCdAccountFieldsAfterCreationInEditMode(Account account) {
        verifyGeneralAccountFieldsAfterCreationInEditMode(account);
        verifyGeneralCdAccountFieldsAfterCreationInEditMode(account);
        Assert.assertEquals(Pages.editAccountPage().getInterestRateValueInEditMode(), account.getInterestRate(), "'Interest Rate' value does not match");
    }

    /**
     * Regular CD IRA account verification
     */
    public void verifyCdIraAccountFieldsAreDisabledForEditing() {
        verifyGeneralFieldsAreDisabledForEditing();
        verifyGeneralCdAccountFieldsAreDisabledForEditing();
        Assert.assertTrue(Pages.editAccountPage().isSpecialMailingInstructionsDisabledInEditMode(), "'Special Mailing Instructions' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAmountLastIRADistributionDisabledInEditMode(), "'Amount last IRA distribution' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isDateLastIRADistributionDisabledInEditMode(), "'Date last IRA distribution' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isIraDistributionAccountNumberDisabledInEditMode(), "'IRA Distribution Account Number' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isTotalContributionsDisabledInEditMode(), "'Total Contributions for Life of Account' field is not disabled");
    }

    public void verifyCdIraAccountFieldsWithUpdatedDataInEditMode(Account account) {
        verifyGeneralFieldsWithUpdatedDataInEditMode(account);
        Assert.assertEquals(Pages.editAccountPage().getDateOfFirstDeposit(), account.getDateOfFirstDeposit(), "'Date Of First Deposit' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getBirthDate(), account.getBirthDate(), "'Birth Date' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getDateDeceased(), account.getDateDeceased(), "'Date Deceased' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getInterestRateValueInEditMode(), account.getInterestRate(), "'Interest Rate' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getApplyInterestTo(), account.getApplyInterestTo(), "'Apply Interest To' value does not match");
        if (account.getCorrespondingAccount() != null) {
            Assert.assertEquals(Pages.editAccountPage().getCorrespondingAccount().replaceAll("[^0-9]", ""),
                    account.getCorrespondingAccount(),
                    "'Corresponding Account' value does not match");
        }
    }

    public void verifyCdIraFieldsAfterCreationInEditMode(Account account) {
        verifyGeneralAccountFieldsAfterCreationInEditMode(account);
        verifyGeneralCdAccountFieldsAfterCreationInEditMode(account);
        verifyGeneralIraAccountFieldsAreDisabledForEditing(account);
    }

    public void editOverdraftStatusAndLimit(String overdraftLimit) {
        Pages.accountDetailsPage().clickEditButton();
        Pages.editAccountPage().clickAutomaticOverdraftStatusSelectorButton();
        Pages.editAccountPage().clickAutomaticOverdraftStatusSelectorOption("Active");
        Pages.editAccountPage().setAutomaticOverdraftLimit(overdraftLimit);
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForEditButton();
    }

    public void enableAdjustableRateSwitch() {
        if (Pages.editAccountPage().getAdjustableRateValue().equalsIgnoreCase("no")) {
            Pages.editAccountPage().clickAdjustableRate();
            SelenideTools.sleep(Constants.MICRO_TIMEOUT);
        }
    }

    public void enableChangePaymentWithRateChangeSwitch() {
        if (Pages.editAccountPage().getChangePaymentWithRateChangeValue().equalsIgnoreCase("no")) {
            Pages.editAccountPage().clickChangePaymentWithRateChangeSwitch();
            SelenideTools.sleep(Constants.MICRO_TIMEOUT);
        }
    }
}
