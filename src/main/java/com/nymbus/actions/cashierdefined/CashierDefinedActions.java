package com.nymbus.actions.cashierdefined;

import com.nymbus.newmodels.account.product.ProductType;
import com.nymbus.newmodels.cashier.CashierDefinedTransactions;
import com.nymbus.newmodels.cashier.PayeeType;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.TransactionDestination;
import com.nymbus.newmodels.transaction.TransactionSource;
import com.nymbus.pages.Pages;
import com.nymbus.pages.settings.SettingsPage;

public class CashierDefinedActions {

    public boolean checkCDTTemplateIsExist(CashierDefinedTransactions template){
        Pages.aSideMenuPage().waitForASideMenu();
        Pages.aSideMenuPage().clickSettingsMenuItem();
        Pages.settings().waitForSettingsPageLoaded();
        SettingsPage.mainPage().clickAllCDTButton();
        SettingsPage.cdtPage().searchCDTTemplate(template.getOperation());
        boolean noResults = SettingsPage.cdtPage().checkResultsDiv();
        if (!noResults){
            noResults = SettingsPage.cdtPage().checkResults(template.getOperation());
        }
        return noResults;
    }


    public void createTransaction(CashierDefinedTransactions type, Transaction transaction, boolean waiveFee){
        int tempIndex = 0;
        setTellerOperation(type.getOperation());
        setWaiveFee(waiveFee);
        if (!type.equals(CashierDefinedTransactions.INCOMING_WIRE_TO_SAVINGS)){
            setTransactionSource(transaction.getTransactionSource(), tempIndex);
        }
        if (!type.equals(CashierDefinedTransactions.OUTGOING_WIRE_FROM_SAVINGS)){
            setTransactionDestination(transaction.getTransactionDestination(), tempIndex);
        }
    }

    public void createOutgoingTransaction(CashierDefinedTransactions type, Transaction transaction, boolean waiveFee){
        int tempIndex = 0;
        setTellerOperation(type.getOperation());
        setWaiveFee(waiveFee);
        setTransactionSource(transaction.getTransactionSource(), tempIndex);
        Pages.tellerPage().inputBankRouting("122105155");
    }

    public void createOfficialTransaction(CashierDefinedTransactions type, Transaction transaction, boolean waiveFee, String name){
        int tempIndex = 0;
        setTellerOperation(type.getOperation());
        setWaiveFee(waiveFee);
        setTransactionSource(transaction.getTransactionSource(), tempIndex);
        typeName(name);
        choosePayeeType(PayeeType.PERSON.getType());
    }

    private void choosePayeeType(String type) {
        Pages.cashierPage().selectPayeeType(type);
    }

    private void typeName(String name) {
        Pages.cashierPage().inputPayeeName(name);
    }


    private void setWaiveFee(boolean waiveFee) {
        if (waiveFee){
            Pages.cashierPage().clickWaiveFee();
        }
    }

    public void setTellerOperation(String type) {
        Pages.cashierPage().clickSelectOperation();

        Pages.cashierPage().selectOperation(type);
    }

    public void setTransactionSource(TransactionSource source, int index) {
        int tempIndex = 1 + index;
        fillSourceAccountNumber(source.getAccountNumber(), tempIndex);
        fillSourceAmount(String.format("%.2f", source.getAmount()), tempIndex);
    }

    public void fillSourceAmount(String amount, int tempIndex) {
        Pages.cashierPage().clickAmountDiv(tempIndex);

        Pages.cashierPage().typeAmountValue(tempIndex, amount);
    }

    public void fillSourceAccountNumber(String accountNumber, int tempIndex) {
        Pages.cashierPage().clickAccountNumberDiv(tempIndex);

        Pages.cashierPage().typeAccountNumber(tempIndex, accountNumber);

        Pages.cashierPage().clickOnAutocompleteDropDownItem(accountNumber);
    }

    public void setTransactionDestination(TransactionDestination transactionDestination, int index) {
        int tempIndex= 1 + index;
        fillDestinationAccountNumber(transactionDestination.getAccountNumber(), tempIndex);
        fillDestinationAmount(String.format("%.2f", transactionDestination.getAmount()), tempIndex);
    }

