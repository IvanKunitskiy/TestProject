package com.nymbus.actions.account;

import com.codeborne.pdftest.PDF;
import com.nymbus.actions.Actions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import com.nymbus.core.utils.Functions;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.cashier.CashierDefinedTransactions;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.client.basicinformation.address.Address;
import com.nymbus.newmodels.settings.teller.TellerLocation;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.pages.Pages;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

import static com.codeborne.pdftest.PDF.containsText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertTrue;

public class CallStatement {

    private final String formattedSystemDate = DateTime.getDateWithFormat(WebAdminActions.loginActions().getSystemDate(), "MM/dd/yyyy", "MM/dd/yy");

    /**
     * Download 'CallStatement' PDF file
     */

    public File downloadCallStatementPdfFile() {
        Pages.transactionsPage().clickTheCallStatementButton();
        Actions.mainActions().switchToTab(1);
        Pages.accountStatementPage().waitForLoadingSpinnerInvisibility();
        return Pages.accountStatementPage().downloadCallStatementPdf();
    }

    /**
     * Verify CHK, Savings, Savings IRA account call statement PDF file
     */

    public void verifyChkSavingsIraAccountCallStatementData(Account account, IndividualClient client, Transaction creditTransaction, Transaction debitTransaction, Address seasonalAddress) {
        File callStatementPdfFile = AccountActions.callStatement().downloadCallStatementPdfFile();
        PDF pdf = new PDF(callStatementPdfFile);

        verifyDateInPdf(pdf);
        verifyClientSectionInPdf(pdf, client, account, seasonalAddress);
        verifyChkSavingsIraAccountSectionInPdf(pdf, account);
        verifyTransactionSectionInPdf(pdf, account, creditTransaction, debitTransaction);

        SelenideTools.closeCurrentTab();
        SelenideTools.switchTo().window(0);
    }

    /**
     * Verify transaction PDF file
     */

    public void verifyTransactionData(TellerLocation location, CashierDefinedTransactions transaction,
                                      String proofDate, IndividualClient client, Account account) {
        SelenideTools.sleep(Constants.SMALL_TIMEOUT);
        SelenideTools.sleep(25);
        SelenideTools.switchToLastTab();
        System.out.println(SelenideTools.getDriver().getWindowHandles().size());
        Pages.noticePage().checkPDFVisible();
        SelenideTools.sleep(10);

        File file = Pages.accountStatementPage().downloadCallStatementPdf();
        PDF pdf = new PDF(file);

        verifyTellerLocationInPdf(pdf, location);
        verifyAccountInfoInPdf(pdf, transaction, proofDate, client, account);
    }

    /**
     * Verify transaction PDF file
     */
//TellerLocation location, CashierDefinedTransactions transaction,
//                                      String proofDate,
    public void verifyCallStatementData(IndividualClient client, Account account, Address seasonalAddress, String date) {
        SelenideTools.sleep(Constants.SMALL_TIMEOUT);
        SelenideTools.switchToLastTab();
        Pages.noticePage().checkPDFVisible();
        SelenideTools.sleep(30);
        File file = Pages.accountStatementPage().downloadCallStatementPdf();
        PDF pdf = new PDF(file);

        verifyClientSectionInPdf(pdf, client, account, seasonalAddress, date);
        verifyDepositAccountInPdfFile(pdf, account);

    }

    /**
     * Verify CD, CD IRA account call statement PDF file
     */

    public void verifyCDIRAAccountCallStatementData(Account account, IndividualClient client, Transaction creditTransaction, Transaction debitTransaction, Address seasonalAddress) {
        File callStatementPdfFile = AccountActions.callStatement().downloadCallStatementPdfFile();
        PDF pdf = new PDF(callStatementPdfFile);

        verifyDateInPdf(pdf);
        verifyClientSectionInPdf(pdf, client, account, seasonalAddress);
        verifyCdIraAccountSectionInPdf(pdf, account);
        verifyTransactionSectionInPdf(pdf, account, creditTransaction, debitTransaction);

        SelenideTools.closeCurrentTab();
        SelenideTools.switchTo().window(0);
    }

    /**
     * Verify date in PDF file
     */

