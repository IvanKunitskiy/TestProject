package com.nymbus.pages.clients;

import com.nymbus.base.BasePage;
import com.nymbus.locator.Locator;
import com.nymbus.locator.XPath;
import io.qameta.allure.Step;

public class ClientsPage extends BasePage {

    private Locator addNewClientButton = new XPath("//a[@*='action-addNewCustomer']");


    @Step("Wait for 'Add new client' button")
    public void waitForAddNewClientButton(){
        waitForElementVisibility(addNewClientButton);
    }

}
