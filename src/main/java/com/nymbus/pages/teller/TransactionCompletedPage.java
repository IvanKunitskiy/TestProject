package com.nymbus.pages.teller;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class TransactionCompletedPage extends PageTools {
    private By modalWindow = By.xpath("//div[div[@id='printReceipt']]");
    private By closeButton = By.xpath("//button[text()='Close']");

    @Step("Wait 'Verify Conductor' modal window")
    public void waitModalWindow() {
        waitForElementVisibility(modalWindow);
        waitForElementClickable(modalWindow);
    }

    @Step("Click on 'Close' button")
    public void clickCloseButton() {
        waitForElementClickable(closeButton);
        click(closeButton);
    }
}
