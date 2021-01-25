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
    private By editButton = By.xpath("//button//span[contains(text(), 'Edit')]/ancestor::button");
    private By deleteButton = By.xpath("//button//span[contains(text(), 'Delete')]/ancestor::button");
    private By creationDate = By.xpath("//input[@data-test-id='field-creationdate']");
    private By advanceDaysFromDueDate = By.xpath("//input[@data-test-id='field-advancedaystomakepayment']");
    private By eftChargeCode = By.xpath("//div[@name='transferchargecode']/a/span/span");
    private By frequency = By.xpath("////div[@name='frequencytransfer']/a/span/span");
    private By amount = By.xpath("//input[@data-test-id='field-transferamount']");

    @Step("Check if 'Edit' button enabled")
    public boolean isEditButtonEnabled() {
        return getWebElement(editButton).isEnabled();
    }

    @Step("Check if 'Delete' button enabled")
    public boolean isDeleteButtonEnabled() {
        return getWebElement(deleteButton).isEnabled();
    }

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

    @Step("Get 'Creation Date' value in transfer view mode")
    public String getCreationDate() {
        waitForElementVisibility(creationDate);
        return getDisabledElementAttributeValue("value", creationDate);
    }

    @Step("Get 'Advance days from due date' value in transfer view mode")
    public String getAdvanceDaysFromDueDate() {
        waitForElementVisibility(advanceDaysFromDueDate);
        return getDisabledElementAttributeValue("value", advanceDaysFromDueDate);
    }

    @Step("Get 'EFT charge code' value in transfer view mode")
    public String getEftChargeCode() {
        waitForElementVisibility(eftChargeCode);
        return getElementText(eftChargeCode);
    }

    @Step("Get 'Frequency' value in transfer view mode")
    public String getFrequency() {
        waitForElementVisibility(frequency);
        return getElementText(frequency);
    }

    @Step("Get 'Amount' value in transfer view mode")
    public String getAmount() {
        waitForElementVisibility(amount);
        return getElementAttributeValue("value", amount);
    }
}
