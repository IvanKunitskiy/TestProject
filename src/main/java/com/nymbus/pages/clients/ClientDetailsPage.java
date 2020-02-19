package com.nymbus.pages.clients;

import com.nymbus.base.BasePage;
import com.nymbus.locator.ID;
import com.nymbus.locator.Locator;
import com.nymbus.locator.Name;
import com.nymbus.locator.XPath;
import io.qameta.allure.Step;

public class ClientDetailsPage extends BasePage {

    private Locator profileForm = new XPath("//div[@name='profileForm']");

    /**
     * Tabs button
     */
    private Locator profileTab = new ID("app-customer-profile");
    private Locator documentsTab = new ID("app-customer-documents");

    /**
     * Profile Tab
     */
    private Locator clientID = new XPath("//p[@data-test-id='display-customerNumber']");
    private Locator type = new XPath("//div[@id='typeid_person']//span[@class='select2-chosen']/span");
    private Locator status = new XPath("//div[@id='statusid']//span[@class='select2-chosen']/span");
    private Locator firstName = new ID("name2");
    private Locator middleName = new ID("middlename");
    private Locator lastName = new ID("name1");
    private Locator suffix = new ID("suffix");
    private Locator maidenFamilyName = new ID("maidenname");
    private Locator aka_1 = new ID("customerAliasSubform1");
    private Locator taxPairIdType = new XPath("//div[@name='taxpayeridtype' and contains(@field-config, 'fieldConfig')]//span[@class='select2-chosen']/span");
    private Locator taxID = new ID("taxidnumber");
    private Locator birthDate = new ID("birthdate");
    private Locator gender = new XPath("//div[@name='gender' and contains(@field-config, 'fieldConfig')]//span[contains(@class, 'select2-chosen')]/span");
    private Locator education = new XPath("//div[@name='education' and contains(@field-config, 'fieldConfig')]//span[contains(@class, 'select2-chosen')]/span");
    private Locator income = new XPath("//div[@name='incomesalary' and contains(@field-config, 'fieldConfig')]//span[contains(@class, 'select2-chosen')]/span");
    private Locator martialStatus = new XPath("//div[@name='maritalstatus' and contains(@field-config, 'fieldConfig')]//span[contains(@class, 'select2-chosen')]/span");
    private Locator occupation = new ID("_occupation");
    private Locator consumerInformationIndicator = new XPath("//div[@name='consumerinformationindicator' and contains(@field-config, 'fieldConfig')]//span[contains(@class, 'select2-chosen')]/span");
    private Locator jobTitle = new ID("jobtitle");
    private Locator ownOrRent = new XPath("//div[@name='ownrent' and contains(@field-config, 'fieldConfig')]//span[contains(@class, 'select2-chosen')]/span");
    private Locator mailCode = new XPath("//div[@name='mailingcode' and contains(@field-config, 'fieldConfig')]//span[contains(@class, 'select2-chosen')]/span");
    private Locator selectOfficer = new XPath("//div[@name='officerid' and contains(@field-config, 'fieldConfig')]//span[contains(@class, 'select2-chosen')]/span");
    private Locator userDefined_1 = new ID("userdefinedfield1");
    private Locator userDefined_2 = new ID("userdefinedfield2");
    private Locator userDefined_3 = new ID("userdefinedfield3");
    private Locator userDefined_4 = new ID("userdefinedfield4");
    private Locator phoneType = new XPath("//div[@name='phoneuse_0' and contains(@field-config, 'phone')]//span[contains(@class, 'select2-chosen')]/span");
    private Locator phone = new Name("fullphonenumber_0");
    private Locator emailType = new XPath("//div[@name='emailuse_0' and contains(@field-config, 'emailSubformConfig')]//span[contains(@class, 'select2-chosen')]/span");
    private Locator email = new Name("email_0");
    private Locator addressType = new XPath("//div[@name='addressuse_0' and contains(@field-config, 'address')]//span[contains(@class, 'select2-chosen')]/span");
    private Locator addressCountry = new XPath("//div[@name='country_0' and contains(@field-config, 'address')]//span[contains(@class, 'select2-chosen')]/span");
    private Locator address = new Name("addressline1_0");
    private Locator addressCity = new Name("cityname_0");
    private Locator addressState = new XPath("//div[@name='states_0']//span[contains(@class, 'select2-chosen')]/span");
    private Locator addressZipCode = new Name("zipcode_0");

