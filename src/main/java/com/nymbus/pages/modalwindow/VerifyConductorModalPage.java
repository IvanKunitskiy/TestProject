package com.nymbus.pages.modalwindow;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class VerifyConductorModalPage extends PageTools {
    private By verifyButton = By.xpath("//button[text()='Verify']");

    @Step("Click 'Verify' button")
    public void clickVerifyButton() {
        waitForElementVisibility(verifyButton);
        click(verifyButton);
    }
}