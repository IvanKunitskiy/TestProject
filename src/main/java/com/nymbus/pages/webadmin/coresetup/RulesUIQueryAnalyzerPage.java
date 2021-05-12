package com.nymbus.pages.webadmin.coresetup;

import com.codeborne.selenide.Condition;
import com.nymbus.core.base.PageTools;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.util.List;

public class RulesUIQueryAnalyzerPage extends PageTools {

    private By searchRegion = By.xpath("//td[@class='mainPanel' and //form[contains(@action, 'RulesUIQuery')]]");
    private By searchResultTable = By.xpath("//table[@id='searchResultTable']");
    private By listOfSearchResult = By.xpath("//table[@id='searchResultTable']//tr");
    private By firstNameByIndex = By.xpath("//table[@id='searchResultTable']//tr[%s]/td[11]/div");
    private By lastNameByIndex = By.xpath("//table[@id='searchResultTable']//tr[%s]/td[12]/div");
    private By accountNumberByIndex = By.xpath("//table[@id='searchResultTable']//tr[%s]/td[2]/span/span");
    private By accountNumberSecByIndex = By.xpath("//table[@id='searchResultTable']//tr[%s]//span[@key-name='accountnumber']/../span/span");
    private By accountNumberTwelveByIndex = By.xpath("//table[@id='searchResultTable']//tr[contains(@class, 'searchResultRow ') and " +
            "not(contains(@class, 'searchResultRowHeader'))][%s]//td[12]/span/span");
    private By balance = By.xpath("//table[@id='searchResultTable']//tr[%s]/td[2]/div");
    private By dormantAccountNumberByIndex = By.xpath("//table[@id='searchResultTable']//tr[%s]/td[2]/div");
    private By foundNumberOfRecords = By.xpath("//div[@class='panelContent']/div[@id='dqlSearch']/div/span[contains(text(), 'Found')]");
    private By printBalanceOnReceiptValue = By.xpath("//table[@id='searchResultTable']//tr[%s]/td[10]/div[@key-name='long']");

    @Step("Wait for 'Rules UI Query Analyzer' page loaded")
    public void waitForPageLoad() {
        waitForElementVisibility(searchRegion);
    }

    @Step("Wait for search result table")
    public void waitForSearchResultTable() {
        waitForElementVisibility(searchResultTable);
    }

    @Step("Get number of found records")
    public int getNumberOfFoundRecords() {
        waitForElementVisibility(foundNumberOfRecords);
        String numberOfRecords = getElementText(foundNumberOfRecords).replaceAll("[^0-9]", "");
        return Integer.parseInt(numberOfRecords);
    }

    @Step("Get number of search result")
    public int getNumberOfSearchResult() {
        waitForElementVisibility(listOfSearchResult);
        return getElements(listOfSearchResult).size();
    }

    @Step("Get first name value")
    public String getFirstNameByIndex(int index) {
        waitForElementVisibility(firstNameByIndex, index);
        return getElementText(firstNameByIndex, index);
    }

    @Step("Get last name value")
    public String getLastNameByIndex(int index) {
        waitForElementVisibility(lastNameByIndex, index);
        return getElementText(lastNameByIndex, index);
    }

    @Step("Get account number value")
    public String getAccountNumberByIndex(int index) {
        waitForElementVisibility(accountNumberByIndex, index);
        return getElementText(accountNumberByIndex, index);
    }

    @Step("Get account number value")
    public String getAccountNumberSecByIndex(int index) {
        waitForElementVisibility(accountNumberSecByIndex, index);
        return getElementText(accountNumberSecByIndex, index);
    }

    @Step("Get account number value")
    public String getAccountNumberTwelveByIndex(int index) {
        waitForElementVisibility(accountNumberTwelveByIndex, index);
        return getElementText(accountNumberTwelveByIndex, index);
    }

    @Step("Get account balance value")
    public String getBalanceByIndex(int index) {
        waitForElementVisibility(balance, index);
        return getElementText(balance, index);
    }

    @Step("Get Print Balance On Receipt value")
    public String getPrintBalanceOnReceiptByIndex(int index) {
        waitForElementVisibility(printBalanceOnReceiptValue, index);
        return getElementText(printBalanceOnReceiptValue, index);
    }

