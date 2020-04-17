package com.nymbus.pages.teller;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class TellerPage extends PageTools {

    private By commitButton = By.xpath("//button[text()='Commit Transaction']");
    private By modalWindow = By.xpath("//div[@class='modal-content']");
    private By cashDrawerName = By.xpath("//div[@name='cashDrawerTemplate']/a/span/span");
    private By effectiveDate = By.xpath("//*[@id='']");

    @Step("Wait 'Proof Date Login' modal window")
    public void waitModalWindow() {
        waitForElementVisibility(modalWindow);
        waitForElementClickable(modalWindow);
    }

    @Step("Get 'Cash Drawer' name")
    public String getCashDrawerName() {
        waitForElementVisibility(cashDrawerName);
        return getElementText(cashDrawerName).trim();
    }

    @Step("Click on 'Commit Transaction' button")
    public void clickCommitButton() {
        waitForElementClickable(commitButton);
        click(commitButton);
    }

    @Step("Set effective date")
    public void setEffectiveDate(String date) {
        waitForElementClickable(effectiveDate);
        type(date, effectiveDate);
    }
    /**
     * Success transaction Modal dialog region
     */
    private By successModalWindow = By.xpath("//*[@class='modal-dialog']");
    private By modalHeaderText = By.xpath("//*[@class='modal-dialog']//h4//span");
    private By closeModalButton = By.xpath("(//*[@class='modal-dialog']//button[@ng-click='close()'])[1]");

    @Step("Get modal header text")
    public String getModalText() {
        waitForElementVisibility(modalHeaderText);
        return getElementText(modalHeaderText).trim();
    }

    @Step("Close modal window")
    public void closeModal() {
        waitForElementVisibility(closeModalButton);
        click(closeModalButton);
    }

    /**
     * Loading spinner
     */
    private By loadingSpinner = By.xpath("//dn-loading-spinner");

    @Step("Wait for loading spinner visibility")
    public void waitForLoadingSpinnerVisibility() {
        waitForElementVisibility(loadingSpinner);
    }

    @Step("Wait for loading spinner invisibility")
    public void waitForLoadingSpinnerInvisibility() {
        waitForElementInvisibility(loadingSpinner);
    }

    /**
     * Notifications region
     */
    private By notificationWithText = By.xpath("//div[@class='toast-message' and contains(text(), '%s')]");

    @Step("Is message with text {0} present")
    public boolean isMessageWithTextPresent(String text) {
        return isElementVisible(notificationWithText, text);
    }

    /**
     * Sources region
     */
    private By sourcePanel = By.id("tellerPanelHeaderSource");
    private By cashInButton = By.xpath("(//*[@id='tellerPanelHeaderSource']//button)[1]");
    private By glDebitButton = By.xpath("(//*[@id='tellerPanelHeaderSource']//button)[2]");
    private By miscDebit = By.xpath("(//*[@id='tellerPanelHeaderSource']//button)[3]");
    private By withDrawAlButton = By.xpath("(//*[@id='tellerPanelHeaderSource']//button)[4]");
    private By unverifiedButton = By.xpath("(//*[@id='tellerPanelHeaderSource']//button)[5]");
    private By checkButton = By.xpath("(//*[@id='tellerPanelHeaderSource']//button)[6]");

    private By accountNumberDiv = By.xpath("(//*[@id='accordion-operation-sources-content']//*[@transaction='item'])[%s]//*[@data-name='accountNumber account']");
    private By accountNumberField = By.xpath("(//*[@id='accordion-operation-sources-content']//*[@transaction='item'])[%s]" +
            "//*[@data-name='accountNumber account']//input[contains(@class, 'ui-select-search')]");

    private By transactionCodeDiv = By.xpath("(//*[@id='accordion-operation-sources-content']//*[@transaction='item'])[%s]//*[@data-name='tranCode']");
    private By transactionCodeField = By.xpath("((//*[@id='accordion-operation-sources-content']//*[@transaction='item'])[%s]" +
            "//*[@data-name='tranCode']//input[contains(@class, 'ui-select-search')])[1]");

    private By transactionAmountDiv = By.xpath("(//*[@id='accordion-operation-sources-content']//*[@transaction='item'])[%s]//*[@data-name='amount']");
    private By transactionAmountField = By.xpath("(//*[@id='accordion-operation-sources-content']//*[@transaction='item'])[%s]" +
            "//*[@data-name='amount']//input");

    private By autocompleteItemInDropDown = By.xpath("//div[@ng-show='$select.openAutocomplete && $select.open']//span[contains(text(), '%s')]");

    private By transactionSourceDetailsArrow = By.xpath("(//*[@id='accordion-operation-sources-content']//*[@transaction='item'])[%s]" +
            "//a[contains(@class, 'detail-icon')]");

    private By transactionSourceNotesInput = By.xpath("(//*[@id='accordion-operation-sources-content']//*[@transaction='item'])[%s]//input[@ng-model='transaction.notes']");

    @Step("Click 'GL Debit' button")
    public void clickGLDebitButton() {
        waitForElementClickable(glDebitButton);
        click(glDebitButton);
    }

    @Step("Click {0} 'Account number' division")
    public void clickAccountNumberDiv(int i) {
        waitForElementClickable(accountNumberDiv, i);
        click(accountNumberDiv, i);
    }

    @Step("Set {0} 'Account number' value {1}")
    public void typeAccountNumber(int i, String number) {
        waitForElementClickable(accountNumberField, i);
        type(number, accountNumberField, i);
    }

    @Step("Click on {0} item in dropdown")
    public void clickOnAutocompleteDropDownItem(String item) {
        waitForElementClickable(autocompleteItemInDropDown, item);
        click(autocompleteItemInDropDown, item);
    }

    @Step("Click 'Amount' {0} division")
    public void clickAmountDiv(int i) {
        waitForElementClickable(transactionAmountDiv, i);
        click(transactionAmountDiv, i);
    }

    @Step("Set {0} 'Amount' value {1}")
    public void typeAmountValue(int i, String amount) {
        waitForElementClickable(transactionAmountField, i);
        type(amount, transactionAmountField, i);
    }

    @Step("Click transition {0} 'Details' arrow")
    public void clickSourceDetailsArrow(int i) {
        waitForElementClickable(transactionSourceDetailsArrow, i);
        click(transactionSourceDetailsArrow, i);
    }

    @Step("Set 'Notes' {0} value {1}")
    public void typeSourceNotesValue(int i, String note) {
        waitForElementClickable(transactionSourceNotesInput, i);
        type(note, transactionSourceNotesInput, i);
    }

    /**
     * Destinations region
     */
    private By destinationsPanel = By.id("tellerPanelHeaderDestination");
    private By cashOutButton = By.xpath("(//*[@id='tellerPanelHeaderDestination']//button)[1]");
    private By depositButton = By.xpath("(//*[@id='tellerPanelHeaderDestination']//button)[2]");
    private By glCreditButton = By.xpath("(//*[@id='tellerPanelHeaderDestination']//button)[3]");
    private By miscCreditButton = By.xpath("(//*[@id='tellerPanelHeaderDestination']//button)[4]");
    private By loanPaymentsButton = By.xpath("(//*[@id='tellerPanelHeaderDestination']//button)[5]");

    private By accountNumberDestinationInput = By.xpath("(//*[@id='accordion-operation-destinations-content']//*[@transaction='item'])[%s]" +
            "//*[@data-name='accountNumber account']//input[contains(@class, 'ui-select-search')]");
    private By transactionDestinationCodeField = By.xpath("(//*[@id='accordion-operation-destinations-content']//*[@transaction='item'])[%s]//*[@data-name='tranCode']");
    private By transactionDestinationAmountDiv = By.xpath("(//*[@id='accordion-operation-destinations-content']//*[@transaction='item'])[%s]//*[@data-name='amount']");
    private By transactionDestinationAmountField = By.xpath("(//*[@id='accordion-operation-destinations-content']//*[@transaction='item'])[%s]" +
            "//*[@data-name='amount']//input");
    private By itemInDropDown = By.xpath("//div[contains(@class, 'select2-drop-active') and not(contains(@class, 'select2-display-none'))]" +
            "//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");


    @Step("Click 'Misc Credit' button")
    public void clickMiscCreditButton() {
        waitForElementClickable(miscCreditButton);
        click(miscCreditButton);
    }

    @Step("Set {0} destination 'Account number' value {1}")
    public void typeDestinationAccountNumber(int i, String number) {
        waitForElementClickable(accountNumberDestinationInput, i);
        type(number, accountNumberDestinationInput, i);
    }

    @Step("Click Destination 'Amount' {0} division")
    public void clickDestinationAmountDiv(int i) {
        waitForElementClickable(transactionDestinationAmountDiv, i);
        click(transactionDestinationAmountDiv, i);
    }

    @Step("Set Destination {0} 'Amount' value {1}")
    public void typeDestinationAmountValue(int i, String amount) {
        waitForElementClickable(transactionDestinationAmountField, i);
        type(amount, transactionDestinationAmountField, i);
    }

    @Step ("Click on destination code {0}")
    public void clickOnDestinationCodeField(int i) {
        waitForElementClickable(transactionDestinationCodeField, i);
        click(transactionDestinationCodeField, i);
    }

    @Step("Click on {0} item in dropdown")
    public void clickOnDropDownItem(String item) {
        waitForElementClickable(itemInDropDown, item);
        click(itemInDropDown, item);
    }
}