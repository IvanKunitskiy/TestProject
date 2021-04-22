package com.nymbus.actions.modalwindow;

import com.nymbus.pages.Pages;
import org.testng.Assert;

import java.util.List;
import java.util.Random;

public class ReservePremiumProcessingModalActions {

    public void setDeferredYesNoSwitchValueToYes() {
        if (Pages.reservePremiumProcessingModalPage().getDeferredYesNoSwitchValue().equalsIgnoreCase("no")) {
            Pages.reservePremiumProcessingModalPage().clickDeferredYesNoSwitch();
        }
    }

    public void setIrsReportablePointsPaidSwitchValueToNo() {
        if (Pages.reservePremiumProcessingModalPage().getIrsReportablePointsPaidSwitchValue().equalsIgnoreCase("yes")) {
            Pages.reservePremiumProcessingModalPage().clickIrsReportablePointsPaidSwitch();
        }
    }

    public void setRandomReservePremiumCode() {
        Pages.reservePremiumProcessingModalPage().clickReservePremiumCode();
        List<String> theListOfReservePremiumCode = Pages.reservePremiumProcessingModalPage().getTheListOfReservePremiumCode();
        Assert.assertTrue(theListOfReservePremiumCode.size() > 0, "There are no 'Reserve premium code options available");
        String option = theListOfReservePremiumCode.get(new Random().nextInt(theListOfReservePremiumCode.size())).trim();
        Pages.reservePremiumProcessingModalPage().clickReservePremiumCodeOptionByName(option);
    }

}
