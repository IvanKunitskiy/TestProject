package com.nymbus.pages.settings.cashdrawer;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class CashDrawerSearchPage extends PageTools {

    private By overlay = By.xpath("//div[contains(@class, 'blockOverlay')]");
    private By searchRegion = By.xpath("//main[contains(@id, 'cashdrawersearch')]");
    private By cellNyUserData = By.xpath("//td[contains(text(), '%s')]");
    private By searchField = By.xpath("//div[contains(@id, 'cashdrawersearch')]//input[contains(@name, 'search')]");
    private By searchButton = By.xpath("//div[contains(@id, 'cashdrawersearch')]//button[span[text()='Search']]");

    @Step("Wait cash drawer page loaded")
    public void waitViewCashDrawerListVisible() {
//        waitForElementVisibility(overlay);
//        waitForElementInvisibility(overlay);
    }

    @Step("Wait for 'Cash Drawer search page' loaded")
    public void waitForPageLoaded() {
        waitForElementVisibility(searchRegion);
    }

    @Step("Click on cell with cash drawer data")
    public void clickCellByUserData(String userData) {
        waitForElementVisibility(cellNyUserData, userData);
        waitForElementClickable(cellNyUserData, userData);
        click(cellNyUserData, userData);
    }

    @Step("Set cash drawer data to search field")
    public void setUserDataForSearching(String userData) {
        waitForElementVisibility(searchField);
        waitForElementClickable(searchField);
        type(userData, searchField);
    }

    @Step("Click 'Search' field")
    public void clickSearchButton() {
        waitForElementVisibility(searchButton);
        waitForElementClickable(searchButton);
        click(searchButton);
    }

}
