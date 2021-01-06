package com.nymbus.pages;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class CashierDefinedActionsPage extends PageTools {

    private By tellerOperation =By.xpath("//span[contains(string(),'Select Operation')]");
    private By operationSelector = By.xpath("//span[contains(string(),'%s')]");

    @Step("Click 'Select Operation' popup")
    public void clickSelectOperation(){
        waitForElementVisibility(tellerOperation);
        jsClick(tellerOperation);
    }

    @Step("Select operation")
    public void selectOperation(String type){
        waitForElementVisibility(operationSelector,type);
        jsClick(operationSelector, type);
    }

}
