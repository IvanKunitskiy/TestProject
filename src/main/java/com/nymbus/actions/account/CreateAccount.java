package com.nymbus.actions.account;

import com.nymbus.models.account.Account;
import com.nymbus.pages.Pages;
import org.testng.Assert;

import java.util.List;
import java.util.Random;

public class CreateAccount {

    public void clickAccountsTab() {
        // check if page is visible
        Pages.clientDetailsPage().clickAccountsTab();
    }

    public void createSavingsAccount(Account account) {
        clickAccountsTab();
        setAddNewOption(account);
        setProductType(account);
        setProduct(account);
        Pages.addAccountPage().setAccountNumberValue(account.getAccountNumber());
        Pages.addAccountPage().setAccountTitleValue(account.getAccountTitle());
        Pages.addAccountPage().setDateOpenedValue(account.getDateOpened());
        setCurrentOfficer(account);
        setBankBranch(account);
        account.setInterestRate(Pages.addAccountPage().generateInterestRateValue());
        Pages.addAccountPage().setInterestRate(account.getInterestRate());
        setStatementCycle(account);
        setCallClassCode(account);
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
        Pages.addAccountPage().setDateOpenedValue(account.getDateOpened());
        setCurrentOfficer(account);
        setBankBranch(account);
        account.setInterestRate(Pages.addAccountPage().generateInterestRateValue());
        Pages.addAccountPage().setInterestRate(account.getInterestRate());
        setStatementCycle(account);
        setCallClassCode(account);
        setChargeOrAnalyze(account);
        setAccountAnalysis(account);
        account.setEarningCreditRate(Pages.addAccountPage().generateEarningCreditRateValue());
        Pages.addAccountPage().setEarningCreditRate(account.getEarningCreditRate());
        Pages.addAccountPage().setOptInOutDateValue(account.getOptInOutDate());
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
        setBankBranch(account);
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();
    }

    public void setCorrespondingAccount(Account account) {
        Pages.addAccountPage().clickCorrespondingAccountSelectorButton();
        List<String> listOfCorrespondingAccount = Pages.addAccountPage().getCorrespondingAccountList();

        Assert.assertTrue(listOfCorrespondingAccount.size() > 0, "There are no product types available");
        if (account.getCorrespondingAccount() == null) {
            account.setCorrespondingAccount(listOfCorrespondingAccount.get(new Random().nextInt(listOfCorrespondingAccount.size())).trim());
        }
        Pages.addAccountPage().clickCorrespondingAccountSelectorOption(account.getCorrespondingAccount());
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
        Pages.addAccountPage().setBoxSizeOption(account.getBoxSize());
        Pages.addAccountPage().clickBoxSizeSelectorOption(account.getBoxSize());
    }

    public void setMailCode(Account account) {
        // set mail code here
    }

}
