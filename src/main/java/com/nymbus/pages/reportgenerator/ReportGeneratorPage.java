package com.nymbus.pages.reportgenerator;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class ReportGeneratorPage extends PageTools {

    private By reportGeneratorPageHeader = By.xpath("//h1[text()='Ad Hoc Reporting']");

    @Step("Wait for Report Generator page loaded")
    public void waitForReportGeneratorPageLoaded() {
        waitForElementVisibility(reportGeneratorPageHeader);
    }
}