    @Step("Get dormant account number value")
    public String getDormantAccountNumberByIndex(int index) {
        waitForElementVisibility(dormantAccountNumberByIndex, index);
        return getElementText(dormantAccountNumberByIndex, index);
    }

    @Step("Is search results table exist")
    public boolean isSearchResultTableExist() {
        return isCondition(Condition.exist, searchResultTable);
    }

    /**
     * Transaction item region
     */

    private By glDateTimePosted = By.xpath("//*[@id='searchResultTable']//tr[%s]//div[@key-name='gldatetimeposted']");
    private By effectiveEntryDate = By.xpath("//table[@id='searchResultTable']//tr[contains(@class, 'searchResultRow ')" +
            " and not(contains(@class, 'searchResultRowHeader'))][%s]//div[@key-name='effectiveentrydate']");
    private By dateOpened = By.xpath("//*[@id='searchResultTable']//tr[%s]//div[@key-name='$DateOpened']");
    private By glFunctionValue = By.xpath("//*[@id='searchResultTable']//tr[%s]//div[@key-name='glfunction']");
    private By transactionHeaderId = By.xpath("//*[@id='searchResultTable']//tr[%s]//*[@key-name='transactionheaderid']");
    private By glTransactionItemPostingStatus = By.xpath("//*[@id='searchResultTable']//tr[%s]" +
            "//*[@key-name='gltransactionitempostingstatus']//..//span[5]/span");
    private By glTransactionItemPostingStatusByTrancode = By.xpath("//td[span[span[contains(text(), '%s')]]]/preceding-sibling::td[6]/span[contains(@class, 'high_title')]/span");

    @Step("Get date posted  {0}")
    public String getDatePosted(int index) {
        waitForElementVisibility(glDateTimePosted, index);
        return getElementText(glDateTimePosted, index).trim();
    }

    @Step("Get effective date {0}")
    public String getEffectiveDate(int index) {
        waitForElementVisibility(effectiveEntryDate, index);
        return getElementText(effectiveEntryDate, index).trim();
    }

    @Step("Get date opened {0}")
    public String getDateOpened(int index) {
        waitForElementVisibility(dateOpened, index);
        return getElementText(dateOpened, index).trim();
    }

    @Step("Get glfunction {0} value")
    public String getGLFunctionValue(int index) {
        waitForElementVisibility(glFunctionValue, index);
        return getElementText(glFunctionValue, index).trim();
    }

    @Step("Get transactionheaderid {0} value")
    public String getTransactionHeaderIdValue(int index) {
        waitForElementVisibility(transactionHeaderId, index);
        return getElementText(transactionHeaderId, index).trim();
    }

    @Step("Get gltransactionitempostingstatus {0} value")
    public String getGLTransactionItemPostingStatusValue(int index) {
        waitForElementVisibility(glTransactionItemPostingStatus, index);
        return getElementText(glTransactionItemPostingStatus, index).trim();
    }

    @Step("Get gltransactionitempostingstatus {transactioncode} value")
    public String getGLTransactionItemPostingStatusByTranCode(String trancode) {
        waitForElementVisibility(glTransactionItemPostingStatusByTrancode, trancode);
        return getElementText(glTransactionItemPostingStatusByTrancode, trancode).trim();
    }

    /**
     * GL interface region
     */
    private By amountField = By.xpath("//*[@id='searchResultTable']//tr[%s]//*[@key-name='amount']");
    private By glInterfaceTransactionHeaderId = By.xpath("//*[@id='searchResultTable']//tr[%s]//*[@key-name='parenttransaction']");
    private By deletedWhen = By.xpath("//*[@id='searchResultTable']//tr[contains(@class, 'searchResultRow restore')][%s]//td[8]//div");
    private By deletedBy = By.xpath("//*[@id='searchResultTable']//tr[contains(@class, 'searchResultRow restore')][%s]//td[9]//div");
    private By listOfSearchResultInterfaceTable = By.xpath("//table[@id='searchResultTable']//tr[contains(@class, 'searchResultRow restore')]");
    private By transactionDeletedStatus = By.xpath("//table[@id='searchResultTable']//tr[contains(@class, 'searchResultRow restore')][%s]//td[2]/span[contains(@class, 'hint')]");

    @Step("Get number of search result")
    public int getNumberOfSearchResultInterfaceTable() {
        waitForElementVisibility(listOfSearchResultInterfaceTable);
        return getElements(listOfSearchResultInterfaceTable).size();
    }

