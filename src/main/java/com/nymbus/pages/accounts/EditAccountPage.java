package com.nymbus.pages.accounts;

import com.nymbus.base.BasePage;
import com.nymbus.locator.Locator;
import com.nymbus.locator.XPath;
import io.qameta.allure.Step;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

public class EditAccountPage extends BasePage {

    private Locator accountTitle = new XPath("//input[@id='accounttitlemailinginstructions']");
    private Locator bankBranch = new XPath("//div[@id='bankbranch']//span[contains(@class, 'ng-scope')]");
    private Locator product = new XPath("//div[@id='accountclasstype']//span[contains(@class, 'ng-scope')]");
    private Locator dateOpened = new XPath("//input[@id='dateopened']");
    private Locator currentOfficer = new XPath("//div[@id='officer']//span[contains(@class, 'ng-scope')]");
    private Locator statementCycle = new XPath("//div[@id='statementcycle']//span[contains(@class, 'ng-scope')]");
    private Locator callClassCode = new XPath("//div[@id='callclasscode']//span[contains(@class, 'ng-scope')]");
    private Locator chargeOrAnalyze = new XPath("//div[@id='chargeoranalyze']//span[contains(@class, 'ng-scope')]");
    private Locator accountAnalysis = new XPath("//div[@id='accountanalysis']//span[contains(@class, 'ng-scope')]");
    private Locator statementFlag = new XPath("//input[@id='statementflag']");
    private Locator interestRate = new XPath("//input[@id='interestrate']");
    private Locator earningCreditRate = new XPath("//input[@id='earningscreditrate']");
    private Locator federalWHReason = new XPath("//div[@id='federalwithholdingreason']//span[contains(@class, 'ng-scope')]");
    private Locator reasonATMChargeWaived = new XPath("//div[@id='reasonatmchargeswaived']//span[contains(@class, 'ng-scope')]");
    private Locator odProtectionAcct = new XPath("//div[@id='overdraftprotectionaccountnumber']//span[contains(@class, 'ng-scope')]");
    private Locator reasonAutoNSFChgWaived = new XPath("//div[@id='reasonautonsfchargeswaived']//span[contains(@class, 'ng-scope')]");
    private Locator reasonDebitCardChargeWaived = new XPath("//div[@id='reasondebitcardchargeswaived']//span[contains(@class, 'ng-scope')]");
    private Locator automaticOverdraftStatus = new XPath("//div[@id='automaticoverdraftstatus']//span[contains(@class, 'ng-scope')]");
    private Locator reasonAutoOdChgWaived = new XPath("//div[@id='reasonautoodchargeswaived']//span[contains(@class, 'ng-scope')]");
    private Locator whenSurchargesRefunded = new XPath("//div[@id='whensurchargesrefunded']//span[contains(@class, 'ng-scope')]");

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
    private Locator cashCollFloatInput = new XPath("//input[@id='cashcollectionfloat']");
    private Locator automaticOverdraftLimitInput = new XPath("//input[@id='automaticoverdraftlimit']");
    private Locator interestFrequency = new XPath("//div[@id='interestfrequency']//span[contains(@class, 'ng-scope')]");
    private Locator primaryAccountForCombinedStatement = new XPath("//div[@id='ddaaccountidforcombinedstatement']//span[contains(@class, 'ng-scope')]");
    private Locator correspondingAccount = new XPath("//div[@id='correspondingaccountid']//span[contains(@class, 'ng-scope')]");

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

    private Locator currentBalance = new XPath("//input[@id='currentbalance']");
    private Locator availableBalance = new XPath("//input[@id='currentbalance']");
    private Locator averageBalance = new XPath("//input[@id='averagebalance']");
    private Locator lowBalanceThisStatementCycle = new XPath("//input[@id='lowbalancethisstatementcycle']");
    private Locator balanceLastStatement = new XPath("//input[@id='balancelaststatement']");
    private Locator dateLastWithdrawal = new XPath("//input[@id='datelastwithdrawal']");
    private Locator dateLastDeposit = new XPath("//input[@id='datelastdeposit']");
    private Locator dateLastStatement = new XPath("//input[@id='datelaststatement']");
    private Locator numberOfWithdrawalsThisStatementCycle = new XPath("//input[@id='numberofwithdrawalsthisstatementcycle']");
    private Locator numberOfDepositsThisStatementCycle = new XPath("//input[@id='numberofdepositsthisstatementcycle']");

    /**
     * Get values in edit mode
     */

    @Step("Get 'Corresponding Account' value in edit mode")
    public String getCorrespondingAccount() {
        waitForElementVisibility(correspondingAccount);
        return getElementText(correspondingAccount);
    }

