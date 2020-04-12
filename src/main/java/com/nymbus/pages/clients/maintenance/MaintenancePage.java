package com.nymbus.pages.clients.maintenance;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class MaintenancePage extends PageTools {

    private By viewAllCardsButton = By.xpath("//button[span[text()='View All Cards']]");
    private By editCardButtonSelector = By.xpath("//table//tr[td[contains(text(), '%s')]]/td/button[@data-test-id='action-showEditPopup']");

    @Step("Click 'Edit' button in list by index")
    public void clickEditButtonInListByNameOnCard(String name) {
        waitForElementVisibility(editCardButtonSelector, name);
        waitForElementClickable(editCardButtonSelector, name);
        click(editCardButtonSelector, name);
    }

    @Step("Click on 'View All Cards' button")
    public void clickOnViewAllCardsButton() {
        waitForElementVisibility(viewAllCardsButton);
        click(viewAllCardsButton);
    }
}
