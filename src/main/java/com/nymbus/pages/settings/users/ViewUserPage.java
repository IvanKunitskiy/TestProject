package com.nymbus.pages.settings.users;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.util.List;

public class ViewUserPage extends PageTools {

    /**
     * Controls
     */

    private By overLay = By.xpath("//div[contains(@ng-show, 'isLoading') and contains(@class, 'xwidget_loading_overlay')]");
    private By addNewButton = By.xpath("//a[span[text()='Add New']]");
    private By editButton = By.xpath("//button[contains(@ng-click, 'editForm')]");

    /**
     * User data
     */
    private By userDataRegion = By.xpath("//div[@id='usrusers']");
    private By firstName = By.xpath("//div[@id='usrusers-userfname']//span[@class='xwidget_readonly_value']");
    private By middleName = By.xpath("//div[@id='usrusers-usermname']//span[@class='xwidget_readonly_value']");
    private By lastName = By.xpath("//div[@id='usrusers-userlname']//span[@class='xwidget_readonly_value']");
    private By userID = By.xpath("//div[@id='usrusers-userid']//span[@class='xwidget_readonly_value']");
    private By initials = By.xpath("//div[@id='usrusers-initials']//span[@class='xwidget_readonly_value']");
    private By branch = By.xpath("//div[@id='usrusers-branchid']//span[@class='xwidget_readonly_value']");
    private By location = By.xpath("//div[@id='usrusers-locationid']//span[@class='xwidget_readonly_value']");
    private By title = By.xpath("//div[@id='usrusers-jobtitle']//span[@class='xwidget_readonly_value']");
    private By taxID = By.xpath("//div[@id='usrusers-taxidnumbers']//span[@class='xwidget_readonly_value']");
    private By phone = By.xpath("//div[@id='usrusers-businesstelephone']//span[@class='xwidget_readonly_value']");
    private By mobile = By.xpath("//div[@id='usrusers-othertelephone']//span[@class='xwidget_readonly_value']");
    private By email = By.xpath("//div[@id='usrusers-emailaddress']//span[@class='xwidget_readonly_value']");
    private By loginID = By.xpath("//div[@id='usrusers-loginname']//span[@class='xwidget_readonly_value']");
    private By loginDisabled = By.xpath("//div[@id='usrusers-logindisabledflag']//input[contains(@name, 'logindisabledflag')]");
    private By roles = By.xpath("//div[@id='usrusers-groupid']//span[@class='xwidget_readonly_value']");
    private By isActive = By.xpath("//div[@id='usrusers-inactive']//input[contains(@name, 'inactive')]");
    private By checkDepositLimit = By.xpath("//div[@id='usrusers-checksdepositslimit']//span[@class='xwidget_readonly_value']");
    private By networkPrinter = By.xpath("//div[@id='usrusers-networkprinter']//span[@class='xwidget_readonly_value']");
    private By officialCheckLimit = By.xpath("//div[@id='usrusers-officialcheckslimit']//span[@class='xwidget_readonly_value']");
    private By cashOutLimit = By.xpath("//div[@id='usrusers-cashoutlimit']//span");
    private By teller = By.xpath("//div[@id='usrusers-telleryn']//input[contains(@name, 'teller')]");
    private By bankBranch = By.xpath("//div[@id='usrusers-branchid']//span[@class='xwidget_readonly_value']");
    private By cashDrawer = By.xpath("//div[@id='usrusers-cashdrawerid']//span[contains(@class, 'xwidget_readonly_value')]");
    private By cashRecycler = By.xpath("//div[@id='usrusers-cashrecyclerid']//span[contains(@class, 'xwidget_readonly_value')]");
    private By cashDispenser = By.xpath("//div[@id='usrusers-cashdispenserid']//span[contains(@class, 'xwidget_readonly_value')]");

    public void waitForUserDataRegion(){
        waitForElementVisibility(userDataRegion);
        waitForElementVisibility(firstName);
    }

    /**
     * Actions with controls
     */

