package com.nymbus.pages.settings.cashdrawer;

import com.nymbus.base.BasePage;
import com.nymbus.locator.Locator;
import com.nymbus.locator.XPath;
import io.qameta.allure.Step;

public class CashDrawerSearchPage extends BasePage {

    private Locator overlay = new XPath("//div[contains(@class, 'blockOverlay')]");
    private Locator searchRegion = new XPath("//main[contains(@id, 'cashdrawersearch')]");
    private Locator cellNyUserData = new XPath("//td[contains(text(), '%s')]");
    private Locator searchField = new XPath("//div[contains(@id, 'cashdrawersearch')]//input[contains(@name, 'search')]");
    private Locator searchButton = new XPath("//div[contains(@id, 'cashdrawersearch')]//button[span[text()='Search']]");

	@Step("Wait cash drawer page loaded")
    public void waitViewCashDrawerListVisible(){
        waitForElementVisibility(overlay);
        waitForElementInvisibility(overlay);
    }

    @Step("Wait for 'Cash Drawer search page' loaded")
    public void waitForPageLoaded(){
        waitForElementVisibility(searchRegion);
    }

    @Step("Click on cell with cash drawer data")
    public void clickCellByUserData(String userData){
        waitForElementVisibility(cellNyUserData, userData);
        waitForElementClickable(cellNyUserData, userData);
        click(cellNyUserData, userData);
    }

    @Step("Set cash drawer data to search field")
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
