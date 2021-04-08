package com.nymbus.pages.modalwindow;

import com.nymbus.core.base.PageTools;
import org.openqa.selenium.By;

public class AlertMessageModalPage extends PageTools {

    private By okButton = By.xpath("//button[span[text()='OK']]");
    private By alertMessageText = By.xpath("//p[@ng-if='message']");

    public void clickOkButton() {
        waitForElementVisibility(okButton);
        click(okButton);
    }

    public String getAlertMessageModalText() {
        return getElementText(alertMessageText).trim();
    }
}
