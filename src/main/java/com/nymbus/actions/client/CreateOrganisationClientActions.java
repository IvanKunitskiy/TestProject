package com.nymbus.actions.client;

import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.Functions;
import com.nymbus.newmodels.client.OrganisationClient;
import com.nymbus.newmodels.client.basicinformation.address.Address;
import com.nymbus.newmodels.client.clientdetails.contactinformation.email.Email;
import com.nymbus.newmodels.client.clientdetails.contactinformation.phone.Phone;
import com.nymbus.newmodels.client.clientdetails.type.organisation.OrganisationClientDetails;
import com.nymbus.newmodels.client.other.document.Document;
import com.nymbus.newmodels.client.other.document.DocumentDetails;
import com.nymbus.newmodels.client.other.document.IDType;
import com.nymbus.pages.Pages;

import java.util.List;
import java.util.Set;

public class CreateOrganisationClientActions {

    public void setBasicInformation(OrganisationClient client) {
        setClientType(client.getOrganisationType().getClientType().getClientType());
        setClientStatus(client.getOrganisationType().getClientStatus().getClientStatus());
        Pages.addClientPage().setLastNameValue(client.getOrganisationType().getName());
        setTaxPayerIDType(client.getOrganisationType().getTaxPayerIDType().getTaxPayerIDType());
        Pages.addClientPage().setTaxIDValue(client.getOrganisationType().getTaxID());
        setClientAddresses(client.getOrganisationType().getAddresses());
        Pages.addClientPage().clickSaveAndContinueButton();
    }

    public void setClientDetails(OrganisationClient client) {
        setOrganisationDetails(client.getOrganisationClientDetails());
        setPhones(client.getOrganisationClientDetails().getPhones());
        setEmails(client.getOrganisationClientDetails().getEmails());
        Pages.addClientPage().clickSaveAndContinueButton();
    }

    public void setDocumentation(OrganisationClient client) {
        int documentsCount = client.getDocuments().size();
        for (int i = 0; i < documentsCount; i++) {
            Document currentDocument = client.getDocuments().get(i);
            setDocumentFileAndType(currentDocument);
            if (isDocumentComplex(currentDocument)) {
                setDocumentDetails(currentDocument.getDocumentDetails());
            }
            Pages.addClientPage().clickDocumentSaveChangesButton();
        }
        Pages.addClientPage().clickSaveAndContinueButton();
    }

    public void setSignature(OrganisationClient client) {
        Pages.addClientPage().uploadClientSignature(client.getClientSignature());
        Pages.addClientPage().clickSaveAndContinueButton();
    }

    private boolean isDocumentComplex(Document document) {
        boolean complexCondition = document.getIdType() != IDType.LOGO
                                    && document.getIdType() != IDType.PHOTO
                                    && document.getIdType() != IDType.SIGNATURE;
        return complexCondition;
    }

    private void setDocumentFileAndType(Document document) {
        Pages.addClientPage().uploadClientDocumentation(Functions.getFilePathByName(document.getFile().getFileName()));
        setDocumentationIDType(document.getIdType().getIdType());
    }

    private void setDocumentDetails(DocumentDetails documentDetails) {
        Pages.addClientPage().setDocumentIDNumberValue(documentDetails.getIdNumber());
        setDocumentationIssuedBY(documentDetails.getIssuedBy().getState());
        setDocumentationCountry(documentDetails.getCountry().getCountry());
        Pages.addClientPage().setDocumentExpirationDateValue(documentDetails.getExpirationDate());
    }

    private void setDocumentationCountry(String country) {
        Pages.addClientPage().clickCountrySelectorButton();
        Pages.addClientPage().clickItemInDropDown(country);
    }

    private void setDocumentationIssuedBY(String issuedBy) {
        Pages.addClientPage().clickDocumentIssuedBySelectorButton();
        Pages.addClientPage().clickItemInDropDown(issuedBy);
    }

    private void setDocumentationIDType(String idType) {
        Pages.addClientPage().clickDocumentIDTypeSelectorButton();
        Pages.addClientPage().clickItemInDropDown(idType);
    }

    private void setOrganisationDetails(OrganisationClientDetails organisationClientDetails) {
        Pages.addClientPage().setIndustryField(organisationClientDetails.getIndustry());
        setMailCode(organisationClientDetails.getMailCode().getMailCode());
        setOfficer(Constants.FIRST_NAME + " " + Constants.LAST_NAME);
        setAKAs(organisationClientDetails.getAKAs());
        Pages.addClientPage().uploadProfilePhoto(Functions.getFilePathByName(organisationClientDetails.getProfilePhoto().getFileName()));
        setUserDefinedFields(organisationClientDetails.getUserDefinedFields());
    }

