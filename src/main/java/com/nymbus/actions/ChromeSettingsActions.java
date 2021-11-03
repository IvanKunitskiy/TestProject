package com.nymbus.actions;

import com.nymbus.core.utils.SelenideTools;
import com.nymbus.pages.Pages;

public class ChromeSettingsActions {

    public void enablePopups(String url) {
        SelenideTools.openUrl("chrome://settings/content/siteDetails?site=https%3A%2F%2F" + url + ".development.nymbus.com%2F");
        SelenideTools.sleep(2);
        Pages.chromeSettingsPage().clickNotificationField();
        SelenideTools.sleep(1);
        Pages.chromeSettingsPage().pickAllowOption();
        SelenideTools.sleep(2);
        SelenideTools.openUrl("https://" + url + ".development.nymbus.com");
    }
}
