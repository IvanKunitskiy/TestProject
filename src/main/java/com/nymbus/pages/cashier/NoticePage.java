package com.nymbus.pages.cashier;

import com.codeborne.selenide.Selenide;
import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class NoticePage extends PageTools {

    private By noticeReceipt = By.xpath("//pdf-viewer");
    private By embedPDF = By.xpath("//embed");


    @Step("Check pdf visible")
    public void checkPDFVisible() {
        waitForElementVisibility(noticeReceipt);
    }

    @Step("Get 'Notice' image")
    public String getNoticeImageSrc() {
        WebElement shadowRoot = Selenide.executeJavaScript("arguments[0].shadowRoot", noticeReceipt);
        String link = shadowRoot.findElement(embedPDF).getAttribute("src");
        return link;
    }

}
