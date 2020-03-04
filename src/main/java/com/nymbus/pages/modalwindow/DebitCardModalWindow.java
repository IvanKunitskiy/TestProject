package com.nymbus.pages.modalwindow;

import com.nymbus.base.BasePage;
import com.nymbus.locator.ID;
import com.nymbus.locator.Locator;
import com.nymbus.locator.XPath;
import com.nymbus.models.client.other.debitcard.CardStatus;
import io.qameta.allure.Step;

public class DebitCardModalWindow extends BasePage {
    /**
     * Select 'Bin Control' Modal Window locators
     * */
    private Locator newDebitCard = new XPath("//button[@data-test-id='action-newDebitCard']");
    private Locator binNumberArrowIcon = new XPath("//div[@id='binnumber']//b");
    private Locator binNumberDropdownValue = new XPath("//div/span[text()='%s']");
    private Locator descriptionInputField = new ID("description");
    private Locator nextButton = new XPath("//button[span[text()='Next']]");

    /**
     * Add New Debit Card locators
     * */
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
    private Locator expirationDateMonth = new XPath("//div[a[span[contains(text(),'Month')]]]");
    private Locator expirationDateYear = new XPath("//div[a[span[contains(text(),'Year')]]]");
    private Locator atmDailyLimitNumberAmtInputField = new ID("atmdailylimitdollaramt");
    private Locator atmDailyLimitNumberNbrInputField = new ID("atmdailylimitnumberoftrans");
    private Locator debitPurchaseDailyLimitNumberAmtInputField = new ID("purchasedailylimitdollaramt");
    private Locator debitPurchaseDailyLimitNumberNbrInputField = new ID("purchasedailylimitnumberoftrans");

    /**
     * Select 'Bin Control' Modal Window methods
     * */
    @Step("Click on 'New Debit Card' button")
    public void clickOnNewDebitCardButton() {
        waitForElementVisibility(newDebitCard);
        waitForElementClickable(newDebitCard);
        click(newDebitCard);
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

    @Step("Typing '{account}' to 'Account' input field")
    public void typeToAccountInputField(String account) {
        waitForElementVisibility(accountInputField, 0); // '0' it's an index of field
        type(account, accountInputField, 0);
    }

    @Step("Typing '{account}' to 'Account' input field by index '{index}'")
    public void typeToAccountInputField(String account, int index) {
        waitForElementVisibility(accountInputField, index);
        type(account, accountInputField, index);
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
    public void typeToPinOffsetInputField(String pinOffset) {
        waitForElementVisibility(pinOffsetInputField);
        waitForElementClickable(pinOffsetInputField);
        type(pinOffset, pinOffsetInputField);
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

    @Step("Typing '{atmDailyLimitNumberNbr}' to 'ATM Daily Limit Number Amt' input field")
    public void typeToDebitPurchaseDailyLimitNumberAmtInputField(String debitPurchaseDailyLimitNumberAmt) {
        waitForElementVisibility(debitPurchaseDailyLimitNumberAmtInputField);
        waitForElementClickable(debitPurchaseDailyLimitNumberAmtInputField);
        type(debitPurchaseDailyLimitNumberAmt, debitPurchaseDailyLimitNumberAmtInputField);
    }

    @Step("Typing '{atmDailyLimitNumberNbr}' to 'ATM Daily Limit Number Nbr' input field")
    public void typeToDebitPurchaseDailyLimitNumberNbrInputField(String debitPurchaseDailyLimitNumberNbr) {
        waitForElementVisibility(debitPurchaseDailyLimitNumberNbrInputField);
        waitForElementClickable(debitPurchaseDailyLimitNumberNbrInputField);
        type(debitPurchaseDailyLimitNumberNbr, debitPurchaseDailyLimitNumberNbrInputField);
    }
}
