package com.nymbus.model.client.details.individual;

public enum IndividualIDType {
    ALIEN_REG_CARD("Alien Reg Card"),
    SOCIAL_SECURITY_CARD("Social Security Card"),
    STATE_DRIVERS_LICENSE("State Drivers License"),
    STATE_ID_CARD("State ID Card"),
    US_GOVERNMENT("US Government"),
    US_MILITARY("US Military");

    private final String idType;

    IndividualIDType(String idType) {
        this.idType = idType;
    }

    public String getIdType() {
        return idType;
    }
}
