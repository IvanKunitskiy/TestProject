package com.nymbus.newmodels.generation.client.builder.type.organisation;

import com.nymbus.newmodels.client.OrganisationClient;
import com.nymbus.newmodels.client.other.document.IDType;

import java.util.Map;

public abstract class OrganisationTypeBuilder {
    protected OrganisationClient organisationClient;

    public void createOrganisationClient() {
        organisationClient = new OrganisationClient();
    }

    public abstract void buildOrganisationType();

    public abstract void buildOrganisationClientDetails();

    public abstract void buildOrganisationClientDocuments(Map<IDType, Integer> documents);

    public OrganisationClient getOrganisationClient() {
        return organisationClient;
    }
}
