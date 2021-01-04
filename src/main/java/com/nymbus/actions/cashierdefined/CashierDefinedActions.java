package com.nymbus.actions.cashierdefined;

import com.nymbus.newmodels.transaction.TransactionDestination;
import com.nymbus.newmodels.transaction.TransactionSource;
import com.nymbus.pages.Pages;

public class CashierDefinedActions {

    public void setTellerOperation(String type) {
        Pages.cashierDefinedActionsPage().clickSelectOperation();
        Pages.cashierDefinedActionsPage().selectOperation(type);
    }

    public void setTransactionSource(TransactionSource source, int index) {
        int tempIndex = 1 + index;
        fillSourceAccountNumber(source.getAccountNumber(), tempIndex);
        fillSourceAmount(String.format("%.2f", source.getAmount()), tempIndex);
        fillSourceDetails(source.getNotes(), tempIndex);
    }

    public void fillSourceAmount(String amount, int tempIndex) {
        Pages.tellerPage().clickAmountDiv(tempIndex);
        Pages.tellerPage().typeAmountValue(tempIndex, amount);
    }

    public void fillSourceAccountNumber(String accountNumber, int tempIndex) {
        Pages.tellerPage().clickAccountNumberDiv(tempIndex);
        Pages.tellerPage().typeAccountNumber(tempIndex, accountNumber);
        Pages.tellerPage().clickOnAutocompleteDropDownItem(accountNumber);
    }

    private void fillSourceDetails(String notes, int tempIndex) {
        Pages.tellerPage().clickSourceDetailsArrow(tempIndex);
        Pages.tellerPage().typeSourceNotesValue(tempIndex, notes);
    }

    public void setTransactionDestination(TransactionDestination transactionDestination, int index) {
        int tempIndex= 1 + index;
        fillDestinationAccountNumber(transactionDestination.getAccountNumber(), tempIndex);
        fillDestinationAmount(String.format("%.2f", transactionDestination.getAmount()), tempIndex);
        fillDestinationDetails(transactionDestination.getNotes(), tempIndex);
    }

    public void fillDestinationAmount(String amount, int tempIndex) {
        Pages.tellerPage().clickDestinationAmountDiv(tempIndex);
        Pages.tellerPage().typeDestinationAmountValue(tempIndex, amount);
    }

    public void fillDestinationAccountNumber(String accountNumber, int tempIndex) {
        Pages.tellerPage().typeDestinationAccountNumber(tempIndex, accountNumber);
        Pages.tellerPage().clickOnAutocompleteDropDownItem(accountNumber);
    }

    private void fillDestinationDetails(String notes, int tempIndex) {
        Pages.tellerPage().clickDestinationDetailsArrow(tempIndex);
        Pages.tellerPage().typeDestinationNotesValue(tempIndex, notes);
    }

    public void clickCommitButton() {
        Pages.tellerPage().clickCommitButton();
    }

}
