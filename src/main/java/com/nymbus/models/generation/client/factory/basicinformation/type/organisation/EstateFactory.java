package com.nymbus.models.generation.client.factory.basicinformation.type.organisation;

import com.nymbus.models.client.basicinformation.type.TaxPayerIDType;
import com.nymbus.models.client.basicinformation.type.organisation.Estate;
import com.nymbus.models.client.basicinformation.type.organisation.OrganisationType;
import com.nymbus.models.client.details.type.ClientStatus;
import com.nymbus.util.Random;

import java.sql.Timestamp;

public class EstateFactory implements OrganisationTypeFactory {
    @Override
    public OrganisationType getOrganisationType() {
        Estate estate = new Estate();
        estate.setClientStatus(ClientStatus.MEMBER);
        estate.setName(Random.genString(10));
        estate.setTaxPayerIDType(TaxPayerIDType.BUSINESS_TIN);
        estate.setTaxID(String.valueOf(new Timestamp(System.currentTimeMillis()).getTime()).substring(4));

        return estate;
    }
}
