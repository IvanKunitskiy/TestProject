package com.nymbus.pages.accounts;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class AccountMaintenancePage extends PageTools {

    private By viewAllMaintenanceHistoryLink = By.xpath("//button//span[contains(text(), 'View All History')]");
    private By viewMoreButton = By.xpath("//button[@data-test-id='action-loadMore']");
    private By changeTypeFields = By.xpath("//table//tr//td/span[text()='%s']");
    private By rowsInTable = By.xpath("//table//tr");

    @Step("Click 'ViewAllMaintenanceHistory' button")
    public void clickViewAllMaintenanceHistoryLink() {
        waitForElementVisibility(viewAllMaintenanceHistoryLink);
        waitForElementClickable(viewAllMaintenanceHistoryLink);
        click(viewAllMaintenanceHistoryLink);
    }

    @Step("Click 'ViewMore' button")
    public void clickViewMoreButton() {
        waitForElementVisibility(viewMoreButton);
        waitForElementClickable(viewMoreButton);
        click(viewMoreButton);
    }

    @Step("Get count of rows in table by change type")
    public int getChangeTypeElementsCount(String text) {
        waitForElementVisibility(changeTypeFields, text);
        return getElements(changeTypeFields, text).size();
    }

    @Step("Wait for more button invisibility")
    public void waitForMoreButtonInvisibility() {
        waitForElementInvisibility(viewMoreButton);
    }

    @Step("Get count of rows in table")
    public int getRowsCount() {
        return getElements(rowsInTable).size();
    }
}