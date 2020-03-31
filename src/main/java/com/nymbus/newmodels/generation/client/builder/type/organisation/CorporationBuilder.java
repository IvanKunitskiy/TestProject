package com.nymbus.newmodels.generation.client.builder.type.organisation;

import com.nymbus.newmodels.client.other.document.Document;
import com.nymbus.newmodels.client.other.document.IDType;
import com.nymbus.newmodels.generation.client.factory.basicinformation.type.organisation.CorporationFactory;
import com.nymbus.newmodels.generation.client.factory.basicinformation.type.organisation.OrganisationTypeFactory;
import com.nymbus.newmodels.generation.client.factory.clientdetails.type.OrganisationClientDetailsFactory;
import com.nymbus.newmodels.generation.client.factory.clientdocuments.OrganisationDocumentsFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CorporationBuilder extends OrganisationTypeBuilder {
    private OrganisationTypeFactory organisationTypeFactory = new CorporationFactory();
    private OrganisationClientDetailsFactory organisationClientDetailsFactory = new OrganisationClientDetailsFactory();
    private OrganisationDocumentsFactory organisationDocumentsFactory = new OrganisationDocumentsFactory();


    @Override
    public void buildOrganisationType() {
        organisationClient.setOrganisationType(organisationTypeFactory.getOrganisationType());
    }

    @Override
    public void buildOrganisationClientDetails() {
        organisationClient.setOrganisationClientDetails(organisationClientDetailsFactory.getOrganisationClientDetails());
    }

    @Override
    public void buildOrganisationClientDocuments(Map<IDType, Integer> documents) {
        List<Document> documentList = new ArrayList<Document>();

        for (Map.Entry<IDType, Integer> entry : documents.entrySet()) {
            switch (entry.getKey()) {
                case COMPANY_ID:
                    addCompanyID(entry.getValue(), documentList);
                    break;
                case LOGO:
                    addLogo(entry.getValue(), documentList);
                    break;
                default:
                    break;
            }
        }
        organisationClient.setDocuments(documentList);
    }

    private void addCompanyID(int count, List<Document> source) {
        for (int i = 0; i < count; i++) {
            source.add(organisationDocumentsFactory.getCompanyID());
        }
    }

    private void addLogo(int count, List<Document> source) {
        for (int i = 0; i < count; i++) {
            source.add(organisationDocumentsFactory.getLogo());
        }
    }
}