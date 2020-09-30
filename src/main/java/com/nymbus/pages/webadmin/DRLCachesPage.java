package com.nymbus.pages.webadmin;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class DRLCachesPage extends PageTools {

    private By ruleTypeRegion = By.xpath("//div[@id='DRLCache-ruletypefull']");
    private By bankingCoreRadioButton = By.xpath("//div[@id='DRLCache-ruletypefull']//ul//input[@title='bankingcore']");
    private By keysItem = By.xpath("//div[@id='DRLCache-caches']" +
            "//tr//td[text()='%s']/following-sibling::td/a[text()='Keys']");
    private By searchField = By.xpath("//div[contains(@class, 'ui-dialog') and " +
            "contains(@style, 'display: flex')][%s]//input[@placeholder='Filter']");
    private By keysValueLink = By.xpath("//div[contains(@class, 'ui-dialog') and contains(@style, 'display: flex')][%s]" +
            "//div[@class='keys']//pre[contains(text(), '%s')]/following-sibling::a[contains(text(), 'value')]");
    private By cashValuesData = By.xpath("//div[contains(@class, 'ui-dialog') and contains(@style, 'display: flex')][%s]" +
            "//div[@class='ui-dialog-content ui-widget-content']//pre[1]");


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

    @Step("Click on 'value' [1] item on [0] modal")
    public void clickValueItemByModalAndClientRootId(int modalNumber, String rootId) {
        waitForElementVisibility(keysValueLink, modalNumber, rootId);
        waitForElementClickable(keysValueLink,  modalNumber, rootId);
        click(keysValueLink,  modalNumber, rootId);
    }

    @Step("Get cach values data [0]")
    public String getCashValuesData(int modalNumber) {
        waitForElementVisibility(cashValuesData, modalNumber);
        waitForElementClickable(cashValuesData,  modalNumber);
        return getElementText(cashValuesData,  modalNumber);
    }
}