package com.nymbus.actions.transfers;

import com.nymbus.core.utils.Generator;
import com.nymbus.newmodels.client.other.transfer.HighBalanceTransfer;
import com.nymbus.pages.Pages;

public class EditTransferActions {

    public void changeTransferData(HighBalanceTransfer highBalanceTransfer) {
        highBalanceTransfer.setHighBalance(String.valueOf(Generator.genInt(100, 900)));
        Pages.editTransferPage().setHighBalance(highBalanceTransfer.getHighBalance());
        highBalanceTransfer.setMaxAmountToTransfer(String.valueOf(Generator.genInt(100, 900)));
        Pages.editTransferPage().setMaxAmount(highBalanceTransfer.getMaxAmountToTransfer());
        highBalanceTransfer.setTransferCharge(String.valueOf(Generator.genInt(100, 900)));
        Pages.editTransferPage().setTransferCharge(highBalanceTransfer.getTransferCharge());
        Pages.editTransferPage().clickSaveButton();
    }
}
