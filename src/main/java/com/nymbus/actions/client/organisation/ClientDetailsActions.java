package com.nymbus.actions.client.organisation;

import com.nymbus.newmodels.client.basicinformation.address.Address;
import com.nymbus.newmodels.client.clientdetails.contactinformation.email.Email;
import com.nymbus.newmodels.client.clientdetails.contactinformation.phone.Phone;
import com.nymbus.pages.Pages;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class ClientDetailsActions {

    public void clickEditProfile() {
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

    public void verifyNotBlankFields() {
        SoftAssert softAssert = new SoftAssert();
        String empty = "";
        softAssert.assertNotEquals(Pages.clientDetailsPage().getStatus(),
                 empty,
                "Client status is empty!");
        softAssert.assertNotEquals(Pages.clientDetailsPage().getFirstName(),
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
}