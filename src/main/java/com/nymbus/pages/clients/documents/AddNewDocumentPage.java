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
    private By replaceDocument = By.xpath("//td/div/div/div/input[@type='file']");
    private By uploadAccountDocument = By.xpath("//span[contains(@class, 'actions')]/input[@type='file']");
    private By notesInput = By.xpath("//textarea[@data-test-id='field-description']");
    private By replaceDocumentButton = By.xpath("//button[contains(text(), 'Replace Document')]");
    private By zoomInButton = By.xpath("//button[contains(text(), 'Zoom In')]");
    private By resetButton = By.xpath("//button[contains(text(), 'Reset')]");
    private By replaceDocumentInput = By.xpath("//input[@type='file']");
    private By modalWindow = By.xpath("//div[@class='modal-content']");

    private By idTypeSelectorButton = By.xpath("//div[@data-test-id='field-type']");
    private By idTypeList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private By idTypeSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private By issuedBySelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private By issuedBySelectorButton = By.xpath("//div[@data-test-id='field-state']");
    private By issuedByList = By.xpath("//li[contains(@role, 'option')]/div/span");

    private By countrySelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private By countrySelectorButton = By.xpath("//div[@data-test-id='field-country']");
    private By countryList = By.xpath("//li[contains(@role, 'option')]/div/span");

    private By categorySelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private By categorySelectorButton = By.xpath("//div[@data-test-id='field-parentcategory']");
    private By categoryList = By.xpath("//li[contains(@role, 'option')]/div/span");

    private By docTypeSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private By docTypeSelectorButton = By.xpath("//div[@data-test-id='field-categoryid']");
    private By docTypeList = By.xpath("//li[contains(@role, 'option')]/div/span");

    @Step("Wait for modal invisibility")
    public void waitForModalInvisibility() {
        waitForElementInvisibility(modalWindow);
    }

    @Step("Click 'Add New Document' button")
    public void clickResetButton() {
        waitForElementClickable(resetButton);
        click(resetButton);
    }

    @Step("Click 'Zoom In' button")
    public void clickZoomInButton() {
        waitForElementVisibility(zoomInButton);
        waitForElementClickable(zoomInButton);
        click(zoomInButton);
    }

    @Step("Check 'Reset' button visible on 'Add Account Document' modal")
    public boolean isResetButtonVisible() {
        waitForElementVisibility(resetButton);
        waitForElementClickable(resetButton);
        return isElementVisible(resetButton);
    }

    @Step("Check 'Zoom In' button visible on 'Add Account Document' modal")
    public boolean isZoomInButtonVisible() {
        waitForElementVisibility(zoomInButton);
        waitForElementClickable(zoomInButton);
        return isElementVisible(zoomInButton);
    }

    @Step("Check 'Replace Document' button visible on 'Add Account Document' modal")
    public boolean isReplaceDocumentButtonVisible() {
        waitForElementVisibility(replaceDocumentButton);
        waitForElementClickable(replaceDocumentButton);
        return isElementVisible(replaceDocumentButton);
    }


    @Step("Click the 'Doc Type' option")
    public void clickDocTypeOption(String option) {
        waitForElementVisibility(docTypeSelectorOption, option);
        waitForElementClickable(docTypeSelectorOption, option);
        click(docTypeSelectorOption, option);
    }

    @Step("Returning list of 'Doc Type' options")
    public List<String> getDocTypeList() {
        waitForElementVisibility(docTypeList);
        waitForElementClickable(docTypeList);
        return getElementsText(docTypeList);
    }

    @Step("Click the 'Doc Type' selector button")
    public void clickDocTypeSelectorButton() {
        waitForElementVisibility(docTypeSelectorButton);
        scrollToElement(docTypeSelectorButton);
        waitForElementClickable(docTypeSelectorButton);
        click(docTypeSelectorButton);
    }

    @Step("Click the 'Category' option")
    public void clickCategoryOption(String option) {
        waitForElementVisibility(categorySelectorOption, option);
        waitForElementClickable(categorySelectorOption, option);
        click(categorySelectorOption, option);
    }

    @Step("Returning list of 'Category' options")
    public List<String> getCategoryList() {
        waitForElementVisibility(categoryList);
        waitForElementClickable(categoryList);
        return getElementsText(categoryList);
    }

    @Step("Click the 'Category' selector button")
    public void clickCategorySelectorButton() {
        waitForElementVisibility(categorySelectorButton);
        scrollToElement(categorySelectorButton);
        waitForElementClickable(categorySelectorButton);
        click(categorySelectorButton);
    }

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

    @Step("Replace document")
    public void replaceDocument(String clientDocument) {
        waitForElementVisibility(saveChangesButton);
        waitForElementClickable(saveChangesButton);
        Selenide.executeJavaScript("arguments[0].style.height='auto'; arguments[0].style.visibility='visible';",
                getWebElement(replaceDocument));
        type(clientDocument, replaceDocument);
        Selenide.executeJavaScript("arguments[0].style.height='auto'; arguments[0].style.visibility='hidden';",
                getWebElement(replaceDocument));
    }

    @Step("Upload new account document")
    public void uploadNewAccountDocument(String accountDocument) {
        waitForElementVisibility(saveChangesButton);
        waitForElementClickable(saveChangesButton);
        Selenide.executeJavaScript("arguments[0].style.height='auto'; arguments[0].style.visibility='visible';",
                getWebElement(uploadAccountDocument));
        type(accountDocument, uploadAccountDocument);
        Selenide.executeJavaScript("arguments[0].style.height='auto'; arguments[0].style.visibility='hidden';",
                getWebElement(uploadAccountDocument));
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

    @Step("Type value to 'Notes' input")
    public void typeValueToNotesField(String value) {
        waitForElementVisibility(notesInput);
        waitForElementClickable(notesInput);
        type(value, notesInput);
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
