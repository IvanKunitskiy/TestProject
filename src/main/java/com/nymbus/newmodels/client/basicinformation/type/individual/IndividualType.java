package com.nymbus.newmodels.client.basicinformation.type.individual;

import com.nymbus.newmodels.client.basicinformation.address.Address;
import com.nymbus.newmodels.client.basicinformation.type.ClientType;
import com.nymbus.newmodels.client.basicinformation.type.TaxPayerIDType;
import com.nymbus.newmodels.client.clientdetails.type.ClientStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public abstract class IndividualType {
    @NonNull private ClientType clientType;
    private ClientStatus clientStatus;
    @NonNull private String firstName;
    private String middleName;
    @NonNull private String lastName;
    @NonNull private TaxPayerIDType taxPayerIDType;
    @NonNull private String taxID;
    @NonNull private String birthDate;
//    @NonNull private IDType idType;
    private String clientID;
//    private State issuedBy;
//    private Country country;
//    private String expirationDate;

    @NonNull private List<Address> addresses; // At least one

    protected IndividualType(ClientType clientType) {
        this.clientType = clientType;
    }
}
