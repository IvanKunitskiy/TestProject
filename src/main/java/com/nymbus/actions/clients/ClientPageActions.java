package com.nymbus.actions.clients;

import com.nymbus.models.TempClient;
import com.nymbus.pages.Pages;

import java.util.ArrayList;
import java.util.List;

public class ClientPageActions {

    public List<TempClient> getAllClientsFromLookupResults(int lookupResultsCount) {
        List<TempClient> clients = new ArrayList<>();
        for (int i = 1; i <= lookupResultsCount; i++) {
            TempClient client = new TempClient(
                    Pages.clientsPage().getClientFirstNameFromLookupResultByIndex(i),
                    Pages.clientsPage().getClientLastNameFromLookupResultByIndex(i)
            );
            clients.add(client);
        }

        return clients;
    }

    public boolean verifySearchResultsByFirstName(List<TempClient> clients, String partOfName) {
        return clients.stream()
                /*This part filters only valid values*/
                .filter(c -> c.getFirstName().contains(partOfName))
                /*There will be only valid values.
                So if one of the values is not valid, count of valid values
                and the size of source list will not be equal and returns false*/
                .count() == clients.size();
    }

    public boolean verifySearchResultsByLastName(List<TempClient> clients, String partOfName) {
        return clients.stream()
                .filter(c -> c.getLastName().contains(partOfName))
                .count() == clients.size();
    }
}
