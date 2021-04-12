package com.nymbus.pages.settings.bankcontrol;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class BankControlPage extends PageTools {
    private final By miscellaneousButton = By.xpath("//a[contains(string(),'Miscellaneous')]");
    private final By loansButton = By.xpath("(//a[contains(string(),'Loans')])[2]");
    private final By editButton = By.xpath("//button[contains(string(),'Edit')]");
    private final By saveButton = By.xpath("//button[contains(string(),'Save Changes')]");
    private final By applyCTROnlineAlert = By.xpath("//div[@data-field-id='CTROnlineAlert']//span");
    private final By applyCtrOnlineAlertButton = By.xpath("//div[@data-field-id='CTROnlineAlert']//input//..");
    private final By applyCtrOnlineAlertText = By.xpath("(//div[@data-field-id='CTROnlineAlert']//input)[2]");
    private final By CTRLimit = By.xpath("//div[@data-field-id='CTRLimit']//input");
    private final By commercialParticipationLite = By.xpath("//input[@name='field[CommercialParticipationLite]']");
    private final By commercialParticipationLiteButton = By.xpath("//div[@data-rootid='43830316']//input/..");

    /*
     ** Tabs
     */
    private final By miscellaneousTab = By.xpath("//a[contains(string(),'Miscellaneous')]");
    private final By loansTab = By.xpath("//a[text()='Loans']");

    @Step("Click the 'Loans' tab")
    public void clickLoansTab() {
        waitForElementClickable(loansTab);
        click(loansTab);
    }

    @Step("Click the 'Miscellaneous' tab")
    public void clickMiscellaneousButton() {
        waitForElementClickable(miscellaneousTab);
        click(miscellaneousTab);
    }

    @Step("Click the 'Miscellaneous' button")
    public void goToLoansButton() {
        waitForElementClickable(loansButton);
        click(loansButton);
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

    @Step("Click 'CTR Online Alert' value")
    public void clickOnlineSTRAlert() {
        waitForElementVisibility(applyCTROnlineAlert);
        waitForElementClickable(applyCTROnlineAlert);
        click(applyCtrOnlineAlertButton);
    }

    @Step("Click 'Commercial Participation - Lite' value")
    public void clickCommercialParticipationLite() {
        waitForElementVisibility(commercialParticipationLiteButton);
        waitForElementClickable(commercialParticipationLiteButton);
        click(commercialParticipationLiteButton);
    }

    @Step("Get the 'CTR Online Alert' value")
    public String getOnlineSTRAlert() {
        waitForElementPresent(applyCtrOnlineAlertText);
        return getElementAttributeValue("value", applyCtrOnlineAlertText);
    }

    @Step("Get the 'Commercial Participation - Lite' value")
    public String getCommercialParticipationLite() {
        waitForElementPresent(commercialParticipationLite);
        return getElementAttributeValue("value", commercialParticipationLite);
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
