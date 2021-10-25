package com.nymbus.actions.settings;

import com.nymbus.actions.Actions;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.data.entity.CashDrawer;
import com.nymbus.newmodels.transaction.verifyingModels.CashDrawerData;
import com.nymbus.pages.Pages;
import com.nymbus.pages.settings.SettingsPage;
import org.testng.Assert;

import java.util.List;
import java.util.Random;

public class CashDrawerAction {

    public void openAllCashDrawerPage() {
        Pages.aSideMenuPage().clickSettingsMenuItem();
        Pages.settings().waitForSettingsPageLoaded();
        SettingsPage.mainPage().waitForCashDrawerRegion();
        SettingsPage.mainPage().clickViewAllCashDrawerLink();
    }

    public void searchCashDrawerOnCashDrawerSearchPage(CashDrawer cashDrawer) {
        openAllCashDrawerPage();
        SettingsPage.cashDrawerSearchPage().waitViewCashDrawerListVisible();
        SettingsPage.cashDrawerSearchPage().waitForPageLoaded();
        //todo temp waiter
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SettingsPage.cashDrawerSearchPage().setUserDataForSearching(cashDrawer.getName());
        SettingsPage.cashDrawerSearchPage().clickSearchButton();
        SettingsPage.cashDrawerSearchPage().clickCellByUserData(cashDrawer.getName());
    }

    public void createCashDrawer(CashDrawer cashDrawer) {
        Pages.aSideMenuPage().clickSettingsMenuItem();
        Pages.settings().waitForSettingsPageLoaded();
        SettingsPage.mainPage().waitForCashDrawerRegion();
        SettingsPage.mainPage().clickAddNewCashDrawerLink();

        setCashDrawerData(cashDrawer);

        SettingsPage.addCashDrawerPage().clickSaveChangesButton();
    }

    public void createCashRecyclerDispenser(CashDrawer cashDrawer) {
        Pages.aSideMenuPage().clickSettingsMenuItem();
        Pages.settings().waitForSettingsPageLoaded();
        SettingsPage.mainPage().waitForCashDrawerRegion();
        SettingsPage.mainPage().clickAddNewCashDrawerLink();

        setCashRecyclerData(cashDrawer);

        SettingsPage.addCashDrawerPage().clickSaveChangesButton();
        SelenideTools.sleep(Constants.MINI_TIMEOUT);
    }

    private void setCashRecyclerData(CashDrawer cashDrawer) {
        SettingsPage.addCashDrawerPage().setNameValue(cashDrawer.getName());
        setCashDrawerType(cashDrawer);
        setRandomBranch(cashDrawer);
        setRandomLocation(cashDrawer);
        setGLAccountNumber(cashDrawer);
        SettingsPage.addCashDrawerPage().clickIncludeCoinDispenserSwitcher();
    }

    private void setCashDrawerData(CashDrawer cashDrawer) {
        SettingsPage.addCashDrawerPage().setNameValue(cashDrawer.getName());
        setCashDrawerType(cashDrawer);
        setDefaultUser(cashDrawer);
        setRandomBranch(cashDrawer);
        setRandomLocation(cashDrawer);
        setGLAccountNumber(cashDrawer);
        setFloatingValue(cashDrawer);
    }

    private void setCashDrawerType(CashDrawer cashDrawer) {
        SettingsPage.addCashDrawerPage().setCashDrawerTypeValue(cashDrawer.getType());
        SettingsPage.addCashDrawerPage().clickCashDrawerTypeOption(cashDrawer.getType());
    }

    private void setDefaultUser(CashDrawer cashDrawer) {
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
        SettingsPage.addCashDrawerPage().waitForBranchHiddenValue();
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
        SettingsPage.addCashDrawerPage().waitForLocationHiddenValue();
    }

    private void setGLAccountNumber(CashDrawer cashDrawer) {
        SettingsPage.addCashDrawerPage().setGLAccountNumberValue("%%%");
        SettingsPage.addCashDrawerPage().clickGLAccountNumberSearchButton();
        List<String> listOfAccounts = SettingsPage.addCashDrawerPage().getGLAccountNumberList();

        Assert.assertTrue(listOfAccounts.size() > 0,
                "There are not an available accounts");
        if (cashDrawer.getGlAccountNumber() == null)
            cashDrawer.setGlAccountNumber(listOfAccounts.get(new Random().nextInt(listOfAccounts.size())).trim());
        SettingsPage.addCashDrawerPage().setGLAccountNumberValue(cashDrawer.getGlAccountNumber());
        SettingsPage.addCashDrawerPage().clickGLAccountNumberOption(cashDrawer.getGlAccountNumber());
        SettingsPage.addCashDrawerPage().waitForGLAccountHiddenValue();
    }

