package com.nymbus.pages.clients.maintenance;

import com.nymbus.core.base.PageTools;
import com.nymbus.newmodels.other.MaintenanceHistoryField;
import org.openqa.selenium.By;

public class MaintenanceHistoryPage extends PageTools {
    private By oldValueByFieldName = By.xpath("//span[text()='%s']/parent::td/parent::tr/td[5]/span");
    private By viewMoreButton = By.xpath("//button[text()='View More']");

    public String getNewValueByFieldName(MaintenanceHistoryField field) {
        return getElementText(oldValueByFieldName, field.getFieldName());
    }

    public void clickOnViewMoreButton() {
        waitForElementVisibility(viewMoreButton);
        waitForElementClickable(viewMoreButton);
        click(viewMoreButton);
    }
}
