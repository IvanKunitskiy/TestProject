package com.nymbus.pages.accounts;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class BalanceInquiryModalPage extends PageTools {
    private By printButton = By.xpath("//button[span[contains(text(), 'Print')]]");
    private By closeButton = By.xpath("//button[contains(text(), 'Close')]");
    private By closeModalButton = By.xpath("//button[@type='button']/span[contains(text(), '×')]");

    @Step("Click the 'Print' button")
    public void clickPrintButton() {
        waitForElementClickable(printButton);
        click(printButton);
    }

    @Step("Click the 'Close' button")
    public void clickCloseButton() {
        waitForElementClickable(closeButton);
        click(closeButton);
    }

    @Step("Click the 'Close modal' button")
    public void clickCloseModalButton() {
        waitForElementClickable(closeModalButton);
        click(closeModalButton);
    }
}
