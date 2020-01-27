package com.nymbus.pages.webadmin.userandsecurity;

import com.nymbus.base.BasePage;
import com.nymbus.locator.ID;
import com.nymbus.locator.Locator;
import com.nymbus.locator.XPath;
import io.qameta.allure.Step;

public class UsersPage extends BasePage {

    private Locator region = new XPath("//td[@class='mainPanel' and div[contains(text(), 'Users')]]");
    private Locator userField = new ID("lookup");
    private Locator searchButton = new ID("searchButton");
    private Locator usersRegion = new ID("selectedId");
    private Locator newPasswordField = new ID("loginPassword");
    private Locator confirmPasswordField = new ID("confirmPassword");
    private Locator saveButton = new XPath("//input[contains(@class, 'saveButton')]");

    @Step("Wait for Users region")
    public void waitUsersRegion(){
        waitForElementVisibility(region);
    }

    @Step("Set user name to 'User' field")
    public void setValueToUserField(String value){
        waitForElementVisibility(userField);
        waitForElementClickable(userField);
        type(value, userField);
    }

    @Step("Click 'Search' button")
    public void clickSearchButton(){
        waitForElementVisibility(searchButton);
        waitForElementClickable(searchButton);
        click(searchButton);
    }

    @Step("Wait for 'Users' list")
    public void waitForUserListRegion(){
        waitForElementVisibility(usersRegion);
    }

    @Step("Set password to 'New Password' field")
    public void setNewPassword(String password){
        waitForElementVisibility(newPasswordField);
        waitForElementClickable(newPasswordField);
        type(password, newPasswordField);
    }

    @Step("Set password to 'Confirm Password' field")
    public void setConfirmPassword(String confirmPassword){
        waitForElementVisibility(confirmPasswordField);
        waitForElementClickable(confirmPasswordField);
        type(confirmPassword, confirmPasswordField);
    }

    @Step("Click 'Save' button")
    public void clickSaveButton(){
        waitForElementVisibility(saveButton);
        waitForElementClickable(saveButton);
        click(saveButton);
    }

}
