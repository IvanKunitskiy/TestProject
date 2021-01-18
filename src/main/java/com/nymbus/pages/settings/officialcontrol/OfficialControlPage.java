package com.nymbus.pages.settings.officialcontrol;

import com.nymbus.core.base.PageTools;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class OfficialControlPage extends PageTools {
    private By lastCheckNumber = By.xpath("//tr[contains(string(),'Cashiers Check')][2]/td[7]//span");


    @Step("Return check number")
    public String checkAccountNumber() {
        SelenideTools.sleep(Constants.MINI_TIMEOUT);
        waitForElementVisibility(lastCheckNumber);
        return getElementText(lastCheckNumber);
    }
}
