package com.nymbus.newmodels.account.loanaccount;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RateRoundingFactor {
    ONE("1"),
    ZERO_SEVENTY_FIVE_THOUSANDTH(".75"),
    ZERO_FIFTY_THOUSANDTH(".50"),
    ZERO_TWENTY_FIVE_THOUSANDTH(".25"),
    ONE_TWENTY_FIVE_THOUSANDTH(".125");

    private final String rateRoundingFactor;
}
