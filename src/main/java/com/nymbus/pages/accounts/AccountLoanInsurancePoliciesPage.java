package com.nymbus.pages.accounts;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class AccountLoanInsurancePoliciesPage extends PageTools {

    private final By addNewButton = By.xpath("//span[contains(text(), 'Add new')]");

    @Step("Click 'Add New' button")
    public void clickAddNewButton(){
        waitForElementVisibility(addNewButton);
        waitForElementClickable(addNewButton);
        click(addNewButton);
    }
}
