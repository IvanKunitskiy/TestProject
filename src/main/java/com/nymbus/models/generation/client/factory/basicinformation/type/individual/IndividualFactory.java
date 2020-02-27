package com.nymbus.models.generation.client.factory.basicinformation.type.individual;

import com.nymbus.models.client.basicinformation.address.Country;
import com.nymbus.models.client.basicinformation.address.State;
import com.nymbus.models.client.basicinformation.type.TaxPayerIDType;
import com.nymbus.models.client.basicinformation.type.individual.Individual;
import com.nymbus.models.client.basicinformation.type.individual.IndividualType;
import com.nymbus.models.client.details.type.ClientStatus;
import com.nymbus.models.client.other.document.IDType;
import com.nymbus.util.Random;

import java.sql.Timestamp;

public class IndividualFactory implements IndividualTypeFactory {
    @Override
    public IndividualType getIndividualType() {
        Individual individual = new Individual();
        individual.setClientStatus(ClientStatus.MEMBER);
        individual.setFirstName(Random.genString(10));
        individual.setMiddleName(Random.genString(10));
        individual.setLastName(Random.genString(10));
        individual.setTaxPayerIDType(TaxPayerIDType.INDIVIDUAL_SSN);
        individual.setTaxID(String.valueOf(new Timestamp(System.currentTimeMillis()).getTime()).substring(4));
        individual.setBirthDate("01/01/1990");
        individual.setIdType(IDType.PASSPORT);
        individual.setIdNumber(String.valueOf(Random.genInt(1_100_000, 9_999_999)));
        individual.setIssuedBy(State.ALABAMA);
        individual.setCountry(Country.UNITED_STATES);
        individual.setExpirationDate("01/01/2050");

        return individual;
    }
}
