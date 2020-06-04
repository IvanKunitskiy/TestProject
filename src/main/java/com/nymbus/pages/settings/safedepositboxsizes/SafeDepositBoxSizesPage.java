package com.nymbus.pages.settings.safedepositboxsizes;

import com.nymbus.core.base.PageTools;
import com.nymbus.core.utils.Constants;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class SafeDepositBoxSizesPage extends PageTools {

    private By boxSizeRowsCount = By.xpath("//table//tbody//tr[position() mod 2 = 1]");
    private By boxSizeValueByIndex = By.xpath("(//table//tbody//tr[position() mod 2 = 1]//td[1])[%s]");
    private By rentalAmountByIndex = By.xpath("(//table//tbody//tr[position() mod 2 = 1]//td[2])[%s]");

    @Step("Get boxSizeRowsCount")
    public int getBoxSizeRowsCount() {
       return getElementsWithZeroOptionWithWait(Constants.MICRO_TIMEOUT, boxSizeRowsCount).size();
    }

    @Step("Get boxSize {0}")
    public String getBoxSizeValueByIndex(int index) {
        waitForElementVisibility(boxSizeValueByIndex, index);
        return getElementText(boxSizeValueByIndex, index);
    }

    @Step("Get rental amount {0}")
    public String getRentalAmountByIndex(int index) {
        waitForElementVisibility(rentalAmountByIndex, index);
        return getElementText(rentalAmountByIndex, index);
    }
}