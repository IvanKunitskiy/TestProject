package com.nymbus.newmodels.maintenance;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Tool {
    INTEREST_RATE_CHANGE("Interest Rate Change"),
    REG_DD_CALCULATOR("Reg DD Calculator"),
    QUOTE_PAYOFF("Quote Payoff"),
    LOAN_PAYOFF_CHARGES("Loan Payoff Charges"),
    LOAN_PAYOFF_PREPAYMENT_PENALTY("Loan Payoff Prepayment Penalty"),
    LOAN_PARTICIPATIONS("Loan Participations"),
    TEASER_RATE_SETUP("Teaser Rate Setup"),
    RESERVE_PREMIUM_PROCESSING("Reserve/Premium Processing");

    private final String tool;
}
