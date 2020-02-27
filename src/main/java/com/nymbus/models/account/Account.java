package com.nymbus.models.account;

import com.nymbus.util.Random;

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
    private String mailCode;
    private String dateOpened;
    private String bankBranch;
    private String statementFlag;
    private String statementCycle;
    private String correspondingAccount;
    private String primaryAccountForCombinedStatement;
    private String userDefinedField_1;
    private String userDefinedField_2;
    private String userDefinedField_3;
    private String userDefinedField_4;
    private String discount;
    private String discountPeriods;
    private String discountReason;

    public Account setDefaultAccountData() {
        Account account = new Account();
        // default account data

        return account;
    }

    public Account setCHKAccountData() {
        Account account = new Account();

        account.setAddNewOption("Account");
        account.setProductType("CHK Account");

        return account;
    }

    public Account setSafeDepositBoxData() {
        Account account = new Account();

        account.setAddNewOption("Account");
        account.setProductType("Safe Deposit Box");
        account.setAccountNumber(String.valueOf(new Timestamp(System.currentTimeMillis()).getTime()).substring(4));
        account.setAccountTitle(Random.genString(5));
        account.setUserDefinedField_1(Random.genString(5));
        account.setUserDefinedField_2(Random.genString(5));
        account.setUserDefinedField_3(Random.genString(5));
        account.setUserDefinedField_4(Random.genString(5));

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

    public String getStatementFlag() {
        return statementFlag;
    }

    public void setStatementFlag(String statementFlag) {
        this.statementFlag = statementFlag;
    }

    public String getCorrespondingAccount() {
        return correspondingAccount;
    }

    public void setCorrespondingAccount(String correspondingAccount) {
        this.correspondingAccount = correspondingAccount;
    }

    public String getPrimaryAccountForCombinedStatement() {
        return primaryAccountForCombinedStatement;
    }

    public void setPrimaryAccountForCombinedStatement(String primaryAccountForCombinedStatement) {
        this.primaryAccountForCombinedStatement = primaryAccountForCombinedStatement;
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
}
