package com.nymbus.pages.settings.users;

import com.nymbus.core.base.PageTools;
import com.nymbus.core.utils.SelenideTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.util.List;

public class AddUsersPage extends PageTools {
    /**
     * Controls
     */
    private By saveChangesButton = By.xpath("//button[contains(@class, 'saveAndContinue')]");
    private By cancelButton = By.xpath("//button[contains(@ng-click, 'cancelForm')]");

    /**
     * User data
     */
    private By firstNameField = By.xpath("//div[@id='usrusers-userfname']" +
            "//input[@type='text' and @class='xwidget_value']");
    private By middleNameField = By.xpath("//div[@id='usrusers-usermname']" +
            "//input[@type='text' and @class='xwidget_value']");
    private By lastNameField = By.xpath("//div[@id='usrusers-userlname']" +
            "//input[@type='text' and @class='xwidget_value']");
    private By initialsField = By.xpath("//div[@id='usrusers-initials']" +
            "//input[@type='text' and @class='xwidget_value']");
    private By profilePhotoField = By.xpath("//div[@id='usrusers-photo']//input[@type='file']");
    private By brandField = By.xpath("//div[@id='usrusers-branchid']" +
            "//input[@type='text']");
    private By brandSelectorButton = By.xpath("//div[@id='usrusers-branchid']" +
            "//div[contains(@class, 'action_icon')]");
    private By brandList = By.xpath("//div[@id='usrusers-branchid']" +
            "//li[contains(@class, 'xwidget_item')]/a");
    private By brandSelectorOption = By.xpath("//div[@id='usrusers-branchid']" +
            "//ul/li/a[contains(text(),'%s')]");
    private By locationField = By.xpath("//div[@id='usrusers-locationid']" +
            "//input[@type='text']");
    private By locationList = By.xpath("//div[@id='usrusers-locationid']" +
            "//li[contains(@class, 'xwidget_item')]/a");
    private By locationSelectorButton = By.xpath("//div[@id='usrusers-locationid']" +
            "//div[contains(@class, 'action_icon')]");
    private By locationSelectorOption = By.xpath("//div[@id='usrusers-locationid']" +
            "//ul/li/a[contains(text(),'%s')]");
    private By titleField = By.xpath("//div[@id='usrusers-jobtitle']//input[@type='text' and @class='xwidget_value']");
    private By addNexTaxIDLink = By.xpath("//div[@id='usrusers-taxidnumbers']//div[contains(@class, 'xwidget_editableGrid_add')]//span[@class='ui-button-text']");
    private By taxIDFieldByIndex = By.xpath("//div[@id='usrusers-taxidnumbers']" +
            "//tr[@data-alias='usrusers.taxidnumber'][%s]//input[@type='text' and @class='xwidget_value']");
    private By deleteTaxIDFieldByIndex = By.xpath("//div[@id='usrusers-taxidnumbers']" +
            "//tr[@data-alias='usrusers.taxidnumber'][%s]//div[contains(@class, 'xwidget_visible')]//button");
    private By phoneField = By.xpath("//div[@id='usrusers-businesstelephone']//input[@type='text' and @class='xwidget_value']");
    private By mobileField = By.xpath("//div[@id='usrusers-othertelephone']//input[@type='text' and @class='xwidget_value']");
    private By emailField = By.xpath("//div[@id='usrusers-emailaddress']//input[@type='text' and @class='xwidget_value']");
    private By loginIDField = By.xpath("//div[@id='usrusers-loginname']//input[@type='text' and @class='xwidget_value']");
    private By loginDisabledToggle = By.xpath("//div[@id='usrusers-logindisabledflag']//div[input[@type='checkbox']]");
    private By addRolesLink = By.xpath("//div[@id='usrusers-groupid']" +
            "//div[contains(@class, 'container-add-field')]/a");
    private By rolesFieldByIndex = By.xpath("//div[@id='usrusers-groupid']" +
            "//div[contains(@class, 'xwidget_single')][%s]//input[@type='text']");
    private By rolesSelectorButtonByIndex = By.xpath("//div[@id='usrusers-groupid']" +
            "//div[contains(@class, 'xwidget_single')][%s]//div[contains(@class, 'action_icon')]");
    private By rolesSelectorOptionByIndex = By.xpath("//div[@id='usrusers-groupid']" +
            "//div[contains(@class, 'xwidget_single')][%s]//ul/li[1]/a[contains(text(),'%s')]");
    private By deleteRolesIcons = By.xpath("//div[@id='usrusers-groupid']//div[@class='xwidget_delete_icon']");
    private By deleteRolesFieldByIndex = By.xpath("//div[@id='usrusers-groupid']" +
            "//div[contains(@class, 'xwidget_single')][%s]/div[@class='xwidget_delete_icon']");
    private By isActiveToggle = By.xpath("//div[@id='usrusers-inactive']//div[input[@type='checkbox']]");
    private By checkDepositLimitField = By.xpath("//div[@id='usrusers-checksdepositslimit']//input[@type='text']");
    private By networkPrinterField = By.xpath("//div[@id='usrusers-networkprinter']" +
            "//input[@type='text']");
    private By networkPrinterSelectorButton = By.xpath("//div[@id='usrusers-networkprinter']" +
            "//div[contains(@class, 'action_icon')]");
    private By networkPrinterSelectorOption = By.xpath("//div[@id='usrusers-networkprinter']" +
            "//ul/li/a[contains(text(),'%s')]");
    private By officialCheckLimitField = By.xpath("//div[@id='usrusers-officialcheckslimit']//input[@type='text']");
    private By cashOutLimitField = By.xpath("//div[@id='usrusers-cashoutlimit']//input[@type='text']");
    private By tellerToggle = By.xpath("//div[@id='usrusers-telleryn']//div[input[@type='checkbox']]");
    private By cashDrawerField = By.xpath("//div[@id='usrusers-cashdrawerid']" +
            "//input[@type='text']");
    private By cashDrawerList = By.xpath("//div[@id='usrusers-cashdrawerid']" +
            "//li[contains(@class, 'xwidget_item')]/a");
    private By cashDrawerSelectorButton = By.xpath("//div[@id='usrusers-cashdrawerid']" +
            "//div[contains(@class, 'action_icon')]");
    private By cashDrawerSelectorOption = By.xpath("//div[@id='usrusers-cashdrawerid']" +
            "//ul/li/a[contains(text(),'%s')]");
    private By addNewCashDrawerLink = By.xpath("//div[@id='usrusers-cashDrawer_addNew']//a");

