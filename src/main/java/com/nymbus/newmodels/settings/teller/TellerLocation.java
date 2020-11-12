package com.nymbus.newmodels.settings.teller;

import lombok.Data;

@Data
public class TellerLocation {
    private String branchName;
    private String bankName;
    private String addressLine1;
    private String city;
    private String state;
    private String zipCode;
    private String phoneNumber;
}
