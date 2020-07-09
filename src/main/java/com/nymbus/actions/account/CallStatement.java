package com.nymbus.actions.account;

import com.codeborne.pdftest.PDF;
import com.nymbus.actions.Actions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.utils.DateTime;
import com.nymbus.models.client.Client;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.pages.Pages;
import org.testng.Assert;

import java.io.File;
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
        String transactionAmount = String.valueOf(transaction.getTransactionSource().getAmount());

        Assert.assertTrue(containsText(formattedSystemDate).matches(pdf), "'Transaction Posting Date' does not match.");
        Assert.assertTrue(containsText(transactionAmount).matches(pdf), "'Balance for committed transaction' and 'Transaction amount of Credit' does not match.");
        if (transactionCode.contains("109") || transactionCode.contains("209")) {
            Assert.assertTrue(containsText(transactionCode).matches(pdf), "'Transaction Description' does not match.");
            Assert.assertTrue(containsText("1 Credits").matches(pdf), "'Calculated # of all transactions' does not match.");
        }
    }

    private void verifyChkAccountSectionInPdf(PDF pdf, Account account) {
        Assert.assertTrue(containsText(getCurrentOfficerInitials(account)).matches(pdf), "'Current Officer' does not match.");
        Assert.assertTrue(containsText(account.getDateOpened()).matches(pdf), "'Date Opened' does not match.");
        Assert.assertTrue(containsText(account.getInterestRate()).matches(pdf), "'Dividend Rate' does not match.");

        // TODO: not filled in during account creation
        // Accrued Interest - selected account's Accrued Interest
        // YTD Dividends Paid - selected account's Interest Paid Year to date value
        // Dividends Paid Last Year - selected account's Interest Paid Last Year value
        // YTD Taxes withheld- selected account's Taxes Withheld YTD value
    }

    private void verifySavingsAccountSectionInPdf(PDF pdf, Account account) {
        Assert.assertTrue(containsText(getCurrentOfficerInitials(account)).matches(pdf), "'Current Officer' does not match.");
        Assert.assertTrue(containsText(account.getInterestRate()).matches(pdf), "'Dividend Rate' does not match.");
        Assert.assertTrue(containsText(account.getDateOpened()).matches(pdf), "'Date Opened' does not match.");

        // TODO: not filled in during account creation
        // Accrued Interest - selected account's Accrued Interest
        // YTD Dividends Paid - selected account's Interest Paid Year to date value
        // Dividends Paid Last Year - selected account's Interest Paid Last Year value
        // YTD Taxes withheld- selected account's Taxes Withheld YTD value
    }

    private void verifyCDAccountSectionInPdf(PDF pdf, Account account) {
        Assert.assertTrue(containsText(account.getDateOpened()).matches(pdf), "'Date Opened' does not match.");

        // TODO: not filled in during account creation

        //    Original Balance
        //    Term (months)
        //    Renew - Auto-Renewable field value
        //    Class (Call Class code)
        //    Dividend Freq (Interest Frequency value)
        //    Dividend Rate (Interest Rate)
        //    Maturity
        //    Dividend Type (Interest Type)
        //    Apply To - (Apply interest to value)
        //    YTD Dividends Paid (YTD Interest Paid value)
        //    Dividends Paid Last Year (Interest Paid Last Year value)
        //    YTD taxes withheld (Taxes Withheld YTD value)
        //    Next Dividend (Date next interest value)
        //    Anticipated (Next Interest Payment Amount value)
        //    Accrued (Accrued Interest value)
        //    Daily Accrual (Daily Interest Accrual value)

    }

    private void verifyCDIRAAccountSectionInPdf(PDF pdf, Account account) {
        Assert.assertTrue(containsText(account.getDateOpened()).matches(pdf), "'Date Opened' does not match.");

        // TODO: not filled in during account creation

        //    Original Balance
        //    Term (months)
        //    Renew - Auto-Renewable field value
        //    Class (Call Class code)
        //    Dividend Freq (Interest Frequency value)
        //    Dividend Rate (Interest Rate)
        //    Maturity
        //    Dividend Type (Interest Type)
        //    Apply To - (Apply interest to value)
        //    YTD Dividends Paid (YTD Interest Paid value)
        //    Dividends Paid Last Year (Interest Paid Last Year value)
        //    YTD taxes withheld (Taxes Withheld YTD value)
        //    Next Dividend (Date next interest value)
        //    Anticipated (Next Interest Payment Amount value)
        //    Accrued (Accrued Interest value)
        //    Daily Accrual (Daily Interest Accrual value)
    }

    private void verifySavingsIraAccountSectionInPdf(PDF pdf, Account account) {
        Assert.assertTrue(containsText(getCurrentOfficerInitials(account)).matches(pdf), "'Current Officer' does not match.");
        Assert.assertTrue(containsText(account.getDateOpened()).matches(pdf), "'Date Opened' does not match.");

        // TODO: not filled in during account creation

        // Accrued Interest
        // Dividend Rate - selected account's Interest Rate
        // YTD Dividends Paid - selected account's Interest Paid Year to date value
        // Dividends Paid Last Year - selected account's Interest Paid Last Year value
        // YTD Taxes withheld- selected account's Taxes Withheld YTD value
    }

    public void verifyChkAccountCallStatementData(File file, Account account, IndividualClient client, Transaction transaction) {
        PDF pdf = new PDF(file);

        verifyDateInPdf(pdf);
        verifyClientSectionInPdf(pdf, client, account);
        verifyChkAccountSectionInPdf(pdf, account);
        verifyTransactionSectionInPdf(pdf, transaction);
    }

    public void verifySavingsAccountCallStatementData(File file, Account account, IndividualClient client, Transaction transaction) {
        PDF pdf = new PDF(file);

        verifyDateInPdf(pdf);
        verifyClientSectionInPdf(pdf, client, account);
        verifySavingsAccountSectionInPdf(pdf, account);
        verifyTransactionSectionInPdf(pdf, transaction);
    }

    public void verifyCDAccountCallStatementData(File file, Account account, IndividualClient client, Transaction transaction) {
        PDF pdf = new PDF(file);

        verifyDateInPdf(pdf);
        verifyClientSectionInPdf(pdf, client, account);
        verifyCDAccountSectionInPdf(pdf, account);
        verifyTransactionSectionInPdf(pdf, transaction);
    }

    public void verifyCDIRAAccountCallStatementData(File file, Account account, IndividualClient client, Transaction transaction) {
        PDF pdf = new PDF(file);

        verifyDateInPdf(pdf);
        verifyClientSectionInPdf(pdf, client, account);
        verifyCDIRAAccountSectionInPdf(pdf, account);
        verifyTransactionSectionInPdf(pdf, transaction);
    }

    public void verifySavingsIraAccountCallStatementData(File file, Account account, IndividualClient client, Transaction transaction) {
        PDF pdf = new PDF(file);

        verifyDateInPdf(pdf);
        verifyClientSectionInPdf(pdf, client, account);
        verifySavingsIraAccountSectionInPdf(pdf, account);
        verifyTransactionSectionInPdf(pdf, transaction);
    }

    private String getCurrentOfficerInitials(Account account) {
        StringBuilder initials = new StringBuilder();
        for (String word : account.getCurrentOfficer().split(" ")) {
            initials.append(word.charAt(0));
        }
        return initials.toString();
    }
}
