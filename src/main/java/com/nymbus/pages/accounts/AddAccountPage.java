package com.nymbus.pages.accounts;

import com.nymbus.base.BasePage;
import com.nymbus.locator.ID;
import com.nymbus.locator.Locator;
import com.nymbus.locator.XPath;
import io.qameta.allure.Step;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

public class AddAccountPage extends BasePage {

    private Locator productTypeSelectorButton = new ID("accounttype");
    private Locator productTypeSelectorOption = new XPath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private Locator productTypeList = new XPath("//li[contains(@role, 'option')]/div/span");
    private Locator productSelectorButton = new XPath("//*[@id='accountclasstype']");
    private Locator productList = new XPath("//li[contains(@role, 'option')]/div/span");
    private Locator boxSizeSelectorButton = new ID("boxsize");
    private Locator boxSizeSelectorOption = new XPath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private Locator boxSizeList = new XPath("//li[contains(@role, 'option')]/div/span");
    private Locator boxSizeField = new XPath("//*[@id='boxsize']//input[contains(@class, 'nb-select-search')]");
    private Locator selectProductTypeField = new XPath("//*[@id='accounttype']/a/span[contains(text(), 'Select')]");
    private Locator productTypeInputField = new XPath("//div[@data-test-id='field-accounttype']//input[contains(@class, 'nb-select-search')]");
    private Locator accountNumberField = new XPath("//input[@data-test-id='field-accountnumber']");
    private Locator accountTitleField = new XPath("//input[@data-test-id='field-accounttitlemailinginstructions']");
    private Locator bankBranchSelectorButton = new XPath("//*[@id='bankbranch']");
    private Locator bankBranchSelectorOption = new XPath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private Locator saveAccountButton = new XPath("//button[@data-test-id='action-save']");
    private Locator bankBranchList = new XPath("//li[contains(@role, 'option')]/div/span");
    private Locator accountType = new XPath("//div[@data-test-id='field-customertype']/a/span/span");
    private Locator dateOpened = new XPath("//input[@data-test-id='field-dateopened']");
    private Locator productSelectorOption = new XPath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private Locator statementCycleSelectorButton = new XPath("//*[@id='statementcycle']");
    private Locator statementCycleList = new XPath("//li[contains(@role, 'option')]/div/span");
    private Locator bankBranch = new XPath("//div[@id='bankbranch']//span[contains(@class, 'ng-scope')]");
    private Locator statementFlagInput = new XPath("//input[@data-test-id='field-statementflag']");
    private Locator interestRateInput = new XPath("//input[@data-test-id='field-interestrate']");
    private Locator earningCreditRateInput = new XPath("//input[@data-test-id='field-earningscreditrate']");
    private Locator optInOutDateCalendarIcon = new XPath("//div[input[@id='dbcodpstatusdate']]/div[span[@class='nyb-icon-calendar']]");
    private Locator optInOutDateInputField = new XPath("//input[@id='dbcodpstatusdate']");
    private Locator dateOpenedField = new XPath("//input[@id='dateopened']");


    /**
     * Account holders and signers
     */

    private Locator accountHolderName = new XPath("//a[@data-test-id='action-goCustomerProfile']");
    private Locator accountHolderRelationship = new XPath("//div[@data-test-id='field-relationshiptype_0']//a//span//span");
    private Locator accountHolderClientType = new XPath("//input[@data-test-id='field-typeid_0']");
    private Locator accountHolderTaxID = new XPath("//input[@data-test-id='field-taxIdNumber']");
    private Locator statementCycleSelectorOption = new XPath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private Locator accountHolderAddress = new XPath("//div[@data-test-id='field-addressid_0']/a/span/span");

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

    @Step("Click the 'Primary Account For Combined Statement' option")
    public void clickSetPrimaryAccountForCombinedStatementSelectorOption(String primaryAccountForCombinedStatementOption) {
        waitForElementVisibility(primaryAccountForCombinedStatementSelectorOption, primaryAccountForCombinedStatementOption);
        waitForElementClickable(primaryAccountForCombinedStatementSelectorOption, primaryAccountForCombinedStatementOption);
        click(primaryAccountForCombinedStatementSelectorOption, primaryAccountForCombinedStatementOption);
    }

    @Step("Returning list of 'Primary Account For Combined Statement' options")
    public List<String> getPrimaryAccountForCombinedStatementList() {
        waitForElementVisibility(primaryAccountForCombinedStatementList);
        waitForElementClickable(primaryAccountForCombinedStatementList);
        return getElementsText(primaryAccountForCombinedStatementList);
    }

    @Step("Click the 'Primary Account For Combined Statement' selector button")
    public void clickPrimaryAccountForCombinedStatementSelectorButton() {
        waitForElementVisibility(primaryAccountForCombinedStatementSelectorButton);
        scrollToElement(primaryAccountForCombinedStatementSelectorButton);
        waitForElementClickable(primaryAccountForCombinedStatementSelectorButton);
        click(primaryAccountForCombinedStatementSelectorButton);
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
        wait(1);
        typeWithoutWipe(dateOpenedValue, dateOpenedField);
    }

    @Step("Set 'DBC ODP Opt In/Out Status Date' value")
    public void setOptInOutDateValue(String optInOutDateValue) {
        waitForElementVisibility(optInOutDateInputField);
        waitForElementClickable(optInOutDateInputField);
        typeWithoutWipe("", optInOutDateInputField);
        wait(1);
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

    @Step("Generate 'Statement Flag' value")
    public String generateStatementFlagValue() {
        String[] flags = {"R", "S"};
        Random random = new Random();
        return flags[random.nextInt(flags.length)];
    }

    @Step("Set 'Statement flag' option")
    public void setStatementFlag(String flagValue) {
        waitForElementVisibility(statementFlagInput);
        waitForElementClickable(statementFlagInput);
        type(flagValue, statementFlagInput);
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
        wait(2);
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

    // --- !
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
        wait(1);
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
    public void clickBoxSizeSelectorButton(){
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
    public void clickBoxSizeSelectorOption(String boxSizeOption){
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
