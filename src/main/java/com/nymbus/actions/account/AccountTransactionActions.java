package com.nymbus.actions.account;

import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.pages.Pages;
import org.testng.asserts.SoftAssert;

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

    public int getRowCount() {
        Pages.accountTransactionPage().waitForCallStatementButton();
        return Pages.accountTransactionPage().getTransactionItemsCountWithZeroOption();
    }

    public void verifyTransactionList(String currentBusinessDate, boolean isBefore) {
        int rowsCount = getRowCount();
        verifyEffectiveDate(currentBusinessDate, rowsCount, isBefore);
    }

    private void verifyEffectiveDate(String expectedDate, int count, boolean isBefore) {
        SoftAssert softAssert = new SoftAssert();
        for(int i = 1; i <= count; ++i) {
            String actualDate = Pages.accountTransactionPage().getEffectiveDateValue(i);
            if (isBefore) {
                softAssert.assertTrue(DateTime.isDateBefore(actualDate, expectedDate, "MM/dd/yyyy"),
                        String.format("Transaction %s effective date is incorrect!", i));
            }
            else {
                softAssert.assertTrue(DateTime.isDateAfter(actualDate, expectedDate, "MM/dd/yyyy"),
                        String.format("Transaction %s effective date is incorrect!", i));
            }
        }
        softAssert.assertAll();
    }

    public void applyFilterByTransactionFromFiled(String transactionSource) {
        Pages.accountTransactionPage().clickTransactionFromDropdown();
        Pages.accountTransactionPage().clickItemInDropdown(transactionSource);
        Pages.accountTransactionPage().clickApplyFilterButton();
    }

    public void clearFilterRegion() {
        Pages.accountTransactionPage().clickClearFilterButton();
        Pages.accountTransactionPage().clickApplyFilterButton();
    }
}