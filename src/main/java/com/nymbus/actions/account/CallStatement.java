package com.nymbus.actions.account;

import com.nymbus.models.client.Client;
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
}
