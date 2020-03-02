package com.nymbus.models.client.other.document;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IDType {
    ALIEN_REG_CARD("Alien Reg Card"),
    BIRTH_CERTIFICATE("Birth Certificate"),
    CERTIFICATION_BENEFICIAL_OWNER("Certification Beneficial Owner(s)/Entity Manager"),
    COMPANY_ID("Company ID"),
    CREDIT_OR_BANK_CARD("Credit/Bank Card"),
    LOGO("Logo"),
    OTHER("Other"),
    PASSPORT("Passport"),
    PHOTO("Photo - Full Face, Front View"),
    SCHOOL_ID("School ID"),
    SIGNATURE("Signature"),
    SOCIAL_SECURITY_CARD("Social Security Card"),
    STATE_DRIVERS_LICENSE("State Drivers License"),
    STATE_ID_CARD("State ID Card"),
    US_GOVERNMENT("US Government"),
    US_MILITARY("US Military");

    private final String idType;
}
