package com.nymbus.actions.account;

import com.nymbus.models.account.Account;
import com.nymbus.pages.Pages;
import org.testng.Assert;

import java.util.List;
import java.util.Random;

public class CreateAccount {

    public void createAccount(Account account) {

    }

    public void setProductType(Account account) {
        System.out.println("____________>");
        Pages.addAccountPage().clickProductTypeSelectorButton();
        System.out.println("Product type " + account.getProductType());
        List<String> listOfProductType = Pages.addAccountPage().getProductTypeList();

        Assert.assertTrue(listOfProductType.size() > 0, "There are no product types available");
        if (account.getProductType() == null) {
            String randomProductType = listOfProductType.get(new Random().nextInt(listOfProductType.size())).trim();
            account.setProductType(randomProductType);
        }

//        Pages.addAccountPage().setProductTypeOption(account.getProductType());
        Pages.addAccountPage().clickProductTypeOption(account.getProductType());

    }

    public void setBoxSize(Account account) {
        Pages.addAccountPage().clickBoxSizeSelectorButton();
        List<String> listOfBoxSize = Pages.addAccountPage().getBoxSizeList();

        Assert.assertTrue(listOfBoxSize.size() > 0, "There are no box sizes available");
        if (account.getBoxSize() == null)
            account.setBoxSize(listOfBoxSize.get(new Random().nextInt(listOfBoxSize.size())).trim());
        Pages.addAccountPage().setBoxSizeOption(account.getBoxSize());
        Pages.addAccountPage().clickBoxSizeSelectorOption(account.getBoxSize());
    }

    public void setMailCode(Account account) {
        Pages.addAccountPage().clickBoxSizeSelectorButton();
        List<String> listOfBoxSize = Pages.addAccountPage().getBoxSizeList();

        Assert.assertTrue(listOfBoxSize.size() > 0, "There are no box sizes available");
        if (account.getBoxSize() == null)
            account.setBoxSize(listOfBoxSize.get(new Random().nextInt(listOfBoxSize.size())).trim());
        Pages.addAccountPage().setBoxSizeOption(account.getBoxSize());
        Pages.addAccountPage().clickBoxSizeSelectorOption(account.getBoxSize());
    }

}
