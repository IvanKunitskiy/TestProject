package com.nymbus.actions.journal;

import com.nymbus.core.utils.SelenideTools;
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
}
