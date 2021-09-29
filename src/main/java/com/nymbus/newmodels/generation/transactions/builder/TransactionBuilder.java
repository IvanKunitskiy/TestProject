package com.nymbus.newmodels.generation.transactions.builder;

import com.nymbus.newmodels.transaction.Transaction;

public interface TransactionBuilder {
    public void setDate();
    public void setSource();
    public void setDestination();
    public Transaction getResult();
}
