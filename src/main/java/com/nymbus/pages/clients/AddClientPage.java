package com.nymbus.pages.clients;

import com.nymbus.base.BasePage;
import com.nymbus.locator.ID;
import com.nymbus.locator.Locator;
import com.nymbus.locator.XPath;
import io.qameta.allure.Step;

import java.util.List;

public class AddClientPage extends BasePage {


    private Locator addNewClientRegion = new XPath("//main[contains(@class, 'customerNew')]");
    private Locator saveAndContinueButton = new XPath("//button[contains(@class,'saveAndContinue')]");
    private Locator cancelButton = new XPath("//button[contains(@class,'btnCancel')]");
    private Locator modalWindow = new XPath("//div[contains(@class, 'modal-content')]");
    private Locator verificationCheckIcon = new XPath("//div[contains(@class, 'modal-content')]" +
            "//div/i[contains(@class, 'nyb-icon-verificationok')]");
    private Locator okModalButton = new XPath("//div[contains(@class, 'modal-content')]" +
            "//button[span[text()='OK']]");

    /**
     * Client data
     */
    private Locator clientTypeField = new XPath("//div[@name='customer-type']" +
            "//input[contains(@class, 'nb-select-search')]");
    private Locator clientTypeSelectorButton = new XPath("//div[@name='customer-type']" +
            "//span[contains(@class, 'select2-arrow')]");
    private Locator clientTypeList = new XPath("//div[@name='customer-type']" +
            "//li[contains(@role, 'option')]/div/span");
    private Locator clientTypeSelectorOption = new XPath("//div[@name='customer-type']" +
            "//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private Locator clientStatusField = new XPath("//div[@name='statusid']" +
            "//input[contains(@class, 'nb-select-search')]");
    private Locator clientStatusSelectorButton = new XPath("//div[@name='statusid']" +
            "//span[contains(@class, 'select2-arrow')]");
    private Locator clientStatusList = new XPath("//div[@name='statusid']" +
            "//li[contains(@role, 'option')]/div/span");
    private Locator clientStatusSelectorOption = new XPath("//div[@name='statusid']" +
            "//li[contains(@role, 'option')]/div/span[contains(text(), '%s')]");
    private Locator firstNameField = new ID("name2");
    private Locator middleNameField = new ID("middlename");
    private Locator lastNameField = new ID("name1");
    private Locator taxPayerIDTypeField = new XPath("//div[@id='taxpayeridtype']" +
            "//input[contains(@class, 'nb-select-search')]");
    private Locator taxPayerIDTypeSelectorButton = new XPath("//div[@id='taxpayeridtype']" +
            "//span[contains(@class, 'select2-arrow')]");
    private Locator taxPayerIDTypeList = new XPath("//div[@id='taxpayeridtype']" +
            "//li[contains(@role, 'option')]/div/span");
    private Locator taxPayerIDTypeSelectorOption = new XPath("//div[@id='taxpayeridtype']" +
            "//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private Locator taxIDField = new ID("taxidnumber");
    private Locator birthDateCalendarIcon = new XPath("//div[input[@id='birthdate']]/div[span[@class='nyb-icon-calendar']]");
    private Locator birthDateField = new ID("birthdate");
    private Locator idTypeField = new XPath("//div[@id='doc_type']" +
            "//input[contains(@class, 'nb-select-search')]");
    private Locator idTypeSelectorButton = new XPath("//div[@id='doc_type']" +
            "//span[contains(@class, 'select2-arrow')]");
    private Locator idTypeList = new XPath("//div[@id='doc_type']" +
            "//li[contains(@role, 'option')]/div/span");
    private Locator idTypeSelectorOption = new XPath("//div[@id='doc_type']" +
            "//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private Locator idNumberField = new ID("doc_id");
    private Locator issuedByField = new XPath("//div[@id='doc_state']" +
            "//input[contains(@class, 'nb-select-search')]");
    private Locator issuedBySelectorButton = new XPath("//div[@id='doc_state']" +
            "//span[contains(@class, 'select2-arrow')]");
    private Locator issuedByList = new XPath("//div[@id='doc_state']" +
            "//li[contains(@role, 'option')]/div/span");
    private Locator issuedBySelectorOption = new XPath("//div[@id='doc_state']" +
            "//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private Locator countryField = new XPath("//div[@id='doc_country']" +
            "//input[contains(@class, 'nb-select-search')]");
    private Locator countrySelectorButton = new XPath("//div[@id='doc_country']" +
            "//span[contains(@class, 'select2-arrow')]");
    private Locator countryList = new XPath("//div[@id='doc_country']" +
            "//li[contains(@role, 'option')]/div/span");
    private Locator countrySelectorOption = new XPath("//div[@id='doc_country']" +
            "//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private Locator expirationDateField = new ID("doc_expiration");
    private Locator addressTypeField = new XPath("//div[@name='addressuse_0']" +
            "//input[contains(@class, 'nb-select-search')]");
    private Locator addressTypeSelectorButton = new XPath("//div[@name='addressuse_0']" +
            "//span[contains(@class, 'select2-arrow')]");
    private Locator addressTypeList = new XPath("//div[@name='addressuse_0']" +
            "//li[contains(@role, 'option')]/div/span");
    private Locator addressTypeSelectorOption = new XPath("//div[@name='addressuse_0']" +
            "//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private Locator addressCountryField = new XPath("//div[@name='country_0']" +
            "//input[contains(@class, 'nb-select-search')]");
    private Locator addressCountrySelectorButton = new XPath("//div[@name='country_0']" +
            "//span[contains(@class, 'select2-arrow')]");
    private Locator addressCountryList = new XPath("//div[@name='country_0']" +
            "//li[contains(@role, 'option')]/div/span");
    private Locator addressCountrySelectorOption = new XPath("//div[@name='country_0']" +
            "//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private Locator addressField = new XPath("//input[@name='addressline1_0']");
    private Locator addressCityField = new XPath("//input[@name='cityname_0']");
    private Locator addressStateField = new XPath("//div[@name='states_0']" +
            "//input[contains(@class, 'nb-select-search')]");
    private Locator addressStateSelectorButton = new XPath("//div[@name='states_0']" +
            "//span[contains(@class, 'select2-arrow')]");
    private Locator addressStateList = new XPath("//div[@name='states_0']" +
            "//li[contains(@role, 'option')]/div/span");
    private Locator addressStateSelectorOption = new XPath("//div[@name='states_0']" +
            "//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private Locator addressZipCodeField = new XPath("//input[@name='zipcode_0']");

    @Step("Click on 'Save and continue' button")
    public void clickSaveAndContinueButton() {
        waitForElementClickable(saveAndContinueButton);
        click(saveAndContinueButton);
    }

    @Step("Click on 'Cancel' button")
    public void clickCancelButton() {
        waitForElementClickable(cancelButton);
        click(cancelButton);
    }

    @Step("Wait for modal window")
    public void waitForModalWindow() {
        waitForElementClickable(modalWindow);
        waitForElementVisibility(modalWindow);
    }

    @Step("Checking verification")
    public boolean isVerificationSuccess(){
        waitForElementVisibility(verificationCheckIcon);
        return getElementAttributeValue("class", verificationCheckIcon).contains("textGreen");
    }

    @Step("Click 'OK' modal button")
    public void clickOkModalButton(){
        waitForElementVisibility(okModalButton);
        waitForElementClickable(okModalButton);
        click(okModalButton);
    }

    /**
     * Client data
     */

    @Step("Wait for page loaded")
    public void waitPageLoaded() {
        waitForElementVisibility(addNewClientRegion);
    }

    @Step("Set 'Client Type' value")
    public void setClientTypeValue(String clientTypeValue) {
        waitForElementVisibility(clientTypeField);
        waitForElementClickable(clientTypeField);
        type(clientTypeValue, clientTypeField);
    }

    @Step("Click on 'Client Type' selector")
    public void clickClientTypeSelectorButton() {
        waitForElementClickable(clientTypeSelectorButton);
        click(clientTypeSelectorButton);
    }

    @Step("Returning list of Client Type")
    public List<String> getClientTypeList() {
        waitForElementVisibility(clientTypeList);
        waitForElementClickable(clientTypeList);
        return getElementsText(clientTypeList);
    }

    @Step("Click on 'Client Type' option")
    public void clickClientTypeOption(String clientTypeOption) {
        waitForElementVisibility(clientTypeSelectorOption, clientTypeOption);
        waitForElementClickable(clientTypeSelectorOption, clientTypeOption);
        click(clientTypeSelectorOption, clientTypeOption);
    }

    @Step("Set 'Client Status' value")
    public void setClientStatusValue(String clientTypeValue) {
        waitForElementVisibility(clientStatusField);
        waitForElementClickable(clientStatusField);
        type(clientTypeValue, clientStatusField);
    }

    @Step("Click on 'Client Status' selector")
    public void clickClientStatusSelectorButton() {
        waitForElementClickable(clientStatusSelectorButton);
        click(clientStatusSelectorButton);
    }

    @Step("Returning list of Client Status")
    public List<String> getClientStatusList() {
        waitForElementVisibility(clientStatusList);
        waitForElementClickable(clientStatusList);
        return getElementsText(clientStatusList);
    }

    @Step("Click on 'Client Status' option")
    public void clickClientStatusOption(String clientTypeOption) {
        waitForElementVisibility(clientStatusSelectorOption, clientTypeOption);
        waitForElementClickable(clientStatusSelectorOption, clientTypeOption);
        click(clientStatusSelectorOption, clientTypeOption);
    }

    @Step("Set 'First name' value")
    public void setFirstNameValue(String firstNameValue) {
        waitForElementVisibility(firstNameField);
        waitForElementClickable(firstNameField);
        type(firstNameValue, firstNameField);
    }

    @Step("Set 'Middle name' value")
    public void setMiddleNameValue(String middleNameValue) {
        waitForElementVisibility(middleNameField);
        waitForElementClickable(middleNameField);
        type(middleNameValue, middleNameField);
    }

    @Step("Set 'Last name' value")
    public void setLastNameValue(String lastNameValue) {
        waitForElementVisibility(lastNameField);
        waitForElementClickable(lastNameField);
        type(lastNameValue, lastNameField);
    }

    @Step("Set 'Tax Payer ID Type' value")
    public void setTaxPayerIDTypeValue(String taxPayerIDTypeTypeValue) {
        waitForElementVisibility(taxPayerIDTypeField);
        waitForElementClickable(taxPayerIDTypeField);
        type(taxPayerIDTypeTypeValue, taxPayerIDTypeField);
    }

    @Step("Click on 'Tax Payer ID Type' selector")
    public void clickTaxPayerIDTypeSelectorButton() {
        waitForElementClickable(taxPayerIDTypeSelectorButton);
        click(taxPayerIDTypeSelectorButton);
    }

    @Step("Returning list of Tax Payer ID Type")
    public List<String> getTaxPayerIDTypeList() {
        waitForElementVisibility(taxPayerIDTypeList);
        waitForElementClickable(taxPayerIDTypeList);
        return getElementsText(taxPayerIDTypeList);
    }

    @Step("Click on 'Tax Payer ID Type' option")
    public void clickTaxPayerIDTypeOption(String taxPayerIDTypeOption) {
        waitForElementVisibility(taxPayerIDTypeSelectorOption, taxPayerIDTypeOption);
        waitForElementClickable(taxPayerIDTypeSelectorOption, taxPayerIDTypeOption);
        click(taxPayerIDTypeSelectorOption, taxPayerIDTypeOption);
    }

    @Step("Set 'Tax ID' value")
    public void setTaxIDValue(String taxIDValue) {
        waitForElementVisibility(taxIDField);
        waitForElementClickable(taxIDField);
        type(taxIDValue, taxIDField);
    }

    @Step("Click on 'Birth Date' calendar icon")
    public void clickBirthDateCalendarIcon() {
        waitForElementClickable(birthDateCalendarIcon);
        click(birthDateCalendarIcon);
    }

    @Step("Set 'Birth Date' value")
    public void setBirthDateValue(String birthDateValue) {
        waitForElementVisibility(birthDateField);
        waitForElementClickable(birthDateField);
        for (char ch : birthDateValue.toCharArray()) {
            wait(1);
            typeWithoutWipe(String.valueOf(ch), birthDateField);
        }
    }

    @Step("Set 'Tax Payer ID Type' value")
    public void setIDTypeValue(String idTypeTypeValue) {
        waitForElementVisibility(idTypeField);
        waitForElementClickable(idTypeField);
        type(idTypeTypeValue, idTypeField);
    }

    @Step("Click on 'Tax Payer ID Type' selector")
    public void clickIDTypeSelectorButton() {
        waitForElementClickable(idTypeSelectorButton);
        click(idTypeSelectorButton);
    }

    @Step("Returning list of Tax Payer ID Type")
    public List<String> getIDTypeList() {
        waitForElementVisibility(idTypeList);
        waitForElementClickable(idTypeList);
        return getElementsText(idTypeList);
    }

    @Step("Click on 'Tax Payer ID Type' option")
    public void clickIDTypeOption(String idTypeOption) {
        waitForElementVisibility(idTypeSelectorOption, idTypeOption);
        waitForElementClickable(idTypeSelectorOption, idTypeOption);
        click(idTypeSelectorOption, idTypeOption);
    }

    @Step("Set 'ID Number' value")
    public void setIDNumberValue(String idNumberValue) {
        waitForElementVisibility(idNumberField);
        waitForElementClickable(idNumberField);
        type(idNumberValue, idNumberField);
    }

    @Step("Set 'Issued By' value")
    public void setIssuedByValue(String issuedByValue) {
        waitForElementVisibility(issuedByField);
        waitForElementClickable(issuedByField);
        type(issuedByValue, issuedByField);
    }

    @Step("Click on 'Issued By' selector")
    public void clickIssuedBySelectorButton() {
        waitForElementClickable(issuedBySelectorButton);
        click(issuedBySelectorButton);
    }

    @Step("Returning list of Issued By")
    public List<String> getIssuedByList() {
        waitForElementVisibility(issuedByList);
        waitForElementClickable(issuedByList);
        return getElementsText(issuedByList);
    }

    @Step("Click on 'Issued By' option")
    public void clickIssuedByOption(String issuedByOption) {
        waitForElementVisibility(issuedBySelectorOption, issuedByOption);
        waitForElementClickable(issuedBySelectorOption, issuedByOption);
        click(issuedBySelectorOption, issuedByOption);
    }

    @Step("Set 'Country' value")
    public void setCountryValue(String countryValue) {
        waitForElementVisibility(countryField);
        waitForElementClickable(countryField);
        type(countryValue, countryField);
    }

    @Step("Click on 'Country' selector")
    public void clickCountrySelectorButton() {
        waitForElementClickable(countrySelectorButton);
        click(countrySelectorButton);
    }

    @Step("Returning list of Country")
    public List<String> getCountryList() {
        waitForElementVisibility(countryList);
        waitForElementClickable(countryList);
        return getElementsText(countryList);
    }

    @Step("Click on 'Country' option")
    public void clickCountryOption(String countryOption) {
        waitForElementVisibility(countrySelectorOption, countryOption);
        waitForElementClickable(countrySelectorOption, countryOption);
        click(countrySelectorOption, countryOption);
    }

    @Step("Set 'Expiration Date' value")
    public void setExpirationDateValue(String expirationDateValue) {
        waitForElementVisibility(expirationDateField);
        waitForElementClickable(expirationDateField);
        type(expirationDateValue, expirationDateField);
    }

    @Step("Set 'Address Type' value")
    public void setAddressTypeValue(String addressTypeValue) {
        waitForElementVisibility(addressTypeField);
        waitForElementClickable(addressTypeField);
        type(addressTypeValue, addressTypeField);
    }

    @Step("Click on 'Address Type' selector")
    public void clickAddressTypeSelectorButton() {
        waitForElementClickable(addressTypeSelectorButton);
        click(addressTypeSelectorButton);
    }

    @Step("Returning list of Address Type")
    public List<String> getAddressTypeList() {
        waitForElementVisibility(addressTypeList);
        waitForElementClickable(addressTypeList);
        return getElementsText(addressTypeList);
    }

    @Step("Click on 'Address Type' option")
    public void clickAddressTypeOption(String addressType) {
        waitForElementVisibility(addressTypeSelectorOption, addressType);
        waitForElementClickable(addressTypeSelectorOption, addressType);
        click(addressTypeSelectorOption, addressType);
    }

    @Step("Set 'Address Country' value")
    public void setAddressCountryValue(String addressCountryValue) {
        waitForElementVisibility(addressCountryField);
        waitForElementClickable(addressCountryField);
        type(addressCountryValue, addressCountryField);
    }

    @Step("Click on 'Address Country' selector")
    public void clickAddressCountrySelectorButton() {
        waitForElementClickable(addressCountrySelectorButton);
        click(addressCountrySelectorButton);
    }

    @Step("Returning list of Address Country")
    public List<String> getAddressCountryList() {
        waitForElementVisibility(addressCountryList);
        waitForElementClickable(addressCountryList);
        return getElementsText(addressCountryList);
    }

    @Step("Click on 'Address Country' option")
    public void clickAddressCountryOption(String addressCountry) {
        waitForElementVisibility(addressCountrySelectorOption, addressCountry);
        waitForElementClickable(addressCountrySelectorOption, addressCountry);
        click(addressCountrySelectorOption, addressCountry);
    }

    @Step("Set 'Address' value")
    public void setAddressValue(String addressValue) {
        waitForElementVisibility(addressField);
        waitForElementClickable(addressField);
        type(addressValue, addressField);
    }

    @Step("Set 'Address City' value")
    public void setAddressCityValue(String addressCityValue) {
        waitForElementVisibility(addressCityField);
        waitForElementClickable(addressCityField);
        type(addressCityValue, addressCityField);
    }

    @Step("Set 'Address State' value")
    public void setAddressStateValue(String addressStateValue) {
        waitForElementVisibility(addressStateField);
        waitForElementClickable(addressStateField);
        type(addressStateValue, addressStateField);
    }

    @Step("Click on 'Address State' selector")
    public void clickAddressStateSelectorButton() {
        waitForElementClickable(addressStateSelectorButton);
        click(addressStateSelectorButton);
    }

    @Step("Returning list of Address State")
    public List<String> getAddressStateList() {
        waitForElementVisibility(addressStateList);
        waitForElementClickable(addressStateList);
        return getElementsText(addressStateList);
    }

    @Step("Click on 'Address State' option")
    public void clickAddressStateOption(String addressState) {
        waitForElementVisibility(addressStateSelectorOption, addressState);
        waitForElementClickable(addressStateSelectorOption, addressState);
        click(addressStateSelectorOption, addressState);
    }

    @Step("Set 'Address ZipCode' value")
    public void setAddressZipCodeValue(String addressZipCodeValue) {
        waitForElementVisibility(addressZipCodeField);
        waitForElementClickable(addressZipCodeField);
        type(addressZipCodeValue, addressZipCodeField);
    }
}
