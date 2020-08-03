package com.nymbus.pages.clients.documents;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class DocumentsPage extends PageTools {

    private By addNewDocumentButton = By.xpath("//button[span[contains(text(), 'Add New Document')]]");
    private By editButtonByDocumentIDSelector = By.xpath("//tr[.//td[contains(text(), '%s')]]//button");
    private By editButton = By.xpath("//*[contains(text(), 'Edit')]");
    private By documentRowByDocumentIDSelector = By.xpath("//table//tr[td[contains(text(), '%s')]]");
    private By selectCheckboxByDocumentIDSelector = By.xpath("//tr[.//td[contains(text(), '%s')]]//input[@data-test-id='action-select']");
    private By deleteButton = By.xpath("//button/span[contains(text(), 'Delete')]");
    private By restoreTooltip = By.xpath("//div[@id='toast-container']/div/div/div[contains(text(), 'The document has been removed. Click to restore.')]");
    private By saveChangesButton = By.xpath("//button[contains(text(), 'Save Changes')]");

    @Step("Check that document with 'Document Type' is present in the list")
    public boolean isDocumentTypePresentInTheList(String type) {
        waitForElementVisibility(documentRowByDocumentIDSelector, type);
        waitForElementClickable(documentRowByDocumentIDSelector, type);
        return isElementVisible(documentRowByDocumentIDSelector, type);
    }

    @Step("Check that document with 'Category' is present in the list")
    public boolean isDocumentCategoryPresentInTheList(String category) {
        waitForElementVisibility(documentRowByDocumentIDSelector, category);
        waitForElementClickable(documentRowByDocumentIDSelector, category);
        return isElementVisible(documentRowByDocumentIDSelector, category);
    }

    @Step("Check that document with 'Document ID' is present in the list")
    public boolean isDocumentIdPresentInTheList(String documentID) {
        waitForElementVisibility(documentRowByDocumentIDSelector, documentID);
        waitForElementClickable(documentRowByDocumentIDSelector, documentID);
        return isElementVisible(documentRowByDocumentIDSelector, documentID);
    }

    @Step("Click the 'Restore' tooltip")
    public void clickRestoreTooltip() {
        waitForElementClickable(restoreTooltip);
        click(restoreTooltip);
    }

    @Step("Click 'Add New Document' button")
    public void clickAddNewDocumentButton() {
        waitForElementVisibility(addNewDocumentButton);
        waitForElementClickable(addNewDocumentButton);
        click(addNewDocumentButton);
    }

    @Step("Click 'Add New Document' button with js")
    public void clickAddNewDocumentButtonWithJs() {
        waitForElementVisibility(addNewDocumentButton);
        jsClick(addNewDocumentButton);
    }

    @Step("Click 'Edit' button for document by its 'Document ID'")
    public void clickEditButtonByDocumentID(String documentID) {
        waitForElementVisibility(editButtonByDocumentIDSelector, documentID);
        waitForElementClickable(editButtonByDocumentIDSelector, documentID);
        click(editButtonByDocumentIDSelector, documentID);
        clickEditButton();
    }

    @Step("Click 'Edit' button for document by its 'Document Type'")
    public void clickEditButtonByDocumentType(String documentType) {
        waitForElementVisibility(editButtonByDocumentIDSelector, documentType);
        waitForElementClickable(editButtonByDocumentIDSelector, documentType);
        click(editButtonByDocumentIDSelector, documentType);
        clickEditButton();
    }

    @Step("Click 'Edit' button")
    public void clickEditButton() {
        waitForElementVisibility(editButton);
        waitForElementClickable(editButton);
        click(editButton);
    }

    @Step("Click document row by its 'Document ID'")
    public void clickDocumentRowByDocumentIDSelector(String documentID) {
        waitForElementVisibility(documentRowByDocumentIDSelector, documentID);
        waitForElementClickable(documentRowByDocumentIDSelector, documentID);
        click(documentRowByDocumentIDSelector, documentID);
    }

    @Step("Click document row by its 'Document ID'")
    public void clickCheckboxByDocumentIDSelector(String documentID) {
        waitForElementVisibility(selectCheckboxByDocumentIDSelector, documentID);
        waitForElementClickable(selectCheckboxByDocumentIDSelector, documentID);
        click(selectCheckboxByDocumentIDSelector, documentID);
    }

    @Step("Click document row by its 'Document Type'")
    public void clickCheckboxByDocumentType(String documentType) {
        waitForElementVisibility(selectCheckboxByDocumentIDSelector, documentType);
        waitForElementClickable(selectCheckboxByDocumentIDSelector, documentType);
        click(selectCheckboxByDocumentIDSelector, documentType);
    }

    @Step("Click 'Delete' button")
    public void clickDeleteButton() {
        waitForElementVisibility(deleteButton);
        waitForElementClickable(deleteButton);
        click(deleteButton);
    }
}
