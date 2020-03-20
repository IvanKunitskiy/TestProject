package com.nymbus.newmodels.client.basicinformation.type.organisation;

import com.nymbus.newmodels.client.basicinformation.type.ClientType;

public class TrustAccount extends OrganisationType {
    public TrustAccount() {
        this(ClientType.TRUST_ACCOUNT);
    }

    private TrustAccount(ClientType clientType) {
        super(clientType);
    }
}
