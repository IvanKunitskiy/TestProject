package com.nymbus.pages.teller;

import com.nymbus.core.base.PageTools;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class TellerModalPage extends PageTools {
    private final By modalWindow = By.xpath("//div[contains(@class, 'login-modal')]");
    private final By cashDrawerName = By.xpath("//div[@name='cashDrawerTemplate']/a/span/span");
    private final By proofDate = By.xpath("//div[@ng-model='viewModel.proofDate']//a//span[@class='select2-chosen']/span");
    private final By location = By.xpath("//div[@ng-model='viewModel.location']//a//span[@class='select2-chosen']/span");
    private final By enterButton = By.xpath("//div[@class='modal-content']//button[contains(@class, 'btn-primary')]");
    private final By enterButtonSpan = By.xpath("//div[@class='modal-content']//button[contains(@class, 'btn-primary')]");
    private final By blankTellerField = By.xpath("//div[@ng-model='viewModel.otherTeller']//a/span[1]");
    private final By bankBranch = By.xpath("//div[@ng-model='viewModel.location']//a/span/span");
    private final By closeButton = By.xpath("//button[contains(string(),'×')]");
    private final By teller = By.xpath("//a[@placeholder='Teller']/span");
    private final By tellerOption = By.xpath("//li[@role='option']//span[contains(string(),'%s')]");
    private final By cashRecycler = By.xpath("//a[@placeholder='Select Cash Recycler']/span");
    private final By cashRecyclerItem = By.xpath("(//span[@ng-bind-html='viewModel.cashDrawerView(item)'])[%s]");
    private final By side = By.xpath("//a[@placeholder='Side']/span");
    private final By leftSide = By.xpath("(//span[@ng-bind-html='viewModel.cashDrawerSideView(item)'])[1]");

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

    @Step("Click 'Enter' button")
    public void clickEnterSpanButton() {
        SelenideTools.sleep(Constants.MINI_TIMEOUT);
        clickNotVisible(enterButtonSpan);
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

    @Step("Get 'Teller' value")
    public String getTeller() {
        waitForElementVisibility(teller);
        return getElementText(teller);
    }

    @Step("Click 'Teller'")
    public void clickTeller() {
        waitForElementVisibility(teller);
        click(teller);
    }

    @Step("Click 'Teller' option")
    public void clickTellerOption(String teller) {
        waitForElementVisibility(tellerOption, teller);
        click(tellerOption, teller);
    }

    @Step("Get 'Cash Recycler' value")
    public String getCashRecycler() {
        waitForElementVisibility(cashRecycler);
        return getElementText(cashRecycler);
    }

    @Step("Click 'Cash Recycler'")
    public void clickCashRecycler() {
        waitForElementVisibility(cashRecycler);
        click(cashRecycler);
    }

    @Step("Click Cash Recycler item")
    public void clickCashRecyclerItem(String name) {
        int index = 1;
        waitForElementVisibility(cashRecyclerItem, index);
        boolean notContains = true;
        while (notContains) {
            notContains = !getElementsText(cashRecyclerItem, index).contains(name);
            if (!notContains) {
                System.out.println(name);
                click(cashRecyclerItem, index);
            }
            index++;
        }
    }

    @Step("Click 'Side'")
    public void clickSide() {
        waitForElementVisibility(side);
        click(side);
    }

    @Step("Get 'Side'")
    public String getSide() {
        waitForElementVisibility(side);
        return getElementText(side);
    }

    @Step("Click 'Left Side'")
    public void clickLeftSide() {
        waitForElementVisibility(leftSide);
        click(leftSide);
    }
}