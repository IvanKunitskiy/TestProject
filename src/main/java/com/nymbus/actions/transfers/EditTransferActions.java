package com.nymbus.actions.transfers;

import com.nymbus.core.utils.Generator;
import com.nymbus.newmodels.client.other.transfer.HighBalanceTransfer;
import com.nymbus.newmodels.client.other.transfer.LoanPaymentTransfer;
import com.nymbus.pages.Pages;
import org.testng.Assert;

import java.util.List;
import java.util.Random;

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

    public void setRandomEftChargeCode(LoanPaymentTransfer transfer) {
        Pages.editTransferPage().clickEftChargeCodeSelectorButton();
        List<String> listOfEftChargeCode = Pages.editTransferPage().getEftChargeCodeList();
        Assert.assertTrue(listOfEftChargeCode.size() > 0, "There are no options available");
        transfer.setEftChargeCode(listOfEftChargeCode.get(new Random().nextInt(listOfEftChargeCode.size())).trim());
        Pages.editTransferPage().clickEftChargeCodeSelectorOption(transfer.getEftChargeCode());
    }
}
