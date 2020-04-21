package com.nymbus.actions.transaction;

import com.nymbus.actions.Actions;
import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.transaction.*;
import com.nymbus.newmodels.transaction.enums.DestinationType;
import com.nymbus.newmodels.transaction.enums.SourceType;
import com.nymbus.pages.Pages;

import java.util.List;

public class TransactionActions {

    public void goToTellerPage() {
        Pages.aSideMenuPage().clickTellerMenuItem();
    }

    public void doLoginTeller() {
        Pages.tellerModalPage().clickEnterButton();

        Pages.tellerModalPage().waitForModalInvisibility();
    }

    public void performCashInMiscCreditTransaction(Transaction transaction) {
        goToTellerPage();
        doLoginTeller();
        createCashInMiscCreditTransaction(transaction);
        clickCommitButton();
        Pages.verifyConductorModalPage().clickVerifyButton();
        Pages.tellerPage().closeModal();
    }

    public void performMultipleTransaction(MultipleTransaction transaction) {
        goToTellerPage();
        doLoginTeller();
        createTransactionWithMultipleSources(transaction);
        clickCommitButton();
        if (isCashInOrCashOutTransactionType(transaction)) {
            Pages.verifyConductorModalPage().clickVerifyButton();
        }
        Pages.tellerPage().closeModal();
    }

    private boolean isCashInOrCashOutTransactionType(MultipleTransaction transaction) {
        boolean isCashOperationsInSources = transaction.getSources().stream()
                                            .map(TransactionSource::getSourceType)
                                            .anyMatch(x -> x.equals(SourceType.CASH_IN));
        boolean isCashOperationsInDestinations = transaction.getDestinations().stream()
                                            .map(TransactionDestination::getSourceType)
                                            .anyMatch(x -> x.equals(DestinationType.CASH_OUT));

        return isCashOperationsInSources || isCashOperationsInDestinations;
    }


    private void setTransactionSource(TransactionSource source, int index) {
        switch (source.getSourceType()) {
            case CASH_IN:
                setCashInSource(source);
                break;
            case MISC_DEBIT:
                setMiscDebitSource(source, index);
                break;
            default:
                break;
        }
    }

    private void setTransactionDestination(TransactionDestination destination, int index) {
        switch (destination.getSourceType()) {
            case CASH_OUT:
                setCashOutDestination(destination);
                break;
            case DEPOSIT:
                setDepositDestination(destination, index);
                break;
            case MISC_CREDIT:
                setMiscCreditDestination(destination, index);
                break;
            default:
                break;
        }
    }

    public void createTransactionWithMultipleSources(MultipleTransaction multipleTransaction) {
        setMultipleTransactionSources(multipleTransaction.getSources());
        setMultipleTransactionDestinations(multipleTransaction.getDestinations());
    }

    private void setMultipleTransactionSources(List<TransactionSource> sources) {
        for (int i = 0; i < sources.size(); i++) {
            setTransactionSource(sources.get(i), i);
        }
    }

    private void setMultipleTransactionDestinations(List<TransactionDestination> destinations) {
        for (int i = 0; i < destinations.size(); i++) {
            setTransactionDestination(destinations.get(i), i);
        }
    }

    private void setCashInSource(TransactionSource source) {
        Pages.tellerPage().clickCashInButton();
        Pages.cashInModalWindowPage().typeHundredsAmountValue(String.format("%.0f", source.getAmount()));
        Pages.cashInModalWindowPage().clickOKButton();
    }

    private void setMiscDebitSource(TransactionSource source, int index) {
        int tempIndex= 1 + index;
        Pages.tellerPage().clickMiscDebitButton();
        fillSourceAccountNumber(source.getAccountNumber(), tempIndex);
        fillSourceAmount(String.format("%.2f", source.getAmount()), tempIndex);
    }

    private void setCashOutDestination(TransactionDestination transactionDestination) {
        Pages.tellerPage().clickCashOutButton();
        if (transactionDestination.getAmount()%100 == 0) {
            Pages.cashInModalWindowPage().typeHundredsAmountValue(String.format("%.0f", transactionDestination.getAmount()));
        }
        if (transactionDestination.getAmount()%50 == 0) {
            Pages.cashInModalWindowPage().typeFiftiesAmountValue(String.format("%.0f", transactionDestination.getAmount()));
        }
        Pages.cashInModalWindowPage().clickOKButton();
    }

    private void setDepositDestination(TransactionDestination transactionDestination, int index) {
        int tempIndex= 1 + index;
        Pages.tellerPage().clickDepositButton();
        fillDestinationAccountNumber(transactionDestination.getAccountNumber(), tempIndex);
        fillDestinationAccountCode(transactionDestination.getTransactionCode(), tempIndex);
        fillDestinationAmount(String.format("%.2f", transactionDestination.getAmount()), tempIndex);
    }

    private void setMiscCreditDestination(TransactionDestination transactionDestination, int index) {
        int tempIndex= 1 + index;
        Pages.tellerPage().clickMiscCreditButton();
        fillDestinationAccountNumber(transactionDestination.getAccountNumber(), tempIndex);
        fillDestinationAccountCode(transactionDestination.getTransactionCode(), tempIndex);
        fillDestinationAmount(String.format("%.2f", transactionDestination.getAmount()), tempIndex);
    }

    public void createCashInMiscCreditTransaction(Transaction transaction) {
        Pages.tellerPage().clickCashInButton();
        Pages.cashInModalWindowPage().typeHundredsAmountValue(String.format("%.0f", transaction.getTransactionSource().getAmount()));
        Pages.cashInModalWindowPage().clickOKButton();
        Pages.tellerPage().clickMiscCreditButton();
        fillDestinationInformation(transaction.getTransactionDestination());
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

    public void waitForLoadSpinnerInvisibility() {
        Pages.tellerPage().waitForLoadingSpinnerVisibility();

        Pages.tellerPage().waitForLoadingSpinnerInvisibility();
    }
}
