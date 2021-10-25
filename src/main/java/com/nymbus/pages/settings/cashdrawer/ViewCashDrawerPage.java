package com.nymbus.pages.settings.cashdrawer;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class ViewCashDrawerPage extends PageTools {

    /**
     * Controls
     */

    private By overLay = By.xpath("//div[contains(@ng-show, 'isLoading') and contains(@class, 'xwidget_loading_overlay')]");
    private By addNewButton = By.xpath("//a[span[text()='Add New']]");
    private By editButton = By.xpath("//button[contains(@ng-click, 'editForm')]");
    private By searchField = By.xpath("//input[@name='field[search]']");
    private By searchButton = By.xpath("//div[@data-fieldname='searchButton']//button");


    /**
     * Cash drawer data
     */
    private By tableItemByNameColumn = By.xpath("//td[@data-column-id='name' and text()='%s']//..//td[@data-column-id='cashdrawertype']");
    private By name = By.xpath("//div[@id='bank.data.cashdrawer-(databean)name']//span");
    private By cashDrawerType = By.xpath("//div[@id='bank.data.cashdrawer-cashdrawertype']//span[contains(@class,'value')]");
    private By defaultUser = By.xpath("//div[@id='bank.data.cashdrawer-defaultuserid']//span[contains(@class,'value')]");
    private By branch = By.xpath("//div[@id='bank.data.cashdrawer-bankbranchid']//span[contains(@class,'value')]");
    private By location = By.xpath("//div[@id='bank.data.cashdrawer-locationid']//span[contains(@class,'value')]");
    private By glAccountNumber = By.xpath("//div[@id='bank.data.cashdrawer-glaccountid']//span[contains(@class,'value')]");
    private By floating = By.xpath("//div[@id='bank.data.cashdrawer-floating']//input[contains(@name,'floating')]");


    /**
     * Actions with controls
     */
    public void waitViewCashDrawerDataVisible() {
        waitForElementVisibility(overLay);
        waitForElementInvisibility(overLay);
    }

    @Step("Click 'Add New' button")
    public void clickAddNewButton() {
        waitForElementVisibility(addNewButton);
        waitForElementClickable(addNewButton);
        click(addNewButton);
    }

    @Step("Click 'Edit' button")
    public void clickEditButton() {
        waitForElementVisibility(editButton);
        waitForElementClickable(editButton);
        click(editButton);
    }

    @Step("Type username into search field")
    public void typeIntoSearchField(String userLoginId) {
        waitForElementClickable(searchField);
        type(userLoginId, searchField);
    }

    @Step("Click 'Search' button")
    public void clickSearchButton() {
        waitForElementClickable(searchButton);
        click(searchButton);
    }

    /**
     * Cash drawer data
     */
    @Step("Check if user present")
    public boolean isSpecificCashDrawerTypeForSpecificUserPresent(String userLoginId, String cashType) {
        return isElementVisible(tableItemByNameColumn, userLoginId) && getElementText(tableItemByNameColumn, userLoginId).equals(cashType);
    }

    @Step("Get name of cash drawer")
    public String getName() {
        waitForElementVisibility(name);
        return getElementText(name).trim();
    }

    @Step("Get Cash Drawer Type")
    public String getCashDrawerType() {
        waitForElementVisibility(cashDrawerType);
        return getElementText(cashDrawerType).trim();
    }

    @Step("Get Default User")
    public String getDefaultUser() {
        waitForElementVisibility(defaultUser);
        return getElementText(defaultUser).trim();
    }

    @Step("Get Branch")
    public String getBranch() {
        waitForElementVisibility(branch);
        return getElementText(branch).trim();
    }

    @Step("Get Location")
    public String getLocation() {
        waitForElementVisibility(location);
        return getElementText(location).trim();
    }

    @Step("Get GL Account Number")
    public String getGLAccount() {
        waitForElementVisibility(glAccountNumber);
        return getElementText(glAccountNumber).trim();
    }

    @Step("Get Branch")
    public boolean isFloating() {
        return getElementAttributeValue("value", floating).contains("1");
    }

}
