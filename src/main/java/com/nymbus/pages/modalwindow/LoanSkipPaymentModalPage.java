package com.nymbus.pages.modalwindow;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class LoanSkipPaymentModalPage extends PageTools {

    private final By nbrOfPaymentsToSkipInput = By.xpath("//input[@data-test-id='field-numberofpaymentstoskip']");
    private final By feeAddOnPaymentToggle = By.xpath("//dn-switch[@id='feeaddonpayment']");
    private final By feeAddOnPaymentToggleValue = By.xpath("//dn-switch[@id='feeaddonpayment']/div/div/span[2]");
    private final By extendMaturity = By.xpath("//dn-switch[@id='extendmaturitydate']");
    private final By extendMaturityValue = By.xpath("//dn-switch[@id='extendmaturitydate']/div/div/span[1]");
    private final By commitTransactionButton = By.xpath("//button[text()='Commit Transaction']");

    @Step("Click tools 'Commit Transaction' toggle")
    public void clickCommitTransactionButton() {
        waitForElementClickable(commitTransactionButton);
        click(commitTransactionButton);
    }

    @Step("Click tools 'NBR of Payments to Skip' button")
    public void typeNbrOfPaymentsToSkipInput(String text) {
        waitForElementClickable(nbrOfPaymentsToSkipInput, text);
        type(text, nbrOfPaymentsToSkipInput);
    }

    @Step("Click tools 'Fee Add-on Payment' toggle")
    public void clickFeeAddOnPaymentToggle() {
        waitForElementClickable(feeAddOnPaymentToggle);
        click(feeAddOnPaymentToggle);
    }

    @Step("Returning 'Fee Add-on Payment' value")
    public String getFeeAddOnPaymentToggleValue() {
        return getElementText(feeAddOnPaymentToggleValue).trim();
    }

    @Step("Click tools 'Extend Maturity' toggle")
    public void clickExtendMaturityToggle() {
        waitForElementClickable(extendMaturity);
        click(extendMaturity);
    }

    @Step("Returning 'Extend Maturity' value")
    public String getExtendMaturityToggleValue() {
        return getElementText(extendMaturityValue).trim();
    }

    /**
     * Get values
     */
    private final By currentDueDate = By.xpath("//input[@id='nextduedate']");
    private final By maturityDate = By.xpath("//input[@id='maturitydate']");
    private final By skipsThisYear = By.xpath("//input[@id='skipsthisyear']");

    @Step("Returning 'Current Due Date' value")
    public String getCurrentDueDate() {
        return getDisabledElementAttributeValue("value", currentDueDate).trim();
    }

    @Step("Returning 'Maturity Date' value")
    public String getMaturityDate() {
        return getDisabledElementAttributeValue("value", maturityDate).trim();
    }

    @Step("Returning 'NBR Skips This Year' value")
    public String getNbrSkipsThisYear() {
        return getDisabledElementAttributeValue("value", skipsThisYear).trim();
    }

}
