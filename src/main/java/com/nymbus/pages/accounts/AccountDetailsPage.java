package com.nymbus.pages.accounts;

import com.nymbus.base.BasePage;
import com.nymbus.locator.Locator;
import com.nymbus.locator.XPath;
import io.qameta.allure.Step;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

public class AccountDetailsPage extends BasePage {

    /**
     * Tabs button
     */
    private Locator maintenanceTab = new XPath("//a[contains(text(), 'Maintenance')]");

    /**
     * Account actions
     */
    private Locator editButton = new XPath("//button[@data-test-id='action-editAccount']");

    /**
     * Details tab
     */
    private Locator moreButton = new XPath("//button[@data-test-id='action-showMoreFields']");
    private Locator lessButton = new XPath("//button[@data-test-id='action-hideLessFields']");
    private Locator fullProfileButton = new XPath("//button[@data-test-id='go-fullProfile']");
    private Locator productType = new XPath("//tr[@data-config-name='accounttype']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator boxSize = new XPath("//tr[@data-config-name='boxsize']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator accountNumber = new XPath("//tr[@data-config-name='accountnumber']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator accountTitle = new XPath("//tr[@data-config-name='accounttitlemailinginstructions']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator bankBranch = new XPath("//tr[@data-config-name='bankbranch']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator product = new XPath("//tr[@data-config-name='accountclasstype']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator dateOpened = new XPath("//tr[@data-config-name='dateopened']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator currentOfficer = new XPath("//tr[@data-config-name='officer']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator statementCycle = new XPath("//tr[@data-config-name='statementcycle']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator callClass = new XPath("//tr[@data-config-name='callclasscode']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator chargeOrAnalyze = new XPath("//tr[@data-config-name='chargeoranalyze']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator accountAnalysis = new XPath("//tr[@data-config-name='accountanalysis']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator statementFlag = new XPath("//tr[@data-config-name='statementflag']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator interestRate = new XPath("//tr[@data-config-name='interestrate']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator earningCreditRate = new XPath("//tr[@data-config-name='earningscreditrate']//span[contains(@class, 'dnTextFixedWidthText')]");

    private Locator federalWHReason = new XPath("//tr[@data-config-name='federalwithholdingreason']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator reasonDebitCardChargeWaived = new XPath("//tr[@data-config-name='reasondebitcardchargeswaived']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator reasonAutoNSFChgWaived = new XPath("//tr[@data-config-name='reasonautonsfchargeswaived']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator odProtectionAcct = new XPath("//tr[@data-config-name='overdraftprotectionaccountnumber']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator reasonATMChargeWaived = new XPath("//tr[@data-config-name='reasonatmchargeswaived']//span[contains(@class, 'dnTextFixedWidthText')]");

    /**
     * Edit Account
     */

    private Locator editAccountTitle = new XPath("//input[@id='accounttitlemailinginstructions']");
    private Locator editBankBranch = new XPath("//div[@id='bankbranch']//span[contains(@class, 'ng-scope')]");
    private Locator editProduct = new XPath("//div[@id='accountclasstype']//span[contains(@class, 'ng-scope')]");
    private Locator editDateOpened = new XPath("//input[@id='dateopened']");
    private Locator editCurrentOfficer = new XPath("//div[@id='officer']//span[contains(@class, 'ng-scope')]");
    private Locator editStatementCycle = new XPath("//div[@id='statementcycle']//span[contains(@class, 'ng-scope')]");
    private Locator editCallClassCode = new XPath("//div[@id='callclasscode']//span[contains(@class, 'ng-scope')]");
    private Locator editChargeOrAnalyze = new XPath("//div[@id='chargeoranalyze']//span[contains(@class, 'ng-scope')]");
    private Locator editAccountAnalysis = new XPath("//div[@id='accountanalysis']//span[contains(@class, 'ng-scope')]");
    private Locator editStatementFlag = new XPath("//input[@id='statementflag']");
    private Locator editInterestRate = new XPath("//input[@id='interestrate']");
    private Locator editEarningCreditRate = new XPath("//input[@id='earningscreditrate']");

    private Locator federalWHReasonSelectorButton = new XPath("//div[@id='federalwithholdingreason']");
    private Locator federalWHReasonList = new XPath("//li[contains(@role, 'option')]/div/span");
    private Locator federalWHReasonSelectorOption = new XPath("//div[@id='federalwithholdingreason']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private Locator reasonATMChargeWaivedSelectorButton = new XPath("//div[@id='reasonatmchargeswaived']");
    private Locator reasonATMChargeWaivedList = new XPath("//li[contains(@role, 'option')]/div/span");
    private Locator reasonATMChargeWaivedSelectorOption = new XPath("//div[@id='reasonatmchargeswaived']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private Locator odProtectionAcctSelectorButton = new XPath("//div[@id='overdraftprotectionaccountnumber']");
    private Locator odProtectionAcctList = new XPath("//li[contains(@role, 'option')]/div/span");
    private Locator odProtectionAcctSelectorOption = new XPath("//div[@id='overdraftprotectionaccountnumber']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private Locator reasonAutoNSFChgWaivedSelectorButton = new XPath("//div[@id='reasonautonsfchargeswaived']");
    private Locator reasonAutoNSFChgWaivedList = new XPath("//li[contains(@role, 'option')]/div/span");
    private Locator reasonAutoNSFChgWaivedSelectorOption = new XPath("//div[@id='reasonautonsfchargeswaived']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private Locator reasonReasonDebitCardChargeWaivedSelectorButton = new XPath("//div[@id='reasondebitcardchargeswaived']");
    private Locator reasonReasonDebitCardChargeWaivedList = new XPath("//li[contains(@role, 'option')]/div/span");
    private Locator reasonReasonDebitCardChargeWaivedSelectorOption = new XPath("//div[@id='reasondebitcardchargeswaived']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private Locator automaticOverdraftStatusSelectorButton = new XPath("//div[@id='automaticoverdraftstatus']");
    private Locator automaticOverdraftStatusList = new XPath("//li[contains(@role, 'option')]/div/span");
    private Locator automaticOverdraftStatusSelectorOption = new XPath("//div[@id='automaticoverdraftstatus']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private Locator reasonAutoOdChgWaivedSelectorButton = new XPath("//div[@id='reasonautoodchargeswaived']");
    private Locator reasonAutoOdChgWaivedList = new XPath("//li[contains(@role, 'option')]/div/span");
    private Locator reasonAutoOdChgWaivedSelectorOption = new XPath("//div[@id='reasonautoodchargeswaived']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private Locator whenSurchargesRefundedSelectorButton = new XPath("//div[@id='whensurchargesrefunded']");
    private Locator whenSurchargesRefundedList = new XPath("//li[contains(@role, 'option')]/div/span");
    private Locator whenSurchargesRefundedSelectorOption = new XPath("//div[@id='whensurchargesrefunded']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private Locator federalWHPercentInput = new XPath("//input[@id='federalwithholdingpercent']");
    private Locator numberOfATMCardsIssuedInput = new XPath("//input[@id='numberofatmcardissued']");
    private Locator userDefinedFieldInput_1 = new XPath("//input[@id='userdefinedfield1']");
    private Locator userDefinedFieldInput_2 = new XPath("//input[@id='userdefinedfield2']");
    private Locator userDefinedFieldInput_3 = new XPath("//input[@id='userdefinedfield3']");
    private Locator userDefinedFieldInput_4 = new XPath("//input[@id='userdefinedfield4']");
    private Locator imageStatementCodeInput = new XPath("//input[@id='imagestatementcode']");
    private Locator numberOfDebitCardsIssuedInput = new XPath("//input[@id='numberofdebitcardsissued']");
    private Locator cashCollDaysBeforeChgInput = new XPath("//input[@id='cashcollectionnumberofdaysbeforeinterestcharge']");
    private Locator cashCollInterestRateInput = new XPath("//input[@id='cashcollectioninterestrate']");
    private Locator cashCollInterestChgInput = new XPath("//input[@id='cashcollectioninterestchargesperstatementcycle']");
    private Locator positivePayInput = new XPath("//input[@id='positivepaycustomer']");
    private Locator cashColFloatInput = new XPath("//input[@id='cashcollectionfloat']");
    private Locator earningCreditRateInput = new XPath("//input[@id='earningscreditrate']");
    private Locator interestRateInput = new XPath("//input[@id='interestrate']");

    /**
     * Disabled fields in edit mode
     */
    private Locator productTypeField = new XPath("//div[@id='accounttype']");
    private Locator productField = new XPath("//div[@id='accountclasstype']");
    private Locator accountNumberField = new XPath("//input[@id='accountnumber']");
    private Locator accountTypeField = new XPath("//div[@id='customertype']");
    private Locator originatingOfficerField = new XPath("//div[@id='originatingofficer']");
    private Locator accountStatusField = new XPath("//input[@id='accountstatus']");
    private Locator dateOpenedField = new XPath("//input[@id='dateopened']");
    private Locator dateClosedField = new XPath("//input[@id='dateclosed']");
    private Locator annualPercentageYieldField = new XPath("//input[@id='apy']");
    private Locator daysOverdraftField = new XPath("//input[@id='daysoverdraftregcc']");
    private Locator daysOverdraftAboveLimitField = new XPath("//input[@id='daysoverdraftabovelimitregcc']");
    private Locator lastDebitAmountField = new XPath("//input[@id='lastwithdrawalamount']");
    private Locator automaticOverdraftLimitField = new XPath("//input[@id='automaticoverdraftlimit']");
    private Locator totalEarningsField = new XPath("//input[@id='totalEarnings']");

    /**
     * Maintenance tab
     */
    private Locator viewAllMaintenanceHistoryLink = new XPath("//button//span[contains(text(), 'View All History')]");
    private Locator viewMoreButton = new XPath("//button[@data-test-id='action-loadMore']");

    /**
     * Edit Account
     */

    @Step("Set 'Interest rate' option")
    public void setInterestRate(String interestRateValue) {
        waitForElementVisibility(interestRateInput);
        waitForElementClickable(interestRateInput);
        type(interestRateValue, interestRateInput);
    }

    @Step("Set 'Cash coll float' value")
    public void setCashCollFloat(String cashCollFloatValue) {
        waitForElementClickable(cashColFloatInput);
        type(cashCollFloatValue, cashColFloatInput);
    }

    @Step("Set 'Positive Pay' value")
    public void setPositivePay(String positivePayValue) {
        waitForElementVisibility(positivePayInput);
        waitForElementClickable(positivePayInput);
        type(positivePayValue, positivePayInput);
    }

    @Step("Set 'Cash Coll interest chg' value")
    public void setCashCollInterestChg(String cashCollInterestChgValue) {
        waitForElementVisibility(cashCollInterestChgInput);
        waitForElementClickable(cashCollInterestChgInput);
        type(cashCollInterestChgValue, cashCollInterestChgInput);
    }

    @Step("Set 'Cash Coll interest rate' value")
    public void setCashCollInterestRate(String cashCollInterestRateValue) {
        waitForElementVisibility(cashCollInterestRateInput);
        waitForElementClickable(cashCollInterestRateInput);
        type(cashCollInterestRateValue, cashCollInterestRateInput);
    }

    @Step("Set 'Cash Coll # Days Before Chg' value")
    public void setCashCollDaysBeforeChg(String cashCollDaysBeforeChgValue) {
        waitForElementVisibility(cashCollDaysBeforeChgInput);
        waitForElementClickable(cashCollDaysBeforeChgInput);
        type(cashCollDaysBeforeChgValue, cashCollDaysBeforeChgInput);
    }

    @Step("Set 'Number of Debit Cards issued' value")
    public void setNumberOfDebitCardsIssued(String numberOfDebitCardsIssuedValue) {
        waitForElementVisibility(numberOfDebitCardsIssuedInput);
        waitForElementClickable(numberOfDebitCardsIssuedInput);
        type(numberOfDebitCardsIssuedValue, numberOfDebitCardsIssuedInput);
    }

    @Step("Set 'Image Statement Code' value")
    public void setImageStatementCode(String imageStatementCodeValue) {
        waitForElementVisibility(imageStatementCodeInput);
        waitForElementClickable(imageStatementCodeInput);
        type(imageStatementCodeValue, imageStatementCodeInput);
    }

    @Step("Set 'User Defined Field 4' value")
    public void setUserDefinedField_4(String value) {
        waitForElementVisibility(userDefinedFieldInput_4);
        waitForElementClickable(userDefinedFieldInput_4);
        type(value, userDefinedFieldInput_4);
    }

    @Step("Set 'User Defined Field 3' value")
    public void setUserDefinedField_3(String value) {
        waitForElementVisibility(userDefinedFieldInput_3);
        waitForElementClickable(userDefinedFieldInput_3);
        type(value, userDefinedFieldInput_3);
    }

    @Step("Set 'User Defined Field 2' value")
    public void setUserDefinedField_2(String value) {
        waitForElementVisibility(userDefinedFieldInput_2);
        waitForElementClickable(userDefinedFieldInput_2);
        type(value, userDefinedFieldInput_2);
    }

    @Step("Set 'User Defined Field 1' value")
    public void setUserDefinedField_1(String value) {
        waitForElementVisibility(userDefinedFieldInput_1);
        waitForElementClickable(userDefinedFieldInput_1);
        type(value, userDefinedFieldInput_1);
    }

    @Step("Generate 'Earning Credit Rate' value")
    public String generateEarningCreditRateValue() {
        Random ran = new Random();
        DecimalFormat df = new DecimalFormat("##.####");
        return String.valueOf(df.format(ran.nextFloat() * 100));
    }

    @Step("Set 'Earning Credit Rate' value")
    public void setEarningCreditRate(String earningCreditRateValue) {
        waitForElementVisibility(earningCreditRateInput);
        waitForElementClickable(earningCreditRateInput);
        type(earningCreditRateValue, earningCreditRateInput);
    }

    @Step("Set 'Federal W/H percent' value")
    public void setNumberOfATMCardsIssued(String numberOfATMCardsIssuedValue) {
        waitForElementVisibility(numberOfATMCardsIssuedInput);
        waitForElementClickable(numberOfATMCardsIssuedInput);
        type(numberOfATMCardsIssuedValue, numberOfATMCardsIssuedInput);
    }

    @Step("Generate 'Federal W/H percent' value")
    public String generateFederalWHPercentValue() {
        Random ran = new Random();
        DecimalFormat df = new DecimalFormat("##.####");
        return String.valueOf(df.format(ran.nextFloat() * 100));
    }

    @Step("Set 'Federal W/H percent' value")
    public void setFederalWHPercent(String federalWHPercentValue) {
        waitForElementVisibility(federalWHPercentInput);
        waitForElementClickable(federalWHPercentInput);
        type(federalWHPercentValue, federalWHPercentInput);
    }

    @Step("Click on 'When surcharges refunded' value")
    public void clickWhenSurchargesRefundedSelectorOption(String whenSurchargesRefunded) {
        waitForElementVisibility(whenSurchargesRefundedSelectorOption, whenSurchargesRefunded);
        waitForElementClickable(whenSurchargesRefundedSelectorOption, whenSurchargesRefunded);
        click(whenSurchargesRefundedSelectorOption, whenSurchargesRefunded);
    }

    @Step("Returning list of 'When surcharges refunded'")
    public List<String> getWhenSurchargesRefundedList() {
        waitForElementVisibility(whenSurchargesRefundedList);
        waitForElementClickable(whenSurchargesRefundedList);
        return getElementsText(whenSurchargesRefundedList);
    }

    @Step("Click the 'When surcharges refunded' selector button")
    public void clickWhenSurchargesRefundedSelectorButton() {
        waitForElementVisibility(whenSurchargesRefundedSelectorButton);
        scrollToElement(whenSurchargesRefundedSelectorButton);
        waitForElementClickable(whenSurchargesRefundedSelectorButton);
        click(whenSurchargesRefundedSelectorButton);
    }

    @Step("Click on 'Reason Auto Od Chg Waived' option")
    public void clickReasonAutoOdChgWaivedSelectorOption(String reasonAutoOdChgWaivedOption) {
        waitForElementVisibility(reasonAutoOdChgWaivedSelectorOption, reasonAutoOdChgWaivedOption);
        waitForElementClickable(reasonAutoOdChgWaivedSelectorOption, reasonAutoOdChgWaivedOption);
        click(reasonAutoOdChgWaivedSelectorOption, reasonAutoOdChgWaivedOption);
    }

    @Step("Returning list of 'Reason Auto Od Chg Waived'")
    public List<String> getReasonAutoOdChgWaivedList() {
        waitForElementVisibility(reasonAutoOdChgWaivedList);
        waitForElementClickable(reasonAutoOdChgWaivedList);
        return getElementsText(reasonAutoOdChgWaivedList);
    }

    @Step("Click the 'Reason Auto Od Chg Waived' selector button")
    public void clickReasonAutoOdChgWaivedSelectorButton() {
        waitForElementVisibility(reasonAutoOdChgWaivedSelectorButton);
        scrollToElement(reasonAutoOdChgWaivedSelectorButton);
        waitForElementClickable(reasonAutoOdChgWaivedSelectorButton);
        click(reasonAutoOdChgWaivedSelectorButton);
    }

    @Step("Click on 'Automatic Overdraft Status' option")
    public void clickAutomaticOverdraftStatusSelectorOption(String automaticOverdraftStatusOption) {
        waitForElementVisibility(automaticOverdraftStatusSelectorOption, automaticOverdraftStatusOption);
        waitForElementClickable(automaticOverdraftStatusSelectorOption, automaticOverdraftStatusOption);
        click(automaticOverdraftStatusSelectorOption, automaticOverdraftStatusOption);
    }

    @Step("Returning list of 'Automatic Overdraft Status'")
    public List<String> getAutomaticOverdraftStatusList() {
        waitForElementVisibility(automaticOverdraftStatusList);
        waitForElementClickable(automaticOverdraftStatusList);
        return getElementsText(automaticOverdraftStatusList);
    }

    @Step("Click the 'Automatic Overdraft Status' selector button")
    public void clickAutomaticOverdraftStatusSelectorButton() {
        waitForElementVisibility(automaticOverdraftStatusSelectorButton);
        scrollToElement(automaticOverdraftStatusSelectorButton);
        waitForElementClickable(automaticOverdraftStatusSelectorButton);
        click(automaticOverdraftStatusSelectorButton);
    }

    @Step("Click on 'Reason Debit Card Charge Waived' option")
    public void clickReasonDebitCardChargeWaivedSelectorOption(String reasonDebitCardChargeWaivedOption) {
        waitForElementVisibility(reasonReasonDebitCardChargeWaivedSelectorOption, reasonDebitCardChargeWaivedOption);
        waitForElementClickable(reasonReasonDebitCardChargeWaivedSelectorOption, reasonDebitCardChargeWaivedOption);
        click(reasonReasonDebitCardChargeWaivedSelectorOption, reasonDebitCardChargeWaivedOption);
    }

    @Step("Returning list of 'Reason Debit Card Charge Waived'")
    public List<String> getReasonDebitCardChargeWaivedList() {
        waitForElementVisibility(reasonReasonDebitCardChargeWaivedList);
        waitForElementClickable(reasonReasonDebitCardChargeWaivedList);
        return getElementsText(reasonReasonDebitCardChargeWaivedList);
    }

    @Step("Click the 'Reason Debit Card Charge Waived' selector button")
    public void clickReasonDebitCardChargeWaivedOptionSelectorButton() {
        waitForElementVisibility(reasonReasonDebitCardChargeWaivedSelectorButton);
        scrollToElement(reasonReasonDebitCardChargeWaivedSelectorButton);
        waitForElementClickable(reasonReasonDebitCardChargeWaivedSelectorButton);
        click(reasonReasonDebitCardChargeWaivedSelectorButton);
    }

    @Step("Click on 'Auto NSF Chg Waived' option")
    public void clickReasonAutoNSFChgWaivedSelectorOption(String reasonAutoNSFChgWaivedOption) {
        waitForElementVisibility(reasonAutoNSFChgWaivedSelectorOption, reasonAutoNSFChgWaivedOption);
        waitForElementClickable(reasonAutoNSFChgWaivedSelectorOption, reasonAutoNSFChgWaivedOption);
        click(reasonAutoNSFChgWaivedSelectorOption, reasonAutoNSFChgWaivedOption);
    }

    @Step("Returning list of 'Auto NSF Chg Waived'")
    public List<String> getReasonAutoNSFChgWaivedList() {
        waitForElementVisibility(reasonAutoNSFChgWaivedList);
        waitForElementClickable(reasonAutoNSFChgWaivedList);
        return getElementsText(reasonAutoNSFChgWaivedList);
    }

    @Step("Click the 'Auto NSF Chg Waived' selector button")
    public void clickReasonAutoNSFChgWaivedSelectorButton() {
        waitForElementVisibility(reasonAutoNSFChgWaivedSelectorButton);
        scrollToElement(reasonAutoNSFChgWaivedSelectorButton);
        waitForElementClickable(reasonAutoNSFChgWaivedSelectorButton);
        click(reasonAutoNSFChgWaivedSelectorButton);
    }

    @Step("Click on 'Od Protection Acct #' option")
    public void clickOdProtectionAcctSelectorOption(String odProtectionAcctOption) {
        waitForElementVisibility(odProtectionAcctSelectorOption, odProtectionAcctOption);
        waitForElementClickable(odProtectionAcctSelectorOption, odProtectionAcctOption);
        click(odProtectionAcctSelectorOption, odProtectionAcctOption);
    }

    @Step("Returning list of 'Od Protection Acct #'")
    public List<String> getOdProtectionAcctList() {
        waitForElementVisibility(odProtectionAcctList);
        waitForElementClickable(odProtectionAcctList);
        return getElementsText(odProtectionAcctList);
    }

    @Step("Click the 'Od Protection Acct #' selector button")
    public void clickOdProtectionAcctSelectorButton() {
        waitForElementVisibility(odProtectionAcctSelectorButton);
        scrollToElement(odProtectionAcctSelectorButton);
        waitForElementClickable(odProtectionAcctSelectorButton);
        click(odProtectionAcctSelectorButton);
    }

    @Step("Click on 'Reason ATM charge waived' option")
    public void clickReasonATMChargeWaivedSelectorOption(String reasonATMChargeWaivedOption) {
        waitForElementVisibility(reasonATMChargeWaivedSelectorOption, reasonATMChargeWaivedOption);
        waitForElementClickable(reasonATMChargeWaivedSelectorOption, reasonATMChargeWaivedOption);
        click(reasonATMChargeWaivedSelectorOption, reasonATMChargeWaivedOption);
    }

    @Step("Returning list of 'Reason ATM charge waived'")
    public List<String> getReasonATMChargeWaivedList() {
        waitForElementVisibility(reasonATMChargeWaivedList);
        waitForElementClickable(reasonATMChargeWaivedList);
        return getElementsText(reasonATMChargeWaivedList);
    }

    @Step("Click the 'Reason ATM charge waived' selector button")
    public void clickReasonATMChargeWaivedSelectorButton() {
        waitForElementVisibility(reasonATMChargeWaivedSelectorButton);
        scrollToElement(reasonATMChargeWaivedSelectorButton);
        waitForElementClickable(reasonATMChargeWaivedSelectorButton);
        click(reasonATMChargeWaivedSelectorButton);
    }

    @Step("Click on 'Federal W/H Reason' option")
    public void clickFederalWHReasonSelectorOption(String federalWHReasonOption) {
        waitForElementVisibility(federalWHReasonSelectorOption, federalWHReasonOption);
        waitForElementClickable(federalWHReasonSelectorOption, federalWHReasonOption);
        click(federalWHReasonSelectorOption, federalWHReasonOption);
    }

    @Step("Returning list of 'Federal W/H Reason'")
    public List<String> getFederalWHReasonList() {
        waitForElementVisibility(federalWHReasonList);
        waitForElementClickable(federalWHReasonList);
        return getElementsText(federalWHReasonList);
    }

    @Step("Click the 'Federal W/H Reason' selector button")
    public void clickFederalWHReasonSelectorButton() {
        waitForElementVisibility(federalWHReasonSelectorButton);
        scrollToElement(federalWHReasonSelectorButton);
        waitForElementClickable(federalWHReasonSelectorButton);
        click(federalWHReasonSelectorButton);
    }

    /**
    * Edit account
     */

    @Step("Check if 'Total Earnings' field is disabled edit mode")
    public boolean isTotalEarningsFieldDisabledInEditMode() {
        waitForElementVisibility(totalEarningsField);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", totalEarningsField));
    }

    @Step("Check if 'Automatic Overdraft Limit Field' field is disabled edit mode")
    public boolean isAutomaticOverdraftLimitFieldDisabledInEditMode() {
        waitForElementVisibility(automaticOverdraftLimitField);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", automaticOverdraftLimitField));
    }

    @Step("Check if 'Last Debit Amount' field is disabled edit mode")
    public boolean isLastDebitAmountFieldDisabledInEditMode() {
        waitForElementVisibility(lastDebitAmountField);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", lastDebitAmountField));
    }

    @Step("Check if 'Times $5000 Overdrawn-6 Months' field is disabled edit mode")
    public boolean isDaysOverdraftAboveLimitFieldDisabledInEditMode() {
        waitForElementVisibility(daysOverdraftAboveLimitField);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", daysOverdraftAboveLimitField));
    }

    @Step("Check if 'Times Overdrawn-6 Months' field is disabled edit mode")
    public boolean isDaysOverdraftFieldDisabledInEditMode() {
        waitForElementVisibility(daysOverdraftField);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", daysOverdraftField));
    }

    @Step("Check if 'Annual Percentage Yield' field is disabled edit mode")
    public boolean isAnnualPercentageYieldFieldDisabledInEditMode() {
        waitForElementVisibility(annualPercentageYieldField);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", annualPercentageYieldField));
    }

    @Step("Check if 'Date Opened' field is disabled edit mode")
    public boolean isDateClosedFieldDisabledInEditMode() {
        waitForElementVisibility(dateClosedField);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", dateClosedField));
    }

    @Step("Check if 'Date Opened' field is disabled edit mode")
    public boolean isDateOpenedFieldDisabledInEditMode() {
        waitForElementVisibility(dateOpenedField);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", dateOpenedField));
    }

    @Step("Check if 'Account Status' field is disabled edit mode")
    public boolean isAccountStatusFieldDisabledInEditMode() {
        waitForElementVisibility(accountStatusField);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", accountStatusField));
    }

    @Step("Check if 'Originating Officer' field is disabled edit mode")
    public boolean isOriginatingOfficerFieldDisabledInEditMode() {
        waitForElementVisibility(originatingOfficerField);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", originatingOfficerField));
    }

    @Step("Check if 'Account Type' field is disabled edit mode")
    public boolean isAccountTypeFieldDisabledInEditMode() {
        waitForElementVisibility(accountTypeField);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", accountTypeField));
    }

    @Step("Check if 'Account Number' field is disabled edit mode")
    public boolean isAccountNumberFieldDisabledInEditMode() {
        waitForElementVisibility(accountNumberField);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", accountNumberField));
    }

    @Step("Check if 'Product' field is disabled edit mode")
    public boolean isProductFieldDisabledInEditMode() {
        waitForElementVisibility(productField);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", productField));
    }

    @Step("Check if 'Product type' field is disabled edit mode")
    public boolean isProductTypeFieldDisabledInEditMode() {
        waitForElementVisibility(productTypeField);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", productTypeField));
    }

    @Step("Get 'Earning Credit Rate' value in edit mode")
    public String getEarningCreditRateInEditMode() {
        waitForElementVisibility(editEarningCreditRate);
        String rate = getElementAttributeValue("value", editEarningCreditRate);
        return rate.substring(0, rate.length() - 1);
    }

    @Step("Get 'Account Analyzis' value in edit mode")
    public String getAccountAnalysisValueInEditMode() {
        waitForElementVisibility(editAccountAnalysis);
        return getElementText(editAccountAnalysis);
    }

    @Step("Get 'Charge or Analyze' value in edit mode")
    public String getChargeOrAnalyzeInEditMode() {
        waitForElementVisibility(editChargeOrAnalyze);
        return getElementText(editChargeOrAnalyze);
    }

    @Step("Get 'Interest Rate' value in edit mode")
    public String getInterestRateValueInEditMode() {
        waitForElementVisibility(editInterestRate);
        String rate = getElementAttributeValue("value", editInterestRate);
        return rate.substring(0, rate.length() - 1);
    }

    @Step("Get 'Statement Flag' value in edit mode")
    public String getStatementFlagValueInEditMode() {
        waitForElementVisibility(editStatementFlag);
        return getElementAttributeValue("value", editStatementFlag);
    }

    @Step("Get 'Call Class Code' value in edit mode")
    public String getCallClassCodeValueInEditMode() {
        waitForElementVisibility(editCallClassCode);
        return getElementText(editCallClassCode);
    }

    @Step("Get 'Statement Cycle' value in edit mode")
    public String getStatementCycleValueInEditMode() {
        waitForElementVisibility(editStatementCycle);
        return getElementText(editStatementCycle);
    }

    @Step("Get 'Current Officer' value in edit mode")
    public String getCurrentOfficerValueInEditMode() {
        waitForElementVisibility(editCurrentOfficer);
        return getElementText(editCurrentOfficer);
    }

    @Step("Get 'Date Opened' value in edit mode")
    public String getDateOpenedValueInEditMode() {
        waitForElementVisibility(editDateOpened);
        return getElementAttributeValue("value", editDateOpened);
    }

    @Step("Get 'Product' value in edit mode")
    public String getProductValueInEditMode() {
        waitForElementVisibility(editProduct);
        return getElementText(editProduct);
    }

    @Step("Get 'Bank Branch' value in edit mode")
    public String getBankBranchValueInEditMode() {
        waitForElementVisibility(editBankBranch);
        return getElementText(editBankBranch);
    }

    @Step("Get 'Account Title' value in edit mode")
    public String getAccountTitleValueInEditMode() {
        waitForElementVisibility(editAccountTitle);
        return getElementAttributeValue("value", editAccountTitle);
    }

    /**
     * Details tab
     */

    @Step("Get account 'Reason Debit Card Charge Waived' value")
    public String getReasonDebitCardChargeWaived() {
        waitForElementVisibility(reasonDebitCardChargeWaived);
        return getElementText(reasonDebitCardChargeWaived);
    }

    @Step("Get account 'Reason Auto NSF Chg Waived' value")
    public String getReasonAutoNSFChgWaived() {
        waitForElementVisibility(reasonAutoNSFChgWaived);
        return getElementText(reasonAutoNSFChgWaived);
    }

    @Step("Get account 'OD Protection Acct' value")
    public String getOdProtectionAcct() {
        waitForElementVisibility(odProtectionAcct);
        return getElementText(odProtectionAcct);
    }

    @Step("Get account 'Reason ATM Charge Waived' value")
    public String getReasonATMChargeWaived() {
        waitForElementVisibility(reasonATMChargeWaived);
        return getElementText(reasonATMChargeWaived);
    }

    @Step("Get account 'Federal W/H reason' value")
    public String getFederalWHReason() {
        waitForElementVisibility(federalWHReason);
        return getElementText(federalWHReason);
    }

    @Step("Click the 'Edit' button")
    public void clickEditButton() {
        waitForElementVisibility(editButton);
        waitForElementClickable(editButton);
        click(editButton);
    }

    @Step("Get account 'Earning Credit Rate' value")
    public String getEarningCreditRate() {
        waitForElementVisibility(earningCreditRate);
        String rate = getElementText(earningCreditRate);
        return rate.substring(0, rate.length() - 1);
    }

    @Step("Get account 'Statement Flag' value")
    public String getInterestRateValue() {
        waitForElementVisibility(interestRate);
        String rate = getElementText(interestRate);
        return rate.substring(0, rate.length() - 1);
    }

    @Step("Get account 'Statement Flag' value")
    public String getStatementFlagValue() {
        waitForElementVisibility(statementFlag);
        return getElementText(statementFlag);
    }

    @Step("Get account 'Account Analysis' value")
    public String getAccountAnalysisValue() {
        waitForElementVisibility(accountAnalysis);
        return getElementText(accountAnalysis);
    }

    @Step("Get account 'Charge or Analyze' value")
    public String getChargeOrAnalyze() {
        waitForElementVisibility(chargeOrAnalyze);
        return getElementText(chargeOrAnalyze);
    }

    @Step("Get account 'Call Class Code' value")
    public String getCallClassCode() {
        waitForElementVisibility(callClass);
        return getElementText(callClass);
    }

    @Step("Get account 'Current Officer' value")
    public String getStatementCycle() {
        waitForElementVisibility(statementCycle);
        return getElementText(statementCycle);
    }

    @Step("Get account 'Current Officer' value")
    public String getCurrentOfficerValue() {
        waitForElementVisibility(currentOfficer);
        return getElementText(currentOfficer);
    }

    @Step("Get account 'Date opened' value")
    public String getDateOpenedValue() {
        waitForElementVisibility(dateOpened);
        return getElementText(dateOpened);
    }

    @Step("Get account 'Product' value")
    public String getProductValue() {
        waitForElementVisibility(product);
        return getElementText(product);
    }

    @Step("Wait for 'Full Profile' button is visible")
    public void waitForFullProfileButton() {
        waitForElementVisibility(fullProfileButton);
        waitForElementClickable(fullProfileButton);
    }

    @Step("Get account 'Product Type' value")
    public String getProductTypeValue() {
        waitForElementVisibility(productType);
        return getElementText(productType);
    }

    @Step("Get account 'Box size' value")
    public String getBoxSizeValue() {
        waitForElementVisibility(boxSize);
        return getElementText(boxSize);
    }

    @Step("Get account 'Account Number' value")
    public String getAccountNumberValue() {
        waitForElementVisibility(accountNumber);
        return getElementText(accountNumber);
    }

    @Step("Get account 'Account Title' value")
    public String getAccountTitleValue() {
        waitForElementVisibility(accountTitle);
        return getElementText(accountTitle);
    }

    @Step("Get account 'Bank Branch' value")
    public String getBankBranchValue() {
        waitForElementVisibility(bankBranch);
        return getElementText(bankBranch);
    }

    @Step("Click the 'More' button")
    public void clickMoreButton() {
        waitForElementVisibility(moreButton);
        waitForElementClickable(moreButton);
        click(moreButton);
    }

    public boolean isMoreButtonVisible() {
        waitForElementVisibility(moreButton);
        waitForElementClickable(moreButton);
        return isElementVisible(moreButton);
    }

    @Step("Click the 'Less' button")
    public void clickLessButton() {
        waitForElementVisibility(lessButton);
        waitForElementClickable(lessButton);
        click(lessButton);
    }

    /**
     * Tabs button
     */
    @Step("Click the 'Maintenance' tab")
    public void clickMaintenanceTab() {
        waitForElementVisibility(maintenanceTab);
        waitForElementClickable(maintenanceTab);
        click(maintenanceTab);
    }

    /**
     * Maintenance tab
     */
    public void clickViewAllMaintenanceHistoryLink() {
        waitForElementVisibility(viewAllMaintenanceHistoryLink);
        waitForElementClickable(viewAllMaintenanceHistoryLink);
        click(viewAllMaintenanceHistoryLink);
    }

    public void clickViewMoreButton() {
        waitForElementVisibility(viewMoreButton);
        waitForElementClickable(viewMoreButton);
        click(viewMoreButton);
    }

}
