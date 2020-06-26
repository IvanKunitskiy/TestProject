package com.nymbus.newmodels.transaction.verifyingModels;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ExtendedBalanceDataForCHKAcc extends BalanceDataForCHKAcc {
    protected double collectedBalance = 0;
    protected double averageBalance = 0;

    @Override
    public void addAmount(double amount) {
        this.collectedBalance += amount;
        super.addAmount(amount);
    }

    @Override
    public void reduceAmount(double amount) {
        this.collectedBalance -= amount;
        super.reduceAmount(amount);
    }
}