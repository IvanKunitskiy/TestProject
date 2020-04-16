package com.nymbus.pages.teller;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class VerifyConductorPage extends PageTools {
    private By modalWindow = By.xpath("//div[@class='modal-content']");
    private By verifyButton = By.xpath("//button[text()='Verify']");

    @Step("Wait 'Verify Conductor' modal window")
    public void waitModalWindow() {
        waitForElementVisibility(modalWindow);
        waitForElementClickable(modalWindow);
    }

    @Step("Click on 'Verify' button")
    public void clickVerifyButton() {
        waitForElementClickable(verifyButton);
        click(verifyButton);
    }
}
