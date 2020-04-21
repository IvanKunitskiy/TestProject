package com.nymbus.newmodels.generation.tansactions.builder;

import com.nymbus.newmodels.transaction.MultipleTransaction;

public interface MultipleTransactionBuilder {
    void setDate();
    void setSource();
    void setDestination();
    MultipleTransaction getResult();
}
