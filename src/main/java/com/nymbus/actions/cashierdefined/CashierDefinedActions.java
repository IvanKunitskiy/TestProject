package com.nymbus.actions.cashierdefined;

import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.TransactionDestination;
import com.nymbus.newmodels.transaction.TransactionSource;
import com.nymbus.pages.Pages;

public class CashierDefinedActions {

    public void createTransaction(String type, Transaction transaction){
        int tempIndex = 0;
        setTellerOperation(type);
        setTransactionSource(transaction.getTransactionSource(), tempIndex);
        setTransactionDestination(transaction.getTransactionDestination(), tempIndex);

    }

    private void setTellerOperation(String type) {
        Pages.tellerPage().clickSelectOperation();

        Pages.tellerPage().selectOperation(type);
    }

    private void setTransactionSource(TransactionSource source, int index) {
        int tempIndex = 1 + index;
        fillSourceAccountNumber(source.getAccountNumber(), tempIndex);
        fillSourceAmount(String.format("%.2f", source.getAmount()), tempIndex);
    }

    private void fillSourceAmount(String amount, int tempIndex) {
        Pages.tellerPage().clickAmountDiv(tempIndex);

        Pages.tellerPage().typeAmountValue(tempIndex, amount);
    }

    private void fillSourceAccountNumber(String accountNumber, int tempIndex) {
        Pages.tellerPage().clickAccountNumberDiv(tempIndex);

        Pages.tellerPage().typeAccountNumber(tempIndex, accountNumber);

        Pages.tellerPage().clickOnAutocompleteDropDownItem(accountNumber);
    }

    public void setTransactionDestination(TransactionDestination transactionDestination, int index) {
        int tempIndex= 1 + index;
        fillDestinationAccountNumber(transactionDestination.getAccountNumber(), tempIndex);
        fillDestinationAmount(String.format("%.2f", transactionDestination.getAmount()), tempIndex);
    }

    private void fillDestinationAmount(String amount, int tempIndex) {
        Pages.tellerPage().clickDestinationAmountDiv(tempIndex);

        Pages.tellerPage().typeDestinationAmountValue(tempIndex, amount);
    }

    private void fillDestinationAccountNumber(String accountNumber, int tempIndex) {
        Pages.tellerPage().typeDestinationAccountNumber(tempIndex, accountNumber);

        Pages.tellerPage().clickOnAutocompleteDropDownItem(accountNumber);
    }

}
