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
    private By accountTitle = new XPath("//input[@id='accounttitlemailinginstructions']");
    private By bankBranch = new XPath("//div[@id='bankbranch']//span[contains(@class, 'ng-scope')]");
    private By product = new XPath("//div[@id='accountclasstype']//span[contains(@class, 'ng-scope')]");
    private By dateOpened = new XPath("//input[@id='dateopened']");
    private By currentOfficer = new XPath("//div[@id='officer']//span[contains(@class, 'ng-scope')]");
    private By statementCycle = new XPath("//div[@id='statementcycle']//span[contains(@class, 'ng-scope')]");
    private By callClassCode = new XPath("//div[@id='callclasscode']//span[contains(@class, 'ng-scope')]");
    private By chargeOrAnalyze = new XPath("//div[@id='chargeoranalyze']//span[contains(@class, 'ng-scope')]");
    private By accountAnalysis = new XPath("//div[@id='accountanalysis']//span[contains(@class, 'ng-scope')]");
    private By statementFlag = new XPath("//input[@id='statementflag']");
    private By interestRate = new XPath("//input[@id='interestrate']");
    private By earningCreditRate = new XPath("//input[@id='earningscreditrate']");
    private By federalWHReason = new XPath("//div[@id='federalwithholdingreason']//span[contains(@class, 'ng-scope')]");
    private By reasonATMChargeWaived = new XPath("//div[@id='reasonatmchargeswaived']//span[contains(@class, 'ng-scope')]");
    private By odProtectionAcct = new XPath("//div[@id='overdraftprotectionaccountnumber']//span[contains(@class, 'ng-scope')]");
    private By reasonAutoNSFChgWaived = new XPath("//div[@id='reasonautonsfchargeswaived']//span[contains(@class, 'ng-scope')]");
    private By reasonDebitCardChargeWaived = new XPath("//div[@id='reasondebitcardchargeswaived']//span[contains(@class, 'ng-scope')]");
    private By automaticOverdraftStatus = new XPath("//div[@id='automaticoverdraftstatus']//span[contains(@class, 'ng-scope')]");
    private By reasonAutoOdChgWaived = new XPath("//div[@id='reasonautoodchargeswaived']//span[contains(@class, 'ng-scope')]");
    private By whenSurchargesRefunded = new XPath("//div[@id='whensurchargesrefunded']//span[contains(@class, 'ng-scope')]");
    private By printStatementNextUpdate = new XPath("//input[@id='printstatementnextupdate']");
    private By interestPaidYTD = new XPath("//input[@id='interestpaidytd']");
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
    private By federalWHPercentInput = new XPath("//input[@id='federalwithholdingpercent']");
    private By numberOfATMCardsIssuedInput = new XPath("//input[@id='numberofatmcardissued']");
    private By userDefinedFieldInput_1 = new XPath("//input[@id='userdefinedfield1']");
    private By userDefinedFieldInput_2 = new XPath("//input[@id='userdefinedfield2']");
    private By userDefinedFieldInput_3 = new XPath("//input[@id='userdefinedfield3']");
    private By userDefinedFieldInput_4 = new XPath("//input[@id='userdefinedfield4']");
    private By imageStatementCodeInput = new XPath("//input[@id='imagestatementcode']");
    private By numberOfDebitCardsIssuedInput = new XPath("//input[@id='numberofdebitcardsissued']");
    private By cashCollDaysBeforeChgInput = new XPath("//input[@id='cashcollectionnumberofdaysbeforeinterestcharge']");
    private By cashCollInterestRateInput = new XPath("//input[@id='cashcollectioninterestrate']");
    private By cashCollInterestChgInput = new XPath("//input[@id='cashcollectioninterestchargesperstatementcycle']");
    private By positivePayInput = new XPath("//input[@id='positivepaycustomer']");
    private By cashColFloatInput = new XPath("//input[@id='cashcollectionfloat']");
    private By earningCreditRateInput = new XPath("//input[@id='earningscreditrate']");
    private By interestRateInput = new XPath("//input[@id='interestrate']");
    private By cashCollFloatInput = new XPath("//input[@id='cashcollectionfloat']");
    private By automaticOverdraftLimitInput = new XPath("//input[@id='automaticoverdraftlimit']");
    private By interestFrequency = new XPath("//div[@id='interestfrequency']//span[contains(@class, 'ng-scope')]");
    private By primaryAccountForCombinedStatement = new XPath("//div[@id='ddaaccountidforcombinedstatement']//span[contains(@class, 'ng-scope')]");
    private By correspondingAccount = new XPath("//div[@id='correspondingaccountid']//span[contains(@class, 'ng-scope')]");
    private By newAccountSwitch = new XPath("//dn-switch[@id='newaccount']");
    private By transactionalAccountSwitch = new XPath("//dn-switch[@id='newaccount']");

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

    private By currentBalance = new XPath("//input[@id='currentbalance']");
    private By availableBalance = new XPath("//input[@id='currentbalance']");
    private By averageBalance = new XPath("//input[@id='averagebalance']");
    private By lowBalanceThisStatementCycle = new XPath("//input[@id='lowbalancethisstatementcycle']");
    private By balanceLastStatement = new XPath("//input[@id='balancelaststatement']");
    private By dateLastWithdrawal = new XPath("//input[@id='datelastwithdrawal']");
    private By dateLastDeposit = new XPath("//input[@id='datelastdeposit']");
    private By dateLastStatement = new XPath("//input[@id='datelaststatement']");
    private By numberOfWithdrawalsThisStatementCycle = new XPath("//input[@id='numberofwithdrawalsthisstatementcycle']");
    private By numberOfDepositsThisStatementCycle = new XPath("//input[@id='numberofdepositsthisstatementcycle']");
    private By accruedInterestThisStatementCycle = new XPath("//input[@id='accruedinterestthisstatementcycle']");
    private By amountInterestLastPaid = new XPath("//input[@id='amountinterestlastpaid']");
    private By lastWithdrawalAmount = new XPath("//input[@id='lastwithdrawalamount']");
    private By lastDepositAmount = new XPath("//input[@id='lastdepositamount']");
    private By previousStatementBalance = new XPath("//input[@id='previousstatementbalance']");
    private By previousStatementDate = new XPath("//input[@id='previousstatementdate']");
    private By serviceChargesYTD = new XPath("//input[@id='servicechargesytd']");
    private By aggregateBalanceYTD = new XPath("//input[@id='aggregatebalanceytd']");
    private By specialMailingInstructions = new XPath("//input[@id='specialmailinginstructions']");
    private By taxesWithheldYTD = new XPath("//input[@id='taxeswithheldytd']");
    private By chargesWaivedYTD = new XPath("//input[@id='chargeswaivedytd']");
    private By numberRegDItems = new XPath("//input[@id='numberofregd6limititemsthisstatementcycle']");
    private By monthlyLowBalance = new XPath("//input[@id='monthlylowbalance']");
    private By monthlyNumberOfWithdrawals = new XPath("//input[@id='monthlynumberofwithdrawals']");
    private By interestPaidLastYear = new XPath("//input[@id='interestpaidlastyear']");
    private By oneDayFloat = new XPath("//input[@id='onedayfloat']");
    private By twoDayFloat = new XPath("//input[@id='twodayfloat']");
    private By threeDayFloat = new XPath("//input[@id='threedayfloat']");
    private By fourDayFloat = new XPath("//input[@id='fourdayfloat']");
    private By fiveDayFloat = new XPath("//input[@id='fivedayfloat']");
    private By aggregateColBal = new XPath("//input[@id='aggregatecollectedbalancethisstatementcycle']");
    private By aggrColLstStmt = new XPath("//input[@id='aggregateoverdraftbalancelaststatementcycle']");
    private By ytdAggrColBal = new XPath("//input[@id='aggregatecollectedbalanceytd']");
    private By aggrOdBalance = new XPath("//input[@id='aggregateoverdraftbalancethisstatementcycle']");
    private By aggrOdLstStmt = new XPath("//input[@id='aggregateoverdraftbalancelaststatementcycle']");
    private By aggrColOdBal = new XPath("//input[@id='aggregatecollectedodbalancethisstatementcycle']");
    private By aggrColOdLstStmt = new XPath("//input[@id='aggregatecollectedodbalancelaststatementcycle']");
    private By onlineBankingLogin = new XPath("//input[@id='datelastlogintoonlinebanking']");
    private By totalEarningsForLifeOfAccount = new XPath("//input[@id='totalEarnings']");
    private By totalContributions = new XPath("//input[@id='totalContributions']");

    /**
     * Click switch elements
     */

    @Step("Click 'Transactional Account' switch")
    public String clickTransactionalAccountSwitch() {
        waitForElementVisibility(newAccountSwitch);
        waitForElementClickable(newAccountSwitch);
        return getElementText(newAccountSwitch);
    }

    @Step("Click 'New Account' switch")
    public String clickNewAccountSwitch() {
        waitForElementVisibility(newAccountSwitch);
        waitForElementClickable(newAccountSwitch);
        return getElementText(newAccountSwitch);
    }

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
        String numberOfATMCardsIssuedInputValue = getElementAttributeValue("value", numberOfATMCardsIssuedInput);
        return numberOfATMCardsIssuedInputValue.replaceAll("[^0-9]", "");
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

    @Step("Get 'Interest Paid YTD' value in edit mode")
    public String getInterestPaidYTD() {
        waitForElementVisibility(interestPaidYTD);
        String interestPaidYTDValue = getElementAttributeValue("value", interestPaidYTD);
        return interestPaidYTDValue.replaceAll("[^0-9]", "");
    }

    @Step("Get 'Print Statement Next Updated' value in edit mode")
    public String getPrintStatementNextUpdate() {
        waitForElementVisibility(printStatementNextUpdate);
        return getElementAttributeValue("value", printStatementNextUpdate);
    }

    /**
    * Check if field is disabled in edit mode
     */

    @Step("Check if 'Interest Paid Last Year' field is disabled edit mode")
    public boolean isInterestPaidLastYearDisabledInEditMode() {
        waitForElementVisibility(interestPaidLastYear);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", interestPaidLastYear));
    }

    @Step("Check if 'One Day Float' field is disabled edit mode")
    public boolean isOneDayFloatDisabledInEditMode() {
        waitForElementVisibility(oneDayFloat);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", oneDayFloat));
    }

    @Step("Check if 'Two Day Float' field is disabled edit mode")
    public boolean isTwoDayFloatDisabledInEditMode() {
        waitForElementVisibility(twoDayFloat);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", twoDayFloat));
    }

    @Step("Check if 'Three Day Float' field is disabled edit mode")
    public boolean isThreeDayFloatDisabledInEditMode() {
        waitForElementVisibility(threeDayFloat);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", threeDayFloat));
    }

    @Step("Check if 'Four Day Float' field is disabled edit mode")
    public boolean isFourDayFloatDisabledInEditMode() {
        waitForElementVisibility(fourDayFloat);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", fourDayFloat));
    }

    @Step("Check if 'Five Day Float' field is disabled edit mode")
    public boolean isFiveDayFloatDisabledInEditMode() {
        waitForElementVisibility(fiveDayFloat);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", fiveDayFloat));
    }

    @Step("Check if 'Aggregate col bal' field is disabled edit mode")
    public boolean isAggregateColBalDisabledInEditMode() {
        waitForElementVisibility(aggregateColBal);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", aggregateColBal));
    }

    @Step("Check if 'Aggr col lst stmt' field is disabled edit mode")
    public boolean isAggrColLstStmtDisabledInEditMode() {
        waitForElementVisibility(aggrColLstStmt);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", aggrColLstStmt));
    }

    @Step("Check if 'YTD aggr col bal' field is disabled edit mode")
    public boolean isYtdAggrColBalDisabledInEditMode() {
        waitForElementVisibility(ytdAggrColBal);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", ytdAggrColBal));
    }

    @Step("Check if 'Aggr OD balance' field is disabled edit mode")
    public boolean isAggrOdBalanceDisabledInEditMode() {
        waitForElementVisibility(aggrOdBalance);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", aggrOdBalance));
    }

    @Step("Check if 'Aggr OD lst stmt' field is disabled edit mode")
    public boolean isAggrOdLstStmtDisabledInEditMode() {
        waitForElementVisibility(aggrOdLstStmt);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", aggrOdLstStmt));
    }

    @Step("Check if 'Aggr col OD bal' field is disabled edit mode")
    public boolean isAggrColOdBalDisabledInEditMode() {
        waitForElementVisibility(aggrColOdBal);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", aggrColOdBal));
    }

    @Step("Check if 'Aggr col OD lst stmt' field is disabled edit mode")
    public boolean isAggrColOdLstStmtDisabledInEditMode() {
        waitForElementVisibility(aggrColOdLstStmt);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", aggrColOdLstStmt));
    }

    @Step("Check if 'Online Banking login' field is disabled edit mode")
    public boolean isOnlineBankingLoginDisabledInEditMode() {
        waitForElementVisibility(onlineBankingLogin);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", onlineBankingLogin));
    }

    @Step("Check if 'Total Earnings for Life of Account' field is disabled edit mode")
    public boolean isTotalEarningsForLifeOfAccountDisabledInEditMode() {
        waitForElementVisibility(totalEarningsForLifeOfAccount);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", totalEarningsForLifeOfAccount));
    }

    @Step("Check if 'Total Contributions for Life of Account' field is disabled edit mode")
    public boolean isTotalContributionsDisabledInEditMode() {
        waitForElementVisibility(totalContributions);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", totalContributions));
    }

    @Step("Check if 'Aggregate Balance Year to date' field is disabled edit mode")
    public boolean isAggregateBalanceYTDDisabledInEditMode() {
        waitForElementVisibility(aggregateBalanceYTD);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", aggregateBalanceYTD));
    }

    @Step("Check if 'Special Mailing Instructions' field is disabled edit mode")
    public boolean isSpecialMailingInstructionsDisabledInEditMode() {
        waitForElementVisibility(specialMailingInstructions);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", specialMailingInstructions));
    }

    @Step("Check if 'Taxes Withheld YTD' field is disabled edit mode")
    public boolean isTaxesWithheldYTDDisabledInEditMode() {
        waitForElementVisibility(taxesWithheldYTD);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", taxesWithheldYTD));
    }

    @Step("Check if 'YTD charges waived' field is disabled edit mode")
    public boolean isChargesWaivedYTDDisabledInEditMode() {
        waitForElementVisibility(chargesWaivedYTD);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", chargesWaivedYTD));
    }

    @Step("Check if 'Number Reg D items (6)' field is disabled edit mode")
    public boolean isNumberRegDItemsDisabledInEditMode() {
        waitForElementVisibility(numberRegDItems);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", numberRegDItems));
    }

    @Step("Check if 'Monthly low balance' field is disabled edit mode")
    public boolean isMonthlyLowBalanceDisabledInEditMode() {
        waitForElementVisibility(monthlyLowBalance);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", monthlyLowBalance));
    }

    @Step("Check if 'Monthly number of withdrawals' field is disabled edit mode")
    public boolean isMonthlyNumberOfWithdrawalsDisabledInEditMode() {
        waitForElementVisibility(monthlyNumberOfWithdrawals);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", monthlyNumberOfWithdrawals));
    }

    @Step("Check if 'Accrued Interest this statement cycle' field is disabled edit mode")
    public boolean isAccruedInterestThisStatementCycleDisabledInEditMode() {
        waitForElementVisibility(accruedInterestThisStatementCycle);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", accruedInterestThisStatementCycle));
    }

    @Step("Check if 'Interest Last paid' field is disabled edit mode")
    public boolean isAmountInterestLastPaidDisabledInEditMode() {
        waitForElementVisibility(amountInterestLastPaid);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", amountInterestLastPaid));
    }

    @Step("Check if 'Last withdrawal amount' field is disabled edit mode")
    public boolean isLastWithdrawalAmountDisabledInEditMode() {
        waitForElementVisibility(lastWithdrawalAmount);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", lastWithdrawalAmount));
    }

    @Step("Check if 'Last Deposit Amount' field is disabled edit mode")
    public boolean isLastDepositAmountDisabledInEditMode() {
        waitForElementVisibility(lastDepositAmount);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", lastDepositAmount));
    }

    @Step("Check if 'Previous Statement Balance' field is disabled edit mode")
    public boolean isPreviousStatementBalanceDisabledInEditMode() {
        waitForElementVisibility(previousStatementBalance);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", previousStatementBalance));
    }

    @Step("Check if 'Previous Statement Date' field is disabled edit mode")
    public boolean isPreviousStatementDateDisabledInEditMode() {
        waitForElementVisibility(previousStatementDate);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", previousStatementDate));
    }

    @Step("Check if 'Service charges YTD' field is disabled edit mode")
    public boolean isServiceChargesYTDDisabledInEditMode() {
        waitForElementVisibility(serviceChargesYTD);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", serviceChargesYTD));
    }

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

    @Step("Set 'Print Statement Next Update' option")
    public void setPrintStatementNextUpdate(String printStatementNextUpdateValue) {
        waitForElementVisibility(printStatementNextUpdate);
        waitForElementClickable(printStatementNextUpdate);
        type(printStatementNextUpdateValue, printStatementNextUpdate);
    }

    @Step("Set 'Interest Paid Year to date' option")
    public void setInterestPaidYTD(String interestPaidYTDValue) {
        waitForElementVisibility(interestPaidYTD);
        waitForElementClickable(interestPaidYTD);
        type(interestPaidYTDValue, interestPaidYTD);
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
