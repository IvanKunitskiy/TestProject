package com.nymbus.models.generation.client.factory.basicinformation.type.organisation;

import com.nymbus.models.client.basicinformation.type.TaxPayerIDType;
import com.nymbus.models.client.basicinformation.type.organisation.OrganisationType;
import com.nymbus.models.client.basicinformation.type.organisation.Partnership;
import com.nymbus.models.client.clientdetails.type.ClientStatus;
import com.nymbus.util.Random;

import java.sql.Timestamp;

public class PartnershipFactory implements OrganisationTypeFactory {
    @Override
    public OrganisationType getOrganisationType() {
        Partnership partnership = new Partnership();
        partnership.setClientStatus(ClientStatus.MEMBER);
        partnership.setName(Random.genString(10));
        partnership.setTaxPayerIDType(TaxPayerIDType.BUSINESS_TIN);
        partnership.setTaxID(String.valueOf(new Timestamp(System.currentTimeMillis()).getTime()).substring(4));

        return partnership;
    }
}
