package com.nymbus.newmodels.notice;

import lombok.Data;

@Data
public class Notice {
    private String date;
    private String bankBranch;
    private String subType;
    private String accountNumber;
}
