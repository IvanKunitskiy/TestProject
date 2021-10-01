package com.nymbus.actions.account;

import com.nymbus.actions.Actions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.client.other.account.type.CHKAccount;
import com.nymbus.pages.Pages;
import org.testng.Assert;

import java.util.List;
import java.util.Random;

public class CreateAccount {

    private void setBasicAccountFields(Account account) {
        setAddNewOption(account);
        setProductType(account);
        setProduct(account);
        Pages.addAccountPage().setAccountNumberValue(account.getAccountNumber());
    }

    public void createCDAccount(Account account) {
        Pages.clientDetailsPage().clickAccountsTab();
        Actions.clientPageActions().closeAllNotifications();
        setBasicAccountFields(account);
        selectValuesInFieldsRequiredForCDAccount(account);
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();
    }

    public void createIRAAccount(Account account) {
        Pages.clientDetailsPage().clickAccountsTab();
        setBasicAccountFields(account);
        Pages.addAccountPage().setAccountTitleValue(account.getAccountTitle());
        selectValuesInDropdownFieldsRequiredForSavingsIRAAccount(account);
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();
    }

    public void createSavingsAccount(Account account) {
        Pages.clientDetailsPage().clickAccountsTab();
        Actions.clientPageActions().closeAllNotifications();
        setBasicAccountFields(account);
        Pages.addAccountPage().setAccountTitleValue(account.getAccountTitle());
        setCurrentOfficer(account);
        setBankBranch(account);
        setMailCode(account);
        Pages.addAccountPage().setDateOpenedValue(account.getDateOpened());
        Pages.addAccountPage().setInterestRate(account.getInterestRate());
        setStatementCycle(account);
        //TODO ucom
        //setCallClassCode(account);
        setInterestFrequency(account);
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();
    }

    public void createSavingAccountForTransactionPurpose(Account account) {
        Pages.clientDetailsPage().clickAccountsTab();
        Actions.clientPageActions().closeAllNotifications();
        setBasicAccountFields(account);
        Pages.addAccountPage().setAccountTitleValue(account.getAccountTitle());
        setCurrentOfficer(account);
        setBankBranch(account);
        Pages.addAccountPage().setDateOpenedValue(account.getDateOpened());
        // Pages.addAccountPage().setInterestRate(account.getInterestRate());
        setStatementCycle(account);
        setCallClassCode(account);
        Pages.addAccountPage().waitForAccountHolderName();
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();
    }

    public void createCDAccountForTransactionPurpose(Account account) {
        Pages.clientDetailsPage().clickAccountsTab();
        Actions.clientPageActions().closeAllNotifications();
        Pages.clientDetailsPage().clickAddNewButton();
        Pages.clientDetailsPage().clickAddNewValueOption(account.getAddNewOption());
        Pages.addAccountPage().clickProductTypeSelectorButton();
        Pages.addAccountPage().clickProductTypeOption(account.getProductType());
        Pages.addAccountPage().clickProductSelectorButton();
        Pages.addAccountPage().clickProductOption(account.getProduct());
        Pages.addAccountPage().setAccountNumberValue(account.getAccountNumber());
        Pages.addAccountPage().setDateOpenedValue(account.getDateOpened());
        Pages.addAccountPage().setInterestRate(account.getInterestRate());
        setApplyInterestTo(account);
        Pages.addAccountPage().waitForAccountHolderName();
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();
    }

    public void createCHKAccountForTransactionPurpose(Account account) {
        Pages.clientDetailsPage().clickAccountsTab();
        Actions.clientPageActions().closeAllNotifications();
        setBasicAccountFields(account);
        Pages.addAccountPage().setDateOpenedValue(account.getDateOpened());
        Pages.addAccountPage().setInterestRate(account.getInterestRate());
        setStatementCycle(account);
        Pages.addAccountPage().waitForAccountHolderName();
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();
    }

    public void createCHKAccount(Account account) {
        Pages.clientDetailsPage().clickAccountsTab();
        Actions.clientPageActions().closeAllNotifications();
        setBasicAccountFields(account);
        setValuesInFieldsRequiredForCheckingAccount(account);
        Pages.addAccountPage().setDateOpenedValue(account.getDateOpened());
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();
    }

    public void createCHKAccountForDormantPurpose(Account account) {
        Pages.clientDetailsPage().clickAccountsTab();
        Actions.clientPageActions().closeAllNotifications();
        setBasicAccountFields(account);
        Pages.addAccountPage().setAccountTitleValue(account.getAccountTitle());
        setCurrentOfficer(account);
        setBankBranch(account);
        Pages.addAccountPage().setDateOpenedValue(account.getDateOpened());
        Pages.addAccountPage().setInterestRate(account.getInterestRate());
        setStatementCycle(account);
        Pages.addAccountPage().setOptInOutDateValue(account.getOptInOutDate());
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();
    }

    public void createCHKAccount(CHKAccount account) {
        Pages.clientDetailsPage().clickAccountsTab();
        setAddNewOption(account);
        setProductType(account);
        setProduct(account);
        Pages.addAccountPage().setAccountNumberValue(account.getAccountNumber());
        Pages.addAccountPage().setAccountTitleValue(account.getAccountTitle());
        setCurrentOfficer(account);
        setBankBranch(account);
        Pages.addAccountPage().setDateOpenedValue(account.getDateOpened());
        Pages.addAccountPage().setInterestRate(account.getInterestRate());
        setStatementCycle(account);
        setChargeOrAnalyze(account);
        setAccountAnalysis(account);
        Pages.addAccountPage().setOptInOutDateValue(account.getOptInOutDate());
        setCallClassCode(account);
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();
    }

    public void createSafeDepositBoxAccount(Account account) {
        Pages.clientDetailsPage().clickAccountsTab();
        setAddNewOption(account);
        setProductType(account);
        setBoxSize(account);
        Pages.addAccountPage().setAccountNumberValue(account.getAccountNumber());
        Pages.addAccountPage().setAccountTitleValue(account.getAccountTitle());
        Pages.addAccountPage().setDateOpenedValue(account.getDateOpened());
        selectValuesInDropdownFieldsRequiredForSafeDepositBoxAccount(account);
        fillInInputFieldsRequiredForSafeDepositBoxAccount(account);
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();
    }

