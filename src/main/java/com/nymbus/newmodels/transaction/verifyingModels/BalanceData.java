package com.nymbus.newmodels.transaction.verifyingModels;

import lombok.Data;

import java.util.Objects;

@Data
public class BalanceData {
    private double currentBalance = 0;
    private double availableBalance = 0;
    private double aggregateBalanceYearToDate = 0;
    private double totalContributionsForLifeOfAccount = 0;


    public void addAmount(double amount) {
        this.availableBalance += amount;
        this.currentBalance += amount;
        this.aggregateBalanceYearToDate += amount;
        this.totalContributionsForLifeOfAccount +=amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BalanceData that = (BalanceData) o;
        return currentBalance == that.currentBalance
                && availableBalance == that.availableBalance
                && aggregateBalanceYearToDate == that.aggregateBalanceYearToDate
                && totalContributionsForLifeOfAccount == that.totalContributionsForLifeOfAccount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentBalance, availableBalance, aggregateBalanceYearToDate, totalContributionsForLifeOfAccount);
    }
}
