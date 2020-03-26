package com.nymbus.actions.client;

public class ClientsActions {

    /**
     * Actions
     */
    private static CreateClient createClient;
    private static VerifyClientDataActions verifyClientDataActions;
    private static CreateOrganisationClientActions createOrganisationClientActions;

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

    /**
     * This function return an instance of 'CreateOrganisationClientActions'
     */
    public static CreateOrganisationClientActions createOrganisationClientActions() {
        if (createOrganisationClientActions == null) {
            createOrganisationClientActions = new CreateOrganisationClientActions();
        }
        return createOrganisationClientActions;
    }
}
