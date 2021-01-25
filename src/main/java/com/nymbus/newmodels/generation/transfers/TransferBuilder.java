package com.nymbus.newmodels.generation.transfers;

import com.nymbus.core.utils.DateTime;
import com.nymbus.core.utils.Generator;
import com.nymbus.newmodels.client.other.transfer.*;

public class TransferBuilder {
    public HighBalanceTransfer getHighBalanceTransfer() {
        HighBalanceTransfer transfer = new HighBalanceTransfer();

        transfer.setTransferType(TransferType.HIGH_BALANCE_TRANSFER);
        transfer.setExpirationDate(DateTime.getTomorrowDate("MM/dd/yyyy"));
        transfer.setHighBalance(String.valueOf(Generator.genInt(100, 900)));
        transfer.setMaxAmountToTransfer(String.valueOf(Generator.genInt(100, 900)));
        transfer.setEftChargeCode("Account Analysis");
        transfer.setTransferCharge(String.valueOf(Generator.genInt(100, 900)));

        return transfer;
    }

    public Transfer getTransfer() {
        Transfer transfer = new Transfer();

        transfer.setTransferType(TransferType.TRANSFER);
        transfer.setFrequency(Frequency.ONE_TIME_ONLY);
        transfer.setAmount("2000");
        transfer.setEftChargeCode("Account Analysis");
        transfer.setTransferCharge(String.valueOf(Generator.genInt(100, 900)));

        return transfer;
    }

    public InsufficientFundsTransfer getInsufficientFundsTransfer() {
        InsufficientFundsTransfer transfer = new InsufficientFundsTransfer();

        transfer.setTransferType(TransferType.INSUFFICIENT_FUNDS_TRANSFER);
        transfer.setAmountToTransfer("50.99");
        transfer.setNearestAmount("0.10");
        transfer.setTransferCharge("1.99");

        return transfer;
    }

    public LoanPaymentTransfer getLoanPaymentTransfer() {
        LoanPaymentTransfer transfer = new LoanPaymentTransfer();

        transfer.setTransferType(TransferType.LOAN_PAYMENT);
        transfer.setExpirationDate(DateTime.getTomorrowDate("MM/dd/yyyy"));
        transfer.setAdvanceDaysFromDueDate(String.valueOf(Generator.genInt(1, 30)));
        transfer.setEftChargeCode("Account Analysis");
        transfer.setTransferCharge(String.valueOf(Generator.genInt(100, 900)));

        return transfer;
    }

}