    @Step("Get amount {0} value")
    public String getAmount(int index) {
        waitForElementVisibility(amountField, index);
        return getElementText(amountField, index).trim();
    }

    @Step("Get transactionheaderid {0} value")
    public String getGLInterfaceTransactionHeaderIdValue(int index) {
        waitForElementVisibility(glInterfaceTransactionHeaderId, index);
        return getElementText(glInterfaceTransactionHeaderId, index).trim();
    }


    @Step("Get deletedWhen {0} value")
    public String getDeletedWhenValue(int index) {
        waitForElementVisibility(deletedWhen, index);
        return getElementText(deletedWhen, index).trim();
    }

    @Step("Get deletedBy {0} value")
    public String getDeletedBy(int index) {
        waitForElementVisibility(deletedBy, index);
        return getElementText(deletedBy, index).trim();
    }

    @Step("Get deletedBy {0} value")
    public String getDeletedStatusByIndex(int index) {
        waitForElementVisibility(transactionDeletedStatus, index);
        return getElementText(transactionDeletedStatus, index).trim();
    }

    /**
     * Data BcFile region
     */
    private By systemDateField = By.xpath("//*[@id='searchResultTable']//tr[2]//*[@key-name='date']");
    private By referenceField = By.xpath("//*[@id='searchResultTable']//tr//td[3]//span[contains(@class, 'high_title')]/span");

    @Attachment
    @Step("Get current date - 1 in system")
    public String getDateInSystem() {
        waitForElementVisibility(systemDateField);
        return getElementText(systemDateField).trim();
    }

    @Step("Get financial institution type")
    public String getFinancialInstitutionType() {
        waitForElementVisibility(referenceField);
        return getElementText(referenceField).trim();
    }

    /**
     * CFMIntegrationEnabled BcFile region
     */
    private By nameField = By.xpath("//*[@id='databean_1']//td[contains(@class, 'rulesui-form-item_(databean)name')]/input");
    private By valueField = By.xpath("//*[@id='databean_1']//td[contains(@class, 'rulesui-form-item_long')]/input");

    @Step("Get 'CFMIntegrationEnabled' name field value")
    public String getBankControlFileNameFieldValue() {
        waitForElementVisibility(nameField);
        return getElementAttributeValue("value", nameField).trim();
    }

    @Step("Get 'CFMIntegrationEnabled' value field")
    public String getBankControlFileValue() {
        waitForElementVisibility(valueField);
        return getElementAttributeValue("value", valueField).trim();
    }

    /**
     * Warehouse transaction section
     */
    private By accountNumber = By.xpath("//table[@id='searchResultTable']//tr[@class='searchResultRow ']" +
            "//td[11]/span[last()]/span");
    private By terminalId = By.xpath("//table[@id='searchResultTable']//tr//td[@class='fieldsCell']//div");

    @Step("Get 'accountNumber' {0} value")
    public String getAccountNumberWithWarehouseTransaction(int index) {
        waitForElementVisibility(accountNumber, index);
        return getElementText(accountNumber, index).trim();
    }

    /**
     * terminalId section
     */
    private By terminalIdNumber = By.xpath("//*[@id='searchResultTable']//tr[%s]" +
            "//td[@class='fieldsCell']//div");

    @Step("Get 'terminalId' {0} value")
    public String getTerminalIdValue(int index) {
        waitForElementVisibility(terminalIdNumber, index);
        return getElementText(terminalIdNumber, index).trim();
    }

    @Step("Get 'terminalId' amount in search results table")
    public int getTerminalIdValueCount() {
        waitForElementVisibility(terminalId);
        return getElementsWithZeroOption(terminalId).size();
    }

    /**
     * foreign fee section
     */
    private By foreignFeeValue = By.xpath("//*[@id='searchResultTable']//tr[%s]" +
            "//td[last()]/div");

    @Step("Get 'terminalId' {0} value")
    public String getForeignFeeValue(int index) {
        waitForElementVisibility(foreignFeeValue, index);
        return getElementText(foreignFeeValue, index).trim();
    }

    /**
     * waive AT usage fee acronym
     */
    private By waiveATUsageFeeAcronymValue = By.xpath("//table[@id='searchResultTable']//tr[%s]"
            + "//td[last()]/div");

