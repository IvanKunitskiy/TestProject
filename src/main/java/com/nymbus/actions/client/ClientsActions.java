package com.nymbus.actions.client;

public class ClientsActions {

    /**
     * Actions
     */
    private static CreateClient createClient;
    private static VerifyClientDataActions verifyClientDataActions;

    /**
     * This function return an instance of `CreateClient`
     */
    public static CreateClient createClient() {
        if (createClient == null) {
            createClient = new CreateClient();
        }
        return createClient;
    }

    /**
     * This function return an instance of `VerifyClientDataActions`
     */
    public static VerifyClientDataActions verifyClientDataActions() {
        if (verifyClientDataActions == null) {
            verifyClientDataActions = new VerifyClientDataActions();
        }
        return verifyClientDataActions;
    }
}
