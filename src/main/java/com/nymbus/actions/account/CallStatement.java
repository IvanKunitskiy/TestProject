package com.nymbus.actions.account;

import com.codeborne.pdftest.PDF;
import com.nymbus.actions.Actions;
import com.nymbus.models.client.Client;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.pages.Pages;
import org.testng.Assert;

import java.util.List;

public class CallStatement {

    public void setAddress(Client client) {
        Pages.callStatementModalPage().clickAddressSelectorButton();
        List<String> listOfAddress = Pages.callStatementModalPage().getAddressList();
        Assert.assertTrue(listOfAddress.size() > 0, "There are no options available");
        Pages.callStatementModalPage().clickAddressSelectorOption(client.getAddress().getAddress());
    }

    public void setAddress(IndividualClient client) {
        Pages.callStatementModalPage().clickAddressSelectorButton();
        List<String> listOfAddress = Pages.callStatementModalPage().getAddressList();
        Assert.assertTrue(listOfAddress.size() > 0, "There are no options available");
        Pages.callStatementModalPage().clickAddressSelectorOption(client.getIndividualType().getAddresses().get(0).getAddress());
    }

    public PDF getCallStatementPdf() {
        Pages.transactionsPage().clickTheCallStatementButton();
        Actions.mainActions().switchToTab(1);
        Pages.accountStatementPage().waitForLoadingSpinnerInvisibility();
        return new PDF(Pages.accountStatementPage().downloadCallStatementPdf());
    }
}
