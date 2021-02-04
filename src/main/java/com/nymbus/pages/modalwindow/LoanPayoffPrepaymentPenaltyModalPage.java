package com.nymbus.pages.modalwindow;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class LoanPayoffPrepaymentPenaltyModalPage extends PageTools {
    private final By effectiveDate = By.xpath("//input[@data-test-id='field-effectivedate']");
    private final By expireDate = By.xpath("//input[@data-test-id='field-expirationdate']");
    private final By fixedPercentage = By.xpath("//input[@data-test-id='field-fixedpercentage']");
    private final By prepaymentPenaltyAmount = By.xpath("//div[text()='Prepayment Penalty Amount']/following-sibling::div/span");

    private final By balanceUsedSelectorButton = By.xpath("//div[@data-test-id='field-balanceused']/a");
    private final By balanceUsedSelectorOption = By.xpath("//ul/li/div/span[text()='%s']");
    private final By balanceUsedList = By.xpath("//ul/li/div/span");

    private final By penaltyCalculationTypeSelectorButton = By.xpath("//div[@data-test-id='field-calculationtype']/a");
    private final By penaltyCalculationTypeSelectorOption = By.xpath("//ul/li/div/span[text()='%s']");
    private final By penaltyCalculationTypeList = By.xpath("//ul/li/div/span");

    @Step("Get 'Prepayment Penalty Amount' value")
    public String getPrepaymentPenaltyAmount() {
        return getElementText(prepaymentPenaltyAmount).replaceAll("[^0-9.]", "");
    }

    @Step("Type 'Effective date' value")
    public void typeEffectiveDate(String date) {
        waitForElementClickable(effectiveDate);
        type(date, effectiveDate);
    }

    @Step("Type 'Expire Date' value")
    public void typeExpireDate(String date) {
        waitForElementClickable(expireDate);
        type(date, expireDate);
    }

    @Step("Type 'Fixed percentage' value")
    public void typeFixedPercentage(String percentage) {
        waitForElementClickable(fixedPercentage);
        type(percentage, fixedPercentage);
    }

    @Step("Click 'Balance Used' selector button")
    public void clickBalanceUsedSelectorButton() {
        waitForElementClickable(balanceUsedSelectorButton);
        click(balanceUsedSelectorButton);
    }

    @Step("Click 'Balance Used' selector option")
    public void clickBalanceUsedSelectorOption(String option) {
        waitForElementClickable(balanceUsedSelectorOption, option);
        click(balanceUsedSelectorOption, option);
    }

    @Step("Click 'Penalty Calculation Type' selector button")
    public void clickPenaltyCalculationTypeSelectorButton() {
        waitForElementClickable(penaltyCalculationTypeSelectorButton);
        click(penaltyCalculationTypeSelectorButton);
    }

    @Step("Click 'Penalty Calculation Type' selector option")
    public void clickPenaltyCalculationTypeSelectorOption(String option) {
        waitForElementClickable(penaltyCalculationTypeSelectorOption, option);
        click(penaltyCalculationTypeSelectorOption, option);
    }
}
