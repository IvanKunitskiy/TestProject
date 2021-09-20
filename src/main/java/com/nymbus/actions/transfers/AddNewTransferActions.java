package com.nymbus.actions.transfers;

import com.nymbus.newmodels.client.other.transfer.*;
import com.nymbus.pages.Pages;
import org.testng.Assert;

import java.util.Collections;
import java.util.Enumeration;
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

    public void addNewLoanPaymentTransfer(LoanPaymentTransfer loanPaymentTransfer) {
        Pages.accountNavigationPage().clickTransfersTab();
        Pages.transfersPage().clickNewTransferButton();
        TransfersActions.addNewTransferActions().setLoanPaymentTransferType(loanPaymentTransfer);
        Pages.newTransferPage().setExpirationDate(loanPaymentTransfer.getExpirationDate());
        TransfersActions.addNewTransferActions().setLoanPaymentFromAccount(loanPaymentTransfer);
        TransfersActions.addNewTransferActions().setLoanPaymentToAccount(loanPaymentTransfer);
        Pages.newTransferPage().setAdvanceDaysFromDueDate(loanPaymentTransfer.getAdvanceDaysFromDueDate());
        TransfersActions.addNewTransferActions().setEftChargeCode(loanPaymentTransfer);
        Pages.newTransferPage().setTransferCharge(loanPaymentTransfer.getTransferCharge());
        Pages.newTransferPage().clickSaveButton();
    }

    public void addExternalLoanPaymentTransfer(ExternalLoanPaymentTransfer externalLoanPaymentTransfer) {
    }

    public void addNewPeriodicLoanPaymentTransfer(Transfer transfer) {
        Pages.accountNavigationPage().clickTransfersTab();
        Pages.transfersPage().clickNewTransferButton();
        TransfersActions.addNewTransferActions().setTransferType(transfer);
        TransfersActions.addNewTransferActions().setTransferFromAccount(transfer);
        TransfersActions.addNewTransferActions().setTransferToAccount(transfer);
        TransfersActions.addNewTransferActions().setTransferFrequency(transfer);
        Pages.newTransferPage().setAmount(transfer.getAmount());
        TransfersActions.addNewTransferActions().setEftChargeCode(transfer);
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

    public void setInsufficientFundsTransferType(InsufficientFundsTransfer transfer) {
        Pages.newTransferPage().clickTransferTypeSelectorButton();
        List<String> listOfTransferType = Pages.newTransferPage().getTransferTypeList();

        Assert.assertTrue(listOfTransferType.size() > 0, "There are no options available");
        if (transfer.getTransferType() == null) {
            transfer.setTransferType(TransferType.valueOf(listOfTransferType.get(new Random().nextInt(listOfTransferType.size())).trim()));
        }
        Pages.newTransferPage().clickTransferTypeSelectorOption(transfer.getTransferType().getTransferType());
    }

    public void verifyAchBankAccountTypeOptions() {
        Pages.newTransferPage().clickAchBankAccountTypeSelectorButton();
        List<String> listOfAchBankAccountType = Pages.newTransferPage().getAchBankAccountTypeList();

        for(AchBankAccountType c : AchBankAccountType.values()){
            Assert.assertTrue(listOfAchBankAccountType.contains(c));
        }
    }

    public void setHighBalanceFromAccount(HighBalanceTransfer transfer) {
        Pages.newTransferPage().clickFromAccountSelectorButton();
        List<String> listOfFromAccount = Pages.newTransferPage().getFromAccountList();

        Assert.assertTrue(listOfFromAccount.size() > 0, "There are no options available");
        Pages.newTransferPage().clickFromAccountSelectorOption(transfer.getFromAccount().getAccountNumber());
    }

    public void setInsufficientFundsFromAccount(InsufficientFundsTransfer transfer) {
        Pages.newTransferPage().clickFromAccountSelectorButton();
        List<String> listOfFromAccount = Pages.newTransferPage().getFromAccountList();

        Assert.assertTrue(listOfFromAccount.size() > 0, "There are no options available");
        Pages.newTransferPage().clickFromAccountSelectorOption(transfer.getFromAccount().getAccountNumber());
    }

    public void setHighBalanceToAccount(HighBalanceTransfer transfer) {
        Pages.newTransferPage().clickToAccountSelectorButton();
        Pages.newTransferPage().setToAccountValue(transfer.getToAccount().getAccountNumber());
        Pages.newTransferPage().clickToAccountSelectorOption(transfer.getToAccount().getAccountNumber());
    }

    public void setLoanPaymentFromAccount(LoanPaymentTransfer transfer) {
        Pages.newTransferPage().clickFromAccountSelectorButton();
        List<String> listOfFromAccount = Pages.newTransferPage().getFromAccountList();

        Assert.assertTrue(listOfFromAccount.size() > 0, "There are no options available");
        Pages.newTransferPage().clickFromAccountSelectorOption(transfer.getFromAccount().getAccountNumber());
    }

    public void setLoanPaymentToAccount(LoanPaymentTransfer transfer) {
        Pages.newTransferPage().clickToAccountSelectorButton();
        Pages.newTransferPage().setToAccountValue(transfer.getToAccount().getAccountNumber());
        Pages.newTransferPage().clickToAccountSelectorOption(transfer.getToAccount().getAccountNumber());
    }

    public void setInsufficientFundsToAccount(InsufficientFundsTransfer transfer) {
        Pages.newTransferPage().clickToAccountSelectorButton();
        Pages.newTransferPage().setToAccountValue(transfer.getToAccount().getAccountNumber());
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

    public void setExternalLoanPaymentTransferType(ExternalLoanPaymentTransfer externalLoanPaymentTransfer) {
        Pages.newTransferPage().clickTransferTypeSelectorButton();
        List<String> listOfTransferType = Pages.newTransferPage().getTransferTypeList();

        Assert.assertTrue(listOfTransferType.size() > 0, "There are no options available");
        if (externalLoanPaymentTransfer.getTransferType() == null) {
            externalLoanPaymentTransfer.setTransferType(TransferType.valueOf(listOfTransferType.get(new Random().nextInt(listOfTransferType.size())).trim()));
        }
        Pages.newTransferPage().clickTransferTypeSelectorOption(externalLoanPaymentTransfer.getTransferType().getTransferType());
    }

    public void setLoanPaymentTransferType(LoanPaymentTransfer transfer) {
        Pages.newTransferPage().clickTransferTypeSelectorButton();
        List<String> listOfTransferType = Pages.newTransferPage().getTransferTypeList();

        Assert.assertTrue(listOfTransferType.size() > 0, "There are no options available");
        if (transfer.getTransferType() == null) {
            transfer.setTransferType(TransferType.valueOf(listOfTransferType.get(new Random().nextInt(listOfTransferType.size())).trim()));
        }
        Pages.newTransferPage().clickTransferTypeSelectorOption(transfer.getTransferType().getTransferType());
    }

    public void setEftChargeCode(LoanPaymentTransfer transfer) {
        Pages.newTransferPage().clickEftChargeCodeSelectorButton();
        List<String> listOfEftChargeCode = Pages.newTransferPage().getEftChargeCodeList();

        Assert.assertTrue(listOfEftChargeCode.size() > 0, "There are no options available");
        if (transfer.getEftChargeCode() == null) {
            transfer.setEftChargeCode(listOfEftChargeCode.get(new Random().nextInt(listOfEftChargeCode.size())).trim());
        }
        Pages.newTransferPage().clickEftChargeCodeSelectorOption(transfer.getEftChargeCode());
    }

    public void setEftChargeCode(Transfer transfer) {
        Pages.newTransferPage().clickEftChargeCodeSelectorButton();
        List<String> listOfEftChargeCode = Pages.newTransferPage().getEftChargeCodeList();

        Assert.assertTrue(listOfEftChargeCode.size() > 0, "There are no options available");
        if (transfer.getEftChargeCode() == null) {
            transfer.setEftChargeCode(listOfEftChargeCode.get(new Random().nextInt(listOfEftChargeCode.size())).trim());
        }
        Pages.newTransferPage().clickEftChargeCodeSelectorOption(transfer.getEftChargeCode());
    }

    public void setTransferFromAccount(Transfer transfer) {
        Pages.newTransferPage().clickFromAccountSelectorButton();
        List<String> listOfFromAccount = Pages.newTransferPage().getFromAccountList();

        Assert.assertTrue(listOfFromAccount.size() > 0, "There are no options available");
        Pages.newTransferPage().clickFromAccountSelectorOption(transfer.getFromAccount().getAccountNumber());
    }

    public void setTransferToAccount(Transfer transfer) {
        Pages.newTransferPage().clickToAccountSelectorButton();
        Pages.newTransferPage().setToAccountValue(transfer.getToAccount().getAccountNumber());
        Pages.newTransferPage().clickToAccountSelectorOption(transfer.getToAccount().getAccountNumber());
    }

    public void setTransferFrequency(Transfer transfer) {
        Pages.newTransferPage().clickFrequencySelectorButton();
        List<String> listOfFrequency = Pages.newTransferPage().getFrequencyList();

        Assert.assertTrue(listOfFrequency.size() > 0, "There are no options available");
        Pages.newTransferPage().clickFrequencySelectorOption(transfer.getFrequency().getFrequencyType());
    }
}
