package com.nymbus.pages.modalwindow;

import com.codeborne.selenide.Condition;
import com.nymbus.core.base.PageTools;
import com.nymbus.core.utils.SelenideTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class VerifyConductorModalPage extends PageTools {

    private By modalWindow = By.xpath("//div[@class='modal-content']");
    private By modalWindowHeader = By.xpath("//h4[text()='Verify Conductor']");

    private By formElement = By.xpath("//ng-form");
    private By addNewClientButton = By.xpath("//button//span[text()='Add New Client']");
    private By verifyButton = By.xpath("//button[text()='Verify']");
    private By saveButton = By.xpath("//button[text()='Save']");

    private By expirationDateCalendarIcon = By.xpath("//div[input[@id='expiration']]/div[span[@class='nyb-icon-calendar']]");
    private By birthDateCalendarIcon = By.xpath("//div[input[@id='birthdate']]/div[span[@class='nyb-icon-calendar']]");

    private By firstNameField = By.xpath("//*[@id='name2']");
    private By addressField = By.xpath("//input[@name='addressline1']");
    private By cityField = By.xpath("//input[@name='cityname']");
    private By zipCodeField =  By.xpath("//input[@name='zipcode']");
    private By phoneField = By.xpath("//input[@name='fullphonenumber']");
    private By idNumberField = By.xpath("//input[@name='id']");

    private By idTypeDropdownButton = By.xpath("//div[@name='type']//span[contains(@class, 'select2-arrow')]");
    private By stateDropdownButton = By.xpath("//div[@name='states']//span[contains(@class, 'select2-arrow')]");
    private By countryDropdownButton = By.xpath("//div[@name='country']//span[contains(@class, 'select2-arrow')]");

    private By expirationDateField = By.id("expiration");

    private By itemInDropdown = By.xpath("//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

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

    @Step("Type {0} in address field")
    public void typeAddressField(String text) {
        waitForElementVisibility(addressField);
        type(text, addressField);
    }

    @Step("Type {0} in city field")
    public void typeCityField(String text) {
        waitForElementVisibility(cityField);
        type(text, cityField);
    }

    @Step("Type {0} in zipCode field")
    public void typeZipCodeField(String text) {
        waitForElementVisibility(zipCodeField);
        type(text, zipCodeField);
    }

    @Step("Type {0} in phone field")
    public void typePhoneField(String text) {
        waitForElementVisibility(phoneField);
        type(text, phoneField);
    }

    @Step("Type {0} in id number")
    public void typeIdNumberField(String text) {
        waitForElementVisibility(idNumberField);
        type(text, idNumberField);
    }

    @Step("Click 'State dropdown' button")
    public void clickStateDropdownButton() {
        waitForElementVisibility(stateDropdownButton);
        click(stateDropdownButton);
    }

    @Step("Click 'Id type dropdown' button")
    public void clickIdTypeDropdownButton() {
        waitForElementVisibility(idTypeDropdownButton);
        click(idTypeDropdownButton);
    }

    @Step("Click 'Country dropdown' button")
    public void clickCountryDropdownButton() {
        waitForElementVisibility(countryDropdownButton);
        click(countryDropdownButton);
    }

    @Step("Click 'Verify' button")
    public void clickVerifyButton() {
        waitForElementVisibility(verifyButton);
        click(verifyButton);
    }

    @Step("Click 'Add new client' button")
    public void clickAddNewClientButton() {
        waitForElementVisibility(addNewClientButton);
        waitForElementClickable(addNewClientButton);
        click(addNewClientButton);
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

    @Step("Set 'Expiration Date' value")
    public void setExpirationDateValue(String expiration) {
        waitForElementVisibility(expirationDateField);
        waitForElementClickable(expirationDateField);
        typeWithoutWipe("", expirationDateField);
        SelenideTools.sleep(1);
        typeWithoutWipe(expiration, expirationDateField);
    }

    @Step("Click item in dropdown")
    public void clickItemInDropdown(String text) {
        waitForElementVisibility(itemInDropdown, text);
        click(itemInDropdown, text);
    }

    @Step("Click Save button")
    public void clickSaveButton() {
        waitForElementVisibility(saveButton);
        click(saveButton);
    }
}