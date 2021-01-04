package com.nymbus.pages.backoffice;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class BackOfficePage extends PageTools {

    private By reportGeneratorPageHeader = By.xpath("//h1[text()='Ad Hoc Reporting']");
    private By documentSearchSeeMoreLink = By.xpath("//li/div/div[@class='header']/h2[text()='Document Search']/parent::div/following-sibling::div//div/div/a[text()='See more ']");
    private By officialChecksButton = By.xpath("//h2[text()='Official Checks']/../..//a");

    @Step("Click the 'Official checks' button")
    public void clickOfficialChecks(){
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

}
