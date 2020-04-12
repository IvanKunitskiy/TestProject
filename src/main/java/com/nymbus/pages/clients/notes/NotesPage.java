package com.nymbus.pages.clients.notes;

import com.nymbus.core.base.PageTools;
import com.nymbus.core.utils.SelenideTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.util.List;

public class NotesPage extends PageTools {

    private By addNewNote = By.xpath("//button[@data-test-id='action-addNewNote']");
    private By newNoteTextArea = By.xpath("//textarea[@data-test-id='field-notes']");
    private By saveNoteButton = By.xpath("//button[@data-test-id='action-saveSelectedNote']");
    private By noteSelector = By.xpath("//ul/li/div/div[contains(text(), '%s')]");
    private By editButton = By.xpath("//button[@data-test-id='action-editNote']");
    private By deleteButton = By.xpath("//button[@data-test-id='action-deleteNote']");
    private By clearResponsibleOfficerIcon = By.xpath("//button[@data-test-id='action-unassignResponsibleOfficer']");
    private By responsibleOfficerSelected = By.xpath("//div[@data-test-id='form-note']/div//h4");
    private By responsibleOfficerSelectorButton = By.xpath("//div[@data-test-id='field-assignedTo']");
    private By responsibleOfficerList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private By responsibleOfficerSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private By dueDateField = By.xpath("//input[@id='duedate']");
    private By dueDateValueInViewMode = By.xpath("//article[@ui-view='selectedNote']//div/div/ul/li//div[contains(@class, 'itemMeta')]");
    private By responsibleOfficerValueInViewMode = By.xpath("//article[@ui-view='selectedNote']/div/div/div/h4[contains(@class, 'ng-binding')]");
    private By dueDateOfTheActiveNote = By.xpath("//ul[@ui-view='notesList']/li[contains(@class, 'active')]/div[contains(@ng-if, 'duedate')]");
    private By severitySelectorButton = By.xpath("//div[@data-test-id='field-severity']");
    private By severityList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private By severitySelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    @Step("Click the 'Responsible Officer' option")
    public void clickSeveritySelectorOption(String severityOption) {
        waitForElementVisibility(severitySelectorOption, severityOption);
        waitForElementClickable(severitySelectorOption, severityOption);
        click(severitySelectorOption, severityOption);
    }

    @Step("Returning list of 'Severity' options")
    public List<String> getSeverityList() {
        waitForElementVisibility(severityList);
        waitForElementClickable(severityList);
        return getElementsText(severityList);
    }

    @Step("Click the 'Severity' selector button")
    public void clickSeveritySelectorButton() {
        click(severitySelectorButton);
    }

    @Step("Get 'Due date' value of the active note in view mode")
    public String getDueDateOfTheActiveNote() {
        waitForElementVisibility(dueDateOfTheActiveNote);
        return getElementText(dueDateOfTheActiveNote).replaceAll("[^0-9/]", "");
    }

    @Step("Click 'Delete' button")
    public void clickDeleteButton() {
        waitForElementVisibility(deleteButton);
        waitForElementClickable(deleteButton);
        click(deleteButton);
    }

    @Step("Get 'Responsible Officer' value in view mode")
    public String getResponsibleOfficerValueInViewMode() {
        waitForElementVisibility(responsibleOfficerValueInViewMode);
        return getElementText(responsibleOfficerValueInViewMode).trim();
    }

    @Step("Get 'Due date' value in view mode")
    public String getDueDateValueInViewMode() {
        waitForElementVisibility(dueDateValueInViewMode);
        return getElementAttributeValue("innerText", dueDateValueInViewMode).replaceAll("[^0-9/]", "");
    }

    @Step("Click the 'Responsible Officer' option")
    public void clickResponsibleOfficerSelectorOption(String responsibleOfficerOption) {
        waitForElementVisibility(responsibleOfficerSelectorOption, responsibleOfficerOption);
        waitForElementClickable(responsibleOfficerSelectorOption, responsibleOfficerOption);
        click(responsibleOfficerSelectorOption, responsibleOfficerOption);
    }

    @Step("Returning list of 'Responsible Officer' options")
    public List<String> getResponsibleOfficerList() {
        waitForElementVisibility(responsibleOfficerList);
        waitForElementClickable(responsibleOfficerList);
        return getElementsText(responsibleOfficerList);
    }

    @Step("Click the 'Responsible Officer' selector button")
    public void clickResponsibleOfficerSelectorButton() {
        click(responsibleOfficerSelectorButton);
    }

    @Step("Set 'Date Opened' value")
    public void setDueDateValue(String date) {
        waitForElementVisibility(dueDateField);
        waitForElementClickable(dueDateField);
        typeWithoutWipe("", dueDateField);
        SelenideTools.sleep(1);
        typeWithoutWipe(date, dueDateField);
    }

    @Step("Is 'Responsible Officer' selected")
    public boolean isResponsibleOfficerSelected() {
        return isElementVisible(responsibleOfficerSelected);
    }

    @Step("Remove Responsible officer from note")
    public void clickClearResponsibleOfficerIcon() {
        click(clearResponsibleOfficerIcon);
    }

    @Step("Click 'Add new note' button")
    public void clickAddNewNoteButton() {
        waitForElementVisibility(addNewNote);
        waitForElementClickable(addNewNote);
        click(addNewNote);
    }

    @Step("Type text to 'New Note' text area")
    public void typeToNewNoteTextArea(String text) {
        waitForElementVisibility(newNoteTextArea, text);
        waitForElementClickable(newNoteTextArea, text);
        type(text, newNoteTextArea);
    }

    @Step("Click the 'Save' button")
    public void clickSaveButton() {
        waitForElementVisibility(saveNoteButton);
        waitForElementClickable(saveNoteButton);
        SelenideTools.sleep(1);
        click(saveNoteButton);
    }

    @Step("Wait for 'Add new note' button is visible")
    public void waitForAddNewNoteButton() {
        waitForElementVisibility(addNewNote);
        waitForElementClickable(addNewNote);
    }

    @Step("Wait for 'Responsible Officer' input is visible")
    public void waitResponsibleOfficerInput() {
        waitForElementVisibility(responsibleOfficerSelectorButton);
    }

    @Step("Click the note in list by its name")
    public void clickNoteByName(String noteName) {
        waitForElementVisibility(noteSelector, noteName);
        waitForElementClickable(noteSelector, noteName);
        click(noteSelector, noteName);
    }

    @Step("Click the note in list by its name")
    public void clickEditButton() {
        waitForElementVisibility(editButton);
        waitForElementClickable(editButton);
        click(editButton);
    }

}
