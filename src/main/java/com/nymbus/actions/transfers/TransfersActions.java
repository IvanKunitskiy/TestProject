package com.nymbus.actions.transfers;

public class TransfersActions {

    /**
     * Actions
     */

    private static AddNewTransferActions addNewTransferActions;

    /**
     * This function return an instance of `AddNewTransferActions`
     */

    public static AddNewTransferActions addNewTransferActions() {
        if (addNewTransferActions == null) {
            addNewTransferActions = new AddNewTransferActions();
        }
        return addNewTransferActions;
    }

}