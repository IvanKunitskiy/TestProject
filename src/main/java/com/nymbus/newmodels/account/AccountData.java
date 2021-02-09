package com.nymbus.newmodels.account;

import lombok.Data;

@Data
public class AccountData {
    private String pifNumber;
    private String accountNumber;
    private String currentBalance;
    private String availableBalance;
    private String payoffAmount;
    private String accruedInterest;
    private String lateChangesDue;
    private String totalPastDue;
    private String principalNextDue;
    private String interestNextDue;
    private String totalNextDue;
    private String currentDateDue;
    private String loanClassCode;
    private String activePaymentAmount;
}
