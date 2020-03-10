package com.nymbus.pages.modalwindow;

import com.nymbus.base.BasePage;
import com.nymbus.locator.ID;
import com.nymbus.locator.Locator;
import com.nymbus.locator.XPath;
import com.nymbus.models.client.other.debitcard.CardStatus;
import com.nymbus.models.client.other.debitcard.TranslationTypeAllowed;
import io.qameta.allure.Step;

public class DebitCardModalWindow extends BasePage {
    /**
     * Select 'Bin Control' Modal Window locators
     * */
    private Locator binNumberArrowIcon = new XPath("//div[@id='binnumber']//b");
    private Locator binNumberDropdownValue = new XPath("//div/span[text()='%s']");
    private Locator descriptionInputField = new ID("description");
    private Locator nextButton = new XPath("//button[span[text()='Next']]");

    /**
     * Add New Debit Card locators
     * */
    private Locator addNewDebitCardModalWindow = new XPath("//form/div[@class='modal-header']");
    private Locator cardNumberInputField = new ID("cardnumber");
    private Locator clientNumberInputField = new ID("pifnumberassignedtocard");
    private Locator nameOnCardInputField = new ID("name");
    private Locator secondLineEmbossingInputField = new ID("secondlineofembossing");
    private Locator accountInputField = new ID("accountid_%s");
    private Locator addAccountButton = new XPath("//button[contains(text(),'Add Account')]");
    private Locator dropdownOption = new XPath("//div[span[contains(text(),'%s')]]");
    private Locator cardDesignSelect = new ID("cardesign");
    private Locator cardStatusSelect = new ID("status");
    private Locator confirmationYesButton = new XPath("//button[span[text()='Yes']]");
    private Locator pinOffsetInputField = new ID("pinoffset");
    private Locator dateEffectiveInputField = new ID("dateeffective");
    private Locator expirationDateMonth = new XPath("//a[@placeholder='Month']/span/span");
    private Locator expirationDateYear = new XPath("//a[@placeholder='Year']/span/span");
    private Locator atmDailyLimitNumberAmtInputField = new ID("atmdailylimitdollaramt");
    private Locator atmDailyLimitNumberNbrInputField = new ID("atmdailylimitnumberoftrans");
    private Locator debitPurchaseDailyLimitNumberAmtInputField = new ID("purchasedailylimitdollaramt");
    private Locator debitPurchaseDailyLimitNumberNbrInputField = new ID("purchasedailylimitnumberoftrans");
    private Locator transactionTypeAllowedSelect = new ID("allowtransactiontypes");
    private Locator chargeForCardReplacementInput = new ID("chargeaccountforcardreplacement");
    private Locator chargeForCardReplacementToggle = new XPath("//div[input[@id='chargeaccountforcardreplacement']]");
    private Locator allowForeignTransactionsInput = new ID("allowforeigntransactions");
    private Locator allowForeignTransactionsToggle = new XPath("//div[input[@id='allowforeigntransactions']]");
    private Locator cancelButton = new XPath("//button[text()='Cancel']");
    private Locator saveAndFinishButton = new XPath("//button[span[text()='Save and Finish']]");

    /**
     * Select 'Bin Control' Modal Window methods
     * */
    @Step("Click on 'Bin Number' input field")
    public void clickOnBinNumberInputField() {
        waitForElementVisibility(binNumberArrowIcon);
        waitForElementClickable(binNumberArrowIcon);
        click(binNumberArrowIcon);
    }

    @Step("Click on 'Bin Number' drop down value '{binNumber}'")
    public void clickOnBinNumberDropdownValue(String binNumber) {
        waitForElementVisibility(binNumberDropdownValue, binNumber);
        waitForElementClickable(binNumberDropdownValue, binNumber);
        click(binNumberDropdownValue, binNumber);
    }

    @Step("Typing '{binNumber}' to 'Bin Number' input field")
    public void typeToBinNumberInputField(String binNumber) {
        waitForElementVisibility(binNumberArrowIcon);
        waitForElementClickable(binNumberArrowIcon);
        type(binNumber, binNumberArrowIcon);
    }

    @Step("Typing '{description}' to 'Description' input field")
    public void typeToDescriptionInputField(String description) {
        waitForElementVisibility(descriptionInputField);
        waitForElementClickable(descriptionInputField);
        type(description, descriptionInputField);
    }

    @Step("Getting value from 'Description' input field")
    public String getDescriptionInputFieldValue() {
        waitForElementVisibility(descriptionInputField);
        return getElementAttributeValue("value", descriptionInputField);
    }

    @Step("Click on 'Next' button")
    public void clickOnNextButton() {
        waitForElementVisibility(nextButton);
        waitForElementClickable(nextButton);
        click(nextButton);
    }

