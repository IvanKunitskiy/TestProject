package com.nymbus.newmodels.generation.tansactions.builder;

import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.newmodels.generation.tansactions.factory.DestinationFactory;
import com.nymbus.newmodels.generation.tansactions.factory.SourceFactory;
import com.nymbus.newmodels.transaction.Transaction;

public class MiscDebitGLCreditTransactionBuilder implements TransactionBuilder {
    private Transaction transaction;

    public MiscDebitGLCreditTransactionBuilder() {
        transaction = new Transaction();
    }

    @Override
    public void setDate() {
        transaction.setTransactionDate(WebAdminActions.loginActions().getSystemDate());
    }

    @Override
    public void setSource() {
        transaction.setTransactionSource(SourceFactory.getMiscDebitSource());
    }

    @Override
    public void setDestination() {
        transaction.setTransactionDestination(DestinationFactory.getGLCreditDestination());
    }

    @Override
    public Transaction getResult() {
        return transaction;
    }
}