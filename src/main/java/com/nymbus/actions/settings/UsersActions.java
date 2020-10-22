package com.nymbus.actions.settings;

import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.data.entity.CashDrawer;
import com.nymbus.data.entity.User;
import com.nymbus.newmodels.account.verifyingmodels.SafeDepositKeyValues;
import com.nymbus.newmodels.settings.UserSettings;
import com.nymbus.pages.Pages;
import com.nymbus.pages.settings.SettingsPage;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class UsersActions {

    public UserSettings getUserSettings() {
        Pages.aSideMenuPage().clickSettingsMenuItem();
        Pages.settings().waitForSettingsPageLoaded();
        SettingsPage.mainPage().clickViewProfile();
        UserSettings userSettings = new UserSettings();
        userSettings.setCashDrawer(getCashDrawerWithoutUserName());
        userSettings.setLocation(SettingsPage.viewUserPage().getLocationValue());
        return  userSettings;
    }

    private String getCashDrawerWithoutUserName() {
        String [] cashDrawerFullValue = SettingsPage.viewUserPage().getCashDrawerValue().split(" ");
        return String.format("%s", cashDrawerFullValue[0]);
    }

    public void openViewAllUsersPage() {
        Pages.aSideMenuPage().clickSettingsMenuItem();
        Pages.settings().waitForSettingsPageLoaded();
        SettingsPage.mainPage().waitForUserRegion();
        SettingsPage.mainPage().clickViewAllUsersLink();

    }

    public void searUserOnCustomerSearchPage(User user) {
        openViewAllUsersPage();
        SettingsPage.usersSearchPage().waitForPageLoaded();
        SelenideTools.sleep(Constants.MICRO_TIMEOUT);
        SettingsPage.usersSearchPage().waitViewUsersListLoading();
        SettingsPage.usersSearchPage().setUserDataForSearching(user.getFirstNameAndLastName());
        SettingsPage.usersSearchPage().clickSearchButton();
        SettingsPage.usersSearchPage().clickCellByUserData(user.getEmail());
    }

    public void searchUser(User user) {
        Pages.aSideMenuPage().clickSettingsMenuItem();

        Pages.settings().waitForSettingsPageLoaded();
        SettingsPage.mainPage().waitForUserRegion();
        SettingsPage.mainPage().setUserNameToSearchField(user.getLoginID());
        SettingsPage.mainPage().clickSearchUserButton();
        SettingsPage.usersSearchPage().waitForPageLoaded();
    }

    public void createUser(User user) {
        Pages.aSideMenuPage().clickSettingsMenuItem();
        Pages.settings().waitForSettingsPageLoaded();
        SettingsPage.mainPage().waitForUserRegion();
        SettingsPage.mainPage().clickAddNewUserLink();

        setUserData(user);

        SettingsPage.addingUsersPage().clickSaveChangesButton();

    }

    public void editUser(User user) {
        SettingsPage.viewUserPage().waitViewUserDataVisible();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SettingsPage.viewUserPage().clickEditButton();

        setUserData(user);

        SettingsPage.addingUsersPage().clickSaveChangesButton();
    }

    private void setUserData(User user) {
        SettingsPage.addingUsersPage().setFirstNameValue(user.getFirstName());
        SettingsPage.addingUsersPage().setLastNameValue(user.getLastName());
        SettingsPage.addingUsersPage().setInitialsValue(user.getInitials());

        addRandomBranch(user);
        addRandomLocation(user);

        SettingsPage.addingUsersPage().setTitleValue(user.getTitle());
        SettingsPage.addingUsersPage().setPhoneValue(user.getBusinessPhone());
        SettingsPage.addingUsersPage().setEmailValue(user.getEmail());
        SettingsPage.addingUsersPage().setLoginIDValue(user.getLoginID());

        addUserRoles(user);

        if (user.isActive()) {
            if (!SettingsPage.addingUsersPage().isIsActiveOptionActivated())
                SettingsPage.addingUsersPage().clickIsActiveToggle();
        } else {
            if (SettingsPage.addingUsersPage().isIsActiveOptionActivated())
                SettingsPage.addingUsersPage().clickIsActiveToggle();
        }

        if (user.isLoginDisabled()) {
            if (!SettingsPage.addingUsersPage().isLoginDisabledOptionActivated())
                SettingsPage.addingUsersPage().clickLoginDisabledToggle();
        } else {
            if (SettingsPage.addingUsersPage().isLoginDisabledOptionActivated())
                SettingsPage.addingUsersPage().clickLoginDisabledToggle();
        }

        if (user.isTeller()) {
            if (!SettingsPage.addingUsersPage().isTellerOptionActivated())
                SettingsPage.addingUsersPage().clickTellerToggle();
        } else {
            if (SettingsPage.addingUsersPage().isTellerOptionActivated())
                SettingsPage.addingUsersPage().clickTellerToggle();
        }

        addNewCashDrawer(user);
    }

    private void addUserRoles(User user) {

        deleteRoles();

        for (int i = 0; i < user.getRolesList().size(); i++) {
            SettingsPage.addingUsersPage().clickRolesSelectorButton(i + 1);
            SettingsPage.addingUsersPage().setRolesValue(user.getRolesList().get(i), i + 1);
            SettingsPage.addingUsersPage().clickRolesOption(user.getRolesList().get(i), i + 1);
            if (user.getRolesList().size() > (i + 1))
                SettingsPage.addingUsersPage().clickAddRolesLink();
        }
    }

    private void deleteRoles() {
        if (SettingsPage.addingUsersPage().getNumberOfRoles() >= 2) {
            for (int i = SettingsPage.addingUsersPage().getNumberOfRoles(); i >= 2; i--) {
                SettingsPage.addingUsersPage().clickDeleteRoleByIndex(i);
            }
        }
    }

    private void addRandomBranch(User user) {

        SettingsPage.addingUsersPage().clickBranchSelectorButton();
        List<String> listOfBranch = SettingsPage.addingUsersPage().getBranchList();

        Assert.assertTrue(listOfBranch.size() > 0,
                "There are not an available branches");
        if (user.getBranch() == null)
            user.setBranch(listOfBranch.get(new Random().nextInt(listOfBranch.size())).trim());
        SettingsPage.addingUsersPage().setBranchValue(user.getBranch());
        SettingsPage.addingUsersPage().clickBranchOption(user.getBranch());
        SettingsPage.addingUsersPage().waitForBranchHiddenValue();
    }

    private void addRandomLocation(User user) {
        SettingsPage.addingUsersPage().clickLocationSelectorButton();
        List<String> listOfLocation = SettingsPage.addingUsersPage().getLocationList();

        Assert.assertTrue(listOfLocation.size() > 0,
                "There are not an available locations");
        if (user.getLocation() == null)
            user.setLocation(listOfLocation.get(new Random().nextInt(listOfLocation.size())).trim());
        SettingsPage.addingUsersPage().setLocationValue(user.getLocation());
        SettingsPage.addingUsersPage().clickLocationOption(user.getLocation());
        SettingsPage.addingUsersPage().waitForLocationHiddenValue();
    }

    public void addNewCashDrawer(User user) {
        if (user.isTeller()) {
            SettingsPage.addingUsersPage().clickAddNewCashDrawerLink();
            SettingsPage.addingUsersPage().waitAddNewCashDrawerWindow();
            SettingsPage.addingUsersPage().setCashDrawerNameValue(user.getCashDrawer().getName());
            setCashDrawerType(user.getCashDrawer());
            setGLAccountNumber(user.getCashDrawer());
            SettingsPage.addingUsersPage().clickAddNewCashDrawerButton();
        }
    }

    private void setCashDrawerType(CashDrawer cashDrawer) {
        SettingsPage.addingUsersPage().clickCashDrawerTypeSelectorButton();
        SettingsPage.addingUsersPage().clickCashDrawerTypeOption(cashDrawer.getType());
    }

    private void setGLAccountNumber(CashDrawer cashDrawer) {
        SettingsPage.addingUsersPage().setGLAccountNumberValue("%%%");
        SettingsPage.addingUsersPage().clickGLAccountNumberSearchButton();
        List<String> listOfAccounts = SettingsPage.addingUsersPage().getGLAccountNumberList();

        Assert.assertTrue(listOfAccounts.size() > 0,
                "There are not an available accounts");
        if (cashDrawer.getGlAccountNumber() == null)
            cashDrawer.setGlAccountNumber(listOfAccounts.get(new Random().nextInt(listOfAccounts.size())).trim());
        SettingsPage.addingUsersPage().setGLAccountNumberValue(cashDrawer.getGlAccountNumber());
        SettingsPage.addingUsersPage().clickGLAccountNumberOption(cashDrawer.getGlAccountNumber());
    }

    public User getUserFromUserViewPage() {
        User user = new User();

        SettingsPage.viewUserPage().waitForUserDataRegion();
        user.setFirstName(SettingsPage.viewUserPage().getFirstNameValue());
        user.setLastName(SettingsPage.viewUserPage().getLastNameValue());
        user.setInitials(SettingsPage.viewUserPage().getInitialsValue());
        user.setBranch(SettingsPage.viewUserPage().getBranchValue());
        user.setLocation(SettingsPage.viewUserPage().getLocationValue());
        user.setTitle(SettingsPage.viewUserPage().getTitleValue());
        user.setBusinessPhone(SettingsPage.viewUserPage().getPhoneValue());
        user.setEmail(SettingsPage.viewUserPage().getEmailValue());
        user.setLoginID(SettingsPage.viewUserPage().getLoginIDValue());
        user.setIsLoginDisabledFlag(SettingsPage.viewUserPage().isLoginDisabled());
        user.setRolesList(SettingsPage.viewUserPage().getRolesValue());
        user.setIsActiveFlag(SettingsPage.viewUserPage().isUserActive());
        user.setIsTellerFlag(SettingsPage.viewUserPage().isTeller());

        Collections.sort(user.getRolesList());

        return user;
    }

    public void openSafeDepositBoxSizesPage() {
        Pages.aSideMenuPage().clickClientMenuItem();
        Pages.aSideMenuPage().clickSettingsMenuItem();
        Pages.settings().waitForSettingsPageLoaded();
        SettingsPage.mainPage().waitViewAllSafeDepositBoxSizes();
        SettingsPage.mainPage().clickViewAllSafeDepositBoxSizes();
        SettingsPage.safeDepositBoxSizesPage().waitForPageLoaded();
    }

    public void openBinControlPage() {
        Pages.aSideMenuPage().clickSettingsMenuItem();
        Pages.settings().waitForSettingsPageLoaded();
        SettingsPage.mainPage().waitViewAllBinControls();
        SettingsPage.mainPage().clickViewAllBinControls();
    }

    public List<SafeDepositKeyValues> getSafeDepositBoxValues() {

        List<SafeDepositKeyValues> safeDepositKeyValues = new ArrayList<>();
        int rowsCount = SettingsPage.safeDepositBoxSizesPage().getBoxSizeRowsCount();
        for (int i = 0; i < rowsCount; i++) {
            String boxSize = SettingsPage.safeDepositBoxSizesPage().getBoxSizeValueByIndex(i+1);
            String rentalAmount = SettingsPage.safeDepositBoxSizesPage().getRentalAmountByIndex(i+1);
            safeDepositKeyValues.add(new SafeDepositKeyValues(boxSize, Double.parseDouble(rentalAmount)));
        }
        return safeDepositKeyValues;
    }

    public String getBankBranch() {
        Pages.aSideMenuPage().clickSettingsMenuItem();
        SettingsPage.mainPage().clickViewProfile();

        return SettingsPage.viewUserPage().getBankBranch();
    }
}