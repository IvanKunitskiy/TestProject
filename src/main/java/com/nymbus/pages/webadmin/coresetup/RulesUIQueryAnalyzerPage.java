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

    /**
     * Transaction item region
     */

    private By glDateTimePosted = By.xpath("//*[@id='searchResultTable']//tr[@class='searchResultRow'][%s]//div[@key-name='gldatetimeposted']");
    private By glFunctionValue = By.xpath("//*[@id='searchResultTable']//tr[@class='searchResultRow'][%s]//div[@key-name='glfunction']");
    private By transactionHeaderId = By.xpath("//*[@id='searchResultTable']//tr[@class='searchResultRow'][%s]//*[@key-name='transactionheaderid']");


    @Step("Get date posted  {0}")
    public String getDatePosted(int index) {
        waitForElementVisibility(glDateTimePosted, index);
        return getElementText(glDateTimePosted, index).trim();
    }

    @Step("Get glfunction {0} value")
    public String getGLFunctionValue(int index) {
        waitForElementVisibility(glFunctionValue, index);
        return getElementText(glFunctionValue, index).trim();
    }

    @Step("Get transactionheaderid {0} value")
    public String getTransactionHeaderIdValue(int index) {
        waitForElementVisibility(transactionHeaderId, index);
        return getElementText(transactionHeaderId, index).trim();
    }

    /**
     * GL interface region
     */
    private By amountField = By.xpath("//*[@id='searchResultTable']//tr[@class='searchResultRow'][%s]//*[@key-name='amount']");
    private By glInterfaceTransactionHeaderId= By.xpath("//*[@id='searchResultTable']//tr[@class='searchResultRow'][%s]//*[@key-name='parenttransaction']");

    @Step("Get amount {0} value")
    public String getAmount(int index) {
        waitForElementVisibility(amountField, index);
        return getElementText(amountField, index).trim();
    }

    @Step("Get transactionheaderid {0} value")
    public String getGLInterfaceTransactionHeaderIdValue(int index) {
        waitForElementVisibility(glInterfaceTransactionHeaderId, index);
        return getElementText(glInterfaceTransactionHeaderId, index).trim();
    }
}