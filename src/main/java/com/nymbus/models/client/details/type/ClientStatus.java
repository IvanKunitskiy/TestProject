package com.nymbus.models.client.details.type;

public enum ClientStatus {
    MEMBER("Member"),
    NON_MEMBER("Non-member"),
    CONSUMER("Consumer");

    private final String clientStatus;

    ClientStatus(String clientStatus) {
        this.clientStatus = clientStatus;
    }

    public String getClientStatus() {
        return clientStatus;
    }
}
