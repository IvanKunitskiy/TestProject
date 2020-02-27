package com.nymbus.models.client.basicinformation.type.organisation;

import com.nymbus.models.client.basicinformation.type.ClientType;

public class Corporation extends OrganisationType {
    public Corporation() {
        this(ClientType.CORPORATION);
    }

    private Corporation(ClientType clientType) {
        super(clientType);
    }
}
