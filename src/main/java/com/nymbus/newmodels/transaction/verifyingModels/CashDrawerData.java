package com.nymbus.newmodels.transaction.verifyingModels;

import lombok.Data;

@Data
public class CashDrawerData {
    private double cashIn = 0;
    private double cashOut = 0;
    private double countedCash = 0;
    private double hundredsAmount = 0;
    private double fiftiesAmount = 0;

    public void addCashIn(double amount) {
        this.cashIn += amount;
    }

    public void reduceCashIn(double amount) {
        this.cashIn -= amount;
    }

    public void addCashOut(double amount) {
        this.cashOut += amount;
    }

    public void reduceCashOut(double amount) {
        this.cashOut -= amount;
    }

    public void addCountedCash(double amount) {
        this.countedCash += amount;
    }

    public void reduceCountedCash(double amount) {
        this.countedCash -= amount;
    }

    public void addHundredsAmount(double amount) {
        this.hundredsAmount += amount;
    }

    public void reduceHundredsAmount(double amount) {
        this.hundredsAmount -= amount;
    }

    public void addFiftiesAmount(double amount) {
        this.fiftiesAmount += amount;
    }

    public void reduceFiftiesAmount(double amount) {
        this.fiftiesAmount -= amount;
    }
}