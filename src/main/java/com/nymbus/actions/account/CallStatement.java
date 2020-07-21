package com.nymbus.actions.account;

import com.codeborne.pdftest.PDF;
import com.nymbus.actions.Actions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.utils.DateTime;
import com.nymbus.core.utils.Functions;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.models.client.Client;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.pages.Pages;
import org.testng.Assert;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

import static com.codeborne.pdftest.PDF.containsText;

public class CallStatement {

    private final String formattedSystemDate = DateTime.getDateWithFormat(WebAdminActions.loginActions().getSystemDate(), "MM/dd/yyyy", "MM/dd/yy");

    public void setAddress(Client client) {
        Pages.callStatementModalPage().clickAddressSelectorButton();
        List<String> listOfAddress = Pages.callStatementModalPage().getAddressList();
        Assert.assertTrue(listOfAddress.size() > 0, "There are no options available");
        Pages.callStatementModalPage().clickAddressSelectorOption(client.getAddress().getAddress());
    }

    public void setAddress(IndividualClient client) {
        Pages.callStatementModalPage().clickAddressSelectorButton();
        List<String> listOfAddress = Pages.callStatementModalPage().getAddressList();
        Assert.assertTrue(listOfAddress.size() > 0, "There are no options available");
        Pages.callStatementModalPage().clickAddressSelectorOption(client.getIndividualType().getAddresses().get(0).getAddress());
    }

    public File downloadCallStatementPdfFile() {
        Pages.transactionsPage().clickTheCallStatementButton();
        Actions.mainActions().switchToTab(1);
        Pages.accountStatementPage().waitForLoadingSpinnerInvisibility();
        return Pages.accountStatementPage().downloadCallStatementPdf();
    }

    private void verifyDateInPdf(PDF pdf) {
        Assert.assertTrue(containsText(formattedSystemDate).matches(pdf), "'Current business date' does not match");
    }

    private void verifyClientSectionInPdf(PDF pdf, IndividualClient client, Account account) {
        String accountNumber = account.getAccountNumber();
        String phoneNumber = client.getIndividualClientDetails().getPhones().get(1).getPhoneNumber().replaceFirst("(\\d{3})(\\d{3})(\\d+)", "$1-$2-$3");

        Assert.assertTrue(containsText(client.getIndividualType().getClientID()).matches(pdf), "'Member number' does not match.");
        Assert.assertTrue(containsText(accountNumber.substring(accountNumber.length() - 4)).matches(pdf), "'Account number' does not match.");
        Assert.assertTrue(containsText(account.getBankBranch()).matches(pdf), "'Bank Branch' does not match.");
        Assert.assertTrue(containsText(client.getFullName()).matches(pdf), "'Client name' does not match");
        Assert.assertTrue(containsText(client.getIndividualType().getAddresses().get(0).getAddress()).matches(pdf), "'Client Addres' does not match.");
        Assert.assertTrue(containsText(phoneNumber).matches(pdf), "'Phone' does not match.");
    }

    private void verifyTransactionSectionInPdf(PDF pdf, Transaction transaction) {
        String transactionCode = String.join(" ", transaction.getTransactionDestination().getTransactionCode().split("\\W+"));
        String transactionAmount = String.format("%.2f", transaction.getTransactionSource().getAmount());

        Assert.assertTrue(containsText(formattedSystemDate).matches(pdf), "'Transaction Posting Date' does not match.");
        Assert.assertTrue(containsText(transactionAmount).matches(pdf), "'Balance for committed transaction' and 'Transaction amount of Credit' does not match.");
        if (transactionCode.contains("109") || transactionCode.contains("209")) {
            Assert.assertTrue(containsText(transactionCode).matches(pdf), "'Transaction Description' does not match.");
            Assert.assertTrue(containsText("1 Credits").matches(pdf), "'Calculated # of all transactions' does not match.");
        }
//        Overdraft Charged Off - selected account's Overdraft Charged Off value - skip this field for Account, for which 105-Overdraft Charge Off transaction was not performed.
    }

    // Common for Checking, Savings and Savings Ira
    private void verifyChkSavingsIraAccountSectionInPdf(PDF pdf, Account account) {
        Assert.assertTrue(containsText(getCurrentOfficerInitials(account)).matches(pdf), "'Current Officer' does not match.");
        Assert.assertTrue(containsText(account.getDateOpened()).matches(pdf), "'Date Opened' does not match.");
        Assert.assertTrue(containsText(account.getInterestRate()).matches(pdf), "'Dividend Rate' does not match.");
        Assert.assertTrue(containsText(account.getAccruedInterest()).matches(pdf), "'Accrued Interest' does not match");
//        Assert.assertTrue(containsText(account.getInterestPaidYTD()).matches(pdf), "'Interest Paid YTD' does not match");
//        Assert.assertTrue(containsText(account.getInterestPaidLastYear()).matches(pdf), "'Interest Paid Last Year' does not match");
//        Assert.assertTrue(containsText(account.getTaxesWithheldYTD()).matches(pdf), "'YTD Taxes withheld' does not match");
    }

