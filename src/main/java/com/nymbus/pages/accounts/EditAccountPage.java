package com.nymbus.pages.accounts;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.nymbus.core.base.PageTools;
import com.nymbus.core.utils.SelenideTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.util.List;
import java.util.stream.Collectors;

public class EditAccountPage extends PageTools {

    private By accountHoldersAndSignersFormTitle = By.xpath("//label[contains(text(), 'Account Holders and Signers')]");
    private By accountTitle = By.xpath("//input[@id='accounttitlemailinginstructions']");
    private By bankBranch = By.xpath("//div[@id='bankbranch']//span[contains(@class, 'ng-scope')]");
    private By product = By.xpath("//div[@id='accountclasstype']//span[contains(@class, 'ng-scope')]");
    private By dateOpened = By.xpath("//input[@id='dateopened']");
    private By dateLastAccess = By.xpath("//input[@id='datelastaccess']");
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
    private By interestFrequency = By.xpath("//div[@id='interestfrequency']//span[contains(@class, 'ng-scope')]");
    private By interestFrequencyCode = By.xpath("//div[@id='interestfrequencycode']//span[contains(@class, 'ng-scope')]");
    private By correspondingAccount = By.xpath("//div[@id='correspondingaccountid']//span[contains(@class, 'ng-scope')]");
    private By newAccountSwitch = By.xpath("//*[@id='newaccount']");
    private By transactionalAccountSwitch = By.xpath("//*[@id='transactionalaccount']");
    private By iraDistributionFrequency = By.xpath("//div[@id='iradistributionfrequency']//span[contains(@class, 'ng-scope')]");
    private By iraDistributionCode = By.xpath("//div[@id='iradistributioncode']//span[contains(@class, 'ng-scope')]");
    private By iraDistributionAmount = By.xpath("//input[@id='iradistributionamount']");
    private By iraDateNextIRADistribution = By.xpath("//input[@id='datenextiradistribution']");
    private By applyInterestTo = By.xpath("//div[@id='codetoapplyinterestto']//span[contains(@class, 'ng-scope')]");
    private By interestType = By.xpath("//div[@id='interesttype']//span[contains(@class, 'ng-scope')]");
    private By boxSize = By.xpath("//div[@id='boxsize']//span[contains(@class, 'ng-scope')]");
    private By rentalAmount = By.xpath("//input[@id='rentalamount']");
    private By discountReason = By.xpath("//div[@id='discountreason']//span[contains(@class, 'ng-scope')]");
    private By discountPeriods = By.xpath("//input[@id='discountperiods']");
    private By dateNextBilling = By.xpath("//input[@id='datenextbiling']");
    private By dateLastPaid = By.xpath("//input[@id='datelastpaid']");
    private By amountLastPaid = By.xpath("//input[@id='amountlastpaid']");
    private By mailCode = By.xpath("//div[@id='mailingcode']//span[contains(@class, 'ng-scope')]");
    private By bankAccountNumberInterestOnCD = By.xpath("//input[@id='bankaccountnumberinterestoncd']");
    private By bankRoutingNumberInterestOnCD = By.xpath("//input[@id='bankroutingnumberinterestoncd']");
    private By applySeasonalAddress = By.xpath("//*[@id='useseasonaladdress']");
    private By enablePositivePay = By.xpath("//*[@id='positivepay']");
    private By bankruptcyJudgement = By.xpath("//div[@id='bankruptcyjudgementcode']//span[contains(@class, 'ng-scope')]");
    private By exemptFromRegCC = By.xpath("//*[@id='exemptfromregcc']");
    private By callClassCodeNotValid = By.xpath("//div[@data-test-id='field-callclasscode']/a[contains(@uib-tooltip-html, 'is no longer a valid value')]");
    private By adjustableRate = By.xpath("//*[@id='adjustablerate_checkbox']");
    private By adjustableRateValue = By.xpath("//*[@id='adjustablerate_checkbox']/div/div/span[2]");
    private By adjustableRateYesValue = By.xpath("//*[@id='adjustablerate_checkbox']/div/div/span[contains(text(), 'YES')]");
    private By saveAccountButton = By.xpath("//button[@data-test-id='action-save']");
    private By changePaymentWithRateChange = By.xpath("//dn-switch[@id='changepaymentwithratechange']");
    private By changePaymentWithRateChangeValue = By.xpath("//dn-switch[@id='changepaymentwithratechange']/div/div/span[2]");

    private By federalWHReasonSelectorButton = By.xpath("//div[@id='federalwithholdingreason']");//label[contains(text(), 'Account Holders and Signers')]
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

    private By currentOfficerSelectorButton = By.xpath("//div[@id='officer']//span[contains(@class, 'select2-arrow')]");
    private By currentOfficerList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private By currentOfficerSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private By callClassCodeSelectorButton = By.xpath("//div[@data-test-id='field-callclasscode']");
    private By callClassCodeList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private By callClassCodeSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private By bankBranchSelectorButton = By.xpath("//div[@id='bankbranch']//span[contains(@class, 'select2-arrow')]");
    private By bankBranchSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private By bankBranchList = By.xpath("//li[contains(@role, 'option')]/div/span");

