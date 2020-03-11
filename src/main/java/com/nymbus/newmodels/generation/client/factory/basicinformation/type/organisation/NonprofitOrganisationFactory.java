package com.nymbus.newmodels.generation.client.factory.basicinformation.type.organisation;

import com.nymbus.newmodels.client.basicinformation.type.TaxPayerIDType;
import com.nymbus.newmodels.client.basicinformation.type.organisation.NonprofitOrganisation;
import com.nymbus.newmodels.client.basicinformation.type.organisation.OrganisationType;
import com.nymbus.newmodels.client.clientdetails.type.ClientStatus;
import com.nymbus.util.Random;

import java.sql.Timestamp;

public class NonprofitOrganisationFactory implements OrganisationTypeFactory {
    @Override
    public OrganisationType getOrganisationType() {
        NonprofitOrganisation nonprofitOrganisation = new NonprofitOrganisation();
        nonprofitOrganisation.setClientStatus(ClientStatus.MEMBER);
        nonprofitOrganisation.setName(Random.genString(10));
        nonprofitOrganisation.setTaxPayerIDType(TaxPayerIDType.BUSINESS_TIN);
        nonprofitOrganisation.setTaxID(String.valueOf(new Timestamp(System.currentTimeMillis()).getTime()).substring(4));

        return nonprofitOrganisation;
    }
}