    /**
     * Add New Debit Card methods
     * */
    @Step("Waiting for 'Add new Debit Card' modal window invisibility")
    public void waitForAddNewDebitCardModalWindowInvisibility() {
        waitForElementInvisibility(addNewDebitCardModalWindow);
    }

    @Step("Typing '{cardNumber}' to 'Card Number' input field")
    public void typeToCardNumberInputField(String cardNumber) {
        waitForElementVisibility(cardNumberInputField);
        type(cardNumber, cardNumberInputField);
    }

    @Step("Typing '{clientNumber}' to 'Client Number' input field")
    public void typeToClientNumberInputField(String clientNumber) {
        waitForElementVisibility(clientNumberInputField);
        type(clientNumber, clientNumberInputField);
    }

    @Step("Typing '{nameOnCard}' to 'Name On Card' input field")
    public void typeToNameOnCardInputField(String nameOnCard) {
        waitForElementVisibility(nameOnCardInputField);
        type(nameOnCard, nameOnCardInputField);
    }

    @Step("Typing '{secondLineEmbossing}' to 'Second Line Embossing' input field")
    public void typeToSecondLineEmbossingInputField(String secondLineEmbossing) {
        waitForElementVisibility(secondLineEmbossingInputField);
        type(secondLineEmbossing, secondLineEmbossingInputField);
    }

    @Step("Click on to 'Account' drop down")
    public void selectAccount(String accountNumber) {
        waitForElementVisibility(accountInputField, 0); // '0' it's an index of field
        click(accountInputField, 0);
        clickOnDropdownOption(accountNumber);
    }

    @Step("Click on to 'Account' drop down by index {index}")
    public void selectAccountFromDropDownByIndex(String accountNumber, int index) {
        waitForElementVisibility(accountInputField, index);
        click(accountInputField, index);
        clickOnDropdownOption(accountNumber);
    }

    @Step("Click on 'Add Account' button")
    public void clickOnAddAccountButton() {
        waitForElementVisibility(addAccountButton);
        click(addAccountButton);
    }

    @Step("Click on '{option}' option")
    public void clickOnDropdownOption(String option) {
        waitForElementVisibility(dropdownOption, option);
        click(dropdownOption, option);
    }

    @Step("Click on 'Card Design' drop down")
    public void selectCardDesign(String cardStatus) {
        waitForElementVisibility(cardDesignSelect);
        click(cardDesignSelect);
        clickOnDropdownOption(cardStatus);
    }

    @Step("Click on 'Card Status' drop down")
    public void selectCardStatus(CardStatus cardStatus) {
        waitForElementVisibility(cardStatusSelect);
        click(cardStatusSelect);
        clickOnDropdownOption(cardStatus.getCardStatus());
    }

    @Step("Click on 'Produce Card Now(instant issue)?' yes button")
    public void clickOnYesButton() {
        waitForElementVisibility(confirmationYesButton);
        click(confirmationYesButton);
        waitForElementInvisibility(confirmationYesButton);
    }

    @Step("Typing '{pinOffset}' to 'Pin Offset' input field")
    public void typeToPinOffsetInputField(int pinOffset) {
        waitForElementVisibility(pinOffsetInputField);
        waitForElementClickable(pinOffsetInputField);
        type(String.valueOf(pinOffset), pinOffsetInputField);
    }

    @Step("Typing '{pinOffset}' to 'Date Effective' input field")
    public void typeToDateEffectiveInputField(String dateEffective) {
        waitForElementVisibility(dateEffectiveInputField);
        waitForElementClickable(dateEffectiveInputField);
        typeWithoutWipe("", dateEffectiveInputField);
        wait(1);
        typeWithoutWipe(dateEffective, dateEffectiveInputField);
    }

    @Step("Click on 'Expiration Date Month' drop down")
    public void selectExpirationDateMonth(String month) {
        waitForElementVisibility(expirationDateMonth);
        click(expirationDateMonth);
        clickOnDropdownOption(month);
    }

    @Step("Click on 'Expiration Date Year' drop down")
    public void selectExpirationDateYear(String year) {
        waitForElementVisibility(expirationDateYear);
        click(expirationDateYear);
        clickOnDropdownOption(year);
    }

    @Step("Typing '{atmDailyLimitNumberAmt}' to 'ATM Daily Limit Number Amt' input field")
    public void typeToATMDailyLimitNumberAmtInputField(String atmDailyLimitNumberAmt) {
        waitForElementVisibility(atmDailyLimitNumberAmtInputField);
        waitForElementClickable(atmDailyLimitNumberAmtInputField);
        type(atmDailyLimitNumberAmt, atmDailyLimitNumberAmtInputField);
    }

