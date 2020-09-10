package com.nymbus.newmodels.transaction.verifyingModels;

import lombok.Data;

@Data
public class ATMRequiredValueModel {
    private String terminalId;
    private String acronymValue;
    private double foreignFeeValue;
}