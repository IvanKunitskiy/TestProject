package com.nymbus.pages.clients;

import com.nymbus.base.BasePage;
import com.nymbus.locator.Locator;
import com.nymbus.locator.XPath;
import io.qameta.allure.Step;

public class ClientsSearchPage extends BasePage {

    private Locator addNewClientButton = new XPath("//a[@*='action-addNewCustomer']");
    private Locator clientsSearchInputField = new XPath("//input[@type='search']");
    private Locator searchButton = new XPath("//button[text()='Search']");
    private Locator lookupResults = new XPath("//div[contains(@class, 'choices')]//div[text()='View Profile']");
    private Locator clientNameFromLookupResultByIndex = new XPath("(//div[contains(@class,'option')]/div[1])[%s]");
    private Locator loadMoreResultsButton = new XPath("//div[text()='Load More Results']");
    private Locator clearSearchInputFieldButton = new XPath("//button[@class='btn btn-link btnIcon']");

    @Step("Wait for 'Add new client' button")
    public void waitForAddNewClientButton(){
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
        waitForElementVisibility(clientNameFromLookupResultByIndex, index);
        return getElementText(clientNameFromLookupResultByIndex, index).split(", ")[1];
    }

    @Step("Getting clients first name from lookup results by index '{index}'")
    public String getClientLastNameFromLookupResultByIndex(int index) {
        waitForElementVisibility(clientNameFromLookupResultByIndex, index);
        return getElementText(clientNameFromLookupResultByIndex, index).split(", ")[0];
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

    @Step("Click 'Add new client' button")
    public void clickAddNewClient(){
        waitForElementVisibility(addNewClientButton);
        waitForElementClickable(addNewClientButton);
        click(addNewClientButton);
    }
}
