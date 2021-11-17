package com.nymbus.actions.journal;

import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.cashier.CashierDefinedTransactions;
import com.nymbus.pages.Pages;
import org.testng.Assert;

import java.util.List;

public class JournalActions {

    public void goToJournalPage() {
        Pages.aSideMenuPage().clickJournalMenuItem();
    }

    public void applyFilterByAccountNumber(String accountNumber) {
        Pages.journalPage().waitForProofDateSpan();
        Pages.journalPage().fillInAccountNumber(accountNumber);
       /* Pages.journalPage().waitForMainSpinnerVisibility();
        Pages.journalPage().waitForMainSpinnerInvisibility();*/
        SelenideTools.sleep(5);
    }

    public void applyFilterByTransactionNumber(String transactionNumber) {
        Pages.journalPage().waitForProofDateSpan();
        Pages.journalPage().fillInTransactionNumber(transactionNumber);
       /* Pages.journalPage().waitForMainSpinnerVisibility();
        Pages.journalPage().waitForMainSpinnerInvisibility();*/
        SelenideTools.sleep(5);
    }

    public void clickLastTransaction() {
        int tempIndex = 1;
        Pages.journalPage().clickItemInTable(tempIndex);
    }

    public String getTransactionState() {
        int tempIndex = 1;
        return Pages.journalDetailsPage().getItemState(tempIndex);
    }

    public void setTeller(String tellerName) {
        Pages.journalPage().clickTellerInputField();
        List<String> listOfTeller = Pages.journalPage().getListOfTeller();

        Assert.assertTrue(listOfTeller.size() > 0, "There are no 'Teller' options available");
        Pages.journalPage().clickTellerSelectorOption(tellerName);
    }

    public boolean checkTransactionDataIsPresent(String transactionData) {
        SelenideTools.sleep(5);
        List<String> listOfTransactionData = Pages.journalPage().getListOfTransactionData();
        for (String trData : listOfTransactionData) {
            if (trData.contains(transactionData)) {
                return true;
            }
        }
        return false;
    }

    public String getTransactionRootId(CashierDefinedTransactions template) {
        SelenideTools.openUrlInNewWindow(Constants.URL);
        SelenideTools.switchToLastTab();
        Pages.aSideMenuPage().clickJournalMenuItem();
        Pages.journalPage().searchCDT(template.getOperation());
        Pages.journalPage().clickCheckbox("Cashier-Defined", 1);
        Pages.journalPage().clickCheckbox("Transaction", 2);
        Pages.journalPage().clickCheckbox("Transaction", 3);
        SelenideTools.sleep(Constants.MINI_TIMEOUT);
        Pages.journalPage().clickItemInTable(1);
        String url = SelenideTools.getCurrentUrl();
        String[] arr = url.split("/");
        int position = arr.length - 1;
        SelenideTools.closeCurrentTab();
        SelenideTools.switchToLastTab();
        return arr[position];
    }
}
