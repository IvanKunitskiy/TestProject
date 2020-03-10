package com.nymbus.pages.accounts;

import com.nymbus.base.BasePage;
import com.nymbus.locator.Locator;
import com.nymbus.locator.XPath;
import io.qameta.allure.Step;

public class AccountNavigationPage extends BasePage {

    /**
     * Tabs buttons
     */

    private Locator maintenanceTab = new XPath("//a[contains(text(), 'Maintenance')]");


    @Step("Click the 'Maintenance' tab")
    public void clickMaintenanceTab() {
        waitForElementVisibility(maintenanceTab);
        waitForElementClickable(maintenanceTab);
        click(maintenanceTab);
    }

}
