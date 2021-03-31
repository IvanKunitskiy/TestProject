package com.nymbus.newmodels.account.loanaccount;

import lombok.Data;

import java.util.Objects;

@Data
public class PaymentDueData {
    private int accountId;
    private String dueDate;
    private double principal;
    private String interest;
    private double escrow;
    private double amount;
    private String dateAssessed;
    private String paymentDueType;
    private String paymentDueStatus;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentDueData that = (PaymentDueData) o;
        return accountId == that.accountId && Double.compare(that.principal, principal) == 0 && Objects.equals(that.interest, interest) && Double.compare(that.escrow, escrow) == 0 && Double.compare(that.amount, amount) == 0 && Objects.equals(dueDate, that.dueDate) && Objects.equals(dateAssessed, that.dateAssessed) && Objects.equals(paymentDueType, that.paymentDueType) && Objects.equals(paymentDueStatus, that.paymentDueStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, dueDate, principal, interest, escrow, amount, dateAssessed, paymentDueType, paymentDueStatus);
    }
}
