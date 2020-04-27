package com.nymbus.newmodels.generation.client.builder;

import com.nymbus.core.utils.Functions;
import com.nymbus.newmodels.client.OrganisationClient;
import com.nymbus.newmodels.client.basicinformation.address.Address;
import com.nymbus.newmodels.client.clientdetails.contactinformation.email.Email;
import com.nymbus.newmodels.client.clientdetails.contactinformation.phone.Phone;
import com.nymbus.newmodels.generation.client.OrganisationClientSettings;
import com.nymbus.newmodels.generation.client.builder.type.organisation.OrganisationTypeBuilder;
import com.nymbus.newmodels.generation.client.factory.basicinformation.AddressFactory;
import com.nymbus.newmodels.generation.client.factory.clientdetails.contactinformation.DocumentsFactory;
import com.nymbus.newmodels.generation.client.factory.clientdetails.contactinformation.EmailFactory;
import com.nymbus.newmodels.generation.client.factory.clientdetails.contactinformation.PhoneFactory;

import java.util.ArrayList;
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
        buildSignature(organisationClient);

        return setPersonalData(organisationClient);
    }

    private OrganisationClient setPersonalData(OrganisationClient organisationClient) {
        AddressFactory addressFactory = new AddressFactory();
        PhoneFactory phoneFactory = new PhoneFactory();
        EmailFactory emailFactory = new EmailFactory();
        DocumentsFactory documentsFactory = new DocumentsFactory();

        organisationClient.getOrganisationType().setAddresses(Collections.singletonList(addressFactory.getAddress()));
        organisationClient.getOrganisationClientDetails().setPhones(new ArrayList<>(Arrays.asList(
                phoneFactory.getPhone(),
                phoneFactory.getPhone()
        )));
        organisationClient.getOrganisationClientDetails().setEmails(new ArrayList<>(Arrays.asList(
                emailFactory.getEmail(),
                emailFactory.getEmail()
        )));

        organisationClient.getOrganisationClientDetails().setDocuments(new ArrayList<>(Arrays.asList(
                documentsFactory.getPassport(),
                documentsFactory.getStateDriverLicense()
        )));

        return organisationClient;
    }

    private HashSet<Email> getEmails(int emailsCount, EmailFactory emailFactory) {
        HashSet<Email> emails = new HashSet<>(emailsCount);
        for (int i = 0; i < emailsCount; i++) {
            if (i == 0) {
                emails.add(emailFactory.getEmail());
            }
            else {
                emails.add(emailFactory.getAlternateEmail());
            }
        }
        return emails;
    }

    private HashSet<Phone> getPhones(int phonesCount, PhoneFactory phoneFactory) {
        HashSet<Phone> phones = new HashSet<>(phonesCount);
        for (int i = 0; i < phonesCount; i++) {
           phones.add(phoneFactory.getPhone());
        }
        return phones;
    }

    private HashSet<Address> getAddresses(int address, AddressFactory addressFactory) {
        HashSet<Address> addresses = new HashSet<>(address);
        for (int i = 0; i < address; i++) {
            addresses.add(addressFactory.getAddress());
        }
        return addresses;
    }

    private void buildSignature(OrganisationClient organisationClient) {
        organisationClient.setClientSignature(Functions.getFilePathByName("clientSignature.png"));
    }
}
