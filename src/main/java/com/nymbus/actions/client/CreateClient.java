package com.nymbus.actions.client;

import com.nymbus.core.utils.Functions;
import com.nymbus.models.client.Client;
import com.nymbus.pages.Pages;
import org.testng.Assert;

import java.util.List;
import java.util.Random;

public class CreateClient {

    public void openClientPage() {
        if (Pages.aSideMenuPage().isClientPageOpened())
            return;

        Pages.aSideMenuPage().waitForASideMenu();
        Pages.aSideMenuPage().clickClientMenuItem();
        Pages.clientsPage().waitForAddNewClientButton();
        Assert.assertTrue(Pages.aSideMenuPage().isClientPageOpened(),
                "Client page is not opened");
    }

    public void createClient(Client client) {
        openClientPage();
        Pages.clientsPage().clickAddNewClient();
        setBasicInformation(client);
        Assert.assertTrue(Pages.addClientPage().isVerificationSuccess(),
                "Client data verification is not success");
        Pages.addClientPage().clickOkModalButton();
        setClientDetailsData(client);
        setDocumentation(client);
        Pages.addClientPage().clickSaveAndContinueButton();
        Pages.addClientPage().uploadClientSignature(Functions.getFilePathByName("clientSignature.png"));
        Pages.addClientPage().clickSaveAndContinueButton();
        Pages.addClientPage().clickViewMemberProfileButton();
    }

    public void setBasicInformation(Client client) {
        setClientType(client);
        setClientStatus(client);
        Pages.addClientPage().setFirstNameValue(client.getFirstName());
        Pages.addClientPage().setMiddleNameValue(client.getMiddleName());
        Pages.addClientPage().setLastNameValue(client.getLastName());
        setTaxPayerIDType(client);
        Pages.addClientPage().setTaxIDValue(client.getTaxID());
        Pages.addClientPage().clickBirthDateCalendarIcon();
        Pages.addClientPage().clickBirthDateCalendarIcon();
        Pages.addClientPage().setBirthDateValue(client.getBirthDate());
        Pages.addClientPage().clickBirthDateCalendarIcon();
        setIDType(client);
        Pages.addClientPage().setIDNumberValue(client.getIdentityDocument().get(0).getNumber());
        setIssuedBY(client);
        setCountry(client);
        Pages.addClientPage().setExpirationDateValue(client.getIdentityDocument().get(0).getExpirationDate());
        setAddress(client);
        Pages.addClientPage().clickSaveAndContinueButton();
        Pages.addClientPage().waitForModalWindow();
    }

    public void setClientDetailsData(Client client) {
        Pages.addClientPage().setSuffixField(client.getSuffix());
        Pages.addClientPage().setMaidenFamilyNameField(client.getMaidenFamilyName());
        Pages.addClientPage().setAkaField(client.getAKA_1());
        Pages.addClientPage().uploadProfilePhoto(Functions.getFilePathByName("profilePhoto.png"));
        setGender(client);
        setEducation(client);
        setIncome(client);
        setMaritalStatus(client);
        Pages.addClientPage().setOccupationValue(client.getOccupation());
        setConsumerInformationIndicator(client);
        Pages.addClientPage().setJobTitleValue(client.getJobTitle());
        setOwnOrRent(client);
        setMailCode(client);
        setSelectOfficer(client);
        Pages.addClientPage().setUserDefinedField1Value(client.getUserDefined_1());
        Pages.addClientPage().setUserDefinedField2Value(client.getUserDefined_2());
        Pages.addClientPage().setUserDefinedField3Value(client.getUserDefined_3());
        Pages.addClientPage().setUserDefinedField4Value(client.getUserDefined_4());
        Pages.addClientPage().setPhoneValue(client.getPhone());
        setEmailType(client);
        Pages.addClientPage().setEmailValue(client.getEmail());
        Pages.addClientPage().clickSaveAndContinueButton();
    }

    private void setClientType(Client client) {

        Pages.addClientPage().clickClientTypeSelectorButton();
        List<String> listOfClientType = Pages.addClientPage().getClientTypeList();

        Assert.assertTrue(listOfClientType.size() > 0,
                "There are not an available client type");
        if (client.getClientType() == null)
            client.setClientType(listOfClientType.get(new Random().nextInt(listOfClientType.size())).trim());
        Pages.addClientPage().setClientTypeValue(client.getClientType());
        Pages.addClientPage().clickClientTypeOption(client.getClientType());
    }

