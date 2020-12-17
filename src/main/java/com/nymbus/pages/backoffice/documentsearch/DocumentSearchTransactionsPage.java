package com.nymbus.pages.backoffice.documentsearch;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class DocumentSearchTransactionsPage extends PageTools {

    private final By accountInput = By.xpath("//input[@name='accountnumber']");
    private final By amountInput = By.xpath("//input[@name='amountfrom']");
    private final By transactionsTab = By.xpath("//ul/li/a[text()='Transactions']");
    private final By searchButton = By.xpath("//button[text()='Search']");
    private final By tabContent = By.xpath("//article[@ui-view='tabContent']");
    private final By tableLineByIndex = By.xpath("//div[@class='gridok_content']/div/div[contains(@class, 'dn-gridok-table__tbody')]" +
            "/div[%s]/div[contains(@class, 'checkbox-column')]//input");
    private final By showCompanionItemsButton = By.xpath("//button[div/span[text()='Show Companion Items']]");
    private final By companionItemsPanel = By.xpath("//div[contains(@class, 'showOnCompanionItems')]");
    private final By companionItem = By.xpath("//div[contains(@class, 'showOnCompanionItems')]/div/div[contains(@class, 'dn-gridok-table__tbody')]");
    private final By clearAllButton = By.xpath("//button[text()='Clear all']");
    private final By transactionItem = By.xpath("//div[contains(@class, 'dn-gridok-table__tbody')]//div[contains(@class, 'dn-gridok-table__tr')]");

    @Step("Click 'Transactions' tab")
    public void clickTransactionsTab() {
        waitForElementClickable(transactionsTab);
        click(transactionsTab);
    }

    @Step("Set 'Account #' value")
    public void setAccount(String accNumber) {
        waitForElementVisibility(accountInput);
        type(accNumber, accountInput);
    }

    @Step("Set 'Amount' value")
    public void setAmount(String amount) {
        waitForElementVisibility(amountInput);
        type(amount, amountInput);
    }

    @Step("Click 'Search' button")
    public void clickSearchButton() {
        waitForElementClickable(searchButton);
        click(searchButton);
    }

    @Step("Wait for tab content is loaded")
    public void waitForTabContent() {
        waitForElementVisibility(tabContent);
    }

    @Step("Wait for 'Companion Items' panel")
    public void waitForCompanionItemsPanel() {
        waitForElementVisibility(companionItemsPanel);
    }

    @Step("Select transaction line by index")
    public void selectLineByIndex(int index) {
        click(tableLineByIndex, index);
    }

    @Step("Click 'Show Companion Items' button")
    public void clickShowCompanionItemsButton() {
        waitForElementClickable(showCompanionItemsButton);
        click(showCompanionItemsButton);
    }

    @Step("Get companion items count")
    public int getCompanionItemsCount() {
        return getElements(companionItem).size();
    }

    @Step("Get transactions count")
    public int getTransactionsCount() {
        return getElements(transactionItem).size();
    }

    @Step("Click 'Clear all' button")
    public void clickClearAllButton() {
        waitForElementClickable(clearAllButton);
        click(clearAllButton);
    }
}
