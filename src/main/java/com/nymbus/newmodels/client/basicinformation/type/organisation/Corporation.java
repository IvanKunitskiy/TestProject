package com.nymbus.newmodels.client.basicinformation.type.organisation;

import com.nymbus.newmodels.client.basicinformation.type.ClientType;

public class Corporation extends OrganisationType {
    public Corporation() {
        this(ClientType.CORPORATION);
    }

    private Corporation(ClientType clientType) {
        super(clientType);
    }
}
