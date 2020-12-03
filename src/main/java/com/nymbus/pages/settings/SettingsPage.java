package com.nymbus.pages.settings;

import com.nymbus.pages.settings.bankcontrol.BankControlPage;
import com.nymbus.pages.settings.cashdrawer.AddCashDrawerPage;
import com.nymbus.pages.settings.cashdrawer.CashDrawerSearchPage;
import com.nymbus.pages.settings.cashdrawer.ViewCashDrawerPage;
import com.nymbus.pages.settings.products.ProductOverviewPage;
import com.nymbus.pages.settings.products.ProductsOverviewPage;
import com.nymbus.pages.settings.safedepositboxsizes.SafeDepositBoxSizesPage;
import com.nymbus.pages.settings.tellerlocation.BranchOverviewPage;
import com.nymbus.pages.settings.tellerlocation.TellerLocationOverviewPage;
import com.nymbus.pages.settings.users.AddUsersPage;
import com.nymbus.pages.settings.users.UsersSearchPage;
import com.nymbus.pages.settings.users.ViewUserPage;

public class SettingsPage {

    /**
     * Pages
     */
    private static MainPage mainPage;
    private static AddUsersPage addUsersPage;
    private static ViewUserPage viewUserPage;
    private static UsersSearchPage usersSearchPage;
    private static AddCashDrawerPage addCashDrawerPage;
    private static ViewCashDrawerPage viewCashDrawerPage;
    private static CashDrawerSearchPage cashDrawerSearchPage;
    private static SafeDepositBoxSizesPage safeDepositBoxSizesPage;
    private static ProductsOverviewPage productsOverviewPage;
    private static ProductOverviewPage productOverviewPage;
    private static TellerLocationOverviewPage tellerLocationOverviewPage;
    private static BranchOverviewPage branchOverviewPage;
    private static BankControlPage bankControlPage;

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
    public static AddUsersPage addingUsersPage() {
        if (addUsersPage == null) {
            addUsersPage = new AddUsersPage();
        }
        return addUsersPage;
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

    /**
     * This function return an instance of `UsersSearchPage`
     */
    public static UsersSearchPage usersSearchPage() {
        if (usersSearchPage == null) {
            usersSearchPage = new UsersSearchPage();
        }
        return usersSearchPage;
    }

    /**
     * This function return an instance of `AddCashDrawerPage`
     */
    public static AddCashDrawerPage addCashDrawerPage() {
        if (addCashDrawerPage == null) {
            addCashDrawerPage = new AddCashDrawerPage();
        }
        return addCashDrawerPage;
    }

    /**
     * This function return an instance of `ViewCashDrawerPage`
     */
    public static ViewCashDrawerPage viewCashDrawerPage() {
        if (viewCashDrawerPage == null) {
            viewCashDrawerPage = new ViewCashDrawerPage();
        }
        return viewCashDrawerPage;
    }

    /**
     * This function return an instance of `CashDrawerSearchPage`
     */
    public static CashDrawerSearchPage cashDrawerSearchPage() {
        if (cashDrawerSearchPage == null) {
            cashDrawerSearchPage = new CashDrawerSearchPage();
        }
        return cashDrawerSearchPage;
    }

    /**
     * This function return an instance of `SafeDepositBoxSizesPage`
     */
    public static SafeDepositBoxSizesPage safeDepositBoxSizesPage() {
        if (safeDepositBoxSizesPage == null) {
            safeDepositBoxSizesPage = new SafeDepositBoxSizesPage();
        }
        return safeDepositBoxSizesPage;
    }

    /**
     * This function return an instance of `ProductsOverview`
     */
    public static ProductsOverviewPage productsOverviewPage() {
        if (productsOverviewPage == null) {
            productsOverviewPage = new ProductsOverviewPage();
        }
        return productsOverviewPage;
    }

    /**
     * This function return an instance of `ProductOverview`
     */
    public static ProductOverviewPage productOverviewPage() {
        if (productOverviewPage == null) {
            productOverviewPage = new ProductOverviewPage();
        }
        return productOverviewPage;
    }

    /**
     * This function return an instance of `TellerLocationOverviewPage`
     */
    public static TellerLocationOverviewPage tellerLocationOverviewPage() {
        if (tellerLocationOverviewPage == null) {
            tellerLocationOverviewPage = new TellerLocationOverviewPage();
        }
        return tellerLocationOverviewPage;
    }

    /**
     * This function return an instance of `BranchOverviewPage`
     */
    public static BranchOverviewPage branchOverviewPage() {
        if (branchOverviewPage == null) {
            branchOverviewPage = new BranchOverviewPage();
        }
        return branchOverviewPage;
    }

    /**
     * This function return an instance of `BranchOverviewPage`
     */
    public static BankControlPage bankControlPage() {
        if (bankControlPage == null) {
            bankControlPage = new BankControlPage();
        }
        return bankControlPage;
    }
}