    public void createLoanAccount(Account account) {
        Pages.clientDetailsPage().clickAccountsTab();
        setAddNewOption(account);
        setProductType(account);
        setProduct(account);
        setAccountType(account);
        Pages.addAccountPage().setAccountNumberValue(account.getAccountNumber());
        setOriginatingOfficer(account);
        setCurrentOfficer(account);
        setMailCode(account);
        Pages.addAccountPage().setDateOpenedValue(account.getDateOpened());
        setBankBranch(account);
        setLoanClassCode(account);
        Pages.addAccountPage().setPaymentAmount(account.getPaymentAmount());
        setPaymentAmountType(account);
        setPaymentFrequency(account);
        if (account.isCycleLoan()) {
            enableCycleLoanSwitch();
            setCycleCode(account);
        } else {
            disableCycleLoanSwitch();
            Pages.addAccountPage().setPaymentBilledLeadDays(account.getPaymentBilledLeadDays());
        }
        if (account.isCurrentEffectiveRateIsTeaser()) {
            enableTeaserLoanSwitch();
        }
        if (!(account.isAdjustableRate())) {
            disableAdjustableRateSwitch();
        }
        if(account.getCommitmentAmt() != null){
            Pages.addAccountPage().setCommitmentAmt(account.getCommitmentAmt());
        }
        Pages.addAccountPage().setNextPaymentBilledDueDate(account.getNextPaymentBilledDueDate());
        Pages.addAccountPage().setDateFirstPaymentDue(account.getDateFirstPaymentDue());
        Pages.addAccountPage().setCurrentEffectiveRate(account.getCurrentEffectiveRate());
        setEscrowPayment(account);
        setInterestMethod(account);
        disableAdjustableRateSwitch();
        setDaysBaseYearBase(account);
        Pages.addAccountPage().setTerm(account.getTerm());
        setCommitmentTypeAmt(account);
        disableLocPaymentRecalculationFlagValueSwitch();
        setCallClassCode(account);
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();
    }

    private void setEscrowPayment(Account account) {
        String escrowPaymentValue = Pages.addAccountPage().getEscrowPaymentValue();
        if (account.getEscrow() != null && escrowPaymentValue != null && !escrowPaymentValue.equals(account.getEscrow())) {
            Pages.addAccountPage().setEscrowPaymentValue(account.getEscrow());
        }
    }

    public void setValuesInFieldsRequiredForSavingsAccount(Account account) {
        Pages.addAccountPage().setAccountTitleValue(account.getAccountTitle());
        setCurrentOfficer(account);
        setBankBranch(account);
        setMailCode(account);
        Pages.addAccountPage().setDateOpenedValue(account.getDateOpened());
        Pages.addAccountPage().setInterestRate(account.getInterestRate());
        setInterestFrequency(account);
        setCorrespondingAccount(account);
        setStatementCycle(account);
        setCallClassCode(account);
    }

    public void selectValuesInDropdownFieldsRequiredForSavingsIRAAccount(Account account) {
        setCurrentOfficer(account);
        setBankBranch(account);
        setInterestFrequency(account);
        setStatementCycle(account);
        setCorrespondingAccount(account);
        setCallClassCode(account);
        setIRADistributionFrequency(account);
        setIRADistributionCode(account);
        setIRADistributionAccountNumber(account);
        Pages.addAccountPage().setAccountTitleValue(account.getAccountTitle());
        Pages.addAccountPage().setIRADistributionAmountValue(account.getIraDistributionAmount());
        Pages.addAccountPage().setDateOpenedValue(account.getDateOpened());
        Pages.addAccountPage().setDateNextIRADistributionValue(account.getDateNextIRADistribution());
        Pages.addAccountPage().setDateOfFirstDepositValue(account.getDateOfFirstDeposit());
    }

    public void setValuesInFieldsRequiredForCDIRAAccount(Account account) {
        Pages.addAccountPage().setAccountTitleValue(account.getAccountTitle());
        setCurrentOfficer(account);
        setBankBranch(account);
        setInterestFrequencyCode(account);
        setApplyInterestTo(account);
        setInterestType(account);
        setCallClassCode(account);
        setIRADistributionFrequency(account);
        setIRADistributionCode(account);
        setIRADistributionAccountNumber(account);
        Pages.addAccountPage().setIRADistributionAmountValue(account.getIraDistributionAmount());
        Pages.addAccountPage().setDateOpenedValue(account.getDateOpened());
        Pages.addAccountPage().setDateNextIRADistributionValue(account.getDateNextIRADistribution());
        Pages.addAccountPage().setDateOfFirstDepositValue(account.getDateOfFirstDeposit());

        if (Pages.addAccountPage().isTransactionalAccountNo()) {
            Pages.addAccountPage().clickTransactionalAccountSwitch();
        }
        if (Pages.addAccountPage().isAutoRenewableYes()) {
            Pages.addAccountPage().clickAutoRenewableSwitch();
        }
        if (Pages.addAccountPage().isApplySeasonalAddressYes()) {
            Pages.addAccountPage().clickApplySeasonalAddressSwitch();
        }
    }

    public void selectValuesInDropdownFieldsRequiredForSafeDepositBoxAccount(Account account) {
        setCurrentOfficer(account);
        setBankBranch(account);
        setCorrespondingAccount(account);
        /*setDiscountReason(account);*/
    }

    public void setValuesInFieldsRequiredForCheckingAccount(Account account) {
        Pages.addAccountPage().setAccountTitleValue(account.getAccountTitle());
        setCurrentOfficer(account);
        setBankBranch(account);
        Pages.addAccountPage().setDateOpenedValue(account.getDateOpened());
        setInterestRate(account);
        setStatementCycle(account);
        setChargeOrAnalyze(account);
        setAccountAnalysis(account);
        Pages.addAccountPage().setOptInOutDateValue(account.getOptInOutDate());
        setCallClassCode(account);
        applySeasonalAddresToNo();

    }

