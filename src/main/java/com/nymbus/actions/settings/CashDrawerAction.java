package com.nymbus.actions.settings;

import com.nymbus.model.CashDrawer;
import com.nymbus.pages.Pages;
import com.nymbus.pages.settings.SettingsPage;
import org.testng.Assert;

import java.util.List;
import java.util.Random;

public class CashDrawerAction {

    public void openAllCashDrawerPage(){
        Pages.aSideMenuPage().clickSettingsMenuItem();
        Pages.settings().waitForSettingsPageLoaded();
        SettingsPage.mainPage().waitForCashDrawerRegion();
        SettingsPage.mainPage().clickViewAllCashDrawerLink();
    }

    public void searCashDrawerOnCashDrawerSearchPage(CashDrawer cashDrawer){
        openAllCashDrawerPage();
        SettingsPage.cashDrawerSearchPage().waitViewCashDrawerListVisible();
        SettingsPage.cashDrawerSearchPage().waitForPageLoaded();
        SettingsPage.cashDrawerSearchPage().setUserDataForSearching(cashDrawer.getName());
        SettingsPage.cashDrawerSearchPage().clickSearchButton();
        SettingsPage.cashDrawerSearchPage().clickCellByUserData(cashDrawer.getName());
    }

    public void createCashDrawer(CashDrawer cashDrawer){
        Pages.aSideMenuPage().clickSettingsMenuItem();
        Pages.settings().waitForSettingsPageLoaded();
        SettingsPage.mainPage().waitForCashDrawerRegion();
        SettingsPage.mainPage().clickAddNewCashDrawerLink();

        setCashDrawerData(cashDrawer);

        SettingsPage.addCashDrawerPage().clickSaveChangesButton();
    }

    private void setCashDrawerData(CashDrawer cashDrawer){
        SettingsPage.addCashDrawerPage().setNameValue(cashDrawer.getName());
        setCashDrawerType(cashDrawer);
        setDefaultUser(cashDrawer);
        setRandomBranch(cashDrawer);
        setRandomLocation(cashDrawer);
        setGLAccountNumber(cashDrawer);
        setFloatingValue(cashDrawer);
    }

    private void setCashDrawerType(CashDrawer cashDrawer){
        SettingsPage.addCashDrawerPage().setCashDrawerTypeValue(cashDrawer.getType());
        SettingsPage.addCashDrawerPage().clickCashDrawerTypeOption(cashDrawer.getType());
    }

    private void setDefaultUser(CashDrawer cashDrawer){
        SettingsPage.addCashDrawerPage().setDefaultUserValue(cashDrawer.getDefaultUser());
        SettingsPage.addCashDrawerPage().clickDefaultUserOption(cashDrawer.getDefaultUser());
    }

    private void setRandomBranch(CashDrawer cashDrawer) {

        SettingsPage.addCashDrawerPage().clickBranchSelectorButton();
        List<String> listOfBranch = SettingsPage.addCashDrawerPage().getBranchList();

        Assert.assertTrue(listOfBranch.size() > 0,
                "There are not an available branches");

        System.out.println("Branch: " + listOfBranch.get(new Random().nextInt(listOfBranch.size())));

        if (cashDrawer.getBranch() == null)
            cashDrawer.setBranch(listOfBranch.get(new Random().nextInt(listOfBranch.size())).trim());


        SettingsPage.addCashDrawerPage().setBranchValue(cashDrawer.getBranch());
        SettingsPage.addCashDrawerPage().clickBranchOption(cashDrawer.getBranch());
    }

    private void setRandomLocation(CashDrawer cashDrawer) {
        SettingsPage.addCashDrawerPage().clickLocationSelectorButton();
        List<String> listOfLocation = SettingsPage.addCashDrawerPage().getLocationList();

        Assert.assertTrue(listOfLocation.size() > 0,
                "There are not an available locations");
        if (cashDrawer.getLocation() == null)
            cashDrawer.setLocation(listOfLocation.get(new Random().nextInt(listOfLocation.size())).trim());
        SettingsPage.addCashDrawerPage().setLocationValue(cashDrawer.getLocation());
        SettingsPage.addCashDrawerPage().clickLocationOption(cashDrawer.getLocation());
    }

    private void setGLAccountNumber(CashDrawer cashDrawer){
        SettingsPage.addCashDrawerPage().setGLAccountNumberValue("%%%");
        SettingsPage.addCashDrawerPage().clickGLAccountNumberSearchButton();
        List<String> listOfAccounts = SettingsPage.addCashDrawerPage().getGLAccountNumberList();

        Assert.assertTrue(listOfAccounts.size() > 0,
                "There are not an available accounts");
        if (cashDrawer.getGlAccountNumber() == null)
            cashDrawer.setGlAccountNumber(listOfAccounts.get(new Random().nextInt(listOfAccounts.size())).trim());
        SettingsPage.addCashDrawerPage().setGLAccountNumberValue(cashDrawer.getGlAccountNumber());
        SettingsPage.addCashDrawerPage().clickGLAccountNumberOption(cashDrawer.getGlAccountNumber());
    }

    private void setFloatingValue(CashDrawer cashDrawer){
        if (cashDrawer.isFloating()) {
            if (!SettingsPage.addCashDrawerPage().isFloatingOptionActivated())
                SettingsPage.addCashDrawerPage().clickFloatingToggle();
        } else {
            if (SettingsPage.addCashDrawerPage().isFloatingOptionActivated())
                SettingsPage.addCashDrawerPage().clickFloatingToggle();
        }
    }

    public CashDrawer getCashDrawerFromCashDrawerViewPage(){
        CashDrawer cashDrawer = new CashDrawer();
        cashDrawer.setName(SettingsPage.viewCashDrawerPage().getName());
        cashDrawer.setType(SettingsPage.viewCashDrawerPage().getCashDrawerType());
        cashDrawer.setDefaultUser(SettingsPage.viewCashDrawerPage().getDefaultUser());
        cashDrawer.setBranch(SettingsPage.viewCashDrawerPage().getBranch());
        cashDrawer.setLocation(SettingsPage.viewCashDrawerPage().getLocation());
        cashDrawer.setGlAccountNumber(SettingsPage.viewCashDrawerPage().getGLAccount());
        cashDrawer.setFloating(SettingsPage.viewCashDrawerPage().isFloating());

        return cashDrawer;
    }

}
