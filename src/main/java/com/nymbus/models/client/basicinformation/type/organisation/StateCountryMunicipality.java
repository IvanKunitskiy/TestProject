package com.nymbus.models.client.basicinformation.type.organisation;

import com.nymbus.models.client.basicinformation.type.ClientType;

public class StateCountryMunicipality extends OrganisationType {
    public StateCountryMunicipality() {
        this(ClientType.STATE_COUNTRY_MUNICIPALITY);
    }

    private StateCountryMunicipality(ClientType clientType) {
        super(clientType);
    }
}
