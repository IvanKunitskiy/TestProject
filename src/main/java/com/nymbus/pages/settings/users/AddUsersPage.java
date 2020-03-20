package com.nymbus.pages.settings.users;

import com.nymbus.base.BasePage;
import com.nymbus.locator.Locator;
import com.nymbus.locator.XPath;
import io.qameta.allure.Step;

import java.util.List;

public class AddUsersPage extends BasePage {

    /**
     * Controls
     */
    private Locator saveChangesButton = new XPath("//button[contains(@class, 'saveAndContinue')]");
    private Locator cancelButton = new XPath("//button[contains(@ng-click, 'cancelForm')]");

    /**
     * User data
     */
    private Locator firstNameField = new XPath("//div[@id='usrusers-userfname']" +
            "//input[@basicinformation='text' and @class='xwidget_value']");
    private Locator middleNameField = new XPath("//div[@id='usrusers-usermname']" +
            "//input[@basicinformation='text' and @class='xwidget_value']");
    private Locator lastNameField = new XPath("//div[@id='usrusers-userlname']" +
            "//input[@basicinformation='text' and @class='xwidget_value']");
    private Locator initialsField = new XPath("//div[@id='usrusers-initials']" +
            "//input[@basicinformation='text' and @class='xwidget_value']");
    private Locator profilePhotoField = new XPath("//div[@id='usrusers-photo']//input[@basicinformation='file']");
    private Locator brandField = new XPath("//div[@id='usrusers-branchid']" +
            "//input[@basicinformation='text']");
    private Locator brandSelectorButton = new XPath("//div[@id='usrusers-branchid']" +
            "//div[contains(@class, 'action_icon')]");
    private Locator brandList = new XPath("//div[@id='usrusers-branchid']" +
            "//li[contains(@class, 'xwidget_item')]/a");
    private Locator brandSelectorOption = new XPath("//div[@id='usrusers-branchid']" +
            "//ul/li/a[contains(text(),'%s')]");
    private Locator locationField = new XPath("//div[@id='usrusers-locationid']" +
            "//input[@basicinformation='text']");
    private Locator locationList = new XPath("//div[@id='usrusers-locationid']" +
            "//li[contains(@class, 'xwidget_item')]/a");
    private Locator locationSelectorButton = new XPath("//div[@id='usrusers-locationid']" +
            "//div[contains(@class, 'action_icon')]");
    private Locator locationSelectorOption = new XPath("//div[@id='usrusers-locationid']" +
            "//ul/li/a[contains(text(),'%s')]");
    private Locator titleField = new XPath("//div[@id='usrusers-jobtitle']//input[@basicinformation='text' and @class='xwidget_value']");
    private Locator addNexTaxIDLink = new XPath("//div[@id='usrusers-taxidnumbers']//div[contains(@class, 'xwidget_editableGrid_add')]//span[@class='ui-button-text']");
    private Locator taxIDFieldByIndex = new XPath("//div[@id='usrusers-taxidnumbers']" +
            "//tr[@data-alias='usrusers.taxidnumber'][%s]//input[@basicinformation='text' and @class='xwidget_value']");
    private Locator deleteTaxIDFieldByIndex = new XPath("//div[@id='usrusers-taxidnumbers']" +
            "//tr[@data-alias='usrusers.taxidnumber'][%s]//div[contains(@class, 'xwidget_visible')]//button");
    private Locator phoneField = new XPath("//div[@id='usrusers-businesstelephone']//input[@basicinformation='text' and @class='xwidget_value']");
    private Locator mobileField = new XPath("//div[@id='usrusers-othertelephone']//input[@basicinformation='text' and @class='xwidget_value']");
    private Locator emailField = new XPath("//div[@id='usrusers-emailaddress']//input[@basicinformation='text' and @class='xwidget_value']");
    private Locator loginIDField = new XPath("//div[@id='usrusers-loginname']//input[@basicinformation='text' and @class='xwidget_value']");
    private Locator loginDisabledToggle = new XPath("//div[@id='usrusers-logindisabledflag']//div[input[@basicinformation='checkbox']]");
    private Locator addRolesLink = new XPath("//div[@id='usrusers-groupid']" +
            "//div[contains(@class, 'container-add-field')]/a");
    private Locator rolesFieldByIndex = new XPath("//div[@id='usrusers-groupid']" +
            "//div[contains(@class, 'xwidget_single')][%s]//input[@basicinformation='text']");
    private Locator rolesSelectorButtonByIndex = new XPath("//div[@id='usrusers-groupid']" +
            "//div[contains(@class, 'xwidget_single')][%s]//div[contains(@class, 'action_icon')]");
    private Locator rolesSelectorOptionByIndex = new XPath("//div[@id='usrusers-groupid']" +
            "//div[contains(@class, 'xwidget_single')][%s]//ul/li[1]/a[contains(text(),'%s')]");
    private Locator deleteRolesIcons = new XPath("//div[@id='usrusers-groupid']//div[@class='xwidget_delete_icon']");
    private Locator deleteRolesFieldByIndex = new XPath("//div[@id='usrusers-groupid']" +
            "//div[contains(@class, 'xwidget_single')][%s]/div[@class='xwidget_delete_icon']");
    private Locator isActiveToggle = new XPath("//div[@id='usrusers-inactive']//div[input[@basicinformation='checkbox']]");
    private Locator checkDepositLimitField = new XPath("//div[@id='usrusers-checksdepositslimit']//input[@basicinformation='text']");
    private Locator networkPrinterField = new XPath("//div[@id='usrusers-networkprinter']" +
            "//input[@basicinformation='text']");
    private Locator networkPrinterSelectorButton = new XPath("//div[@id='usrusers-networkprinter']" +
            "//div[contains(@class, 'action_icon')]");
    private Locator networkPrinterSelectorOption = new XPath("//div[@id='usrusers-networkprinter']" +
            "//ul/li/a[contains(text(),'%s')]");
    private Locator officialCheckLimitField = new XPath("//div[@id='usrusers-officialcheckslimit']//input[@basicinformation='text']");
    private Locator cashOutLimitField = new XPath("//div[@id='usrusers-cashoutlimit']//input[@basicinformation='text']");
    private Locator tellerToggle = new XPath("//div[@id='usrusers-telleryn']//div[input[@basicinformation='checkbox']]");
    private Locator cashDrawerField = new XPath("//div[@id='usrusers-cashdrawerid']" +
            "//input[@basicinformation='text']");
    private Locator cashDrawerList = new XPath("//div[@id='usrusers-cashdrawerid']" +
            "//li[contains(@class, 'xwidget_item')]/a");
    private Locator cashDrawerSelectorButton = new XPath("//div[@id='usrusers-cashdrawerid']" +
            "//div[contains(@class, 'action_icon')]");
    private Locator cashDrawerSelectorOption = new XPath("//div[@id='usrusers-cashdrawerid']" +
            "//ul/li/a[contains(text(),'%s')]");
    private Locator addNewCashDrawerLink = new XPath("//div[@id='usrusers-cashDrawer_addNew']//a");

