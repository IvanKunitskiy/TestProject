package com.nymbus.pages.modalwindow;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class SupervisorModalPage extends PageTools {

    private By modalWindow = By.xpath("//*[contains(@class, 'supervisorLogin')]");
    private By loginInput = By.xpath("//input[@ng-model='supervisor.userName']");
    private By passwordInput = By.xpath("//input[@ng-model='supervisor.password']");
    private By enterButton = By.xpath("//*[contains(@class, 'supervisorLogin')]//button[contains(text(), 'Enter')]");

    @Step("Type login input {0}")
    public void inputLogin(String text) {
        waitForElementClickable(loginInput);
        type(text, loginInput);
    }

    @Step("Type password input {0}")
    public void inputPassword(String text) {
        waitForElementClickable(passwordInput);
        type(text, passwordInput);
    }

    @Step("Click 'Enter' button")
    public void clickEnter() {
        waitForElementClickable(enterButton);
        click(enterButton);
    }

    @Step("Wait for modal window invisibility")
    public void waitForModalWindowInvisibility() {
        waitForElementInvisibility(modalWindow);
    }

    /**
     * Js actions on supervisor modal page
     */
    @Step("Type password input {0}")
    public void inputJsPassword(String text) {
        waitForElementClickable(passwordInput);
        jsSetValue(text, passwordInput);
        jsRiseOnchange(passwordInput);
    }
    @Step("Type login input {0}")
    public void inputJsLogin(String text) {
        waitForElementClickable(loginInput);
        jsSetValue(text, loginInput);
        jsRiseOnchange(loginInput);
    }

    @Step("Click 'Enter' button")
    public void clickJsEnter() {
        waitForElementClickable(enterButton);
        jsClick(enterButton);
    }
}