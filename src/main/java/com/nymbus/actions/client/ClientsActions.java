package com.nymbus.actions.client;

public class ClientsActions {

    /**
     * Actions
     */
    private static CreateClient createClient;

    /**
     * This function return an instance of `CreateClient`
     */
    public static CreateClient createClient() {
        if (createClient == null) {
            createClient = new CreateClient();
        }
        return createClient;
    }
}
