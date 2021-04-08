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

    public void createAutotestParticipant(){
        Pages.aSideMenuPage().clickLoansMenuItem();
        Pages.loansPage().waitForLoanPageLoaded();
        Pages.loansPage().clickViewAllParticipantLink();
        if (!Pages.loanParticipantsPage().isTestCodeExists()) {
            Pages.loanParticipantsPage().clickAddNew();
            String text = "autotest";
            Pages.loanParticipantsPage().inputParticipantCode(text);
            Pages.loanParticipantsPage().inputParticipantCity(text);
            Pages.loanParticipantsPage().inputParticipantAddressLine1(text);
            Pages.loanParticipantsPage().inputParticipantEIN(text);
            Pages.loanParticipantsPage().inputParticipantEmail(text + "@nymbus.com");
            Pages.loanParticipantsPage().inputParticipantName(text);
            Pages.loanParticipantsPage().inputParticipantPhone("0920909099");
            Pages.loanParticipantsPage().inputParticipantRoutNumber(text);
            Pages.loanParticipantsPage().inputParticipantZip("18000");
            Pages.loanParticipantsPage().inputParticipantState("Alabama");
            Pages.loanParticipantsPage().inputParticipantGLAccount("Dummy");
            Pages.loanParticipantsPage().clickSaveButton();
        }
    }
}
