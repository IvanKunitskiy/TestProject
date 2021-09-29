package com.nymbus.newmodels.generation.transactions.builder;

import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.TransactionDestination;
import com.nymbus.newmodels.transaction.TransactionSource;
import com.nymbus.newmodels.transaction.enums.DestinationType;
import com.nymbus.newmodels.transaction.enums.SourceType;
import com.nymbus.newmodels.transaction.enums.TransactionCode;
import com.nymbus.util.Random;

public class CheckGLCreditCHKAccBuilder implements TransactionBuilder {
    private final Transaction transaction;

    public CheckGLCreditCHKAccBuilder() {
        this.transaction =  new Transaction();
    }

    @Override
    public void setDate() {
        transaction.setTransactionDate(WebAdminActions.loginActions().getSystemDate());
    }

    @Override
    public void setSource() {
        transaction.setTransactionSource(new TransactionSource());
        transaction.getTransactionSource().setSourceType(SourceType.CHECK);
        transaction.getTransactionSource().setAccountNumber("83460855747");
        transaction.getTransactionSource().setAmount(150.00);
        transaction.getTransactionSource().setTransactionCode(TransactionCode.CHECK.getTransCode());
        transaction.getTransactionSource().setCheckNumber("2222");
    }

    @Override
    public void setDestination() {
        transaction.setTransactionDestination(new TransactionDestination());
        transaction.getTransactionDestination().setSourceType(DestinationType.GL_CREDIT);
        transaction.getTransactionDestination().setAccountNumber("0-0-Dummy");
        transaction.getTransactionDestination().setAmount(150.00);
        transaction.getTransactionDestination().setTransactionCode(TransactionCode.GL_CREDIT_865.getTransCode());
        transaction.getTransactionDestination().setNotes(Random.genString(20));
    }

    @Override
    public Transaction getResult() {
        return transaction;
    }
}