    private void verifyDateInPdf(PDF pdf) {
        // Date - current business date
        assertThat(pdf, containsText(formattedSystemDate));
    }

    /**
     * Verify 'Client section' in PDF file
     */

    private void verifyClientSectionInPdf(PDF pdf, IndividualClient client, Account account, Address seasonalAddress) {
        // PIF Number (or Member number) - Client ID from clients profile
        assertThat(pdf, containsText(client.getIndividualType().getClientID()));

        // Account number - masked account# (last 4 digits are displayed)
        String accountNumber = account.getAccountNumber();
        assertThat(pdf, containsText(accountNumber.substring(accountNumber.length() - 4)));

        // Branch - Bank Branch Number on account
        assertThat(pdf, containsText(account.getBankBranch()));

        // Client Name - Client's First Name, Middle Name, Last Name
        assertThat(pdf, containsText(client.getFullName()));

        // Client Address - Client's Seasonal Address
        assertThat(pdf, containsText(client.getIndividualType().getAddresses().get(0).getAddress()));

        // Phone - Clients Phone
        String phoneNumber = client.getIndividualClientDetails().getPhones().get(1).getPhoneNumber().replaceFirst("(\\d{3})(\\d{3})(\\d+)", "$1-$2-$3");
        assertThat(pdf, containsText(phoneNumber));
    }

    /**
     * Verify 'Client section' in PDF file
     */

    private void verifyClientSectionInPdf(PDF pdf, IndividualClient client, Account account, Address seasonalAddress, String date) {
        // PIF Number (or Member number) - Client ID from clients profile
        assertThat(pdf, containsText("CIF #: " + client.getIndividualType().getClientID()));

        // Account number - masked account# (last 4 digits are displayed)
        String accountNumber = account.getAccountNumber();
        assertThat(pdf, containsText(accountNumber.substring(accountNumber.length() - 4)));


        // Client Name - Client's First Name, Middle Name, Last Name
        assertThat(pdf, containsText(client.getFullName()));

        // Client Address - Client's Seasonal Address
        assertThat(pdf, containsText(seasonalAddress.getAddress()));

        // Client Birth Date
        assertThat(pdf, containsText(client.getIndividualType().getBirthDate()));

        // Client Since
        assertThat(pdf, containsText(date));

        // Phone - Clients Phone
        String phoneNumber = client.getIndividualClientDetails().getPhones().get(1).getPhoneNumber().replaceFirst("(\\d{3})(\\d{3})(\\d+)", "$1.$2.$3");
        assertThat(pdf, containsText(phoneNumber));
    }

    /**
     * Verify 'Deposit account' in pdf file
     */
    private void verifyDepositAccountInPdfFile(PDF pdf, Account account) {
        //Deposits:
        //- Accounts are grouped together by their types - first all Checking accounts, then all Savings accounts, then all CD accounts.
        //- "Product" field displays the full "Product" name. (bank.data.ddaprd->Name/bank.data.savprd->Name/bank.data.cdprd->Name)
        //- Sub-headings are displayed for Checking, Savings, and CD accounts.
        //- Total line is displayed for all deposit accounts totals up.


        // Account Type - selected account's Account Type
        if (account.getProductType().equals("CHK Account")) {
            assertThat(pdf, containsText(account.getProduct()));
        }

        // Interest Rate - selected account's Interest Rate
        assertThat(pdf, containsText(account.getInterestRate()));

        //Product
        assertThat(pdf,containsText(account.getProduct()));

    }

    /**
     * Verify 'Client section' in PDF file
     */

    private void verifyTellerLocationInPdf(PDF pdf, TellerLocation location) {
        // Bank Name
        assertThat(pdf, containsText(location.getBankName()));

        // City
        assertThat(pdf, containsText(location.getCity()));

        // State
        assertThat(pdf, containsText(location.getState()));

        // ZipCode
        assertThat(pdf, containsText(location.getZipCode()));

        // Phone - Clients Phone
        assertThat(pdf, containsText(location.getPhoneNumber()));
    }

    /**
     * Verify 'Client section' in PDF file
     */

