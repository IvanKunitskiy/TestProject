package com.nymbus.models.generation.client.factory.basicinformation.type.organisation;

import com.nymbus.models.client.basicinformation.type.TaxPayerIDType;
import com.nymbus.models.client.basicinformation.type.organisation.OrganisationType;
import com.nymbus.models.client.basicinformation.type.organisation.StateCountryMunicipality;
import com.nymbus.models.client.details.type.ClientStatus;
import com.nymbus.util.Random;

import java.sql.Timestamp;

public class StateCountryMunicipalityFactory implements OrganisationTypeFactory {
    @Override
    public OrganisationType getOrganisationType() {
        StateCountryMunicipality stateCountryMunicipality = new StateCountryMunicipality();
        stateCountryMunicipality.setClientStatus(ClientStatus.MEMBER);
        stateCountryMunicipality.setName(Random.genString(10));
        stateCountryMunicipality.setTaxPayerIDType(TaxPayerIDType.BUSINESS_TIN);
        stateCountryMunicipality.setTaxID(String.valueOf(new Timestamp(System.currentTimeMillis()).getTime()).substring(4));

        return stateCountryMunicipality;
    }
}
