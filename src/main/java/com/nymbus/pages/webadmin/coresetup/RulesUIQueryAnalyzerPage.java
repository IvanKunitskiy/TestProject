package com.nymbus.pages.webadmin.coresetup;

import com.nymbus.base.BasePage;
import com.nymbus.locator.Locator;
import com.nymbus.locator.XPath;
import io.qameta.allure.Step;

public class RulesUIQueryAnalyzerPage extends BasePage {

    private Locator searchRegion = new XPath("//td[@class='mainPanel' and //form[contains(@action, 'RulesUIQuery')]]");
    private Locator searchResultTable = new XPath("//table[@id='searchResultTable']");
    private Locator listOfSearchResult = new XPath("//table[@id='searchResultTable']//tr[@class='searchResultRow ']");
    private Locator firstNameByIndex = new XPath("//table[@id='searchResultTable']//tr[@class='searchResultRow '][%s]/td[11]/div");
    private Locator lastNameByIndex = new XPath("//table[@id='searchResultTable']//tr[@class='searchResultRow '][%s]/td[12]/div");

    @Step("Wait for 'Rules UI Query Analyzer' page loaded")
    public void waitForPageLoad(){
        waitForElementVisibility(searchRegion);
    }

    @Step("Wait for search result table")
    public void waitForSearchResultTable(){
        waitForElementVisibility(searchResultTable);
    }

    @Step("Get number of search result")
    public int getNumberOfSearchResult(){
        waitForElementVisibility(listOfSearchResult);
        return getElements(listOfSearchResult).size();
    }

    @Step("Get first name value")
    public String getFirstNameByIndex(int index){
        waitForElementVisibility(firstNameByIndex, index);
        return getElementText(firstNameByIndex, index);
    }

    @Step("Get last name value")
    public String getLastNameByIndex(int index){
        waitForElementVisibility(lastNameByIndex, index);
        return getElementText(lastNameByIndex, index);
    }

}