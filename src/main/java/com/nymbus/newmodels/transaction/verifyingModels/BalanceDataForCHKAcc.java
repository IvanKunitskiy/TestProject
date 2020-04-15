package com.nymbus.newmodels.transaction.verifyingModels;

import lombok.Data;

@Data
public class BalanceDataForCHKAcc {
    private double currentBalance = 0;
    private double availableBalance = 0;

    public void addAmount(double amount) {
        this.availableBalance += amount;
        this.currentBalance += amount;
    }
}
