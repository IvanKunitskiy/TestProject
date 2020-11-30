package com.nymbus.pages;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class ConfirmModalPage extends PageTools {

    private final By yesButton = By.xpath("//button[span[text()='Yes']]");
    private final By okButton = By.xpath("//button[contains(string(),'OK')]");


    @Step("Click on 'Yes' button")
    public void clickYes() {
        waitForElementClickable(yesButton);
        jsClick(yesButton);
    }

    @Step("Click on 'OK' button")
    public void clickOk() {
        waitForElementClickable(okButton);
        jsClick(okButton);
    }
}
