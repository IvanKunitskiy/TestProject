package com.demo.pages;

import com.demo.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class NavigationPage extends PageTools {

    private By userMenu = By.id("user-menu");

    @Step("Click 'Proof date login' link")
    public void clickProofDateLogin() {
        waitForElementClickable(userMenu);
        click(userMenu);
    }
}