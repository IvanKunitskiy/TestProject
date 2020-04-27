package com.nymbus.newmodels.generation.client.factory.basicinformation.type.individual;

import com.nymbus.newmodels.client.basicinformation.address.Country;
import com.nymbus.newmodels.client.basicinformation.address.State;
import com.nymbus.newmodels.client.basicinformation.type.TaxPayerIDType;
import com.nymbus.newmodels.client.basicinformation.type.individual.Employee;
import com.nymbus.newmodels.client.basicinformation.type.individual.IndividualType;
import com.nymbus.newmodels.client.clientdetails.type.ClientStatus;
import com.nymbus.newmodels.client.other.document.IDType;
import com.nymbus.util.Random;

import java.sql.Timestamp;

public class EmployeeFactory implements IndividualTypeFactory {
    @Override
    public IndividualType getIndividualType() {
        Employee employee = new Employee();
        employee.setClientStatus(ClientStatus.MEMBER);
        employee.setFirstName(Random.genString(10));
        employee.setMiddleName(Random.genString(10));
        employee.setLastName(Random.genString(10));
        employee.setTaxPayerIDType(TaxPayerIDType.INDIVIDUAL_SSN);
        employee.setTaxID(String.valueOf(new Timestamp(System.currentTimeMillis()).getTime()).substring(4));
        employee.setBirthDate("01/01/1990");
//        employee.setIdType(IDType.PASSPORT);
//        employee.setIdNumber(String.valueOf(Random.genInt(1_100_000, 9_999_999)));
//        employee.setIssuedBy(State.ALABAMA);
//        employee.setCountry(Country.UNITED_STATES);
//        employee.setExpirationDate("01/01/2050");

        return employee;
    }
}
