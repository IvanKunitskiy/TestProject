package com.nymbus.newmodels.account.loanaccount;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CommitmentTypeAmt {
    NONE("None"),
    REVOLVING_AMOUNT("Revolving amount"),
    NON_REVOLVING("Non_Revolving"),
    LETTER_OF_CREDIT("Letter of Credit");

    private final String commitmentTypeAmt;
}
