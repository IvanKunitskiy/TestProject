package com.nymbus.newmodels.transaction.verifyingModels;

import lombok.Data;

import java.util.Objects;

@Data
public class BalanceData {
    private double currentBalance = 0;
    private double availableBalance = 0;
    //private double aggregateBalanceYearToDate = 0;
    // private double totalContributionsForLifeOfAccount = 0;


    public void addAmount(double amount) {
        this.availableBalance += amount;
        this.currentBalance += amount;
        //this.aggregateBalanceYearToDate += amount;
        // this.totalContributionsForLifeOfAccount += amount;
    }

    public void subtractAmount(double amount) {
        this.availableBalance -= amount;
        this.currentBalance -= amount;
        //this.aggregateBalanceYearToDate -= amount;
        // this.totalContributionsForLifeOfAccount -= amount;
    }

    public void applyHoldInstruction(double instructionAmount) {
        this.availableBalance = this.currentBalance - instructionAmount;
    }

    public void removeHoldInstruction(double instructionAmount) {
        this.availableBalance = this.availableBalance + instructionAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BalanceData that = (BalanceData) o;
        return currentBalance == that.currentBalance
                && availableBalance == that.availableBalance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentBalance, availableBalance);
    }
}
