package com.nymbus.pages.accounts;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class AccountInstructionsPage extends PageTools {

    /**
     * Instruction form region
     */
    private By newInstructionButton = By.xpath("//*[@id='tab-ca-instructions']//span[text()='New Instruction']");
    private By instructionDropdownSelectButton = By.xpath("//*[@id='recordcode']//span[contains(@class, 'select2-arrow')]");
    private By itemInDropDown = By.xpath("//div[contains(@class, 'select2-drop-active') and not(contains(@class, 'select2-display-none'))]" +
            "//li[contains(@role, 'option')]/div/span[text()='%s']");
    private By amountInput = By.id("amount");
    private By expirationDateInput = By.id("expirationdate");
    private By notesInput = By.id("notes");
    private By saveButton = By.xpath("//button[text()='Save']");

    /**
     *  List with created instructions region
     */
    private By createdInstructionList = By.xpath("//ul[contains(@class, 'instructionsList')]/li");
    private By createdInstructionListItem = By.xpath("//ul[contains(@class, 'instructionsList')]/li[%s]");
    private By instructionInListHeaderDiv = By.xpath("//ul[contains(@class, 'instructionsList')]/li[%s]//div[@class='itemHeader']/div");
    private By instructionInListCreatedDate = By.xpath("//ul[contains(@class, 'instructionsList')]/li[%s]//div[@ng-if='instruction.dateentered']");
    private By instructionInListExpirationDate = By.xpath("//ul[contains(@class, 'instructionsList')]/li[%s]//div[@ng-if='instruction.expirationdate']");


    @Step("Click 'New Instruction' button")
    public void clickNewInstructionButton() {
        waitForElementClickable(newInstructionButton);
        click(newInstructionButton);
    }

    @Step("Click Instruction DropDown selector arrow")
    public void clickInstructionDropdownSelectButton() {
        waitForElementClickable(instructionDropdownSelectButton);
        click(instructionDropdownSelectButton);
    }

    @Step("Click on {0} item in dropdown")
    public void clickDropDownItem(String item) {
        waitForElementClickable(itemInDropDown, item);
        click(itemInDropDown, item);
    }

    @Step("Set 'Amount' value {0}")
    public void typeAmountValue(String amount) {
        waitForElementClickable(amountInput);
        type(amount, amountInput);
    }

    @Step("Set 'Expiration Date' value {0}")
    public void typeExpirationDateValue(String expirationDate) {
        waitForElementClickable(expirationDateInput);
        type(expirationDate, expirationDateInput);
    }

    @Step("Set 'Notes' value {0}")
    public void typeNotesValue(String notes) {
        waitForElementClickable(notesInput);
        type(notes, notesInput);
    }

    @Step("Click 'Save' button")
    public void clickSaveButton() {
        waitForElementClickable(saveButton);
        click(saveButton);
    }

    @Step("Get {0} instruction type value")
    public String getCreatedInstructionType(int i) {
        waitForElementVisibility(instructionInListHeaderDiv, i);
        return getElementText(instructionInListHeaderDiv, i);
    }

    @Step("Get {0} instruction created date value")
    public String getCreatedInstructionCreationDate(int i) {
        waitForElementVisibility(instructionInListCreatedDate, i);
        return getElementAttributeValue("lastChild", instructionInListCreatedDate, i);
    }

    @Step("Get {0} instruction expiration date value")
    public String getCreatedInstructionExpirationDate(int i) {
        waitForElementVisibility(instructionInListExpirationDate, i);
        return getElementText(instructionInListExpirationDate, i).split(" ")[1];
    }

    @Step("Wait for created instruction list item {0} to be visible")
    public void waitForCreatedInstruction(int i) {
        waitForElementVisibility(createdInstructionListItem, i);
    }

    @Step("Get created instructions count")
    public int getCreatedInstructionsCount() {
        waitForElementVisibility(createdInstructionList);
        return getElements(createdInstructionList).size();
    }

    @Step("Is instructions list visible")
    public boolean isInstructionsListVisible() {
        return isElementVisible(createdInstructionList);
    }
}