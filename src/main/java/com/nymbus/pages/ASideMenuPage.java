package com.nymbus.pages;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class ASideMenuPage extends PageTools {

    private By menu = By.id("menu");
    private By clientMenuItem = By.xpath("//li[a[@id='menu-customers']]");
    private By tellerMenuItem = By.xpath("//li[a[@id='menu-teller']]");
    private By loansMenuItem = By.xpath("//li[a[@id='menu-loans']]");
    private By tellerToTellerMenuItem = By.xpath("//li[a[@id='menu-tellerToTeller']]");
    private By reportGeneratorMenuItem = By.xpath("//li[a[@id='menu-adhocReport']]");
    private By backOfficeMenuItem = By.xpath("//li[a[@id='menu-backoffice']]");
    private By settingsMenuItem = By.xpath("//li[a[@id='menu-settings']]");

    @Step("Wait for a side menu")
    public void waitForASideMenu(){
        waitForElementVisibility(menu);
    }

    @Step("Click 'Client' menu item")
    public void clickClientMenuItem(){
        waitForElementVisibility(clientMenuItem);
        click(clientMenuItem);
    }

    @Step("Check is individualClient page opened")
    public boolean isClientPageOpened(){
        waitForElementVisibility(clientMenuItem);
        return getElementAttributeValue("class", clientMenuItem).contains("active");
    }

    @Step("Click 'Teller' menu item")
    public void clickTellerMenuItem(){
        waitForElementVisibility(tellerMenuItem);
        click(tellerMenuItem);
    }

    @Step("Check is teller page opened")
    public boolean isTellerPageOpened(){
        waitForElementVisibility(tellerMenuItem);
        return getElementAttributeValue("class", tellerMenuItem).contains("active");
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

    @Step("Click 'Teller To Teller Transfer' menu item")
    public void clickTellerToTellerMenuItem(){
        waitForElementVisibility(tellerToTellerMenuItem);
        click(tellerToTellerMenuItem);
    }

    @Step("Check is Teller To Teller Transfer page opened")
    public boolean isTellerToTellerPageOpened(){
        waitForElementVisibility(tellerToTellerMenuItem);
        return getElementAttributeValue("class", tellerToTellerMenuItem).contains("active");
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