    private void applySeasonalAddresToNo() {
        if (Constants.getEnvironment().equals("dev4")) {
            if (Pages.addAccountPage().isApplySeasonalAddressYes()) {
                Pages.addAccountPage().clickApplySeasonalAddressSwitch();
            }
        } else {
            if (Pages.addAccountPage().getApplySeasonalAddress().equalsIgnoreCase("yes")) {
                Pages.addAccountPage().clickApplySeasonalAddressSwitch();
            }
        }
    }

    public void selectValuesInFieldsRequiredForCDAccount(Account account) {
        Pages.addAccountPage().setAccountTitleValue(account.getAccountTitle());
        setCurrentOfficer(account);
        setBankBranch(account);
        Pages.addAccountPage().setDateOpenedValue(account.getDateOpened());
        setInterestFrequencyCode(account);
        setApplyInterestTo(account);
        Pages.addAccountPage().setInterestRate(account.getInterestRate());
        setInterestType(account);
        setCorrespondingAccount(account);
        setCallClassCode(account);
        Pages.addAccountPage().clickTransactionalAccountSwitch();
    }

    public void fillInInputFieldsRequiredForSafeDepositBoxAccount(Account account) {
        Pages.addAccountPage().setAccountTitleValue(account.getAccountTitle());
        Pages.addAccountPage().setUserDefinedField_1(account.getUserDefinedField_1());
        Pages.addAccountPage().setUserDefinedField_2(account.getUserDefinedField_2());
        Pages.addAccountPage().setUserDefinedField_3(account.getUserDefinedField_3());
        Pages.addAccountPage().setUserDefinedField_4(account.getUserDefinedField_4());
        Pages.addAccountPage().setDiscountPeriods(account.getDiscountPeriods());
    }

    public void setDiscountReason(Account account) {
        Pages.addAccountPage().clickDiscountReasonSelectorButton();
        List<String> listOfDiscountReason = Pages.addAccountPage().getDiscountReasonList();

        Assert.assertTrue(listOfDiscountReason.size() > 0, "There are no 'Discount Reasons' options available");
        if (account.getDiscountReason() == null) {
            account.setDiscountReason(listOfDiscountReason.get(new Random().nextInt(listOfDiscountReason.size())).trim());
        }
        Pages.addAccountPage().clickDiscountReasonSelectorOption(account.getDiscountReason());
    }

    public void setInterestType(Account account) {
        Pages.addAccountPage().clickInterestTypeSelectorButton();
        List<String> listOfInterestType = Pages.addAccountPage().getInterestTypeList();

        Assert.assertTrue(listOfInterestType.size() > 0, "There are no 'Interest Types' options available");
        if (account.getInterestType() == null) {
            account.setInterestType(listOfInterestType.get(new Random().nextInt(listOfInterestType.size())).trim());
        }
        Pages.addAccountPage().clickInterestTypeSelectorOption(account.getInterestType());
    }

    public void setApplyInterestTo(Account account) {
        Pages.addAccountPage().clickApplyInterestToSelectorButton();
        List<String> listOfApplyInterestTo = Pages.addAccountPage().getApplyInterestToList();

        Assert.assertTrue(listOfApplyInterestTo.size() > 0, "There are no 'Apply Interest To' options available");
        if (account.getApplyInterestTo() == null) {
            account.setApplyInterestTo(listOfApplyInterestTo.get(new Random().nextInt(listOfApplyInterestTo.size())).trim());
        }
        Pages.addAccountPage().clickApplyInterestToSelectorOption(account.getApplyInterestTo());
    }

    public void setIRADistributionFrequency(Account account) {
        Pages.addAccountPage().clickIRADistributionFrequencySelectorButton();
        List<String> listOfIRADistributionFrequency = Pages.addAccountPage().getIRADistributionFrequencyList();

        Assert.assertTrue(listOfIRADistributionFrequency.size() > 0, "There are no 'Ira Distribution Frequency' options available");
        if (account.getIraDistributionFrequency() == null) {
            account.setIraDistributionFrequency(listOfIRADistributionFrequency.get(new Random().nextInt(listOfIRADistributionFrequency.size())).trim());
        }
        Pages.addAccountPage().clickIRADistributionFrequencySelectorOption(account.getIraDistributionFrequency());
    }

    public void setIRADistributionCode(Account account) {
        Pages.addAccountPage().clickIRADistributionCodeSelectorButton();
        List<String> listOfIRADistributionCode = Pages.addAccountPage().getIRADistributionCodeList();

        Assert.assertTrue(listOfIRADistributionCode.size() > 0, "There are no 'Ira Distribution Code' options available");
        if (account.getIraDistributionCode() == null) {
            account.setIraDistributionCode(listOfIRADistributionCode.get(new Random().nextInt(listOfIRADistributionCode.size())).trim());
        }
        Pages.addAccountPage().clickIRADistributionCodeSelectorOption(account.getIraDistributionCode());
    }

    public void setIRADistributionAccountNumber(Account account) {
        if (account.getIraDistributionAccountNumber() != null) {
            if (Constants.getEnvironment().equals("dev4") || Constants.getEnvironment().equals("dev29") || Constants.getEnvironment().equals("dev12") || Constants.getEnvironment().equals("dev47")) {
                Pages.addAccountPage().clickIRADistributionAccountNumberSelectorButton(account.getIraDistributionAccountNumber());
                Pages.addAccountPage().clickIRADistributionAccountNumberSelectorOption(account.getIraDistributionAccountNumber());
            } else {
                Pages.addAccountPage().clickIRADistributionAccountNumberSelectorButton();
                Pages.addAccountPage().clickIRADistributionAccountNumberSelectorOptionSpan(account.getIraDistributionAccountNumber());
            }
        }
    }

