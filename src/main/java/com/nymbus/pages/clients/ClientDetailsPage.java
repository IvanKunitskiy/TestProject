package com.nymbus.pages.clients;

import com.nymbus.core.base.PageTools;
import com.nymbus.core.utils.SelenideTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.util.List;

public class ClientDetailsPage extends PageTools {

    private By profileForm = By.xpath("//div[@name='profileForm']");
    private By editProfileButton = By.xpath("//button[@ng-click='editCustomerProfile()']");

    /**
     * Tabs button
     */
    private By profileTab = By.id("app-customer-profile");
    private By documentsTab = By.id("app-customer-documents");
    private By accountsTab = By.id("app-customer-accounts");
    private By maintenanceTab = By.id("app-customer-maintenance");

    /**
     * Profile Tab
     */
    private By statusDiv = By.xpath("//div[@id='statusid']");
    private By clientID = By.xpath("//p[@data-test-id='display-customerNumber']");
    private By type = By.xpath("//p[@data-test-id='display-customerType']");
    private By status = By.xpath("//div[@id='statusid']//span[@class='select2-chosen']/span");
    private By organizationName = By.id("name1");
    private By firstName = By.id("name2");
    private By middleName = By.id("middlename");
    private By lastName = By.id("name1");
    private By suffix = By.id("suffix");
    private By maidenFamilyName = By.id("maidenname");
    private By aka_1 = By.id("customerAliasSubform1");
    private By taxPairIdType = By.xpath("//div[@name='taxpayeridtype' and contains(@field-config, 'fieldConfig')]//span[@class='select2-chosen']/span");
    private By taxID = By.id("taxidnumber");
    private By industry = By.xpath("//div[@id='naicsindustry' and contains(@field-config, 'fieldConfig')]//span[contains(@class, 'ng-binding')]");
    private By birthDate = By.id("birthdate");
    private By gender = By.xpath("//div[@name='gender' and contains(@field-config, 'fieldConfig')]//span[contains(@class, 'select2-chosen')]/span");
    private By education = By.xpath("//div[@name='education' and contains(@field-config, 'fieldConfig')]//span[contains(@class, 'select2-chosen')]/span");
    private By income = By.xpath("//div[@name='incomesalary' and contains(@field-config, 'fieldConfig')]//span[contains(@class, 'select2-chosen')]/span");
    private By martialStatus = By.xpath("//div[@name='maritalstatus' and contains(@field-config, 'fieldConfig')]//span[contains(@class, 'select2-chosen')]/span");
    private By occupation = By.id("_occupation");
    private By consumerInformationIndicator = By.xpath("//div[@name='consumerinformationindicator' and contains(@field-config, 'fieldConfig')]//span[contains(@class, 'select2-chosen')]/span");
    private By jobTitle = By.id("jobtitle");
    private By ownOrRent = By.xpath("//div[@name='ownrent' and contains(@field-config, 'fieldConfig')]//span[contains(@class, 'select2-chosen')]/span");
    private By mailCode = By.xpath("//div[@name='mailingcode' and contains(@field-config, 'fieldConfig')]//span[contains(@class, 'select2-chosen')]/span");
    private By selectOfficer = By.xpath("//div[@name='officerid' and contains(@field-config, 'fieldConfig')]//span[contains(@class, 'select2-chosen')]/span");
    private By userDefined_1 = By.id("userdefinedfield1");
    private By userDefined_2 = By.id("userdefinedfield2");
    private By userDefined_3 = By.id("userdefinedfield3");
    private By userDefined_4 = By.id("userdefinedfield4");
    private By phoneType = By.xpath("//div[@name='phoneuse_0' and contains(@field-config, 'phone')]//span[contains(@class, 'select2-chosen')]/span");
    private By phone = By.name("fullphonenumber_0");
    private By emailType = By.xpath("//div[@name='emailuse_0' and contains(@field-config, 'emailSubformConfig')]//span[contains(@class, 'select2-chosen')]/span");
    private By email = By.name("email_0");
    private By addressType = By.xpath("//div[@name='addressuse_0' and contains(@field-config, 'address')]//span[contains(@class, 'select2-chosen')]/span");
    private By addressCountry = By.xpath("//div[@name='country_0' and contains(@field-config, 'address')]//span[contains(@class, 'select2-chosen')]/span");
    private By address = By.name("addressline1_0");
    private By addressCity = By.name("cityname_0");
    private By addressState = By.xpath("//div[@name='states_0']//span[contains(@class, 'select2-chosen')]/span");
    private By addressZipCode = By.name("zipcode_0");

