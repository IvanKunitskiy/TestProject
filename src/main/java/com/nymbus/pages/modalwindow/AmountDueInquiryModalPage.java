package com.nymbus.pages.modalwindow;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class AmountDueInquiryModalPage extends PageTools {

    private By modalWindow = By.xpath("//div[@class='modal-dialog']");

    private By payoffAmount = By.xpath("//tr/td[text()='Payoff Amount']/following-sibling::td//span[@text='value']/span");
    private By payoffCharges = By.xpath("//tr/td[text()='Payoff Charges']/following-sibling::td//span[@text='value']/span");
    private By closeButton = By.xpath("//button[@data-test-id='action-Close']");

    @Step("Wait for modal window visibility")
    public void waitForModalWindowVisibility() {
        waitForElementVisibility(modalWindow);
    }

    @Step("Wait for modal window visibility")
    public void waitForModalWindowInVisibility() {
        waitForElementInvisibility(modalWindow);
    }

    @Step("Get 'Payoff Amount' value")
    public String getPayoffAmount() {
        waitForElementVisibility(payoffAmount);
        return getElementText(payoffAmount).replaceAll("[^0-9.]", "");
    }

    @Step("Get 'Payoff Charges' value")
    public String getPayoffCharges() {
        waitForElementVisibility(payoffCharges);
        return getElementText(payoffCharges).replaceAll("[^0-9.]", "");
    }

    @Step("Click 'Close' button")
    public void clickCloseButton() {
        waitForElementVisibility(closeButton);
        click(closeButton);
    }
}
