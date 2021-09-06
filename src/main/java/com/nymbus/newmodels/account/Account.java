package com.nymbus.newmodels.account;

import com.nymbus.actions.loans.DaysBaseYearBase;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import com.nymbus.core.utils.Generator;
import com.nymbus.newmodels.account.loanaccount.*;
import com.nymbus.newmodels.account.product.ProductType;
import com.nymbus.newmodels.client.other.account.AccountType;
import com.nymbus.newmodels.client.other.account.InterestFrequency;

import java.sql.Timestamp;

public class Account {

    private String addNewOption;
    private String productType;
    private String product;
    private String boxSize;
    private String rentalAmount;
    private String accountNumber;
    private String accountTitle;
    private String accountType;
    private String currentOfficer;
    private String originatingOfficer;
    private String callClassCode;
    private String accountAnalysis;
    private String chargeOrAnalyze;
    private String optInOutDate;
    private String interestRate;
    private String earningCreditRate;
    private String numberOfDebitCardsIssued;
    private String cashCollDaysBeforeChg;
    private String cashCollInterestRate;
    private String cashCollInterestChg;
    private String cashCollFloat;
    private String positivePay;
    private String federalWHReason;
    private String federalWHPercent;
    private String reasonATMChargeWaived;
    private String odProtectionAcct;
    private String reasonAutoNSFChgWaived;
    private String reasonDebitCardChargeWaived;
    private String automaticOverdraftStatus;
    private String reasonAutoOdChgWaived;
    private String whenSurchargesRefunded;
    private String numberOfATMCardsIssued;
    private String mailCode;
    private String dateOpened;
    private String bankBranch;
    private String statementCycle;
    private String correspondingAccount;
    private String userDefinedField_1;
    private String userDefinedField_2;
    private String userDefinedField_3;
    private String userDefinedField_4;
    private String discount;
    private String discountPeriods;
    private String discountReason;
    private String automaticOverdraftLimit;
    private String interestFrequency;
    private String printStatementNextUpdate;
    private String interestPaidYTD;
    private String iraDistributionFrequency;
    private String iraDistributionCode;
    private String iraDistributionAccountNumber;
    private String iraDistributionAmount;
    private String dateNextIRADistribution;
    private String applyInterestTo;
    private String interestType;
    private String termType;
    private String autoRenewable;
    private String accountHolder;
    private String maturityDate;
    private String dateNextInterest;
    private String transactionalAccount;
    private String dateLastAccess;
    private String bankRoutingNumberInterestOnCD;
    private String bankAccountNumberInterestOnCD;
    private String bankruptcyJudgement;
    private String dateOfFirstDeposit;
    private String dateDeceased;
    private String birthDate;
    private String accruedInterest;
    private String interestPaidLastYear;
    private String taxesWithheldYTD;
    private String originalBalance;
    private String term;
    private String dailyInterestAccrual;
    private String nextInterestPaymentAmount;
    private String minTerm;
    private String loanClassCode;
    private String paymentAmount;
    private String paymentAmountType;
    private String paymentFrequency;
    private String currentEffectiveRate;
    private String nextPaymentBilledDueDate;
    private String interestMethod;
    private String daysBaseYearBase;
    private String paymentBilledLeadDays;
    private String rateChangeFrequency;
    private String cycleCode;
    private String commitmentTypeAmt;
    private String dateFirstPaymentDue;
    private String escrow;
    private String paymentChangeFrequency;
    private String rateIndex;
    private String rateRoundingMethod;
    private String nextRateChangeDate;
    private String rateChangeLeadDays;
    private String nextPaymentChangeDate;
    private String paymentChangeLeadDays;
    private String rateMargin;
    private String minRate;
    private String maxRate;
    private String maxRateChangeUpDown;
    private String maxRateLifetimeCap;
    private String rateRoundingFactor;
    private String originalInterestRate;
    private boolean cycleLoan = false;
    private boolean adjustableRate = false;
    private boolean currentEffectiveRateIsTeaser = false;

