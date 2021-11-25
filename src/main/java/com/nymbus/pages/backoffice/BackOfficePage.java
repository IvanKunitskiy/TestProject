package com.nymbus.pages.backoffice;

import com.codeborne.selenide.Selenide;
import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class BackOfficePage extends PageTools {

    private final By reportGeneratorPageHeader = By.xpath("//h1[text()='Ad Hoc Reporting']");
    private final By documentSearchSeeMoreLink = By.xpath("//li/div/div[@class='header']/h2[text()='Document Search']/parent::div/following-sibling::div//div/div/a[text()='See more ']");
    private final By officialChecksButton = By.xpath("//h2[text()='Official Checks']/../..//a");
    private final By fileImportDropdown = By.xpath("(//span[contains(string(),'Select')])[2]");
    private final By fileImportSpan = By.xpath("//span[contains(string(),'%s')]");
    private final By importSpan = By.xpath("//span[contains(string(),'Import')]");
    private final By importButton = By.xpath("(//span[contains(string(),'Import')])[2]");
    private final By fileInput = By.xpath("//input[@type='file']");
    private final By cellImportTable = By.xpath("(//td)[%s]");
    private final By postButton = By.xpath("//span[contains(text(),\"Post\")]");
    private final By cellSpan = By.xpath("(//td)[%s]/span");
    private final By itemsToWorkButton = By.xpath("//h2[contains(text(), 'Items to Work')]//..//..//a[contains(text(), 'See more')]");
    private final By accountNumber = By.xpath("//div[@class='reject-item-summary-column ng-binding' and contains(string(),'%s')]");
    private final By date = By.xpath("//input[@data-test-id='field-']");
    private final By reason = By.xpath("(//span[contains(string(),'Reason')])[1]");
    private final By badCode = By.xpath("(//span[contains(string(),'bad code')])[1]");
    private final By filterButton = By.xpath("//button[contains(string(),'Filter')]");
    private final By editButton = By.xpath("//button[contains(string(),'Edit')]");
    private final By transactionCodeInput = By.xpath("(//a[@aria-label='Select box select'])[2]");
    private final By itemInTranCodeList = By.xpath("//ul[@repeat='item in tranCodeList']");
    private final By saveButton = By.xpath("//button[contains(string(),'Save')]");
    private final By createSwapButton = By.xpath("//button[contains(string(),'Create')]");
    private final By closeButton = By.xpath("//button[contains(string(),'Close')]");
    private final By statusDropDown = By.xpath("//a[@placeholder='Status']");
    private final By status = By.xpath("(//span[contains(string(),'%s')])[1]");
    private final By sourceDropDown = By.xpath("//a[@placeholder='Source']");
    private final By proofDateByAccountNumber = By.xpath("(//div[@class='reject-item-summary-column ng-binding' and contains(string(),'%s')]/../*)[1]");
    private final By sourceByAccountNumber = By.xpath("(//div[@class='reject-item-summary-column ng-binding' and contains(string(),'%s')]/../*)[3]");
    private final By amountByAccountNumber = By.xpath("(//div[@class='reject-item-summary-column ng-binding' and contains(string(),'%s')]/../*)[9]");
    private final By statusByAccountNumber = By.xpath("(//div[@class='reject-item-summary-column ng-binding' and contains(string(),'%s')]/../*)[10]");
    private final By branchByAccountNumber = By.xpath("(//div[@class='reject-item-summary-column ng-binding' and contains(string(),'%s')]/../*)[7]");
    private final By xSource= By.xpath("(//abbr[@ng-click='customClear($event)'])[1]");
    private final By branchDropDown = By.xpath("//a[@placeholder='Branch']");
    private final By branchOption = By.xpath("//li[@role='option']/div/span[contains(string(),'%s')]");
    private final By showMore = By.xpath("//button[@ng-click='loadMoreHandler()']");
    private final By batchProc = By.xpath("//li[contains(string(),'Batch Processing / Outgoing Cash Letter')]//a//li[contains(string(),'Batch Processing / Outgoing Cash Letter')]//a");

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

    @Step("Click 'Batch Processing' button")
    public void clickBatchProcessingButton() {
        waitForElementVisibility(batchProc);
        click(batchProc);
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

    @Step("Click 'Status' dropdown")
    public void clickStatusDropDown() {
        waitForElementVisibility(statusDropDown);
        click(statusDropDown);
    }

    @Step("Click 'Status' or 'Source'")
    public void clickStatusOrSource(String status) {
        waitForElementVisibility(this.status, status);
        click(this.status, status);
    }

    @Step("Click 'Source' dropdown")
    public void clickSourceDropDown() {
        waitForElementVisibility(sourceDropDown);
        click(sourceDropDown);
    }

    @Step("Get 'Proof Date' by account number")
    public String getProofDateByAccountNumber(String accountNumber) {
        waitForElementVisibility(proofDateByAccountNumber, accountNumber);
        return getElementText(proofDateByAccountNumber, accountNumber);
    }

    @Step("Get 'Source' by account number")
    public String getSourceByAccountNumber(String accountNumber) {
        waitForElementVisibility(sourceByAccountNumber, accountNumber);
        return getElementText(sourceByAccountNumber, accountNumber);
    }

    @Step("Get 'Amount' by account number")
    public String getAmountByAccountNumber(String accountNumber) {
        waitForElementVisibility(amountByAccountNumber, accountNumber);
        return getElementText(amountByAccountNumber, accountNumber);
    }

    @Step("Get 'Status' by account number")
    public String getStatusByAccountNumber(String accountNumber) {
        waitForElementVisibility(statusByAccountNumber, accountNumber);
        return getElementText(statusByAccountNumber, accountNumber);
    }

    @Step("Get 'Branch' by account number")
    public String getBranchByAccountNumber(String accountNumber) {
        waitForElementVisibility(branchByAccountNumber, accountNumber);
        return getElementText(branchByAccountNumber, accountNumber);
    }

    @Step("Click 'XSource' button")
    public void clickXSource() {
        waitForElementVisibility(xSource);
        click(xSource);
    }

    @Step("Click 'Branch' dropdown")
    public void clickBranchDropDown() {
        waitForElementVisibility(branchDropDown);
        click(branchDropDown);
    }

    @Step("Click 'Branch' option")
    public void clickBranchOption(String branch) {
        waitForElementVisibility(branchOption, branch);
        click(branchOption, branch);
    }

    @Step("Click 'Show More' button")
    public void clickShowMore() {
        waitForElementVisibility(showMore);
        waitForElementClickable(showMore);
        click(showMore);
    }



}
