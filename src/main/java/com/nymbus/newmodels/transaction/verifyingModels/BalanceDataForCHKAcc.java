package com.nymbus.newmodels.transaction.verifyingModels;

import lombok.Data;

@Data
public class BalanceDataForCHKAcc {
    protected double currentBalance = 0;
    protected double availableBalance = 0;

    public void addAmount(double amount) {
        this.availableBalance += amount;
        this.currentBalance += amount;
    }

    public void reduceAmount(double amount) {
        this.availableBalance -= amount;
        this.currentBalance -= amount;
    }
}