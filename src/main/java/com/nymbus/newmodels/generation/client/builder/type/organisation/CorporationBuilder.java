package com.nymbus.newmodels.generation.client.builder.type.organisation;

import com.nymbus.newmodels.generation.client.factory.basicinformation.type.organisation.CorporationFactory;
import com.nymbus.newmodels.generation.client.factory.basicinformation.type.organisation.OrganisationTypeFactory;
import com.nymbus.newmodels.generation.client.factory.clientdetails.type.OrganisationClientDetailsFactory;
import com.nymbus.newmodels.generation.client.factory.clientdocuments.OrganisationDocumentsFactory;

import java.util.Collections;

public class CorporationBuilder extends OrganisationTypeBuilder {
    private OrganisationTypeFactory organisationTypeFactory;
    private OrganisationClientDetailsFactory organisationClientDetailsFactory;
    private OrganisationDocumentsFactory organisationDocumentsFactory;


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

    @Override
    public void buildOrganisationClientDocuments() {
        organisationDocumentsFactory = new OrganisationDocumentsFactory();
        organisationClient.setDocuments(Collections.singletonList(organisationDocumentsFactory.getCompanyID()));
    }
}
