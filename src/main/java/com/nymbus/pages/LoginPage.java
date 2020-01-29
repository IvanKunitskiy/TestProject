package com.nymbus.pages;

import com.nymbus.base.BasePage;
import com.nymbus.locator.ClassName;
import com.nymbus.locator.Locator;
import com.nymbus.locator.Name;
import com.nymbus.locator.XPath;
import io.qameta.allure.Step;

public class LoginPage extends BasePage {

    private Locator loginForm = new ClassName("loginForm");
    private Locator userNameField = new Name("login");
    private Locator userNameFieldError = new XPath("//div[@data-test-id='error-loginUserRecognize']");
    private Locator passwordField = new Name("password");
    private Locator passwordFieldError = new XPath("//div[@data-test-id='error-loginPasswordNotRight']");
    private Locator enterButton = new XPath("//button[@data-test-id='action-login']");

    private Locator wrongCredentialsMessage = new XPath("//div[contains(@class, 'toast-error')]");

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

    @Step("Click on Enter button")
    public void clickEnterButton() {
        waitForElementVisibility(enterButton);
        click(enterButton);
    }

    @Step("Check is login's error visible")
    public boolean isErrorsVisibleOnLoginForm(){
        return isElementVisible(userNameFieldError) ||
                isElementVisible(passwordFieldError) ||
                isElementVisible(wrongCredentialsMessage);
    }

    @Step("Get error message text")
    public String getErrorMessage(){
        return getElementText(wrongCredentialsMessage).trim();
    }

    @Step("Wait for error message block disappear")
    public void waitForErrorMessageDisappear(){
        waitForElementInvisibility(wrongCredentialsMessage);
    }

    @Step("Check is user name field has error visible")
    public boolean isUserNameFieldHasError(){
        return isElementVisible(userNameFieldError);
    }

    @Step("Check is password field has error visible")
    public boolean isPasswordFieldHasError(){
        return isElementVisible(passwordFieldError);
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
