package com.nymbus.pages.clients.transfers;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.util.List;

public class EditTransferPage extends PageTools {

    private By saveButton = By.xpath("//button[contains(text(), 'Save')]");

    private By highBalance = By.xpath("//input[@id='transferthreshold']");
    private By maxAmountToTransfer = By.xpath("//input[@id='transferamount']");
    private By transferCharge = By.xpath("//input[@id='transfercharge']");
    private By advanceDaysFromDueDate = By.xpath("//input[@data-test-id='field-advancedaystomakepayment']");

    private By eftChargeCodeSelectorButton = By.xpath("//div[@data-test-id='field-transferchargecode']");
    private By eftChargeCodeList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private By eftChargeCodeSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    @Step("Type value to the 'Advance days from due date' field")
    public void setAdvanceDaysFromDueDate(String value) {
        waitForElementClickable(advanceDaysFromDueDate, value);
        type(value, advanceDaysFromDueDate);
    }

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

    @Step("Click the 'Eft Charge Code' option")
    public void clickEftChargeCodeSelectorOption(String eftChargeCodeOption) {
        waitForElementVisibility(eftChargeCodeSelectorOption, eftChargeCodeOption);
        waitForElementClickable(eftChargeCodeSelectorOption, eftChargeCodeOption);
        click(eftChargeCodeSelectorOption, eftChargeCodeOption);
    }

    @Step("Returning list of 'Eft Charge Code' options")
    public List<String> getEftChargeCodeList() {
        waitForElementVisibility(eftChargeCodeList);
        waitForElementClickable(eftChargeCodeList);
        return getElementsText(eftChargeCodeList);
    }

    @Step("Click the 'Eft Charge Code' selector button")
    public void clickEftChargeCodeSelectorButton() {
        waitForElementClickable(eftChargeCodeSelectorButton);
        click(eftChargeCodeSelectorButton);
    }
}
