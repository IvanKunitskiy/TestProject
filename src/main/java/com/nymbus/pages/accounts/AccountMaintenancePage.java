package com.nymbus.pages.accounts;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.util.List;

public class AccountMaintenancePage extends PageTools {

    private final By viewAllMaintenanceHistoryLink = By.xpath("//button//span[contains(text(), 'View All History')]");
    private final By viewMoreButton = By.xpath("//button[@data-test-id='action-loadMore']");
    private final By changeTypeFields = By.xpath("//table//tr//td/span[text()='%s']");
    private final By rowsInTable = By.xpath("//table//tr");
    private final By rowOldValueByRowName = By.xpath("(//table//tr//td/span[text()='%s']//ancestor::node()[3]/td[4]/span)[%s]");
    private final By rowNewValueByRowName = By.xpath("(//table//tr//td/span[text()='%s']//ancestor::node()[3]/td[5]/span)[%s]");
    private final By rowsCountByName = By.xpath("//table//tr//td/span[text()='%s']");

    @Step("Is more button visible")
    public boolean isMoreButtonVisible() {
        return isElementVisible(viewMoreButton);
    }

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

    @Step("Get count of rows in table by field name")
    public int getRowsCountByFieldName(String fieldName) {
        waitForElementVisibility(rowsCountByName, fieldName);
        return getElementsWithZeroOption(rowsCountByName, fieldName).size();
    }

    @Step("Wait for more button invisibility")
    public void waitForMoreButtonInvisibility() {
        waitForElementInvisibility(viewMoreButton);
    }

    @Step("Get count of rows in table")
    public int getRowsCount() {
        return getElements(rowsInTable).size();
    }

    /**
     * Tools
     */

    private final By chooseToolSelectorButton = By.xpath("//div[@data-test-id='field-toolSelect']");
    private final By chooseToolList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private final By chooseToolSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[text()='%s']]");
    private final By toolsLaunchButton = By.xpath("//article[@ui-view='tools']//button/span[text()='Launch']");

    @Step("Click the 'Choose Tool' selector button")
    public void clickChooseToolOption(String chooseToolOption) {
        waitForElementVisibility(chooseToolSelectorOption, chooseToolOption);
        waitForElementClickable(chooseToolSelectorOption, chooseToolOption);
        click(chooseToolSelectorOption, chooseToolOption);
    }

    @Step("Returning list of 'Choose Tool' options")
    public List<String> getChooseToolList() {
        waitForElementVisibility(chooseToolList);
        waitForElementClickable(chooseToolList);
        return getElementsText(chooseToolList);
    }

    @Step("Click the 'Choose Tool' selector button")
    public void clickChooseToolSelectorButton() {
        waitForElementVisibility(chooseToolSelectorButton);
        scrollToPlaceElementInCenter(chooseToolSelectorButton);
        waitForElementClickable(chooseToolSelectorButton);
        click(chooseToolSelectorButton);
    }

    @Step("Click tools 'Launch' button")
    public void clickToolsLaunchButton() {
        waitForElementClickable(toolsLaunchButton);
        click(toolsLaunchButton);
    }


}