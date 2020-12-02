package com.nymbus.pages.cashier;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class NoticePage extends PageTools {

    private By noticeReceipt = By.xpath("//iframe");
    private By embedPDF = By.xpath("//embed");
    private By nextButton = By.xpath("//button[contains(string(),'Next')]");

    @Step("Check pdf visible")
    public void checkPDFVisible() {
        waitForElementPresent(noticeReceipt);
    }

    @Step("Get 'Notice' image")
    public String getNoticeImageSrc() {
        return getElementAttributeValue("src",noticeReceipt);
    }

    @Step("Click 'Next' button")
    public void clickNextButton(){
        waitForElementClickable(nextButton);
        jsClick(nextButton);
    }

}
