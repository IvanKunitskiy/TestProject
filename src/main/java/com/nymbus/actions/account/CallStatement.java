package com.nymbus.actions.account;

import com.codeborne.pdftest.PDF;
import com.nymbus.actions.Actions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.utils.DateTime;
import com.nymbus.core.utils.Functions;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.client.IndividualClient;
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

    public void verifyChkSavingsIraAccountCallStatementData(Account account, IndividualClient client, Transaction transaction) {
        File callStatementPdfFile = AccountActions.callStatement().downloadCallStatementPdfFile();
        PDF pdf = new PDF(callStatementPdfFile);

        verifyDateInPdf(pdf);
        verifyClientSectionInPdf(pdf, client, account);
        verifyChkSavingsIraAccountSectionInPdf(pdf, account);
        verifyTransactionSectionInPdf(pdf, transaction);

        SelenideTools.closeCurrentTab();
        SelenideTools.switchTo().window(0);
    }

    /**
     * Verify CD, CD IRA account call statement PDF file
     */

    public void verifyCDIRAAccountCallStatementData(Account account, IndividualClient client, Transaction transaction) {
        File callStatementPdfFile = AccountActions.callStatement().downloadCallStatementPdfFile();
        PDF pdf = new PDF(callStatementPdfFile);

        verifyDateInPdf(pdf);
        verifyClientSectionInPdf(pdf, client, account);
        verifyCdIraAccountSectionInPdf(pdf, account);
        verifyTransactionSectionInPdf(pdf, transaction);

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

    private void verifyClientSectionInPdf(PDF pdf, IndividualClient client, Account account) {
        String accountNumber = account.getAccountNumber();
        String phoneNumber = client.getIndividualClientDetails().getPhones().get(1).getPhoneNumber().replaceFirst("(\\d{3})(\\d{3})(\\d+)", "$1-$2-$3");

        // PIF Number (or Member number) - Client ID from clients profile
        assertThat(pdf, containsText(client.getIndividualType().getClientID()));

        // Account number - masked account# (last 4 digits are displayed)
        assertThat(pdf, containsText(accountNumber.substring(accountNumber.length() - 4)));

        // Branch - Bank Branch Number on account
        assertThat(pdf, containsText(account.getBankBranch()));

        // Client Name - Client's First Name, Middle Name, Last Name
        assertThat(pdf, containsText(client.getFullName()));

        // TODO: Address should be equal to 'Client's Seasonal Address'
        assertThat(pdf, containsText(client.getIndividualType().getAddresses().get(0).getAddress()));

        // Phone - Clients Phone
        assertThat(pdf, containsText(phoneNumber));
    }

    /**
     * Verify 'Transaction section' in PDF file
     */

    private void verifyTransactionSectionInPdf(PDF pdf, Transaction transaction) {
        String transactionCode = String.join(" ", transaction.getTransactionDestination().getTransactionCode().split("\\W+"));
        String transactionAmount = String.format("%.2f", transaction.getTransactionSource().getAmount());

        assertThat(pdf, containsText(formattedSystemDate));
        assertThat(pdf, containsText(transactionAmount));
        if (transactionCode.contains("109") || transactionCode.contains("209")) {
            assertThat(pdf, containsText(transactionCode));
            assertThat(pdf, containsText("1 Credits"));
        }
        // TODO: verify Overdraft Charged Off (account's Overdraft Charged Off value) - skip this field for account, for which 105-Overdraft Charge Off transaction was not performed.
    }

    /**
     * Verify 'Account section' in Checking, Savings and Savings Ira accounts in PDF file
     */

    private void verifyChkSavingsIraAccountSectionInPdf(PDF pdf, Account account) {
        assertThat(pdf, containsText(getCurrentOfficerInitials(account)));
        assertThat(pdf, containsText(account.getDateOpened()));
        assertThat(pdf, containsText(account.getInterestRate()));
        assertThat(pdf, containsText(account.getAccruedInterest()));
    }

    /**
     * Verify 'Account section' in CD and CD IRA accounts in PDF file
     */

    private void verifyCdIraAccountSectionInPdf(PDF pdf, Account account) {
        assertThat(pdf, containsText(account.getDateOpened()));
        assertThat(pdf, containsText(account.getAccruedInterest()));
        assertThat(pdf, containsText(account.getOriginalBalance()));
        assertThat(pdf, containsText(account.getProduct()));
        assertThat(pdf, containsText(account.getInterestFrequency()));
        assertThat(pdf, containsText(account.getInterestRate()));
        assertThat(pdf, containsText(account.getInterestType()));
        assertThat(pdf, containsText(account.getApplyInterestTo()));
        assertThat(pdf, containsText(account.getTerm()));
        assertThat(pdf, containsText(account.getDateNextInterest()));
        assertThat(pdf, containsText(Functions.capitalize(account.getAutoRenewable().toLowerCase())));
        assertThat(pdf, containsText(account.getMaturityDate()));
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
        setAccruedInterest(account);
        setInterestRate(account);
    }

    public void setDataForCDIRAAAccountCallStatementVerification(Account account) {
        setAccruedInterest(account);
        setOriginalBalance(account);
        setTerm(account);
        setDateNextInterest(account);
        setMaturityDate(account);
        setDailyInterestAccrual(account);
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
            AccountActions.callStatement().navigateToTransactionsAndVerifyPdfContainsText(interestPaidYTD);
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
            AccountActions.callStatement().navigateToTransactionsAndVerifyPdfContainsText(interestPaidLastYear);
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
            AccountActions.callStatement().navigateToTransactionsAndVerifyPdfContainsText(ytdTaxesWithheld);
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
            AccountActions.callStatement().navigateToTransactionsAndVerifyPdfContainsText(overdraftChargedOff);
        }
        SelenideTools.closeCurrentTab();
        SelenideTools.switchTo().window(0);
    }
}
