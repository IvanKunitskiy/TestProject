package com.nymbus.pages.accounts;

import com.nymbus.core.base.PageTools;
import com.nymbus.core.utils.SelenideTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.util.List;

public class AddAccountPage extends PageTools {

    private By productTypeSelectorButton = By.id("accounttype");
    private By productTypeSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private By productTypeList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private By productSelectorButton = By.xpath("//*[@id='accountclasstype']");
    private By productList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private By boxSizeSelectorButton = By.id("boxsize");
    private By boxSizeSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private By boxSizeList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private By boxSizeField = By.xpath("//*[@id='boxsize']//input[contains(@class, 'nb-select-search')]");
    private By selectProductTypeField = By.xpath("//*[@id='accounttype']/a/span[contains(text(), 'Select')]");
    private By productTypeInputField = By.xpath("//div[@data-test-id='field-accounttype']//input[contains(@class, 'nb-select-search')]");
    private By accountNumberField = By.xpath("//input[@data-test-id='field-accountnumber']");
    private By accountTitleField = By.xpath("//input[@data-test-id='field-accounttitlemailinginstructions']");
    private By bankBranchSelectorButton = By.xpath("//*[@id='bankbranch']");
    private By bankBranchSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private By saveAccountButton = By.xpath("//button[@data-test-id='action-save']");
    private By bankBranchList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private By accountType = By.xpath("//div[@data-test-id='field-customertype']/a/span/span");
    private By dateOpened = By.xpath("//input[@data-test-id='field-dateopened']");
    private By productSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private By statementCycleSelectorButton = By.xpath("//*[@id='statementcycle']");
    private By statementCycleList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private By bankBranch = By.xpath("//div[@data-test-id='field-bankbranch']/a/span/span[contains(@class, 'ng-binding')]");
    private By statementFlagInput = By.xpath("//input[@data-test-id='field-statementflag']");
    private By interestRateInput = By.xpath("//input[@data-test-id='field-interestrate']");
    private By earningCreditRateInput = By.xpath("//input[@data-test-id='field-earningscreditrate']");
    private By optInOutDateCalendarIcon = By.xpath("//div[input[@id='dbcodpstatusdate']]/div[span[@class='nyb-icon-calendar']]");
    private By optInOutDateInputField = By.xpath("//input[@id='dbcodpstatusdate']");
    private By dateOpenedField = By.xpath("//input[@id='dateopened']");
    private By dateOfBirth = By.xpath("//input[@id='dateofbirth']");
    private By iraDistributionCode = By.xpath("//div[@id='iradistributioncode']//a//span/span");
    private By iraDistributionFrequency = By.xpath("//div[@id='iradistributionfrequency']//a//span/span");
    private By iraDistributionAmountField = By.xpath("//input[@id='iradistributionamount']");

    /**
     * Account holders and signers
     */

    private By accountHolderName = By.xpath("//a[@data-test-id='action-goCustomerProfile']");
    private By accountHolderRelationship = By.xpath("//div[@data-test-id='field-relationshiptype_0']//a//span//span");
    private By accountHolderClientType = By.xpath("//input[@data-test-id='field-typeid_0']");
    private By accountHolderTaxID = By.xpath("//input[@data-test-id='field-taxIdNumber']");
    private By statementCycleSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private By accountHolderAddress = By.xpath("//div[@data-test-id='field-addressid_0']/a/span/span");

    /**
     * Originating officer
     */

    private By originatingOfficer = By.xpath("//div[@data-test-id='field-originatingOfficer']/a/span/span");
    private By currentOfficer = By.xpath("//div[@data-test-id='field-officer']/a/span/span");
    private By optInOutStatus = By.xpath("//div[@data-test-id='field-dbcodpstatus']/a/span/span");

