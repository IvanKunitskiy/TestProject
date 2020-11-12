package com.nymbus.pages.settings.tellerlocation;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class BranchOverviewPage extends PageTools {

    private By branchName = By.xpath("//div[@data-field-id='(databean)name']/div/div/div/div/span[1]");
    private By bankName = By.xpath("//div[@data-field-id='mailname']/div/div/div/div/span[1]");
    private By addressLine1 = By.xpath("//div[@data-field-id='addressline1']/div/div/div/div/span[1]");
    private By city = By.xpath("//div[@data-field-id='city']/div/div/div/div/span[1]");
    private By state = By.xpath("//div[@data-field-id='state']/div/div/div/div/span[1]");
    private By zipCode = By.xpath("//div[@data-field-id='zipcode']/div/div/div/div/span[1]");
    private By phoneNumber = By.xpath("//div[@data-field-id='phonenumber']/div/div/div/div/span[1]");

    @Step("Get branch name value")
    public String getBranchName() {
        return getElementText(branchName);
    }

    @Step("Get bank name value")
    public String getBankName() {
        return getElementText(bankName);
    }

    @Step("Get address line 1 value")
    public String getAddressLine1() {
        return getElementText(addressLine1);
    }

    @Step("Get city value")
    public String getCity() {
        return getElementText(city);
    }

    @Step("Get state value")
    public String getState() {
        return getElementText(state);
    }

    @Step("Get zip code value")
    public String getZipCode() {
        return getElementText(zipCode);
    }

    @Step("Get phone number value")
    public String getPhoneNumber() {
        return getElementText(phoneNumber);
    }
}
