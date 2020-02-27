package com.nymbus.actions.clients.maintenance;

import com.nymbus.model.client.Client;
import com.nymbus.pages.Pages;
import org.testng.Assert;

public class MaintenancePageActions {

    public void addNewDebitCard(Client client) {
        Pages.maintenancePage().clickOnNewDebitCardButton();
        Pages.maintenancePage().clickOnBinNumberInputField();
        Pages.maintenancePage().clickOnBinNumberDropdownValue(client.getBinControl().getCardDescription());
        Assert.assertFalse(Pages.maintenancePage().getDescriptionInputFieldValue().isEmpty());
        Pages.maintenancePage().clickOnNextButton();
    }
}
