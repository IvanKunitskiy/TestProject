package com.nymbus.models.client.basicinformation.type;

public enum TaxPayerIDType {
    BUSINESS_TIN("Business TIN"),
    INDIVIDUAL_SSN("Individual SSN"),
    NON_REPORTABLE("Non-Reportable"),
    UNDETERMINABLE("Undeterminable");

    private final String taxPayerIDType;

    TaxPayerIDType(String taxPayerIDType) {
        this.taxPayerIDType = taxPayerIDType;
    }

    public String getTaxPayerIDType() {
        return taxPayerIDType;
    }
}
