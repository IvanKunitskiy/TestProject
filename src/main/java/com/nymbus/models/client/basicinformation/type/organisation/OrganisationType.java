package com.nymbus.models.client.basicinformation.type.organisation;

import com.nymbus.models.RequiredField;
import com.nymbus.models.client.Client;
import com.nymbus.models.client.basicinformation.address.Address;
import com.nymbus.models.client.basicinformation.type.ClientType;
import com.nymbus.models.client.basicinformation.type.TaxPayerIDType;
import com.nymbus.models.client.details.type.ClientStatus;
import lombok.Data;

import java.util.Set;

@Data
public abstract class OrganisationType extends Client {
    @RequiredField private ClientType clientType;
    private ClientStatus clientStatus;
    @RequiredField String name;
    @RequiredField private TaxPayerIDType taxPayerIDType;
    @RequiredField private String taxID;

    @RequiredField private Set<Address> addresses; // At least one

    protected OrganisationType(ClientType clientType) {
        this.clientType = clientType;
    }
}