    // Add Cash Drawer modal
    private Locator addNewCashDrawerModal = new XPath("//div[span[text()='Add Cash Drawer']]");
    private Locator cashDrawerNameModal = new XPath("//div[@id='usrusers-cashDrawer_name']" +
            "//input[@name='field[cashDrawer_name]']");
    private Locator cashDrawerTypeField = new XPath("//div[@id='usrusers-cashDrawer_cashdrawertype']" +
            "//input[@basicinformation='text']");
    private Locator cashDrawerTypeList = new XPath("//div[@id='usrusers-cashDrawer_cashdrawertype']" +
            "//li[contains(@class, 'xwidget_item')]/a");
    private Locator cashDrawerTypeSelectorButton = new XPath("//div[@id='usrusers-cashDrawer_cashdrawertype']" +
            "//div[contains(@class, 'action_icon')]");
    private Locator cashDrawerTypeSelectorOption = new XPath("//div[@id='usrusers-cashDrawer_cashdrawertype']" +
            "//ul/li/a[contains(text(),'%s')]");
    private Locator glAccountNumberField = new XPath("//div[@id='usrusers-cashDrawer_lglaccountid']" +
            "//input[@basicinformation='text']");
    private Locator glAccountNumberList = new XPath("//div[@id='usrusers-cashDrawer_lglaccountid']" +
            "//li[contains(@class, 'xwidget_item')]/a");
    private Locator glAccountNumberSearchButton = new XPath("//div[@id='usrusers-cashDrawer_lglaccountid']" +
            "//div[contains(@class, 'action_icon')]");
    private Locator glAccountNumberOption = new XPath("//div[@id='usrusers-cashDrawer_lglaccountid']" +
            "//ul/li/a[contains(text(),'%s')]");