    /**
     * Profile tab address information
     */
    private By addAddressButton = By.xpath("//button[@ng-click='addAddress()']");
    private By addressRows = By.xpath("//tr[@ng-repeat-start='(key, address) in profile.addresses']");
    private By addressType1 = By.xpath("//tr[@class='ng-scope' and @ng-if='address.pageConfig.addressuse.isShow'][%s]" +
            "//span[contains(@class, 'ng-binding') and not(contains(@class, 'ng-hide'))]");
    private By addressCountry1 = By.xpath("//tr[@class='ng-scope' and @ng-if='address.pageConfig.country.isShow'][%s]" +
            "//span[contains(@class, 'ng-binding') and not(contains(@class, 'ng-hide'))]");
    private By address1 = By.xpath("//tr[@class='ng-scope' and @ng-if='address.pageConfig.addressline1.isShow'][%s]//input");
    private By addressCity1 = By.xpath("//tr[@class='ng-scope' and @ng-if='address.pageConfig.cityname.isShow'][%s]//input");
    private By addressState1 = By.xpath("//tr[@class='ng-scope' and @ng-if='address.pageConfig.states.isShow'][%s]" +
             "//span[contains(@class, 'ng-binding') and not(contains(@class, 'ng-hide'))]");
    private By addressZipCode1 =By.xpath("(//tr[@class='ng-scope' and @ng-if='address.pageConfig.zipcode.isShow'][%s]" +
            "//input)[1]");

    /**
     * Profile tab contacts information
     */
    private By contactInformationHeader = By.xpath("//div[@ui-view='contactInformation']//h3");
    private By phoneRows = By.xpath("//div[@ui-view='contactInformation']//tr[@ng-repeat='phone in profile.phones']");
    private By phoneRow = By.xpath("//div[@ui-view='contactInformation']//tr[@ng-repeat='phone in profile.phones'][%s]");
    private By emailRows = By.xpath("//div[@ui-view='contactInformation']//tr[@ng-repeat='email in profile.emails']");
    private By emailRow = By.xpath("//div[@ui-view='contactInformation']//tr[@ng-repeat='email in profile.emails'][%s]");
    private By deletePhoneRowImage = By.xpath("(//div[@ui-view='contactInformation']//tr[@ng-repeat='phone in profile.phones']//button)[%s]");
    private By deleteEmailImage = By.xpath("(//div[@ui-view='contactInformation']//tr[@ng-repeat='email in profile.emails']//button)[%s]");
    private By addPhoneButton = By.xpath("//button[@ng-click='addPhone()']");
    private By addEmailButton = By.xpath("//button[@ng-click='addEmail()']");

    @Step("Wait for contacts header visibility")
    public void waitForContactsHeaderVisibility() {
       waitForElementInvisibility(contactInformationHeader);
    }

    @Step("Wait for phone row {0} invisibility")
    public void waitForPhoneRowInvisibility(int index) {
        waitForElementInvisibility(phoneRow, index);
    }

    @Step("Wait for email row {0} invisibility")
    public void waitForEmailRowInvisibility(int index) {
        waitForElementInvisibility(emailRow, index);
    }

    @Step("Get phones count")
    public int getPhonesRows() {
        waitForElementVisibility(contactInformationHeader);
        return getElementsWithZeroOptionWithWait(1, phoneRows).size();
    }

    @Step("Get emails count")
    public int getEmailsCount() {
        waitForElementVisibility(contactInformationHeader);
        return getElementsWithZeroOptionWithWait(1, emailRows).size();
    }

    @Step("Delete phone row {0}")
    public void deletePhoneRow(int index) {
        waitForElementInvisibility(deletePhoneRowImage, index);
        click(deletePhoneRowImage, index);
    }

    @Step("Delete email row {0}")
    public void deleteEmailRow(int index) {
        waitForElementInvisibility(deleteEmailImage, index);
        click(deleteEmailImage, index);
    }

    @Step("Click add phone button")
    public void clickAddPhoneButton() {
        waitForElementInvisibility(addPhoneButton);
        click(addPhoneButton);
    }

    @Step("Click add email button")
    public void clickAddEmailButton() {
        waitForElementInvisibility(addEmailButton);
        click(addEmailButton);
    }

