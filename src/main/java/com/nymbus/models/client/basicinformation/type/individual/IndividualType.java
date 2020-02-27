package com.nymbus.models.client.basicinformation.type.individual;

import com.nymbus.models.RequiredField;
import com.nymbus.models.client.basicinformation.address.Address;
import com.nymbus.models.client.basicinformation.address.Country;
import com.nymbus.models.client.basicinformation.address.State;
import com.nymbus.models.client.basicinformation.type.ClientType;
import com.nymbus.models.client.basicinformation.type.TaxPayerIDType;
import com.nymbus.models.client.details.type.ClientStatus;
import com.nymbus.models.client.other.document.IDType;
import lombok.Data;

import java.util.Set;

@Data
public abstract class IndividualType {
    @RequiredField private ClientType clientType;
    private ClientStatus clientStatus;
    @RequiredField private String firstName;
    private String middleName;
    @RequiredField private String lastName;
    @RequiredField private TaxPayerIDType taxPayerIDType;
    @RequiredField private String taxID;
    @RequiredField private String birthDate;
    @RequiredField private IDType idType;
    @RequiredField private String idNumber;
    private State issuedBy;
    private Country country;
    private String expirationDate;

    @RequiredField private Set<Address> addresses; // At least one

    protected IndividualType(ClientType clientType) {
        this.clientType = clientType;
    }
}
