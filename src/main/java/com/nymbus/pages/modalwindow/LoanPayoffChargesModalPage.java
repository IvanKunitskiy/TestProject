package com.nymbus.pages.modalwindow;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class LoanPayoffChargesModalPage extends PageTools {

    private By addNewPayoffCharge = By.xpath("//button[span[text()='Add New Payoff Charge']]");

    @Step("Click the 'Add New Payoff Charge'")
    public void clickAddNewPayoffChargeButton() {
        click(addNewPayoffCharge);
    }
}
