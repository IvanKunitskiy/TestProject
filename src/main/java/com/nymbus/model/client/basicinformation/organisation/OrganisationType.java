package com.nymbus.model.client.basicinformation.organisation;

import com.nymbus.model.RequiredField;
import com.nymbus.model.client.details.ClientStatus;
import com.nymbus.model.client.details.TaxPayerIDType;
import lombok.Data;

@Data
public abstract class OrganisationType {
    private ClientStatus clientStatus;
    @RequiredField String name;
    @RequiredField private TaxPayerIDType taxPayerIDType;
    @RequiredField private int taxID;
}