    public void fillDestinationAmount(String amount, int tempIndex) {
        Pages.cashierPage().clickDestinationAmountDiv(tempIndex);

        Pages.cashierPage().typeDestinationAmountValue(tempIndex, amount);
    }

    private void fillDestinationAccountNumber(String accountNumber, int tempIndex) {
        Pages.cashierPage().typeDestinationAccountNumber(tempIndex, accountNumber);

        Pages.cashierPage().clickOnAutocompleteDropDownItem(accountNumber);
    }

    public void fillDestinationDetails(String notes, int tempIndex) {
        Pages.cashierPage().clickDestinationDetailsArrow(tempIndex);

        Pages.cashierPage().typeDestinationNotesValue(tempIndex, notes);
    }

    public void fillSourceDetails(String notes, int tempIndex) {
        Pages.cashierPage().clickSourceDetailsArrow(tempIndex);

        Pages.cashierPage().typeSourceNotesValue(tempIndex, notes);
    }

    public boolean createTransferFromSavToCHK() {
        SettingsPage.cdtPage().clickAddNew();
        SettingsPage.createCdtPage().inputName(CashierDefinedTransactions.TRANSFER_FROM_SAV_TO_CHK.getOperation());
        SettingsPage.createCdtPage().selectDebitTypeAccount(ProductType.SAVINGS_ACCOUNT);
        SettingsPage.createCdtPage().selectCreditTypeAccount(ProductType.CHK_ACCOUNT);
        SettingsPage.createCdtPage().selectDebitTransactionCode("(221) Debit Transfer");
        SettingsPage.createCdtPage().selectCreditTransactionCode("(101) Credit Transfr");
        SettingsPage.createCdtPage().inputDebitDescription("TRANSFER TO CHECKING");
        SettingsPage.createCdtPage().inputCreditDescription("TRANSFER FROM SAVINGS");
        SettingsPage.createCdtPage().selectDebitNoticeOption("No Notice");
        SettingsPage.createCdtPage().selectCreditNoticeOption("No Notice");
        SettingsPage.createCdtPage().inputFeeAmount("0");
        SettingsPage.createCdtPage().inputGLAccount("0-0");
        SettingsPage.createCdtPage().clickSaveButton();
        return SettingsPage.createCdtPage().checkNameIsVisible();
    }

    public boolean createTransferFromSavToCHKWithFee() {
        SettingsPage.cdtPage().clickAddNew();
        SettingsPage.createCdtPage().inputName(CashierDefinedTransactions.TRANSFER_FROM_SAVINGS_TO_CHECKING_WITH_FEE.getOperation());
        SettingsPage.createCdtPage().selectDebitTypeAccount(ProductType.SAVINGS_ACCOUNT);
        SettingsPage.createCdtPage().selectCreditTypeAccount(ProductType.CHK_ACCOUNT);
        SettingsPage.createCdtPage().selectDebitTransactionCode("(221) Debit Transfer");
        SettingsPage.createCdtPage().selectCreditTransactionCode("(101) Credit Transfr");
        SettingsPage.createCdtPage().inputDebitDescription("TRANSFER FROM SAVINGS TO CHECKING WITH FEE");
        SettingsPage.createCdtPage().inputCreditDescription("TRANSFER FROM SAVINGS TO CHECKING WITH FEE");
        SettingsPage.createCdtPage().selectDebitNoticeOption("No Notice");
        SettingsPage.createCdtPage().selectCreditNoticeOption("No Notice");
        SettingsPage.createCdtPage().inputFeeAmount("3.00");
        SettingsPage.createCdtPage().inputFeeDescription("TRANSFER FROM SAVINGS TO CHECKING WITH FEE");
        SettingsPage.createCdtPage().inputGLAccount("0-0");
        SettingsPage.createCdtPage().clickSaveButton();
        return SettingsPage.createCdtPage().checkNameIsVisible();
    }