    // Add Cash Drawer modal
    private By addNewCashDrawerModal = By.xpath("//div[span[text()='Add Cash Drawer']]");
    private By cashDrawerNameModal = By.xpath("//div[@id='usrusers-cashDrawer_name']" +
            "//input[@name='field[cashDrawer_name]']");
    private By cashDrawerTypeField = By.xpath("//div[@id='usrusers-cashDrawer_cashdrawertype']" +
            "//input[@type='text']");
    private By cashDrawerTypeList = By.xpath("//div[@id='usrusers-cashDrawer_cashdrawertype']" +
            "//li[contains(@class, 'xwidget_item')]/a");
    private By cashDrawerTypeSelectorButton = By.xpath("//div[@id='usrusers-cashDrawer_cashdrawertype']" +
            "//div[contains(@class, 'action_icon')]");
    private By cashDrawerTypeSelectorOption = By.xpath("//div[@id='usrusers-cashDrawer_cashdrawertype']" +
            "//ul/li/a[contains(text(),'%s')]");
    private By glAccountNumberField = By.xpath("//div[@id='usrusers-cashDrawer_lglaccountid']" +
            "//input[@type='text']");
    private By glAccountNumberList = By.xpath("//div[@id='usrusers-cashDrawer_lglaccountid']" +
            "//li[contains(@class, 'xwidget_item')]/a");
    private By glAccountNumberSearchButton = By.xpath("//div[@id='usrusers-cashDrawer_lglaccountid']" +
            "//div[contains(@class, 'action_icon')]");
    private By glAccountNumberOption = By.xpath("//div[@id='usrusers-cashDrawer_lglaccountid']" +
            "//ul/li/a[contains(text(),'%s')]");

    private By cancelCashDrawerButton = By.xpath("//div[span[text()='Add Cash Drawer']]/..//button[span[text()='Cancel']]");
    private By addCashDrawerButton = By.xpath("//div[span[text()='Add Cash Drawer']]/..//button[span[text()='Add']]");



    /**
     * Actions with controls
     */

    @Step("Click 'Save changes' button")
    public void clickSaveChangesButton() {
        click(saveChangesButton);
    }

    @Step("Click 'Cancel' button")
    public void clickCancelButton() {
        click(cancelButton);
    }

    /**
     * Filling user data
     */

    @Step("Set 'First Name' value")
    public void setFirstNameValue(String firstNameValue) {
        type(firstNameValue, firstNameField);
    }

    @Step("Set 'Middle Name' value")
    public void setMiddleNameValue(String middleNameValue) {
        type(middleNameValue, middleNameField);
    }

    @Step("Set 'Last Name' value")
    public void setLastNameValue(String lastNameValue) {
        type(lastNameValue, lastNameField);
    }

