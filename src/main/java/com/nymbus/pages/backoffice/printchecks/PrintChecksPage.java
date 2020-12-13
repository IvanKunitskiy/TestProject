package com.nymbus.pages.backoffice.printchecks;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class PrintChecksPage extends PageTools {

    private final By checkTypeSelector = By.xpath("//a[@placeholder='Check Type']//span[contains(@class, 'ui-select-toggle')]");
    private final By checkTypeOption = By.xpath("//li[contains(@id, 'ui-select-choices-row')]/div/span[text()='%s']");
    private final By searchButton = By.xpath("//button[@type='submit']");
    private final By checkboxByAccountNumber = By.xpath("//section[@ui-view='searchResults']//tbody/tr/td[span/span[contains(text(), '%s')]]/preceding-sibling::td/input");
    private final By checkNumberByAccountNumber = By.xpath("//section[@ui-view='searchResults']//tbody/tr/td[span/span[contains(text(), '%s')]]/following-sibling::td[3]");
    private final By tableRowWithAccountNumber = By.xpath("//section[@ui-view='searchResults']//tbody/tr[td/span/span[contains(text(), '%s')]]");
    private final By loadMoreResultsButton = By.xpath("//button[@data-test-id='action-loadMore']");
    private final By printChecksButton = By.xpath("//button/span[text()='Print Checks']");

    @Step("Click the 'Check Type' selector button")
    public void clickCheckTypeSelector() {
        waitForElementClickable(checkTypeSelector);
        click(checkTypeSelector);
    }

    @Step("Click the 'Check type' option")
    public void clickCheckTypeOption(String option) {
        waitForElementClickable(checkTypeOption, option);
        click(checkTypeOption, option);
    }

    @Step("Click the 'Search' button")
    public void clickSearchButton() {
        waitForElementClickable(searchButton);
        click(searchButton);
    }

    @Step("Tick the checkbox by account number")
    public void selectLineWithAccountNumber(String accountNumber) {
        waitForElementClickable(checkboxByAccountNumber, accountNumber);
        click(checkboxByAccountNumber, accountNumber);
    }

    @Step("Tick the checkbox by account number")
    public String getCheckNumberFromLineWithAccountNumber(String accountNumber) {
        return getElementText(checkNumberByAccountNumber, accountNumber).trim();
    }

    @Step("Verify if line containing account number is visible")
    public boolean isLineWithAccountNumberVisible(String accountNumber) {
        return isElementVisible(tableRowWithAccountNumber, accountNumber);
    }

    @Step("Click the 'Load more' button")
    public void clickLoadMoreResultsButton() {
        waitForElementClickable(loadMoreResultsButton);
        click(loadMoreResultsButton);
    }

    @Step("Is 'Load more' button visible")
    public boolean isLoadMoreResultsButtonVisible() {
        return isElementVisible(loadMoreResultsButton);
    }

    @Step("Click the 'Print Checks' button")
    public void clickPrintChecksButton() {
        waitForElementClickable(printChecksButton);
        click(printChecksButton);
    }
}
