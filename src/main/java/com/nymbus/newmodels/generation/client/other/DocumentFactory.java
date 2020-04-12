package com.nymbus.newmodels.generation.client.other;

import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.client.basicinformation.address.Country;
import com.nymbus.newmodels.client.basicinformation.address.State;
import com.nymbus.newmodels.client.other.File;
import com.nymbus.newmodels.client.other.document.CompanyID;
import com.nymbus.newmodels.client.other.document.IDType;
import com.nymbus.util.Random;

public class DocumentFactory {
    public CompanyID getCompanyIDDocument() {
        CompanyID document = new CompanyID();

        document.setIdType(IDType.COMPANY_ID);
        document.setFile(File.CLIENT_DOCUMENT_PNG);
        document.setIdNumber(Random.genString(10));
        document.setIssuedBy(State.ALABAMA);
        document.setCountry(Country.UNITED_STATES);
        document.setIssueDate(DateTime.getYesterdayDate("MM/dd/yyyy"));
        document.setExpirationDate(DateTime.getTomorrowDate("MM/dd/yyyy"));

        return document;
    }
}