    private void verifyAccountInfoInPdf(PDF pdf, CashierDefinedTransactions transaction,
                                        String proofDate, IndividualClient client, Account account) {
        // CDT template
        assertThat(pdf, containsText(transaction.getOperation()));

        // Date
        assertThat(pdf, containsText(proofDate));

        // Account number
        assertThat(pdf, containsText(account.getAccountNumber()));

        // Firstname
        assertThat(pdf, containsText(client.getIndividualType().getFirstName()));

        // Adress
        assertThat(pdf, containsText(client.getIndividualType().getAddresses().stream().findFirst().get().getAddress()));
    }

    /**
     * Verify 'Transaction section' in PDF file
     */

    private void verifyTransactionSectionInPdf(PDF pdf, Account account, Transaction creditTransaction, Transaction debitTransaction) {
        // Date - Transaction Posting Date for committed transaction from transactions tab
        assertThat(pdf, containsText(formattedSystemDate));

        // Transaction Description - Transaction Code for committed transaction from transactions tab
        String debitTransactionCode = String.join(" ", debitTransaction.getTransactionSource().getTransactionCode().split("\\W+"));
        assertThat(pdf, containsText(debitTransactionCode));
        String creditTransactionCode = String.join(" ", creditTransaction.getTransactionDestination().getTransactionCode().split("\\W+"));
        assertThat(pdf, containsText(creditTransactionCode));

        if (account.getProductType().equals("CHK Account") || account.getProductType().equals("Savings Account")) {
            // Debits - transaction amount of Debit transaction (Amount is displayed with '-' sign for such transactions)
            assertThat(pdf, containsText("1 Credits"));

            // Credits - transaction amount of Credit transaction (Amount is displayed with '+' sign for such transactions)
            assertThat(pdf, containsText("1 Debits"));
        }

        // Balance - Balance for committed transaction from transactions tab
        String creditTransactionAmount = String.format("%.2f", creditTransaction.getTransactionSource().getAmount());
        assertThat(pdf, containsText(creditTransactionAmount));
        String debitTransactionAmount = String.format("%.2f", debitTransaction.getTransactionSource().getAmount());
        assertThat(pdf, containsText(debitTransactionAmount));

        // Totals - calculated # of all transactions, total Debits and Total Credits
        double total = debitTransaction.getTransactionSource().getAmount() - creditTransaction.getTransactionSource().getAmount();
        assertThat(pdf, containsText((String.format("%.2f", total))));

        //Transaction Date
        assertThat(pdf,containsText(DateTime.getDateWithFormat(creditTransaction.getTransactionDate(),"08/25/2021","08/25/21")));
        assertThat(pdf,containsText(DateTime.getDateWithFormat(debitTransaction.getTransactionDate(),"08/25/2021","08/25/21")));
    }

    /**
     * Verify 'Account section' in Checking, Savings and Savings Ira accounts in PDF file
     */

    private void verifyChkSavingsIraAccountSectionInPdf(PDF pdf, Account account) {
        // Account Type - selected account's Account Type
        if (account.getProductType().equals("CHK Account")) {
            assertThat(pdf, containsText(account.getProduct()));
        }

        // Officer - selected account's Current Officer
        assertThat(pdf, containsText(getCurrentOfficerInitials(account)));

        // Opened - selected account's Date Opened
        assertThat(pdf, containsText(account.getDateOpened()));

        // Interest Rate - selected account's Interest Rate
        assertThat(pdf, containsText(account.getInterestRate()));
    }

    /**
     * Verify 'Account section' in CD and CD IRA accounts in PDF file
     */

