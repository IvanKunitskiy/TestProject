package com.nymbus.pages.settings.users;

import com.nymbus.base.BasePage;
import com.nymbus.locator.Locator;
import com.nymbus.locator.XPath;
import io.qameta.allure.Step;

public class UsersSearchPage extends BasePage {

    private Locator overlay = new XPath("//div[contains(@class, 'blockOverlay')]");
    private Locator searchRegion = new XPath("//main[contains(@id, 'usruserssearch')]");
    private Locator cellNyUserData = new XPath("//td[contains(text(), '%s')]");
    private Locator searchField = new XPath("//div[contains(@id, 'usruserssearch-search')]//input[contains(@name, 'search')]");
    private Locator searchButton = new XPath("//div[contains(@id, 'usruserssearch-search')]//button[span[text()='Search']]");


    public void waitViewUsersListVisible(){
        waitForElementVisibility(overlay);
        waitForElementInvisibility(overlay);
    }

    @Step("Wait for 'User search page' loaded")
    public void waitForPageLoaded(){
        waitForElementVisibility(searchRegion);
    }

    @Step("Click on cell with user data")
    public void clickCellByUserData(String userData){
        waitForElementVisibility(cellNyUserData, userData);
        waitForElementClickable(cellNyUserData, userData);
        click(cellNyUserData, userData);
    }

    @Step("Set user data to search field")
    public void setUserDataForSearching(String userData){
        waitForElementClickable(searchField);
        waitForElementVisibility(searchField);
        type(userData, searchField);
    }

    @Step("Click 'Search' field")
    public void clickSearchButton(){
        waitForElementVisibility(searchButton);
        waitForElementClickable(searchButton);
        click(searchButton);
    }

}
