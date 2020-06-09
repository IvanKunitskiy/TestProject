package com.nymbus.newmodels.client.clientdetails.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ClientStatus {
    MEMBER("Member"),
    NON_MEMBER("Non-member"),
    CONSUMER("Consumer"),
    CUSTOMER("Customer");

    private final String clientStatus;
}
