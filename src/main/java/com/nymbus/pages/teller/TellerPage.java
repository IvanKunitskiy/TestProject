package com.nymbus.pages.teller;

import com.codeborne.selenide.Condition;
import com.nymbus.core.base.PageTools;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class TellerPage extends PageTools {

    private By commitButton = By.xpath("//button[text()='Commit Transaction']");
    private By modalWindow = By.xpath("//div[@class='modal-content']");
    private By cashDrawerName = By.xpath("//div[@name='cashDrawerTemplate']/a/span/span");
    private By effectiveDate = By.xpath("//input[@data-test-id='field-']");
    private By drawerNameInFooter = By.xpath("//footer//span[contains(text(), 'Drawer Name')]/span");
    private By transactionSection = By.xpath("//section[@ui-view='transaction']");
    private By tellerOperation =By.xpath("//span[contains(string(),'Select Operation')]");
    private By operationSelector = By.xpath("//span[contains(string(),'%s')]");
    private By errorMessage = By.xpath("//div[contains(@class, 'toast-error')]");

    @Step("Check error message")
    public boolean errorMessagesIsVisible(){
        SelenideTools.sleep(Constants.MICRO_TIMEOUT);
        return isElementVisible(errorMessage);
    }

    @Step("Wait for transaction section visibility")
    public void waitForTransactionSectionVisibility() {
        waitForElementVisibility(transactionSection);
    }

    @Step("Wait 'Proof Date Login' modal window")
    public void waitModalWindow() {
        waitForElementVisibility(modalWindow);
        waitForElementClickable(modalWindow);
    }

    @Step("Get 'Cash Drawer' name")
    public String getCashDrawerNameInFooter() {
        waitForElementVisibility(drawerNameInFooter);
        return getElementText(drawerNameInFooter).trim();
    }

    @Step("Get 'Cash Drawer' name")
    public String getCashDrawerName() {
        waitForElementVisibility(cashDrawerName);
        return getElementText(cashDrawerName).trim();
    }

    @Step("Click on 'Commit Transaction' button")
    public void clickCommitButton() {
        waitForElementClickable(commitButton);
        jsClick(commitButton);
    }

    @Step("Set effective date")
    public void setEffectiveDate(String date) {
        waitForElementVisibility(effectiveDate);
        jsType(date, effectiveDate);
        jsRiseOnchange(effectiveDate);
    }

    @Step("Get effective date")
    public String getEffectiveDate() {
        waitForElementClickable(effectiveDate);
        return getElementAttributeValue("value", effectiveDate);
    }

    @Step("Click 'Select Operation' popup")
    public void clickSelectOperation(){
        waitForElementVisibility(tellerOperation);
        jsClick(tellerOperation);
    }

    @Step("Select operation")
    public void selectOperation(String type){
        waitForElementVisibility(operationSelector,type);
        jsClick(operationSelector, type);
    }

    /**
     * Success transaction Modal dialog region
     */
    private By successModalWindow = By.xpath("//*[@class='modal-dialog']");
    private By modalHeaderText = By.xpath("//*[@class='modal-dialog']//h4//span");
    private By closeModalButton = By.xpath("(//*[@class='modal-dialog']//button[@ng-click='close()'])[1]");
    private By printReceipt = By.xpath("//*[@id='printReceipt']");
    private By popupLoadingSpinner = By.xpath("//*[@id='printReceipt']/dn-loading-spinner");
    private By popupImg = By.xpath("//*[@id='printReceipt']//img[@id='receiptTemplate'][contains(@src, 'base64')]");

    @Step("Get modal header text")
    public String getModalText() {
        waitForElementVisibility(modalHeaderText);
        return getElementText(modalHeaderText).trim();
    }

    @Step("Close modal window")
    public void closeModal() {
        waitForElementVisibility(closeModalButton);
        jsClick(closeModalButton);
    }

    @Step("Wait for print receipt region")
    public void waitForPrintReceipt() {
        waitForElementVisibility(printReceipt);
    }

    @Step("Wait for popup spinner invisibility")
    public void waitForPopupSpinnerInvisibility() {
        waitForElementInvisibility(popupLoadingSpinner);
    }

    @Step("Get 'src' attribute of popup img")
    public String getPopupImg() {
        waitForElementVisibility(popupImg);
        return getElementAttributeValue("src", popupImg);
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
    private By noteAlert = By.xpath("//section[contains(@class, 'alerts-section')]/div/div/span[contains(text(), '%s')]");
    private By notifications = By.xpath("//div[@class='toast-message']");

    @Step("Is any notifications present")
    public boolean isNotificationsPresent() {
        SelenideTools.sleep(Constants.MICRO_TIMEOUT);
        return isCondition(Condition.appears, notifications);
    }

    @Step("Is message with text {0} present")
    public boolean isMessageWithTextPresent(String text) {
        return isElementVisible(notificationWithText, text);
    }

    @Step("Is alert with text visible")
    public boolean isAlertWithTextVisible(String text) {
        waitForElementVisibility(noteAlert, text);
        return isElementVisible(noteAlert, text);
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
    private By checkAccountNumberField = By.xpath("(//*[@id='accordion-operation-sources-content']//*[@transaction='item'])[%s]//*[@data-name='accountNumber account']/input");
    private By accountNumberInput = By.xpath("(//input[@type='search'])[2]");
    private By accountNumberField = By.xpath("(//*[@id='accordion-operation-sources-content']//*[@transaction='item'])[%s]" +
            "//*[@data-name='accountNumber account']//input[contains(@class, 'ui-select-search')]");

    private By routingNumberDiv = By.xpath("(//*[@id='accordion-operation-sources-content']//*[@transaction='item'])[%s]//*[@data-name='routingTransitNumber']");
    private By routingNumberInput = By.xpath("(//*[@id='accordion-operation-sources-content']//*[@transaction='item'])[%s]//*[@data-name='routingTransitNumber']/input");

    private By transactionCodeDropdownArrow = By.xpath("(//*[@id='accordion-operation-sources-content']//*[@transaction='item'])[%s]" +
            "//*[@data-name='tranCode']//span[contains(@class, 'select2-arrow')]");
    private By transactionCodeField = By.xpath("((//*[@id='accordion-operation-sources-content']//*[@transaction='item'])[%s]" +
            "//*[@data-name='tranCode']//input[contains(@class, 'ui-select-search')])[1]");
    private By transactionCodeDiv = By.xpath("(//*[@id='accordion-operation-sources-content']//*[@transaction='item'])[%s]" +
            "//*[@data-name='tranCode']/div");

    private By checkNumberDiv = By.xpath("(//*[@id='accordion-operation-sources-content']//*[@transaction='item'])[%s]//*[@data-name='checkNumber']");
    private By checkNumberInput = By.xpath("(//*[@id='accordion-operation-sources-content']//*[@transaction='item'])[%s]//*[@data-name='checkNumber']/input");

    private By transactionAmountDiv = By.xpath("(//*[@id='accordion-operation-sources-content']//*[@transaction='item'])[%s]//*[@data-name='amount']");
    private By transactionAmountField = By.xpath("(//*[@id='accordion-operation-sources-content']//*[@transaction='item'])[%s]" +
            "//*[@data-name='amount']//input");

    private By autocompleteItemInDropDown = By.xpath("//div[@ng-show='$select.openAutocomplete && $select.open']//span[contains(text(), '%s')]");

    private By transactionSourceDetailsArrow = By.xpath("(//*[@id='accordion-operation-sources-content']//*[@transaction='item'])[%s]" +
            "//a[contains(@class, 'detail-icon')]");

    private By transactionSourceNotesInput = By.xpath("(//*[@id='accordion-operation-sources-content']//*[@transaction='item'])[%s]//input[@ng-model='transaction.notes']");

    private By creditTransferCodeSelector = By.xpath("//span[contains(text(), '101 - Credit Transfr')]");

    private By deleteTransactionItem = By.xpath("//div[@id='accordion-operation-sources-content']//div[@transaction='item'][%s]//button[@data-test-id='delete-transaction-item']");

    @Step("Click 'Misc-Debit' button")
    public void clickMiscDebitButton() {
        waitForElementClickable(miscDebit);
        jsClick(miscDebit);
    }

    @Step("Click {0} 'Delete transaction' button")
    public void clickDeleteTransactionButton(int index) {
        jsClick(deleteTransactionItem, index);
    }

    @Step("Click 'Withdrawal' button")
    public void clickWithdrawalButton() {
        waitForElementClickable(withDrawAlButton);
        jsClick(withDrawAlButton);
    }

    @Step("Click 'Check' button")
    public void clickCheckButton() {
        waitForElementClickable(checkButton);
        jsClick(checkButton);
    }

    @Step("Wait for 'Credit transfer' code visible")
    public void waitForCreditTransferCodeVisible() {
        waitForElementVisibility(creditTransferCodeSelector);
        waitForElementClickable(creditTransferCodeSelector);
    }

    @Step("Click 'Cash-In' button")
    public void clickCashInButton() {
        waitForElementClickable(cashInButton);
        jsClick(cashInButton);
    }

    @Step("Click 'GL Debit' button")
    public void clickGLDebitButton() {
        waitForElementClickable(glDebitButton);
        jsClick(glDebitButton);
    }

    @Step("Click {0} 'Account number' division")
    public void clickAccountNumberDiv(int i) {
        waitForElementClickable(accountNumberDiv, i);
        jsClick(accountNumberDiv, i);
    }

    @Step("Click {0} 'Routing number' division")
    public void clickRoutingNumberDiv(int i) {
        waitForElementClickable(routingNumberDiv, i);
        jsClick(routingNumberDiv, i);
    }

    @Step("Click {0} 'Check number' division")
    public void clickCheckNumberDiv(int i) {
        waitForElementClickable(checkNumberDiv, i);
        jsClick(checkNumberDiv, i);
    }

    @Step("Set {0} 'Routing number' value {1}")
    public void typeRoutingNumber(int i, String number) {
        waitForElementClickable(routingNumberInput, i);
        jsType(number, routingNumberInput, i);
        jsRiseOnchange(routingNumberInput, i);
    }

    @Step("Set {0} 'Check number' value {1}")
    public void typeCheckNumber(int i, String number) {
        waitForElementClickable(checkNumberInput, i);
        jsType(number, checkNumberInput, i);
        jsRiseOnchange(checkNumberInput, i);
    }

    @Step("Click {0} 'Account number' input")
    public void clickAccountNumberInput(int i) {
        waitForElementClickable(accountNumberInput, i);
        click(accountNumberInput, i);
    }

    @Step("Set {0} 'Account number' value {1}")
    public void typeAccountNumber(int i, String number) {
        waitForElementClickable(accountNumberField, i);
        jsType(number, accountNumberField, i);
        jsRiseOnchange(accountNumberField, i);
    }

    @Step("Set {0} 'Check Account number' value {1}")
    public void typeCheckAccountNumber(int i, String number) {
        waitForElementClickable(checkAccountNumberField, i);
        jsType(number, checkAccountNumberField, i);
        jsRiseOnchange(checkAccountNumberField, i);
    }

    @Step("Click on {0} item in dropdown")
    public void clickOnAutocompleteDropDownItem(String item) {
        waitForElementClickable(autocompleteItemInDropDown, item);
        jsClick(autocompleteItemInDropDown, item);
    }

    @Step("Check on {0} item in dropdown")
    public boolean elementVisibleOnAutocompleteDropDownItem(String item) {
        waitForElementVisibility(autocompleteItemInDropDown, item);
        return isElementClickable(autocompleteItemInDropDown, item);
    }

    @Step("Click 'Amount' {0} division")
    public void clickAmountDiv(int i) {
        waitForElementClickable(transactionAmountDiv, i);
        jsClick(transactionAmountDiv, i);
    }

    @Step("Set {0} 'Amount' value {1}")
    public void typeAmountValue(int i, String amount) {
        waitForElementClickable(transactionAmountField, i);
        jsType(amount, transactionAmountField, i);
        jsRiseOnchange(transactionAmountField, i);
    }

    @Step("Click transition {0} 'Details' arrow")
    public void clickSourceDetailsArrow(int i) {
        waitForElementClickable(transactionSourceDetailsArrow, i);
        jsClick(transactionSourceDetailsArrow, i);
    }

    @Step("Set 'Notes' {0} value {1}")
    public void typeSourceNotesValue(int i, String note) {
        waitForElementClickable(transactionSourceNotesInput, i);
        jsSetValue(note, transactionSourceNotesInput, i);
        jsRiseOnchange(transactionSourceNotesInput, i);

    }

    @Step("Click transition code {0} dropdown arrow")
    public void clickSourceTransactionCodeArrow(int i) {
        waitForElementClickable(transactionCodeDropdownArrow, i);
        jsClick(transactionCodeDropdownArrow, i);
    }

    @Step("Click transition code {0} div")
    public void clickSourceTransactionCode(int i) {
        waitForElementClickable(transactionCodeDiv, i);
        jsClick(transactionCodeDiv, i);
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
    private By transactionDestinationCodeDropdownArrow = By.xpath("(//*[@id='accordion-operation-destinations-content']//*[@transaction='item'])[%s]" +
            "//*[@data-name='tranCode']//span[contains(@class, 'select2-arrow')]");
    private By transactionDestinationAmountDiv = By.xpath("(//*[@id='accordion-operation-destinations-content']//*[@transaction='item'])[%s]//*[@data-name='amount']");
    private By transactionDestinationAmountField = By.xpath("(//*[@id='accordion-operation-destinations-content']//*[@transaction='item'])[%s]" +
            "//*[@data-name='amount']//input");
    private By itemInDropDown = By.xpath("//div[contains(@class, 'select2-drop-active') and not(contains(@class, 'select2-display-none'))]" +
            "//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private By accountNumberOptionSelector = By.xpath("//div[contains(@class, 'ui-select-dropdown')]//div//span[contains(text(), '%s')]");

    private By transactionDestinationDetailsArrow = By.xpath("(//*[@id='accordion-operation-destinations-content']//*[@transaction='item'])[%s]" +
            "//a[contains(@class, 'detail-icon')]");

    private By transactionDestinationNotesInput = By.xpath("(//*[@id='accordion-operation-destinations-content']//*[@transaction='item'])[%s]//input[@ng-model='transaction.notes']");

    private By waiveFeeButton = By.xpath("//dn-switch[contains(string(),'No')]//div//div//span");
    private By bankRoutingInput = By.xpath("(//tr[contains(string(),'Bank Routing')])[2]/*/input");

    @Step("Input 'Bank routing'")
    public void inputBankRouting(String routing){
        waitForElementVisibility(bankRoutingInput);
        jsSetValue(routing, bankRoutingInput);
        jsRiseOnchange(bankRoutingInput);
    }

    @Step("Click 'Waive Fee' popup")
    public void clickWaiveFee(){
        SelenideTools.sleep(Constants.MICRO_TIMEOUT);
        waitForElementVisibility(waiveFeeButton);
        jsClick(waiveFeeButton);
    }

    @Step("Click transition {0} 'Details' arrow")
    public void clickDestinationDetailsArrow(int i) {
        waitForElementClickable(transactionDestinationDetailsArrow, i);
        jsClick(transactionDestinationDetailsArrow, i);
    }

    @Step("Set Destination 'Notes' {0} value {1}")
    public void typeDestinationNotesValue(int i, String note) {
        waitForElementClickable(transactionDestinationNotesInput, i);
        jsSetValue(note, transactionDestinationNotesInput, i);
        jsRiseOnchange(transactionDestinationNotesInput, i);
    }

    @Step("Click 'Deposit' button")
    public void clickDepositButton() {
        waitForElementClickable(depositButton);
        jsClick(depositButton);
    }

    @Step("Click 'GL Credit' button")
    public void clickGLCreditButton() {
        waitForElementClickable(glCreditButton);
        jsClick(glCreditButton);
    }

    @Step("Click 'Cash-Out' button")
    public void clickCashOutButton() {
        waitForElementClickable(cashOutButton);
        jsClick(cashOutButton);
    }

    @Step("Click 'Misc Credit' button")
    public void clickMiscCreditButton() {
        waitForElementClickable(miscCreditButton);
        jsClick(miscCreditButton);
    }

    @Step("Set {0} destination 'Account number' value {1}")
    public void typeDestinationAccountNumber(int i, String number) {
        waitForElementClickable(accountNumberDestinationInput, i);
        jsSetValue(number, accountNumberDestinationInput, i);
        jsRiseOnchange(accountNumberDestinationInput, i);

    }

    @Step("Click destination 'Account number' suggestion option")
    public void clickDestinationAccountSuggestionOption(String accountNumber) {
        waitForElementClickable(accountNumberOptionSelector, accountNumber);
        jsClick(accountNumberOptionSelector, accountNumber);
    }

    @Step("Click Destination 'Amount' {0} division")
    public void clickDestinationAmountDiv(int i) {
        waitForElementClickable(transactionDestinationAmountDiv, i);
        jsClick(transactionDestinationAmountDiv, i);
    }

    @Step("Set Destination {0} 'Amount' value {1}")
    public void typeDestinationAmountValue(int i, String amount) {
        waitForElementClickable(transactionDestinationAmountField, i);
        jsType(amount, transactionDestinationAmountField, i);
        jsRiseOnchange(transactionDestinationAmountField, i);
    }

    @Step("Click on destination code {0}")
    public void clickOnDestinationCodeField(int i) {
        waitForElementClickable(transactionDestinationCodeDropdownArrow, i);
        jsClick(transactionDestinationCodeDropdownArrow, i);
    }

    @Step("Click on {0} item in dropdown")
    public void clickOnDropDownItem(String item) {
        waitForElementClickable(itemInDropDown, item);
        jsClick(itemInDropDown, item);
    }

    /**
     * Account Quick View
     */
    private By accountQuickViewToggleButton = By.xpath("//a[@ng-click='toggleAccountQuick()']//i");
    private By availableBalance = By.xpath("//*[@id='accordion-operation-aqv-content']" +
            "//span[text()='Available Balance']//ancestor::node()[1]//span[2]");
    private By originalBalance = By.xpath("//*[@id='accordion-operation-aqv-content']" +
            "//span[text()='Original Balance']//ancestor::node()[1]//span[2]");
    private By currentBalance = By.xpath("//*[@id='accordion-operation-aqv-content']" +
            "//span[text()='Current Balance']//ancestor::node()[1]//span[2]");
    private By accruedInterest = By.xpath("//*[@id='accordion-operation-aqv-content']" +
            "//span[text()='Accrued Interest']//ancestor::node()[1]//span[2]");
    private By automaticOverdraftLimit = By.xpath("//*[@id='accordion-operation-aqv-content']" +
            "//span[text()='Automatic Overdraft Limit']//ancestor::node()[1]//span[2]");
    private By pIFNumber = By.xpath("//*[@id='accordion-operation-aqv-content']" +
            "//span[text()='PIF Number']//ancestor::node()[1]//span[2]");
    private By accountType = By.xpath("//*[@id='accordion-operation-aqv-content']" +
            "//span[text()='Account Type']//ancestor::node()[1]//span[2]");
    private By productType = By.xpath("//*[@id='accordion-operation-aqv-content']" +
            "//span[text()='Product Type']//ancestor::node()[1]//span[2]");
    private By dateOpened = By.xpath("//*[@id='accordion-operation-aqv-content']" +
            "//span[text()='Date Opened']//ancestor::node()[1]//span[2]");
    private By boxSize = By.xpath("//*[@id='accordion-operation-aqv-content']" +
            "//span[text()='Box Size']//ancestor::node()[1]//span[2]");
    private By rentalAmount = By.xpath("//*[@id='accordion-operation-aqv-content']" +
            "//span[text()='Rental Amount']//ancestor::node()[1]//span[2]");
    private By detaulsButton = By.xpath("//button[contains(string(), 'Details')]");

    @Step("Is account quick view visible")
    public boolean isAccountQuickViewVisible() {
        waitForElementVisibility(accountQuickViewToggleButton);
        return getWebElement(accountQuickViewToggleButton).getAttribute("class").contains("open-icon");
    }

    @Step("Click account quick view")
    public void clickAccountQuickViewArrow() {
        waitForElementVisibility(accountQuickViewToggleButton);
        jsClick(accountQuickViewToggleButton);
    }

    @Step("Click details button")
    public void clickDetailsButton() {
        waitForElementVisibility(detaulsButton);
        jsClick(detaulsButton);
    }

    @Step("Get 'Available Balance' value")
    public String getAvailableBalance() {
        waitForElementVisibility(availableBalance);
        return getElementText(availableBalance).trim().replaceAll("[^0-9.]", "");
    }

    @Step("Get 'Original Balance' value")
    public String getOriginalBalance() {
        waitForElementVisibility(originalBalance);
        return getElementText(originalBalance).trim().replaceAll("[^0-9.]", "");
    }

    @Step("Get 'Current Balance' value")
    public String getCurrentBalance() {
        waitForElementVisibility(currentBalance);
        return getElementText(currentBalance).trim().replaceAll("[^0-9.]", "");
    }

    @Step("Get 'Accrued Interest' value")
    public String getAccruedInterest() {
        waitForElementVisibility(accruedInterest);
        return getElementText(accruedInterest).trim().replaceAll("[^0-9.]", "");
    }

    @Step("Get 'Automatic Overdraft Limit' value")
    public String getAutomaticOverdraftLimit() {
        waitForElementVisibility(automaticOverdraftLimit);
        String limit = getElementText(automaticOverdraftLimit).trim();
        return limit.substring(limit.indexOf("/")).trim().replaceAll("[^0-9.]", "");
    }

    @Step("Get 'Automatic Overdraft Limit' value")
    public String getFirstAutomaticOverdraftLimit() {
        waitForElementVisibility(automaticOverdraftLimit);
        String limit = getElementText(automaticOverdraftLimit).trim();
        if (!limit.contains("/")){
            return limit;
        }
        return limit.substring(0, limit.indexOf("/")).trim();
    }

    @Step("Get 'PIF Number' value")
    public String getPIFNumber() {
        waitForElementVisibility(pIFNumber);
        return getElementText(pIFNumber).trim();
    }

    @Step("Get 'Date opened' value")
    public String getDateOpened() {
        waitForElementVisibility(dateOpened);
        return getElementText(dateOpened).trim();
    }

    @Step("Get 'Box Size' value")
    public String getBoxSize() {
        waitForElementVisibility(boxSize);
        return getElementText(boxSize).trim();
    }

    @Step("Get 'Rental Amount' value")
    public String getRentalAmount() {
        waitForElementVisibility(rentalAmount);
        return getElementText(rentalAmount).trim().replaceAll("[^0-9.]", "");
    }

    @Step("Get 'Account Type' value")
    public String getAccountType() {
        waitForElementVisibility(accountType);
        return getElementText(accountType).trim();
    }

    @Step("Get 'Product Type' value")
    public String getProductType() {
        waitForElementVisibility(productType);
        return getElementText(productType).trim();
    }
}