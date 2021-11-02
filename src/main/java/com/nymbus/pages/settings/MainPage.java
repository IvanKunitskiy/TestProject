package com.nymbus.pages.settings;

import com.nymbus.core.base.PageTools;
import com.nymbus.core.utils.SelenideTools;
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

    // Printers
    private By viewAllPrinters = By.xpath("//a[text()='View all Printers']");

    //View COntrols
    private By viewControls = By.xpath("//span[contains(string(),'View Controls')]");

    // Bank Control Settings
    private By viewSettingsLink = By.xpath("//a[span[text()='View Settings']]");

    // Teller Locations
    private By viewAllTellerLocationsLink = By.xpath("//a[text()='View all Teller Locations']");

    //Cashier Defined Transactions
    private By viewAllCDT = By.xpath("//a[text()='View all Cashier Defined Transactions']");

    // Call Class Codes
    private By viewAllCallCodes = By.xpath("//a[text()='View all Call Codes']");

    //Reg CC management
    private By regCCManagement = By.xpath("//h2[text()='Reg CC Management']/..//span");

    @Step("Waiting 'Cash Drawer' region")
    public void waitForCashDrawerRegion() {
        waitForElementVisibility(cashDrawerRegion);
    }

    @Step("Click 'Add New' cash drawer link")
    public void clickAddNewCashDrawerLink() {
        waitForElementClickable(addNewCashDrawerLink);
        click(addNewCashDrawerLink);
    }

    @Step("Click 'View all Printers' link")
    public void clickViewAllPrinters(){
        waitForElementClickable(viewAllPrinters);
        click(viewAllPrinters);
    }

    @Step("Click 'View controls' link")
    public void clickViewControls(){
        waitForElementClickable(viewControls);
        click(viewControls);
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
        SelenideTools.sleep(15);
        scrollToElement(viewProfileLink);
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

    @Step("Click view all CDT templates button")
    public void clickAllCDTButton() {
        scrollToPlaceElementInCenter(viewAllCDT);
        click(viewAllCDT);
    }

    // Call Class Codes
    @Step("Click 'View all Call Class Codes' link")
    public void clickViewAllCallClassCodesLink() {
        scrollToPlaceElementInCenter(viewAllCallCodes);
        click(viewAllCallCodes);
    }

    @Step("Click 'Reg CC manage' button")
    public void clickManageRegCC() {
        waitForElementVisibility(regCCManagement);
        waitForElementClickable(regCCManagement);
        click(regCCManagement);
    }
}