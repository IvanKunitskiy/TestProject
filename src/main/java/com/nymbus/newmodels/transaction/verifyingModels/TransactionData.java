package com.nymbus.newmodels.transaction.verifyingModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionData {
    String postingDate;
    String effectiveDate;
    String amountSymbol;
    double balance = 0;
    double amount = 0;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionData that = (TransactionData) o;
        return postingDate.equals(that.postingDate)
                && effectiveDate.equals(that.effectiveDate)
                && amountSymbol.equals(that.amountSymbol)
                && amount == that.amount
                && balance == that.balance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(balance, amount, effectiveDate, postingDate, amountSymbol);
    }
}
