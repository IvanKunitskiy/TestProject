package com.nymbus.pages.settings.users;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class UsersSearchPage extends PageTools {

    private By overlay = By.xpath("//div[contains(@class, 'blockOverlay')]");
    private By loadingOverlay = By.xpath("//div[contains(@class, 'loading_overlay')]");
    private By searchRegion = By.xpath("//main[contains(@id, 'usruserssearch')]");
    private By cellNyUserData = By.xpath("//td[contains(text(), '%s')]");
    private By searchField = By.xpath("//div[contains(@id, 'usruserssearch-search')]//input[contains(@name, 'search')]");
    private By searchButton = By.xpath("//div[contains(@id, 'usruserssearch-search')]//button[span[text()='Search']]");


    public void waitViewUsersListVisible() {
//        waitForElementVisibility(overlay);
//        waitForElementInvisibility(overlay);
//        waitForElementVisibility(searchButton);
//        waitForElementClickable(searchButton);
    }

    public void waitViewUsersListLoading() {
        waitForElementVisibility(loadingOverlay);
        waitForElementInvisibility(loadingOverlay);
    }

    @Step("Wait for 'User search page' loaded")
    public void waitForPageLoaded() {
        waitForElementVisibility(searchRegion);
    }

    @Step("Click on cell with user data")
    public void clickCellByUserData(String userData) {
        waitForElementVisibility(cellNyUserData, userData);
        waitForElementClickable(cellNyUserData, userData);
        click(cellNyUserData, userData);
    }

    @Step("Wait for search field")
    public void waitForSearching() {
        waitForElementPresent(searchField);
        waitForElementVisibility(searchField);
    }

    @Step("Set user data to search field")
    public void setUserDataForSearching(String userData) {
        waitForElementVisibility(searchField);
        waitForElementClickable(searchField);
        wipeText(searchField);
        type(userData, searchField);
    }

    @Step("Click 'Search' field")
    public void clickSearchButton() {
        waitForElementVisibility(searchButton);
        waitForElementClickable(searchButton);
        click(searchButton);
    }

}
