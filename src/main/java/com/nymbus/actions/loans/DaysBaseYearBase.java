package com.nymbus.actions.loans;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DaysBaseYearBase {
    DAY_YEAR_365_365("365/365 day year"),
    DAY_YEAR_365_360("365/360 day year");

    private final String daysBaseYearBase;
}
