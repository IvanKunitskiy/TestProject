package com.nymbus.model.client.other.document;

public enum IDType {
    ALIEN_REG_CARD("Alien Reg Card"),
    BIRTH_CERTIFICATE(""),
    CERTIFICATION_BENEFICIAL_OWNER(""),
    COMPANY_ID(""),
    CREDIT_OR_BANK_CARD(""),
    LOGO(""),
    OTHER(""),
    PASSPORT("Passport"),
    PHOTO(""),
    SCHOOL_ID(""),
    SIGNATURE(""),
    SOCIAL_SECURITY_CARD("Social Security Card"),
    STATE_DRIVERS_LICENSE("State Drivers License"),
    STATE_ID_CARD("State ID Card"),
    US_GOVERNMENT("US Government"),
    US_MILITARY("US Military");

    private final String idType;

    IDType(String idType) {
        this.idType = idType;
    }

    public String getIdType() {
        return idType;
    }
}
