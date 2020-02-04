package com.nymbus.pages.teller;

import com.nymbus.base.BasePage;
import com.nymbus.locator.Locator;
import com.nymbus.locator.XPath;
import io.qameta.allure.Step;

public class TellerPage extends BasePage {

    private Locator modalWindow = new XPath("//div[@class='modal-content']");
    private Locator cashDrawerName = new XPath("//div[@name='cashDrawerTemplate']/a/span/span");

    @Step("Wait 'Proof Date Login' modal window")
    public void waitModalWindow(){
        waitForElementVisibility(modalWindow);
        waitForElementClickable(modalWindow);
    }

    @Step("Get 'Cash Drawer' name")
    public String getCashDrawerName(){
        waitForElementVisibility(cashDrawerName);
        return getElementText(cashDrawerName).trim();
    }

}
