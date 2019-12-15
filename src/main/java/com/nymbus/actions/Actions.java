package com.nymbus.actions;

public class Actions {

    private static LoginActions loginActions;

    /**
     * This function returns an instance of `LoginActions`
     */
    public static LoginActions loginActions() {
        if (loginActions == null) {
            loginActions = new LoginActions();
        }
        return loginActions;
    }
}
