package com.nymbus.newmodels.client.basicinformation.type.organisation;

import com.nymbus.newmodels.client.basicinformation.type.ClientType;

public class Partnership extends OrganisationType {
    public Partnership() {
        this(ClientType.PARTNERSHIP);
    }

    private Partnership(ClientType clientType) {
        super(clientType);
    }
}
