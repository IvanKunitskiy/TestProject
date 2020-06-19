package com.nymbus.newmodels.account.verifyingmodels;

import com.nymbus.actions.webadmin.WebAdminActions;
import lombok.Data;

@Data
public class ClosedAccountData {
    private double currentBalance = 0;
    private double availableBalance = 0;
    private double accruedInterest = 0;
    private String accountStatus = "Closed";
    private String dateClosed;
}