    public void setCorrespondingAccount(Account account) {
        if (account.getApplyInterestTo().equals("CHK Acct")) {
            Pages.addAccountPage().clickCorrespondingAccountSelectorButton();
            List<String> listOfCorrespondingAccount = Pages.addAccountPage().getCorrespondingAccountList();

            if (listOfCorrespondingAccount.size() > 0) {
                if (account.getCorrespondingAccount() == null) {
                    account.setCorrespondingAccount(listOfCorrespondingAccount.get(new Random().nextInt(listOfCorrespondingAccount.size())).trim());
                }
                Pages.addAccountPage().clickCorrespondingAccountSelectorOption(account.getCorrespondingAccount().replaceAll("[^0-9]", ""));
            }
        }
    }

    public void setInterestFrequency(Account account) {
        Pages.addAccountPage().clickInterestFrequencySelectorButton();
        List<String> listOfInterestFrequency = Pages.addAccountPage().getInterestFrequencyList();

        Assert.assertTrue(listOfInterestFrequency.size() > 0, "There are no 'Interest Frequency' options available");
        if (account.getInterestFrequency() == null) {
            account.setInterestFrequency(listOfInterestFrequency.get(new Random().nextInt(listOfInterestFrequency.size())).trim());
        }
        Pages.addAccountPage().clickInterestFrequencySelectorOption(account.getInterestFrequency());
    }

    public void setInterestFrequencyCode(Account account) {
        Pages.addAccountPage().clickInterestFrequencyCodeSelectorButton();
        List<String> listOfInterestFrequency = Pages.addAccountPage().getInterestFrequencyCodeList();

        Assert.assertTrue(listOfInterestFrequency.size() > 0, "There are no 'Interest Frequency Code' options available");
        if (account.getInterestFrequency() == null) {
            account.setInterestFrequency(listOfInterestFrequency.get(new Random().nextInt(listOfInterestFrequency.size())).trim());
        }
        Pages.addAccountPage().clickInterestFrequencyCodeSelectorOption(account.getInterestFrequency());
    }

    public void setChargeOrAnalyze(Account account) {
        Pages.addAccountPage().clickChargeOrAnalyzeSelectorButton();
        List<String> listOfChargeOrAnalyze = Pages.addAccountPage().getChargeOrAnalyzeList();

        Assert.assertTrue(listOfChargeOrAnalyze.size() > 0, "There are no 'Charge Or Analyze' options available");
        if (account.getChargeOrAnalyze() == null) {
            account.setChargeOrAnalyze(listOfChargeOrAnalyze.get(new Random().nextInt(listOfChargeOrAnalyze.size())).trim());
        }
        Pages.addAccountPage().clickChargeOrAnalyzeSelectorOption(account.getChargeOrAnalyze());
    }

    public void setChargeOrAnalyze(CHKAccount account) {
        Pages.addAccountPage().clickChargeOrAnalyzeSelectorButton();
        List<String> listOfChargeOrAnalyze = Pages.addAccountPage().getChargeOrAnalyzeList();

        Assert.assertTrue(listOfChargeOrAnalyze.size() > 0, "There are no 'Charge Or Analyze' options available");
        if (account.getChargeOrAnalyze() == null) {
            account.setChargeOrAnalyze(listOfChargeOrAnalyze.get(new Random().nextInt(listOfChargeOrAnalyze.size())).trim());
        }
        Pages.addAccountPage().clickChargeOrAnalyzeSelectorOption(account.getChargeOrAnalyze());
    }

    public void setAccountAnalysis(Account account) {
        Pages.addAccountPage().clickAccountAnalysisSelectorButton();
        List<String> listOfAccountAnalysis = Pages.addAccountPage().getAccountAnalysisList();

        if (listOfAccountAnalysis.size() > 0) {
            if (account.getAccountAnalysis() == null) {
                //account.setAccountAnalysis(listOfAccountAnalysis.get(new Random().nextInt(listOfAccountAnalysis.size())).trim());
                account.setAccountAnalysis(listOfAccountAnalysis.get(0));
            }
            Pages.addAccountPage().clickAccountAnalysisSelectorOption(account.getAccountAnalysis());
        }
    }

    public void setAccountAnalysis(CHKAccount account) {
        Pages.addAccountPage().clickAccountAnalysisSelectorButton();
        List<String> listOfAccountAnalysis = Pages.addAccountPage().getAccountAnalysisList();

        Assert.assertTrue(listOfAccountAnalysis.size() > 0, "There are no 'Account Analysis' options available");
        if (account.getAccountAnalysis() == null) {
            account.setAccountAnalysis(listOfAccountAnalysis.get(new Random().nextInt(listOfAccountAnalysis.size())).trim());
        }
        Pages.addAccountPage().clickAccountAnalysisSelectorOption(account.getAccountAnalysis());
    }

    public void setCallClassCode(Account account) {
        Pages.addAccountPage().clickCallClassCodeSelectorButton();
        List<String> listOfCallClassCode = Pages.addAccountPage().getCallClassCodeList();

        if (listOfCallClassCode.size() > 0) {
            if (account.getCallClassCode() == null) {
                account.setCallClassCode(listOfCallClassCode.get(new Random().nextInt(listOfCallClassCode.size())).trim());
            }
            Pages.addAccountPage().clickCallClassCodeSelectorOption(account.getCallClassCode());
        }
    }

    public void setCallClassCodeByIndex(Account account, int index) {
        Pages.addAccountPage().clickCallClassCodeSelectorButton();
        List<String> listOfCallClassCode = Pages.addAccountPage().getCallClassCodeList();

        if (listOfCallClassCode.size() > 0) {
            int randomIndex = new Random().nextInt(listOfCallClassCode.size());
            account.setCallClassCode(listOfCallClassCode.get(randomIndex).trim());
            Pages.addAccountPage().clickCallClassCodeSelectorOptionByIndex(listOfCallClassCode.indexOf(account.getCallClassCode()));
        }
    }

