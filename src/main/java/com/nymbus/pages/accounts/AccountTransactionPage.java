package com.nymbus.pages.accounts;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class AccountTransactionPage extends PageTools {

    /**
     * Action buttons
     */
    private By callStatementButton = By.xpath("//div[contains(@class, 'actions')]/button[1]");

    /**
     * Data in table
     */
    private By amountSymbol = By.xpath("//tr[contains(@class, 'transactionLine')][%s]//td[6]//span[@ng-if='showCurrency']/span[1]");
    private By postingDate = By.xpath("//tr[contains(@class, 'transactionLine')][%s]//td[2]//span");
    private By effectiveDate = By.xpath("//tr[contains(@class, 'transactionLine')][%s]//td[3]//span");
    private By amount = By.xpath("//tr[contains(@class, 'transactionLine')][%s]//td[6]//span[@ng-if='showCurrency']/span[2]");
    private By balance = By.xpath("//tr[contains(@class, 'transactionLine')][%s]//td[7]//span[@ng-if='showCurrency']/span[2]");
    private By balanceFractional = By.xpath("//tr[contains(@class, 'transactionLine')][%s]//td[7]//span[@ng-if='showCurrency']/span[3]");
    private By amountFractional = By.xpath("//tr[contains(@class, 'transactionLine')][%s]//td[6]//span[@ng-if='showCurrency']/span[3]");

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

    @Step(" Click 'Call Statement' button")
    public void clickStatementButton() {
        waitForElementClickable(callStatementButton);
        click(callStatementButton);
    }
}
