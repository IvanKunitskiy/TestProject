package com.nymbus.pages.accounts;

import com.nymbus.base.BasePage;
import com.nymbus.locator.Locator;
import com.nymbus.locator.XPath;
import io.qameta.allure.Step;

public class AccountDetailsPage extends BasePage {

    /**
     * Tabs button
     */
    private Locator maintenanceTab = new XPath("//a[contains(text(), 'Maintenance')]");

    /**
     * Account actions
     */
    private Locator editButton = new XPath("//button[@data-test-id='action-editAccount']");

    /**
     * Details tab
     */
    private Locator moreButton = new XPath("//button[@data-test-id='action-showMoreFields']");
    private Locator fullProfileButton = new XPath("//button[@data-test-id='go-fullProfile']");
    private Locator productType = new XPath("//tr[@data-config-name='accounttype']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator boxSize = new XPath("//tr[@data-config-name='boxsize']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator accountNumber = new XPath("//tr[@data-config-name='accountnumber']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator accountTitle = new XPath("//tr[@data-config-name='accounttitlemailinginstructions']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator bankBranch = new XPath("//tr[@data-config-name='bankbranch']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator product = new XPath("//tr[@data-config-name='accountclasstype']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator dateOpened = new XPath("//tr[@data-config-name='dateopened']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator currentOfficer = new XPath("//tr[@data-config-name='officer']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator statementCycle = new XPath("//tr[@data-config-name='statementcycle']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator callClass = new XPath("//tr[@data-config-name='callclasscode']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator chargeOrAnalyze = new XPath("//tr[@data-config-name='chargeoranalyze']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator accountAnalysis = new XPath("//tr[@data-config-name='accountanalysis']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator statementFlag = new XPath("//tr[@data-config-name='statementflag']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator interestRate = new XPath("//tr[@data-config-name='interestrate']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator earningCreditRate = new XPath("//tr[@data-config-name='earningscreditrate']//span[contains(@class, 'dnTextFixedWidthText')]");

    /**
     * Maintenance tab
     */
    private Locator viewAllMaintenanceHistoryLink = new XPath("//button//span[contains(text(), 'View All History')]");
    private Locator viewMoreButton = new XPath("//button[@data-test-id='action-loadMore']");

    /**
     * Details tab
     */

    @Step("Click the 'Edit' button")
    public void clickEditButton() {
        waitForElementVisibility(editButton);
        waitForElementClickable(editButton);
        click(editButton);
    }

    @Step("Get account 'Earning Credit Rate' value")
    public String getEarningCreditRate() {
        waitForElementVisibility(earningCreditRate);
        String rate = getElementText(earningCreditRate);
        return rate.substring(0, rate.length() - 1);
    }

    @Step("Get account 'Statement Flag' value")
    public String getInterestRateValue() {
        waitForElementVisibility(interestRate);
        String rate = getElementText(interestRate);
        return rate.substring(0, rate.length() - 1);
    }

    @Step("Get account 'Statement Flag' value")
    public String getStatementFlagValue() {
        waitForElementVisibility(statementFlag);
        return getElementText(statementFlag);
    }

    @Step("Get account 'Account Analysis' value")
    public String getAccountAnalysisValue() {
        waitForElementVisibility(accountAnalysis);
        return getElementText(accountAnalysis);
    }

    @Step("Get account 'Charge or Analyze' value")
    public String getChargeOrAnalyze() {
        waitForElementVisibility(chargeOrAnalyze);
        return getElementText(chargeOrAnalyze);
    }

    @Step("Get account 'Call Class Code' value")
    public String getCallClassCode() {
        waitForElementVisibility(callClass);
        return getElementText(callClass);
    }

    @Step("Get account 'Current Officer' value")
    public String getStatementCycle() {
        waitForElementVisibility(statementCycle);
        return getElementText(statementCycle);
    }

    @Step("Get account 'Current Officer' value")
    public String getCurrentOfficerValue() {
        waitForElementVisibility(currentOfficer);
        return getElementText(currentOfficer);
    }

    @Step("Get account 'Date opened' value")
    public String getDateOpenedValue() {
        waitForElementVisibility(dateOpened);
        return getElementText(dateOpened);
    }

    @Step("Get account 'Product' value")
    public String getProductValue() {
        waitForElementVisibility(product);
        return getElementText(product);
    }

    @Step("Wait for 'Full Profile' button is visible")
    public void waitForFullProfileButton() {
        waitForElementVisibility(fullProfileButton);
        waitForElementClickable(fullProfileButton);
    }

    @Step("Get account 'Product Type' value")
    public String getProductTypeValue() {
        waitForElementVisibility(productType);
        return getElementText(productType);
    }

    @Step("Get account 'Box size' value")
    public String getBoxSizeValue() {
        waitForElementVisibility(boxSize);
        return getElementText(boxSize);
    }

    @Step("Get account 'Account Number' value")
    public String getAccountNumberValue() {
        waitForElementVisibility(accountNumber);
        return getElementText(accountNumber);
    }

    @Step("Get account 'Account Title' value")
    public String getAccountTitleValue() {
        waitForElementVisibility(accountTitle);
        return getElementText(accountTitle);
    }

    @Step("Get account 'Bank Branch' value")
    public String getBankBranchValue() {
        waitForElementVisibility(bankBranch);
        return getElementText(bankBranch);
    }

    @Step("Click the 'More' button")
    public void clickMoreButton() {
        waitForElementVisibility(moreButton);
        waitForElementClickable(moreButton);
        click(moreButton);
    }

    /**
     * Tabs button
     */
    @Step("Click the 'Maintenance' tab")
    public void clickMaintenanceTab() {
        waitForElementVisibility(maintenanceTab);
        waitForElementClickable(maintenanceTab);
        click(maintenanceTab);
    }

    /**
     * Maintenance tab
     */
    public void clickViewAllMaintenanceHistoryLink() {
        waitForElementVisibility(viewAllMaintenanceHistoryLink);
        waitForElementClickable(viewAllMaintenanceHistoryLink);
        click(viewAllMaintenanceHistoryLink);
    }

    public void clickViewMoreButton() {
        waitForElementVisibility(viewMoreButton);
        waitForElementClickable(viewMoreButton);
        click(viewMoreButton);
    }

}
