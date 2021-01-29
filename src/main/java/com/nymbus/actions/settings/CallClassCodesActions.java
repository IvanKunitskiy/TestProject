package com.nymbus.actions.settings;

import com.nymbus.pages.Pages;
import com.nymbus.pages.settings.SettingsPage;

public class CallClassCodesActions {

    public void removeProductTypeFromCallCode(String callClassCode) {
        Pages.aSideMenuPage().clickSettingsMenuItem();
        SettingsPage.mainPage().clickViewAllCallClassCodesLink();
        SettingsPage.callClassCodesPage().typeToSearchField(callClassCode);
        SettingsPage.callClassCodesPage().clickSearchButton();
        SettingsPage.callClassCodesPage().clickRowByCallCode(callClassCode);
        SettingsPage.callCodePage().clickEditButton();
        SettingsPage.editCallCodePage().clickProductTypeSelectorButton();
        SettingsPage.editCallCodePage().clickProductTypeSelectorOptionByIndex(1);
        SettingsPage.editCallCodePage().clickSaveChangesButton();
        SettingsPage.callCodePage().waitForAddNewButtonVisible();
    }

    public void setCallCodeProductType(String callClassCode, String productType) {
        Pages.aSideMenuPage().clickSettingsMenuItem();
        SettingsPage.mainPage().clickViewAllCallClassCodesLink();
        SettingsPage.callClassCodesPage().typeToSearchField(callClassCode);
        SettingsPage.callClassCodesPage().clickSearchButton();
        SettingsPage.callClassCodesPage().clickRowByCallCode(callClassCode);
        SettingsPage.callCodePage().clickEditButton();
        SettingsPage.editCallCodePage().clickProductTypeSelectorButton();
        SettingsPage.editCallCodePage().typeToProductTypeField(productType);
        SettingsPage.editCallCodePage().clickProductTypeSelectorOptionByIndex(1);
        SettingsPage.editCallCodePage().clickSaveChangesButton();
        SettingsPage.callCodePage().waitForAddNewButtonVisible();
    }
}
