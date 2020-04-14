package com.nymbus.actions.journal;

import com.nymbus.pages.Pages;

public class JournalActions {

    public void goToJournalPage() {
        Pages.aSideMenuPage().clickJournalMenuItem();
    }

    public void clickLastTransaction() {
        int tempIndex = 1;
        Pages.journalPage().clickItemInTable(tempIndex);
    }

    public String getTransactionState() {
        int tempIndex = 1;
        return Pages.journalDetailsPage().getItemState(tempIndex);
    }
}
