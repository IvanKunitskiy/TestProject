package com.nymbus.pages.loans;

import com.nymbus.core.base.PageTools;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class LoanParticipantsPage extends PageTools {

    private By testCode = By.xpath("//td[@data-column-id='code' and contains(string(),'autotest')]");
    private By addNewButton = By.xpath("//a[contains(string(),'Add New')]");
    private By partCode = By.xpath("//input[@name='field[(databean)code]']");
    private By partName = By.xpath("//input[@name='field[(databean)name]']");
    private By partEIN = By.xpath("//input[@name='field[ein]']");
    private By partRoutNumber = By.xpath("//input[@name='field[routingnumber]']");
    private By addressLine1 = By.xpath("//input[@name='field[addressline1]']");
    private By city = By.xpath("//input[@name='field[city]']");
    private By zip = By.xpath("//input[@name='field[zip]']");
    private By phone = By.xpath("//input[@name='field[phone]']");
    private By email = By.xpath("//input[@name='field[email]']");
    private By stateSelector = By.xpath("//input[@name='field[state]']/..");
    private By state = By.xpath("//li[@role='presentation']/a[contains(string(),'%s')]");
    private By inputGL = By.xpath("//input[@name='field[glaccountnumber]']/../input");
    private By gLAccount = By.xpath("//li[@role='presentation']/a[contains(string(),'%s')]");
    private By saveButton = By.xpath("//button[contains(string(),'Save')]");


    @Step("Is participant exist")
    public boolean isTestCodeExists() {
        SelenideTools.sleep(Constants.MICRO_TIMEOUT);
        return getElementsWithZeroOption(testCode).size() > 0;
    }

    @Step("Click 'Add New' button")
    public void clickAddNew() {
        waitForElementClickable(addNewButton);
        click(addNewButton);
    }

    @Step("Input 'Participant Code'")
    public void inputParticipantCode(String text) {
        waitForElementVisibility(partCode);
        type(text, partCode);
    }

    @Step("Input 'Participant Name'")
    public void inputParticipantName(String text) {
        waitForElementVisibility(partName);
        type(text, partName);
    }

    @Step("Input 'Participant EIN'")
    public void inputParticipantEIN(String text) {
        waitForElementVisibility(partEIN);
        type(text, partEIN);
    }

    @Step("Input 'Participant Rout Number'")
    public void inputParticipantRoutNumber(String text) {
        waitForElementVisibility(partRoutNumber);
        type(text, partRoutNumber);
    }

    @Step("Input 'Participant Address Line1'")
    public void inputParticipantAddressLine1(String text) {
        waitForElementVisibility(addressLine1);
        type(text, addressLine1);
    }

    @Step("Input 'Participant City'")
    public void inputParticipantCity(String text) {
        waitForElementVisibility(city);
        type(text, city);
    }

    @Step("Input 'Participant ZIP'")
    public void inputParticipantZip(String text) {
        waitForElementVisibility(zip);
        type(text, zip);
    }

    @Step("Input 'Participant Phone'")
    public void inputParticipantPhone(String text) {
        waitForElementVisibility(phone);
        type(text, phone);
    }

    @Step("Input 'Participant Email'")
    public void inputParticipantEmail(String text) {
        waitForElementVisibility(email);
        type(text, email);
    }

    @Step("Input 'Participant State'")
    public void inputParticipantState(String state) {
        waitForElementVisibility(stateSelector);
        click(stateSelector);
        waitForElementVisibility(this.state, state);
        click(this.state, state);
    }

    @Step("Input 'Participant GL account'")
    public void inputParticipantGLAccount(String text) {
        waitForElementVisibility(inputGL);
        type(text, inputGL);
        waitForElementVisibility(gLAccount, text);
        click(gLAccount, text);
    }

    @Step("Click 'Save' button")
    public void clickSaveButton() {
        waitForElementClickable(saveButton);
        click(saveButton);
        SelenideTools.sleep(Constants.MICRO_TIMEOUT);
    }

    private final By loanParticipantNameByIndex = By.xpath("//table//tr[contains(@class, 'xwidget_grid_row')][%s]/td[@data-column-id='name']");
    private final By loanParticipantName= By.xpath("//table//tr[contains(@class, 'xwidget_grid_row')]/td[@data-column-id='name']");

    public String getLoanParticipantNameByIndex(int index) {
        return getElementText(loanParticipantNameByIndex, index);
    }

    public int getLoanParticipantCount() {
        return getElementsText(loanParticipantName).size();
    }
}
