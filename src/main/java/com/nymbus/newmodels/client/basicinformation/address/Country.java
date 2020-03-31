package com.nymbus.newmodels.client.basicinformation.address;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Country {
    UNITED_STATES("United States"),
    UKRAINE("Ukraine"),
    RUSSIA("Russia"),
    NONE("");

    private final String country;
}
