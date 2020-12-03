package com.nymbus.pages.webadmin.coresetup;

import com.codeborne.selenide.Condition;
import com.nymbus.core.base.PageTools;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class RulesUIQueryAnalyzerPage extends PageTools {

    private By searchRegion = By.xpath("//td[@class='mainPanel' and //form[contains(@action, 'RulesUIQuery')]]");
    private By searchResultTable = By.xpath("//table[@id='searchResultTable']");
    private By listOfSearchResult = By.xpath("//table[@id='searchResultTable']//tr[@class='searchResultRow ']");
    private By firstNameByIndex = By.xpath("//table[@id='searchResultTable']//tr[@class='searchResultRow '][%s]/td[11]/div");
    private By lastNameByIndex = By.xpath("//table[@id='searchResultTable']//tr[@class='searchResultRow '][%s]/td[12]/div");
    private By accountNumberByIndex = By.xpath("//table[@id='searchResultTable']//tr[@class='searchResultRow '][%s]/td[2]/span/span");
    private By dormantAccountNumberByIndex = By.xpath("//table[@id='searchResultTable']//tr[@class='searchResultRow '][%s]/td[2]/div");
    private By foundNumberOfRecords = By.xpath("//div[@class='panelContent']/div[@id='dqlSearch']/div/span[contains(text(), 'Found')]");
    private By printBalanceOnReceiptValue = By.xpath("//table[@id='searchResultTable']//tr[@class='searchResultRow '][%s]/td[10]/div[@key-name='long']");

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

    private By glDateTimePosted = By.xpath("//*[@id='searchResultTable']//tr[@class='searchResultRow '][%s]//div[@key-name='gldatetimeposted']");
    private By effectiveEntryDate =  By.xpath("//*[@id='searchResultTable']//tr[@class='searchResultRow '][%s]//div[@key-name='effectiveentrydate']");
    private By glFunctionValue = By.xpath("//*[@id='searchResultTable']//tr[@class='searchResultRow '][%s]//div[@key-name='glfunction']");
    private By transactionHeaderId = By.xpath("//*[@id='searchResultTable']//tr[@class='searchResultRow '][%s]//*[@key-name='transactionheaderid']");
    private By glTransactionItemPostingStatus = By.xpath("//*[@id='searchResultTable']//tr[@class='searchResultRow '][%s]" +
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
    private By amountField = By.xpath("//*[@id='searchResultTable']//tr[@class='searchResultRow '][%s]//*[@key-name='amount']");
    private By glInterfaceTransactionHeaderId= By.xpath("//*[@id='searchResultTable']//tr[@class='searchResultRow '][%s]//*[@key-name='parenttransaction']");
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
    private By systemDateField = By.xpath("//*[@id='searchResultTable']//tr[@class='searchResultRow '][1]//*[@key-name='date']");
    private By referenceField = By.xpath("//*[@id='searchResultTable']//tr[@class='searchResultRow ']//td[3]//span[contains(@class, 'high_title')]/span");
    
    @Attachment
    @Step ("Get current date - 1 in system")
    public String getDateInSystem() {
        waitForElementVisibility(systemDateField);
        return getElementText(systemDateField).trim();
    }

    @Step ("Get financial institution type")
    public String getFinancialInstitutionType() {
        waitForElementVisibility(referenceField);
        return getElementText(referenceField).trim();
    }

    /**
     * CFMIntegrationEnabled BcFile region
     */
    private By nameField = By.xpath("//*[@id='databean_1']//td[contains(@class, 'rulesui-form-item_(databean)name')]/input");
    private By valueField = By.xpath("//*[@id='databean_1']//td[contains(@class, 'rulesui-form-item_long')]/input");

    @Step ("Get 'CFMIntegrationEnabled' name field value")
    public String getBankControlFileNameFieldValue() {
        waitForElementVisibility(nameField);
        return getElementAttributeValue("value", nameField).trim();
    }

    @Step ("Get 'CFMIntegrationEnabled' value field")
    public String getBankControlFileValue() {
        waitForElementVisibility(valueField);
        return getElementAttributeValue("value", valueField).trim();
    }

    /**
     * Warehouse transaction section
     */
    private By accountNumber = By.xpath("//*[@id='searchResultTable']//tr[@class='searchResultRow '][%s]" +
            "//td[10]/span[last()]/span");
    private By terminalId = By.xpath("//table[@id='searchResultTable']//tr[@class='searchResultRow ']//td[@class='fieldsCell']//div");

    @Step ("Get 'accountNumber' {0} value")
    public String getAccountNumberWithWarehouseTransaction(int index) {
        waitForElementVisibility(accountNumber, index);
        return getElementText(accountNumber, index).trim();
    }

    /**
     *  terminalId section
     */
    private By terminalIdNumber = By.xpath("//*[@id='searchResultTable']//tr[@class='searchResultRow '][%s]" +
            "//td[@class='fieldsCell']//div");

    @Step ("Get 'terminalId' {0} value")
    public String getTerminalIdValue(int index) {
        waitForElementVisibility(terminalIdNumber, index);
        return getElementText(terminalIdNumber, index).trim();
    }

    @Step ("Get 'terminalId' amount in search results table")
    public int getTerminalIdValueCount() {
        waitForElementVisibility(terminalId);
        return getElementsWithZeroOption(terminalId).size();
    }

    /**
     *  foreign fee section
     */
    private By foreignFeeValue = By.xpath("//*[@id='searchResultTable']//tr[@class='searchResultRow '][%s]" +
            "//td[last()]/div");

    @Step ("Get 'terminalId' {0} value")
    public String getForeignFeeValue(int index) {
        waitForElementVisibility(foreignFeeValue, index);
        return getElementText(foreignFeeValue, index).trim();
    }

    /**
     *  waive AT usage fee acronym
     */
    private By waiveATUsageFeeAcronymValue = By.xpath("//table[@id='searchResultTable']//tr[@class='searchResultRow '][%s]"
            + "//td[last()]/div");

    @Step ("Get 'Waive AT Usage Fee Acronym' {0} value")
    public String getWaiveATUsageFeeAcronymValue(int index) {
        waitForElementVisibility(waiveATUsageFeeAcronymValue, index);
        return getElementText(waiveATUsageFeeAcronymValue, index).trim();
    }

    /**
     *  transaction header section
     */
    private By customerId = By.xpath("//table[@id='searchResultTable']//tr[@class='searchResultRow '][%s]"
            + "//td//span[@key-name='transactioncustomerid']");
    private By name = By.xpath("//table[@id='searchResultTable']//tr[@class='searchResultRow '][%s]"
            + "//td/div[@key-name='(databean)name']");

    @Step ("Get 'terminalId' {0} value")
    public String getCustomerIdValue(int index) {
        waitForElementVisibility(customerId, index);
        return getElementText(customerId, index).trim();
    }

    @Step ("Get 'Name' {0} value")
    public String getNameValue(int index) {
        waitForElementVisibility(name, index);
        return getElementText(name, index).trim();
    }

    /**
     * notices section
     */

    private By noticeBankBranch = By.xpath("//table[@id='searchResultTable']//tr[@class='searchResultRow '][%s]/td[2]/span[5]/span");
    private By noticeAccountId = By.xpath("//table[@id='searchResultTable']//tr[@class='searchResultRow '][%s]/td[3]/span[5]/span");
    private By noticeAccountType = By.xpath("//table[@id='searchResultTable']//tr[@class='searchResultRow '][%s]/td[4]/span[5]/span");
    private By noticeDate = By.xpath("//table[@id='searchResultTable']//tr[@class='searchResultRow '][%s]/td[5]/div");

    @Step ("Get 'Bank Branch' {0} value")
    public String getNoticeBankBranchValue(int index) {
        waitForElementVisibility(noticeBankBranch, index);
        return getElementText(noticeBankBranch, index).trim();
    }

    @Step ("Get 'Account Id' {0} value")
    public String getNoticeAccountIdValue(int index) {
        waitForElementVisibility(noticeAccountId, index);
        return getElementText(noticeAccountId, index).trim();
    }

    @Step ("Get 'Account type' {0} value")
    public String getNoticeAccountTypeValue(int index) {
        waitForElementVisibility(noticeAccountType, index);
        return getElementText(noticeAccountType, index).trim();
    }

    @Step ("Get 'Date' {0} value")
    public String getNoticeDateValue(int index) {
        waitForElementVisibility(noticeDate, index);
        return getElementText(noticeDate, index).trim();
    }
}