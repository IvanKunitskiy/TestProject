package com.nymbus.pages.loans;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class AddNewLoanInsurancePlanSetupPage extends PageTools {
    private final By code = By.xpath("//input[@name='field[(databean)code]']");
    private final By planDescription = By.xpath("//input[@name='field[(databean)name]']");
    private final By companySelector = By.xpath("//input[@name='field[insurancecompanyid]']/../input");
    private final By companyOption = By.xpath("//a[contains(string(),'%s')]");
    private final By insuranceTypeSelector = By.xpath("//input[@name='field[insurancetype]']/../input");
    private final By insuranceTypeOption = By.xpath("(//a[contains(string(),'%s')])[2]");
    private final By formulaSelector = By.xpath("//input[@name='field[calculationformula]']/../input");
    private final By formulaOption = By.xpath("//a[contains(string(),'%s')]");
    private final By minimumEarnings = By.xpath("//input[@data-widget='auto_numeric']");
    private final By maximumRebate = By.xpath("(//input[@data-widget='auto_numeric'])[2]");
    private final By date = By.xpath("//input[@class='datebox']");
    private final By saveChangesButton = By.xpath("//button[span[text()='Save Changes']]");

    @Step("Input 'Code'")
    public void inputCode(String code) {
        waitForElementVisibility(this.code);
        type(code, this.code);
    }

    @Step("Input 'Name'")
    public void inputDescription(String description) {
        waitForElementVisibility(this.planDescription);
        type(description, this.planDescription);
    }

    @Step("Input 'Company'")
    public void inputCompany(String company) {
        waitForElementVisibility(companySelector);
        click(companySelector);
        waitForElementVisibility(companyOption, company);
        click(companyOption, company);
    }

    @Step("Input 'Insurance Type'")
    public void inputInsuranceType(String type) {
        waitForElementVisibility(insuranceTypeSelector);
        click(insuranceTypeSelector);
        waitForElementVisibility(insuranceTypeOption, type);
        click(insuranceTypeOption, type);
    }

    @Step("Input 'Formula'")
    public void inputFormula(String formula) {
        waitForElementVisibility(formulaSelector);
        click(formulaSelector);
        waitForElementVisibility(formulaOption, formula);
        click(formulaOption, formula);
    }

    @Step("Click 'Save Changes' button")
    public void clickSaveChangesButton() {
        waitForElementClickable(saveChangesButton);
        click(saveChangesButton);
    }

    @Step("Input 'Minimum Earnings'")
    public void inputMinimumEarnings(String earnings) {
        waitForElementVisibility(minimumEarnings);
        type(earnings, minimumEarnings);
    }

    @Step("Input 'Maximum Rebate'")
    public void inputMaximumRebate(String maximum) {
        waitForElementVisibility(maximumRebate);
        type(maximum, maximumRebate);
    }

    @Step("Input 'Date'")
    public void inputDate(String date) {
        waitForElementVisibility(this.date);
        click(this.date);
        type(date, this.date);
    }
}
