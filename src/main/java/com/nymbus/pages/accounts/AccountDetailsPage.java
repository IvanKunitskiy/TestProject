package com.nymbus.pages.accounts;

import com.nymbus.core.base.PageTools;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class AccountDetailsPage extends PageTools {

    /**
     * Tab buttons
     */
    private By maintenanceTab = By.xpath("//a[contains(text(), 'Maintenance')]");
    private By loanInsurancePoliciesTab = By.xpath("//a[contains(text(), 'Loan Insurance Policies')]");
    private By transactionsTab = By.xpath("//a[contains(text(), 'Transactions')]");
    private By instructionsTab = By.xpath("//a[contains(text(), 'Instructions')]");
    private By detailsTab = By.xpath("//a[contains(text(), 'Details')]");
    private By commercialAnalysisTab = By.xpath("//a[contains(text(), 'Commercial Analysis')]");
    private By paymentInfoTab = By.xpath("//a[contains(text(), 'Payment Info')]");

    /**
     * Account actions
     */
    private By editButton = By.xpath("//button[@data-test-id='action-editAccount']");
    private By balanceInquiry = By.xpath("//button[@data-test-id='action-print-receipt']");
    private By activateButton = By.xpath("//button[@data-test-id='action-activateAccount']");
    private By amountDueInquiry = By.xpath("//button[@data-test-id='action-amount-due-inquiry']");


    /**
     * Notifications section
     */
    private By notificationWithText = By.xpath("//section[@ui-view = 'notesNotification']//*[contains(@class, 'notifications-item-text')]//span[contains(text(), '%s')]");

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
    private By interestRate = By.xpath("//tr[@data-config-name='interestrate']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By earningCreditRate = By.xpath("//tr[@data-config-name='earningscreditrate']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By federalWHReason = By.xpath("//tr[@data-config-name='federalwithholdingreason']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By reasonDebitCardChargeWaived = By.xpath("//tr[@data-config-name='reasondebitcardchargeswaived']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By reasonAutoNSFChgWaived = By.xpath("//tr[@data-config-name='reasonautonsfchargeswaived']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By odProtectionAcct = By.xpath("//tr[@data-config-name='overdraftprotectionaccountnumber']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By reasonATMChargeWaived = By.xpath("//tr[@data-config-name='reasonatmchargeswaived']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By automaticOverdraftStatus = By.xpath("//tr[@data-config-name='automaticoverdraftstatus']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By reasonAutoOdChgWaived = By.xpath("//tr[@data-config-name='reasonautoodchargeswaived']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By whenSurchargesRefunded = By.xpath("//tr[@data-config-name='whensurchargesrefunded']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By federalWHPercent = By.xpath("//tr[@data-config-name='federalwithholdingpercent']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By numberOfATMCardsIssued = By.xpath("//tr[@data-config-name='numberofatmcardissued']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By userDefinedField_1 = By.xpath("//tr[@data-config-name='userdefinedfield1']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By userDefinedField_2 = By.xpath("//tr[@data-config-name='userdefinedfield2']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By userDefinedField_3 = By.xpath("//tr[@data-config-name='userdefinedfield3']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By userDefinedField_4 = By.xpath("//tr[@data-config-name='userdefinedfield4']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By iraDistributionFrequency = By.xpath("//tr[@data-config-name='iradistributionfrequency']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By iraDistributionCode = By.xpath("//tr[@data-config-name='iradistributioncode']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By iraDistributionAmount = By.xpath("//tr[@data-config-name='iradistributionamount']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By dateNextIRADistribution = By.xpath("//tr[@data-config-name='datenextiradistribution']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By applyInterestTo = By.xpath("//tr[@data-config-name='codetoapplyinterestto']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By interestType = By.xpath("//tr[@data-config-name='interesttype']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By maturityDate  = By.xpath("//tr[@data-config-name='maturitydate']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By numberOfDebitCardsIssued= By.xpath("//tr[@data-config-name='numberofdebitcardsissued']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By automaticOverdraftLimit= By.xpath("//tr[@data-config-name='automaticoverdraftlimit']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By cashCollDaysBeforeChg= By.xpath("//tr[@data-config-name='cashcollectionnumberofdaysbeforeinterestcharge']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By cashCollInterestChg= By.xpath("//tr[@data-config-name='cashcollectioninterestchargesperstatementcycle']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By cashCollFloat= By.xpath("//tr[@data-config-name='cashcollectionfloat']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By positivePay= By.xpath("//tr[@data-config-name='positivepaycustomer']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By interestFrequency= By.xpath("//tr[@data-config-name='interestfrequency']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By interestFrequencyCode= By.xpath("//tr[@data-config-name='interestfrequencycode']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By correspondingAccount= By.xpath("//tr[@data-config-name='correspondingaccountid']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By printStatementNextUpdate= By.xpath("//tr[@data-config-name='printstatementnextupdate']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By interestPaidYTD= By.xpath("//tr[@data-config-name='interestpaidytd']//span[contains(@class, 'dnTextFixedWidthText')]"); //
    private By interestPaidToDate= By.xpath("//tr[@data-config-name='interestpaidtodate']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By dateInterestPaidThru = By.xpath("//tr[@data-test-id='field-dateinterestpaidthrough']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By dateNextInterest= By.xpath("//tr[@data-config-name='datenextinterestpayment']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By rentalAmount = By.xpath("//tr[@data-config-name='rentalamount']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By discountReason = By.xpath("//tr[@data-config-name='discountreason']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By discountPeriods = By.xpath("//tr[@data-config-name='discountperiods']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By dateNextBilling = By.xpath("//tr[@data-config-name='datenextbiling']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By dateNextDue = By.xpath("//tr[@data-config-name='nextduedate']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By dateLastPayment = By.xpath("//tr[@data-config-name='datelastpayment']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By accountsLink = By.xpath("//a[@data-test-id='go-accounts']");
    private By mailCode = By.xpath("//tr[@data-config-name='mailingcode']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By bankAccountNumberInterestOnCD = By.xpath("//tr[@data-config-name='bankaccountnumberinterestoncd']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By bankRoutingNumberInterestOnCD = By.xpath("//tr[@data-config-name='bankroutingnumberinterestoncd']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By transactionalAccount = By.xpath("//tr[@data-config-name='transactionalaccount']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By currentBalance = By.xpath("//tr[@data-config-name='currentbalance']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By currentBalance1 = By.xpath("//div[@ng-if='accountHeaderConfig.currentbalance.isShow']//p");
    private By availableBalance = By.xpath("//tr[@data-config-name='memopostingbalance']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By availableBalance1 = By.xpath("//div[@ng-if='accountHeaderConfig.memobalance.isShow']//p");
    private By aggregateBalanceYearToDate = By.xpath("//*[@data-config-name='aggregatebalanceytd']//span[contains(@class, 'dnTextFixedWidthText') and contains(@class, 'ng-binding')]");
    private By totalContributionsForLifeOfAccount = By.xpath("//*[@data-config-name='totalContributions']//span[contains(@class, 'dnTextFixedWidthText') and contains(@class, 'ng-binding')]");
    private By dateLastDeposit = By.xpath("//*[@data-config-name='datelastdeposit']//span[contains(@class, 'dnTextFixedWidthText') and contains(@class, 'ng-binding')]");
    private By dateLastActivity = By.xpath("//*[@data-config-name='datelastactivity']//span[contains(@class, 'dnTextFixedWidthText') and contains(@class, 'ng-binding')]");
    private By numberOfDepositsThisStatementCycle = By.xpath("//*[@data-config-name='numberofdepositsthisstatementcycle']//span[contains(@class, 'dnTextFixedWidthText') and contains(@class, 'ng-binding')]");
    private By lastDepositAmount = By.xpath("//*[@data-config-name='lastdepositamount']//span[contains(@class, 'dnTextFixedWidthText') and contains(@class, 'ng-binding')]");
    private By closeAccountButton = By.xpath("//div[@class='topRight']//dn-close-button/button");
    private By reopenButton = By.xpath("//button[@data-test-id='action-activate-non-accrual']");
    private By accountClosedNotification = By.xpath("//span[contains(text(), 'The account is closed')]");
    private By originalBalance = By.xpath("//*[@data-config-name='originalbalance']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By dateLastInterestPay = By.xpath("//*[@data-config-name='datelastinterestpaid']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By dateNextInterestPay = By.xpath("//*[@data-config-name='datenextinterestpayment']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By nextInterestPaymentAmount = By.xpath("//tr[@data-config-name='nextinterestpaymentamount']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By averageBalance = By.xpath("//*[@data-config-name='averagebalance']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By collectedBalance = By.xpath("//*[@data-config-name='collectedbalance']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By accountStatus = By.xpath("//tr[@data-test-id='field-accountstatus']//span[contains(@class, 'ng-binding')]");
    private By activeStatus = By.xpath("//tr[@data-test-id='field-accountstatus']//span[contains(text(), 'Active')]");
    private By accruedInterest = By.xpath("//tr[@data-config-name='accruedinterest']" +
            "//span[contains(@class, 'dnTextFixedWidthText') and contains(@class, 'ng-binding')]");
    private By escrowBalance = By.xpath("//tr[@data-test-id='field-escrowbalance']" +
            "//span[contains(@class, 'dnTextFixedWidthText') and contains(@class, 'ng-binding')]");
    private By dailyInterestFactor = By.xpath("//tr[@data-config-name='dailyinterestfactor']" +
            "//span[contains(@class, 'dnTextFixedWidthText') and contains(@class, 'ng-binding')]");
    private By accruedInterestThisStatementCycle = By.xpath("//tr[@data-config-name='accruedinterestthisstatementcycle']" +
            "//span[contains(@class, 'dnTextFixedWidthText') and contains(@class, 'ng-binding')]");
    private By dateClosed = By.xpath("//*[@data-config-name='dateclosed']" +
            "//span[contains(@class, 'dnTextFixedWidthText') and contains(@class, 'ng-binding')]");
    private By bankruptcyJudgement = By.xpath("//tr[@data-test-id='field-bankruptcyjudgementcode']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By dateOfFirstDeposit = By.xpath("//tr[@data-test-id='field-datefirstdeposit']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By birthDate = By.xpath("//tr[@data-test-id='field-dateofbirth']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By dateDeceased = By.xpath("//tr[@data-test-id='field-datedeceased']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By interestPaidLastYear = By.xpath("//tr[@data-config-name='interestpaidlastyear']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By taxesWithheldYTD = By.xpath("//tr[@data-config-name='taxeswithheldytd']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By termInMonthOrDays = By.xpath("//tr[@data-config-name='terminmonthsordays']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By dailyInterestAccrual = By.xpath("//tr[@data-test-id='field-dailyinterestfactor']//span/span");
    private By overdraftChargedOff = By.xpath("//tr[@data-config-name='overdraftchargedoff']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By currentEffectiveRate = By.xpath("(//tr[@data-test-id='field-currenteffectiverate']//td[2]//span)[2]");
    private By daysBase = By.xpath("//tr[@data-test-id='field-daybaseforinterestrate']//td//span/span");
    private By loanClassCode = By.xpath("//tr[@data-config-name='classtype']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By lateFeeDue = By.xpath("//tr[@data-config-name='latefeedue']//span[contains(@class, 'dnTextFixedWidthText')]");
    private By paymentBilledLeadDays = By.xpath("//tr[@data-config-name='noticedays']/td//span[@text='value']/span");
    private final By changePaymentWithRateChange = By.xpath("//tr[@data-test-id='field-changepaymentwithratechange']/td/dn-field-view/div/div/span/span");
    private final By rateChangeFrequency = By.xpath("//tr[@data-test-id='field-variableratechangefrequency']/td/dn-field-view/div/div/span/span");
    private final By paymentChangeFrequency = By.xpath("//tr[@data-test-id='field-armchangefrequency']/td/dn-field-view/div/div/span/span");
    private final By nextRateChangeDate = By.xpath("//tr[@data-test-id='field-armdatenextirchange']/td/dn-field-view/div/div/span/span");
    private final By rateChangeLeadDays = By.xpath("//tr[@data-test-id='field-ratechangeleaddays']/td/dn-field-view/div/div/span/span");
    private final By nextPaymentChangeDate = By.xpath("//tr[@data-test-id='field-armdatenextpaymentchange']/td/dn-field-view/div/div/span/span");
    private final By paymentChangeLeadDays = By.xpath("//tr[@data-test-id='field-paymentchangeleaddays']/td/dn-field-view/div/div/span/span");
    private final By rateIndex = By.xpath("//tr[@data-test-id='field-armrateindex']/td/dn-field-view/div/div/span/span");
    private final By rateMargin = By.xpath("//tr[@data-test-id='field-interestratebase']/td/dn-field-view/div/div/span/span");
    private final By minRate = By.xpath("//tr[@data-test-id='field-armfloor']/td/dn-field-view/div/div/span/span");
    private final By maxRate = By.xpath("//tr[@data-test-id='field-armceiling']/td/dn-field-view/div/div/span/span");
    private final By maxRateChangeUpDown = By.xpath("//tr[@data-test-id='field-armperiodiccap']/td/dn-field-view/div/div/span/span");
    private final By maxRateLifetimeCap = By.xpath("//tr[@data-test-id='field-armlifetimecap']/td/dn-field-view/div/div/span/span");
    private final By rateRoundingFactor = By.xpath("//tr[@data-test-id='field-armroundingfactor']/td/dn-field-view/div/div/span/span");
    private final By rateRoundingMethod = By.xpath("//tr[@data-test-id='field-armroundingindicator']/td/dn-field-view/div/div/span/span");
    private final By originalInterestRate = By.xpath("//tr[@data-test-id='field-armoriginalinterestrate']/td/dn-field-view/div/div/span/span");
    private final By nextPaymentBilledDueDate = By.xpath("//tr[@data-test-id='field-nextduedate']//span/span");
    private final By numberOfPaymentsReceived = By.xpath("//tr[@data-test-id='field-numberofpaymentsreceived']/td//span/span");
    private final By participationPercentSold = By.xpath("//tr[@data-test-id='field-participationpercentsold']//span/span");
    private final By participantBalance = By.xpath("//tr[@data-test-id='field-participantcurrentbalance']//span/span");
    private final By amountChargedOff = By.xpath("//tr[@data-config-name='amountchargedoff']//span/span");
    private final By unsavedTextAttentionModal = By.xpath("//div[contains(@class, 'dialog-modal')]//p[contains(text(), \"If you leave now, you will lose any text you've entered on this page. Are you sure you want to leave?\")]");
    private final By yesButton = By.xpath("//button/span[contains(text(), 'Yes')]");
    private final By bypassNonAccrualOnDelinquentLoanSwitchYesValue = By.xpath("//tr[@data-test-id='field-bypassNonAccrual']//div/span/span");

    @Step("Click the 'Accounts' link")
    public void clickAccountsLink() {
        waitForElementVisibility(accountsLink);
        waitForElementClickable(accountsLink);
        click(accountsLink);
    }

    /**
     * Details tab
     */
    @Step("Check if 'Unsaved Text' modal present")
    public boolean isUnsavedTextAttentionModalPresent(){
        return isElementVisible(unsavedTextAttentionModal);
    }

    @Step("Click 'Yes' button")
    public void clickYesButton(){
        waitForElementClickable(yesButton);
        click(yesButton);
    }

    @Step("Check if 'Bypass Non-Accrual on Delinquent Loan' switch value equals to 'Yes'")
    public boolean isBypassNonAccrualOnDelinquentLoanSwitchYesValuePresent() {
        return isElementVisible(bypassNonAccrualOnDelinquentLoanSwitchYesValue);
    }

    @Step("Get 'Participation Percent Sold'")
    public String getParticipationPercentSold() {
        waitForElementVisibility(participationPercentSold);
        return getElementText(participationPercentSold).replaceAll("[^0-9]", "");
    }

    @Step("Get 'Next Payment Billed Due Date'")
    public String getNextPaymentBilledDueDate() {
        waitForElementVisibility(nextPaymentBilledDueDate);
        return getElementText(nextPaymentBilledDueDate).trim();
    }

    @Step("Get 'Current effective rate'")
    public String getCurrentEffectiveRate() {
        return getElementText(currentEffectiveRate).replaceAll("[^0-9.]", "");
    }

    @Step("Get 'Date Closed'")
    public String getDateClosed() {
        waitForElementVisibility(dateClosed);
        return getElementText(dateClosed).trim();
    }

    @Step("Get 'Account Status'")
    public String getAccountStatus() {
        waitForElementVisibility(accountStatus);
        return getElementText(accountStatus).trim();
    }

    @Step("Wait for status changed to 'Active'")
    public void waitForStatusChangedToActive() {
        waitForElementVisibility(activeStatus);
    }

    @Step("Get 'Average Balance' value")
    public String getAverageBalanceValue() {
        waitForElementVisibility(averageBalance);
        return getElementText(averageBalance).replaceAll("[^0-9.-]", "");
    }

    @Step("Get 'Amount charged off' value")
    public String getChargedOffAmount() {
        waitForElementVisibility(amountChargedOff);
        String elementText = getElementText(amountChargedOff).replaceAll("[^0-9.]", "");
        return elementText.isEmpty() ? "0.00" : elementText;
    }


    @Step("Get 'Collected Balance' value")
    public String getCollectedBalanceValue() {
        waitForElementVisibility(collectedBalance);
        return getElementText(collectedBalance).replaceAll("[^0-9.-]", "");
    }

    @Step("Get 'Next Interest Payment amount' value")
    public String getNextInterestPaymentAmount() {
        waitForElementVisibility(nextInterestPaymentAmount);
        return getElementText(nextInterestPaymentAmount).replaceAll("[^0-9.-]", "");
    }

    @Step("Get 'Date Next Interest Paid'")
    public String getDateNextInterestPaid() {
        waitForElementVisibility(dateNextInterestPay);
        return getElementText(dateNextInterestPay).trim();
    }

    @Step("Get 'Date Last Interest Paid'")
    public String getDateLastInterestPaid() {
        waitForElementVisibility(dateLastInterestPay);
        return getElementText(dateLastInterestPay).trim();
    }

    @Step("Get 'Original balance' value")
    public String getOriginalBalanceValue() {
        waitForElementVisibility(originalBalance);
        return getElementText(originalBalance).replaceAll("[^0-9.-]", "");
    }

    @Step("Get 'Term In Month Or Days' value")
    public String getTermInMonthOrDays() {
        waitForElementVisibility(termInMonthOrDays);
        return getElementText(termInMonthOrDays).trim();
    }

    @Step("Get 'Daily Interest Accrual' value")
    public String getDailyInterestAccrual() {
        waitForElementVisibility(dailyInterestAccrual);
        return getElementText(dailyInterestAccrual).replaceAll("[^0-9.-]", "");
    }

    @Step("Get 'Last Deposit Amount' value")
    public String getLastDepositAmountValue() {
        waitForElementVisibility(lastDepositAmount);
        return getElementText(lastDepositAmount).replaceAll("[^0-9.-]", "");
    }

    @Step("Get 'Number Of Deposits This Statement Cycle' value")
    public String getNumberOfDepositsThisStatementCycleValue() {
        waitForElementVisibility(numberOfDepositsThisStatementCycle);
        return getElementText(numberOfDepositsThisStatementCycle).trim();
    }

    @Step("Get 'Date Last Activity' value")
    public String getDateLastActivityValue() {
        waitForElementVisibility(dateLastActivity);
        return getElementText(dateLastActivity).trim();
    }

    @Step("Get 'Date Last Deposit' value")
    public String getDateLastDepositValue() {
        waitForElementVisibility(dateLastDeposit);
        return getElementText(dateLastDeposit).trim();
    }

    @Step("Get 'CurrentBalance' value")
    public String getCurrentBalance() {
        waitForElementVisibility(currentBalance);
        String currentBalanceValue = getElementText(currentBalance).trim();
        return currentBalanceValue.replaceAll("[^0-9.-]", "");
    }

    @Step("Get 'Participant Balance' value")
    public String getParticipantBalance() {
        waitForElementVisibility(participantBalance);
        return getElementText(participantBalance).replaceAll("[^0-9.-]", "");
    }

    @Step("Get 'CurrentBalance' value from header menu")
    public String getCurrentBalanceFromHeaderMenu() {
        waitForElementVisibility(currentBalance1);
        String currentBalanceValue = getElementText(currentBalance1).trim();
        return currentBalanceValue.replaceAll("[^0-9.-]", "");
    }

    @Step("Get 'Number Of Payments Received' value from header menu")
    public String getNumberOfPaymentsReceived() {
        waitForElementVisibility(numberOfPaymentsReceived);
        return getElementText(numberOfPaymentsReceived).trim();
    }

    @Step("Get 'Accrued Interest' value")
    public String getAccruedInterest() {
        waitForElementVisibility(accruedInterest);
        String accruedInterestValue = getElementText(accruedInterest).trim();
        return accruedInterestValue.replaceAll("[^0-9.-]", "");
    }

    @Step("Get 'Escrow Balance' value")
    public String getEscrowBalance() {
        waitForElementVisibility(escrowBalance);
        String escrowBalanceValue = getElementText(escrowBalance).trim();
        return escrowBalanceValue.isEmpty()
                ? "0.00"
                : escrowBalanceValue.replaceAll("[^0-9.-]", "");
    }

    @Step("Get 'Daily Interest Factor' value")
    public String getDailyInterestFactor() {
        waitForElementVisibility(dailyInterestFactor);
        return getElementText(dailyInterestFactor).replaceAll("[^0-9.]", "");
    }

    @Step("Get 'Accrued Interest this statement cycle' value")
    public String getAccruedInterestThisStatementCycle() {
        waitForElementVisibility(accruedInterestThisStatementCycle);
        String accruedInterestValue = getElementText(accruedInterestThisStatementCycle).trim();
        return accruedInterestValue.replaceAll("[^0-9.-]", "");
    }

    @Step("Get 'Accrued Interest' value with period")
    public String getAccruedInterestWithPeriod() {
        waitForElementVisibility(accruedInterest);
        return getElementText(accruedInterest).replaceAll("[^0-9.]", "");
    }

    @Step("Get 'Available Balance' value")
    public String getAvailableBalance() {
        waitForElementVisibility(availableBalance);
        String availableBalanceValue = getElementText(availableBalance).trim();
        return availableBalanceValue.replaceAll("[^0-9.-]", "");
    }

    @Step("Get 'Available Balance' value from header menu")
    public String getAvailableBalanceFromHeaderMenu() {
        waitForElementVisibility(availableBalance1);
        String availableBalanceValue = getElementText(availableBalance1).trim();
        return availableBalanceValue.replaceAll("[^0-9.-]", "");
    }

    @Step("Get 'Aggregate Balance Year To Date' value")
    public String getAggregateBalanceYearToDate() {
        scrollToElement(aggregateBalanceYearToDate);
        waitForElementVisibility(aggregateBalanceYearToDate);
        String aggregateBalanceYearToDateValue = getElementText(aggregateBalanceYearToDate).trim();
        return aggregateBalanceYearToDateValue.replaceAll("[^0-9.-]", "");
    }

    @Step("Get 'Total Contributions For Life Of Account' value")
    public String getTotalContributionsForLifeOfAccount() {
        scrollToElement(totalContributionsForLifeOfAccount);
        waitForElementVisibility(totalContributionsForLifeOfAccount);
        String totalContributionsForLifeOfAccountValue = getElementText(totalContributionsForLifeOfAccount).trim();
        return totalContributionsForLifeOfAccountValue.replaceAll("[^0-9.-]", "");
    }

    @Step("Get 'Loan Class Code' value")
    public String getLoanClassCode() {
        scrollToElement(loanClassCode);
        waitForElementVisibility(loanClassCode);
        return getElementText(loanClassCode).trim();
    }

    @Step("Get 'Late Fee Due' value")
    public String getLateFeeDue() {
        scrollToElement(lateFeeDue);
        waitForElementVisibility(lateFeeDue);
        return getElementText(lateFeeDue).trim();
    }

    @Step("Check if 'Account Closed' notification visible")
    public boolean isAccountClosedNotificationVisible() {
        waitForElementVisibility(accountClosedNotification);
        return isElementVisible(accountClosedNotification);
    }

    @Step("Check if 'Re-Open' button visible")
    public boolean isReOpenButtonVisible() {
        waitForElementClickable(reopenButton);
        return isElementVisible(reopenButton);
    }

    @Step("Check if 'Close Account' button visible")
    public boolean isCloseAccountButtonVisible() {
        waitForElementClickable(closeAccountButton);
        return isElementVisible(closeAccountButton);
    }

    @Step("Click 'Close Account' button")
    public void clickCloseAccountButton() {
        waitForElementClickable(closeAccountButton);
        click(closeAccountButton);
    }

    @Step("Get 'Date Next Interest' value")
    public String getDateNextInterest() {
        waitForElementVisibility(dateNextInterest);
        return getElementText(dateNextInterest);
    }

    @Step("Get 'Date interest Thru' value")
    public String getDateInterestPaidThru(){
        waitForElementVisibility(dateInterestPaidThru);
        return getElementText(dateInterestPaidThru);
    }

    @Step("Get 'Maturity Date' value")
    public String getMaturityDate() {
        waitForElementVisibility(maturityDate);
        return getElementText(maturityDate);
    }

    @Step("Get 'Apply Interest To' value")
    public String getApplyInterestTo() {
        waitForElementVisibility(applyInterestTo);
        return getElementText(applyInterestTo);
    }

    @Step("Get 'Interest Type' value")
    public String getInterestType() {
        waitForElementVisibility(interestType);
        return getElementText(interestType);
    }

    @Step("Get 'IRA Distribution Frequency' value")
    public String getIraDistributionFrequency() {
        waitForElementVisibility(iraDistributionFrequency);
        return getElementText(iraDistributionFrequency);
    }

    @Step("Get 'IRA Distribution Code' value")
    public String getIraDistributionCode() {
        waitForElementVisibility(iraDistributionCode);
        return getElementText(iraDistributionCode);
    }

    @Step("Get 'Ira Distribution Amount' value")
    public String getIraDistributionAmount() {
        waitForElementVisibility(iraDistributionAmount);
        return getElementText(iraDistributionAmount).replaceAll("[^0-9]", "");
    }

    @Step("Get 'Date Next IRA Distribution' value")
    public String getDateNextIRADistribution() {
        waitForElementVisibility(dateNextIRADistribution);
        return getElementText(dateNextIRADistribution);
    }

    @Step("Get 'Date Of First Deposit' value")
    public String getDateOfFirstDeposit() {
        waitForElementVisibility(dateOfFirstDeposit);
        return getElementText(dateOfFirstDeposit);
    }

    @Step("Get 'Birth Date' value")
    public String getBirthDate() {
        waitForElementVisibility(birthDate);
        return getElementText(birthDate);
    }

    @Step("Get 'Date Deceased' value")
    public String getDateDeceased() {
        waitForElementVisibility(dateDeceased);
        return getElementText(dateDeceased);
    }

    @Step("Get 'Print Statement Next Update' value")
    public String getPrintStatementNextUpdate() {
        waitForElementVisibility(printStatementNextUpdate);
        return getElementText(printStatementNextUpdate);
    }

    @Step("Get 'Interest Paid YTD' value")
    public String getInterestPaidYTD() {
        waitForElementVisibility(interestPaidYTD);
        return getElementText(interestPaidYTD).replaceAll("[^0-9.]", "");
    }

    @Step("Get 'Interest Paid To Date' value")
    public String getInterestPaidToDate() {
        waitForElementVisibility(interestPaidToDate);
        String interestPaidToDateToReturn = getElementText(interestPaidToDate).replaceAll("[^0-9.]", "");
        return interestPaidToDateToReturn.isEmpty() ? "0.0" : interestPaidToDateToReturn;
    }

    @Step("Get 'Interest Paid Last Year' value")
    public String getInterestPaidLastYear() {
        waitForElementVisibility(interestPaidLastYear);
        return getElementText(interestPaidLastYear).replaceAll("[^0-9.]", "");
    }

    @Step("Get 'Taxes Withheld YTD' value")
    public String getTaxesWithheldYTD() {
        waitForElementVisibility(taxesWithheldYTD);
        return getElementText(taxesWithheldYTD).replaceAll("[^0-9.]", "");
    }

    @Step("Get 'Overdraft Charged Off' value")
    public String getOverdraftChargedOff() {
        waitForElementVisibility(overdraftChargedOff);
        return getElementText(overdraftChargedOff).replaceAll("[^0-9.]", "");
    }

    @Step("Get 'Corresponding Account' value")
    public String getCorrespondingAccount() {
        waitForElementVisibility(correspondingAccount);
        return getElementText(correspondingAccount);
    }

    @Step("Get 'Interest Frequency' value")
    public String getInterestFrequency() {
        waitForElementVisibility(interestFrequency);
        return getElementText(interestFrequency);
    }

    @Step("Get 'Interest Frequency Code' value")
    public String getInterestFrequencyCode() {
        waitForElementVisibility(interestFrequencyCode);
        return getElementText(interestFrequencyCode);
    }

    @Step("Get 'Positive Pay' value")
    public String getPositivePay() {
        waitForElementVisibility(positivePay);
        return getElementText(positivePay);
    }

    @Step("Get 'Cash Coll Float' value")
    public String getCashCollFloat() {
        waitForElementVisibility(cashCollFloat);
        String cashCollFloatValue = getElementText(cashCollFloat);
        return cashCollFloatValue.replaceAll("[^0-9]", "");
    }

    @Step("Get 'Cash Coll Interest Chg' value")
    public String getCashCollInterestChg() {
        waitForElementVisibility(cashCollInterestChg);
        String cashCollValue = getElementText(cashCollInterestChg);
        return cashCollValue.replaceAll("[^0-9]", "");
    }

    @Step("Get 'Cash Coll Days Before Chg' value")
    public String getCashCollDaysBeforeChg() {
        waitForElementVisibility(cashCollDaysBeforeChg);
        return getElementText(cashCollDaysBeforeChg);
    }

    @Step("Get 'Automatic Overdraft Limit' value")
    public String getAutomaticOverdraftLimit() {
        waitForElementVisibility(automaticOverdraftLimit);
        String limitValue = getElementText(automaticOverdraftLimit);
        return limitValue.replaceAll("[^0-9]", "");
    }

    @Step("Get 'Number Of Debit Cards Issued' value")
    public String getNumberOfDebitCardsIssued() {
        waitForElementVisibility(numberOfDebitCardsIssued);
        return getElementText(numberOfDebitCardsIssued);
    }

    @Step("Get 'Mail Code' value")
    public String getMailCodeValue() {
        waitForElementVisibility(mailCode);
        return getElementText(mailCode);
    }

    @Step("Get 'User Defined Field 1' value")
    public String getUserDefinedField_4() {
        waitForElementVisibility(userDefinedField_4);
        return getElementText(userDefinedField_4);
    }

    @Step("Get 'User Defined Field 3' value")
    public String getUserDefinedField_3() {
        waitForElementVisibility(userDefinedField_3);
        return getElementText(userDefinedField_3);
    }

    @Step("Get 'User Defined Field 2' value")
    public String getUserDefinedField_2() {
        waitForElementVisibility(userDefinedField_2);
        return getElementText(userDefinedField_2);
    }

    @Step("Get 'User Defined Field 1' value")
    public String getUserDefinedField_1() {
        waitForElementVisibility(userDefinedField_1);
        return getElementText(userDefinedField_1);
    }

    @Step("Get 'Number Of ATM Cards Issued' value")
    public String getNumberOfATMCardsIssued() {
        waitForElementVisibility(numberOfATMCardsIssued);
        return getElementText(numberOfATMCardsIssued);
    }

    @Step("Get 'Federal WH Percent' value")
    public String getFederalWHPercent() {
        waitForElementVisibility(federalWHPercent);
        String whPercent = getElementText(federalWHPercent);
        return whPercent.substring(0, whPercent.length() - 1);
    }

    @Step("Get 'When Surcharges Refunded' value")
    public String getWhenSurchargesRefunded() {
        waitForElementVisibility(whenSurchargesRefunded);
        return getElementText(whenSurchargesRefunded);
    }

    @Step("Get 'Reason Auto Od Chg Waived' value")
    public String getReasonAutoOdChgWaived() {
        waitForElementVisibility(reasonAutoOdChgWaived);
        return getElementText(reasonAutoOdChgWaived);
    }

    @Step("Get 'Automatic Overdraft Status' value")
    public String getAutomaticOverdraftStatus() {
        waitForElementVisibility(automaticOverdraftStatus);
        return getElementText(automaticOverdraftStatus);
    }

    @Step("Get account 'Reason Debit Card Charge Waived' value")
    public String getReasonDebitCardChargeWaived() {
        waitForElementVisibility(reasonDebitCardChargeWaived);
        return getElementText(reasonDebitCardChargeWaived);
    }

    @Step("Get account 'Bankruptcy Judgement' value")
    public String getBankruptcyJudgement() {
        waitForElementVisibility(bankruptcyJudgement);
        return getElementText(bankruptcyJudgement);
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

    @Step("Click the 'Balance inquiry' button")
    public void clickBalanceInquiry() {
        waitForElementClickable(balanceInquiry);
        click(balanceInquiry);
    }

    @Step("Click the 'Activate' button")
    public void clickActivateButton() {
        waitForElementClickable(activateButton);
        click(activateButton);
    }

    @Step("Click the 'Amount Due Inquiry' button")
    public void clickAmountDueInquiryButton() {
        waitForElementClickable(amountDueInquiry);
        click(amountDueInquiry);
    }

    @Step("Get account 'Earning Credit Rate' value")
    public String getEarningCreditRate() {
        waitForElementVisibility(earningCreditRate);
        return getElementText(earningCreditRate).replaceAll("[^0-9]", "");
    }

    @Step("Get account 'Statement Flag' value")
    public String getInterestRateValue() {
        waitForElementVisibility(interestRate);
        return getElementText(interestRate).replaceAll("[^0-9.]", "");
    }

    @Step("Get 'Bank Account Number Interest On CD' value")
    public String getBankAccountNumberInterestOnCD() {
        waitForElementVisibility(bankAccountNumberInterestOnCD);
        return getElementText(bankAccountNumberInterestOnCD);
    }

    @Step("Get 'Bank Routing Number Interest On CD' value")
    public String getBankRoutingNumberInterestOnCD() {
        waitForElementVisibility(bankRoutingNumberInterestOnCD);
        return getElementText(bankRoutingNumberInterestOnCD);
    }

    @Step("Get 'Transactional Account' value")
    public String getTransactionalAccount() {
        waitForElementVisibility(transactionalAccount);
        return getElementText(transactionalAccount).trim().toUpperCase();
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

    @Step("Get account 'Date next Billing' value")
    public String getBillingNextDate() {
        waitForElementVisibility(dateNextBilling);
        return getElementText(dateNextBilling);
    }

    @Step("Get account 'Date next Due' value")
    public String getNextDueDate() {
        waitForElementVisibility(dateNextDue);
        return getElementText(dateNextDue);
    }

    @Step("Get account 'Date last payment' value")
    public String getDateLastPayment() {
        waitForElementVisibility(dateLastPayment);
        return getElementText(dateLastPayment);
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

    @Step("Get account 'Days Base' value")
    public String getDaysBaseYearBase() {
        waitForElementVisibility(daysBase);
        return getElementText(daysBase);
    }

    @Step("Get account 'Product' value")
    public String getProductValue() {
        waitForElementVisibility(product);
        return getElementText(product);
    }

    @Step("Get account 'Payment Billed Lead Days' value")
    public String getPaymentBilledLeadDays() {
        waitForElementVisibility(paymentBilledLeadDays);
        return getElementText(paymentBilledLeadDays);
    }

    @Step("Wait for 'Full Profile' button is visible")
    public void waitForFullProfileButton() {
        waitForElementVisibility(fullProfileButton);
        waitForElementClickable(fullProfileButton);
    }

    @Step("Wait for 'Edit' button is visible")
    public void waitForEditButton() {
        waitForElementVisibility(editButton);
        waitForElementClickable(editButton);
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

    @Step("Get account 'Rental Amount' value")
    public String getRentalAmount() {
        waitForElementVisibility(rentalAmount);
        return getElementText(rentalAmount).replaceAll("[^0-9.,]", "");
    }

    @Step("Get account 'Discount Reason' value")
    public String getDiscountReason() {
        waitForElementVisibility(discountReason);
        return getElementText(discountReason);
    }

    @Step("Get account 'Discount Periods' value")
    public String getDiscountPeriods() {
        waitForElementVisibility(discountPeriods);
        return getElementText(discountPeriods);
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

    @Step("Check if 'More' button is visible")
    public boolean isMoreButtonVisible() {
        return isElementVisible(moreButton);
    }


    @Step("Check if 'Less' button is visible")
    public boolean isLessButtonVisible() {
        return isElementVisible(lessButton);
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
        SelenideTools.sleep(Constants.MICRO_TIMEOUT);
        waitForElementVisibility(maintenanceTab);
        waitForElementClickable(maintenanceTab);
        click(maintenanceTab);
    }

    @Step("Click the 'Loan Insurance Policies' tab")
    public void clickLoanInsurancePoliciesTab() {
        SelenideTools.sleep(Constants.MICRO_TIMEOUT);
        waitForElementVisibility(loanInsurancePoliciesTab);
        waitForElementClickable(loanInsurancePoliciesTab);
        click(loanInsurancePoliciesTab);
    }

    @Step("Click the 'Transactions' tab")
    public void clickTransactionsTab() {
        waitForElementClickable(transactionsTab);
        click(transactionsTab);
    }

    @Step("Click the 'Instructions' tab")
    public void clickInstructionsTab() {
        waitForElementClickable(instructionsTab);
        click(instructionsTab);
    }

    @Step("Click the 'Details' tab")
    public void clickDetailsTab() {
        waitForElementClickable(detailsTab);
        click(detailsTab);
    }

    @Step("Click the 'Commercial Analysis' tab")
    public void clickCommercialAnalysisTab() {
        waitForElementClickable(commercialAnalysisTab);
        click(commercialAnalysisTab);
    }

    @Step("Click the 'Payment Info' tab")
    public void clickPaymentInfoTab() {
        waitForElementClickable(paymentInfoTab);
        click(paymentInfoTab);
    }

    @Step("Is notification with text {0} visible")
    public boolean isNotificationWithTextVisible(String text) {
        return isElementVisible(notificationWithText, text);
    }

    @Step("Wait for 'Re-Open button")
    public void waitForReopenButton() {
        waitForElementVisibility(reopenButton);
    }

    @Step("Check if 'Edit' button is visible")
    public boolean isEditButtonVisible() {
        return isElementVisible(editButton);
    }

    @Step("Check if 'Activate' button is visible")
    public boolean isActivateButtonVisible() {
        waitForElementVisibility(activateButton);
        return isElementVisible(activateButton);
    }


    /**
     * Grouping sections
     */

    private final By balanceAndInterest = By.xpath("//span[text()='Balance and Interest']");
    private final By accountSettings = By.xpath("//span[text()='Account Settings']");
    private final By creditLimit = By.xpath("//span[text()='Credit Limit']");
    private final By ratePaymentChange = By.xpath("//span[text()='Rate/Payment Change']");
    private final By lateCharge = By.xpath("//span[text()='Late Charge']");
    private final By loanCodesReporting = By.xpath("//span[text()='Loan Codes & Reporting']");
    private final By userDefinedFields = By.xpath("//span[text()='User Defined Fields']");
    private final By accountInfoStatistics = By.xpath("//span[text()='Account Info/Statistics']");
    private final By collections = By.xpath("//span[text()='Collections']");
    private final By otherLoanSettings = By.xpath("//span[text()='Other Loan Settings']");

    @Step("Check if 'Balance and Interest' group is visible")
    public boolean isBalanceAndInterestVisible() {
        waitForElementVisibility(balanceAndInterest);
        return isElementVisible(balanceAndInterest);
    }

    @Step("Check if 'Account Settings' group is visible")
    public boolean isAccountSettingsVisible() {
        waitForElementVisibility(accountSettings);
        return isElementVisible(accountSettings);
    }


    @Step("Check if 'Credit Limit' group is visible")
    public boolean isCreditLimitVisible() {
        waitForElementVisibility(creditLimit);
        return isElementVisible(creditLimit);
    }

    @Step("Check if 'Rate/Payment Change' group is visible")
    public boolean isRatePaymentChangeVisible() {
        waitForElementVisibility(ratePaymentChange);
        return isElementVisible(ratePaymentChange);
    }

    @Step("Check if 'Late Charge' group is visible")
    public boolean isLateChargeVisible() {
        waitForElementVisibility(lateCharge);
        return isElementVisible(lateCharge);
    }

    @Step("Check if 'Loan Codes & Reporting' group is visible")
    public boolean isLoanCodesReportingVisible() {
        waitForElementVisibility(loanCodesReporting);
        return isElementVisible(loanCodesReporting);
    }

    @Step("Check if 'User Defined Fields' group is visible")
    public boolean isUserDefinedFieldsVisible() {
        waitForElementVisibility(userDefinedFields);
        return isElementVisible(userDefinedFields);
    }

    @Step("Check if 'Account Info/Statistics' group is visible")
    public boolean isAccountInfoStatisticsVisible() {
        waitForElementVisibility(accountInfoStatistics);
        return isElementVisible(accountInfoStatistics);
    }

    @Step("Check if 'Collections' group is visible")
    public boolean isCollectionsVisible() {
        waitForElementVisibility(collections);
        return isElementVisible(collections);
    }

    @Step("Check if 'Other Loan Settings' group is visible")
    public boolean isOtherLoanSettingsVisible() {
        waitForElementVisibility(otherLoanSettings);
        return isElementVisible(otherLoanSettings);
    }

    @Step("Get the 'Change Payment with Rate Change' value")
    public String getChangePaymentWithRateChange() {
        return getElementText(changePaymentWithRateChange);
    }

    @Step("Get the 'Rate Change Frequency' value")
    public String getRateChangeFrequency() {
        return getElementText(rateChangeFrequency);
    }

    @Step("Get the 'Payment Change Frequency' value")
    public String getPaymentChangeFrequency() {
        return getElementText(paymentChangeFrequency);
    }

    @Step("Get the 'Next Rate Change Date' value")
    public String getNextRateChangeDate() {
        return getElementText(nextRateChangeDate);
    }

    @Step("Get the 'Rate Change Lead Days' value")
    public String getRateChangeLeadDays() {
        return getElementText(rateChangeLeadDays);
    }

    @Step("Get the 'Next Payment Change Date' value")
    public String getNextPaymentChangeDate() {
        return getElementText(nextPaymentChangeDate);
    }

    @Step("Get the 'Payment Change Lead Days' value")
    public String getPaymentChangeLeadDays() {
        return getElementText(paymentChangeLeadDays);
    }

    @Step("Get the 'Rate index' value")
    public String getRateIndex() {
        return getElementText(rateIndex);
    }

    @Step("Get the 'Rate Margin' value")
    public String getRateMargin() {
        return getElementText(rateMargin).replaceAll("[^0-9]", "");
    }

    @Step("Get the 'Min Rate' value")
    public String getMinRate() {
        return getElementText(minRate).replaceAll("[^0-9]", "");
    }

    @Step("Get the 'Max Rate' value")
    public String getMaxRate() {
        return getElementText(maxRate).replaceAll("[^0-9]", "");
    }

    @Step("Get the 'Max rate change up/down' value")
    public String getMaxRateChangeUpDown() {
        return getElementText(maxRateChangeUpDown).replaceAll("[^0-9]", "");
    }

    @Step("Get the 'Max rate lifetime cap' value")
    public String getMaxRateLifetimeCap() {
        return getElementText(maxRateLifetimeCap).replaceAll("[^0-9]", "");
    }

    @Step("Get the 'Rate rounding factor' value")
    public String getRateRoundingFactor() {
        return getElementText(rateRoundingFactor).replaceAll("[^0-9]", "");
    }

    @Step("Get the 'Rate Rounding Method' value")
    public String getRateRoundingMethod() {
        return getElementText(rateRoundingMethod);
    }

    @Step("Get the 'Original interest rate' value")
    public String getOriginalInterestRate() {
        return getElementText(originalInterestRate).replaceAll("[^0-9]", "");
    }

}