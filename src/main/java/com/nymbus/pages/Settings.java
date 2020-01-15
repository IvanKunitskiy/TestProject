package com.nymbus.pages;

import com.nymbus.base.BasePage;
import com.nymbus.locator.Locator;
import com.nymbus.locator.XPath;
import io.qameta.allure.Step;

public class Settings extends BasePage {

    Locator settingsPageHeader = new XPath("//h1[text()='Settings']");

    @Step("Wait for Report Generator page loaded")
    public void waitForSettingsPageLoaded(){
        waitForElementVisibility(settingsPageHeader);
    }

}
