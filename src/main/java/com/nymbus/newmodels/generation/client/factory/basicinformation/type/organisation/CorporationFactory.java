package com.nymbus.newmodels.generation.client.factory.basicinformation.type.organisation;

import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.FinancialInstitutionType;
import com.nymbus.newmodels.client.basicinformation.type.TaxPayerIDType;
import com.nymbus.newmodels.client.basicinformation.type.individual.Individual;
import com.nymbus.newmodels.client.basicinformation.type.organisation.Corporation;
import com.nymbus.newmodels.client.basicinformation.type.organisation.OrganisationType;
import com.nymbus.newmodels.client.clientdetails.type.ClientStatus;
import com.nymbus.util.Random;

import java.sql.Timestamp;

public class CorporationFactory implements OrganisationTypeFactory {
    @Override
    public OrganisationType getOrganisationType() {
        Corporation corporation = new Corporation();
        setCorporationStatus(corporation);
        corporation.setName(Random.genString(10));
        corporation.setTaxPayerIDType(TaxPayerIDType.BUSINESS_TIN);
        corporation.setTaxID(String.valueOf(new Timestamp(System.currentTimeMillis()).getTime()).substring(4));
        return corporation;
    }

    private void setCorporationStatus(Corporation client) {
        if (Constants.INSTITUTION_TYPE.equals(FinancialInstitutionType.BANK)) {
            client.setClientStatus(ClientStatus.CUSTOMER);
        }
        else {
            client.setClientStatus(ClientStatus.MEMBER);
        }
    }
}
