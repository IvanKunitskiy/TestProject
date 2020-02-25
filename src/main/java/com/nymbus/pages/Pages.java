package com.nymbus.pages;

import com.nymbus.pages.clients.AddClientPage;
import com.nymbus.pages.clients.ClientDetailsPage;
import com.nymbus.pages.clients.ClientsPage;
import com.nymbus.pages.clients.ClientsSearchResultsPage;
import com.nymbus.pages.clients.maintenance.MaintenancePage;
import com.nymbus.pages.loans.LoansPage;
import com.nymbus.pages.reportgenerator.ReportGeneratorPage;
import com.nymbus.pages.teller.TellerPage;
import com.nymbus.pages.tellertotellertransfer.TellerToTellerPage;

public class Pages {

    /**
     * Pages
     */
    private static LoginPage loginPage;
    private static NavigationPage navigationPage;
    private static ASideMenuPage aSideMenuPage;
    private static AddClientPage addClientPage;
    private static ClientsPage clientsPage;
    private static ClientDetailsPage clientDetailsPage;
    private static TellerPage tellerPage;
    private static LoansPage loansPage;
    private static ReportGeneratorPage reportGeneratorPage;
    private static TellerToTellerPage tellerToTellerPage;
    private static Settings settings;
    private static ClientsSearchResultsPage clientsSearchResultsPage;
    private static MaintenancePage maintenancePage;

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
    public static AddClientPage addClientPage() {
        if (addClientPage == null) {
            addClientPage = new AddClientPage();
        }
        return addClientPage;
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
     * This function return an instance of `ClientDetailsPage`
     */
    public static ClientDetailsPage clientDetailsPage() {
        if (clientDetailsPage == null) {
            clientDetailsPage = new ClientDetailsPage();
        }
        return clientDetailsPage;
    }

    /**
     * This function return an instance of `TellerPage`
     */
    public static TellerPage tellerPage() {
        if (tellerPage == null) {
            tellerPage = new TellerPage();
        }
        return tellerPage;
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
     * This function return an instance of `TellerToTellerPage`
     */
    public static TellerToTellerPage tellerToTellerPage() {
        if (tellerToTellerPage == null) {
            tellerToTellerPage = new TellerToTellerPage();
        }
        return tellerToTellerPage;
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

    /**
     * This function return an instance of `ClientsSearchResultsPage`
     */
    public static ClientsSearchResultsPage clientsSearchResultsPage() {
        if (clientsSearchResultsPage == null) {
            clientsSearchResultsPage = new ClientsSearchResultsPage();
        }
        return clientsSearchResultsPage;
    }

    /**
     * This function return an instance of `MaintenancePage`
     */
    public static MaintenancePage maintenancePage() {
        if (maintenancePage == null) {
            maintenancePage = new MaintenancePage();
        }
        return maintenancePage;
    }
}
