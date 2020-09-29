package com.nymbus.pages.webadmin;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class DRLCachesPage extends PageTools {

    private By ruleTypeRegion = By.xpath("//div[@id='DRLCache-ruletypefull']");
    private By bankingCoreRadioButton = By.xpath("//div[@id='DRLCache-ruletypefull']//ul//input[@title='bankingcore']");
    private By keysItem = By.xpath("//div[@id='DRLCache-caches']" +
            "//tr//td[text()='%s']/following-sibling::td/a[text()='Keys']");
    private By searchField = By.xpath("//div[contains(@class, 'ui-dialog') and" +
            "contains(@style, 'display: flex')][%s]//input[@placeholder='Filter']");

    @Step("Wait for 'Rule Type' region")
    public void waitForPageLoaded() {
        waitForElementVisibility(ruleTypeRegion);
    }

    @Step("Click on 'bankingcore' radio button")
    public void clickBankingCoreRadio() {
        waitForElementVisibility(bankingCoreRadioButton);
        waitForElementClickable(bankingCoreRadioButton);
        click(bankingCoreRadioButton);
    }

    @Step("Click on 'keys' item by [0] value")
    public void clickKeysItemByValue(String value) {
        waitForElementVisibility(keysItem, value);
        waitForElementClickable(keysItem, value);
        click(keysItem, value);
    }

    @Step("Type [1] to search input in [0] modal")
    public void typeToSearchInput(int count, String value) {
        waitForElementVisibility(searchField, count);
        waitForElementClickable(searchField, count);
        type(value, searchField, count);
    }
}