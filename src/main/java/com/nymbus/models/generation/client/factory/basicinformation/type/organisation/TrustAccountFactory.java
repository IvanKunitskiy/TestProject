package com.nymbus.models.generation.client.factory.basicinformation.type.organisation;

import com.nymbus.models.client.basicinformation.type.TaxPayerIDType;
import com.nymbus.models.client.basicinformation.type.organisation.OrganisationType;
import com.nymbus.models.client.basicinformation.type.organisation.TrustAccount;
import com.nymbus.models.client.details.type.ClientStatus;
import com.nymbus.util.Random;

import java.sql.Timestamp;

public class TrustAccountFactory implements OrganisationTypeFactory {
    @Override
    public OrganisationType getOrganisationType() {
        TrustAccount trustAccount = new TrustAccount();
        trustAccount.setClientStatus(ClientStatus.MEMBER);
        trustAccount.setName(Random.genString(10));
        trustAccount.setTaxPayerIDType(TaxPayerIDType.BUSINESS_TIN);
        trustAccount.setTaxID(String.valueOf(new Timestamp(System.currentTimeMillis()).getTime()).substring(4));

        return trustAccount;
    }
}
