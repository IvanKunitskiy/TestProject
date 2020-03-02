package com.nymbus.models.client.clientdetails.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ClientStatus {
    MEMBER("Member"),
    NON_MEMBER("Non-member"),
    CONSUMER("Consumer");

    private final String clientStatus;
}
