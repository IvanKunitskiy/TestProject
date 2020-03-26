package com.nymbus.actions.client;

import com.nymbus.newmodels.client.OrganisationClient;
import com.nymbus.newmodels.client.basicinformation.address.Address;
import com.nymbus.pages.Pages;

import java.util.Set;

public class CreateOrganisationClientActions {

    public void setBasicInformation(OrganisationClient client) {
        setClientType(client.getOrganisationType().getClientType().getClientType());
        setClientStatus(client.getOrganisationType().getClientStatus().getClientStatus());
        Pages.addClientPage().setLastNameValue(client.getOrganisationType().getName());
        setTaxPayerIDType(client.getOrganisationType().getTaxPayerIDType().getTaxPayerIDType());
        Pages.addClientPage().setTaxIDValue(client.getOrganisationType().getTaxID());
        setClientAddresses(client.getOrganisationType().getAddresses());
    }

    private void setClientAddresses(Set<Address> addresses) {
        for (int i = 0; i < addresses.size(); i++) {
            setAddress((Address)addresses.toArray()[i], i);
        }
    }

    private void setAddress(Address address, int i) {
        setAddressType(address.getType().getAddressType(), i);
        setAddressCountry(address.getCountry().getCountry());
        Pages.addClientPage().setAddressValue(address.getAddress());
        Pages.addClientPage().setAddressCityValue(address.getCity());
        setAddressStates(address.getState().getState());
        Pages.addClientPage().setAddressZipCodeValue(address.getZipCode());
    }

    private void setAddressStates(String addressStates) {
        Pages.addClientPage().clickAddressStateSelectorButton();
        Pages.addClientPage().clickAddressStateOption(addressStates);
    }

    private void setAddressType(String addressType, int i) {
        Pages.addClientPage().clickAddressTypeSelectorButton1(i);
        Pages.addClientPage().clickAddressTypeOption(addressType);
    }

    private void setAddressCountry(String country) {
        Pages.addClientPage().clickAddressCountrySelectorButton();
        Pages.addClientPage().clickAddressCountryOption(country);
    }

    private void setTaxPayerIDType(String taxPayerType) {
        Pages.addClientPage().clickTaxPayerIDTypeSelectorButton();
        Pages.addClientPage().clickTaxPayerIDTypeOption(taxPayerType);
    }

    private void setClientStatus(String clientStatus) {
        Pages.addClientPage().clickClientStatusSelectorButton();
        Pages.addClientPage().clickClientStatusOption(clientStatus);
    }

    private void setClientType(String clientType) {
        Pages.addClientPage().clickClientTypeSelectorButton();
        Pages.addClientPage().clickClientTypeOption(clientType);
    }
}
