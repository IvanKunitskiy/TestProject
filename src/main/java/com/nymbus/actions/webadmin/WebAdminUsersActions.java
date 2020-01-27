package com.nymbus.actions.webadmin;

import com.nymbus.models.User;
import com.nymbus.pages.webadmin.WebAdminPages;
import com.nymbus.util.Random;
import org.testng.Assert;

public class WebAdminUsersActions {

    public void setUserPassword(User user) {
        WebAdminPages.navigationPage().waitForPageLoaded();
        WebAdminPages.navigationPage().clickUsersAndSecurityItem();
        WebAdminPages.navigationPage().clickUsersItem();
        WebAdminPages.usersPage().waitUsersRegion();
        WebAdminPages.usersPage().setValueToUserField(user.getLoginID());
        WebAdminPages.usersPage().clickSearchButton();
        WebAdminPages.usersPage().waitForUserListRegion();
        user.setPassword(Random.genString(6));
        WebAdminPages.usersPage().setNewPassword(user.getPassword());
        WebAdminPages.usersPage().setConfirmPassword(user.getPassword());
        WebAdminPages.usersPage().clickSaveButton();
    }
}
