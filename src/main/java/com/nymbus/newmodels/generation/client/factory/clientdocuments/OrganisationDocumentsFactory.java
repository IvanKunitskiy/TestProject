package com.nymbus.newmodels.generation.client.factory.clientdocuments;

import com.nymbus.newmodels.client.basicinformation.address.Country;
import com.nymbus.newmodels.client.basicinformation.address.State;
import com.nymbus.newmodels.client.other.File;
import com.nymbus.newmodels.client.other.document.BaseDocument;
import com.nymbus.newmodels.client.other.document.DocumentDetails;
import com.nymbus.newmodels.client.other.document.IDType;
import com.nymbus.util.Random;

public class OrganisationDocumentsFactory {
    public BaseDocument getCompanyID() {
        BaseDocument baseDocument = new BaseDocument();
        baseDocument.setFile(File.CLIENT_DOCUMENT_PNG);
        baseDocument.setIdType(IDType.COMPANY_ID);
        baseDocument.setDocumentDetails(getCompanyIdDetails());

        return  baseDocument;
    }

    public BaseDocument getLogo() {
        BaseDocument baseDocument = new BaseDocument();
        baseDocument.setFile(File.PROFILE_PHOTO_PNG);
        baseDocument.setIdType(IDType.LOGO);
        baseDocument.setDocumentDetails(getEmptyDetails());

        return  baseDocument;
    }

    public BaseDocument getSignature() {
        BaseDocument baseDocument = new BaseDocument();
        baseDocument.setFile(File.CLIENT_SIGNATURE_PNG);
        baseDocument.setIdType(IDType.SIGNATURE);
        baseDocument.setDocumentDetails(getEmptyDetails());

        return  baseDocument;
    }

    private DocumentDetails getCompanyIdDetails() {
        DocumentDetails documentDetails = new DocumentDetails();
        documentDetails.setIdNumber(String.valueOf(Random.genInt(100000, 999999)));
        documentDetails.setCountry(Country.UNITED_STATES);
        documentDetails.setIssuedBy(State.ALABAMA);
        documentDetails.setExpirationDate("01/01/2050");
        documentDetails.setIssueDate("01/01/2000");

        return documentDetails;
    }

    private DocumentDetails getEmptyDetails() {
        DocumentDetails documentDetails = new DocumentDetails();
        documentDetails.setIdNumber("");
        documentDetails.setCountry(Country.NONE);
        documentDetails.setIssuedBy(State.NONE);
        documentDetails.setExpirationDate("");
        documentDetails.setIssueDate("");

        return documentDetails;
    }
}
