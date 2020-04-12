package com.nymbus.newmodels.accountinstructions.verifyingModels;

import lombok.Data;

import java.util.Objects;

@Data
public class InstructionBalanceData {
    private double currentBalance = 0;
    private double availableBalance = 0;


    public void reduceAmount(double amount) {
        this.availableBalance -= amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
       InstructionBalanceData that = (InstructionBalanceData) o;
        return currentBalance == that.currentBalance
                && availableBalance == that.availableBalance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentBalance, availableBalance);
    }
}
