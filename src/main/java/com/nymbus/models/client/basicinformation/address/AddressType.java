package com.nymbus.models.client.basicinformation.address;

public enum AddressType {
    ALTERNATE("Alternate"),
    MAILING("Mailing"),
    PHYSICAL("Physical"),
    PREVIOUS("Previous"),
    SEASONAL("Seasonal");

    private final String addressType;

    AddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getAddressType() {
        return addressType;
    }
}
