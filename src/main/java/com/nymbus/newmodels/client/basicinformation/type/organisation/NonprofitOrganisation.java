package com.nymbus.newmodels.client.basicinformation.type.organisation;

import com.nymbus.newmodels.client.basicinformation.type.ClientType;

public class NonprofitOrganisation extends OrganisationType {
    public NonprofitOrganisation() {
        this(ClientType.NONPROFIT_ORGANIZATION);
    }

    private NonprofitOrganisation(ClientType clientType) {
        super(clientType);
    }
}