    // Common for CD and CD IRA account
    private void verifyCdIraAccountSectionInPdf(PDF pdf, Account account) {
        Assert.assertTrue(containsText(account.getDateOpened()).matches(pdf), "'Date Opened' does not match.");
        Assert.assertTrue(containsText(account.getAccruedInterest()).matches(pdf), "'Accrued Interest' does not match");
        Assert.assertTrue(containsText(account.getInterestPaidYTD()).matches(pdf), "'Interest Paid YTD' does not match");
        Assert.assertTrue(containsText(account.getInterestPaidLastYear()).matches(pdf), "'Interest Paid Last Year' does not match");
        Assert.assertTrue(containsText(account.getTaxesWithheldYTD()).matches(pdf), "'YTD Taxes withheld' does not match");
        Assert.assertTrue(containsText(account.getOriginalBalance()).matches(pdf), "'Original Balance' does not match");
        Assert.assertTrue(containsText(account.getProduct()).matches(pdf), "'Class' does not match");
        Assert.assertTrue(containsText(account.getInterestFrequency()).matches(pdf), "'Dividend Freq (Interest Frequency value)', does not match");
        Assert.assertTrue(containsText(account.getInterestRate()).matches(pdf), "'Dividend Rate (Interest Rate)' doe not match");
        Assert.assertTrue(containsText(account.getInterestType()).matches(pdf), "'Dividend Type (Interest Type)' does not match");
        Assert.assertTrue(containsText(account.getApplyInterestTo()).matches(pdf), "Apply To (Apply interest to value) does not match");
        Assert.assertTrue(containsText(account.getTerm()).matches(pdf), "'Term (months)' does not match");
        Assert.assertTrue(containsText(account.getDateNextInterest()).matches(pdf), "'Next Dividend (Date next interest value)' does not match");
        Assert.assertTrue(containsText(Functions.capitalize(account.getAutoRenewable().toLowerCase())).matches(pdf), "'Renew - Auto-Renewable' does not match");
        Assert.assertTrue(containsText(account.getMaturityDate()).matches(pdf), "'Maturity' does not match");
        Assert.assertTrue(containsText(account.getDailyInterestAccrual()).matches(pdf), "'Daily Interest Accrual' does not match");
    }

    // Verify CHK, Savings, Savings IRA call statement pdf
    public void verifyChkSavingsIraAccountCallStatementData(File file, Account account, IndividualClient client, Transaction transaction) {
        File callStatementPdfFile = AccountActions.callStatement().downloadCallStatementPdfFile();
        PDF pdf = new PDF(callStatementPdfFile);

        verifyDateInPdf(pdf);
        verifyClientSectionInPdf(pdf, client, account);
        verifyChkSavingsIraAccountSectionInPdf(pdf, account);
        verifyTransactionSectionInPdf(pdf, transaction);

        SelenideTools.closeCurrentTab();
        SelenideTools.switchTo().window(0);
    }

    // Verify CD, CD IRA call statement pdf
    public void verifyCDIRAAccountCallStatementData(File file, Account account, IndividualClient client, Transaction transaction) {
        PDF pdf = new PDF(file);

        verifyDateInPdf(pdf);
        verifyClientSectionInPdf(pdf, client, account);
        verifyCdIraAccountSectionInPdf(pdf, account);
        verifyTransactionSectionInPdf(pdf, transaction);
    }

    private String getCurrentOfficerInitials(Account account) {
        StringBuilder initials = new StringBuilder();
        for (String word : account.getCurrentOfficer().split(" ")) {
            initials.append(word.charAt(0));
        }
        return initials.toString();
    }

    public void setDataForChkSavingsIraAccountCallStatementVerification(Account account) {
        setAccruedInterest(account);
        setInterestRate(account);
//        setInterestPaidYTD(account);
//        setInterestPaidLastYear(account);
//        setTaxesWithheldYTD(account);
    }

    public void setDataForCDIRAAAccountCallStatementVerification(Account account) {
        setAccruedInterest(account);
//        setInterestPaidYTD(account);
//        setInterestPaidLastYear(account);
//        setTaxesWithheldYTD(account);
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
        Assert.assertTrue(containsText(text).matches(pdf), "Given text is not present in PDF document");
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
