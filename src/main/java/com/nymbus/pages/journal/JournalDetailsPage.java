package com.nymbus.pages.journal;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class JournalDetailsPage extends PageTools {

    private By errorCorrectButton = By.xpath("//td[contains(@class, 'actions')]/button[1]");
    private By itemInTableState = By.xpath("//tr[contains(@class, 'hoverPointer')][%s]//td[2]/button");
    private By voidState = By.xpath("//tr[contains(@class, 'hoverPointer')][1]//td[2]/button[@title='Void']");
    private By transactionData = By.xpath("//tr[contains(@class, 'hoverPointer')][%s]/td[4]");
    private By journalLinkInBreadCrumbs = By.xpath("//a[@data-test-id='go-tellerJournal']");
    private By loadingSpinner = By.xpath("//div[@id='printReceipt']/dn-loading-spinner/div/svg");

    @Step("Click 'Error Correct' button")
    public void clickErrorCorrectButton() {
        waitForElementClickable(errorCorrectButton);
        click(errorCorrectButton);
    }

    @Step("Wait for 'Error Correct' button invisibility")
    public void waitForErrorCorrectButtonInvisibility() {
        waitForElementDisabled(errorCorrectButton);
    }

    @Step("Wait for loading spinner invisibility")
    public void waitForLoadingSpinnerInvisibility() {
        waitForElementInvisibility(loadingSpinner);
    }

    @Step("Get item {0} state")
    public String getItemState(int index) {
        waitForElementVisibility(itemInTableState, index);
        return getWebElement(itemInTableState, index).getAttribute("title");
    }

    @Step("Wait for 'Create Error' applying")
    public void waitForCreateErrorApplying() {
        waitForElementVisibility(voidState);
    }

    @Step("Get item {0} transaction data")
    public String getTransactionData(int index) {
        waitForElementVisibility(transactionData, index);
        return getElementText(transactionData, index).trim();
    }

    @Step("Go back to journal page")
    public void goBackToJournalPage() {
        waitForElementClickable(journalLinkInBreadCrumbs);
        click(journalLinkInBreadCrumbs);
    }
}