    @Step("Get 'Primary Account For Combined Statement' value in edit mode")
    public String getPrimaryAccountForCombinedStatement() {
        waitForElementVisibility(primaryAccountForCombinedStatement);
        return getElementText(primaryAccountForCombinedStatement);
    }

    @Step("Get 'Interest Frequency' value in edit mode")
    public String getInterestFrequency() {
        waitForElementVisibility(interestFrequency);
        return getElementText(interestFrequency);
    }

    @Step("Get 'Automatic Overdraft Limit' value in edit mode")
    public String getAutomaticOverdraftLimit() {
        waitForElementVisibility(automaticOverdraftLimitInput);
        String overdraftValue = getElementAttributeValue("value", automaticOverdraftLimitInput);
        return overdraftValue.replaceAll("[^0-9]", "");
    }

    @Step("Get 'Positive Pay' value in edit mode")
    public String getPositivePay() {
        waitForElementVisibility(positivePayInput);
        return getElementAttributeValue("value", positivePayInput);
    }

    @Step("Get 'Cash Coll Float' value in edit mode")
    public String getCashCollFloat() {
        waitForElementVisibility(cashCollFloatInput);
        String cashCollFloatValue = getElementAttributeValue("value", cashCollFloatInput);
        return cashCollFloatValue.replaceAll("[^0-9]", "");
    }

    @Step("Get 'Cash Coll Interest Chg' value in edit mode")
    public String getCashCollInterestChg() {
        waitForElementVisibility(cashCollInterestChgInput);
        String cashCollValue = getElementAttributeValue("value", cashCollInterestChgInput);
        return cashCollValue.replaceAll("[^0-9]", "");
    }

    @Step("Get 'Cash Coll Days Before Chg' value in edit mode")
    public String getCashCollDaysBeforeChg() {
        waitForElementVisibility(cashCollDaysBeforeChgInput);
        String cashCollDaysValue = getElementAttributeValue("value", cashCollDaysBeforeChgInput);
        return cashCollDaysValue.replaceAll("[^0-9]", "");
    }

    @Step("Get 'Number Of Debit Cards Issued' value in edit mode")
    public String getNumberOfDebitCardsIssued() {
        waitForElementVisibility(numberOfDebitCardsIssuedInput);
        return getElementAttributeValue("value", numberOfDebitCardsIssuedInput);
    }

    @Step("Get 'Image Statement Code' value in edit mode")
    public String getImageStatementCode() {
        waitForElementVisibility(imageStatementCodeInput);
        return getElementAttributeValue("value", imageStatementCodeInput);
    }

    @Step("Get 'When Surcharges Refunded' value in edit mode")
    public String getWhenSurchargesRefunded() {
        waitForElementVisibility(whenSurchargesRefunded);
        return getElementText(whenSurchargesRefunded);
    }

    @Step("Get 'Federal WH Percent' value in edit mode")
    public String getFederalWHPercent() {
        waitForElementVisibility(federalWHPercentInput);
        String percentValue = getElementAttributeValue("value", federalWHPercentInput);
        return percentValue.substring(0, percentValue.length() - 1);
    }

    @Step("Get 'Number Of ATM Cards Issued' value in edit mode")
    public String getNumberOfATMCardsIssued() {
        waitForElementVisibility(numberOfATMCardsIssuedInput);
        return getElementAttributeValue("value", numberOfATMCardsIssuedInput);
    }

    @Step("Get 'Earning Credit Rate' value in edit mode")
    public String getEarningCreditRate() {
        waitForElementVisibility(earningCreditRate);
        String rate = getElementAttributeValue("value", earningCreditRate);
        return rate.substring(0, rate.length() - 1);
    }

    @Step("Get 'User Defined Field 1' value in edit mode")
    public String getUserDefinedField4() {
        waitForElementVisibility(userDefinedFieldInput_4);
        return getElementAttributeValue("value", userDefinedFieldInput_4);
    }

    @Step("Get 'User Defined Field 1' value in edit mode")
    public String getUserDefinedField3() {
        waitForElementVisibility(userDefinedFieldInput_3);
        return getElementAttributeValue("value", userDefinedFieldInput_3);
    }

    @Step("Get 'User Defined Field 2' value in edit mode")
    public String getUserDefinedField2() {
        waitForElementVisibility(userDefinedFieldInput_2);
        return getElementAttributeValue("value", userDefinedFieldInput_2);
    }

    @Step("Get 'User Defined Field 1' value in edit mode")
    public String getUserDefinedField1() {
        waitForElementVisibility(userDefinedFieldInput_1);
        return getElementAttributeValue("value", userDefinedFieldInput_1);
    }

