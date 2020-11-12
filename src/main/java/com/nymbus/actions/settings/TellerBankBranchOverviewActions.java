package com.nymbus.actions.settings;

import com.nymbus.newmodels.settings.teller.TellerLocation;
import com.nymbus.pages.settings.SettingsPage;

public class TellerBankBranchOverviewActions {

    public TellerLocation getTellerLocation(String bankBranch) {
        TellerLocation tellerLocation = new TellerLocation();

        SettingsPage.mainPage().clickViewAllTellerLocationsLink();
        SettingsPage.tellerLocationOverviewPage().clickRowByBranchName(bankBranch);

        tellerLocation.setBranchName(SettingsPage.branchOverviewPage().getBranchName());
        tellerLocation.setBankName(SettingsPage.branchOverviewPage().getBankName());
        tellerLocation.setAddressLine1(SettingsPage.branchOverviewPage().getAddressLine1());
        tellerLocation.setCity(SettingsPage.branchOverviewPage().getCity());
        tellerLocation.setState(SettingsPage.branchOverviewPage().getState());
        tellerLocation.setZipCode(SettingsPage.branchOverviewPage().getZipCode());
        tellerLocation.setPhoneNumber(SettingsPage.branchOverviewPage().getPhoneNumber());

        return tellerLocation;
    }
}
