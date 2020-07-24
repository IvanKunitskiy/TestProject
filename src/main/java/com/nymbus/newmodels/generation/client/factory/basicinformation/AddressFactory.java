package com.nymbus.newmodels.generation.client.factory.basicinformation;

import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.client.basicinformation.address.Address;
import com.nymbus.newmodels.client.basicinformation.address.AddressType;
import com.nymbus.newmodels.client.basicinformation.address.Country;
import com.nymbus.newmodels.client.basicinformation.address.State;
import com.nymbus.util.Random;

public class AddressFactory {
    public Address getAddress() {
        Address address = new Address();
        address.setType(AddressType.PHYSICAL);
        address.setCountry(Country.UNITED_STATES);
        address.setAddress(Random.genAddress());
        address.setAddressLine2(Random.genAddress());
        address.setCity(Random.genCity());
        address.setState(State.ALABAMA);
        address.setDistrictName(Random.genString(10));
        address.setZipCode(Random.genPostalCode());
        address.setYearsInThisAddress(Random.genInt(1, 10));

        return address;
    }

    public Address getAdditionalAddress() {
        Address address = new Address();
        address.setType(AddressType.ALTERNATE);
        address.setCountry(Country.UNITED_STATES);
        address.setAddress(Random.genAddress());
        address.setAddressLine2(Random.genAddress());
        address.setCity(Random.genCity());
        address.setState(State.ALABAMA);
        address.setDistrictName(Random.genString(10));
        address.setZipCode(Random.genPostalCode());
        address.setYearsInThisAddress(Random.genInt(1, 10));

        return address;
    }

    public Address getSeasonalAddress() {
        Address address = new Address();
        address.setType(AddressType.SEASONAL);
        address.setCountry(Country.UNITED_STATES);
        address.setAddress(Random.genAddress());
        address.setAddressLine2(Random.genAddress());
        address.setCity(Random.genCity());
        address.setState(State.ALABAMA);
        address.setDistrictName(Random.genString(10));
        address.setZipCode(Random.genPostalCode());
        address.setStartDate(DateTime.getDateMinusDays(WebAdminActions.loginActions().getSystemDate(), 1));
        address.setEndDate(DateTime.getDatePlusDays(WebAdminActions.loginActions().getSystemDate(), 1));
        address.setYearsInThisAddress(Random.genInt(1, 10));

        return address;
    }
}