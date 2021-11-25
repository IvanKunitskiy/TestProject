package com.nymbus.pages.backoffice;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class BatchProcessingPage extends PageTools {

    private final By tellerInput = By.xpath("//a[@placeholder='Teller']");
    private final By tellerOption = By.xpath("(//div[contains(string(),'%s')]/span)[2]");
    private final By searchButton = By.xpath("//button[@type='submit']");
    private final By status = By.xpath("//span[@ng-if='isCorrectStatus(batch)']");

    @Step("Click Teller input")
    public void clickTellerSearch(){
        waitForElementVisibility(tellerInput);
        click(tellerInput);
    }

    @Step("Click Teller option")
    public void clickTellerOption(String teller){
        waitForElementVisibility(tellerOption, teller);
        click(tellerOption, teller);
    }

    @Step("Click Submit button")
    public void clickSearchButton(){
        waitForElementVisibility(searchButton);
        click(searchButton);
    }

    @Step("Get status")
    public String getStatusTransaction(){
        waitForElementVisibility(status);
        return getElementText(status);
    }


}
