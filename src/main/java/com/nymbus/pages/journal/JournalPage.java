package com.nymbus.pages.journal;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class JournalPage extends PageTools {

    /**
     * Filter region
     */
    private By accountNumberInput = By.xpath("//input[@ng-model='filter.number.account']");
    private By proofDateSpan = By.xpath("//div[@ng-model='teller.proofDate']//span[@class='select2-chosen']/span");

    @Step("Type account number")
    public void fillInAccountNumber(String accountNumber) {
        waitForElementVisibility(accountNumberInput);
        waitForElementClickable(accountNumberInput);
        type(accountNumber, accountNumberInput);
    }

    @Step("Wait for Proof date span")
    public void waitForProofDateSpan() {
        waitForElementVisibility(proofDateSpan);
    }
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

    /**
     * Spinners region
     */
    private By leftAsideSpinner = By.xpath("//div[contains(@class, 'filter-fixed-body')]//dn-loading-spinner");
    private By mainSpinner = By.xpath("//main/dn-loading-spinner");

    @Step("Wait for aside spinner visibility")
    public void waitForAsideSpinnerVisibility() {
        waitForElementVisibility(leftAsideSpinner);
    }

    @Step("Wait for aside spinner invisibility")
    public void waitForAsideSpinnerInvisibility() {
        waitForElementInvisibility(leftAsideSpinner);
    }

    @Step("Wait for main spinner visibility")
    public void waitForMainSpinnerVisibility() {
        waitForElementVisibility(mainSpinner);
    }

    @Step("Wait for aside spinner invisibility")
    public void waitForMainSpinnerInvisibility() {
        waitForElementInvisibility(mainSpinner);
    }
}