package com.nymbus.newmodels.account.loanaccount;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DaysYearBase {
    DAYS_360_YEAR_365("360/365");

    private final String daysYearCount;
}
