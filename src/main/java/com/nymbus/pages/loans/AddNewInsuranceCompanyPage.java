package com.nymbus.pages.loans;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class AddNewInsuranceCompanyPage extends PageTools {
    private final By code = By.xpath("//input[@name='field[(databean)code]']");
    private final By name = By.xpath("//input[@name='field[(databean)name]']");
    private final By glInput = By.xpath("//input[@name='field[glaccountid]']/../input");
    private final By glOption = By.xpath("//a[contains(string(),'%s')]");
    private final By saveChangesButton = By.xpath("//button[span[text()='Save Changes']]");

    @Step("Input 'Code'")
    public void inputCode(String code) {
        waitForElementVisibility(this.code);
        type(code, this.code);
    }

    @Step("Input 'Name'")
    public void inputName(String name) {
        waitForElementVisibility(this.name);
        type(name, this.name);
    }

    @Step("Input 'Gl Account'")
    public void inputGlAccount(String glAccount) {
        waitForElementVisibility(glInput);
        type(glAccount, glInput);
        waitForElementVisibility(glOption, glAccount);
        click(glOption, glAccount);
    }

    @Step("Click 'Save Changes' button")
    public void clickSaveChangesButton() {
        waitForElementClickable(saveChangesButton);
        click(saveChangesButton);
    }
}
