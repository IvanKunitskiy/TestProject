package com.nymbus.models.client.clientdetails.contactinformation.email;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmailType {
    ALTERNATE("Alternative"),
    PRIMARY("Primary");

    private final String emailType;
}
