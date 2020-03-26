package com.nymbus.newmodels.generation.client.factory.basicinformation.type.individual;

import com.nymbus.newmodels.client.basicinformation.address.Country;
import com.nymbus.newmodels.client.basicinformation.address.State;
import com.nymbus.newmodels.client.basicinformation.type.TaxPayerIDType;
import com.nymbus.newmodels.client.basicinformation.type.individual.Director;
import com.nymbus.newmodels.client.basicinformation.type.individual.IndividualType;
import com.nymbus.newmodels.client.clientdetails.type.ClientStatus;
import com.nymbus.newmodels.client.other.document.IDType;
import com.nymbus.util.Random;

import java.sql.Timestamp;

public class DirectorFactory implements IndividualTypeFactory {
    @Override
    public IndividualType getIndividualType() {
        Director director = new Director();
        director.setClientStatus(ClientStatus.MEMBER);
        director.setFirstName(Random.genString(10));
        director.setMiddleName(Random.genString(10));
        director.setLastName(Random.genString(10));
        director.setTaxPayerIDType(TaxPayerIDType.INDIVIDUAL_SSN);
        director.setTaxID(String.valueOf(new Timestamp(System.currentTimeMillis()).getTime()).substring(4));
        director.setBirthDate("01/01/1990");
        director.setIdType(IDType.PASSPORT);
        director.setIdNumber(String.valueOf(Random.genInt(1_100_000, 9_999_999)));
        director.setIssuedBy(State.ALABAMA);
        director.setCountry(Country.UNITED_STATES);
        director.setExpirationDate("01/01/2050");

        return director;
    }
}
