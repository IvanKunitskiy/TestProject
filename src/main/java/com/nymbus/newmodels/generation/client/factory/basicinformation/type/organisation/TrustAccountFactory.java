package com.nymbus.newmodels.generation.client.factory.basicinformation.type.organisation;

import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.FinancialInstitutionType;
import com.nymbus.newmodels.client.basicinformation.type.TaxPayerIDType;
import com.nymbus.newmodels.client.basicinformation.type.individual.Individual;
import com.nymbus.newmodels.client.basicinformation.type.organisation.OrganisationType;
import com.nymbus.newmodels.client.basicinformation.type.organisation.TrustAccount;
import com.nymbus.newmodels.client.clientdetails.type.ClientStatus;
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