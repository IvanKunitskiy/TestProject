package com.nymbus.pages.modalwindow;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class CashInOutModalWindowPage extends PageTools {

    private By modalWindow = By.xpath("//div[@class='modal-content']//div[@name='cashDenominationForm']");
    private By hundredsDenomination = By.xpath("//td[text()='Hundreds']/following-sibling::td//input");
    private By fiftiesDenomination = By.xpath("//td[text()='Fifties']/following-sibling::td//input");
    private By inputHundreds = By.xpath("//input[@name='onehundredsloose']");
    private By inputFifties = By.xpath("//input[@name='fiftiesloose']");
    private By inputHundredsInventory = By.xpath("//div[@class='modal-content']//*[@class='cashDenominationTableBody']//tr[1]//td[2]//input");
    private By cashMachineRadioButton = By.xpath("//input[@id='cashMachine']");
    private By cashDrawerRadioButton = By.xpath("//label[contains(string(),'Cash Drawer')]");

    /**
     * Action buttons
     */
    private By okButton = By.xpath("//div[@class='modal-content']//button[@ng-click='saveCashDenomination()']");
    private By cancelButton = By.xpath("//div[@class='modal-content']//button[@ng-click='closeWithoutSaving()']");

    @Step("Wait for modal window visibility")
    public void waitForModalWindowVisibility() {
        waitForElementVisibility(modalWindow);
    }

    @Step("Wait for modal window visibility")
    public void waitForModalWindowInVisibility() {
        waitForElementInvisibility(modalWindow);
    }



    @Step("Set {0} in hundreds amount")
    public void typeHundredsAmountValue(String value) {
        shouldNotBeEmpty(hundredsDenomination);
        waitForElementClickable(inputHundreds);
        jsType(value, inputHundreds);
        jsRiseOnchange(inputHundreds);
    }

    @Step("Set {0} in fifties amount")
    public void typeFiftiesAmountValue(String value) {
        shouldNotBeEmpty(fiftiesDenomination);
        waitForElementClickable(inputFifties);
        jsType(value, inputFifties);
        jsRiseOnchange(inputFifties);
    }

    @Step("Get hundreds inventory value")
    public String getHundredsInventorValue() {
        waitForElementVisibility(inputHundredsInventory);
        return getElementText(inputHundredsInventory).replaceAll("[^0-9.]", "");
    }

    @Step("Click 'Ok' button")
    public void clickOKButton() {
        waitForElementVisibility(okButton);
        jsClick(okButton);
    }

    @Step("Click 'Cancel' button")
    public void clickCancelButton() {
        waitForElementVisibility(cancelButton);
        jsClick(cancelButton);
    }

    @Step("Check if 'Cash Machine' radio button checked")
    public boolean isCashMachineRadioButtonChecked(){
        return isElementChecked(cashMachineRadioButton);
    }

    @Step("Click 'Cash Drawer' radio button")
    public void clickCashDrawerRadioButton(){
        waitForElementVisibility(cashDrawerRadioButton);
        click(cashDrawerRadioButton);
    }
}