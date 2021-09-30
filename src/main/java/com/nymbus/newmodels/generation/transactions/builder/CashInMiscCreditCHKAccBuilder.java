package com.nymbus.newmodels.generation.transactions.builder;

import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.TransactionDestination;
import com.nymbus.newmodels.transaction.TransactionSource;
import com.nymbus.newmodels.transaction.enums.Denominations;
import com.nymbus.newmodels.transaction.enums.DestinationType;
import com.nymbus.newmodels.transaction.enums.SourceType;
import com.nymbus.util.Random;

import java.util.HashMap;

public class CashInMiscCreditCHKAccBuilder implements TransactionBuilder {
    private Transaction transaction;

    public CashInMiscCreditCHKAccBuilder() {
        transaction = new Transaction();
    }

    @Override
    public void setDate() {
        transaction.setTransactionDate(DateTime.getDateTodayPlusDaysWithFormat(0, "MM/dd/yyyy"));
    }

    @Override
    public void setSource() {
        transaction.setTransactionSource(new TransactionSource());
        transaction.getTransactionSource().setSourceType(SourceType.CASH_IN);
        transaction.getTransactionSource().setAccountNumber("0-0-Dummy");
        transaction.getTransactionSource().setAmount(100.00);
        transaction.getTransactionSource().setNotes(Random.genString(20));
        transaction.getTransactionSource().setDenominationsHashMap(new HashMap<>());
        transaction.getTransactionSource().getDenominationsHashMap().put(Denominations.HUNDREDS, 100.00);
    }

    @Override
    public void setDestination() {
        transaction.setTransactionDestination(new TransactionDestination());
        transaction.getTransactionDestination().setSourceType(DestinationType.MISC_CREDIT);
        transaction.getTransactionDestination().setAccountNumber("12400589946");
        transaction.getTransactionDestination().setAmount(100.00);
        transaction.getTransactionDestination().setTransactionCode("109 - Deposit");
    }

    @Override
    public Transaction getResult() {
        return transaction;
    }
}
