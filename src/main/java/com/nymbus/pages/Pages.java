package com.nymbus.pages;

import com.nymbus.pages.clients.ClientsPage;
import com.nymbus.pages.loans.LoansPage;
import com.nymbus.pages.reportgenerator.ReportGeneratorPage;

public class Pages {

    /**
     * Pages
     */
    private static LoginPage loginPage;
    private static NavigationPage navigationPage;
    private static ASideMenuPage aSideMenuPage;
    private static ClientsPage clientsPage;
    private static LoansPage loansPage;
    private static ReportGeneratorPage reportGeneratorPage;
    private static Settings settings;
    private static Settings settings1;

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
     * This function return an instance of `ASideMenuPage`
     */
    public static ASideMenuPage aSideMenuPage() {
        if (aSideMenuPage == null) {
            aSideMenuPage = new ASideMenuPage();
        }
        return aSideMenuPage;
    }

    /**
     * This function return an instance of `ClientsPage`
     */
    public static ClientsPage clientsPage() {
        if (clientsPage == null) {
            clientsPage = new ClientsPage();
        }
        return clientsPage;
    }

    /**
     * This function return an instance of `LoansPage`
     */
    public static LoansPage loansPage() {
        if (loansPage == null) {
            loansPage = new LoansPage();
        }
        return loansPage;
    }

    /**
     * This function return an instance of `ReportGeneratorPage`
     */
    public static ReportGeneratorPage reportGeneratorPage() {
        if (reportGeneratorPage == null) {
            reportGeneratorPage = new ReportGeneratorPage();
        }
        return reportGeneratorPage;
    }

    /**
     * This function return an instance of `Settings`
     */
    public static Settings settings() {
        if (settings == null) {
            settings = new Settings();
        }
        return settings;
    }

}
