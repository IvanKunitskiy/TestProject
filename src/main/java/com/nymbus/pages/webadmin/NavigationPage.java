package com.nymbus.pages.webadmin;

import com.nymbus.base.BasePage;
import com.nymbus.locator.ID;
import com.nymbus.locator.Locator;
import com.nymbus.locator.XPath;
import io.qameta.allure.Step;

public class NavigationPage extends BasePage {

    private Locator navigatorRegion = new ID("navigatorContainer");
    private Locator usersAndSecurityItem = new XPath("//li[span[contains(@data-code, 'usersAndSecurity')]]/div");
    private Locator usersItem = new XPath("//ul/li[@data-name='Users']/a[@class='navigatorItem']");

    @Step("Wait for 'Navigation' page")
    public void waitForPageLoaded(){
        waitForElementVisibility(navigatorRegion);
    }

    @Step("Click 'Users and Security' item")
    public void clickUsersAndSecurityItem(){
        waitForElementVisibility(usersAndSecurityItem);
        waitForElementClickable(usersAndSecurityItem);
        click(usersAndSecurityItem);
    }

    @Step("Click 'Users' item")
    public void clickUsersItem(){
        waitForElementVisibility(usersItem);
        waitForElementClickable(usersItem);
        click(usersItem);
    }

}