    private Locator cancelCashDrawerButton = new XPath("//div[span[text()='Add Cash Drawer']]/..//button[span[text()='Cancel']]");
    private Locator addCashDrawerButton = new XPath("//div[span[text()='Add Cash Drawer']]/..//button[span[text()='Add']]");



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
     * Filling user data
     */

    @Step("Set 'First Name' value")
    public void setFirstNameValue(String firstNameValue) {
        waitForElementVisibility(firstNameField);
        waitForElementClickable(firstNameField);
        type(firstNameValue, firstNameField);
    }

    @Step("Set 'Middle Name' value")
    public void setMiddleNameValue(String middleNameValue) {
        waitForElementVisibility(middleNameField);
        waitForElementClickable(middleNameField);
        type(middleNameValue, middleNameField);
    }

    @Step("Set 'Last Name' value")
    public void setLastNameValue(String lastNameValue) {
        waitForElementVisibility(lastNameField);
        waitForElementClickable(lastNameField);
        type(lastNameValue, lastNameField);
    }

    @Step("Set 'Initials' value")
    public void setInitialsValue(String initialsValue) {
        waitForElementClickable(initialsField);
        type(initialsValue, initialsField);
    }

    @Step("Set 'Profile Photo'")
    public void setProfilePhoto(String photoPath) {
        waitForElementClickable(profilePhotoField);
        type(photoPath, profilePhotoField);
    }

