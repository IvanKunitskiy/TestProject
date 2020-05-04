package com.nymbus.newmodels.transaction.verifyingModels;

import com.nymbus.core.utils.DateTime;
import lombok.Data;

@Data
public class NonTellerTransactionData {
    private String cardNumber;
    private String expirationDate;
    private String amount;

    public void setAmount(double amount) {
        this.amount = String.format("%.2f", amount).replace(".", "");
    }

    private void setExpirationDate(String date) {
        this.expirationDate = DateTime.getDateWithFormat(date, "MM/dd/yyyy", "yyMM");
    }
}