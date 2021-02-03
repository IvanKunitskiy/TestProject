package com.nymbus.pages.modalwindow;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.util.List;

public class LoanPayoffChargesModalPage extends PageTools {

    private final By addNewPayoffCharge = By.xpath("//button[span[text()='Add New Payoff Charge']]");
    private final By doneButton = By.xpath("//button[text()='Done']");
    private final By chargeDescription = By.xpath("//input[@data-test-id='field-description_0']");
    private final By chargeAmount = By.xpath("//input[@data-test-id='field-amount_0']");
    private final By glOffset = By.xpath("//div[@id='glaccountid_0']/div//input");
    private final By getGlOffsetOption = By.xpath("//div[@id='glaccountid_0']/div/div//div[@role='option']/div/div[contains(text(), '%s')]");
    private final By getGlOffsetList = By.xpath("//div[@id='glaccountid_0']/div/div//div[@role='option']/div/div");
    private final By closeButton = By.xpath("//div[@class='modal-content']//button[@ng-click='close()']/span[text()='×']");
    private final By modalWindow = By.xpath("//div[@class='modal-content']");

    @Step("Wait for modal window visibility")
    public void waitForModalWindowVisibility() {
        waitForElementVisibility(modalWindow);
    }

    @Step("Click the 'Add New Payoff Charge' button")
    public void clickAddNewPayoffChargeButton() {
        waitForElementClickable(addNewPayoffCharge);
        click(addNewPayoffCharge);
    }

    @Step("Click the 'Done' button")
    public void clickDoneButton() {
        click(doneButton);
    }

    @Step("Type 'Charge description' value")
    public void typeChargeDescription(String text) {
        type(text, chargeDescription);
    }

    @Step("Type 'Charge amount' value")
    public void typeChargeAmount(String amount) {
        type(amount, chargeAmount);
    }

    @Step("Type 'GL Offset' value")
    public void typeGlOffset(String value) {
        type(value, glOffset);
    }

    @Step("Click 'GL Offset' option")
    public void clickGlOffsetOption(String option) {
        click(getGlOffsetOption, option);
    }

    @Step("Click 'Close' option")
    public void clickCloseButton() {
        click(closeButton);
    }

    @Step("Get list of 'GL Offset' options")
    public List<String> getGlOffsetList() {
        return getElementsText(getGlOffsetList);
    }
}
