package com.nymbus.pages.webadmin.userandsecurity;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class UsersPage extends PageTools {

    private By region = By.xpath("//td[@class='mainPanel' and div[contains(text(), 'Users')]]");
    private By userField = By.id("lookup");
    private By searchButton = By.id("searchButton");
    private By usersRegion = By.id("selectedId");
    private By newPasswordField = By.id("loginPassword");
    private By confirmPasswordField = By.id("confirmPassword");
    private By saveButton = By.xpath("//input[contains(@class, 'saveButton')]");
    private By userGlAccoutsInput = By.xpath("(//input[@type='integerbox'])[2]");
    private By accountNumber = By.xpath("((//td[@class='fieldsCell'])[7]//span)[6]");

    @Step("Wait for Users region")
    public void waitUsersRegion() {
        waitForElementVisibility(region);
    }

    @Step("Set user name to 'User' field")
    public void setValueToUserField(String value) {
        waitForElementVisibility(userField);
        waitForElementClickable(userField);
        type(value, userField);
    }

    @Step("Click 'Search' button")
    public void clickSearchButton() {
        waitForElementVisibility(searchButton);
        waitForElementClickable(searchButton);
        click(searchButton);
    }

    @Step("Wait for 'Users' list")
    public void waitForUserListRegion() {
        waitForElementVisibility(usersRegion);
    }

    @Step("Set password to 'New Password' field")
    public void setNewPassword(String password) {
        waitForElementVisibility(newPasswordField);
        waitForElementClickable(newPasswordField);
        type(password, newPasswordField);
    }

    @Step("Set password to 'Confirm Password' field")
    public void setConfirmPassword(String confirmPassword) {
        waitForElementVisibility(confirmPasswordField);
        waitForElementClickable(confirmPasswordField);
        type(confirmPassword, confirmPasswordField);
    }

    @Step("Click 'Save' button")
    public void clickSaveButton() {
        waitForElementVisibility(saveButton);
        waitForElementClickable(saveButton);
        click(saveButton);
    }

    public boolean getUseGLAccountsValue() {
        waitForElementVisibility(userGlAccoutsInput);
        return !getElementAttributeValue("value", userGlAccoutsInput).equals("0");
    }

    public String getInternalAccountNumber() {
        waitForElementVisibility(accountNumber);
        return getElementText(accountNumber);
    }
}
