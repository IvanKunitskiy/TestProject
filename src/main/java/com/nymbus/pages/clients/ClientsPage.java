package com.nymbus.pages.clients;

import com.nymbus.base.BasePage;
import com.nymbus.locator.Locator;
import com.nymbus.locator.XPath;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class ClientsPage extends BasePage {
    private Locator addNewClientButton = new XPath("//a[@*='action-addNewCustomer']");
    private Locator clientsSearchInputField = new XPath("//input[@type='search']");
    private Locator searchButton = new XPath("//button[text()='Search']");
    private Locator lookupResults = new XPath("//div[contains(text(),'View')]/preceding-sibling::*[1]");
    private Locator lookupResultByIndex = new XPath("(//div[contains(text(),'View')]/preceding-sibling::*[1])[%s]");
    private Locator loadMoreResultsButton = new XPath("//div[text()='Load More Results']");
    private Locator clearSearchInputFieldButton = new XPath("//button[@class='btn btn-link btnIcon']");
    private Locator viewAccountButtonByValue = new XPath("//div[contains(text(),'%s')]/parent::div");

    @Step("Wait for 'Add new individualClient' button")
    public void waitForAddNewClientButton(){
        waitForElementVisibility(addNewClientButton);
    }

    @Step("Type '{individualClient}' to clients input field")
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
        wait(1);
    }

    @Step("Click 'Add new individualClient' button")
    public void clickAddNewClient(){
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
