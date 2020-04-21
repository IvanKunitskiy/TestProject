package com.nymbus.pages.modalwindow;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class CashInModalWindowPage extends PageTools {

    private By inputHundreds = By.xpath("//input[@name='onehundredsloose']");
    private By inputFifties = By.xpath("//input[@name='fiftiesloose']");
    private By inputHundredsInventory = By.xpath("//div[@class='modal-content']//*[@class='cashDenominationTableBody']//tr[1]//td[2]//input");

    /**
     * Action buttons
     */
    private By okButton = By.xpath("//div[@class='modal-content']//button[@ng-click='saveCashDenomination()']");
    private By cancelButton = By.xpath("//div[@class='modal-content']//button[@ng-click='closeWithoutSaving()']");

    @Step("Set {0} in hundreds amount")
    public void typeHundredsAmountValue(String value) {
        waitForElementClickable(inputHundreds);
        type(value, inputHundreds);
    }

    @Step("Set {0} in fifties amount")
    public void typeFiftiesAmountValue(String value) {
        waitForElementClickable(inputFifties);
        type(value, inputFifties);
    }

    @Step("Get hundreds inventory value")
    public String getHundredsInventorValue() {
        waitForElementVisibility(inputHundredsInventory);
        return getElementText(inputHundredsInventory).replaceAll("[^0-9.]", "");
    }

    @Step("Click 'Ok' button")
    public void clickOKButton() {
        waitForElementVisibility(okButton);
        click(okButton);
    }

    @Step("Click 'Cancel' button")
    public void clickCancelButton() {
        waitForElementVisibility(cancelButton);
        click(cancelButton);
    }
}