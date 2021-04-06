package com.nymbus.actions.settings;

import com.nymbus.pages.Pages;
import com.nymbus.pages.settings.SettingsPage;

public class LoansActions {

    public void clickCommercialParticipationLiteYes(){
        Pages.aSideMenuPage().clickSettingsMenuItem();
        SettingsPage.mainPage().clickViewSettingsLink();
        SettingsPage.bankControlPage().goToLoansButton();
        SettingsPage.bankControlPage().clickEditButton();
        if (SettingsPage.bankControlPage().getCommercialParticipationLite().equals("0")) {
            SettingsPage.bankControlPage().clickCommercialParticipationLite();
        }
        SettingsPage.bankControlPage().clickSaveButton();
    }
}
