package com.nymbus.actions.account;

import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.pages.Pages;

import java.util.Collections;
import java.util.List;

public class AccountTransactionActions {
    private final String PLUS_SYMBOL = "+";
    private final String MINUS_SYMBOL = "-";
    private final String GREEN = "rgb(128, 200, 92)";
    private final String RED = "rgb(245, 97, 97)";
    private final int TRANSACTION_ITEMS_SIZE = 10;

    public boolean isTransactionSymbolRight(String accountNumber, Transaction transaction, int index) {
        if (!isTransactionContainsNumber(transaction, accountNumber)) {
            return false;
        }
        String symbol = Pages.accountTransactionPage().getAmountSymbol(index);
        if (isDebitTransaction(transaction, accountNumber)) {
            return symbol.equals(MINUS_SYMBOL)
                    && Pages.accountTransactionPage().isAmountSymbolColorRight(index, RED);
        }
        else {
            return symbol.equals(PLUS_SYMBOL)
                    && Pages.accountTransactionPage().isAmountSymbolColorRight(index, GREEN);
        }
    }

    public boolean isAllImageVisible() {
        for(int i = 1; i <= TRANSACTION_ITEMS_SIZE; i++) {
            if(!Pages.accountTransactionPage().isImageVisible(i)) {
                return false;
            }
        }
        return true;
    }

    public boolean isTransactionsSymbolRight(String accountNumber, List<Transaction> transactionList) {
        Collections.reverse(transactionList);
        for(int i = 0; i < transactionList.size(); i++) {
            if(!isTransactionSymbolRight(accountNumber, transactionList.get(i), i+1)) {
                return false;
            }
        }
        return true;
    }

    private boolean isTransactionContainsNumber(Transaction transaction, String number) {
        return transaction.getTransactionSource().getAccountNumber().equals(number)
                || transaction.getTransactionDestination().getAccountNumber().equals(number);
    }

    private boolean isDebitTransaction(Transaction transaction, String number) {
        return transaction.getTransactionSource().getAccountNumber().equals(number);
    }

    private boolean isCreditTransaction(Transaction transaction, String number) {
        return transaction.getTransactionDestination().getAccountNumber().equals(number);
    }
}