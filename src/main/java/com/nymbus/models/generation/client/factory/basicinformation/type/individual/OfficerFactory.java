package com.nymbus.models.generation.client.factory.basicinformation.type.individual;

import com.nymbus.models.client.basicinformation.address.Country;
import com.nymbus.models.client.basicinformation.address.State;
import com.nymbus.models.client.basicinformation.type.TaxPayerIDType;
import com.nymbus.models.client.basicinformation.type.individual.IndividualType;
import com.nymbus.models.client.basicinformation.type.individual.Officer;
import com.nymbus.models.client.details.type.ClientStatus;
import com.nymbus.models.client.other.document.IDType;
import com.nymbus.util.Random;

import java.sql.Timestamp;

public class OfficerFactory implements IndividualTypeFactory {
    @Override
    public IndividualType getIndividualType() {
        Officer officer = new Officer();
        officer.setClientStatus(ClientStatus.MEMBER);
        officer.setFirstName(Random.genString(10));
        officer.setMiddleName(Random.genString(10));
        officer.setLastName(Random.genString(10));
        officer.setTaxPayerIDType(TaxPayerIDType.INDIVIDUAL_SSN);
        officer.setTaxID(String.valueOf(new Timestamp(System.currentTimeMillis()).getTime()).substring(4));
        officer.setBirthDate("01/01/1990");
        officer.setIdType(IDType.PASSPORT);
        officer.setIdNumber(String.valueOf(Random.genInt(1_100_000, 9_999_999)));
        officer.setIssuedBy(State.ALABAMA);
        officer.setCountry(Country.UNITED_STATES);
        officer.setExpirationDate("01/01/2050");

        return officer;
    }
}
