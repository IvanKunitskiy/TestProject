package com.nymbus.newmodels.client.basicinformation.type.organisation;

import com.nymbus.newmodels.client.Client;
import com.nymbus.newmodels.client.basicinformation.address.Address;
import com.nymbus.newmodels.client.basicinformation.type.ClientType;
import com.nymbus.newmodels.client.basicinformation.type.TaxPayerIDType;
import com.nymbus.newmodels.client.clientdetails.type.ClientStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public abstract class OrganisationType {
    @NonNull private ClientType clientType;
    private ClientStatus clientStatus;
    @NonNull String name;
    @NonNull private TaxPayerIDType taxPayerIDType;
    @NonNull private String taxID;

    @NonNull private Set<Address> addresses; // At least one

    protected OrganisationType(ClientType clientType) {
        this.clientType = clientType;
    }
}
