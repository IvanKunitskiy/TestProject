package com.nymbus.newmodels.client.other.transfer;

import com.nymbus.newmodels.account.Account;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class HighBalanceTransfer implements Transfer {
    @NonNull private TransferType transferType;
    private String effectiveDate;
    private String expirationDate;
    @NonNull private Account fromAccount;
    @NonNull private Account toAccount;
    private String highBalance;
    private String maxAmountToTransfer;
    private String eftChargeCode;
    private String transferCharge;
}
