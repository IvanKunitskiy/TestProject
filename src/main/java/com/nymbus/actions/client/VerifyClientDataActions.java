package com.nymbus.actions.client;

import com.nymbus.models.client.Address;
import com.nymbus.models.client.Client;
import com.nymbus.models.client.IdentityDocument;
import com.nymbus.pages.Pages;

import java.util.ArrayList;
import java.util.List;

public class VerifyClientDataActions {

    public Client getIndividualClientData() {
        Client client = new Client();

        Pages.clientDetailsPage().waitForPageLoaded();

        client.setClientID(Pages.clientDetailsPage().getClientID());
        Pages.clientDetailsPage().clickProfileTab();
        setIndividualInformation(client);
        setContactInformation(client);
        setAddressInformation(client);
        Pages.clientDetailsPage().clickDocumentsTab();
        setIdentityDocuments(client);

        return client;
    }

    private void setIndividualInformation(Client client) {
        client.setClientType(Pages.clientDetailsPage().getType());
        client.setClientStatus(Pages.clientDetailsPage().getStatus());
        client.setFirstName(Pages.clientDetailsPage().getFirstName());
        client.setMiddleName(Pages.clientDetailsPage().getMiddleName());
        client.setLastName(Pages.clientDetailsPage().getLastName());
        client.setSuffix(Pages.clientDetailsPage().getSuffix());
        client.setMaidenFamilyName(Pages.clientDetailsPage().getMaidenFamilyName());
        client.setAKA_1(Pages.clientDetailsPage().getAka_1());
        client.setTaxPayerIDType(Pages.clientDetailsPage().getTaxPairIdType());
        client.setTaxID(Pages.clientDetailsPage().getTaxID());
        client.setBirthDate(Pages.clientDetailsPage().getBirthDate());
        client.setGender(Pages.clientDetailsPage().getGender());
        client.setEducation(Pages.clientDetailsPage().getEducation());
        client.setIncome(Pages.clientDetailsPage().getIncome());
        client.setMaritalStatus(Pages.clientDetailsPage().getMaritalStatus());
        client.setOccupation(Pages.clientDetailsPage().getOccupation());
        client.setConsumerInfoIndicator(Pages.clientDetailsPage().getConsumerInformationIndicator());
        client.setJobTitle(Pages.clientDetailsPage().getJobTitle());
        client.setOwnOrRent(Pages.clientDetailsPage().getOwnOrRent());
        client.setMailCode(Pages.clientDetailsPage().getMailCode());
        client.setSelectOfficer(Pages.clientDetailsPage().getSelectOfficer());
        client.setUserDefined_1(Pages.clientDetailsPage().getUserDefined1());
        client.setUserDefined_2(Pages.clientDetailsPage().getUserDefined2());
        client.setUserDefined_3(Pages.clientDetailsPage().getUserDefined3());
        client.setUserDefined_4(Pages.clientDetailsPage().getUserDefined4());
    }

    private void setContactInformation(Client client) {
        client.setPhoneType(Pages.clientDetailsPage().getPhoneType());
        client.setPhone(Pages.clientDetailsPage().getPhone());
        client.setEmailType(Pages.clientDetailsPage().getEmailType());
        client.setEmail(Pages.clientDetailsPage().getEmail());
    }

    private void setAddressInformation(Client client) {
        Address address = new Address();

        address.setType(Pages.clientDetailsPage().getAddressType());
        address.setCountry(Pages.clientDetailsPage().getAddressCountry());
        address.setAddress(Pages.clientDetailsPage().getAddress());
        address.setCity(Pages.clientDetailsPage().getAddressCity());
        address.setState(Pages.clientDetailsPage().getAddressState());
        address.setZipCode(Pages.clientDetailsPage().getAddressZipCode());

        client.setAddress(address);
    }

    private void setIdentityDocuments(Client client) {
        Pages.clientDetailsPage().waitForDocumentsTable();
        int countOfCountry = Pages.clientDetailsPage().amountOfDocuments();
        List<IdentityDocument> identityDocuments = new ArrayList<>();
        for (int i = 1; i <= countOfCountry; i++) {
            IdentityDocument identityDocument = new IdentityDocument();
            identityDocument.setType(Pages.clientDetailsPage().getDocumentTypeByIndex(i));
            identityDocument.setCountry(Pages.clientDetailsPage().getCountryByIndex(i));
            identityDocument.setIssuedBy(Pages.clientDetailsPage().getStateByIndex(i));
            identityDocument.setNumber(Pages.clientDetailsPage().getDocumentIDByIndex(i));
            identityDocument.setExpirationDate(Pages.clientDetailsPage().getExpirationDateByIndex(i));

            identityDocuments.add(identityDocument);
        }

        client.setIdentityDocument(identityDocuments);
    }
}