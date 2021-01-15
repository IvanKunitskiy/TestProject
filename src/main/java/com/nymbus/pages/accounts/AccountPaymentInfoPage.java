package com.nymbus.pages.accounts;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class AccountPaymentInfoPage extends PageTools {

    /**
     * P&I Payments
     */

    private final By piPaymentsEffectiveDate = By.xpath("//input[@data-test-id='field-effectivedate_paymentHistory_0']");
    private final By piPaymentsInactiveDate = By.xpath("//input[@data-test-id='field-inactivedate_paymentHistory_0']");
    private final By piPaymentsPaymentType = By.xpath("//div[@data-test-id='field-paymenttype_paymentHistory_0']/a/span[2]/span");
    private final By piPaymentsFrequency = By.xpath("//div[@data-test-id='field-frequency_paymentHistory_0']/a/span[2]/span");
    private final By piPaymentsAmount = By.xpath("//input[@data-test-id='field-amount_paymentHistory_0']");

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
