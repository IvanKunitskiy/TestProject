package com.nymbus.newmodels.client.other.transfer;

import com.nymbus.newmodels.account.Account;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class InsufficientFundsTransfer {
    @NonNull
    private TransferType transferType;
    private String expirationDate;
    @NonNull private Account fromAccount;
    @NonNull private Account toAccount;
    @NonNull private String nearestAmount;
    @NonNull private String amountToTransfer;
    private String eftChargeCode;
    private String transferCharge;
}
