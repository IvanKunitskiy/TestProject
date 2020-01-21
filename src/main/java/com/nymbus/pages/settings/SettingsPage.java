package com.nymbus.pages.settings;

import com.nymbus.pages.settings.users.AddingUsersPage;
import com.nymbus.pages.settings.users.ViewUserPage;

public class SettingsPage {

    /**
     * Pages
     */
    private static MainPage mainPage;
    private static AddingUsersPage addingUsersPage;
    private static ViewUserPage viewUserPage;

    /**
     * This function return an instance of `MainPage`
     */
    public static MainPage mainPage() {
        if (mainPage == null) {
            mainPage = new MainPage();
        }
        return mainPage;
    }

    /**
     * This function return an instance of `AddingUsersPage`
     */
    public static AddingUsersPage addingUsersPage() {
        if (addingUsersPage == null) {
            addingUsersPage = new AddingUsersPage();
        }
        return addingUsersPage;
    }

    /**
     * This function return an instance of `ViewUserPage`
     */
    public static ViewUserPage viewUserPage() {
        if (viewUserPage == null) {
            viewUserPage = new ViewUserPage();
        }
        return viewUserPage;
    }


}
