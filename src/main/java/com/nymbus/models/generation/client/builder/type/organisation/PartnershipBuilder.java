package com.nymbus.models.generation.client.builder.type.organisation;

import com.nymbus.models.generation.client.factory.basicinformation.type.organisation.OrganisationTypeFactory;
import com.nymbus.models.generation.client.factory.basicinformation.type.organisation.PartnershipFactory;
import com.nymbus.models.generation.client.factory.details.type.OrganisationClientDetailsFactory;

public class PartnershipBuilder extends OrganisationTypeBuilder {
    private OrganisationTypeFactory organisationTypeFactory;
    private OrganisationClientDetailsFactory organisationClientDetailsFactory;

    @Override
    public void buildOrganisationType() {
        organisationTypeFactory = new PartnershipFactory();
        organisationClient.setOrganisationType(organisationTypeFactory.getOrganisationType());
    }

    @Override
    public void buildOrganisationClientDetails() {
        organisationClientDetailsFactory = new OrganisationClientDetailsFactory();
        organisationClient.setOrganisationClientDetails(organisationClientDetailsFactory.getOrganisationClientDetails());
    }
}
