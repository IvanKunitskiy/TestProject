package com.nymbus.newmodels.client.other.verifyingmodels;

import lombok.Data;

@Data
public class MaintenanceHistoryRowModel {
    private String dateTime;
    private String user;
    private String fieldName;
    private String oldValue;
    private String newValue;
    private String changeType;
}
