package com.nymbus.actions.settings;

import com.nymbus.models.User;
import com.nymbus.pages.Pages;
import com.nymbus.pages.Settings;
import com.nymbus.pages.settings.SettingsPage;

public class UsersActions {

    public void createUser(User user){
        Pages.settings().waitForSettingsPageLoaded();
        SettingsPage.mainPage().waitForUserRegion();
        SettingsPage.mainPage().clickAddNewUserLink();

        SettingsPage.addingUsersPage().setFirstNameValue(user.getFirstName());
        SettingsPage.addingUsersPage().setLastNameValue(user.getLastName());
        SettingsPage.addingUsersPage().setInitialsValue(user.getInitials());

        SettingsPage.addingUsersPage().setTitleValue(user.getTitle());
        SettingsPage.addingUsersPage().setPhoneValue(user.getBusinessPhone());
        SettingsPage.addingUsersPage().setEmailValue(user.getEmail());
        SettingsPage.addingUsersPage().setLoginIDValue(user.getLoginID());

        addUserRoles(user);
    }

    public void addUserRoles(User user){
        for (int i=0; i<user.getRolesList().size(); i++){
            SettingsPage.addingUsersPage().clickRolesSelectorButton(i+1);
            SettingsPage.addingUsersPage().wait(3);
            SettingsPage.addingUsersPage().setRolesValue(user.getRolesList().get(i), i+1);
            SettingsPage.addingUsersPage().wait(3);
            SettingsPage.addingUsersPage().clickRolesOption(user.getRolesList().get(i), i+1);
            SettingsPage.addingUsersPage().wait(3);
            SettingsPage.addingUsersPage().clickAddRolesLink();
            SettingsPage.addingUsersPage().wait(3);
        }
    }

}
