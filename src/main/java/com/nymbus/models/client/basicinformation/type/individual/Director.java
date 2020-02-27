package com.nymbus.models.client.basicinformation.type.individual;

import com.nymbus.models.client.basicinformation.type.ClientType;

public class Director extends IndividualType {
    public Director() {
        this(ClientType.DIRECTOR);
    }

    private Director(ClientType clientType) {
        super(clientType);
    }
}
