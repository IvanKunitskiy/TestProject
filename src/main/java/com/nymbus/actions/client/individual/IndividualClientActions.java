package com.nymbus.actions.client.individual;

import com.nymbus.core.utils.Functions;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.client.basicinformation.address.Address;
import com.nymbus.newmodels.client.clientdetails.contactinformation.documents.Document;
import com.nymbus.newmodels.client.clientdetails.contactinformation.email.Email;
import com.nymbus.newmodels.client.clientdetails.contactinformation.phone.Phone;
import com.nymbus.newmodels.client.clientdetails.contactinformation.phone.PhoneType;
import com.nymbus.newmodels.client.clientdetails.type.ClientStatus;
import com.nymbus.pages.Pages;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;


public class IndividualClientActions {
    public void openClientPage() {
        if (Pages.aSideMenuPage().isClientPageOpened())
            return;

        Pages.aSideMenuPage().waitForASideMenu();
        Pages.aSideMenuPage().clickClientMenuItem();
        Pages.clientsSearchPage().waitForAddNewClientButton();
        Assert.assertTrue(Pages.aSideMenuPage().isClientPageOpened(),
                "Client page is not opened");
    }

    public void createClientOnVerifyConductorModalPage(IndividualClient client) {
        Pages.verifyConductorModalPage().clickAddNewClientButton();
        Pages.verifyConductorModalPage().waitForFirstNameFieldEnabled();
        Pages.addClientPage().setFirstNameValue(client.getIndividualType().getFirstName());
        Pages.addClientPage().setLastNameValue(client.getIndividualType().getLastName());
        Pages.addClientPage().setTaxIDValue(client.getIndividualType().getTaxID());
        setBirthDateOnVerifyConductorModal(client.getIndividualType().getBirthDate());
        Pages.verifyConductorModalPage().typeAddressField(client.getIndividualType().getAddresses().get(0).getAddress());
        Pages.verifyConductorModalPage().typeCityField(client.getIndividualType().getAddresses().get(0).getCity());
        setAddressStatesOnVerifyConductorModal(client.getIndividualType().getAddresses().get(0).getState().getState());
        Pages.verifyConductorModalPage().typeZipCodeField(client.getIndividualType().getAddresses().get(0).getZipCode());
        Pages.verifyConductorModalPage().typePhoneField(client.getIndividualClientDetails().getPhones().get(0).getPhoneNumber());
        setIdTypeOnVerifyConductorModal(client.getIndividualClientDetails().getDocuments().get(0).getIdType().getIdType());
        Pages.verifyConductorModalPage().typeIdNumberField(client.getIndividualClientDetails().getDocuments().get(0).getIdNumber());
        setCountryOnVerifyConductorModal(client.getIndividualClientDetails().getDocuments().get(0).getCountry().getCountry());
        setExpirationDateOnVerifyConductorModal(client.getIndividualClientDetails().getDocuments().get(0).getExpirationDate());
    }

    private void setExpirationDateOnVerifyConductorModal(String date) {
        Pages.verifyConductorModalPage().clickCalendarIconWithJs();
        Pages.verifyConductorModalPage().clickCalendarIconWithJs();
        Pages.verifyConductorModalPage().setExpirationDateValue(date);
    }

    private void setBirthDateOnVerifyConductorModal(String date) {
        Pages.verifyConductorModalPage().clickBirthDateCalendarIconWithJs();
        Pages.verifyConductorModalPage().clickBirthDateCalendarIconWithJs();
        Pages.addClientPage().setBirthDateValue(date);
    }

    private void setAddressStatesOnVerifyConductorModal(String state) {
        Pages.verifyConductorModalPage().clickStateDropdownButton();
        Pages.verifyConductorModalPage().clickItemInDropdown(state);
    }

    private void setIdTypeOnVerifyConductorModal(String idType) {
        Pages.verifyConductorModalPage().clickIdTypeDropdownButton();
        Pages.verifyConductorModalPage().clickItemInDropdown(idType);
    }