    @Step("Get 'Reason Auto NSF Chg Waived' value in edit mode")
    public String getReasonAutoNSFChgWaived() {
        waitForElementVisibility(reasonAutoNSFChgWaived);
        return getElementText(reasonAutoNSFChgWaived);
    }

    @Step("Get 'Reason Debit Card Charge Waived' value in edit mode")
    public String getReasonDebitCardChargeWaived() {
        waitForElementVisibility(reasonDebitCardChargeWaived);
        return getElementText(reasonDebitCardChargeWaived);
    }

    @Step("Get 'Automatic Overdraft Status' value in edit mode")
    public String getAutomaticOverdraftStatus() {
        waitForElementVisibility(automaticOverdraftStatus);
        return getElementText(automaticOverdraftStatus);
    }

    @Step("Get 'Reason Auto Od Chg Waived' value in edit mode")
    public String getReasonAutoOdChgWaived() {
        waitForElementVisibility(reasonAutoOdChgWaived);
        return getElementText(reasonAutoOdChgWaived);
    }

    @Step("Get 'OD Protection Acct' value in edit mode")
    public String getOdProtectionAcct() {
        waitForElementVisibility(odProtectionAcct);
        return getElementText(odProtectionAcct);
    }

    @Step("Get 'Reason ATM Charge Waived' value in edit mode")
    public String getReasonATMChargeWaived() {
        waitForElementVisibility(reasonATMChargeWaived);
        return getElementText(reasonATMChargeWaived);
    }

    @Step("Get 'Federal W/H Reason' value in edit mode")
    public String getFederalWHReasonInEditMode() {
        waitForElementVisibility(federalWHReason);
        return getElementText(federalWHReason);
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

    /**
    * Check if field is disabled in edit mode
     */

    @Step("Check if 'Current Balance' field is disabled edit mode")
    public boolean isCurrentBalanceDisabledInEditMode() {
        waitForElementVisibility(currentBalance);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", currentBalance));
    }

    @Step("Check if 'Total Earnings' field is disabled edit mode")
    public boolean isAvailableBalanceDisabledInEditMode() {
        waitForElementVisibility(availableBalance);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", availableBalance));
    }

    @Step("Check if 'Average Balance' field is disabled edit mode")
    public boolean isAverageBalanceDisabledInEditMode() {
        waitForElementVisibility(averageBalance);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", averageBalance));
    }

    @Step("Check if 'Low Balance This Statement Cycle' field is disabled edit mode")
    public boolean isLowBalanceThisStatementCycleDisabledInEditMode() {
        waitForElementVisibility(lowBalanceThisStatementCycle);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", lowBalanceThisStatementCycle));
    }

    @Step("Check if 'Balance Last Statement' field is disabled edit mode")
    public boolean isBalanceLastStatementDisabledInEditMode() {
        waitForElementVisibility(balanceLastStatement);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", balanceLastStatement));
    }

    @Step("Check if 'Date Last Withdrawal' field is disabled edit mode")
    public boolean isDateLastWithdrawalDisabledInEditMode() {
        waitForElementVisibility(dateLastWithdrawal);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", dateLastWithdrawal));
    }

    @Step("Check if 'Date Last Deposit' field is disabled edit mode")
    public boolean isDateLastDepositDisabledInEditMode() {
        waitForElementVisibility(dateLastDeposit);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", dateLastDeposit));
    }

    @Step("Check if 'Date Last Statement' field is disabled edit mode")
    public boolean isDateLastStatementDisabledInEditMode() {
        waitForElementVisibility(dateLastStatement);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", dateLastStatement));
    }

    @Step("Check if 'Number Of Withdrawals This Statement Cycle' field is disabled edit mode")
    public boolean isNumberOfWithdrawalsThisStatementCycleDisabledInEditMode() {
        waitForElementVisibility(numberOfWithdrawalsThisStatementCycle);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", numberOfWithdrawalsThisStatementCycle));
    }

    @Step("Check if 'Number Of Deposits This Statement Cycle' field is disabled edit mode")
    public boolean isNumberOfDepositsThisStatementCycleDisabledInEditMode() {
        waitForElementVisibility(numberOfDepositsThisStatementCycle);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", numberOfDepositsThisStatementCycle));
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

    @Step("Set 'Federal W/H percent' value")
    public void setAutomaticOverdraftLimit(String automaticOverdraftLimitValue) {
        waitForElementVisibility(automaticOverdraftLimitInput);
        waitForElementClickable(automaticOverdraftLimitInput);
        type(automaticOverdraftLimitValue, automaticOverdraftLimitInput);
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