    private By currentOfficerSelectorButton = By.xpath("//div[@data-test-id='field-officer']");
    private By currentOfficerList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private By currentOfficerSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private By callClassCodeSelectorButton = By.xpath("//div[@data-test-id='field-callclasscode']");
    private By callClassCodeList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private By callClassCodeSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private By accountAnalysisSelectorButton = By.xpath("//div[@id='accountanalysis']");
    private By accountAnalysisList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private By accountAnalysisSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private By chargeOrAnalyzeSelectorButton = By.xpath("//div[@id='chargeoranalyze']");
    private By chargeOrAnalyzeList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private By chargeOrAnalyzeSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private By interestFrequencySelectorButton = By.xpath("//div[@id='interestfrequency']");
    private By interestFrequencyList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private By interestFrequencySelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private By correspondingAccountSelectorButton = By.xpath("//div[@id='correspondingaccountid']");
    private By correspondingAccountList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private By correspondingAccountSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private By iraDistributionFrequencySelectorButton = By.xpath("//div[@id='iradistributionfrequency']");
    private By iraDistributionFrequencyList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private By iraDistributionFrequencySelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private By iraDistributionCodeSelectorButton = By.xpath("//div[@id='iradistributioncode']");
    private By iraDistributionCodeList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private By iraDistributionCodeSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    @Step("Click the 'IRA Distribution Frequency' option")
    public void clickIRADistributionCodeSelectorOption(String iraDistributionCodeOption) {
        waitForElementVisibility(iraDistributionCodeSelectorOption, iraDistributionCodeOption);
        waitForElementClickable(iraDistributionCodeSelectorOption, iraDistributionCodeOption);
        click(iraDistributionCodeSelectorOption, iraDistributionCodeOption);
    }

    @Step("Returning list of 'IRA Distribution Frequency' options")
    public List<String> getIRADistributionCodeList() {
        waitForElementVisibility(iraDistributionCodeList);
        waitForElementClickable(iraDistributionCodeList);
        return getElementsText(iraDistributionCodeList);
    }

    @Step("Click the 'IRA Distribution Frequency' selector button")
    public void clickIRADistributionCodeSelectorButton() {
        waitForElementVisibility(iraDistributionCodeSelectorButton);
        scrollToElement(iraDistributionCodeSelectorButton);
        waitForElementClickable(iraDistributionCodeSelectorButton);
        click(iraDistributionCodeSelectorButton);
    }

    @Step("Click the 'IRA Distribution Frequency' option")
    public void clickIRADistributionFrequencySelectorOption(String iraDistributionFrequencyOption) {
        waitForElementVisibility(iraDistributionFrequencySelectorOption, iraDistributionFrequencyOption);
        waitForElementClickable(iraDistributionFrequencySelectorOption, iraDistributionFrequencyOption);
        click(iraDistributionFrequencySelectorOption, iraDistributionFrequencyOption);
    }

    @Step("Returning list of 'IRA Distribution Frequency' options")
    public List<String> getIRADistributionFrequencyList() {
        waitForElementVisibility(iraDistributionFrequencyList);
        waitForElementClickable(iraDistributionFrequencyList);
        return getElementsText(iraDistributionFrequencyList);
    }

    @Step("Click the 'IRA Distribution Frequency' selector button")
    public void clickIRADistributionFrequencySelectorButton() {
        waitForElementVisibility(iraDistributionFrequencySelectorButton);
        scrollToElement(iraDistributionFrequencySelectorButton);
        waitForElementClickable(iraDistributionFrequencySelectorButton);
        click(iraDistributionFrequencySelectorButton);
    }

    @Step("Click the 'Corresponding Account' option")
    public void clickCorrespondingAccountSelectorOption(String correspondingAccountOption) {
        waitForElementVisibility(correspondingAccountSelectorOption, correspondingAccountOption);
        waitForElementClickable(correspondingAccountSelectorOption, correspondingAccountOption);
        click(correspondingAccountSelectorOption, correspondingAccountOption);
    }

    @Step("Returning list of 'Corresponding Account' options")
    public List<String> getCorrespondingAccountList() {
        waitForElementVisibility(correspondingAccountList);
        waitForElementClickable(correspondingAccountList);
        return getElementsText(correspondingAccountList);
    }

    @Step("Click the 'Corresponding Account' selector button")
    public void clickCorrespondingAccountSelectorButton() {
        waitForElementVisibility(correspondingAccountSelectorButton);
        scrollToElement(correspondingAccountSelectorButton);
        waitForElementClickable(correspondingAccountSelectorButton);
        click(correspondingAccountSelectorButton);
    }

