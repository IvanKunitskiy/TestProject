package com.nymbus.newmodels.transaction.verifyingModels;

import lombok.Data;

@Data
public class BalanceDataForCDAcc {
    private double currentBalance = 0;
    private double originalBalance = 0;
    private double totalContributionForLifeOfAcc = 0;

    public void addAmount(double amount) {
        this.originalBalance += amount;
        this.currentBalance += amount;
        this.totalContributionForLifeOfAcc += amount;
    }

    public void reduceAmount(double amount) {
        this.originalBalance -= amount;
        this.currentBalance -= amount;
        this.totalContributionForLifeOfAcc -= amount;
    }
}
