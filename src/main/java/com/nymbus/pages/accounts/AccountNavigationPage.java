package com.nymbus.pages.accounts;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class AccountNavigationPage extends PageTools {

    /**
     * Tabs buttons
     */

    private By maintenanceTab = By.xpath("//a[contains(text(), 'Maintenance')]");


    @Step("Click the 'Maintenance' tab")
    public void clickMaintenanceTab() {
        waitForElementVisibility(maintenanceTab);
        waitForElementClickable(maintenanceTab);
        click(maintenanceTab);
    }

}
