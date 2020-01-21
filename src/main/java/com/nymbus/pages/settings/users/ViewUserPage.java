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

    Locator addNewButton = new XPath("//a[span[text()='Add New']]");
    Locator editButton = new XPath("//button[contains(@ng-click, 'editForm')]");

    /**
     * User data
     */

    Locator firstName = new XPath("//div[@id='usrusers-userfname']//span[@class='xwidget_readonly_value']");
    Locator middleName = new XPath("//div[@id='usrusers-usermname']//span[@class='xwidget_readonly_value']");
    Locator lastName = new XPath("//div[@id='usrusers-userlname']//span[@class='xwidget_readonly_value']");
    Locator userID = new XPath("//div[@id='usrusers-userid']//span[@class='xwidget_readonly_value']");
    Locator initials = new XPath("//div[@id='usrusers-initials']//span[@class='xwidget_readonly_value']");
    Locator branch = new XPath("//div[@id='usrusers-branchid']//span[@class='xwidget_readonly_value']");
    Locator location = new XPath("//div[@id='usrusers-locationid']//span[@class='xwidget_readonly_value']");
    Locator title = new XPath("//div[@id='usrusers-jobtitle']//span[@class='xwidget_readonly_value']");
    Locator taxID = new XPath("//div[@id='usrusers-taxidnumbers']//span[@class='xwidget_readonly_value']");
    Locator phone = new XPath("//div[@id='usrusers-businesstelephone']//span[@class='xwidget_readonly_value']");
    Locator mobile = new XPath("//div[@id='usrusers-othertelephone']//span[@class='xwidget_readonly_value']");
    Locator email = new XPath("//div[@id='usrusers-emailaddress']//span[@class='xwidget_readonly_value']");
    Locator loginID = new XPath("//div[@id='usrusers-loginname']//span[@class='xwidget_readonly_value']");
    Locator loginDisabled = new XPath("//div[@id='usrusers-logindisabledflag']//input[contains(@name, 'logindisabledflag')]");
    Locator roles = new XPath("//div[@id='usrusers-groupid']//span[@class='xwidget_readonly_value']");
    Locator isActive = new XPath("//div[@id='usrusers-inactive']//input[contains(@name, 'inactive')]");
    Locator checkDepositLimit = new XPath("//div[@id='usrusers-checksdepositslimit']//span[@class='xwidget_readonly_value']");
    Locator networkPrinter = new XPath("//div[@id='usrusers-networkprinter']//span[@class='xwidget_readonly_value']");
    Locator officialCheckLimit = new XPath("//div[@id='usrusers-officialcheckslimit']//span[@class='xwidget_readonly_value']");
    Locator cashOutLimit = new XPath("//div[@id='usrusers-cashoutlimit']//span");
    Locator teller = new XPath("//div[@id='usrusers-telleryn']//input[contains(@name, 'teller')]");

    /**
     * Actions with controls
     */

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
        return getElementText(firstName);
    }

    @Step("Get 'Middle name' value")
    public String getMiddleNameValue(){
        return getElementText(middleName);
    }

    @Step("Get 'Last Name' value")
    public String getLastNameValue(){
        return getElementText(lastName);
    }

    @Step("Get 'USERID' value")
    public String getUSERIDValue(){
        return getElementText(userID);
    }

    @Step("Get 'Initials' value")
    public String getInitialsValue(){
        return getElementText(initials);
    }

    @Step("Get 'Branch' value")
    public String getBranchValue(){
        return getElementText(branch);
    }

    @Step("Get 'Location' value")
    public String getLocationValue(){
        return getElementText(location);
    }

    @Step("Get 'Title' value")
    public String getTitleValue(){
        return getElementText(title);
    }

    @Step("Get 'Tax ID' value")
    public String getTaxIDValue(){
        return getElementText(taxID);
    }

    @Step("Get 'Phone' value")
    public String getPhoneValue(){
        return getElementText(phone);
    }

    @Step("Get 'Mobile' value")
    public String getMobileValue(){
        return getElementText(mobile);
    }

    @Step("Get 'Email' value")
    public String getEmailValue(){
        return getElementText(email);
    }

    @Step("Get 'Login ID' value")
    public String getLoginIDValue(){
        return getElementText(loginID);
    }

    @Step("Returning is 'Login Disabled' result")
    public boolean isLoginDisabled(){
        return getElementAttributeValue("value", loginDisabled).contains("1");
    }

    @Step("Get 'Roles' values")
    public List<String> getRolesValue(){
        return getElementsText(roles);
    }

    @Step("Returning is user 'Is Active' result")
    public boolean isUserActive(){
        return getElementAttributeValue("value", isActive).contains("0");
    }

    @Step("Get 'Check Deposit Limit' value")
    public String getCheckDepositLimitValue(){
        return getElementText(checkDepositLimit);
    }

    @Step("Get 'Network Printer' value")
    public String getNetworkPrinterValue(){
        return getElementText(networkPrinter);
    }

    @Step("Get 'Official Check Limit' value")
    public String getOfficialCheckLimitValue(){
        return getElementText(officialCheckLimit);
    }

    @Step("Get 'Cash Out Limit' value")
    public String getCashOutLimitValue(){
        return getElementText(cashOutLimit);
    }

    @Step("Returning is user 'Teller' result")
    public boolean isTeller(){
        return getElementAttributeValue("value", teller).contains("1");
    }

}
