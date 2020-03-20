package com.nymbus.newmodels.generation.client.builder.type.individual;

import com.nymbus.newmodels.generation.client.factory.basicinformation.type.individual.IndividualFactory;
import com.nymbus.newmodels.generation.client.factory.basicinformation.type.individual.IndividualTypeFactory;
import com.nymbus.newmodels.generation.client.factory.clientdetails.type.IndividualClientDetailsFactory;

public class IndividualBuilder extends IndividualTypeBuilder {
    private IndividualTypeFactory individualTypeFactory;
    private IndividualClientDetailsFactory individualClientDetailsFactory;

    @Override
    public void buildIndividualType() {
        individualTypeFactory = new IndividualFactory();
        individualClient.setIndividualType(individualTypeFactory.getIndividualType());
    }

    @Override
    public void buildIndividualClientDetails() {
        individualClientDetailsFactory = new IndividualClientDetailsFactory();
        individualClient.setIndividualClientDetails(individualClientDetailsFactory.getIndividualClientDetails());
    }
}
