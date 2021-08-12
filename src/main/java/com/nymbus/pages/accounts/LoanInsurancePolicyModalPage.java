package com.nymbus.pages.accounts;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class LoanInsurancePolicyModalPage extends PageTools {

    private final By columns = By.xpath("//td");
    private final By dateIssued = By.xpath("//input[@name='dateissued']");
    private final By maturityDate = By.xpath("//input[@name='maturitydate']");
    private final By premium = By.xpath("//input[@name='premium']");
    private final By premiumEarned = By.xpath("//input[@name='premiumearned']");
    private final By premiumRefunded = By.xpath("//input[@name='premiumrefunded']");
    private final By company = By.xpath("//div[@data-test-id='field-company']/a");
    private final By insuranceType = By.xpath("//div[@data-test-id='field-insurancetype']/a");
    private final By plan = By.xpath("//div[@data-test-id='field-plannumber']/a");
    private final By option = By.xpath("//span[text()='%s']");
    private final By secondOption = By.xpath("(//span[text()='%s'])[2]");
    private final By saveButton = By.xpath("//button[contains(string(),'Save')]");

    @Step("Get count of columns")
    public int getCountOfColumns(){
        waitForElementVisibility(columns);
        return getElements(columns).size();
    }

    @Step("Check is date issued is disabled")
    public String getDateIssuedDisabled(){
        waitForElementVisibility(dateIssued);
        return getDisabledElementAttributeValue("value", dateIssued);
    }

    @Step("Check is maturity date is disabled")
    public String getMaturityDateDisabled(){
        waitForElementVisibility(maturityDate);
        return getDisabledElementAttributeValue("value", maturityDate);
    }

    @Step("Check is premium is disabled")
    public String getPremiumDisabled(){
        waitForElementVisibility(premium);
        return getDisabledElementAttributeValue("value", premium);
    }

    @Step("Check is premium earned is disabled")
    public String getPremiumEarnedDisabled(){
        waitForElementVisibility(premiumEarned);
        return getDisabledElementAttributeValue("value", premiumEarned);
    }

    @Step("Check is premium refunded is disabled")
    public String getPremiumRefundedDisabled(){
        waitForElementVisibility(premiumRefunded);
        return getDisabledElementAttributeValue("value", premiumRefunded);
    }

    @Step("Input Company")
    public void inputCompany(String text){
        waitForElementVisibility(company);
        click(company);
        click(option,text);
    }

    @Step("Input Company")
    public void inputType(String text){
        waitForElementVisibility(insuranceType);
        click(insuranceType);
        click(option,text);
    }

    @Step("Input Company")
    public void inputPlan(String text){
        waitForElementVisibility(plan);
        click(plan);
        click(secondOption,text);
    }

    @Step("Click save button")
    public void clickSave(){
        waitForElementVisibility(saveButton);
        click(saveButton);
    }

}
