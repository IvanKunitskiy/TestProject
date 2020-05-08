package com.nymbus.actions.account;

import com.nymbus.actions.Actions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.account.Account;
import com.nymbus.pages.Pages;
import org.testng.Assert;

import java.util.List;
import java.util.Random;

public class CreateAccount {

    public void clickAccountsTab() {
        // check if page is visible
        Pages.clientDetailsPage().clickAccountsTab();
    }

    public void createCDAccount(Account account) {
        clickAccountsTab();
        setAddNewOption(account);
        setProductType(account);
        setProduct(account);
        Pages.addAccountPage().setAccountNumberValue(account.getAccountNumber());
        Pages.addAccountPage().setAccountTitleValue(account.getAccountTitle());
        setCurrentOfficer(account);
        setBankBranch(account);
        Pages.addAccountPage().setDateOpenedValue(account.getDateOpened());
        setApplyInterestTo(account);
        setInterestType(account);
        Pages.addAccountPage().setInterestRate(account.getInterestRate());
        setCallClassCode(account);
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();
    }

    public void createIRAAccount(Account account) {
        clickAccountsTab();
        setAddNewOption(account);
        setProductType(account);
        setProduct(account);
        Pages.addAccountPage().setAccountNumberValue(account.getAccountNumber());
        Pages.addAccountPage().setAccountTitleValue(account.getAccountTitle());
        setInterestFrequency(account);
        Pages.addAccountPage().setDateOpenedValue(account.getDateOpened());
        setCurrentOfficer(account);
        setBankBranch(account);
        setStatementCycle(account);
        Pages.addAccountPage().setIRADistributionAmountValue(account.getIraDistributionAmount());
        Pages.addAccountPage().setDateNextIRADistributionValue(account.getDateNextIRADistribution());
        setCallClassCode(account);
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();
    }

    public void createSavingsAccount(Account account) {
        clickAccountsTab();
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
        setCallClassCode(account);
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();
    }

    public void createSavingAccountForTransactionPurpose(Account account) {
        clickAccountsTab();
        Actions.clientPageActions().closeAllNotifications();
        Pages.clientDetailsPage().clickAddNewButton();
        Pages.clientDetailsPage().clickAddNewValueOption(account.getAddNewOption());
        Pages.addAccountPage().clickProductTypeSelectorButton();
        Pages.addAccountPage().clickProductTypeOption(account.getProductType());
        Pages.addAccountPage().clickProductSelectorButton();
        Pages.addAccountPage().clickProductOption(account.getProduct());
        Pages.addAccountPage().setAccountNumberValue(account.getAccountNumber());
        Pages.addAccountPage().setDateOpenedValue(/*DateTime.getDateTodayPlusDaysWithFormat(0, "MM/dd/yyyy")*/WebAdminActions.loginActions().getSystemDate());
        setStatementCycle(account);
        Pages.addAccountPage().waitForAccountHolderName();
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();
    }

    public void createCDAccountForTransactionPurpose(Account account) {
        clickAccountsTab();
        Actions.clientPageActions().closeAllNotifications();
        Pages.clientDetailsPage().clickAddNewButton();
        Pages.clientDetailsPage().clickAddNewValueOption(account.getAddNewOption());
        Pages.addAccountPage().clickProductTypeSelectorButton();
        Pages.addAccountPage().clickProductTypeOption(account.getProductType());
        Pages.addAccountPage().clickProductSelectorButton();
        Pages.addAccountPage().clickProductOption(account.getProduct());
        Pages.addAccountPage().setAccountNumberValue(account.getAccountNumber());
        Pages.addAccountPage().setDateOpenedValue(DateTime.getDateTodayPlusDaysWithFormat(0, "MM/dd/yyyy"));
        Pages.addAccountPage().setInterestRate(account.getInterestRate());
        setApplyInterestTo(account);
        Pages.addAccountPage().waitForAccountHolderName();
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();
    }

    public void createCHKAccountForTransactionPurpose(Account account) {
        clickAccountsTab();
        Actions.clientPageActions().closeAllNotifications();
        Pages.clientDetailsPage().clickAddNewButton();
        Pages.clientDetailsPage().clickAddNewValueOption(account.getAddNewOption());
        Pages.addAccountPage().clickProductTypeSelectorButton();
        Pages.addAccountPage().clickProductTypeOption(account.getProductType());
        Pages.addAccountPage().clickProductSelectorButton();
        Pages.addAccountPage().clickProductOption(account.getProduct());
        Pages.addAccountPage().setAccountNumberValue(account.getAccountNumber());
        Pages.addAccountPage().setDateOpenedValue(/*DateTime.getDateTodayPlusDaysWithFormat(0, "MM/dd/yyyy")*/WebAdminActions.loginActions().getSystemDate());
        Pages.addAccountPage().clickStatementCycleSelectorButton();
        Pages.addAccountPage().clickStatementCycleOption("1");
        Pages.addAccountPage().waitForAccountHolderName();
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();
    }

