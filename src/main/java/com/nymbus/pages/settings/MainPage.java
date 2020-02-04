package com.nymbus.pages.settings;

import com.nymbus.base.BasePage;
import com.nymbus.locator.Locator;
import com.nymbus.locator.XPath;
import io.qameta.allure.Step;

public class MainPage extends BasePage {

    //Cash Drawer
    private Locator cashDrawerRegion = new XPath("//div[div[h2[text()='Cash Drawer']]]");
    private Locator addNewCashDrawerLink = new XPath("//div[div[h2[text()='Cash Drawer']]]//span");
    private Locator searchCashDrawerField = new XPath("//div[div[h2[text()='Cash Drawer']]]//form/input[@name='productType']");
    private Locator searchCashDrawerButton = new XPath("//div[div[h2[text()='Cash Drawer']]]//form/button");
    private Locator viewAllCashDrawersLink = new XPath("//div[div[h2[text()='Cash Drawer']]]/div[@class='footer']//a");

    // Users
    private Locator userRegion = new XPath("//div[div[h2[text()='Users']]]");
    private Locator addNewUserLink = new XPath("//div[div[h2[text()='Users']]]//span");
    private Locator searchUserField = new XPath("//div[div[h2[text()='Users']]]//form/input[@name='username']");
    private Locator searchUserButton = new XPath("//div[div[h2[text()='Users']]]//form/button");
    private Locator viewAllUsersLink = new XPath("//div[div[h2[text()='Users']]]/div[@class='footer']//a");

    @Step("Waiting 'Cash Drawer' region")
    public void waitForCashDrawerRegion(){
        waitForElementVisibility(cashDrawerRegion);
    }

    @Step("Click 'Add New' cash drawer link")
    public void clickAddNewCashDrawerLink(){
        waitForElementClickable(addNewCashDrawerLink);
        click(addNewCashDrawerLink);
    }

    @Step("Setting 'Cash Drawer' to search field")
    public void setCashDrawerToSearchField(String cashDrawer){
        waitForElementClickable(searchCashDrawerField);
        type(cashDrawer, searchCashDrawerField);
    }

    @Step("Click 'Search' cash drawer button")
    public void clickSearchCashDrawerButton(){
        waitForElementClickable(searchCashDrawerButton);
        click(searchCashDrawerButton);
    }

    @Step("Click 'View all cash drawer' link")
    public void clickViewAllCashDrawerLink(){
        waitForElementClickable(viewAllCashDrawersLink);
        click(viewAllCashDrawersLink);
    }

    //    Users
    @Step("Waiting 'Users' region")
    public void waitForUserRegion() {
        waitForElementVisibility(userRegion);
    }

    @Step("Click 'Add New' user link")
    public void clickAddNewUserLink() {
        waitForElementClickable(addNewUserLink);
        click(addNewUserLink);
    }

    @Step("Setting 'Username' to search field")
    public void setUserNameToSearchField(String userName) {
        waitForElementClickable(searchUserField);
        type(userName, searchUserField);
    }

    @Step("Click 'Search' users button")
    public void clickSearchUserButton() {
        waitForElementClickable(searchUserButton);
        click(searchUserButton);
    }

    @Step("Click 'View all users' link")
    public void clickViewAllUsersLink() {
        waitForElementClickable(viewAllUsersLink);
        click(viewAllUsersLink);
    }

}
