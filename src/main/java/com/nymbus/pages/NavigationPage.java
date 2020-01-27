package com.nymbus.pages;

import com.nymbus.base.BasePage;
import com.nymbus.locator.ID;
import com.nymbus.locator.Locator;
import com.nymbus.locator.XPath;
import io.qameta.allure.Step;

public class NavigationPage extends BasePage {

    private Locator userMenu = new ID("user-menu");
    private Locator openAccountButton = new XPath("//li[contains(@class, 'account')]/button");
    private Locator userFullName = new XPath("//span[@text='header.fullName']/span");
    private Locator viewMyProfileLink = new ID("site-header-view-profile");
    private Locator signOutButton = new XPath("//li[a/*[@class='nyb-icon-logout']]");

    @Step("Wait for user menu")
    public void waitForUserMenuVisible(){
        waitForElementVisibility(userMenu);
    }

    @Step("Click on account button")
    public void clickAccountButton(){
        waitForElementVisibility(openAccountButton);
        click(openAccountButton);
    }

    @Step("Get user full name")
    public String getUserFullName(){
        waitForElementVisibility(userFullName);
        waitForElementClickable(userFullName);
        return getElementText(userFullName);
    }

    @Step("Check is 'Sign Out' button visible")
    public boolean isSingOutButtonVisible(){
        return isElementVisible(signOutButton);
    }

    @Step("Click 'Sign Out' button")
    public void clickSignOut(){
        waitForElementVisibility(signOutButton);
        waitForElementClickable(signOutButton);
        click(signOutButton);
    }

    @Step("Click 'View my Profile' link")
    public void clickViewMyProfileLink(){
        waitForElementVisibility(viewMyProfileLink);
        waitForElementClickable(viewMyProfileLink);
        click(viewMyProfileLink);
    }

}
