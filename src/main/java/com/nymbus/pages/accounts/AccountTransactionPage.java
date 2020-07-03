package com.nymbus.pages.accounts;

import com.nymbus.core.base.PageTools;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class AccountTransactionPage extends PageTools {

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
     * Filter region
     */
    private By transactionsFromArrowButton = By.xpath("//*[@ng-model='transactionsFilter.statement']//span[contains(@class, 'select2-arrow')]");
    private By itemInDropDown = By.xpath("//div[contains(@class, 'select2-drop-active') and not(contains(@class, 'select2-display-none'))]" +
                                 "//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private By applyFiltersButton = By.xpath("//button[@ng-click='applyFilter()']");


}