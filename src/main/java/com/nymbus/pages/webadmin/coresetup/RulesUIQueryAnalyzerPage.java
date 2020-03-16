package com.nymbus.pages.webadmin.coresetup;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class RulesUIQueryAnalyzerPage extends PageTools {

    private By searchRegion = By.xpath("//td[@class='mainPanel' and //form[contains(@action, 'RulesUIQuery')]]");
    private By searchResultTable = By.xpath("//table[@id='searchResultTable']");
    private By listOfSearchResult = By.xpath("//table[@id='searchResultTable']//tr[@class='searchResultRow ']");
    private By firstNameByIndex = By.xpath("//table[@id='searchResultTable']//tr[@class='searchResultRow '][%s]/td[11]/div");
    private By lastNameByIndex = By.xpath("//table[@id='searchResultTable']//tr[@class='searchResultRow '][%s]/td[12]/div");

    @Step("Wait for 'Rules UI Query Analyzer' page loaded")
    public void waitForPageLoad() {
        waitForElementVisibility(searchRegion);
    }

    @Step("Wait for search result table")
    public void waitForSearchResultTable() {
        waitForElementVisibility(searchResultTable);
    }

    @Step("Get number of search result")
    public int getNumberOfSearchResult() {
        waitForElementVisibility(listOfSearchResult);
        return getElements(listOfSearchResult).size();
    }

    @Step("Get first name value")
    public String getFirstNameByIndex(int index) {
        waitForElementVisibility(firstNameByIndex, index);
        return getElementText(firstNameByIndex, index);
    }

    @Step("Get last name value")
    public String getLastNameByIndex(int index) {
        waitForElementVisibility(lastNameByIndex, index);
        return getElementText(lastNameByIndex, index);
    }

}