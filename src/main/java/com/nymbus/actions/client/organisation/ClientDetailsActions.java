package com.nymbus.actions.client.organisation;

import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.client.basicinformation.address.Address;
import com.nymbus.newmodels.client.clientdetails.contactinformation.email.Email;
import com.nymbus.newmodels.client.clientdetails.contactinformation.phone.Phone;
import com.nymbus.newmodels.client.verifyingmodels.TrustAccountUpdateModel;
import com.nymbus.pages.Pages;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class ClientDetailsActions {

    public void clickEditProfile() {
        if (!Pages.clientDetailsPage().isEditProfileButtonVisible()) {
            SelenideTools.refresh();
        }
        Pages.clientDetailsPage().clickEditProfileButton();
        Pages.clientDetailsPage().waitForProfileEditable();
    }

    public void deleteAllPhoneRows() {
        int rowsCount = Pages.clientDetailsPage().getPhonesRows();
        for (int i = rowsCount; i > 0; i--) {
            Pages.clientDetailsPage().deletePhoneRow(i);
            Pages.clientDetailsPage().waitForPhoneRowInvisibility(i);
        }
    }

    public void deleteAllEmailRows() {
        int rowsCount = Pages.clientDetailsPage().getEmailsCount();
        for (int i = rowsCount; i > 0; i--) {
            Pages.clientDetailsPage().deleteEmailRow(i);
            Pages.clientDetailsPage().waitForEmailRowInvisibility(i);
        }
    }

    public void setPhones(List<Phone> phones) {
        int offset =  Pages.clientDetailsPage().getPhonesRows();
        for (int i = 0; i < phones.size(); i++) {
            if (phones.size() > offset && (i < (phones.size() - offset)))  {
                Pages.clientDetailsPage().clickAddPhoneButton();
            }
            setPhone(phones.get(i), i+1+offset);
        }
    }

    private void setPhone(Phone phone, int i) {
        setPhoneType(phone.getPhoneType().getPhoneType(), i);
        setPhoneCountry(phone.getCountry().getCountry(), i);
        Pages.addClientPage().setPhoneField(i, phone.getPhoneNumber());
    }

    private void setPhoneCountry(String country, int i) {
        Pages.addClientPage().clickPhoneCountrySelectorButton(i);
        Pages.addClientPage().clickItemInDropDown(country);
    }

    private void setPhoneType(String phoneType, int i) {
        Pages.addClientPage().clickPhoneTypeSelectorButton(i);
        Pages.addClientPage().clickItemInDropDown(phoneType);
    }

    private void setEmails(List<Email> emails) {
        int offset =  Pages.clientDetailsPage().getEmailsCount();
        for (int i = 0; i < emails.size(); i++) {
            if (emails.size() > offset && (i < (emails.size() - offset)))  {
                Pages.clientDetailsPage().clickAddEmailButton();
            }
            setEmail(emails.get(i), i+1+offset);
        }
    }

    private void setEmail(Email email, int i) {
        setEmailType(email.getEmailType().getEmailType(), i);
        Pages.addClientPage().setEmailField(i, email.getEmail());
    }

    private void setEmailType(String emailType, int i) {
        Pages.addClientPage().clickEmailTypeSelectorButton1(i);
        Pages.addClientPage().clickItemInDropDown(emailType);
    }

    public void addAddress(Address address) {
        int offset = Pages.clientDetailsPage().getAddressRowsCount() + 1;
        Pages.clientDetailsPage().clickAddAddressButton();
        setAddressType(address.getType().getAddressType(), offset);
        setAddressCountry(address.getCountry().getCountry(), offset);
        Pages.addClientPage().setAddressField1Value(offset, address.getAddress());
        Pages.addClientPage().setAddressCityValue(offset, address.getCity());
        setAddressStates(address.getState().getState(), offset);
        Pages.addClientPage().setAddressZipCode1Value(offset, address.getZipCode());
    }

    public void addSeasonalAddress(Address seasonalAddress, IndividualClient client) {
        int offset = Pages.clientDetailsPage().getAddressRowsCount() + 1;
        Pages.clientDetailsPage().clickAddAddressButton();
        setAddressType(seasonalAddress.getType().getAddressType(), offset);
        setAddressCountry(seasonalAddress.getCountry().getCountry(), offset);
        Pages.addClientPage().setAddressField1Value(offset, seasonalAddress.getAddress());
        Pages.addClientPage().setAddressCityValue(offset, seasonalAddress.getCity());
        setAddressStates(seasonalAddress.getState().getState(), offset);
        Pages.addClientPage().setAddressZipCode1Value(offset, seasonalAddress.getZipCode());
        Pages.addClientPage().setSeasonalStartDate(seasonalAddress.getStartDate());
        Pages.addClientPage().setSeasonalEndDate(seasonalAddress.getEndDate());
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

    public void verifyNotBlankFieldsForCorporationClient() {
        SoftAssert softAssert = new SoftAssert();
        String empty = "";
        softAssert.assertNotEquals(Pages.clientDetailsPage().getStatus(),
                empty,
                "Client status is empty!");
        softAssert.assertNotEquals(Pages.clientDetailsPage().getOrganizationName(),
                empty,
                "Client name is empty!");
        softAssert.assertNotEquals(Pages.clientDetailsPage().getTaxPairIdType(),
                empty,
                "Client Tax Payer ID Type is empty!");
        softAssert.assertNotEquals(Pages.clientDetailsPage().getTaxID(),
                empty,
                "Client Tax ID is empty!");
        softAssert.assertNotEquals(Pages.clientDetailsPage().getSelectOfficer(),
                empty,
                "Client selected officer is empty!");
        softAssert.assertNotEquals(Pages.clientDetailsPage().getPhone(),
                empty,
                "Client Phone is empty!");
        softAssert.assertNotEquals(Pages.clientDetailsPage().getAddress(),
                empty,
                "Client Address is empty!");
        softAssert.assertAll();
    }

    public void verifyNotBlankFieldsForConsumerClient() {
        SoftAssert softAssert = new SoftAssert();
        String empty = "";
        softAssert.assertNotEquals(Pages.clientDetailsPage().getClientTypeFromInputField(),
                 empty,
                "Client type is empty!");
        softAssert.assertNotEquals(Pages.clientDetailsPage().getStatus(),
                 empty,
                "Client status is empty!");
        softAssert.assertNotEquals(Pages.clientDetailsPage().getFirstName(),
                 empty,
                "Client First Name is empty!");
        softAssert.assertNotEquals(Pages.clientDetailsPage().getLastName(),
                 empty,
                "Client Last Name is empty!");
        softAssert.assertNotEquals(Pages.clientDetailsPage().getTaxPairIdType(),
                 empty,
                "Client Tax Payer ID Type is empty!");
        softAssert.assertNotEquals(Pages.clientDetailsPage().getTaxID(),
                 empty,
                "Client Tax ID is empty!");
        softAssert.assertNotEquals(Pages.clientDetailsPage().getBirthDate(),
                 empty,
                "Client BirthDate is empty!");
        softAssert.assertNotEquals(Pages.clientDetailsPage().getAddress(),
                 empty,
                "Client Address is empty!");
        softAssert.assertAll();
    }

    public void verifyNotBlankFieldsForIndividualClient() {
        SoftAssert softAssert = new SoftAssert();
        String empty = "";
        softAssert.assertNotEquals(Pages.clientDetailsPage().getClientTypeFromInputField(),
                 empty,
                "Client type is empty!");
        softAssert.assertNotEquals(Pages.clientDetailsPage().getStatus(),
                 empty,
                "Client status is empty!");
        softAssert.assertNotEquals(Pages.clientDetailsPage().getFirstName(),
                empty,
                "Client First Name is empty!");
        softAssert.assertNotEquals(Pages.clientDetailsPage().getLastName(),
                 empty,
                "Client Last Name is empty!");
        softAssert.assertNotEquals(Pages.clientDetailsPage().getTaxPairIdType(),
                 empty,
                "Client Tax Payer ID Type is empty!");
        softAssert.assertNotEquals(Pages.clientDetailsPage().getTaxID(),
                 empty,
                "Client Tax ID is empty!");
        softAssert.assertNotEquals(Pages.clientDetailsPage().getBirthDate(),
                 empty,
                "Client BirthDate is empty!");
        softAssert.assertNotEquals(Pages.clientDetailsPage().getOccupation(),
                 empty,
                "Client occupation is empty!");
        softAssert.assertNotEquals(Pages.clientDetailsPage().getSelectOfficer(),
                 empty,
                "Client selected officer is empty!");
        softAssert.assertNotEquals(Pages.clientDetailsPage().getPhone(),
                 empty,
                "Client Phone is empty!");
        softAssert.assertNotEquals(Pages.clientDetailsPage().getAddress(),
                 empty,
                "Client Address is empty!");
        softAssert.assertAll();
    }

    public void verifyNotBlankFields() {
        SoftAssert softAssert = new SoftAssert();
        String empty = "";
        softAssert.assertNotEquals(Pages.clientDetailsPage().getStatus(),
                 empty,
                "Client status is empty!");
        softAssert.assertNotEquals(Pages.clientDetailsPage().getOrganizationName(),
                 empty,
                "Client name is empty!");
        softAssert.assertNotEquals(Pages.clientDetailsPage().getTaxPairIdType(),
                 empty,
                "Client Tax Payer ID Type is empty!");
        softAssert.assertNotEquals(Pages.clientDetailsPage().getTaxID(),
                 empty,
                "Client Tax ID is empty!");
        softAssert.assertNotEquals(Pages.clientDetailsPage().getMailCode(),
                 empty,
                "Client Mail Code is empty!");
        softAssert.assertNotEquals(Pages.clientDetailsPage().getSelectOfficer(),
                 empty,
                "Client selected officer is empty!");
        softAssert.assertNotEquals(Pages.clientDetailsPage().getPhone(),
                 empty,
                "Client Phone is empty!");
        softAssert.assertNotEquals(Pages.clientDetailsPage().getAddress(),
                 empty,
                "Client Address is empty!");
        softAssert.assertAll();
    }

    public void verifyTrustAccountUpdatedFields(TrustAccountUpdateModel updateModel) {
        SoftAssert softAssert = new SoftAssert();
        int addressIndex = Pages.clientDetailsPage().getAddressRowsCount();
        verifyUserDefinedFields(softAssert, updateModel);
        verifyPhones(softAssert, updateModel.getUpdatedPhones());
        verifyAddress(softAssert, updateModel.getAdditionalAddress(), addressIndex);
        softAssert.assertAll();
    }

    private void verifyUserDefinedFields(SoftAssert softAssert, TrustAccountUpdateModel updateModel) {
        softAssert.assertEquals(Pages.clientDetailsPage().getUserDefined1(),
                updateModel.getUserDefinedField1(),
                "User defined field1 is not correct");
        softAssert.assertEquals(Pages.clientDetailsPage().getUserDefined2(),
                updateModel.getUserDefinedField2(),
                "User defined field2 is not correct");
        softAssert.assertEquals(Pages.clientDetailsPage().getUserDefined3(),
                updateModel.getUserDefinedField3(),
                "User defined field3 is not correct");
        softAssert.assertEquals(Pages.clientDetailsPage().getUserDefined4(),
                updateModel.getUserDefinedField4(),
                "User defined field4 is not correct");
    }

    private void verifyPhones(SoftAssert softAssert, List<Phone> phones) {
        for (int i = 0; i < phones.size(); i++) {
            softAssert.assertEquals(Pages.clientDetailsPage().getPhoneTypeByIndex(i+1),
                    phones.get(i).getPhoneType().getPhoneType(),
                    "phone type " + i + " is not correct!");
            softAssert.assertEquals(Pages.clientDetailsPage().getPhoneCountryByIndex(i+1),
                    phones.get(i).getCountry().getCountry(),
                    "phone type " + i + " is not correct!");
            softAssert.assertEquals(Pages.clientDetailsPage().getPhoneNumberByIndex(i+1),
                    phones.get(i).getPhoneNumber(),
                    "phone type " + i + " is not correct!");
        }
    }

    private void verifyAddress(SoftAssert softAssert, Address address, int addressIndex) {
        softAssert.assertEquals(Pages.clientDetailsPage().getAddressType1(addressIndex),
                address.getType().getAddressType(),
                "address type is incorrect!");
        softAssert.assertEquals(Pages.clientDetailsPage().getAddressCountry1(addressIndex),
                address.getCountry().getCountry(),
                "address country is incorrect!");
        softAssert.assertEquals(Pages.clientDetailsPage().getAddress1(addressIndex),
                address.getAddress(),
                "address address line is incorrect!");
        softAssert.assertEquals(Pages.clientDetailsPage().getAddressCity1(addressIndex),
                address.getCity(),
                "address city line is incorrect!");
        softAssert.assertEquals(Pages.clientDetailsPage().getAddressState1(addressIndex),
                address.getState().getState(),
                "address state line is incorrect!");
        softAssert.assertEquals(Pages.clientDetailsPage().getAddressZipCode1(addressIndex),
                address.getZipCode(),
                "address zipCode line is incorrect!");
    }

    public void clickSaveChangesButton() {
        Pages.clientDetailsPage().clickSaveChangesButtonWithJs();
        Pages.clientDetailsPage().waitForProfileNotEditable();
    }

    public void verifyClientInformationOnMaintenanceTab(TrustAccountUpdateModel updateModel, int rowsCount) {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(Pages.accountMaintenancePage().getRowNewValueByRowName("Zip Code", 1),
                                updateModel.getAdditionalAddress().getZipCode(),
                                "Zip code is incorrect !");
        softAssert.assertEquals(Pages.accountMaintenancePage().getRowNewValueByRowName("State", 1),
                                updateModel.getAdditionalAddress().getState().getState(),
                                "State is incorrect !");
        softAssert.assertEquals(Pages.accountMaintenancePage().getRowNewValueByRowName("Country", 1),
                                updateModel.getAdditionalAddress().getCountry().getCountry(),
                                "Country is incorrect !");
        softAssert.assertEquals(Pages.accountMaintenancePage().getRowNewValueByRowName("City", 1),
                                updateModel.getAdditionalAddress().getCity(),
                                "City is incorrect !");
        softAssert.assertEquals(Pages.accountMaintenancePage().getRowNewValueByRowName("Type", 1),
                                updateModel.getAdditionalAddress().getType().getAddressType(),
                                "Type is incorrect !");
        softAssert.assertEquals(Pages.accountMaintenancePage().getRowNewValueByRowName("Address", 1),
                                updateModel.getAdditionalAddress().getAddress(),
                                "Address line1 is incorrect!");
        softAssert.assertEquals(Pages.accountMaintenancePage().getRowNewValueByRowName("Phone", 2),
                                updateModel.getUpdatedPhones().get(0).getPhoneNumber(),
                                "phone number1 is incorrect!");
        softAssert.assertEquals(Pages.accountMaintenancePage().getRowNewValueByRowName("Phone", 3),
                                updateModel.getUpdatedPhones().get(1).getPhoneNumber(),
                                "phone number1 is incorrect!");
        softAssert.assertTrue(Pages.accountMaintenancePage().getRowsCount() > rowsCount,
                            "Rows count is incorrect!");
        softAssert.assertAll();
    }

    public void clickIgnoreOFACMatching() {
        Pages.clientDetailsPage().clickIgnoreOFACMatching();
    }
}