package com.nymbus.newmodels.client.clientdetails.contactinformation.email;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmailType {
    ALTERNATE("Alternate"),
    PRIMARY("Primary");

    private final String emailType;
}