    @Step("Get 'Waive AT Usage Fee Acronym' {0} value")
    public String getWaiveATUsageFeeAcronymValue(int index) {
        waitForElementVisibility(waiveATUsageFeeAcronymValue, index);
        return getElementText(waiveATUsageFeeAcronymValue, index).trim();
    }

    /**
     * transaction header section
     */
    private By customerId = By.xpath("//table[@id='searchResultTable']//tr[%s]"
            + "//td//span[@key-name='transactioncustomerid']");
    private By name = By.xpath("//table[@id='searchResultTable']//tr[%s]"
            + "//td/div[@key-name='(databean)name']");
    private By interestValue = By.xpath("//table[@id='searchResultTable']//tr[%s]//td/div[@key-name='interest']");

    @Step("Get 'terminalId' {0} value")
    public String getCustomerIdValue(int index) {
        waitForElementVisibility(customerId, index);
        return getElementText(customerId, index).trim();
    }

    @Step("Get 'Name' {0} value")
    public String getNameValue(int index) {
        waitForElementVisibility(name, index);
        return getElementText(name, index).trim();
    }

    @Step("Get 'interest' {0} value")
    public String getInterestValue(int index) {
        waitForElementVisibility(interest, index);
        return getElementText(interest, index).trim();
    }

    @Step("Get 'interest' {0} value")
    public String getInterest(int index) {
        waitForElementVisibility(interestValue, index);
        return getElementText(interestValue, index).trim();
    }

    /**
     * notices section
     */

    private By noticeBankBranch = By.xpath("//table[@id='searchResultTable']//tr[%s]/td[2]/span[5]/span");
    private By noticeAccountId = By.xpath("//table[@id='searchResultTable']//tr[%s]/td[3]/span[5]/span");
    private By noticeAccountType = By.xpath("//table[@id='searchResultTable']//tr[%s]/td[4]/span[5]/span");
    private By noticeDate = By.xpath("//table[@id='searchResultTable']//tr[%s]/td[5]/div");

    @Step("Get 'Bank Branch' {0} value")
    public String getNoticeBankBranchValue(int index) {
        waitForElementVisibility(noticeBankBranch, index);
        return getElementText(noticeBankBranch, index).trim();
    }

    @Step("Get 'Account Id' {0} value")
    public String getNoticeAccountIdValue(int index) {
        waitForElementVisibility(noticeAccountId, index);
        return getElementText(noticeAccountId, index).trim();
    }

    @Step("Get 'Account type' {0} value")
    public String getNoticeAccountTypeValue(int index) {
        waitForElementVisibility(noticeAccountType, index);
        return getElementText(noticeAccountType, index).trim();
    }

    @Step("Get 'Date' {0} value")
    public String getNoticeDateValue(int index) {
        waitForElementVisibility(noticeDate, index);
        return getElementText(noticeDate, index).trim();
    }

    /**
     * Print Checks
     */

    private By accountNumberWithCheckByIndex = By.xpath("//table[@id='searchResultTable']/tbody/tr[contains(@class, 'searchResultRow') "
            + "and not(contains(@class, 'searchResultRowHeader'))][%s]/td[3]//span[contains(@class, 'high_title')]/span");

    @Step("Get 'Account number' {0} value")
    public String getAccountNumberWithCheckValueByIndex(int index) {
        waitForElementVisibility(accountNumberWithCheckByIndex, index);
        return getElementText(accountNumberWithCheckByIndex, index).trim();
    }

    /**
     * Interest Check
     */

    private By officialCheckControlNumber = By.xpath("//table[@id='searchResultTable']//tr[2]/td[2]/span[contains(@class, 'high_title')]/span");

    @Step("Get official check control acc number")
    public String getOfficialCheckControlNumber() {
        return getElementText(officialCheckControlNumber);
    }

    /**
     * Remote Deposit Return EFT Description
     */

    private By remoteDepositReturnEFTDescription = By.xpath("//table[@id='searchResultTable']//tr[2]/td[10]/div[@key-name='string']");

    @Step("Get 'Remote Deposit Return EFT Description' value")
    public String getRemoteDepositReturnEFTDescription() {
        return getElementText(remoteDepositReturnEFTDescription).trim();
    }

    /**
     * CDT Template
     */

    private By cdtTemplateCommittedFromChkOnGlAccount = By.xpath("//table[@id='searchResultTable']/tbody/tr/td[7]/div[contains(text(), '%s')]");

