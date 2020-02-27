package com.nymbus.models.generation.client.builder.type.organisation;

import com.nymbus.models.client.OrganisationClient;

public abstract class OrganisationTypeBuilder {
    protected OrganisationClient organisationClient;

    public void createOrganisationClient() {
        organisationClient = new OrganisationClient();
    }

    public abstract void buildOrganisationType();

    public abstract void buildOrganisationClientDetails();

    public OrganisationClient getOrganisationClient() {
        return organisationClient;
    }
}
