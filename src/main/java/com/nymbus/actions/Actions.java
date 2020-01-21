package com.nymbus.actions;

import com.nymbus.actions.settings.UsersActions;

public class Actions {

    private static LoginActions loginActions;
    private static UsersActions usersActions;

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
     * This function returns an instance of `UsersActions`
     */
    public static UsersActions usersActions() {
        if (usersActions == null) {
            usersActions = new UsersActions();
        }
        return usersActions;
    }
}
