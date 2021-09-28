package com.nymbus.pages.accounts;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class AccountsPage extends PageTools {

    private By callStatement = By.xpath("//button[span[text()='Call Statement']]");
    private By specificAccountByNumber = By.xpath("//tbody/tr/td[1]//span[contains(text(), '%s')]");
    private By specificAccountCurrentDate = By.xpath("//tbody/tr/td[1]//span[contains(text(), '%s')]/../../../td[11]");
    private By accountsTable = By.xpath("//table");

    @Step("Click the 'Call Statement' button")
    public void clickCallStatementButton() {
        waitForElementVisibility(callStatement);
        waitForElementClickable(callStatement);
        click(callStatement);
    }

    @Step("Check if Account by number {%s} present")
    public boolean isSpecificAccountByNumberPresent(String accNum) {
        return isElementVisible(specificAccountByNumber, accNum);
    }

    @Step("Get 'Current Date' of account by number {%s}")
    public String getSpecificAccountCurrentDate(String accNum) {
        waitForElementVisibility(specificAccountCurrentDate, accNum);
        return getElementText(specificAccountCurrentDate, accNum);
    }

    @Step("Waiting for accounts table load...")
    public void waitAccountTableLoad() {
        waitForElementVisibility(accountsTable);
    }

}