package com.nymbus.newmodels.account.verifyingmodels;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SafeDepositKeyValues {
    private String safeBoxSize;
    private double safeBoxRentalAmount;
}