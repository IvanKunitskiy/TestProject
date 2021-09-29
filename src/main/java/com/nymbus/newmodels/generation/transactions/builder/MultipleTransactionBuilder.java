package com.nymbus.newmodels.generation.transactions.builder;

import com.nymbus.newmodels.transaction.MultipleTransaction;

public interface MultipleTransactionBuilder {
    void setDate();
    void setSource();
    void setDestination();
    MultipleTransaction getResult();
}