    public void setCallClassCode(CHKAccount account) {
        Pages.addAccountPage().clickCallClassCodeSelectorButton();
        List<String> listOfCallClassCode = Pages.addAccountPage().getCallClassCodeList();

        Assert.assertTrue(listOfCallClassCode.size() > 0, "There are no 'Call Class Code' options available");
        if (account.getCallClassCode() == null) {
            account.setCallClassCode(listOfCallClassCode.get(new Random().nextInt(listOfCallClassCode.size())).trim());
        }
        Pages.addAccountPage().clickCallClassCodeSelectorOption(account.getCallClassCode());
    }

    public void setCurrentOfficer(Account account) {
        Pages.addAccountPage().clickCurrentOfficerSelectorButton();
        List<String> listOfCurrentOfficers = Pages.addAccountPage().getCurrentOfficerList();

        Assert.assertTrue(listOfCurrentOfficers.size() > 0, "There are no 'Current Officer' options available");
        if (account.getCurrentOfficer() == null) {
            account.setCurrentOfficer(listOfCurrentOfficers.get(new Random().nextInt(listOfCurrentOfficers.size())).trim());
        }
        Pages.addAccountPage().clickCurrentOfficerSelectorOption(account.getCurrentOfficer());
    }

    public void setCurrentOfficer(CHKAccount account) {
        Pages.addAccountPage().clickCurrentOfficerSelectorButton();
        List<String> listOfCurrentOfficers = Pages.addAccountPage().getCurrentOfficerList();

        Assert.assertTrue(listOfCurrentOfficers.size() > 0, "There are no 'Current Officer' options available");
        if (account.getCurrentOfficer() == null) {
            account.setCurrentOfficer(listOfCurrentOfficers.get(new Random().nextInt(listOfCurrentOfficers.size())).trim());
        }
        Pages.addAccountPage().clickCurrentOfficerSelectorOption(account.getCurrentOfficer());
    }

    public void setOriginatingOfficer(Account account) {
        Pages.addAccountPage().clickOriginatingOfficerSelectorButton();
        List<String> listOfOriginatingOfficers = Pages.addAccountPage().getOriginatingOfficerList();

        Assert.assertTrue(listOfOriginatingOfficers.size() > 0, "There are no 'Originating Officer' options available");
        if (account.getOriginatingOfficer() == null) {
            account.setOriginatingOfficer(listOfOriginatingOfficers.get(new Random().nextInt(listOfOriginatingOfficers.size())).trim());
        }
        Pages.addAccountPage().clickOriginatingOfficerSelectorOption(account.getOriginatingOfficer());
    }

    public void setStatementCycle(Account account) {
        Pages.addAccountPage().clickStatementCycleSelectorButton();
        List<String> listOfStatementCycle = Pages.addAccountPage().getStatementCycleList();

        Assert.assertTrue(listOfStatementCycle.size() > 0, "There are no 'Statement Cycle' options available");
        if (account.getStatementCycle() == null) {
            account.setStatementCycle(listOfStatementCycle.get(new Random().nextInt(listOfStatementCycle.size())).trim());
        }
        Pages.addAccountPage().clickStatementCycleOption(account.getStatementCycle());
    }

    public void setStatementCycle(CHKAccount account) {
        Pages.addAccountPage().clickStatementCycleSelectorButton();
        List<String> listOfStatementCycle = Pages.addAccountPage().getStatementCycleList();

        Assert.assertTrue(listOfStatementCycle.size() > 0, "There are no 'Statement Cycle' options available");
        if (account.getStatementCycle() == null) {
            account.setStatementCycle(listOfStatementCycle.get(new Random().nextInt(listOfStatementCycle.size())).trim());
        }
        Pages.addAccountPage().clickStatementCycleOption(account.getStatementCycle());
    }

    public void setProduct(Account account) {
        Pages.addAccountPage().clickProductSelectorButton();
        List<String> listOfProduct = Pages.addAccountPage().getProductList();

        Assert.assertTrue(listOfProduct.size() > 0, "There are no 'Product' options available");
        if (account.getProduct() == null) {
            account.setProduct(listOfProduct.get(new Random().nextInt(listOfProduct.size())));
        }

        for (String s : listOfProduct) {
            if (s.trim().equals(account.getProduct())) {
                Pages.addAccountPage().clickProductOption(s);
            }
        }
    }

    public void setProduct(CHKAccount account) {
        Pages.addAccountPage().clickProductSelectorButton();
        List<String> listOfProduct = Pages.addAccountPage().getProductList();

        Assert.assertTrue(listOfProduct.size() > 0, "There are no 'Product' options available");
        Pages.addAccountPage().clickProductOption(account.getProduct().getProduct());
    }

    public void setBankBranch(Account account) {
        Pages.addAccountPage().clickBankBranchSelectorButton();
        List<String> listOfBankBranchOptions = Pages.addAccountPage().getBankBranchList();

        Assert.assertTrue(listOfBankBranchOptions.size() > 0, "There are no 'Bank Branch' options available");
        if (account.getBankBranch() == null) {
            account.setBankBranch(listOfBankBranchOptions.get(new Random().nextInt(listOfBankBranchOptions.size())).trim());
        }
        Pages.addAccountPage().clickBankBranchOption(account.getBankBranch());
    }

    public void setBankBranch(CHKAccount account) {
        Pages.addAccountPage().clickBankBranchSelectorButton();
        List<String> listOfBankBranchOptions = Pages.addAccountPage().getBankBranchList();

        Assert.assertTrue(listOfBankBranchOptions.size() > 0, "There are no 'Bank Branch' options available");
        if (account.getBankBranch() == null) {
            account.setBankBranch(listOfBankBranchOptions.get(new Random().nextInt(listOfBankBranchOptions.size())).trim());
        }
        Pages.addAccountPage().clickBankBranchOption(account.getBankBranch());
    }

    public void setAccountType(Account account) {
        Pages.addAccountPage().clickAccountTypeSelectorButton();
        List<String> listOfAccountType = Pages.addAccountPage().getAccountTypeList();

        Assert.assertTrue(listOfAccountType.size() > 0, "There are no 'Account type' options available");
        if (account.getAccountType() == null) {
            account.setAccountType(listOfAccountType.get(new Random().nextInt(listOfAccountType.size())).trim());
        }
        Pages.addAccountPage().clickAccountTypeSelectorOption(account.getAccountType());
    }