    @Step("Click the 'Interest Frequency' option")
    public void clickInterestFrequencySelectorOption(String interestFrequencyOption) {
        waitForElementVisibility(interestFrequencySelectorOption, interestFrequencyOption);
        waitForElementClickable(interestFrequencySelectorOption, interestFrequencyOption);
        click(interestFrequencySelectorOption, interestFrequencyOption);
    }

    @Step("Returning list of 'Interest Frequency' options")
    public List<String> getInterestFrequencyList() {
        waitForElementVisibility(interestFrequencyList);
        waitForElementClickable(interestFrequencyList);
        return getElementsText(interestFrequencyList);
    }

    @Step("Click the 'Interest Frequency' selector button")
    public void clickInterestFrequencySelectorButton() {
        waitForElementVisibility(interestFrequencySelectorButton);
        scrollToElement(interestFrequencySelectorButton);
        waitForElementClickable(interestFrequencySelectorButton);
        click(interestFrequencySelectorButton);
    }

    @Step("Set 'Date Opened' value")
    public void setDateNextIRADistributionValue(String dateNextIRADistributionValue) {
        waitForElementVisibility(dateOpenedField);
        waitForElementClickable(dateOpenedField);
        typeWithoutWipe("", dateOpenedField);
        SelenideTools.sleep(1);
        typeWithoutWipe(dateNextIRADistributionValue, dateOpenedField);
    }

    @Step("Set 'Date Opened' value")
    public void setDateOpenedValue(String dateOpenedValue) {
        waitForElementVisibility(dateOpenedField);
        waitForElementClickable(dateOpenedField);
        typeWithoutWipe("", dateOpenedField);
        SelenideTools.sleep(1);
        typeWithoutWipe(dateOpenedValue, dateOpenedField);
    }

    @Step("Set 'DBC ODP Opt In/Out Status Date' value")
    public void setOptInOutDateValue(String optInOutDateValue) {
        waitForElementVisibility(optInOutDateInputField);
        waitForElementClickable(optInOutDateInputField);
        typeWithoutWipe("", optInOutDateInputField);
        SelenideTools.sleep(1);
        typeWithoutWipe(optInOutDateValue, optInOutDateInputField);
    }

    @Step("Click on 'DBC ODP Opt In/Out Status Date' calendar icon")
    public void clickOptInOutDateCalendarIcon() {
        waitForElementClickable(optInOutDateCalendarIcon);
        click(optInOutDateCalendarIcon);
    }

    @Step("Click the 'Account Analysis' option")
    public void clickChargeOrAnalyzeSelectorOption(String chargeOrAnalyzeOption) {
        waitForElementVisibility(chargeOrAnalyzeSelectorOption, chargeOrAnalyzeOption);
        waitForElementClickable(chargeOrAnalyzeSelectorOption, chargeOrAnalyzeOption);
        click(chargeOrAnalyzeSelectorOption, chargeOrAnalyzeOption);
    }

    @Step("Returning list of 'Charge or Analyze' options")
    public List<String> getChargeOrAnalyzeList() {
        waitForElementVisibility(chargeOrAnalyzeList);
        waitForElementClickable(chargeOrAnalyzeList);
        return getElementsText(chargeOrAnalyzeList);
    }

    @Step("Click the 'Charge or Analyze' selector button")
    public void clickChargeOrAnalyzeSelectorButton() {
        waitForElementVisibility(chargeOrAnalyzeSelectorButton);
        scrollToElement(callClassCodeSelectorButton);
        waitForElementClickable(chargeOrAnalyzeSelectorButton);
        click(chargeOrAnalyzeSelectorButton);
    }

    @Step("Set 'Earning Credit Rate' option")
    public void setEarningCreditRate(String earningCreditRateValue) {
        waitForElementVisibility(earningCreditRateInput);
        waitForElementClickable(earningCreditRateInput);
        type(earningCreditRateValue, earningCreditRateInput);
    }