    @Step("Get CDT Template where Misc Debit trans is committed from CHK on GL account")
    public boolean isCdtTemplateCommittedFromChkOnGlAccountCreated(String templateName) {
        return isElementVisible(cdtTemplateCommittedFromChkOnGlAccount, templateName);
    }

    /**
     * Transactions
     */

    private By trBankBranch = By.xpath("//table[@id='searchResultTable']//tr[contains(@class, 'searchResultRow')" +
            " and not(contains(@class, 'searchResultRowHeader'))][%s]//td[2]/span[contains(@class, 'high_title')]/span");
    private By trAccountNumber = By.xpath("//table[@id='searchResultTable']//tr[contains(@class, 'searchResultRow')" +
            " and not(contains(@class, 'searchResultRowHeader'))][%s]//td[3]/span[contains(@class, 'high_title')]/span");
    private By trAmount = By.xpath("//table[@id='searchResultTable']//tr[contains(@class, 'searchResultRow')" +
            " and not(contains(@class, 'searchResultRowHeader'))]//td[4]/div");
    private By trEffectiveDate = By.xpath("//table[@id='searchResultTable']//tr[contains(@class, 'searchResultRow')" +
            " and not(contains(@class, 'searchResultRowHeader'))][%s]//td[5]/span[contains(@class, 'high_title')]/span");
    private By trCode = By.xpath("//table[@id='searchResultTable']//tr[contains(@class, 'searchResultRow')" +
            " and not(contains(@class, 'searchResultRowHeader'))][%s]//td[6]/span[contains(@class, 'high_title')]/span");
    private By trHeaderId = By.xpath("//table[@id='searchResultTable']//tr[contains(@class, 'searchResultRow')" +
            " and not(contains(@class, 'searchResultRowHeader'))][%s]//td[7]/span[contains(@class, 'high_title')]/span");
    private By trEftDescription = By.xpath("//table[@id='searchResultTable']//tr[contains(@class, 'searchResultRow')" +
            " and not(contains(@class, 'searchResultRowHeader'))][%s]//td/div[@key-name='uniqueeftdescription']");
    private By trItemType = By.xpath("//table[@id='searchResultTable']//tr[contains(@class, 'searchResultRow')" +
            " and not(contains(@class, 'searchResultRowHeader'))][%s]//td[6]/span[contains(@class, 'high_title')]/span");
    private By trCheckNumber = By.xpath("//table[@id='searchResultTable']//tr//td/div[@key-name='checknumber']");

    @Step("Get transaction 'Bank branch' {0} value")
    public String getTransactionBankBranchValueByIndex(int index) {
        waitForElementVisibility(trBankBranch, index);
        return getElementText(trBankBranch, index).trim();
    }

    @Step("Get transaction 'Account number' {0} value")
    public String getTransactionAccountNumberByIndex(int index) {
        waitForElementVisibility(trAccountNumber, index);
        return getElementText(trAccountNumber, index).trim();
    }

    @Step("Get transaction 'Amount' {0} value")
    public String getTransactionAmountValueByIndex(int index) {
        waitForElementVisibility(trAmount, index);
        return getElementText(trAmount, index).trim();
    }

    @Step("Get transaction 'Effective date' {0} value")
    public String getTransactionEffectiveDateValueByIndex(int index) {
        waitForElementVisibility(trEffectiveDate, index);
        return getElementText(trEffectiveDate, index).trim();
    }

    @Step("Get transaction 'Code' {0} value")
    public String getTransactionCodeValueByIndex(int index) {
        waitForElementVisibility(trCode, index);
        return getElementText(trCode, index).trim();
    }

    @Step("Get transaction 'Header ID' {0} value")
    public String getTransactionHeaderIdValueByIndex(int index) {
        waitForElementVisibility(trHeaderId, index);
        return getElementText(trHeaderId, index).trim();
    }

    @Step("Get transaction 'Description' {0} value")
    public String getTransactionDescriptionValueByIndex(int index) {
        waitForElementVisibility(trEftDescription, index);
        return getElementText(trEftDescription, index).trim();
    }

    @Step("Get transaction 'Item type' {0} value")
    public String getTransactionItemTypeValueByIndex(int index) {
        waitForElementVisibility(trItemType, index);
        return getElementText(trItemType, index).trim();
    }

