package com.nymbus.newmodels.client.basicinformation.type.organisation;

import com.nymbus.newmodels.client.basicinformation.type.ClientType;

public class FederalGovernment extends OrganisationType {
    public FederalGovernment() {
        this(ClientType.FEDERAL_GOVERNMENT);
    }

    private FederalGovernment(ClientType clientType) {
        super(clientType);
    }
}
