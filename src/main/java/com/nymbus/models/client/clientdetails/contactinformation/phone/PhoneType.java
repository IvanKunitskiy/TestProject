package com.nymbus.models.client.clientdetails.contactinformation.phone;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PhoneType {
    DAYTIME("Daytime"),
    FAX("Fax"),
    HOME("Home"),
    MOBILE("Mobile"),
    WORK("Work");

    private final String phoneType;
}