    public Account setLoanAccountData() {
        Account account = new Account();

        account.setAddNewOption("Account");
        account.setAccountType(AccountType.INDIVIDUAL.getAccountType());
        account.setProductType(ProductType.LOAN_ACCOUNT.getProductType());
        account.setAccountNumber(Generator.genAccountNumber());
        account.setDateOpened(DateTime.getDateMinusMonth(DateTime.getLocalDateOfPattern("MM/dd/yyyy"), 1));
        account.setLoanClassCode(LoanClassCode.COMMERCIAL_LOAN.getLoanClassCode());
        account.setPaymentAmount("1001.00");
        account.setPaymentAmountType(PaymentAmountType.PRIN_AND_INT.getPaymentAmountType());
        account.setPaymentFrequency(PaymentFrequency.MONTHLY.getPaymentFrequency());
        account.setPaymentBilledLeadDays(String.valueOf(Generator.genInt(6, 30))); // less than number of days in Payment Frequency (e.g. Payment Frequency = Monthly, then range from 1 to 31)
        account.setNextPaymentBilledDueDate(DateTime.getDatePlusMonth(account.getDateOpened(), 1));
        account.setDateFirstPaymentDue(DateTime.getLocalDatePlusMonthsWithPatternAndLastDay(account.getDateOpened(), 1, "MM/dd/yyyy"));
        account.setCurrentEffectiveRate(String.valueOf(10));
        account.setInterestMethod(InterestMethod.SIMPLE_INTEREST.getInterestMethod());
        account.setTerm(String.valueOf(12));
        account.setDaysBaseYearBase(DaysBaseYearBase.DAY_YEAR_365_365.getDaysBaseYearBase());

        /* Edit loan account fields */
        account.setRateChangeFrequency(RateChangeFrequency.TWO_YEARS.getRateChangeFrequency());
        account.setPaymentChangeFrequency(PaymentChangeFrequency.NONE.getPaymentChangeFrequency());
        account.setNextRateChangeDate(DateTime.getDateWithNMonthAdded(WebAdminActions.loginActions().getSystemDate(), "MM/dd/yyyy", Generator.genInt(1, 12)));
        account.setRateChangeLeadDays(Generator.getRandomStringNumber(1));
        account.setNextPaymentChangeDate(DateTime.getDateWithNMonthAdded(WebAdminActions.loginActions().getSystemDate(), "MM/dd/yyyy", Generator.genInt(1, 12)));
        account.setPaymentChangeLeadDays(Generator.getRandomIntMinMaxInclusiveStringValue(13, 99));
        account.setRateMargin(Generator.getRandomIntMinMaxInclusiveStringValue(10, 99));
        account.setMinRate(Generator.getRandomStringNumber(1));
        account.setMaxRate(Generator.getRandomIntMinMaxInclusiveStringValue(10, 99));
        account.setMaxRateChangeUpDown(Generator.getRandomIntMinMaxInclusiveStringValue(10, 99));
        account.setMaxRateLifetimeCap(Generator.getRandomIntMinMaxInclusiveStringValue(10, 99));
        account.setRateRoundingFactor(RateRoundingFactor.ZERO_SEVENTY_FIVE_THOUSANDTH.getRateRoundingFactor());
        account.setOriginalInterestRate(Generator.getRandomIntMinMaxInclusiveStringValue(10, 99));
        account.setRateIndex("Wall Street Journal Prime");

        return account;
    }

    public Account setCdAccountData() {
        Account account = new Account();

        account.setAddNewOption("Account");
        account.setProductType(ProductType.CD_ACCOUNT.getProductType());
        account.setAutoRenewable("YES");
        account.setInterestFrequency(InterestFrequency.QUARTERLY.getInterestFrequency());
        account.setInterestType("Simple");
        account.setApplyInterestTo("Remain in Account");
        account.setAccountHolder("Owner");
        account.setAccountTitle(Generator.genString(5));
        account.setDateOpened(DateTime.getDateMinusDays(WebAdminActions.loginActions().getSystemDate(), Constants.DAYS_BEFORE_SYSTEM_DATE));
        account.setAccountNumber(Generator.genAccountNumber());
        account.setInterestRate(String.valueOf(Generator.genInt(0, 100)));
        account.setCashCollInterestRate(String.valueOf(new Timestamp(System.currentTimeMillis()).getTime()).substring(4));
        account.setFederalWHPercent(String.valueOf(Generator.genInt(0, 100)));
        account.setUserDefinedField_1(Generator.genString(5));
        account.setUserDefinedField_2(Generator.genString(5));
        account.setUserDefinedField_3(Generator.genString(5));
        account.setUserDefinedField_4(Generator.genString(5));

        return account;
    }

