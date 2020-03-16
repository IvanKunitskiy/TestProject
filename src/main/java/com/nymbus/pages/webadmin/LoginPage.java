package com.nymbus.pages.webadmin;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class LoginPage extends PageTools {

    private By loginForm = By.id("form");
    private By userNameField = By.id("j_username");
    private By passwordField = By.name("j_password");
    private By goButton = By.xpath("//input[contains(@onclick, 'submitForm')]");

    private By wrongCredentialsMessage = By.xpath("//div[contains(@class, 'warningBox')]");

    @Step("Wait login form")
    public void waitForLoginForm() {
        waitForElementVisibility(loginForm);
        waitForElementClickable(loginForm);
    }

    @Step("Type user name")
    public void typeUserName(String email) {
        waitForElementVisibility(userNameField);
        waitForElementClickable(userNameField);
        type(email, userNameField);
    }

    @Step("Type user password")
    public void typePassword(String password) {
        waitForElementVisibility(passwordField);
        waitForElementClickable(passwordField);
        type(password, passwordField);
    }

    @Step("Click 'Go' button")
    public void clickGoButton() {
        waitForElementVisibility(goButton);
        waitForElementClickable(goButton);
        click(goButton);
    }

    @Step("Check is login's error visible")
    public boolean isErrorMessageVisibleOnLoginForm() {
        return isElementVisible(wrongCredentialsMessage);
    }

    @Step("Check is user name field visible")
    public boolean isUserNameFieldVisible() {
        return isElementVisible(userNameField);
    }

    @Step("Check is password field visible")
    public boolean isPasswordFieldVisible() {
        return isElementVisible(passwordField);
    }

}