    public void createCHKAccount(Account account) {
        clickAccountsTab();
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
        clickAccountsTab();
        setAddNewOption(account);
        setProductType(account);
        setBoxSize(account);
        Pages.addAccountPage().setAccountNumberValue(account.getAccountNumber());
        Pages.addAccountPage().setAccountTitleValue(account.getAccountTitle());
        Pages.addAccountPage().setDateOpenedValue(account.getDateOpened());
        selectValuesInDropdownFieldsRequiredForSafeDepositBoxAccount(account);
        Pages.addAccountPage().setDiscountPeriods(account.getDiscountPeriods());
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();
    }

    public void selectValuesInDropdownFieldsRequiredForSavingsAccount(Account account) {
        setCurrentOfficer(account);
        setBankBranch(account);
        setInterestFrequency(account);
        setStatementCycle(account);
        setCorrespondingAccount(account);
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
    }

    public void selectValuesInDropdownFieldsRequiredForCDIRAAccount(Account account) {
        setCurrentOfficer(account);
        setBankBranch(account);
        setCorrespondingAccount(account);
        setInterestFrequency(account);
        setApplyInterestTo(account);
        setInterestType(account);
        setCallClassCode(account);
        setIRADistributionFrequency(account);
        setIRADistributionCode(account);
    }

    public void selectValuesInDropdownFieldsRequiredForSafeDepositBoxAccount(Account account) {
        setCurrentOfficer(account);
        setBankBranch(account);
        setCorrespondingAccount(account);
        setDiscountReason(account);
    }

    public void selectValuesInDropdownFieldsRequiredForCheckingAccount(Account account) {
        setProduct(account);
        Pages.addAccountPage().setDateOpenedValue(account.getDateOpened());
        setCurrentOfficer(account);
        setBankBranch(account);
        setStatementCycle(account);
        setCallClassCode(account);
        setChargeOrAnalyze(account);
        setAccountAnalysis(account);
    }

    public void selectValuesInDropdownFieldsRequiredForCDAccount(Account account) {
        setCurrentOfficer(account);
        setBankBranch(account);
        setInterestFrequency(account);
        setApplyInterestTo(account);
        setInterestType(account);
        setCorrespondingAccount(account);
        setCallClassCode(account);
    }

    public void fillInInputFieldsRequiredForCheckingAccount(Account account) {
        Pages.addAccountPage().setAccountTitleValue(account.getAccountTitle());
        Pages.addAccountPage().setInterestRate(account.getInterestRate());
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

        Assert.assertTrue(listOfDiscountReason.size() > 0, "There are no options available");
        if (account.getDiscountReason() == null) {
            account.setDiscountReason(listOfDiscountReason.get(new Random().nextInt(listOfDiscountReason.size())).trim());
        }
        Pages.addAccountPage().clickDiscountReasonSelectorOption(account.getDiscountReason());
    }

    public void setInterestType(Account account) {
        Pages.addAccountPage().clickInterestTypeSelectorButton();
        List<String> listOfInterestType = Pages.addAccountPage().getInterestTypeList();

        Assert.assertTrue(listOfInterestType.size() > 0, "There are no product types available");
        if (account.getInterestType() == null) {
            account.setInterestType(listOfInterestType.get(new Random().nextInt(listOfInterestType.size())).trim());
        }
        Pages.addAccountPage().clickInterestTypeSelectorOption(account.getInterestType());
    }

    public void setApplyInterestTo(Account account) {
        Pages.addAccountPage().clickApplyInterestToSelectorButton();
        List<String> listOfApplyInterestTo = Pages.addAccountPage().getApplyInterestToList();

        Assert.assertTrue(listOfApplyInterestTo.size() > 0, "There are no product types available");
        if (account.getApplyInterestTo() == null) {
            account.setApplyInterestTo(listOfApplyInterestTo.get(new Random().nextInt(listOfApplyInterestTo.size())).trim());
        }
        Pages.addAccountPage().clickApplyInterestToSelectorOption(account.getApplyInterestTo());
    }

