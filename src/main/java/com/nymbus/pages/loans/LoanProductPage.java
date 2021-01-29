package com.nymbus.pages.loans;

import com.nymbus.core.base.PageTools;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class LoanProductPage extends PageTools {

    private By searchButton = By.xpath("//div[@data-fieldname='searchButton']//button/span[text()='Search']");
    private By searchInput = By.xpath("//input[@placeholder='Type to search']");
    private By productRowByDescription = By.xpath("//table/tbody/tr[td[@data-column-id='name' and text()='%s']]");
    private By addNewButton = By.xpath("//section[contains(@class, subheader)]//div[@data-field-id='addNew']//a[text()='Add New']");
    private final By loadMoreResults = By.xpath("//a[@title='Next Page']");
    private final By loanProductByName = By.xpath("//tr/td[@data-column-id='name' and text()='%s']");

    @Step("Check that row with product description is visible")
    public boolean isRowWithProductDescriptionVisible(String productDescription) {
        waitForElementVisibility(productRowByDescription, productDescription);
        return isElementVisible(productRowByDescription, productDescription);
    }

    @Step("Click the 'search' input")
    public void clickSearchInput() {
        click(searchInput);
    }

    @Step("Click the 'Add new' button")
    public void clickAddNewButton() {
        click(addNewButton);
    }

    @Step("Type to search field")
    public void typeToSearchInput(String queryText) {
        waitForElementVisibility(searchInput);
        waitForElementClickable(searchInput);
        typeWithoutWipe(queryText, searchInput);
        SelenideTools.sleep(Constants.MICRO_TIMEOUT);
    }

    @Step("Click the 'Search' button")
    public void clickSearchButton() {
        click(searchButton);
    }

    @Step("Wait for search button is active")
    public void waitForSearchButtonActive() {
        SelenideTools.sleep(Constants.MINI_TIMEOUT);
        waitForElementClickable(searchButton);
    }

    @Step("Check is 'Load More Results' button visible")
    public boolean isLoadMoreResultsButtonVisible() {
        return isElementVisible(loadMoreResults);
    }

    @Step("Click 'Load More Results' button")
    public void clickLoadMoreResultsButton() {
        click(loadMoreResults);
    }

    @Step("Click loan product by name")
    public void clickLoanProductByName(String name) {
        click(loanProductByName, name);
    }

}
