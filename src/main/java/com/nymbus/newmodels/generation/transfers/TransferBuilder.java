package com.nymbus.newmodels.generation.transfers;

import com.nymbus.core.utils.DateTime;
import com.nymbus.core.utils.Generator;
import com.nymbus.newmodels.client.other.transfer.HighBalanceTransfer;
import com.nymbus.newmodels.client.other.transfer.TransferType;

public class TransferBuilder {
    public HighBalanceTransfer getHighBalanceTransfer() {
        HighBalanceTransfer transfer = new HighBalanceTransfer();

        transfer.setTransferType(TransferType.HIGH_BALANCE_TRANSFER);
        transfer.setExpirationDate(DateTime.getTomorrowDate("MM/dd/yyyy"));
//        transfer.setFromAccount()
//        transfer.setToAccount();
        transfer.setHighBalance(String.valueOf(Generator.genInt(100, 900)));
        transfer.setMaxAmountToTransfer(String.valueOf(Generator.genInt(100, 900)));
        transfer.setEftChargeCode("Account Analysis");
        transfer.setTransferCharge(String.valueOf(Generator.genInt(100, 900)));

        return transfer;
    }
}