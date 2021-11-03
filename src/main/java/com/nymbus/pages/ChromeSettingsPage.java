package com.nymbus.pages;

import com.codeborne.selenide.Selenide;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.base.PageTools;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class ChromeSettingsPage extends PageTools {
    public void clickNotificationField() {
        clickInShadow();
//        Selenide.executeJavaScript("document.querySelector('settings-ui').shadowRoot.querySelector('settings-main')" +
//                ".shadowRoot.querySelector('settings-basic-page').shadowRoot.querySelector('settings-privacy-page')" +
//                ".shadowRoot.querySelector('settings-animated-pages > settings-subpage > site-details')" +
//                ".shadowRoot.querySelector('site-details-permission[label=\"Notifications\"]')" +
//                ".shadowRoot.querySelector('#permission');");
    }

    public void pickSpecificOption(String option) {
        clickInShadow2();
//        Selenide.executeJavaScript("document.querySelector('settings-ui').shadowRoot.querySelector('settings-main')" +
//                ".shadowRoot.querySelector('settings-basic-page').shadowRoot.querySelector('settings-privacy-page')" +
//                ".shadowRoot.querySelector('settings-animated-pages > settings-subpage > site-details')" +
//                ".shadowRoot.querySelector('site-details-permission[label=\"Notifications\"]')" +
//                ".shadowRoot.querySelector('#permission > option#" + option + "').click();");
    }
}
