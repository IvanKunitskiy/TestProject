package com.nymbus.pages.clients.transfers;

import com.codeborne.selenide.Selenide;
import com.nymbus.core.base.PageTools;
import com.nymbus.core.utils.SelenideTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.util.List;

public class NewTransferPage extends PageTools {

    private By saveButton = By.xpath("//button[contains(text(), 'Save')]");

    private By transferTypeSelectorButton = By.xpath("//div[@data-test-id='field-transfertype']");
    private By transferTypeList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private By transferTypeSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private By fromAccountSelectorButton = By.xpath("//div[@data-test-id='field-accountid']");
    private By fromAccountList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private By fromAccountSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private By toAccountSelectorButton = By.xpath("//div[@data-test-id='field-accountid2']");
    private By toAccountList = By.xpath("//div[contains(@class, 'ui-select-choices-row ng-scope')]/div/div[contains(@class, 'ng-scope')]");
    private By toAccountSelectorOption = By.xpath("//div[contains(@class, 'ui-select-choices-row ng-scope')]/div/div[contains(text(), '%s')]");
    private By highBalance = By.xpath("//input[@id='transferthreshold']");
    private By maxAmountToTransfer = By.xpath("//input[@id='transferamount']");
    private By amount = By.xpath("//input[@id='field-transferamount']");
    private By transferCharge = By.xpath("//input[@id='transfercharge']");

    private By frequencySelectorButton = By.xpath("//div[@data-test-id='field-frequencytransfer']");
    private By frequencyList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private By frequencySelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    @Step("Type value to the 'Max Amount To Transfer' field")
    public void setMaxAmount(String value) {
        waitForElementClickable(maxAmountToTransfer, value);
        type(value, maxAmountToTransfer);
    }

    @Step("Type value to the 'Amount' field")
    public void setAmount(String value) {
        waitForElementClickable(amount, value);
        type(value, amount);
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

    @Step("Click the 'Transfer Type' option")
    public void clickTransferTypeSelectorOption(String transferTypeOption) {
        waitForElementVisibility(transferTypeSelectorOption, transferTypeOption);
        waitForElementClickable(transferTypeSelectorOption, transferTypeOption);
        click(transferTypeSelectorOption, transferTypeOption);
    }

    @Step("Returning list of 'Transfer Type' options")
    public List<String> getTransferTypeList() {
        waitForElementVisibility(transferTypeList);
        waitForElementClickable(transferTypeList);
        return getElementsText(transferTypeList);
    }

    @Step("Click the 'Transfer Type' selector button")
    public void clickTransferTypeSelectorButton() {
        waitForElementClickable(transferTypeSelectorButton);
        click(transferTypeSelectorButton);
    }

    @Step("Click the 'From Account' option")
    public void clickFromAccountSelectorOption(String transferTypeOption) {
        waitForElementVisibility(fromAccountSelectorOption, transferTypeOption);
        waitForElementClickable(fromAccountSelectorOption, transferTypeOption);
        click(fromAccountSelectorOption, transferTypeOption);
    }

    @Step("Returning list of 'From Account' options")
    public List<String> getFromAccountList() {
        waitForElementVisibility(fromAccountList);
        waitForElementClickable(fromAccountList);
        return getElementsText(fromAccountList);
    }

    @Step("Click the 'From Account' selector button")
    public void clickFromAccountSelectorButton() {
        waitForElementClickable(fromAccountSelectorButton);
        click(fromAccountSelectorButton);
    }

    @Step("Click the 'To Account' option")
    public void clickToAccountSelectorOption(String transferTypeOption) {
        waitForElementVisibility(toAccountSelectorOption, transferTypeOption);
        waitForElementClickable(toAccountSelectorOption, transferTypeOption);
        click(toAccountSelectorOption, transferTypeOption);
    }

    @Step("Returning list of 'To Account' options")
    public List<String> getToAccountList() {
        waitForElementVisibility(toAccountList);
        waitForElementClickable(toAccountList);
        return getElementsText(toAccountList);
    }

    @Step("Click the 'To Account' selector button")
    public void clickToAccountSelectorButton() {
        waitForElementClickable(toAccountSelectorButton);
        click(toAccountSelectorButton);
    }

    @Step("Click the 'Frequency' option")
    public void clickFrequencySelectorOption(String frequencyOption) {
        waitForElementVisibility(frequencySelectorOption, frequencyOption);
        waitForElementClickable(frequencySelectorOption, frequencyOption);
        click(frequencySelectorOption, frequencyOption);
    }

    @Step("Returning list of 'Frequency' options")
    public List<String> getFrequencyList() {
        waitForElementVisibility(frequencyList);
        waitForElementClickable(frequencyList);
        return getElementsText(frequencyList);
    }

    @Step("Click the 'Frequency' selector button")
    public void clickFrequencySelectorButton() {
        waitForElementClickable(frequencySelectorButton);
        click(frequencySelectorButton);
    }
}