package com.nymbus.newmodels.client.basicinformation.type.organisation;

import com.nymbus.newmodels.client.basicinformation.type.ClientType;

public class Estate extends OrganisationType {
    public Estate() {
        this(ClientType.ESTATE);
    }

    private Estate(ClientType clientType) {
        super(clientType);
    }
}
