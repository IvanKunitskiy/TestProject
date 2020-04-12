package com.nymbus.pages.clients.documents;

import com.codeborne.selenide.Selenide;
import com.nymbus.core.base.PageTools;
import com.nymbus.core.utils.SelenideTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.util.List;

public class AddNewDocumentPage extends PageTools {
    private By saveChangesButton = By.xpath("//button[contains(text(), 'Save Changes')]");
    private By documentationField = By.xpath("//td[@class='customerContainerImage']/section/div/p//input[@type='file']");
    private By idNumberInput = By.xpath("//input[@id='id']");
    private By issueDate = By.xpath("//input[@data-test-id='field-issued']");
    private By expirationDate = By.xpath("//input[@data-test-id='field-expiration']");

    private By idTypeSelectorButton = By.xpath("//div[@data-test-id='field-type']");
    private By idTypeList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private By idTypeSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private By issuedBySelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private By issuedBySelectorButton = By.xpath("//div[@data-test-id='field-state']");
    private By issuedByList = By.xpath("//li[contains(@role, 'option')]/div/span");

    private By countrySelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private By countrySelectorButton = By.xpath("//div[@data-test-id='field-country']");
    private By countryList = By.xpath("//li[contains(@role, 'option')]/div/span");

    @Step("Click the 'Save Changes' button")
    public void clickSaveChangesButton() {
        waitForElementVisibility(saveChangesButton);
        waitForElementClickable(saveChangesButton);
        click(saveChangesButton);
    }

    @Step("Upload new document")
    public void uploadNewDocument(String clientDocument) {
        waitForElementVisibility(saveChangesButton);
        waitForElementClickable(saveChangesButton);
        Selenide.executeJavaScript("arguments[0].style.height='auto'; arguments[0].style.visibility='visible';",
                getWebElement(documentationField));
        type(clientDocument, documentationField);
        Selenide.executeJavaScript("arguments[0].style.height='auto'; arguments[0].style.visibility='hidden';",
                getWebElement(documentationField));
    }

    @Step("Click the 'ID Type' option")
    public void clickIdTypeSelectorOption(String idOption) {
        waitForElementVisibility(idTypeSelectorOption, idOption);
        waitForElementClickable(idTypeSelectorOption, idOption);
        click(idTypeSelectorOption, idOption);
    }

    @Step("Returning list of 'ID Type' options")
    public List<String> getIdTypeList() {
        waitForElementVisibility(idTypeList);
        waitForElementClickable(idTypeList);
        return getElementsText(idTypeList);
    }

    @Step("Click the 'ID Type' selector button")
    public void clickIdTypeSelectorButton() {
        waitForElementVisibility(idTypeSelectorButton);
        scrollToElement(idTypeSelectorButton);
        waitForElementClickable(idTypeSelectorButton);
        click(idTypeSelectorButton);
    }

    @Step("Click on 'Issued By' selector")
    public void clickIssuedBySelectorButton() {
        waitForElementClickable(issuedBySelectorButton);
        click(issuedBySelectorButton);
    }

    @Step("Returning list of 'Issued By'")
    public List<String> getIssuedByList() {
        waitForElementVisibility(issuedByList);
        waitForElementClickable(issuedByList);
        return getElementsText(issuedByList);
    }

    @Step("Click on 'Document Issued By' option")
    public void clickIssuedByOption(String issuedByOption) {
        waitForElementVisibility(issuedBySelectorOption, issuedByOption);
        waitForElementClickable(issuedBySelectorOption, issuedByOption);
        click(issuedBySelectorOption, issuedByOption);
    }

    @Step("Click the 'Country' selector")
    public void clickCountrySelectorButton() {
        waitForElementClickable(countrySelectorButton);
        click(countrySelectorButton);
    }

    @Step("Returning list of Country")
    public List<String> getCountryList() {
        waitForElementVisibility(countryList);
        waitForElementClickable(countryList);
        return getElementsText(countryList);
    }

    @Step("Click the 'Country' option")
    public void clickDocumentCountryOption(String countryOption) {
        waitForElementVisibility(countrySelectorOption, countryOption);
        waitForElementClickable(countrySelectorOption, countryOption);
        click(countrySelectorOption, countryOption);
    }

    @Step("Type value to 'ID Number' input")
    public void typeValueToIDNumberField(String value) {
        waitForElementVisibility(idNumberInput);
        waitForElementClickable(idNumberInput);
        type(value, idNumberInput);
    }

    @Step("Set 'Issue Date' value")
    public void setIssueDateValue(String date) {
        waitForElementVisibility(issueDate);
        waitForElementClickable(issueDate);
        typeWithoutWipe("", issueDate);
        SelenideTools.sleep(1);
        typeWithoutWipe(date, issueDate);
    }

    @Step("Set 'Expiration Date' value")
    public void setExpirationDateValue(String date) {
        waitForElementVisibility(expirationDate);
        waitForElementClickable(expirationDate);
        typeWithoutWipe("", expirationDate);
        SelenideTools.sleep(1);
        typeWithoutWipe(date, expirationDate);
    }
}
