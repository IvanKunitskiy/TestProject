package com.nymbus.pages.settings.bankcontrol;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class BankControlLoansPage extends PageTools {
    private final By commercialParticipation = By.xpath("//div[@data-field-id='CommercialParticipationLite']//input[@type='checkbox']");

    @Step("Get 'Commercial Participation' value")
    public String getCommercialParticipation() {
        return getDisabledElementAttributeValue("value", commercialParticipation);
    }
}
