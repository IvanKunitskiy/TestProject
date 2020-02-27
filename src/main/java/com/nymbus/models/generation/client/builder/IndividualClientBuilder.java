package com.nymbus.models.generation.client.builder;

import com.nymbus.models.client.IndividualClient;
import com.nymbus.models.generation.client.builder.type.individual.IndividualTypeBuilder;
import com.nymbus.models.generation.client.factory.basicinformation.AddressFactory;
import com.nymbus.models.generation.client.factory.clientdetails.contactinformation.EmailFactory;
import com.nymbus.models.generation.client.factory.clientdetails.contactinformation.PhoneFactory;

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

        individualClient.getIndividualType().setAddresses(Collections.singleton(addressFactory.getAddress()));
        individualClient.getIndividualClientDetails().setPhones(new HashSet<>(Arrays.asList(
                phoneFactory.getPhone(),
                phoneFactory.getPhone(),
                phoneFactory.getPhone()
        )));
        individualClient.getIndividualClientDetails().setEmails(new HashSet<>(Arrays.asList(
                emailFactory.getEmail(),
                emailFactory.getEmail(),
                emailFactory.getEmail()
        )));

        return individualClient;
    }
}
