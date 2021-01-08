package com.nymbus.pages.settings.bankcontrol;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class BankControlPage extends PageTools {
    private final By miscellaneousButton = By.xpath("//a[contains(string(),'Miscellaneous')]");
    private final By editButton = By.xpath("//button[contains(string(),'Edit')]");
    private final By saveButton = By.xpath("//button[contains(string(),'Save Changes')]");
    private final By applyCTROnlineAlert = By.xpath("//div[@data-field-id='CTROnlineAlert']//span");
    private final By applyCtrOnlineAlertButton = By.xpath("//div[@data-field-id='CTROnlineAlert']//input//..");
    private final By applyCtrOnlineAlertText = By.xpath("(//div[@data-field-id='CTROnlineAlert']//input)[2]");
    private final By CTRLimit = By.xpath("//div[@data-field-id='CTRLimit']//input");

    @Step("Click the 'Miscellaneous' button")
    public void clickMiscellaneousButton() {
        waitForElementClickable(miscellaneousButton);
        click(miscellaneousButton);
    }

    @Step("Click the 'Edit button' button")
    public void clickEditButton() {
        waitForElementClickable(editButton);
        click(editButton);
    }

    @Step("Click the 'Save button' button")
    public void clickSaveButton() {
        waitForElementClickable(saveButton);
        click(saveButton);
    }

    @Step("Get the 'CTR Online Alert' value")
    public void clickOnlineSTRAlert() {
        waitForElementVisibility(applyCTROnlineAlert);
        waitForElementClickable(applyCTROnlineAlert);
        click(applyCtrOnlineAlertButton);
    }

    @Step("Get the 'CTR Online Alert' value")
    public String getOnlineSTRAlert() {
        waitForElementPresent(applyCtrOnlineAlertText);
        return getElementAttributeValue("value", applyCtrOnlineAlertText);
    }

    @Step("Get 'CTR Limit' value")
    public String getCTRLimitValue() {
        waitForElementPresent(CTRLimit);
        return getElementText(CTRLimit);
    }

    @Step("Input 'CTR Limit' value")
    public void setCTRLimitValue(String limit) {
        waitForElementPresent(CTRLimit);
        type(limit, CTRLimit);
    }

}
