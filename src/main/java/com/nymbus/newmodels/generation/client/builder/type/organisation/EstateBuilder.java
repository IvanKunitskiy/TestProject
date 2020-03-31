package com.nymbus.newmodels.generation.client.builder.type.organisation;

import com.nymbus.newmodels.client.other.document.IDType;
import com.nymbus.newmodels.generation.client.factory.basicinformation.type.organisation.EstateFactory;
import com.nymbus.newmodels.generation.client.factory.basicinformation.type.organisation.OrganisationTypeFactory;
import com.nymbus.newmodels.generation.client.factory.clientdetails.type.OrganisationClientDetailsFactory;

import java.util.Map;

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

    @Override
    public void buildOrganisationClientDocuments(Map<IDType, Integer> documents) {
        
    }
}