    private void setCountryOnVerifyConductorModal(String country) {
        Pages.verifyConductorModalPage().clickCountryDropdownButton();
        Pages.verifyConductorModalPage().clickItemInDropdown(country);
    }

    public void waitForOFACModalWindowVerification() {
        Assert.assertTrue(Pages.addClientPage().isVerificationSuccess(),
                "Client data verification is not success");
        Pages.addClientPage().clickOkModalButton();
    }

    public void createClient(IndividualClient client) {
        openClientPage();
        Pages.clientsSearchPage().clickAddNewClient();
        setBasicInformation(client);
        Assert.assertTrue(Pages.addClientPage().isVerificationSuccess(),
                "Client data verification is not success");
        Pages.addClientPage().clickOkModalButton();
    }

    public void setBasicInformation(IndividualClient client) {
        setClientType(client);
        setClientStatus(client);
        Pages.addClientPage().setFirstNameValue(client.getIndividualType().getFirstName());
        Pages.addClientPage().setMiddleNameValue(client.getIndividualType().getMiddleName());
        Pages.addClientPage().setLastNameValue(client.getIndividualType().getLastName());
        setTaxPayerIDType(client);
        Pages.addClientPage().setTaxIDValue(client.getIndividualType().getTaxID());
        Pages.addClientPage().clickBirthDateCalendarIcon();
        Pages.addClientPage().clickBirthDateCalendarIcon();
        Pages.addClientPage().setBirthDateValue(client.getIndividualType().getBirthDate());
        Pages.addClientPage().clickBirthDateCalendarIcon();

        setDocumentData(client.getIndividualClientDetails().getDocuments().get(0));
        for (Address address : client.getIndividualType().getAddresses()) {
            setAddress(address);
        }
        Pages.addClientPage().clickSaveAndContinueButton();
        Pages.addClientPage().waitForModalWindow();
    }

    private void setClientType(IndividualClient client) {
        Pages.addClientPage().clickClientTypeSelectorButton();
        Pages.addClientPage().setClientTypeValue(client.getIndividualType().getClientType().getClientType());
        Pages.addClientPage().clickClientTypeOption(client.getIndividualType().getClientType().getClientType());
    }

    private void setClientStatus(IndividualClient client) {
        Pages.addClientPage().clickClientStatusSelectorButton();
//        Pages.addClientPage().setClientStatusValue(client.getIndividualType().getClientStatus().getClientStatus());
        Pages.addClientPage().clickClientStatusOption(client.getIndividualType().getClientStatus().getClientStatus());
    }

