package com.nymbus.pages;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class NavigationPage extends PageTools {

    private By userMenu = By.id("user-menu");
    private By openAccountButton = By.xpath("//li[contains(@class, 'account')]/button");
    private By userFullName = By.xpath("//span[@text='header.fullName']/span");
    private By viewMyProfileLink = By.id("site-header-view-profile");
    private By signOutButton = By.xpath("//li[a/*[@class='nyb-icon-logout']]");

    @Step("Wait for user menu")
    public void waitForUserMenuVisible() {
        waitForElementVisibility(userMenu);
    }

    @Step("Click on account button")
    public void clickAccountButton() {
        waitForElementVisibility(openAccountButton);
        waitForElementClickable(openAccountButton);
        click(openAccountButton);
    }

    @Step("Get user full name")
    public String getUserFullName() {
        waitForElementVisibility(userFullName);
        waitForElementClickable(userFullName);
        return getElementText(userFullName);
    }

    @Step("Check is 'Sign Out' button visible")
    public boolean isSingOutButtonVisible() {
        return isElementVisible(signOutButton);
    }

    @Step("Click 'Sign Out' button")
    public void clickSignOut() {
        waitForElementVisibility(signOutButton);
        waitForElementClickable(signOutButton);
        click(signOutButton);
    }

    @Step("Click 'View my Profile' link")
    public void clickViewMyProfileLink() {
        waitForElementVisibility(viewMyProfileLink);
        waitForElementClickable(viewMyProfileLink);
        click(viewMyProfileLink);
    }

}
