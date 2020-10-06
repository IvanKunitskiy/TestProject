package com.nymbus.pages.settings.products;

import com.nymbus.core.base.PageTools;
import com.nymbus.core.utils.SelenideTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.util.List;

public class ProductsOverviewPage extends PageTools {

    private final By productTypeSelectorButton = By.xpath("//div[@data-field-id='select']//input[@data-widget='autocomplete']");
    private final By productTypeOption = By.xpath("//ul/li/a[contains(text(), '%s')]");
    private final By searchResultsTable = By.xpath("//table[contains(@class, 'xwidget_grid_table display')]");
    private final By searchResultsTableRow = By.xpath("//table/tbody/tr");
    private final By accountDescriptionsByType = By.xpath("//table/tbody/tr[(td[3]='%s') and (td[4]='%s')]/td[1]");
    private final By loadMoreResults = By.xpath("//a[@title='Next Page']");

    @Step("Wait for results table is visible")
    public void waitForResultsIsVisible() {
        waitForElementVisibility(searchResultsTable);
        waitForElementClickable(searchResultsTable);
    }

    @Step("Check is 'Load More Results' button visible")
    public boolean isLoadMoreResultsButtonVisible() {
        return isElementVisible(loadMoreResults);
    }

    @Step("CLick the 'Load More Results' button")
    public void clickLoadMoreResultsButton() {
        click(loadMoreResults);
    }

    @Step("Click the 'Product Type' selector button")
    public void clickProductTypeSelectorButton() {
        click(productTypeSelectorButton);
    }

    @Step("Click the 'Product Type' option")
    public void clickProductTypeOption(String productOption) {
        click(productTypeOption, productOption);
        SelenideTools.sleep(2);
    }

    @Step("Returning list of 'Product Type' options")
    public List<String> getProductTypeList() {
        return getElementsText(searchResultsTableRow);
    }

    @Step("Returning list of 'Account Descriptions' values")
    public List<String> getAccountDescriptionsByAccountType(String accountType, String rateType) {
        return getElementsText(accountDescriptionsByType, accountType, rateType);
    }
}
