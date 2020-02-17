package com.nymbus.pages.clients;

import com.nymbus.base.BasePage;
import com.nymbus.base.BaseTest;
import com.nymbus.locator.ID;
import com.nymbus.locator.Locator;
import com.nymbus.locator.Name;
import com.nymbus.locator.XPath;
import io.qameta.allure.Step;
import org.openqa.selenium.JavascriptExecutor;

import java.util.List;

public class AddClientPage extends BasePage {


    private Locator addNewClientRegion = new XPath("//main[contains(@class, 'customerNew')]");
    private Locator saveAndContinueButton = new XPath("//button[contains(@class,'saveAndContinue')]");
    private Locator cancelButton = new XPath("//button[contains(@class,'btnCancel')]");
    private Locator modalWindow = new XPath("//div[contains(@class, 'modal-content')]");
    private Locator verificationCheckIcon = new XPath("//div[contains(@class, 'modal-content')]" +
            "//div/h4[contains(@class, 'modal-title')]");
    private Locator okModalButton = new XPath("//div[contains(@class, 'modal-content')]" +
            "//button[span[text()='OK']]");

    /**
     * Client basic information
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

    /**
     * Client detailed information
     */
    private Locator suffixField = new ID("suffix");
    private Locator maidenFamilyNameField = new ID("maidenname");
    private Locator akaField = new ID("nickname");
    private Locator profilePhotoField = new XPath("//tr[contains(@ng-if, 'profilephoto')]//input[@type='file']");
    private Locator genderField = new XPath("//div[@id='gender']" +
            "//input[contains(@class, 'nb-select-search')]");
    private Locator genderSelectorButton = new XPath("//div[@id='gender']" +
            "//span[contains(@class, 'select2-arrow')]");
    private Locator genderList = new XPath("//div[@id='gender']" +
            "//li[contains(@role, 'option')]/div/span");
    private Locator genderSelectorOption = new XPath("//div[@id='gender']" +
            "//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private Locator educationField = new XPath("//div[@id='education']" +
            "//input[contains(@class, 'nb-select-search')]");
    private Locator educationSelectorButton = new XPath("//div[@id='education']" +
            "//span[contains(@class, 'select2-arrow')]");
    private Locator educationList = new XPath("//div[@id='education']" +
            "//li[contains(@role, 'option')]/div/span");
    private Locator educationSelectorOption = new XPath("//div[@id='education']" +
            "//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private Locator incomeField = new XPath("//div[@id='incomesalary']" +
            "//input[contains(@class, 'nb-select-search')]");
    private Locator incomeSelectorButton = new XPath("//div[@id='incomesalary']" +
            "//span[contains(@class, 'select2-arrow')]");
    private Locator incomeList = new XPath("//div[@id='incomesalary']" +
            "//li[contains(@role, 'option')]/div/span");
    private Locator incomeSelectorOption = new XPath("//div[@id='incomesalary']" +
            "//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private Locator maritalStatusField = new XPath("//div[@id='maritalstatus']" +
            "//input[contains(@class, 'nb-select-search')]");
    private Locator maritalStatusSelectorButton = new XPath("//div[@id='maritalstatus']" +
            "//span[contains(@class, 'select2-arrow')]");
    private Locator maritalStatusList = new XPath("//div[@id='maritalstatus']" +
            "//li[contains(@role, 'option')]/div/span");
    private Locator maritalStatusSelectorOption = new XPath("//div[@id='maritalstatus']" +
            "//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private Locator occupationField = new ID("_occupation");
    private Locator consumerInformationIndicatorField = new XPath("//div[@id='consumerinformationindicator']" +
            "//input[contains(@class, 'nb-select-search')]");
    private Locator consumerInformationIndicatorSelectorButton = new XPath("//div[@id='consumerinformationindicator']" +
            "//span[contains(@class, 'select2-arrow')]");
    private Locator consumerInformationIndicatorList = new XPath("//div[@id='consumerinformationindicator']" +
            "//li[contains(@role, 'option')]/div/span");
    private Locator consumerInformationIndicatorSelectorOption = new XPath("//div[@id='consumerinformationindicator']" +
            "//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private Locator jobTitleField = new ID("jobtitle");
    private Locator ownOrRentField = new XPath("//div[@id='ownrent']" +
            "//input[contains(@class, 'nb-select-search')]");
    private Locator ownOrRentSelectorButton = new XPath("//div[@id='ownrent']" +
            "//span[contains(@class, 'select2-arrow')]");
    private Locator ownOrRentList = new XPath("//div[@id='ownrent']" +
            "//li[contains(@role, 'option')]/div/span");
    private Locator ownOrRentSelectorOption = new XPath("//div[@id='ownrent']" +
            "//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private Locator mailCodeField = new XPath("//div[@id='mailingcode']" +
            "//input[contains(@class, 'nb-select-search')]");
    private Locator mailCodeSelectorButton = new XPath("//div[@id='mailingcode']" +
            "//span[contains(@class, 'select2-arrow')]");
    private Locator mailCodeList = new XPath("//div[@id='mailingcode']" +
            "//li[contains(@role, 'option')]/div/span");
    private Locator mailCodeSelectorOption = new XPath("//div[@id='mailingcode']" +
            "//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private Locator selectOfficerField = new XPath("//div[@id='officerid']" +
            "//input[contains(@class, 'nb-select-search')]");
    private Locator selectOfficerSelectorButton = new XPath("//div[@id='officerid']" +
            "//span[contains(@class, 'select2-arrow')]");
    private Locator selectOfficerList = new XPath("//div[@id='officerid']" +
            "//li[contains(@role, 'option')]/div/span");
    private Locator selectOfficerSelectorOption = new XPath("//div[@id='officerid']" +
            "//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private Locator userDefinedField_1 = new ID("userdefinedfield1");
    private Locator userDefinedField_2 = new ID("userdefinedfield2");
    private Locator userDefinedField_3 = new ID("userdefinedfield3");
    private Locator userDefinedField_4 = new ID("userdefinedfield4");
    private Locator phoneField = new Name("fullphonenumber_0");
    private Locator emailTypeField = new XPath("//div[@name='emailuse_0']" +
            "//input[contains(@class, 'nb-select-search')]");
    private Locator emailTypeSelectorButton = new XPath("//div[@name='emailuse_0']" +
            "//span[contains(@class, 'select2-arrow')]");
    private Locator emailTypeList = new XPath("//div[@name='emailuse_0']" +
            "//li[contains(@role, 'option')]/div/span");
    private Locator emailTypeSelectorOption = new XPath("//div[@name='emailuse_0']" +
            "//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private Locator emailField = new Name("email_0");

    /**
     * Upload documentation
     */
    private Locator documentationField = new XPath("//section[contains(@class, 'uploadDocumentation')]//input[@type='file']");
    private Locator documentationIdTypeField = new XPath("//div[@id='type']" +
            "//input[contains(@class, 'nb-select-search')]");
    private Locator documentationIdTypeSelectorButton = new XPath("//div[@id='type']" +
            "//span[contains(@class, 'select2-arrow')]");
    private Locator documentationIdTypeList = new XPath("//div[@id='type']" +
            "//li[contains(@role, 'option')]/div/span");
    private Locator documentationIdTypeSelectorOption = new XPath("//div[@id='type']" +
            "//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private Locator documentationIdNumberField = new ID("id");
    private Locator documentationIssuedByField = new XPath("//div[@id='state']" +
            "//input[contains(@class, 'nb-select-search')]");
    private Locator documentationIssuedBySelectorButton = new XPath("//div[@id='state']" +
            "//span[contains(@class, 'select2-arrow')]");
    private Locator documentationIssuedByList = new XPath("//div[@id='state']" +
            "//li[contains(@role, 'option')]/div/span");
    private Locator documentationIssuedBySelectorOption = new XPath("//div[@id='state']" +
            "//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private Locator documentationCountryField = new XPath("//div[@id='country']" +
            "//input[contains(@class, 'nb-select-search')]");
    private Locator documentationCountrySelectorButton = new XPath("//div[@id='country']" +
            "//span[contains(@class, 'select2-arrow')]");
    private Locator documentationCountryList = new XPath("//div[@id='country']" +
            "//li[contains(@role, 'option')]/div/span");
    private Locator documentationCountrySelectorOption = new XPath("//div[@id='country']" +
            "//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private Locator documentationExpirationDateField = new ID("expiration");
    private Locator documentationSaveButton = new XPath("//form[contains(@name, 'documentForm')]//button[contains(@ng-click, 'save')]");

    /**
     * Upload signature
     */
    private Locator clientSignatureField = new XPath("//section[contains(@ng-show, 'customerSignature')]//input[@type='file' and @name='file']");

    private Locator viewMemberProfileButton = new XPath("//button[@data-test-id='go-CustomerPage']");

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
    public boolean isVerificationSuccess() {
        waitForElementVisibility(verificationCheckIcon);
        return getElementAttributeValue("class", verificationCheckIcon).contains("textBlue");
    }

    @Step("Click 'OK' modal button")
    public void clickOkModalButton() {
        waitForElementVisibility(okModalButton);
        waitForElementClickable(okModalButton);
        click(okModalButton);
    }

    /**
     * Client basic information
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
        typeWithoutWipe("", birthDateField);
        wait(1);
        typeWithoutWipe(birthDateValue, birthDateField);
//        for (char ch : birthDateValue.toCharArray()) {
//            wait(1);
//            typeWithoutWipe(String.valueOf(ch), birthDateField);
//        }
    }

    @Step("Set 'ID Type' value")
    public void setIDTypeValue(String idTypeTypeValue) {
        waitForElementVisibility(idTypeField);
        waitForElementClickable(idTypeField);
        type(idTypeTypeValue, idTypeField);
    }

    @Step("Click on 'ID Type' selector")
    public void clickIDTypeSelectorButton() {
        waitForElementClickable(idTypeSelectorButton);
        click(idTypeSelectorButton);
    }

    @Step("Returning list of ID Type")
    public List<String> getIDTypeList() {
        waitForElementVisibility(idTypeList);
        waitForElementClickable(idTypeList);
        return getElementsText(idTypeList);
    }

    @Step("Click on 'ID Type' option")
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

    /**
     * Client detailed information
     */

    @Step("Set 'Suffix' Value")
    public void setSuffixField(String suffixValue) {
        waitForElementVisibility(suffixField);
        waitForElementClickable(suffixField);
        type(suffixValue, suffixField);
    }

    @Step("Set 'Maiden/Family Name'")
    public void setMaidenFamilyNameField(String maidenFamilyNameValue) {
        waitForElementVisibility(maidenFamilyNameField);
        waitForElementClickable(maidenFamilyNameField);
        type(maidenFamilyNameValue, maidenFamilyNameField);
    }

    @Step("Set 'AKA'")
    public void setAkaField(String akaValue) {
        waitForElementVisibility(akaField);
        waitForElementClickable(akaField);
        type(akaValue, akaField);
    }

    @Step("Set Profile photo")
    public void uploadProfilePhoto(String profilePhotoPath) {
        ((JavascriptExecutor) BaseTest.getDriver())
                .executeScript("arguments[0].style.height='auto'; arguments[0].style.visibility='visible';",
                        getElement(profilePhotoField));
        type(profilePhotoPath, profilePhotoField);
        ((JavascriptExecutor) BaseTest.getDriver())
                .executeScript("arguments[0].style.height='auto'; arguments[0].style.visibility='hidden';",
                        getElement(profilePhotoField));
    }

    @Step("Set 'Gender' value")
    public void setGenderValue(String educationValue) {
        waitForElementVisibility(genderField);
        waitForElementClickable(genderField);
        type(educationValue, genderField);
    }

    @Step("Click on 'Gender' selector")
    public void clickGenderSelectorButton() {
        waitForElementClickable(genderSelectorButton);
        click(genderSelectorButton);
    }

    @Step("Returning list of Gender")
    public List<String> getGenderList() {
        waitForElementVisibility(genderList);
        waitForElementClickable(genderList);
        return getElementsText(genderList);
    }

    @Step("Click on 'Gender' option")
    public void clickGenderOption(String genderOption) {
        waitForElementVisibility(genderSelectorOption, genderOption);
        waitForElementClickable(genderSelectorOption, genderOption);
        click(genderSelectorOption, genderOption);
    }

    @Step("Set 'Education' value")
    public void setEducationValue(String educationValue) {
        waitForElementVisibility(educationField);
        waitForElementClickable(educationField);
        type(educationValue, educationField);
    }

    @Step("Click on 'Education' selector")
    public void clickEducationSelectorButton() {
        waitForElementClickable(educationSelectorButton);
        click(educationSelectorButton);
    }

    @Step("Returning list of Education")
    public List<String> getEducationList() {
        waitForElementVisibility(educationList);
        waitForElementClickable(educationList);
        return getElementsText(educationList);
    }

    @Step("Click on 'Education' option")
    public void clickEducationOption(String educationOption) {
        waitForElementVisibility(educationSelectorOption, educationOption);
        waitForElementClickable(educationSelectorOption, educationOption);
        click(educationSelectorOption, educationOption);
    }

    @Step("Set 'Income' value")
    public void setIncomeValue(String incomeValue) {
        waitForElementVisibility(incomeField);
        waitForElementClickable(incomeField);
        type(incomeValue, incomeField);
    }

    @Step("Click on 'Income' selector")
    public void clickIncomeSelectorButton() {
        waitForElementClickable(incomeSelectorButton);
        click(incomeSelectorButton);
    }

    @Step("Returning list of Income")
    public List<String> getIncomeList() {
        waitForElementVisibility(incomeList);
        waitForElementClickable(incomeList);
        return getElementsText(incomeList);
    }

    @Step("Click on 'Income' option")
    public void clickIncomeOption(String incomeOption) {
        waitForElementVisibility(incomeSelectorOption, incomeOption);
        waitForElementClickable(incomeSelectorOption, incomeOption);
        click(incomeSelectorOption, incomeOption);
    }

    @Step("Set 'Marital Status' value")
    public void setMaritalStatusValue(String maritalStatusValue) {
        waitForElementVisibility(maritalStatusField);
        waitForElementClickable(maritalStatusField);
        type(maritalStatusValue, maritalStatusField);
    }

    @Step("Click on 'Marital Status' selector")
    public void clickMaritalStatusSelectorButton() {
        waitForElementClickable(maritalStatusSelectorButton);
        click(maritalStatusSelectorButton);
    }

    @Step("Returning list of Marital Status")
    public List<String> getsMaritalStatusList() {
        waitForElementVisibility(maritalStatusList);
        waitForElementClickable(maritalStatusList);
        return getElementsText(maritalStatusList);
    }

    @Step("Click on 'Marital Status' option")
    public void clickMaritalStatusOption(String maritalStatusOption) {
        waitForElementVisibility(maritalStatusSelectorOption, maritalStatusOption);
        waitForElementClickable(maritalStatusSelectorOption, maritalStatusOption);
        click(maritalStatusSelectorOption, maritalStatusOption);
    }

    @Step("Set 'Occupation' value")
    public void setOccupationValue(String occupationValue) {
        waitForElementVisibility(occupationField);
        waitForElementClickable(occupationField);
        type(occupationValue, occupationField);
    }

    @Step("Set 'Consumer Information Indicator' value")
    public void setConsumerInformationIndicatorValue(String consumerInformationIndicatorValue) {
        waitForElementVisibility(consumerInformationIndicatorField);
        waitForElementClickable(consumerInformationIndicatorField);
        type(consumerInformationIndicatorValue, consumerInformationIndicatorField);
    }

    @Step("Click on 'Consumer Information Indicator' selector")
    public void clickConsumerInformationIndicatorSelectorButton() {
        waitForElementClickable(consumerInformationIndicatorSelectorButton);
        click(consumerInformationIndicatorSelectorButton);
    }

    @Step("Returning list of Consumer Information Indicator")
    public List<String> getsConsumerInformationIndicatorList() {
        waitForElementVisibility(consumerInformationIndicatorList);
        waitForElementClickable(consumerInformationIndicatorList);
        return getElementsText(consumerInformationIndicatorList);
    }

    @Step("Click on 'Consumer Information Indicator' option")
    public void clickConsumerInformationIndicatorOption(String consumerInformationIndicatorOption) {
        waitForElementVisibility(consumerInformationIndicatorSelectorOption, consumerInformationIndicatorOption);
        waitForElementClickable(consumerInformationIndicatorSelectorOption, consumerInformationIndicatorOption);
        click(consumerInformationIndicatorSelectorOption, consumerInformationIndicatorOption);
    }

    @Step("Set 'Job Title' value")
    public void setJobTitleValue(String jobTitleValue) {
        waitForElementVisibility(jobTitleField);
        waitForElementClickable(jobTitleField);
        type(jobTitleValue, jobTitleField);
    }

    @Step("Set 'Own Or Rent' value")
    public void setOwnOrRentValue(String mailCodeValue) {
        waitForElementVisibility(ownOrRentField);
        waitForElementClickable(ownOrRentField);
        type(mailCodeValue, ownOrRentField);
    }

    @Step("Click on 'Own Or Rent' selector")
    public void clickOwnOrRentSelectorButton() {
        waitForElementClickable(ownOrRentSelectorButton);
        click(ownOrRentSelectorButton);
    }

    @Step("Returning list of Own Or Rent")
    public List<String> getsOwnOrRentList() {
        waitForElementVisibility(ownOrRentList);
        waitForElementClickable(ownOrRentList);
        return getElementsText(ownOrRentList);
    }

    @Step("Click on 'Own Or Rent' option")
    public void clickOwnOrRentOption(String ownOrRentOption) {
        waitForElementVisibility(ownOrRentSelectorOption, ownOrRentOption);
        waitForElementClickable(ownOrRentSelectorOption, ownOrRentOption);
        click(ownOrRentSelectorOption, ownOrRentOption);
    }

    @Step("Set 'Mail Code' value")
    public void setMailCodeValue(String mailCodeValue) {
        waitForElementVisibility(mailCodeField);
        waitForElementClickable(mailCodeField);
        type(mailCodeValue, mailCodeField);
    }

    @Step("Click on 'Mail Code' selector")
    public void clickMailCodeSelectorButton() {
        waitForElementClickable(mailCodeSelectorButton);
        click(mailCodeSelectorButton);
    }

    @Step("Returning list of Mail Code")
    public List<String> getsMailCodeList() {
        waitForElementVisibility(mailCodeList);
        waitForElementClickable(mailCodeList);
        return getElementsText(mailCodeList);
    }

    @Step("Click on 'Mail Code' option")
    public void clickMailCodeOption(String mailCodeOption) {
        waitForElementVisibility(mailCodeSelectorOption, mailCodeOption);
        waitForElementClickable(mailCodeSelectorOption, mailCodeOption);
        click(mailCodeSelectorOption, mailCodeOption);
    }

    @Step("Set 'Select Officer' value")
    public void setSelectOfficerValue(String selectOfficerValue) {
        waitForElementVisibility(selectOfficerField);
        waitForElementClickable(selectOfficerField);
        type(selectOfficerValue, selectOfficerField);
    }

    @Step("Click on 'Select Officer' selector")
    public void clickSelectOfficerSelectorButton() {
        waitForElementClickable(selectOfficerSelectorButton);
        click(selectOfficerSelectorButton);
    }

    @Step("Returning list of Select Officer")
    public List<String> getSlectOfficerList() {
        waitForElementVisibility(selectOfficerList);
        waitForElementClickable(selectOfficerList);
        return getElementsText(selectOfficerList);
    }

    @Step("Click on 'Select Officer' option")
    public void clickSelectOfficerOption(String selectOfficerOption) {
        waitForElementVisibility(selectOfficerSelectorOption, selectOfficerOption);
        waitForElementClickable(selectOfficerSelectorOption, selectOfficerOption);
        click(selectOfficerSelectorOption, selectOfficerOption);
    }

    @Step("Set 'User Defined Field 1' value")
    public void setUserDefinedField1Value(String userDefinedField1Value) {
        waitForElementVisibility(userDefinedField_1);
        waitForElementClickable(userDefinedField_1);
        type(userDefinedField1Value, userDefinedField_1);
    }

    @Step("Set 'User Defined Field 2' value")
    public void setUserDefinedField2Value(String userDefinedField2Value) {
        waitForElementVisibility(userDefinedField_2);
        waitForElementClickable(userDefinedField_2);
        type(userDefinedField2Value, userDefinedField_2);
    }

    @Step("Set 'User Defined Field 3' value")
    public void setUserDefinedField3Value(String userDefinedField3Value) {
        waitForElementVisibility(userDefinedField_3);
        waitForElementClickable(userDefinedField_3);
        type(userDefinedField3Value, userDefinedField_3);
    }

    @Step("Set 'User Defined Field 4' value")
    public void setUserDefinedField4Value(String userDefinedField4Value) {
        waitForElementVisibility(userDefinedField_4);
        waitForElementClickable(userDefinedField_4);
        type(userDefinedField4Value, userDefinedField_4);
    }

    @Step("Set 'Phone' value")
    public void setPhoneValue(String phoneValue) {
        waitForElementVisibility(phoneField);
        waitForElementClickable(phoneField);
        type(phoneValue, phoneField);
    }

    @Step("Set 'Email Type' value")
    public void setEmailTypeValue(String emailTypeValue) {
        waitForElementVisibility(emailTypeField);
        waitForElementClickable(emailTypeField);
        type(emailTypeValue, emailTypeField);
    }

    @Step("Click on 'Email Type' selector")
    public void clickEmailTypeSelectorButton() {
        waitForElementClickable(emailTypeSelectorButton);
        click(emailTypeSelectorButton);
    }

    @Step("Returning list of Email Type")
    public List<String> getEmailTypeList() {
        waitForElementVisibility(emailTypeList);
        waitForElementClickable(emailTypeList);
        return getElementsText(emailTypeList);
    }

    @Step("Click on 'Email Type' option")
    public void clickEmailTypeOption(String emailTypeOption) {
        waitForElementVisibility(emailTypeSelectorOption, emailTypeOption);
        waitForElementClickable(emailTypeSelectorOption, emailTypeOption);
        click(emailTypeSelectorOption, emailTypeOption);
    }

    @Step("Set 'Email' value")
    public void setEmailValue(String emailValue) {
        waitForElementVisibility(emailField);
        waitForElementClickable(emailField);
        type(emailValue, emailField);
    }

    @Step("Set Profile photo")
    public void uploadClientDocumentation(String clientDocument) {
        waitForElementVisibility(saveAndContinueButton);
        waitForElementClickable(saveAndContinueButton);
        ((JavascriptExecutor) BaseTest.getDriver())
                .executeScript("arguments[0].style.height='auto'; arguments[0].style.visibility='visible';",
                        getElement(documentationField));
        type(clientDocument, documentationField);
        ((JavascriptExecutor) BaseTest.getDriver())
                .executeScript("arguments[0].style.height='auto'; arguments[0].style.visibility='hidden';",
                        getElement(documentationField));
    }

    @Step("Set 'Document ID Type' value")
    public void setDocumentIDTypeValue(String idTypeTypeValue) {
        waitForElementVisibility(documentationIdTypeField);
        waitForElementClickable(documentationIdTypeField);
        type(idTypeTypeValue, documentationIdTypeField);
    }

    @Step("Click on 'Document ID Type' selector")
    public void clickDocumentIDTypeSelectorButton() {
        waitForElementClickable(documentationIdTypeSelectorButton);
        click(documentationIdTypeSelectorButton);
    }

    @Step("Returning list of Document ID Type")
    public List<String> getDocumentIDTypeList() {
        waitForElementVisibility(documentationIdTypeList);
        waitForElementClickable(documentationIdTypeList);
        return getElementsText(documentationIdTypeList);
    }

    @Step("Click on 'Document ID Type' option")
    public void clickDocumentIDTypeOption(String idTypeOption) {
        waitForElementVisibility(documentationIdTypeSelectorOption, idTypeOption);
        waitForElementClickable(documentationIdTypeSelectorOption, idTypeOption);
        click(documentationIdTypeSelectorOption, idTypeOption);
    }

    @Step("Set 'Document ID Number' value")
    public void setDocumentIDNumberValue(String idNumberValue) {
        waitForElementVisibility(documentationIdNumberField);
        waitForElementClickable(documentationIdNumberField);
        type(idNumberValue, documentationIdNumberField);
    }

    @Step("Set 'Document Issued By' value")
    public void setDocumentIssuedByValue(String issuedByValue) {
        waitForElementVisibility(documentationIssuedByField);
        waitForElementClickable(documentationIssuedByField);
        type(issuedByValue, documentationIssuedByField);
    }

    @Step("Click on 'Document Issued By' selector")
    public void clickDocumentIssuedBySelectorButton() {
        waitForElementClickable(documentationIssuedBySelectorButton);
        click(documentationIssuedBySelectorButton);
    }

    @Step("Returning list of Document Issued By")
    public List<String> getDocumentIssuedByList() {
        waitForElementVisibility(documentationIssuedByList);
        waitForElementClickable(documentationIssuedByList);
        return getElementsText(documentationIssuedByList);
    }

    @Step("Click on 'Document Issued By' option")
    public void clickDocumentIssuedByOption(String issuedByOption) {
        waitForElementVisibility(documentationIssuedBySelectorOption, issuedByOption);
        waitForElementClickable(documentationIssuedBySelectorOption, issuedByOption);
        click(documentationIssuedBySelectorOption, issuedByOption);
    }

    @Step("Set 'Document Country' value")
    public void setDocumentCountryValue(String countryValue) {
        waitForElementVisibility(documentationCountryField);
        waitForElementClickable(documentationCountryField);
        type(countryValue, documentationCountryField);
    }

    @Step("Click on 'Document Country' selector")
    public void clickDocumentCountrySelectorButton() {
        waitForElementClickable(documentationCountrySelectorButton);
        click(documentationCountrySelectorButton);
    }

    @Step("Returning list of Document Country")
    public List<String> getDocumentCountryList() {
        waitForElementVisibility(documentationCountryList);
        waitForElementClickable(documentationCountryList);
        return getElementsText(documentationCountryList);
    }

    @Step("Click on 'Document Country' option")
    public void clickDocumentCountryOption(String countryOption) {
        waitForElementVisibility(documentationCountrySelectorOption, countryOption);
        waitForElementClickable(documentationCountrySelectorOption, countryOption);
        click(documentationCountrySelectorOption, countryOption);
    }

    @Step("Set 'Document Expiration Date' value")
    public void setDocumentExpirationDateValue(String expirationDateValue) {
        waitForElementVisibility(documentationExpirationDateField);
        waitForElementClickable(documentationExpirationDateField);
        typeWithoutWipe("", documentationExpirationDateField);
        wait(1);
        typeWithoutWipe(expirationDateValue, documentationExpirationDateField);
    }

    @Step("Click 'Save Changes' documentation button")
    public void clickDocumentSaveChangesButton() {
        waitForElementVisibility(documentationSaveButton);
        waitForElementClickable(documentationSaveButton);
        click(documentationSaveButton);
    }

    @Step("Set Client Signature")
    public void uploadClientSignature(String clientDocument) {
        waitForElementVisibility(saveAndContinueButton);
        waitForElementClickable(saveAndContinueButton);
        ((JavascriptExecutor) BaseTest.getDriver())
                .executeScript("arguments[0].style.height='auto'; arguments[0].style.visibility='visible';",
                        getElement(clientSignatureField));
        type(clientDocument, clientSignatureField);
        ((JavascriptExecutor) BaseTest.getDriver())
                .executeScript("arguments[0].style.height='auto'; arguments[0].style.visibility='hidden';",
                        getElement(clientSignatureField));
    }

    @Step("Click 'View Member Profile'")
    public void clickViewMemberProfileButton() {
        waitForElementVisibility(viewMemberProfileButton);
        waitForElementClickable(viewMemberProfileButton);
        click(viewMemberProfileButton);
    }

}
