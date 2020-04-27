package com.nymbus.actions.client;

import com.nymbus.newmodels.client.IndividualClient;

public class MainActions {

    public void createIndividualClient(IndividualClient individualClient){
        ClientsActions.individualClientActions().createClient(individualClient);
        ClientsActions.individualClientActions().setClientDetailsData(individualClient);
        ClientsActions.individualClientActions().setDocumentation(individualClient);

        ClientsActions.individualClientActions().verifyClientData(individualClient);
    }

}
