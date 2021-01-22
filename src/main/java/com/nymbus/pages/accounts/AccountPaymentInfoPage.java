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
}
