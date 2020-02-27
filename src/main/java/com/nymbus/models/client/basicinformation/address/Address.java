package com.nymbus.models.client.basicinformation.address;

import com.nymbus.models.RequiredField;
import lombok.Data;

@Data
public class Address {
    @RequiredField private AddressType type;
    @RequiredField private Country country;
    @RequiredField private String address;
    private String addressLine2;
    @RequiredField private String city;
    @RequiredField private State state; // Shown for countries which have states (United States)
    @RequiredField private String districtName; // Shown for countries without states (Ukraine, Russia, etc)
    @RequiredField private String zipCode;
    private int yearsInThisAddress;
}
