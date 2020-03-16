package com.nymbus.pages.settings.users;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class UsersSearchPage extends PageTools {

    private By overlay = By.xpath("//div[contains(@class, 'blockOverlay')]");
    private By searchRegion = By.xpath("//main[contains(@id, 'usruserssearch')]");
    private By cellNyUserData = By.xpath("//td[contains(text(), '%s')]");
    private By searchField = By.xpath("//div[contains(@id, 'usruserssearch-search')]//input[contains(@name, 'search')]");
    private By searchButton = By.xpath("//div[contains(@id, 'usruserssearch-search')]//button[span[text()='Search']]");


    public void waitViewUsersListVisible() {
//        waitForElementVisibility(overlay);
        waitForElementInvisibility(overlay);
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

    @Step("Set user data to search field")
    public void setUserDataForSearching(String userData) {
        waitForElementClickable(searchField);
        waitForElementVisibility(searchField);
        type(userData, searchField);
    }

    @Step("Click 'Search' field")
    public void clickSearchButton() {
        waitForElementVisibility(searchButton);
        waitForElementClickable(searchButton);
        click(searchButton);
    }

}
