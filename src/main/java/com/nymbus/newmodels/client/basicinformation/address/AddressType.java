package com.nymbus.newmodels.client.basicinformation.address;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AddressType {
    ALTERNATE("Alternate"),
    MAILING("Mailing"),
    PHYSICAL("Physical"),
    PREVIOUS("Previous"),
    SEASONAL("Seasonal");

    private final String addressType;
}