    @Step("Set 'Interest Rate' option")
    public void setInterestRate(String interestRateValue) {
        waitForElementVisibility(interestRateInput);
        waitForElementClickable(interestRateInput);
        type(interestRateValue, interestRateInput);
    }

    @Step("Click the 'Account Analysis' option")
    public void clickAccountAnalysisSelectorOption(String callClassCodeOption) {
        waitForElementVisibility(accountAnalysisSelectorOption, callClassCodeOption);
        waitForElementClickable(accountAnalysisSelectorOption, callClassCodeOption);
        click(accountAnalysisSelectorOption, callClassCodeOption);
    }

    @Step("Returning list of 'Account Analysis' options")
    public List<String> getAccountAnalysisList() {
        waitForElementVisibility(accountAnalysisList);
        waitForElementClickable(accountAnalysisList);
        return getElementsText(accountAnalysisList);
    }

    @Step("Click the 'Account Analysis' selector button")
    public void clickAccountAnalysisSelectorButton() {
//        wait(2);
        waitForElementVisibility(accountAnalysisSelectorButton);
        waitForElementClickable(accountAnalysisSelectorButton);
        click(accountAnalysisSelectorButton);
    }

    @Step("Click the 'Call Class Code' option")
    public void clickCallClassCodeSelectorOption(String callClassCodeOption) {
        waitForElementVisibility(callClassCodeSelectorOption, callClassCodeOption);
        waitForElementClickable(callClassCodeSelectorOption, callClassCodeOption);
        click(callClassCodeSelectorOption, callClassCodeOption);
    }

    @Step("Returning list of 'Call Class Code' options")
    public List<String> getCallClassCodeList() {
        waitForElementVisibility(callClassCodeList);
        waitForElementClickable(callClassCodeList);
        return getElementsText(callClassCodeList);
    }

    @Step("Click the 'Call Class Code' selector button")
    public void clickCallClassCodeSelectorButton() {
        waitForElementVisibility(callClassCodeSelectorButton);
        scrollToElement(callClassCodeSelectorButton);
        waitForElementClickable(callClassCodeSelectorButton);
        click(callClassCodeSelectorButton);
    }

    @Step("Click the 'Current Officer' option")
    public void clickCurrentOfficerSelectorOption(String currentOfficerOption) {
        waitForElementVisibility(currentOfficerSelectorOption, currentOfficerOption);
        waitForElementClickable(currentOfficerSelectorOption, currentOfficerOption);
        click(currentOfficerSelectorOption, currentOfficerOption);
    }

    @Step("Returning list of 'Current Officer' options")
    public List<String> getCurrentOfficerList() {
        waitForElementVisibility(currentOfficerList);
        waitForElementClickable(currentOfficerList);
        return getElementsText(currentOfficerList);
    }

    @Step("Click the 'Current Officer' selector button")
    public void clickCurrentOfficerSelectorButton() {
        waitForElementVisibility(currentOfficerSelectorButton);
        scrollToElement(currentOfficerSelectorButton);
        waitForElementClickable(currentOfficerSelectorButton);
        click(currentOfficerSelectorButton);
    }

    @Step("Returning the 'Address' value")
    public String getAccountHolderAddress() {
        waitForElementVisibility(accountHolderAddress);
        waitForElementClickable(accountHolderAddress);
        return getElementText(accountHolderAddress);
    }

    @Step("Returning the 'Date Of Birth' value")
    public String getDateOfBirth() {
        waitForElementVisibility(dateOfBirth);
        waitForElementClickable(dateOfBirth);
        SelenideTools.sleep(2);
        return getElementAttributeValue("value", dateOfBirth);
    }

    @Step("Returning the 'Bank Branch' value")
    public String getBankBranch() {
        waitForElementVisibility(bankBranch);
        waitForElementClickable(bankBranch);
        return getElementText(bankBranch);
    }

    @Step("Returning the 'IRA Distribution Code' value")
    public String getIRADistributionCode() {
        waitForElementVisibility(iraDistributionCode);
        waitForElementClickable(iraDistributionCode);
        return getElementText(iraDistributionCode);
    }

