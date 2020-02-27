package com.nymbus.models;

import com.nymbus.models.client.Client;
import com.nymbus.models.client.OrganisationClient;
import com.nymbus.models.client.basicinformation.address.Address;
import com.nymbus.models.client.basicinformation.address.AddressType;
import com.nymbus.models.client.basicinformation.address.Country;
import com.nymbus.models.client.basicinformation.address.State;
import com.nymbus.models.client.basicinformation.type.TaxPayerIDType;
import com.nymbus.models.client.basicinformation.type.organisation.Estate;
import com.nymbus.models.client.basicinformation.type.organisation.OrganisationType;
import com.nymbus.models.client.details.contactinformation.email.Email;
import com.nymbus.models.client.details.contactinformation.email.EmailType;
import com.nymbus.models.client.details.contactinformation.phone.Phone;
import com.nymbus.models.client.details.contactinformation.phone.PhoneType;
import com.nymbus.models.client.details.type.organisation.OrganisationClientDetails;
import com.nymbus.models.client.other.document.CompanyID;
import com.nymbus.models.client.other.document.Document;
import com.nymbus.models.client.other.document.IDType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

public class Sample {
    public static void main(String[] args) {
        Address address = new Address();
        address.setType(AddressType.ALTERNATE);
        address.setCountry(Country.UNITED_STATES);
        address.setCity("qwe");
        address.setAddress("qwe");
        address.setZipCode("1234567");

        Phone phone11 = new Phone();
        phone11.setPhoneType(PhoneType.MOBILE);
        phone11.setCountry(Country.UNITED_STATES);
        phone11.setPhoneNumber("1234567890");

        Phone phone12 = new Phone();
        phone12.setPhoneType(PhoneType.HOME);
        phone12.setCountry(Country.UNITED_STATES);
        phone12.setPhoneNumber("0987654321");

        Email email11 = new Email();
        email11.setEmailType(EmailType.PRIMARY);
        email11.setEmail("qwerty");

        Email email12 = new Email();
        email12.setEmailType(EmailType.ALTERNATE);
        email12.setEmail("asdfgh");

        OrganisationType organisationType = new Estate();
        organisationType.setName("qwe");
        organisationType.setTaxPayerIDType(TaxPayerIDType.INDIVIDUAL_SSN);
        organisationType.setTaxID("1234567");
        organisationType.setAddresses(Collections.singleton(address));

        OrganisationClientDetails clientDetails = new OrganisationClientDetails();
        clientDetails.setIndustry("qwe");
        clientDetails.setUserDefinedFields(new ArrayList<>(Arrays.asList("qwe1", "qwe2", "qwe3")));
        clientDetails.setPhones(new HashSet<>(Arrays.asList(phone11, phone12)));
        clientDetails.setEmails(new HashSet<>(Arrays.asList(email11, email12)));

        Document document = new CompanyID();
        document.setIdType(IDType.COMPANY_ID);
        ((CompanyID) document).setCountry(Country.UNITED_STATES);
        ((CompanyID) document).setIssuedBy(State.ALABAMA);
        ((CompanyID) document).setIdNumber("qwe");

        Client client = new OrganisationClient();
        ((OrganisationClient) client).setOrganisationType(organisationType);
        ((OrganisationClient) client).setOrganisationClientDetails(clientDetails);
        client.setDocuments(Collections.singletonList(document));



        System.out.println(client);
        System.out.println("\n\n");



        Address address1 = new Address();
        address1.setType(AddressType.ALTERNATE);
        address1.setCountry(Country.UNITED_STATES);
        address1.setCity("qwe");
        address1.setAddress("qwe");
        address1.setZipCode("1234567");

        Phone phone21 = new Phone();
        phone21.setPhoneType(PhoneType.MOBILE);
        phone21.setCountry(Country.UNITED_STATES);
        phone21.setPhoneNumber("1234567890");

        Phone phone22 = new Phone();
        phone22.setPhoneType(PhoneType.HOME);
        phone22.setCountry(Country.UNITED_STATES);
        phone22.setPhoneNumber("0987654321");

        Email email21 = new Email();
        email21.setEmailType(EmailType.PRIMARY);
        email21.setEmail("qwerty");

        Email email22 = new Email();
        email22.setEmailType(EmailType.ALTERNATE);
        email22.setEmail("asdfgh");

        OrganisationType organisationType1 = new Estate();
        organisationType1.setName("qwe");
        organisationType1.setTaxPayerIDType(TaxPayerIDType.INDIVIDUAL_SSN);
        organisationType1.setTaxID("1234567");
        organisationType1.setAddresses(Collections.singleton(address1));

        OrganisationClientDetails clientDetails1 = new OrganisationClientDetails();
        clientDetails1.setIndustry("qwe");
        clientDetails1.setUserDefinedFields(new ArrayList<>(Arrays.asList("qwe1", "qwe2", "qwe3")));
        clientDetails1.setPhones(new HashSet<>(Arrays.asList(phone21, phone22)));
        clientDetails1.setEmails(new HashSet<>(Arrays.asList(email21, email22)));

        Document document1 = new CompanyID();
        document1.setIdType(IDType.COMPANY_ID);
        ((CompanyID) document1).setCountry(Country.UNITED_STATES);
        ((CompanyID) document1).setIssuedBy(State.ALABAMA);
        ((CompanyID) document1).setIdNumber("qwe");

        Client client1 = new OrganisationClient();
        ((OrganisationClient) client1).setOrganisationType(organisationType1);
        ((OrganisationClient) client1).setOrganisationClientDetails(clientDetails1);
        client1.setDocuments(Collections.singletonList(document1));

        System.out.println(client1);
        System.out.println("\n\n");

        System.out.println(client.equals(client1));

        /*OrganisationClientBuilder organisationClientBuilder = new OrganisationClientBuilder();
        organisationClientBuilder.setOrganisationTypeBuilder(new TrustAccountBuilder());
        System.out.println(organisationClientBuilder.buildClient());*/
    }
}
