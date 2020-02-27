package com.nymbus.models.client.clientdetails.type.individual;

public enum MaritalStatus {
    DIVORCED("Divorced"),
    LEGALLY_SEPARATED("Legally Separated"),
    MARRIED("Married"),
    SIGNIFICANT_OTHER("Significant Other"),
    SINGLE("Single");

    private final String maritalStatus;

    MaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }
}
