package com.nymbus.pages.teller;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class TellerModalPage extends PageTools {
    private By modalWindow = By.xpath("//div[@class='modal-content']");
    private By cashDrawerName = By.xpath("//div[@name='cashDrawerTemplate']/a/span/span");
    private By enterButton = By.xpath("//div[@class='modal-content']//button[contains(@class, 'btn-primary')]");

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

    @Step("Click 'Enter' button")
    public void clickEnterButton() {
        waitForElementVisibility(enterButton);
        click(enterButton);
    }

    @Step("Wait for modal disappear")
    public void waitForModalInvisibility() {
        waitForElementInvisibility(modalWindow);
    }
}
