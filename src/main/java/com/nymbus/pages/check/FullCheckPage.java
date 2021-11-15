package com.nymbus.pages.check;

import com.nymbus.core.base.PageTools;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class FullCheckPage extends PageTools {
    private By checkType = By.xpath("(//td[@ng-if])[1]");
    private By payee = By.xpath("(//tr[contains(string(),'%s')]/td)[3]/span");
    private By selectPurchaser = By.xpath("//*[@id=\"purchaseraccountnumber\"]/a/span");
    private By purchaser = By.xpath("//*[@id=\"purchaseraccountnumber\"]/a/span[2]/span");
    private By initials = By.xpath("//*[@id=\"operatoruserid\"]/a/span[2]/span");
    private By amount = By.xpath("//*[@id=\"amount\"]");
    private By status = By.xpath("(//td[@ng-if])[7]/span");
    private By date = By.xpath("//*[@id=\"issuedate\"]");
    private By checkNumber = By.xpath("//input[@data-test-id='field-checknumber']");
    private By remitter = By.xpath("//input[@data-test-id='field-remitter']");
    private By phone = By.xpath("//input[@data-test-id='field-purchaserPhone']");
    private By documentType = By.xpath("//input[@data-test-id='field-purchaserIDType']");
    private By documentID = By.xpath("//input[@data-test-id='field-purchaserID']");
    private By branch = By.xpath("//*[@id=\"bankbranchlocationid\"]/a/span[2]/span");
    private By fee = By.xpath("//*[@id=\"checkfee\"]");
    private By cashPurchased = By.xpath("//*[@id=\"iscashpurchase\"]/div/div/span[2]");
    private By yesCashPurchased = By.xpath("//*[@id=\"iscashpurchase\"]/div/div/span[1]");
    private By voidButton = By.xpath("//button[contains(string(),'Void')]");
    private By confirmation = By.xpath("//p[contains(string(),'Do you want to void the selected official check?')]");
    private final By yesButton = By.xpath("//button[span[text()='Yes']]");

    @Step("Get 'Check Type' text")
    public String getCheckType() {
        waitForElementVisibility(checkType);
        return getElementText(checkType);
    }

    @Step("Get 'Payee' text")
    public String getPayee(String number) {
        waitForElementVisibility(payee, number);
        return getElementText(payee, number);
    }

    @Step("Get 'Purchaser' text")
    public String getPurchaser() {
        if (isElementVisible(selectPurchaser)){
            return "";
        }
        waitForElementVisibility(purchaser);
        return getElementText(purchaser);
    }

    @Step("Get 'Inintials' text")
    public String getInitials() {
        waitForElementVisibility(initials);
        return getElementText(initials);
    }

    @Step("Get 'Amount' text")
    public String getAmount() {
        waitForElementVisibility(amount);
        return getDisabledElementAttributeValue("value",amount);
    }

    @Step("Get 'Status' text")
    public String getStatus(){
        SelenideTools.sleep(Constants.MINI_TIMEOUT);
        waitForElementVisibility(status);
        return getElementText(status);
    }

    @Step("Get 'Date' text")
    public String getDate(){
        waitForElementVisibility(date);
        return getDisabledElementAttributeValue("text",date);
    }

    @Step("Get 'Check number' text")
    public String getCheckNumber(){
        waitForElementVisibility(checkNumber);
        return getDisabledElementAttributeValue("text",checkNumber);
    }

    @Step("Get 'Remitter' text")
    public String getRemitter(){
        waitForElementVisibility(remitter);
        return getDisabledElementAttributeValue("value",remitter);
    }

    @Step("Get 'Phone' text")
    public String getPhone(){
        waitForElementVisibility(phone);
        return getDisabledElementAttributeValue("value",phone);
    }

    @Step("Get 'Document type' text")
    public String getDocumentType(){
        waitForElementVisibility(documentType);
        return getDisabledElementAttributeValue("value",documentType);
    }

    @Step("Get 'Document Id' text")
    public String getDocumentID(){
        waitForElementVisibility(documentID);
        return getDisabledElementAttributeValue("value",documentID);
    }

    @Step("Get 'Branch' text")
    public String getBranch(){
        waitForElementVisibility(branch);
        return getElementText(branch);
    }

    @Step("Get 'Fee' text")
    public String getFee(){
        waitForElementVisibility(fee);
        return getDisabledElementAttributeValue("value",fee);
    }

    @Step("Get 'Cash purchased' text")
    public String getCashPurchased(){
        waitForElementVisibility(cashPurchased);
        return getElementText(cashPurchased);
    }

    @Step("Get 'Cash purchased' text")
    public String getYesCashPurchased(){
        waitForElementVisibility(yesCashPurchased);
        return getElementText(yesCashPurchased);
    }

    @Step("Click 'Void' button")
    public void clickVoid(){
        waitForElementClickable(voidButton);
        click(voidButton);
    }

    @Step("Check 'Void' is disabled")
    public boolean isVoidDisabled(){
        waitForElementVisibility(voidButton);
        return !isElementClickable(voidButton);
    }


    @Step("Check 'Confirmation' popup")
    public boolean checkConfirmation(){
        waitForElementPresent(confirmation);
        return isElementVisible(confirmation);
    }

    @Step("Click on 'Yes' button")
    public void clickYes() {
        waitForElementClickable(yesButton);
        jsClick(yesButton);
    }



}
