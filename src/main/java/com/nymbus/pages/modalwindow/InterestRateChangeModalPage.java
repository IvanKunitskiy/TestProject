package com.nymbus.pages.modalwindow;

import com.nymbus.core.base.PageTools;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class InterestRateChangeModalPage extends PageTools {

    private final By modalWindow = By.xpath("//div[@class='modal-content']");
    private final By newCurrentEffectiveRate = By.xpath("//input[@data-test-id='field-new_currenteffectiverate']");
    private final By beginEarnDate = By.xpath("//input[@data-test-id='field-datethisratechange']");
    private final By accrueThruDate = By.xpath("//input[@data-test-id='field-dateaccruethru']");
    private final By commitTransactionButton = By.xpath("//button/span[text()='Commit Transaction']");
    private final By cancelButton = By.xpath("//button/span[text()='Cancel']");

    @Step("Set 'Begin earn date' value")
    public void setBeginEarnDate(String date) {
        click(beginEarnDate);
        SelenideTools.sleep(Constants.MICRO_TIMEOUT);
        typeWithoutWipe(date, beginEarnDate);
    }

    @Step("Set 'Accrue thru date' value")
    public void setAccrueThruDate(String date) {
        click(accrueThruDate);
        SelenideTools.sleep(Constants.MICRO_TIMEOUT);
        typeWithoutWipe(date, accrueThruDate);
    }

    @Step("Set 'NEW Current Effective Rate' amount")
    public void setNewCurrentEffectiveRateValue(String value) {
        waitForElementClickable(newCurrentEffectiveRate);
        type(value, newCurrentEffectiveRate);
    }

    @Step("Wait for modal window visibility")
    public void waitForModalWindowVisibility() {
        waitForElementVisibility(modalWindow);
    }

    @Step("Wait for modal window visibility")
    public void waitForModalWindowInVisibility() {
        waitForElementInvisibility(modalWindow);
    }

    @Step("Click 'Commit transaction' button")
    public void clickCommitTransactionButton() {
        waitForElementVisibility(commitTransactionButton);
        jsClick(commitTransactionButton);
    }

    @Step("Click 'Cancel' button")
    public void clickCancelButton() {
        waitForElementVisibility(cancelButton);
        jsClick(cancelButton);
    }

}
