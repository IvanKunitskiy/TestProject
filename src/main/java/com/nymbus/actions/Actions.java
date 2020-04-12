package com.nymbus.actions;

import com.nymbus.actions.clients.ClientPageActions;
import com.nymbus.actions.clients.ClientsSearchResultsPageActions;
import com.nymbus.actions.clients.maintenance.MaintenanceHistoryPageActions;
import com.nymbus.actions.clients.maintenance.MaintenancePageActions;
import com.nymbus.actions.modalwindow.DebitCardModalWindowActions;
import com.nymbus.actions.settings.CashDrawerAction;
import com.nymbus.actions.settings.UsersActions;
import com.nymbus.actions.transaction.TransactionActions;

public class Actions {
    /**
     * Page actions
     * */
    private static LoginActions loginActions;
    private static UsersActions usersActions;
    private static CashDrawerAction cashDrawerAction;
    private static ClientPageActions clientPageActions;
    private static ClientsSearchResultsPageActions clientsSearchResultsPageActions;
    private static MaintenancePageActions maintenancePageActions;
    private static MaintenanceHistoryPageActions maintenanceHistoryPageActions;
    private static TransactionActions transactionActions;
    private static MainActions mainActions;

    /**
     * Modal Window Actions
     * */
    private static DebitCardModalWindowActions debitCardModalWindowActions;

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

    /**
     * This function returns an instance of `CashDrawerAction`
     */
    public static CashDrawerAction cashDrawerAction() {
        if (cashDrawerAction == null) {
            cashDrawerAction = new CashDrawerAction();
        }
        return cashDrawerAction;
    }

    /**
     * This function returns an instance of `ClientPageActions`
     */
    public static ClientPageActions clientPageActions() {
        if (clientPageActions == null) {
            clientPageActions = new ClientPageActions();
        }
        return clientPageActions;
    }

    /**
     * This function returns an instance of `ClientsSearchResultsPageActions`
     */
    public static ClientsSearchResultsPageActions clientsSearchResultsPageActions() {
        if (clientsSearchResultsPageActions == null) {
            clientsSearchResultsPageActions = new ClientsSearchResultsPageActions();
        }
        return clientsSearchResultsPageActions;
    }

    /**
     * This function returns an instance of `MaintenancePageActions`
     */
    public static MaintenancePageActions maintenancePageActions() {
        if (maintenancePageActions == null) {
            maintenancePageActions = new MaintenancePageActions();
        }
        return maintenancePageActions;
    }

    /**
     * This function returns an instance of `DebitCardModalWindowActions`
     */
    public static DebitCardModalWindowActions debitCardModalWindowActions() {
        if (debitCardModalWindowActions == null) {
            debitCardModalWindowActions = new DebitCardModalWindowActions();
        }
        return debitCardModalWindowActions;
    }

    /**
     * This function returns an instance of `MaintenanceHistoryPageActions`
     */
    public static MaintenanceHistoryPageActions maintenanceHistoryPageActions() {
        if (maintenanceHistoryPageActions == null) {
            maintenanceHistoryPageActions = new MaintenanceHistoryPageActions();
        }
        return maintenanceHistoryPageActions;
    }

    /**
     * This function returns an instance of `TransactionActions`
     */
    public static TransactionActions transactionActions() {
        if (transactionActions == null) {
            transactionActions = new TransactionActions();
        }
        return transactionActions;
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