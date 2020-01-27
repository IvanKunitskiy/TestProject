package com.nymbus.pages.backoffice;

import com.nymbus.base.BasePage;
import com.nymbus.locator.Locator;
import com.nymbus.locator.XPath;
import io.qameta.allure.Step;

public class BackOfficePage extends BasePage {

    private Locator reportGeneratorPageHeader = new XPath("//h1[text()='Ad Hoc Reporting']");

    @Step("Wait for Report Generator page loaded")
    public void waitForReportGeneratorPageLoaded(){
        waitForElementVisibility(reportGeneratorPageHeader);
    }

}