    public Account setCdIraAccountData() {
        Account account = new Account();

        account.setAddNewOption("Account");
        account.setProductType(ProductType.CD_ACCOUNT.getProductType());
        account.setAccountTitle(Generator.genString(5));
        account.setDateOpened(DateTime.getDateMinusDays(WebAdminActions.loginActions().getSystemDate(), Constants.DAYS_BEFORE_SYSTEM_DATE));
        account.setAccountNumber(Generator.genAccountNumber());
        account.setIraDistributionCode("No dist");
        account.setIraDistributionFrequency("No Dist");
        account.setTransactionalAccount("NO");
        account.setAutoRenewable("YES");
        account.setAccountHolder("Owner");
        account.setTermType(getCDIRATerms(Constants.getEnvironment()));
        account.setInterestRate(String.valueOf(Generator.genFloat(0.001, 99.999, 1)));
        account.setInterestFrequency("Quarterly");
        account.setApplyInterestTo("Remain in Account");
        account.setBankRoutingNumberInterestOnCD("102101645");
        account.setBankAccountNumberInterestOnCD("102101645");
        account.setInterestRate(Generator.getRandomFormattedDecimalStringValue("###.####"));
        account.setIraDistributionAmount(String.valueOf(Generator.genLong(10000000000L, 922337203685L)));
        account.setDateNextIRADistribution(DateTime.getTomorrowDate("MM/dd/yyyy"));
        account.setNumberOfDebitCardsIssued(String.valueOf(Generator.genInt(0, 100)));
        account.setNumberOfATMCardsIssued(String.valueOf(Generator.genInt(0, 100)));
        account.setFederalWHPercent(String.valueOf(Generator.genInt(0, 100)));
        account.setPrintStatementNextUpdate(String.valueOf(new Timestamp(System.currentTimeMillis()).getTime()).substring(4));
        account.setInterestPaidYTD(String.valueOf(Generator.genLong(100000000000L, 922337203685L)));
        account.setUserDefinedField_1(Generator.genString(5));
        account.setUserDefinedField_2(Generator.genString(5));
        account.setUserDefinedField_3(Generator.genString(5));
        account.setUserDefinedField_4(Generator.genString(5));
        account.setDateOfFirstDeposit(DateTime.getDateMinusDays(WebAdminActions.loginActions().getSystemDate(), 1));
        account.setBirthDate(DateTime.getDateMinusDays(WebAdminActions.loginActions().getSystemDate(), 5));
        account.setDateDeceased(DateTime.getDateMinusDays(WebAdminActions.loginActions().getSystemDate(), 1));

        return account;
    }

    public Account setSavingsIraAccountData() {
        Account account = new Account();

        account.setAddNewOption("Account");
        account.setProductType(ProductType.SAVINGS_ACCOUNT.getProductType());
        account.setAccountTitle(Generator.genString(5));
        account.setDateOpened(DateTime.getDateMinusDays(WebAdminActions.loginActions().getSystemDate(), Constants.DAYS_BEFORE_SYSTEM_DATE));
        account.setAccountNumber(Generator.genAccountNumber());
        account.setIraDistributionCode("No dist");
        account.setIraDistributionFrequency("No Dist");
        account.setIraDistributionAmount(String.valueOf(Generator.genLong(10000000000L, 922337203685L)));
        account.setDateNextIRADistribution(/*DateTime.getTomorrowDate("MM/dd/yyyy")*/ DateTime.getDateWithFormatPlusDays(WebAdminActions.loginActions().getSystemDate(), "MM/dd/yyyy", "MM/dd/yyyy", 1));
        account.setNumberOfDebitCardsIssued(String.valueOf(Generator.genInt(0, 100)));
        account.setNumberOfATMCardsIssued(String.valueOf(Generator.genInt(0, 100)));
        account.setFederalWHPercent(String.valueOf(Generator.genInt(0, 100)));
        account.setPrintStatementNextUpdate(String.valueOf(Generator.genLong(100000000L, 999999999L)));
        account.setInterestPaidYTD(String.valueOf(Generator.genLong(100000000000L, 922337203685L)));
        account.setUserDefinedField_1(Generator.genString(5));
        account.setUserDefinedField_2(Generator.genString(5));
        account.setUserDefinedField_3(Generator.genString(5));
        account.setUserDefinedField_4(Generator.genString(5));
        account.setDateOfFirstDeposit(DateTime.getDateWithFormatPlusDays(WebAdminActions.loginActions().getSystemDate(), "MM/dd/yyyy", "MM/dd/yyyy", 1));

        return account;
    }

    public Account setSavingsAccountData() {
        Account account = new Account();

        account.setAddNewOption("Account");
        account.setProductType(ProductType.SAVINGS_ACCOUNT.getProductType());
        account.setAccountTitle(Generator.genString(5));
        account.setAccountNumber(/*String.valueOf(Generator.genLong(10000000000L, 922337203685L))*/Generator.genAccountNumber());
        account.setAccountTitle(Generator.genString(5));
        account.setDateOpened(DateTime.getDateMinusDays(WebAdminActions.loginActions().getSystemDate(), Constants.DAYS_BEFORE_SYSTEM_DATE));
        account.setInterestRate(Generator.getRandomFormattedDecimalStringValue("###.####"));
        account.setCashCollInterestRate(String.valueOf(new Timestamp(System.currentTimeMillis()).getTime()).substring(4));
        account.setNumberOfDebitCardsIssued(String.valueOf(Generator.genInt(0, 100)));
        account.setNumberOfATMCardsIssued(String.valueOf(Generator.genInt(0, 100)));
        account.setFederalWHPercent(String.valueOf(Generator.genInt(0, 100)));
        account.setUserDefinedField_1(Generator.genString(5));
        account.setUserDefinedField_2(Generator.genString(5));
        account.setUserDefinedField_3(Generator.genString(5));
        account.setUserDefinedField_4(Generator.genString(5));
        account.setPrintStatementNextUpdate(String.valueOf(Generator.genLong(10000000L, 99999999L)));
        account.setInterestPaidYTD(String.valueOf(Generator.genLong(100000000000L, 922337203685L)));

        return account;
    }

