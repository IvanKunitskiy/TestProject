package com.nymbus.pages.accounts;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class AccountLoanInsurancePoliciesPage extends PageTools {

    private final By addNewButton = By.xpath("//span[contains(text(), 'Add new')]");
    private final By company = By.xpath("//tr");

    @Step("Click 'Add New' button")
    public void clickAddNewButton(){
        waitForElementVisibility(addNewButton);
        waitForElementClickable(addNewButton);
        click(addNewButton);
    }

    @Step("Check company")
    public boolean isCompanyVisible(){
        waitForElementVisibility(company);
        return getElements(company).size()>0;
    }
}
