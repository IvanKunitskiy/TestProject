package com.nymbus.pages.accounts;

import com.nymbus.base.BasePage;
import com.nymbus.locator.ID;
import com.nymbus.locator.Locator;
import com.nymbus.locator.XPath;
import io.qameta.allure.Step;

import java.util.List;

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
    private Locator dateOpened = new XPath("//*[@data-test-id='field-dateopened']");
    private Locator productSelectorOption = new XPath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private Locator statementCycleSelectorButton = new XPath("//*[@id='statementcycle']");
    private Locator statementCycleList = new XPath("//li[contains(@role, 'option')]/div/span");

    /**
     * Account holders and signers
     */

    private Locator accountHolderName = new XPath("//a[@data-test-id='action-goCustomerProfile']");
    private Locator accountHolderRelationship = new XPath("//div[@data-test-id='field-relationshiptype_0']//a//span//span");
    private Locator accountHolderClientType = new XPath("//input[@data-test-id='field-typeid_0']");
    private Locator accountHolderTaxID = new XPath("//input[@data-test-id='field-taxIdNumber']");
    private Locator statementCycleSelectorOption = new XPath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    /**
     * Originating officer
     */

    private Locator originatingOfficer = new XPath("//div[@data-test-id='field-originatingOfficer']/a/span/span");
    private Locator currentOfficer = new XPath("//div[@data-test-id='field-officer']/a/span/span");
    private Locator optInOutStatus = new XPath("//div[@data-test-id='field-dbcodpstatus']/a/span/span");

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
        return getElementText(dateOpened);
    }

    @Step("Returning the 'Account Holder Tax ID' value")
    public String getAccountHolderTaxID() {
        waitForElementVisibility(accountHolderTaxID);
        return getElementText(accountHolderTaxID);
    }

    @Step("Returning the 'Account Holder Client type' value")
    public String getAccountHolderClientType() {
        waitForElementVisibility(accountHolderClientType);
        return getElementText(accountHolderClientType);
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
