package com.nymbus.models.generation.client.builder.type.individual;

import com.nymbus.models.client.IndividualClient;

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
