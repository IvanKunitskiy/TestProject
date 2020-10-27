package com.nymbus.pages.settings.products;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class ProductOverviewPage extends PageTools {

    private final By minTerm = By.xpath("//div[@data-field-id='maxterm']//span[@class='xwidget_readonly_container']");
    private final By interestFrequency = By.xpath("//div[@data-field-id='interestfrequencycode']//span[@class='xwidget_readonly_value']");
    private final By termType = By.xpath("//div[@data-field-id='termtype']//span[@class='xwidget_readonly_value']");
    private final By interestType = By.xpath("//div[@data-field-id='interesttype']//span[@class='xwidget_readonly_value']");
    private final By interestRate = By.xpath("//div[@data-field-id='interestrate']//span[@class='xwidget_readonly_container']");

    @Step("Get 'Min term' value")
    public String getMinTerm() {
        waitForElementVisibility(minTerm);
        return getElementText(minTerm);
    }

    @Step("Get 'Interest Frequency' value")
    public String getInterestFrequency() {
        waitForElementVisibility(interestFrequency);
        return getElementText(interestFrequency);
    }

    @Step("Get 'Term Type' value")
    public String getTermType() {
        waitForElementVisibility(termType);
        return getElementText(termType);
    }

    @Step("Get 'Interest Type' value")
    public String getInterestType() {
        waitForElementVisibility(interestType);
        return getElementText(interestType);
    }

    @Step("Get 'Interest Rate' value")
    public String getInterestRate() {
        waitForElementVisibility(interestRate);
        return getElementText(interestRate).replaceAll("[^0-9.]", "");
    }
}