    private void setClientStatus(Client client) {

        Pages.addClientPage().clickClientStatusSelectorButton();
        List<String> listOfClientStatus = Pages.addClientPage().getClientStatusList();

        Assert.assertTrue(listOfClientStatus.size() > 0,
                "There are not an available client status");
        if (client.getClientStatus() == null)
            client.setClientStatus(listOfClientStatus.get(new Random().nextInt(listOfClientStatus.size())).trim());
//        Pages.addClientPage().setClientStatusValue(client.getClientStatus());
        Pages.addClientPage().clickClientStatusOption(client.getClientStatus());
    }

    private void setTaxPayerIDType(Client client) {

        Pages.addClientPage().clickTaxPayerIDTypeSelectorButton();
        List<String> listOfTaxPayerIDType = Pages.addClientPage().getTaxPayerIDTypeList();

        Assert.assertTrue(listOfTaxPayerIDType.size() > 0,
                "There are not an available client status");
        if (client.getTaxPayerIDType() == null)
            client.setTaxPayerIDType(listOfTaxPayerIDType.get(new Random().nextInt(listOfTaxPayerIDType.size())).trim());
        Pages.addClientPage().setTaxPayerIDTypeValue(client.getTaxPayerIDType());
        Pages.addClientPage().clickTaxPayerIDTypeOption(client.getTaxPayerIDType());
    }

    private void setIDType(Client client) {

        Pages.addClientPage().clickIDTypeSelectorButton();
        List<String> listOfIDType = Pages.addClientPage().getIDTypeList();

        Assert.assertTrue(listOfIDType.size() > 0,
                "There are not an available ID Type");
        if (client.getIdentityDocument().get(0).getType() == null)
            client.getIdentityDocument().get(0).setType(listOfIDType.get(new Random().nextInt(listOfIDType.size())).trim());
        Pages.addClientPage().setIDTypeValue(client.getIdentityDocument().get(0).getType());
        Pages.addClientPage().clickIDTypeOption(client.getIdentityDocument().get(0).getType());
    }

    private void setIssuedBY(Client client) {

        Pages.addClientPage().clickIssuedBySelectorButton();
        List<String> listOfIssuedBy = Pages.addClientPage().getIssuedByList();

        Assert.assertTrue(listOfIssuedBy.size() > 0,
                "There are not an available Issued By");
        if (client.getIdentityDocument().get(0).getIssuedBy() == null)
            client.getIdentityDocument().get(0).setIssuedBy(listOfIssuedBy.get(new Random().nextInt(listOfIssuedBy.size())).trim());
        Pages.addClientPage().setIssuedByValue(client.getIdentityDocument().get(0).getIssuedBy());
        Pages.addClientPage().clickIssuedByOption(client.getIdentityDocument().get(0).getIssuedBy());
    }

    private void setCountry(Client client) {

        Pages.addClientPage().clickCountrySelectorButton();
        List<String> listOfCountry = Pages.addClientPage().getCountryList();

        Assert.assertTrue(listOfCountry.size() > 0,
                "There are not an available Country");
        if (client.getIdentityDocument().get(0).getCountry() == null)
            client.getIdentityDocument().get(0).setCountry(listOfCountry.get(new Random().nextInt(listOfCountry.size())).trim());
        Pages.addClientPage().setCountryValue(client.getIdentityDocument().get(0).getCountry());
        Pages.addClientPage().clickCountryOption(client.getIdentityDocument().get(0).getCountry());
    }

    private void setAddress(Client client) {
        setAddressType(client);
        setAddressCountry(client);
        Pages.addClientPage().setAddressValue(client.getAddress().getAddress());
        Pages.addClientPage().setAddressCityValue(client.getAddress().getCity());
        setAddressStates(client);
        Pages.addClientPage().setAddressZipCodeValue(client.getAddress().getZipCode());
    }

    private void setAddressType(Client client) {
        Pages.addClientPage().clickAddressTypeSelectorButton();
        List<String> listOfAddressType = Pages.addClientPage().getAddressTypeList();

        Assert.assertTrue(listOfAddressType.size() > 0,
                "There are not an available Address Type");
        if (client.getAddress().getType() == null)
            client.getAddress().setType(listOfAddressType.get(new Random().nextInt(listOfAddressType.size())).trim());
        Pages.addClientPage().setAddressTypeValue(client.getAddress().getType());
        Pages.addClientPage().clickAddressTypeOption(client.getAddress().getType());
    }

    private void setAddressCountry(Client client) {
        Pages.addClientPage().clickAddressCountrySelectorButton();
        List<String> listOfAddressCountry = Pages.addClientPage().getAddressCountryList();

        Assert.assertTrue(listOfAddressCountry.size() > 0,
                "There are not an available Address Country");
        if (client.getAddress().getCountry() == null)
            client.getAddress().setCountry(listOfAddressCountry.get(new Random().nextInt(listOfAddressCountry.size())).trim());
        Pages.addClientPage().setAddressCountryValue(client.getAddress().getCountry());
        Pages.addClientPage().clickAddressCountryOption(client.getAddress().getCountry());
    }

