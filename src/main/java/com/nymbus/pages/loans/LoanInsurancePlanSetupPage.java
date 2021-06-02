package com.nymbus.pages.loans;

import com.nymbus.core.base.PageTools;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class LoanInsurancePlanSetupPage extends PageTools {

    private final By addNewButton = By.xpath("(//a[text()='Add New'])[1]");
    private final By searchBox = By.xpath("//input[@name='field[search]']");
    private final By searchButton = By.xpath("//button[contains(string(),'Search')]");
    private final By rows = By.xpath("//tr");

    @Step("Click the 'Add new' button")
    public void clickAddNewButton() {
        waitForElementClickable(addNewButton);
        click(addNewButton);
    }

    @Step("Search company by code")
    public void searchPlan(String code){
        waitForElementVisibility(searchBox);
        type(code, searchBox);
        click(searchButton);
    }

    public boolean checkExistPlan() {
        SelenideTools.sleep(Constants.MINI_TIMEOUT);
        waitForElementVisibility(rows);
        return getElements(rows).size()>1;
    }
}
