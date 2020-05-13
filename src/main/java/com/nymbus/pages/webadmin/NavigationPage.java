package com.nymbus.pages.webadmin;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class NavigationPage extends PageTools {

    private By navigatorRegion = By.id("navigatorContainer");
    private By usersAndSecurityItem = By.xpath("//li[span[contains(@data-code, 'usersAndSecurity')]]/div");
    private By accountsTransactionsItem = By.xpath("//li[span[contains(@data-code, 'Accounts/Transactions')]]/div");
    private By accountsItem = By.xpath("//ul/li[@data-name='actmst (Accounts)']/a[@class='navigatorItem']");
    private By usersItem = By.xpath("//ul/li[@data-name='Users']/a[@class='navigatorItem']");
    private By logoutMenu = By.xpath("//div[@id='logoutMenu']/div/p");
    private By signOutOption = By.xpath("//div[@id='logoutMenu' and contains(@class, 'jDropDown')]/ul/li/a[contains(@class, 'logout')]");
    private By optionsUl = By.xpath("//div[@id='logoutMenu']/ul");

    @Step("Wait for 'Navigation' page")
    public void waitForPageLoaded() {
        waitForElementVisibility(navigatorRegion);
    }

    @Step("Click the 'Logout' menu")
    public void clickLogoutMenu() {
        click(logoutMenu);
    }

    @Step("Wait for options list visibility")
    public void waitForOptionUlVisibility() {
        waitForElementVisibility(optionsUl);
    }
    @Step("Click 'Sign Out' option")
    public void clickSignOut() {
        waitForElementVisibility(signOutOption);
        waitForElementClickable(signOutOption);
        click(signOutOption);
    }

    @Step("Click 'Users and Security' item")
    public void clickUsersAndSecurityItem() {
        waitForElementVisibility(usersAndSecurityItem);
        waitForElementClickable(usersAndSecurityItem);
        click(usersAndSecurityItem);
    }

    @Step("Click 'Users' item")
    public void clickUsersItem() {
        waitForElementVisibility(usersItem);
        waitForElementClickable(usersItem);
        click(usersItem);
    }

    @Step("Click 'Accounts/Transactions' item")
    public void clickAccountsTransactionsItem() {
        waitForElementVisibility(accountsTransactionsItem);
        waitForElementClickable(accountsTransactionsItem);
        click(accountsTransactionsItem);
    }

    @Step("Click 'Accounts/Transactions' item")
    public void clickAccountsItem() {
        waitForElementVisibility(accountsItem);
        waitForElementClickable(accountsItem);
        click(accountsItem);
    }

}
