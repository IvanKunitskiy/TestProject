package com.nymbus.pages;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class AttentionModalPage extends PageTools {
    private By modalWindow = By.xpath("//div[@class='modal-content']");
    private By yesButton = By.xpath("//button/span[contains(text(), 'Yes')]");

    @Step("Click 'Yes' button")
    public void clickYesButton() {
        waitForElementVisibility(yesButton);
        waitForElementClickable(yesButton);
        click(yesButton);
    }


}
