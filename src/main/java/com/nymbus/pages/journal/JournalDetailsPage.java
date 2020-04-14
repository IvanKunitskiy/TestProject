package com.nymbus.pages.journal;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class JournalDetailsPage extends PageTools {

    private By errorCorrectButton = By.xpath("//td[contains(@class, 'actions')]/button[1]");
    private By itemInTableState = By.xpath("//tr[contains(@class, 'hoverPointer')][%s]//td[2]/button");

    @Step("Click 'Error Correct' button")
    public void clickErrorCorrectButton() {
        waitForElementClickable(errorCorrectButton);
        click(errorCorrectButton);
    }

    @Step("Wait for 'Error Correct' button invisibility")
    public void waitForErrorCorrectButtonInvisibility() {
        waitForElementInvisibility(errorCorrectButton);
    }

    @Step("Get item {0} state")
    public String getItemState(int index) {
        waitForElementVisibility(itemInTableState, index);
        return getWebElement(itemInTableState, index).getAttribute("title");
    }
}
