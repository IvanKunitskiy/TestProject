package com.nymbus.actions.account;

import com.nymbus.core.utils.SelenideTools;
import com.nymbus.pages.Pages;

public class AccountMaintenanceActions {
    public void expandAllRows() {
        int sleepTimeout = 2;
        do {
            Pages.accountMaintenancePage().clickViewMoreButton();
            SelenideTools.sleep(sleepTimeout);
        }
        while (Pages.accountMaintenancePage().isMoreButtonVisible());
    }
}