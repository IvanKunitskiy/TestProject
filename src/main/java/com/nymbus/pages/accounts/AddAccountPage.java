package com.nymbus.pages.accounts;

import com.codeborne.selenide.SelenideElement;
import com.nymbus.core.base.PageTools;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.client.other.account.ProductType;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.util.List;
import java.util.stream.Collectors;

public class AddAccountPage extends PageTools {

    private final By productTypeSelectorButton = By.id("accounttype");
    private final By productTypeSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private final By productTypeList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private final By productSelectorButton = By.xpath("//div[@id='accountclasstype']");
    private final By productList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private final By productTypeInputField = By.xpath("//div[@data-test-id='field-accounttype']//input[contains(@class, 'nb-select-search')]");
    private final By accountNumberField = By.xpath("//input[@data-test-id='field-accountnumber']");
    private final By accountTitleField = By.xpath("//input[@data-test-id='field-accounttitlemailinginstructions']");
    private final By bankBranchSelectorButton = By.xpath("//div[@id='bankbranch']");
    private final By bankBranchSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private final By saveAccountButton = By.xpath("//button[@data-test-id='action-save']");
    private final By bankBranchList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private final By accountType = By.xpath("//div[@data-test-id='field-customertype']/a/span/span");
    private final By dateOpened = By.xpath("//input[@data-test-id='field-dateopened']");
    private final By productSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[normalize-space(text())='%s']]");
    private final By statementCycleSelectorButton = By.xpath("//div[@id='statementcycle']");
    private final By statementCycleList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private final By bankBranch = By.xpath("//div[@data-test-id='field-bankbranch']/a/span/span[contains(@class, 'ng-binding')]");
    private final By interestRateInput = By.xpath("//input[@data-test-id='field-interestrate']");
    private final By earningCreditRateInput = By.xpath("//input[@data-test-id='field-earningscreditrate']");
    private final By optInOutDateCalendarIcon = By.xpath("//div[input[@id='dbcodpstatusdate']]/div[span[@class='nyb-icon-calendar']]");
    private final By optInOutDateInputField = By.xpath("//input[@id='dbcodpstatusdate']");
    private final By dateOpenedField = By.xpath("//input[@id='dateopened']");
    private final By dateOfBirth = By.xpath("//input[@id='dateofbirth']");
    private final By iraDistributionCode = By.xpath("//div[@id='iradistributioncode']//a//span/span");
    private final By iraDistributionFrequency = By.xpath("//div[@id='iradistributionfrequency']//a//span/span");
    private final By iraDistributionAmountField = By.xpath("//input[@id='iradistributionamount']");
    private final By dateNextIRADistribution = By.xpath("//input[@id='datenextiradistribution']");
    private final By termType = By.xpath("//input[@id='terminmonthsordays']");
    private final By autoRenewable = By.xpath("//dn-switch[@id='autorenewablecode']/div/div/span[contains(@class, 'ng-scope')]");
    private final By interestFrequency = By.xpath("//div[@id='interestfrequencycode']/a/span/span[contains(@class, 'ng-scope')]");
    private final By interestRate = By.xpath("//input[@id='interestrate']");
    private final By interestType = By.xpath("//div[@id='interesttype']/a/span/span[contains(@class, 'ng-scope')]");
    private final By transactionalAccountSwitch = By.xpath("//dn-switch[@id='transactionalaccount']");
    private final By transactionalAccount = By.xpath("//dn-switch[@id='transactionalaccount']/div/div/span[contains(@class, 'ng-scope')]");
    private final By applyInterestTo = By.xpath("//div[@id='codetoapplyinterestto']/a/span/span[contains(@class, 'ng-scope')]");
    private final By mailCode = By.xpath("//div[@id='mailingcode']/a/span/span[contains(@class, 'ng-scope')]");
    private final By rentalAmount = By.xpath("//input[@id='rentalamount']");
    private final By userDefinedFieldInput_1 = By.xpath("//input[@id='userdefinedfield1']");
    private final By userDefinedFieldInput_2 = By.xpath("//input[@id='userdefinedfield2']");
    private final By userDefinedFieldInput_3 = By.xpath("//input[@id='userdefinedfield3']");
    private final By userDefinedFieldInput_4 = By.xpath("//input[@id='userdefinedfield4']");
    private final By discountPeriods = By.xpath("//input[@id='discountperiods']");
    private final By applySeasonalAddress = By.xpath("//dn-switch[@id='useseasonaladdress']//span[@ng-if='model']");
    private final By applySeasonalAddressSwitch = By.xpath("//dn-switch[@id='useseasonaladdress']");
    private final By applySeasonalAddressValue = By.xpath("//dn-switch[@id='useseasonaladdress']/div/div/span");
    private final By dateOfFirstDeposit = By.xpath("//input[@id='datefirstdeposit']");
    private final By autoRenewableSwitch = By.xpath("//dn-switch[@id='autorenewablecode']");
    private final By autoRenewableSwitchValue = By.xpath("//dn-switch[@id='autorenewablecode']/div/div/span");
    private final By paymentAmount = By.xpath("//input[@data-test-id='field-paymentamount']");
    private final By cycleLoanValue = By.xpath("//dn-switch[@id='cycleloan']/div/div/span[2]");
    private final By cycleLoanSwitch = By.xpath("//dn-switch[@id='cycleloan']");
    private final By nextPaymentBilledDueDate = By.xpath("//input[@data-test-id='field-nextduedate']");
    private final By paymentBilledLeadDays = By.xpath("//input[@data-test-id='field-noticedays']");
    private final By currentEffectiveRate = By.xpath("//input[@data-test-id='field-currenteffectiverate']");
    private final By term = By.xpath("//input[@data-test-id='field-termofloanmonths']");
    private final By locPaymentRecalculationFlag = By.xpath("//dn-switch[@id='locpaymentrecalc']");
    private final By locPaymentRecalculationFlagValue = By.xpath("//dn-switch[@id='locpaymentrecalc']/div/div/span[2]");
    private final By adjustableRate = By.xpath("//dn-switch[@id='adjustablerate_checkbox']");
    private final By adjustableRateValue = By.xpath("//dn-switch[@id='adjustablerate_checkbox']/div/div/span[1]");
    private final By nextRateChangeDate = By.xpath("//input[@id='armdatenextirchange']");
    private final By escrowPayment = By.xpath("//input[@data-test-id='field-escrowpayment']");

    /**
     * Account holders and signers
     */

    private final By accountHolderName = By.xpath("//a[@data-test-id='action-goCustomerProfile']");
    private final By accountHolderRelationship = By.xpath("//div[@data-test-id='field-relationshiptype_0']//a//span//span");
    private final By accountHolderClientType = By.xpath("//input[@data-test-id='field-typeid_0']");
    private final By accountHolderTaxID = By.xpath("//input[@data-test-id='field-taxIdNumber']");
    private final By statementCycleSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private final By accountHolderAddress = By.xpath("//div[@data-test-id='field-addressid_0']/a/span/span");

    private final By originatingOfficer = By.xpath("//div[@data-test-id='field-originatingOfficer']/a/span/span");
    private final By currentOfficer = By.xpath("//div[@data-test-id='field-officer']/a/span/span");
    private final By optInOutStatus = By.xpath("//div[@data-test-id='field-dbcodpstatus']/a/span/span");

    private final By currentOfficerSelectorButton = By.xpath("//div[@data-test-id='field-officer']");
    private final By currentOfficerList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private final By currentOfficerSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private final By originatingOfficerSelectorButton = By.xpath("//div[@data-test-id='field-originatingOfficer']");
    private final By originatingOfficerList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private final By originatingOfficerSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private final By callClassCodeSelectorButton = By.xpath("//div[@data-test-id='field-callclasscode']");
    private final By callClassCodeList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private final By callClassCodeSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private final By accountAnalysisSelectorButton = By.xpath("//div[@id='accountanalysis']");
    private final By accountAnalysisList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private final By accountAnalysisSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private final By chargeOrAnalyzeSelectorButton = By.xpath("//div[@id='chargeoranalyze']");
    private final By chargeOrAnalyzeList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private final By chargeOrAnalyzeSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private final By interestFrequencySelectorButton = By.xpath("//div[@id='interestfrequency']//span[contains(@class, 'select2-arrow')]");
    private final By interestFrequencyList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private final By interestFrequencySelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private final By interestFrequencyCodeSelectorButton = By.xpath("//div[@id='interestfrequencycode']//span[contains(@class, 'select2-arrow')]");
    private final By interestFrequencyCodeList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private final By interestFrequencyCodeSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private final By correspondingAccountSelectorButton = By.xpath("//div[@id='correspondingaccountid']");
    private final By correspondingAccountList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private final By correspondingAccountSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private final By iraDistributionFrequencySelectorButton = By.xpath("//div[@id='iradistributionfrequency']");
    private final By iraDistributionFrequencyList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private final By iraDistributionFrequencySelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private final By iraDistributionCodeSelectorButton = By.xpath("//div[@id='iradistributioncode']");
    private final By iraDistributionCodeList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private final By iraDistributionCodeSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private final By applyInterestToSelectorButton = By.xpath("//div[@id='codetoapplyinterestto']");
    private final By applyInterestToList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private final By applyInterestToSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private final By interestTypeSelectorButton = By.xpath("//div[@id='interesttype']");
    private final By interestTypeList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private final By interestTypeSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private final By discountReasonSelectorButton = By.xpath("//div[@id='discountreason']");
    private final By discountReasonList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private final By discountReasonSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");

    private final By boxSizeSelectorButton = By.xpath("//div[@id='boxsize']");
    private final By boxSizeSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[contains(text(), '%s')]]");
    private final By boxSizeList = By.xpath("//li[contains(@role, 'option')]/div/span");

    private final By mailCodeSelectorButton = By.xpath("//div[@id='mailingcode']");
    private final By mailCodeList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private final By mailCodeSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[text()='%s']]");

    private final By loanClassCodeSelectorButton = By.xpath("//div[@id='classtype']");
    private final By loanClassCodeList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private final By loanClassCodeSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[text()='%s']]");

    private final By accountTypeSelectorButton = By.xpath("//div[@id='customertype']");
    private final By accountTypeList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private final By accountTypeSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[text()='%s']]");

    private final By paymentAmountTypeSelectorButton = By.xpath("//div[@id='paymenttypefornonclass1']");
    private final By paymentAmountTypeList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private final By paymentAmountTypeSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[text()='%s']]");

    private final By paymentFrequencySelectorButton = By.xpath("//div[@id='principalpaymentfrequencycode']");
    private final By paymentFrequencyList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private final By paymentFrequencySelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[text()='%s']]");

    private final By interestMethodSelectorButton = By.xpath("//div[@id='interestmethod']");
    private final By interestMethodList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private final By interestMethodSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[text()='%s']]");

    private final By daysBaseYearBaseSelectorButton = By.xpath("//div[@id='daybaseforinterestrate']");
    private final By daysBaseYearBaseList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private final By daysBaseYearBaseSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[text()='%s']]");

    private final By rateChangeFrequencySelectorButton = By.xpath("//div[@id='variableratechangefrequency']");
    private final By rateChangeFrequencyList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private final By rateChangeFrequencySelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[text()='%s']]");

    private final By cycleCodeSelectorButton = By.xpath("//div[@id='cyclecode']");
    private final By cycleCodeList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private final By cycleCodeSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[text()='%s']]");

    private final By commitmentTypeAmtSelectorButton = By.xpath("//div[@id='commitmenttype']");
    private final By commitmentTypeAmtList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private final By commitmentTypeAmtSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[text()='%s']]");

    @Step("Wait for account holder name")
    public void waitForAccountHolderName() {
        waitForElementVisibility(accountHolderName);
    }

    @Step("Click the 'Account Type' option")
    public void clickAccountTypeSelectorOption(String accountTypeOption) {
        waitForElementClickable(accountTypeSelectorOption, accountTypeOption);
        click(accountTypeSelectorOption, accountTypeOption);
    }

    @Step("Returning list of 'Account Type' options")
    public List<String> getAccountTypeList() {
        return getElementsText(accountTypeList);
    }

    @Step("Click the 'Account Type' selector button")
    public void clickAccountTypeSelectorButton() {
        scrollToPlaceElementInCenter(accountTypeSelectorButton);
        click(accountTypeSelectorButton);
    }

    @Step("Click the 'Discount reason' option")
    public void clickDiscountReasonSelectorOption(String discountReasonOption) {
        waitForElementVisibility(discountReasonSelectorOption,  discountReasonOption);
        waitForElementClickable(discountReasonSelectorOption,  discountReasonOption);
        click(discountReasonSelectorOption,  discountReasonOption);
    }

    @Step("Returning list of 'Discount reason' options")
    public List<String> getDiscountReasonList() {
        waitForElementVisibility(discountReasonList);
        waitForElementClickable(discountReasonList);
        return getElementsText(discountReasonList);
    }

    @Step("Click the 'Discount Reason' selector button")
    public void clickDiscountReasonSelectorButton() {
        waitForElementVisibility(discountReasonSelectorButton);
        scrollToElement(discountReasonSelectorButton);
        waitForElementClickable(discountReasonSelectorButton);
        click(discountReasonSelectorButton);
    }

    @Step("Click the 'Interest Type' option")
    public void clickInterestTypeSelectorOption(String interestTypeOption) {
        waitForElementVisibility(interestTypeSelectorOption, interestTypeOption);
        waitForElementClickable(interestTypeSelectorOption, interestTypeOption);
        click(interestTypeSelectorOption, interestTypeOption);
    }

    @Step("Returning list of 'Interest Type' options")
    public List<String> getInterestTypeList() {
        waitForElementVisibility(interestTypeList);
        waitForElementClickable(interestTypeList);
        return getElementsText(interestTypeList);
    }

    @Step("Click the 'Interest Type' selector button")
    public void clickInterestTypeSelectorButton() {
        waitForElementVisibility(interestTypeSelectorButton);
        scrollToPlaceElementInCenter(interestTypeSelectorButton);
        waitForElementClickable(interestTypeSelectorButton);
        click(interestTypeSelectorButton);
    }

    @Step("Click the 'Apply Interest To' option")
    public void clickApplyInterestToSelectorOption(String applyInterestToOption) {
        waitForElementVisibility(applyInterestToSelectorOption, applyInterestToOption);
        waitForElementClickable(applyInterestToSelectorOption, applyInterestToOption);
        click(applyInterestToSelectorOption, applyInterestToOption);
    }

    @Step("Returning list of 'Apply Interest To' options")
    public List<String> getApplyInterestToList() {
        waitForElementVisibility(applyInterestToList);
        waitForElementClickable(applyInterestToList);
        return getElementsText(applyInterestToList);
    }

    @Step("Click the 'Apply Interest To' selector button")
    public void clickApplyInterestToSelectorButton() {
        waitForElementVisibility(applyInterestToSelectorButton);
        scrollToPlaceElementInCenter(applyInterestToSelectorButton);
        waitForElementClickable(applyInterestToSelectorButton);
        click(applyInterestToSelectorButton);
    }

    @Step("Click the 'IRA Distribution Frequency' option")
    public void clickIRADistributionCodeSelectorOption(String iraDistributionCodeOption) {
        waitForElementVisibility(iraDistributionCodeSelectorOption, iraDistributionCodeOption);
        waitForElementClickable(iraDistributionCodeSelectorOption, iraDistributionCodeOption);
        click(iraDistributionCodeSelectorOption, iraDistributionCodeOption);
    }

    @Step("Returning list of 'IRA Distribution Frequency' options")
    public List<String> getIRADistributionCodeList() {
        waitForElementVisibility(iraDistributionCodeList);
        waitForElementClickable(iraDistributionCodeList);
        return getElementsText(iraDistributionCodeList);
    }

    @Step("Click the 'IRA Distribution Frequency' selector button")
    public void clickIRADistributionCodeSelectorButton() {
        waitForElementVisibility(iraDistributionCodeSelectorButton);
        scrollToPlaceElementInCenter(iraDistributionCodeSelectorButton);
        waitForElementClickable(iraDistributionCodeSelectorButton);
        click(iraDistributionCodeSelectorButton);
    }

    @Step("Click the 'IRA Distribution Frequency' option")
    public void clickIRADistributionFrequencySelectorOption(String iraDistributionFrequencyOption) {
        waitForElementVisibility(iraDistributionFrequencySelectorOption, iraDistributionFrequencyOption);
        waitForElementClickable(iraDistributionFrequencySelectorOption, iraDistributionFrequencyOption);
        click(iraDistributionFrequencySelectorOption, iraDistributionFrequencyOption);
    }

    @Step("Returning list of 'IRA Distribution Frequency' options")
    public List<String> getIRADistributionFrequencyList() {
        waitForElementVisibility(iraDistributionFrequencyList);
        waitForElementClickable(iraDistributionFrequencyList);
        return getElementsText(iraDistributionFrequencyList);
    }

    @Step("Click the 'IRA Distribution Frequency' selector button")
    public void clickIRADistributionFrequencySelectorButton() {
        waitForElementVisibility(iraDistributionFrequencySelectorButton);
        scrollToPlaceElementInCenter(iraDistributionFrequencySelectorButton);
        waitForElementClickable(iraDistributionFrequencySelectorButton);
        click(iraDistributionFrequencySelectorButton);
    }

    @Step("Click the 'Corresponding Account' option")
    public void clickCorrespondingAccountSelectorOption(String correspondingAccountOption) {
        waitForElementVisibility(correspondingAccountSelectorOption, correspondingAccountOption);
        waitForElementClickable(correspondingAccountSelectorOption, correspondingAccountOption);
        click(correspondingAccountSelectorOption, correspondingAccountOption);
    }

    @Step("Returning list of 'Corresponding Account' options")
    public List<String> getCorrespondingAccountList() {
        return getElementsWithZeroOption(correspondingAccountList).stream()
                .map(SelenideElement::text)
                .collect(Collectors.toList());
    }

    @Step("Click the 'Corresponding Account' selector button")
    public void clickCorrespondingAccountSelectorButton() {
        waitForElementVisibility(correspondingAccountSelectorButton);
        scrollToPlaceElementInCenter(correspondingAccountSelectorButton);
        waitForElementClickable(correspondingAccountSelectorButton);
        click(correspondingAccountSelectorButton);
    }

    @Step("Click the 'Interest Frequency' option")
    public void clickInterestFrequencySelectorOption(String interestFrequencyOption) {
        waitForElementVisibility(interestFrequencySelectorOption, interestFrequencyOption);
        waitForElementClickable(interestFrequencySelectorOption, interestFrequencyOption);
        click(interestFrequencySelectorOption, interestFrequencyOption);
    }

    @Step("Returning list of 'Interest Frequency' options")
    public List<String> getInterestFrequencyList() {
        waitForElementVisibility(interestFrequencyList);
        waitForElementClickable(interestFrequencyList);
        return getElementsText(interestFrequencyList);
    }

    @Step("Click the 'Interest Frequency' selector button")
    public void clickInterestFrequencySelectorButton() {
        scrollToPlaceElementInCenter(interestFrequencySelectorButton);
        click(interestFrequencySelectorButton);
    }

    @Step("Click the 'Interest Frequency code' option")
    public void clickInterestFrequencyCodeSelectorOption(String interestFrequencyOption) {
        waitForElementVisibility(interestFrequencyCodeSelectorOption, interestFrequencyOption);
        waitForElementClickable(interestFrequencyCodeSelectorOption, interestFrequencyOption);
        click(interestFrequencyCodeSelectorOption, interestFrequencyOption);
    }

    @Step("Returning list of 'Interest Frequency code' options")
    public List<String> getInterestFrequencyCodeList() {
        waitForElementVisibility(interestFrequencyCodeList);
        waitForElementClickable(interestFrequencyCodeList);
        return getElementsText(interestFrequencyCodeList);
    }

    @Step("Click the 'Interest Frequency code' selector button")
    public void clickInterestFrequencyCodeSelectorButton() {
        scrollToPlaceElementInCenter(interestFrequencyCodeSelectorButton);
        click(interestFrequencyCodeSelectorButton);
    }

    @Step("Set 'Date Next IRA Distribution' value")
    public void setDateNextIRADistributionValue(String dateNextIRADistributionValue) {
        waitForElementVisibility(dateNextIRADistribution);
        waitForElementClickable(dateNextIRADistribution);
        scrollToPlaceElementInCenter(dateNextIRADistribution);
        typeWithoutWipe("", dateNextIRADistribution);
        SelenideTools.sleep(3);
        typeWithoutWipe(dateNextIRADistributionValue, dateNextIRADistribution);
    }

    @Step("Set 'Date Of First Deposit' value")
    public void setDateOfFirstDepositValue(String dateOfFirstDepositValue) {
        waitForElementVisibility(dateOfFirstDeposit);
        waitForElementClickable(dateOfFirstDeposit);
        scrollToPlaceElementInCenter(dateOfFirstDeposit);
        typeWithoutWipe("", dateOfFirstDeposit);
        SelenideTools.sleep(3);
        typeWithoutWipe(dateOfFirstDepositValue, dateOfFirstDeposit);
    }

    @Step("Set 'Date Opened' value")
    public void setDateOpenedValue(String dateOpenedValue) {
        waitForElementVisibility(dateOpenedField);
        waitForElementClickable(dateOpenedField);
        scrollToPlaceElementInCenter(dateOpenedField);
        typeWithoutWipe("", dateOpenedField);
        click(dateOpenedField);
        SelenideTools.sleep(Constants.MICRO_TIMEOUT);
        typeWithoutWipe(dateOpenedValue, dateOpenedField);
    }

    @Step("Set 'Next Payment Billed Due Date' value")
    public void setNextPaymentBilledDueDate(String date) {
        waitForElementClickable(nextPaymentBilledDueDate);
        scrollToPlaceElementInCenter(nextPaymentBilledDueDate);
        typeWithoutWipe("", nextPaymentBilledDueDate);
        SelenideTools.sleep(Constants.MICRO_TIMEOUT);
        typeWithoutWipe(date, nextPaymentBilledDueDate);
    }

    @Step("Set 'Next Rate Change Date' value")
    public void setNextRateChangeDate(String date) {
        waitForElementClickable(nextRateChangeDate);
        scrollToPlaceElementInCenter(nextRateChangeDate);
        typeWithoutWipe("", nextRateChangeDate);
        SelenideTools.sleep(Constants.MICRO_TIMEOUT);
        typeWithoutWipe(date, nextRateChangeDate);
    }

    @Step("Set 'DBC ODP Opt In/Out Status Date' value")
    public void setOptInOutDateValue(String optInOutDateValue) {
        waitForElementVisibility(optInOutDateInputField);
        waitForElementClickable(optInOutDateInputField);
        typeWithoutWipe("", optInOutDateInputField);
        SelenideTools.sleep(1);
        typeWithoutWipe(optInOutDateValue, optInOutDateInputField);
    }

    @Step("Click on 'DBC ODP Opt In/Out Status Date' calendar icon")
    public void clickOptInOutDateCalendarIcon() {
        waitForElementClickable(optInOutDateCalendarIcon);
        click(optInOutDateCalendarIcon);
    }

    @Step("Click the 'Charge or Analyze' option")
    public void clickChargeOrAnalyzeSelectorOption(String chargeOrAnalyzeOption) {
        waitForElementVisibility(chargeOrAnalyzeSelectorOption, chargeOrAnalyzeOption);
        waitForElementClickable(chargeOrAnalyzeSelectorOption, chargeOrAnalyzeOption);
        click(chargeOrAnalyzeSelectorOption, chargeOrAnalyzeOption);
    }

    @Step("Returning list of 'Charge or Analyze' options")
    public List<String> getChargeOrAnalyzeList() {
        waitForElementVisibility(chargeOrAnalyzeList);
        waitForElementClickable(chargeOrAnalyzeList);
        return getElementsText(chargeOrAnalyzeList);
    }

    @Step("Click the 'Charge or Analyze' selector button")
    public void clickChargeOrAnalyzeSelectorButton() {
        waitForElementVisibility(chargeOrAnalyzeSelectorButton);
        scrollToPlaceElementInCenter(callClassCodeSelectorButton);
        waitForElementClickable(chargeOrAnalyzeSelectorButton);
        click(chargeOrAnalyzeSelectorButton);
    }

    @Step("Set 'Earning Credit Rate' option")
    public void setEarningCreditRate(String earningCreditRateValue) {
        waitForElementVisibility(earningCreditRateInput);
        waitForElementClickable(earningCreditRateInput);
        type(earningCreditRateValue, earningCreditRateInput);
    }

    @Step("Set 'Interest Rate' option")
    public void setInterestRate(String interestRateValue) {
        waitForElementVisibility(interestRateInput);
        waitForElementClickable(interestRateInput);
        type(interestRateValue, interestRateInput);
    }

    @Step("Click the 'Account Analysis' option")
    public void clickAccountAnalysisSelectorOption(String callClassCodeOption) {
        waitForElementVisibility(accountAnalysisSelectorOption, callClassCodeOption);
        waitForElementClickable(accountAnalysisSelectorOption, callClassCodeOption);
        click(accountAnalysisSelectorOption, callClassCodeOption);
    }

    @Step("Returning list of 'Account Analysis' options")
    public List<String> getAccountAnalysisList() {
        return getElementsWithZeroOption(accountAnalysisList).stream()
                .map(SelenideElement::text)
                .collect(Collectors.toList());
    }

    @Step("Click the 'Account Analysis' selector button")
    public void clickAccountAnalysisSelectorButton() {
        waitForElementVisibility(accountAnalysisSelectorButton);
        scrollToPlaceElementInCenter(accountAnalysisSelectorButton);
        waitForElementClickable(accountAnalysisSelectorButton);
        click(accountAnalysisSelectorButton);
    }

    @Step("Click the 'Call Class Code' option")
    public void clickCallClassCodeSelectorOption(String callClassCodeOption) {
        waitForElementVisibility(callClassCodeSelectorOption, callClassCodeOption);
        waitForElementClickable(callClassCodeSelectorOption, callClassCodeOption);
        click(callClassCodeSelectorOption, callClassCodeOption);
    }

    @Step("Returning list of 'Call Class Code' options")
    public List<String> getCallClassCodeList() {
        return getElementsWithZeroOption(callClassCodeList).stream()
                .map(SelenideElement::text)
                .collect(Collectors.toList());
    }

    @Step("Click the 'Call Class Code' selector button")
    public void clickCallClassCodeSelectorButton() {
        scrollToPlaceElementInCenter(callClassCodeSelectorButton);
        jsClick(callClassCodeSelectorButton);
    }

    @Step("Click the 'Current Officer' option")
    public void clickCurrentOfficerSelectorOption(String currentOfficerOption) {
        waitForElementVisibility(currentOfficerSelectorOption, currentOfficerOption);
        waitForElementClickable(currentOfficerSelectorOption, currentOfficerOption);
        click(currentOfficerSelectorOption, currentOfficerOption);
    }

    @Step("Returning list of 'Current Officer' options")
    public List<String> getCurrentOfficerList() {
        waitForElementVisibility(currentOfficerList);
        waitForElementClickable(currentOfficerList);
        return getElementsText(currentOfficerList);
    }

    @Step("Click the 'Current Officer' selector button")
    public void clickCurrentOfficerSelectorButton() {
        waitForElementVisibility(currentOfficerSelectorButton);
        scrollToPlaceElementInCenter(currentOfficerSelectorButton);
        waitForElementClickable(currentOfficerSelectorButton);
        click(currentOfficerSelectorButton);
    }

    @Step("Click the 'Originating Officer' option")
    public void clickOriginatingOfficerSelectorOption(String originatingOfficerOption) {
        waitForElementVisibility(originatingOfficerSelectorOption, originatingOfficerOption);
        waitForElementClickable(originatingOfficerSelectorOption, originatingOfficerOption);
        click(originatingOfficerSelectorOption, originatingOfficerOption);
    }

    @Step("Returning list of 'Originating Officer' options")
    public List<String> getOriginatingOfficerList() {
        waitForElementVisibility(originatingOfficerList);
        waitForElementClickable(originatingOfficerList);
        return getElementsText(originatingOfficerList);
    }

    @Step("Click the 'Originating Officer' selector button")
    public void clickOriginatingOfficerSelectorButton() {
        waitForElementVisibility(originatingOfficerSelectorButton);
        scrollToPlaceElementInCenter(originatingOfficerSelectorButton);
        waitForElementClickable(originatingOfficerSelectorButton);
        click(originatingOfficerSelectorButton);
    }

    @Step("Returning the 'Address' value")
    public String getAccountHolderAddress() {
        waitForElementVisibility(accountHolderAddress);
        waitForElementClickable(accountHolderAddress);
        return getElementText(accountHolderAddress);
    }

    @Step("Returning the 'Mail Code' value")
    public String getMailCode() {
        waitForElementVisibility(mailCode);
        SelenideTools.sleep(2);
        return getElementText(mailCode);
    }

    @Step("Returning the 'Date Of Birth' value")
    public String getDateOfBirth() {
        waitForElementVisibility(dateOfBirth);
        waitForElementClickable(dateOfBirth);
        SelenideTools.sleep(2);
        return getElementAttributeValue("value", dateOfBirth);
    }

    @Step("Returning the 'Rental Amount' value")
    public String getRentalAmount() {
        waitForElementVisibility(rentalAmount);
        waitForElementClickable(rentalAmount);
        SelenideTools.sleep(2);
        return getElementAttributeValue("value", rentalAmount).replaceAll("[^0-9.,]", "");
    }

    @Step("Returning the 'Bank Branch' value")
    public String getBankBranch() {
        waitForElementClickable(bankBranch);
        return getElementText(bankBranch).trim();
    }

    @Step("Returning the 'IRA Distribution Code' value")
    public String getIRADistributionCode() {
        waitForElementVisibility(iraDistributionCode);
        waitForElementClickable(iraDistributionCode);
        return getElementText(iraDistributionCode).trim();
    }

    @Step("Returning the 'IRA Distribution Frequency' value")
    public String getIRADistributionFrequency() {
        waitForElementVisibility(iraDistributionFrequency);
        waitForElementClickable(iraDistributionFrequency);
        return getElementText(iraDistributionFrequency);
    }

    @Step("Click the 'Statement Cycle' option")
    public void clickStatementCycleOption(String statementCycleOption) {
        waitForElementVisibility(statementCycleSelectorOption, statementCycleOption);
        waitForElementClickable(statementCycleSelectorOption, statementCycleOption);
        click(statementCycleSelectorOption, statementCycleOption);
    }

    @Step("Returning list of 'Statement Cycle' options")
    public List<String> getStatementCycleList() {
        waitForElementVisibility(statementCycleList);
        waitForElementClickable(statementCycleList);
        return getElementsText(statementCycleList);
    }

    @Step("Click the 'Statement Cycle' selector button")
    public void clickStatementCycleSelectorButton() {
        scrollToPlaceElementInCenter(statementCycleSelectorButton);
        click(statementCycleSelectorButton);
    }

    @Step("Click the 'Product' selector button")
    public void clickProductSelectorButton() {
        waitForElementVisibility(productSelectorButton);
        waitForElementClickable(productSelectorButton);
        SelenideTools.sleep(2);
        click(productSelectorButton);
    }

    @Step("Returning list of 'Product' options")
    public List<String> getProductList() {
        waitForElementVisibility(productList);
        waitForElementClickable(productList);
        return getElementsText(productList);
    }

    @Step("Click the 'Product' option")
    public void clickProductOption(String productOption) {
        clickIfExist(productSelectorOption, productOption);
        SelenideTools.sleep(2);
    }

    @Step("Returning the 'DBC ODP Opt In/Out Status' value")
    public String getOptInOutStatus() {
        waitForElementVisibility(optInOutStatus);
        waitForElementClickable(optInOutStatus);
        return getElementText(optInOutStatus);
    }

    @Step("Returning the 'Originating Officer' value")
    public String getOriginatingOfficer() {
        waitForElementVisibility(originatingOfficer);
        waitForElementClickable(originatingOfficer);
        return getElementText(originatingOfficer);
    }

    @Step("Returning the 'Current Officer' value")
    public String getCurrentOfficer() {
        waitForElementVisibility(currentOfficer);
        waitForElementClickable(currentOfficer);
        return getElementText(currentOfficer);
    }

    @Step("Returning the 'Date Opened' value")
    public String getDateOpened() {
        return getElementAttributeValue("value", dateOpened);
    }

    @Step("Returning the 'Account Holder Tax ID' value")
    public String getAccountHolderTaxID() {
        waitForElementVisibility(accountHolderTaxID);
        String taxid = getElementAttributeValue("value", accountHolderTaxID);
        return taxid.replaceAll("-", "");
    }

    @Step("Returning the 'Account Holder Client type' value")
    public String getAccountHolderClientType() {
        waitForElementVisibility(accountHolderClientType);
        return getElementAttributeValue("value", accountHolderClientType);
    }

    @Step("Returning the 'Account Holder Relationship' value")
    public String getAccountHolderRelationship() {
        waitForElementVisibility(accountHolderRelationship);
        waitForElementClickable(accountHolderRelationship);
        return getElementText(accountHolderRelationship);
    }


    @Step("Returning the 'Term Type' value")
    public String getTermType() {
        waitForElementVisibility(termType);
        waitForElementClickable(termType);
        return getElementAttributeValue("value", termType);
    }

    @Step("Returning the 'Auto Renewable' value")
    public String getAutoRenewable() {
        waitForElementVisibility(autoRenewable);
        waitForElementClickable(autoRenewable);
        return getElementText(autoRenewable);
    }

    @Step("Returning the 'Interest Frequency' value")
    public String getInterestFrequency() {
        waitForElementVisibility(interestFrequency);
        waitForElementClickable(interestFrequency);
        return getElementText(interestFrequency);
    }

    @Step("Returning the 'Interest Rate' value")
    public String getInterestRate() {
        waitForElementVisibility(interestRate);
        waitForElementClickable(interestRate);
        return getElementAttributeValue("value", interestRate);
    }

    @Step("Returning the 'Interest Type' value")
    public String getInterestType() {
        waitForElementVisibility(interestType);
        waitForElementClickable(interestType);
        return getElementText(interestType);
    }

    @Step("Click 'Transactional Account' switch")
    public void clickTransactionalAccountSwitch() {
        scrollToPlaceElementInCenter(transactionalAccountSwitch);
        jsClick(transactionalAccountSwitch);
    }

    @Step("Get 'Transactional Account' switch value")
    public String getTransactionalAccountSwitchValue() {
        waitForElementVisibility(transactionalAccount);
        waitForElementClickable(transactionalAccount);
        scrollToPlaceElementInCenter(transactionalAccount);
        return getElementText(transactionalAccount);
    }

    @Step("Click 'Apply Seasonal Address' switch")
    public String clickApplySeasonalAddressSwitch() {
        waitForElementVisibility(applySeasonalAddressSwitch);
        waitForElementClickable(applySeasonalAddressSwitch);
        return getElementText(applySeasonalAddressSwitch);
    }

    @Step("Get 'Seasonal Address' switch value")
    public String getApplySeasonalAddressSwitchValue() {
        waitForElementVisibility(applySeasonalAddressValue);
        waitForElementClickable(applySeasonalAddressValue);
        scrollToPlaceElementInCenter(applySeasonalAddressValue);
        return getElementText(applySeasonalAddressValue);
    }

    @Step("Click 'Auto Renewable' switch")
    public String clickAutoRenewableSwitch() {
        waitForElementVisibility(autoRenewableSwitch);
        waitForElementClickable(autoRenewableSwitch);
        return getElementText(autoRenewableSwitch);
    }

    @Step("Get 'Auto Renewable' switch value")
    public String getAutoRenewableSwitchValue() {
        waitForElementVisibility(autoRenewableSwitchValue);
        waitForElementClickable(autoRenewableSwitchValue);
        scrollToPlaceElementInCenter(autoRenewableSwitchValue);
        return getElementText(autoRenewableSwitchValue);
    }

    @Step("Returning the 'Account Holder Name' value")
    public String getAccountHolderName() {
        waitForElementVisibility(accountHolderName);
        waitForElementClickable(accountHolderName);
        return getElementText(accountHolderName);
    }

    @Step("Returning the 'Account type' value")
    public String getAccountType() {
        waitForElementVisibility(accountType);
        waitForElementClickable(accountType);
        return getElementText(accountType);
    }

    @Step("Returning list of 'Bank Branch'")
    public List<String> getBankBranchList() {
        waitForElementVisibility(bankBranchList);
        waitForElementClickable(bankBranchList);
        return getElementsText(bankBranchList);
    }

    @Step("Returning 'Apply Interest To' value")
    public String getApplyInterestTo() {
        waitForElementVisibility(applyInterestTo);
        waitForElementClickable(applyInterestTo);
        return getElementText(applyInterestTo);
    }

    @Step("Returning 'Transactional Account' value")
    public String getTransactionalAccount() {
        waitForElementVisibility(transactionalAccount);
        waitForElementClickable(transactionalAccount);
        return getElementText(transactionalAccount);
    }

    @Step("Click the 'Product Type' selector button")
    public void clickProductTypeSelectorButton() {
        waitForElementVisibility(productTypeSelectorButton);
        waitForElementClickable(productTypeSelectorButton);
        click(productTypeSelectorButton);
    }

    @Step("Click the 'Product Type' option")
    public void clickProductTypeOption(String productTypeOption) {
        waitForElementVisibility(productTypeSelectorOption, productTypeOption);
        waitForElementClickable(productTypeSelectorOption, productTypeOption);
        click(productTypeSelectorOption, productTypeOption);
    }

    @Step("Click the 'Product Type' option")
    public void clickProductTypeOption(ProductType productTypeOption) {
        waitForElementVisibility(productTypeSelectorOption, productTypeOption.getProductType());
        waitForElementClickable(productTypeSelectorOption, productTypeOption.getProductType());
        click(productTypeSelectorOption, productTypeOption.getProductType());
    }

    @Step("Click the 'Box size' selector button")
    public void clickBoxSizeSelectorButton() {
        waitForElementVisibility(boxSizeSelectorButton);
        waitForElementClickable(boxSizeSelectorButton);
        click(boxSizeSelectorButton);
    }

    @Step("Returning list of 'Box Size'")
    public List<String> getBoxSizeList() {
        waitForElementVisibility(boxSizeList);
        waitForElementClickable(boxSizeList);
        return getElementsText(boxSizeList);
    }

    @Step("Returning list of 'Box Size'")
    public List<String> getBoxSizeListWithZeroOption() {
       return getElementsTextWithWait(Constants.MICRO_TIMEOUT, boxSizeList);
    }

    @Step("Set 'Box Size' option")
    public void setBoxSizeOption(String boxSizeOptionValue) {
        waitForElementVisibility(boxSizeSelectorOption);
        waitForElementClickable(boxSizeSelectorOption);
        type(boxSizeOptionValue, boxSizeSelectorOption);
    }

    @Step("Click the 'Box size' option")
    public void clickBoxSizeSelectorOption(String boxSizeOption) {
        waitForElementVisibility(boxSizeSelectorOption, boxSizeOption);
        waitForElementClickable(boxSizeSelectorOption, boxSizeOption);
        click(boxSizeSelectorOption, boxSizeOption);
    }

    @Step("Returning list of 'Product Type'")
    public List<String> getProductTypeList() {
        waitForElementVisibility(productTypeList);
        waitForElementClickable(productTypeList);
        return getElementsText(productTypeList);
    }

    @Step("Set 'Product Type' option")
    public void setProductTypeOption(String productTypeOptionValue) {
        waitForElementVisibility(productTypeSelectorButton);
        waitForElementClickable(productTypeSelectorButton);
        click(productTypeSelectorButton);
        waitForElementVisibility(productTypeInputField);
        waitForElementClickable(productTypeInputField);
        type(productTypeOptionValue, productTypeInputField);
    }

    @Step("Set 'Account Number' value")
    public void setAccountNumberValue(String accountNumberValue) {
        waitForElementVisibility(accountNumberField);
        scrollToPlaceElementInCenter(accountNumberField);
        waitForElementClickable(accountNumberField);
        type(accountNumberValue, accountNumberField);
    }

    @Step("Set 'Discount Periods' value")
    public void setDiscountPeriods(String value) {
        waitForElementVisibility(discountPeriods);
        waitForElementClickable(discountPeriods);
        type(value, discountPeriods);
    }

    @Step("Set 'User Defined Field 4' value")
    public void setUserDefinedField_4(String value) {
        waitForElementVisibility(userDefinedFieldInput_4);
        waitForElementClickable(userDefinedFieldInput_4);
        type(value, userDefinedFieldInput_4);
    }

    @Step("Set 'User Defined Field 3' value")
    public void setUserDefinedField_3(String value) {
        waitForElementVisibility(userDefinedFieldInput_3);
        waitForElementClickable(userDefinedFieldInput_3);
        type(value, userDefinedFieldInput_3);
    }

    @Step("Set 'User Defined Field 2' value")
    public void setUserDefinedField_2(String value) {
        waitForElementVisibility(userDefinedFieldInput_2);
        waitForElementClickable(userDefinedFieldInput_2);
        type(value, userDefinedFieldInput_2);
    }

    @Step("Set 'User Defined Field 1' value")
    public void setUserDefinedField_1(String value) {
        waitForElementVisibility(userDefinedFieldInput_1);
        waitForElementClickable(userDefinedFieldInput_1);
        type(value, userDefinedFieldInput_1);
    }

    @Step("Set 'Account Title' value")
    public void setAccountTitleValue(String accountTitleValue) {
        waitForElementVisibility(accountTitleField);
        waitForElementClickable(accountTitleField);
        type(accountTitleValue, accountTitleField);
    }

    @Step("Set 'IRA distribution amount' value")
    public void setIRADistributionAmountValue(String iraDistributionAmountValue) {
        waitForElementVisibility(iraDistributionAmountField);
        waitForElementClickable(iraDistributionAmountField);
        scrollToPlaceElementInCenter(iraDistributionAmountField);
        type(iraDistributionAmountValue, iraDistributionAmountField);
    }

    @Step("Click the 'Bank branch' selector button")
    public void clickBankBranchSelectorButton() {
        waitForElementVisibility(bankBranchSelectorButton);
        scrollToPlaceElementInCenter(bankBranchSelectorButton);
        waitForElementClickable(bankBranchSelectorButton);
        click(bankBranchSelectorButton);
    }

    @Step("Click the 'Bank branch' option")
    public void clickBankBranchOption(String bankBranchOption) {
        waitForElementVisibility(bankBranchSelectorOption, bankBranchOption);
        waitForElementClickable(bankBranchSelectorOption, bankBranchOption);
        click(bankBranchSelectorOption, bankBranchOption);
    }

    @Step("Click the 'Mail Code' selector button")
    public void clickMailCodeSelectorOption(String mailCodeOption) {
        waitForElementVisibility(mailCodeSelectorOption,  mailCodeOption);
        waitForElementClickable(mailCodeSelectorOption,  mailCodeOption);
        click(mailCodeSelectorOption,  mailCodeOption);
    }

    @Step("Returning list of 'Mail Code' options")
    public List<String> getMailCodeList() {
        waitForElementVisibility(mailCodeList);
        waitForElementClickable(mailCodeList);
        return getElementsText(mailCodeList);
    }

    @Step("Click the 'Mail Code' selector button")
    public void clickMailCodeSelectorButton() {
        waitForElementVisibility(mailCodeSelectorButton);
        scrollToPlaceElementInCenter(mailCodeSelectorButton);
        waitForElementClickable(mailCodeSelectorButton);
        click(mailCodeSelectorButton);
    }

    @Step("Click the 'Loan Class Code' selector button")
    public void clickLoanClassCodeSelectorOption(String loanClassCodeOption) {
        waitForElementVisibility(loanClassCodeSelectorOption,  loanClassCodeOption);
        waitForElementClickable(loanClassCodeSelectorOption,  loanClassCodeOption);
        click(loanClassCodeSelectorOption,  loanClassCodeOption);
    }

    @Step("Returning list of 'Loan Class Code' options")
    public List<String> getLoanClassCodeList() {
        waitForElementVisibility(loanClassCodeList);
        waitForElementClickable(loanClassCodeList);
        return getElementsText(loanClassCodeList);
    }

    @Step("Click the 'Loan Class Code' selector button")
    public void clickLoanClassCodeSelectorButton() {
        waitForElementVisibility(loanClassCodeSelectorButton);
        scrollToPlaceElementInCenter(loanClassCodeSelectorButton);
        waitForElementClickable(loanClassCodeSelectorButton);
        click(loanClassCodeSelectorButton);
    }

    @Step("Click the 'Payment Amount Type' selector button")
    public void clickPaymentAmountTypeSelectorOption(String paymentAmountTypeOption) {
        waitForElementVisibility(paymentAmountTypeSelectorOption,  paymentAmountTypeOption);
        waitForElementClickable(paymentAmountTypeSelectorOption,  paymentAmountTypeOption);
        click(paymentAmountTypeSelectorOption,  paymentAmountTypeOption);
    }

    @Step("Returning list of 'Payment Amount Type' options")
    public List<String> getPaymentAmountTypeList() {
        waitForElementVisibility(paymentAmountTypeList);
        waitForElementClickable(paymentAmountTypeList);
        return getElementsText(paymentAmountTypeList);
    }

    @Step("Click the 'Payment Amount Type' selector button")
    public void clickPaymentAmountTypeSelectorButton() {
        waitForElementVisibility(paymentAmountTypeSelectorButton);
        scrollToPlaceElementInCenter(paymentAmountTypeSelectorButton);
        waitForElementClickable(paymentAmountTypeSelectorButton);
        click(paymentAmountTypeSelectorButton);
    }

    @Step("Click the 'Payment Amount Type' selector button")
    public void clickPaymentFrequencySelectorOption(String paymentFrequencyOption) {
        waitForElementVisibility(paymentFrequencySelectorOption,  paymentFrequencyOption);
        waitForElementClickable(paymentFrequencySelectorOption,  paymentFrequencyOption);
        click(paymentFrequencySelectorOption,  paymentFrequencyOption);
    }

    @Step("Returning list of 'Payment Amount Type' options")
    public List<String> getPaymentFrequencyList() {
        waitForElementVisibility(paymentFrequencyList);
        waitForElementClickable(paymentFrequencyList);
        return getElementsText(paymentFrequencyList);
    }

    @Step("Click the 'Payment Amount Type' selector button")
    public void clickPaymentFrequencySelectorButton() {
        waitForElementVisibility(paymentFrequencySelectorButton);
        scrollToPlaceElementInCenter(paymentFrequencySelectorButton);
        waitForElementClickable(paymentFrequencySelectorButton);
        click(paymentFrequencySelectorButton);
    }

    @Step("Click the 'Interest Method' selector button")
    public void clickInterestMethodSelectorOption(String interestMethodOption) {
        waitForElementVisibility(interestMethodSelectorOption, interestMethodOption);
        waitForElementClickable(interestMethodSelectorOption, interestMethodOption);
        click(interestMethodSelectorOption, interestMethodOption);
    }

    @Step("Returning list of 'Interest Method' options")
    public List<String> getInterestMethodList() {
        waitForElementVisibility(interestMethodList);
        waitForElementClickable(interestMethodList);
        return getElementsText(interestMethodList);
    }

    @Step("Click the 'Interest Method' selector button")
    public void clickInterestMethodSelectorButton() {
        waitForElementVisibility(interestMethodSelectorButton);
        scrollToPlaceElementInCenter(interestMethodSelectorButton);
        waitForElementClickable(interestMethodSelectorButton);
        click(interestMethodSelectorButton);
    }

    @Step("Click the 'Days Base/Year Base' selector button")
    public void clickDaysBaseYearBaseSelectorOption(String daysBaseYearBaseOption) {
        waitForElementVisibility(daysBaseYearBaseSelectorOption, daysBaseYearBaseOption);
        waitForElementClickable(daysBaseYearBaseSelectorOption, daysBaseYearBaseOption);
        click(daysBaseYearBaseSelectorOption, daysBaseYearBaseOption);
    }

    @Step("Returning list of 'Days Base/Year Base' options")
    public List<String> getDaysBaseYearBaseList() {
        waitForElementVisibility(daysBaseYearBaseList);
        waitForElementClickable(daysBaseYearBaseList);
        return getElementsText(daysBaseYearBaseList);
    }

    @Step("Click the 'Days Base/Year Base' selector button")
    public void clickDaysBaseYearBaseSelectorButton() {
        waitForElementVisibility(daysBaseYearBaseSelectorButton);
        scrollToPlaceElementInCenter(daysBaseYearBaseSelectorButton);
        waitForElementClickable(daysBaseYearBaseSelectorButton);
        click(daysBaseYearBaseSelectorButton);
    }

    @Step("Click the 'Rate Change Frequency' selector button")
    public void clickRateChangeFrequencySelectorOption(String rateChangeFrequencyOption) {
        waitForElementVisibility(rateChangeFrequencySelectorOption, rateChangeFrequencyOption);
        waitForElementClickable(rateChangeFrequencySelectorOption, rateChangeFrequencyOption);
        click(rateChangeFrequencySelectorOption, rateChangeFrequencyOption);
    }

    @Step("Returning list of 'Rate Change Frequency' options")
    public List<String> getRateChangeFrequencyList() {
        waitForElementVisibility(rateChangeFrequencyList);
        waitForElementClickable(rateChangeFrequencyList);
        return getElementsText(rateChangeFrequencyList);
    }

    @Step("Click the 'Rate Change Frequency' selector button")
    public void clickRateChangeFrequencySelectorButton() {
        waitForElementVisibility(rateChangeFrequencySelectorButton);
        scrollToPlaceElementInCenter(rateChangeFrequencySelectorButton);
        waitForElementClickable(rateChangeFrequencySelectorButton);
        click(rateChangeFrequencySelectorButton);
    }

    @Step("Click the 'Commitment type/amt' selector button")
    public void clickCommitmentTypeAmtSelectorOption(String commitmentTypeAmtOption) {
        waitForElementVisibility(commitmentTypeAmtSelectorOption, commitmentTypeAmtOption);
        waitForElementClickable(commitmentTypeAmtSelectorOption, commitmentTypeAmtOption);
        click(commitmentTypeAmtSelectorOption, commitmentTypeAmtOption);
    }

    @Step("Returning list of 'Commitment type/amt' options")
    public List<String> getCommitmentTypeAmtList() {
        waitForElementVisibility(commitmentTypeAmtList);
        waitForElementClickable(commitmentTypeAmtList);
        return getElementsText(commitmentTypeAmtList);
    }

    @Step("Click the 'Commitment type/amt' selector button")
    public void clickCommitmentTypeAmtSelectorButton() {
        waitForElementVisibility(commitmentTypeAmtSelectorButton);
        scrollToPlaceElementInCenter(commitmentTypeAmtSelectorButton);
        waitForElementClickable(commitmentTypeAmtSelectorButton);
        click(commitmentTypeAmtSelectorButton);
    }

    @Step("Click the 'Cycle Code' selector button")
    public void clickCycleCodeSelectorOption(String cycleCodeOption) {
        waitForElementVisibility(cycleCodeSelectorOption, cycleCodeOption);
        waitForElementClickable(cycleCodeSelectorOption, cycleCodeOption);
        click(cycleCodeSelectorOption, cycleCodeOption);
    }

    @Step("Returning list of 'Cycle Code' options")
    public List<String> getCycleCodeList() {
        waitForElementVisibility(cycleCodeList);
        waitForElementClickable(cycleCodeList);
        return getElementsText(cycleCodeList);
    }

    @Step("Click the 'Cycle Code' selector button")
    public void clickCycleCodeSelectorButton() {
        waitForElementVisibility(cycleCodeSelectorButton);
        scrollToPlaceElementInCenter(cycleCodeSelectorButton);
        waitForElementClickable(cycleCodeSelectorButton);
        click(cycleCodeSelectorButton);
    }

    @Step("Set 'Payment Amount' value")
    public void setPaymentAmount(String value) {
        waitForElementClickable(paymentAmount);
        type(value, paymentAmount);
    }

    @Step("Set 'Term' value")
    public void setTerm(String value) {
        waitForElementClickable(term);
        scrollToPlaceElementInCenter(term);
        type(value, term);
    }

    @Step("Set 'Payment Billed Lead Days' value")
    public void setPaymentBilledLeadDays(String value) {
        waitForElementClickable(paymentBilledLeadDays);
        scrollToPlaceElementInCenter(paymentBilledLeadDays);
        type(value, paymentBilledLeadDays);
    }

    @Step("Set 'Current effective rate' value")
    public void setCurrentEffectiveRate(String value) {
        waitForElementClickable(currentEffectiveRate);
        scrollToPlaceElementInCenter(currentEffectiveRate);
        type(value, currentEffectiveRate);
    }

    @Step("Click the 'Save' button")
    public void clickSaveAccountButton() {
        waitForElementVisibility(saveAccountButton);
        scrollToPlaceElementInCenter(saveAccountButton);
        waitForElementClickable(saveAccountButton);
        click(saveAccountButton);
    }

    @Step("Get the 'Apply Seasonal Address' value")
    public String getApplySeasonalAddress() {
        waitForElementVisibility(applySeasonalAddress);
        waitForElementClickable(applySeasonalAddress);
        return getElementText(applySeasonalAddress);
    }

    @Step("Get the 'Cycle Loan' value")
    public String getCycleLoanValue() {
        return getElementText(cycleLoanValue).trim();
    }

    @Step("Click the 'Cycle Loan' switch")
    public void clickCycleLoanSwitch() {
        waitForElementClickable(cycleLoanSwitch);
        scrollToPlaceElementInCenter(cycleLoanSwitch);
        click(cycleLoanSwitch);
    }

    @Step("Get the 'LOC Payment Recalculation Flag' value")
    public String getLocPaymentRecalculationFlagValue() {
        return getElementText(locPaymentRecalculationFlagValue).trim();
    }

    @Step("Click the 'LOC Payment Recalculation Flag' switch")
    public void clickLocPaymentRecalculationFlagValue() {
        waitForElementClickable(locPaymentRecalculationFlag);
        scrollToPlaceElementInCenter(locPaymentRecalculationFlag);
        click(locPaymentRecalculationFlag);
    }

    @Step("Get the 'Adjustable Rate' value")
    public String getAdjustableRateValue() {
        return getElementText(adjustableRateValue).trim();
    }

    @Step("Click the 'Adjustable Rate' switch")
    public void clickAdjustableRate() {
        waitForElementClickable(adjustableRate);
        scrollToPlaceElementInCenter(adjustableRate);
        click(adjustableRate);
    }

    @Step("Get the 'Escrow payment' value")
    public String getEscrowPaymentValue() {
        return getElementAttributeValue("value", escrowPayment).replaceAll("[^0-9.]", "");
    }
}
