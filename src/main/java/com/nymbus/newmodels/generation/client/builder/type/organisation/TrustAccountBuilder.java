package com.nymbus.newmodels.generation.client.builder.type.organisation;

import com.nymbus.newmodels.generation.client.factory.basicinformation.type.organisation.OrganisationTypeFactory;
import com.nymbus.newmodels.generation.client.factory.basicinformation.type.organisation.TrustAccountFactory;
import com.nymbus.newmodels.generation.client.factory.clientdetails.type.OrganisationClientDetailsFactory;

public class TrustAccountBuilder extends OrganisationTypeBuilder {
    private OrganisationTypeFactory organisationTypeFactory;
    private OrganisationClientDetailsFactory organisationClientDetailsFactory;

    @Override
    public void buildOrganisationType() {
        organisationTypeFactory = new TrustAccountFactory();
        organisationClient.setOrganisationType(organisationTypeFactory.getOrganisationType());
    }

    @Override
    public void buildOrganisationClientDetails() {
        organisationClientDetailsFactory = new OrganisationClientDetailsFactory();
        organisationClient.setOrganisationClientDetails(organisationClientDetailsFactory.getOrganisationClientDetails());
    }
}
