package com.nymbus.pages;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class LoginPage extends PageTools {

    private By loginForm = By.className("loginForm");
    private By userNameField = By.name("login");
    private By userNameFieldError = By.xpath("//div[@data-test-id='error-loginUserRecognize']");
    private By passwordField = By.name("password");
    private By passwordFieldError = By.xpath("//div[@data-test-id='error-loginPasswordNotRight']");
    private By enterButton = By.xpath("//button[@data-test-id='action-login']");

    private By wrongCredentialsMessage = By.xpath("//div[contains(@class, 'toast-error')]");
    private By progressBar = By.xpath("//div[contains(@class, 'progress-bar')]");

    public void waitForLoginForm() {
        waitForElementVisibility(loginForm);
        waitForElementClickable(loginForm);
    }

    public void waitForLoginFormNotVisible() {
        waitForElementInvisibility(loginForm);
    }

    public void typeUserName(String email) {
        waitForElementVisibility(userNameField);
        waitForElementClickable(userNameField);
        type(email, userNameField);
    }

    public void typePassword(String password) {
        waitForElementVisibility(passwordField);
        waitForElementClickable(passwordField);
        type(password, passwordField);
    }

    public void clickEnterButton() {
        waitForElementVisibility(enterButton);
        click(enterButton);
    }

    public boolean isErrorsVisibleOnLoginForm(){
        return isElementVisible(userNameFieldError) ||
                isElementVisible(passwordFieldError) ||
                isElementVisible(wrongCredentialsMessage);
    }

    public String getErrorMessage(){
        return getElementText(wrongCredentialsMessage).trim();
    }

    public void waitForErrorMessageDisappear(){
        waitForElementInvisibility(wrongCredentialsMessage);
    }

    public boolean isUserNameFieldHasError(){
        return isElementVisible(userNameFieldError);
    }

    public boolean isPasswordFieldHasError(){
        return isElementVisible(passwordFieldError);
    }

    public boolean isUserNameFieldVisible(){
        return isElementVisible(userNameField);
    }

    public boolean isPasswordFieldVisible(){
        return isElementVisible(passwordField);
    }

    public void waitForProgressBarDisappear(){
        waitForElementVisibility(progressBar);
        waitForElementInvisibility(progressBar);
    }
}