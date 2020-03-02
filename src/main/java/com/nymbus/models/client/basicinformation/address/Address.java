package com.nymbus.models.client.basicinformation.address;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class Address {
    @NonNull private AddressType type;
    @NonNull private Country country;
    @NonNull private String address;
    private String addressLine2;
    @NonNull private String city;
    @NonNull private State state; // Shown for countries which have states (United States)
    @NonNull private String districtName; // Shown for countries without states (Ukraine, Russia, etc)
    @NonNull private String zipCode;
    private int yearsInThisAddress;
}