    @Step("Returning the 'IRA Distribution Frequency' value")
    public String getIRADistributionFrequency() {
        waitForElementVisibility(iraDistributionFrequency);
        waitForElementClickable(iraDistributionFrequency);
        return getElementText(iraDistributionFrequency);
    }

    @Step("Click the 'Statement Cycle' option")
    public void clickStatementCycleOption(String statementCycleOption) {
        waitForElementVisibility(statementCycleSelectorOption, statementCycleOption);
        waitForElementClickable(statementCycleSelectorOption, statementCycleOption);
        click(statementCycleSelectorOption, statementCycleOption);
    }

    @Step("Returning list of 'Statement Cycle' options")
    public List<String> getStatementCycleList() {
        waitForElementVisibility(statementCycleList);
        waitForElementClickable(statementCycleList);
        return getElementsText(statementCycleList);
    }

    @Step("Click the 'Statement Cycle' selector button")
    public void clickStatementCycleSelectorButton() {
        waitForElementVisibility(statementCycleSelectorButton);
        waitForElementClickable(statementCycleSelectorButton);
        click(statementCycleSelectorButton);
    }

    @Step("Click the 'Product' selector button")
    public void clickProductSelectorButton() {
        waitForElementVisibility(productSelectorButton);
        waitForElementClickable(productSelectorButton);
        SelenideTools.sleep(2);
        click(productSelectorButton);
    }

    @Step("Returning list of 'Product' options")
    public List<String> getProductList() {
        waitForElementVisibility(productList);
        waitForElementClickable(productList);
        return getElementsText(productList);
    }

    @Step("Click the 'Product' option")
    public void clickProductOption(String productOption) {
        waitForElementVisibility(productSelectorOption, productOption);
        waitForElementClickable(productSelectorOption, productOption);
        click(productSelectorOption, productOption);
    }

    @Step("Returning the 'DBC ODP Opt In/Out Status' value")
    public String getOptInOutStatus() {
        waitForElementVisibility(optInOutStatus);
        waitForElementClickable(optInOutStatus);
        return getElementText(optInOutStatus);
    }

    @Step("Returning the 'Originating Officer' value")
    public String getOriginatingOfficer() {
        waitForElementVisibility(originatingOfficer);
        waitForElementClickable(originatingOfficer);
        return getElementText(originatingOfficer);
    }

    @Step("Returning the 'Current Officer' value")
    public String getCurrentOfficer() {
        waitForElementVisibility(currentOfficer);
        waitForElementClickable(currentOfficer);
        return getElementText(currentOfficer);
    }

    @Step("Returning the 'Date Opened' value")
    public String getDateOpened() {
        waitForElementVisibility(dateOpened);
        waitForElementClickable(dateOpened);
        SelenideTools.sleep(2);
        return getElementAttributeValue("value", dateOpened);
    }

    @Step("Returning the 'Account Holder Tax ID' value")
    public String getAccountHolderTaxID() {
        waitForElementVisibility(accountHolderTaxID);
        String taxid = getElementAttributeValue("value", accountHolderTaxID);
        return taxid.replaceAll("-", "");
    }

    @Step("Returning the 'Account Holder Client type' value")
    public String getAccountHolderClientType() {
        waitForElementVisibility(accountHolderClientType);
        return getElementAttributeValue("value", accountHolderClientType);
    }

    @Step("Returning the 'Account Holder Relationship' value")
    public String getAccountHolderRelationship() {
        waitForElementVisibility(accountHolderRelationship);
        waitForElementClickable(accountHolderRelationship);
        return getElementText(accountHolderRelationship);
    }

    @Step("Returning the 'Account Holder Name' value")
    public String getAccountHolderName() {
        waitForElementVisibility(accountHolderName);
        waitForElementClickable(accountHolderName);
        return getElementText(accountHolderName);
    }

    @Step("Returning the 'Account type' value")
    public String getAccountType() {
        waitForElementVisibility(accountType);
        waitForElementClickable(accountType);
        return getElementText(accountType);
    }

    @Step("Returning list of 'Product Type'")
    public List<String> getBankBranchList() {
        waitForElementVisibility(bankBranchList);
        waitForElementClickable(bankBranchList);
        return getElementsText(bankBranchList);
    }

