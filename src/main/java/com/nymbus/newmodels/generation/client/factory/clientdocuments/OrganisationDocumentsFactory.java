package com.nymbus.newmodels.generation.client.factory.clientdocuments;

import com.nymbus.newmodels.client.basicinformation.address.Country;
import com.nymbus.newmodels.client.basicinformation.address.State;
import com.nymbus.newmodels.client.other.File;
import com.nymbus.newmodels.client.other.document.CompanyID;
import com.nymbus.newmodels.client.other.document.IDType;
import com.nymbus.util.Random;

public class OrganisationDocumentsFactory {
    public CompanyID getCompanyID() {
        CompanyID companyID = new CompanyID();
        companyID.setFile(File.CLIENT_DOCUMENT_PNG);
        companyID.setIdType(IDType.COMPANY_ID);
        companyID.setIdNumber(String.valueOf(Random.genInt(100000, 999999)));
        companyID.setIssuedBy(State.ALABAMA);
        companyID.setCountry(Country.UNITED_STATES);
        companyID.setIssueDate("01/01/2000");
        companyID.setExpirationDate("01/01/2050");

        return  companyID;
    }
}
