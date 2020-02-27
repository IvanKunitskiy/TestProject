package com.nymbus.models.generation.client.builder;

import com.nymbus.models.client.OrganisationClient;
import com.nymbus.models.generation.client.builder.type.organisation.OrganisationTypeBuilder;
import com.nymbus.models.generation.client.factory.basicinformation.AddressFactory;
import com.nymbus.models.generation.client.factory.details.contactinformation.EmailFactory;
import com.nymbus.models.generation.client.factory.details.contactinformation.PhoneFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

public class OrganisationClientBuilder {
    private OrganisationTypeBuilder organisationTypeBuilder;

    public void setOrganisationTypeBuilder(OrganisationTypeBuilder organisationTypeBuilder) {
        this.organisationTypeBuilder = organisationTypeBuilder;
    }

    public OrganisationClient buildClient() {
        organisationTypeBuilder.createOrganisationClient();
        organisationTypeBuilder.buildOrganisationType();
        organisationTypeBuilder.buildOrganisationClientDetails();

        OrganisationClient organisationClient = organisationTypeBuilder.getOrganisationClient();

        return setPersonalData(organisationClient);
    }

    private OrganisationClient setPersonalData(OrganisationClient organisationClient) {
        AddressFactory addressFactory = new AddressFactory();
        PhoneFactory phoneFactory = new PhoneFactory();
        EmailFactory emailFactory = new EmailFactory();

        organisationClient.getOrganisationType().setAddresses(Collections.singleton(addressFactory.getAddress()));
        organisationClient.getOrganisationClientDetails().setPhones(new HashSet<>(Arrays.asList(
                phoneFactory.getPhone(),
                phoneFactory.getPhone(),
                phoneFactory.getPhone()
        )));
        organisationClient.getOrganisationClientDetails().setEmails(new HashSet<>(Arrays.asList(
                emailFactory.getEmail(),
                emailFactory.getEmail(),
                emailFactory.getEmail()
        )));

        return organisationClient;
    }
}