    @Step("Get transaction 'Check number' {0} value")
    public String getTransactionCheckNumberValueByIndex(int index) {
        waitForElementVisibility(trCheckNumber, index);
        return getElementText(trCheckNumber, index).trim();
    }

    /**
     * CDT template
     */
    private final By templateName = By.xpath("//tr/td[7]/div");
    private final By templateNameByIndex = By.xpath("//table[@id='searchResultTable']/tbody/tr[@class='searchResultRow '][%s]/td[3]/div");

    @Step("Get list of CDT template names")
    public List<String> getListOfCdtTemplateNames() {
        waitForElementVisibility(templateName);
        return getElementsText(templateName);
    }

    @Step ("Get template name by index")
    public String getCdtTemplateNameByIndex(int index) {
        waitForElementVisibility(templateNameByIndex, index);
        return getElementText(templateNameByIndex, index).trim();
    }

    /**
     * Loan Accounts
     */

    private By loanAccountId = By.xpath("//table[@id='searchResultTable']//tr[%s]/td[5]/span[contains(@class, 'high_title')]/span");
    private By activeConvertedLoanAccountId = By.xpath("//table[contains(@class, 'searchResultPanel')]/tbody/" +
            "tr[contains(@class, 'searchResultRow') and not(contains(@class, 'searchResultRowHeader'))][%s]/" +
            "td[@class='fieldsCell'][10]/span/span");
    private By interestEarned = By.xpath("//table[contains(@class, 'searchResultPanel' )]//" +
            "tr[contains(@class, 'searchResultRow') and not(contains(@class, 'searchResultRowHeader'))][%s]/" +
            "td/div[@key-name='interestearned']");
    private By payoff = By.xpath("//table[contains(@class, 'searchResultPanel' )]//" +
            "tr[contains(@class, 'searchResultRow') and not(contains(@class, 'searchResultRowHeader'))]/td/div[@key-name='payoff']");
    private By totalPast = By.xpath("//table[contains(@class, 'searchResultPanel' )]//" +
            "tr[contains(@class, 'searchResultRow') and not(contains(@class, 'searchResultRowHeader'))]/td/div[@key-name='totalPast']");
    private By principalNext = By.xpath("//table[contains(@class, 'searchResultPanel' )]//" +
            "tr[contains(@class, 'searchResultRow') and not(contains(@class, 'searchResultRowHeader'))]/td/div[@key-name='principalNext']");
    private By principalNextDate = By.xpath("//table[contains(@class, 'searchResultPanel' )]//" +
            "tr[contains(@class, 'searchResultRow') and not(contains(@class, 'searchResultRowHeader'))]/td/div[@key-name='principalnextpaymentdate']");
    private By interestNext = By.xpath("//table[contains(@class, 'searchResultPanel' )]//" +
            "tr[contains(@class, 'searchResultRow') and not(contains(@class, 'searchResultRowHeader'))]/td/div[@key-name='interestNext']");
    private By totalNextDue = By.xpath("//table[contains(@class, 'searchResultPanel' )]//" +
            "tr[contains(@class, 'searchResultRow') and not(contains(@class, 'searchResultRowHeader'))]/td/div[@key-name='totalNext']");
    private By currentDateDue = By.xpath("//table[contains(@class, 'searchResultPanel' )]//" +
            "tr[contains(@class, 'searchResultRow') and not(contains(@class, 'searchResultRowHeader'))]/td/div[@key-name='nextDueDate']");
    private By principalNextPaymentDateByIndex = By.xpath("//table[contains(@class, 'searchResultPanel' )]//" +
            "tr[contains(@class, 'searchResultRow') and not(contains(@class, 'searchResultRowHeader'))][1]/" +
            "td/div[@key-name='principalnextpaymentdate']");
    private By interestNextPaymentDateByIndex = By.xpath("//table[contains(@class, 'searchResultPanel' )]//" +
            "tr[contains(@class, 'searchResultRow') and not(contains(@class, 'searchResultRowHeader'))][%s]/" +
            "td/div[@key-name='interestnextpaymentdate']");

    @Step("Get 'Loan account number' {index} value")
    public String getLoanAccountNumberValueByIndex(int index) {
        waitForElementVisibility(loanAccountId, index);
        return getElementText(loanAccountId, index).trim();
    }