    private void setTaxPayerIDType(IndividualClient client) {
        Pages.addClientPage().clickTaxPayerIDTypeSelectorButton();
        Pages.addClientPage().setTaxPayerIDTypeValue(client.getIndividualType().getTaxPayerIDType().getTaxPayerIDType());
        Pages.addClientPage().clickTaxPayerIDTypeOption(client.getIndividualType().getTaxPayerIDType().getTaxPayerIDType());
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

    public void setClientDetailsData(IndividualClient client) {
        Pages.addClientPage().setSuffixField(client.getIndividualClientDetails().getSuffix());
        Pages.addClientPage().setMaidenFamilyNameField(client.getIndividualClientDetails().getMaidenFamilyName());

        for (String aka : client.getIndividualClientDetails().getAKAs()) {
            Pages.addClientPage().setAkaField(aka);
        }

        Pages.addClientPage().uploadProfilePhoto(Functions.getFilePathByName("profilePhoto.png"));
        setGender(client.getIndividualClientDetails().getGender().getGender());
        setEducation(client.getIndividualClientDetails().getEducation().getEducation());
        setIncome(client.getIndividualClientDetails().getIncome().getIncome());
        setMaritalStatus(client.getIndividualClientDetails().getMaritalStatus().getMaritalStatus());
        Pages.addClientPage().setOccupationValue(client.getIndividualClientDetails().getOccupation());
        setConsumerInformationIndicator(client.getIndividualClientDetails().getConsumerInformationIndicator().getIndicator());
        Pages.addClientPage().setJobTitleValue(client.getIndividualClientDetails().getJobTitle());
        setOwnOrRent(client.getIndividualClientDetails().getOwnOrRent().getOwnOrRent());
        setMailCode(client.getIndividualClientDetails().getMailCode().getMailCode());
        setSelectOfficer(client.getIndividualClientDetails().getSelectOfficer());
//        Pages.addClientPage().setUserDefinedField1Value(client.getUserDefined_1());
//        Pages.addClientPage().setUserDefinedField2Value(client.getUserDefined_2());
//        Pages.addClientPage().setUserDefinedField3Value(client.getUserDefined_3());
//        Pages.addClientPage().setUserDefinedField4Value(client.getUserDefined_4());
        for (Phone phone : client.getIndividualClientDetails().getPhones()) {
            Pages.addClientPage().setPhoneValue(phone.getPhoneNumber());
        }

        for (Email email : client.getIndividualClientDetails().getEmails()) {
            setEmailType(email.getEmailType().getEmailType());
            Pages.addClientPage().setEmailValue(email.getEmail());
        }

        Pages.addClientPage().clickSaveAndContinueButton();
    }

    private void setGender(String gender) {
        Pages.addClientPage().clickGenderSelectorButton();
        Pages.addClientPage().setGenderValue(gender);
        Pages.addClientPage().clickGenderOption(gender);
    }

    private void setEducation(String education) {
        Pages.addClientPage().clickEducationSelectorButton();
        Pages.addClientPage().setEducationValue(education);
        Pages.addClientPage().clickEducationOption(education);
    }

    private void setIncome(String income) {
        Pages.addClientPage().clickIncomeSelectorButton();
        Pages.addClientPage().setIncomeValue(income);
        Pages.addClientPage().clickIncomeOption(income);
    }

    private void setMaritalStatus(String maritalStatus) {
        Pages.addClientPage().clickMaritalStatusSelectorButton();
        Pages.addClientPage().setMaritalStatusValue(maritalStatus);
        Pages.addClientPage().clickMaritalStatusOption(maritalStatus);
    }

    private void setConsumerInformationIndicator(String consumerInformationIndicator) {
        Pages.addClientPage().clickConsumerInformationIndicatorSelectorButton();
        Pages.addClientPage().setConsumerInformationIndicatorValue(consumerInformationIndicator);
        Pages.addClientPage().clickConsumerInformationIndicatorOption(consumerInformationIndicator);
    }

    private void setOwnOrRent(String ownOrRent) {
        Pages.addClientPage().clickOwnOrRentSelectorButton();
        Pages.addClientPage().setOwnOrRentValue(ownOrRent);
        Pages.addClientPage().clickOwnOrRentOption(ownOrRent);
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

    public void setDocumentation(IndividualClient client) {
        Pages.addClientPage().uploadClientDocumentation(Functions.getFilePathByName("clientDocument.png"));
        setDocumentationIDType(client.getIndividualClientDetails().getDocuments().get(1).getIdType().getIdType());
        Pages.addClientPage().setDocumentIDNumberValue(client.getIndividualClientDetails().getDocuments().get(1).getIdNumber());
        setDocumentationIssuedBY(client.getIndividualClientDetails().getDocuments().get(1).getIssuedBy().getState());
        setDocumentationCountry(client.getIndividualClientDetails().getDocuments().get(1).getCountry().getCountry());
        Pages.addClientPage().setDocumentExpirationDateValue(client.getIndividualClientDetails().getDocuments().get(1).getExpirationDate());
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

    public void verifyClientData(IndividualClient individualClient) {
        SoftAssert asert = new SoftAssert();

        /**
         * Individual Information
         */

        asert.assertEquals(Pages.clientDetailsPage().getType(),
                individualClient.getIndividualType().getClientType().getClientType(), "Client type isn't as expected.");
        asert.assertEquals(Pages.clientDetailsPage().getStatus(),
                individualClient.getIndividualType().getClientStatus().getClientStatus(), "Client status isn't as expected.");
        asert.assertEquals(Pages.clientDetailsPage().getFirstName(),
                individualClient.getIndividualType().getFirstName(), "Client 'First Name' isn't as expected.");
        asert.assertEquals(Pages.clientDetailsPage().getMiddleName(),
                individualClient.getIndividualType().getMiddleName(), "Client 'Middle Name' isn't as expected.");
        asert.assertEquals(Pages.clientDetailsPage().getLastName(),
                individualClient.getIndividualType().getLastName(), "Client 'Last Name' isn't as expected.");

        if (!ClientStatus.CONSUMER.getClientStatus().equals(individualClient.getIndividualType().getClientStatus().getClientStatus())) {
            asert.assertEquals(Pages.clientDetailsPage().getSuffix(),
                    individualClient.getIndividualClientDetails().getSuffix(), "Client 'Suffix' isn't as expected.");
            asert.assertEquals(Pages.clientDetailsPage().getMaidenFamilyName(),
                    individualClient.getIndividualClientDetails().getMaidenFamilyName(), "Client 'Maiden/Family Name' isn't as expected.");
            asert.assertEquals(Pages.clientDetailsPage().getAka_1(),
                    individualClient.getIndividualClientDetails().getAKAs().get(0), "Client 'AKA #1' isn't as expected.");
            asert.assertEquals(Pages.clientDetailsPage().getBirthDate(),
                    individualClient.getIndividualType().getBirthDate(), "Client 'Birth Date' isn't as expected.");
            asert.assertEquals(Pages.clientDetailsPage().getGender(),
                    individualClient.getIndividualClientDetails().getGender().getGender(), "Client 'Gender' isn't as expected.");
            asert.assertEquals(Pages.clientDetailsPage().getEducation(),
                    individualClient.getIndividualClientDetails().getEducation().getEducation(), "Client 'Education' isn't as expected.");
            asert.assertEquals(Pages.clientDetailsPage().getIncome(),
                    individualClient.getIndividualClientDetails().getIncome().getIncome(), "Client 'Income' isn't as expected.");
            asert.assertEquals(Pages.clientDetailsPage().getMaritalStatus(),
                    individualClient.getIndividualClientDetails().getMaritalStatus().getMaritalStatus(), "Client 'Marital Status' isn't as expected.");
            asert.assertEquals(Pages.clientDetailsPage().getOccupation(),
                    individualClient.getIndividualClientDetails().getOccupation(), "Client 'Occupation' isn't as expected.");
            asert.assertEquals(Pages.clientDetailsPage().getConsumerInformationIndicator(),
                    individualClient.getIndividualClientDetails().getConsumerInformationIndicator().getIndicator(), "Client 'Consumer Information Indicator' isn't as expected.");
            asert.assertEquals(Pages.clientDetailsPage().getJobTitle(),
                    individualClient.getIndividualClientDetails().getJobTitle(), "Client 'Job Title' isn't as expected.");
            asert.assertEquals(Pages.clientDetailsPage().getOwnOrRent(),
                    individualClient.getIndividualClientDetails().getOwnOrRent().getOwnOrRent(), "Client 'Own or Rent' isn't as expected.");
            asert.assertEquals(Pages.clientDetailsPage().getMailCode(),
                    individualClient.getIndividualClientDetails().getMailCode().getMailCode(), "Client 'Mail Code' isn't as expected.");
            asert.assertEquals(Pages.clientDetailsPage().getSelectOfficer(),
                    individualClient.getIndividualClientDetails().getSelectOfficer(), "Client 'Select Officer' isn't as expected.");
        }

        asert.assertEquals(Pages.clientDetailsPage().getTaxPairIdType(),
                individualClient.getIndividualType().getTaxPayerIDType().getTaxPayerIDType(), "Client 'Tax payer id type' isn't as expected.");
        asert.assertEquals(Pages.clientDetailsPage().getTaxID(),
                individualClient.getIndividualType().getTaxID(), "Client 'Tax ID' isn't as expected.");
        asert.assertEquals(Pages.clientDetailsPage().getBirthDate(),
                individualClient.getIndividualType().getBirthDate(), "Client 'Birth Date' isn't as expected.");

        /**
         * Contact Information
         */

        if (!Pages.clientDetailsPage().getPhone().equals("")) {
            asert.assertEquals(Pages.clientDetailsPage().getPhoneType(),
                    PhoneType.HOME.getPhoneType(), "Client 'Phone Use' isn't as expected.");
            asert.assertEquals(Pages.clientDetailsPage().getPhoneCountryByIndex(1),
                    individualClient.getIndividualClientDetails().getPhones().get(0).getCountry().getCountry(), "Client 'Phone Country' isn't as expected.");
            asert.assertEquals(Pages.clientDetailsPage().getPhoneNumberByIndex(1),
                    individualClient.getIndividualClientDetails().getPhones().get(1).getPhoneNumber(), "Client 'Phone Number' isn't as expected.");
        }
        if (Pages.clientDetailsPage().isEmailVisible()) {
            asert.assertEquals(Pages.clientDetailsPage().getEmailType(),
                    individualClient.getIndividualClientDetails().getEmails().get(0).getEmailType().getEmailType(), "Client 'Email Address Use' isn't as expected.");
            asert.assertEquals(Pages.clientDetailsPage().getEmail(),
                    individualClient.getIndividualClientDetails().getEmails().get(1).getEmail(), "Client 'Email' isn't as expected.");
        }

        /**
         * Address Information
         */

        asert.assertEquals(Pages.clientDetailsPage().getAddressType(),
                individualClient.getIndividualType().getAddresses().get(0).getType().getAddressType(), "Client 'Address Type' isn't as expected.");
        asert.assertEquals(Pages.clientDetailsPage().getAddressCountry(),
                individualClient.getIndividualType().getAddresses().get(0).getCountry().getCountry(), "Client 'Address Country' isn't as expected.");
        asert.assertEquals(Pages.clientDetailsPage().getAddress(),
                individualClient.getIndividualType().getAddresses().get(0).getAddress(), "Client 'Address' isn't as expected.");
        asert.assertEquals(Pages.clientDetailsPage().getAddressCity(),
                individualClient.getIndividualType().getAddresses().get(0).getCity(), "Client 'Address City' isn't as expected.");
        asert.assertEquals(Pages.clientDetailsPage().getAddressState(),
                individualClient.getIndividualType().getAddresses().get(0).getState().getState(), "Client 'Address State' isn't as expected.");
        asert.assertEquals(Pages.clientDetailsPage().getAddressZipCode(),
                individualClient.getIndividualType().getAddresses().get(0).getZipCode(), "Client 'Address Zip Code' isn't as expected.");

        /**
         * Documents
         */

        Pages.clientDetailsPage().clickDocumentsTab();
        Pages.clientDetailsPage().waitForDocumentsTable();

        if (ClientStatus.CONSUMER.getClientStatus().equals(individualClient.getIndividualType().getClientStatus().getClientStatus())) {
            asert.assertEquals(Pages.clientDetailsPage().getDocumentIDByIndex(1),
                    individualClient.getIndividualClientDetails().getDocuments().get(0).getIdNumber(), "Client 'Document Type' isn't as expected.");
            asert.assertEquals(Pages.clientDetailsPage().getDocumentTypeByIndex(1),
                    individualClient.getIndividualClientDetails().getDocuments().get(0).getIdType().getIdType(), "Client 'Document ID' isn't as expected.");
        } else {
            for (int i = 0; i < individualClient.getIndividualClientDetails().getDocuments().size(); i++) {
                asert.assertEquals(Pages.clientDetailsPage().getDocumentIDByIndex(i + 1),
                        individualClient.getIndividualClientDetails().getDocuments().get(i).getIdNumber(), "Client 'Document Type' isn't as expected.");
                asert.assertEquals(Pages.clientDetailsPage().getDocumentTypeByIndex(i + 1),
                        individualClient.getIndividualClientDetails().getDocuments().get(i).getIdType().getIdType(), "Client 'Document ID' isn't as expected.");
            }
        }

        asert.assertAll();
    }

}