    /**
     *  Notifications region
     */
    private By notifications = By.xpath("//section[@ui-view='notesNotification']//div[contains(@class, 'notifications-item-text')]");
    private By notification = By.xpath("//section[@ui-view='notesNotification']//div[contains(@class, 'notifications-item-text')][%s]");
    private By notificationCloseButton = By.xpath("//section[@ui-view='notesNotification']//" +
                                        "div[contains(@class, 'notifications-item-close')][%s]");

    @Step("Get Notifications count")
    public int getNotificationCount() {
        return getElements(notifications).size();
    }

    @Step("Click notification {0}")
    public void clickNotificationByIndex(int index) {
        waitForElementClickable(notification, index);
        click(notification, index);
    }

    @Step("Click close notification {0}")
    public void clickCloseNotificationByIndex(int index) {
        waitForElementClickable(notificationCloseButton, index);
        click(notificationCloseButton, index);
    }

    @Step("Wait for notification {0} invisibility")
    public void waitForNotificationInvisibility(int index) {
        waitForElementInvisibility(notification, index);
    }

    /**
     * Profile address methods
     */
    @Step("Click add address button")
    public void clickAddAddressButton() {
        waitForElementClickable(addAddressButton);
        click(addAddressButton);
    }

    @Step("Get address rows count")
    public int getAddressRowsCount() {
        return getElementsWithZeroOptionWithWait(1, addressRows).size();
    }

    @Step("Get 'Address Type' {i} value")
    public String getAddressType1(int i) {
        waitForElementVisibility(addressType1, i);
        return getElementText(addressType1, i).trim();
    }

    @Step("Get 'Address Country' {i} value")
    public String getAddressCountry1(int i) {
        waitForElementVisibility(addressCountry1, i);
        return getElementText(addressCountry1, i).trim();
    }

    @Step("Get 'Address Line 1' {i} value")
    public String getAddress1(int i) {
        waitForElementVisibility(address1, i);
        return getElementAttributeValue("value", address1, i).trim();
    }

    @Step("Get 'Address City' {i} value")
    public String getAddressCity1(int i) {
        waitForElementVisibility(addressCity1, i);
        return getElementAttributeValue("value", addressCity1, i).trim();
    }

    @Step("Get 'Address State' {i} value")
    public String getAddressState1(int i) {
        waitForElementVisibility(addressState1, i);
        return getElementText(addressState1, i).trim();
    }

    @Step("Get 'Address ZipCode' {i} value")
    public String getAddressZipCode1(int i) {
        waitForElementVisibility(addressZipCode1, i);
        return getElementAttributeValue("value", addressZipCode1, i).trim();
    }
    /**
     * Documents Tab
     */
    private By listOfDocumentsRegion = By.xpath("//table[contains(@ng-if, 'documents')]");
    private By listOfDocuments = By.xpath("//tbody/tr[td[i[contains(@class, 'icon-doc') and not(contains(@class,'document-picture'))]]]");
    private By documentType = By.xpath("//tbody/tr[td[i[contains(@class, 'icon-doc') and not(contains(@class,'document-picture'))]]][%s]/td/span");
    private By country = By.xpath("//tbody/tr[td[i[contains(@class, 'icon-doc') and not(contains(@class,'document-picture'))]]][%s]/td[3]");
    private By state = By.xpath("//tbody/tr[td[i[contains(@class, 'icon-doc') and not(contains(@class,'document-picture'))]]][%s]/td[4]");
    private By documentID = By.xpath("//tbody/tr[td[i[contains(@class, 'icon-doc') and not(contains(@class,'document-picture'))]]][%s]/td[5]");
    private By issueDate = By.xpath("//tbody/tr[td[i[contains(@class, 'icon-doc') and not(contains(@class,'document-picture'))]]][%s]/td[6]");
    private By expirationDate = By.xpath("//tbody/tr[td[i[contains(@class, 'icon-doc') and not(contains(@class,'document-picture'))]]][%s]/td[7]");
    private By createdBy = By.xpath("//tbody/tr[td[i[contains(@class, 'icon-doc') and not(contains(@class,'document-picture'))]]][%s]/td[8]");

    /**
     * Accounts Tab
     */
    private By addNewButton = By.id("newAccountOrCreditPlan");
    private By accountOption = By.xpath("//div[contains(@class, 'select2-result-label')]//span[contains(text(), 'Account')]");
    private By creditPlanOption = By.id("//div[contains(@class, 'select2-result-label')]//span[contains(text(), 'Credit Plan')]");
    private By clientAccountByNumber = By.xpath("//span[contains(text(), '%s')]");
    private By addNewList = By.xpath("//div[contains(@class, 'select2-result-label')]//span");
    private By addNewField = By.xpath("//div[@id='newAccountOrCreditPlan']//input[contains(@class, 'nb-select-search')]");
    private By addNewSelectorOption = By.xpath("//div[@id='newAccountOrCreditPlan']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");