    @Step("Get 'Active Converted Loan Account' {index} value")
    public String getActiveLoanConvertedAccountNumberValueByIndex(int index) {
        waitForElementVisibility(activeConvertedLoanAccountId, index);
        return getElementText(activeConvertedLoanAccountId, index).trim();
    }

    @Step("Get 'Interest Earned' {index} value")
    public String getInterestEarnedValueByIndex(int index) {
        waitForElementVisibility(interestEarned, index);
        return getElementText(interestEarned, index).trim();
    }

    @Step("Get 'Payoff' value")
    public String getPayoff() {
        waitForElementVisibility(payoff);
        return getElementText(payoff).trim();
    }

    @Step("Get 'Total Past' value")
    public String getTotalPast() {
        waitForElementVisibility(totalPast);
        return getElementText(totalPast).trim();
    }

    @Step("Get 'Principal next' value")
    public String getPrincipalNextDue() {
        waitForElementVisibility(principalNext);
        return getElementText(principalNext).trim();
    }

    @Step("Get 'Principal next date' value")
    public String getPrincipalNextDate() {
        waitForElementVisibility(principalNextDate);
        return getElementText(principalNextDate).trim();
    }

    @Step("Get 'Principal' value")
    public String getPrincipalValue(int index) {
        waitForElementVisibility(principal, index);
        return getElementText(principal, index).trim();
    }

    @Step("Get 'Interest next' value")
    public String getInterestNextDue() {
        waitForElementVisibility(interestNext);
        return getElementText(interestNext).trim();
    }

    @Step("Get 'Total next due' value")
    public String getTotalNextDue() {
        waitForElementVisibility(totalNextDue);
        return getElementText(totalNextDue).trim();
    }

    @Step("Get 'Current Date Due' value")
    public String getCurrentDateDue() {
        waitForElementVisibility(currentDateDue);
        return getElementText(currentDateDue).trim();
    }

    @Step("Get 'principalnextpaymentdate' value")
    public String getPrincipalNextPaymentDateByIndex(int index) {
        waitForElementVisibility(principalNextPaymentDateByIndex, index);
        return getElementText(principalNextPaymentDateByIndex, index);
    }

    @Step("Get 'interestnextpaymentdate' value")
    public String getInterestNextPaymentDateByIndex(int index) {
        waitForElementVisibility(interestNextPaymentDateByIndex, index);
        return getElementText(interestNextPaymentDateByIndex, index);
    }


    /**
     * Payment Due
     */

    private final By accountId = By.xpath("//table/tbody//tr[contains(@class, 'searchResultRow') and not(contains(@class, 'searchResultRowHeader'))][%s]//td/span[@key-name='accountid']");
    private final By dueDate = By.xpath("//table/tbody//tr[contains(@class, 'searchResultRow') and not(contains(@class, 'searchResultRowHeader'))][%s]//td/div[@key-name='duedate']");
    private final By escrow = By.xpath("//table/tbody//tr[contains(@class, 'searchResultRow') and not(contains(@class, 'searchResultRowHeader'))][%s]//td/div[@key-name='escrow']");
    private final By amount = By.xpath("//table/tbody//tr[contains(@class, 'searchResultRow') and not(contains(@class, 'searchResultRowHeader'))][%s]//td/div[@key-name='amount']");
    private final By dateAssessed = By.xpath("//table/tbody//tr[contains(@class, 'searchResultRow') and not(contains(@class, 'searchResultRowHeader'))][%s]//td/div[@key-name='dateassessed']");
    private final By paymentDueType = By.xpath("//table/tbody//tr[contains(@class, 'searchResultRow') and not(contains(@class, 'searchResultRowHeader'))][%s]//td/span[@key-name='paymentduetype']/following-sibling::span[3]/span");
    private final By paymentDueStatus = By.xpath("//table/tbody//tr[contains(@class, 'searchResultRow') and not(contains(@class, 'searchResultRowHeader'))][%s]//td/span[@key-name='paymentduestatus']/following-sibling::span[3]/span");
    private final By interest = By.xpath("//table/tbody//tr[contains(@class, 'searchResultRow') and not(contains(@class, 'searchResultRowHeader'))][1]//td/div[@key-name='interest']");
    private final By principal = By.xpath("//table/tbody//tr[contains(@class, 'searchResultRow') and not(contains(@class, 'searchResultRowHeader'))][1]//td/div[@key-name='principal']");

