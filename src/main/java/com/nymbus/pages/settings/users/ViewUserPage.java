package com.nymbus.pages.settings.users;

import com.nymbus.base.BasePage;
import com.nymbus.locator.Locator;
import com.nymbus.locator.XPath;
import io.qameta.allure.Step;
import java.util.List;

public class ViewUserPage extends BasePage {

    /**
     * Controls
     */

    private Locator overLay = new XPath("//div[contains(@ng-show, 'isLoading') and contains(@class, 'xwidget_loading_overlay')]");
    private Locator addNewButton = new XPath("//a[span[text()='Add New']]");
    private Locator editButton = new XPath("//button[contains(@ng-click, 'editForm')]");

    /**
     * User data
     */

    private Locator firstName = new XPath("//div[@id='usrusers-userfname']//span[@class='xwidget_readonly_value']");
    private Locator middleName = new XPath("//div[@id='usrusers-usermname']//span[@class='xwidget_readonly_value']");
    private Locator lastName = new XPath("//div[@id='usrusers-userlname']//span[@class='xwidget_readonly_value']");
    private Locator userID = new XPath("//div[@id='usrusers-userid']//span[@class='xwidget_readonly_value']");
    private Locator initials = new XPath("//div[@id='usrusers-initials']//span[@class='xwidget_readonly_value']");
    private Locator branch = new XPath("//div[@id='usrusers-branchid']//span[@class='xwidget_readonly_value']");
    private Locator location = new XPath("//div[@id='usrusers-locationid']//span[@class='xwidget_readonly_value']");
    private Locator title = new XPath("//div[@id='usrusers-jobtitle']//span[@class='xwidget_readonly_value']");
    private Locator taxID = new XPath("//div[@id='usrusers-taxidnumbers']//span[@class='xwidget_readonly_value']");
    private Locator phone = new XPath("//div[@id='usrusers-businesstelephone']//span[@class='xwidget_readonly_value']");
    private Locator mobile = new XPath("//div[@id='usrusers-othertelephone']//span[@class='xwidget_readonly_value']");
    private Locator email = new XPath("//div[@id='usrusers-emailaddress']//span[@class='xwidget_readonly_value']");
    private Locator loginID = new XPath("//div[@id='usrusers-loginname']//span[@class='xwidget_readonly_value']");
    private Locator loginDisabled = new XPath("//div[@id='usrusers-logindisabledflag']//input[contains(@name, 'logindisabledflag')]");
    private Locator roles = new XPath("//div[@id='usrusers-groupid']//span[@class='xwidget_readonly_value']");
    private Locator isActive = new XPath("//div[@id='usrusers-inactive']//input[contains(@name, 'inactive')]");
    private Locator checkDepositLimit = new XPath("//div[@id='usrusers-checksdepositslimit']//span[@class='xwidget_readonly_value']");
    private Locator networkPrinter = new XPath("//div[@id='usrusers-networkprinter']//span[@class='xwidget_readonly_value']");
    private Locator officialCheckLimit = new XPath("//div[@id='usrusers-officialcheckslimit']//span[@class='xwidget_readonly_value']");
    private Locator cashOutLimit = new XPath("//div[@id='usrusers-cashoutlimit']//span");
    private Locator teller = new XPath("//div[@id='usrusers-telleryn']//input[contains(@name, 'teller')]");

    /**
     * Actions with controls
     */

    public void waitViewUserDataVisible(){
        waitForElementVisibility(overLay);
        waitForElementInvisibility(overLay);
    }

    @Step("Click 'Add New' button")
    public void clickAddNewButton() {
        waitForElementClickable(addNewButton);
        click(addNewButton);
    }

    @Step("Click 'Edit' button")
    public void clickEditButton() {
        waitForElementClickable(editButton);
        click(editButton);
    }

    /**
     * Retrieving user data
     */

    @Step("Get 'First Name' value")
    public String getFirstNameValue(){
        waitForElementVisibility(location);
        return getElementText(firstName).trim();
    }

    @Step("Get 'Middle name' value")
    public String getMiddleNameValue(){
        waitForElementVisibility(location);
        return getElementText(middleName).trim();
    }

    @Step("Get 'Last Name' value")
    public String getLastNameValue(){
        waitForElementVisibility(location);
        return getElementText(lastName).trim();
    }

    @Step("Get 'USERID' value")
    public String getUSERIDValue(){
        waitForElementVisibility(location);
        return getElementText(userID).trim();
    }

    @Step("Get 'Initials' value")
    public String getInitialsValue(){
        waitForElementVisibility(location);
        return getElementText(initials).trim();
    }

    @Step("Get 'Branch' value")
    public String getBranchValue(){
        waitForElementVisibility(location);
        return getElementText(branch).trim();
    }

    @Step("Get 'Location' value")
    public String getLocationValue(){
        waitForElementVisibility(location);
        return getElementText(location).trim();
    }

    @Step("Get 'Title' value")
    public String getTitleValue(){
        waitForElementVisibility(location);
        return getElementText(title).trim();
    }

    @Step("Get 'Tax ID' value")
    public String getTaxIDValue(){
        waitForElementVisibility(location);
        return getElementText(taxID).trim();
    }

    @Step("Get 'Phone' value")
    public String getPhoneValue(){
        waitForElementVisibility(location);
        return getElementText(phone).trim().replaceAll("[\\W_&&[^°]]+","");
    }

    @Step("Get 'Mobile' value")
    public String getMobileValue(){
        waitForElementVisibility(location);
        return getElementText(mobile).trim().replaceAll("[\\W_&&[^°]]+","");
    }

    @Step("Get 'Email' value")
    public String getEmailValue(){
        waitForElementVisibility(location);
        return getElementText(email).trim();
    }

    @Step("Get 'Login ID' value")
    public String getLoginIDValue(){
        waitForElementVisibility(location);
        return getElementText(loginID).trim();
    }

    @Step("Returning is 'Login Disabled' result")
    public boolean isLoginDisabled(){
        waitForElementVisibility(location);
        return getElementAttributeValue("value", loginDisabled).contains("1");
    }

    @Step("Get 'Roles' values")
    public List<String> getRolesValue(){
        waitForElementVisibility(location);
        return getElementsText(roles);
    }

    @Step("Returning is user 'Is Active' result")
    public boolean isUserActive(){
        waitForElementVisibility(location);
        return getElementAttributeValue("value", isActive).contains("0");
    }

    @Step("Get 'Check Deposit Limit' value")
    public String getCheckDepositLimitValue(){
        waitForElementVisibility(location);
        return getElementText(checkDepositLimit).trim();
    }

    @Step("Get 'Network Printer' value")
    public String getNetworkPrinterValue(){
        waitForElementVisibility(location);
        return getElementText(networkPrinter).trim();
    }

    @Step("Get 'Official Check Limit' value")
    public String getOfficialCheckLimitValue(){
        waitForElementVisibility(location);
        return getElementText(officialCheckLimit).trim();
    }

    @Step("Get 'Cash Out Limit' value")
    public String getCashOutLimitValue(){
        waitForElementVisibility(location);
        return getElementText(cashOutLimit).trim();
    }

    @Step("Returning is user 'Teller' result")
    public boolean isTeller(){
        waitForElementVisibility(location);
        return getElementAttributeValue("value", teller).contains("1");
    }

}
