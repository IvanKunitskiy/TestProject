package com.nymbus.actions.loans;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DaysBaseYearBase {
    DAY_YEAR_365_365("365/365 day year"),
    DAY_YEAR_365_360("365/360 day year"),
    DAYS_360_YEAR_365("360/365"),
    DAY_YEAR_360_360("360/360 day year"),
    DAY_YEAR_365_365_OR_366("365/365or366");

    private final String daysBaseYearBase;
}
