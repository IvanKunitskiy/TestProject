package com.nymbus.actions.client.organisation;

import com.nymbus.core.utils.Functions;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.client.OrganisationClient;
import com.nymbus.newmodels.client.basicinformation.address.Address;
import com.nymbus.newmodels.client.clientdetails.contactinformation.documents.Document;
import com.nymbus.newmodels.client.clientdetails.contactinformation.email.Email;
import com.nymbus.newmodels.client.clientdetails.contactinformation.phone.Phone;
import com.nymbus.newmodels.client.clientdetails.type.ClientStatus;
import com.nymbus.newmodels.client.verifyingmodels.TrustAccountPredefinedField;
import com.nymbus.pages.Pages;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;


public class OrganisationClientActions {
    public void openClientPage() {
        if (Pages.aSideMenuPage().isClientPageOpened())
            return;

        Pages.aSideMenuPage().waitForASideMenu();
        Pages.aSideMenuPage().clickClientMenuItem();
        Pages.clientsSearchPage().waitForAddNewClientButton();
        Assert.assertTrue(Pages.aSideMenuPage().isClientPageOpened(),
                "Client page is not opened");
    }

    public void createClient(OrganisationClient client) {
        openClientPage();
        Pages.clientsSearchPage().clickAddNewClient();
        setBasicInformation(client);
        Assert.assertTrue(Pages.addClientPage().isVerificationSuccess(),
                "Client data verification is not success");
        Pages.addClientPage().clickOkModalButton();
    }

    public void setBasicInformation(OrganisationClient client) {
        setClientType(client);
        setClientStatus(client);
        Pages.addClientPage().setOrganizationName(client.getOrganisationType().getName());
        setTaxPayerIDType(client);
        Pages.addClientPage().setTaxIDValue(client.getOrganisationType().getTaxID());

        for (Address address : client.getOrganisationType().getAddresses()) {
            setAddress(address);
        }
        Pages.addClientPage().clickSaveAndContinueButton();
        Pages.addClientPage().waitForModalWindow();
    }

    private void setClientType(OrganisationClient client) {
        Pages.addClientPage().clickClientTypeSelectorButton();
        Pages.addClientPage().setClientTypeValue(client.getOrganisationType().getClientType().getClientType());
        Pages.addClientPage().clickClientTypeOption(client.getOrganisationType().getClientType().getClientType());
    }

    private void setClientStatus(OrganisationClient client) {
        Pages.addClientPage().clickClientStatusSelectorButton();
//        Pages.addClientPage().setClientStatusValue(client.getIndividualType().getClientStatus().getClientStatus());
        Pages.addClientPage().clickClientStatusOption(client.getOrganisationType().getClientStatus().getClientStatus());
    }

    private void setTaxPayerIDType(OrganisationClient client) {
        Pages.addClientPage().clickTaxPayerIDTypeSelectorButton();
        Pages.addClientPage().setTaxPayerIDTypeValue(client.getOrganisationType().getTaxPayerIDType().getTaxPayerIDType());
        Pages.addClientPage().clickTaxPayerIDTypeOption(client.getOrganisationType().getTaxPayerIDType().getTaxPayerIDType());
    }