    private void setAKAs(List<String> akAs) {
        int akAsCount = akAs.size();
        for (int i = 0; i < akAsCount; i++) {
            int position = i+1;
            setAKA(akAs.get(i), position);
            if (akAsCount > 1 && (i < (akAsCount-1)))  {
                Pages.addClientPage().clickAddEmailButton();
            }
        }
    }

    private void setAKA(String aka, int i) {
        Pages.addClientPage().setAKAField(i, aka);
    }

    private void setUserDefinedFields(List<String> userDefinedFields) {

    }

    private void setOfficer(String officer) {
        Pages.addClientPage().clickSelectOfficerSelectorButton1();
        Pages.addClientPage().clickItemInDropDown(officer);
    }

    private void setMailCode(String mailCode) {
        Pages.addClientPage().clickMailCodeSelectorButton1();
        Pages.addClientPage().clickItemInDropDown(mailCode);
    }

    private void setEmails(Set<Email> emails) {
        int emailsCount = emails.size();
        for (int i = 0; i < emailsCount; i++) {
            setEmail((Email)emails.toArray()[i], i);
            if (emailsCount > 2 && (i < (emailsCount-2)))  {
                Pages.addClientPage().clickAddEmailButton();
            }
        }
    }

    private void setPhones(Set<Phone> phones) {
        int phonesCount = phones.size();
        for (int i = 0; i < phonesCount; i++) {
            setPhone((Phone)phones.toArray()[i], i);
            if (phonesCount > 2 && (i < (phonesCount-2)))  {
                Pages.addClientPage().clickAddPhoneNumberButton();
            }
        }
    }

    private void setEmail(Email email, int i) {
        int tempIterator = i + 1;
        setEmailType(email.getEmailType().getEmailType(), tempIterator);
        Pages.addClientPage().setEmailField(tempIterator, email.getEmail());
    }

    private void setEmailType(String emailType, int i) {
        Pages.addClientPage().clickEmailTypeSelectorButton1(i);
        Pages.addClientPage().clickItemInDropDown(emailType);
    }

    private void setPhone(Phone phone, int i) {
        int tempIterator = i + 1;
        setPhoneType(phone.getPhoneType().getPhoneType(), tempIterator);
        setPhoneCountry(phone.getCountry().getCountry(), tempIterator);
        Pages.addClientPage().setPhoneField(tempIterator, phone.getPhoneNumber());
    }

    private void setPhoneCountry(String country, int i) {
        Pages.addClientPage().clickPhoneCountrySelectorButton(i);
        Pages.addClientPage().clickItemInDropDown(country);
    }

    private void setPhoneType(String phoneType, int i) {
        Pages.addClientPage().clickPhoneTypeSelectorButton(i);
        Pages.addClientPage().clickItemInDropDown(phoneType);
    }

    private void setClientAddresses(Set<Address> addresses) {
        int addressesCount = addresses.size();
        for (int i = 0; i < addressesCount; i++) {
            setAddress((Address)addresses.toArray()[i], i);
            if (addressesCount > 1 && (i < (addressesCount-1)))  {
                Pages.addClientPage().clickAddAddress();
            }
        }
    }

    private void setAddress(Address address, int i) {
        int tempIterator = i + 1;
        setAddressType(address.getType().getAddressType(), tempIterator);
        setAddressCountry(address.getCountry().getCountry(), tempIterator);
        Pages.addClientPage().setAddressField1Value(tempIterator, address.getAddress());
        Pages.addClientPage().setAddressCityValue(tempIterator, address.getCity());
        setAddressStates(address.getState().getState(), tempIterator);
        Pages.addClientPage().setAddressZipCode1Value(tempIterator, address.getZipCode());
    }

    private void setAddressStates(String addressStates, int i) {
        Pages.addClientPage().clickAddressStateSelectorButton(i);
        Pages.addClientPage().clickItemInDropDown(addressStates);
    }

    private void setAddressType(String addressType, int i) {
        Pages.addClientPage().clickAddressTypeSelectorButton1(i);
        Pages.addClientPage().clickItemInDropDown(addressType);
    }

    private void setAddressCountry(String country, int i) {
        Pages.addClientPage().clickCountrySelectorButton1(i);
        Pages.addClientPage().clickItemInDropDown(country);
    }

    private void setTaxPayerIDType(String taxPayerType) {
        Pages.addClientPage().clickTaxPayerIDTypeSelectorButton();
        Pages.addClientPage().clickTaxPayerIDTypeOption(taxPayerType);
    }

    private void setClientStatus(String clientStatus) {
        Pages.addClientPage().clickClientStatusSelectorButton();
        Pages.addClientPage().clickItemInDropDown(clientStatus);
    }

    private void setClientType(String clientType) {
        Pages.addClientPage().clickClientTypeSelectorButton();
        Pages.addClientPage().clickItemInDropDown(clientType);
    }
}