    @Step("Click the 'Product Type' selector button")
    public void clickProductTypeSelectorButton() {
        waitForElementVisibility(productTypeSelectorButton);
        waitForElementClickable(productTypeSelectorButton);
        click(productTypeSelectorButton);
    }

    @Step("Click the 'Product Type' option")
    public void clickProductTypeOption(String productTypeOption) {
        waitForElementVisibility(productTypeSelectorOption, productTypeOption);
        waitForElementClickable(productTypeSelectorOption, productTypeOption);
        click(productTypeSelectorOption, productTypeOption);
    }

    @Step("Click the 'Box size' selector button")
    public void clickBoxSizeSelectorButton() {
        waitForElementVisibility(boxSizeSelectorButton);
        waitForElementClickable(boxSizeSelectorButton);
        click(boxSizeSelectorButton);
    }

    @Step("Returning list of 'Box Size'")
    public List<String> getBoxSizeList() {
        waitForElementVisibility(boxSizeList);
        waitForElementClickable(boxSizeList);
        return getElementsText(boxSizeList);
    }

    @Step("Set 'Box Size' option")
    public void setBoxSizeOption(String boxSizeOptionValue) {
        waitForElementVisibility(boxSizeField);
        waitForElementClickable(boxSizeField);
        type(boxSizeOptionValue, boxSizeField);
    }

    @Step("Click the 'Box size' option")
    public void clickBoxSizeSelectorOption(String boxSizeOption) {
        waitForElementVisibility(boxSizeSelectorOption, boxSizeOption);
        waitForElementClickable(boxSizeSelectorOption, boxSizeOption);
        click(boxSizeSelectorOption, boxSizeOption);
    }

    @Step("Returning list of 'Product Type'")
    public List<String> getProductTypeList() {
        waitForElementVisibility(productTypeList);
        waitForElementClickable(productTypeList);
        return getElementsText(productTypeList);
    }

    @Step("Set 'Product Type' option")
    public void setProductTypeOption(String productTypeOptionValue) {
        waitForElementVisibility(productTypeSelectorButton);
        waitForElementClickable(productTypeSelectorButton);
        click(productTypeSelectorButton);
        waitForElementVisibility(productTypeInputField);
        waitForElementClickable(productTypeInputField);
        type(productTypeOptionValue, productTypeInputField);
    }

    @Step("Set 'Account Number' value")
    public void setAccountNumberValue(String accountNumberValue) {
        waitForElementVisibility(accountNumberField);
        waitForElementClickable(accountNumberField);
        type(accountNumberValue, accountNumberField);
    }

    @Step("Set 'Account Title' value")
    public void setAccountTitleValue(String accountTitleValue) {
        waitForElementVisibility(accountTitleField);
        waitForElementClickable(accountTitleField);
        type(accountTitleValue, accountTitleField);
    }

    @Step("Set 'IRA distribution amount' value")
    public void setIRADistributionAmountValue(String iraDistributionAmountValue) {
        waitForElementVisibility(iraDistributionAmountField);
        waitForElementClickable(iraDistributionAmountField);
        type(iraDistributionAmountValue, iraDistributionAmountField);
    }

    @Step("Click the 'Bank branch' selector button")
    public void clickBankBranchSelectorButton() {
        waitForElementVisibility(bankBranchSelectorButton);
        scrollToElement(bankBranchSelectorButton);
        waitForElementClickable(bankBranchSelectorButton);
        click(bankBranchSelectorButton);
    }

    @Step("Click the 'Bank branch' option")
    public void clickBankBranchOption(String bankBranchOption) {
        waitForElementVisibility(bankBranchSelectorOption, bankBranchOption);
        waitForElementClickable(bankBranchSelectorOption, bankBranchOption);
        click(bankBranchSelectorOption, bankBranchOption);
    }

    @Step("Click the 'Save' button")
    public void clickSaveAccountButton() {
        waitForElementVisibility(saveAccountButton);
        scrollToElement(saveAccountButton);
        waitForElementClickable(saveAccountButton);
        click(saveAccountButton);
    }
}
