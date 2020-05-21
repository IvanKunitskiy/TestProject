package com.nymbus.newmodels.transaction.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Denominations {
    HUNDREDS("onehundredsloose"),
    FIFTIES("fiftiesloose"),
    TWENTIES("twentiesloose");

    private final String denominationValue;
}
