package com.nymbus.newmodels.generation.tansactions.builder;

import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.TransactionDestination;
import com.nymbus.newmodels.transaction.TransactionSource;
import com.nymbus.newmodels.transaction.enums.DestinationType;
import com.nymbus.newmodels.transaction.enums.SourceType;
import com.nymbus.util.Random;

public class GLDebitMiscCreditCHKAccBuilder implements TransactionBuilder {
    private Transaction transaction;


    public GLDebitMiscCreditCHKAccBuilder() {
        transaction = new Transaction();
    }

    @Override
    public void setDate() {
        transaction.setTransactionDate(DateTime.getDateTodayPlusDaysWithFormat(0, "MM/dd/yyyy"));
    }

    @Override
    public void setSource() {
        transaction.setTransactionSource(new TransactionSource());
        transaction.getTransactionSource().setSourceType(SourceType.GL_DEBIT);
        transaction.getTransactionSource().setAccountNumber("0-0-Dummy");
        transaction.getTransactionSource().setAmount(100.00);
        transaction.getTransactionSource().setNotes(Random.genString(20));
    }

    @Override
    public void setDestination() {
        transaction.setTransactionDestination(new TransactionDestination());
        transaction.getTransactionDestination().setSourceType(DestinationType.MISC_CREDIT);
        transaction.getTransactionDestination().setAccountNumber("83460855747");
        transaction.getTransactionDestination().setAmount(100.00);
        transaction.getTransactionDestination().setTransactionCode("109 - Deposit");
    }

    @Override
    public Transaction getResult() {
        return transaction;
    }
}