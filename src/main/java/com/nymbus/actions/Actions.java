package com.nymbus.actions;

import com.nymbus.actions.clients.ClientPageActions;
import com.nymbus.actions.clients.ClientsSearchResultsPageActions;
import com.nymbus.actions.settings.CashDrawerAction;
import com.nymbus.actions.settings.UsersActions;

public class Actions {

    private static LoginActions loginActions;
    private static UsersActions usersActions;
    private static CashDrawerAction cashDrawerAction;
    private static ClientPageActions clientPageActions;
    private static ClientsSearchResultsPageActions clientsSearchResultsPageActions;

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
}
