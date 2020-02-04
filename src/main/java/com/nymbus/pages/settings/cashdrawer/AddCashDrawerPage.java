package com.nymbus.pages.settings.cashdrawer;

import com.nymbus.base.BasePage;
import com.nymbus.locator.Locator;
import com.nymbus.locator.XPath;
import io.qameta.allure.Step;

import java.util.List;

public class AddCashDrawerPage extends BasePage {

    /**
     * Controls
     */
    private Locator saveChangesButton = new XPath("//button[contains(@class, 'saveAndContinue')]");
    private Locator cancelButton = new XPath("//button[contains(@ng-click, 'cancelForm')]");

    /**
     * Cash drawer data
     */
    private Locator nameField = new XPath("//div[@id='bank.data.cashdrawer-(databean)name']" +
            "//input[@name='field[(databean)name]']");
    private Locator cashDrawerTypeField = new XPath("//div[@id='bank.data.cashdrawer-cashdrawertype']" +
            "//input[@type='text']");
    private Locator cashDrawerTypeList = new XPath("//div[@id='bank.data.cashdrawer-cashdrawertype']" +
            "//li[contains(@class, 'xwidget_item')]/a");
    private Locator cashDrawerTypeSelectorButton = new XPath("//div[@id='bank.data.cashdrawer-cashdrawertype']" +
            "//div[contains(@class, 'action_icon')]");
    private Locator cashDrawerTypeSelectorOption = new XPath("//div[@id='bank.data.cashdrawer-cashdrawertype']" +
            "//ul/li/a[contains(text(),'%s')]");
    private Locator defaultUserField = new XPath("//div[@id='bank.data.cashdrawer-defaultuserid']" +
            "//input[@type='text']");
    private Locator defaultUserList = new XPath("//div[@id='bank.data.cashdrawer-defaultuserid']" +
            "//li[contains(@class, 'xwidget_item')]/a");
    private Locator defaultUserSearchButton = new XPath("//div[@id='bank.data.cashdrawer-defaultuserid']" +
            "//div[contains(@class, 'action_icon')]");
    private Locator defaultUserOption = new XPath("//div[@id='bank.data.cashdrawer-defaultuserid']" +
            "//ul/li/a[contains(text(),'%s')]");
    private Locator brandField = new XPath("//div[@id='bank.data.cashdrawer-bankbranchid']" +
            "//input[@type='text']");
    private Locator brandSelectorButton = new XPath("//div[@id='bank.data.cashdrawer-bankbranchid']" +
            "//div[contains(@class, 'action_icon')]");
    private Locator brandList = new XPath("//div[@id='bank.data.cashdrawer-bankbranchid']" +
            "//li[contains(@class, 'xwidget_item')]/a");
    private Locator brandSelectorOption = new XPath("//div[@id='bank.data.cashdrawer-bankbranchid']" +
            "//ul/li/a[contains(text(),'%s')]");
    private Locator locationField = new XPath("//div[@id='bank.data.cashdrawer-locationid']" +
            "//input[@type='text']");
    private Locator locationList = new XPath("//div[@id='bank.data.cashdrawer-locationid']" +
            "//li[contains(@class, 'xwidget_item')]/a");
    private Locator locationSelectorButton = new XPath("//div[@id='bank.data.cashdrawer-locationid']" +
            "//div[contains(@class, 'action_icon')]");
    private Locator locationSelectorOption = new XPath("//div[@id='bank.data.cashdrawer-locationid']" +
            "//ul/li/a[contains(text(),'%s')]");
    private Locator glAccountNumberField = new XPath("//div[@id='bank.data.cashdrawer-glaccountid']" +
            "//input[@type='text']");
    private Locator glAccountNumberList = new XPath("//div[@id='bank.data.cashdrawer-glaccountid']" +
            "//li[contains(@class, 'xwidget_item')]/a");
    private Locator glAccountNumberSearchButton = new XPath("//div[@id='bank.data.cashdrawer-glaccountid']" +
            "//div[contains(@class, 'action_icon')]");
    private Locator glAccountNumberOption = new XPath("//div[@id='bank.data.cashdrawer-glaccountid']" +
            "//ul/li/a[contains(text(),'%s')]");
    private Locator floatingToggle = new XPath("//div[@id='bank.data.cashdrawer-floating']//div[input[@type='checkbox']]");

    /**
     * Actions with controls
     */
    @Step("Click 'Save changes' button")
    public void clickSaveChangesButton() {
        waitForElementClickable(saveChangesButton);
        click(saveChangesButton);
    }

    @Step("Click 'Cancel' button")
    public void clickCancelButton() {
        waitForElementClickable(cancelButton);
        click(cancelButton);
    }

    /**
     * Filling cash drawer data
     */
    @Step("Set 'Name' value")
    public void setNameValue(String nameValue) {
        waitForElementVisibility(nameField);
        waitForElementClickable(nameField);
        type(nameValue, nameField);
    }

    @Step("Set 'Cash Drawer Type' value")
    public void setCashDrawerTypeValue(String cashDrawerTypeValue) {
        waitForElementVisibility(cashDrawerTypeField);
        waitForElementClickable(cashDrawerTypeField);
        type(cashDrawerTypeValue, cashDrawerTypeField);
    }

