package com.nymbus.pages.tellertotellertransfer;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class TellerToTellerPage extends PageTools {

    private By modalWindow = By.xpath("//div[@class='modal-content']");
    private By cashDrawerName = By.xpath("//div[@name='cashDrawerTemplate']/a/span/span");

    @Step("Wait 'Proof Date Login' modal window")
    public void waitModalWindow() {
        waitForElementVisibility(modalWindow);
        waitForElementClickable(modalWindow);
    }

    @Step("Get 'Cash Drawer' name")
    public String getCashDrawerName() {
        waitForElementVisibility(cashDrawerName);
        return getElementText(cashDrawerName).trim();
    }

}
