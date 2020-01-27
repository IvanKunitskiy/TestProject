package com.nymbus.pages.webadmin;

import com.nymbus.base.BasePage;
import com.nymbus.locator.*;
import io.qameta.allure.Step;

public class LoginPage extends BasePage {

    private Locator loginForm = new ID("form");
    private Locator userNameField = new ID("j_username");
    private Locator passwordField = new Name("j_password");
    private Locator goButton = new XPath("//input[contains(@onclick, 'submitForm')]");

    private Locator wrongCredentialsMessage = new XPath("//div[contains(@class, 'warningBox')]");

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
    public boolean isErrorMessageVisibleOnLoginForm(){
        return isElementVisible(wrongCredentialsMessage);
    }

    @Step("Check is user name field visible")
    public boolean isUserNameFieldVisible(){
        return isElementVisible(userNameField);
    }

    @Step("Check is password field visible")
    public boolean isPasswordFieldVisible(){
        return isElementVisible(passwordField);
    }

}
