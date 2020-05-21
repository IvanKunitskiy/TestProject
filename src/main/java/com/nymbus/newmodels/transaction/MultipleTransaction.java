package com.nymbus.newmodels.transaction;

import lombok.Data;

import java.util.ArrayList;

@Data
public class MultipleTransaction {
    private String transactionDate;
    private ArrayList<TransactionSource> sources;
    private ArrayList<TransactionDestination> destinations;

    public boolean isTransactionBalanced() {
        double sourcesAmount = getSources().stream().map(TransactionSource::getAmount).reduce(0d, Double::sum);
        double destinationAmount = getDestinations().stream().map(TransactionDestination::getAmount).reduce(0d, Double::sum);
        return sourcesAmount == destinationAmount;
    }

    public double getSourcesAmount() {
        return getSources().stream().map(TransactionSource::getAmount).reduce(0d, Double::sum);
    }

    public double getDestinationAmount() {
        return getDestinations().stream().map(TransactionDestination::getAmount).reduce(0d, Double::sum);
    }
}