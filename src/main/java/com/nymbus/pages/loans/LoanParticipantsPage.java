package com.nymbus.pages.loans;

import com.nymbus.core.base.PageTools;
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


    @Step("Is participant exist")
    public boolean isTestCodeExists(){
        return getElements(testCode).size() > 0;
    }

    @Step("Click 'Add New' button")
    public void clickAddNew(){
        waitForElementClickable(addNewButton);
        click(addNewButton);
    }
}
