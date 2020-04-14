package com.nymbus.pages.clients.transfers;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class TransfersPage extends PageTools {

    private By newTransferButton = By.xpath("//button[span[contains(text(), 'New Transfer')]]");
    private By transferSelector = By.xpath("//div[div[span[span[contains(text(), '%s')]]]]");

    @Step("Click the 'New Transfer' button")
    public void clickNewTransferButton() {
        waitForElementClickable(newTransferButton);
        click(newTransferButton);
    }

    @Step("Click the transfer in list by its name")
    public void clickTransferInTheListByType(String transferName) {
        waitForElementVisibility(transferSelector, transferName);
        waitForElementClickable(transferSelector, transferName);
        click(transferSelector, transferName);
    }

}