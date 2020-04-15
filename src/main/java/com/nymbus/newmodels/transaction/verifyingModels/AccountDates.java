package com.nymbus.newmodels.transaction.verifyingModels;

import lombok.Data;

@Data
public class AccountDates {
    private  String lastDepositDateBeforeTransaction = "";
    private  String lastActivityDateBeforeTransaction = "";
    private String lastDepositDate = "";
    private String lastActivityDate = "";
    private int numberOfDeposits = 0;
    private double lastDepositAmount = 0;



    public void reduceNumberOfDeposits(int value) {
        numberOfDeposits -= value;
    }

    public void reduceLastDepositAmount(double value) {
        lastDepositAmount -= value;
    }
}