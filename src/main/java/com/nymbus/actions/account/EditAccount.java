package com.nymbus.actions.account;

import com.nymbus.models.account.Account;
import com.nymbus.pages.Pages;
import org.testng.Assert;

import java.util.List;
import java.util.Random;

public class EditAccount {

    public void setWhenSurchargesRefunded(Account account) {
        Pages.editAccountPage().clickWhenSurchargesRefundedSelectorButton();
        List<String> listOfWhenSurchargesRefunded = Pages.editAccountPage().getWhenSurchargesRefundedList();

        Assert.assertTrue(listOfWhenSurchargesRefunded.size() > 0, "There are no options available.");
        if (account.getWhenSurchargesRefunded() == null) {
            account.setWhenSurchargesRefunded(listOfWhenSurchargesRefunded.get(new Random().nextInt(listOfWhenSurchargesRefunded.size())).trim());
        }
        Pages.editAccountPage().clickWhenSurchargesRefundedSelectorOption(account.getWhenSurchargesRefunded());
    }

    public void setReasonAutoOdChgWaived(Account account) {
        Pages.editAccountPage().clickReasonAutoOdChgWaivedSelectorButton();
        List<String> listOfReasonAutoOdChgWaived = Pages.editAccountPage().getReasonAutoOdChgWaivedList();

        Assert.assertTrue(listOfReasonAutoOdChgWaived.size() > 0, "There are no options available");
        if (account.getReasonAutoOdChgWaived() == null) {
            account.setReasonAutoOdChgWaived(listOfReasonAutoOdChgWaived.get(new Random().nextInt(listOfReasonAutoOdChgWaived.size())).trim());
        }
        Pages.editAccountPage().clickReasonAutoOdChgWaivedSelectorOption(account.getReasonAutoOdChgWaived());
    }

    public void setAutomaticOverdraftStatus(Account account) {
        Pages.editAccountPage().clickAutomaticOverdraftStatusSelectorButton();
        List<String> listOfClickAutomaticOverdraftStatus = Pages.editAccountPage().getAutomaticOverdraftStatusList();

        Assert.assertTrue(listOfClickAutomaticOverdraftStatus.size() > 0, "There are no options available");
        if (account.getAutomaticOverdraftStatus() == null) {
            account.setAutomaticOverdraftStatus(listOfClickAutomaticOverdraftStatus.get(new Random().nextInt(listOfClickAutomaticOverdraftStatus.size())).trim());
        }
        Pages.editAccountPage().clickAutomaticOverdraftStatusSelectorOption(account.getAutomaticOverdraftStatus());
    }

    public void setReasonDebitCardChargeWaived(Account account) {
        Pages.editAccountPage().clickReasonDebitCardChargeWaivedOptionSelectorButton();
        List<String> listOfReasonReasonDebitCardChargeWaived = Pages.editAccountPage().getReasonDebitCardChargeWaivedList();

        Assert.assertTrue(listOfReasonReasonDebitCardChargeWaived.size() > 0, "There are no options available");
        if (account.getReasonDebitCardChargeWaived() == null) {
            account.setReasonDebitCardChargeWaived(listOfReasonReasonDebitCardChargeWaived.get(new Random().nextInt(listOfReasonReasonDebitCardChargeWaived.size())).trim());
        }
        Pages.editAccountPage().clickReasonDebitCardChargeWaivedSelectorOption(account.getReasonDebitCardChargeWaived());
    }

    public void setReasonAutoNSFChgWaived(Account account) {
        Pages.editAccountPage().clickReasonAutoNSFChgWaivedSelectorButton();
        List<String> listOfReasonAutoNSFChgWaived = Pages.editAccountPage().getReasonAutoNSFChgWaivedList();

        Assert.assertTrue(listOfReasonAutoNSFChgWaived.size() > 0, "There are no options available");
        if (account.getReasonAutoNSFChgWaived() == null) {
            account.setReasonAutoNSFChgWaived(listOfReasonAutoNSFChgWaived.get(new Random().nextInt(listOfReasonAutoNSFChgWaived.size())).trim());
        }
        Pages.editAccountPage().clickReasonAutoNSFChgWaivedSelectorOption(account.getReasonAutoNSFChgWaived());
    }

    public void setOdProtectionAcct(Account account) {
        Pages.editAccountPage().clickOdProtectionAcctSelectorButton();
        List<String> listOfOdProtectionAcct = Pages.editAccountPage().getOdProtectionAcctList();

        Assert.assertTrue(listOfOdProtectionAcct.size() > 0, "There are no product types available");
        if (account.getOdProtectionAcct() == null) {
            account.setOdProtectionAcct(listOfOdProtectionAcct.get(new Random().nextInt(listOfOdProtectionAcct.size())).trim());
        }
        Pages.editAccountPage().clickOdProtectionAcctSelectorOption(account.getOdProtectionAcct().replaceAll("[^0-9]", ""));
    }

