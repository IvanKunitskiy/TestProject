package com.nymbus.actions.transfers;

import com.nymbus.newmodels.client.other.transfer.HighBalanceTransfer;
import com.nymbus.newmodels.client.other.transfer.Transfer;
import com.nymbus.newmodels.client.other.transfer.TransferType;
import com.nymbus.newmodels.client.other.transfer.Transfers;
import com.nymbus.pages.Pages;
import org.testng.Assert;

import java.util.List;
import java.util.Random;

public class AddNewTransferActions {

    public void addNewHighBalanceTransfer(HighBalanceTransfer transfer) {
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

    public void addNewTransfer(Transfer transfer) {
        Pages.accountNavigationPage().clickTransfersTab();
        Pages.transfersPage().clickNewTransferButton();
        TransfersActions.addNewTransferActions().setTransferType(transfer);
        TransfersActions.addNewTransferActions().setTransferFromAccount(transfer);
        TransfersActions.addNewTransferActions().setTransferToAccount(transfer);
        TransfersActions.addNewTransferActions().setTransferFrequency(transfer);
        Pages.newTransferPage().setAmount(transfer.getAmount());
        Pages.newTransferPage().setTransferCharge(transfer.getTransferCharge());
        Pages.newTransferPage().clickSaveButton();
    }

    public void setHighBalanceTransferType(HighBalanceTransfer transfer) {
        Pages.newTransferPage().clickTransferTypeSelectorButton();
        List<String> listOfTransferType = Pages.newTransferPage().getTransferTypeList();

        Assert.assertTrue(listOfTransferType.size() > 0, "There are no options available");
        if (transfer.getTransferType() == null) {
            transfer.setTransferType(TransferType.valueOf(listOfTransferType.get(new Random().nextInt(listOfTransferType.size())).trim()));
        }
        Pages.newTransferPage().clickTransferTypeSelectorOption(transfer.getTransferType().getTransferType());
    }

    public void setHighBalanceFromAccount(HighBalanceTransfer transfer) {
        Pages.newTransferPage().clickFromAccountSelectorButton();
        List<String> listOfFromAccount = Pages.newTransferPage().getFromAccountList();

        Assert.assertTrue(listOfFromAccount.size() > 0, "There are no options available");
        Pages.newTransferPage().clickFromAccountSelectorOption(transfer.getFromAccount().getAccountNumber());
    }

    public void setHighBalanceToAccount(HighBalanceTransfer transfer) {
        Pages.newTransferPage().clickToAccountSelectorButton();
        List<String> listOfToAccount = Pages.newTransferPage().getToAccountList();

        Assert.assertTrue(listOfToAccount.size() > 0, "There are no options available");
        Pages.newTransferPage().clickToAccountSelectorOption(transfer.getToAccount().getAccountNumber());
    }

    public void setTransferType(Transfer transfer) {
        Pages.newTransferPage().clickTransferTypeSelectorButton();
        List<String> listOfTransferType = Pages.newTransferPage().getTransferTypeList();

        Assert.assertTrue(listOfTransferType.size() > 0, "There are no options available");
        if (transfer.getTransferType() == null) {
            transfer.setTransferType(TransferType.valueOf(listOfTransferType.get(new Random().nextInt(listOfTransferType.size())).trim()));
        }
        Pages.newTransferPage().clickTransferTypeSelectorOption(transfer.getTransferType().getTransferType());
    }

    public void setTransferFromAccount(Transfer transfer) {
        Pages.newTransferPage().clickFromAccountSelectorButton();
        List<String> listOfFromAccount = Pages.newTransferPage().getFromAccountList();

        Assert.assertTrue(listOfFromAccount.size() > 0, "There are no options available");
        Pages.newTransferPage().clickFromAccountSelectorOption(transfer.getFromAccount().getAccountNumber());
    }

    public void setTransferToAccount(Transfer transfer) {
        Pages.newTransferPage().clickToAccountSelectorButton();
        List<String> listOfToAccount = Pages.newTransferPage().getToAccountList();

        Assert.assertTrue(listOfToAccount.size() > 0, "There are no options available");
        Pages.newTransferPage().clickToAccountSelectorOption(transfer.getToAccount().getAccountNumber());
    }

    public void setTransferFrequency(Transfer transfer) {
        Pages.newTransferPage().clickFrequencySelectorButton();
        List<String> listOfFrequency = Pages.newTransferPage().getFrequencyList();

        Assert.assertTrue(listOfFrequency.size() > 0, "There are no options available");
        Pages.newTransferPage().clickFrequencySelectorOption(transfer.getFrequency().getFrequencyType());
    }
}