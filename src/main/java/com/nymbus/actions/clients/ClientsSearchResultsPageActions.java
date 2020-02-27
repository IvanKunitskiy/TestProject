package com.nymbus.actions.clients;

import com.nymbus.model.TempClient;
import com.nymbus.pages.Pages;

import java.util.ArrayList;
import java.util.List;

public class ClientsSearchResultsPageActions {

    public List<TempClient> getAllClientsFromResult(int lookupResultsCount) {
        List<TempClient> clients = new ArrayList<>();
        for (int i = 1; i <= lookupResultsCount; i++) {
            TempClient client = new TempClient();
            client.setFirstName(Pages.clientsSearchResultsPage().getClientFirstNameFromResultByIndex(i));
            client.setLastName(Pages.clientsSearchResultsPage().getClientLastNameFromResultByIndex(i));
            clients.add(client);
        }

        return clients;
    }
}
