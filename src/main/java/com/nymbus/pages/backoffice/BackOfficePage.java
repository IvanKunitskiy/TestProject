package com.nymbus.pages.backoffice;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class BackOfficePage extends PageTools {

    private By reportGeneratorPageHeader = By.xpath("//h1[text()='Ad Hoc Reporting']");
    private By documentSearchSeeMoreLink = By.xpath("//li/div/div[@class='header']/h2[text()='Document Search']/parent::div/following-sibling::div//div/div/a[text()='See more ']");

    @Step("Click the 'See more' document search link")
    public void clickTheDocumentSearchSeeMoreLink() {
        click(documentSearchSeeMoreLink);
    }

    @Step("Wait for Report Generator page loaded")
    public void waitForReportGeneratorPageLoaded() {
        waitForElementVisibility(reportGeneratorPageHeader);
    }

}
