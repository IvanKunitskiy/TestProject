package com.nymbus.actions.account;

import com.nymbus.pages.Pages;

import java.util.ArrayList;
import java.util.List;

public class AccountPaymentInfoActions {

    // 'Transactions' section actions
    public boolean isRecordWithSpecificStatusPresent(String status) {
        List<String> records = Pages.accountPaymentInfoPage().getTransactionsSectionRecordsStatusList();
        return records.contains(status);
    }
}
