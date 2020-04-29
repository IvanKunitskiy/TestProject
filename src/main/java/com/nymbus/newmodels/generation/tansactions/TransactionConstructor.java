package com.nymbus.newmodels.generation.tansactions;

import com.nymbus.newmodels.generation.tansactions.builder.TransactionBuilder;
import com.nymbus.newmodels.transaction.Transaction;

public class TransactionConstructor {
    private TransactionBuilder transactionBuilder;

    public TransactionConstructor(TransactionBuilder transactionBuilder) {
        this.transactionBuilder = transactionBuilder;
    }

    public Transaction constructTransaction() {
        transactionBuilder.setDate();
        transactionBuilder.setSource();
        transactionBuilder.setDestination();

        return transactionBuilder.getResult();
    }

    public void setBuilder(TransactionBuilder transactionBuilder) {
        this.transactionBuilder = transactionBuilder;
    }
}