    public void setFederalWHReason(Account account) {
        Pages.editAccountPage().clickFederalWHReasonSelectorButton();
        List<String> listOfFederalWHReason = Pages.editAccountPage().getFederalWHReasonList();

        Assert.assertTrue(listOfFederalWHReason.size() > 0, "There are no options available");
        if (account.getFederalWHReason() == null) {
            account.setFederalWHReason(listOfFederalWHReason.get(new Random().nextInt(listOfFederalWHReason.size())).trim());
        }
        Pages.editAccountPage().clickFederalWHReasonSelectorOption(account.getFederalWHReason());
    }

    public void setReasonATMChargeWaived(Account account) {
        Pages.editAccountPage().clickReasonATMChargeWaivedSelectorButton();
        List<String> listOfReasonATMChargeWaived = Pages.editAccountPage().getReasonATMChargeWaivedList();

        Assert.assertTrue(listOfReasonATMChargeWaived.size() > 0, "There are no options available");
        if (account.getReasonATMChargeWaived() == null) {
            account.setReasonATMChargeWaived(listOfReasonATMChargeWaived.get(new Random().nextInt(listOfReasonATMChargeWaived.size())).trim());
        }
        Pages.editAccountPage().clickReasonATMChargeWaivedSelectorOption(account.getReasonATMChargeWaived());
    }

    public void selectValuesInDropdownFieldsThatWereNotAvailableDuringCheckingAccountCreation(Account account) {
        setFederalWHReason(account);
        setReasonATMChargeWaived(account);
        setOdProtectionAcct(account);
        setReasonAutoNSFChgWaived(account);
        setReasonDebitCardChargeWaived(account);
        setAutomaticOverdraftStatus(account);
        setReasonAutoOdChgWaived(account);
        setWhenSurchargesRefunded(account);
    }

    public void fillInInputFieldsThatWereNotAvailableDuringCheckingAccountCreation(Account account) {
        Pages.editAccountPage().setFederalWHPercent(account.getFederalWHPercent());
        Pages.editAccountPage().setNumberOfATMCardsIssued(account.getNumberOfATMCardsIssued());
        Pages.editAccountPage().setEarningCreditRate(account.getEarningCreditRate());
        Pages.editAccountPage().setUserDefinedField_1(account.getUserDefinedField_1());
        Pages.editAccountPage().setUserDefinedField_2(account.getUserDefinedField_2());
        Pages.editAccountPage().setUserDefinedField_3(account.getUserDefinedField_3());
        Pages.editAccountPage().setUserDefinedField_4(account.getUserDefinedField_4());
        Pages.editAccountPage().setNumberOfDebitCardsIssued(account.getNumberOfDebitCardsIssued());
        Pages.editAccountPage().setAutomaticOverdraftLimit(account.getAutomaticOverdraftLimit());
        Pages.editAccountPage().setCashCollDaysBeforeChg(account.getCashCollDaysBeforeChg());
        Pages.editAccountPage().setCashCollInterestRate(account.getCashCollInterestRate());
        Pages.editAccountPage().setCashCollInterestChg(account.getCashCollInterestChg());
        Pages.editAccountPage().setCashCollFloat(account.getCashCollFloat());
        Pages.editAccountPage().setPositivePay(account.getPositivePay());
    }

    public void selectValuesInDropdownFieldsThatWereNotAvailableDuringSavingsAccountCreation(Account account) {
        setFederalWHReason(account);
        setReasonATMChargeWaived(account);
        setReasonDebitCardChargeWaived(account);
    }

    public void fillInInputFieldsThatWereNotAvailableDuringSavingsAccountCreation(Account account) {
        Pages.editAccountPage().setPrintStatementNextUpdate(account.getPrintStatementNextUpdate());
        Pages.editAccountPage().setInterestPaidYTD(account.getInterestPaidYTD());
        Pages.editAccountPage().setFederalWHPercent(account.getFederalWHPercent());
        Pages.editAccountPage().setNumberOfATMCardsIssued(account.getNumberOfATMCardsIssued());
        Pages.editAccountPage().setUserDefinedField_1(account.getUserDefinedField_1());
        Pages.editAccountPage().setUserDefinedField_2(account.getUserDefinedField_2());
        Pages.editAccountPage().setUserDefinedField_3(account.getUserDefinedField_3());
        Pages.editAccountPage().setUserDefinedField_4(account.getUserDefinedField_4());
        Pages.editAccountPage().setNumberOfDebitCardsIssued(account.getNumberOfDebitCardsIssued());
    }

    public void editSavingsAccount(Account account) {
        Pages.accountDetailsPage().clickEditButton();
        AccountActions.editAccount().selectValuesInDropdownFieldsThatWereNotAvailableDuringSavingsAccountCreation(account);
        AccountActions.editAccount().fillInInputFieldsThatWereNotAvailableDuringSavingsAccountCreation(account);
        AccountActions.createAccount().setCurrentOfficer(account);
        Pages.editAccountPage().setInterestRate(account.getInterestRate());
        AccountActions.createAccount().setBankBranch(account);
        AccountActions.createAccount().setCallClassCode(account);
        AccountActions.createAccount().setStatementCycle(account);
        Pages.editAccountPage().clickNewAccountSwitch();
        Pages.editAccountPage().clickTransactionalAccountSwitch();
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();
    }

}
