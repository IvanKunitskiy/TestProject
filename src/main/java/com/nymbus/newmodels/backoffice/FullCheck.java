package com.nymbus.newmodels.backoffice;

import lombok.Data;

import java.util.Objects;

@Data
public class FullCheck extends Check{
    public String remitter;
    public String phone;
    public String documentType;
    public String documentID;
    public String branch;
    public double fee;
    public String cashPurchased;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FullCheck fullCheck = (FullCheck) o;
        return Double.compare(fullCheck.fee, fee) == 0 && Objects.equals(remitter, fullCheck.remitter) && Objects.equals(phone, fullCheck.phone) && Objects.equals(documentType, fullCheck.documentType) && Objects.equals(documentID, fullCheck.documentID) && Objects.equals(branch, fullCheck.branch) && Objects.equals(cashPurchased, fullCheck.cashPurchased);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), remitter, phone, documentType, documentID, branch, fee, cashPurchased);
    }
}