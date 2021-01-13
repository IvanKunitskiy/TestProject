package com.nymbus.pages.loans;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class LoanProductOverviewPage extends PageTools {

    private static By addNewButton = By.xpath("//div/a//span[text()='Add New']");
    private static By escrowPayment = By.xpath("//tr/td[@data-column-id='dataentryfieldnumber_name']" +
            "[div[@data-field-id='dataentryfieldnumber_name']//span[text()='Escrow payment']]" +
            "/following-sibling::td[1]/div//span[@class='xwidget_readonly_value']");

    @Step("Wait for 'Add New' button is clickable")
    public void waitForAddNewButtonClickable() {
        waitForElementClickable(addNewButton);
    }

    @Step("Get 'Escrow payment' value")
    public String getEscrowPaymentValue() {
        return getElementText(escrowPayment).replaceAll("[^0-9.]", "");
    }
}
