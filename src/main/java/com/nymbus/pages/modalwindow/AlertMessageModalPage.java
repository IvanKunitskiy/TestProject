package com.nymbus.pages.modalwindow;

import com.nymbus.core.base.PageTools;
import org.openqa.selenium.By;

public class AlertMessageModalPage extends PageTools {

    private By okButton = By.xpath("//button[span[text()='OK']]");

    public void clickOkButton() {
        waitForElementVisibility(okButton);
        click(okButton);
    }
}
