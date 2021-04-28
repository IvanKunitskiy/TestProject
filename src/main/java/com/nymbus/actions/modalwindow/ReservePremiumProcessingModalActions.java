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

    public void setDeferredYesNoSwitchValueToNo() {
        if (Pages.reservePremiumProcessingModalPage().getDeferredYesNoSwitchValue().equalsIgnoreCase("yes")) {
            Pages.reservePremiumProcessingModalPage().clickDeferredYesNoSwitch();
        }
    }

    public void setIrsReportablePointsPaidSwitchValueToNo() {
        if (Pages.reservePremiumProcessingModalPage().getIrsReportablePointsPaidSwitchValue().equalsIgnoreCase("yes")) {
            Pages.reservePremiumProcessingModalPage().clickIrsReportablePointsPaidSwitch();
        }
    }

    public void setRandomReservePremiumCode(String query) {
        Pages.reservePremiumProcessingModalPage().clickReservePremiumCode(query);

        List<String> theListOfReservePremiumCode = Pages.reservePremiumProcessingModalPage().getListOfReservePremiumCode();
        Assert.assertTrue(theListOfReservePremiumCode.size() > 0, "There are no 'Reserve premium code options available");
        Pages.reservePremiumProcessingModalPage().clickReservePremiumCodeOptionByName(query);
    }

    public void setGlOffset(String query) {
        Pages.reservePremiumProcessingModalPage().setGlOffset(query);

        List<String> listOfGlOffset = Pages.reservePremiumProcessingModalPage().getListOfGlOffset();
        Assert.assertTrue(listOfGlOffset.size() > 0, "There are no 'Gl Offset' options available");
        String option = listOfGlOffset.get(new Random().nextInt(listOfGlOffset.size())).trim();
        Pages.reservePremiumProcessingModalPage().clickGlOffsetByTextOptionByName(option);
    }
}
