package com.nymbus.pages.journal;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.util.List;

public class JournalPage extends PageTools {

    /**
     * Filter region
     */
    private By accountNumberInput = By.xpath("//input[@ng-model='filter.number.account']");
    private By proofDateSpan = By.xpath("//div[@ng-model='teller.proofDate']//span[@class='select2-chosen']/span");
    private By transactionAmountInput = By.xpath("//input[@data-test-id='field-amountTransaction']");
    private By tellerInputField = By.xpath("//div[@data-test-id='field-teller']/a");
    private By tellerListOption = By.xpath("//ul[@role='listbox']/li[@role='option']/div/span");
    private By tellerListSelectorOption = By.xpath("//ul[@role='listbox']/li[@role='option']/div/span[contains(text(), '%s')]");
    private By clearFiltersButton = By.xpath("//button[@data-test-id='action-clearFilters']");

    @Step("Type account number")
    public void fillInAccountNumber(String accountNumber) {
        waitForElementVisibility(accountNumberInput);
        waitForElementClickable(accountNumberInput);
        type(accountNumber, accountNumberInput);
    }

    @Step("Type transaction amount")
    public void fillInTransactionAmount(String transactionAmount) {
        type(transactionAmount, transactionAmountInput);
    }

    @Step("Get transaction amount input value")
    public String getTransactionAmountValue() {
        return getElementAttributeValue("value", transactionAmountInput);
    }

    @Step("Click 'Clear filters' button")
    public void clickClearFiltersButton() {
        click(clearFiltersButton);
    }

    @Step("Wait for Proof date span")
    public void waitForProofDateSpan() {
        waitForElementVisibility(proofDateSpan);
    }

    @Step("Click teller input field")
    public void clickTellerInputField() {
        waitForElementVisibility(tellerInputField);
        click(tellerInputField);
    }

    @Step("Get list of tellers")
    public List<String> getListOfTeller() {
        return getElementsText(tellerListOption);
    }

    @Step("Click teller selector option")
    public void clickTellerSelectorOption(String tellerName) {
        click(tellerListSelectorOption, tellerName);
    }

    /**
     * Items row region
     */

    private By itemInTable = By.xpath("//tr[contains(@class, 'hoverPointer')][%s]");
    private By itemInTableState = By.xpath("//tr[contains(@class, 'hoverPointer')][%s]//td[2]/button");
    private By itemInTableType = By.xpath("//tr[contains(@class, 'hoverPointer')][%s]//td[3]" +
            "//span[contains(@class, 'dnTextFixedWidthText ')]");
    private By itemInTableTransactionData = By.xpath("//tr[contains(@class, 'hoverPointer')][%s]//td[4]/span/span");
    private By transactionDescriptionList = By.xpath("//tr[contains(@class, 'hoverPointer')]//td[4]/span/span");

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

    @Step("Get item {0} transaction data")
    public String getTransactionData(int index) {
        waitForElementVisibility(itemInTableTransactionData, index);
        return getElementText(itemInTableTransactionData, index);
    }

    @Step("Get list of 'transaction data'")
    public List<String> getListOfTransactionData() {
        return getElementsText(transactionDescriptionList);
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