    @Step("Set 'Initials' value")
    public void setInitialsValue(String initialsValue) {
        type(initialsValue, initialsField);
    }

    @Step("Set 'Profile Photo'")
    public void setProfilePhoto(String photoPath) {
        type(photoPath, profilePhotoField);
    }

    @Step("Set 'Branch' value")
    public void setBranchValue(String branchValue) {
        wipeText(brandField);
        type(branchValue, brandField);
    }

    @Step("Click on 'Branch' selector")
    public void clickBranchSelectorButton() {
        click(brandSelectorButton);
    }

    @Step("Returning list of branchs")
    public List<String> getBranchList(){
        return getElementsText(brandList);
    }

    @Step("Click on 'Branch' option")
    public void clickBranchOption(String branchOption) {
        click(brandSelectorOption, branchOption);
    }

    @Step("Set 'Location' value")
    public void setLocationValue(String locationValue) {
        wipeText(locationField);
        type(locationValue, locationField);
    }

    @Step("Click on 'Location' selector")
    public void clickLocationSelectorButton() {
        click(locationSelectorButton);
    }

    @Step("Returning list of locations")
    public List<String> getLocationList(){
        return getElementsText(locationList);
    }

    @Step("Click on 'Location' option")
    public void clickLocationOption(String locationOption) {
        click(locationSelectorOption, locationOption);
    }

    @Step("Set 'Title' value")
    public void setTitleValue(String titleValue) {
        type(titleValue, titleField);
    }

    @Step("Click 'Add New Tax ID' button")
    public void clickAddNewTaxIDLink() {
        click(addNexTaxIDLink);
    }

    @Step("Set 'New Tax ID' value")
    public void setTaxIDValueByIndex(String taxIDValue, int index) {
        type(taxIDValue, taxIDFieldByIndex, index);
    }

    @Step("Click 'Delete' Tax ID button")
    public void clickDeleteTaxIDLink(int index) {
        click(deleteTaxIDFieldByIndex, index);
    }

    @Step("Set 'Phone' value")
    public void setPhoneValue(String phoneValue) {
        click(phoneField);
        for (char ch:phoneValue.toCharArray()) {
            SelenideTools.sleep(1);
            typeWithoutWipe(String.valueOf(ch), phoneField);
        }
    }

    @Step("Set 'Mobile' value")
    public void setMobileValue(String mobileValue) {
        click(mobileField);
        for (char ch: mobileValue.toCharArray()) {
            SelenideTools.sleep(1);
            typeWithoutWipe(String.valueOf(ch), mobileField);
        }
    }

    @Step("Set 'Email' value")
    public void setEmailValue(String emailValue) {
        type(emailValue, emailField);
    }

    @Step("Set 'Login ID' value")
    public void setLoginIDValue(String loginIDValue) {
        type(loginIDValue, loginIDField);
    }

    @Step("Click 'Login Disabled' toggle")
    public void clickLoginDisabledToggle() {
        click(loginDisabledToggle);
    }

    @Step("Checking is 'Login Disabled' option activated")
    public boolean isLoginDisabledOptionActivated() {
        return getHiddenElementAttributeValue("value",
                By.xpath("//div[@id='usrusers-logindisabledflag']//div[contains(@class, 'field_container')]/input"))
                .contains("1");
    }

    @Step("Click 'Add Roles' link")
    public void clickAddRolesLink() {
        click(addRolesLink);
    }

    @Step("Set 'Roles' value")
    public void setRolesValue(String rolesValue, int index) {
        type(rolesValue, rolesFieldByIndex, index);
    }

    @Step("Click on 'Roles' selector")
    public void clickRolesSelectorButton(int index) {
        click(rolesSelectorButtonByIndex, index);
    }

    @Step("Click on 'Roles' option")
    public void clickRolesOption(String rolesOption, int index) {
        click(rolesSelectorOptionByIndex, index, rolesOption);
    }

    @Step("Get numbers of roles")
    public int getNumberOfRoles(){
        return getElements(deleteRolesIcons).size();
    }

    @Step("Click 'Delete' Role button")
    public void clickDeleteRoleByIndex(int index) {
        click(deleteRolesFieldByIndex, index);
    }

    @Step("Click 'Is Active' toggle")
    public void clickIsActiveToggle() {
        click(isActiveToggle);
    }

    @Step("Checking is 'Is Active' option activated")
    public boolean isIsActiveOptionActivated() {
        return getHiddenElementAttributeValue("value",
                By.xpath("//div[@id='usrusers-inactive']//div[contains(@class, 'field_container')]/input"))
                .contains("0");
    }

