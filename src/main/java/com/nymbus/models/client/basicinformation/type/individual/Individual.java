package com.nymbus.models.client.basicinformation.type.individual;

import com.nymbus.models.client.basicinformation.type.ClientType;

public class Individual extends IndividualType {
    public Individual() {
        this(ClientType.INDIVIDUAL);
    }

    private Individual(ClientType clientType) {
        super(clientType);
    }
}
