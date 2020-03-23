package com.nymbus.actions.clients;

import com.nymbus.models.TempClient;
import com.nymbus.pages.Pages;

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
}
