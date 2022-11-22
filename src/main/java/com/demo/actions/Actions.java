package com.demo.actions;

public class Actions {
    /**
     * Page actions
     * */
    private static LoginActions loginActions;
    private static MainActions mainActions;
    private static MainPageActions mainPageActions;
    private static SearchPageActions searchPageActions;

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
     * This function returns an instance of `MainPageActions`
     */
    public static MainPageActions mainPageActions() {
        if (mainPageActions == null) {
            mainPageActions = new MainPageActions();
        }
        return mainPageActions;
    }

    /**
     * This function returns an instance of `LoginActions`
     */
    public static SearchPageActions searchPageActions() {
        if (searchPageActions == null) {
            searchPageActions = new SearchPageActions();
        }
        return searchPageActions;
    }

    /**
     * This function returns an instance of `MainActions`
     */
    public static MainActions mainActions() {
        if (mainActions == null) {
            mainActions = new MainActions();
        }
        return mainActions;
    }
}