    /**
     * Maintenance Tab
     */
    private By newDebitCardButton = By.xpath("//button[span[text()='New Debit Card']]");
    private By viewAllCardsButton = By.xpath("//button[span[text()='View All Cards']]");
    private By cardList = By.xpath("//tbody/tr");
    // Card list
    private By debitCardNumber = By.xpath("//tbody/tr/td[1]//span");
    private By nameOfCardColumn = By.xpath("//tbody/tr[1]/td[2]");
    private By secondLineEmbossingColumn = By.xpath("//tbody/tr[1]/td[3]");
    private By expirationDateColumn = By.xpath("//tbody/tr[1]/td[4]");
    private By cardStatusColumn = By.xpath("//tbody/tr[1]/td[5]");

    @Step("Wait for Client Details page loaded")
    public void waitForPageLoaded() {
        waitForElementVisibility(profileForm);
        waitForElementClickable(profileForm);
    }

    @Step("Wait for Client profile to be editable")
    public void waitForProfileEditable() {
        shouldNotHaveClass("select2-container-disabled", statusDiv);
    }

    @Step("Click 'Edit Profile' button")
    public void clickEditProfileButton() {
        waitForElementVisibility(editProfileButton);
        waitForElementClickable(editProfileButton);
        click(editProfileButton);
    }

    /**
     * Tabs button
     */
    @Step("Click 'Profile' tab")
    public void clickProfileTab() {
        waitForElementVisibility(profileTab);
        waitForElementClickable(profileTab);
        click(profileTab);
    }

    @Step("Click 'Documents' tab")
    public void clickDocumentsTab() {
        waitForElementVisibility(documentsTab);
        waitForElementClickable(documentsTab);
        click(documentsTab);
    }

    @Step("Click 'Accounts' tab")
    public void clickAccountsTab() {
        waitForElementVisibility(accountsTab);
        waitForElementClickable(accountsTab);
        click(accountsTab);
    }


    @Step("Click 'Maintenance' tab")
    public void clickOnMaintenanceTab() {
        waitForElementVisibility(maintenanceTab);
        waitForElementClickable(maintenanceTab);
        click(maintenanceTab);
    }

    /**
     * Profile Tab
     */
    @Step("Get 'Client ID' value")
    public String getClientID() {
        waitForElementVisibility(clientID);
        return getElementText(clientID).trim();
    }

    @Step("Get 'Type' value")
    public String getType() {
        waitForElementVisibility(type);
        return getElementText(type).trim();
    }

    @Step("Get 'Status' value")
    public String getStatus() {
        waitForElementVisibility(status);
        return getElementText(status).trim();
    }

    @Step("Get 'Organization Name' value")
    public String getOrganizationName() {
        waitForElementVisibility(organizationName);
        return getElementAttributeValue("value", organizationName).trim();
    }

    @Step("Get 'First Name' value")
    public String getFirstName() {
        waitForElementVisibility(firstName);
        return getElementAttributeValue("value", firstName).trim();
    }

    @Step("Get 'Middle Name' value")
    public String getMiddleName() {
        waitForElementVisibility(middleName);
        return getElementAttributeValue("value", middleName).trim();
    }

    @Step("Get 'Last Name' value")
    public String getLastName() {
        waitForElementVisibility(lastName);
        return getElementAttributeValue("value", lastName).trim();
    }

    @Step("Get 'Suffix' value")
    public String getSuffix() {
        waitForElementVisibility(suffix);
        return getElementAttributeValue("value", suffix).trim();
    }

    @Step("Get 'Maiden Family Name' value")
    public String getMaidenFamilyName() {
        waitForElementVisibility(maidenFamilyName);
        return getElementAttributeValue("value", maidenFamilyName).trim();
    }

    @Step("Get 'AKA 1' value")
    public String getAka_1() {
        waitForElementVisibility(aka_1);
        return getElementAttributeValue("value", aka_1).trim();
    }

    @Step("Get 'Tax Pair Id Type' value")
    public String getTaxPairIdType() {
        waitForElementVisibility(taxPairIdType);
        return getElementText(taxPairIdType).trim();
    }

    @Step("Get 'Tax ID' value")
    public String getTaxID() {
        waitForElementVisibility(taxID);
        return getElementAttributeValue("value", taxID).trim().replaceAll("[\\W_&&[^°]]+", "");
    }

