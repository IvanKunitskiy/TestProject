package com.nymbus.pages.modalwindow;

import com.nymbus.core.base.PageTools;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class QuotePayoffModalPage extends PageTools {

    private By selectPayoffDate = By.xpath("//dn-datepicker[@name='payoffDate']//input");

    @Step("Get value from 'Select Payoff Date' field")
    public String getSelectPayoffDateValue() {
        waitForElementVisibility(selectPayoffDate);
        return getElementAttributeValue("value", selectPayoffDate);
    }

    @Step("Type value to 'Select Payoff Date' field")
    public void typeToSelectPayoffDateValue(String date) {
        waitForElementClickable(selectPayoffDate);
        type(date, selectPayoffDate);
        clickEnterButton();
        SelenideTools.sleep(Constants.MINI_TIMEOUT);
    }

    /**
     * Quote Payoff Table
     */

    private By dateByRowIndex = By.xpath("//tr[%s]//dn-field-view[@data-test-id='repeat-viewConfig-0']/div/div");
    private By payoffByRowIndex = By.xpath("//tr[%s]//dn-field-view[@data-test-id='repeat-viewConfig-1']/div/div");
    private By balanceByRowIndex = By.xpath("//tr[%s]//dn-field-view[@data-test-id='repeat-viewConfig-2']/div/div");
    private By interestByRowIndex = By.xpath("//tr[%s]//dn-field-view[@data-test-id='repeat-viewConfig-3']/div/div");
    private By lateChargeByRowIndex = By.xpath("//tr[%s]//dn-field-view[@data-test-id='repeat-viewConfig-4']/div/div");
    private By escrowByRowIndex = By.xpath("//tr[%s]//dn-field-view[@data-test-id='repeat-viewConfig-5']/div/div");
    private By insuranceByRowIndex = By.xpath("//tr[%s]//dn-field-view[@data-test-id='repeat-viewConfig-6']/div/div");
    private By skipFeesByRowIndex = By.xpath("//tr[%s]//dn-field-view[@data-test-id='repeat-viewConfig-7']/div/div");
    private By otherChargesByRowIndex = By.xpath("//tr[%s]//dn-field-view[@data-test-id='repeat-viewConfig-8']/div/div");

    @Step("Get 'Date' by index: {index}")
    public String getDateByRowIndex(int index) {
        return getElementText(dateByRowIndex, index).trim();
    }

    @Step("Get 'Payoff' by index: {index}")
    public String getPayoffByRowIndex(int index) {
        return getElementText(payoffByRowIndex, index).replaceAll("[^0-9.]", "");
    }

    @Step("Get 'Balance' by index: {index}")
    public String getBalanceByRowIndex(int index) {
        return getElementText(balanceByRowIndex, index).replaceAll("[^0-9.]", "");
    }

    @Step("Get 'Interest' by index: {index}")
    public String getInterestByRowIndex(int index) {
        return getElementText(interestByRowIndex, index).replaceAll("[^0-9.]", "");
    }

    @Step("Get 'Late Charge' by index: {index}")
    public String getLateChargeByRowIndex(int index) {
        return getElementText(lateChargeByRowIndex, index).replaceAll("[^0-9.]", "");
    }

    @Step("Get 'Escrow' by index: {index}")
    public String getEscrowByRowIndex(int index) {
        return getElementText(escrowByRowIndex, index).replaceAll("[^0-9.]", "");
    }

    @Step("Get 'Insurance' by index: {index}")
    public String getInsuranceByRowIndex(int index) {
        return getElementText(insuranceByRowIndex, index).replaceAll("[^0-9.]", "");
    }

    @Step("Get 'Skip Fees' by index: {index}")
    public String getSkipFeesByRowIndex(int index) {
        return getElementText(skipFeesByRowIndex, index).replaceAll("[^0-9.]", "");
    }

    @Step("Get 'Other Charges' by index: {index}")
    public String getOtherChargesByRowIndex(int index) {
        return getElementText(otherChargesByRowIndex, index).replaceAll("[^0-9.]", "");
    }

    /**
     * Account Details section
     */

    private By payoffDate = By.xpath("//span[text()='Payoff Date']/../following-sibling::div/span");
    private By clientName = By.xpath("//span[text()='Client Name']/../following-sibling::div/span");
    private By accountNumber = By.xpath("//span[text()='Account #']/../following-sibling::div/span");
    private By balance = By.xpath("//span[text()='Balance']/../following-sibling::div/span");
    private By payoff = By.xpath("//span[text()='Payoff']/../following-sibling::div/span");
    private By interestRate = By.xpath("//span[text()='Interest Rate']/../following-sibling::div/span");
    private By interestBase = By.xpath("//span[text()='Interest Base']/../following-sibling::div/span");
    private By interestPaidToDate = By.xpath("//span[text()='Interest Paid to Date']/../following-sibling::div/span");
    private By otherCharges = By.xpath("//span[text()='Other Charges']/../following-sibling::div/span");
    private By payoffCharges = By.xpath("//span[text()='Payoff Charges']/../following-sibling::div/span");

    @Step("Get 'Payoff Date' value")
    public String getPayoffDate() {
        return getElementText(payoffDate).trim();
    }

    @Step("Get 'Client Name' value")
    public String getClientName() {
        return getElementText(clientName).trim();
    }

    @Step("Get 'Account Number' value")
    public String getAccountNumber() {
        return getElementText(accountNumber).trim();
    }

    @Step("Get 'Balance' value")
    public String getBalance() {
        return getElementText(balance).replaceAll("[^0-9.]", "");
    }

    @Step("Get 'Payoff' value")
    public String getPayoff() {
        return getElementText(payoff).replaceAll("[^0-9.]", "");
    }

    @Step("Get 'Interest Rate' value")
    public String getInterestRate() {
        return getElementText(interestRate).replaceAll("[^0-9.]", "");
    }

    @Step("Get 'Interest Base' value")
    public String getInterestBase() {
        return getElementText(interestBase).replaceAll("[^0-9.]", "");
    }

    @Step("Get 'Interest Paid to Date' value")
    public String getInterestPaidToDate() {
        return getElementText(interestPaidToDate).replaceAll("[^0-9.]", "");
    }

    @Step("Get 'Other Charges' value")
    public String getOtherCharges() {
        return getElementText(otherCharges).replaceAll("[^0-9.]", "");
    }

    @Step("Get 'Payoff Charges' value")
    public String getPayoffCharges() {
        return getElementText(payoffCharges).replaceAll("[^0-9.]", "");
    }

    /**
     * Print Payoff Letter section
     */

    private By date = By.xpath("//dn-datepicker[@name='printDate']//input[@data-test-id='field-']");
    private By name = By.xpath("//input[preceding-sibling::label[text()='Name']]");
    private By address = By.xpath("//div[@data-test-id='field-addressSelect']");

    @Step("Is 'Date' field disabled")
    public boolean isDateFieldDisabled() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", date));
    }

    @Step("Is 'Name' field disabled")
    public boolean isNameFieldDisabled() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", name));
    }

    @Step("Is 'Address' field disabled")
    public boolean isAddressFieldDisabled() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", address));
    }

    @Step("Get 'Date' value")
    public String getDate() {
        return getElementText(date).trim();
    }

    @Step("Get 'Name' value")
    public String getName() {
        return getElementText(name).trim();
    }
}