    public void setAddNewOption(Account account) {
        Pages.clientDetailsPage().clickAddNewButton();
        List<String> listOfAddNewOptions = Pages.clientDetailsPage().getAddNewList();

        Assert.assertTrue(listOfAddNewOptions.size() > 0, "There are no 'Add New Option' options available");
        if (account.getAddNewOption() == null) {
            account.setAddNewOption(listOfAddNewOptions.get(new Random().nextInt(listOfAddNewOptions.size())).trim());
        }
        Pages.clientDetailsPage().clickAddNewValueOption(account.getAddNewOption());
    }

    public void setAddNewOption(CHKAccount account) {
        Pages.clientDetailsPage().clickAddNewButton();
        List<String> listOfAddNewOptions = Pages.clientDetailsPage().getAddNewList();

        Assert.assertTrue(listOfAddNewOptions.size() > 0, "There are no 'Add New Option' options available");
        Pages.clientDetailsPage().clickAddNewValueOption(account.getAddNewOption());
    }

    public void setProductType(Account account) {
        Pages.addAccountPage().clickProductTypeSelectorButton();
        List<String> listOfProductType = Pages.addAccountPage().getProductTypeList();

        Assert.assertTrue(listOfProductType.size() > 0, "There are no 'Product Type' options available");
        if (account.getProductType() == null) {
            account.setProductType(listOfProductType.get(new Random().nextInt(listOfProductType.size())).trim());
        }
        Pages.addAccountPage().clickProductTypeOption(account.getProductType());
        SelenideTools.sleep(5);
    }

    public void setProductType(CHKAccount account) {
        Pages.addAccountPage().clickProductTypeSelectorButton();
        List<String> listOfProductType = Pages.addAccountPage().getProductTypeList();

        Assert.assertTrue(listOfProductType.size() > 0, "There are no 'Product Type' options available");
        Pages.addAccountPage().clickProductTypeOption(account.getProductType().getProductType());
    }

    public void setBoxSize(Account account) {
        Pages.addAccountPage().clickBoxSizeSelectorButton();
        Pages.addAccountPage().clickBoxSizeSelectorOption(account.getBoxSize());
    }

    public void setMailCode(Account account) {
        Pages.addAccountPage().clickMailCodeSelectorButton();
        List<String> listOfMailCode = Pages.addAccountPage().getMailCodeList();

        Assert.assertTrue(listOfMailCode.size() > 0, "There are no 'Mail Code' options available");
        if (account.getMailCode() == null) {
            account.setMailCode(listOfMailCode.get(new Random().nextInt(listOfMailCode.size())).trim());
        }
        Pages.addAccountPage().clickMailCodeSelectorOption(account.getMailCode());
    }

    public void setLoanClassCode(Account account) {
        Pages.addAccountPage().clickLoanClassCodeSelectorButton();
        List<String> listOfLoanClassCode = Pages.addAccountPage().getLoanClassCodeList();

        Assert.assertTrue(listOfLoanClassCode.size() > 0, "There are no 'Loan Class Code' options available");
        if (account.getLoanClassCode() == null) {
            account.setLoanClassCode(listOfLoanClassCode.get(new Random().nextInt(listOfLoanClassCode.size())).trim());
        }
        Pages.addAccountPage().clickLoanClassCodeSelectorOption(account.getLoanClassCode());
    }

    public void setPaymentAmountType(Account account) {
        Pages.addAccountPage().clickPaymentAmountTypeSelectorButton();
        List<String> listOfPaymentAmountType = Pages.addAccountPage().getPaymentAmountTypeList();

        Assert.assertTrue(listOfPaymentAmountType.size() > 0, "There are no 'Payment Amount' options available");
        if (account.getPaymentAmountType() == null) {
            account.setPaymentAmountType(listOfPaymentAmountType.get(new Random().nextInt(listOfPaymentAmountType.size())).trim());
        }
        Pages.addAccountPage().clickPaymentAmountTypeSelectorOption(account.getPaymentAmountType());
    }

    public void setPaymentFrequency(Account account) {
        Pages.addAccountPage().clickPaymentFrequencySelectorButton();
        List<String> listOfPaymentFrequency = Pages.addAccountPage().getPaymentFrequencyList();

        Assert.assertTrue(listOfPaymentFrequency.size() > 0, "There are no 'Payment Frequency' options available");
        if (account.getPaymentFrequency() == null) {
            account.setPaymentFrequency(listOfPaymentFrequency.get(new Random().nextInt(listOfPaymentFrequency.size())).trim());
        }
        Pages.addAccountPage().clickPaymentFrequencySelectorOption(account.getPaymentFrequency());
    }

    public void setInterestMethod(Account account) {
        Pages.addAccountPage().clickInterestMethodSelectorButton();
        List<String> listOfInterestMethod = Pages.addAccountPage().getInterestMethodList();

        Assert.assertTrue(listOfInterestMethod.size() > 0, "There are no 'Interest Method' options available");
        if (account.getInterestMethod() == null) {
            account.setInterestMethod(listOfInterestMethod.get(new Random().nextInt(listOfInterestMethod.size())).trim());
        }
        Pages.addAccountPage().clickInterestMethodSelectorOption(account.getInterestMethod());
    }

    public void setDaysBaseYearBase(Account account) {
        Pages.addAccountPage().clickDaysBaseYearBaseSelectorButton();
        List<String> listOfDaysBaseYearBase = Pages.addAccountPage().getDaysBaseYearBaseList();

        Assert.assertTrue(listOfDaysBaseYearBase.size() > 0, "There are no 'Days Base/Year Base' options available");
        if (account.getDaysBaseYearBase() == null) {
            account.setDaysBaseYearBase(listOfDaysBaseYearBase.get(new Random().nextInt(listOfDaysBaseYearBase.size())).trim());
        }
        Pages.addAccountPage().clickDaysBaseYearBaseSelectorOption(account.getDaysBaseYearBase());
    }

