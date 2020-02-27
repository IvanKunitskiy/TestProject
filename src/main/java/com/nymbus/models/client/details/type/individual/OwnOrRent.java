package com.nymbus.models.client.details.type.individual;

public enum OwnOrRent {
    OWN("Own"),
    RENT("Rent");

    private final String ownOrRent;

    OwnOrRent(String ownOrRent) {
        this.ownOrRent = ownOrRent;
    }

    public String getOwnOrRent() {
        return ownOrRent;
    }
}
