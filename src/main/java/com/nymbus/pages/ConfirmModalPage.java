package com.nymbus.pages;

import com.nymbus.core.base.PageTools;
import org.openqa.selenium.By;

public class ConfirmModalPage extends PageTools {

    private final By yesButton = By.xpath("//button[span[text()='Yes']]");


    public void clickYes() {
        waitForElementClickable(yesButton);
        click(yesButton);
    }
}
