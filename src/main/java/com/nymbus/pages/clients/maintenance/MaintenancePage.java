package com.nymbus.pages.clients.maintenance;

import com.nymbus.base.BasePage;
import com.nymbus.locator.ID;
import com.nymbus.locator.Locator;
import com.nymbus.locator.XPath;
import io.qameta.allure.Step;

public class MaintenancePage extends BasePage {
    /**
     * Cards Management
     */
    private Locator newDebitCard = new XPath("//button[@data-test-id='action-newDebitCard']");
    private Locator binNumberArrowIcon = new XPath("//div[@id='binnumber']//b");
    private Locator binNumberDropdownValue = new XPath("//div/span[text()='%s']");
    private Locator descriptionInputField = new ID("description");
    private Locator nextButton = new XPath("//button[span[text()='Next']]");

    /**
     * Cards Management
     */
    @Step("Click on 'New Debit Card' button")
    public void clickOnNewDebitCardButton() {
        waitForElementVisibility(newDebitCard);
        waitForElementClickable(newDebitCard);
        click(newDebitCard);
    }

    @Step("Click on 'Bin Number' input field")
    public void clickOnBinNumberInputField() {
        waitForElementVisibility(binNumberArrowIcon);
        waitForElementClickable(binNumberArrowIcon);
        click(binNumberArrowIcon);
    }

    @Step("Click on 'Bin Number' drop down value '{binNumber}'")
    public void clickOnBinNumberDropdownValue(String binNumber) {
        waitForElementVisibility(binNumberDropdownValue, binNumber);
        waitForElementClickable(binNumberDropdownValue, binNumber);
        click(binNumberDropdownValue, binNumber);
    }

    @Step("Type '{binNumber}' to 'Bin Number' input field")
    public void typeToBinNumberInputField(String binNumber) {
        waitForElementVisibility(binNumberArrowIcon);
        waitForElementClickable(binNumberArrowIcon);
        type(binNumber, binNumberArrowIcon);
    }

    @Step("Type '{description}' to 'Description' input field")
    public void typeToDescriptionInputField(String description) {
        waitForElementVisibility(descriptionInputField);
        waitForElementClickable(descriptionInputField);
        type(description, descriptionInputField);
    }

    public String getDescriptionInputFieldValue() {
        waitForElementVisibility(descriptionInputField);
        return getElementAttributeValue("value", descriptionInputField);
    }

    @Step("Click on 'Next' button")
    public void clickOnNextButton() {
        waitForElementVisibility(nextButton);
        waitForElementClickable(nextButton);
        click(nextButton);
    }
}
