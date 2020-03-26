package com.nymbus.pages.clients;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class ClientsSearchPage extends PageTools {
    private By addNewClientButton = By.xpath("//a[@*='action-addNewCustomer']");
    private By clientsSearchInputField = By.xpath("//input[@type='search']");
    private By searchButton = By.xpath("//button[text()='Search']");
    private By lookupResults = By.xpath("//div[contains(text(),'View')]/preceding-sibling::*[1]");
    private By lookupResultByIndex = By.xpath("(//div[contains(text(),'View')]/preceding-sibling::*[1])[%s]");
    private By loadMoreResultsButton = By.xpath("//div[text()='Load More Results']");
    private By clearSearchInputFieldButton = By.xpath("//button[@class='btn btn-link btnIcon']");
    private By viewAccountButtonByValue = By.xpath("//div[contains(text(),'%s')]/parent::div");

    @Step("Wait for 'Add new client' button")
    public void waitForAddNewClientButton() {
        waitForElementVisibility(addNewClientButton);
    }

    @Step("Type '{client}' to clients input field")
    public void typeToClientsSearchInputField(String client) {
        waitForElementVisibility(clientsSearchInputField);
        type(client, clientsSearchInputField);
    }

    @Step("Click on 'Search' button")
    public void clickOnSearchButton() {
        waitForElementVisibility(searchButton);
        click(searchButton);
    }

    @Step("Getting lookup results count")
    public int getLookupResultsCount() {
        waitForElementVisibility(lookupResults);
        return getElements(lookupResults).size();
    }

    @Step("Getting clients first name from lookup results by index '{index}'")
    public String getClientFirstNameFromLookupResultByIndex(int index) {
        waitForElementVisibility(lookupResultByIndex, index);
        return getElementText(lookupResultByIndex, index).split(", ")[1];
    }

    @Step("Getting clients first name from lookup results by index '{index}'")
    public String getClientLastNameFromLookupResultByIndex(int index) {
        waitForElementVisibility(lookupResultByIndex, index);
        return getElementText(lookupResultByIndex, index).split(", ")[0];
    }

    @Step("Getting all lookup results")
    public List<String> getAllLookupResults() {
        waitForElementVisibility(lookupResults);
        return getElements(lookupResults).stream().map(WebElement::getText).collect(Collectors.toList());
    }

    @Step("Verify is 'Load Mode Results' button visible")
    public boolean isLoadMoreResultsButtonVisible() {
        return isElementVisible(loadMoreResultsButton);
    }

    @Step("Click on Search Input Field 'Clear' button")
    public void clickOnSearchInputFieldClearButton() {
        waitForElementVisibility(clearSearchInputFieldButton);
        click(clearSearchInputFieldButton);
    }

    @Step("Click 'Add new client' button")
    public void clickAddNewClient() {
        waitForElementVisibility(addNewClientButton);
        waitForElementClickable(addNewClientButton);
        click(addNewClientButton);
    }

    @Step("Click on 'View Account' button by value {value}")
    public void clickOnViewAccountButtonByValue(String value) {
        waitForElementVisibility(viewAccountButtonByValue, value);
        waitForElementClickable(viewAccountButtonByValue, value);
        click(viewAccountButtonByValue, value);
    }

    @Step("Check that all results in search list are relevant")
    public boolean isSearchResultsRelative(List<String> listOfSearchResults, String query) {
        int match = 0;
        for (String client : listOfSearchResults) {
            if (client.contains(query)) {
                match++;
            }
        }
        return listOfSearchResults.size() == match;
    }
}
