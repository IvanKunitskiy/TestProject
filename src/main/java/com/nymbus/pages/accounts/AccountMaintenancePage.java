package com.nymbus.pages.accounts;

import com.nymbus.base.BasePage;
import com.nymbus.locator.Locator;
import com.nymbus.locator.XPath;

public class AccountMaintenancePage extends BasePage {

    private Locator viewAllMaintenanceHistoryLink = new XPath("//button//span[contains(text(), 'View All History')]");
    private Locator viewMoreButton = new XPath("//button[@data-test-id='action-loadMore']");

    public void clickViewAllMaintenanceHistoryLink() {
        waitForElementVisibility(viewAllMaintenanceHistoryLink);
        waitForElementClickable(viewAllMaintenanceHistoryLink);
        click(viewAllMaintenanceHistoryLink);
    }

    public void clickViewMoreButton() {
        waitForElementVisibility(viewMoreButton);
        waitForElementClickable(viewMoreButton);
        click(viewMoreButton);
    }
}
