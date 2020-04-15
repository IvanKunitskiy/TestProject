package com.nymbus.pages.clients.transfers;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class ViewTransferPage extends PageTools {
    private By fromAccount = By.xpath("//div[@data-test-id='field-accountid']/a/span/span");
    private By toAccount = By.xpath("//div[@data-test-id='field-accountid2']/div/div/span");
    private By highBalance = By.xpath("//input[@id='transferthreshold']");
    private By maxAmountToTransfer = By.xpath("//input[@id='transferamount']");
    private By transferCharge = By.xpath("//input[@id='transfercharge']");
    private By editButton = By.xpath("//button//span[contains(text(), 'Edit')]");
    private By deleteButton = By.xpath("//button//span[contains(text(), 'Delete')]");


    @Step("Click 'Delete' button")
    public void clickDeleteButton() {
        waitForElementClickable(deleteButton);
        waitForElementVisibility(deleteButton);
        click(deleteButton);
    }

    @Step("Click 'Edit' button")
    public void clickEditButton() {
        waitForElementClickable(editButton);
        waitForElementVisibility(editButton);
        click(editButton);
    }

    @Step("Get 'From Account' value in transfer view mode")
    public String getFromAccount() {
        waitForElementVisibility(fromAccount);
        return getElementAttributeValue("textContent", fromAccount);
    }

    @Step("Get 'To Account' value in transfer view mode")
    public String getToAccount() {
        waitForElementVisibility(toAccount);
        return getElementAttributeValue("textContent", toAccount);
    }

    @Step("Get 'High Balance' value in transfer view mode")
    public String getHighBalance() {
        waitForElementVisibility(highBalance);
        return getDisabledElementAttributeValue("value", highBalance).replaceAll("[^0-9]", "");
    }

    @Step("Get 'Max Amount To Transfer' value in transfer view mode")
    public String getMaxAmountToTransfer() {
        waitForElementVisibility(maxAmountToTransfer);
        return getDisabledElementAttributeValue("value", maxAmountToTransfer).replaceAll("[^0-9]", "");
    }

    @Step("Get 'Transfer Charge' value in transfer view mode")
    public String getTransferCharge() {
        waitForElementVisibility(transferCharge);
        return getDisabledElementAttributeValue("value", transferCharge).replaceAll("[^0-9]", "");
    }

}
