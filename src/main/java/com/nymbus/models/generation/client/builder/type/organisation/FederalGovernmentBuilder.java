package com.nymbus.models.generation.client.builder.type.organisation;

import com.nymbus.models.generation.client.factory.basicinformation.type.organisation.FederalGovernmentFactory;
import com.nymbus.models.generation.client.factory.basicinformation.type.organisation.OrganisationTypeFactory;
import com.nymbus.models.generation.client.factory.details.type.OrganisationClientDetailsFactory;

public class FederalGovernmentBuilder extends OrganisationTypeBuilder {
    private OrganisationTypeFactory organisationTypeFactory;
    private OrganisationClientDetailsFactory organisationClientDetailsFactory;

    @Override
    public void buildOrganisationType() {
        organisationTypeFactory = new FederalGovernmentFactory();
        organisationClient.setOrganisationType(organisationTypeFactory.getOrganisationType());
    }

    @Override
    public void buildOrganisationClientDetails() {
        organisationClientDetailsFactory = new OrganisationClientDetailsFactory();
        organisationClient.setOrganisationClientDetails(organisationClientDetailsFactory.getOrganisationClientDetails());
    }
}
