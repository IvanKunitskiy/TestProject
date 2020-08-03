package com.nymbus.pages.modalwindow;

import com.nymbus.core.base.PageTools;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.client.other.debitcard.types.CardStatus;
import com.nymbus.newmodels.client.other.debitcard.types.TranslationTypeAllowed;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class DebitCardModalWindow extends PageTools {
    /**
     * Select 'Bin Control' Modal Window Bys
     * */
    private By binNumberArrowIcon = By.xpath("//div[@id='binnumber']//b");
    private By binNumberDropdownValue = By.xpath("//div/span[text()='%s']");
    private By descriptionInputField = By.id("description");
    private By nextButton = By.xpath("//button[span[text()='Next']]");

    /**
     * Add New Debit Card Bys
     * */
    private By addNewDebitCardModalWindow = By.xpath("//form/div[@class='modal-header']");
    private By cardNumberInputField = By.id("cardnumber");
    private By clientNumberInputField = By.id("pifnumberassignedtocard");
    private By nameOnCardInputField = By.id("name");
    private By secondLineEmbossingInputField = By.id("secondlineofembossing");
    private By accountInputField = By.id("accountid_%s");
    private By addAccountButton = By.xpath("//button[contains(text(),'Add Account')]");
    private By dropdownOption = By.xpath("//div[span[contains(text(),'%s')]]");
    private By cardDesignSelect = By.id("cardesign");
    private By cardStatusSelect = By.id("status");
    private By confirmationYesButton = By.xpath("//button[span[text()='Yes']]");
    private By pinOffsetInputField = By.id("pinoffset");
    private By dateEffectiveInputField = By.id("dateeffective");
    private By expirationDateMonth = By.xpath("//a[@placeholder='Month']/span/span");
    private By expirationDateYear = By.xpath("//a[@placeholder='Year']/span/span");
    private By atmDailyLimitNumberAmtInputField = By.id("atmdailylimitdollaramt");
    private By atmDailyLimitNumberNbrInputField = By.id("atmdailylimitnumberoftrans");
    private By debitPurchaseDailyLimitNumberAmtInputField = By.id("purchasedailylimitdollaramt");
    private By debitPurchaseDailyLimitNumberNbrInputField = By.id("purchasedailylimitnumberoftrans");
    private By transactionTypeAllowedSelect = By.id("allowtransactiontypes");
    private By chargeForCardReplacementInput = By.id("chargeaccountforcardreplacement");
    private By chargeForCardReplacementToggle = By.xpath("//div[input[@id='chargeaccountforcardreplacement']]");
    private By allowForeignTransactionsInput = By.id("allowforeigntransactions");
    private By allowForeignTransactionsToggle = By.xpath("//div[input[@id='allowforeigntransactions']]");
    private By cancelButton = By.xpath("//button[text()='Cancel']");
    private By saveAndFinishButton = By.xpath("//button[span[text()='Save and Finish']]");
    private By saveButton = By.xpath("//button[span[text()='Save']]");
    private By closeDebitCardModalButton = By.xpath("//div[@class='modal-header']//button[contains(@aria-label, 'Close')]");
    private By dateEffective = By.xpath("//input[@data-test-id='field-dateeffective']");
    private By accountRows = By.xpath("//tr[@ng-repeat='accountField in debitCard.accounts track by $index']");

    /**
     * Select 'Bin Control' Modal Window methods
     * */
    @Step("Get account rows count")
    public int getAccountRowsCount() {
        waitForElementVisibility(accountRows);
        return getElements(accountRows).size();
    }

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

    @Step("Waiting for 'Add new Debit Card' modal window visibility")
    public void waitForAddNewDebitCardModalWindowVisibility() {
        waitForElementVisibility(addNewDebitCardModalWindow);
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
        click(nameOnCardInputField);
        wipeText(nameOnCardInputField);
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
        SelenideTools.sleep(1);
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
        SelenideTools.sleep(3);
        click(saveAndFinishButton);
    }

    @Step("Click on 'Save' button")
    public void clickOnSaveButton() {
        waitForElementVisibility(saveButton);
        click(saveButton);
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

    @Step("Getting value from 'Pin Offset' input field")
    public int getPinOffset() {
        waitForElementVisibility(pinOffsetInputField);
        return Integer.parseInt(getElementAttributeValue("value", pinOffsetInputField));
    }

    @Step("Getting value from 'Debit Purchase Daily Limit Number Amt' input field")
    public String getDebitPurchaseDailyLimitNumberAmt() {
        return getElementAttributeValue("value", debitPurchaseDailyLimitNumberAmtInputField).replace("$ ", "");
    }

    @Step("Getting value from 'Debit Purchase Daily Limit Number Nbr' input field")
    public String getDebitPurchaseDailyLimitNumberNbr() {
        return getElementAttributeValue("value", debitPurchaseDailyLimitNumberNbrInputField);
    }

    @Step("Click on 'Close' modal button")
    public void clickTheCloseModalIcon() {
        waitForElementVisibility(closeDebitCardModalButton);
        waitForElementClickable(closeDebitCardModalButton);
        click(closeDebitCardModalButton);
    }

    @Step("Get card number input")
    public String getCardNumber() {
        waitForElementVisibility(cardNumberInputField);
        return getWebElement(cardNumberInputField).getAttribute("value").trim();
    }

    @Step("Get 'Date Effective' input value")
    public String getDateEffective() {
        waitForElementVisibility(dateEffective);
        return getElementAttributeValue("value", dateEffective).trim();
    }
}