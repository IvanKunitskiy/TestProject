package com.nymbus.pages.cashdrawer;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;


public class CashDrawerBalancePage extends PageTools {
    // balance value loads with delay so we need wait until counted cash size will be greater than 5 and less than 50
    private final String MATCHER_FOR_SYMBOLS_COUNT = "^.{5,50}$";

    private By countedCash = By.xpath("//div[@ng-if='pageConfig.endingbalance.isShow']//label[@ng-model='cashDenomination.endingbalance']");
    private By cashIn = By.xpath("//ul[contains(@class, 'twoColsList ')]//label[@ng-model='cashDenomination.cashin']");
    private By cashOut = By.xpath("//ul[contains(@class, 'twoColsList ')]//label[@ng-model = 'cashDenomination.cashout']");
    private final By enterAmounts = By.xpath("//span[text()='Enter Amounts']");
    private final By save = By.xpath("");

    @Step("Get countedCash")
    public String getCountedCash() {
        waitForElementVisibility(countedCash);
        return shouldMatchText(MATCHER_FOR_SYMBOLS_COUNT, countedCash).text().trim().replaceAll(",","");
    }

    @Step("Get Cash In")
    public String getCashIn() {
        waitForElementVisibility(cashIn);
        shouldMatchText(MATCHER_FOR_SYMBOLS_COUNT, countedCash);
        return getElementText(cashIn).trim().replaceAll(",","");
    }

    @Step("Get Cash Out")
    public String getCashOut() {
        waitForElementVisibility(cashOut);
        shouldMatchText(MATCHER_FOR_SYMBOLS_COUNT, countedCash);
        return getElementText(cashOut).trim().replaceAll(",","");
    }

    @Step("Click 'Enter amounts' button")
    public void clickEnterAmounts(){
        waitForElementVisibility(enterAmounts);
        waitForElementClickable(enterAmounts);
        click(enterAmounts);
    }

    @Step("Click 'Save' button")
    public void clickSave(){
        waitForElementVisibility(save);
        waitForElementClickable(save);
        click(save);
    }

    /**
     * Amounts / Denominations section
     */

    private By hundredsAmount = By.xpath("//input[@id='onehundredsloose']");
    private By fiftiesAmount = By.xpath("//input[@id='fiftiesloose']");

    @Step("Get Hundreds amount")
    public String getHundredsAmount() {
        waitForElementVisibility(hundredsAmount);
        shouldMatchText(MATCHER_FOR_SYMBOLS_COUNT, countedCash);
        return getElementAttributeValue("value", hundredsAmount) .trim().replaceAll(",","");
    }

    @Step("Set Hundreds amount")
    public void setHundredsAmount(double value) {
        waitForElementVisibility(hundredsAmount);
        type(String.valueOf(value), hundredsAmount);
    }

    @Step("Get Fifties amount")
    public String getFiftiesAmount() {
        waitForElementVisibility(fiftiesAmount);
        shouldMatchText(MATCHER_FOR_SYMBOLS_COUNT, countedCash);
        return getElementAttributeValue("value", fiftiesAmount) .trim().replaceAll(",","");
    }

    @Step("Set Fifties amount")
    public void setFiftiesAmount(double value) {
        waitForElementVisibility(fiftiesAmount);
        type(String.valueOf(value), fiftiesAmount);
    }
}