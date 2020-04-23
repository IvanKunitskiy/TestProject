package com.nymbus.pages.accounts;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class AccountsPage extends PageTools {

    private By callStatement = By.xpath("//button[span[text()='Call Statement']]");

    @Step("Click the 'Call Statement' button")
    public void clickCallStatementButton() {
        waitForElementVisibility(callStatement);
        waitForElementClickable(callStatement);
        click(callStatement);
    }

}