    public Account setCHKAccountData() {
        Account account = new Account();

        account.setAddNewOption("Account");
        account.setProductType(ProductType.CHK_ACCOUNT.getProductType());
        account.setAccountTitle(Generator.genString(5));
        account.setAccountNumber(Generator.genAccountNumber());
        account.setOptInOutDate("01/01/2020");
        account.setDateOpened(DateTime.getDateMinusDays(WebAdminActions.loginActions().getSystemDate(), Constants.DAYS_BEFORE_SYSTEM_DATE));
        account.setInterestRate(Generator.getRandomFormattedDecimalStringValue("###.####"));
        account.setEarningCreditRate(Generator.getRandomStringNumber(3));
        account.setAutomaticOverdraftStatus("Active");
        account.setAutomaticOverdraftLimit(String.valueOf(Generator.genLong(100000000000L, 922337203688L)));
        account.setNumberOfATMCardsIssued(String.valueOf(Generator.genInt(0, 100)));
        account.setNumberOfDebitCardsIssued(String.valueOf(Generator.genInt(0, 100)));
        account.setFederalWHPercent(String.valueOf(Generator.genInt(0, 100)));
        account.setCashCollDaysBeforeChg(String.valueOf(new Timestamp(System.currentTimeMillis()).getTime()).substring(4));
        account.setCashCollInterestChg(String.valueOf(new Timestamp(System.currentTimeMillis()).getTime()).substring(4));
        account.setCashCollInterestRate(String.valueOf(new Timestamp(System.currentTimeMillis()).getTime()).substring(4));
        account.setCashCollFloat(String.valueOf(new Timestamp(System.currentTimeMillis()).getTime()).substring(4));
        account.setUserDefinedField_1(Generator.genString(5));
        account.setUserDefinedField_2(Generator.genString(5));
        account.setUserDefinedField_3(Generator.genString(5));
        account.setUserDefinedField_4(Generator.genString(5));
        account.setPositivePay(Generator.genString(5));

        return account;
    }

    public Account setSafeDepositBoxData() {
        Account account = new Account();

        account.setAddNewOption("Account");
        account.setProductType(ProductType.SAFE_DEPOSIT_BOX.getProductType());
        account.setAccountHolder("Owner");
        account.setAccountNumber(Generator.genAccountNumber());
        account.setAccountTitle(Generator.genString(5));
        account.setDateOpened(DateTime.getDateMinusDays(WebAdminActions.loginActions().getSystemDate(), Constants.DAYS_BEFORE_SYSTEM_DATE));
        account.setDiscountPeriods(String.valueOf(Generator.genInt(1000000000, 2147483646)));
        account.setDateLastAccess(DateTime.getLocalDate());
        account.setUserDefinedField_1(Generator.genString(5));
        account.setUserDefinedField_2(Generator.genString(5));
        account.setUserDefinedField_3(Generator.genString(5));
        account.setUserDefinedField_4(Generator.genString(5));

        return account;
    }

    public boolean isCycleLoan() {
        return cycleLoan;
    }

    public void setCycleLoan(boolean cycleLoan) {
        this.cycleLoan = cycleLoan;
    }

    public String getEscrow() {
        return escrow;
    }