    public void setCommitmentTypeAmt(Account account) {
        Pages.addAccountPage().clickCommitmentTypeAmtSelectorButton();
        List<String> listOfCommitmentTypeAmt = Pages.addAccountPage().getCommitmentTypeAmtList();

        Assert.assertTrue(listOfCommitmentTypeAmt.size() > 0, "There are no 'Commitment type/amt' options available");
        if (account.getCommitmentTypeAmt() == null) {
            account.setCommitmentTypeAmt(listOfCommitmentTypeAmt.get(new Random().nextInt(listOfCommitmentTypeAmt.size())).trim());
        }
        Pages.addAccountPage().clickCommitmentTypeAmtSelectorOption(account.getCommitmentTypeAmt());
    }

    public void setRateChangeFrequency(Account account) {
        Pages.addAccountPage().clickRateChangeFrequencySelectorButton();
        List<String> listOfRateChangeFrequency = Pages.addAccountPage().getRateChangeFrequencyList();

        Assert.assertTrue(listOfRateChangeFrequency.size() > 0, "There are no 'Rate Change Frequency' options available");
        if (account.getRateChangeFrequency() == null) {
            account.setRateChangeFrequency(listOfRateChangeFrequency.get(new Random().nextInt(listOfRateChangeFrequency.size())).trim());
        }
        Pages.addAccountPage().clickRateChangeFrequencySelectorOption(account.getRateChangeFrequency());
    }

    public void setCycleCode(Account account) {
        Pages.addAccountPage().clickCycleCodeSelectorButton();
        List<String> listOfCycleCode = Pages.addAccountPage().getCycleCodeList();

        Assert.assertTrue(listOfCycleCode.size() > 0, "There are no 'Cycle Code' options available");
        if (account.getCycleCode() == null) {
            account.setCycleCode(listOfCycleCode.get(new Random().nextInt(listOfCycleCode.size())).trim());
        }
        Pages.addAccountPage().clickCycleCodeSelectorOption(account.getCycleCode());
    }

    public String getDateOpenedValue(Account account) {
        String dateOpened = Pages.addAccountPage().getDateOpened();
        int counter = 0;

        while (dateOpened.isEmpty() && counter < 10) {
            SelenideTools.refresh();
            AccountActions.createAccount().setProductType(account);
            AccountActions.createAccount().setProduct(account);
            dateOpened = Pages.addAccountPage().getDateOpened();
            counter++;
        }
        return dateOpened;
    }

    public void disableCycleLoanSwitch() {
        if (Constants.getEnvironment().equals("dev4") || Constants.getEnvironment().equals("dev29")) {
            if (Pages.addAccountPage().isCycleLoanValueYes()) {
                Pages.addAccountPage().clickCycleLoanSwitch();
                SelenideTools.sleep(Constants.MICRO_TIMEOUT);
            }
        } else {
            if (Pages.addAccountPage().getCycleLoanValue().equalsIgnoreCase("yes")) {
                Pages.addAccountPage().clickCycleLoanSwitch();
                SelenideTools.sleep(Constants.MICRO_TIMEOUT);
            }
        }
    }

    public void enableCycleLoanSwitch() {
        if(Constants.getEnvironment().equals("dev29") || Constants.getEnvironment().equals("dev4")){
            if (!(Pages.addAccountPage().isCycleLoanValueYes())) {
                Pages.addAccountPage().clickCycleLoanSwitch();
                SelenideTools.sleep(Constants.MICRO_TIMEOUT);
            }
        } else {
            if (Pages.addAccountPage().getCycleLoanValue().equalsIgnoreCase("no")) {
                Pages.addAccountPage().clickCycleLoanSwitch();
                SelenideTools.sleep(Constants.MICRO_TIMEOUT);
            }
        }
    }

    public void enableTeaserLoanSwitch() {
        if (Pages.addAccountPage().getTeaserLoanValue().equalsIgnoreCase("no")) {
            Pages.addAccountPage().clickTeaserLoanSwitch();
            SelenideTools.sleep(Constants.MICRO_TIMEOUT);
        }
    }

    public void disableLocPaymentRecalculationFlagValueSwitch() {
        if (Constants.getEnvironment().equals("dev4")) {
            if (Pages.addAccountPage().isLocPaymentRecalculationFlagYesValue()) {
                Pages.addAccountPage().clickLocPaymentRecalculationFlagValue();
                SelenideTools.sleep(Constants.MICRO_TIMEOUT);
            }
        } else {
            if (Pages.addAccountPage().getLocPaymentRecalculationFlagValue().equalsIgnoreCase("yes")) {
                Pages.addAccountPage().clickLocPaymentRecalculationFlagValue();
                SelenideTools.sleep(Constants.MICRO_TIMEOUT);
            }
        }
    }

    public void enableLocPaymentRecalculationFlagValueSwitch() {
        if (Pages.addAccountPage().getLocPaymentRecalculationFlagValue().equalsIgnoreCase("no")) {
            Pages.addAccountPage().clickLocPaymentRecalculationFlagValue();
            SelenideTools.sleep(Constants.MICRO_TIMEOUT);
        }
    }

    public void disableAdjustableRateSwitch() {
        if (Constants.getEnvironment().equals("dev4")) {
            if (Pages.addAccountPage().isAdjustableRateValueYesVisible()) {
                Pages.addAccountPage().clickAdjustableRate();
                SelenideTools.sleep(Constants.MICRO_TIMEOUT);
            }
        } else {
            if (Pages.addAccountPage().getAdjustableRateValue().equalsIgnoreCase("yes")) {
                Pages.addAccountPage().clickAdjustableRate();
                SelenideTools.sleep(Constants.MICRO_TIMEOUT);
            }
        }
    }

    public void setInterestRate(Account account) {
        if (!Boolean.parseBoolean(Pages.addAccountPage().isInterestRateDisabled())) {
            Pages.addAccountPage().setInterestRate(account.getInterestRate());
        }
    }

    /**
     * Verify common fields that are prefilled for all account types
     */

