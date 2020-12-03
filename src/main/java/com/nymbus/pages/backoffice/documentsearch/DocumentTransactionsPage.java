package com.nymbus.pages.backoffice.documentsearch;

import com.codeborne.selenide.SelenideElement;
import com.nymbus.core.base.PageTools;
import com.nymbus.core.utils.SelenideTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class DocumentTransactionsPage extends PageTools {
    private final By tabContent = By.xpath("//article[@ui-view='tabContent']");
    private final By date = By.xpath("//input[@data-test-id='field-from']");
    private final By branchOption = By.xpath("//div[@class='multipleDropdown__itemsContainer']/div/label[contains(text(), '%s')]/preceding-sibling::input[@type='checkbox']");
    private final By branchSelectorButton = By.xpath("//div/div/span/input[contains(@id, 'multipleDropdown')]");
    private final By searchResultWithDate = By.xpath("//div[contains(@class, 'dn-gridok-table__tr')]//div[text()='%s']");
    private final By searchResultsRow = By.xpath("//div[contains(@class, 'dn-gridok-table__tr') and contains(@class, 'ng-scope')]");
    private final By searchButton = By.xpath("//button[text()='Search']");
    private final By clearAllButton = By.xpath("//button[text()='Clear all']");
    private final By accountTypeSelectorButton = By.xpath("//div[@name='accounttypeid']");
    private final By accountOption = By.xpath("//ul[@role='listbox']/li[contains(@id, ui-select-choices-row)]/div/span[text()='%s']");
    private final By accountNumber = By.xpath("//div[@data-test-id='account']//input[@type='search']");
    private final By noticeCheckbox = By.xpath("//div[@config='navigationConfig']//input[@type='checkbox']");
    private final By printButton = By.xpath("//button[text()='Print']");
    private final By pdfLoadSpinner = By.xpath("//div[@class='spinnerWrapper']");
    private final By pdfItem = By.xpath("//iframe[@id='pdf-item']");
    private final By noticesTab = By.xpath("//ul/li[@data-test-id='notices-presentation']/a");

    @Step("Click 'Notices' tab")
    public void clickNoticesTab() {
        waitForElementClickable(noticesTab);
        click(noticesTab);
    }

    @Step("Wait for tab content is loaded")
    public void waitForTabContent() {
        waitForElementVisibility(tabContent);
    }

    @Step("Set 'Date Opened' value")
    public void setDateOpenedValue(String dateOpenedValue) {
        waitForElementClickable(date);
        SelenideTools.sleep(2);
        type(dateOpenedValue, date);
    }

    @Step("Click 'Branch' selector button")
    public void clickBranchSelectorButton() {
        waitForElementClickable(branchSelectorButton);
        click(branchSelectorButton);
    }

    @Step("Click 'Branch' option")
    public void clickBranchOption(String option) {
        waitForElementClickable(branchOption, option);
        click(branchOption, option);
    }

    @Step("Click 'Notice type' selector button")
    public void clickAccountTypeSelectorButton() {
        waitForElementClickable(accountTypeSelectorButton);
        click(accountTypeSelectorButton);
    }

    @Step("Click 'Notice' option")
    public void clickAccountOption(String option) {
        waitForElementClickable(accountOption, option);
        click(accountOption, option);
    }

    @Step("Click 'Search' button")
    public void clickSearchButton() {
        waitForElementClickable(searchButton);
        click(searchButton);
    }

    @Step("Click 'Clear All' button")
    public void clickClearAllButton() {
        waitForElementClickable(clearAllButton);
        click(clearAllButton);
    }

    @Step("Click 'Print' button")
    public void clickPrintButton() {
        waitForElementClickable(printButton);
        click(printButton);
    }

    @Step("Wait for search results with date")
    public void waitForSearchResultsWithDate(String date) {
        SelenideTools.sleep(3);
        waitForElementVisibility(searchResultWithDate, date);
    }

    @Step("Get search results count")
    public int getSearchResultsCount() {
        return getElements(searchResultsRow).size();
    }

    @Step("Click row with enabled checkbox")
    public void clickRowWithEnabledCheckbox() {
        getElements(noticeCheckbox).stream()
                .filter(SelenideElement::isSelected)
                .findAny()
                .orElse(null)
                .click();
    }

    @Step("Type account number")
    public void typeAccountNumber(String number) {
        waitForElementClickable(accountNumber, number);
        type(number, accountNumber);
    }

    @Step("Wait for loading spinner invisibility")
    public void waitForLoadingSpinnerInvisibility() {
        waitForElementInvisibility(pdfLoadSpinner);
    }

    @Step("Is pdf visible")
    public boolean isPdfVisible() {
        waitForElementVisibility(pdfItem);
        return isElementVisible(pdfItem);
    }
}
