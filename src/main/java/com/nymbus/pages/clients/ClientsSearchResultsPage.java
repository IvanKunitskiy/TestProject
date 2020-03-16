package com.nymbus.pages.clients;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.util.List;
import java.util.stream.Collectors;

public class ClientsSearchResultsPage extends PageTools {
    private By searchResults = By.xpath("//div[contains(@ng-repeat,'item')]");
    private By accountNumberSearchResults = By.xpath("//div/span[@class='spaceRight ng-binding']");
    private By loadMoreResultsButton = By.xpath("//button[text()='Load More Results']");
    private By clientNameFromResultByIndex
            = By.xpath("(//div[@class='search-result-container']//div[contains(@class,'table__td')][1])[%s]");
    private By clientIDFromResultByIndex
            = By.xpath("(//div[@class='search-result-container']//div[contains(@class,'table__td')][2])[%s]");
    private By clientTypeFromResultByIndex
            = By.xpath("(//div[@class='search-result-container']//div[contains(@class,'table__td')][3])[%s]");
    private By clientAddressFromResultByIndex
            = By.xpath("(//div[@class='search-result-container']//div[contains(@class,'table__td')][4])[%s]");
    private By clientAKAFromResultByIndex
            = By.xpath("(//div[@class='search-result-container']//div[contains(@class,'table__td')][5])[%s]");
    private By exactMatchInSearchResults = By.xpath("//div[contains(@class, 'selected')]");

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

    @Step("Getting clients type from results by index '{index}'")
    public String getClientTypeFromResultByIndex(int index) {
        waitForElementVisibility(clientTypeFromResultByIndex, index);
        return getElementText(clientTypeFromResultByIndex, index);
    }

    @Step("Getting clients type from results by index '{index}'")
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

    @Step("Getting client ids from search results")
    public List<String> getClientIDsFromSearchResults() {
        waitForElementVisibility(searchResults);
        return getElementsText(searchResults).stream()
                .filter(result -> !result.contains("-"))
                .map(result -> result.split("\n")[1])
                .collect(Collectors.toList());
    }

    @Step("Click the client fromsearch results by order number")
    public void clickTheExactlyMatchedClientInSearchResults() {
        waitForElementVisibility(exactMatchInSearchResults);
        click(exactMatchInSearchResults);
    }

}
