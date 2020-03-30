package com.nymbus.newmodels.generation.client.builder.type.organisation;

import com.nymbus.newmodels.client.OrganisationClient;

public abstract class OrganisationTypeBuilder {
    protected OrganisationClient organisationClient;

    public void createOrganisationClient() {
        organisationClient = new OrganisationClient();
    }

    public abstract void buildOrganisationType();

    public abstract void buildOrganisationClientDetails();

    public abstract void buildOrganisationClientDocuments();

    public OrganisationClient getOrganisationClient() {
        return organisationClient;
    }
}
