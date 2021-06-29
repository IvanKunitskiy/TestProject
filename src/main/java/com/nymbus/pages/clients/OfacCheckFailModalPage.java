package com.nymbus.pages.clients;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class OfacCheckFailModalPage extends PageTools {
    private final By confirmButton = By.xpath("//button[contains(string(),'Confirm Match')]");
    private final By printButton = By.xpath("//button[contains(string(),'Print')]//span");

    @Step("Click Confirm button")
    public void clickConfirm(){
        waitForElementVisibility(confirmButton);
        waitForElementClickable(confirmButton);
        click(confirmButton);
    }

    @Step("Click Print button")
    public void clickPrint(){
        waitForElementVisibility(printButton);
        waitForElementClickable(printButton);
        click(printButton);
    }

}
