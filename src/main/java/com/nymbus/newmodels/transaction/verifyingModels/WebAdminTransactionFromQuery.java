package com.nymbus.newmodels.transaction.verifyingModels;

import lombok.Data;

@Data
public class WebAdminTransactionFromQuery {
    private String bankBranch;
    private String accountNumber;
    private String amount;
    private String effectiveEntryDate;
    private String itemType;
    private String uniqueEftDescription;
    private String transactionCode;
    private String transactionHeaderId;
    private String checkNumber;
}