    private By itemInDropdown = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private By correspondingAccountSelectorButton = By.xpath("//div[@id='correspondingaccountid']//span[contains(@class, 'select2-arrow')]");
    private By correspondingAccountList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private By correspondingAccountSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private By discountReasonSelectorButton = By.xpath("//div[@id='discountreason']");
    private By discountReasonList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private By discountReasonSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private By mailCodeSelectorButton = By.xpath("//div[@id='mailingcode']//span[contains(@class, 'select2-arrow')]");
    private By mailCodeList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private By mailCodeSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private By applyInterestToSelectorButton = By.xpath("//div[@id='codetoapplyinterestto']");
    private By applyInterestToList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private By applyInterestToSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private By chargeOrAnalyzeSelectorButton = By.xpath("//div[@id='chargeoranalyze']");
    private By chargeOrAnalyzeList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private By chargeOrAnalyzeSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private By bankruptcyJudgementSelectorButton = By.xpath("//div[@id='bankruptcyjudgementcode']");
    private By bankruptcyJudgementList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private By bankruptcyJudgementSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private By statementCycleSelectorButton = By.xpath("//div[@id='statementcycle']");
    private By statementCycleList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private By statementCycleSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private final By daysBaseYearBaseSelectorButton = By.xpath("//div[@id='daybaseforinterestrate']");
    private final By daysBaseYearBaseList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private final By daysBaseYearBaseSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[text()='%s']]");

    private final By rateChangeFrequencySelectorButton = By.xpath("//div[@id='variableratechangefrequency']");
    private final By rateChangeFrequencyList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private final By rateChangeFrequencySelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[text()='%s']]");

    private final By paymentChangeFrequencySelectorButton = By.xpath("//div[@id='armchangefrequency']");
    private final By paymentChangeFrequencyList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private final By paymentChangeFrequencySelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[text()='%s']]");

    private final By rateIndexSelectorButton = By.xpath("//div[@id='armrateindex']");
    private final By rateIndexList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private final By rateIndexSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[text()='%s']]");

    private final By rateRoundingMethodSelectorButton = By.xpath("//div[@id='armroundingindicator']");
    private final By rateRoundingMethodList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private final By rateRoundingMethodSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[text()='%s']]");

    private final By expandAllButton = By.xpath("//button//span[contains(text(), 'Expand All')]");
    private final By collapseAllButton = By.xpath("//button//span[contains(text(), 'Collapse All')]");

    /**
     * Disabled fields in edit mode
     */
    private By productTypeField = By.xpath("//div[@id='accounttype']");
    private By productField = By.xpath("//div[@id='accountclasstype']");
    private By accountNumberField = By.xpath("//input[@id='accountnumber']");
    private By accountTypeField = By.xpath("//div[@id='customertype']");
    private By accountTypeFieldValue = By.xpath("//div[@id='customertype']//span[@ng-transclude]//span");
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
    private By originalBalance = By.xpath("//input[@id='originalbalance']");
    private By amountLastInterestPaid = By.xpath("//input[@id='amountlastinterestpaid']");
    private By dateLastInterestPaid = By.xpath("//input[@id='datelastinterestpaid']");
    private By termTypeMonths = By.xpath("//div[@id='termtype']");
    private By dateNextInterest = By.xpath("//input[@id='datenextinterestpayment']");
    private By nextInterestPaymentAmount = By.xpath("//input[@id='nextinterestpaymentamount']");
    private By maturityDate = By.xpath("//input[@id='maturitydate']");
    private By accruedInterest = By.xpath("//input[@id='accruedinterest']");
    private By penaltyAmountYTD = By.xpath("//input[@id='penaltyamountytd']");
    private By dailyInterestAccrual = By.xpath("//input[@id='dailyinterestaccrual']");
    private By balanceAtRenewal = By.xpath("//input[@id='balanceatrenewal']");
    private By dateOfRenewal = By.xpath("//input[@id='dateofrenewal']");
    private By interestRateAtRenewal = By.xpath("//input[@id='interestrateatrenewal']");
    private By renewalAmount = By.xpath("//input[@id='renewalamount']");
    private By balanceAtEndOfYear = By.xpath("//input[@id='balanceatendofyear']");
    private By accruedInterestAtEndOfYear = By.xpath("//input[@id='accruedinterestatendofyear']");
    private By iraPlanNumber = By.xpath("//input[@id='iraplannumber']");
    private By printInterestNoticeOverride = By.xpath("//input[@id='printinterestnoticeoverride']");
    private By totalEarnings = By.xpath("//input[@id='totalEarnings']");
    private By interstPaidYTD = By.xpath("//input[@id='interestpaidlastyear']");
    private By iraDistributionAccountID = By.xpath("//div[@id='iradistributionaccountid']");
    private By numberOfDebitsThisStatementCycle = By.xpath("//input[@data-test-id='field-numberofwithdrawalsthisstatementcycle']");
    private By dateLastDebit = By.xpath("//input[@data-test-id='field-datelastwithdrawal']");
    private By ydtAverageBalance = By.xpath("//input[@data-test-id='field-averagebalanceytd']");
    private By ytdChargesWaived = By.xpath("//input[@data-test-id='field-chargeswaivedytd']");
    private By dateOfFirstDeposit = By.xpath("//input[@data-test-id='field-datefirstdeposit']");
    private By interestLastPaid = By.xpath("//input[@data-test-id='field-amountinterestlastpaid']");
    private By birthDate = By.xpath("//input[@data-test-id='field-dateofbirth']");
    private By dateDeceased = By.xpath("//input[@data-test-id='field-datedeceased']");
    private By nextRateChangeDate = By.xpath("//input[@id='armdatenextirchange']");
    private final By rateChangeLeadDays = By.xpath("//input[@id='ratechangeleaddays']");
    private final By nextPaymentChangeDate = By.xpath("//input[@id='armdatenextpaymentchange']");
    private final By paymentChangeLeadDays = By.xpath("//input[@id='paymentchangeleaddays']");
    private final By rateMargin = By.xpath("//input[@id='interestratebase']");
    private final By minRate = By.xpath("//input[@id='armfloor']");
    private final By maxRate = By.xpath("//input[@id='armceiling']");
    private final By maxRateChangeUpDown = By.xpath("//input[@id='armperiodiccap']");
    private final By maxRateLifetimeCap = By.xpath("//input[@id='armlifetimecap']");
    private final By rateRoundingFactor = By.xpath("//*[@data-test-id='field-armroundingfactor']");
    private final By rateRoundingFactorInput = By.xpath("//*[@data-test-id='field-armroundingfactor']//ul//li//span[contains(text(), '%s')]");
    private final By originalInterestRate = By.xpath("//input[@id='armoriginalinterestrate']");

    /**
     * Labels
     */
    private By productFieldLabel = By.xpath("//label[contains(text(), 'Product')]");
    private By productTypeLabel = By.xpath("//label[contains(text(), 'Product Type')]");
    private By currentBalanceLabel = By.xpath("//label[contains(text(), 'Current Balance')]");
    private By totalEarningsLabel = By.xpath("//label[contains(text(), 'Total Earnings')]");
    private By accountTypeFieldLabel = By.xpath("//label[contains(text(), 'Account Type')]");
    private By accountNumberFieldLabel = By.xpath("//label[contains(text(), 'Account Number')]");
    private By accountTitleLabel = By.xpath("//label[contains(text(), 'Account Title')]");
    private By accountHoldersAndSignersFormTitleLabel = By.xpath("//label[contains(text(), 'Account Holders and Signers')]");
    private By accountStatusFieldLabel = By.xpath("//label[contains(text(), 'Account Status')]");
    private By originatingOfficerFieldLabel = By.xpath("//label[contains(text(), 'Originating Officer')]");
    private By currentOfficerLabel = By.xpath("//label[contains(text(), 'Current Officer')]");
    private By bankBranchLabel = By.xpath("//label[contains(text(), 'Bank Branch')]");
    private By mailCodeLabel = By.xpath("//label[contains(text(), 'Mail Code')]");
    private By applySeasonalAddressLabel = By.xpath("//label[contains(text(), 'Apply Seasonal Address')]");
    private By specialMailingInstructionsLabel = By.xpath("//label[contains(text(), 'Special Mailing Instructions')]");
    private By dateOpenedFieldLabel = By.xpath("//label[contains(text(), 'Date Opened')]");
    private By dateClosedFieldLabel = By.xpath("//label[contains(text(), 'Date Closed')]");
    private By averageBalanceLabel = By.xpath("//label[contains(text(), 'Average Balance')]");
    private By interestRateLabel = By.xpath("//label[contains(text(), 'Interest Rate')]");
    private By collectedBalanceLabel = By.xpath("//label[contains(text(), 'Collected Balance')]");
    private By accruedInterestThisStatementCycleLabel = By.xpath("//label[contains(text(), 'Accrued Interest this statement cycle')]");
    private By lowBalanceThisStatementCycleLabel = By.xpath("//tr[@data-test-id='field-lowbalancethisstatementcycle']//td//label");
    private By balanceLastStatementLabel = By.xpath("//label[contains(text(), 'Balance Last Statement')]");
    private By ytdAverageBalanceLabel = By.xpath("//label[contains(text(), 'YTD average balance')]");
    private By dateLastDebitLabel = By.xpath("//label[contains(text(), 'Date Last Debit')]");
    private By dateLastCheckLabel = By.xpath("//label[contains(text(), 'Date Last Check')]");
    private By dateLastDepositLabel = By.xpath("//label[contains(text(), 'Date Last Deposit')]");
    private By annualPercentageYieldLabel = By.xpath("//label[contains(text(), 'Annual Percentage Yield')]");
    private By interestPaidYearToDateLabel = By.xpath("//label[contains(text(), 'Interest Paid Year to date')]");
    private By avgColBalLstStmtLabel = By.xpath("//label[contains(text(), 'Avg col bal lst stmt')]");
    private By dateLastStatementLabel = By.xpath("//label[contains(text(), 'Date Last Statement')]");
    private By averageColBalanceLabel = By.xpath("//label[contains(text(), 'Average col balance')]");
    private By ytdAvgColBalanceLabel = By.xpath("//label[contains(text(), 'YTD avg col balance')]");
    private By previousStatementDateLabel = By.xpath("//label[contains(text(), 'Previous Statement Date')]");
    private By previousStatementBalanceLabel = By.xpath("//label[contains(text(), 'Previous Statement Balance')]");
    private By interestPaidLastLabel = By.xpath("//label[contains(text(), 'Interest Paid Last')]");
    private By interestLastPaidLabel = By.xpath("//label[contains(text(), 'Interest Last paid')]");
    private By interestFrequencyLabel = By.xpath("//label[contains(text(), 'Interest Frequency')]");
    private By correspondingAccountLastLabel = By.xpath("//label[contains(text(), 'Corresponding Account')]");
    private By monthlyLowBalanceLabel = By.xpath("//label[contains(text(), 'Monthly low balance')]");
    private By monthlyNumberOfWithdrawalsLabel = By.xpath("//label[contains(text(), 'Monthly number of withdrawals')]");
    private By aggregateBalanceYearToDateLabel = By.xpath("//label[contains(text(), 'Aggregate Balance Year to date')]");
    private By aggregateColBalLabel = By.xpath("//label[contains(text(), 'Aggregate col bal')]");
    private By aggrColLstStmtLabel = By.xpath("//label[contains(text(), 'Aggr col lst stmt')]");
    private By ytdAggrColBalLabel = By.xpath("//label[contains(text(), 'YTD aggr col bal')]");
    private By numberOfChecksThisStatementCycleLabel = By.xpath("//label[contains(text(), 'Number Of Checks This Statement Cycle')]");
    private By dateLastActivityContactLabel = By.xpath("//label[contains(text(), 'Date Last Activity/Contact')]");
    private By numberOfDepositsThisStatementCycleLabel = By.xpath("//tr[@data-test-id='field-numberofdepositsthisstatementcycle']//td//label");
    private By lastDebitAmountLabel = By.xpath("//label[contains(text(), 'Last Debit Amount')]");
    private By lastCheckAmountLabel = By.xpath("//label[contains(text(), 'Last Check Amount')]");
    private By lastDepositAmountLabel = By.xpath("//label[contains(text(), 'Last Deposit Amount')]");
    private By numberRegDitemsLabel = By.xpath("//label[contains(text(), 'Number Reg D items (6)')]");
    private By regDViolationsLastMosLabel = By.xpath("//label[contains(text(), 'Reg D violations last 12 mos')]");
    private By ytdChargesWaivedLabel = By.xpath("//tr[@data-test-id='field-chargeswaivedytd']//td//label");
    private By statementCycleLabel = By.xpath("//tr[@data-test-id='field-statementcycle']//td//label");
    private By chargeOrAnalyzeLabel = By.xpath("//label[contains(text(), 'Charge or analyze')]");
    private By accountAnalysisLabel = By.xpath("//label[contains(text(), 'Account analysis')]");
    private By transitItemsDepositedLabel = By.xpath("//label[contains(text(), 'Transit items deposited')]");
    private By onUsItemsDepositedLabel = By.xpath("//label[contains(text(), 'ON-US items deposited')]");
    private By serviceChargesYtdLabel = By.xpath("//label[contains(text(), 'Service charges YTD')]");
    private By automaticOverdraftStatusLabel = By.xpath("//label[contains(text(), 'Automatic Overdraft Status')]");
    private By automaticOverdraftLimitLabel = By.xpath("//label[contains(text(), 'Automatic Overdraft Limit')]");
    private By dbcOdpOptInOutStatusLabel = By.xpath("//label[contains(text(), 'DBC ODP Opt In/Out Status')]");
    private By dbcOdpOptInOutStatusDateLabel = By.xpath("//label[contains(text(), 'DBC ODP Opt In/Out Status Date')]");
    private By yearToDateDaysOverdraftLabel = By.xpath("//label[contains(text(), 'Year-to-date days overdraft')]");
    private By daysOverdraftLastYearLabel = By.xpath("//label[contains(text(), 'Days overdraft last year')]");
    private By daysConsecutiveOverdraftLabel = By.xpath("//label[contains(text(), 'Days consecutive overdraft')]");
    private By overdraftChargedOffLabel = By.xpath("//label[contains(text(), 'Overdraft Charged Off')]");
    private By dateLastOverdraftLabel = By.xpath("//label[contains(text(), 'Date Last Overdraft')]");
    private By timesOverdrawn6MonthsLabel = By.xpath("//label[contains(text(), 'Times Overdrawn-6 Months')]");
    private By times$5000OverdrawnMonthsLabel = By.xpath("//label[contains(text(), 'Times $5000 Overdrawn-6 Months')]");
    private By timesInsufficientYTDLabel = By.xpath("//label[contains(text(), 'Times insufficient YTD')]");
    private By aggrOdBalanceLabel = By.xpath("//label[contains(text(), 'Aggr OD balance')]");
    private By aggrColOdBalLabel = By.xpath("//label[contains(text(), 'Aggr col OD bal')]");
    private By aggrOdLstStmtLabel = By.xpath("//label[contains(text(), 'Aggr OD lst stmt')]");
    private By aggrColOdlstStmtLabel = By.xpath("//label[contains(text(), 'Aggr col OD lst stmt')]");
    private By itemsInsufficientYTDLabel = By.xpath("//label[contains(text(), 'Items insufficient YTD')]");
    private By timesInsufficientLYRLabel = By.xpath("//label[contains(text(), 'Times insufficient LYR')]");
    private By nsfFeePaidThisCycleLabel = By.xpath("//label[contains(text(), 'NSF fee, paid this cycle')]");
    private By nsfFeeRtrnThisCycleLabel = By.xpath("//label[contains(text(), 'NSF fee, rtrn this cycle')]");
    private By nsfFeePaidYTDLabel = By.xpath("//label[contains(text(), 'NSF fee, paid YTD')]");
    private By nsfFeeRtrnYTDLabel = By.xpath("//label[contains(text(), 'NSF fee, rtrn YTD')]");
    private By nsfFeePaidLastYearLabel = By.xpath("//label[contains(text(), 'NSF fee, paid last year')]");
    private By nsfFeeRtrnLastYearLabel = By.xpath("//label[contains(text(), 'NSF fee, rtrn last year')]");
    private By numberRefundedStmtCycleLabel = By.xpath("//label[contains(text(), 'Number refunded stmt cycle')]");
    private By amountRefundedStmtCycleLabel = By.xpath("//label[contains(text(), 'Amount refunded stmt cycle')]");
    private By federalWhReasonLabel = By.xpath("//label[contains(text(), 'Federal W/H reason')]");
    private By federalWhPercentLabel = By.xpath("//label[contains(text(), 'Federal W/H percent')]");
    private By taxesWithheldYtdLabel = By.xpath("//label[contains(text(), 'Taxes Withheld YTD')]");
    private By userDefinedField1Label = By.xpath("//label[contains(text(), 'User Defined Field 1')]");
    private By userDefinedField2Label = By.xpath("//label[contains(text(), 'User Defined Field 2')]");
    private By userDefinedField3Label = By.xpath("//label[contains(text(), 'User Defined Field 3')]");
    private By userDefinedField4Label = By.xpath("//label[contains(text(), 'User Defined Field 4')]");
    private By printStatementNextUpdateLabel = By.xpath("//label[contains(text(), 'Print Statement Next Update')]");
    private By callClassCodeLabel = By.xpath("//label[contains(text(), 'Call Class Code')]");
    private By day1FloatLabel = By.xpath("//label[contains(text(), '1 day float')]");
    private By day2FloatLabel = By.xpath("//label[contains(text(), '2 day float')]");
    private By day3FloatLabel = By.xpath("//label[contains(text(), '3 day float')]");
    private By day4FloatLabel = By.xpath("//label[contains(text(), '4 day float')]");
    private By day5FloatLabel = By.xpath("//label[contains(text(), '5 day float')]");
    private By numberOfDebitCardsIssuedLabel = By.xpath("//label[contains(text(), 'Number of Debit Cards issued')]");
    private By reasonDebitCardChargeWaivedLabel = By.xpath("//label[contains(text(), 'Reason Debit Card Charge Waived')]");
    private By numberRefundedYtdLabel = By.xpath("//label[contains(text(), 'Number refunded YTD')]");
    private By amountRefundedYtdLabel = By.xpath("//label[contains(text(), 'Amount refunded YTD')]");
    private By numberRefundedLyrLabel = By.xpath("//label[contains(text(), 'Number refunded LYR')]");
    private By amountRefundedLyrlabel = By.xpath("//label[contains(text(), 'Amount refunded LYR')]");
    private By bankruptcyJudgementLabel = By.xpath("//label[contains(text(), 'Bankruptcy/Judgement')]");
    private By bankruptcyJudgementDateLabel = By.xpath("//label[contains(text(), 'Bankruptcy/Judgement Date')]");
    private By exemptFromRegCcLabel = By.xpath("//label[contains(text(), 'Exempt from Reg CC')]");
    private By newAccountLabel = By.xpath("//label[contains(text(), 'New Account')]");
    private By transactionalAccountLabel = By.xpath("//label[contains(text(), 'Transactional Account')]");
    private By totalEarningsForLifeOfAccountLabel = By.xpath("//tr[@data-test-id='field-totalEarnings']//td//label");
    private By verifyAchFundsLabel = By.xpath("//label[contains(text(), 'Verify ACH funds')]");
    private By waiveServiceChargesLabel = By.xpath("//label[contains(text(), 'Waive Service Charges')]");
    private By enablePositivePayLabel = By.xpath("//label[contains(text(), 'Enable Positive Pay')]");
    private By dateOfFirstDepositLabel = By.xpath("//label[contains(text(), 'Date Of First Deposit')]");
    private By iraDistributionFrequencyLabel = By.xpath("//label[contains(text(), 'IRA Distribution Frequency')]");
    private By iraDistributionCodeLabel = By.xpath("//label[contains(text(), 'IRA Distribution Code')]");
    private By iraDistributionAccountNumberLabel = By.xpath("//label[contains(text(), 'IRA Distribution Account Number')]");
    private By iraDistributionAmountLabel = By.xpath("//label[contains(text(), 'IRA distribution amount')]");
    private By amountLastIRADistributionLabel = By.xpath("//label[contains(text(), 'Amount last IRA distribution')]");
    private By dateLastIRADistributionLabel = By.xpath("//label[contains(text(), 'Date last IRA distribution')]");
    private By dateNextIRADistributionLabel = By.xpath("//label[contains(text(), 'Date next IRA distribution')]");
    private By rmdDateLabel = By.xpath("//label[contains(text(), 'RMD Date')]");
    private By rmdAmountLabel = By.xpath("//label[contains(text(), 'RMD Amount')]");
    private By iraDistributionsYTDLabel = By.xpath("//label[contains(text(), 'IRA distributions YTD')]");
    private By dateOfBirthLabel = By.xpath("//label[contains(text(), 'Date of Birth')]");
    private By dateDeceasedLabel = By.xpath("//label[contains(text(), 'Date Deceased')]");
    private By totalContributionsForLifeOfAccountLabel = By.xpath("//tr[@data-test-id='field-totalContributions']//label[contains(text(), 'Total Contributions for Life of Account')]");

    private By numberOfDebitsThisStatementCycleLabel = By.xpath("//tr[@data-test-id='field-numberofwithdrawalsthisstatementcycle']//td//label");

    private By termTypeLabel = By.xpath("//label[contains(text(), 'Term Type')]");
    private By termInMonthsOrDaysLabel = By.xpath("//label[contains(text(), 'Term In Months Or Days')]");
    private By autoRenewableLabel = By.xpath("//label[contains(text(), 'Auto Renewable')]");
    private By maturityDateLabel = By.xpath("//label[contains(text(), 'Maturity Date')]");
    private By penaltyAmountYearToDateLabel = By.xpath("//label[contains(text(), 'Penalty Amount Year-to-date')]");
    private By balanceAtRenewalLabel = By.xpath("//label[contains(text(), 'Balance At Renewal')]");
    private By dateOfRenewalLabel = By.xpath("//label[contains(text(), 'Date of renewal')]");
    private By interestRateAtRenewalLabel = By.xpath("//label[contains(text(), 'Interest rate at renewal')]");
    private By renewalAmountLabel = By.xpath("//label[contains(text(), 'Renewal amount')]");
    private By rateIndexLabel = By.xpath("//label[contains(text(), 'Rate Index')]");

    private By accruedInterestLabel = By.xpath("//label[contains(text(), 'Accrued Interest')]");
    private By dailyInterestAccrualLabel = By.xpath("//label[contains(text(), 'Daily Interest Accrual')]");
    private By interestTypeLabel = By.xpath("//label[contains(text(), 'Interest Type')]");
    private By applyInterestToLabel = By.xpath("//label[contains(text(), 'Apply Interest To')]");
    private By amountLastInterestPaidLabel = By.xpath("//label[contains(text(), 'Amount Last Interest Paid')]");
    private By dateLastInterestPaidLabel = By.xpath("//label[contains(text(), 'Date Last Interest Paid')]");
    private By dateNextInterestLabel = By.xpath("//label[contains(text(), 'Date next interest')]");
    private By nextInterestPaymentAmountLabel = By.xpath("//label[contains(text(), 'Next Interest Payment Amount')]");
    private By balanceAtEndOfYearLabel = By.xpath("//label[contains(text(), 'Balance at end of year')]");
    private By accruedInterestAtEndOfYearLabel = By.xpath("//label[contains(text(), 'Accrued interest at end of year')]");
    private By interestPaidLastYearLabel = By.xpath("//label[contains(text(), 'Interest Paid Last Year')]");
    private By printInterestNoticeOverrideLabel = By.xpath("//label[contains(text(), 'Print interest notice override')]");
    private By iraPlanNumberLabel = By.xpath("//label[contains(text(), 'IRA plan number')]");

    /**
     * Groups
     */
    private By balanceAndInterestGroup = By.xpath("//h4[contains(@class, 'panel-title')]//span[contains(text(), 'Balance and Interest')]");
    private By transactionsTitle = By.xpath("//h4[contains(@class, 'panel-title')]//span[contains(text(), 'Transactions')]");
    private By transactionsGroup = By.xpath("//span[contains(text(), 'Transactions')]//ancestor::div[contains(@class, 'panel-heading')]/following-sibling::div");
    private By overdraftGroup = By.xpath("//h4[contains(@class, 'panel-title')]//span[contains(text(), 'Overdraft')]");
    private By miscTitle = By.xpath("//h4[contains(@class, 'panel-title')]//span[contains(text(), 'Misc')]");
    private By miscGroup = By.xpath("//span[contains(text(), 'Misc')]//ancestor::div[contains(@class, 'panel-heading')]/following-sibling::div");
    private By distrbutionTitle = By.xpath("//h4[contains(@class, 'panel-title')]//span[contains(text(), 'Distribution')]");
    private By distrbutionGroup = By.xpath("//span[contains(text(), 'Misc')]//ancestor::div[contains(@class, 'panel-heading')]/following-sibling::div");
    private By distributionAndMiscTitle = By.xpath("//h4[contains(@class, 'panel-title')]//span[contains(text(), 'Distribution and Misc')]");
    private By distributionAndMiscGroup = By.xpath("//span[contains(text(), 'Misc')]//ancestor::div[contains(@class, 'panel-heading')]/following-sibling::div");
    private By termGroup = By.xpath("//h4[contains(@class, 'panel-title')]//span[contains(text(), 'Term')]");

    private By balanceAndInterestGroupOpened = By.xpath("//div[contains(@class, 'panel-open')]//h4[contains(@class, 'panel-title')]//span[contains(text(), 'Balance and Interest')]");
    private By transactionsGroupOpened = By.xpath("//div[contains(@class, 'panel-open')]//h4[contains(@class, 'panel-title')]//span[contains(text(), 'Transactions')]");
    private By overdraftGroupOpened = By.xpath("//div[contains(@class, 'panel-open')]//h4[contains(@class, 'panel-title')]//span[contains(text(), 'Overdraft')]");
    private By miscGroupOpened = By.xpath("//div[contains(@class, 'panel-open')]//h4[contains(@class, 'panel-title')]//span[contains(text(), 'Misc')]");
    private By distributionGroupOpened = By.xpath("//div[contains(@class, 'panel-open')]//h4[contains(@class, 'panel-title')]//span[contains(text(), 'Distribution')]");
    private By termGroupOpened = By.xpath("//div[contains(@class, 'panel-open')]//h4[contains(@class, 'panel-title')]//span[contains(text(), 'Term')]");
    private By distributionAndMiscGroupOpened = By.xpath("//div[contains(@class, 'panel-open')]//h4[contains(@class, 'panel-title')]//span[contains(text(), 'Distribution and Misc')]");

    private By acccountTypeDropdownItem = By.xpath("//div[@id='customertype']//li[contains(@id, 'ui-select-choices-row')]//span[contains(text(), '%s')]");

    /**
     * Click switch elements
     */

    @Step("Click 'Transactional Account' switch")
    public void clickTransactionalAccountSwitch() {
        waitForElementVisibility(transactionalAccountSwitch);
        waitForElementClickable(transactionalAccountSwitch);
        scrollToPlaceElementInCenter(transactionalAccountSwitch);
        click(transactionalAccountSwitch);
    }

    @Step("Get 'Transactional Account' switch value")
    public String getTransactionalAccountSwitchValue() {
        waitForElementVisibility(transactionalAccountSwitch);
        waitForElementClickable(transactionalAccountSwitch);
        scrollToPlaceElementInCenter(transactionalAccountSwitch);
        return getElementText(transactionalAccountSwitch);
    }

    @Step("Click 'New Account' switch")
    public void clickNewAccountSwitch() {
        waitForElementVisibility(newAccountSwitch);
        waitForElementClickable(newAccountSwitch);
        scrollToPlaceElementInCenter(newAccountSwitch);
        click(newAccountSwitch);
    }

    @Step("Get 'New Account' switch value")
    public String getNewAccountSwitchValue() {
        waitForElementVisibility(newAccountSwitch);
        waitForElementClickable(newAccountSwitch);
        scrollToPlaceElementInCenter(newAccountSwitch);
        return getElementText(newAccountSwitch);
    }

    @Step("Click 'Exempt From Reg CC' switch")
    public void clickExemptFromRegCCSwitch() {
        waitForElementVisibility(exemptFromRegCC);
        waitForElementClickable(exemptFromRegCC);
        scrollToPlaceElementInCenter(exemptFromRegCC);
        click(exemptFromRegCC);
    }

    @Step("Get 'Exempt From Reg CC' switch value")
    public String getExemptFromRegCCSwitchValue() {
        waitForElementVisibility(exemptFromRegCC);
        waitForElementClickable(exemptFromRegCC);
        scrollToPlaceElementInCenter(exemptFromRegCC);
        return getElementText(exemptFromRegCC);
    }

    @Step("Click 'Change Payment with Rate Change' switch")
    public void clickChangePaymentWithRateChangeSwitch() {
        scrollToPlaceElementInCenter(changePaymentWithRateChange);
        click(changePaymentWithRateChange);
    }

    @Step("Click 'Apply Seasonal Address' switch")
    public void clickApplySeasonalAddressSwitch() {
        click(applySeasonalAddress);
    }

    @Step("Get 'Apply Seasonal Address' value")
    public String getApplySeasonalAddressSwitchValue() {
        waitForElementVisibility(applySeasonalAddress);
        waitForElementClickable(applySeasonalAddress);
        scrollToPlaceElementInCenter(applySeasonalAddress);
        return getElementText(applySeasonalAddress);
    }

    @Step("Click 'Enable Positive Pay' switch")
    public void clickEnablePositivePaySwitch() {
        click(enablePositivePay);
    }

    @Step("Get 'Enable Positive Pay' value")
    public String getEnablePositivePaySwitchValue() {
        waitForElementVisibility(enablePositivePay);
        waitForElementClickable(enablePositivePay);
        scrollToPlaceElementInCenter(enablePositivePay);
        return getElementText(enablePositivePay).trim();
    }


    @Step("Check if 'Adjustable Rate' switcher switched to 'YES'")
    public boolean isAdjustableRateYesValuePicked() {
        return isElementVisible(adjustableRateYesValue);
    }

    /**
     * Get values in edit mode
     */

    @Step("Get 'Box Size' value in edit mode")
    public String getBoxSize() {
        waitForElementVisibility(boxSize);
        return getElementText(boxSize);
    }

    @Step("Get 'Rental Amount' value in edit mode")
    public String getRentalAmount() {
        waitForElementVisibility(rentalAmount);
        return getElementAttributeValue("value", rentalAmount).replaceAll("[^0-9.,]", "");
    }

    @Step("Get 'Discount Reason' value in edit mode")
    public String getDiscountReason() {
        waitForElementVisibility(discountReason);
        return getElementText(discountReason);
    }

    @Step("Get 'Discount Periods' value in edit mode")
    public String getDiscountPeriods() {
        waitForElementVisibility(discountPeriods);
        return getElementAttributeValue("value", discountPeriods).replaceAll("[^0-9]", "");
    }

    @Step("Get 'Transactional Account' value in edit mode")
    public String getTransactionalAccountInEditMode() {
        waitForElementVisibility(transactionalAccountSwitch);
        return getElementAttributeValue("textContent", transactionalAccountSwitch).replaceAll("[^a-zA-Z]", "").toUpperCase();
    }

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

    @Step("Get 'Date Of First Deposit' value in edit mode")
    public String getDateOfFirstDeposit() {
        waitForElementVisibility(dateOfFirstDeposit);
        return getElementAttributeValue("value", dateOfFirstDeposit);
    }

    @Step("Get 'Birth Date' value in edit mode")
    public String getBirthDate() {
        waitForElementVisibility(birthDate);
        return getElementAttributeValue("value", birthDate);
    }

    @Step("Get 'Date Deceased' value in edit mode")
    public String getDateDeceased() {
        waitForElementVisibility(dateDeceased);
        return getElementAttributeValue("value", dateDeceased);
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

    @Step("Get 'Interest Frequency Code' value in edit mode")
    public String getInterestFrequencyCode() {
        waitForElementVisibility(interestFrequencyCode);
        return getElementText(interestFrequencyCode);
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

    @Step("Get 'Mail Code' value in edit mode")
    public String getMailCode() {
        waitForElementVisibility(mailCode);
        return getElementText(mailCode);
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

    @Step("Get 'Bankruptcy Judgement' value in edit mode")
    public String getBankruptcyJudgement() {
        waitForElementVisibility(bankruptcyJudgement);
        return getElementText(bankruptcyJudgement);
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

    private final By pendingVariablePayment = By.xpath("//input[@id='armpendingpaymentamt']");
    private final By lastPaymentChangeDate = By.xpath("//input[@id='armdatelastpaymentchange']");
    private final By priorPaymentAmount = By.xpath("//input[@id='armpriorpaymentamount']");

    @Step("Check if 'Last Payment Change Date' field is disabled edit mode")
    public boolean isLastPaymentChangeDateDisabled() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", lastPaymentChangeDate));
    }

    @Step("Check if 'Pending Variable Payment Amount' field is disabled edit mode")
    public boolean isPendingVariablePaymentAmountDisabled() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", pendingVariablePayment));
    }

    @Step("Check if 'Prior Variable Payment Amount' field is disabled edit mode")
    public boolean isPriorVariablePaymentAmountDisabled() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", priorPaymentAmount));
    }

    @Step("Check if 'IRA Distribution Account Number' field is disabled edit mode")
    public boolean isIraDistributionAccountNumberDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", iraDistributionAccountNumber));
    }

    @Step("Check if 'Interest Paid YTD' field is disabled edit mode")
    public boolean isInterestPaidYTDDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", interestPaidYTD));
    }

    @Step("Check if 'Date Next Billing' field is disabled edit mode")
    public boolean isDateNextBillingDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", interestPaidYTD));
    }

    @Step("Check if 'Date Last Paid' field is disabled edit mode")
    public boolean isDateLastPaidDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", dateLastPaid));
    }

    @Step("Check if 'Amount Last Paid' field is disabled edit mode")
    public boolean isAmountLastPaidDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", amountLastPaid));
    }

    @Step("Check if 'Balance At End Of Year' field is disabled edit mode")
    public boolean isBalanceAtEndOfYearDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", balanceAtEndOfYear));
    }

    @Step("Check if 'Accrued Interest At End Of Year' field is disabled edit mode")
    public boolean isAccruedInterestAtEndOfYearDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", accruedInterestAtEndOfYear));
    }

    @Step("Check if 'IRA Plan Number' field is disabled edit mode")
    public boolean isIraPlanNumberDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", iraPlanNumber));
    }

    @Step("Check if 'Print Interest Notice Override' field is disabled edit mode")
    public boolean isPrintInterestNoticeOverrideDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", printInterestNoticeOverride));
    }

    @Step("Check if 'Total Earnings' field is disabled edit mode")
    public boolean isTotalEarningsDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", totalEarnings));
    }

    @Step("Check if 'Interest Rate At Renewal' field is disabled edit mode")
    public boolean isInterestRateAtRenewalDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", interestRateAtRenewal));
    }

    @Step("Check if 'Renewal Amount' field is disabled edit mode")
    public boolean isRenewalAmountDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", renewalAmount));
    }

    @Step("Check if 'Penalty Amount YTD' field is disabled edit mode")
    public boolean isPenaltyAmountYTDDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", dateNextInterest));
    }

    @Step("Check if 'Daily Interest Accrual' field is disabled edit mode")
    public boolean isDailyInterestAccrualDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", dailyInterestAccrual));
    }

    @Step("Check if 'Balance At Renewal' field is disabled edit mode")
    public boolean isBalanceAtRenewalDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", balanceAtRenewal));
    }

    @Step("Check if 'Date Of Renewal' field is disabled edit mode")
    public boolean isDateOfRenewalDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", dateOfRenewal));
    }

    @Step("Check if 'Date Next Interest' field is disabled edit mode")
    public boolean isDateNextInterestDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", dateNextInterest));
    }

    @Step("Check if 'Next Interest Payment Amount' field is disabled edit mode")
    public boolean isNextInterestPaymentAmountDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", nextInterestPaymentAmount));
    }

    @Step("Check if 'Maturity Date' field is disabled edit mode")
    public boolean isMaturityDateDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", maturityDate));
    }

    @Step("Check if 'Accrued Interest' field is disabled edit mode")
    public boolean isAccruedInterestDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", accruedInterest));
    }

    @Step("Check if 'Original Balance' field is disabled edit mode")
    public boolean isOriginalBalanceDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", originalBalance));
    }

    @Step("Check if 'Amount Last Interest Paid' field is disabled edit mode")
    public boolean isAmountLastInterestPaidDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", amountLastInterestPaid));
    }

    @Step("Check if 'Date Last Interest Paid' field is disabled edit mode")
    public boolean isDateLastInterestPaidDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", dateLastInterestPaid));
    }

    @Step("Check if 'Term Type Months' field is disabled edit mode")
    public boolean isTermTypeMonthsDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", termTypeMonths));
    }

    @Step("Check if 'IRA Distribution Account Number' field is disabled edit mode")
    public boolean isIRADistributionAccountNumberDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", iraDistributionAccountNumber));
    }

    @Step("Check if 'IRA Distribution Account Number' field is visible edit mode")
    public boolean isIRADistributionAccountNumberVisibleInEditMode() {
        return isElementVisible(iraDistributionAccountNumber);
    }

    @Step("Check if 'Bank Routing Number for IRA Distr' field is disabled edit mode")
    public boolean isBankRoutingNumberForIRADistrDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", bankRoutingNumberForIRADistr));
    }

    @Step("Check if 'Bank Account Number For IRA Distr' field is disabled edit mode")
    public boolean isBankAccountNumberForIRADistrDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", bankAccountNumberForIRADistr));
    }

    @Step("Check if 'Amount last IRA distribution' field is disabled edit mode")
    public boolean isAmountLastIRADistributionDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", amountLastIRADistribution));
    }

    @Step("Check if 'Date last IRA distribution' field is disabled edit mode")
    public boolean isDateLastIRADistributionDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", dateLastIRADistribution));
    }

    @Step("Check if 'IRA distributions YTD' field is disabled edit mode")
    public boolean isIRADistributionsYTDDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", iraDistributionsYTD));
    }

    @Step("Check if 'Interest Paid Last Year' field is disabled edit mode")
    public boolean isInterestPaidLastYearDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", interestPaidLastYear));
    }

    @Step("Check if 'One Day Float' field is disabled edit mode")
    public boolean isOneDayFloatDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", oneDayFloat));
    }

    @Step("Check if 'Two Day Float' field is disabled edit mode")
    public boolean isTwoDayFloatDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", twoDayFloat));
    }

    @Step("Check if 'Three Day Float' field is disabled edit mode")
    public boolean isThreeDayFloatDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", threeDayFloat));
    }

    @Step("Check if 'Four Day Float' field is disabled edit mode")
    public boolean isFourDayFloatDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", fourDayFloat));
    }

    @Step("Check if 'Five Day Float' field is disabled edit mode")
    public boolean isFiveDayFloatDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", fiveDayFloat));
    }

    @Step("Check if 'Aggregate col bal' field is disabled edit mode")
    public boolean isAggregateColBalDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", aggregateColBal));
    }

    @Step("Check if 'Aggr col lst stmt' field is disabled edit mode")
    public boolean isAggrColLstStmtDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", aggrColLstStmt));
    }

    @Step("Check if 'YTD aggr col bal' field is disabled edit mode")
    public boolean isYtdAggrColBalDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", ytdAggrColBal));
    }

    @Step("Check if 'Aggr OD balance' field is disabled edit mode")
    public boolean isAggrOdBalanceDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", aggrOdBalance));
    }

    @Step("Check if 'Date Last Activity/Contact' field is disabled edit mode")
    public boolean isDateLastActivityContactDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", dateLastActivityContact));
    }

    @Step("Check if 'Aggr OD lst stmt' field is disabled edit mode")
    public boolean isAggrOdLstStmtDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", aggrOdLstStmt));
    }

    @Step("Check if 'Aggr col OD bal' field is disabled edit mode")
    public boolean isAggrColOdBalDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", aggrColOdBal));
    }

    @Step("Check if 'Aggr col OD lst stmt' field is disabled edit mode")
    public boolean isAggrColOdLstStmtDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", aggrColOdLstStmt));
    }

    @Step("Check if 'Online Banking login' field is disabled edit mode")
    public boolean isOnlineBankingLoginDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", onlineBankingLogin));
    }

    @Step("Check if 'Total Earnings for Life of Account' field is disabled edit mode")
    public boolean isTotalEarningsForLifeOfAccountDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", totalEarningsForLifeOfAccount));
    }

    @Step("Check if 'Total Contributions for Life of Account' field is disabled edit mode")
    public boolean isTotalContributionsDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", totalContributions));
    }

    @Step("Check if 'Aggregate Balance Year to date' field is disabled edit mode")
    public boolean isAggregateBalanceYTDDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", aggregateBalanceYTD));
    }

    @Step("Check if 'Special Mailing Instructions' field is disabled edit mode")
    public boolean isSpecialMailingInstructionsDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", specialMailingInstructions));
    }

    @Step("Check if 'Taxes Withheld YTD' field is disabled edit mode")
    public boolean isTaxesWithheldYTDDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", taxesWithheldYTD));
    }

    @Step("Check if 'YTD charges waived' field is disabled edit mode")
    public boolean isChargesWaivedYTDDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", chargesWaivedYTD));
    }

    @Step("Check if 'Number Reg D items (6)' field is disabled edit mode")
    public boolean isNumberRegDItemsDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", numberRegDItems));
    }

    @Step("Check if 'Monthly low balance' field is disabled edit mode")
    public boolean isMonthlyLowBalanceDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", monthlyLowBalance));
    }

    @Step("Check if 'Monthly number of withdrawals' field is disabled edit mode")
    public boolean isMonthlyNumberOfWithdrawalsDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", monthlyNumberOfWithdrawals));
    }

    @Step("Check if 'Accrued Interest this statement cycle' field is disabled edit mode")
    public boolean isAccruedInterestThisStatementCycleDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", accruedInterestThisStatementCycle));
    }

    @Step("Check if 'Interest Last paid' field is disabled edit mode")
    public boolean isAmountInterestLastPaidDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", amountInterestLastPaid));
    }

    @Step("Check if 'Last withdrawal amount' field is disabled edit mode")
    public boolean isLastWithdrawalAmountDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", lastWithdrawalAmount));
    }

    @Step("Check if 'Last Deposit Amount' field is disabled edit mode")
    public boolean isLastDepositAmountDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", lastDepositAmount));
    }

    @Step("Check if 'Previous Statement Balance' field is disabled edit mode")
    public boolean isPreviousStatementBalanceDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", previousStatementBalance));
    }

    @Step("Check if 'Previous Statement Date' field is disabled edit mode")
    public boolean isPreviousStatementDateDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", previousStatementDate));
    }

    @Step("Check if 'Service charges YTD' field is disabled edit mode")
    public boolean isServiceChargesYTDDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", serviceChargesYTD));
    }

    @Step("Check if 'Current Balance' field is disabled edit mode")
    public boolean isCurrentBalanceDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", currentBalance));
    }

    @Step("Check if 'Total Earnings' field is disabled edit mode")
    public boolean isAvailableBalanceDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", availableBalance));
    }

    @Step("Check if 'Average Balance' field is disabled edit mode")
    public boolean isAverageBalanceDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", averageBalance));
    }

    @Step("Check if 'Low Balance This Statement Cycle' field is disabled edit mode")
    public boolean isLowBalanceThisStatementCycleDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", lowBalanceThisStatementCycle));
    }

    @Step("Check if 'Balance Last Statement' field is disabled edit mode")
    public boolean isBalanceLastStatementDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", balanceLastStatement));
    }

    @Step("Check if 'Date Last Withdrawal' field is disabled edit mode")
    public boolean isDateLastWithdrawalDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", dateLastWithdrawal));
    }

    @Step("Check if 'Date Last Deposit' field is disabled edit mode")
    public boolean isDateLastDepositDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", dateLastDeposit));
    }

    @Step("Check if 'Date Last Statement' field is disabled edit mode")
    public boolean isDateLastStatementDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", dateLastStatement));
    }

    @Step("Check if 'Number Of Withdrawals This Statement Cycle' field is disabled edit mode")
    public boolean isNumberOfWithdrawalsThisStatementCycleDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", numberOfWithdrawalsThisStatementCycle));
    }

    @Step("Check if 'Number Of Deposits This Statement Cycle' field is disabled edit mode")
    public boolean isNumberOfDepositsThisStatementCycleDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", numberOfDepositsThisStatementCycle));
    }

    @Step("Check if 'Total Earnings' field is disabled edit mode")
    public boolean isTotalEarningsFieldDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", totalEarningsField));
    }

    @Step("Check if 'Automatic Overdraft Limit Field' field is disabled edit mode")
    public boolean isAutomaticOverdraftLimitFieldDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", automaticOverdraftLimitField));
    }

    @Step("Check if 'Last Debit Amount' field is disabled edit mode")
    public boolean isLastDebitAmountFieldDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", lastDebitAmountField));
    }

    @Step("Check if 'Times $5000 Overdrawn-6 Months' field is disabled edit mode")
    public boolean isDaysOverdraftAboveLimitFieldDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", daysOverdraftAboveLimitField));
    }

    @Step("Check if 'Times Overdrawn-6 Months' field is disabled edit mode")
    public boolean isDaysOverdraftFieldDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", daysOverdraftField));
    }

    @Step("Check if 'Annual Percentage Yield' field is disabled edit mode")
    public boolean isAnnualPercentageYieldFieldDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", annualPercentageYieldField));
    }

    @Step("Check if 'Date Opened' field is disabled edit mode")
    public boolean isDateClosedFieldDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", dateClosedField));
    }

    @Step("Check if 'Date Opened' field is disabled edit mode")
    public boolean isDateOpenedFieldDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", dateOpenedField));
    }

    @Step("Check if 'Account Status' field is disabled edit mode")
    public boolean isAccountStatusFieldDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", accountStatusField));
    }

    @Step("Check if 'Originating Officer' field is disabled edit mode")
    public boolean isOriginatingOfficerFieldDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", originatingOfficerField));
    }

    @Step("Check if 'Account Type' field is disabled edit mode")
    public boolean isAccountTypeFieldDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", accountTypeField));
    }

    /*Check for fields's labels visability*/

    @Step("Check if 'Average Balance' is visible")
    public boolean isAverageBalanceFieldVisible() {
        return isElementVisible(averageBalanceLabel);
    }

    @Step("Check if 'Interest Rate' is visible")
    public boolean isInterestRateFieldVisible() {
        return isElementVisible(interestRateLabel);
    }

    @Step("Check if 'Collected Balance' is visible")
    public boolean isCollectedBalanceFieldVisible() {
        return isElementVisible(collectedBalanceLabel);
    }

    @Step("Check if 'Accrued Interest this statement cycle' is visible")
    public boolean isAccruedInterestThisStatementCycleFieldVisible() {
        return isElementVisible(accruedInterestThisStatementCycleLabel);
    }

    @Step("Check if 'Low Balance This Statement Cycle' is visible")
    public boolean isLowBalanceThisFieldVisible() {
        return isElementVisible(lowBalanceThisStatementCycleLabel);
    }

    @Step("Check if 'Balance Last Statement' is visible")
    public boolean isBalanceLastStatementFieldVisible() {
        return isElementVisible(balanceLastStatementLabel);
    }

    @Step("Check if 'YTD average balance' is visible")
    public boolean isYTDaveragebalanceFieldVisible() {
        return isElementVisible(ytdAverageBalanceLabel);
    }

    @Step("Check if 'Date Last Debit' is visible")
    public boolean isDateLastDebitFieldVisible() {
        return isElementVisible(dateLastDebitLabel);
    }

    @Step("Check if 'Date Last Check' is visible")
    public boolean isDateLastCheckFieldVisible() {
        return isElementVisible(dateLastCheckLabel);
    }

    @Step("Check if 'Date Last Deposit' is visible")
    public boolean isDateLastDepositFieldVisible() {
        return isElementVisible(dateLastDepositLabel);
    }

    @Step("Check if 'Annual Percentage Yield' is visible")
    public boolean isAnnualPercentageYieldFieldVisible() {
        return isElementVisible(annualPercentageYieldLabel);
    }

    @Step("Check if 'Interest Paid Year to date' is visible")
    public boolean isInterestPaidYearFieldVisible() {
        return isElementVisible(interestPaidYearToDateLabel);
    }

    @Step("Check if 'Avg col bal lst stmt' is visible")
    public boolean isAvgColBalLstStmtFieldVisible() {
        return isElementVisible(avgColBalLstStmtLabel);
    }

    @Step("Check if 'Date Last Statement' is visible")
    public boolean isDateLastStatementFieldVisible() {
        return isElementVisible(dateLastStatementLabel);
    }

    @Step("Check if 'Average col balance' is visible")
    public boolean isAverageColBalanceFieldVisible() {
        return isElementVisible(averageColBalanceLabel);
    }

    @Step("Check if 'YTD avg col balance' is visible")
    public boolean isYtdAvgColFieldVisible() {
        return isElementVisible(ytdAvgColBalanceLabel);
    }

    @Step("Check if 'Previous Statement Date' is visible")
    public boolean isPreviousStatementDateFieldVisible() {
        return isElementVisible(previousStatementDateLabel);
    }

    @Step("Check if 'Previous Statement Balance' is visible")
    public boolean isPreviousStatementBalanceFieldVisible() {
        return isElementVisible(previousStatementBalanceLabel);
    }

    @Step("Check if 'Interest Paid Last ' is visible")
    public boolean isInterestPaidLastFieldVisible() {
        return isElementVisible(interestPaidLastLabel);
    }

    @Step("Check if 'Interest Last paid ' is visible")
    public boolean isInterestLastPaidFieldVisible() {
        return isElementVisible(interestLastPaidLabel);
    }

    @Step("Check if 'Interest Frequency' is visible")
    public boolean isInterestFrequencyFieldVisible() {
        return isElementVisible(interestFrequencyLabel);
    }

    @Step("Check if 'Corresponding Account' is visible")
    public boolean isCorrespondingAccountFieldVisible() {
        return isElementVisible(correspondingAccountLastLabel);
    }

    @Step("Check if 'Monthly low balance' is visible")
    public boolean isMonthlyLowBalanceFieldVisible() {
        return isElementVisible(monthlyLowBalanceLabel);
    }

    @Step("Check if 'Monthly number of withdrawals' is visible")
    public boolean isMonthlyNumberOfWithdrawalsFieldVisible() {
        return isElementVisible(monthlyNumberOfWithdrawalsLabel);
    }

    @Step("Check if 'Aggregate Balance Year to date' is visible")
    public boolean isAggregateBalanceYearToDateFieldVisible() {
        return isElementVisible(aggregateBalanceYearToDateLabel);
    }

    @Step("Check if 'Aggregate col bal' is visible")
    public boolean isAggregateColBalFieldVisible() {
        return isElementVisible(aggregateColBalLabel);
    }

    @Step("Check if 'Aggr col lst stmt' is visible")
    public boolean isAggrColLstStmtFieldVisible() {
        return isElementVisible(aggrColLstStmtLabel);
    }

    @Step("Check if 'YTD aggr col bal' is visible")
    public boolean isYTDAggrColBalFieldVisible() {
        return isElementVisible(ytdAggrColBalLabel);
    }

    @Step("Check if 'Current Balance' is visible")
    public boolean isCurrentBalanceFieldVisible() {
        return isElementVisible(currentBalanceLabel);
    }

    @Step("Check if 'Total Earnings' is visible")
    public boolean isAvailableBalanceFieldVisible() {
        return isElementVisible(totalEarningsLabel);
    }

    @Step("Check if 'Account Type' field is visible")
    public boolean isAccountTypeFieldVisible() {
        return isElementVisible(accountTypeFieldLabel);
    }

    @Step("Check if 'Account Number' field is visible")
    public boolean isAccountNumberFieldVisible() {
        return isElementVisible(accountNumberFieldLabel);
    }

    @Step("Check if 'Account Number' field is disabled edit mode")
    public boolean isAccountNumberFieldDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", accountNumberField));
    }

    @Step("Check if 'Account Title' field is visible")
    public boolean isAccountTitleFieldVisible() {
        return isElementVisible(accountTitleLabel);
    }

    @Step("Check if 'Product' field is visible")
    public boolean isProductFieldVisible() {
        return isElementVisible(productFieldLabel);
    }

    @Step("Check if 'Product' field is disabled edit mode")
    public boolean isProductFieldDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", productField));
    }

    @Step("Check if 'Product type' field is visible")
    public boolean isProductTypeFieldVisible() {
        return isElementVisible(productTypeLabel);
    }

    @Step("Check if 'Number Of Checks This Statement Cycle' field is visible")
    public boolean isNumberOfChecksThisStatementCycleFieldVisible() {
        return isElementVisible(numberOfChecksThisStatementCycleLabel);
    }

    @Step("Check if 'Date Last Activity/Contact' field is visible")
    public boolean isDateLastActivityContactFieldVisible() {
        return isElementVisible(dateLastActivityContactLabel);
    }


    @Step("Check if 'Number Of Deposits This Statement Cycle' field is visible")
    public boolean isNumberOfDepositsThisStatementCycleFieldVisible() {
        return isElementVisible(numberOfDepositsThisStatementCycleLabel);
    }

    @Step("Check if 'Low Balance This Statement Cycle' field is visible")
    public boolean isLowBalanceThisStatementCycleFieldVisible() {
        return isElementVisible(lowBalanceThisStatementCycleLabel);
    }

    @Step("Check if 'Last Debit Amount' field is visible")
    public boolean isLastDebitAmountFieldVisible() {
        return isElementVisible(lastDebitAmountLabel);
    }

    @Step("Check if 'Last Check Amount' field is visible")
    public boolean isLastCheckAmountFieldVisible() {
        return isElementVisible(lastCheckAmountLabel);
    }

    @Step("Check if 'Last Deposit Amount' field is visible")
    public boolean isLastDepositAmountFieldVisible() {
        return isElementVisible(lastDepositAmountLabel);
    }

    @Step("Check if 'Number Reg D items (6)' field is visible")
    public boolean isNumberRegDItemsFieldVisible() {
        return isElementVisible(numberRegDitemsLabel);
    }

    @Step("Check if 'Reg D violations last 12 mos' field is visible")
    public boolean isRegDViolationsLastMosFieldVisible() {
        return isElementVisible(regDViolationsLastMosLabel);
    }

    @Step("Check if 'YTD charges waived' field is visible")
    public boolean isYtdChargesWaivedFieldVisible() {
        return isElementVisible(ytdChargesWaivedLabel);
    }

    @Step("Check if 'Statement Cycle' field is visible")
    public boolean isStatementCycleFieldVisible() {
        return isElementVisible(statementCycleLabel);
    }

    @Step("Check if 'Charge or analyze' field is visible")
    public boolean isChargeOrAnalyzeFieldVisible() {
        return isElementVisible(chargeOrAnalyzeLabel);
    }

    @Step("Check if 'Account analysis' field is visible")
    public boolean isAccountAnalysisFieldVisible() {
        return isElementVisible(accountAnalysisLabel);
    }

    @Step("Check if 'Transit items deposited' field is visible")
    public boolean isTransitItemsDepositedFieldVisible() {
        return isElementVisible(transitItemsDepositedLabel);
    }

    @Step("Check if 'ON-US items deposited' field is visible")
    public boolean isONusItemsDepositedFieldVisible() {
        return isElementVisible(onUsItemsDepositedLabel);
    }

    @Step("Check if 'Service charges YTD' field is visible")
    public boolean isServiceChargesYtdFieldVisible() {
        return isElementVisible(serviceChargesYtdLabel);
    }

    @Step("Check if 'Automatic Overdraft Status'")
    public boolean isAutomaticOverdraftStatusFieldVisible() {
        return isElementVisible(automaticOverdraftStatusLabel);
    }

    @Step("Check if 'Automatic Overdraft Limit' field is visible")
    public boolean isAutomaticOverdraftLimitFieldVisible() {
        return isElementVisible(automaticOverdraftLimitLabel);
    }

    @Step("Check if 'DBC ODP Opt In/Out Status' field is visible")
    public boolean isDBCODPOptInOutStatusFieldVisible() {
        return isElementVisible(dbcOdpOptInOutStatusLabel);
    }

    @Step("Check if 'DBC ODP Opt In/Out Status Date' field is visible")
    public boolean isDBCODPOptInOutStatusDateFieldVisible() {
        return isElementVisible(dbcOdpOptInOutStatusDateLabel);
    }

    @Step("Check if 'Year-to-date days overdraft' field is visible")
    public boolean isYearToDateDaysOverdraftFieldVisible() {
        return isElementVisible(yearToDateDaysOverdraftLabel);
    }

    @Step("Check if 'Days overdraft last year' field is visible")
    public boolean isDaysOverdraftLastYearFieldVisible() {
        return isElementVisible(daysOverdraftLastYearLabel);
    }

    @Step("Check if 'Days consecutive overdraft' field is visible")
    public boolean isDaysConsecutiveOverdraftFieldVisible() {
        return isElementVisible(daysConsecutiveOverdraftLabel);
    }

    @Step("Check if 'Overdraft Charged Off' field is visible")
    public boolean isOverdraftChargedOffFieldVisible() {
        return isElementVisible(overdraftChargedOffLabel);
    }

    @Step("Check if 'Date Last Overdraft' field is visible")
    public boolean isDateLastOverdraftFieldVisible() {
        return isElementVisible(dateLastOverdraftLabel);
    }

    @Step("Check if 'Times Overdrawn-6 Months' field is visible")
    public boolean isTimesOverdrawnMonthsFieldVisible() {
        return isElementVisible(timesOverdrawn6MonthsLabel);
    }

    @Step("Check if 'Times $5000 Overdrawn-6 Months' field is visible")
    public boolean isTimes$5000OverdrawnMonthsFieldVisible() {
        return isElementVisible(times$5000OverdrawnMonthsLabel);
    }

    @Step("Check if 'Times insufficient YTD' field is visible")
    public boolean isTimesInsufficientYTDFieldVisible() {
        return isElementVisible(timesInsufficientYTDLabel);
    }

    @Step("Check if 'Aggr OD balance' field is visible")
    public boolean isAggrOdBalanceFieldVisible() {
        return isElementVisible(aggrOdBalanceLabel);
    }

    @Step("Check if 'Aggr col OD bal' field is visible")
    public boolean isAggrColOdBalFieldVisible() {
        return isElementVisible(aggrColOdBalLabel);
    }

    @Step("Check if 'Aggr OD lst stmt' field is visible")
    public boolean isAggrOdLstStmtFieldVisible() {
        return isElementVisible(aggrOdLstStmtLabel);
    }

    @Step("Check if 'Aggr col OD lst stmt' field is visible")
    public boolean isAggrColOdLstStmtFieldVisible() {
        return isElementVisible(aggrColOdlstStmtLabel);
    }

    @Step("Check if 'Items insufficient YTD' field is visible")
    public boolean isItemsInsufficientYTDFieldVisible() {
        return isElementVisible(itemsInsufficientYTDLabel);
    }

    @Step("Check if 'Times insufficient LYR' field is visible")
    public boolean isTimesInsufficientLYRFieldVisible() {
        return isElementVisible(timesInsufficientLYRLabel);
    }

    @Step("Check if 'NSF fee, paid this cycle' field is visible")
    public boolean isNsfFeePaidThisCycleFieldVisible() {
        return isElementVisible(nsfFeePaidThisCycleLabel);
    }

    @Step("Check if 'NSF fee, rtrn this cycle' field is visible")
    public boolean isNsfFeeRtrnThisCycleFieldVisible() {
        return isElementVisible(nsfFeeRtrnThisCycleLabel);
    }

    @Step("Check if 'NSF fee, paid YTD' field is visible")
    public boolean isNsfFeePaidYTDFieldVisible() {
        return isElementVisible(nsfFeePaidYTDLabel);
    }

    @Step("Check if 'NSF fee, rtrn YTD' field is visible")
    public boolean isNsfFeeRtrnYTDFieldVisible() {
        return isElementVisible(nsfFeeRtrnYTDLabel);
    }

    @Step("Check if 'NSF fee, paid last year' field is visible")
    public boolean isNsfFeePaidLastYearFieldVisible() {
        return isElementVisible(nsfFeePaidLastYearLabel);
    }

    @Step("Check if 'NSF fee, rtrn last year' field is visible")
    public boolean isNsfFeeRtrnLastYearFieldVisible() {
        return isElementVisible(nsfFeeRtrnLastYearLabel);
    }

    @Step("Check if 'Number refunded stmt cycle' field is visible")
    public boolean isNumberRefundedStmtCycleFieldVisible() {
        return isElementVisible(numberRefundedStmtCycleLabel);
    }

    @Step("Check if 'Amount refunded stmt cycle' field is visible")
    public boolean isAmountRefundedStmtCycleFieldVisible() {
        return isElementVisible(amountRefundedStmtCycleLabel);
    }

    @Step("Check if 'Misc Section Group' fields is hidden")
    public boolean isMiscSectionGroupFieldsOpened() {
        return isElementVisible(miscGroupOpened);
    }

    @Step("Check if 'Balance And Interest Section Group' fields is hidden")
    public boolean isBalanceAndInterestSectionGroupFieldsOpened() {
        return isElementVisible(balanceAndInterestGroupOpened);
    }

    @Step("Check if 'Term' fields is hidden")
    public boolean isTermGroupFieldsOpened() {
        return isElementVisible(termGroupOpened);
    }

    @Step("Check if 'Distribution and Misc' fields is hidden")
    public boolean isDistributionAndMiscGroupFieldsOpened() {
        return isElementVisible(distributionAndMiscGroupOpened);
    }

    @Step("Check if 'Transactions Section Group' fields is hidden")
    public boolean isTransactionsSectionGroupFieldsOpened() {
        return isElementVisible(transactionsGroupOpened);
    }

    @Step("Check if 'Overdraft Section Group' fields is hidden")
    public boolean isOverdraftSectionGroupFieldsOpened() {
        return isElementVisible(overdraftGroupOpened);
    }

    @Step("Check if 'Distribution Section Group' fields is hidden")
    public boolean isDistributionSectionGroupFieldsOpened() {
        return isElementVisible(distributionGroupOpened);
    }

    @Step("Federal W/H reason' field is visible")
    public boolean isFederalWhReasonFieldVisible() {
        return isElementVisible(federalWhReasonLabel);
    }

    @Step("Federal W/H percent' field is visible")
    public boolean isFederalWhPercentFieldVisible() {
        return isElementVisible(federalWhPercentLabel);
    }


    @Step("'Taxes Withheld YTD' field is visible")
    public boolean isTaxesWithheldYtdFieldVisible() {
        return isElementVisible(taxesWithheldYtdLabel);
    }


    @Step("'User Defined Field 1' field is visible")
    public boolean isUserDefinedField1FieldVisible() {
        return isElementVisible(userDefinedField1Label);
    }


    @Step("'User Defined Field 2' field is visible")
    public boolean isUserDefinedField2FieldVisible() {
        return isElementVisible(userDefinedField2Label);
    }


    @Step("'User Defined Field 3' field is visible")
    public boolean isUserDefinedField3FieldVisible() {
        return isElementVisible(userDefinedField3Label);
    }


    @Step("'User Defined Field 4' field is visible")
    public boolean isUserDefinedField4FieldVisible() {
        return isElementVisible(userDefinedField4Label);
    }


    @Step("'Print Statement Next Update' field is visible")
    public boolean isPrintStatementNextUpdateFieldVisible() {
        return isElementVisible(printStatementNextUpdateLabel);
    }


    @Step("'Call Class Code' field is visible")
    public boolean isCallClassCodeFieldVisible() {
        return isElementVisible(callClassCodeLabel);
    }


    @Step("'1 day float' field is visible")
    public boolean is1DayFloatFieldVisible() {
        return isElementVisible(day1FloatLabel);
    }


    @Step("'2 day float' field is visible")
    public boolean is2DayFloatFieldVisible() {
        return isElementVisible(day2FloatLabel);
    }


    @Step("'3 day float' field is visible")
    public boolean is3DayFloatFieldVisible() {
        return isElementVisible(day3FloatLabel);
    }


    @Step("'4 day float' field is visible")
    public boolean is4DayFloatFieldVisible() {
        return isElementVisible(day4FloatLabel);
    }


    @Step("'5 day float' field is visible")
    public boolean is5DayFloatFieldVisible() {
        return isElementVisible(day5FloatLabel);
    }


    @Step("'Number of Debit Cards issued' field is visible")
    public boolean isNumberOfDebitCardsIssuedFieldVisible() {
        return isElementVisible(numberOfDebitCardsIssuedLabel);
    }


    @Step("'Reason Debit Card Charge Waived' field is visible")
    public boolean isReasonDebitCardChargeWaivedFieldVisible() {
        return isElementVisible(reasonDebitCardChargeWaivedLabel);
    }


    @Step("'Number refunded YTD' field is visible")
    public boolean isNumberRefundedYtdFieldVisible() {
        return isElementVisible(numberRefundedYtdLabel);
    }


    @Step("'Amount refunded YTD' field is visible")
    public boolean isAmountRefundedYtdFieldVisible() {
        return isElementVisible(amountRefundedYtdLabel);
    }


    @Step("'Number refunded LYR' field is visible")
    public boolean isNumberRefundedLyrFieldVisible() {
        return isElementVisible(numberRefundedLyrLabel);
    }


    @Step("'Amount refunded LYR' field is visible")
    public boolean isAmountRefundedLyrFieldVisible() {
        return isElementVisible(amountRefundedLyrlabel);
    }


    @Step("'Bankruptcy/Judgement' field is visible")
    public boolean isBankruptcyJudgementFieldVisible() {
        return isElementVisible(bankruptcyJudgementLabel);
    }


    @Step("'Bankruptcy/Judgement Date' field is visible")
    public boolean isBankruptcyJudgementDateFieldVisible() {
        return isElementVisible(bankruptcyJudgementDateLabel);
    }


    @Step("'Exempt from Reg CC' field is visible")
    public boolean isExemptFromRegCCFieldVisible() {
        return isElementVisible(exemptFromRegCcLabel);
    }


    @Step("'New Account' field is visible")
    public boolean isNewAccountFieldVisible() {
        return isElementVisible(newAccountLabel);
    }


    @Step("'Transactional Account' field is visible")
    public boolean isTransactionalAccountFieldVisible() {
        return isElementVisible(transactionalAccountLabel);
    }


    @Step("'Total Earnings for Life of Account' field is visible")
    public boolean isTotalEarningsForLifeOfAccountFieldVisible() {
        return isElementVisible(totalEarningsForLifeOfAccountLabel);
    }

    @Step("'Balance at end of year' field is visible")
    public boolean isBalanceAtEndOfYearFieldVisible() {
        return isElementVisible(balanceAtEndOfYearLabel);
    }

    @Step("'Accrued interest at end of year' field is visible")
    public boolean isAccruedInterestAtEndOfYearFieldVisible() {
        return isElementVisible(accruedInterestAtEndOfYearLabel);
    }

    @Step("'Interest Paid Last Year' field is visible")
    public boolean isInterestPaidLastYearFieldVisible() {
        return isElementVisible(interestPaidLastYearLabel);
    }

    @Step("'Print interest notice override' field is visible")
    public boolean isPrintInterestNoticeOverrideFieldVisible() {
        return isElementVisible(printInterestNoticeOverrideLabel);
    }

    @Step("'Verify ACH funds' field is visible")
    public boolean isVerifyAchFundsFieldVisible() {
        return isElementVisible(verifyAchFundsLabel);
    }


    @Step("'Waive Service Charges' field is visible")
    public boolean isWaiveServiceChargesFieldVisible() {
        return isElementVisible(waiveServiceChargesLabel);
    }

    @Step("'Enable Positive Pay' field is visible")
    public boolean isEnablePositivePayFieldVisible() {
        return isElementVisible(enablePositivePayLabel);
    }

    @Step("'Date Of First Deposit' field is visible")
    public boolean isDateOfFirstDepositFieldVisible() {
        return isElementVisible(dateOfFirstDepositLabel);
    }

    @Step("'IRA Distribution Frequency' field is visible")
    public boolean isIRADistributionFrequencyFieldVisible() {
        return isElementVisible(iraDistributionFrequencyLabel);
    }

    @Step("'IRA Distribution Code' field is visible")
    public boolean isIRADistributionCodeFieldVisible() {
        return isElementVisible(iraDistributionCodeLabel);
    }

    @Step("'IRA Distribution Account Number' field is visible")
    public boolean isIRADistributionAccountNumberFieldVisible() {
        return isElementVisible(iraDistributionAccountNumberLabel);
    }

    @Step("'IRA distribution amount' field is visible")
    public boolean isIRADistributionAmountFieldVisible() {
        return isElementVisible(iraDistributionAmountLabel);
    }

    @Step("'Amount last IRA distribution' field is visible")
    public boolean isAmountLastIRADistributionFieldVisible() {
        return isElementVisible(amountLastIRADistributionLabel);
    }

    @Step("'Date last IRA distribution' field is visible")
    public boolean isDateLastIRADistributionFieldVisible() {
        return isElementVisible(dateLastIRADistributionLabel);
    }

    @Step("'Date next IRA distribution' field is visible")
    public boolean isDateNextIRADistributionFieldVisible() {
        return isElementVisible(dateNextIRADistributionLabel);
    }

    @Step("'RMD Date' field is visible")
    public boolean isRMDDateFieldVisible() {
        return isElementVisible(rmdDateLabel);
    }

    @Step("'RMD Amount' field is visible")
    public boolean isRMDAmountFieldVisible() {
        return isElementVisible(rmdAmountLabel);
    }

    @Step("'IRA distributions YTD' field is visible")
    public boolean isIRADistributionsYTDFieldVisible() {
        return isElementVisible(iraDistributionsYTDLabel);
    }

    @Step("'Date of Birth' field is visible")
    public boolean isDateOfBirthFieldVisible() {
        return isElementVisible(dateOfBirthLabel);
    }

    @Step("'Date Deceased' field is visible")
    public boolean isDateDeceasedFieldVisible() {
        return isElementVisible(dateDeceasedLabel);
    }

    @Step("'Term Type' field is visible")
    public boolean isTermTypeFieldVisible() {
        return isElementVisible(termTypeLabel);
    }

    @Step("'Term In Months Or Days' field is visible")
    public boolean isTermInMonthsOrDaysFieldVisible() {
        return isElementVisible(termInMonthsOrDaysLabel);
    }

    @Step("'Auto Renewable' field is visible")
    public boolean isAutoRenewableFieldVisible() {
        return isElementVisible(autoRenewableLabel);
    }

    @Step("'Maturity Date' field is visible")
    public boolean isMaturityDateFieldVisible() {
        return isElementVisible(maturityDateLabel);
    }

    @Step("'Penalty Amount Year-to-date' field is visible")
    public boolean isPenaltyAmountYearToDateFieldVisible() {
        return isElementVisible(penaltyAmountYearToDateLabel);
    }

    @Step("'Balance At Renewal' field is visible")
    public boolean isBalanceAtRenewalFieldVisible() {
        return isElementVisible(balanceAtRenewalLabel);
    }

    @Step("'Date of renewal' field is visible")
    public boolean isDateOfRenewalFieldVisible() {
        return isElementVisible(dateOfRenewalLabel);
    }

    @Step("'Interest rate at renewal' field is visible")
    public boolean isInterestRateAtRenewalFieldVisible() {
        return isElementVisible(interestRateAtRenewalLabel);
    }

    @Step("'Renewal amount' field is visible")
    public boolean isRenewalAmountFieldVisible() {
        return isElementVisible(renewalAmountLabel);
    }

    @Step("'Rate Index' field is visible")
    public boolean isRateIndexFieldVisible() {
        return isElementVisible(rateIndexLabel);
    }

    @Step("'Accrued Interest' field is visible")
    public boolean isAccruedInterestFieldVisible() {
        return isElementVisible(accruedInterestLabel);
    }

    @Step("'Daily Interest Accrual' field is visible")
    public boolean isDailyInterestAccrualFieldVisible() {
        return isElementVisible(dailyInterestAccrualLabel);
    }

    @Step("'Interest Type' field is visible")
    public boolean isInterestTypeFieldVisible() {
        return isElementVisible(interestTypeLabel);
    }

    @Step("'Apply Interest To' field is visible")
    public boolean isApplyInterestToFieldVisible() {
        return isElementVisible(applyInterestToLabel);
    }

    @Step("'Amount Last Interest Paid' field is visible")
    public boolean isAmountLastInterestPaidFieldVisible() {
        return isElementVisible(amountLastInterestPaidLabel);
    }

    @Step("'Date Last Interest Paid' field is visible")
    public boolean isDateLastInterestPaidFieldVisible() {
        return isElementVisible(dateLastInterestPaidLabel);
    }

    @Step("'Date next interest' field is visible")
    public boolean isDateNextInterestFieldVisible() {
        return isElementVisible(dateNextInterestLabel);
    }

    @Step("'Next Interest Payment Amount' field is visible")
    public boolean isNextInterestPaymentAmountFieldVisible() {
        return isElementVisible(nextInterestPaymentAmountLabel);
    }

    @Step("'Interest Paid Year to date' field is visible")
    public boolean isInterestPaidYearToDateFieldVisible() {
        return isElementVisible(interestPaidYearToDateLabel);
    }

    @Step("'Total Contributions for Life of Account' field is visible")
    public boolean isTotalContributionsForLifOfAccountFieldVisible() {
        return isElementVisible(totalContributionsForLifeOfAccountLabel);
    }

    @Step("'IRA plan number' field is visible")
    public boolean isIRAPlanNumberFieldVisible() {
        return isElementVisible(iraPlanNumberLabel);
    }

    @Step("'Number of Debits This Statement Cycle' field is visible")
    public boolean isNumberOfDebitsThisStatementCycleLabelVisible() {
        return isElementVisible(numberOfDebitsThisStatementCycleLabel);
    }

    @Step("Check if 'Product type' field is disabled edit mode")
    public boolean isProductTypeFieldDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", productTypeField));
    }

    @Step("Check if 'Date Last Debit' field is disabled edit mode")
    public boolean isDateLastDebitDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", dateLastDebit));
    }

    @Step("Check if 'Number Of Debits This Statement Cycle' field is disabled edit mode")
    public boolean isNumberOfDebitsThisStatementCycleDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", numberOfDebitsThisStatementCycle));
    }

    @Step("Check if 'Ytd Average Balance' field is disabled edit mode")
    public boolean isYtdAverageBalanceDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", ydtAverageBalance));
    }

    @Step("Check if 'Date Of First Deposit' field is disabled edit mode")
    public boolean isDateOfFirstDepositDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", dateOfFirstDeposit));
    }

    @Step("Check if 'YTD charges waived' field is disabled edit mode")
    public boolean isYtdChargesWaivedDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", ytdChargesWaived));
    }

    @Step("Check if 'Interest Rate' field is disabled edit mode")
    public boolean isInterestRateDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", interestRate));
    }

    @Step("Check if 'Interest Last paid' field is disabled edit mode")
    public boolean isInterestLastPaidDisabledInEditMode() {
        return Boolean.parseBoolean(getElementAttributeValue("disabled", interestLastPaid));
    }

    @Step("Check if 'Account Holders and Signers' field is visible")
    public boolean isAccountHoldersAndSignersFieldVisible() {
        return isElementVisible(accountHoldersAndSignersFormTitleLabel);
    }

    @Step("Check if 'Account Status' field is visible")
    public boolean isAccountStatusFieldVisible() {
        return isElementVisible(accountStatusFieldLabel);
    }

    @Step("Check if 'Originating Officer' field is visible")
    public boolean isOriginatingOfficerFieldVisible() {
        return isElementVisible(originatingOfficerFieldLabel);
    }

    @Step("Check if 'Current Officer' header is visible")
    public boolean isCurrentOfficerHeaderVisible() {
        return isElementVisible(currentOfficerLabel);
    }

    @Step("Check if 'Bank Branch' field is visible")
    public boolean isBankBranchFieldVisible() {
        return isElementVisible(bankBranchLabel);
    }

    @Step("Check if 'Mail Code' field is visible")
    public boolean isMailCodeFieldVisible() {
        return isElementVisible(mailCodeLabel);
    }

    @Step("Check if 'Apply Seasonal Address' switcher is visible")
    public boolean isApplySeasonalAddressSwitcherVisible() {
        return isElementVisible(applySeasonalAddressLabel);
    }

    @Step("Check if 'Special Mailing Instructions' field is visible")
    public boolean isSpecialMailingInstructionsFieldVisible() {
        return isElementVisible(specialMailingInstructionsLabel);

    }

    @Step("Check if 'Date Opened' field is visible")
    public boolean isDateOpenedFieldVisible() {
        return isElementVisible(dateOpenedFieldLabel);
    }

    @Step("Check if 'Date Closed' field is visible")
    public boolean isDateClosedFieldVisible() {
        return isElementVisible(dateClosedFieldLabel);

    }

    @Step("Set 'Print Statement Next Update' option")
    public void setPrintStatementNextUpdate(String printStatementNextUpdateValue) {
        waitForElementVisibility(printStatementNextUpdate);
        waitForElementClickable(printStatementNextUpdate);
        scrollToPlaceElementInCenter(printStatementNextUpdate, printStatementNextUpdateValue);
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
        scrollToPlaceElementInCenter(interestRate);
        type(interestRateValue, interestRate);
    }

    @Step("Click the 'Charge or Analyze' option")
    public void clickChargeOrAnalyzeSelectorOption(String chargeOrAnalyzeOption) {
        waitForElementVisibility(chargeOrAnalyzeSelectorOption, chargeOrAnalyzeOption);
        waitForElementClickable(chargeOrAnalyzeSelectorOption, chargeOrAnalyzeOption);
        click(chargeOrAnalyzeSelectorOption, chargeOrAnalyzeOption);
    }

    @Step("Returning list of 'Charge or Analyze' options")
    public List<String> getChargeOrAnalyzeList() {
        waitForElementVisibility(chargeOrAnalyzeList);
        waitForElementClickable(chargeOrAnalyzeList);
        return getElementsText(chargeOrAnalyzeList);
    }

    @Step("Click the 'Charge or Analyze' selector button")
    public void clickChargeOrAnalyzeSelectorButton() {
        scrollToPlaceElementInCenter(chargeOrAnalyzeSelectorButton);
        click(chargeOrAnalyzeSelectorButton);
    }

    @Step("Click the 'Bankruptcy Judgement' option")
    public void clickBankruptcyJudgementSelectorOption(String bankruptcyJudgementOption) {
        waitForElementVisibility(bankruptcyJudgementSelectorOption, bankruptcyJudgementOption);
        waitForElementClickable(bankruptcyJudgementSelectorOption, bankruptcyJudgementOption);
        click(bankruptcyJudgementSelectorOption, bankruptcyJudgementOption);
    }

    @Step("Returning list of 'Bankruptcy Judgement' options")
    public List<String> getBankruptcyJudgementList() {
        waitForElementVisibility(bankruptcyJudgementList);
        waitForElementClickable(bankruptcyJudgementList);
        return getElementsText(bankruptcyJudgementList);
    }

    @Step("Click the 'Bankruptcy Judgement' selector button")
    public void clickBankruptcyJudgementSelectorButton() {
        scrollToPlaceElementInCenter(bankruptcyJudgementSelectorButton);
        click(bankruptcyJudgementSelectorButton);
    }

    @Step("Click the 'Statement Cycle' option")
    public void clickStatementCycleOption(String statementCycleOption) {
        waitForElementVisibility(statementCycleSelectorOption, statementCycleOption);
        waitForElementClickable(statementCycleSelectorOption, statementCycleOption);
        click(statementCycleSelectorOption, statementCycleOption);
    }

    @Step("Returning list of 'Statement Cycle' options")
    public List<String> getStatementCycleList() {
        waitForElementVisibility(statementCycleList);
        waitForElementClickable(statementCycleList);
        return getElementsText(statementCycleList);
    }

    @Step("Click the 'Statement Cycle' selector button")
    public void clickStatementCycleSelectorButton() {
        scrollToPlaceElementInCenter(statementCycleSelectorButton);
        click(statementCycleSelectorButton);
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
        scrollToPlaceElementInCenter(numberOfDebitCardsIssuedInput);
        type(numberOfDebitCardsIssuedValue, numberOfDebitCardsIssuedInput);
    }

    @Step("Set 'User Defined Field 4' value")
    public void setUserDefinedField_4(String value) {
        scrollToPlaceElementInCenter(userDefinedFieldInput_4);
        type(value, userDefinedFieldInput_4);
    }

    @Step("Set 'User Defined Field 3' value")
    public void setUserDefinedField_3(String value) {
        scrollToPlaceElementInCenter(userDefinedFieldInput_3);
        type(value, userDefinedFieldInput_3);
    }

    @Step("Set 'User Defined Field 2' value")
    public void setUserDefinedField_2(String value) {
        scrollToPlaceElementInCenter(userDefinedFieldInput_2);
        type(value, userDefinedFieldInput_2);
    }

    @Step("Set 'User Defined Field 1' value")
    public void setUserDefinedField_1(String value) {
        scrollToPlaceElementInCenter(userDefinedFieldInput_1);
        type(value, userDefinedFieldInput_1);
    }

    @Step("Set 'Account Routing Interest On CD' value")
    public void setBankRoutingNumberInterestOnCDValue(String bankRoutingNumberInterestOnCDValue) {
        waitForElementVisibility(bankRoutingNumberInterestOnCD);
        waitForElementClickable(bankRoutingNumberInterestOnCD);
        type(bankRoutingNumberInterestOnCDValue, bankRoutingNumberInterestOnCD);
    }

    @Step("Set 'Account Number Interest On CD' value")
    public void setBankAccountNumberInterestOnCDValue(String bankAccountNumberInterestOnCDValue) {
        waitForElementVisibility(bankAccountNumberInterestOnCD);
        waitForElementClickable(bankAccountNumberInterestOnCD);
        type(bankAccountNumberInterestOnCDValue, bankAccountNumberInterestOnCD);
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
        scrollToPlaceElementInCenter(federalWHPercentInput);
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
        scrollToPlaceElementInCenter(whenSurchargesRefundedSelectorButton);
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
        scrollToPlaceElementInCenter(reasonAutoOdChgWaivedSelectorButton);
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
        scrollToPlaceElementInCenter(automaticOverdraftStatusSelectorButton);
        click(automaticOverdraftStatusSelectorButton);
    }

    @Step("Click the 'Corresponding Account' option")
    public void clickCorrespondingAccountSelectorOption(String correspondingAccountOption) {
        waitForElementVisibility(correspondingAccountSelectorOption, correspondingAccountOption);
        waitForElementClickable(correspondingAccountSelectorOption, correspondingAccountOption);
        click(correspondingAccountSelectorOption, correspondingAccountOption);
    }

    @Step("Returning list of 'Corresponding Account' options")
    public List<String> getCorrespondingAccountList() {
        return getElementsWithZeroOption(correspondingAccountList).stream()
                .map(SelenideElement::text)
                .collect(Collectors.toList());
    }

    @Step("Click the 'Corresponding Account' selector button")
    public void clickCorrespondingAccountSelectorButton() {
        waitForElementVisibility(correspondingAccountSelectorButton);
        scrollToPlaceElementInCenter(correspondingAccountSelectorButton);
        waitForElementClickable(correspondingAccountSelectorButton);
        click(correspondingAccountSelectorButton);
    }

    @Step("Click the 'Corresponding Account' selector button with js")
    public void clickCorrespondingAccountSelectorButtonWithJs() {
        waitForElementVisibility(correspondingAccountSelectorButton);
        jsClick(correspondingAccountSelectorButton);
    }

    public void clickDiscountReasonSelectorOption(String discountReasonOption) {
        waitForElementVisibility(discountReasonSelectorOption, discountReasonOption);
        waitForElementClickable(discountReasonSelectorOption, discountReasonOption);
        click(discountReasonSelectorOption, discountReasonOption);
    }

    @Step("Returning list of 'Discount reason' options")
    public List<String> getDiscountReasonList() {
        waitForElementVisibility(discountReasonList);
        waitForElementClickable(discountReasonList);
        return getElementsText(discountReasonList);
    }

    @Step("Click the 'Discount Reason' selector button")
    public void clickDiscountReasonSelectorButton() {
        waitForElementVisibility(discountReasonSelectorButton);
        scrollToPlaceElementInCenter(discountReasonSelectorButton);
        waitForElementClickable(discountReasonSelectorButton);
        click(discountReasonSelectorButton);
    }

    @Step("Click the 'Mail Code' selector button")
    public void clickMailCodeSelectorOption(String mailCodeOption) {
        waitForElementVisibility(mailCodeSelectorOption, mailCodeOption);
        waitForElementClickable(mailCodeSelectorOption, mailCodeOption);
        click(mailCodeSelectorOption, mailCodeOption);
    }

    @Step("Returning list of 'Mail Code' options")
    public List<String> getMailCodeList() {
        waitForElementVisibility(mailCodeList);
        waitForElementClickable(mailCodeList);
        return getElementsText(mailCodeList);
    }

    @Step("Click the 'Mail Code' selector button")
    public void clickMailCodeSelectorButton() {
        waitForElementVisibility(mailCodeSelectorButton);
        scrollToPlaceElementInCenter(mailCodeSelectorButton);
        waitForElementClickable(mailCodeSelectorButton);
        click(mailCodeSelectorButton);
    }

    @Step("Click the 'Mail Code' selector button with js")
    public void clickMailCodeSelectorButtonWithJs() {
        waitForElementVisibility(mailCodeSelectorButton);
        jsClick(mailCodeSelectorButton);
    }

    @Step("Set 'Date Last Access' value")
    public void setDateLastAccess(String dateLastAccessValue) {
        waitForElementVisibility(dateLastAccess);
        waitForElementClickable(dateLastAccess);
        typeWithoutWipe("", dateLastAccess);
        SelenideTools.sleep(1);
        typeWithoutWipe(dateLastAccessValue, dateLastAccess);
    }

    @Step("Set 'Maturity Date' value")
    public void setMaturityDate(String maturityDateValue) {
        waitForElementClickable(maturityDate);
        typeWithoutWipe("", maturityDate);
        SelenideTools.sleep(1);
        typeWithoutWipe(maturityDateValue, maturityDate);
    }

    @Step("Set 'Date Of First Deposit' value")
    public void setDateOfFirstDeposit(String date) {
        Selenide.sleep(3);
        waitForElementVisibility(dateOfFirstDeposit);
        waitForElementClickable(dateOfFirstDeposit);
        typeWithoutWipe("", dateOfFirstDeposit);
        SelenideTools.sleep(1);
        typeWithoutWipe(date, dateOfFirstDeposit);
    }

    @Step("Set 'Birth Date' value")
    public void setBirthDate(String date) {
        Selenide.sleep(3);
        waitForElementVisibility(birthDate);
        waitForElementClickable(birthDate);
        typeWithoutWipe("", birthDate);
        SelenideTools.sleep(1);
        typeWithoutWipe(date, birthDate);
    }

    @Step("Set 'Date Deceased' value")
    public void setDateDeceased(String date) {
        Selenide.sleep(3);
        waitForElementVisibility(dateDeceased);
        waitForElementClickable(dateDeceased);
        typeWithoutWipe("", dateDeceased);
        SelenideTools.sleep(1);
        typeWithoutWipe(date, dateDeceased);
    }

    @Step("Set 'Next Rate Change Date' value")
    public void setNextRateChangeDate(String date) {
        waitForElementClickable(nextRateChangeDate);
        scrollToPlaceElementInCenter(nextRateChangeDate);
        typeWithoutWipe("", nextRateChangeDate);
        SelenideTools.sleep(1);
        typeWithoutWipe(date, nextRateChangeDate);
    }

    @Step("Set 'Rate Change Lead Days' value")
    public void setRateChangeLeadDays(String days) {
        waitForElementClickable(rateChangeLeadDays);
        scrollToPlaceElementInCenter(rateChangeLeadDays);
        type(days, rateChangeLeadDays);
    }

    @Step("Set 'Next Payment Change Date' value")
    public void setNextPaymentChangeDate(String date) {
        waitForElementClickable(nextPaymentChangeDate);
        scrollToPlaceElementInCenter(nextPaymentChangeDate);
        typeWithoutWipe("", nextPaymentChangeDate);
        SelenideTools.sleep(1);
        typeWithoutWipe(date, nextPaymentChangeDate);
    }

    @Step("Set 'Rate Margin' value")
    public void setRateMargin(String days) {
        waitForElementClickable(rateMargin);
        scrollToPlaceElementInCenter(rateMargin);
        type(days, rateMargin);
    }

    @Step("Set 'Min Rate' value")
    public void setMinRate(String days) {
        waitForElementClickable(minRate);
        scrollToPlaceElementInCenter(minRate);
        type(days, minRate);
    }

    @Step("Set 'Max Rate' value")
    public void setMaxRate(String days) {
        waitForElementClickable(maxRate);
        scrollToPlaceElementInCenter(maxRate);
        type(days, maxRate);
    }

    @Step("Set 'Max Rate change up/down' value")
    public void setMaxRateChangeUpDown(String days) {
        waitForElementClickable(maxRateChangeUpDown);
        scrollToPlaceElementInCenter(maxRateChangeUpDown);
        type(days, maxRateChangeUpDown);
    }

    @Step("Set 'Max rate lifetime cap' value")
    public void setMaxRateLifetimeCap(String days) {
        waitForElementClickable(maxRateLifetimeCap);
        scrollToPlaceElementInCenter(maxRateLifetimeCap);
        type(days, maxRateLifetimeCap);
    }

    @Step("Set 'Rate rounding factor' value")
    public void setRateRoundingFactor(String factor) {
        waitForElementClickable(rateRoundingFactorInput, factor);
        click(rateRoundingFactorInput, factor);
    }

    @Step("Click 'Rate rounding factor' field")
    public void clickRateRoundingFactorField() {
        waitForElementClickable(rateRoundingFactor);
        scrollToPlaceElementInCenter(rateRoundingFactor);
        click(rateRoundingFactor);
    }