    private void verifyCdIraAccountSectionInPdf(PDF pdf, Account account) {
        // Date Opened - selected account's Date Opened
        assertThat(pdf, containsText(account.getDateOpened()));

        // Original Balance - selected account's Original Balance value
        assertThat(pdf, containsText(account.getOriginalBalance()));

        // Term (months) - selected account's Term In Months Or Days value
        assertThat(pdf, containsText(account.getTerm()));

        // Class - selected account's Call Class code value
        if (!(account.getCallClassCode() == null)) {
            assertThat(pdf, containsText(account.getCallClassCode()));
        }

        // Renew - selected account's Auto-Renewable field value
        assertThat(pdf, containsText(Functions.capitalize(account.getAutoRenewable().toLowerCase())));

        // Interest Freq - selected account's Interest Frequency value
        assertThat(pdf, containsText(account.getInterestFrequency()));

        // Interest Rate selected account's Interest Rate value
        assertThat(pdf, containsText(account.getInterestRate()));

        // Maturity - selected account's Maturity Date
        assertThat(pdf, containsText(account.getMaturityDate()));

        // Interest Type - selected account's Interest Type
        assertThat(pdf, containsText(account.getInterestType()));

        // Apply To - selected account's Apply interest to value
        assertThat(pdf, containsText(account.getApplyInterestTo()));

        // Next Interest - selected account's Date next interest value
        assertThat(pdf, containsText(account.getDateNextInterest()));

        // Anticipated - selected account's Next Interest Payment Amount value
        assertThat(pdf, containsText(account.getNextInterestPaymentAmount()));

        // Accrued - selected account's Accrued Interest value
        assertThat(pdf, containsText(account.getAccruedInterest()));

        // Daily Accrual - selected account's Daily Interest Accrual value
        assertThat(pdf, containsText(account.getDailyInterestAccrual()));
    }

    private String getCurrentOfficerInitials(Account account) {
        StringBuilder initials = new StringBuilder();
        for (String word : account.getCurrentOfficer().split(" ")) {
            initials.append(word.charAt(0));
        }
        return initials.toString();
    }

    public void setAddress(IndividualClient client) {
        Pages.callStatementModalPage().clickAddressSelectorButton();
        List<String> listOfAddress = Pages.callStatementModalPage().getAddressList();
        assertTrue(listOfAddress.size() > 0, "There are no options available");
        Pages.callStatementModalPage().clickAddressSelectorOption(client.getIndividualType().getAddresses().get(0).getAddress());
    }

    public void setDataForChkSavingsIraAccountCallStatementVerification(Account account) {
        setInterestRate(account);
    }

    public void setDataForCDIRAAAccountCallStatementVerification(Account account) {
        setAccruedInterest(account);
        setOriginalBalance(account);
        setTerm(account);
        setDateNextInterest(account);
        setMaturityDate(account);
        setDailyInterestAccrual(account);
        setNextInterestPaymentAmount(account);
    }

    public void setDailyInterestAccrual(Account account) {
        String interestRate = Pages.accountDetailsPage().getInterestRateValue();
        if (!interestRate.isEmpty()) {
            DecimalFormat df = new DecimalFormat("#.####");
            double rate = Double.parseDouble(interestRate);
            double dailyAccrual = ((rate / 100) / 365) * 100;
            account.setDailyInterestAccrual(df.format(dailyAccrual));
        } else {
            account.setDailyInterestAccrual("0.00");
        }
    }

    public void setMaturityDate(Account account) {
        String maturityDate = Pages.accountDetailsPage().getMaturityDate();
        account.setMaturityDate(maturityDate);
    }

    public void setDateNextInterest(Account account) {
        String dateNextInterest = Pages.accountDetailsPage().getDateNextInterest();
        account.setDateNextInterest(dateNextInterest);
    }

    public void setTerm(Account account) {
        String term = Pages.accountDetailsPage().getTermInMonthOrDays().replaceAll("[^0-9]", "");
        account.setTerm(term);
    }

    public void setOriginalBalance(Account account) {
        String originalBalance = Pages.accountDetailsPage().getOriginalBalanceValue();
        if (!originalBalance.isEmpty()) {
            account.setOriginalBalance(originalBalance);
        } else {
            account.setOriginalBalance("0.00");
        }
    }

    public void setAccruedInterest(Account account) {
        String accruedInterest = Pages.accountDetailsPage().getAccruedInterest();
        if (!accruedInterest.isEmpty()) {
            account.setAccruedInterest(accruedInterest);
        } else {
            account.setAccruedInterest("0.00");
        }
    }

    public void setInterestRate(Account account) {
        String interestRate = Pages.accountDetailsPage().getInterestRateValue();
        if (!interestRate.isEmpty()) {
            account.setInterestRate(interestRate);
        } else {
            account.setInterestRate("0.00");
        }
    }

