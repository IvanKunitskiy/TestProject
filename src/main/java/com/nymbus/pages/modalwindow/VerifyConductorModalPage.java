package com.nymbus.pages.modalwindow;

import com.codeborne.selenide.Condition;
import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class VerifyConductorModalPage extends PageTools {

    private By modalWindow = By.xpath("//div[@class='modal-content']");
    private By modalWindowHeader = By.xpath("//h4[text()='Verify Conductor']");
    private By okModalButton = By.xpath("//div[contains(@class, 'modal-content')]" +
            "//button[span[text()='OK']]");

    private By formElement = By.xpath("//ng-form");
    private By addNewClientButton = By.xpath("//button//span[text()='Add New Client']");
    private By verifyButton = By.xpath("//button[text()='Verify']");
    private By saveButton = By.xpath("//button[text()='Save']");

    private By expirationDateCalendarIcon = By.xpath("//div[input[@id='expiration']]/div[span[@class='nyb-icon-calendar']]");
    private By birthDateCalendarIcon = By.xpath("//div[input[@id='birthdate']]/div[span[@class='nyb-icon-calendar']]");
    private By birthDateField = By.id("birthdate");

    private By firstNameField = By.xpath("//*[@id='name2']");
    private By lastNameField = By.id("name1");
    private By taxIDField = By.id("taxidnumber");
    private By occupationField = By.xpath("//*[@id='_occupation']");
    private By addressField = By.xpath("//input[@name='addressline1']");
    private By cityField = By.xpath("//input[@name='cityname']");
    private By stateField = By.xpath("//*[@id='states']//span[@class='select2-chosen']/span");
    private By zipCodeField =  By.xpath("//input[@name='zipcode']");
    private By phoneField = By.xpath("//input[@name='fullphonenumber']");
    private By idTypeField = By.xpath("//*[@id='type']//span[@class='select2-chosen']/span");
    private By idNumberField = By.xpath("//input[@name='id']");
    private By issuedByField = By.xpath("//*[@id='state']//span[@class='select2-chosen']/span");
    private By countryField = By.xpath("//*[@id='country']//span[@class='select2-chosen']/span");

    private By idTypeDropdownButton = By.xpath("//div[@name='type']//span[contains(@class, 'select2-arrow')]");
    private By stateDropdownButton = By.xpath("//div[@name='states']//span[contains(@class, 'select2-arrow')]");
    private By countryDropdownButton = By.xpath("//div[@name='country']//span[contains(@class, 'select2-arrow')]");

    private By expirationDateField = By.id("expiration");

    private By itemInDropdown = By.xpath("//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    /**
     * Get client data in view mode region
     */

    @Step("Get 'First Name' value")
    public String getFirstNameValue() {
        waitForElementVisibility(firstNameField);
        return getElementAttributeValue("value", firstNameField);
    }

    @Step("Get 'Last Name' value")
    public String getLastNameValue() {
        waitForElementVisibility(lastNameField);
        return getElementAttributeValue("value", lastNameField);
    }

    @Step("Get 'Tax ID' value")
    public String getTaxIDValue() {
        waitForElementVisibility(taxIDField);
        return getElementAttributeValue("value", taxIDField).trim().replaceAll("[\\W_&&[^°]]+", "");
    }

    @Step("Get 'Occupation' value")
    public String getOccupationValue() {
        waitForElementVisibility(occupationField);
        return getElementAttributeValue("value", occupationField);
    }

    @Step("Get 'Birth date' value")
    public String getBirthDateValue() {
        waitForElementVisibility(birthDateField);
        return getElementAttributeValue("value", birthDateField);
    }

    @Step("Get 'Address' value")
    public String getAddressValue() {
        waitForElementVisibility(addressField);
        return getElementAttributeValue("value", addressField);
    }

    @Step("Get 'City' value")
    public String getCityValue() {
        waitForElementVisibility(cityField);
        return getElementAttributeValue("value", cityField);
    }

    @Step("Get 'State' value")
    public String getStateValue() {
        waitForElementVisibility(stateField);
        return getElementText(stateField);
    }

    @Step("Get 'Zip Code' value")
    public String getZipCodeValue() {
        waitForElementVisibility(zipCodeField);
        return getElementAttributeValue("value", zipCodeField);
    }

    @Step("Get 'Phone' value")
    public String getPhoneValue() {
        waitForElementVisibility(phoneField);
        return getElementAttributeValue("value", phoneField).trim().replaceAll("[\\W_&&[^°]]+", "");
    }

    @Step("Get 'Id Type' value")
    public String getIDTypeValue() {
        waitForElementVisibility(idTypeField);
        return getElementText(idTypeField);
    }

    @Step("Get 'Id Number' value")
    public String getIDNumberValue() {
        waitForElementVisibility(idNumberField);
        return getElementAttributeValue("value", idNumberField);
    }

    @Step("Get 'Issued By' value")
    public String getIssuedByValue() {
        waitForElementVisibility(issuedByField);
        return getElementText(issuedByField);
    }

    @Step("Get 'Country' value")
    public String getCountryValue() {
        waitForElementVisibility(countryField);
        return getElementText(countryField);
    }

    @Step("Get 'Expiration Date' value")
    public String getExpirationDateValue() {
        waitForElementVisibility(expirationDateField);
        return getElementAttributeValue("value", expirationDateField);
    }

    /**
     * End of region
     */

    @Step("Wait for modal window visibility")
    public void waitForModalWindowVisibility() {
        waitForElementVisibility(modalWindow);
        waitForElementVisibility(modalWindowHeader);
        waitForElementVisibility(verifyButton);
    }

    @Step("Wait for verify button visibility")
    public void waitForReadOnlyClientFields() {
        waitForElementVisibility(verifyButton);
        shouldBe(Condition.disabled, firstNameField);
    }

    @Step("Click 'OK' modal button")
    public void clickOkModalButton() {
        waitForElementVisibility(okModalButton);
        jsClick(okModalButton);
    }

    @Step("Type {0} in First name field")
    public void typeFirstName(String firstName) {
        waitForElementVisibility(firstNameField);
        jsType(firstName, firstNameField);
        jsRiseOnchange(firstNameField);
    }

    @Step("Type {0} in Last name field")
    public void typeLastName(String lastName) {
        waitForElementVisibility(lastNameField);
        jsType(lastName, lastNameField);
        jsRiseOnchange(lastNameField);
    }

    @Step("Type {0} in TaxId field")
    public void typeTaxId(String taxId) {
        waitForElementVisibility(taxIDField);
        jsType(taxId, taxIDField);
        jsRiseOnchange(taxIDField);
    }

    @Step("Type {0} in address field")
    public void typeAddressField(String text) {
        waitForElementVisibility(addressField);
        jsType(text, addressField);
        jsRiseOnchange(addressField);
    }

    @Step("Type {0} in city field")
    public void typeCityField(String text) {
        waitForElementVisibility(cityField);
        jsType(text, cityField);
        jsRiseOnchange(cityField);
    }

    @Step("Type {0} in zipCode field")
    public void typeZipCodeField(String text) {
        waitForElementVisibility(zipCodeField);
        jsType(text, zipCodeField);
        jsRiseOnchange(zipCodeField);
    }

    @Step("Type {0} in phone field")
    public void typePhoneField(String text) {
        waitForElementVisibility(phoneField);
        jsType(text, phoneField);
        jsRiseOnchange(phoneField);
    }

    @Step("Type {0} in id number")
    public void typeIdNumberField(String text) {
        waitForElementVisibility(idNumberField);
        jsType(text, idNumberField);
        jsRiseOnchange(idNumberField);
    }

    @Step("Click 'State dropdown' button")
    public void clickStateDropdownButton() {
        waitForElementVisibility(stateDropdownButton);
        jsClick(stateDropdownButton);
    }

    @Step("Click 'Id type dropdown' button")
    public void clickIdTypeDropdownButton() {
        waitForElementVisibility(idTypeDropdownButton);
        jsClick(idTypeDropdownButton);
    }

    @Step("Click 'Country dropdown' button")
    public void clickCountryDropdownButton() {
        waitForElementVisibility(countryDropdownButton);
        jsClick(countryDropdownButton);
    }

    @Step("Click 'Verify' button")
    public void clickVerifyButton() {
        waitForElementVisibility(verifyButton);
        jsClick(verifyButton);
    }

    @Step("Click 'Add new client' button")
    public void clickAddNewClientButton() {
        waitForElementVisibility(addNewClientButton);
        waitForElementClickable(addNewClientButton);
        jsClick(addNewClientButton);
    }

    @Step("Click expiration date calendar icon with js")
    public void clickCalendarIconWithJs() {
        jsClick(expirationDateCalendarIcon);
    }

    @Step("Click birth date calendar icon with js")
    public void clickBirthDateCalendarIconWithJs() {
        jsClick(birthDateCalendarIcon);
    }

    @Step("Wait for first name to be enabled")
    public void waitForFirstNameFieldEnabled() {
        shouldBe(Condition.enabled, firstNameField);
    }

    @Step("Set 'Birth Date' value")
    public void setBirthDateValue(String birthDate) {
        waitForElementVisibility(birthDateField);
        waitForElementClickable(birthDateField);
        jsType(birthDate, birthDateField);
        jsRiseOnchange(birthDateField);
    }

    @Step("Set 'Expiration Date' value")
    public void setExpirationDateValue(String expiration) {
        waitForElementVisibility(expirationDateField);
        waitForElementClickable(expirationDateField);
        jsType(expiration, expirationDateField);
        jsRiseOnchange(expirationDateField);
    }

    @Step("Click item in dropdown")
    public void clickItemInDropdown(String text) {
        waitForElementVisibility(itemInDropdown, text);
        jsClick(itemInDropdown, text);
    }

    @Step("Click Save button")
    public void clickSaveButton() {
        waitForElementVisibility(saveButton);
        jsClick(saveButton);
    }
}