    public boolean createIncomingWireToSavings() {
        SettingsPage.cdtPage().clickAddNew();
        SettingsPage.createCdtPage().inputName(CashierDefinedTransactions.INCOMING_WIRE_TO_SAVINGS.getOperation());
        SettingsPage.createCdtPage().selectDebitTypeAccount(ProductType.INCOMING_WIRE);
        SettingsPage.createCdtPage().selectCreditTypeAccount(ProductType.SAVINGS_ACCOUNT);
        SettingsPage.createCdtPage().selectDebitTransactionCode("(860) G/L Debit");
        SettingsPage.createCdtPage().selectCreditTransactionCode("(264) Incoming Wire");
        SettingsPage.createCdtPage().inputDebitDescription("INCOMING WIRE TO SAVINGS");
        SettingsPage.createCdtPage().inputCreditDescription("INCOMING WIRE TRANSFERS");
        SettingsPage.createCdtPage().selectDebitNoticeOption("No Notice");
        SettingsPage.createCdtPage().selectCreditNoticeOption("No Notice");
        SettingsPage.createCdtPage().inputFeeAmount("5.00");
        SettingsPage.createCdtPage().inputGLAccount("0-0");
        SettingsPage.createCdtPage().selectOperationType("Wire");
        SettingsPage.createCdtPage().inputFeeDescription("Incoming Wire Fee");
        SettingsPage.createCdtPage().clickSaveButton();
        return SettingsPage.createCdtPage().checkNameIsVisible();
    }

    public boolean createMoneyOrderFromSavings() {
        SettingsPage.cdtPage().clickAddNew();
        SettingsPage.createCdtPage().inputName(CashierDefinedTransactions.MONEY_ORDER_FROM_SAVINGS.getOperation());
        SettingsPage.createCdtPage().selectDebitTypeAccount(ProductType.SAVINGS_ACCOUNT);
        SettingsPage.createCdtPage().selectCreditTypeAccount(ProductType.CHK_ACCOUNT);
        SettingsPage.createCdtPage().selectDebitTransactionCode("(216) Withdrawal");
        SettingsPage.createCdtPage().selectCreditTransactionCode("(109) Deposit");
        SettingsPage.createCdtPage().inputDebitDescription("MONEY ORDER PURCHASE");
        SettingsPage.createCdtPage().inputCreditDescription("MONEY ORDER FROM SAVINGS");
        SettingsPage.createCdtPage().selectDebitNoticeOption("No Notice");
        SettingsPage.createCdtPage().selectCreditNoticeOption("No Notice");
        SettingsPage.createCdtPage().inputFeeAmount("2.00");
        SettingsPage.createCdtPage().inputFeeDescription("MONEY ORDER FEE");
        SettingsPage.createCdtPage().inputGLAccount("4800200");
        SettingsPage.createCdtPage().clickSaveButton();
        return SettingsPage.createCdtPage().checkNameIsVisible();
    }

    public boolean createOfficialCheckFromSavings() {
        SettingsPage.cdtPage().clickAddNew();
        SettingsPage.createCdtPage().inputName(CashierDefinedTransactions.OFFICIAL_CHECK_FROM_SAVINGS.getOperation());
        SettingsPage.createCdtPage().selectDebitTypeAccount(ProductType.SAVINGS_ACCOUNT);
        SettingsPage.createCdtPage().selectCreditTypeAccount(ProductType.CHK_ACCOUNT);
        SettingsPage.createCdtPage().selectDebitTransactionCode("(216) Withdrawal");
        SettingsPage.createCdtPage().selectCreditTransactionCode("(109) Deposit");
        SettingsPage.createCdtPage().inputDebitDescription("OFFICIAL CHECK PURCHASE");
        SettingsPage.createCdtPage().inputCreditDescription("OFFICIAL CHECK FROM SAV");
        SettingsPage.createCdtPage().selectDebitNoticeOption("No Notice");
        SettingsPage.createCdtPage().selectCreditNoticeOption("No Notice");
        SettingsPage.createCdtPage().inputFeeAmount("5.00");
        SettingsPage.createCdtPage().inputGLAccount("4800200");
        SettingsPage.createCdtPage().selectOperationType("Official Check");
        SettingsPage.createCdtPage().inputFeeDescription("OFFICIAL CHECK FEE");
        SettingsPage.createCdtPage().clickSaveButton();
        return SettingsPage.createCdtPage().checkNameIsVisible();
    }

