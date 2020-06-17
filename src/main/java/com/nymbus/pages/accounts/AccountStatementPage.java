package com.nymbus.pages.accounts;

import com.codeborne.selenide.Selenide;
import com.nymbus.core.base.PageTools;
import org.openqa.selenium.By;

import java.io.File;

public class AccountStatementPage extends PageTools {

    private By frameElement = By.id("pdf-item");
    private By downloadButtonLink = By.xpath("//a[@download='document.pdf']");
    private By pdfLoadSpinner = By.xpath("//div[@class='spinnerWrapper']");

    public File downloadCallStatementPdf() {
        return downloadFile(downloadButtonLink);
    }

    public void waitForLoadingSpinnerInvisibility() {
        waitForElementInvisibility(pdfLoadSpinner);
    }

    public void switchToFrame() {
        waitForElementVisibility(frameElement);
        Selenide.switchTo().frame(getWebElement(frameElement));
    }

    public void switchToDefaultContent() {
        Selenide.switchTo().defaultContent();
    }
}