    @Step("Get 'Industry' value")
    public String getIndustry() {
        return getElementText(industry).trim();
    }

    @Step("Get 'BirthDate' value")
    public String getBirthDate() {
        waitForElementVisibility(birthDate);
        return getElementAttributeValue("value", birthDate).trim();
    }

    @Step("Get 'Gender' value")
    public String getGender() {
//        waitForElementVisibility(gender);
        return getElementText(gender).trim();
    }

    @Step("Get 'Education' value")
    public String getEducation() {
//        waitForElementVisibility(education);
        return getElementText(education).trim();
    }

    @Step("Get 'Income' value")
    public String getIncome() {
//        waitForElementVisibility(income);
        return getElementText(income).trim();
    }

    @Step("Get 'Marital Status' value")
    public String getMaritalStatus() {
//        waitForElementVisibility(martialStatus);
        return getElementText(martialStatus).trim();
    }

    @Step("Get 'Job Title' value")
    public String getOccupation() {
//        waitForElementVisibility(occupation);
        return getElementAttributeValue("value", occupation).trim();
    }

    @Step("Get 'Consumer Information Indicator' value")
    public String getConsumerInformationIndicator() {
//        waitForElementVisibility(consumerInformationIndicator);
        return getElementText(consumerInformationIndicator).trim();
    }

    @Step("Get 'Job Title' value")
    public String getJobTitle() {
//        waitForElementVisibility(jobTitle);
        return getElementAttributeValue("value", jobTitle).trim();
    }

    @Step("Get 'Own or Rent' value")
    public String getOwnOrRent() {
//        waitForElementVisibility(ownOrRent);
        return getElementText(ownOrRent).trim();
    }

    @Step("Get 'Mail Code' value")
    public String getMailCode() {
//        waitForElementVisibility(mailCode);
        return getElementText(mailCode).trim();
    }

    @Step("Get 'Select Officer' value")
    public String getSelectOfficer() {
//        waitForElementVisibility(selectOfficer);
        return getElementText(selectOfficer).trim();
    }

    @Step("Get 'User Defined 1' value")
    public String getUserDefined1() {
//        waitForElementVisibility(userDefined_1);
        return getElementAttributeValue("value", userDefined_1).trim();
    }

    @Step("Get 'User Defined 2' value")
    public String getUserDefined2() {
//        waitForElementVisibility(userDefined_2);
        return getElementAttributeValue("value", userDefined_2).trim();
    }

    @Step("Get 'User Defined 3' value")
    public String getUserDefined3() {
//        waitForElementVisibility(userDefined_3);
        return getElementAttributeValue("value", userDefined_3).trim();
    }

    @Step("Get 'User Defined 4' value")
    public String getUserDefined4() {
//        waitForElementVisibility(userDefined_4);
        return getElementAttributeValue("value", userDefined_4).trim();
    }

    @Step("Get 'Phone Type' value")
    public String getPhoneType() {
//        waitForElementVisibility(phoneType);
        return getElementText(phoneType).trim();
    }

    @Step("Get 'Phone' value")
    public String getPhone() {
//        waitForElementVisibility(phone);
        return getElementAttributeValue("value", phone).trim().replaceAll("[\\W_&&[^°]]+", "");
    }

    @Step("Get 'Email Type' value")
    public String getEmailType() {
//        waitForElementVisibility(emailType);
        return getElementText(emailType).trim();
    }

    @Step("Get 'Email' value")
    public String getEmail() {
//        waitForElementVisibility(email);
        return getElementAttributeValue("value", email).trim();
    }

    @Step("Get 'Address Type' value")
    public String getAddressType() {
//        waitForElementVisibility(addressType);
        return getElementText(addressType).trim();
    }

    @Step("Get 'Country' value")
    public String getAddressCountry() {
//        waitForElementVisibility(addressCountry);
        return getElementText(addressCountry).trim();
    }

    @Step("Get 'Address' value")
    public String getAddress() {
        waitForElementVisibility(address);
        return getElementAttributeValue("value", address).trim();
    }

    @Step("Get 'City' value")
    public String getAddressCity() {
        waitForElementVisibility(addressCity);
        return getElementAttributeValue("value", addressCity).trim();
    }

    @Step("Get 'State' value")
    public String getAddressState() {
        waitForElementVisibility(addressState);
        return getElementText(addressState).trim();
    }

    @Step("Get 'Zip code' value")
    public String getAddressZipCode() {
        waitForElementVisibility(addressZipCode);
        return getElementAttributeValue("value", addressZipCode).trim();
    }

