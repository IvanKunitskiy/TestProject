package com.nymbus.pages.webadmin;

import com.nymbus.pages.webadmin.userandsecurity.UsersPage;

public class WebAdminPages {

    /**
     * Pages
     */
    private static LoginPage loginPage;
    private static NavigationPage navigationPage;
    private static UsersPage usersPage;

    /**
     * This function return an instance of `LoginPage`
     */
    public static LoginPage loginPage() {
        if (loginPage == null) {
            loginPage = new LoginPage();
        }
        return loginPage;
    }

    /**
     * This function return an instance of `NavigationPage`
     */
    public static NavigationPage navigationPage() {
        if (navigationPage == null) {
            navigationPage = new NavigationPage();
        }
        return navigationPage;
    }

    /**
     * This function return an instance of `UsersPage`
     */
    public static UsersPage usersPage() {
        if (usersPage == null) {
            usersPage = new UsersPage();
        }
        return usersPage;
    }

}
