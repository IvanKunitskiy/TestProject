package com.nymbus.pages.modalwindow;

import com.nymbus.core.base.PageTools;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class SupervisorModalPage extends PageTools {

    private final By modalWindow = By.xpath("//*[contains(@class, 'supervisorLogin')]");
    private final By loginInput = By.xpath("//input[@ng-model='supervisor.userName']");
    private final By passwordInput = By.xpath("//input[@ng-model='supervisor.password']");
    private final By enterButton = By.xpath("//*[contains(@class, 'supervisorLogin')]//button[contains(text(), 'Enter')]");

    @Step("Type login input {0}")
    public void inputLogin(String text) {
        waitForElementClickable(loginInput);
        jsType(text, loginInput);
    }

    @Step("Type password input {0}")
    public void inputPassword(String text) {
        waitForElementClickable(passwordInput);
        jsType(text, passwordInput);
    }

    @Step("Click 'Enter' button")
    public void clickEnter() {
        waitForElementClickable(enterButton);
        jsClick(enterButton);
    }

    @Step("Wait for modal window invisibility")
    public void waitForModalWindowInvisibility() {
        waitForElementInvisibility(modalWindow);
    }

    @Step("Is modal window visible")
    public boolean isModalWindowVisible() {
        SelenideTools.sleep(Constants.MINI_TIMEOUT);
        return isElementVisible(modalWindow);
    }
}