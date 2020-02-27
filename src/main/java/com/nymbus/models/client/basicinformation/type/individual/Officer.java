package com.nymbus.models.client.basicinformation.type.individual;

import com.nymbus.models.client.basicinformation.type.ClientType;

public class Officer extends IndividualType {
    public Officer() {
        this(ClientType.OFFICER);
    }

    private Officer(ClientType clientType) {
        super(clientType);
    }
}