    public void setIRADistributionFrequency(Account account) {
        Pages.addAccountPage().clickIRADistributionFrequencySelectorButton();
        List<String> listOfIRADistributionFrequency = Pages.addAccountPage().getIRADistributionFrequencyList();

        Assert.assertTrue(listOfIRADistributionFrequency.size() > 0, "There are no product types available");
        if (account.getIraDistributionFrequency() == null) {
            account.setIraDistributionFrequency(listOfIRADistributionFrequency.get(new Random().nextInt(listOfIRADistributionFrequency.size())).trim());
        }
        Pages.addAccountPage().clickIRADistributionFrequencySelectorOption(account.getIraDistributionFrequency());
    }

    public void setIRADistributionCode(Account account) {
        Pages.addAccountPage().clickIRADistributionCodeSelectorButton();
        List<String> listOfIRADistributionCode = Pages.addAccountPage().getIRADistributionCodeList();

        Assert.assertTrue(listOfIRADistributionCode.size() > 0, "There are no product types available");
        if (account.getIraDistributionCode() == null) {
            account.setIraDistributionCode(listOfIRADistributionCode.get(new Random().nextInt(listOfIRADistributionCode.size())).trim());
        }
        Pages.addAccountPage().clickIRADistributionCodeSelectorOption(account.getIraDistributionCode());
    }

    public void setCorrespondingAccount(Account account) {
        Pages.addAccountPage().clickCorrespondingAccountSelectorButton();
        List<String> listOfCorrespondingAccount = Pages.addAccountPage().getCorrespondingAccountList();

        Assert.assertTrue(listOfCorrespondingAccount.size() > 0, "There are no product types available");
        if (account.getCorrespondingAccount() == null) {
            account.setCorrespondingAccount(listOfCorrespondingAccount.get(new Random().nextInt(listOfCorrespondingAccount.size())).trim());
        }
        Pages.addAccountPage().clickCorrespondingAccountSelectorOption(account.getCorrespondingAccount().replaceAll("[^0-9]", ""));
    }

    public void setInterestFrequency(Account account) {
        Pages.addAccountPage().clickInterestFrequencySelectorButton();
        List<String> listOfInterestFrequency = Pages.addAccountPage().getInterestFrequencyList();

        Assert.assertTrue(listOfInterestFrequency.size() > 0, "There are no product types available");
        if (account.getInterestFrequency() == null) {
            account.setInterestFrequency(listOfInterestFrequency.get(new Random().nextInt(listOfInterestFrequency.size())).trim());
        }
        Pages.addAccountPage().clickInterestFrequencySelectorOption(account.getInterestFrequency());
    }

    public void setChargeOrAnalyze(Account account) {
        Pages.addAccountPage().clickChargeOrAnalyzeSelectorButton();
        List<String> listOfChargeOrAnalyze = Pages.addAccountPage().getChargeOrAnalyzeList();

        Assert.assertTrue(listOfChargeOrAnalyze.size() > 0, "There are no product types available");
        if (account.getChargeOrAnalyze() == null) {
            account.setChargeOrAnalyze(listOfChargeOrAnalyze.get(new Random().nextInt(listOfChargeOrAnalyze.size())).trim());
        }
        Pages.addAccountPage().clickChargeOrAnalyzeSelectorOption(account.getChargeOrAnalyze());
    }

    public void setAccountAnalysis(Account account) {
        Pages.addAccountPage().clickAccountAnalysisSelectorButton();
        List<String> listOfAccountAnalysis = Pages.addAccountPage().getAccountAnalysisList();

        Assert.assertTrue(listOfAccountAnalysis.size() > 0, "There are no product types available");
        if (account.getAccountAnalysis() == null) {
            account.setAccountAnalysis(listOfAccountAnalysis.get(new Random().nextInt(listOfAccountAnalysis.size())).trim());
        }
        Pages.addAccountPage().clickAccountAnalysisSelectorOption(account.getAccountAnalysis());
    }

    public void setCallClassCode(Account account) {
        Pages.addAccountPage().clickCallClassCodeSelectorButton();
        List<String> listOfCallClassCode = Pages.addAccountPage().getCallClassCodeList();

        Assert.assertTrue(listOfCallClassCode.size() > 0, "There are no product types available");
        if (account.getCallClassCode() == null) {
            account.setCallClassCode(listOfCallClassCode.get(new Random().nextInt(listOfCallClassCode.size())).trim());
        }
        Pages.addAccountPage().clickCallClassCodeSelectorOption(account.getCallClassCode());
    }

