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
