package com.nymbus.pages.accounts;

import com.nymbus.core.base.PageTools;
import org.openqa.selenium.By;

public class AccountMaintenancePage extends PageTools {

    private By viewAllMaintenanceHistoryLink = By.xpath("//button//span[contains(text(), 'View All History')]");
    private By viewMoreButton = By.xpath("//button[@data-test-id='action-loadMore']");
    private By changeTypeFields = By.xpath("//table//tr//td/span[text()='%s']");


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

    public int getChangeTypeElementsCount(String text) {
        waitForElementVisibility(changeTypeFields, text);
        return getElements(changeTypeFields, text).size();
    }
}
