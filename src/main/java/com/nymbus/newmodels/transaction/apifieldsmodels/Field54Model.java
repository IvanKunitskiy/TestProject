package com.nymbus.newmodels.transaction.apifieldsmodels;

import com.nymbus.core.utils.Functions;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class Field54Model {
    private String accountType;
    private String amountType;
    private String currencyCode;
    private String signIndicator;
    private double amount;

    @Getter(AccessLevel.NONE) private final int MAX_LENGTH = 12;
    @Getter(AccessLevel.NONE) private final String ZERO_SYMBOL = "0";

    public String getData() {
        String formattedAmount = Functions.getStringValueWithOnlyDigits(amount);
        String additionalZeros = Functions.getAdditionalSymbols(formattedAmount.length(),  MAX_LENGTH,  ZERO_SYMBOL);

        return accountType + amountType + currencyCode + signIndicator + additionalZeros + formattedAmount;
    }
}