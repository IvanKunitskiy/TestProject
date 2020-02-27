package com.nymbus.models.generation.client.builder.type.individual;

import com.nymbus.models.generation.client.factory.basicinformation.type.individual.DirectorFactory;
import com.nymbus.models.generation.client.factory.basicinformation.type.individual.IndividualTypeFactory;
import com.nymbus.models.generation.client.factory.details.type.IndividualClientDetailsFactory;

public class DirectorBuilder extends IndividualTypeBuilder {
    private IndividualTypeFactory individualTypeFactory;
    private IndividualClientDetailsFactory individualClientDetailsFactory;

    @Override
    public void buildIndividualType() {
        individualTypeFactory = new DirectorFactory();
        individualClient.setIndividualType(individualTypeFactory.getIndividualType());
    }

    @Override
    public void buildIndividualClientDetails() {
        individualClientDetailsFactory = new IndividualClientDetailsFactory();
        individualClient.setIndividualClientDetails(individualClientDetailsFactory.getIndividualClientDetails());
    }
}
