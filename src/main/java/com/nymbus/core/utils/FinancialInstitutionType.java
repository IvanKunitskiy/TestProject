package com.nymbus.core.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FinancialInstitutionType {
    FEDERAL_CREDIT_UNION("Federal Credit Union"),
    BANK("Bank");
    private final String financialInstitutionType;
}
