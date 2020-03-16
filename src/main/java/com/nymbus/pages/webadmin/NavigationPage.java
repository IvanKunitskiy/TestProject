package com.nymbus.pages.webadmin;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class NavigationPage extends PageTools {

    private By navigatorRegion = By.id("navigatorContainer");
    private By usersAndSecurityItem = By.xpath("//li[span[contains(@data-code, 'usersAndSecurity')]]/div");
    private By usersItem = By.xpath("//ul/li[@data-name='Users']/a[@class='navigatorItem']");

    @Step("Wait for 'Navigation' page")
    public void waitForPageLoaded() {
        waitForElementVisibility(navigatorRegion);
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

}
