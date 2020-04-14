package com.nymbus.pages.journal;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class JournalPage extends PageTools {

    /**
     * Filter region
     */
    private By accountNumberInput = By.xpath("//input[@ng-model='filter.number.account']");

    /**
     * Items row region
     */

    private By itemInTable = By.xpath("//tr[contains(@class, 'hoverPointer')][%s]");
    private By itemInTableState = By.xpath("//tr[contains(@class, 'hoverPointer')][%s]//td[2]/button");
    private By itemInTableType = By.xpath("//tr[contains(@class, 'hoverPointer')][%s]//td[3]" +
            "//span[contains(@class, 'dnTextFixedWidthText ')]");

    @Step("Type Account number")
    public void typeAccountNumber(String text) {
        waitForElementClickable(accountNumberInput);
        type(text, accountNumberInput);
    }

    @Step("Click on {0} item in table")
    public void clickItemInTable(int index) {
        waitForElementVisibility(itemInTable, index);
        click(itemInTable, index);
    }

    @Step("Get item {0} state")
    public String getItemState(int index) {
        waitForElementVisibility(itemInTableState, index);
        return getWebElement(itemInTableState, index).getAttribute("title");
    }

    @Step("Get item {0} type")
    public String getItemType(int index) {
        waitForElementVisibility(itemInTableType, index);
        return getElementText(itemInTableType, index);
    }
}