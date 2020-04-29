package com.nymbus.actions.account;

import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.pages.Pages;

import java.util.Collections;
import java.util.List;

public class AccountTransactionActions {
    private final String PLUS_SYMBOL = "+";
    private final String MINUS_SYMBOL = "-";

    public boolean isTransactionSymbolRight(String accountNumber, Transaction transaction, int index) {
        if (!isTransactionContainsNumber(transaction, accountNumber)) {
            return false;
        }
        String symbol = Pages.accountTransactionPage().getAmountSymbol(index);
        if (isDebitTransaction(transaction, accountNumber)) {
            return symbol.equals(MINUS_SYMBOL);
        }
        else {
            return symbol.equals(PLUS_SYMBOL);
        }
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