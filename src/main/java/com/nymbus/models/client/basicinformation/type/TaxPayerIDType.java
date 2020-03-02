package com.nymbus.models.client.basicinformation.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TaxPayerIDType {
    BUSINESS_TIN("Business TIN"),
    INDIVIDUAL_SSN("Individual SSN"),
    NON_REPORTABLE("Non-Reportable"),
    UNDETERMINABLE("Undeterminable");

    private final String taxPayerIDType;
}