    public void setCurrentOfficer(Account account) {
        Pages.addAccountPage().clickCurrentOfficerSelectorButton();
        List<String> listOfCurrentOfficers = Pages.addAccountPage().getCurrentOfficerList();

        Assert.assertTrue(listOfCurrentOfficers.size() > 0, "There are no product types available");
        if (account.getCurrentOfficer() == null) {
            account.setCurrentOfficer(listOfCurrentOfficers.get(new Random().nextInt(listOfCurrentOfficers.size())).trim());
        }
        Pages.addAccountPage().clickCurrentOfficerSelectorOption(account.getCurrentOfficer());
    }

    public void setStatementCycle(Account account) {
        Pages.addAccountPage().clickStatementCycleSelectorButton();
        List<String> listOfStatementCycle = Pages.addAccountPage().getStatementCycleList();

        Assert.assertTrue(listOfStatementCycle.size() > 0, "There are no product types available");
        if (account.getStatementCycle() == null) {
            account.setStatementCycle(listOfStatementCycle.get(new Random().nextInt(listOfStatementCycle.size())).trim());
        }
        Pages.addAccountPage().clickStatementCycleOption(account.getStatementCycle());
    }

    public void setProduct(Account account) {
        Pages.addAccountPage().clickProductSelectorButton();
        List<String> listOfProduct = Pages.addAccountPage().getProductList();

        Assert.assertTrue(listOfProduct.size() > 0, "There are no product types available");
        if (account.getProduct() == null) {
            account.setProduct(listOfProduct.get(new Random().nextInt(listOfProduct.size())).trim());
        }
        Pages.addAccountPage().clickProductOption(account.getProduct());
    }

    public void setBankBranch(Account account) {
        Pages.addAccountPage().clickBankBranchSelectorButton();
        List<String> listOfBankBranchOptions = Pages.addAccountPage().getBankBranchList();

        Assert.assertTrue(listOfBankBranchOptions.size() > 0, "There are no options available");
        if (account.getBankBranch() == null) {
            account.setBankBranch(listOfBankBranchOptions.get(new Random().nextInt(listOfBankBranchOptions.size())).trim());
        }
        Pages.addAccountPage().clickBankBranchOption(account.getBankBranch());
    }

    public void setAddNewOption(Account account) {
        Pages.clientDetailsPage().clickAddNewButton();
        List<String> listOfAddNewOptions = Pages.clientDetailsPage().getAddNewList();

        Assert.assertTrue(listOfAddNewOptions.size() > 0, "There are no options available");
        if (account.getAddNewOption() == null) {
            account.setAddNewOption(listOfAddNewOptions.get(new Random().nextInt(listOfAddNewOptions.size())).trim());
        }
        Pages.clientDetailsPage().clickAddNewValueOption(account.getAddNewOption());
    }

    public void setProductType(Account account) {
        Pages.addAccountPage().clickProductTypeSelectorButton();
        List<String> listOfProductType = Pages.addAccountPage().getProductTypeList();

        Assert.assertTrue(listOfProductType.size() > 0, "There are no product types available");
        if (account.getProductType() == null) {
            account.setProductType(listOfProductType.get(new Random().nextInt(listOfProductType.size())).trim());
        }
        Pages.addAccountPage().clickProductTypeOption(account.getProductType());
    }

    public void setBoxSize(Account account) {
        Pages.addAccountPage().clickBoxSizeSelectorButton();
        List<String> listOfBoxSize = Pages.addAccountPage().getBoxSizeList();

        Assert.assertTrue(listOfBoxSize.size() > 0, "There are no box sizes available");
        if (account.getBoxSize() == null) {
            account.setBoxSize(listOfBoxSize.get(new Random().nextInt(listOfBoxSize.size())).trim());
        }
        Pages.addAccountPage().clickBoxSizeSelectorOption(account.getBoxSize());
    }

    public void setMailCode(Account account) {
        Pages.addAccountPage().clickMailCodeSelectorButton();
        List<String> listOfMailCode = Pages.addAccountPage().getMailCodeList();

        Assert.assertTrue(listOfMailCode.size() > 0, "There are no options available");
        if (account.getMailCode() == null) {
            account.setMailCode(listOfMailCode.get(new Random().nextInt(listOfMailCode.size())).trim());
        }
        Pages.addAccountPage().clickMailCodeSelectorOption(account.getMailCode());
    }

}
