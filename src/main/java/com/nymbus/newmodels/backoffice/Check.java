package com.nymbus.newmodels.backoffice;

import lombok.Data;

import java.util.Objects;

@Data
public class Check {
    public String checkNumber;
    public String purchaser;
    public String payee;
    public String date;
    public String initials;
    public String checkType;
    public String status;
    public double amount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Check check = (Check) o;
        return checkNumber == check.checkNumber && Objects.equals(purchaser, check.purchaser) &&
                Objects.equals(payee, check.payee) && Objects.equals(date, check.date) &&
                Objects.equals(initials, check.initials) && Objects.equals(checkType, check.checkType) &&
                Objects.equals(status, check.status) && Objects.equals(amount, check.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(checkNumber, purchaser, payee, date, initials, checkType, status, amount);
    }
}