    public void waitViewUserDataVisible() {
//        waitForElementVisibility(overLay);
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

    /**
     * Retrieving user data
     */

    @Step("Get 'First Name' value")
    public String getFirstNameValue() {
        waitForElementVisibility(location);
        return getElementText(firstName).trim();
    }

    @Step("Get 'Middle name' value")
    public String getMiddleNameValue() {
        waitForElementVisibility(location);
        return getElementText(middleName).trim();
    }

    @Step("Get 'Last Name' value")
    public String getLastNameValue() {
        waitForElementVisibility(location);
        return getElementText(lastName).trim();
    }

    @Step("Get 'USERID' value")
    public String getUSERIDValue() {
        waitForElementVisibility(userID);
        return getElementText(userID).trim();
    }

    @Step("Get 'Initials' value")
    public String getInitialsValue() {
        waitForElementVisibility(initials);
        return getElementText(initials).trim();
    }

    @Step("Get 'Branch' value")
    public String getBranchValue() {
        waitForElementVisibility(branch);
        return getElementText(branch).trim();
    }

    @Step("Get 'Location' value")
    public String getLocationValue() {
        waitForElementVisibility(location);
        return getElementText(location).trim();
    }

    @Step("Get 'Title' value")
    public String getTitleValue() {
        waitForElementVisibility(title);
        return getElementText(title).trim();
    }

    @Step("Get 'Tax ID' value")
    public String getTaxIDValue() {
        waitForElementVisibility(taxID);
        return getElementText(taxID).trim();
    }

    @Step("Get 'Phone' value")
    public String getPhoneValue() {
        waitForElementVisibility(phone);
        return getElementText(phone).trim().replaceAll("[\\W_&&[^°]]+", "");
    }

    @Step("Get 'Mobile' value")
    public String getMobileValue() {
        waitForElementVisibility(mobile);
        return getElementText(mobile).trim().replaceAll("[\\W_&&[^°]]+", "");
    }

    @Step("Get 'Email' value")
    public String getEmailValue() {
        waitForElementVisibility(email);
        return getElementText(email).trim();
    }

    @Step("Get 'Login ID' value")
    public String getLoginIDValue() {
        waitForElementVisibility(loginID);
        return getElementText(loginID).trim();
    }

    @Step("Returning is 'Login Disabled' result")
    public boolean isLoginDisabled() {
//        waitForElementVisibility(loginDisabled);
        return getElementAttributeValue("value", loginDisabled).contains("1");
    }

    @Step("Get 'Roles' values")
    public List<String> getRolesValue() {
        waitForElementVisibility(roles);
        return getElementsText(roles);
    }

    @Step("Returning is user 'Is Active' result")
    public boolean isUserActive() {
//        waitForElementVisibility(isActive);
        return getElementAttributeValue("value", isActive).contains("0");
    }

    @Step("Get 'Check Deposit Limit' value")
    public String getCheckDepositLimitValue() {
        waitForElementVisibility(checkDepositLimit);
        return getElementText(checkDepositLimit).trim();
    }

    @Step("Get 'Network Printer' value")
    public String getNetworkPrinterValue() {
        waitForElementVisibility(networkPrinter);
        return getElementText(networkPrinter).trim();
    }

    @Step("Get 'Official Check Limit' value")
    public String getOfficialCheckLimitValue() {
        waitForElementVisibility(officialCheckLimit);
        return getElementText(officialCheckLimit).trim();
    }

    @Step("Get 'Cash Out Limit' value")
    public String getCashOutLimitValue() {
        waitForElementVisibility(cashOutLimit);
        return getElementText(cashOutLimit).trim();
    }

    @Step("Returning is user 'Teller' result")
    public boolean isTeller() {
//        waitForElementVisibility(teller);
        return getElementAttributeValue("value", teller).contains("1");
    }

    @Step("Wait for 'Cash Drawer' field value")
    public void waitForCashDrawerFieldValue() {
        waitForElementVisibility(cashDrawer);
        shouldNotBeEmpty(cashDrawer);
    }

    @Step("Get 'Cash Drawer' value")
    public String getCashDrawerValue() {
        waitForElementVisibility(cashDrawer);
        return getElementText(cashDrawer).trim();
    }

    @Step("Get 'Bank Branch' value")
    public String getBankBranch() {
        waitForElementVisibility(bankBranch);
        return getElementText(bankBranch).trim();
    }

    @Step("Check if user have 'Cash Recycler'")
    public boolean isCashRecyclerPresent() {
        return isElementVisible(cashRecycler);
    }

    @Step("Check if user have 'Cash Dispenser'")
    public boolean isCashDispenserPresent() {
        return isElementVisible(cashDispenser);
    }
}