    @Step("Set 'Check Deposit Limit' value")
    public void setCheckDepositLimitValue(String checkDepositLimitValue) {
        type(checkDepositLimitValue, checkDepositLimitField);
    }

    @Step("Set 'Network Printer' value")
    public void setNetworkPrinterValue(String networkPrinterValue) {
        type(networkPrinterValue, networkPrinterField);
    }

    @Step("Click on 'Network Printer' selector")
    public void clickNetworkPrinterSelectorButton() {
        click(networkPrinterSelectorButton);
    }

    @Step("Click on 'Network Printer' option")
    public void clickNetworkPrinterOption(String networkPrinterOption) {
        click(networkPrinterSelectorOption, networkPrinterOption);
    }

    @Step("Set 'Official Check Limit' value")
    public void setOfficialCheckLimitValue(String officialCheckLimitValue) {
        type(officialCheckLimitValue, officialCheckLimitField);
    }

    @Step("Set 'Cash Out Limit' value")
    public void setCashOutLimitValue(String cashOutLimitValue) {
        type(cashOutLimitValue, cashOutLimitField);
    }

    @Step("Click 'Teller' toggle")
    public void clickTellerToggle() {
        click(tellerToggle);
    }

    @Step("Checking is 'Teller' option activated")
    public boolean isTellerOptionActivated() {
        return getHiddenElementAttributeValue("value",
                By.xpath("//div[@id='usrusers-telleryn']//div[contains(@class, 'field_container')]/input"))
                .contains("1");
    }

    @Step("Set 'Cash Drawer' value")
    public void setCashDrawerValue(String cashDrawerValue) {
        wipeText(cashDrawerField);
        type(cashDrawerValue, cashDrawerField);
    }

    @Step("Click on 'Cash Drawer' selector")
    public void clickCashDrawerSelectorButton() {
        click(cashDrawerSelectorButton);
    }

    @Step("Returning list of cash drawer")
    public List<String> getCashDrawerList(){
        return getElementsText(cashDrawerList);
    }

    @Step("Click on 'Cash Drawer' option")
    public void clickCashDrawerOption(String cashDrawerOption) {
        click(cashDrawerSelectorOption, cashDrawerOption);
    }

    @Step("Click 'Add New' cash drawer link")
    public void clickAddNewCashDrawerLink() {
        click(addNewCashDrawerLink);
    }

    /**
     * Add Cash Drawer modal
     */
	@Step("Wait Add Cash Drawer modal window")
    public void waitAddNewCashDrawerWindow(){
        waitForElementVisibility(addNewCashDrawerModal);
        waitForElementClickable(addNewCashDrawerModal);
    }

    @Step("Set 'GL Account Number' value")
    public void setGLAccountNumberValue(String glAccountNumberValue) {
        typeWithoutLogs(glAccountNumberValue, glAccountNumberField);
    }

    @Step("Click on 'GL Account Number' selector")
    public void clickGLAccountNumberSearchButton() {
        click(glAccountNumberSearchButton);
    }

    @Step("Returning list of GL Account Number")
    public List<String> getGLAccountNumberList(){
        return getElementsText(glAccountNumberList);
    }

    @Step("Click on 'GL Account Number' option")
    public void clickGLAccountNumberOption(String glAccountNumber) {
        click(glAccountNumberOption, glAccountNumber);
    }

    @Step("Set 'Cash Drawer Name' value")
    public void setCashDrawerNameValue(String nameValue) {
        type(nameValue, cashDrawerNameModal);
    }

    @Step("Set 'Cash Drawer Type' value")
    public void setCashDrawerTypeValue(String cashDrawerTypeValue) {
        wipeText(cashDrawerTypeField);
        type(cashDrawerTypeValue, cashDrawerTypeField);
    }

    @Step("Click on 'Cash Drawer Type' selector")
    public void clickCashDrawerTypeSelectorButton() {
        click(cashDrawerTypeSelectorButton);
    }

    @Step("Returning list of cash drawer's types")
    public List<String> getCashDrawerTypeList(){
        return getElementsText(cashDrawerTypeList);
    }

    @Step("Click on 'Cash Drawer Type' option")
    public void clickCashDrawerTypeOption(String cashDrawerTypeOption) {
        click(cashDrawerTypeSelectorOption, cashDrawerTypeOption);
    }

    @Step("Click 'Cancel' create cash drawer button")
    public void clickCancelAddCashDrawer() {
        click(cancelButton);
    }

    @Step("Click 'Add' create cash drawer button")
    public void clickAddNewCashDrawerButton() {
        click(addCashDrawerButton);
    }
}
