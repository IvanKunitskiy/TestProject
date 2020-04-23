package com.nymbus.actions.transfers;

public class TransfersActions {

    /**
     * Actions
     */

    private static AddNewTransferActions addNewTransferActions;
    private static EditTransferActions editTransferActions;

    /**
     * This function return an instance of `AddNewTransferActions`
     */

    public static AddNewTransferActions addNewTransferActions() {
        if (addNewTransferActions == null) {
            addNewTransferActions = new AddNewTransferActions();
        }
        return addNewTransferActions;
    }

    /**
     * This function return an instance of `EditTransferActions`
     */

    public static EditTransferActions editTransferActions() {
        if (editTransferActions == null) {
            editTransferActions = new EditTransferActions();
        }
        return editTransferActions;
    }

}