    private void setDocumentData(Document document) {
        setIDType(document.getIdType().getIdType());
        Pages.addClientPage().setIDNumberValue(document.getIdNumber());
        setIssuedBY(document.getIssuedBy().getState());
        setCountry(document.getCountry().getCountry());
        Pages.addClientPage().clickExpirationDateCalendarIcon();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Pages.addClientPage().clickExpirationDateCalendarIcon();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Pages.addClientPage().setExpirationDateValue(document.getExpirationDate());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void setIDType(String idType) {
        Pages.addClientPage().clickIDTypeSelectorButton();
        Pages.addClientPage().setIDTypeValue(idType);
        Pages.addClientPage().clickIDTypeOption(idType);
    }

    private void setIssuedBY(String issuedBY) {
        Pages.addClientPage().clickIssuedBySelectorButton();
        Pages.addClientPage().setIssuedByValue(issuedBY);
        Pages.addClientPage().clickIssuedByOption(issuedBY);
    }

    private void setCountry(String country) {
        Pages.addClientPage().clickCountrySelectorButton();
        Pages.addClientPage().setCountryValue(country);
        Pages.addClientPage().clickCountryOption(country);
    }

    private void setAddress(Address address) {
        setAddressType(address);
        setAddressCountry(address);
        Pages.addClientPage().setAddressValue(address.getAddress());
        Pages.addClientPage().setAddressCityValue(address.getCity());
        setAddressStates(address);
        Pages.addClientPage().setAddressZipCodeValue(address.getZipCode());
    }

    private void setAddressType(Address address) {
        Pages.addClientPage().clickAddressTypeSelectorButton();
        Pages.addClientPage().setAddressTypeValue(address.getType().getAddressType());
        Pages.addClientPage().clickAddressTypeOption(address.getType().getAddressType());
    }

    private void setAddressCountry(Address address) {
        Pages.addClientPage().clickAddressCountrySelectorButton();
        Pages.addClientPage().setAddressCountryValue(address.getCountry().getCountry());
        Pages.addClientPage().clickAddressCountryOption(address.getCountry().getCountry());
    }

    private void setAddressStates(Address address) {
        Pages.addClientPage().clickAddressStateSelectorButton();
        Pages.addClientPage().setAddressStateValue(address.getState().getState());
        Pages.addClientPage().clickAddressStateOption(address.getState().getState());
    }

    /**
     * Set client details data
     */

    public void setClientDetailsData(OrganisationClient client) {
        setIndustry(client.getOrganisationClientDetails().getIndustry());
        setMailCode(client.getOrganisationClientDetails().getMailCode().getMailCode());
        setSelectOfficer(client.getOrganisationClientDetails().getSelectOfficer());

        for (String aka : client.getOrganisationClientDetails().getAKAs()) {
            Pages.addClientPage().setAkaField(aka);
        }

        Pages.addClientPage().uploadProfilePhoto(Functions.getFilePathByName(client.getOrganisationClientDetails().getProfilePhoto().getFileName()));

//        Pages.addClientPage().setUserDefinedField1Value(client.getUserDefined_1());
//        Pages.addClientPage().setUserDefinedField2Value(client.getUserDefined_2());
//        Pages.addClientPage().setUserDefinedField3Value(client.getUserDefined_3());
//        Pages.addClientPage().setUserDefinedField4Value(client.getUserDefined_4());
        for (Phone phone : client.getOrganisationClientDetails().getPhones()) {
            Pages.addClientPage().setPhoneValue(phone.getPhoneNumber());
        }

        for (Email email : client.getOrganisationClientDetails().getEmails()) {
            setEmailType(email.getEmailType().getEmailType());
            Pages.addClientPage().setEmailValue(email.getEmail());
        }

        Pages.addClientPage().clickSaveAndContinueButton();
    }

    private void setIndustry(String industry) {
        Pages.addClientPage().clickIndustrySearchIcon();
        Pages.addClientPage().setIndustryField(industry);
        Pages.addClientPage().clickIndustryOption(industry);
    }

    private void setMailCode(String mailCode) {
        Pages.addClientPage().clickMailCodeSelectorButton();
        Pages.addClientPage().setMailCodeValue(mailCode);
        Pages.addClientPage().clickMailCodeOption(mailCode);
    }

    private void setSelectOfficer(String selectOfficer) {
        Pages.addClientPage().clickSelectOfficerSelectorButton();
        Pages.addClientPage().setSelectOfficerValue(selectOfficer);
        Pages.addClientPage().clickSelectOfficerOption(selectOfficer);
    }

    private void setEmailType(String emailType) {
        Pages.addClientPage().clickEmailTypeSelectorButton();
        Pages.addClientPage().setEmailTypeValue(emailType);
        Pages.addClientPage().clickEmailTypeOption(emailType);
    }

    /**
     * Set client documentation
     */

    public void setDocumentation(OrganisationClient client) {
        Pages.addClientPage().uploadClientDocumentation(Functions.getFilePathByName("clientDocument.png"));
        setDocumentationIDType(client.getOrganisationClientDetails().getDocuments().get(0).getIdType().getIdType());
        Pages.addClientPage().setDocumentIDNumberValue(client.getOrganisationClientDetails().getDocuments().get(0).getIdNumber());
        setDocumentationIssuedBY(client.getOrganisationClientDetails().getDocuments().get(0).getIssuedBy().getState());
        setDocumentationCountry(client.getOrganisationClientDetails().getDocuments().get(0).getCountry().getCountry());
        Pages.addClientPage().setDocumentExpirationDateValue(client.getOrganisationClientDetails().getDocuments().get(0).getExpirationDate());
        Pages.addClientPage().clickDocumentSaveChangesButton();
        Pages.addClientPage().clickSaveAndContinueButton();
        Pages.addClientPage().uploadClientSignature(Functions.getFilePathByName("clientSignature.png"));
        Pages.addClientPage().clickSaveAndContinueButton();
        Pages.addClientPage().clickViewMemberProfileButton();
    }

    private void setDocumentationIDType(String documentationIDType) {
        Pages.addClientPage().clickDocumentIDTypeSelectorButton();
        Pages.addClientPage().setDocumentIDTypeValue(documentationIDType);
        Pages.addClientPage().clickDocumentIDTypeOption(documentationIDType);
    }

    private void setDocumentationIssuedBY(String documentationIssuedBY) {
        Pages.addClientPage().clickDocumentIssuedBySelectorButton();
        Pages.addClientPage().setDocumentIssuedByValue(documentationIssuedBY);
        Pages.addClientPage().clickDocumentIssuedByOption(documentationIssuedBY);
    }

    private void setDocumentationCountry(String documentationCountry) {
        Pages.addClientPage().clickDocumentCountrySelectorButton();
        Pages.addClientPage().setDocumentCountryValue(documentationCountry);
        Pages.addClientPage().clickDocumentCountryOption(documentationCountry);
    }

    public void verifyClientData(OrganisationClient organisationClient) {
        SoftAssert asert = new SoftAssert();

        /**
         * Individual Information
         */

        asert.assertEquals(Pages.clientDetailsPage().getType(),
                organisationClient.getOrganisationType().getClientType().getClientType(), "Organization type isn't as expected.");
        asert.assertEquals(Pages.clientDetailsPage().getStatus(),
                organisationClient.getOrganisationType().getClientStatus().getClientStatus(), "Organization status isn't as expected.");
        asert.assertEquals(Pages.clientDetailsPage().getOrganizationName(),
                organisationClient.getOrganisationType().getName(), "Organization 'Name' isn't as expected.");
        asert.assertEquals(Pages.clientDetailsPage().getTaxPairIdType(),
                organisationClient.getOrganisationType().getTaxPayerIDType().getTaxPayerIDType(), "Organization 'Tax payer id type' isn't as expected.");
        asert.assertEquals(Pages.clientDetailsPage().getTaxID(),
                organisationClient.getOrganisationType().getTaxID(), "Organization 'Tax ID' isn't as expected.");
        asert.assertTrue(Pages.clientDetailsPage().getIndustry().contains(organisationClient.getOrganisationClientDetails().getIndustry()),
                 "Organization 'Industry' isn't as expected.");
        asert.assertEquals(Pages.clientDetailsPage().getMailCode(),
                organisationClient.getOrganisationClientDetails().getMailCode().getMailCode(), "Organization 'Mail Code' isn't as expected.");
        asert.assertEquals(Pages.clientDetailsPage().getSelectOfficer(),
                organisationClient.getOrganisationClientDetails().getSelectOfficer(), "Organization 'Select Officer' isn't as expected.");
        asert.assertEquals(Pages.clientDetailsPage().getAka_1(),
                organisationClient.getOrganisationClientDetails().getAKAs().get(0), "Organization 'AKA #1' isn't as expected.");


        /**
         * Contact Information
         */

        //TODO add contact information verification

        /**
         * Address Information
         */

        asert.assertEquals(Pages.clientDetailsPage().getAddressType(),
                organisationClient.getOrganisationType().getAddresses().get(0).getType().getAddressType(), "Organization 'Address Type' isn't as expected.");
        asert.assertEquals(Pages.clientDetailsPage().getAddressCountry(),
                organisationClient.getOrganisationType().getAddresses().get(0).getCountry().getCountry(), "Organization 'Address Country' isn't as expected.");
        asert.assertEquals(Pages.clientDetailsPage().getAddress(),
                organisationClient.getOrganisationType().getAddresses().get(0).getAddress(), "Organization 'Address' isn't as expected.");
        asert.assertEquals(Pages.clientDetailsPage().getAddressCity(),
                organisationClient.getOrganisationType().getAddresses().get(0).getCity(), "Organization 'Address City' isn't as expected.");
        asert.assertEquals(Pages.clientDetailsPage().getAddressState(),
                organisationClient.getOrganisationType().getAddresses().get(0).getState().getState(), "Organization 'Address State' isn't as expected.");
        asert.assertEquals(Pages.clientDetailsPage().getAddressZipCode(),
                organisationClient.getOrganisationType().getAddresses().get(0).getZipCode(), "Organization 'Address Zip Code' isn't as expected.");

        /**
         * Documents
         */

        Pages.clientDetailsPage().clickDocumentsTab();
        Pages.clientDetailsPage().waitForDocumentsTable();


        asert.assertEquals(Pages.clientDetailsPage().getDocumentIDByIndex(1),
                organisationClient.getOrganisationClientDetails().getDocuments().get(0).getIdNumber(), "Organization 'Document Type' isn't as expected.");
        asert.assertEquals(Pages.clientDetailsPage().getDocumentTypeByIndex(1),
                organisationClient.getOrganisationClientDetails().getDocuments().get(0).getIdType().getIdType(), "Organization 'Document ID' isn't as expected.");


        asert.assertAll();
    }

    public void createClient(OrganisationClient client, TrustAccountPredefinedField predefinedField) {
        openClientPage();
        Pages.clientsSearchPage().clickAddNewClient();
        setBasicInformation(client, predefinedField);
        Assert.assertTrue(Pages.addClientPage().isVerificationSuccess(),
                "Client data verification is not success");
        Pages.addClientPage().clickOkModalButton();
    }

    public void setBasicInformation(OrganisationClient client, TrustAccountPredefinedField predefinedField) {
        setClientType(client);
        setClientStatus(client);
        verifyTrustAccountPredefinedFields(predefinedField);
        Pages.addClientPage().setOrganizationName(client.getOrganisationType().getName());
        setTaxPayerIDType(client);
        Pages.addClientPage().setTaxIDValue(client.getOrganisationType().getTaxID());

        for (Address address : client.getOrganisationType().getAddresses()) {
            setAddress(address);
        }
        Pages.addClientPage().clickSaveAndContinueButton();
        Pages.addClientPage().waitForModalWindow();
    }

    public void verifyTrustAccountPredefinedFields(TrustAccountPredefinedField predefinedField) {
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(Pages.addClientPage().getTaxPayerIdTypeSelectedSpan(1),
                predefinedField.getTaxPayerIDType().getTaxPayerIDType(),
                "Tax payer id type is not correct!");
        softAssert.assertEquals(Pages.addClientPage().getAddressTypeSelectedSpan(1),
                predefinedField.getAddressType().getAddressType(),
                "Address type is not correct!");
        softAssert.assertEquals(Pages.addClientPage().getAddressCountrySelectedSpan(1),
                predefinedField.getCountry().getCountry(),
                "Address country is not correct!");
        softAssert.assertAll();
    }
}