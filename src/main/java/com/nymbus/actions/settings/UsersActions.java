package com.nymbus.actions.settings;

import com.nymbus.models.User;
import com.nymbus.pages.Pages;
import com.nymbus.pages.settings.SettingsPage;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class UsersActions {

    public void createUser(User user){
        Pages.settings().waitForSettingsPageLoaded();
        SettingsPage.mainPage().waitForUserRegion();
        SettingsPage.mainPage().clickAddNewUserLink();

        setUserData(user);

        SettingsPage.addingUsersPage().clickSaveChangesButton();

    }

    private void setUserData(User user){
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

        if(user.isActive()){
            if (!SettingsPage.addingUsersPage().isIsActiveOptionActivated())
                SettingsPage.addingUsersPage().clickIsActiveToggle();
        } else {
            if (SettingsPage.addingUsersPage().isIsActiveOptionActivated())
                SettingsPage.addingUsersPage().clickIsActiveToggle();
        }

        if(user.isLoginDisabled()){
            if (!SettingsPage.addingUsersPage().isLoginDisabledOptionActivated())
                SettingsPage.addingUsersPage().clickLoginDisabledToggle();
        } else {
            if (SettingsPage.addingUsersPage().isLoginDisabledOptionActivated())
                SettingsPage.addingUsersPage().clickLoginDisabledToggle();
        }

        if(user.isTeller()){
            if (!SettingsPage.addingUsersPage().isTellerOptionActivated())
                SettingsPage.addingUsersPage().clickTellerToggle();
        } else {
            if (SettingsPage.addingUsersPage().isTellerOptionActivated())
                SettingsPage.addingUsersPage().clickTellerToggle();
        }
    }

    private void addUserRoles(User user){
        for (int i=0; i<user.getRolesList().size(); i++){
            SettingsPage.addingUsersPage().clickRolesSelectorButton(i+1);
            SettingsPage.addingUsersPage().setRolesValue(user.getRolesList().get(i), i+1);
            SettingsPage.addingUsersPage().clickRolesOption(user.getRolesList().get(i), i+1);
            if (user.getRolesList().size() > (i+1))
                SettingsPage.addingUsersPage().clickAddRolesLink();
        }
    }

    private void addRandomBranch(User user){

        SettingsPage.addingUsersPage().clickBranchSelectorButton();
        List<String> listOfBranch = SettingsPage.addingUsersPage().getBranchList();

        Assert.assertTrue(listOfBranch.size()>0,
                "There are not an available branches");

        user.setBranch(listOfBranch.get(new Random().nextInt(listOfBranch.size())).trim());
        SettingsPage.addingUsersPage().setBranchValue(user.getBranch());
        SettingsPage.addingUsersPage().clickBranchOption(user.getBranch());
    }

    private void addRandomLocation(User user){
        SettingsPage.addingUsersPage().clickLocationSelectorButton();
        List<String> listOfLocation = SettingsPage.addingUsersPage().getLocationList();

        Assert.assertTrue(listOfLocation.size()>0,
                "There are not an available locations");

        user.setLocation(listOfLocation.get(new Random().nextInt(listOfLocation.size())).trim());
        SettingsPage.addingUsersPage().setLocationValue(user.getLocation());
        SettingsPage.addingUsersPage().clickLocationOption(user.getLocation());
    }

    public User getUserFromUserViewPage(){
        User user = new User();

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

}
