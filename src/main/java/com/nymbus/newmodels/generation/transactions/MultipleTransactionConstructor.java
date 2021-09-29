package com.nymbus.newmodels.generation.transactions;

import com.nymbus.newmodels.generation.transactions.builder.MultipleTransactionBuilder;
import com.nymbus.newmodels.transaction.MultipleTransaction;

public class MultipleTransactionConstructor {
    private MultipleTransactionBuilder multipleTransactionBuilder;

    public MultipleTransactionConstructor(MultipleTransactionBuilder multipleTransactionBuilder) {
        this.multipleTransactionBuilder = multipleTransactionBuilder;
    }

    public MultipleTransaction constructTransaction() {
        multipleTransactionBuilder.setDate();
        multipleTransactionBuilder.setSource();
        multipleTransactionBuilder.setDestination();

        return multipleTransactionBuilder.getResult();
    }
}