    public boolean createOfficialCheckWithCash() {
        SettingsPage.cdtPage().clickAddNew();
        SettingsPage.createCdtPage().inputName(CashierDefinedTransactions.OFFICIAL_CHECK_WITH_CASH.getOperation());
        SettingsPage.createCdtPage().selectDebitTypeAccount(ProductType.GL_TICKETS);
        SettingsPage.createCdtPage().selectCreditTypeAccount(ProductType.GL_TICKETS);
        SettingsPage.createCdtPage().selectDebitTransactionCode("(850) Cash In");
        SettingsPage.createCdtPage().selectCreditTransactionCode("(865) G/L Credit");
        SettingsPage.createCdtPage().inputDebitDescription("OFFICIAL CHECK PURCHASE");
        SettingsPage.createCdtPage().inputCreditDescription("OFFICIAL CHECK WITH CASH");
        SettingsPage.createCdtPage().selectDebitNoticeOption("No Notice");
        SettingsPage.createCdtPage().selectCreditNoticeOption("No Notice");
        SettingsPage.createCdtPage().inputFeeAmount("5.00");
        SettingsPage.createCdtPage().inputGLAccount("4800200");
        SettingsPage.createCdtPage().selectOperationType("Official Check");
        SettingsPage.createCdtPage().inputFeeDescription("OFFICIAL CHECK FEE");
        SettingsPage.createCdtPage().clickSaveButton();
        return SettingsPage.createCdtPage().checkNameIsVisible();
    }

    public boolean createTransferFromSavToCHKWithNotice() {
        SettingsPage.cdtPage().clickAddNew();
        SettingsPage.createCdtPage().inputName(CashierDefinedTransactions.TRANSFER_FROM_SAV_TO_CHK_Print_Notice_On_Entry.getOperation());
        SettingsPage.createCdtPage().selectDebitTypeAccount(ProductType.SAVINGS_ACCOUNT);
        SettingsPage.createCdtPage().selectCreditTypeAccount(ProductType.CHK_ACCOUNT);
        SettingsPage.createCdtPage().selectDebitTransactionCode("(221) Debit Transfer");
        SettingsPage.createCdtPage().selectCreditTransactionCode("(101) Credit Transfr");
        SettingsPage.createCdtPage().selectDebitNoticeOption("On Entry");
        SettingsPage.createCdtPage().selectCreditNoticeOption("On Entry");
        SettingsPage.createCdtPage().inputFeeAmount("0.00");
        SettingsPage.createCdtPage().inputGLAccount("0-0");
        SettingsPage.createCdtPage().clickSaveButton();
        return SettingsPage.createCdtPage().checkNameIsVisible();
    }

    public boolean createOutgoingWireFromSavings() {
        SettingsPage.cdtPage().clickAddNew();
        SettingsPage.createCdtPage().inputName(CashierDefinedTransactions.OUTGOING_WIRE_FROM_SAVINGS.getOperation());
        SettingsPage.createCdtPage().selectDebitTypeAccount(ProductType.SAVINGS_ACCOUNT);
        SettingsPage.createCdtPage().selectCreditTypeAccount(ProductType.OUTGOING_WIRE);
        SettingsPage.createCdtPage().selectDebitTransactionCode("(231) Outgoing Wire");
        SettingsPage.createCdtPage().selectCreditTransactionCode("(865) G/L Credit");
        SettingsPage.createCdtPage().inputDebitDescription("OUTGOING DOM WIRE FROM SAVINGS");
        SettingsPage.createCdtPage().inputCreditDescription("OUTGOING DOM WIRE FROM SAVINGS");
        SettingsPage.createCdtPage().inputCreditAccount("1005300");
        SettingsPage.createCdtPage().selectDebitNoticeOption("No Notice");
        SettingsPage.createCdtPage().selectCreditNoticeOption("No Notice");
        SettingsPage.createCdtPage().inputFeeAmount("30.00");
        SettingsPage.createCdtPage().inputGLAccount("4800200");
        SettingsPage.createCdtPage().selectOperationType("Wire");
        SettingsPage.createCdtPage().inputFeeDescription("WIRE FEE");
        SettingsPage.createCdtPage().clickSaveButton();
        return SettingsPage.createCdtPage().checkNameIsVisible();
    }
    public void clickCommitButton() {
        Pages.cashierPage().clickCommitButton();
    }

}
