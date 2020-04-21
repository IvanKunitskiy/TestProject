package com.nymbus.newmodels.transaction;

import lombok.Data;

import java.util.ArrayList;

@Data
public class MultipleTransaction {
    private String transactionDate;
    private ArrayList<TransactionSource> sources;
    private ArrayList<TransactionDestination> destinations;

    public boolean isTransactionBalanced() {
        double sourcesAmount = getSources().stream().map(TransactionSource::getAmount).count();
        double destinationAmount = getDestinations().stream().map(TransactionDestination::getAmount).count();
        return sourcesAmount == destinationAmount;
    }
}
