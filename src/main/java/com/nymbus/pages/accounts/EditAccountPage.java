package com.nymbus.pages.accounts;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

public class EditAccountPage extends PageTools {

    private By accountTitle = By.xpath("//input[@id='accounttitlemailinginstructions']");
    private By bankBranch = By.xpath("//div[@id='bankbranch']//span[contains(@class, 'ng-scope')]");
    private By product = By.xpath("//div[@id='accountclasstype']//span[contains(@class, 'ng-scope')]");
    private By dateOpened = By.xpath("//input[@id='dateopened']");
    private By currentOfficer = By.xpath("//div[@id='officer']//span[contains(@class, 'ng-scope')]");
    private By statementCycle = By.xpath("//div[@id='statementcycle']//span[contains(@class, 'ng-scope')]");
    private By callClassCode = By.xpath("//div[@id='callclasscode']//span[contains(@class, 'ng-scope')]");
    private By chargeOrAnalyze = By.xpath("//div[@id='chargeoranalyze']//span[contains(@class, 'ng-scope')]");
    private By accountAnalysis = By.xpath("//div[@id='accountanalysis']//span[contains(@class, 'ng-scope')]");
    private By statementFlag = By.xpath("//input[@id='statementflag']");
    private By interestRate = By.xpath("//input[@id='interestrate']");
    private By earningCreditRate = By.xpath("//input[@id='earningscreditrate']");

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
     * Get values in edit mode
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
        waitForElementVisibility(earningCreditRate);
        String rate = getElementAttributeValue("value", earningCreditRate);
        return rate.substring(0, rate.length() - 1);
    }

    @Step("Get 'Account Analyzis' value in edit mode")
    public String getAccountAnalysisValueInEditMode() {
        waitForElementVisibility(accountAnalysis);
        return getElementText(accountAnalysis);
    }

    @Step("Get 'Charge or Analyze' value in edit mode")
    public String getChargeOrAnalyzeInEditMode() {
        waitForElementVisibility(chargeOrAnalyze);
        return getElementText(chargeOrAnalyze);
    }

    @Step("Get 'Interest Rate' value in edit mode")
    public String getInterestRateValueInEditMode() {
        waitForElementVisibility(interestRate);
        String rate = getElementAttributeValue("value", interestRate);
        return rate.substring(0, rate.length() - 1);
    }

    @Step("Get 'Statement Flag' value in edit mode")
    public String getStatementFlagValueInEditMode() {
        waitForElementVisibility(statementFlag);
        return getElementAttributeValue("value", statementFlag);
    }

    @Step("Get 'Call Class Code' value in edit mode")
    public String getCallClassCodeValueInEditMode() {
        waitForElementVisibility(callClassCode);
        return getElementText(callClassCode);
    }

    @Step("Get 'Statement Cycle' value in edit mode")
    public String getStatementCycleValueInEditMode() {
        waitForElementVisibility(statementCycle);
        return getElementText(statementCycle);
    }

    @Step("Get 'Current Officer' value in edit mode")
    public String getCurrentOfficerValueInEditMode() {
        waitForElementVisibility(currentOfficer);
        return getElementText(currentOfficer);
    }

    @Step("Get 'Date Opened' value in edit mode")
    public String getDateOpenedValueInEditMode() {
        waitForElementVisibility(dateOpened);
        return getElementAttributeValue("value", dateOpened);
    }

    @Step("Get 'Product' value in edit mode")
    public String getProductValueInEditMode() {
        waitForElementVisibility(product);
        return getElementText(product);
    }

    @Step("Get 'Bank Branch' value in edit mode")
    public String getBankBranchValueInEditMode() {
        waitForElementVisibility(bankBranch);
        return getElementText(bankBranch);
    }

    @Step("Get 'Account Title' value in edit mode")
    public String getAccountTitleValueInEditMode() {
        waitForElementVisibility(accountTitle);
        return getElementAttributeValue("value", accountTitle);
    }

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
}
