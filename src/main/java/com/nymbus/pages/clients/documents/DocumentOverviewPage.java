package com.nymbus.pages.clients.documents;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class DocumentOverviewPage extends PageTools {
    private By documentImage = By.xpath("//*[@id='main-content']//img");
    private By closeButton = By.xpath("//button[@data-test-id='action-close']");

    @Step("Click the 'Close' button")
    public void clickCloseButton() {
        waitForElementVisibility(closeButton);
        waitForElementClickable(closeButton);
        click(closeButton);
    }

    @Step("Get image src")
    public String getImageSrc() {
        waitForElementVisibility(documentImage);
        waitForElementClickable(documentImage);
        return getWebElement(documentImage).getAttribute("src");
    }
}