    /**
     * Documents Tab
     */
    private Locator listOfDocumentsRegion = new XPath("//table[contains(@ng-if, 'documents')]");
    private Locator listOfDocuments = new XPath("//tbody/tr[td[i[contains(@class, 'icon-doc') and not(contains(@class,'document-picture'))]]]");
    private Locator documentType = new XPath("//tbody/tr[td[i[contains(@class, 'icon-doc') and not(contains(@class,'document-picture'))]]][%s]/td/span");
    private Locator country = new XPath("//tbody/tr[td[i[contains(@class, 'icon-doc') and not(contains(@class,'document-picture'))]]][%s]/td[3]");
    private Locator state = new XPath("//tbody/tr[td[i[contains(@class, 'icon-doc') and not(contains(@class,'document-picture'))]]][%s]/td[4]");
    private Locator documentID = new XPath("//tbody/tr[td[i[contains(@class, 'icon-doc') and not(contains(@class,'document-picture'))]]][%s]/td[5]");
    private Locator issueDate = new XPath("//tbody/tr[td[i[contains(@class, 'icon-doc') and not(contains(@class,'document-picture'))]]][%s]/td[6]");
    private Locator expirationDate = new XPath("//tbody/tr[td[i[contains(@class, 'icon-doc') and not(contains(@class,'document-picture'))]]][%s]/td[7]");
    private Locator createdBy = new XPath("//tbody/tr[td[i[contains(@class, 'icon-doc') and not(contains(@class,'document-picture'))]]][%s]/td[8]");

    @Step("Wait for Client Details page loaded")
    public void waitForPageLoaded(){
        waitForElementVisibility(profileForm);
        waitForElementClickable(profileForm);
    }

    /**
     * Tabs button
     */
    @Step("Click 'Profile' tab")
    public void clickProfileTab(){
        waitForElementVisibility(profileTab);
        waitForElementClickable(profileTab);
        click(profileTab);
    }

    @Step("Click 'Documents' tab")
    public void clickDocumentsTab(){
        waitForElementVisibility(documentsTab);
        waitForElementClickable(documentsTab);
        click(documentsTab);
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
        return getElementAttributeValue("value", taxID).trim().replaceAll("[\\W_&&[^°]]+","");
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
        return getElementAttributeValue("value", phone).trim().replaceAll("[\\W_&&[^°]]+","");
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
    public void waitForDocumentsTable(){
        waitForElementVisibility(listOfDocumentsRegion);
        waitForElementClickable(listOfDocumentsRegion);
    }

    @Step("Get amount of Documents")
    public int amountOfDocuments(){
        waitForElementVisibility(listOfDocuments);
        return getElements(listOfDocuments).size();
    }

    @Step("Get document type by index")
    public String getDocumentTypeByIndex(int index){
        waitForElementVisibility(documentType, index);
        return getElementText(documentType, index).trim();
    }

    @Step("Get country by index")
    public String getCountryByIndex(int index){
        waitForElementVisibility(country, index);
        return getElementText(country, index).trim();
    }

    @Step("Get state by index")
    public String getStateByIndex(int index){
        waitForElementVisibility(state, index);
        return getElementText(state, index).trim();
    }

    @Step("Get documentID by index")
    public String getDocumentIDByIndex(int index){
        waitForElementVisibility(documentID, index);
        return getElementText(documentID, index).trim();
    }

    @Step("Get issueDate by index")
    public String getIssueDateByIndex(int index){
        waitForElementVisibility(issueDate, index);
        return getElementText(issueDate, index).trim();
    }

    @Step("Get expirationDate by index")
    public String getExpirationDateByIndex(int index){
        waitForElementVisibility(expirationDate, index);
        return getElementText(expirationDate, index).trim();
    }

    @Step("Get createdBy by index")
    public String getCreatedByByIndex(int index){
        waitForElementVisibility(createdBy, index);
        return getElementText(createdBy, index).trim();
    }

}
