package com.nymbus.pages.teller;

import com.nymbus.core.base.PageTools;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class TellerModalPage extends PageTools {
    private By modalWindow = By.xpath("//div[contains(@class, 'login-modal')]");
    private By cashDrawerName = By.xpath("//div[@name='cashDrawerTemplate']/a/span/span");
    private By proofDate = By.xpath("//div[@ng-model='viewModel.proofDate']//a//span[@class='select2-chosen']/span");
    private By location = By.xpath("//div[@ng-model='viewModel.location']//a//span[@class='select2-chosen']/span");
    private By enterButton = By.xpath("//div[@class='modal-content']//button[contains(@class, 'btn-primary')]");
    private By blankTellerField = By.xpath("//div[@ng-model='viewModel.otherTeller']//a/span[1]");
    private By bankBranch = By.xpath("//div[@ng-model='viewModel.location']//a/span/span");
    private By closeButton = By.xpath("//button[contains(string(),'×')]");

    @Step("Is blank teller field visible")
    public boolean isBlankTellerFieldVisible() {
        return isElementVisible(blankTellerField);
    }

    @Step("Wait 'Proof Date Login' modal window")
    public void waitModalWindow() {
        waitForElementVisibility(modalWindow);
        waitForElementClickable(modalWindow);
    }

    @Step("Get 'Cash Drawer' name")
    public String getCashDrawerName() {
        waitForElementVisibility(cashDrawerName);
        return getElementText(cashDrawerName).trim();
    }

    @Step("Get 'Bank Branch' name")
    public String getBankBranch() {
        waitForElementVisibility(bankBranch);
        return getElementText(bankBranch).trim();
    }

    @Step("Get 'Proof date' value")
    public String getProofDateValue() {
        waitForElementVisibility(proofDate);
        return getElementText(proofDate).trim();
    }

    @Step("Get 'Location' value")
    public String getLocation() {
        waitForElementVisibility(location);
        return getElementText(location).trim();
    }

    @Step("Click 'Enter' button")
    public void clickEnterButton() {
        waitForElementVisibility(enterButton);
        waitForElementClickable(enterButton);
        jsClick(enterButton);
    }

    @Step("Click 'Close' button")
    public void clickCloseButton() {
        waitForElementVisibility(closeButton);
        waitForElementClickable(closeButton);
        jsClick(closeButton);
    }

    @Step("Is Enter button clickable")
    public boolean isEnterButtonClickable() {
        return isElementClickable(enterButton);
    }

    @Step("Wait for modal disappear")
    public void waitForModalInvisibility() {
        waitForElementInvisibility(modalWindow);
    }

    @Step("Wait for modal appear")
    public void waitForModalVisibility() {
        waitForElementVisibility(modalWindow);
    }

    @Step("Is modal window visible")
    public boolean isModalWindowVisible() {
        SelenideTools.sleep(Constants.MINI_TIMEOUT);
        return isElementVisible(modalWindow);
    }
}