package com.nymbus.pages.settings.regcc;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class RegCCPage extends PageTools {
    private final By activeRegularCheck = By.xpath("(//td[@data-column-id='active']//span)[1]");

    @Step("Get status for Reg CC")
    public boolean isRegCCActive(){
        waitForElementVisibility(activeRegularCheck);
        return getElementsText(activeRegularCheck).contains("YES");
    }
}