    private void verifyAccountPrefilledFields(Account account, IndividualClient client) {
        Assert.assertEquals(getDateOpenedValue(account), WebAdminActions.loginActions().getSystemDate(), "'Date' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getAccountType(), client.getIndividualType().getClientType().getClientType(), "'Account type' is prefilled with wrong value");
        final String accountHolderName = client.getIndividualType().getFirstName() + " " + client.getIndividualType().getLastName() + " (" + client.getIndividualType().getClientID() + ")";
        Assert.assertEquals(Pages.addAccountPage().getAccountHolderName(), accountHolderName, "'Name' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getAccountHolderRelationship(), "Owner", "'Relationship' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getAccountHolderClientType(), client.getIndividualType().getClientType().getClientType(), "'Client type' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getAccountHolderTaxID(), client.getIndividualType().getTaxID(), "'Tax ID' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getOriginatingOfficer(), client.getIndividualClientDetails().getSelectOfficer(), "'Originating officer' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getCurrentOfficer(), client.getIndividualClientDetails().getSelectOfficer(), "'Current officer' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getBankBranch(), account.getBankBranch(), "'Bank branch' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getMailCode(), client.getIndividualClientDetails().getMailCode().getMailCode(), "'Mail code' is prefilled with wrong value");
    }

    /**
     * Verify common prefilled fields for CD accounts
     */

    public void verifyCdAccountPrefilledFields(Account account) {
        Assert.assertEquals(Pages.addAccountPage().getTermType(), account.getMinTerm(), "'Term Type' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getInterestFrequency(), account.getInterestFrequency(), "'Interest Frequency' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getInterestType(), account.getInterestType(), "'Interest Type' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getApplyInterestTo(), "Remain in Account", "'Apply interest to' is prefilled with wrong value");
        if (Constants.getEnvironment().equals("dev4") || Constants.getEnvironment().equals("dev29") || Constants.getEnvironment().equals("dev12") || Constants.getEnvironment().equals("dev47")) {
            Assert.assertTrue(Pages.addAccountPage().isAutoRenewableYes(), "'Auto Renewable' is prefilled with wrong value");
            Assert.assertFalse(Pages.addAccountPage().isTransactionalAccountYes(), "'Transactional Account' is prefilled with wrong value");
        } else {
            Assert.assertEquals(Pages.addAccountPage().getAutoRenewable(), "YES", "'Auto Renewable' is prefilled with wrong value");
            Assert.assertEquals(Pages.addAccountPage().getTransactionalAccount().toLowerCase(), "no", "'Transactional Account' is prefilled with wrong value");
        }
        Assert.assertEquals(Pages.addAccountPage().getApplySeasonalAddress().toLowerCase(), "yes", "'Apply Seasonal Address' is prefilled with wrong value");
    }

    /**
     * Verify common prefilled fields for IRA accounts
     */

    public void verifyIraAccountPrefilledFields(Account account, IndividualClient client) {
        Assert.assertEquals(Pages.addAccountPage().getDateOfBirth(), client.getIndividualType().getBirthDate(), "'Date Of Birth' value does not match");
        Assert.assertEquals(Pages.addAccountPage().getIRADistributionFrequency(), account.getIraDistributionFrequency(), "'IRA Distribution Frequency' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getIRADistributionCode(), account.getIraDistributionCode(), "'IRA Distribution Code' is prefilled with wrong value");
    }

    /**
     * Verify prefilled fields for Savings account
     */

    public void verifySavingsAccountPrefilledFields(Account account, IndividualClient client) {
        verifyAccountPrefilledFields(account, client);
        if (Constants.getEnvironment().equals("dev4") || Constants.getEnvironment().equals("dev29") || Constants.getEnvironment().equals("dev47")) {
            Assert.assertTrue(Pages.addAccountPage().isApplySeasonalAddressYes(), "'Apply Seasonal Address' is prefilled with wrong value");
        } else {
            Assert.assertEquals(Pages.addAccountPage().getApplySeasonalAddress().toLowerCase(), "yes", "'Apply Seasonal Address' is prefilled with wrong value");
        }
    }

    /**
     * Verify prefilled fields for Savings IRA account
     */

    public void verifySavingsIraAccountPrefilledFields(Account account, IndividualClient client) {
        verifyAccountPrefilledFields(account, client);
        verifyIraAccountPrefilledFields(account, client);
        if (Constants.getEnvironment().equals("dev4")) {
            Assert.assertTrue(Pages.addAccountPage().isApplySeasonalAddressYes(), "'Apply Seasonal Address' is prefilled with wrong value");
        } else {
            Assert.assertEquals(Pages.addAccountPage().getApplySeasonalAddress().toLowerCase(), "yes", "'Apply Seasonal Address' is prefilled with wrong value");
        }
    }

    /**
     * Verify prefilled fields for CD account
     */

    public void verifyRegularCdAccountPrefilledFields(Account account, IndividualClient client) {
        verifyAccountPrefilledFields(account, client);
        verifyCdAccountPrefilledFields(account);
    }

    /**
     * Verify prefilled fields for CD IRA account
     */

    public void verifyCdIraAccountPrefilledFields(Account account, IndividualClient client) {
        verifyAccountPrefilledFields(account, client);
        verifyCdAccountPrefilledFields(account);
        verifyIraAccountPrefilledFields(account, client);
    }

    /**
     * Verify prefilled fields for Checking account
     */

    public void verifyChkAccountPrefilledFields(Account account, IndividualClient client) {
        verifyAccountPrefilledFields(account, client);
        Assert.assertEquals(Pages.addAccountPage().getOptInOutStatus(), "Client Has Not Responded", "'DBC ODP Opt In/Out Status' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getApplySeasonalAddress().toLowerCase(), "yes", "'Apply Seasonal Address' is prefilled with wrong value");
    }

    public String getLoanAccountId() {
            String url = SelenideTools.getCurrentUrl();
            String[] arr = url.split("/");
            int position = arr.length - 2;

            return  arr[position];
    }
}
