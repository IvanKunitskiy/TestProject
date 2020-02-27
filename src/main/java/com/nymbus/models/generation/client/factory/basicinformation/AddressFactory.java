package com.nymbus.models.generation.client.factory.basicinformation;

import com.nymbus.models.client.basicinformation.address.Address;
import com.nymbus.models.client.basicinformation.address.AddressType;
import com.nymbus.models.client.basicinformation.address.Country;
import com.nymbus.models.client.basicinformation.address.State;
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
}
