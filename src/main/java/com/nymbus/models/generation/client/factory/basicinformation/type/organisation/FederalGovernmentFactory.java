package com.nymbus.models.generation.client.factory.basicinformation.type.organisation;

import com.nymbus.models.client.basicinformation.type.TaxPayerIDType;
import com.nymbus.models.client.basicinformation.type.organisation.FederalGovernment;
import com.nymbus.models.client.basicinformation.type.organisation.OrganisationType;
import com.nymbus.models.client.clientdetails.type.ClientStatus;
import com.nymbus.util.Random;

import java.sql.Timestamp;

public class FederalGovernmentFactory implements OrganisationTypeFactory {
    @Override
    public OrganisationType getOrganisationType() {
        FederalGovernment federalGovernment = new FederalGovernment();
        federalGovernment.setClientStatus(ClientStatus.MEMBER);
        federalGovernment.setName(Random.genString(10));
        federalGovernment.setTaxPayerIDType(TaxPayerIDType.BUSINESS_TIN);
        federalGovernment.setTaxID(String.valueOf(new Timestamp(System.currentTimeMillis()).getTime()).substring(4));

        return federalGovernment;
    }
}