    /**
     * Documents Tab
     */

    @Step("Wait for documents table")
    public void waitForDocumentsTable() {
        waitForElementVisibility(listOfDocumentsRegion);
        waitForElementClickable(listOfDocumentsRegion);
    }

    @Step("Get amount of Documents")
    public int amountOfDocuments() {
        waitForElementVisibility(listOfDocuments);
        return getElements(listOfDocuments).size();
    }

    @Step("Get document type by index")
    public String getDocumentTypeByIndex(int index) {
        waitForElementVisibility(documentType, index);
        return getElementText(documentType, index).trim();
    }

    @Step("Get country by index")
    public String getCountryByIndex(int index) {
        waitForElementVisibility(country, index);
        return getElementText(country, index).trim();
    }

    @Step("Get state by index")
    public String getStateByIndex(int index) {
        waitForElementVisibility(state, index);
        return getElementText(state, index).trim();
    }

    @Step("Get documentID by index")
    public String getDocumentIDByIndex(int index) {
        waitForElementVisibility(documentID, index);
        return getElementText(documentID, index).trim();
    }

    @Step("Get issueDate by index")
    public String getIssueDateByIndex(int index) {
        waitForElementVisibility(issueDate, index);
        return getElementText(issueDate, index).trim();
    }

    @Step("Get expirationDate by index")
    public String getExpirationDateByIndex(int index) {
        waitForElementVisibility(expirationDate, index);
        return getElementText(expirationDate, index).trim();
    }

    @Step("Get createdBy by index")
    public String getCreatedByByIndex(int index) {
        waitForElementVisibility(createdBy, index);
        return getElementText(createdBy, index).trim();
    }

    /**
     * Accounts Tab
     */
    @Step("Click the 'Add New' button")
    public void clickAddNewButton() {
        waitForElementVisibility(addNewButton);
        waitForElementClickable(addNewButton);
        click(addNewButton);
    }

    @Step("Click the 'Account' option in 'Add New' dropdown")
    public void clickAccountOption() {
        waitForElementVisibility(accountOption);
        waitForElementClickable(accountOption);
        click(accountOption);
    }

    @Step("Return the 'Add New' list of options")
    public List<String> getAddNewList() {
        waitForElementVisibility(addNewList);
        waitForElementClickable(addNewList);
        return getElementsText(addNewList);
    }

    @Step("Open account by its number")
    public void openAccountByNumber(String accountNumber) {
        waitForElementVisibility(clientAccountByNumber, accountNumber);
        waitForElementClickable(clientAccountByNumber, accountNumber);
        click(clientAccountByNumber, accountNumber);
    }

    @Step("Click 'Add New' option")
    public void clickAddNewValueOption(String addNewValue) {
        waitForElementVisibility(addNewSelectorOption, addNewValue);
        waitForElementClickable(addNewSelectorOption, addNewValue);
        click(addNewSelectorOption, addNewValue);
    }

    @Step("Click on 'New Debit Card' button")
    public void clickOnNewDebitCardButton() {
        waitForElementVisibility(newDebitCardButton);
        click(newDebitCardButton);
    }

    @Step("Click on 'View All Cards' button")
    public void clickOnViewAllCardsButton() {
        waitForElementVisibility(viewAllCardsButton);
        click(viewAllCardsButton);
    }

    @Step("Checking is card list displayed")
    public boolean isCardListDisplayed() {
        SelenideTools.sleep(5);
        return !getElements(cardList).isEmpty();
    }

    // Card list
    @Step("Getting debit card number")
    public String getDebitCardNumber() {
        waitForElementVisibility(debitCardNumber);
        return getElementText(debitCardNumber);
    }

    @Step("Getting debit card 'Name of Card'")
    public String getNameOfCard() {
        waitForElementVisibility(nameOfCardColumn);
        return getElementText(nameOfCardColumn);
    }

    @Step("Getting debit card 'Second Line Embossing'")
    public String getSecondLineEmbossing() {
        waitForElementVisibility(secondLineEmbossingColumn);
        return getElementText(secondLineEmbossingColumn);
    }

    @Step("Getting debit card 'Second Line Embossing'")
    public String getExpirationDate() {
        waitForElementVisibility(expirationDateColumn);
        return getElementText(expirationDateColumn);
    }

    @Step("Getting debit card 'Card Status'")
    public String getCardStatus() {
        waitForElementVisibility(cardStatusColumn);
        return getElementText(cardStatusColumn);
    }
}