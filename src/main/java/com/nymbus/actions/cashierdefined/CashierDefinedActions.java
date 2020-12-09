package com.nymbus.actions.cashierdefined;

import com.nymbus.newmodels.cashier.CashierDefinedTransactions;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.TransactionDestination;
import com.nymbus.newmodels.transaction.TransactionSource;
import com.nymbus.pages.Pages;

public class CashierDefinedActions {

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

    private void setWaiveFee(boolean waiveFee) {
        if (waiveFee){
            Pages.cashierPage().clickWaiveFee();
        }
    }

    private void setTellerOperation(String type) {
        Pages.cashierPage().clickSelectOperation();

        Pages.cashierPage().selectOperation(type);
    }

    private void setTransactionSource(TransactionSource source, int index) {
        int tempIndex = 1 + index;
        fillSourceAccountNumber(source.getAccountNumber(), tempIndex);
        fillSourceAmount(String.format("%.2f", source.getAmount()), tempIndex);
    }

    private void fillSourceAmount(String amount, int tempIndex) {
        Pages.cashierPage().clickAmountDiv(tempIndex);

        Pages.cashierPage().typeAmountValue(tempIndex, amount);
    }

    private void fillSourceAccountNumber(String accountNumber, int tempIndex) {
        Pages.cashierPage().clickAccountNumberDiv(tempIndex);

        Pages.cashierPage().typeAccountNumber(tempIndex, accountNumber);

        Pages.cashierPage().clickOnAutocompleteDropDownItem(accountNumber);
    }

    public void setTransactionDestination(TransactionDestination transactionDestination, int index) {
        int tempIndex= 1 + index;
        fillDestinationAccountNumber(transactionDestination.getAccountNumber(), tempIndex);
        fillDestinationAmount(String.format("%.2f", transactionDestination.getAmount()), tempIndex);
    }

    private void fillDestinationAmount(String amount, int tempIndex) {
        Pages.cashierPage().clickDestinationAmountDiv(tempIndex);

        Pages.cashierPage().typeDestinationAmountValue(tempIndex, amount);
    }

    private void fillDestinationAccountNumber(String accountNumber, int tempIndex) {
        Pages.cashierPage().typeDestinationAccountNumber(tempIndex, accountNumber);

        Pages.cashierPage().clickOnAutocompleteDropDownItem(accountNumber);
    }

}
