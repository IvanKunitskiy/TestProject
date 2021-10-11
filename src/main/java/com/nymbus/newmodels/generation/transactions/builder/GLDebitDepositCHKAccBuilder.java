package com.nymbus.newmodels.generation.transactions.builder;

import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.TransactionDestination;
import com.nymbus.newmodels.transaction.TransactionSource;
import com.nymbus.newmodels.transaction.enums.DestinationType;
import com.nymbus.newmodels.transaction.enums.SourceType;
import com.nymbus.util.Random;

public class GLDebitDepositCHKAccBuilder implements TransactionBuilder {
    private Transaction transaction;

    public GLDebitDepositCHKAccBuilder() {
        this.transaction = new Transaction();
    }


    @Override
    public void setDate() {
        transaction.setTransactionDate(WebAdminActions.loginActions().getSystemDate());
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
        transaction.getTransactionDestination().setSourceType(DestinationType.DEPOSIT);
        transaction.getTransactionDestination().setAccountNumber("83460855747");
        transaction.getTransactionDestination().setAmount(100.00);
        transaction.getTransactionDestination().setTransactionCode("109 - Deposit");
    }

    @Override
    public Transaction getResult() {
        return transaction;
    }
}