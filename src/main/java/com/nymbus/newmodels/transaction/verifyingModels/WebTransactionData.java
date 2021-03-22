package com.nymbus.newmodels.transaction.verifyingModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebTransactionData {
    String effectiveDate;
    String number = "";
    double amount = 0;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WebTransactionData that = (WebTransactionData) o;
        return Double.compare(that.amount, amount) == 0 && Objects.equals(effectiveDate, that.effectiveDate) && Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(effectiveDate, number, amount);
    }
}
