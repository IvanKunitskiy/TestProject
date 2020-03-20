package com.nymbus.pages.accounts;

import com.nymbus.core.base.PageTools;
import com.nymbus.core.utils.SelenideTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

public class AccountDetailsPage extends PageTools {

    /**
     * Tabs button
     */
    private By maintenanceTab = By.xpath("//a[contains(text(), 'Maintenance')]");

    /**
     * Account actions
     */
    private By editButton = By.xpath("//button[@data-test-id='action-editAccount']");

    /**
     * Details tab
     */
    private By moreButton = By.xpath("//button[@data-test-id='action-showMoreFields']");
    private By lessButton = By.xpath("//button[@data-test-id='action-hideLessFields']");
    private By fullProfileButton = By.xpath("//button[@data-test-id='go-fullProfile']");
    private By productType = By.xpath("//tr[@data-config-name='accounttype']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By boxSize = By.xpath("//tr[@data-config-name='boxsize']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By accountNumber = By.xpath("//tr[@data-config-name='accountnumber']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By accountTitle = By.xpath("//tr[@data-config-name='accounttitlemailinginstructions']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By bankBranch = By.xpath("//tr[@data-config-name='bankbranch']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By product = By.xpath("//tr[@data-config-name='accountclasstype']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By dateOpened = By.xpath("//tr[@data-config-name='dateopened']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By currentOfficer = By.xpath("//tr[@data-config-name='officer']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By statementCycle = By.xpath("//tr[@data-config-name='statementcycle']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By callClass = By.xpath("//tr[@data-config-name='callclasscode']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By chargeOrAnalyze = By.xpath("//tr[@data-config-name='chargeoranalyze']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By accountAnalysis = By.xpath("//tr[@data-config-name='accountanalysis']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By statementFlag = By.xpath("//tr[@data-config-name='statementflag']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By interestRate = By.xpath("//tr[@data-config-name='interestrate']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By earningCreditRate = By.xpath("//tr[@data-config-name='earningscreditrate']//span[contains(@class, 'dnTextFixedWidthText')]");

    private By federalWHReason = By.xpath("//tr[@data-config-name='federalwithholdingreason']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By reasonDebitCardChargeWaived = By.xpath("//tr[@data-config-name='reasondebitcardchargeswaived']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By reasonAutoNSFChgWaived = By.xpath("//tr[@data-config-name='reasonautonsfchargeswaived']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By odProtectionAcct = By.xpath("//tr[@data-config-name='overdraftprotectionaccountnumber']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By reasonATMChargeWaived = By.xpath("//tr[@data-config-name='reasonatmchargeswaived']//span[contains(@class, 'dnTextFixedWidthText')]");

    /**
     * Edit Account
     */

    private By editAccountTitle = By.xpath("//input[@id='accounttitlemailinginstructions']");
    private By editBankBranch = By.xpath("//div[@id='bankbranch']//span[contains(@class, 'ng-scope')]");
    private By editProduct = By.xpath("//div[@id='accountclasstype']//span[contains(@class, 'ng-scope')]");
    private By editDateOpened = By.xpath("//input[@id='dateopened']");
    private By editCurrentOfficer = By.xpath("//div[@id='officer']//span[contains(@class, 'ng-scope')]");
    private By editStatementCycle = By.xpath("//div[@id='statementcycle']//span[contains(@class, 'ng-scope')]");
    private By editCallClassCode = By.xpath("//div[@id='callclasscode']//span[contains(@class, 'ng-scope')]");
    private By editChargeOrAnalyze = By.xpath("//div[@id='chargeoranalyze']//span[contains(@class, 'ng-scope')]");
    private By editAccountAnalysis = By.xpath("//div[@id='accountanalysis']//span[contains(@class, 'ng-scope')]");
    private By editStatementFlag = By.xpath("//input[@id='statementflag']");
    private By editInterestRate = By.xpath("//input[@id='interestrate']");
    private By editEarningCreditRate = By.xpath("//input[@id='earningscreditrate']");
    private By editAutomaticOverdraftStatus = By.xpath("//div[@id='automaticoverdraftstatus']//span[contains(@class, 'ng-scope')]");

    private By federalWHReasonSelectorButton = By.xpath("//div[@id='federalwithholdingreason']");
    private By federalWHReasonList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private By federalWHReasonSelectorOption = By.xpath("//div[@id='federalwithholdingreason']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private By reasonATMChargeWaivedSelectorButton = By.xpath("//div[@id='reasonatmchargeswaived']");
    private By reasonATMChargeWaivedList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private By reasonATMChargeWaivedSelectorOption = By.xpath("//div[@id='reasonatmchargeswaived']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private By odProtectionAcctSelectorButton = By.xpath("//div[@id='overdraftprotectionaccountnumber']");
    private By odProtectionAcctList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private By odProtectionAcctSelectorOption = By.xpath("//div[@id='overdraftprotectionaccountnumber']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private By reasonAutoNSFChgWaivedSelectorButton = By.xpath("//div[@id='reasonautonsfchargeswaived']");
    private By reasonAutoNSFChgWaivedList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private By reasonAutoNSFChgWaivedSelectorOption = By.xpath("//div[@id='reasonautonsfchargeswaived']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private By reasonReasonDebitCardChargeWaivedSelectorButton = By.xpath("//div[@id='reasondebitcardchargeswaived']");
    private By reasonReasonDebitCardChargeWaivedList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private By reasonReasonDebitCardChargeWaivedSelectorOption = By.xpath("//div[@id='reasondebitcardchargeswaived']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private By automaticOverdraftStatusSelectorButton = By.xpath("//div[@id='automaticoverdraftstatus']");
    private By automaticOverdraftStatusList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private By automaticOverdraftStatusSelectorOption = By.xpath("//div[@id='automaticoverdraftstatus']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private By reasonAutoOdChgWaivedSelectorButton = By.xpath("//div[@id='reasonautoodchargeswaived']");
    private By reasonAutoOdChgWaivedList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private By reasonAutoOdChgWaivedSelectorOption = By.xpath("//div[@id='reasonautoodchargeswaived']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private By whenSurchargesRefundedSelectorButton = By.xpath("//div[@id='whensurchargesrefunded']");
    private By whenSurchargesRefundedList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private By whenSurchargesRefundedSelectorOption = By.xpath("//div[@id='whensurchargesrefunded']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private By federalWHPercentInput = By.xpath("//input[@id='federalwithholdingpercent']");
    private By numberOfATMCardsIssuedInput = By.xpath("//input[@id='numberofatmcardissued']");
    private By userDefinedFieldInput_1 = By.xpath("//input[@id='userdefinedfield1']");
    private By userDefinedFieldInput_2 = By.xpath("//input[@id='userdefinedfield2']");
    private By userDefinedFieldInput_3 = By.xpath("//input[@id='userdefinedfield3']");
    private By userDefinedFieldInput_4 = By.xpath("//input[@id='userdefinedfield4']");
    private By imageStatementCodeInput = By.xpath("//input[@id='imagestatementcode']");
    private By numberOfDebitCardsIssuedInput = By.xpath("//input[@id='numberofdebitcardsissued']");
    private By cashCollDaysBeforeChgInput = By.xpath("//input[@id='cashcollectionnumberofdaysbeforeinterestcharge']");
    private By cashCollInterestRateInput = By.xpath("//input[@id='cashcollectioninterestrate']");
    private By cashCollInterestChgInput = By.xpath("//input[@id='cashcollectioninterestchargesperstatementcycle']");
    private By positivePayInput = By.xpath("//input[@id='positivepaycustomer']");
    private By cashColFloatInput = By.xpath("//input[@id='cashcollectionfloat']");
    private By earningCreditRateInput = By.xpath("//input[@id='earningscreditrate']");
    private By interestRateInput = By.xpath("//input[@id='interestrate']");
    private By reasonAutoOdChgWaived = By.xpath("//div[@id='reasonautoodchargeswaived']");

    /**
     * Disabled fields in edit mode
     */
    private By productTypeField = By.xpath("//div[@id='accounttype']");
    private By productField = By.xpath("//div[@id='accountclasstype']");
    private By accountNumberField = By.xpath("//input[@id='accountnumber']");
    private By accountTypeField = By.xpath("//div[@id='customertype']");
    private By originatingOfficerField = By.xpath("//div[@id='originatingofficer']");
    private By accountStatusField = By.xpath("//input[@id='accountstatus']");
    private By dateOpenedField = By.xpath("//input[@id='dateopened']");
    private By dateClosedField = By.xpath("//input[@id='dateclosed']");
    private By annualPercentageYieldField = By.xpath("//input[@id='apy']");
    private By daysOverdraftField = By.xpath("//input[@id='daysoverdraftregcc']");
    private By daysOverdraftAboveLimitField = By.xpath("//input[@id='daysoverdraftabovelimitregcc']");
    private By lastDebitAmountField = By.xpath("//input[@id='lastwithdrawalamount']");
    private By automaticOverdraftLimitField = By.xpath("//input[@id='automaticoverdraftlimit']");
    private By totalEarningsField = By.xpath("//input[@id='totalEarnings']");

    /**
     * Maintenance tab
     */
    private By viewAllMaintenanceHistoryLink = By.xpath("//button//span[contains(text(), 'View All History')]");
    private By viewMoreButton = By.xpath("//button[@data-test-id='action-loadMore']");

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

    @Step("Get 'Reason Auto Od Chg Waived' value in edit mode")
    public String getReasonAutoOdChgWaived() {
        waitForElementVisibility(reasonAutoOdChgWaived);
        return getElementAttributeValue("value", reasonAutoOdChgWaived);
    }

    @Step("Get 'Automatic Overdraft Status' value in edit mode")
    public String getAutomaticOverdraftStatus() {
        waitForElementVisibility(editAutomaticOverdraftStatus);
        return getElementText(editAutomaticOverdraftStatus);
    }

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
        SelenideTools.sleep(5);
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
