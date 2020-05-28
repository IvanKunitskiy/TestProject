package com.nymbus.newmodels.generation.tansactions.builder;

import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.newmodels.generation.tansactions.factory.DestinationFactory;
import com.nymbus.newmodels.generation.tansactions.factory.SourceFactory;
import com.nymbus.newmodels.transaction.Transaction;

public class CashInDepositCHKAccBuilder implements TransactionBuilder {
    private Transaction transaction;

    public CashInDepositCHKAccBuilder() {
        this.transaction = new Transaction();
    }

    @Override
    public void setDate() {
        transaction.setTransactionDate(WebAdminActions.loginActions().getSystemDate());
    }

    @Override
    public void setSource() {
        transaction.setTransactionSource(SourceFactory.getCashInSource());
    }

    @Override
    public void setDestination() {
        transaction.setTransactionDestination(DestinationFactory.getDepositDestination());
    }

    @Override
    public Transaction getResult() {
        return transaction;
    }
}