    private void setAddressStates(Client client) {
        Pages.addClientPage().clickAddressStateSelectorButton();
        List<String> listOfAddressStates = Pages.addClientPage().getAddressStateList();

        Assert.assertTrue(listOfAddressStates.size() > 0,
                "There are not an available Address Sates");
        if (client.getAddress().getState() == null)
            client.getAddress().setState(listOfAddressStates.get(new Random().nextInt(listOfAddressStates.size())).trim());
        Pages.addClientPage().setAddressStateValue(client.getAddress().getState());
        Pages.addClientPage().clickAddressStateOption(client.getAddress().getState());
    }

    private void setGender(Client client) {
        Pages.addClientPage().clickGenderSelectorButton();
        List<String> listOfGender = Pages.addClientPage().getGenderList();

        Assert.assertTrue(listOfGender.size() > 0,
                "There are not an available gender");
        if (client.getGender() == null)
            client.setGender(listOfGender.get(new Random().nextInt(listOfGender.size())).trim());
        Pages.addClientPage().setGenderValue(client.getGender());
        Pages.addClientPage().clickGenderOption(client.getGender());
    }

    private void setEducation(Client client) {
        Pages.addClientPage().clickEducationSelectorButton();
        List<String> listOfEducation = Pages.addClientPage().getEducationList();

        Assert.assertTrue(listOfEducation.size() > 0,
                "There are not an available education");
        if (client.getEducation() == null)
            client.setEducation(listOfEducation.get(new Random().nextInt(listOfEducation.size())).trim());
        Pages.addClientPage().setEducationValue(client.getEducation());
        Pages.addClientPage().clickEducationOption(client.getEducation());
    }

    private void setIncome(Client client) {
        Pages.addClientPage().clickIncomeSelectorButton();
        List<String> listOfIncome = Pages.addClientPage().getIncomeList();

        Assert.assertTrue(listOfIncome.size() > 0,
                "There are not an available income");
        if (client.getIncome() == null)
            client.setIncome(listOfIncome.get(new Random().nextInt(listOfIncome.size())).trim());
        Pages.addClientPage().setIncomeValue(client.getIncome());
        Pages.addClientPage().clickIncomeOption(client.getIncome());
    }

    private void setMaritalStatus(Client client) {
        Pages.addClientPage().clickMaritalStatusSelectorButton();
        List<String> listOfMaritalStatus = Pages.addClientPage().getsMaritalStatusList();

        Assert.assertTrue(listOfMaritalStatus.size() > 0,
                "There are not an available marital status");
        if (client.getMaritalStatus() == null)
            client.setMaritalStatus(listOfMaritalStatus.get(new Random().nextInt(listOfMaritalStatus.size())).trim());
        Pages.addClientPage().setMaritalStatusValue(client.getMaritalStatus());
        Pages.addClientPage().clickMaritalStatusOption(client.getMaritalStatus());
    }

    private void setConsumerInformationIndicator(Client client) {
        Pages.addClientPage().clickConsumerInformationIndicatorSelectorButton();
        List<String> listOfConsumerInformationIndicator = Pages.addClientPage().getsConsumerInformationIndicatorList();

        Assert.assertTrue(listOfConsumerInformationIndicator.size() > 0,
                "There are not an available Consumer Information Indicator");
        if (client.getConsumerInfoIndicator() == null)
            client.setConsumerInfoIndicator(listOfConsumerInformationIndicator.get(new Random().nextInt(listOfConsumerInformationIndicator.size())).trim());
        Pages.addClientPage().setConsumerInformationIndicatorValue(client.getConsumerInfoIndicator());
        Pages.addClientPage().clickConsumerInformationIndicatorOption(client.getConsumerInfoIndicator());
    }

    private void setOwnOrRent(Client client) {
        Pages.addClientPage().clickOwnOrRentSelectorButton();
        List<String> listOfOwnOrRent = Pages.addClientPage().getsOwnOrRentList();

        Assert.assertTrue(listOfOwnOrRent.size() > 0,
                "There are not an available Own or Rent");
        if (client.getOwnOrRent() == null)
            client.setOwnOrRent(listOfOwnOrRent.get(new Random().nextInt(listOfOwnOrRent.size())).trim());
        Pages.addClientPage().setOwnOrRentValue(client.getOwnOrRent());
        Pages.addClientPage().clickOwnOrRentOption(client.getOwnOrRent());
    }

