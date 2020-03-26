package com.nymbus.newmodels.generation.client.builder.type.individual;

import com.nymbus.newmodels.client.IndividualClient;

public abstract class IndividualTypeBuilder {
    protected IndividualClient individualClient;

    public void createIndividualClient() {
        individualClient = new IndividualClient();
    }

    public abstract void buildIndividualType();

    public abstract void buildIndividualClientDetails();

    public IndividualClient getIndividualClient() {
        return individualClient;
    }
}
