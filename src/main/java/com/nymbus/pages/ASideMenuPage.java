package com.nymbus.pages;

import com.nymbus.base.BasePage;
import com.nymbus.locator.ID;
import com.nymbus.locator.Locator;
import com.nymbus.locator.XPath;
import io.qameta.allure.Step;

public class ASideMenuPage extends BasePage {

    Locator menu = new ID("menu");
    Locator clientMenuItem = new XPath("//li[a[@id='menu-customers']]");
    Locator loansMenuItem = new XPath("//li[a[@id='menu-loans']]");
    Locator reportGeneratorMenuItem = new XPath("//li[a[@id='menu-adhocReport']]");
    Locator backOfficeMenuItem = new XPath("//li[a[@id='menu-backoffice']]");
    Locator settingsMenuItem = new XPath("//li[a[@id='menu-settings']]");

    @Step("Wait for a side menu")
    public void waitForASideMenu(){
        waitForElementVisibility(menu);
    }

    @Step("Click 'Client' menu item")
    public void clickClientMenuItem(){
        waitForElementVisibility(clientMenuItem);
        click(clientMenuItem);
    }

    @Step("Check is client page opened")
    public boolean isClientPageOpened(){
        waitForElementVisibility(clientMenuItem);
        return getElementAttributeValue("class", clientMenuItem).contains("active");
    }

    @Step("Click 'Loans' menu item")
    public void clickLoansMenuItem(){
        waitForElementVisibility(loansMenuItem);
        click(loansMenuItem);
    }

    @Step("Check is Loan page opened")
    public boolean isLoansPageOpened(){
        waitForElementVisibility(loansMenuItem);
        return getElementAttributeValue("class", loansMenuItem).contains("active");
    }

    @Step("Click 'Report Generator' menu item")
    public void clickReportGeneratorMenuItem(){
        waitForElementVisibility(reportGeneratorMenuItem);
        click(reportGeneratorMenuItem);
    }

    @Step("Check is Report Generator page opened")
    public boolean isReportGeneratorPageOpened(){
        waitForElementVisibility(reportGeneratorMenuItem);
        return getElementAttributeValue("class", reportGeneratorMenuItem).contains("active");
    }

    @Step("Click 'Back Office' menu item")
    public void clickBackOfficeMenuItem(){
        waitForElementVisibility(backOfficeMenuItem);
        click(backOfficeMenuItem);
    }

    @Step("Check is Back Office page opened")
    public boolean isBackOfficePageOpened(){
        waitForElementVisibility(backOfficeMenuItem);
        return getElementAttributeValue("class", backOfficeMenuItem).contains("active");
    }

    @Step("Click 'Settings' menu item")
    public void clickSettingsMenuItem(){
        waitForElementVisibility(settingsMenuItem);
        click(settingsMenuItem);
    }

    @Step("Check is Settings page opened")
    public boolean isSettingsPageOpened(){
        waitForElementVisibility(settingsMenuItem);
        return getElementAttributeValue("class", settingsMenuItem).contains("active");
    }



}
