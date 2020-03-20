package com.nymbus.pages.clients.maintenance;

import com.nymbus.base.BasePage;
import com.nymbus.locator.Locator;
import com.nymbus.locator.XPath;
import com.nymbus.newmodels.other.MaintenanceHistoryField;

public class MaintenanceHistoryPage extends BasePage {
    private Locator oldValueByFieldName = new XPath("//span[text()='%s']/parent::td/parent::tr/td[5]/span");
    private Locator viewMoreButton = new XPath("//button[text()='View More']");

    public String getNewValueByFieldName(MaintenanceHistoryField field) {
        return getElementText(oldValueByFieldName, field.getFieldName());
    }

    public void clickOnViewMoreButton() {
        waitForElementVisibility(viewMoreButton);
        waitForElementClickable(viewMoreButton);
        click(viewMoreButton);
    }
}
