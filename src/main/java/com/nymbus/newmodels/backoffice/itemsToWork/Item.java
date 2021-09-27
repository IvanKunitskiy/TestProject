package com.nymbus.newmodels.backoffice.itemsToWork;

import lombok.Data;

@Data
public class Item {
    private String accountNumber;
    private String effectiveEntryDate;
    private double amount;
    private String dateTimePosted;
    private String rejectStatus;

}
