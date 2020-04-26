package com.nymbus.newmodels.generation.client.builder;

import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualTypeBuilder;
import com.nymbus.newmodels.generation.client.factory.basicinformation.AddressFactory;
import com.nymbus.newmodels.generation.client.factory.clientdetails.contactinformation.DocumentsFactory;
import com.nymbus.newmodels.generation.client.factory.clientdetails.contactinformation.EmailFactory;
import com.nymbus.newmodels.generation.client.factory.clientdetails.contactinformation.PhoneFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

public class IndividualClientBuilder {
    private IndividualTypeBuilder individualClientBuilder;

    public void setIndividualClientBuilder(IndividualTypeBuilder individualClientBuilder) {
        this.individualClientBuilder = individualClientBuilder;
    }

    public IndividualClient buildClient() {
        individualClientBuilder.createIndividualClient();
        individualClientBuilder.buildIndividualType();
        individualClientBuilder.buildIndividualClientDetails();

        IndividualClient individualClient = individualClientBuilder.getIndividualClient();

        return setPersonalData(individualClient);
    }

    private IndividualClient setPersonalData(IndividualClient individualClient) {
        AddressFactory addressFactory = new AddressFactory();
        PhoneFactory phoneFactory = new PhoneFactory();
        EmailFactory emailFactory = new EmailFactory();
        DocumentsFactory documentsFactory = new DocumentsFactory();

        individualClient.getIndividualType().setAddresses(Collections.singletonList(addressFactory.getAddress()));
        individualClient.getIndividualClientDetails().setPhones(new ArrayList<>(Arrays.asList(
                phoneFactory.getPhone(),
                phoneFactory.getPhone()
        )));
        individualClient.getIndividualClientDetails().setEmails(new ArrayList<>(Arrays.asList(
                emailFactory.getEmail(),
                emailFactory.getEmail()
        )));

        individualClient.getIndividualClientDetails().setDocuments(new ArrayList<>(Arrays.asList(
                documentsFactory.getPassport(),
                documentsFactory.getStateDriverLicense()
        )));

        return individualClient;
    }
}
