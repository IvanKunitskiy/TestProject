package com.nymbus.newmodels.generation.transactions.builder;

import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.TransactionDestination;
import com.nymbus.newmodels.transaction.TransactionSource;
import com.nymbus.newmodels.transaction.enums.DestinationType;
import com.nymbus.newmodels.transaction.enums.SourceType;
import com.nymbus.util.Random;

public class WithdrawalGLDebitCHKAccBuilder implements TransactionBuilder {
    private Transaction transaction;

    public WithdrawalGLDebitCHKAccBuilder() {
        this.transaction =  new Transaction();
    }

    @Override
    public void setDate() {
        transaction.setTransactionDate(WebAdminActions.loginActions().getSystemDate());
    }

    @Override
    public void setSource() {
        transaction.setTransactionSource(new TransactionSource());
        transaction.getTransactionSource().setSourceType(SourceType.WITHDRAWL);
        transaction.getTransactionSource().setAccountNumber("83460855747");
        transaction.getTransactionSource().setAmount(100.00);
        //transaction.getTransactionSource().setTransactionCode("104 - Deposit Corr");
    }

    @Override
    public void setDestination() {
        transaction.setTransactionDestination(new TransactionDestination());
        transaction.getTransactionDestination().setSourceType(DestinationType.DEPOSIT);
        transaction.getTransactionDestination().setAccountNumber("0-0-Dummy");
        transaction.getTransactionDestination().setAmount(100.00);
        transaction.getTransactionDestination().setTransactionCode("109 - Deposit");
        transaction.getTransactionDestination().setNotes(Random.genString(20));
    }

    @Override
    public Transaction getResult() {
        return transaction;
    }
}