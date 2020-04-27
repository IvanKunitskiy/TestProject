package com.nymbus.pages.accounts;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.util.List;

public class CallStatementModalPage extends PageTools {

    private By addressSelectorButton = By.xpath("//div[@data-test-id='field-address']");
    private By addressList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private By addressSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private By okButton = By.xpath("//button/span[contains(text(), 'OK')]");

    @Step("Click the 'Ok' button")
    public void clickOkButton() {
        waitForElementClickable(okButton);
        click(okButton);
    }

    @Step("Click the 'Address' selector button")
    public void clickAddressSelectorOption(String option) {
        waitForElementClickable(addressSelectorOption,  option);
        click(addressSelectorOption, option);
    }

    @Step("Returning list of 'Address' options")
    public List<String> getAddressList() {
        waitForElementVisibility(addressList);
        return getElementsText(addressList);
    }

    @Step("Click the 'Address' selector button")
    public void clickAddressSelectorButton() {
        waitForElementClickable(addressSelectorButton);
        click(addressSelectorButton);
    }
}
