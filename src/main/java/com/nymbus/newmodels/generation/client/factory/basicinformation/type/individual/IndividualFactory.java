package com.nymbus.newmodels.generation.client.factory.basicinformation.type.individual;

import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.FinancialInstitutionType;
import com.nymbus.newmodels.client.basicinformation.address.Country;
import com.nymbus.newmodels.client.basicinformation.address.State;
import com.nymbus.newmodels.client.basicinformation.type.TaxPayerIDType;
import com.nymbus.newmodels.client.basicinformation.type.individual.Individual;
import com.nymbus.newmodels.client.basicinformation.type.individual.IndividualType;
import com.nymbus.newmodels.client.clientdetails.type.ClientStatus;
import com.nymbus.newmodels.client.other.document.IDType;
import com.nymbus.util.Random;

import java.sql.Timestamp;

public class IndividualFactory implements IndividualTypeFactory {
    @Override
    public IndividualType getIndividualType() {
        Individual individual = new Individual();
        setIndividualStatus(individual);
        individual.setFirstName(Random.genString(3) + Constants.FIRST_NAME);
        individual.setMiddleName(Random.genString(10));
        individual.setLastName(Random.genString(3) + Constants.LAST_NAME);
        individual.setTaxPayerIDType(TaxPayerIDType.INDIVIDUAL_SSN);
        individual.setTaxID(String.valueOf(new Timestamp(System.currentTimeMillis()).getTime()).substring(4));
        individual.setBirthDate("01/01/1990");
//        individual.setIdType(IDType.PASSPORT);
//        individual.setIdNumber(String.valueOf(Random.genInt(1_100_000, 9_999_999)));
//        individual.setIssuedBy(State.ALABAMA);
//        individual.setCountry(Country.UNITED_STATES);
//        individual.setExpirationDate("01/01/2050");

        return individual;
    }

    private void setIndividualStatus(Individual client) {
        if (Constants.INSTITUTION_TYPE.equals(FinancialInstitutionType.BANK)) {
            client.setClientStatus(ClientStatus.CUSTOMER);
        }
        else {
            client.setClientStatus(ClientStatus.MEMBER);
        }
    }
}