    private void setFloatingValue(CashDrawer cashDrawer) {
        if (cashDrawer.isFloating()) {
            if (!SettingsPage.addCashDrawerPage().isFloatingOptionActivated())
                SettingsPage.addCashDrawerPage().clickFloatingToggle();
        } else {
            if (SettingsPage.addCashDrawerPage().isFloatingOptionActivated())
                SettingsPage.addCashDrawerPage().clickFloatingToggle();
        }
    }

    public CashDrawer getCashDrawerFromCashDrawerViewPage() {
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

    public void goToCashDrawerPage() {
        Pages.aSideMenuPage().clickCashDrawerMenuItem();

      //  SelenideTools.sleep(Constants.SMALL_TIMEOUT);
    }

    public CashDrawerData getCashDrawerData() {
        CashDrawerData data = new CashDrawerData();
        data.setCashIn(getCashInValue());
        data.setCashOut(getCashOutValue());
        data.setCountedCash(getCountedCashValue());
        data.setHundredsAmount(getHundredsValue());
        data.setFiftiesAmount(getFiftiesValue());

        return data;
    }

    private double getCashInValue() {
        String value = Pages.cashDrawerBalancePage().getCashIn();
        return value.equals("") ? 0.00 : Double.parseDouble(value);
    }

    private double getCashOutValue() {
        String value = Pages.cashDrawerBalancePage().getCashOut();
        return value.equals("") ? 0.00 : Double.parseDouble(value);
    }

    private double getCountedCashValue() {
        String value = Pages.cashDrawerBalancePage().getCountedCash();
        return value.equals("") ? 0.00 : Double.parseDouble(value);
    }

    public double getCountedCashValueWithoutFormat() {
        String value = Pages.cashDrawerBalancePage().getCountedCashWithoutFormat();
        return value.equals("") ? 0.00 : Double.parseDouble(value);
    }

    private double getHundredsValue() {
        String value = Pages.cashDrawerBalancePage().getHundredsAmount();
        return value.equals("") ? 0.00 : Double.parseDouble(value);
    }

    private double getFiftiesValue() {
        String value = Pages.cashDrawerBalancePage().getFiftiesAmount();
        return value.equals("") ? 0.00 : Double.parseDouble(value);
    }

    public void selectSpecificCashDrawer(String name) {
        Pages.cashDrawerBalancePage().clickCashDrawerField();
        Pages.cashDrawerBalancePage().pickSpecificCashDrawerNameFromDropdown(name);
    }

    public void searchForCashDrawerByNameOnCashDrawerViewPage(String cashDrawerName) {
        Pages.aSideMenuPage().clickSettingsMenuItem();
        Pages.settings().waitForSettingsPageLoaded();
        SettingsPage.mainPage().waitForCashDrawerRegion();
        SettingsPage.mainPage().clickViewAllCashDrawerLink();
        SelenideTools.sleep(Constants.MICRO_TIMEOUT);

        SettingsPage.viewCashDrawerPage().typeIntoSearchField(cashDrawerName);
        SettingsPage.viewCashDrawerPage().clickSearchButton();
    }

    public boolean isCashRecyclerExist(String cashRecyclerName) {
        searchForCashDrawerByNameOnCashDrawerViewPage(cashRecyclerName);
        return SettingsPage.viewCashDrawerPage().isSpecificCashDrawerTypeForSpecificUserPresent(cashRecyclerName, "Cash Recycler");
    }

    public void isCashRecyclerExistAndCreateIfNot(String userLoginId) {
        String cashRecyclerName = userLoginId + "CashRecycler";
        if(!isCashRecyclerExist(cashRecyclerName)){
            CashDrawer cashRecycler = new CashDrawer();
            cashRecycler.setName(cashRecyclerName);
            cashRecycler.setType("Cash Recycler");
            cashRecycler.setBranch(Actions.usersActions().getBankBranch());
            cashRecycler.setLocation(Actions.usersActions().getLocation());
            createCashRecyclerDispenser(cashRecycler);
        }
    }

    public boolean isCashDispenserExist(String cashDispenserName) {
        searchForCashDrawerByNameOnCashDrawerViewPage(cashDispenserName);
        return SettingsPage.viewCashDrawerPage().isSpecificCashDrawerTypeForSpecificUserPresent(cashDispenserName, "Cash Dispenser");
    }

    public void isCashDispenserExistAndCreateIfNot(String userLoginId) {
        String cashDispenser= userLoginId + "CashDispenser";
        if(!isCashDispenserExist(cashDispenser)){
            CashDrawer cashRecycler = new CashDrawer();
            cashRecycler.setName(cashDispenser);
            cashRecycler.setType("Cash Dispenser");
            cashRecycler.setBranch(Actions.usersActions().getBankBranch());
            cashRecycler.setLocation(Actions.usersActions().getLocation());
            createCashRecyclerDispenser(cashRecycler);
        }
    }

}