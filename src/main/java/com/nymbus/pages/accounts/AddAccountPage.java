package com.nymbus.pages.accounts;

import com.nymbus.base.BasePage;
import com.nymbus.base.BaseTest;
import com.nymbus.locator.ID;
import com.nymbus.locator.Locator;
import com.nymbus.locator.XPath;
import io.qameta.allure.Step;

import java.util.List;

public class AddAccountPage extends BasePage {

    private Locator productTypeSelectorButton = new ID("accounttype");
    private Locator productTypeSelectorOption = new XPath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private Locator boxSizeSelectorButton = new ID("boxsize");
    private Locator boxSizeSelectorOption = new XPath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private Locator boxSizeList = new XPath("//li[contains(@role, 'option')]/div/span");
    private Locator boxSizeField = new XPath("//*[@id='boxsize']//input[contains(@class, 'nb-select-search')]");
    private Locator productTypeField = new XPath("//div[@id='accounttype']" + "//input[contains(@class, 'nb-select-search')]");
    private Locator accountNumberField = new XPath("//input[@data-test-id='field-accountnumber']");
    private Locator accountTitleField = new XPath("//input[@data-test-id='field-accounttitlemailinginstructions']");
    private Locator bankBranchSelectorButton = new XPath("//*[@id='bankbranch']");
    private Locator bankBranchSelectorOption = new XPath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private Locator saveAccountButton = new XPath("//button[@data-test-id='action-save']");

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
        waitForElementVisibility(boxSizeSelectorOption);
        waitForElementClickable(boxSizeSelectorOption);
        return getElementsText(boxSizeSelectorOption);
    }

    @Step("Set 'Product Type' option")
    public void setProductTypeOption(String productTypeOptionValue) {
        waitForElementVisibility(productTypeField);
        waitForElementClickable(productTypeField);
        type(productTypeOptionValue, productTypeField);
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

}
