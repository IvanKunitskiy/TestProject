package com.nymbus.pages.accounts;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.util.List;

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
    private By interestRate = By.xpath("//input[@id='interestrate']");
    private By earningCreditRate = By.xpath("//input[@id='earningscreditrate']");
    private By federalWHReason = By.xpath("//div[@id='federalwithholdingreason']//span[contains(@class, 'ng-scope')]");
    private By reasonATMChargeWaived = By.xpath("//div[@id='reasonatmchargeswaived']//span[contains(@class, 'ng-scope')]");
    private By odProtectionAcct = By.xpath("//div[@id='overdraftprotectionaccountnumber']//span[contains(@class, 'ng-scope')]");
    private By reasonAutoNSFChgWaived = By.xpath("//div[@id='reasonautonsfchargeswaived']//span[contains(@class, 'ng-scope')]");
    private By reasonDebitCardChargeWaived = By.xpath("//div[@id='reasondebitcardchargeswaived']//span[contains(@class, 'ng-scope')]");
    private By automaticOverdraftStatus = By.xpath("//div[@id='automaticoverdraftstatus']//span[contains(@class, 'ng-scope')]");
    private By reasonAutoOdChgWaived = By.xpath("//div[@id='reasonautoodchargeswaived']//span[contains(@class, 'ng-scope')]");
    private By whenSurchargesRefunded = By.xpath("//div[@id='whensurchargesrefunded']//span[contains(@class, 'ng-scope')]");
    private By printStatementNextUpdate = By.xpath("//input[@id='printstatementnextupdate']");
    private By interestPaidYTD = By.xpath("//input[@id='interestpaidytd']");
    private By federalWHPercentInput = By.xpath("//input[@id='federalwithholdingpercent']");
    private By numberOfATMCardsIssuedInput = By.xpath("//input[@id='numberofatmcardissued']");
    private By userDefinedFieldInput_1 = By.xpath("//input[@id='userdefinedfield1']");
    private By userDefinedFieldInput_2 = By.xpath("//input[@id='userdefinedfield2']");
    private By userDefinedFieldInput_3 = By.xpath("//input[@id='userdefinedfield3']");
    private By userDefinedFieldInput_4 = By.xpath("//input[@id='userdefinedfield4']");
    private By numberOfDebitCardsIssuedInput = By.xpath("//input[@id='numberofdebitcardsissued']");
    private By cashCollDaysBeforeChgInput = By.xpath("//input[@id='cashcollectionnumberofdaysbeforeinterestcharge']");
    private By cashCollInterestRateInput = By.xpath("//input[@id='cashcollectioninterestrate']");
    private By cashCollInterestChgInput = By.xpath("//input[@id='cashcollectioninterestchargesperstatementcycle']");
    private By positivePayInput = By.xpath("//input[@id='positivepaycustomer']");
    private By cashColFloatInput = By.xpath("//input[@id='cashcollectionfloat']");
    private By earningCreditRateInput = By.xpath("//input[@id='earningscreditrate']");
    private By cashCollFloatInput = By.xpath("//input[@id='cashcollectionfloat']");
    private By automaticOverdraftLimitInput = By.xpath("//input[@id='automaticoverdraftlimit']");
    private By interestFrequency = By.xpath("//div[@id='interestfrequencycode']//span[contains(@class, 'ng-scope')]");
    private By correspondingAccount = By.xpath("//div[@id='correspondingaccountid']//span[contains(@class, 'ng-scope')]");
    private By newAccountSwitch = By.xpath("//dn-switch[@id='newaccount']");
    private By iraDistributionFrequency = By.xpath("//div[@id='iradistributionfrequency']//span[contains(@class, 'ng-scope')]");
    private By iraDistributionCode = By.xpath("//div[@id='iradistributioncode']//span[contains(@class, 'ng-scope')]");
    private By iraDistributionAmount = By.xpath("//input[@id='iradistributionamount']");
    private By iraDateNextIRADistribution = By.xpath("//input[@id='datenextiradistribution']");
    private By applyInterestTo = By.xpath("//div[@id='codetoapplyinterestto']//span[contains(@class, 'ng-scope')]");
    private By interestType = By.xpath("//div[@id='interesttype']//span[contains(@class, 'ng-scope')]");

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
    private By currentBalance = By.xpath("//input[@id='currentbalance']");
    private By availableBalance = By.xpath("//input[@id='currentbalance']");
    private By averageBalance = By.xpath("//input[@id='averagebalance']");
    private By lowBalanceThisStatementCycle = By.xpath("//input[@id='lowbalancethisstatementcycle']");
    private By balanceLastStatement = By.xpath("//input[@id='balancelaststatement']");
    private By dateLastWithdrawal = By.xpath("//input[@id='datelastwithdrawal']");
    private By dateLastDeposit = By.xpath("//input[@id='datelastdeposit']");
    private By dateLastStatement = By.xpath("//input[@id='datelaststatement']");
    private By numberOfWithdrawalsThisStatementCycle = By.xpath("//input[@id='numberofwithdrawalsthisstatementcycle']");
    private By numberOfDepositsThisStatementCycle = By.xpath("//input[@id='numberofdepositsthisstatementcycle']");
    private By accruedInterestThisStatementCycle = By.xpath("//input[@id='accruedinterestthisstatementcycle']");
    private By amountInterestLastPaid = By.xpath("//input[@id='amountinterestlastpaid']");
    private By lastWithdrawalAmount = By.xpath("//input[@id='lastwithdrawalamount']");
    private By lastDepositAmount = By.xpath("//input[@id='lastdepositamount']");
    private By previousStatementBalance = By.xpath("//input[@id='previousstatementbalance']");
    private By previousStatementDate = By.xpath("//input[@id='previousstatementdate']");
    private By serviceChargesYTD = By.xpath("//input[@id='servicechargesytd']");
    private By aggregateBalanceYTD = By.xpath("//input[@id='aggregatebalanceytd']");
    private By specialMailingInstructions = By.xpath("//input[@id='specialmailinginstructions']");
    private By taxesWithheldYTD = By.xpath("//input[@id='taxeswithheldytd']");
    private By chargesWaivedYTD = By.xpath("//input[@id='chargeswaivedytd']");
    private By numberRegDItems = By.xpath("//input[@id='numberofregd6limititemsthisstatementcycle']");
    private By monthlyLowBalance = By.xpath("//input[@id='monthlylowbalance']");
    private By monthlyNumberOfWithdrawals = By.xpath("//input[@id='monthlynumberofwithdrawals']");
    private By interestPaidLastYear = By.xpath("//input[@id='interestpaidlastyear']");
    private By oneDayFloat = By.xpath("//input[@id='onedayfloat']");
    private By twoDayFloat = By.xpath("//input[@id='twodayfloat']");
    private By threeDayFloat = By.xpath("//input[@id='threedayfloat']");
    private By fourDayFloat = By.xpath("//input[@id='fourdayfloat']");
    private By fiveDayFloat = By.xpath("//input[@id='fivedayfloat']");
    private By aggregateColBal = By.xpath("//input[@id='aggregatecollectedbalancethisstatementcycle']");
    private By aggrColLstStmt = By.xpath("//input[@id='aggregateoverdraftbalancelaststatementcycle']");
    private By ytdAggrColBal = By.xpath("//input[@id='aggregatecollectedbalanceytd']");
    private By aggrOdBalance = By.xpath("//input[@id='aggregateoverdraftbalancethisstatementcycle']");
    private By aggrOdLstStmt = By.xpath("//input[@id='aggregateoverdraftbalancelaststatementcycle']");
    private By aggrColOdBal = By.xpath("//input[@id='aggregatecollectedodbalancethisstatementcycle']");
    private By aggrColOdLstStmt = By.xpath("//input[@id='aggregatecollectedodbalancelaststatementcycle']");
    private By onlineBankingLogin = By.xpath("//input[@id='datelastlogintoonlinebanking']");
    private By totalEarningsForLifeOfAccount = By.xpath("//input[@id='totalEarnings']");
    private By totalContributions = By.xpath("//input[@id='totalContributions']");
    private By dateLastActivityContact = By.xpath("//input[@id='datelastactivity']");

    private By iraDistributionAccountNumber = By.xpath("//div[@id='iradistributionaccountid']");
    private By bankRoutingNumberForIRADistr = By.xpath("//input[@id='bankroutingnumberforiradistr']");
    private By bankAccountNumberForIRADistr = By.xpath("//input[@id='bankaccountnumberforiradistr']");
    private By amountLastIRADistribution = By.xpath("//input[@id='amountlastiradistribution']");
    private By dateLastIRADistribution = By.xpath("//input[@id='datelastiradistribution']");
    private By iraDistributionsYTD = By.xpath("//input[@id='iradistributionsytd']");

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
    @Step("Get 'Apply Interest To' value in edit mode")
    public String getApplyInterestTo() {
        waitForElementVisibility(applyInterestTo);
        return getElementText(applyInterestTo);
    }
    @Step("Get 'Interest Type' value in edit mode")
    public String getInterestType() {
        waitForElementVisibility(interestType);
        return getElementText(interestType);
    }

    @Step("Get 'IRA Distribution Frequency' value in edit mode")
    public String getIraDistributionFrequencyInEditMode() {
        waitForElementVisibility(iraDistributionFrequency);
        return getElementText(iraDistributionFrequency);
    }

    @Step("Get 'IRA Distribution Code' value in edit mode")
    public String getIraDistributionCodeInEditMode() {
        waitForElementVisibility(iraDistributionCode);
        return getElementText(iraDistributionCode);
    }

    @Step("Get 'IRA distribution amount' value in edit mode")
    public String getIraDistributionAmountInEditMode() {
        waitForElementVisibility(iraDistributionAmount);
        return getElementAttributeValue("value", iraDistributionAmount).replaceAll("[^0-9]", "");
    }

    @Step("Get 'Date next IRA distribution' value in edit mode")
    public String getDateNextIRADistributionInEditMode() {
        waitForElementVisibility(iraDateNextIRADistribution);
        return getElementAttributeValue("value", iraDateNextIRADistribution);
    }

    @Step("Get 'Corresponding Account' value in edit mode")
    public String getCorrespondingAccount() {
        waitForElementVisibility(correspondingAccount);
        return getElementText(correspondingAccount);
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

    @Step("Check if 'IRA Distribution Account Number' field is disabled edit mode")
    public boolean isIRADistributionAccountNumberDisabledInEditMode() {
        waitForElementVisibility(iraDistributionAccountNumber);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", iraDistributionAccountNumber));
    }

    @Step("Check if 'Bank Routing Number for IRA Distr' field is disabled edit mode")
    public boolean isBankRoutingNumberForIRADistrDisabledInEditMode() {
        waitForElementVisibility(bankRoutingNumberForIRADistr);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", bankRoutingNumberForIRADistr));
    }

    @Step("Check if 'Bank Account Number For IRA Distr' field is disabled edit mode")
    public boolean isBankAccountNumberForIRADistrDisabledInEditMode() {
        waitForElementVisibility(bankAccountNumberForIRADistr);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", bankAccountNumberForIRADistr));
    }

    @Step("Check if 'Amount last IRA distribution' field is disabled edit mode")
    public boolean isAmountLastIRADistributionDisabledInEditMode() {
        waitForElementVisibility(amountLastIRADistribution);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", amountLastIRADistribution));
    }

    @Step("Check if 'Date last IRA distribution' field is disabled edit mode")
    public boolean isDateLastIRADistributionDisabledInEditMode() {
        waitForElementVisibility(dateLastIRADistribution);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", dateLastIRADistribution));
    }

    @Step("Check if 'IRA distributions YTD' field is disabled edit mode")
    public boolean isIRADistributionsYTDDisabledInEditMode() {
        waitForElementVisibility(iraDistributionsYTD);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", iraDistributionsYTD));
    }

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

    @Step("Check if 'Date Last Activity/Contact' field is disabled edit mode")
    public boolean isDateLastActivityContactDisabledInEditMode() {
        waitForElementVisibility(dateLastActivityContact);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", dateLastActivityContact));
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
        waitForElementVisibility(interestRate);
        waitForElementClickable(interestRate);
        type(interestRateValue, interestRate);
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