//    @Step("Set 'Rate rounding method' value")
//    public void setRateRoundingMethod(String days) {
//        waitForElementClickable(rateRoundingMethod);
//        scrollToPlaceElementInCenter(rateRoundingMethod);
//        type(days, rateRoundingMethod);
//    }

    @Step("Set 'Original interest rate' value")
    public void setOriginalInterestRate(String days) {
        waitForElementClickable(originalInterestRate);
        scrollToPlaceElementInCenter(originalInterestRate);
        type(days, originalInterestRate);
    }

    @Step("Set 'Payment Change Lead Days' value")
    public void setPaymentChangeLeadDays(String days) {
        waitForElementClickable(paymentChangeLeadDays);
        scrollToPlaceElementInCenter(paymentChangeLeadDays);
        type(days, paymentChangeLeadDays);
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
        scrollToPlaceElementInCenter(reasonReasonDebitCardChargeWaivedSelectorButton);
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
        scrollToPlaceElementInCenter(reasonAutoNSFChgWaivedSelectorButton);
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
        scrollToPlaceElementInCenter(odProtectionAcctSelectorButton);
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
        scrollToPlaceElementInCenter(reasonATMChargeWaivedSelectorButton);
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
        scrollToPlaceElementInCenter(federalWHReasonSelectorButton);
        click(federalWHReasonSelectorButton);
    }

    @Step("Click the 'Call Class Code' option")
    public void clickCallClassCodeSelectorOption(String callClassCodeOption) {
        waitForElementVisibility(callClassCodeSelectorOption, callClassCodeOption);
        waitForElementClickable(callClassCodeSelectorOption, callClassCodeOption);
        click(callClassCodeSelectorOption, callClassCodeOption);
    }

    @Step("Returning list of 'Call Class Code' options")
    public List<String> getCallClassCodeList() {
        return getElementsWithZeroOption(callClassCodeList).stream()
                .map(SelenideElement::text)
                .collect(Collectors.toList());
    }

    @Step("Click the 'Call Class Code' selector button")
    public void clickCallClassCodeSelectorButton() {
        scrollToPlaceElementInCenter(callClassCodeSelectorButton);
        click(callClassCodeSelectorButton);
    }

    @Step("Returning list of 'Bank Branch'")
    public List<String> getBankBranchList() {
        waitForElementVisibility(bankBranchList);
        waitForElementClickable(bankBranchList);
        return getElementsText(bankBranchList);
    }

    @Step("Click the 'Bank branch' selector button")
    public void clickBankBranchSelectorButton() {
        scrollToPlaceElementInCenter(bankBranchSelectorButton);
        click(bankBranchSelectorButton);
    }

    @Step("Click the 'Bank branch' selector button with js")
    public void clickBankBranchSelectorButtonWithJs() {
        waitForElementVisibility(bankBranchSelectorButton);
        jsClick(bankBranchSelectorButton);
    }

    @Step("Click the 'Bank branch' option")
    public void clickBankBranchOption(String bankBranchOption) {
        waitForElementVisibility(bankBranchSelectorOption, bankBranchOption);
        waitForElementClickable(bankBranchSelectorOption, bankBranchOption);
        click(bankBranchSelectorOption, bankBranchOption);
    }

    @Step("Click the 'Current Officer' option")
    public void clickCurrentOfficerSelectorOption(String currentOfficerOption) {
        waitForElementVisibility(currentOfficerSelectorOption, currentOfficerOption);
        waitForElementClickable(currentOfficerSelectorOption, currentOfficerOption);
        click(currentOfficerSelectorOption, currentOfficerOption);
    }

    @Step("Click item in dropdown")
    public void clickItemInDropDownWithJs(String option) {
        waitForElementVisibility(itemInDropdown, option);
        jsClick(itemInDropdown, option);
    }

    @Step("Returning list of 'Current Officer' options")
    public List<String> getCurrentOfficerList() {
        waitForElementVisibility(currentOfficerList);
        waitForElementClickable(currentOfficerList);
        return getElementsText(currentOfficerList);
    }

    @Step("Click the 'Current Officer' selector button")
    public void clickCurrentOfficerSelectorButton() {
        scrollToPlaceElementInCenter(currentOfficerSelectorButton);
        click(currentOfficerSelectorButton);
    }

    @Step("Click the 'Current Officer' selector button with js")
    public void clickCurrentOfficerSelectorButtonWithJs() {
        waitForElementVisibility(currentOfficerSelectorButton);
        jsClick(currentOfficerSelectorButton);
    }

    @Step("Click the 'Apply Interest To' option")
    public void clickApplyInterestToSelectorOption(String applyInterestToOption) {
        waitForElementVisibility(applyInterestToSelectorOption, applyInterestToOption);
        waitForElementClickable(applyInterestToSelectorOption, applyInterestToOption);
        click(applyInterestToSelectorOption, applyInterestToOption);
    }

    @Step("Returning list of 'Apply Interest To' options")
    public List<String> getApplyInterestToList() {
        waitForElementVisibility(applyInterestToList);
        waitForElementClickable(applyInterestToList);
        return getElementsText(applyInterestToList);
    }

    @Step("Click the 'Apply Interest To' selector button")
    public void clickApplyInterestToSelectorButton() {
        waitForElementVisibility(applyInterestToSelectorButton);
        scrollToPlaceElementInCenter(applyInterestToSelectorButton);
        waitForElementClickable(applyInterestToSelectorButton);
        click(applyInterestToSelectorButton);
    }

    @Step("Get 'Bank Account Number Interest On CD' value in edit mode")
    public String getBankAccountNumberInterestOnCD() {
        waitForElementVisibility(bankAccountNumberInterestOnCD);
        return getElementAttributeValue("value", bankAccountNumberInterestOnCD);
    }

    @Step("Get 'Bank Routing Number Interest On CD' value in edit mode")
    public String getBankRoutingNumberInterestOnCD() {
        waitForElementVisibility(bankRoutingNumberInterestOnCD);
        return getElementAttributeValue("value", bankRoutingNumberInterestOnCD);
    }

    @Step("Is 'Balance And Interest' group visible")
    public boolean isBalanceAndInterestGroupVisible() {
        waitForElementPresent(balanceAndInterestGroup);
        return isElementVisible(balanceAndInterestGroup);
    }

    @Step("Is 'Transactions' group visible")
    public boolean isTransactionsGroupVisible() {
        waitForElementPresent(transactionsGroup);
        return isElementVisible(transactionsGroup);
    }

    @Step("Is 'Overdraft' group visible")
    public boolean isOverdraftGroupVisible() {
        waitForElementPresent(overdraftGroup);
        return isElementVisible(overdraftGroup);
    }

    @Step("Is 'Misc' group visible")
    public boolean isMiscGroupVisible() {
        waitForElementPresent(miscGroup);
        return isElementVisible(miscGroup);
    }

    @Step("Is 'Distribution' group visible")
    public boolean isDistributionGroupVisible() {
        waitForElementPresent(distrbutionGroup);
        return isElementVisible(distrbutionGroup);
    }

    @Step("Is 'Distribution and Misc' group visible")
    public boolean isDistributionAndMiscGroupVisible() {
        waitForElementPresent(distributionAndMiscGroup);
        return isElementVisible(distributionAndMiscGroup);
    }

    @Step("Is 'Term' group visible")
    public boolean isTermGroupVisible() {
        waitForElementPresent(termGroup);
        return isElementVisible(termGroup);
    }

    @Step("Click the 'Misc' section link")
    public void clickMiscSectionLink() {
        scrollToPlaceElementInCenter(miscTitle);
        click(miscTitle);
    }

    @Step("Click the 'Distribution' section link")
    public void clickDistributionSectionLink() {
        scrollToPlaceElementInCenter(distrbutionTitle);
        click(distrbutionTitle);
    }

    @Step("Click the 'Balance and Interest' section link")
    public void clickBalanceAndInterestSectionLink() {
        scrollToPlaceElementInCenter(balanceAndInterestGroup);
        click(balanceAndInterestGroup);
    }

    @Step("Click the 'Transactions' section link")
    public void clickTransactionsSectionLink() {
        scrollToPlaceElementInCenter(transactionsTitle);
        click(transactionsTitle);
    }

    @Step("Click the 'Overdraft' section link")
    public void clickOverdraftSectionLink() {
        scrollToPlaceElementInCenter(overdraftGroup);
        click(overdraftGroup);
    }

    @Step("Click the 'Term' section link")
    public void clickTermSectionLink() {
        scrollToPlaceElementInCenter(termGroup);
        click(termGroup);
    }

    @Step("Click the 'Distribution and Misc' section link")
    public void clickDistributionAndMiscSectionLink() {
        scrollToPlaceElementInCenter(distributionAndMiscTitle);
        click(distributionAndMiscTitle);
    }

    @Step("Check 'Call class code' not valid anymore")
    public boolean isCallClassCodeNotValid() {
        waitForElementVisibility(callClassCodeNotValid);
        return isElementVisible(callClassCodeNotValid);
    }

    @Step("Click the 'Days Base/Year Base' selector button")
    public void clickDaysBaseYearBaseSelectorOption(String daysBaseYearBaseOption) {
        waitForElementVisibility(daysBaseYearBaseSelectorOption, daysBaseYearBaseOption);
        waitForElementClickable(daysBaseYearBaseSelectorOption, daysBaseYearBaseOption);
        click(daysBaseYearBaseSelectorOption, daysBaseYearBaseOption);
    }

    @Step("Returning list of 'Days Base/Year Base' options")
    public List<String> getDaysBaseYearBaseList() {
        waitForElementVisibility(daysBaseYearBaseList);
        waitForElementClickable(daysBaseYearBaseList);
        return getElementsText(daysBaseYearBaseList);
    }

    @Step("Click the 'Days Base/Year Base' selector button")
    public void clickDaysBaseYearBaseSelectorButton() {
        waitForElementVisibility(daysBaseYearBaseSelectorButton);
        scrollToPlaceElementInCenter(daysBaseYearBaseSelectorButton);
        waitForElementClickable(daysBaseYearBaseSelectorButton);
        click(daysBaseYearBaseSelectorButton);
    }

    @Step("Click the 'Rate Change Frequency' selector button")
    public void clickRateChangeFrequencySelectorOption(String rateChangeFrequencyOption) {
        waitForElementVisibility(rateChangeFrequencySelectorOption, rateChangeFrequencyOption);
        waitForElementClickable(rateChangeFrequencySelectorOption, rateChangeFrequencyOption);
        click(rateChangeFrequencySelectorOption, rateChangeFrequencyOption);
    }

    @Step("Returning list of 'Rate Change Frequency' options")
    public List<String> getRateChangeFrequencyList() {
        waitForElementVisibility(rateChangeFrequencyList);
        waitForElementClickable(rateChangeFrequencyList);
        return getElementsText(rateChangeFrequencyList);
    }

    @Step("Click the 'Rate Change Frequency' selector button")
    public void clickRateChangeFrequencySelectorButton() {
        waitForElementVisibility(rateChangeFrequencySelectorButton);
        scrollToPlaceElementInCenter(rateChangeFrequencySelectorButton);
        waitForElementClickable(rateChangeFrequencySelectorButton);
        click(rateChangeFrequencySelectorButton);
    }

    @Step("Click the 'Payment Change Frequency' selector button")
    public void clickPaymentChangeFrequencySelectorOption(String paymentChangeFrequencyOption) {
        waitForElementVisibility(paymentChangeFrequencySelectorOption, paymentChangeFrequencyOption);
        waitForElementClickable(paymentChangeFrequencySelectorOption, paymentChangeFrequencyOption);
        click(paymentChangeFrequencySelectorOption, paymentChangeFrequencyOption);
    }

    @Step("Returning list of 'Payment Change Frequency' options")
    public List<String> getPaymentChangeFrequencyList() {
        waitForElementVisibility(paymentChangeFrequencyList);
        waitForElementClickable(paymentChangeFrequencyList);
        return getElementsText(paymentChangeFrequencyList);
    }

    @Step("Click the 'Payment Change Frequency' selector button")
    public void clickPaymentChangeFrequencySelectorButton() {
        waitForElementVisibility(paymentChangeFrequencySelectorButton);
        scrollToPlaceElementInCenter(paymentChangeFrequencySelectorButton);
        waitForElementClickable(paymentChangeFrequencySelectorButton);
        click(paymentChangeFrequencySelectorButton);
    }

    @Step("Click the 'Rate Index' selector button")
    public void clickRateIndexSelectorOption(String rateIndexOption) {
        waitForElementVisibility(rateIndexSelectorOption, rateIndexOption);
        waitForElementClickable(rateIndexSelectorOption, rateIndexOption);
        click(rateIndexSelectorOption, rateIndexOption);
    }

    @Step("Returning list of 'Rate Index' options")
    public List<String> getRateIndexList() {
        waitForElementVisibility(rateIndexList);
        waitForElementClickable(rateIndexList);
        return getElementsText(rateIndexList);
    }

    @Step("Click the 'Rate Index' selector button")
    public void clickRateIndexSelectorButton() {
        waitForElementVisibility(rateIndexSelectorButton);
        scrollToPlaceElementInCenter(rateIndexSelectorButton);
        waitForElementClickable(rateIndexSelectorButton);
        click(rateIndexSelectorButton);
    }

    @Step("Click the 'Rate Rounding Method' selector button")
    public void clickRateRoundingMethodSelectorOption(String rateRoundingMethodOption) {
        waitForElementVisibility(rateRoundingMethodSelectorOption, rateRoundingMethodOption);
        waitForElementClickable(rateRoundingMethodSelectorOption, rateRoundingMethodOption);
        click(rateRoundingMethodSelectorOption, rateRoundingMethodOption);
    }

    @Step("Returning list of 'Rate Rounding Method' options")
    public List<String> getRateRoundingMethodList() {
        waitForElementVisibility(rateRoundingMethodList);
        waitForElementClickable(rateRoundingMethodList);
        return getElementsText(rateRoundingMethodList);
    }

    @Step("Click the 'Rate Rounding Method' selector button")
    public void clickRateRoundingMethodSelectorButton() {
        waitForElementVisibility(rateRoundingMethodSelectorButton);
        scrollToPlaceElementInCenter(rateRoundingMethodSelectorButton);
        waitForElementClickable(rateRoundingMethodSelectorButton);
        click(rateRoundingMethodSelectorButton);
    }

    @Step("Get the 'Adjustable Rate' value")
    public String getAdjustableRateValue() {
        return getElementText(adjustableRateValue).trim();
    }

    @Step("Click the 'Adjustable Rate' switch")
    public void clickAdjustableRate() {
        waitForElementClickable(adjustableRate);
        scrollToPlaceElementInCenter(adjustableRate);
        click(adjustableRate);
    }

    @Step("Get the 'Change Payment With Rate Change' value")
    public String getChangePaymentWithRateChangeValue() {
        return getElementText(changePaymentWithRateChangeValue).trim();
    }

    @Step("Click the 'Save' button")
    public void clickSaveAccountButton() {
        waitForElementVisibility(saveAccountButton);
        scrollToPlaceElementInCenter(saveAccountButton);
        waitForElementClickable(saveAccountButton);
        click(saveAccountButton);
    }

    @Step("Is {0} item present in 'Account Type' dropdown")
    public boolean isItemPresentInAccounTypeDropdown(String item) {
        waitForElementVisibility(acccountTypeDropdownItem, item);
        waitForElementClickable(acccountTypeDropdownItem, item);
        return isElementVisible(acccountTypeDropdownItem, item);
    }

    @Step("Pick {0} item from 'Account Type' dropdown")
    public void pickItemFromAccountTypeDropdown(String item) {
        waitForElementVisibility(acccountTypeDropdownItem, item);
        waitForElementClickable(acccountTypeDropdownItem, item);
        click(acccountTypeDropdownItem, item);
    }

    @Step("Click on 'Account Type' field")
    public void clickAccountTypeField() {
        waitForElementVisibility(accountTypeField);
        scrollToPlaceElementInCenter(accountTypeField);
        waitForElementClickable(accountTypeField);
        click(accountTypeField);
    }

    @Step("Click 'Expand All' button")
    public void clickExpandAllButton() {
        waitForElementVisibility(expandAllButton);
        scrollToPlaceElementInCenter(expandAllButton);
        waitForElementClickable(expandAllButton);
        click(expandAllButton);
    }

    @Step("Click 'Collapse All' button")
    public void clickCollapseAllButton() {
        waitForElementVisibility(collapseAllButton);
        scrollToPlaceElementInCenter(collapseAllButton);
        waitForElementClickable(collapseAllButton);
        click(collapseAllButton);
    }

    public String getAccountTypeFieldValu() {
        return getElementText(accountTypeFieldValue);
    }

//    Check if fields is required

    public boolean isAccountTitleFieldRequired() {
        return Boolean.parseBoolean(getElementAttributeValue("required", accountTitle));
    }
}
