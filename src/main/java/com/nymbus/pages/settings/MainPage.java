package com.nymbus.pages.settings;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class MainPage extends PageTools {

    // User Profile
    private By viewProfileLink = By.xpath("//div[contains(@class, 'profileModule')]//a[text()='View Profile']");

    //Cash Drawer
    private By cashDrawerRegion = By.xpath("//div[div[h2[text()='Cash Drawer']]]");
    private By addNewCashDrawerLink = By.xpath("//div[div[h2[text()='Cash Drawer']]]//span");
    private By searchCashDrawerField = By.xpath("//div[div[h2[text()='Cash Drawer']]]//form/input[@name='productType']");
    private By searchCashDrawerButton = By.xpath("//div[div[h2[text()='Cash Drawer']]]//form/button");
    private By viewAllCashDrawersLink = By.xpath("//div[div[h2[text()='Cash Drawer']]]/div[@class='footer']//a");

    // Users
    private By userRegion = By.xpath("//div[div[h2[text()='Users']]]");
    private By addNewUserLink = By.xpath("//div[div[h2[text()='Users']]]//span");
    private By searchUserField = By.xpath("//div[div[h2[text()='Users']]]//form/input[@name='username']");
    private By searchUserButton = By.xpath("//div[div[h2[text()='Users']]]//form/button");
    private By viewAllUsersLink = By.xpath("//div[div[h2[text()='Users']]]/div[@class='footer']//a");

    // Safe deposit Box region
    private By safeDepositBoxRegion = By.xpath("//div[div[h2[text()='Safe Deposit Box Sizes']]]");
    private By viewAllSafeDepositBoxSizesLink = By.xpath("//div[div[h2[text()='Safe Deposit Box Sizes']]]/div[@class='footer']//a");

    // Bin control region
    private By binControlRegion = By.xpath("//div[div[h2[text()='Bin Control']]]");
    private By viewAllBinControlsLink = By.xpath("//div[div[h2[text()='Bin Control']]]/div[@class='footer']//a");

    // Products
    private By viewAllProducts = By.xpath("//a[text()='View all Products']");

    // Bank Control Settings
    private By viewSettingsLink = By.xpath("//a[span[text()='View Settings']]");

    // Teller Locations
    private By viewAllTellerLocationsLink = By.xpath("//a[text()='View all Teller Locations']");

    @Step("Waiting 'Cash Drawer' region")
    public void waitForCashDrawerRegion() {
        waitForElementVisibility(cashDrawerRegion);
    }

    @Step("Click 'Add New' cash drawer link")
    public void clickAddNewCashDrawerLink() {
        waitForElementClickable(addNewCashDrawerLink);
        click(addNewCashDrawerLink);
    }

    @Step("Setting 'Cash Drawer' to search field")
    public void setCashDrawerToSearchField(String cashDrawer) {
        waitForElementClickable(searchCashDrawerField);
        type(cashDrawer, searchCashDrawerField);
    }

    @Step("Click 'Search' cash drawer button")
    public void clickSearchCashDrawerButton() {
        waitForElementClickable(searchCashDrawerButton);
        click(searchCashDrawerButton);
    }

    @Step("Click 'View all cash drawer' link")
    public void clickViewAllCashDrawerLink() {
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

    @Step("Click 'View Profile' link")
    public void clickViewProfile() {
        waitForElementClickable(viewProfileLink);
        click(viewProfileLink);
    }

    @Step("Click 'View All Products' link")
    public void clickViewAllProducts() {
        waitForElementClickable(viewAllProducts);
        click(viewAllProducts);
    }

    // Safe Deposit Box Sizes
    @Step("Waiting 'ViewAllSafeDepositBoxSizes' region")
    public void waitViewAllSafeDepositBoxSizes() {
        waitForElementVisibility(safeDepositBoxRegion);
    }

    @Step("Click 'ViewAllSafeDepositBoxSizes' link")
    public void clickViewAllSafeDepositBoxSizes() {
        waitForElementClickable(viewAllSafeDepositBoxSizesLink);
        click(viewAllSafeDepositBoxSizesLink);
    }

    // Bin Control
    @Step("Waiting 'ViewAllBinControls' region")
    public void waitViewAllBinControls() {
        waitForElementVisibility(binControlRegion);
    }

    @Step("Click 'ViewAllBinControls' link")
    public void clickViewAllBinControls() {
        waitForElementClickable(viewAllBinControlsLink);
        click(viewAllBinControlsLink);
    }

    // Bank Control Settings
    @Step("Click 'View Settings' link")
    public void clickViewSettingsLink() {
        waitForElementClickable(viewSettingsLink);
        click(viewSettingsLink);
    }

    // Teller locations
    @Step("Click 'View all Teller Locations' link")
    public void clickViewAllTellerLocationsLink() {
        scrollToPlaceElementInCenter(viewAllTellerLocationsLink);
        click(viewAllTellerLocationsLink);
    }
}