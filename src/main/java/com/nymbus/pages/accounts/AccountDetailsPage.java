package com.nymbus.pages.accounts;

import com.nymbus.base.BasePage;
import com.nymbus.locator.Locator;
import com.nymbus.locator.XPath;
import io.qameta.allure.Step;

public class AccountDetailsPage extends BasePage {

    /**
     * Account actions
     */

    private Locator editButton = new XPath("//button[@data-test-id='action-editAccount']");

    /**
     * Details tab
     */

    private Locator moreButton = new XPath("//button[@data-test-id='action-showMoreFields']");
    private Locator lessButton = new XPath("//button[@data-test-id='action-hideLessFields']");
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
    private Locator federalWHReason = new XPath("//tr[@data-config-name='federalwithholdingreason']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator reasonDebitCardChargeWaived = new XPath("//tr[@data-config-name='reasondebitcardchargeswaived']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator reasonAutoNSFChgWaived = new XPath("//tr[@data-config-name='reasonautonsfchargeswaived']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator odProtectionAcct = new XPath("//tr[@data-config-name='overdraftprotectionaccountnumber']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator reasonATMChargeWaived = new XPath("//tr[@data-config-name='reasonatmchargeswaived']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator automaticOverdraftStatus = new XPath("//tr[@data-config-name='automaticoverdraftstatus']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator reasonAutoOdChgWaived = new XPath("//tr[@data-config-name='reasonautoodchargeswaived']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator whenSurchargesRefunded = new XPath("//tr[@data-config-name='whensurchargesrefunded']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator federalWHPercent = new XPath("//tr[@data-config-name='federalwithholdingpercent']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator numberOfATMCardsIssued = new XPath("//tr[@data-config-name='numberofatmcardissued']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator userDefinedField_1 = new XPath("//tr[@data-config-name='userdefinedfield1']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator userDefinedField_2 = new XPath("//tr[@data-config-name='userdefinedfield2']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator userDefinedField_3 = new XPath("//tr[@data-config-name='userdefinedfield3']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator userDefinedField_4 = new XPath("//tr[@data-config-name='userdefinedfield4']//span[contains(@class, 'dnTextFixedWidthText')]");

    private Locator imageStatementCode= new XPath("//tr[@data-config-name='imagestatementcode']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator numberOfDebitCardsIssued= new XPath("//tr[@data-config-name='numberofdebitcardsissued']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator automaticOverdraftLimit= new XPath("//tr[@data-config-name='automaticoverdraftlimit']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator cashCollDaysBeforeChg= new XPath("//tr[@data-config-name='cashcollectionnumberofdaysbeforeinterestcharge']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator cashCollInterestChg= new XPath("//tr[@data-config-name='cashcollectioninterestchargesperstatementcycle']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator cashCollFloat= new XPath("//tr[@data-config-name='cashcollectionfloat']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator positivePay= new XPath("//tr[@data-config-name='positivepaycustomer']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator interestFrequency= new XPath("//tr[@data-config-name='interestfrequency']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator primaryAccountForCombinedStatement= new XPath("//tr[@data-config-name='ddaaccountidforcombinedstatement']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator correspondingAccount= new XPath("//tr[@data-config-name='correspondingaccountid']//span[contains(@class, 'dnTextFixedWidthText')]");

    private Locator printStatementNextUpdate= new XPath("//tr[@data-config-name='printstatementnextupdate']//span[contains(@class, 'dnTextFixedWidthText')]");
    private Locator interestPaidYTD= new XPath("//tr[@data-config-name='interestpaidytd']//span[contains(@class, 'dnTextFixedWidthText')]");

    /**
     * Details tab
     */

    @Step("Get 'Print Statement Next Update' value")
    public String getPrintStatementNextUpdate() {
        waitForElementVisibility(printStatementNextUpdate);
        return getElementText(printStatementNextUpdate);
    }

    @Step("Get 'Interest Paid YTD' value")
    public String getInterestPaidYTD() {
        waitForElementVisibility(interestPaidYTD);
        String interestPaidYTDValue = getElementText(interestPaidYTD);
        return interestPaidYTDValue.replaceAll("[^0-9]", "");
    }

    @Step("Get 'Corresponding Account' value")
    public String getCorrespondingAccount() {
        waitForElementVisibility(correspondingAccount);
        return getElementText(correspondingAccount);
    }

    @Step("Get 'PrimaryAccountForCombinedStatement' value")
    public String getPrimaryAccountForCombinedStatement() {
        waitForElementVisibility(primaryAccountForCombinedStatement);
        return getElementText(primaryAccountForCombinedStatement);
    }

    @Step("Get 'Interest Frequency' value")
    public String getInterestFrequency() {
        waitForElementVisibility(interestFrequency);
        return getElementText(interestFrequency);
    }

    @Step("Get 'Positive Pay' value")
    public String getPositivePay() {
        waitForElementVisibility(positivePay);
        return getElementText(positivePay);
    }

    @Step("Get 'Cash Coll Float' value")
    public String getCashCollFloat() {
        waitForElementVisibility(cashCollFloat);
        String cashCollFloatValue = getElementText(cashCollFloat);
        return cashCollFloatValue.replaceAll("[^0-9]", "");
    }

    @Step("Get 'Cash Coll Interest Chg' value")
    public String getCashCollInterestChg() {
        waitForElementVisibility(cashCollInterestChg);
        String cashCollValue = getElementText(cashCollInterestChg);
        return cashCollValue.replaceAll("[^0-9]", "");
    }

    @Step("Get 'Cash Coll Days Before Chg' value")
    public String getCashCollDaysBeforeChg() {
        waitForElementVisibility(cashCollDaysBeforeChg);
        return getElementText(cashCollDaysBeforeChg);
    }

    @Step("Get 'Automatic Overdraft Limit' value")
    public String getAutomaticOverdraftLimit() {
        waitForElementVisibility(automaticOverdraftLimit);
        String limitValue = getElementText(automaticOverdraftLimit);
        return limitValue.replaceAll("[^0-9]", "");
    }

    @Step("Get 'Number Of Debit Cards Issued' value")
    public String getNumberOfDebitCardsIssued() {
        waitForElementVisibility(numberOfDebitCardsIssued);
        return getElementText(numberOfDebitCardsIssued);
    }

    @Step("Get 'Image Statement Code' value")
    public String getImageStatementCode() {
        waitForElementVisibility(imageStatementCode);
        return getElementText(imageStatementCode);
    }

    @Step("Get 'User Defined Field 1' value")
    public String getUserDefinedField_4() {
        waitForElementVisibility(userDefinedField_4);
        return getElementText(userDefinedField_4);
    }

    @Step("Get 'User Defined Field 3' value")
    public String getUserDefinedField_3() {
        waitForElementVisibility(userDefinedField_3);
        return getElementText(userDefinedField_3);
    }

    @Step("Get 'User Defined Field 2' value")
    public String getUserDefinedField_2() {
        waitForElementVisibility(userDefinedField_2);
        return getElementText(userDefinedField_2);
    }

    @Step("Get 'User Defined Field 1' value")
    public String getUserDefinedField_1() {
        waitForElementVisibility(userDefinedField_1);
        return getElementText(userDefinedField_1);
    }

    @Step("Get 'Number Of ATM Cards Issued' value")
    public String getNumberOfATMCardsIssued() {
        waitForElementVisibility(numberOfATMCardsIssued);
        return getElementText(numberOfATMCardsIssued);
    }

    @Step("Get 'Federal WH Percent' value")
    public String getFederalWHPercent() {
        waitForElementVisibility(federalWHPercent);
        String whPercent = getElementText(federalWHPercent);
        return whPercent.substring(0, whPercent.length() - 1);
    }

    @Step("Get 'When Surcharges Refunded' value")
    public String getWhenSurchargesRefunded() {
        waitForElementVisibility(whenSurchargesRefunded);
        return getElementText(whenSurchargesRefunded);
    }

    @Step("Get 'Reason Auto Od Chg Waived' value")
    public String getReasonAutoOdChgWaived() {
        waitForElementVisibility(reasonAutoOdChgWaived);
        return getElementText(reasonAutoOdChgWaived);
    }

    @Step("Get 'Automatic Overdraft Status' value")
    public String getAutomaticOverdraftStatus() {
        waitForElementVisibility(automaticOverdraftStatus);
        return getElementText(automaticOverdraftStatus);
    }

    @Step("Get account 'Reason Debit Card Charge Waived' value")
    public String getReasonDebitCardChargeWaived() {
        waitForElementVisibility(reasonDebitCardChargeWaived);
        return getElementText(reasonDebitCardChargeWaived);
    }

    @Step("Get account 'Reason Auto NSF Chg Waived' value")
    public String getReasonAutoNSFChgWaived() {
        waitForElementVisibility(reasonAutoNSFChgWaived);
        return getElementText(reasonAutoNSFChgWaived);
    }

    @Step("Get account 'OD Protection Acct' value")
    public String getOdProtectionAcct() {
        waitForElementVisibility(odProtectionAcct);
        return getElementText(odProtectionAcct);
    }

    @Step("Get account 'Reason ATM Charge Waived' value")
    public String getReasonATMChargeWaived() {
        waitForElementVisibility(reasonATMChargeWaived);
        return getElementText(reasonATMChargeWaived);
    }

    @Step("Get account 'Federal W/H reason' value")
    public String getFederalWHReason() {
        waitForElementVisibility(federalWHReason);
        return getElementText(federalWHReason);
    }

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

    @Step("Check if 'More' button is visible")
    public boolean isMoreButtonVisible() {
        waitForElementVisibility(moreButton);
        waitForElementClickable(moreButton);
        return isElementVisible(moreButton);
    }

    @Step("Click the 'Less' button")
    public void clickLessButton() {
        waitForElementVisibility(lessButton);
        waitForElementClickable(lessButton);
        click(lessButton);
    }
}
