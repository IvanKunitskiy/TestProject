package com.nymbus.pages.clients;

import com.codeborne.selenide.Selenide;
import com.nymbus.core.base.PageTools;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.util.List;

public class AddClientPage extends PageTools {
    private By addNewClientRegion = By.xpath("//main[contains(@class, 'customerNew')]");
    private By saveAndContinueButton = By.xpath("//button[contains(@class,'saveAndContinue')]");
    private By cancelButton = By.xpath("//button[contains(@class,'btnCancel')]");
    private By modalWindow = By.xpath("//div[contains(@class, 'modal-content')]");
    private By verificationCheckIcon = By.xpath("//div[contains(@class, 'modal-content')]" +
            "//div/h4[contains(@class, 'modal-title')]");
    private By okModalButton = By.xpath("//div[contains(@class, 'modal-content')]" +
            "//button[span[text()='OK']]");

    /**
     * Client basic information
     */
    private By clientTypeField = By.xpath("//div[@name='customer-type']" +
            "//input[contains(@class, 'nb-select-search')]");
    private By clientTypeSelectorButton = By.xpath("//div[@name='customer-type']" +
            "//span[contains(@class, 'select2-arrow')]");
    private By clientTypeList = By.xpath("//div[@name='customer-type']" +
            "//li[contains(@role, 'option')]/div/span");
    private By clientTypeSelectorOption = By.xpath("//div[@name='customer-type']" +
            "//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private By clientStatusField = By.xpath("//div[@name='statusid']" +
            "//input[contains(@class, 'nb-select-search')]");
    private By clientStatusSelectorButton = By.xpath("//div[@name='statusid']" +
            "//span[contains(@class, 'select2-arrow')]");
    private By clientStatusList = By.xpath("//div[@name='statusid']" +
            "//li[contains(@role, 'option')]/div/span");
    private By clientStatusSelectorOption = By.xpath("//div[@name='statusid']" +
            "//li[contains(@role, 'option')]/div/span[contains(text(), '%s')]");
    private By organizationNameField = By.id("name1");
    private By firstNameField = By.id("name2");
    private By middleNameField = By.id("middlename");
    private By lastNameField = By.id("name1");
    private By taxPayerIDTypeField = By.xpath("//div[@id='taxpayeridtype']" +
            "//input[contains(@class, 'nb-select-search')]");
    private By taxPayerIDTypeSelectorButton = By.xpath("//div[@id='taxpayeridtype']" +
            "//span[contains(@class, 'select2-arrow')]");
    private By taxPayerIDTypeList = By.xpath("//div[@id='taxpayeridtype']" +
            "//li[contains(@role, 'option')]/div/span");
    private By taxPayerIDTypeSelectorOption = By.xpath("//div[@id='taxpayeridtype']" +
            "//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private By taxIDField = By.id("taxidnumber");
    private By birthDateCalendarIcon = By.xpath("//div[input[@id='birthdate']]/div[span[@class='nyb-icon-calendar']]");
    private By birthDateField = By.id("birthdate");
    private By idTypeField = By.xpath("//div[@id='doc_type']" +
            "//input[contains(@class, 'nb-select-search')]");
    private By idTypeSelectorButton = By.xpath("//div[@id='doc_type']" +
            "//span[contains(@class, 'select2-arrow')]");
    private By idTypeList = By.xpath("//div[@id='doc_type']" +
            "//li[contains(@role, 'option')]/div/span");
    private By idTypeSelectorOption = By.xpath("//div[@id='doc_type']" +
            "//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private By idNumberField = By.id("doc_id");
    private By issuedByField = By.xpath("//div[@id='doc_state']" +
            "//input[contains(@class, 'nb-select-search')]");
    private By issuedBySelectorButton = By.xpath("//div[@id='doc_state']" +
            "//span[contains(@class, 'select2-arrow')]");
    private By issuedByList = By.xpath("//div[@id='doc_state']" +
            "//li[contains(@role, 'option')]/div/span");
    private By issuedBySelectorOption = By.xpath("//div[@id='doc_state']" +
            "//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private By countryField = By.xpath("//div[@id='doc_country']" +
            "//input[contains(@class, 'nb-select-search')]");
    private By countrySelectorButton = By.xpath("//div[@id='doc_country']" +
            "//span[contains(@class, 'select2-arrow')]");
    private By countryList = By.xpath("//div[@id='doc_country']" +
            "//li[contains(@role, 'option')]/div/span");
    private By countrySelectorOption = By.xpath("//div[@id='doc_country']" +
            "//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private By expirationDateField = By.id("doc_expiration");
    private By expirationDateCalendarIcon = By.xpath("//div[input[@id='doc_expiration']]/div[span[@class='nyb-icon-calendar']]");
    private By addressTypeField = By.xpath("//div[@name='addressuse_0']" +
            "//input[contains(@class, 'nb-select-search')]");
    private By addressTypeSelectorButton = By.xpath("//div[@name='addressuse_0']" +
            "//span[contains(@class, 'select2-arrow')]");
    private By addressTypeList = By.xpath("//div[@name='addressuse_0']" +
            "//li[contains(@role, 'option')]/div/span");
    private By addressTypeSelectorOption = By.xpath("//div[@name='addressuse_0']" +
            "//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private By addressCountryField = By.xpath("//div[@name='country_0']" +
            "//input[contains(@class, 'nb-select-search')]");
    private By addressCountrySelectorButton = By.xpath("//div[@name='country_0']" +
            "//span[contains(@class, 'select2-arrow')]");
    private By addressCountryList = By.xpath("//div[@name='country_0']" +
            "//li[contains(@role, 'option')]/div/span");
    private By addressCountrySelectorOption = By.xpath("//div[@name='country_0']" +
            "//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private By addressField = By.xpath("//input[@name='addressline1_0']");
    private By addressCityField = By.xpath("//input[@name='cityname_0']");
    private By addressStateField = By.xpath("//div[@name='states_0']" +
            "//input[contains(@class, 'nb-select-search')]");
    private By addressStateSelectorButton = By.xpath("//div[@name='states_0']" +
            "//span[contains(@class, 'select2-arrow')]");
    private By addressStateList = By.xpath("//div[@name='states_0']" +
            "//li[contains(@role, 'option')]/div/span");
    private By addressStateSelectorOption = By.xpath("//div[@name='states_0']" +
            "//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private By addressZipCodeField = By.xpath("//input[@name='zipcode_0']");
    private By taxPayerIDTypeSelectedSpan = By.xpath("//div[@id='taxpayeridtype']//span[@class='select2-chosen']/span");

    /**
     * Address information
     */
    private By addressHeadersList = By.xpath("//tr[@class='header noHover ng-scope']");
    private By addressTypeSelectorButton1 = By.xpath("//tr[@class='ng-scope' and @ng-if='address.pageConfig.addressuse.isShow'][%s]" +
            "//span[contains(@class, 'select2-arrow')]");
    private By countrySelectorButton1 = By.xpath("//tr[@class='ng-scope' and @ng-if='address.pageConfig.country.isShow'][%s]" +
            "//span[contains(@class, 'select2-arrow')]");
    private By addressField1 = By.xpath("//tr[@class='ng-scope' and @ng-if='address.pageConfig.addressline1.isShow'][%s]" +
            "//input");
    private By addressField2 = By.xpath("//tr[@class='ng-scope' and @ng-if='address.pageConfig.addressline2.isShow'][%s]" +
            "//input");
    private By addressCityField1 = By.xpath("//tr[@class='ng-scope' and @ng-if='address.pageConfig.cityname.isShow'][%s]" +
            "//input");
    private By addressStateSelectorButton1 = By.xpath("//tr[@class='ng-scope' and @ng-if='address.pageConfig.states.isShow'][%s]" +
            "//span[contains(@class, 'select2-arrow')]");
    private By getAddressZipCodeField1 = By.xpath("(//tr[@class='ng-scope' and @ng-if='address.pageConfig.zipcode.isShow'][%s]" +
            "//input)[1]");
    private By getAddressZipCodeField2 = By.xpath("(//tr[@class='ng-scope' and @ng-if='address.pageConfig.zipcode.isShow'][%s]" +
            "//input)[2]");
    private By addressYearsAtAddress = By.xpath("//tr[@class='ng-scope' and @ng-if='address.pageConfig.yearsinthisaddress.isShow'][%s]" +
            "//input");
    private By itemInDropDown = By.xpath("//div[contains(@class, 'select2-drop-active') and not(contains(@class, 'select2-display-none'))]" +
            "//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private By addAddress = By.xpath("//tr[@class='addRow']//button");
    private By addressTypeSelectedSpan = By.xpath("//tr[@class='ng-scope' and @ng-if='address.pageConfig.addressuse.isShow'][%s]" +
            "//span[@class='select2-chosen']/span");
    private By addressCountrySelectedSpan = By.xpath("//tr[@class='ng-scope' and @ng-if='address.pageConfig.country.isShow'][%s]" +
            "//span[@class='select2-chosen']/span");
    private By seasonalStartDate = By.xpath("//input[@id='seasonstartdate_1']");
    private By seasonalEndDate = By.xpath("//input[@id='seasonstopdate_1']");

    /**
     * Phones and Emails information
     */
    private By selectedPhoneTypeByIndex = By.xpath("(//tr[@ng-repeat='phone in basicInformation.phones']//div[@field-config='phone.pageConfig.phoneuse']" +
            "//span[@class='select2-chosen']//span)[%s]");
    private By selectedPhoneCountryByIndex = By.xpath("(//tr[@ng-repeat='phone in basicInformation.phones']//div[@field-config='phone.pageConfig.country']" +
            "//span[@class='select2-chosen']//span)[%s]");
    private By phoneTypeSelectorButton = By.xpath("(//tr[@class='ng-scope' and @ng-if='isNotHidden(phoneSubformConfig)'][%s]" +
            "//span[contains(@class, 'select2-arrow')])[1]");
    private By phoneCountrySelectorButton =By.xpath("(//tr[@class='ng-scope' and @ng-if='isNotHidden(phoneSubformConfig)'][%s]" +
            "//span[contains(@class, 'select2-arrow')])[2]");
    private By phoneField1 = By.xpath("//tr[@class='ng-scope' and @ng-if='isNotHidden(phoneSubformConfig)'][%s]" +
            "//div[@ng-if='phone.pageConfig.fullphonenumber.isShow']/input");
    private By emailTypeSelectorButton1 = By.xpath("//tr[@class='ng-scope' and @ng-if='isNotHidden(emailSubformConfig)'][%s]" +
            "//span[contains(@class, 'select2-arrow')]");
    private By emailField1 = By.xpath("//tr[@class='ng-scope' and @ng-if='isNotHidden(emailSubformConfig)'][%s]" +
            "//div[@ng-if='email.pageConfig.email.isShow']//input");
    private By addPhoneNumberButton = By.xpath("//button[contains(text(), 'Additional Phone Number')]");
    private By addEmailButton = By.xpath("//button[contains(text(), 'Additional Email Address')]");
    private By phoneRows = By.xpath("//tr[@ng-repeat='phone in basicInformation.phones']");

    /**
     * Client Details Organisation info
     */
    private By industrySearchIcon = By.xpath("//*[@data-test-id='field-naicsIndustry']//i[contains(@class, 'nyb-icon-search')]");
    private By industryField = By.xpath("//*[@data-test-id='field-naicsIndustry']//input[contains(@class, 'ui-select-search')]");
    private By industryOption = By.xpath("//*[@data-test-id='field-naicsIndustry']//div[contains(@class, 'ui-select-choices-row-inner')]/div[contains(text(), '%s')]");
    private By mailCodeSelectorButton1 = By.xpath("//*[@id='mailingcode']//span[contains(@class, 'select2-arrow')]");
    private By selectOfficerSelectorButton1 = By.xpath("//*[@id='officerid']//span[contains(@class, 'select2-arrow')]");
    private By akaField1 = By.xpath("//tr[@class='ng-scope' and  @ng-repeat='alias in basicInformation.aliases'][%s]" +
            "//*[@id='nickname']");
    private By addAKAButton = By.xpath("//button[contains(text(), 'Add AKA')]");

    /**
     * Filling Client Details Organisation info
     */

    public void clickIndustrySearchIcon() {
        waitForElementClickable(industrySearchIcon);
        click(industrySearchIcon);
    }

    public void setIndustryField(String industry) {
        waitForElementClickable(industryField);
        type(industry, industryField);
    }

    public void clickIndustryOption(String option) {
        waitForElementClickable(industryOption, option);
        click(industryOption, option);
    }

    public void clickMailCodeSelectorButton1() {
        waitForElementClickable(mailCodeSelectorButton1);
        click(mailCodeSelectorButton1);
    }

    public void clickSelectOfficerSelectorButton1() {
        waitForElementClickable(selectOfficerSelectorButton1);
        click(selectOfficerSelectorButton1);
    }

    public void setAKAField(int i, String text) {
        waitForElementClickable(akaField1, i);
        type(text, akaField1, i);
    }

    public void clickAddAKAButton() {
        waitForElementClickable(addAKAButton);
        click(addAKAButton);
    }

    /**
     * Filling phones and emails fields
     */
    public void clickPhoneTypeSelectorButton(int i) {
        waitForElementClickable(phoneTypeSelectorButton, i);
        click(phoneTypeSelectorButton, i);
    }

    public void clickPhoneCountrySelectorButton(int i) {
        waitForElementClickable(phoneCountrySelectorButton, i);
        click(phoneCountrySelectorButton, i);
    }

    public void setPhoneField(int i, String text) {
        waitForElementClickable(phoneField1, i);
        type(text, phoneField1, i);
    }

    public void clickEmailTypeSelectorButton1(int i) {
        waitForElementClickable(emailTypeSelectorButton1, i);
        click(emailTypeSelectorButton1, i);
    }

    public void setEmailField(int i, String text) {
        waitForElementClickable(emailField1, i);
        type(text, emailField1, i);
    }

    public void clickAddPhoneNumberButton() {
        waitForElementClickable(addPhoneNumberButton);
        click(addPhoneNumberButton);
    }

    public void clickAddEmailButton() {
        waitForElementClickable(addEmailButton);
        click(addEmailButton);
    }

    /**
     * Filling dynamic address fields
     */
    public void clickAddAddress() {
        waitForElementVisibility(addAddress);
        click(addAddress);
    }

    public void clickItemInDropDown(String item) {
        waitForElementVisibility(itemInDropDown, item);
        click(itemInDropDown, item);
    }

    public int getAddressFieldCount() {
        waitForElementVisibility(addressHeadersList);
        return getElements(addressHeadersList).size();
    }

    @Step("Click on 'Address Type' selectorButton")
    public void clickAddressTypeSelectorButton1(int i) {
        waitForElementClickable(addressTypeSelectorButton1, i);
        click(addressTypeSelectorButton1, i);
    }

    @Step("Click on 'Country' selectorButton")
    public void clickCountrySelectorButton1(int i) {
        waitForElementClickable(countrySelectorButton1, i);
        click(countrySelectorButton1, i);
    }

    @Step("Set 'Address Line1' value")
    public void setAddressField1Value(int i, String text) {
        waitForElementClickable(addressField1, i);
        type(text, addressField1, i);
    }

    @Step("Set 'Address Line2' value")
    public void setAddressField2Value(int i, String text) {
        waitForElementClickable(addressField2, i);
        type(text, addressField2, i);
    }

    @Step("Set 'Address City' value")
    public void setAddressCityValue(int i, String text) {
        waitForElementClickable(addressCityField1, i);
        type(text, addressCityField1, i);
    }

    @Step("Click on 'Address State' selectorButton")
    public void clickAddressStateSelectorButton(int i) {
        waitForElementClickable(addressStateSelectorButton1, i);
        click(addressStateSelectorButton1, i);
    }

    @Step("Set 'Address ZipCode1' value")
    public void setAddressZipCode1Value(int i, String text) {
        waitForElementClickable(getAddressZipCodeField1, i);
        type(text, getAddressZipCodeField1, i);
    }

    @Step("Set 'Seasonal Start Date' value")
    public void setSeasonalStartDate(String date) {
        waitForElementVisibility(seasonalStartDate);
        waitForElementClickable(seasonalStartDate);
        scrollToPlaceElementInCenter(seasonalStartDate);
        typeWithoutWipe("", seasonalStartDate);
        SelenideTools.sleep(1);
        typeWithoutWipe(date, seasonalStartDate);
    }

    @Step("Set 'Seasonal End Date' value")
    public void setSeasonalEndDate(String date) {
        waitForElementVisibility(seasonalEndDate);
        waitForElementClickable(seasonalEndDate);
        scrollToPlaceElementInCenter(seasonalEndDate);
        typeWithoutWipe("", seasonalEndDate);
        SelenideTools.sleep(1);
        typeWithoutWipe(date, seasonalEndDate);
    }

    @Step("Set 'Address ZipCode2' value")
    public void setAddressZipCode2Value(int i, String text) {
        waitForElementClickable(getAddressZipCodeField2, i);
        type(text, getAddressZipCodeField2, i);
    }

    @Step("Set 'Address YearsAt' value")
    public void setAddressYearsAtAddress(int i, String text) {
        waitForElementClickable(addressYearsAtAddress, i);
        type(text, addressYearsAtAddress, i);
    }

    /**
     * Client detailed information
     */
    private By suffixField = By.id("suffix");
    private By maidenFamilyNameField = By.id("maidenname");
    private By akaField = By.id("nickname");
    private By profilePhotoField = By.xpath("//tr[contains(@ng-if, 'profilephoto')]//input[@type='file']");
    private By genderField = By.xpath("//div[@id='gender']" +
            "//input[contains(@class, 'nb-select-search')]");
    private By genderSelectorButton = By.xpath("//div[@id='gender']" +
            "//span[contains(@class, 'select2-arrow')]");
    private By genderList = By.xpath("//div[@id='gender']" +
            "//li[contains(@role, 'option')]/div/span");
    private By genderSelectorOption = By.xpath("//div[@id='gender']" +
            "//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private By educationField = By.xpath("//div[@id='education']" +
            "//input[contains(@class, 'nb-select-search')]");
    private By educationSelectorButton = By.xpath("//div[@id='education']" +
            "//span[contains(@class, 'select2-arrow')]");
    private By educationList = By.xpath("//div[@id='education']" +
            "//li[contains(@role, 'option')]/div/span");
    private By educationSelectorOption = By.xpath("//div[@id='education']" +
            "//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private By incomeField = By.xpath("//div[@id='incomesalary']" +
            "//input[contains(@class, 'nb-select-search')]");
    private By incomeSelectorButton = By.xpath("//div[@id='incomesalary']" +
            "//span[contains(@class, 'select2-arrow')]");
    private By incomeList = By.xpath("//div[@id='incomesalary']" +
            "//li[contains(@role, 'option')]/div/span");
    private By incomeSelectorOption = By.xpath("//div[@id='incomesalary']" +
            "//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private By maritalStatusField = By.xpath("//div[@id='maritalstatus']" +
            "//input[contains(@class, 'nb-select-search')]");
    private By maritalStatusSelectorButton = By.xpath("//div[@id='maritalstatus']" +
            "//span[contains(@class, 'select2-arrow')]");
    private By maritalStatusList = By.xpath("//div[@id='maritalstatus']" +
            "//li[contains(@role, 'option')]/div/span");
    private By maritalStatusSelectorOption = By.xpath("//div[@id='maritalstatus']" +
            "//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private By occupationField = By.id("_occupation");
    private By consumerInformationIndicatorField = By.xpath("//div[@id='consumerinformationindicator']" +
            "//input[contains(@class, 'nb-select-search')]");
    private By consumerInformationIndicatorSelectorButton = By.xpath("//div[@id='consumerinformationindicator']" +
            "//span[contains(@class, 'select2-arrow')]");
    private By consumerInformationIndicatorList = By.xpath("//div[@id='consumerinformationindicator']" +
            "//li[contains(@role, 'option')]/div/span");
    private By consumerInformationIndicatorSelectorOption = By.xpath("//div[@id='consumerinformationindicator']" +
            "//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private By jobTitleField = By.id("jobtitle");
    private By ownOrRentField = By.xpath("//div[@id='ownrent']" +
            "//input[contains(@class, 'nb-select-search')]");
    private By ownOrRentSelectorButton = By.xpath("//div[@id='ownrent']" +
            "//span[contains(@class, 'select2-arrow')]");
    private By ownOrRentList = By.xpath("//div[@id='ownrent']" +
            "//li[contains(@role, 'option')]/div/span");
    private By ownOrRentSelectorOption = By.xpath("//div[@id='ownrent']" +
            "//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private By selectedMailCodeSpan = By.xpath("//div[@id='mailingcode']" +
            "//span[@class='select2-chosen']//span");
    private By mailCodeField = By.xpath("//div[@id='mailingcode']" +
            "//input[contains(@class, 'nb-select-search')]");
    private By mailCodeSelectorButton = By.xpath("//div[@id='mailingcode']" +
            "//span[contains(@class, 'select2-arrow')]");
    private By mailCodeList = By.xpath("//div[@id='mailingcode']" +
            "//li[contains(@role, 'option')]/div/span");
    private By mailCodeSelectorOption = By.xpath("//div[@id='mailingcode']" +
            "//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private By selectOfficerSpan = By.xpath("//div[@id='officerid']" +
            "//span[@class='select2-chosen']//span");
    private By selectOfficerField = By.xpath("//div[@id='officerid']" +
            "//input[contains(@class, 'nb-select-search')]");
    private By selectOfficerSelectorButton = By.xpath("//div[@id='officerid']" +
            "//span[contains(@class, 'select2-arrow')]");
    private By selectOfficerList = By.xpath("//div[@id='officerid']" +
            "//li[contains(@role, 'option')]/div/span");
    private By selectOfficerSelectorOption = By.xpath("//div[@id='officerid']" +
            "//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private By userDefinedField_1 = By.id("userdefinedfield1");
    private By userDefinedField_2 = By.id("userdefinedfield2");
    private By userDefinedField_3 = By.id("userdefinedfield3");
    private By userDefinedField_4 = By.id("userdefinedfield4");
    private By phoneField = By.name("fullphonenumber_0");
    private By emailTypeField = By.xpath("//div[@name='emailuse_0']" +
            "//input[contains(@class, 'nb-select-search')]");
    private By emailTypeSelectorButton = By.xpath("//div[@name='emailuse_0']" +
            "//span[contains(@class, 'select2-arrow')]");
    private By emailTypeList = By.xpath("//div[@name='emailuse_0']" +
            "//li[contains(@role, 'option')]/div/span");
    private By emailTypeSelectorOption = By.xpath("//div[@name='emailuse_0']" +
            "//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private By emailField = By.name("email_0");

    /**
     * Upload documentation
     */
    private By documentationField = By.xpath("//section[contains(@class, 'uploadDocumentation')]//input[@type='file']");
    private By documentationIdTypeField = By.xpath("//div[@id='type']" +
            "//input[contains(@class, 'nb-select-search')]");
    private By documentationIdTypeSelectorButton = By.xpath("//div[@id='type']" +
            "//span[contains(@class, 'select2-arrow')]");
    private By documentationIdTypeList = By.xpath("//div[@id='type']" +
            "//li[contains(@role, 'option')]/div/span");
    private By documentationIdTypeSelectorOption = By.xpath("//div[@id='type']" +
            "//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private By documentationIdNumberField = By.id("id");
    private By documentationIssuedByField = By.xpath("//div[@id='state']" +
            "//input[contains(@class, 'nb-select-search')]");
    private By documentationIssuedBySelectorButton = By.xpath("//div[@id='state']" +
            "//span[contains(@class, 'select2-arrow')]");
    private By documentationIssuedByList = By.xpath("//div[@id='state']" +
            "//li[contains(@role, 'option')]/div/span");
    private By documentationIssuedBySelectorOption = By.xpath("//div[@id='state']" +
            "//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private By documentationCountryField = By.xpath("//div[@id='country']" +
            "//input[contains(@class, 'nb-select-search')]");
    private By documentationCountrySelectorButton = By.xpath("//div[@id='country']" +
            "//span[contains(@class, 'select2-arrow')]");
    private By documentationCountryList = By.xpath("//div[@id='country']" +
            "//li[contains(@role, 'option')]/div/span");
    private By documentationCountrySelectorOption = By.xpath("//div[@id='country']" +
            "//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private By documentationExpirationDateField = By.id("expiration");
    private By documentationSaveButton = By.xpath("//form[contains(@name, 'documentForm')]//button[contains(@ng-click, 'save')]");

    /**
     * Upload signature
     */
    private By clientSignatureField = By.xpath("//section[contains(@ng-show, 'customerSignature')]//input[@type='file' and @name='file']");

    private By viewMemberProfileButton = By.xpath("//button[@data-test-id='go-CustomerPage']");

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

    @Step("Set 'Organization Name' value")
    public void setOrganizationName(String organizationName) {
        waitForElementVisibility(organizationNameField);
        waitForElementClickable(organizationNameField);
        type(organizationName, organizationNameField);
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
        SelenideTools.sleep(Constants.MICRO_TIMEOUT);
        typeWithoutWipe(birthDateValue, birthDateField);
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

    @Step("Click on 'Expiration Date' calendar icon")
    public void clickExpirationDateCalendarIcon() {
        waitForElementClickable(expirationDateCalendarIcon);
        click(expirationDateCalendarIcon);
    }

    @Step("Set 'Expiration Date' value")
    public void setExpirationDateValue(String expirationDateValue) {
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
    @Step("Get 'Phone' row count")
    public int getPhoneRowCount() {
        waitForElementVisibility(phoneRows);
        waitForElementClickable(phoneRows);
        return getElementsWithZeroOption(phoneRows).size();
    }

    @Step("Get selected 'Mail code' value")
    public String getSelectedMailCodeValue() {
        waitForElementVisibility(selectedMailCodeSpan);
        waitForElementClickable(selectedMailCodeSpan);
       return getElementText(selectedMailCodeSpan);
    }

    @Step("Get selected 'Select officer' value")
    public String getSelectedOfficerValue() {
        waitForElementVisibility(selectOfficerSpan);
        waitForElementClickable(selectOfficerSpan);
        return getElementText(selectOfficerSpan);
    }

    @Step("Get selected 'Phone Type' value by {0} index")
    public String getSelectedPhoneTypeByIndex(int index) {
        waitForElementVisibility(selectedPhoneTypeByIndex, index);
        waitForElementClickable(selectedPhoneTypeByIndex, index);
        return getElementText(selectedPhoneTypeByIndex, index);
    }

    @Step("Get selected 'Phone Country' value by {0} index")
    public String getSelectedPhoneCountryByIndex(int index) {
        waitForElementVisibility(selectedPhoneCountryByIndex, index);
        waitForElementClickable(selectedPhoneCountryByIndex, index);
        return getElementText(selectedPhoneCountryByIndex, index);
    }

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
        Selenide.executeJavaScript("arguments[0].style.height='auto'; arguments[0].style.visibility='visible';",
                getWebElement(profilePhotoField));
        type(profilePhotoPath, profilePhotoField);
        Selenide.executeJavaScript("arguments[0].style.height='auto'; arguments[0].style.visibility='hidden';",
                getWebElement(profilePhotoField));
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
        Selenide.executeJavaScript("arguments[0].style.height='auto'; arguments[0].style.visibility='visible';",
                getWebElement(documentationField));
        type(clientDocument, documentationField);
        Selenide.executeJavaScript("arguments[0].style.height='auto'; arguments[0].style.visibility='hidden';",
                getWebElement(documentationField));
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
        SelenideTools.sleep(1);
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
        Selenide.executeJavaScript("arguments[0].style.height='auto'; arguments[0].style.visibility='visible';",
                getWebElement(clientSignatureField));
        type(clientDocument, clientSignatureField);
        Selenide.executeJavaScript("arguments[0].style.height='auto'; arguments[0].style.visibility='hidden';",
                getWebElement(clientSignatureField));
    }

    @Step("Click 'View Member Profile'")
    public void clickViewMemberProfileButton() {
        waitForElementVisibility(viewMemberProfileButton);
        waitForElementClickable(viewMemberProfileButton);
        click(viewMemberProfileButton);
    }

    @Step("Get tax payer id type selected span")
    public String getTaxPayerIdTypeSelectedSpan(int index) {
        waitForElementVisibility(taxPayerIDTypeSelectedSpan, index);
        waitForElementClickable(taxPayerIDTypeSelectedSpan, index);
        return getElementText(taxPayerIDTypeSelectedSpan, index).trim();
    }

    @Step("Get address type selected value")
    public String getAddressTypeSelectedSpan(int index) {
        waitForElementVisibility(addressTypeSelectedSpan, index);
        waitForElementClickable(addressTypeSelectedSpan, index);
        return getElementText(addressTypeSelectedSpan, index).trim();
    }

    @Step("Get address country selected value")
    public String getAddressCountrySelectedSpan(int index) {
        waitForElementVisibility(addressCountrySelectedSpan, index);
        waitForElementClickable(addressCountrySelectedSpan, index);
        return getElementText(addressCountrySelectedSpan, index).trim();
    }
}