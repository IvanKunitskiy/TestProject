package com.nymbus.models.client.clientdetails.type.individual;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OwnOrRent {
    OWN("Own"),
    RENT("Rent");

    private final String ownOrRent;
}
