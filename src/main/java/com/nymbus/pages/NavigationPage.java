package com.nymbus.pages;

import com.nymbus.core.base.PageTools;
import com.nymbus.core.utils.SelenideTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class NavigationPage extends PageTools {

    private By userMenu = By.id("user-menu");
    private By openAccountButton = By.xpath("//li[contains(@class, 'account')]/button");
    private By userFullName = By.xpath("//span[@text='header.fullName']/span");
    private By viewMyProfileLink = By.id("site-header-view-profile");
    private By signOutButton = By.xpath("//li[a/*[@class='nyb-icon-logout']]");
    private By alertNotificationsButton = By.xpath("//button[@data-panel='alert-notifications']");
    private By notificationCircle = By.xpath("//span[contains(@class, 'notificationCircle')]");
    private By notificationsCount = By.xpath("//button[@data-panel='alert-notifications']/i/span/span");
    private By proofDateLoginButton = By.xpath("//*[@id='user-menu']//a[@ng-click='header.showProofDateLoginModal()']");

    @Step("Get notifications count")
    public int getNotificationsCount() {
        String count = getElementAttributeValue("innerText", notificationCircle).replaceAll("[0-9]]", "");
        return Integer.parseInt(count);
    }

    @Step("Wait for notification circle is visible")
    public void waitNotificationCircleVisible() {
        SelenideTools.sleep(10);
        waitForElementVisibility(notificationCircle);
    }

    @Step("Is notification circle visible")
    public boolean isNotificationCircleVisible() {
        return isElementVisible(notificationCircle);
    }

    @Step("Click on account button")
    public void clickAlertNotificationsButton() {
        waitForElementVisibility(alertNotificationsButton);
        waitForElementClickable(alertNotificationsButton);
        click(alertNotificationsButton);
    }

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

    @Step("Click 'Proof date login' link")
    public void clickProofDateLogin() {
        waitForElementClickable(proofDateLoginButton);
        click(proofDateLoginButton);
    }
}