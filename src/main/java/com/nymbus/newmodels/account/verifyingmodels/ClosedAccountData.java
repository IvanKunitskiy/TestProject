package com.nymbus.newmodels.account.verifyingmodels;

import com.nymbus.core.utils.DateTime;
import lombok.Data;

@Data
public class ClosedAccountData {
    private double currentBalance = 0;
    private double availableBalance = 0;
    private double accruedInterest = 0;
    private String accountStatus = "Closed";
    private String dateClosed = DateTime.getDateTodayPlusDaysWithFormat(0, "MM/dd/yyyy");
}