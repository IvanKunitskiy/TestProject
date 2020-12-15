package com.nymbus.pages.check;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class FullCheckPage extends PageTools {
    private By checkType = By.xpath("(//td[@ng-if])[1]");
    private By payee = By.xpath("(//tr[contains(string(),'%s')]/td)[3]/span");
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
        return getElementText(amount);
    }

    @Step("Get 'Status' text")
    public String getStatus(){
        waitForElementVisibility(status);
        return getElementText(status);
    }

    @Step("Get 'Date' text")
    public String getDate(){
        waitForElementVisibility(date);
        return getElementText(date);
    }

    @Step("Get 'Check number' text")
    public String getCheckNumber(){
        waitForElementVisibility(checkNumber);
        return getElementText(checkNumber);
    }

    @Step("Get 'Remitter' text")
    public String getRemitter(){
        waitForElementVisibility(remitter);
        return getElementText(remitter);
    }

    @Step("Get 'Phone' text")
    public String getPhone(){
        waitForElementVisibility(phone);
        return getElementText(phone);
    }

    @Step("Get 'Document type' text")
    public String getDocumentType(){
        waitForElementVisibility(documentType);
        return getElementText(documentType);
    }

    @Step("Get 'Document Id' text")
    public String getDocumentID(){
        waitForElementVisibility(documentID);
        return getElementText(documentID);
    }

    @Step("Get 'Branch' text")
    public String getBranch(){
        waitForElementVisibility(branch);
        return getElementText(branch);
    }

    @Step("Get 'Fee' text")
    public String getFee(){
        waitForElementVisibility(fee);
        return getElementText(fee);
    }

    @Step("Get 'Cash purchased' text")
    public String getCashPurchased(){
        waitForElementVisibility(cashPurchased);
        return getElementText(cashPurchased);
    }


}