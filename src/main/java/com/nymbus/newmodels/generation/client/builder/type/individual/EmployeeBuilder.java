package com.nymbus.newmodels.generation.client.builder.type.individual;

import com.nymbus.newmodels.generation.client.factory.basicinformation.type.individual.EmployeeFactory;
import com.nymbus.newmodels.generation.client.factory.basicinformation.type.individual.IndividualTypeFactory;
import com.nymbus.newmodels.generation.client.factory.clientdetails.type.IndividualClientDetailsFactory;

public class EmployeeBuilder extends IndividualTypeBuilder {
    private IndividualTypeFactory individualTypeFactory;
    private IndividualClientDetailsFactory individualClientDetailsFactory;

    @Override
    public void buildIndividualType() {
        individualTypeFactory = new EmployeeFactory();
        individualClient.setIndividualType(individualTypeFactory.getIndividualType());
    }

    @Override
    public void buildIndividualClientDetails() {
        individualClientDetailsFactory = new IndividualClientDetailsFactory();
        individualClient.setIndividualClientDetails(individualClientDetailsFactory.getIndividualClientDetails());
    }
}
