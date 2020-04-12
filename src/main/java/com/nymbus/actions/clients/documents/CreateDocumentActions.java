package com.nymbus.actions.clients.documents;

import com.nymbus.core.utils.Functions;
import com.nymbus.newmodels.client.basicinformation.address.Country;
import com.nymbus.newmodels.client.basicinformation.address.State;
import com.nymbus.newmodels.client.other.document.CompanyID;
import com.nymbus.newmodels.client.other.document.IDType;
import com.nymbus.pages.Pages;
import org.testng.Assert;

import java.util.List;
import java.util.Random;

public class CreateDocumentActions {

    public void createNewCompanyIDDocument(CompanyID document) {
        Pages.documentsPage().clickAddNewDocumentButton();
        Pages.addNewDocumentPage().uploadNewDocument(Functions.getFilePathByName("clientDocument.png"));
        DocumentActions.createDocumentActions().setIDType(document);
        Pages.addNewDocumentPage().typeValueToIDNumberField(document.getIdNumber());
        DocumentActions.createDocumentActions().setIssuedBY(document);
        DocumentActions.createDocumentActions().setCountry(document);
        Pages.addNewDocumentPage().setIssueDateValue(document.getIssueDate());
        Pages.addNewDocumentPage().setExpirationDateValue(document.getExpirationDate());
        Pages.addNewDocumentPage().clickSaveChangesButton();

    }

    public void setIDType(CompanyID document) {
        Pages.addNewDocumentPage().clickIdTypeSelectorButton();
        List<String> listOfIDType = Pages.addNewDocumentPage().getIdTypeList();

        Assert.assertTrue(listOfIDType.size() > 0, "There are no options available");
        if (document.getIdType() == null) {
            document.setIdType(IDType.valueOf(listOfIDType.get(new Random().nextInt(listOfIDType.size())).trim()));
        }
        Pages.addNewDocumentPage().clickIdTypeSelectorOption(document.getIdType().getIdType());
    }

    public void setIssuedBY(CompanyID document) {
        Pages.addNewDocumentPage().clickIssuedBySelectorButton();
        List<String> listOfIssuedBy = Pages.addNewDocumentPage().getIssuedByList();

        Assert.assertTrue(listOfIssuedBy.size() > 0, "There are no options available");
        if (document.getIssuedBy() == null) {
            document.setIssuedBy(State.valueOf(listOfIssuedBy.get(new Random().nextInt(listOfIssuedBy.size())).trim()));
        }
        Pages.addNewDocumentPage().clickIssuedByOption(document.getIssuedBy().getState());
    }

    public void setCountry(CompanyID document) {
        Pages.addNewDocumentPage().clickCountrySelectorButton();
        List<String> listOfCountry = Pages.addNewDocumentPage().getCountryList();

        Assert.assertTrue(listOfCountry.size() > 0, "There are no options available");
        if (document.getCountry() == null) {
            document.setCountry(Country.valueOf(listOfCountry.get(new Random().nextInt(listOfCountry.size())).trim()));
        }
        Pages.addNewDocumentPage().clickDocumentCountryOption(document.getCountry().getCountry());
    }
}
