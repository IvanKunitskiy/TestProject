package com.nymbus.pages.accounts;

import com.codeborne.selenide.Selenide;
import com.nymbus.core.base.PageTools;
import org.openqa.selenium.By;

public class AccountStatementPage extends PageTools {

    private By frameElement = By.id("pdf-item");

    public void switchToFrame() {
        waitForElementVisibility(frameElement);
        Selenide.switchTo().frame(getWebElement(frameElement));
    }

    public void switchToDefaultContent() {
        Selenide.switchTo().defaultContent();
    }
}
