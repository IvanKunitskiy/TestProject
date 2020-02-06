package com.nymbus.actions.client;

import com.nymbus.models.client.Client;
import com.nymbus.pages.Pages;
import org.testng.Assert;

import java.util.List;
import java.util.Random;

public class CreateClient {

    public void openClientPage(){
        if (Pages.aSideMenuPage().isClientPageOpened())
            return;

        Pages.aSideMenuPage().waitForASideMenu();
        Pages.aSideMenuPage().clickClientMenuItem();
        Pages.clientsPage().waitForAddNewClientButton();
        Assert.assertTrue(Pages.aSideMenuPage().isClientPageOpened(),
                "Client page is not opened");
    }

    public void createClient(Client client){
        openClientPage();
        Pages.clientsPage().clickAddNewClient();
        setBasicInformation(client);
        Assert.assertTrue(Pages.addClientPage().isVerificationSuccess(),
                "Client data verification is not success");
        Pages.addClientPage().clickOkModalButton();
    }

    public void setBasicInformation(Client client){
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
        Pages.addClientPage().setIDNumberValue(client.getIdentityDocument().getNumber());
        setIssuedBY(client);
        setCountry(client);
        Pages.addClientPage().setExpirationDateValue(client.getIdentityDocument().getExpirationDate());
        setAddress(client);
        Pages.addClientPage().clickSaveAndContinueButton();
        Pages.addClientPage().waitForModalWindow();
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
        if (client.getIdentityDocument().getType() == null)
            client.getIdentityDocument().setType(listOfIDType.get(new Random().nextInt(listOfIDType.size())).trim());
        Pages.addClientPage().setIDTypeValue(client.getIdentityDocument().getType());
        Pages.addClientPage().clickIDTypeOption(client.getIdentityDocument().getType());
    }

    private void setIssuedBY(Client client) {

        Pages.addClientPage().clickIssuedBySelectorButton();
        List<String> listOfIssuedBy = Pages.addClientPage().getIssuedByList();

        Assert.assertTrue(listOfIssuedBy.size() > 0,
                "There are not an available Issued By");
        if (client.getIdentityDocument().getIssuedBy() == null)
            client.getIdentityDocument().setIssuedBy(listOfIssuedBy.get(new Random().nextInt(listOfIssuedBy.size())).trim());
        Pages.addClientPage().setIssuedByValue(client.getIdentityDocument().getIssuedBy());
        Pages.addClientPage().clickIssuedByOption(client.getIdentityDocument().getIssuedBy());
    }

    private void setCountry(Client client) {

        Pages.addClientPage().clickCountrySelectorButton();
        List<String> listOfCountry = Pages.addClientPage().getCountryList();

        Assert.assertTrue(listOfCountry.size() > 0,
                "There are not an available Country");
        if (client.getIdentityDocument().getCountry() == null)
            client.getIdentityDocument().setCountry(listOfCountry.get(new Random().nextInt(listOfCountry.size())).trim());
        Pages.addClientPage().setCountryValue(client.getIdentityDocument().getCountry());
        Pages.addClientPage().clickCountryOption(client.getIdentityDocument().getCountry());
    }

    private void setAddress(Client client){
        setAddressType(client);
        setAddressCountry(client);
        Pages.addClientPage().setAddressValue(client.getAddress().getAddress());
        Pages.addClientPage().setAddressCityValue(client.getAddress().getCity());
        setAddressStates(client);
        Pages.addClientPage().setAddressZipCodeValue(client.getAddress().getZipCode());
    }

    private void setAddressType(Client client){
        Pages.addClientPage().clickAddressTypeSelectorButton();
        List<String> listOfAddressType = Pages.addClientPage().getAddressTypeList();

        Assert.assertTrue(listOfAddressType.size() > 0,
                "There are not an available Address Type");
        if (client.getAddress().getType() == null)
            client.getAddress().setType(listOfAddressType.get(new Random().nextInt(listOfAddressType.size())).trim());
        Pages.addClientPage().setAddressTypeValue(client.getAddress().getType());
        Pages.addClientPage().clickAddressTypeOption(client.getAddress().getType());
    }

    private void setAddressCountry(Client client){
        Pages.addClientPage().clickAddressCountrySelectorButton();
        List<String> listOfAddressCountry = Pages.addClientPage().getAddressCountryList();

        Assert.assertTrue(listOfAddressCountry.size() > 0,
                "There are not an available Address Country");
        if (client.getAddress().getCountry() == null)
            client.getAddress().setCountry(listOfAddressCountry.get(new Random().nextInt(listOfAddressCountry.size())).trim());
        Pages.addClientPage().setAddressCountryValue(client.getAddress().getCountry());
        Pages.addClientPage().clickAddressCountryOption(client.getAddress().getCountry());
    }

    private void setAddressStates(Client client){
        Pages.addClientPage().clickAddressStateSelectorButton();
        List<String> listOfAddressStates = Pages.addClientPage().getAddressStateList();

        Assert.assertTrue(listOfAddressStates.size() > 0,
                "There are not an available Address Sates");
        if (client.getAddress().getState() == null)
            client.getAddress().setState(listOfAddressStates.get(new Random().nextInt(listOfAddressStates.size())).trim());
        Pages.addClientPage().setAddressStateValue(client.getAddress().getState());
        Pages.addClientPage().clickAddressStateOption(client.getAddress().getState());
    }
}
