package com.nymbus.actions.clients;

import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.models.TempClient;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.loanaccount.PaymentDueData;
import com.nymbus.pages.Pages;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class ClientPageActions {

    public List<TempClient> getAllClientsFromLookupResults(int lookupResultsCount) {
        List<TempClient> clients = new ArrayList<>();
        for (int i = 1; i <= lookupResultsCount; i++) {
            TempClient client = new TempClient();
            client.setFirstName(Pages.clientsSearchPage().getClientFirstNameFromLookupResultByIndex(i));
            client.setLastName(Pages.clientsSearchPage().getClientLastNameFromLookupResultByIndex(i));
            clients.add(client);
        }

        return clients;
    }

    public List<String> getAllClientFirstName(int lookupResultsCount) {
        List<String> listOfFirstName = new ArrayList<>();

        for (int i = 1; i <= lookupResultsCount; i++) {
            listOfFirstName.add(Pages.clientsSearchPage().getClientFirstNameFromLookupResultByIndex(i));
        }

        return listOfFirstName;
    }

    public List<String> getAllClientLastName(int lookupResultsCount) {
        List<String> listOfLastName = new ArrayList<>();

        for (int i = 1; i <= lookupResultsCount; i++) {
            listOfLastName.add(Pages.clientsSearchPage().getClientLastNameFromLookupResultByIndex(i));
        }

        return listOfLastName;
    }

    public void searchAndOpenClientByName(String name) {
        Pages.aSideMenuPage().clickClientMenuItem();
        Pages.clientsSearchPage().typeToClientsSearchInputField(name);
        Pages.clientsSearchResultsPage().clickSearchResultsWithText(name);
    }

    public void searchAndOpenIndividualClientByID(String clientID) {
        Pages.clientsSearchPage().typeToClientsSearchInputField(clientID);
        Assert.assertEquals(Pages.clientsSearchPage().getAllLookupResults().size(), 1, "There is more than one client found");
        Assert.assertTrue(Pages.clientsSearchPage().isSearchResultsRelative(Pages.clientsSearchPage().getAllLookupResults(), clientID));
        Pages.clientsSearchPage().clickOnSearchButton();
        Pages.clientsSearchResultsPage().clickTheExactlyMatchedClientInSearchResults();
        Pages.clientDetailsPage().waitForPageLoaded();
    }

    public void searchAndOpenAccountByAccountNumber(Account account) {
        Pages.clientsSearchPage().typeToClientsSearchInputField(account.getAccountNumber());
        Assert.assertTrue(Pages.clientsSearchPage().isSearchResultsRelative(Pages.clientsSearchPage().getAllLookupResults(), account.getAccountNumber()));
        Pages.clientsSearchPage().clickOnSearchButton();
        Pages.clientsSearchResultsPage().clickTheExactlyMatchedAccountInSearchResults(account.getAccountNumber());
    }

    public void searchAndOpenAccountByAccountNumber(String accountNumber) {
        Pages.clientsSearchPage().typeToClientsSearchInputField(accountNumber);
        Assert.assertTrue(Pages.clientsSearchPage().isSearchResultsRelative(Pages.clientsSearchPage().getAllLookupResults(), accountNumber));
        Pages.clientsSearchPage().clickOnSearchButton();
        Pages.clientsSearchResultsPage().clickTheExactlyMatchedAccountInSearchResults(accountNumber);
    }

    public void closeAllNotifications() {
        int notificationsCount = Pages.clientDetailsPage().getNotificationCount();

        closeNotifications(notificationsCount);
    }

    private void closeNotifications(int count) {
        for (int i = 1; i <= count; count--) {
            Pages.clientDetailsPage().clickNotificationByIndex(i);
            SelenideTools.sleep(Constants.MICRO_TIMEOUT);
            Pages.clientDetailsPage().clickCloseNotificationByIndex(i);
            SelenideTools.sleep(Constants.MICRO_TIMEOUT);
        }
        Pages.clientDetailsPage().waitForNotificationInvisibility(1);
    }

    public void checkRejectedItems(int rejectedTransactionsItems) {
        if (rejectedTransactionsItems == 1) {
            Assert.assertTrue(Pages.accountTransactionPage().isNoResultsVisible(), "Transaction is not visible");
        } else {
            int rejectedTransactionsItems1 = Pages.accountTransactionPage().getRejectedTransactionsItems();
            Assert.assertTrue(rejectedTransactionsItems1 < rejectedTransactionsItems,
                    "Transaction is not visible");
        }
    }

    public PaymentDueData getPaymentDueInfo(Account loanAccount) {
        PaymentDueData paymentDueData = new PaymentDueData();
        paymentDueData.setDueDate(loanAccount.getNextPaymentBilledDueDate());
        paymentDueData.setPrincipal(0.00);
        String currentBalance = Pages.accountDetailsPage().getCurrentBalance();
        String currentEffectiveRate = Pages.accountDetailsPage().getCurrentEffectiveRate();
        String daysBaseYearBase = Pages.accountDetailsPage().getDaysBaseYearBase();
        int yearBase = Integer.parseInt(daysBaseYearBase.split("/")[1].substring(0, 3));
        double interest = Double.parseDouble(currentBalance) * Double.parseDouble(currentEffectiveRate) / 100 / yearBase *
                DateTime.getDaysBetweenTwoDates(loanAccount.getDateOpened(),
                        DateTime.getDatePlusMonth(loanAccount.getDateOpened(), 1), false);
        paymentDueData.setInterest(String.format("%.2f", interest));
        paymentDueData.setEscrow(0.00);
        paymentDueData.setAmount(Double.parseDouble(paymentDueData.getInterest()) + paymentDueData.getEscrow() + paymentDueData.getPrincipal());
        paymentDueData.setDateAssessed(WebAdminActions.loginActions().getSystemDate());
        paymentDueData.setPaymentDueType(loanAccount.getPaymentAmountType());
        paymentDueData.setPaymentDueStatus("Active");

        return paymentDueData;
    }

    public PaymentDueData getPaymentDueInfoPrinAndIntBill(Account loanAccount) {
        PaymentDueData paymentDueData = new PaymentDueData();
        paymentDueData.setDueDate(loanAccount.getNextPaymentBilledDueDate());
        String currentBalance = Pages.accountDetailsPage().getCurrentBalance();
        String currentEffectiveRate = Pages.accountDetailsPage().getCurrentEffectiveRate();
        String daysBaseYearBase = Pages.accountDetailsPage().getDaysBaseYearBase();
        int yearBase = Integer.parseInt(daysBaseYearBase.split("/")[1].substring(0, 3));
        double interest;
        int cycle = Integer.parseInt(loanAccount.getCycleCode());
        int day = Integer.parseInt(loanAccount.getDateOpened().substring(3, 5));
        double mainPartInterest = Double.parseDouble(currentBalance) * Double.parseDouble(currentEffectiveRate) / 100 / yearBase;
        if (cycle > day) {
            interest = mainPartInterest * cycle;
        } else {
            int daysInMonth = DateTime.getDaysInMonth(Integer.parseInt(loanAccount.getDateOpened().substring(0, 2))+1);
            interest = mainPartInterest * (daysInMonth
                    - day + cycle);
        }
        paymentDueData.setInterest(String.format("%.2f", interest));
        paymentDueData.setEscrow(0.00);
        Pages.accountDetailsPage().clickPaymentInfoTab();
        Pages.accountPaymentInfoPage().clickPaymentDueRecord();
        paymentDueData.setAmount(Double.parseDouble(Pages.accountPaymentInfoPage().getDisabledAmount()));
        paymentDueData.setPrincipal(paymentDueData.getAmount() - Double.parseDouble(paymentDueData.getInterest()));
        paymentDueData.setDateAssessed(WebAdminActions.loginActions().getSystemDate());
        paymentDueData.setPaymentDueType(loanAccount.getPaymentAmountType());
        paymentDueData.setPaymentDueStatus("Active");

        return paymentDueData;
    }

    public PaymentDueData getPaymentDueInfoIntOnlyBill(Account loanAccount) {
        PaymentDueData paymentDueData = new PaymentDueData();
        paymentDueData.setDueDate(loanAccount.getNextPaymentBilledDueDate());
        String currentBalance = Pages.accountDetailsPage().getCurrentBalance();
        String currentEffectiveRate = Pages.accountDetailsPage().getCurrentEffectiveRate();
        String daysBaseYearBase = Pages.accountDetailsPage().getDaysBaseYearBase();
        int yearBase = Integer.parseInt(daysBaseYearBase.split("/")[1].substring(0, 3));
        int countOfDays = getCountOfDays(loanAccount);
        double interest = Double.parseDouble(currentBalance) * Double.parseDouble(currentEffectiveRate) / 100 / yearBase *
                countOfDays;
        paymentDueData.setInterest(String.format("%.2f", interest));
        paymentDueData.setEscrow(0.00);
        Pages.accountDetailsPage().clickPaymentInfoTab();
        Pages.accountPaymentInfoPage().clickPaymentDueRecord();
        paymentDueData.setAmount(Double.parseDouble(Pages.accountPaymentInfoPage().getDisabledAmount()));
        paymentDueData.setPrincipal(0.00);
        paymentDueData.setDateAssessed(WebAdminActions.loginActions().getSystemDate());
        paymentDueData.setPaymentDueType(loanAccount.getPaymentAmountType());
        paymentDueData.setPaymentDueStatus("Active");

        return paymentDueData;
    }

    private int getCountOfDays(Account loanAccount) {
        int cycleCode = Integer.parseInt(loanAccount.getCycleCode());
        int number = Integer.parseInt(loanAccount.getDateOpened().substring(3, 5));
        int countOfDays;
        if (cycleCode > number) {
            countOfDays = cycleCode - number;
        } else if (cycleCode < number) {
            countOfDays = cycleCode + (DateTime.getDaysInMonth(Integer.parseInt(loanAccount.getDateOpened().substring(0, 2)))
                    - number);
        } else {
            countOfDays = DateTime.getDaysInMonth(Integer.parseInt(loanAccount.getDateOpened().substring(0, 2)));
        }
        return countOfDays + 1;
    }

    public PaymentDueData getPaymentDueInfoForCyclePrinAndInt(Account loanAccount) {
        PaymentDueData paymentDueData = new PaymentDueData();
        paymentDueData.setDueDate(loanAccount.getNextPaymentBilledDueDate());
        paymentDueData.setPrincipal(0.00);
        paymentDueData.setInterest(String.format("%.2f", 0.00));
        paymentDueData.setEscrow(0.00);
        Pages.accountDetailsPage().clickPaymentInfoTab();
        Pages.accountPaymentInfoPage().clickPaymentDueRecord();
        paymentDueData.setAmount(Double.parseDouble(Pages.accountPaymentInfoPage().getDisabledAmount()));
        paymentDueData.setDateAssessed(WebAdminActions.loginActions().getSystemDate());
        paymentDueData.setPaymentDueType(loanAccount.getPaymentAmountType());
        paymentDueData.setPaymentDueStatus("Active");

        return paymentDueData;
    }
}