    @Step("Set 'Branch' value")
    public void setBranchValue(String branchValue) {
        waitForElementVisibility(brandField);
        waitForElementClickable(brandField);
        wipeText(brandField);
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
        wipeText(locationField);
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

    @Step("Set 'Title' value")
    public void setTitleValue(String titleValue) {
        waitForElementVisibility(titleField);
        waitForElementClickable(titleField);
        type(titleValue, titleField);
    }

    @Step("Click 'Add New Tax ID' button")
    public void clickAddNewTaxIDLink() {
        waitForElementClickable(addNexTaxIDLink);
        click(addNexTaxIDLink);
    }

    @Step("Set 'New Tax ID' value")
    public void setTaxIDValueByIndex(String taxIDValue, int index) {
        waitForElementVisibility(taxIDFieldByIndex, index);
        waitForElementClickable(taxIDFieldByIndex, index);
        type(taxIDValue, taxIDFieldByIndex, index);
    }

    @Step("Click 'Delete' Tax ID button")
    public void clickDeleteTaxIDLink(int index) {
        waitForElementClickable(deleteTaxIDFieldByIndex, index);
        click(deleteTaxIDFieldByIndex, index);
    }

    @Step("Set 'Phone' value")
    public void setPhoneValue(String phoneValue) {
        waitForElementVisibility(phoneField);
        waitForElementClickable(phoneField);
        click(phoneField);
        for (char ch:phoneValue.toCharArray()) {
            wait(1);
            typeWithoutWipe(String.valueOf(ch), phoneField);
        }
    }

    @Step("Set 'Mobile' value")
    public void setMobileValue(String mobileValue) {
        waitForElementVisibility(mobileField);
        waitForElementClickable(mobileField);
        click(mobileField);
        for (char ch: mobileValue.toCharArray()) {
            wait(1);
            typeWithoutWipe(String.valueOf(ch), mobileField);
        }
    }

    @Step("Set 'Email' value")
    public void setEmailValue(String emailValue) {
        waitForElementVisibility(emailField);
        waitForElementClickable(emailField);
        type(emailValue, emailField);
    }

    @Step("Set 'Login ID' value")
    public void setLoginIDValue(String loginIDValue) {
        waitForElementVisibility(loginIDField);
        waitForElementClickable(loginIDField);
        type(loginIDValue, loginIDField);
    }

    @Step("Click 'Login Disabled' toggle")
    public void clickLoginDisabledToggle() {
        waitForElementClickable(loginDisabledToggle);
        click(loginDisabledToggle);
    }

    @Step("Checking is 'Login Disabled' option activated")
    public boolean isLoginDisabledOptionActivated() {
        return getElementAttributeValue("value",
                new XPath("//div[@id='usrusers-logindisabledflag']//div[contains(@class, 'field_container')]/input"))
                .contains("1");
    }

    @Step("Click 'Add Roles' link")
    public void clickAddRolesLink() {
        waitForElementClickable(addRolesLink);
        click(addRolesLink);
    }

    @Step("Set 'Roles' value")
    public void setRolesValue(String rolesValue, int index) {
        waitForElementVisibility(rolesFieldByIndex, index);
        waitForElementClickable(rolesFieldByIndex, index);
        type(rolesValue, rolesFieldByIndex, index);
    }

    @Step("Click on 'Roles' selector")
    public void clickRolesSelectorButton(int index) {
        waitForElementClickable(rolesSelectorButtonByIndex, index);
        click(rolesSelectorButtonByIndex, index);
    }

    @Step("Click on 'Roles' option")
    public void clickRolesOption(String rolesOption, int index) {
        waitForElementVisibility(rolesSelectorOptionByIndex, index, rolesOption);
        waitForElementClickable(rolesSelectorOptionByIndex, index, rolesOption);
        click(rolesSelectorOptionByIndex, index, rolesOption);
    }

    @Step("Get numbers of roles")
    public int getNumberOfRoles(){
        return getElements(deleteRolesIcons).size();
    }

    @Step("Click 'Delete' Role button")
    public void clickDeleteRoleByIndex(int index) {
        waitForElementClickable(deleteRolesFieldByIndex, index);
        click(deleteRolesFieldByIndex, index);
    }

    @Step("Click 'Is Active' toggle")
    public void clickIsActiveToggle() {
        waitForElementClickable(isActiveToggle);
        click(isActiveToggle);
    }

    @Step("Checking is 'Is Active' option activated")
    public boolean isIsActiveOptionActivated() {
        return getElementAttributeValue("value",
                new XPath("//div[@id='usrusers-inactive']//div[contains(@class, 'field_container')]/input"))
                .contains("0");
    }

    @Step("Set 'Check Deposit Limit' value")
    public void setCheckDepositLimitValue(String checkDepositLimitValue) {
        waitForElementVisibility(checkDepositLimitField);
        waitForElementClickable(checkDepositLimitField);
        type(checkDepositLimitValue, checkDepositLimitField);
    }

    @Step("Set 'Network Printer' value")
    public void setNetworkPrinterValue(String networkPrinterValue) {
        waitForElementVisibility(networkPrinterField);
        waitForElementClickable(networkPrinterField);
        type(networkPrinterValue, networkPrinterField);
    }

    @Step("Click on 'Network Printer' selector")
    public void clickNetworkPrinterSelectorButton() {
        waitForElementClickable(networkPrinterSelectorButton);
        click(networkPrinterSelectorButton);
    }

    @Step("Click on 'Network Printer' option")
    public void clickNetworkPrinterOption(String networkPrinterOption) {
        waitForElementVisibility(networkPrinterSelectorOption, networkPrinterOption);
        waitForElementClickable(networkPrinterSelectorOption, networkPrinterOption);
        click(networkPrinterSelectorOption, networkPrinterOption);
    }

    @Step("Set 'Official Check Limit' value")
    public void setOfficialCheckLimitValue(String officialCheckLimitValue) {
        waitForElementVisibility(officialCheckLimitField);
        waitForElementClickable(officialCheckLimitField);
        type(officialCheckLimitValue, officialCheckLimitField);
    }

    @Step("Set 'Cash Out Limit' value")
    public void setCashOutLimitValue(String cashOutLimitValue) {
        waitForElementVisibility(cashOutLimitField);
        waitForElementClickable(cashOutLimitField);
        type(cashOutLimitValue, cashOutLimitField);
    }

    @Step("Click 'Teller' toggle")
    public void clickTellerToggle() {
        waitForElementClickable(tellerToggle);
        click(tellerToggle);
    }

    @Step("Checking is 'Teller' option activated")
    public boolean isTellerOptionActivated() {
        return getElementAttributeValue("value",
                new XPath("//div[@id='usrusers-telleryn']//div[contains(@class, 'field_container')]/input"))
                .contains("1");
    }

    @Step("Set 'Cash Drawer' value")
    public void setCashDrawerValue(String cashDrawerValue) {
        waitForElementVisibility(cashDrawerField);
        waitForElementClickable(cashDrawerField);
        wipeText(cashDrawerField);
        type(cashDrawerValue, cashDrawerField);
    }

    @Step("Click on 'Cash Drawer' selector")
    public void clickCashDrawerSelectorButton() {
        waitForElementClickable(cashDrawerSelectorButton);
        click(cashDrawerSelectorButton);
    }

    @Step("Returning list of cash drawer")
    public List<String> getCashDrawerList(){
        waitForElementVisibility(cashDrawerList);
        waitForElementClickable(cashDrawerList);
        return getElementsText(cashDrawerList);
    }

    @Step("Click on 'Cash Drawer' option")
    public void clickCashDrawerOption(String cashDrawerOption) {
        waitForElementVisibility(cashDrawerSelectorOption, cashDrawerOption);
        waitForElementClickable(cashDrawerSelectorOption, cashDrawerOption);
        click(cashDrawerSelectorOption, cashDrawerOption);
    }

    @Step("Click 'Add New' cash drawer link")
    public void clickAddNewCashDrawerLink() {
        waitForElementClickable(addNewCashDrawerLink);
        click(addNewCashDrawerLink);
    }

    // Add Cash Drawer modal

	@Step("Wait Add Cash Drawer modal window")
    public void waitAddNewCashDrawerWindow(){
        waitForElementVisibility(addNewCashDrawerModal);
        waitForElementClickable(addNewCashDrawerModal);
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

    @Step("Set 'Cash Drawer Name' value")
    public void setCashDrawerNameValue(String nameValue) {
        waitForElementVisibility(cashDrawerNameModal);
        waitForElementClickable(cashDrawerNameModal);
        type(nameValue, cashDrawerNameModal);
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
        click(cashDrawerTypeSelectorButton);
    }

    @Step("Returning list of cash drawer's basicinformation")
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

    @Step("Click 'Cancel' create cash drawer button")
    public void clickCancelAddCashDrawer() {
        waitForElementVisibility(cancelButton);
        waitForElementClickable(cancelButton);
        click(cancelButton);
    }

    @Step("Click 'Add' create cash drawer button")
    public void clickAddNewCashDrawerButton() {
        waitForElementVisibility(addCashDrawerButton);
        waitForElementClickable(addCashDrawerButton);
        click(addCashDrawerButton);
    }
}
