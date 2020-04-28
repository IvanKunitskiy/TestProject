package com.nymbus.newmodels.transaction.verifyingModels;

import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.transaction.enums.GLFunctionValue;
import lombok.Data;

@Data
public class WebAdminTransactionData {
    private String postingDate;
    private GLFunctionValue glFunctionValue;
    private String amount;
    private String transactionHeaderId;

    public void setPostingDate(String date) {
        this.postingDate = DateTime.getDateWithFormat(date, "MM/dd/yyyy", "yyyy-MM-dd");
    }
}