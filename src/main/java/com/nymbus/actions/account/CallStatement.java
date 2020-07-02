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
        // Date - current business date
        Assert.assertTrue(containsText(formattedSystemDate).matches(pdf));
    }

    private void verifyClientSectionInPdf(PDF pdf, IndividualClient client, Account account) {
        // Member number - Client ID from clients profile (customer->ACCOUNTNUMBER)
        Assert.assertTrue(containsText(client.getIndividualType().getClientID()).matches(pdf));
        // Account number - masked account# (last 4 digits are displayed)
        String accountNumber = account.getAccountNumber();
        Assert.assertTrue(containsText(accountNumber.substring(accountNumber.length() - 4)).matches(pdf));
        // Branch - Bank Branch Number on account
        Assert.assertTrue(containsText(account.getBankBranch()).matches(pdf));
        // Client Name - Client's First Name, Middle Name, Last Name
        Assert.assertTrue(containsText(client.getFullName()).matches(pdf), "Client name does not match, received: " + client.getFullName());
        // Client Address - Address selected on Account (Account Holders and Signers section)
        Assert.assertTrue(containsText(client.getIndividualType().getAddresses().get(0).getAddress()).matches(pdf));
        // Phone - Clients Phone
        String phoneNumber = client.getIndividualClientDetails().getPhones().get(1).getPhoneNumber().replaceFirst("(\\d{3})(\\d{3})(\\d+)", "$1-$2-$3");
        Assert.assertTrue(containsText(phoneNumber).matches(pdf));
    }

    private void verifyTransactionSectionInPdf(PDF pdf, Transaction transaction) {
        String transactionCode = String.join(" ", transaction.getTransactionDestination().getTransactionCode().split("\\W+"));
        String transactionAmount = String.valueOf(transaction.getTransactionSource().getAmount());

        // Date - Transaction Posting Date for committed transaction from transactions tab
        Assert.assertTrue(containsText(formattedSystemDate).matches(pdf));
        // Credits - transaction amount of Credit transaction (Amount is displayed with green color and with '+' sign for such transactions)
        Assert.assertTrue(containsText(transactionAmount).matches(pdf));
        // Balance - Balance for committed transaction from transactions tab
        Assert.assertTrue(containsText(transactionAmount).matches(pdf));
        // Transaction Description - Transaction Code for committed transaction from transactions tab
        // Totals - calculated # of all transactions, total Debits and Total Credits
        if (transactionCode.contains("109") || transactionCode.contains("209")) {
            Assert.assertTrue(containsText(transactionCode).matches(pdf));
            Assert.assertTrue(containsText("1 Credits").matches(pdf));
        }
    }

    private void verifyChkAccountSectionInPdf(PDF pdf, Account account) {
        // Account Type - selected account's Account Type
        Assert.assertTrue(containsText(account.getProduct()).matches(pdf));
        // Officer - selected account's Current Officer
        StringBuilder initials = new StringBuilder();
        for (String word : account.getCurrentOfficer().split(" ")) {
            initials.append(word.charAt(0));
        }
        Assert.assertTrue(containsText(initials.toString()).matches(pdf));
        // Opened - selected account's Date Opened
        Assert.assertTrue(containsText(account.getDateOpened()).matches(pdf));
        // Dividend Rate - selected account's Interest Rate
        Assert.assertTrue(containsText(account.getInterestRate()).matches(pdf));

        // TODO: not filled in during account creation

        // YTD Dividends Paid - selected account's Interest Paid Year to date value
        // Dividends Paid Last Year - selected account's Interest Paid Last Year value
        // YTD Taxes withheld- selected account's Taxes Withheld YTD value
    }

    private void verifySavingsAccountSectionInPdf(PDF pdf, Account account) {
        // Officer - selected account's Current Officer
        StringBuilder initials = new StringBuilder();
        for (String word : account.getCurrentOfficer().split(" ")) {
            initials.append(word.charAt(0));
        }
        Assert.assertTrue(containsText(initials.toString()).matches(pdf));
        // Dividend Rate - selected account's Interest Rate
        Assert.assertTrue(containsText(account.getInterestRate()).matches(pdf));
        // Opened - selected account's Date Opened
        Assert.assertTrue(containsText(account.getDateOpened()).matches(pdf));

        // TODO: not filled in during account creation

        // Accrued Interest - selected account's Accrued Interest
        // YTD Dividends Paid - selected account's Interest Paid Year to date value
        // Dividends Paid Last Year - selected account's Interest Paid Last Year value
        // YTD Taxes withheld- selected account's Taxes Withheld YTD value
    }

    private void verifyCDAccountSectionInPdf(PDF pdf, Account account) {
        // Opened - selected account's Date Opened
        Assert.assertTrue(containsText(account.getDateOpened()).matches(pdf));
        
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
        // Opened - selected account's Date Opened
        Assert.assertTrue(containsText(account.getDateOpened()).matches(pdf));

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
        // Officer - selected account's Current Officer
        StringBuilder initials = new StringBuilder();
        for (String word : account.getCurrentOfficer().split(" ")) {
            initials.append(word.charAt(0));
        }
        Assert.assertTrue(containsText(initials.toString()).matches(pdf));
        // Opened - selected account's Date Opened
        Assert.assertTrue(containsText(account.getDateOpened()).matches(pdf));

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
}
