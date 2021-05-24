package com.nymbus.pages.check;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class CheckPage extends PageTools {
    private By checkType = By.xpath("(//tr[contains(string(),'%s')]/td)[6]/span");
    private By payee = By.xpath("(//tr[contains(string(),'%s')]/td)[3]/span");
    private By purchaser = By.xpath("(//tr[contains(string(),'%s')]/td)[2]/span");
    private By initials = By.xpath("(//tr[contains(string(),'%s')]/td)[5]/span");
    private By amount = By.xpath("(//tr[contains(string(),'%s')]/td)[8]/label");
    private By status = By.xpath("(//tr[contains(string(),'%s')]/td)[7]/span");
    private By date = By.xpath("(//tr[contains(string(),'%s')]/td)[4]/span");

    @Step("Get 'Check Type' text")
    public String getCheckType(String number) {
        waitForElementVisibility(checkType, number);
        return getElementText(checkType, number);
    }

    @Step("Get 'Payee' text")
    public String getPayee(String number) {
        waitForElementVisibility(payee, number);
        return getElementText(payee, number);
    }

    @Step("Get 'Purchaser' text")
    public String getPurchaser(String number) {
        waitForElementVisibility(purchaser, number);
        return getElementText(purchaser, number);
    }

    @Step("Get 'Inintials' text")
    public String getInitials(String number) {
        waitForElementVisibility(initials, number);
        return getElementText(initials, number);
    }

    @Step("Get 'Amount' text")
    public String getAmount(String number) {
        waitForElementVisibility(amount, number);
        return getElementText(amount, number).replaceAll("[^0-9.]", "");
    }

    @Step("Get 'Status' text")
    public String getStatus(String number){
        waitForElementVisibility(status, number);
        return getElementText(status,number);
    }

    @Step("Get 'Date' text")
    public String getDate(String number){
        waitForElementVisibility(date, number);
        return getElementText(date,number);
    }

    @Step("Click to check")
    public void clickToCheck(String number){
        waitForElementVisibility(initials, number);
        click(initials,number);
    }



}
