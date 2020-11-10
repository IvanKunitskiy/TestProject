package com.nymbus.newmodels.accountinstructions.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StopType {
    STOP_ACH_ONLY("Stop ACH Only"),
    STOP_ALL_ITEMS("Stop ALL Items");

    private final String stopType;
}
