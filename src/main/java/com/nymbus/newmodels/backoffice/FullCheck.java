package com.nymbus.newmodels.backoffice;

import lombok.Data;

import java.util.Objects;

@Data
public class FullCheck extends Check {
    private String remitter;
    private String phone;
    private String documentType;
    private String documentID;
    private String branch;
    private double fee;
    private String cashPurchased;
    private String purchaseAccount;

    public void fromCheck(Check check) {
        setCheckNumber(check.getCheckNumber());
        setPurchaser(check.getPurchaser());
        setPayee(check.getPayee());
        setDate(check.getDate());
        setInitials(check.getInitials());
        setCheckType(check.getCheckType());
        setStatus(check.getStatus());
        setAmount(check.getAmount());

    }

    @Override
    public String toString() {
        return "FullCheck{" +
                "checkNumber='" + checkNumber + '\'' +
                ", purchaser='" + purchaser + '\'' +
                ", payee='" + payee + '\'' +
                ", date='" + date + '\'' +
                ", initials='" + initials + '\'' +
                ", checkType='" + checkType + '\'' +
                ", status='" + status + '\'' +
                ", amount=" + amount +
                ", remitter='" + remitter + '\'' +
                ", phone='" + phone + '\'' +
                ", documentType='" + documentType + '\'' +
                ", documentID='" + documentID + '\'' +
                ", branch='" + branch + '\'' +
                ", fee=" + fee +
                ", cashPurchased='" + cashPurchased + '\'' +
                ", purchaseAccount='" + purchaseAccount + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FullCheck fullCheck = (FullCheck) o;
        return Double.compare(fullCheck.fee, fee) == 0 && Objects.equals(remitter, fullCheck.remitter) && Objects.equals(phone, fullCheck.phone) && Objects.equals(documentType, fullCheck.documentType) && Objects.equals(documentID, fullCheck.documentID) && Objects.equals(branch, fullCheck.branch) && Objects.equals(cashPurchased, fullCheck.cashPurchased) && Objects.equals(purchaseAccount, fullCheck.purchaseAccount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), remitter, phone, documentType, documentID, branch, fee, cashPurchased, purchaseAccount);
    }
}