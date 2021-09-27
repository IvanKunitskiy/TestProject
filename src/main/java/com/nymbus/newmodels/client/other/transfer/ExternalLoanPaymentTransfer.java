package com.nymbus.newmodels.client.other.transfer;

import com.nymbus.newmodels.account.Account;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class ExternalLoanPaymentTransfer {

    private TransferType transferType;
    private String expirationDate;
    @NonNull private Account internalAccount;
    private AchBankAccountType achBankAccountType;
    private String achBankRoutingNumber;
    private String achBankAccountNumber;
    private String achLeadDays;

}
