package com.nymbus.newmodels.client.other.verifyingmodels;

import lombok.Data;

@Data
public class MaintenanceHistoryDebitCardVerifyingModel {
    private MaintenanceHistoryRowModel row;

    public MaintenanceHistoryDebitCardVerifyingModel() {
        row = new MaintenanceHistoryRowModel();
    }
}
