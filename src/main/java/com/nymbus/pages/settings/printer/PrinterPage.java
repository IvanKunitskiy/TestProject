package com.nymbus.pages.settings.printer;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class PrinterPage extends PageTools {
    private By printer = By.xpath("//td[contains(string(),'%s')]");
    private By officialInput = By.xpath("//input[@name='field[isofficialcheck]']");


    @Step("Click 'View printer' link")
    public void clickToPrinter(String name) {
        waitForElementClickable(printer, name);
        click(printer, name);
    }

    @Step("Check 'official printer' link")
    public boolean checkIsOfficialPrinter() {
        waitForElementInvisibility(officialInput);
        String value = getElementAttributeValue("value", officialInput);
        return value.equals("1");
    }
}