    private void setMailCode(Client client) {
        Pages.addClientPage().clickMailCodeSelectorButton();
        List<String> listOfMailCode = Pages.addClientPage().getsMailCodeList();

        Assert.assertTrue(listOfMailCode.size() > 0,
                "There are not an available Mail Codes");
        if (client.getMailCode() == null)
            client.setMailCode(listOfMailCode.get(new Random().nextInt(listOfMailCode.size())).trim());
        Pages.addClientPage().setMailCodeValue(client.getMailCode());
        Pages.addClientPage().clickMailCodeOption(client.getMailCode());
    }

    private void setSelectOfficer(Client client) {
        Pages.addClientPage().clickSelectOfficerSelectorButton();
        List<String> listOfSelectOfficer = Pages.addClientPage().getSlectOfficerList();

        Assert.assertTrue(listOfSelectOfficer.size() > 0,
                "There are not an available Select Officer");
        if (client.getSelectOfficer() == null)
            client.setSelectOfficer(listOfSelectOfficer.get(new Random().nextInt(listOfSelectOfficer.size())).trim());
        Pages.addClientPage().setSelectOfficerValue(client.getSelectOfficer());
        Pages.addClientPage().clickSelectOfficerOption(client.getSelectOfficer());
    }

    private void setEmailType(Client client) {
        Pages.addClientPage().clickEmailTypeSelectorButton();
        List<String> listOfEmailType = Pages.addClientPage().getEmailTypeList();

        Assert.assertTrue(listOfEmailType.size() > 0,
                "There are not an available Email Type");
        if (client.getEmailType() == null)
            client.setEmailType(listOfEmailType.get(new Random().nextInt(listOfEmailType.size())).trim());
        Pages.addClientPage().setEmailTypeValue(client.getEmailType());
        Pages.addClientPage().clickEmailTypeOption(client.getEmailType());
    }

    private void setDocumentation(Client client) {
        Pages.addClientPage().uploadClientDocumentation(Functions.getFilePathByName("clientDocument.png"));
        setDocumentationIDType(client);
        Pages.addClientPage().setDocumentIDNumberValue(client.getIdentityDocument().get(1).getNumber());
        setDocumentationIssuedBY(client);
        setDocumentationCountry(client);
        Pages.addClientPage().setDocumentExpirationDateValue(client.getIdentityDocument().get(1).getExpirationDate());
        Pages.addClientPage().clickDocumentSaveChangesButton();
    }

    private void setDocumentationIDType(Client client) {

        Pages.addClientPage().clickDocumentIDTypeSelectorButton();
        List<String> listOfIDType = Pages.addClientPage().getDocumentIDTypeList();

        Assert.assertTrue(listOfIDType.size() > 0,
                "There are not an available ID Type");
        if (client.getIdentityDocument().get(1).getType() == null)
            client.getIdentityDocument().get(1).setType(listOfIDType.get(new Random().nextInt(listOfIDType.size())).trim());
        Pages.addClientPage().setDocumentIDTypeValue(client.getIdentityDocument().get(1).getType());
        Pages.addClientPage().clickDocumentIDTypeOption(client.getIdentityDocument().get(1).getType());
    }

    private void setDocumentationIssuedBY(Client client) {
        Pages.addClientPage().clickDocumentIssuedBySelectorButton();
        List<String> listOfIssuedBy = Pages.addClientPage().getDocumentIssuedByList();

        Assert.assertTrue(listOfIssuedBy.size() > 0,
                "There are not an available Issued By");
        if (client.getIdentityDocument().get(1).getIssuedBy() == null)
            client.getIdentityDocument().get(1).setIssuedBy(listOfIssuedBy.get(new Random().nextInt(listOfIssuedBy.size())).trim());
        Pages.addClientPage().setDocumentIssuedByValue(client.getIdentityDocument().get(1).getIssuedBy());
        Pages.addClientPage().clickDocumentIssuedByOption(client.getIdentityDocument().get(1).getIssuedBy());
    }

    private void setDocumentationCountry(Client client) {
        Pages.addClientPage().clickDocumentCountrySelectorButton();
        List<String> listOfCountry = Pages.addClientPage().getDocumentCountryList();

        Assert.assertTrue(listOfCountry.size() > 0,
                "There are not an available Country");
        if (client.getIdentityDocument().get(1).getCountry() == null)
            client.getIdentityDocument().get(1).setCountry(listOfCountry.get(new Random().nextInt(listOfCountry.size())).trim());
        Pages.addClientPage().setDocumentCountryValue(client.getIdentityDocument().get(1).getCountry());
        Pages.addClientPage().clickDocumentCountryOption(client.getIdentityDocument().get(1).getCountry());
    }
}
