package com.nymbus.pages.accounts;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class AccountMaintenancePage extends PageTools {

    private By viewAllMaintenanceHistoryLink = By.xpath("//button//span[contains(text(), 'View All History')]");
    private By viewMoreButton = By.xpath("//button[@data-test-id='action-loadMore']");
    private By changeTypeFields = By.xpath("//table//tr//td/span[text()='%s']");
    private By rowsInTable = By.xpath("//table//tr");
    private By rowOldValueByRowName = By.xpath("(//table//tr//td/span[text()='%s']//ancestor::node()[3]/td[4]/span)[%s]");
    private By rowNewValueByRowName = By.xpath("(//table//tr//td/span[text()='%s']//ancestor::node()[3]/td[5]/span)[%s]");

    @Step("Get row {0} old value by {1}")
    public String getRowOldValueByRowName(String text, int index) {
        waitForElementVisibility(rowOldValueByRowName, text, index);
        return getElementText(rowOldValueByRowName, text, index).trim();
    }

    @Step("Get row {0} new value by {1}")
    public String getRowNewValueByRowName(String text, int index) {
        waitForElementVisibility(rowNewValueByRowName, text, index);
        return getElementText(rowNewValueByRowName, text, index).trim();
    }

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