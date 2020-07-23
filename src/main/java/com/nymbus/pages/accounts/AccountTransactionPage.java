package com.nymbus.pages.accounts;

import com.nymbus.core.base.PageTools;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class AccountTransactionPage extends PageTools {
    // element positions in table
    private final int AMOUNT_POSITION = 5;
    private final int BALANCE_POSITION = 6;
    private final int POSTING_DATE_POSITION = 1;
    private final int EFFECTIVE_DATE_POSITION = 2;
    private final int TRANSACTION_CODE_POSITION =4;

    /**
     * Action buttons
     */
    private By callStatementButton = By.xpath("//div[contains(@class, 'actions')]/button[1]");
    private By expandAll = By.xpath("//div[contains(@class, 'actions')]/button[2]");

    /**
     * Data in table
     */
    private By noResultsLabel = By.xpath("//section[@ng-if='!haveTransactions()']//div//p");
    private By amountSymbol = By.xpath("//tr[contains(@class, 'transactionLine')][%s]//td[6]//span[@ng-if='showCurrency']/span[1]");
    private By postingDate = By.xpath("//tr[contains(@class, 'transactionLine')][%s]//td[2]//span");
    private By effectiveDate = By.xpath("//tr[contains(@class, 'transactionLine')][%s]//td[3]//span");
    private By effectiveDateWithSourceFilter = By.xpath("//tr[contains(@class, 'transactionLine')][%s]//td[2]//span");
    private By amount = By.xpath("//tr[contains(@class, 'transactionLine')][%s]//td[6]//span[@ng-if='showCurrency']/span[2]");
    private By balance = By.xpath("//tr[contains(@class, 'transactionLine')][%s]//td[7]//span[@ng-if='showCurrency']/span[2]");
    private By balanceFractional = By.xpath("//tr[contains(@class, 'transactionLine')][%s]//td[7]//span[@ng-if='showCurrency']/span[3]");
    private By amountFractional = By.xpath("//tr[contains(@class, 'transactionLine')][%s]//td[6]//span[@ng-if='showCurrency']/span[3]");
    private By transactionItems = By.xpath("//tr[contains(@class, 'transactionLine')]");
    private By image = By.xpath("//tr[contains(@class, 'detail-view')][1]//img");
    private By transactionCode = By.xpath("//tr[contains(@class, 'transactionLine')][%s]//td[5]//span[@ng-switch-when='transactioncode']");

    @Step("Is 'No results' label visible")
    public boolean isNoResultsVisible() {
        SelenideTools.sleep(Constants.MICRO_TIMEOUT);
        return isElementVisible(noResultsLabel);
    }

    @Step("Get transaction item count")
    public int getTransactionItemsCount() {
        return getElements(transactionItems).size();
    }

    @Step("Get transaction item count")
    public int getTransactionItemsCountWithZeroOption() {
        return getElementsWithZeroOptionWithWait(Constants.MICRO_TIMEOUT, transactionItems).size();
    }

    @Step("Get transaction code")
    public String getTransactionCodeByIndex(int index) {
        waitForElementVisibility(transactionCode, index);
        return getWebElement(transactionCode, index).getAttribute("innerText").trim();
    }

    @Step("Get amountSymbol")
    public String getAmountSymbol(int index) {
        waitForElementVisibility(amountSymbol, index);
        return getElementText(amountSymbol, index).trim();
    }

    @Step("Get 'Posting date' value")
    public String getPostingDateValue(int index) {
        waitForElementVisibility(postingDate, index);
        return getElementText(postingDate, index).trim().replaceAll("-", "/");
    }

    @Step("Get 'Effective date' value")
    public String getEffectiveDateValue(int index) {
        waitForElementVisibility(effectiveDate, index);
        return getElementText(effectiveDate, index).trim().replaceAll("-", "/");
    }

    @Step("Get 'Effective date' value with applied filter")
    public String getEffectiveDateWithAppliedFilterValue(int index) {
        waitForElementVisibility(effectiveDateWithSourceFilter, index);
        return getElementText(effectiveDateWithSourceFilter, index).trim().replaceAll("-", "/");
    }

    @Step("Get 'Amount' value")
    public String getAmountValue(int index) {
        waitForElementVisibility(amount, index);
        return getElementText(amount, index).trim().replaceAll("[^0-9.]", "");
    }

    @Step("Get 'Amount' fractional value")
    public String getAmountFractionalValue(int index) {
        waitForElementVisibility(amountFractional, index);
        return getElementText(amountFractional, index).trim().replaceAll("[^0-9.]", "");
    }

    @Step("Get 'Balance' value")
    public String getBalanceValue(int index) {
        waitForElementVisibility(balance, index);
        return getElementText(balance, index).trim().replaceAll("[^0-9.]", "");
    }

    @Step("Get 'Balance' value")
    public String getBalanceFractionalValue(int index) {
        waitForElementVisibility(balanceFractional, index);
        return getElementText(balanceFractional, index).trim().replaceAll("[^0-9.]", "");
    }

    @Step("Wait for 'Call Statement' button")
    public void waitForCallStatementButton() {
        waitForElementVisibility(callStatementButton);
    }

    @Step("Click 'Call Statement' button")
    public void clickStatementButton() {
        waitForElementClickable(callStatementButton);
        click(callStatementButton);
    }

    @Step("Click 'Expand All' button")
    public void clickExpandAllButton() {
        waitForElementClickable(expandAll);
        click(expandAll);
        SelenideTools.sleep(1);
    }

    @Step("Is 'Expand All' button visible")
    public boolean isExpandAllButtonVisible() {
       return isElementVisible(expandAll);
    }

    @Step("Is amount symbol {0} has correct color")
    public boolean isAmountSymbolColorRight(int index, String expectedColor) {
        waitForElementVisibility(amountSymbol, index);
        String actualColor = getWebElement(amountSymbol, index).getCssValue("color");
        return isColorMatch(actualColor, expectedColor);
    }

    @Step("Is image {0} visible")
    public boolean isImageVisible(int index) {
        return isImageLoaded(image, index);
    }

    /**
     * Duplicated selectors for atm transactions
     */
    private By amountSymbol1 = By.xpath("//tr[contains(@class, 'transactionLine')][%s]//td[5]//span[@ng-if='showCurrency']/span[1]");
    private By postingDate1 = By.xpath("//tr[contains(@class, 'transactionLine')][%s]//td[1]//span");
    private By effectiveDate1 = By.xpath("//tr[contains(@class, 'transactionLine')][%s]//td[2]//span");
    private By amount1 = By.xpath("//tr[contains(@class, 'transactionLine')][%s]//td[5]//span[@ng-if='showCurrency']/span[2]");
    private By balance1 = By.xpath("//tr[contains(@class, 'transactionLine')][%s]//td[6]//span[@ng-if='showCurrency']/span[2]");
    private By balanceFractional1 = By.xpath("//tr[contains(@class, 'transactionLine')][%s]//td[6]//span[@ng-if='showCurrency']/span[3]");
    private By amountFractional1 = By.xpath("//tr[contains(@class, 'transactionLine')][%s]//td[5]//span[@ng-if='showCurrency']/span[3]");
    private By transactionCode1 = By.xpath("//tr[contains(@class, 'transactionLine')][%s]//td[4]//span[@ng-switch-when='transactioncode']");

    private By tableHeaders = By.xpath("//section[@ng-if = 'haveTransactions()']//table//thead//th");
    private By amountSymbolWithOffset = By.xpath("//tr[contains(@class, 'transactionLine')][%s]//td[%s]//span[@ng-if='showCurrency']/span[1]");
    private By postingDateWithOffset = By.xpath("//tr[contains(@class, 'transactionLine')][%s]//td[%s]//span");
    private By effectiveDateWithOffset = By.xpath("//tr[contains(@class, 'transactionLine')][%s]//td[%s]//span");
    private By amountWithOffset = By.xpath("//tr[contains(@class, 'transactionLine')][%s]//td[%s]//span[@ng-if='showCurrency']/span[2]");
    private By balanceWithOffset = By.xpath("//tr[contains(@class, 'transactionLine')][%s]//td[%s]//span[@ng-if='showCurrency']/span[2]");
    private By balanceFractionalWithOffset = By.xpath("//tr[contains(@class, 'transactionLine')][%s]//td[%s]//span[@ng-if='showCurrency']/span[3]");
    private By amountFractionalWithOffset = By.xpath("//tr[contains(@class, 'transactionLine')][%s]//td[%s]//span[@ng-if='showCurrency']/span[3]");
    private By transactionCodeWithOffset = By.xpath("//tr[contains(@class, 'transactionLine')][%s]//td[%s]//span[@ng-switch-when='transactioncode']");

    @Step("Get table header cols count")
    public int getTableHeaderCols() {
        waitForElementVisibility(tableHeaders);
        return getElements(tableHeaders).size();
    }

    @Step("Get amountSymbol with offset")
    public String getAmountSymbol(int index, int offset) {
        int calculatedOffset = AMOUNT_POSITION + offset;
        waitForElementVisibility(amountSymbolWithOffset, index, calculatedOffset);
        return getElementText(amountSymbolWithOffset, index, calculatedOffset).trim();
    }

    @Step("Get 'Posting date' value with offset")
    public String getPostingDateValue(int index, int offset) {
        int calculatedOffset = POSTING_DATE_POSITION + offset;
        waitForElementVisibility(postingDateWithOffset, index, calculatedOffset);
        return getElementText(postingDateWithOffset, index, calculatedOffset).trim().replaceAll("-", "/");
    }

    @Step("Get transaction code with offset")
    public String getTransactionCodeByIndex(int index, int offset) {
        int calculatedOffset = TRANSACTION_CODE_POSITION + offset;
        waitForElementVisibility(transactionCodeWithOffset, index, calculatedOffset);
        return getWebElement(transactionCodeWithOffset, index, calculatedOffset).getAttribute("innerText").trim();
    }

    @Step("Get 'Effective date' value with offset")
    public String getEffectiveDateValue(int index, int offset) {
        int calculatedOffset = EFFECTIVE_DATE_POSITION + offset;
        waitForElementVisibility(effectiveDateWithOffset, index, calculatedOffset);
        return getElementText(effectiveDateWithOffset, index, calculatedOffset).trim().replaceAll("-", "/");
    }

    @Step("Get 'Amount' value with offset")
    public String getAmountValue(int index, int offset) {
        int calculatedOffset = AMOUNT_POSITION + offset;
        waitForElementVisibility(amountWithOffset, index, calculatedOffset);
        return getElementText(amountWithOffset, index, calculatedOffset).trim().replaceAll("[^0-9.]", "");
    }

    @Step("Get 'Amount' fractional value with offset")
    public String getAmountFractionalValue(int index, int offset) {
        int calculatedOffset = AMOUNT_POSITION + offset;
        waitForElementVisibility(amountFractionalWithOffset, index, calculatedOffset);
        return getElementText(amountFractionalWithOffset, index, calculatedOffset).trim().replaceAll("[^0-9.]", "");
    }

    @Step("Get 'Balance' value with offset")
    public String getBalanceValue(int index, int offset) {
        int calculatedOffset = BALANCE_POSITION + offset;
        waitForElementVisibility(balanceWithOffset, index, calculatedOffset);
        return getElementText(balanceWithOffset, index, calculatedOffset).trim().replaceAll("[^0-9.]", "");
    }

    @Step("Get 'Balance' value with offset")
    public String getBalanceFractionalValue(int index, int offset) {
        int calculatedOffset = BALANCE_POSITION + offset;
        waitForElementVisibility(balanceFractionalWithOffset, index, calculatedOffset);
        return getElementText(balanceFractionalWithOffset, index, calculatedOffset).trim().replaceAll("[^0-9.]", "");
    }

    @Step("Get transaction code")
    public String getTransactionCodeByIndex1(int index) {
        waitForElementVisibility(transactionCode1, index);
        return getWebElement(transactionCode1, index).getAttribute("innerText").trim();
    }

    @Step("Get amountSymbol")
    public String getAmountSymbol1(int index) {
        waitForElementVisibility(amountSymbol1, index);
        return getElementText(amountSymbol1, index).trim();
    }

    @Step("Get 'Posting date' value")
    public String getPostingDateValue1(int index) {
        waitForElementVisibility(postingDate1, index);
        return getElementText(postingDate1, index).trim().replaceAll("-", "/");
    }

    @Step("Get 'Effective date' value")
    public String getEffectiveDateValue1(int index) {
        waitForElementVisibility(effectiveDate1, index);
        return getElementText(effectiveDate1, index).trim().replaceAll("-", "/");
    }

    @Step("Get 'Amount' value")
    public String getAmountValue1(int index) {
        waitForElementVisibility(amount1, index);
        return getElementText(amount1, index).trim().replaceAll("[^0-9.]", "");
    }

    @Step("Get 'Amount' fractional value")
    public String getAmountFractionalValue1(int index) {
        waitForElementVisibility(amountFractional1, index);
        return getElementText(amountFractional1, index).trim().replaceAll("[^0-9.]", "");
    }

    @Step("Get 'Balance' value")
    public String getBalanceValue1(int index) {
        waitForElementVisibility(balance1, index);
        return getElementText(balance1, index).trim().replaceAll("[^0-9.]", "");
    }

    @Step("Get 'Balance' value")
    public String getBalanceFractionalValue1(int index) {
        waitForElementVisibility(balanceFractional1, index);
        return getElementText(balanceFractional1, index).trim().replaceAll("[^0-9.]", "");
    }

    /**
     * Filter region
     */
    private By transactionsFromArrowButton = By.xpath("//*[@ng-model='transactionsFilter.statement']//span[contains(@class, 'select2-arrow')]");
    private By itemInDropDown = By.xpath("//div[contains(@class, 'select2-drop-active') and not(contains(@class, 'select2-display-none'))]" +
                                 "//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private By applyFiltersButton = By.xpath("//button[@ng-click='applyFilter()']");
    private By clearFilterButton = By.xpath("//*[@ng-model='transactionsFilter.statement']//abbr");

    @Step("Click 'Transaction from dropdown' arrow")
    public void clickTransactionFromDropdown() {
        waitForElementClickable(transactionsFromArrowButton);
        click(transactionsFromArrowButton);
    }

    @Step("Click item in dropdown {0}")
    public void clickItemInDropdown(String item) {
        waitForElementClickable(itemInDropDown, item);
        click(itemInDropDown, item);
    }

    @Step("Click apply filter button")
    public void clickApplyFilterButton() {
        waitForElementClickable(applyFiltersButton);
        click(applyFiltersButton);
    }

    @Step("Click clear filter button")
    public void clickClearFilterButton() {
        waitForElementClickable(clearFilterButton);
        click(clearFilterButton);
    }
}