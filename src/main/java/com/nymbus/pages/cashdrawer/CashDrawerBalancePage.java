package com.nymbus.pages.cashdrawer;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class CashDrawerBalancePage extends PageTools {

    private By countedCash = By.xpath("//div[@ng-if='pageConfig.endingbalance.isShow']//label[@ng-model='cashDenomination.endingbalance']");
    private By cashIn = By.xpath("//ul[contains(@class, 'twoColsList ')]//label[@ng-model='cashDenomination.cashin']");
    private By cashOut = By.xpath("//ul[contains(@class, 'twoColsList ')]//label[@ng-model = 'cashDenomination.cashout']");

    @Step("Get countedCash")
    public String getCountedCash() {
        waitForElementVisibility(countedCash);
        return getElementText(countedCash).trim().replaceAll(",","");
    }

    @Step("Get Cash In")
    public String getCashIn() {
        waitForElementVisibility(cashIn);
        return getElementText(cashIn).trim().replaceAll(",","");
    }

    @Step("Get Cash Out")
    public String getCashOut() {
        waitForElementVisibility(cashOut);
        return getElementText(cashOut).trim().replaceAll(",","");
    }
}