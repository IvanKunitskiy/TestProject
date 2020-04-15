package com.nymbus.newmodels.client.other.transfer;

import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.client.other.File;
import com.nymbus.newmodels.client.other.document.DocumentDetails;
import com.nymbus.newmodels.client.other.document.IDType;
import lombok.NonNull;

public interface Transfers {
    TransferType getTransferType();
    void setTransferType(TransferType transferType);
    String getExpirationDate();
    DocumentDetails getDocumentDetails();
    Account getFromAccount();
    Account getToAccount();
    String getEftChargeCode();
    String getTransferCharge();
}
