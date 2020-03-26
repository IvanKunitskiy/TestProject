package com.nymbus.newmodels.client.basicinformation.type.organisation;

import com.nymbus.newmodels.client.basicinformation.type.ClientType;

public class StateCountryMunicipality extends OrganisationType {
    public StateCountryMunicipality() {
        this(ClientType.STATE_COUNTRY_MUNICIPALITY);
    }

    private StateCountryMunicipality(ClientType clientType) {
        super(clientType);
    }
}
