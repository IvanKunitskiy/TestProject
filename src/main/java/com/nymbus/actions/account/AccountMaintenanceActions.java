package com.nymbus.actions.account;

import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.pages.Pages;

public class AccountMaintenanceActions {
    public void expandAllRows() {
        do {
            Pages.accountMaintenancePage().clickViewMoreButton();
            SelenideTools.sleep(Constants.MICRO_TIMEOUT);
        }
        while (Pages.accountMaintenancePage().isMoreButtonVisible());
    }
}