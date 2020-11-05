package com.nymbus.pages.accounts;

import com.nymbus.core.base.PageTools;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class AccountInstructionsPage extends PageTools {

    private By instructionAlertByContent = By.xpath("//div[contains(@class, 'notifications-item')]//div/span[contains(text(), '%s')]");
    private By viewExpiredAndDeletedHolds = By.xpath("//*[@id='tab-ca-instructions']//span[text()='View Expired and Deleted Holds']");
    private By loadingSpinner = By.xpath("//*[name()='svg' and contains(@class, 'fixed-centred-spinner')]");

    /**
     * Deleted and expired holds modal window region
     */

    private By deletedHoldHeader = By.xpath("//div[@class='modal-content']//h4");
    private By closeModalButton = By.xpath("//div[@class='modal-content']//button[@ng-click='closePopup()']");
    private By rowsCount = By.xpath("//div[@class='modal-content']//tbody//tr");
    private By deletedHoldAmount = By.xpath("//div[@class='modal-content']//tbody//tr[%s]//td[2]//span");
    private By deletedHoldTableheader = By.xpath("//div[@class='modal-content']//table//thead");

    /**
     * Instruction form region
     */

    private By newInstructionButton = By.xpath("//*[@id='tab-ca-instructions']//span[text()='New Instruction']");
    private By editInstructionButton = By.xpath("//*[@class = 'actions']//button[1]");
    private By deleteInstructionButton = By.xpath("//*[@class = 'actions']//button[2]");
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

    /**
     * Instruction details region
     */
    private By reasonText = By.xpath("//article[contains(@class, 'itemDetail')]//div[@ng-if='actualConfig.reason.isShow']//div//span");
    private By amount = By.xpath("//article[contains(@class, 'itemDetail')]//div[@ng-if='actualConfig.amount.isShow']//span[@ng-if='isNeedCurrency']");
    private By notes = By.xpath("//p[@class='ng-binding']");

    @Step("Wait for loading spinner invisibility")
    public void waitForLoadingSpinnerInvisibility() {
        waitForElementInvisibility(loadingSpinner);
    }

    @Step("wait for modal window invisibility")
    public void waitForModalWindowInvisibility() {
       waitForElementInvisibility(deletedHoldHeader);
    }

    @Step("Get amount value from modal")
    public String getAmountValueByIndex(int index) {
        waitForElementVisibility(deletedHoldAmount, index);
        return getElementText(deletedHoldAmount, index).trim().replaceAll("[^0-9.]", "");
    }

    @Step("Get rows count")
    public int getRowsCount() {
        waitForElementVisibility(deletedHoldTableheader);
        return getElementsWithZeroOptionWithWait(2, rowsCount).size();
    }

    @Step("Click close modal button")
    public void clickCloseModalButton() {
        waitForElementVisibility(closeModalButton);
        waitForElementClickable(closeModalButton);
        click(closeModalButton);
    }

    @Step("Click 'View Expired and Deleted Holds' button")
    public void clickViewExpiredAndDeletedHold() {
        waitForElementVisibility(viewExpiredAndDeletedHolds);
        waitForElementClickable(viewExpiredAndDeletedHolds);
        click(viewExpiredAndDeletedHolds);
    }

    @Step("Get hold amount")
    public String getHoldAmount() {
        waitForElementVisibility(amount);
        String currentBalanceValue = getElementText(amount).trim();
        return currentBalanceValue.replaceAll("[^0-9.]", "");
    }

    @Step("Get notes")
    public String getNotes() {
        waitForElementVisibility(notes);
        return getElementText(notes).trim();
    }

    @Step("Wait for alert for created instruction appeared")
    public void waitForAlertVisible(String instructionContent) {
        waitForElementVisibility(instructionAlertByContent, instructionContent);
    }

    @Step("Check that alert for created instruction appeared")
    public boolean isInstructionAlertAppeared(String instructionContent) { // format (Account | {account number} | note)
        return isElementVisible(instructionAlertByContent, instructionContent);
    }

    @Step("Wait for Edit button visibility")
    public void waitForEditButtonVisibility() {
        waitForElementVisibility(editInstructionButton);
    }

    @Step("Get reason text")
    public String getReasonText() {
        waitForElementVisibility(reasonText);
        return getElementText(reasonText).trim();
    }

    @Step("Click 'New Instruction' button")
    public void clickNewInstructionButton() {
        waitForElementVisibility(newInstructionButton);
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

    @Step("Add text to 'Notes' field")
    public void typeNotesValueWithoutWipe(String notes) {
        waitForElementClickable(notesInput);
        typeWithoutWipe(notes, notesInput);
    }

    @Step("Click 'Save' button")
    public void clickSaveButton() {
        waitForElementClickable(saveButton);
        click(saveButton);
    }

    @Step("Click 'Edit' button")
    public void clickEditButton() {
        waitForElementClickable(editInstructionButton);
        click(editInstructionButton);
    }

    @Step("Click 'Delete' button")
    public void clickDeleteButton() {
        waitForElementClickable(deleteInstructionButton);
        click(deleteInstructionButton);
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

    @Step("Wait for deleted instruction list item {0} invisibility")
    public void waitForDeletedInstructionInvisibility(int i) {
        waitForElementInvisibility(createdInstructionListItem, i);
    }

    @Step("Click instruction in list by index")
    public void clickInstructionInListByIndex(int index) {
        waitForElementVisibility(createdInstructionListItem, index);
        click(createdInstructionListItem, index);
    }

    @Step("Get created instructions count")
    public int getCreatedInstructionsCount() {
        SelenideTools.sleep(Constants.MINI_TIMEOUT);
        return getElementsWithZeroOption(createdInstructionList).size();
    }

    @Step("Is instructions list visible")
    public boolean isInstructionsListVisible() {
        return isElementVisible(createdInstructionList);
    }

    @Step("Wait for save button invisibility")
    public void waitForSaveButtonInvisibility() {
        waitForElementInvisibility(saveButton);
    }

    @Step("Wait for delete button invisibility")
    public void waitForDeleteButtonInvisibility() {
        waitForElementInvisibility(deleteInstructionButton);
    }
}