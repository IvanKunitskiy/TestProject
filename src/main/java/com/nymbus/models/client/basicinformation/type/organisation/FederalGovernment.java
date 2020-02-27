package com.nymbus.models.client.basicinformation.type.organisation;

import com.nymbus.models.client.basicinformation.type.ClientType;

public class FederalGovernment extends OrganisationType {
    public FederalGovernment() {
        this(ClientType.FEDERAL_GOVERNMENT);
    }

    private FederalGovernment(ClientType clientType) {
        super(clientType);
    }
}
