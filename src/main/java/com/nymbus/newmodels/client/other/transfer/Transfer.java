package com.nymbus.newmodels.client.other.transfer;

import com.nymbus.newmodels.account.Account;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Transfer {
    @NonNull private TransferType transferType;
    @NonNull private String nextDateOfTransfer;
    private String expirationDate;
    @NonNull private Account fromAccount;
    @NonNull private Account toAccount;
    @NonNull private String amount;
    @NonNull private Frequency frequency;
    private String eftChargeCode;
    private String transferCharge;
}

