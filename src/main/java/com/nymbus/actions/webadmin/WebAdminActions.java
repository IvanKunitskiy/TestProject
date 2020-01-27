package com.nymbus.actions.webadmin;

import com.nymbus.actions.webadmin.LoginActions;
import com.nymbus.actions.settings.UsersActions;

public class WebAdminActions {

    private static LoginActions loginActions;
    private static WebAdminUsersActions webAdminUsersActions;

    /**
     * This function returns an instance of `LoginActions`
     */
    public static LoginActions loginActions() {
        if (loginActions == null) {
            loginActions = new LoginActions();
        }
        return loginActions;
    }

    /**
     * This function returns an instance of `WebAdminUsersActions`
     */
    public static WebAdminUsersActions webAdminUsersActions() {
        if (webAdminUsersActions == null) {
            webAdminUsersActions = new WebAdminUsersActions();
        }
        return webAdminUsersActions;
    }
}
