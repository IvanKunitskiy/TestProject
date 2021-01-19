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

    @Step("Click the 'Official checks' button")
    public void clickOfficialChecks() {
        waitForElementClickable(officialChecksButton);
        click(officialChecksButton);
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
}
