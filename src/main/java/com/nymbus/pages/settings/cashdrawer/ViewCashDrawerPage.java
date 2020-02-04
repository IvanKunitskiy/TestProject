package com.nymbus.pages.settings.cashdrawer;

import com.nymbus.base.BasePage;
import com.nymbus.locator.Locator;
import com.nymbus.locator.XPath;
import io.qameta.allure.Step;

import java.util.List;

public class ViewCashDrawerPage extends BasePage {

    /**
     * Controls
     */

    private Locator overLay = new XPath("//div[contains(@ng-show, 'isLoading') and contains(@class, 'xwidget_loading_overlay')]");
    private Locator addNewButton = new XPath("//a[span[text()='Add New']]");
    private Locator editButton = new XPath("//button[contains(@ng-click, 'editForm')]");

    /**
     * Cash drawer data
     */
    private Locator name = new XPath("//div[@id='bank.data.cashdrawer-(databean)name']//span");
    private Locator cashDrawerType = new XPath("//div[@id='bank.data.cashdrawer-cashdrawertype']//span[contains(@class,'value')]");
    private Locator defaultUser = new XPath("//div[@id='bank.data.cashdrawer-defaultuserid']//span[contains(@class,'value')]");
    private Locator branch = new XPath("//div[@id='bank.data.cashdrawer-bankbranchid']//span[contains(@class,'value')]");
    private Locator location = new XPath("//div[@id='bank.data.cashdrawer-locationid']//span[contains(@class,'value')]");
    private Locator glAccountNumber = new XPath("//div[@id='bank.data.cashdrawer-glaccountid']//span[contains(@class,'value')]");
    private Locator floating = new XPath("//div[@id='bank.data.cashdrawer-floating']//input[contains(@name,'floating')]");


    /**
     * Actions with controls
     */
    public void waitViewCashDrawerDataVisible(){
        waitForElementVisibility(overLay);
        waitForElementInvisibility(overLay);
    }

    @Step("Click 'Add New' button")
    public void clickAddNewButton() {
        waitForElementVisibility(addNewButton);
        waitForElementClickable(addNewButton);
        click(addNewButton);
    }

    @Step("Click 'Edit' button")
    public void clickEditButton() {
        waitForElementVisibility(editButton);
        waitForElementClickable(editButton);
        click(editButton);
    }

    /**
     * Cash drawer data
     */
    @Step("Get name of cash drawer")
    public String getName(){
        waitForElementVisibility(name);
        return getElementText(name).trim();
    }

    @Step("Get Cash Drawer Type")
    public String getCashDrawerType(){
        waitForElementVisibility(cashDrawerType);
        return getElementText(cashDrawerType).trim();
    }

    @Step("Get Default User")
    public String getDefaultUser(){
        waitForElementVisibility(defaultUser);
        return getElementText(defaultUser).trim();
    }

    @Step("Get Branch")
    public String getBranch(){
        waitForElementVisibility(branch);
        return getElementText(branch).trim();
    }

    @Step("Get Location")
    public String getLocation(){
        waitForElementVisibility(location);
        wait(2);
        return getElementText(location).trim();
    }

    @Step("Get GL Account Number")
    public String getGLAccount(){
        waitForElementVisibility(glAccountNumber);
        return getElementText(glAccountNumber).trim();
    }

    @Step("Get Branch")
    public boolean isFloating(){
        return getElementAttributeValue("value", floating).contains("1");
    }

}
