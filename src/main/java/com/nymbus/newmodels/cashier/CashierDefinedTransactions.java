package com.nymbus.newmodels.cashier;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CashierDefinedTransactions {
    TRANSFER_FROM_SAVINGS_TO_CHECKING_WITH_FEE("TRANSFER FROM SAVINGS TO CHECKING WITH FEE"),
    TRANSFER_FROM_SAV_TO_CHK("TRANSFER FROM SAV TO CHK"),
    TRANSFER_FROM_SAV_TO_CHK_Print_Notice_On_Entry("TRANSFER FROM SAV TO CHK (Print Notice = On Entry)");

    private String product;
}