    public void setNextInterestPaymentAmount(Account account) {
        String nextInterestPaymentAmount = Pages.accountDetailsPage().getNextInterestPaymentAmount();
        if (!nextInterestPaymentAmount.isEmpty()) {
            account.setNextInterestPaymentAmount(nextInterestPaymentAmount);
        } else {
            account.setNextInterestPaymentAmount("0.00");
        }
    }

    public void navigateToTransactionsAndVerifyPdfContainsText(String text) {
        Pages.accountNavigationPage().clickTransactionsTab();
        PDF pdf = new PDF(AccountActions.callStatement().downloadCallStatementPdfFile());
        assertThat(pdf, containsText(text));
    }

    public void verifyYtdInterestPaidValue() {
        String ytdInterestPaidAccountNumber = WebAdminActions.webAdminUsersActions().getAccountWithYtdInterestPaidNotNull();
        if (!ytdInterestPaidAccountNumber.isEmpty()) {
            Pages.aSideMenuPage().clickClientMenuItem();
            Actions.clientPageActions().searchAndOpenAccountByAccountNumber(ytdInterestPaidAccountNumber);
            String interestPaidYTD = Pages.accountDetailsPage().getInterestPaidYTD();
            String interestPaidYTDWithLocale = Functions.getNumberWithLocale(Double.parseDouble(interestPaidYTD));
            AccountActions.callStatement().navigateToTransactionsAndVerifyPdfContainsText(interestPaidYTDWithLocale);
        }
        SelenideTools.closeCurrentTab();
        SelenideTools.switchTo().window(0);
    }

    public void verifyInterestPaidLastYearValue() {
        String interestPaidLastYearAccountNumber = WebAdminActions.webAdminUsersActions().getAccountWithInterestPaidLastYearNotNull();
        if (!interestPaidLastYearAccountNumber.isEmpty()) {
            Pages.aSideMenuPage().clickClientMenuItem();
            Actions.clientPageActions().searchAndOpenAccountByAccountNumber(interestPaidLastYearAccountNumber);
            String interestPaidLastYear = Pages.accountDetailsPage().getInterestPaidLastYear();
            String interestPaidLastYearWithLocale = Functions.getNumberWithLocale(Double.parseDouble(interestPaidLastYear));
            AccountActions.callStatement().navigateToTransactionsAndVerifyPdfContainsText(interestPaidLastYearWithLocale);
        }
        SelenideTools.closeCurrentTab();
        SelenideTools.switchTo().window(0);
    }

    public void verifyYtdTaxesWithheldValue() {
        String ytdTaxesWithheldAccountNumber = WebAdminActions.webAdminUsersActions().getAccountWithYtdTaxesWithheldNotNull();
        if (!ytdTaxesWithheldAccountNumber.isEmpty()) {
            Pages.aSideMenuPage().clickClientMenuItem();
            Actions.clientPageActions().searchAndOpenAccountByAccountNumber(ytdTaxesWithheldAccountNumber);
            String ytdTaxesWithheld = Pages.accountDetailsPage().getTaxesWithheldYTD();
            String ytdTaxesWithheldWithLocale = Functions.getNumberWithLocale(Double.parseDouble(ytdTaxesWithheld));
            AccountActions.callStatement().navigateToTransactionsAndVerifyPdfContainsText(ytdTaxesWithheldWithLocale);
        }
        SelenideTools.closeCurrentTab();
        SelenideTools.switchTo().window(0);
    }

    public void verifyOverdraftWasChargedOffValue() {
        String overdraftChargedOffAccountNumber = WebAdminActions.webAdminUsersActions().getAccountsWithOverdraftChargedOffMoreThanZeroNull();
        if (!overdraftChargedOffAccountNumber.isEmpty()) {
            Pages.aSideMenuPage().clickClientMenuItem();
            Actions.clientPageActions().searchAndOpenAccountByAccountNumber(overdraftChargedOffAccountNumber);
            String overdraftChargedOff = Pages.accountDetailsPage().getOverdraftChargedOff();
            String overdraftChargedOffWithLocale = Functions.getNumberWithLocale(Double.parseDouble(overdraftChargedOff));
            AccountActions.callStatement().navigateToTransactionsAndVerifyPdfContainsText(overdraftChargedOffWithLocale);
        }
        SelenideTools.closeCurrentTab();
        SelenideTools.switchTo().window(0);
    }
}
