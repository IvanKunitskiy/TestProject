package com.nymbus.newmodels.client.clientdetails.type.individual;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IndividualIDType {
    ALIEN_REG_CARD("Alien Reg Card"),
    SOCIAL_SECURITY_CARD("Social Security Card"),
    STATE_DRIVERS_LICENSE("State Drivers License"),
    STATE_ID_CARD("State ID Card"),
    US_GOVERNMENT("US Government"),
    US_MILITARY("US Military");

    private final String idType;
}
