package com.nymbus.pages;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class Alerts extends PageTools {

    private By alertsSidePanel = By.xpath("//aside[contains(@class, 'notifications')]");
    private By closeButton = By.xpath("//section[contains(@class, 'header')]//button");
    private By notificationCircle = By.xpath("//span[contains(@class, 'notificationCircle')]");
    private By alertSelector = By.xpath("//section[@class='content']/article/div//span[contains(text(), '%s')]");
    private By noteSelector = By.xpath("//article[contains(@class, 'item')][div/h4[contains(text(), '%s')]]" +
            "[div/p/span/ span[contains(text(), '%s')]]");

    @Step
    public boolean isNoteAlertVisible(String title, String text) {
        return isElementVisible(noteSelector, title, text);
    }

    @Step("Wait for alerts side panel")
    public void waitForAlertsSidePanelVisible() {
        waitForElementVisibility(alertsSidePanel);
    }

    @Step("Is notification circle visible")
    public boolean isNotificationCircleVisible() {
        return isElementVisible(notificationCircle);
    }

    @Step("Click on close button")
    public void clickCloseButton() {
        waitForElementVisibility(closeButton);
        waitForElementClickable(closeButton);
        click(closeButton);
    }

    @Step("Click the alert by note text")
    public void clickAlertByNoteText(String name) {
        waitForElementVisibility(alertSelector, name);
        waitForElementClickable(alertSelector, name);
        click(alertSelector, name);
    }

    @Step("Is note visible in the notes list")
    public boolean isNoteVisibleInTheListByText(String name) {
//        waitForElementVisibility(alertSelector, name);
        return isElementVisible(alertSelector, name);
    }
}
