package com.nymbus.actions.transfers;

import com.nymbus.newmodels.client.other.transfer.HighBalanceTransfer;
import com.nymbus.newmodels.client.other.transfer.TransferType;
import com.nymbus.pages.Pages;
import org.testng.Assert;

import java.util.List;
import java.util.Random;

public class AddNewTransferActions {

    public void addNewTransfer(HighBalanceTransfer transfer) {
        Pages.accountNavigationPage().clickTransfersTab();
        Pages.transfersPage().clickNewTransferButton();
        TransfersActions.addNewTransferActions().setHighBalanceTransferType(transfer);
        TransfersActions.addNewTransferActions().setHighBalanceFromAccount(transfer);
        TransfersActions.addNewTransferActions().setHighBalanceToAccount(transfer);
        Pages.newTransferPage().setHighBalance(transfer.getHighBalance());
        Pages.newTransferPage().setMaxAmount(transfer.getMaxAmountToTransfer());
        Pages.newTransferPage().setTransferCharge(transfer.getTransferCharge());
        Pages.newTransferPage().clickSaveButton();
    }

    public void setHighBalanceTransferType(HighBalanceTransfer highBalanceTransfer) {
        Pages.newTransferPage().clickTransferTypeSelectorButton();
        List<String> listOfTransferType = Pages.newTransferPage().getTransferTypeList();

        Assert.assertTrue(listOfTransferType.size() > 0, "There are no options available");
        if (highBalanceTransfer.getTransferType() == null) {
            highBalanceTransfer.setTransferType(TransferType.valueOf(listOfTransferType.get(new Random().nextInt(listOfTransferType.size())).trim()));
        }
        Pages.newTransferPage().clickTransferTypeSelectorOption(highBalanceTransfer.getTransferType().getTransferType());
    }

    public void setHighBalanceFromAccount(HighBalanceTransfer highBalanceTransfer) {
        Pages.newTransferPage().clickFromAccountSelectorButton();
        List<String> listOfFromAccount = Pages.newTransferPage().getFromAccountList();

        Assert.assertTrue(listOfFromAccount.size() > 0, "There are no options available");
        Pages.newTransferPage().clickFromAccountSelectorOption(highBalanceTransfer.getFromAccount().getAccountNumber());
    }

    public void setHighBalanceToAccount(HighBalanceTransfer highBalanceTransfer) {
        Pages.newTransferPage().clickToAccountSelectorButton();
        List<String> listOfToAccount = Pages.newTransferPage().getToAccountList();

        Assert.assertTrue(listOfToAccount.size() > 0, "There are no options available");
        Pages.newTransferPage().clickToAccountSelectorOption(highBalanceTransfer.getToAccount().getAccountNumber());
    }
}
