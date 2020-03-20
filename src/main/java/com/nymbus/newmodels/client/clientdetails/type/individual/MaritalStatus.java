package com.nymbus.newmodels.client.clientdetails.type.individual;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MaritalStatus {
    DIVORCED("Divorced"),
    LEGALLY_SEPARATED("Legally Separated"),
    MARRIED("Married"),
    SIGNIFICANT_OTHER("Significant Other"),
    SINGLE("Single");

    private final String maritalStatus;
}
