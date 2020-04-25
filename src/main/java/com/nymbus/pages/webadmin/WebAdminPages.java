package com.nymbus.pages.webadmin;

import com.nymbus.pages.webadmin.accountstransactions.AccountsPage;
import com.nymbus.pages.webadmin.coresetup.RulesUIQueryAnalyzerPage;
import com.nymbus.pages.webadmin.userandsecurity.UsersPage;

public class WebAdminPages {

    /**
     * Pages
     */
    private static LoginPage loginPage;
    private static NavigationPage navigationPage;
    private static UsersPage usersPage;
    private static RulesUIQueryAnalyzerPage rulesUIQueryAnalyzerPage;
    private static AccountsPage accountsPage;

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

    /**
     * This function return an instance of `RulesUIQueryAnalyzerPage`
     */
    public static RulesUIQueryAnalyzerPage rulesUIQueryAnalyzerPage() {
        if (rulesUIQueryAnalyzerPage == null) {
            rulesUIQueryAnalyzerPage = new RulesUIQueryAnalyzerPage();
        }
        return rulesUIQueryAnalyzerPage;
    }

    /**
     * This function return an instance of `AccountsPage`
     */
    public static AccountsPage accountsPage() {
        if (accountsPage == null) {
            accountsPage = new AccountsPage();
        }
        return accountsPage;
    }
}
