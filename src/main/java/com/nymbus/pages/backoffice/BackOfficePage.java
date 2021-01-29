package com.nymbus.pages.backoffice;

import com.codeborne.selenide.Selenide;
import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class BackOfficePage extends PageTools {

    private By reportGeneratorPageHeader = By.xpath("//h1[text()='Ad Hoc Reporting']");
    private By documentSearchSeeMoreLink = By.xpath("//li/div/div[@class='header']/h2[text()='Document Search']/parent::div/following-sibling::div//div/div/a[text()='See more ']");
    private By officialChecksButton = By.xpath("//h2[text()='Official Checks']/../..//a");
    private By fileImportDropdown = By.xpath("(//span[contains(string(),'Select')])[2]");
    private By fileImportSpan = By.xpath("//span[contains(string(),'%s')]");
    private By importSpan = By.xpath("//span[contains(string(),'Import')]");
    private By importButton = By.xpath("(//span[contains(string(),'Import')])[2]");
    private By fileInput = By.xpath("//input[@type='file']");
    private By cellImportTable = By.xpath("(//td)[%s]");
    private By postButton = By.xpath("//span[contains(text(),\"Post\")]");
    private By cellSpan = By.xpath("(//td)[%s]/span");
    private By itemsToWorkButton = By.xpath("//li[14]/div[1]/div[2]/div[2]/div/a");
    private By accountNumber = By.xpath("//div[@class='reject-item-summary-column ng-binding' and contains(string(),'%s')]");
    private By date = By.xpath("//input[@data-test-id='field-']");
    private By reason = By.xpath("(//span[contains(string(),'Reason')])[1]");
    private By badCode = By.xpath("(//span[contains(string(),'bad code')])[1]");
    private By filterButton = By.xpath("//button[contains(string(),'Filter')]");
    private By editButton = By.xpath("//button[contains(string(),'Edit')]");
    private By transactionCodeInput = By.xpath("(//a[@aria-label='Select box select'])[2]");
    private By itemInTranCodeList = By.xpath("//ul[@repeat='item in tranCodeList']");
    private By saveButton = By.xpath("//button[contains(string(),'Save')]");
    private By createSwapButton = By.xpath("//button[contains(string(),'Create')]");
    private By closeButton = By.xpath("//button[contains(string(),'Close')]");

    @Step("Click the 'Official checks' button")
    public void clickOfficialChecks() {
        waitForElementClickable(officialChecksButton);
        click(officialChecksButton);
    }

    @Step("Click item to work button")
    public void clickItemToWork() {
        waitForElementClickable(itemsToWorkButton);
        click(itemsToWorkButton);
    }

    private By printChecksSeeMoreLink = By.xpath("//li/div/div[@class='header']/h2[text()='Print Checks']/parent::div/following-sibling::div//div/div/a[text()='See more ']");

    @Step("Click the 'See more' document search link")
    public void clickDocumentSearchSeeMoreLink() {
        click(documentSearchSeeMoreLink);
    }

    @Step("Click the 'See more' print checks link")
    public void clickPrintChecksSeeMoreLink() {
        click(printChecksSeeMoreLink);
    }

    @Step("Wait for Report Generator page loaded")
    public void waitForReportGeneratorPageLoaded() {
        waitForElementVisibility(reportGeneratorPageHeader);
    }

    @Step("Choose file Import Type")
    public void chooseFileImportType(String type) {
        waitForElementVisibility(fileImportDropdown);
        click(fileImportDropdown);
        waitForElementVisibility(fileImportSpan, type);
        click(fileImportSpan, type);
    }

    @Step("Click 'Import' button")
    public void clickImport() {
        waitForElementVisibility(importSpan);
        waitForElementClickable(importSpan);
        click(importSpan);
    }

    @Step("Set Profile photo")
    public void importFile(String filename) {
        waitForElementVisibility(importButton);
        waitForElementClickable(importButton);
        Selenide.executeJavaScript("arguments[0].style.height='auto'; arguments[0].style.visibility='visible';",
                getWebElement(fileInput));
        type(filename, fileInput);
        Selenide.executeJavaScript("arguments[0].style.height='auto'; arguments[0].style.visibility='hidden';",
                getWebElement(fileInput));
        click(importButton);
    }

    @Step("Check value from import table")
    public boolean checkValueFromImportFilesTable(int i, String text) {
        waitForElementVisibility(cellImportTable, i);
        String elementsText = getElementText(cellImportTable, i);
        return elementsText.equals(text);
    }

    @Step("Click 'Post' button")
    public void clickPostButton() {
        waitForElementVisibility(postButton);
        waitForElementClickable(postButton);
        click(postButton);
    }

    @Step("Check value from span import table")
    public boolean checkValueFromSpanImportFilesTable(int i, String text) {
        waitForElementVisibility(cellSpan, i);
        String elementsText = getElementText(cellSpan, i);
        return elementsText.equals(text);
    }

    @Step("Click to account number")
    public void clickToAccountNumber(String number) {
        waitForElementVisibility(accountNumber, number);
        click(accountNumber, number);
    }

    @Step("Input date")
    public void inputProofDate(String effectiveDate) {
        waitForElementVisibility(date);
        click(date);
        type(effectiveDate, date);
    }

    @Step("Input bad code reason")
    public void inputReasonBadCode() {
        waitForElementVisibility(reason);
        click(reason);
        waitForElementVisibility(badCode);
        click(badCode);
    }

    @Step("Click 'Filter' button")
    public void clickFilterButton() {
        waitForElementVisibility(filterButton);
        click(filterButton);
    }

    @Step("Click 'Edit' button")
    public void clickEditButton() {
        waitForElementVisibility(editButton);
        click(editButton);
    }

    @Step("Choose transaction code input")
    public void chooseTranCode() {
        waitForElementVisibility(transactionCodeInput);
        click(transactionCodeInput);
        waitForElementVisibility(itemInTranCodeList);
        click(itemInTranCodeList);
    }

    @Step("Click 'Save' button")
    public void clickSaveButton() {
        waitForElementVisibility(saveButton);
        click(saveButton);
    }

    @Step("Click 'Create Swap' button")
    public void clickCreateSwapButton() {
        waitForElementVisibility(createSwapButton);
        click(createSwapButton);
    }

    @Step("Click 'Close' button")
    public void clickCloseButton() {
        waitForElementVisibility(closeButton);
        click(closeButton);
    }
}