    @Step("Click on 'Cash Drawer Type' selector")
    public void clickCashDrawerTypeSelectorButton() {
        waitForElementClickable(cashDrawerTypeSelectorButton);
        click(defaultUserSearchButton);
    }

    @Step("Returning list of cash drawer's type")
    public List<String> getCashDrawerTypeList(){
        waitForElementVisibility(cashDrawerTypeList);
        waitForElementClickable(cashDrawerTypeList);
        return getElementsText(cashDrawerTypeList);
    }

    @Step("Click on 'Cash Drawer Type' option")
    public void clickCashDrawerTypeOption(String cashDrawerTypeOption) {
        waitForElementVisibility(cashDrawerTypeSelectorOption, cashDrawerTypeOption);
        waitForElementClickable(cashDrawerTypeSelectorOption, cashDrawerTypeOption);
        click(cashDrawerTypeSelectorOption, cashDrawerTypeOption);
    }

    @Step("Set 'Default User' value")
    public void setDefaultUserValue(String defaultUserValue) {
        waitForElementVisibility(defaultUserField);
        waitForElementClickable(defaultUserField);
        type(defaultUserValue, defaultUserField);
    }

    @Step("Click on 'Default User' selector")
    public void clickDefaultUserSearchButton() {
        waitForElementClickable(defaultUserSearchButton);
        click(defaultUserSearchButton);
    }

    @Step("Returning list of default users")
    public List<String> getDefaultUserList(){
        waitForElementVisibility(defaultUserList);
        waitForElementClickable(defaultUserList);
        return getElementsText(defaultUserList);
    }

    @Step("Click on 'Default User' option")
    public void clickDefaultUserOption(String defaultUser) {
        waitForElementVisibility(defaultUserOption, defaultUser);
        waitForElementClickable(defaultUserOption, defaultUser);
        click(defaultUserOption, defaultUser);
    }

    @Step("Set 'Branch' value")
    public void setBranchValue(String branchValue) {
        waitForElementVisibility(brandField);
        waitForElementClickable(brandField);
        type(branchValue, brandField);
    }

    @Step("Click on 'Branch' selector")
    public void clickBranchSelectorButton() {
        waitForElementClickable(brandSelectorButton);
        click(brandSelectorButton);
    }

    @Step("Returning list of branchs")
    public List<String> getBranchList(){
        waitForElementVisibility(brandList);
        waitForElementClickable(brandList);
        return getElementsText(brandList);
    }

    @Step("Click on 'Branch' option")
    public void clickBranchOption(String branchOption) {
        waitForElementVisibility(brandSelectorOption, branchOption);
        waitForElementClickable(brandSelectorOption, branchOption);
        click(brandSelectorOption, branchOption);
    }

    @Step("Set 'Location' value")
    public void setLocationValue(String locationValue) {
        waitForElementVisibility(locationField);
        waitForElementClickable(locationField);
        type(locationValue, locationField);
    }

    @Step("Click on 'Location' selector")
    public void clickLocationSelectorButton() {
        waitForElementClickable(locationSelectorButton);
        click(locationSelectorButton);
    }

    @Step("Returning list of locations")
    public List<String> getLocationList(){
        waitForElementVisibility(locationList);
        waitForElementClickable(locationList);
        return getElementsText(locationList);
    }

    @Step("Click on 'Location' option")
    public void clickLocationOption(String locationOption) {
        waitForElementVisibility(locationSelectorOption, locationOption);
        waitForElementClickable(locationSelectorOption, locationOption);
        click(locationSelectorOption, locationOption);
    }

    @Step("Set 'GL Account Number' value")
    public void setGLAccountNumberValue(String glAccountNumberValue) {
        waitForElementVisibility(glAccountNumberField);
        waitForElementClickable(glAccountNumberField);
        type(glAccountNumberValue, glAccountNumberField);
    }

    @Step("Click on 'GL Account Number' selector")
    public void clickGLAccountNumberSearchButton() {
        waitForElementClickable(glAccountNumberSearchButton);
        click(glAccountNumberSearchButton);
    }

    @Step("Returning list of GL Account Number")
    public List<String> getGLAccountNumberList(){
        waitForElementVisibility(glAccountNumberList);
        waitForElementClickable(glAccountNumberList);
        return getElementsText(glAccountNumberList);
    }

    @Step("Click on 'GL Account Number' option")
    public void clickGLAccountNumberOption(String glAccountNumber) {
        waitForElementVisibility(glAccountNumberOption, glAccountNumber);
        waitForElementClickable(glAccountNumberOption, glAccountNumber);
        click(glAccountNumberOption, glAccountNumber);
    }

    @Step("Click 'Floating' toggle")
    public void clickFloatingToggle() {
        waitForElementClickable(floatingToggle);
        click(floatingToggle);
    }

    @Step("Checking is 'Floating' option activated")
    public boolean isFloatingOptionActivated() {
        return getElementAttributeValue("value",
                new XPath("//div[@id='bank.data.cashdrawer-floating']//div[contains(@class, 'field_container')]/input"))
                .contains("1");
    }

}
