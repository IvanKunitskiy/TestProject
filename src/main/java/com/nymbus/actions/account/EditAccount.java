package com.nymbus.actions.account;

import com.nymbus.models.account.Account;
import com.nymbus.pages.Pages;
import org.testng.Assert;

import java.util.List;
import java.util.Random;

public class EditAccount {

    public void setWhenSurchargesRefunded(Account account) {
        Pages.accountDetailsPage().clickWhenSurchargesRefundedSelectorButton();
        List<String> listOfWhenSurchargesRefunded = Pages.accountDetailsPage().getWhenSurchargesRefundedList();

        Assert.assertTrue(listOfWhenSurchargesRefunded.size() > 0, "There are no options available.");
        if (account.getWhenSurchargesRefunded() == null) {
            account.setWhenSurchargesRefunded(listOfWhenSurchargesRefunded.get(new Random().nextInt(listOfWhenSurchargesRefunded.size())).trim());
        }
        Pages.accountDetailsPage().clickWhenSurchargesRefundedSelectorOption(account.getWhenSurchargesRefunded());
    }

    public void setReasonAutoOdChgWaived(Account account) {
        Pages.accountDetailsPage().clickReasonAutoOdChgWaivedSelectorButton();
        List<String> listOfReasonAutoOdChgWaived = Pages.accountDetailsPage().getReasonAutoOdChgWaivedList();

        Assert.assertTrue(listOfReasonAutoOdChgWaived.size() > 0, "There are no options available");
        if (account.getReasonAutoOdChgWaived() == null) {
            account.setReasonAutoOdChgWaived(listOfReasonAutoOdChgWaived.get(new Random().nextInt(listOfReasonAutoOdChgWaived.size())).trim());
        }
        Pages.accountDetailsPage().clickReasonAutoOdChgWaivedSelectorOption(account.getReasonAutoOdChgWaived());
    }

    public void setAutomaticOverdraftStatus(Account account) {
        Pages.accountDetailsPage().clickAutomaticOverdraftStatusSelectorButton();
        List<String> listOfClickAutomaticOverdraftStatus = Pages.accountDetailsPage().getAutomaticOverdraftStatusList();

        Assert.assertTrue(listOfClickAutomaticOverdraftStatus.size() > 0, "There are no options available");
        if (account.getAutomaticOverdraftStatus() == null) {
            account.setAutomaticOverdraftStatus(listOfClickAutomaticOverdraftStatus.get(new Random().nextInt(listOfClickAutomaticOverdraftStatus.size())).trim());
        }
        Pages.accountDetailsPage().clickAutomaticOverdraftStatusSelectorOption(account.getAutomaticOverdraftStatus());
    }

    public void setReasonDebitCardChargeWaived(Account account) {
        Pages.accountDetailsPage().clickReasonDebitCardChargeWaivedOptionSelectorButton();
        List<String> listOfReasonReasonDebitCardChargeWaived = Pages.accountDetailsPage().getReasonDebitCardChargeWaivedList();

        Assert.assertTrue(listOfReasonReasonDebitCardChargeWaived.size() > 0, "There are no options available");
        if (account.getReasonDebitCardChargeWaived() == null) {
            account.setReasonDebitCardChargeWaived(listOfReasonReasonDebitCardChargeWaived.get(new Random().nextInt(listOfReasonReasonDebitCardChargeWaived.size())).trim());
        }
        Pages.accountDetailsPage().clickReasonDebitCardChargeWaivedSelectorOption(account.getReasonDebitCardChargeWaived());
    }

    public void setReasonAutoNSFChgWaived(Account account) {
        Pages.accountDetailsPage().clickReasonAutoNSFChgWaivedSelectorButton();
        List<String> listOfReasonAutoNSFChgWaived = Pages.accountDetailsPage().getReasonAutoNSFChgWaivedList();

        Assert.assertTrue(listOfReasonAutoNSFChgWaived.size() > 0, "There are no options available");
        if (account.getReasonAutoNSFChgWaived() == null) {
            account.setReasonAutoNSFChgWaived(listOfReasonAutoNSFChgWaived.get(new Random().nextInt(listOfReasonAutoNSFChgWaived.size())).trim());
        }
        Pages.accountDetailsPage().clickReasonAutoNSFChgWaivedSelectorOption(account.getReasonAutoNSFChgWaived());
    }

    public void setOdProtectionAcct(Account account) {
        Pages.accountDetailsPage().clickOdProtectionAcctSelectorButton();
        List<String> listOfOdProtectionAcct = Pages.accountDetailsPage().getOdProtectionAcctList();

        Assert.assertTrue(listOfOdProtectionAcct.size() > 0, "There are no product types available");
        if (account.getOdProtectionAcct() == null) {
            account.setOdProtectionAcct(listOfOdProtectionAcct.get(new Random().nextInt(listOfOdProtectionAcct.size())).trim());
        }
        Pages.accountDetailsPage().clickOdProtectionAcctSelectorOption(account.getOdProtectionAcct());
    }

    public void setFederalWHReason(Account account) {
        Pages.accountDetailsPage().clickFederalWHReasonSelectorButton();
        List<String> listOfFederalWHReason = Pages.accountDetailsPage().getFederalWHReasonList();

        Assert.assertTrue(listOfFederalWHReason.size() > 0, "There are no options available");
        if (account.getFederalWHReason() == null) {
            account.setFederalWHReason(listOfFederalWHReason.get(new Random().nextInt(listOfFederalWHReason.size())).trim());
        }
        Pages.accountDetailsPage().clickFederalWHReasonSelectorOption(account.getFederalWHReason());
    }

    public void setReasonATMChargeWaived(Account account) {
        Pages.accountDetailsPage().clickReasonATMChargeWaivedSelectorButton();
        List<String> listOfReasonATMChargeWaived = Pages.accountDetailsPage().getReasonATMChargeWaivedList();

        Assert.assertTrue(listOfReasonATMChargeWaived.size() > 0, "There are no options available");
        if (account.getReasonATMChargeWaived() == null) {
            account.setReasonATMChargeWaived(listOfReasonATMChargeWaived.get(new Random().nextInt(listOfReasonATMChargeWaived.size())).trim());
        }
        Pages.accountDetailsPage().clickReasonATMChargeWaivedSelectorOption(account.getReasonATMChargeWaived());
    }

}
