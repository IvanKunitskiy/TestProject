package com.nymbus.pages;

import com.nymbus.core.base.PageTools;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Selectors.shadowCss;

public class ChromeSettingsPage extends PageTools {
    private final By notificationField = shadowCss("#permission", "settings-ui",
            "settings-main",
            "settings-basic-page",
            "settings-privacy-page",
            "site-details",
            "site-details-permission[label=\"Notifications\"]");

    private final By allowOption = shadowCss("#permission > option#allow", "settings-ui",
            "settings-main",
            "settings-basic-page",
            "settings-privacy-page",
            "site-details",
            "site-details-permission[label=\"Notifications\"]");

    public void clickNotificationField() {
        clickElementInShadowRoot(notificationField);
    }

    public void pickAllowOption() {
        clickElementInShadowRoot(allowOption);
    }
}