    @Step("Get 'Account id' value")
    public String getAccountIdByIndex(int index) {
        return getElementText(accountId, index).trim();
    }

    @Step("Get 'Due Date' value")
    public String getDueDateByIndex(int index) {
        return getElementText(dueDate, index).trim();
    }

    @Step("Get 'Escrow' value")
    public String getEscrowByIndex(int index) {
        return getElementText(escrow, index).trim();
    }

    @Step("Get 'Interest' value")
    public String getInterestByIndex(int index) {
        return getElementText(interest, index).trim();
    }

    @Step("Get 'Principal' value")
    public String getPrincipalByIndex(int index) {
        return getElementText(principal, index).trim();
    }

    @Step("Get 'Amount' value")
    public String getAmountByIndex(int index) {
        return getElementText(amount, index).trim();
    }

    @Step("Get 'Amount' value")
    public String getDateAssessedByIndex(int index) {
        return getElementText(dateAssessed, index).trim();
    }

    @Step("Get 'Payment Due Type' value")
    public String getPaymentDueTypeByIndex(int index) {
        return getElementText(paymentDueType, index).trim();
    }

    @Step("Get 'Payment Due Status' value")
    public String getPaymentDueStatusByIndex(int index) {
        return getElementText(paymentDueStatus, index).trim();
    }

    /**
     * Participants
     */
    private final By interestEarnedByIndex = By.xpath("(//table//tr//td/div[@key-name='interestearned'])[%s]");
    private final By participantBalanceByIndex = By.xpath("(//table//tr//td/div[@key-name='participantbalance'])[%s]");
    private final By serviceFeeEarnedByIndex = By.xpath("(//table//tr//td/div[@key-name='servicefeeearned'])[%s]");

    @Step("Get 'Participant Interest Earned' value")
    public String getParticipantInterestEarnedByIndex(int index) {
        waitForElementVisibility(interestEarnedByIndex, index);
        return getElementText(interestEarnedByIndex, index);
    }

    @Step("Get 'Service Fee Earned' value")
    public String getParticipantServiceFeeEarnedByIndex(int index) {
        waitForElementVisibility(serviceFeeEarnedByIndex, index);
        return getElementText(serviceFeeEarnedByIndex, index);
    }

    @Step("Get 'Participant Balance' value")
    public String getParticipantBalanceByIndex(int index) {
        waitForElementVisibility(participantBalanceByIndex, index);
        return getElementText(participantBalanceByIndex, index);
    }


    /**
     * Teaser
     */

    private final By effectiveDate = By.xpath("(//div[@key-name='efffectivedate'])[%s]");
    private final By expirationDate = By.xpath("(//div[@key-name='expirationdate'])[%s]");
    private final By noteRate = By.xpath("(//div[@key-name='noterate'])[%s]");
    private final By rateChangeLeadDays = By.xpath("(//div[@key-name='ratechangeleaddays'])[%s]");
    private final By rateChangeType = By.xpath("(//span[@key-name='ratechangetype']/../span/span)[%s]");
    private final By rateIndex = By.xpath("(//span[@key-name='rateindex']/../span/span)[1]");

    @Step("Get Effective Date value")
    public String getEffectiveDateFromTeaser(int index) {
        waitForElementVisibility(effectiveDate, index);
        return getElementText(effectiveDate, index);
    }

    @Step("Get Expiration Date value")
    public String getExpirationDateFromTeaser(int index) {
        waitForElementVisibility(expirationDate, index);
        return getElementText(expirationDate, index);
    }

    @Step("Get Note rate value")
    public String getNoteRateFromTeaser(int index) {
        waitForElementVisibility(noteRate, index);
        return getElementText(noteRate, index);
    }

    @Step("Get Rate Change Lead Days value")
    public String getRateChangeLeadDaysFromTeaser(int index) {
        waitForElementVisibility(rateChangeLeadDays, index);
        return getElementText(rateChangeLeadDays, index);
    }

    @Step("Get RateChangeType value")
    public String getRateChangeTypeFromTeaser(int index) {
        waitForElementVisibility(rateChangeType, index);
        return getElementText(rateChangeType, index);
    }

    @Step("Get Rate Index value")
    public String getRateIndexFromTeaser(int index) {
        waitForElementVisibility(rateIndex, index);
        return getElementText(rateIndex, index);
    }






}