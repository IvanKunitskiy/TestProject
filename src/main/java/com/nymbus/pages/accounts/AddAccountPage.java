package com.nymbus.pages.accounts;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

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
    private By bankBranch = By.xpath("//div[@data-test-id='field-bankbranch']/a/span[contains(@class, 'ng-binding')]");
    private By statementFlagInput = By.xpath("//input[@data-test-id='field-statementflag']");
    private By interestRateInput = By.xpath("//input[@data-test-id='field-interestrate']");
    private By earningCreditRateInput = By.xpath("//input[@data-test-id='field-earningscreditrate']");
    private By optInOutDateCalendarIcon = By.xpath("//div[input[@id='dbcodpstatusdate']]/div[span[@class='nyb-icon-calendar']]");
    private By optInOutDateInputField = By.xpath("//input[@id='dbcodpstatusdate']");
    private By dateOpenedField = By.xpath("//input[@id='dateopened']");


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

    private Locator originatingOfficer = new XPath("//div[@data-test-id='field-originatingOfficer']/a/span/span");
    private Locator currentOfficer = new XPath("//div[@data-test-id='field-officer']/a/span/span");
    private Locator optInOutStatus = new XPath("//div[@data-test-id='field-dbcodpstatus']/a/span/span");

    private Locator currentOfficerSelectorButton = new XPath("//div[@data-test-id='field-officer']");
    private Locator currentOfficerList = new XPath("//li[contains(@role, 'option')]/div/span");
    private Locator currentOfficerSelectorOption = new XPath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private Locator callClassCodeSelectorButton = new XPath("//div[@data-test-id='field-callclasscode']");
    private Locator callClassCodeList = new XPath("//li[contains(@role, 'option')]/div/span");
    private Locator callClassCodeSelectorOption = new XPath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private Locator accountAnalysisSelectorButton = new XPath("//div[@id='accountanalysis']");
    private Locator accountAnalysisList = new XPath("//li[contains(@role, 'option')]/div/span");
    private Locator accountAnalysisSelectorOption = new XPath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private Locator chargeOrAnalyzeSelectorButton = new XPath("//div[@id='chargeoranalyze']");
    private Locator chargeOrAnalyzeList = new XPath("//li[contains(@role, 'option')]/div/span");
    private Locator chargeOrAnalyzeSelectorOption = new XPath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private Locator interestFrequencySelectorButton = new XPath("//div[@id='interestfrequency']");
    private Locator interestFrequencyList = new XPath("//li[contains(@role, 'option')]/div/span");
    private Locator interestFrequencySelectorOption = new XPath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private Locator primaryAccountForCombinedStatementSelectorButton = new XPath("//div[@id='ddaaccountidforcombinedstatement']");
    private Locator primaryAccountForCombinedStatementList = new XPath("//li[contains(@role, 'option')]/div/span");
    private Locator primaryAccountForCombinedStatementSelectorOption = new XPath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private Locator correspondingAccountSelectorButton = new XPath("//div[@id='correspondingaccountid']");
    private Locator correspondingAccountList = new XPath("//li[contains(@role, 'option')]/div/span");
    private Locator correspondingAccountSelectorOption = new XPath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

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

    @Step("Set 'DBC ODP Opt In/Out Status Date' value")
    public void setDateOpenedValue(String dateOpenedValue) {
        waitForElementVisibility(dateOpenedField);
        waitForElementClickable(dateOpenedField);
        typeWithoutWipe("", dateOpenedField);
//        wait(1);
        typeWithoutWipe(dateOpenedValue, dateOpenedField);
    }

    @Step("Set 'DBC ODP Opt In/Out Status Date' value")
    public void setOptInOutDateValue(String optInOutDateValue) {
        waitForElementVisibility(optInOutDateInputField);
        waitForElementClickable(optInOutDateInputField);
        typeWithoutWipe("", optInOutDateInputField);
//        wait(1);
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

    @Step("Generate 'Earning Credit Rate' value")
    public String generateEarningCreditRateValue() {
        Random ran = new Random();
        return String.valueOf(ran.nextInt(100));
    }

    @Step("Set 'Earning Credit Rate' option")
    public void setEarningCreditRate(String earningCreditRateValue) {
        waitForElementVisibility(earningCreditRateInput);
        waitForElementClickable(earningCreditRateInput);
        type(earningCreditRateValue, earningCreditRateInput);
    }

    @Step("Geterate 'Interest Rate' value")
    public String generateInterestRateValue() {
        Random ran = new Random();
        DecimalFormat df = new DecimalFormat("###.####");
        return String.valueOf(df.format(ran.nextFloat() * 100));
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

    @Step("Returning the 'Bank Branch' value")
    public String getBankBranch() {
        waitForElementVisibility(bankBranch);
        waitForElementClickable(bankBranch);
        return getElementText(bankBranch);
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
//        wait(2);
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

    /**
     * Account holders and signers
     */


}
