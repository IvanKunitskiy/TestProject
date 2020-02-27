package com.nymbus.models.generation.client.builder.type.organisation;

import com.nymbus.models.generation.client.factory.basicinformation.type.organisation.CorporationFactory;
import com.nymbus.models.generation.client.factory.basicinformation.type.organisation.OrganisationTypeFactory;
import com.nymbus.models.generation.client.factory.details.type.OrganisationClientDetailsFactory;

public class CorporationBuilder extends OrganisationTypeBuilder {
    private OrganisationTypeFactory organisationTypeFactory;
    private OrganisationClientDetailsFactory organisationClientDetailsFactory;

    @Override
    public void buildOrganisationType() {
        organisationTypeFactory = new CorporationFactory();
        organisationClient.setOrganisationType(organisationTypeFactory.getOrganisationType());
    }

    @Override
    public void buildOrganisationClientDetails() {
        organisationClientDetailsFactory = new OrganisationClientDetailsFactory();
        organisationClient.setOrganisationClientDetails(organisationClientDetailsFactory.getOrganisationClientDetails());
    }
}
