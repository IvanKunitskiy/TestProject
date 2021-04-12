package com.nymbus.actions.modalwindow;

import com.nymbus.pages.Pages;
import org.testng.Assert;

public class ParticipationsActions {

    public void setParticipant(String name) {
        Pages.participationsModalPage().clickParticipantSelectorButton();
        Assert.assertTrue(Pages.participationsModalPage().getParticipantList().size() > 0, "There are no 'Loan Class Code' options available");
        Pages.participationsModalPage().clickParticipantSelectorOption(name);
    }
}