    @Step("Typing '{atmDailyLimitNumberNbr}' to 'ATM Daily Limit Number Nbr' input field")
    public void typeToATMDailyLimitNumberNbrInputField(String atmDailyLimitNumberNbr) {
        waitForElementVisibility(atmDailyLimitNumberNbrInputField);
        waitForElementClickable(atmDailyLimitNumberNbrInputField);
        type(atmDailyLimitNumberNbr, atmDailyLimitNumberNbrInputField);
    }

    @Step("Typing '{debitPurchaseDailyLimitNumberAmt}' to 'Debit Purchase Daily Limit Number Amt' input field")
    public void typeToDebitPurchaseDailyLimitNumberAmtInputField(String debitPurchaseDailyLimitNumberAmt) {
        waitForElementVisibility(debitPurchaseDailyLimitNumberAmtInputField);
        waitForElementClickable(debitPurchaseDailyLimitNumberAmtInputField);
        type(debitPurchaseDailyLimitNumberAmt, debitPurchaseDailyLimitNumberAmtInputField);
    }

    @Step("Typing '{debitPurchaseDailyLimitNumberNbr}' to 'DebitPurchaseDailyLimitNumberNbr' input field")
    public void typeToDebitPurchaseDailyLimitNumberNbrInputField(String debitPurchaseDailyLimitNumberNbr) {
        waitForElementVisibility(debitPurchaseDailyLimitNumberNbrInputField);
        waitForElementClickable(debitPurchaseDailyLimitNumberNbrInputField);
        type(debitPurchaseDailyLimitNumberNbr, debitPurchaseDailyLimitNumberNbrInputField);
    }

    @Step("Click on 'Transaction Type Allowed' drop down")
    public void selectTransactionTypeAllowedSelect(TranslationTypeAllowed translationTypeAllowed) {
        waitForElementVisibility(transactionTypeAllowedSelect);
        click(transactionTypeAllowedSelect);
        clickOnDropdownOption(translationTypeAllowed.getTranslationTypeAllowed());
    }

    @Step("Setting state '{state}' for 'Charge for Card Replacement' toggle")
    public void setChargeForCardReplacementToggle(boolean state) {
        waitForElementVisibility(chargeForCardReplacementToggle);
        boolean currentState = Boolean.parseBoolean(getElementAttributeValue("checked", chargeForCardReplacementInput));
        if (state != currentState)
            actionClick(chargeForCardReplacementToggle);
    }

    @Step("Setting state '{state}' for 'Allow Foreign Transactions' toggle")
    public void setAllowForeignTransactionsToggle(boolean state) {
        waitForElementVisibility(allowForeignTransactionsToggle);
        boolean currentState = Boolean.parseBoolean(getElementAttributeValue("checked", allowForeignTransactionsInput));
        if (state != currentState)
            actionClick(allowForeignTransactionsToggle);
    }

    @Step("Click on 'Cancel' button")
    public void clickOnCancelButton() {
        waitForElementVisibility(cancelButton);
        click(cancelButton);
    }

    @Step("Click on 'Save and Finish' button")
    public void clickOnSaveAndFinishButton() {
        waitForElementVisibility(saveAndFinishButton);
        click(saveAndFinishButton);
    }

    @Step("Getting value from 'Expiration Date Month' drop down")
    public String getExpirationDateMonth() {
        waitForElementVisibility(expirationDateMonth);
        return getElementText(expirationDateMonth);
    }

    @Step("Getting value from 'Expiration Date Year' drop down")
    public String getExpirationDateYear() {
        waitForElementVisibility(expirationDateYear);
        return getElementText(expirationDateYear);
    }

    @Step("Getting value from 'ATM Daily Limit Number Amt' input field")
    public String getATMDailyLimitNumberAmt() {
        return getElementAttributeValue("value", atmDailyLimitNumberAmtInputField).replace("$ ", "");
    }

    @Step("Getting value from 'ATM Daily Limit Number Nbr' input field")
    public String getATMDailyLimitNumberNbr() {
        return getElementAttributeValue("value", atmDailyLimitNumberNbrInputField);
    }

    @Step("Getting value from 'Debit Purchase Daily Limit Number Amt' input field")
    public String getDebitPurchaseDailyLimitNumberAmt() {
        return getElementAttributeValue("value", debitPurchaseDailyLimitNumberAmtInputField).replace("$ ", "");
    }

    @Step("Getting value from 'Debit Purchase Daily Limit Number Nbr' input field")
    public String getDebitPurchaseDailyLimitNumberNbr() {
        return getElementAttributeValue("value", debitPurchaseDailyLimitNumberNbrInputField);
    }
}
