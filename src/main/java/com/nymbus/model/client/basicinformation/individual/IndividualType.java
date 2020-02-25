package com.nymbus.model.client.basicinformation.individual;

import com.nymbus.model.RequiredField;
import com.nymbus.model.client.details.ClientStatus;
import com.nymbus.model.client.details.TaxPayerIDType;
import com.nymbus.model.client.other.document.IDType;
import lombok.Data;

@Data
public abstract class IndividualType {
    private ClientStatus clientStatus;
    @RequiredField private String firstName;
    private String middleName;
    @RequiredField private String lastName;
    @RequiredField private TaxPayerIDType taxPayerIDType;
    @RequiredField private int taxID;
    @RequiredField private String birthDate;
    @RequiredField private IDType idType;
    @RequiredField private String idNumber;
    private String issuedBy;
    private String country;
    private String expirationDate;
}
