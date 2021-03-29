package com.nymbus.actions.clients;

import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.utils.DateTime;
import com.nymbus.models.TempClient;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.loanaccount.PaymentAmountType;
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

    public List<String> getAllClientFirstName(int lookupResultsCount){
        List<String> listOfFirstName = new ArrayList<>();

        for (int i = 1; i <= lookupResultsCount; i++) {
            listOfFirstName.add(Pages.clientsSearchPage().getClientFirstNameFromLookupResultByIndex(i));
        }

        return listOfFirstName;
    }

    public List<String> getAllClientLastName(int lookupResultsCount){
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
        for (int i = 1; i <= count; i++) {
            Pages.clientDetailsPage().clickCloseNotificationByIndex(i);

            Pages.clientDetailsPage().waitForNotificationInvisibility(i);
        }
    }

    public void checkRejectedItems(int rejectedTransactionsItems){
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
        int daysBase = Integer.parseInt(daysBaseYearBase.split("/")[0].substring(0, 3));
        double interest = Double.parseDouble(currentBalance) * Double.parseDouble(currentEffectiveRate)/ 100 / yearBase *
                DateTime.getDaysBetweenTwoDates(loanAccount.getDateOpened(),
                        DateTime.getDatePlusMonth(loanAccount.getDateOpened(),1),false);
        paymentDueData.setInterest(String.format("%.2f",interest));
        paymentDueData.setEscrow(0.00);
        paymentDueData.setAmount(Double.parseDouble(paymentDueData.getInterest()) + paymentDueData.getEscrow() + paymentDueData.getPrincipal());
        paymentDueData.setDateAssessed(WebAdminActions.loginActions().getSystemDate());
        paymentDueData.setPaymentDueType(PaymentAmountType.INTEREST_ONLY.getPaymentAmountType());
        paymentDueData.setPaymentDueStatus("Active");

        return paymentDueData;
    }
}