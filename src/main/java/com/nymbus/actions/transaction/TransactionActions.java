package com.nymbus.actions.transaction;

import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.transaction.MultipleTransaction;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.TransactionDestination;
import com.nymbus.newmodels.transaction.TransactionSource;
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
        loginTeller();
        goToTellerPage();
       /* doLoginTeller();*/
        createCashInMiscCreditTransaction(transaction);
        clickCommitButton();
        Pages.verifyConductorModalPage().clickVerifyButton();
        Pages.tellerPage().closeModal();
    }

    public void performGLDebitMiscCreditTransaction(Transaction transaction) {
        loginTeller();
        goToTellerPage();
        /*doLoginTeller();*/
        createGlDebitMiscCreditTransaction(transaction);
        clickCommitButton();
        Pages.tellerPage().closeModal();
    }

    public void performMiscDebitGLCreditTransaction(Transaction transaction) {
        loginTeller();
        goToTellerPage();
       /* doLoginTeller();*/
        createMiscDebitGLCreditTransaction(transaction);
        clickCommitButton();
        Pages.tellerPage().closeModal();
    }

    public void performMultipleTransaction(MultipleTransaction transaction) {
        loginTeller();
        goToTellerPage();
       /* doLoginTeller();*/
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

    private boolean isCashInOrCashOutTransactionType(Transaction transaction) {
        boolean isCashOperationsInSource = transaction.getTransactionSource().getSourceType().equals(SourceType.CASH_IN);
        boolean isCashOperationsInDestination = transaction.getTransactionDestination().getSourceType().equals(DestinationType.CASH_OUT);

        return isCashOperationsInSource || isCashOperationsInDestination;
    }

    private void setTransactionSource(TransactionSource source, int index) {
        switch (source.getSourceType()) {
            case GL_DEBIT:
                setGLDebitSource(source, index);
                break;
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

    private void setGLDebitSource(TransactionSource source, int index) {
        int tempIndex = 1 + index;
        Pages.tellerPage().clickGLDebitButton();
        fillSourceAccountNumber(source.getAccountNumber(), tempIndex);
        fillSourceAmount(String.format("%.2f", source.getAmount()), tempIndex);
        fillSourceDetails(source.getNotes(), tempIndex);
    }

    private void setTransactionDestination(TransactionDestination destination, int index) {
        switch (destination.getSourceType()) {
            case GL_CREDIT:
                setGLCreditDestination(destination, index);
                break;
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
        int tempIndex = 1 + index;
        Pages.tellerPage().clickMiscDebitButton();
        fillSourceAccountNumber(source.getAccountNumber(), tempIndex);
        fillSourceAmount(String.format("%.2f", source.getAmount()), tempIndex);
    }

    private void setCashOutDestination(TransactionDestination transactionDestination) {
        Pages.tellerPage().clickCashOutButton();
        Pages.cashInModalWindowPage().typeHundredsAmountValue(String.format("%.0f", transactionDestination.getAmount()));
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

    private void setGLCreditDestination(TransactionDestination transactionDestination, int index) {
        int tempIndex = index + 1;
        Pages.tellerPage().clickGLCreditButton();
        fillDestinationAccountNumber(transactionDestination.getAccountNumber(), tempIndex);
        fillDestinationAmount(String.format("%.2f", transactionDestination.getAmount()), tempIndex);
        fillDestinationDetails(transactionDestination.getNotes(), tempIndex);
    }

    private void fillDestinationDetails(String notes, int tempIndex) {
        Pages.tellerPage().clickDestinationDetailsArrow(tempIndex);

        Pages.tellerPage().typeDestinationNotesValue(tempIndex, notes);
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

    public void createMiscDebitGLCreditTransaction(Transaction transaction) {
        int currentIndex = 0;
        setMiscDebitSource(transaction.getTransactionSource(), currentIndex);
        setGLCreditDestination(transaction.getTransactionDestination(), currentIndex);
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

    public void performTransactionList(List<Transaction> transactions) {
        goToTellerPage();
        if (Pages.tellerModalPage().isModalWindowVisible()) {
            doLoginTeller();
        }
        for (Transaction transaction : transactions) {
            setTransactionSource(transaction.getTransactionSource(), 0);
            setTransactionDestination(transaction.getTransactionDestination(), 0);
            clickCommitButton();

            if (isCashInOrCashOutTransactionType(transaction)) {
                Pages.verifyConductorModalPage().clickVerifyButton();
            }
            Pages.tellerPage().closeModal();
        }
    }

    public void loginTeller() {
        Pages.navigationPage().clickAccountButton();

        Pages.navigationPage().clickProofDateLogin();

        SelenideTools.sleep(1);

        Pages.tellerModalPage().clickEnterButton();

        Pages.tellerModalPage().waitForModalInvisibility();
    }
}