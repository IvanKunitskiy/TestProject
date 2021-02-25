package com.nymbus.pages.accounts;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class AccountPaymentInfoPage extends PageTools {

    /**
     * Payment history
     */

    private final By editPaymentHistoryButton = By.xpath("//button[@data-test-id='action-edit-payment-info']");
    private final By saveButton = By.xpath("//button[@data-test-id='action-save-payment-info']");

    @Step("Click 'Edit' button")
    public void clickEditPaymentHistoryButton() {
        waitForElementClickable(editPaymentHistoryButton);
        click(editPaymentHistoryButton);
    }

    @Step("Click 'Save' button")
    public void clickSaveButton() {
        waitForElementClickable(saveButton);
        click(saveButton);
    }

    @Step("Wait for 'Save' button invisibility")
    public void waitForSaveButtonInvisibility() {
        waitForElementInvisibility(saveButton);
    }

    /**
     * P&I Payments
     */

    private final By piPaymentsEffectiveDate = By.xpath("//input[@data-test-id='field-effectivedate_paymentHistory_0']");
    private final By piPaymentsInactiveDate = By.xpath("//input[@data-test-id='field-inactivedate_paymentHistory_0']");
    private final By piPaymentsPaymentType = By.xpath("//div[@data-test-id='field-paymenttype_paymentHistory_0']/a/span[2]/span");
    private final By piPaymentsFrequency = By.xpath("//div[@data-test-id='field-frequency_paymentHistory_0']/a/span[2]/span");
    private final By piPaymentsAmount = By.xpath("//input[@data-test-id='field-amount_paymentHistory_0']");
    private final By piPaymentsPercentage = By.xpath("//input[@data-test-id='field-percentage_paymentHistory_0']");
    private final By piPaymentsRecalcFuturePymt = By.xpath("//dn-switch[@id='recalcfuturepayment']/div/div/span[2]");
    private final By paymentDueRecord = By.xpath("(//div[@ui-view='paymentsDue']//tr)[2]");
    private final By amountDue = By.xpath("//input[@data-test-id='field-amountdue']");
    private final By paidStatus = By.xpath("(//div[@id='paymentduestatus']//span[contains(string(),\"Paid\")])[2]");
    private final By paymentType = By.xpath("(//div[@id='paymenttype_paymentHistory_0']//span[contains(string(),\"Interest Only (Bill)\")])[2]");
    private final By datePaymentPaidInFull = By.xpath("//input[@id='datepaid']");
    private final By dueDate = By.xpath("//input[@id='duedate']");
    private final By disInterest = By.xpath("//input[@id='interest']");
    private final By disPrincipal = By.xpath("//input[@id='principal']");
    private final By disEscrow = By.xpath("//input[@id='escrow']");
    private final By disAmount = By.xpath("//input[@id='amount']");
    private final By dueTypeTittle = By.xpath("//input[@id='paymentDueTypeTitle']");
    private final By dateDue = By.xpath("(//tr[@data-test-id='repeat-payment-0'])[3]//span//span");
    private final By dueType = By.xpath("((//tr[@data-test-id='repeat-payment-0'])[3]//span//span)[2]");
    private final By dueAmount = By.xpath("((//tr[@data-test-id='repeat-payment-0'])[3]//span//span)[3]");
    private final By dueStatus = By.xpath("((//tr[@data-test-id='repeat-payment-0'])[3]//span//span)[4]");
    private final By paymentDate = By.xpath("//tr[@data-test-id='repeat-transaction-0']//span//span");
    private final By interest = By.xpath("//tr[@data-test-id='repeat-transaction-0']//td[2]//span//span");
    private final By principal = By.xpath("//tr[@data-test-id='repeat-transaction-0']//td[3]//span//span");
    private final By escrow = By.xpath("//tr[@data-test-id='repeat-transaction-0']//td[4]//span//span");
    private final By amount = By.xpath("//tr[@data-test-id='repeat-transaction-0']//td[5]//span//span");
    private final By status = By.xpath("//tr[@data-test-id='repeat-transaction-0']//td[6]//span//span");
    private final By amountDueFromtTable = By.xpath("(//tr[@data-test-id='repeat-payment-0']//td[3]//span//span)[2]");

    @Step("Get Pi Payments Payment 'Recalc Future Pymt' value")
    public String getPiPaymentsRecalcFuturePymt() {
        return getElementText(piPaymentsRecalcFuturePymt).trim();
    }

    @Step("Get 'Pi Payments Percentage' value")
    public String getPiPaymentsPercentage() {
        return getElementAttributeValue("value", piPaymentsPercentage).replaceAll("[^0-9]", "");
    }

    @Step("Get 'Pi Payments Effective Date' value")
    public String getPiPaymentsEffectiveDate() {
        return getElementAttributeValue("value", piPaymentsEffectiveDate).trim();
    }

    @Step("Get 'Pi Payments Inactive Date' value")
    public String getPiPaymentsInactiveDate() {
        return getElementAttributeValue("value", piPaymentsInactiveDate).trim();
    }

    @Step("Get 'Pi Payments Payment Type' value")
    public String getPiPaymentsPaymentType() {
        return getElementText(piPaymentsPaymentType).trim();
    }

    @Step("Get 'Pi Payments Frequency' value")
    public String getPiPaymentsFrequency() {
        return getElementText(piPaymentsFrequency).trim();
    }

    @Step("Get 'Pi Payments Amount' value")
    public String getPiPaymentsAmount() {
        return getElementAttributeValue("value", piPaymentsAmount).replaceAll("[^0-9.]", "");
    }

    @Step("Type 'amount' value")
    public void typeAmountValue(String value) {
        type(value, piPaymentsAmount);
    }

    @Step("Type 'Percentage' value")
    public void typePercentageValue(String value) {
        type(value, piPaymentsPercentage);
    }

    /**
     * Other Payments
     */

    private final By activePaymentAmount = By.xpath("//input[@data-test-id='field-paymentamount']");
    private final By activePaymentAmountInterestOnly = By.xpath("//div[@ui-view='paymentHistory']//div[@ng-if='accountData && otherPaymentField']/div[2]");

    @Step("Get 'Active Payment Amount' value")
    public String getActivePaymentAmount() {
        return getElementAttributeValue("value", activePaymentAmount).replaceAll("[^0-9.]", "");
    }

    @Step("Get 'Active Payment Amount' interest only value")
    public String getActivePaymentAmountInterestOnly() {
        return getElementText(activePaymentAmountInterestOnly).trim();
    }

    @Step("Click payment due record")
    public void clickPaymentDueRecord() {
        waitForElementVisibility(paymentDueRecord);
        waitForElementClickable(paymentDueRecord);
        click(paymentDueRecord);
    }

    @Step("Get amount due")
    public String getAmountDue() {
        waitForElementVisibility(amountDue);
        return getDisabledElementAttributeValue("value",amountDue);
    }

    @Step("Check Paid status visibility")
    public boolean paidStatusIsVisibility(){
        return isElementVisible(paidStatus);
    }

    @Step("Check payment status visibility")
    public boolean paymentStatusIsVisibility(){
        return isElementVisible(paymentType);
    }

    @Step("Get Date payment paid in full value")
    public String getDatePaymentPaidInFull() {
        waitForElementVisibility(datePaymentPaidInFull);
        return getDisabledElementAttributeValue("value", datePaymentPaidInFull);
    }

    @Step("Get Due date value")
    public String getDueDate() {
        waitForElementVisibility(dueDate);
        return getDisabledElementAttributeValue("value", dueDate);
    }

    @Step("Get interest value")
    public String getDisabledInterest() {
        waitForElementVisibility(disInterest);
        return getDisabledElementAttributeValue("value", disInterest).replaceAll("[^0-9.]", "");
    }

    @Step("Get principal value")
    public String getDisabledPrincipal() {
        waitForElementVisibility(disPrincipal);
        return getDisabledElementAttributeValue("value", disPrincipal).replaceAll("[^0-9.]", "");
    }

    @Step("Get escrow value")
    public String getDisabledEscrow() {
        waitForElementVisibility(disEscrow);
        return getDisabledElementAttributeValue("value", disEscrow).replaceAll("[^0-9.]", "");
    }

    @Step("Get amount value")
    public String getDisabledAmount() {
        waitForElementVisibility(disAmount);
        return getDisabledElementAttributeValue("value", disAmount).replaceAll("[^0-9.]", "");
    }

    @Step("Get Due date value")
    public String getTypeDue() {
        waitForElementVisibility(dueTypeTittle);
        return getDisabledElementAttributeValue("value", dueTypeTittle);
    }

    @Step("Get Due date value")
    public String getDateDue() {
        waitForElementVisibility(dateDue);
        return getElementText(dateDue);
    }

    @Step("Get Due type value")
    public String getDueType() {
        waitForElementVisibility(dueType);
        return getElementText(dueType);
    }

    @Step("Get Due amount value")
    public String getDueAmount() {
        waitForElementVisibility(dueAmount);
        return getElementText(dueAmount);
    }

    @Step("Get Due status value")
    public String getDueStatus() {
        waitForElementVisibility(dueStatus);
        return getElementText(dueStatus);
    }

    @Step("Get payment date value")
    public String getPaymentDate(){
        waitForElementVisibility(paymentDate);
        return getElementText(paymentDate);
    }

    @Step("Get Interest value")
    public String getInterest(){
        waitForElementVisibility(interest);
        return getElementText(interest).replaceAll("[^0-9.]", "");
    }

    @Step("Get Principal value")
    public String getPrincipal(){
        waitForElementVisibility(principal);
        return getElementText(principal).replaceAll("[^0-9.]", "");
    }

    @Step("Get Escrow value")
    public String getEscrow(){
        waitForElementVisibility(escrow);
        return getElementText(escrow).replaceAll("[^0-9.]", "");
    }

    @Step("Get Amount value")
    public String getAmount(){
        waitForElementVisibility(amount);
        return getElementText(amount).replaceAll("[^0-9.]", "");
    }

    @Step("Get Amount value")
    public String getAmountDueTable(){
        waitForElementVisibility(amountDueFromtTable);
        return getElementText(amountDueFromtTable).replaceAll("[^0-9.]", "");
    }

    @Step("Get Status value")
    public String getStatus(){
        waitForElementVisibility(status);
        return getElementText(status);
    }


}
