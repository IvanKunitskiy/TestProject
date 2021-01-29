package com.nymbus.pages.settings.callclasscodes;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class CallCodePage extends PageTools {
    private By editButton = By.xpath("//button[span[text()='Edit']]");
    private By addNewButton = By.xpath("//div[@id='addNewButton']/a");

    @Step("Wait for 'Add new' button visible")
    public void waitForAddNewButtonVisible() {
        waitForElementVisibility(addNewButton);
    }

    @Step("Click the 'Edit' button")
    public void clickEditButton() {
        waitForElementClickable(editButton);
        click(editButton);
    }
}
