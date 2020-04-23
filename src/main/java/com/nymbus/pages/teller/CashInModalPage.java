package com.nymbus.pages.teller;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class CashInModalPage extends PageTools {
    private By cashInModalWindow = By.xpath("//div[@class='modal-content']");
    private By okButton = By.xpath("//button[contains(text(), 'OK')]");

    @Step("Wait 'Cash In' modal window")
    public void waitCashInModalWindow() {
        waitForElementVisibility(cashInModalWindow);
        waitForElementClickable(cashInModalWindow);
    }

    @Step("Click 'OK' button")
    public void clickOkButton() {
        waitForElementVisibility(okButton);
        waitForElementClickable(okButton);
        click(okButton);
    }

    /**
    * Bills
     */
    private By hundredsItemCountField = By.xpath("//tr[@form='cashDenominationForm'][1]//td[3]//input");

    @Step("Type 'Hundreds' value")
    public void typeToHundredsItemCountInputField(String val) {
        waitForElementClickable(hundredsItemCountField);
        type(val, hundredsItemCountField);
    }
}
