package com.nymbus.pages.settings.cdt;

import com.nymbus.core.base.PageTools;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.account.product.ProductType;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class CreateCDTPage extends PageTools {
    private final By nameInput = By.xpath("//input[@placeholder=' ']");
    private final By debitAccountTypeInput = By.xpath("//div[@data-field-id='debitaccounttype']//input");
    private final By accountType = By.xpath("//a[contains(string(),'%s')]");
    private final By creditAccountTypeInput = By.xpath("//div[@data-field-id='creditaccounttype']//input");
    private final By creditAccountType = By.xpath("(//a[contains(string(),'%s')])[2]");
    private final By debitAccountCodeInput = By.xpath("//div[@data-field-id='debittrancode']//input");
    private final By creditAccountCodeInput = By.xpath("//div[@data-field-id='credittrancode']//input");
    private final By debitDescriptionInput = By.xpath("(//div[@data-field-id='debitdescription']//input)[3]");
    private final By creditDescriptionInput = By.xpath("(//div[@data-field-id='creditdescription']//input)[3]");
    private final By feeDescriptionInput = By.xpath("(//div[@data-field-id='feedescription']//input)[3]");
    private final By debitPrintNoticeInput = By.xpath("//div[@data-field-id='debitprintnoticeflag']//input");
    private final By creditPrintNoticeInput = By.xpath("//div[@data-field-id='creditprintnoticeflag']//input");
    private final By feeAmountInput = By.xpath("//div[@data-field-id='feeamount']//input");
    private final By GLAccountInput = By.xpath("//div[@data-field-id='feeglaccountid']//input");
    private final By creditAccountInput = By.xpath("//div[@data-field-id='creditglaccountid']//input");
    private final By saveButton = By.xpath("//button[contains(string(),'Save Changes')]");
    private final By nameDiv = By.xpath("//div[@data-field-id='(databean)name']//span");
    private final By feeOperationTypeInput = By.xpath("//div[@data-field-id='operationcode']//input");
    private final By operationTypeType = By.xpath("//div[@data-field-id='operationcode']//ul//a[contains(string(),'%s')]");

    @Step("Input name")
    public void inputName(String name) {
        waitForElementVisibility(nameInput);
        type(name, nameInput);
    }

    @Step("Select debit type account")
    public void selectDebitTypeAccount(ProductType productType) {
        waitForElementVisibility(debitAccountTypeInput);
        click(debitAccountTypeInput);
        waitForElementVisibility(accountType, productType.getProductType());
        click(accountType, productType.getProductType());
    }

    @Step("Select credit type account")
    public void selectCreditTypeAccount(ProductType productType) {
        waitForElementVisibility(creditAccountTypeInput);
        click(creditAccountTypeInput);
        waitForElementVisibility(creditAccountType, productType.getProductType());
        click(creditAccountType, productType.getProductType());
    }

    @Step("Select debit transaction code")
    public void selectDebitTransactionCode(String code) {
        waitForElementVisibility(debitAccountCodeInput);
        click(debitAccountCodeInput);
        waitForElementVisibility(accountType, code);
        click(accountType, code);
    }

    @Step("Select debit transaction code")
    public void selectCreditTransactionCode(String code) {
        waitForElementVisibility(creditAccountCodeInput);
        click(creditAccountCodeInput);
        waitForElementVisibility(accountType, code);
        click(accountType, code);
    }

    @Step("Input debit description")
    public void inputDebitDescription(String description) {
        waitForElementVisibility(debitDescriptionInput);
        type(description, debitDescriptionInput);
    }

    @Step("Input credit description")
    public void inputCreditDescription(String description) {
        waitForElementVisibility(creditDescriptionInput);
        type(description, creditDescriptionInput);
    }

    @Step("Select debit notice option")
    public void selectDebitNoticeOption(String notice) {
        waitForElementVisibility(debitPrintNoticeInput);
        click(debitPrintNoticeInput);
        waitForElementVisibility(accountType, notice);
        click(accountType, notice);
    }

    @Step("Select credit notice option")
    public void selectCreditNoticeOption(String notice) {
        waitForElementVisibility(creditPrintNoticeInput);
        click(creditPrintNoticeInput);
        waitForElementVisibility(creditAccountType, notice);
        click(creditAccountType, notice);
    }

    @Step("Input fee amount")
    public void inputFeeAmount(String amount) {
        waitForElementVisibility(feeAmountInput);
        type(amount, feeAmountInput);
    }

    @Step("Input fee description")
    public void inputFeeDescription(String description) {
        waitForElementVisibility(feeDescriptionInput);
        type(description, feeDescriptionInput);
    }

    @Step("Input G/L account")
    public void inputGLAccount(String number) {
        waitForElementVisibility(GLAccountInput);
        type(number, GLAccountInput);
        waitForElementVisibility(accountType, number);
        click(accountType, number);
    }

    @Step("Input credit account")
    public void inputCreditAccount(String number) {
        waitForElementVisibility(creditAccountInput);
        type(number, creditAccountInput);
        waitForElementVisibility(accountType, number);
        click(accountType, number);
    }

    @Step("Click the 'Save button' button")
    public void clickSaveButton() {
        waitForElementClickable(saveButton);
        click(saveButton);
    }

    @Step("Check name span")
    public boolean checkNameIsVisible(){
        SelenideTools.sleep(Constants.MINI_TIMEOUT);
        return isElementVisible(nameDiv);
    }

    @Step("Select operation type")
    public void selectOperationType(String type){
        waitForElementVisibility(feeOperationTypeInput);
        click(feeOperationTypeInput);
        waitForElementVisibility(operationTypeType, type);
        click(operationTypeType, type);
    }

}
