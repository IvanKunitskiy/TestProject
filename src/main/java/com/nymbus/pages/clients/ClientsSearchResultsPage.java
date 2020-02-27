package com.nymbus.pages.clients;

import com.nymbus.base.BasePage;
import com.nymbus.locator.Locator;
import com.nymbus.locator.XPath;
import io.qameta.allure.Step;

import java.util.List;
import java.util.stream.Collectors;

public class ClientsSearchResultsPage extends BasePage {
    private Locator searchResults = new XPath("//div[contains(@ng-repeat,'item')]");
    private Locator accountNumberSearchResults = new XPath("//div/span[@class='spaceRight ng-binding']");
    private Locator loadMoreResultsButton = new XPath("//button[text()='Load More Results']");
    private Locator clientNameFromResultByIndex
            = new XPath("(//div[@class='search-result-container']//div[contains(@class,'table__td')][1])[%s]");
    private Locator clientIDFromResultByIndex
            = new XPath("(//div[@class='search-result-container']//div[contains(@class,'table__td')][2])[%s]");
    private Locator clientTypeFromResultByIndex
            = new XPath("(//div[@class='search-result-container']//div[contains(@class,'table__td')][3])[%s]");
    private Locator clientAddressFromResultByIndex
            = new XPath("(//div[@class='search-result-container']//div[contains(@class,'table__td')][4])[%s]");
    private Locator clientAKAFromResultByIndex
            = new XPath("(//div[@class='search-result-container']//div[contains(@class,'table__td')][5])[%s]");

    @Step("Wait for search results")
    public void waitForSearchResults() {
        waitForElementVisibility(searchResults);
        waitForElementClickable(searchResults);
    }

    @Step("Getting search results count")
    public int getSearchResultsCount() {
        waitForElementVisibility(searchResults);
        return getElements(searchResults).size();
    }

    @Step("Verify is 'Load Mode Results' button visible")
    public boolean isLoadMoreResultsButtonVisible() {
        return isElementVisible(loadMoreResultsButton);
    }

    @Step("Getting clients first name from results by index '{index}'")
    public String getClientFirstNameFromResultByIndex(int index) {
        waitForElementVisibility(clientNameFromResultByIndex, index);
        return getElementText(clientNameFromResultByIndex, index).split(" ")[0];
    }

    @Step("Getting clients first name from results by index '{index}'")
    public String getClientLastNameFromResultByIndex(int index) {
        waitForElementVisibility(clientNameFromResultByIndex, index);
        return getElementText(clientNameFromResultByIndex, index).split(" ")[1];
    }

    @Step("Getting clients id from results by index '{index}'")
    public String getClientIDFromResultByIndex(int index) {
        waitForElementVisibility(clientIDFromResultByIndex, index);
        return getElementText(clientIDFromResultByIndex, index);
    }

    @Step("Getting clients basicinformation from results by index '{index}'")
    public String getClientTypeFromResultByIndex(int index) {
        waitForElementVisibility(clientTypeFromResultByIndex, index);
        return getElementText(clientTypeFromResultByIndex, index);
    }

    @Step("Getting clients basicinformation from results by index '{index}'")
    public String getClientAddressFromResultByIndex(int index) {
        waitForElementVisibility(clientAddressFromResultByIndex, index);
        return getElementText(clientAddressFromResultByIndex, index);
    }

    @Step("Getting clients AKA from results by index '{index}'")
    public String getClientAKAFromResultByIndex(int index) {
        waitForElementVisibility(clientAKAFromResultByIndex, index);
        return getElementText(clientAKAFromResultByIndex, index);
    }

    @Step("Getting account numbers from search results")
    public List<String> getAccountNumbersFromSearchResults() {
        waitForElementVisibility(accountNumberSearchResults);
        return getElementsText(accountNumberSearchResults)
                .stream()
                .map(n -> n.split(" - ")[1])
                .collect(Collectors.toList());
    }

    @Step("Getting individualClient ids from search results")
    public List<String> getClientIDsFromSearchResults() {
        waitForElementVisibility(searchResults);
        return getElementsText(searchResults).stream()
                .filter(result -> !result.contains("-"))
                .map(result -> result.split("\n")[1])
                .collect(Collectors.toList());
    }
}
