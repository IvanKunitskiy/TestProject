package com.nymbus.models.generation.client.builder.type.organisation;

import com.nymbus.models.generation.client.factory.basicinformation.type.organisation.EstateFactory;
import com.nymbus.models.generation.client.factory.basicinformation.type.organisation.OrganisationTypeFactory;
import com.nymbus.models.generation.client.factory.details.type.OrganisationClientDetailsFactory;

public class EstateBuilder extends OrganisationTypeBuilder {
    private OrganisationTypeFactory organisationTypeFactory;
    private OrganisationClientDetailsFactory organisationClientDetailsFactory;

    @Override
    public void buildOrganisationType() {
        organisationTypeFactory = new EstateFactory();
        organisationClient.setOrganisationType(organisationTypeFactory.getOrganisationType());
    }

    @Override
    public void buildOrganisationClientDetails() {
        organisationClientDetailsFactory = new OrganisationClientDetailsFactory();
        organisationClient.setOrganisationClientDetails(organisationClientDetailsFactory.getOrganisationClientDetails());
    }
}
