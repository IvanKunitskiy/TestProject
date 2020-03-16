package com.nymbus.pages;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class Settings extends PageTools {

    private By settingsPageHeader = By.xpath("//h1[text()='Settings']");

    @Step("Wait for Report Generator page loaded")
    public void waitForSettingsPageLoaded() {
        waitForElementVisibility(settingsPageHeader);
    }

}
