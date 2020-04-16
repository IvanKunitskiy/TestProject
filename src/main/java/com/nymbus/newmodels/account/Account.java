package com.nymbus.newmodels.account;

import com.nymbus.core.utils.DateTime;
import com.nymbus.core.utils.Generator;

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

    public Account setCDAccountData() {
        Account account = new Account();

        account.setAddNewOption("Account");
        account.setProductType("CD Account");
        account.setProduct("3 Month Regular Certificate");
        account.setAutoRenewable("YES");
        account.setInterestFrequency("Quarterly");
        account.setInterestType("Simple");
        account.setApplyInterestTo("Remain in Account");
        account.setAccountHolder("Owner");
        account.setAccountTitle(Generator.genString(5));
        account.setDateOpened(DateTime.getYesterdayDate("MM/dd/yyyy"));
        account.setAccountNumber(String.valueOf(Generator.genLong(10000000000L, 922337203685L)));
        account.setInterestRate(String.valueOf(Generator.genFloat(0.001, 99.999, 1)));
        account.setCashCollInterestRate(String.valueOf(new Timestamp(System.currentTimeMillis()).getTime()).substring(4));
        account.setFederalWHPercent(String.valueOf(Generator.genInt(0, 100)));
        account.setUserDefinedField_1(Generator.genString(5));
        account.setUserDefinedField_2(Generator.genString(5));
        account.setUserDefinedField_3(Generator.genString(5));
        account.setUserDefinedField_4(Generator.genString(5));

        return account;
    }

    public Account setCDIRAAccountData() {
        Account account = new Account();

        account.setAddNewOption("Account");
        account.setProductType("CD");
        account.setProduct("6 Month Roth IRA Certificate");
        account.setAccountTitle(Generator.genString(5));
        account.setDateOpened(DateTime.getYesterdayDate("MM/dd/yyyy"));
        account.setAccountNumber(String.valueOf(Generator.genLong(10000000000L, 922337203685L)));
        account.setIraDistributionCode("No dist");
        account.setIraDistributionFrequency("No Dist");
        account.setTransactionalAccount("NO");
        account.setAutoRenewable("YES");
        account.setAccountHolder("Owner");
        account.setTermType("6");
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

        return account;
    }

    public Account setIRAAccountData() {
        Account account = new Account();

        account.setAddNewOption("Account");
        account.setProductType("Savings Account");
        account.setProduct("Traditional IRA Accumulation Acct");
        account.setAccountTitle(Generator.genString(5));
        account.setDateOpened(DateTime.getYesterdayDate("MM/dd/yyyy"));
        account.setAccountNumber(String.valueOf(Generator.genLong(10000000000L, 922337203685L)));
        account.setIraDistributionCode("No dist");
        account.setIraDistributionFrequency("No Dist");
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

        return account;
    }

    public Account setSavingsAccountData() {
        Account account = new Account();

        account.setAddNewOption("Account");
        account.setProductType("Savings Account");
        account.setAccountTitle(Generator.genString(5));
        account.setAccountNumber(String.valueOf(Generator.genLong(10000000000L, 922337203685L)));
        account.setProduct("Regular Savings Account");
        account.setAccountNumber(String.valueOf(new Timestamp(System.currentTimeMillis()).getTime()).substring(4));
        account.setAccountTitle(Generator.genString(5));
        account.setDateOpened("02/27/2020");
        account.setInterestRate(Generator.getRandomFormattedDecimalStringValue("###.####"));
        account.setCashCollInterestRate(String.valueOf(new Timestamp(System.currentTimeMillis()).getTime()).substring(4));
        account.setNumberOfDebitCardsIssued(String.valueOf(Generator.genInt(0, 100)));
        account.setNumberOfATMCardsIssued(String.valueOf(Generator.genInt(0, 100)));
        account.setFederalWHPercent(String.valueOf(Generator.genInt(0, 100)));
        account.setUserDefinedField_1(Generator.genString(5));
        account.setUserDefinedField_2(Generator.genString(5));
        account.setUserDefinedField_3(Generator.genString(5));
        account.setUserDefinedField_4(Generator.genString(5));
        account.setPrintStatementNextUpdate(String.valueOf(new Timestamp(System.currentTimeMillis()).getTime()).substring(4));
        account.setInterestPaidYTD(String.valueOf(Generator.genLong(100000000000L, 922337203685L)));

        return account;
    }

    public Account setCHKAccountData() {
        Account account = new Account();

        account.setAddNewOption("Account");
        account.setProductType("CHK Account");
        account.setAccountTitle(Generator.genString(5));
        account.setAccountNumber(String.valueOf(Generator.genLong(10000000000L, 922337203685L)));
        account.setProduct("Basic Business Checking");
        account.setOptInOutDate("01/01/2020");
        account.setDateOpened("02/27/2020");
        account.setInterestRate(Generator.getRandomFormattedDecimalStringValue("###.####"));
        account.setEarningCreditRate(Generator.getRandomStringNumber(3));
        account.setAutomaticOverdraftStatus("Active");
        account.setAutomaticOverdraftLimit(String.valueOf(Generator.genLong(100000000000L, 922337203688L)));
        account.setNumberOfATMCardsIssued(String.valueOf(Generator.genInt(0, 100)));
        account.setNumberOfDebitCardsIssued(String.valueOf(Generator.genInt(0, 100)));
        account.setFederalWHPercent(String.valueOf(Generator.genInt(0, 100)));
        account.setEarningCreditRate(String.valueOf(Generator.genInt(0, 100)));
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
        account.setProductType("Safe Deposit Box");
        account.setAccountHolder("Owner");
        account.setAccountNumber(String.valueOf(new Timestamp(System.currentTimeMillis()).getTime()).substring(4));
        account.setAccountTitle(Generator.genString(5));
        account.setDateOpened(DateTime.getYesterdayDate("MM/dd/yyyy"));
        account.setDiscountPeriods(String.valueOf(Generator.genInt(1000000000, 2147483646)));
        account.setDateLastAccess(DateTime.getLocalDate());
        account.setUserDefinedField_1(Generator.genString(5));
        account.setUserDefinedField_2(Generator.genString(5));
        account.setUserDefinedField_3(Generator.genString(5));
        account.setUserDefinedField_4(Generator.genString(5));

        return account;
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
}