    public void setEscrow(String escrow) {
        this.escrow = escrow;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getBoxSize() {
        return boxSize;
    }

    public void setBoxSize(String boxSize) {
        this.boxSize = boxSize;
    }

    public String getRentalAmount() {
        return rentalAmount;
    }

    public void setRentalAmount(String rentalAmount) {
        this.rentalAmount = rentalAmount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountTitle() {
        return accountTitle;
    }

    public void setAccountTitle(String accountTitle) {
        this.accountTitle = accountTitle;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getMailCode() {
        return mailCode;
    }

    public void setMailCode(String mailCode) {
        this.mailCode = mailCode;
    }

    public String getDateOpened() {
        return dateOpened;
    }

    public void setDateOpened(String dateOpened) {
        this.dateOpened = dateOpened;
    }

    public String getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }

    public String getCorrespondingAccount() {
        return correspondingAccount;
    }

    public void setCorrespondingAccount(String correspondingAccount) {
        this.correspondingAccount = correspondingAccount;
    }

    public String getUserDefinedField_1() {
        return userDefinedField_1;
    }

    public void setUserDefinedField_1(String userDefinedField_1) {
        this.userDefinedField_1 = userDefinedField_1;
    }

    public String getUserDefinedField_2() {
        return userDefinedField_2;
    }

    public void setUserDefinedField_2(String userDefinedField_2) {
        this.userDefinedField_2 = userDefinedField_2;
    }

    public String getUserDefinedField_3() {
        return userDefinedField_3;
    }

    public void setUserDefinedField_3(String userDefinedField_3) {
        this.userDefinedField_3 = userDefinedField_3;
    }

    public String getUserDefinedField_4() {
        return userDefinedField_4;
    }

    public void setUserDefinedField_4(String userDefinedField_4) {
        this.userDefinedField_4 = userDefinedField_4;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDiscountPeriods() {
        return discountPeriods;
    }

    public void setDiscountPeriods(String discountPeriods) {
        this.discountPeriods = discountPeriods;
    }

    public String getDiscountReason() {
        return discountReason;
    }

    public void setDiscountReason(String discountReason) {
        this.discountReason = discountReason;
    }

    public String getAddNewOption() {
        return addNewOption;
    }

    public void setAddNewOption(String addNewOption) {
        this.addNewOption = addNewOption;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getStatementCycle() {
        return statementCycle;
    }

    public void setStatementCycle(String statementCycle) {
        this.statementCycle = statementCycle;
    }

    public String getCurrentOfficer() {
        return currentOfficer;
    }

    public void setCurrentOfficer(String currentOfficer) {
        this.currentOfficer = currentOfficer;
    }

    public String getCallClassCode() {
        return callClassCode;
    }

    public void setCallClassCode(String callClassCode) {
        this.callClassCode = callClassCode;
    }

    public String getAccountAnalysis() {
        return accountAnalysis;
    }

    public void setAccountAnalysis(String accountAnalysis) {
        this.accountAnalysis = accountAnalysis;
    }

    public String getChargeOrAnalyze() {
        return chargeOrAnalyze;
    }

    public void setChargeOrAnalyze(String chargeOrAnalyze) {
        this.chargeOrAnalyze = chargeOrAnalyze;
    }

    public String getOptInOutDate() {
        return optInOutDate;
    }

    public void setOptInOutDate(String optInOutDate) {
        this.optInOutDate = optInOutDate;
    }

    public String getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate;
    }

    public String getEarningCreditRate() {
        return earningCreditRate;
    }

    public void setEarningCreditRate(String earningCreditRate) {
        this.earningCreditRate = earningCreditRate;
    }

    public String getFederalWHReason() {
        return federalWHReason;
    }

    public void setFederalWHReason(String federalWHReason) {
        this.federalWHReason = federalWHReason;
    }

    public String getReasonATMChargeWaived() {
        return reasonATMChargeWaived;
    }

    public void setReasonATMChargeWaived(String reasonATMChargeWaived) {
        this.reasonATMChargeWaived = reasonATMChargeWaived;
    }

    public String getOdProtectionAcct() {
        return odProtectionAcct;
    }

    public void setOdProtectionAcct(String odProtectionAcct) {
        this.odProtectionAcct = odProtectionAcct;
    }

    public String getReasonAutoNSFChgWaived() {
        return reasonAutoNSFChgWaived;
    }

    public void setReasonAutoNSFChgWaived(String reasonAutoNSFChgWaived) {
        this.reasonAutoNSFChgWaived = reasonAutoNSFChgWaived;
    }

    public String getReasonDebitCardChargeWaived() {
        return reasonDebitCardChargeWaived;
    }

    public void setReasonDebitCardChargeWaived(String reasonDebitCardChargeWaived) {
        this.reasonDebitCardChargeWaived = reasonDebitCardChargeWaived;
    }

    public String getAutomaticOverdraftStatus() {
        return automaticOverdraftStatus;
    }

    public void setAutomaticOverdraftStatus(String automaticOverdraftStatus) {
        this.automaticOverdraftStatus = automaticOverdraftStatus;
    }

    public String getReasonAutoOdChgWaived() {
        return reasonAutoOdChgWaived;
    }

    public void setReasonAutoOdChgWaived(String reasonAutoOdChgWaived) {
        this.reasonAutoOdChgWaived = reasonAutoOdChgWaived;
    }

    public String getWhenSurchargesRefunded() {
        return whenSurchargesRefunded;
    }

    public void setWhenSurchargesRefunded(String whenSurchargesRefunded) {
        this.whenSurchargesRefunded = whenSurchargesRefunded;
    }

    public String getNumberOfATMCardsIssued() {
        return numberOfATMCardsIssued;
    }

    public void setNumberOfATMCardsIssued(String numberOfATMCardsIssued) {
        this.numberOfATMCardsIssued = numberOfATMCardsIssued;
    }

    public String getFederalWHPercent() {
        return federalWHPercent;
    }

    public void setFederalWHPercent(String federalWHPercent) {
        this.federalWHPercent = federalWHPercent;
    }

    public String getNumberOfDebitCardsIssued() {
        return numberOfDebitCardsIssued;
    }

    public void setNumberOfDebitCardsIssued(String numberOfDebitCardsIssued) {
        this.numberOfDebitCardsIssued = numberOfDebitCardsIssued;
    }

    public String getCashCollDaysBeforeChg() {
        return cashCollDaysBeforeChg;
    }

    public void setCashCollDaysBeforeChg(String cashCollDaysBeforeChg) {
        this.cashCollDaysBeforeChg = cashCollDaysBeforeChg;
    }

    public String getCashCollInterestRate() {
        return cashCollInterestRate;
    }

    public void setCashCollInterestRate(String cashCollInterestRate) {
        this.cashCollInterestRate = cashCollInterestRate;
    }

    public String getCashCollInterestChg() {
        return cashCollInterestChg;
    }

    public void setCashCollInterestChg(String cashCollInterestChg) {
        this.cashCollInterestChg = cashCollInterestChg;
    }

    public String getPositivePay() {
        return positivePay;
    }

    public void setPositivePay(String positivePay) {
        this.positivePay = positivePay;
    }

    public String getCashCollFloat() {
        return cashCollFloat;
    }

    public void setCashCollFloat(String cashCollFloat) {
        this.cashCollFloat = cashCollFloat;
    }

    public String getAutomaticOverdraftLimit() {
        return automaticOverdraftLimit;
    }

    public void setAutomaticOverdraftLimit(String automaticOverdraftLimit) {
        this.automaticOverdraftLimit = automaticOverdraftLimit;
    }

    public String getInterestFrequency() {
        return interestFrequency;
    }

    public void setInterestFrequency(String interestFrequency) {
        this.interestFrequency = interestFrequency;
    }

    public String getPrintStatementNextUpdate() {
        return printStatementNextUpdate;
    }

    public void setPrintStatementNextUpdate(String printStatementNextUpdate) {
        this.printStatementNextUpdate = printStatementNextUpdate;
    }

    public String getInterestPaidYTD() {
        return interestPaidYTD;
    }

    public void setInterestPaidYTD(String printInterestPaidYTD) {
        this.interestPaidYTD = printInterestPaidYTD;
    }

    public String getIraDistributionFrequency() {
        return iraDistributionFrequency;
    }

    public void setIraDistributionFrequency(String iraDistributionFrequency) {
        this.iraDistributionFrequency = iraDistributionFrequency;
    }

    public String getIraDistributionCode() {
        return iraDistributionCode;
    }

    public void setIraDistributionCode(String iraDistributionCode) {
        this.iraDistributionCode = iraDistributionCode;
    }

    public String getIraDistributionAmount() {
        return iraDistributionAmount;
    }

    public void setIraDistributionAmount(String iraDistributionAmount) {
        this.iraDistributionAmount = iraDistributionAmount;
    }

    public String getDateNextIRADistribution() {
        return dateNextIRADistribution;
    }

    public void setDateNextIRADistribution(String dateNextIRADistribution) {
        this.dateNextIRADistribution = dateNextIRADistribution;
    }

    public String getApplyInterestTo() {
        return applyInterestTo;
    }

    public void setApplyInterestTo(String applyInterestTo) {
        this.applyInterestTo = applyInterestTo;
    }

    public String getInterestType() {
        return interestType;
    }

    public void setInterestType(String interestType) {
        this.interestType = interestType;
    }

    public String getTermType() {
        return termType;
    }

    public void setTermType(String termType) {
        this.termType = termType;
    }

    public String getAutoRenewable() {
        return autoRenewable;
    }

    public void setAutoRenewable(String autoRenewable) {
        this.autoRenewable = autoRenewable;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public String getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(String maturityDate) {
        this.maturityDate = maturityDate;
    }

    public String getDateNextInterest() {
        return dateNextInterest;
    }

    public void setDateNextInterest(String dateNextInterest) {
        this.dateNextInterest = dateNextInterest;
    }

    public String getTransactionalAccount() {
        return transactionalAccount;
    }

    public void setTransactionalAccount(String transactionalAccount) {
        this.transactionalAccount = transactionalAccount;
    }

    public String getDateLastAccess() {
        return dateLastAccess;
    }

    public void setDateLastAccess(String dateLastAccess) {
        this.dateLastAccess = dateLastAccess;
    }

    public String getBankAccountNumberInterestOnCD() {
        return bankAccountNumberInterestOnCD;
    }

    public void setBankAccountNumberInterestOnCD(String bankAccountNumberInterestOnCD) {
        this.bankAccountNumberInterestOnCD = bankAccountNumberInterestOnCD;
    }

    public String getBankRoutingNumberInterestOnCD() {
        return bankRoutingNumberInterestOnCD;
    }

    public void setBankRoutingNumberInterestOnCD(String bankRoutingNumberInterestOnCD) {
        this.bankRoutingNumberInterestOnCD = bankRoutingNumberInterestOnCD;
    }

    public String getBankruptcyJudgement() {
        return bankruptcyJudgement;
    }

    public void setBankruptcyJudgement(String bankruptcyJudgement) {
        this.bankruptcyJudgement = bankruptcyJudgement;
    }

    public String getDateOfFirstDeposit() {
        return dateOfFirstDeposit;
    }

    public void setDateOfFirstDeposit(String dateOfFirstDeposit) {
        this.dateOfFirstDeposit = dateOfFirstDeposit;
    }

    public String getDateDeceased() {
        return dateDeceased;
    }

    public void setDateDeceased(String dateDeceased) {
        this.dateDeceased = dateDeceased;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getAccruedInterest() {
        return accruedInterest;
    }

    public void setAccruedInterest(String accruedInterest) {
        this.accruedInterest = accruedInterest;
    }

    public String getInterestPaidLastYear() {
        return interestPaidLastYear;
    }

    public void setInterestPaidLastYear(String interestPaidLastYear) {
        this.interestPaidLastYear = interestPaidLastYear;
    }

    public String getTaxesWithheldYTD() {
        return taxesWithheldYTD;
    }

    public void setTaxesWithheldYTD(String taxesWithheldYTD) {
        this.taxesWithheldYTD = taxesWithheldYTD;
    }

    public String getOriginalBalance() {
        return originalBalance;
    }

    public void setOriginalBalance(String originalBalance) {
        this.originalBalance = originalBalance;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getDailyInterestAccrual() {
        return dailyInterestAccrual;
    }

    public void setDailyInterestAccrual(String dailyInterestAccrual) {
        this.dailyInterestAccrual = dailyInterestAccrual;
    }

    public String getNextInterestPaymentAmount() {
        return nextInterestPaymentAmount;
    }

    public void setNextInterestPaymentAmount(String nextInterestPaymentAmount) {
        this.nextInterestPaymentAmount = nextInterestPaymentAmount;
    }

    public String getMinTerm() {
        return minTerm;
    }

    public void setMinTerm(String minTerm) {
        this.minTerm = minTerm;
    }

    private String getCDIRATerms(String environment) {
        switch (environment) {
            case "dev6":
            case "dev12":
            default:
                return "6";
            case "dev21":
            case "dev4":
                return "12";
        }
    }

    public String getOriginatingOfficer() {
        return originatingOfficer;
    }

    public void setOriginatingOfficer(String originatingOfficer) {
        this.originatingOfficer = originatingOfficer;
    }

    public String getLoanClassCode() {
        return loanClassCode;
    }

    public void setLoanClassCode(String loanClassCode) {
        this.loanClassCode = loanClassCode;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getPaymentAmountType() {
        return paymentAmountType;
    }

    public void setPaymentAmountType(String paymentAmountType) {
        this.paymentAmountType = paymentAmountType;
    }

    public String getPaymentFrequency() {
        return paymentFrequency;
    }

    public void setPaymentFrequency(String paymentFrequency) {
        this.paymentFrequency = paymentFrequency;
    }

    public String getCurrentEffectiveRate() {
        return currentEffectiveRate;
    }

    public void setCurrentEffectiveRate(String currentEffectiveRate) {
        this.currentEffectiveRate = currentEffectiveRate;
    }

    public String getNextPaymentBilledDueDate() {
        return nextPaymentBilledDueDate;
    }

    public void setNextPaymentBilledDueDate(String nextPaymentBilledDueDate) {
        this.nextPaymentBilledDueDate = nextPaymentBilledDueDate;
    }

    public String getInterestMethod() {
        return interestMethod;
    }

    public void setInterestMethod(String interestMethod) {
        this.interestMethod = interestMethod;
    }

    public String getDaysBaseYearBase() {
        return daysBaseYearBase;
    }

    public void setDaysBaseYearBase(String daysBaseYearBase) {
        this.daysBaseYearBase = daysBaseYearBase;
    }

    public void setAdjustableRate (Boolean adjustableRate) {
        this.adjustableRate = adjustableRate;
    }

    public String getPaymentBilledLeadDays() {
        return paymentBilledLeadDays;
    }

    public void setPaymentBilledLeadDays(String paymentBilledLeadDays) {
        this.paymentBilledLeadDays = paymentBilledLeadDays;
    }

    public String getRateChangeFrequency() {
        return rateChangeFrequency;
    }

    public void setRateChangeFrequency(String rateChangeFrequency) {
        this.rateChangeFrequency = rateChangeFrequency;
    }

    public String getCommitmentTypeAmt() {
        return commitmentTypeAmt;
    }

    public void setCommitmentTypeAmt(String commitmentTypeAmt) {
        this.commitmentTypeAmt = commitmentTypeAmt;
    }

    public String getCycleCode() {
        return cycleCode;
    }

    public void setCycleCode(String cycleCode) {
        this.cycleCode = cycleCode;
    }

    public String getDateFirstPaymentDue() {
        return dateFirstPaymentDue;
    }

    public void setDateFirstPaymentDue(String dateFirstPaymentDue) {
        this.dateFirstPaymentDue = dateFirstPaymentDue;
    }

    public String getPaymentChangeFrequency() {
        return paymentChangeFrequency;
    }

    public void setPaymentChangeFrequency(String paymentChangeFrequency) {
        this.paymentChangeFrequency = paymentChangeFrequency;
    }

    public String getRateIndex() {
        return rateIndex;
    }

    public void setRateIndex(String rateIndex) {
        this.rateIndex = rateIndex;
    }

    public String getRateRoundingMethod() {
        return rateRoundingMethod;
    }

    public void setRateRoundingMethod(String rateRoundingMethod) {
        this.rateRoundingMethod = rateRoundingMethod;
    }

    public String getNextRateChangeDate() {
        return nextRateChangeDate;
    }

    public void setNextRateChangeDate(String nextRateChangeDate) {
        this.nextRateChangeDate = nextRateChangeDate;
    }

    public String getRateChangeLeadDays() {
        return rateChangeLeadDays;
    }

    public void setRateChangeLeadDays(String rateChangeLeadDays) {
        this.rateChangeLeadDays = rateChangeLeadDays;
    }

    public String getNextPaymentChangeDate() {
        return nextPaymentChangeDate;
    }

    public void setNextPaymentChangeDate(String nextPaymentChangeDate) {
        this.nextPaymentChangeDate = nextPaymentChangeDate;
    }

    public String getPaymentChangeLeadDays() {
        return paymentChangeLeadDays;
    }

    public void setPaymentChangeLeadDays(String paymentChangeLeadDays) {
        this.paymentChangeLeadDays = paymentChangeLeadDays;
    }

    public String getRateMargin() {
        return rateMargin;
    }

    public void setRateMargin(String rateMargin) {
        this.rateMargin = rateMargin;
    }

    public String getMinRate() {
        return minRate;
    }

    public void setMinRate(String minRate) {
        this.minRate = minRate;
    }

    public String getMaxRate() {
        return maxRate;
    }

    public void setMaxRate(String maxRate) {
        this.maxRate = maxRate;
    }

    public String getMaxRateChangeUpDown() {
        return maxRateChangeUpDown;
    }

    public void setMaxRateChangeUpDown(String maxRateChangeUpDown) {
        this.maxRateChangeUpDown = maxRateChangeUpDown;
    }

    public String getMaxRateLifetimeCap() {
        return maxRateLifetimeCap;
    }

    public void setMaxRateLifetimeCap(String maxRateLifetimeCap) {
        this.maxRateLifetimeCap = maxRateLifetimeCap;
    }

    public String getRateRoundingFactor() {
        return rateRoundingFactor;
    }

    public void setRateRoundingFactor(String rateRoundingFactor) {
        this.rateRoundingFactor = rateRoundingFactor;
    }

    public String getOriginalInterestRate() {
        return originalInterestRate;
    }

    public void setOriginalInterestRate(String originalInterestRate) {
        this.originalInterestRate = originalInterestRate;
    }

    public boolean isCurrentEffectiveRateIsTeaser() {
        return currentEffectiveRateIsTeaser;
    }

    public boolean isAdjustableRate() {
        return adjustableRate;
    }

    public void setCurrentEffectiveRateIsTeaser(boolean currentEffectiveRateIsTeaser) {
        this.currentEffectiveRateIsTeaser = currentEffectiveRateIsTeaser;
    }

    public String getIraDistributionAccountNumber() {
        return iraDistributionAccountNumber;
    }

    public void  setIraDistributionAccountNumber(String iraDistributionAccountNumber) {
        this.iraDistributionAccountNumber = iraDistributionAccountNumber;
    }
}