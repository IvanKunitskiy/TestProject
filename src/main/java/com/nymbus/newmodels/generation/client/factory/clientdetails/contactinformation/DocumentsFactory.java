package com.nymbus.newmodels.generation.client.factory.clientdetails.contactinformation;

import com.nymbus.core.utils.Generator;
import com.nymbus.newmodels.client.basicinformation.address.Country;
import com.nymbus.newmodels.client.basicinformation.address.State;
import com.nymbus.newmodels.client.clientdetails.contactinformation.documents.Document;
import com.nymbus.newmodels.client.other.document.IDType;

public class DocumentsFactory {
    public Document getPassport() {
        Document document = new Document();

        document.setIdType(IDType.PASSPORT);
        document.setIdNumber("C" + Generator.genInt(11111111, 99999999));
        document.setIssuedBy(State.ALABAMA);
        document.setCountry(Country.UNITED_STATES);
        document.setIssueDate("11/11/2016");
        document.setExpirationDate("11/11/2030");

        return document;
    }

    public Document getStateDriverLicense() {
        Document stateDriverLicense = new Document();

        stateDriverLicense.setIdType(IDType.STATE_DRIVERS_LICENSE);
        stateDriverLicense.setIdNumber("C" + Generator.genInt(11111111, 99999999));
        stateDriverLicense.setIssuedBy(State.ALABAMA);
        stateDriverLicense.setCountry(Country.UNITED_STATES);
        stateDriverLicense.setIssueDate("11/11/2016");
        stateDriverLicense.setExpirationDate("11/11/2030");

        return stateDriverLicense;
    }
}
