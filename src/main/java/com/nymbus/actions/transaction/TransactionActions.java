package com.nymbus.actions.transaction;

import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.transaction.*;
import com.nymbus.pages.Pages;

public class TransactionActions {

    public void goToTellerPage() {
        Pages.aSideMenuPage().clickTellerMenuItem();
    }

    public void doLoginTeller() {
        Pages.tellerModalPage().clickEnterButton();

        Pages.tellerModalPage().waitForModalInvisibility();
    }

    public void createGlDebitMiscCreditTransaction(Transaction transaction) {
        Pages.tellerPage().clickGLDebitButton();
        fillSourceInformation(transaction.getTransactionSource());
        Pages.tellerPage().clickMiscCreditButton();
        fillDestinationInformation(transaction.getTransactionDestination());
    }

    public void clickCommitButton() {
        Pages.tellerPage().clickCommitButton();
    }

    private void fillDestinationInformation(TransactionDestination transactionDestination) {
        int tempIndex = 1;
        fillDestinationAccountNumber(transactionDestination.getAccountNumber(), tempIndex);
        fillDestinationAccountCode(transactionDestination.getTransactionCode(), tempIndex);
        fillDestinationAmount(String.format("%.2f", transactionDestination.getAmount()), tempIndex);
    }

    private void fillDestinationAccountCode(String transactionCode, int tempIndex) {
        Pages.tellerPage().clickOnDestinationCodeField(tempIndex);

        Pages.tellerPage().clickOnDropDownItem(transactionCode);
    }

    private void fillDestinationAmount(String amount, int tempIndex) {
        Pages.tellerPage().clickDestinationAmountDiv(tempIndex);

        Pages.tellerPage().typeDestinationAmountValue(tempIndex, amount);
    }

    private void fillDestinationAccountNumber(String accountNumber, int tempIndex) {
        Pages.tellerPage().typeDestinationAccountNumber(tempIndex, accountNumber);

        Pages.tellerPage().clickOnAutocompleteDropDownItem(accountNumber);
    }

    private void fillSourceInformation(TransactionSource transactionSource) {
        int tempIndex = 1;
        fillSourceAccountNumber(transactionSource.getAccountNumber(), tempIndex);
        fillSourceAmount(String.format("%.2f", transactionSource.getAmount()), tempIndex);
        fillSourceDetails(transactionSource.getNotes(), tempIndex);
    }

    private void fillSourceDetails(String notes, int tempIndex) {
        Pages.tellerPage().clickSourceDetailsArrow(tempIndex);

        Pages.tellerPage().typeSourceNotesValue(tempIndex, notes);
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
}
