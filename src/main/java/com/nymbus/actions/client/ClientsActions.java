package com.nymbus.actions.client;

import com.nymbus.actions.client.individual.IndividualClientActions;
import com.nymbus.actions.client.organisation.OrganisationClientActions;

public class ClientsActions {

    /**
     * Actions
     */
    private static CreateClient createClient;
    private static VerifyClientDataActions verifyClientDataActions;
    private static CreateOrganisationClientActions createOrganisationClientActions;
    private static IndividualClientActions individualClientActions;
    private static OrganisationClientActions organisationClientActions;
    private static MainActions mainActions;

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

    /**
     * This function return an instance of 'IndividualClientActions'
     */
    public static IndividualClientActions individualClientActions() {
        if (individualClientActions == null) {
            individualClientActions = new IndividualClientActions();
        }
        return individualClientActions;
    }

    /**
     * This function return an instance of 'OrganisationClientActions'
     */
    public static OrganisationClientActions organisationClientActions() {
        if (organisationClientActions == null) {
            organisationClientActions = new OrganisationClientActions();
        }
        return organisationClientActions;
    }

    /**
     * This function return an instance of 'MainActions'
     */
    public static MainActions mainActions() {
        if (mainActions == null) {
            mainActions = new MainActions();
        }
        return mainActions;
    }
}
