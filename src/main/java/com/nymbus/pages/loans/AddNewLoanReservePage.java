package com.nymbus.pages.loans;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.util.List;

public class AddNewLoanReservePage extends PageTools {

    private final By reservePremiumAmortizationCodeField = By.xpath("//input[@name='field[(databean)code]']");
    private final By saveChangesButton = By.xpath("//button[span[text()='Save Changes']]");

    private final By balanceDefinitionSelectorButton = By.xpath("//div[@data-field-id='balancedef']//div/input[1]");
    private final By balanceDefinitionList = By.xpath("//li[contains(@class, 'ui-menu-item')]/a");
    private final By balanceDefinitionSelectorOption = By.xpath("//li[contains(@class, 'ui-menu-item')]/a[contains(text(), '%s')]");

    @Step("Click 'Save Changes' button")
    public void clickSaveChangesButton() {
        waitForElementClickable(saveChangesButton);
        click(saveChangesButton);
    }

    @Step("Type value to 'Reserve Premium Amortization Code' field")
    public void typeReservePremiumAmortizationCodeField(String value) {
        waitForElementClickable(reservePremiumAmortizationCodeField, value);
        type(value, reservePremiumAmortizationCodeField);
    }

    @Step("Click the 'Balance Definition' option")
    public void clickBalanceDefinitionSelectorOption(String currentOfficerOption) {
        waitForElementVisibility(balanceDefinitionSelectorOption, currentOfficerOption);
        waitForElementClickable(balanceDefinitionSelectorOption, currentOfficerOption);
        click(balanceDefinitionSelectorOption, currentOfficerOption);
    }

    @Step("Returning list of 'Balance Definition' options")
    public List<String> getBalanceDefinitionList() {
        waitForElementVisibility(balanceDefinitionList);
        waitForElementClickable(balanceDefinitionList);
        return getElementsText(balanceDefinitionList);
    }

    @Step("Click the 'Balance Definition' selector button")
    public void clickBalanceDefinitionSelectorButton() {
        waitForElementVisibility(balanceDefinitionSelectorButton);
        scrollToPlaceElementInCenter(balanceDefinitionSelectorButton);
        waitForElementClickable(balanceDefinitionSelectorButton);
        click(balanceDefinitionSelectorButton);
    }

}
