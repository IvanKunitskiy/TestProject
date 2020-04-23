package com.nymbus.pages.clients.transfers;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class EditTransferPage extends PageTools {

    private By saveButton = By.xpath("//button[contains(text(), 'Save')]");

    private By highBalance = By.xpath("//input[@id='transferthreshold']");
    private By maxAmountToTransfer = By.xpath("//input[@id='transferamount']");
    private By transferCharge = By.xpath("//input[@id='transfercharge']");

    @Step("Type value to the 'Max Amount To Transfer' field")
    public void setMaxAmount(String value) {
        waitForElementClickable(maxAmountToTransfer, value);
        type(value, maxAmountToTransfer);
    }

    @Step("Type value to the 'Transfer Charge' field")
    public void setTransferCharge(String value) {
        waitForElementClickable(transferCharge, value);
        type(value, transferCharge);
    }

    @Step("Type value to the 'High Balance' field")
    public void setHighBalance(String value) {
        waitForElementClickable(highBalance, value);
        type(value, highBalance);
    }

    @Step("Wait for 'Save' button invisibility")
    public void waitForSaveButtonInvisibility() {
        waitForElementInvisibility(saveButton);
    }

    @Step("Click the 'Save' button")
    public void clickSaveButton() {
        waitForElementVisibility(saveButton);
        waitForElementClickable(saveButton);
        click(saveButton);
        waitForSaveButtonInvisibility();
    }
}
