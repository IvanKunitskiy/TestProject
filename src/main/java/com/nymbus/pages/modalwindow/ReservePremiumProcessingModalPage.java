package com.nymbus.pages.modalwindow;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class ReservePremiumProcessingModalPage extends PageTools {

    private final By addNewButton = By.xpath("//span[text()='Add New Loan Reserve/Premium']");
    private final By effectiveDate = By.xpath("//input[@id='effectivedate']");
    private final By originalAmount = By.xpath("//input[@id='originalamount']");
    private final By reserveId = By.xpath("//div[@id='loanreserveid']");
    private final By reserveIdOption = By.xpath("//div[@id='loanreserveid']//li/div/span[text()='%s']");
    private final By termInMonths = By.xpath("//input[@id='terminmonths']");
    private final By deferringStartDate = By.xpath("//input[@id='deferringstartdate']");
    private final By glOffset = By.xpath("//div[@id='gloffsetaccountid']//input");
    private final By glAccount = By.xpath("//a[contains(string(),'%s')]");
    private final By commitButton = By.xpath("//button[text()='Commit Transaction']");
    private final By codeSpan = By.xpath("//span[contains(string(),'%s')]");
    private final By closeButton = By.xpath("[//button[contains(string(),'×')]](2)");
    private final By amountLabel = By.xpath("//label[text()='Adjustment Amount']/../..//span/span");
    private final By originalAmountLabel = By.xpath("//label[text()='Reserve/Premium Original Amount']/../..//span/span");
    private final By unAmortizedLabel = By.xpath("//label[text()='Reserve/Premium Unamortized']/../..//span/span");
    private final By maturityDate = By.xpath("//label[text()='Reserve/Premium Maturity Date']/../..//span/span");

    @Step("Click 'Add New' button")
    public void clickAddNewButton() {
        waitForElementVisibility(addNewButton);
        waitForElementClickable(addNewButton);
        click(addNewButton);
    }

    @Step("Input Effective Date")
    public void inputEffectiveDate(String date) {
        waitForElementVisibility(effectiveDate);
        type(date, effectiveDate);
    }

    @Step("Input Original Amount")
    public void inputOriginalAmount(String date) {
        waitForElementVisibility(originalAmount);
        type(date, originalAmount);
    }

    @Step("Input Reserve Id")
    public void inputReserveId(String id) {
        waitForElementVisibility(reserveId);
        click(reserveId);
        waitForElementVisibility(reserveIdOption, id);
        click(reserveIdOption, id);
    }

    @Step("Input term in month")
    public void inputTermInMonth(String term) {
        waitForElementVisibility(termInMonths);
        type(term, termInMonths);
    }

    @Step("Input deferring start date")
    public void inputDeferringStartDate(String date) {
        waitForElementVisibility(deferringStartDate);
        type(date, deferringStartDate);
    }

    @Step("Input Gl Account")
    public void inputGLAccount(String account) {
        waitForElementVisibility(glOffset);
        click(glOffset);
        type(account, glOffset);
        waitForElementVisibility(glAccount, account);
        click(glAccount, account);
    }

    @Step("Click Commit Button")
    public void clickCommitButton() {
        waitForElementVisibility(commitButton);
        click(commitButton);
    }

    @Step("Click record")
    public void clickRecord(String id) {
        waitForElementVisibility(codeSpan, id);
        click(codeSpan, id);
    }

    @Step("Click Close Button")
    public void clickCloseButton() {
        waitForElementVisibility(closeButton);
        click(closeButton);
    }

    @Step("Get amount")
    public String getAmount() {
        waitForElementVisibility(amountLabel);
        return getElementText(amountLabel);
    }

    @Step("Get original amount")
    public String getOriginalAmount() {
        waitForElementVisibility(originalAmountLabel);
        return getElementText(originalAmountLabel);
    }

    @Step("Get un amortized amount")
    public String getUnAmortizedAmount() {
        waitForElementVisibility(unAmortizedLabel);
        return getElementText(unAmortizedLabel);
    }

    @Step("Get maturity date")
    public String getMaturityDate() {
        waitForElementVisibility(maturityDate);
        return getElementText(maturityDate);
    }


}
