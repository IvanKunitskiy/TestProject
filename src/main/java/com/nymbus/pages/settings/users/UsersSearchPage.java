package com.nymbus.pages.settings.users;

import com.nymbus.base.BasePage;
import com.nymbus.locator.Locator;
import com.nymbus.locator.XPath;
import io.qameta.allure.Step;

public class UsersSearchPage extends BasePage {

    private Locator searchRegion = new XPath("//main[contains(@id, 'usruserssearch')]");
    private Locator cellNyUserData = new XPath("//td[contains(text(), '%s')]");

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

}
