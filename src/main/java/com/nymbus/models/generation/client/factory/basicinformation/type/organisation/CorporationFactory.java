package com.nymbus.models.generation.client.factory.basicinformation.type.organisation;

import com.nymbus.models.client.basicinformation.type.TaxPayerIDType;
import com.nymbus.models.client.basicinformation.type.organisation.Corporation;
import com.nymbus.models.client.basicinformation.type.organisation.OrganisationType;
import com.nymbus.models.client.clientdetails.type.ClientStatus;
import com.nymbus.util.Random;

import java.sql.Timestamp;

public class CorporationFactory implements OrganisationTypeFactory {
    @Override
    public OrganisationType getOrganisationType() {
        Corporation corporation = new Corporation();
        corporation.setClientStatus(ClientStatus.MEMBER);
        corporation.setName(Random.genString(10));
        corporation.setTaxPayerIDType(TaxPayerIDType.BUSINESS_TIN);
        corporation.setTaxID(String.valueOf(new Timestamp(System.currentTimeMillis()).getTime()).substring(4));

        return corporation;
    }
}
