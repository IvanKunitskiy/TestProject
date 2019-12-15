package com.nymbus.pages;

import com.nymbus.pages.homepages.LoginPage;

public class Pages {

    /**
     * Home pages
     */
    private static LoginPage loginPage;

    /**
     * This function return an instance of `LoginPage`
     */
    public static LoginPage loginPage() {
        if (loginPage == null) {
            loginPage = new LoginPage();
        }
        return loginPage;
    }
}
