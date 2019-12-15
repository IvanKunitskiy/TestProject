package com.nymbus.pages.homepages;

import com.nymbus.base.BasePage;
import com.nymbus.locator.Locator;
import com.nymbus.locator.XPath;
import io.qameta.allure.Step;

public class LoginPage extends BasePage {

    private Locator emailInputField = new XPath("//input[@id='edit-name']");
    private Locator passwordInputField = new XPath("//input[@id='edit-pass']");
    private Locator loginButton = new XPath("//input[@value='Log in']");

    /**
     * Types
     */

    @Step("Type user email")
    public void typeEmail(String email) {
        waitForElementVisibility(emailInputField);
        type(email, emailInputField);
    }

    @Step("Type user password")
    public void typePassword(String password) {
        waitForElementVisibility(passwordInputField);
        type(password, passwordInputField);
    }

    @Step("Click on log in button")
    public void clickLoginButton() {
        waitForElementVisibility(loginButton);
        click(loginButton);
    }

    /**
     * Waits
     */

    public void waitForEmailFieldVisible() {
        waitForElementVisibility(emailInputField);
    }
}
