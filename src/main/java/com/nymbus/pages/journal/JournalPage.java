package com.nymbus.pages.journal;

import com.codeborne.selenide.Selenide;
import com.nymbus.core.base.PageTools;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.awt.*;
import java.util.List;

public class JournalPage extends PageTools {

    /**
     * Filter region
     */
    private By accountNumberInput = By.xpath("//input[@ng-model='filter.number.account']");
    private By transactionNumberInput = By.xpath("//input[@ng-model='filter.number.transaction']");
    private By proofDateSpan = By.xpath("//div[@ng-model='teller.proofDate']//span[@class='select2-chosen']/span");
    private By transactionAmountInput = By.xpath("//input[@data-test-id='field-amountTransaction']");
    private By tellerInputField = By.xpath("//div[@data-test-id='field-teller']/a");
    private By tellerListOption = By.xpath("//ul[@role='listbox']/li[@role='option']/div/span");
    private By tellerListSelectorOption = By.xpath("//ul[@role='listbox']/li[@role='option']/div/span[contains(text(), '%s')]");
    private By clearFiltersButton = By.xpath("//button[@data-test-id='action-clearFilters']");
    private By endBatchButton = By.xpath("//button[@data-test-id='action-endBatch']");
    private By enterAmountsButton = By.xpath("//button[@data-test-id='action-enterAmounts']");
    private By commitButton = By.xpath("//button[@data-test-id='action-commitCashDenomination']");
    private By cancelButton = By.xpath("//button[@data-test-id='action-commitCashDenomination']");
    private By clearSelectButton = By.xpath("(//button[contains(string(),'Clear Select')])[2]");
    private By checkbox = By.xpath("(//span[contains(string(),'%s')]/../input)[%s]");
    private final By cdtSelector = By.xpath("//div[@data-test-id='field-cashierDefinedTemplate']/a");
    private final By cdtSearch = By.xpath("(//div[@data-test-id='field-cashierDefinedTemplate']//input)[1]");
    private final By cdtSpan = By.xpath("(//span[contains(string(),'%s')])[1]");
    private final By preview = By.xpath("//print-preview-app");
    private By voidButton = By.xpath("//button[contains(string(),'Void')]");

    @Step("Click Cancel Button")
    public void clickCancelButton() throws AWTException {
        SelenideTools.sleep(15);
        WebDriver driver = SelenideTools.getDriver();
        driver.switchTo().window(driver.getWindowHandles().stream().filter(handle -> !handle.equals(driver.getWindowHandle())).findAny().get());
        WebElement printPreviewApp = driver.findElement(By.xpath("//print-preview-app"));
        WebElement printPreviewAppContent = Selenide.executeJavaScript("return arguments[0].shadowRoot", printPreviewApp);
        WebElement printPreviewSideBar = printPreviewAppContent.findElement(By.id("sidebar"));
        WebElement printPreviewSideBarContent = Selenide.executeJavaScript("return arguments[0].shadowRoot", printPreviewSideBar);
        WebElement printPreviewHeader = printPreviewSideBarContent.findElement(By.tagName("print-preview-button-strip"));
        WebElement printPreviewHeaderContent = Selenide.executeJavaScript("return arguments[0].shadowRoot", printPreviewHeader);
        printPreviewHeaderContent.findElement(By.className("cancel-button")).click();
        SelenideTools.sleep(15);
        SelenideTools.switchToLastTab();

    }

    @Step("Type account number")
    public void fillInAccountNumber(String accountNumber) {
        waitForElementVisibility(accountNumberInput);
        waitForElementClickable(accountNumberInput);
        type(accountNumber, accountNumberInput);
    }

    @Step("Type transaction number")
    public void fillInTransactionNumber(String transactionNumber) {
        waitForElementVisibility(transactionNumberInput);
        waitForElementClickable(transactionNumberInput);
        type(transactionNumber, transactionNumberInput);
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

    @Step("Click 'Clear end batch' button")
    public void clickEndBatchButton() {
        waitForElementVisibility(endBatchButton);
        waitForElementClickable(endBatchButton);
        click(endBatchButton);
    }

    @Step("Click 'Clear enter amounts' button")
    public void clickEnterAmountsButton() {
        waitForElementVisibility(enterAmountsButton);
        waitForElementClickable(enterAmountsButton);
        click(enterAmountsButton);
    }

    @Step("Click 'Commit' button")
    public void clickCommitButton() {
        waitForElementVisibility(commitButton);
        waitForElementClickable(commitButton);
        click(commitButton);
    }

    @Step("Click 'Clear filters' button")
    public void clickClearSelectFiltersButton() {
        waitForElementVisibility(clearSelectButton);
        waitForElementClickable(clearSelectButton);
        jsClick(clearSelectButton);
    }

    @Step("Click {0} checkbox")
    public void clickCheckbox(String text, int index) {
        waitForElementVisibility(checkbox, text, index);
        waitForElementClickable(checkbox, text, index);
        jsClick(checkbox, text, index);
    }

    @Step("Check 'Void' is disabled")
    public void isVoidNotVisible() {
        SelenideTools.sleep(Constants.MINI_TIMEOUT);
        waitForElementNotPresent(voidButton);
    }

    @Step("Click 'CDT' button")
    public void clickCDTFilterButton() {
        waitForElementVisibility(cdtSelector);
        click(cdtSelector);
    }

    @Step("Search 'CDT'")
    public void searchCDT(String template) {
        waitForElementVisibility(cdtSelector);
        click(cdtSelector);
        waitForElementVisibility(cdtSearch);
        type(template, cdtSearch);
        waitForElementVisibility(cdtSpan, template);
        click(cdtSpan, template);

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

    @Step("Wait for main spinner invisibility")
    public void waitForMainSpinnerInvisibility() {
        waitForElementInvisibility(mainSpinner);
    }
}