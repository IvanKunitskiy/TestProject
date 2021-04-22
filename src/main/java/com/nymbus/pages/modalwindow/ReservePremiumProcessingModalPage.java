package com.nymbus.pages.modalwindow;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class ReservePremiumProcessingModalPage extends PageTools {

    private final By modalWindow = By.xpath("//div[@class='modal-content']");
    private final By addNewLoanReservePremiumButton = By.xpath("//button/span[text()='Add New Loan Reserve/Premium']");

    @Step("Wait for modal window visibility")
    public void waitForModalWindowVisibility() {
        waitForElementVisibility(modalWindow);
    }

    @Step("Click the 'Add New Loan Reserve/Premium' button")
    public void clickAddNewLoanReservePremiumButton() {
        waitForElementClickable(addNewLoanReservePremiumButton);
        click(addNewLoanReservePremiumButton);
    }
}
