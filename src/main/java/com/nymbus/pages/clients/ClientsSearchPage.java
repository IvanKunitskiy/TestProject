package com.nymbus.pages.clients;

import com.nymbus.base.BasePage;
import com.nymbus.locator.Locator;
import com.nymbus.locator.XPath;
import io.qameta.allure.Step;

public class ClientsSearchPage extends BasePage {

    private Locator addNewClientButton = new XPath("//a[@*='action-addNewCustomer']");


    @Step("Wait for 'Add new client' button")
    public void waitForAddNewClientButton(){
        waitForElementVisibility(addNewClientButton);
    }

    @Step("Click 'Add new client' button")
    public void clickAddNewClient(){
        waitForElementVisibility(addNewClientButton);
        waitForElementClickable(addNewClientButton);
        click(addNewClientButton);
    }

}
